package com.appsdeveloperblog.app.ws.mobileappws.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.appsdeveloperblog.app.ws.mobileappws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.mobileappws.io.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.repositories.PasswordResetTokenRepository;
import com.appsdeveloperblog.app.ws.mobileappws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.mobileappws.service.EmailService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.Utils;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	PasswordResetTokenRepository passwordResetTokenRepository;

	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	EmailService emailService;

	String userId = "dfwlejfo2i";
	String encryptedPassword = "aefoj2fwef93ew5qwd";
	UserEntity userEntity = new UserEntity();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		userEntity.setId(1L);
		userEntity.setFirstName("Apiwit");
		userEntity.setLastName("Wutithumkitti");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("test@test.com");
		userEntity.setEmailVerificationToken("3okew5er36wertasdg");
		userEntity.setAddresses(getAddressesEntity());
	}

	@Test
	void testGetUserString() {

		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("test@test.com");

		assertNotNull(userDto);
		assertEquals("Apiwit", userDto.getFirstName());

	}

	@Test
	void testGetUser_UsernameNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);

		assertThrows(UsernameNotFoundException.class,

				() -> {
					userService.getUser("test0@test.com");
				}

		);
	}

	@Test
	void testCreateUser_CreateUserServiceException() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("Apiwit");
		userDto.setLastName("Wutithumkitti");
		userDto.setPassword("password123");
		userDto.setEmail("apiwit.wutithumkitti@test.com");
		
		assertThrows(UserServiceException.class,

				() -> {
					userService.createUser(userDto);
				}

		);
	}

	@Test
	void testCreateUser() {

		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.generateAddressId(anyInt())).thenReturn("asf43gu90etglad9023r90wqedikdfklj");
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		Mockito.doNothing().when(emailService).sendVerificationEmail(any(UserDto.class));
		;

		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("Apiwit");
		userDto.setLastName("Wutithumkitti");
		userDto.setPassword("password123");
		userDto.setEmail("apiwit.wutithumkitti@test.com");

		UserDto storedUserDetails = userService.createUser(userDto);
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());

		verify(utils, times(storedUserDetails.getAddresses().size())).generateAddressId(30);
		verify(bCryptPasswordEncoder, times(1)).encode("password123");
		verify(userRepository, times(1)).save(any(UserEntity.class));

	}

	private List<AddressDTO> getAddressesDto() {
		AddressDTO addressDto = new AddressDTO();
		addressDto.setTypes("shipping");
		addressDto.setCity("Bangkok");
		addressDto.setCountry("Thailand");
		addressDto.setPostalCode("10120");
		addressDto.setStreetName("Rama 3");

		AddressDTO billingAddressDto = new AddressDTO();
		billingAddressDto.setTypes("billing");
		billingAddressDto.setCity("Bangkok");
		billingAddressDto.setCountry("Thailand");
		billingAddressDto.setPostalCode("10120");
		billingAddressDto.setStreetName("Rama 3");

		List<AddressDTO> addresses = new ArrayList<>();
		addresses.add(addressDto);
		addresses.add(billingAddressDto);

		return addresses;
	}

	private List<AddressEntity> getAddressesEntity() {

		List<AddressDTO> addresses = getAddressesDto();

		Type listType = new TypeToken<List<AddressEntity>>() {
		}.getType();

		return new ModelMapper().map(addresses, listType);
	}

}
