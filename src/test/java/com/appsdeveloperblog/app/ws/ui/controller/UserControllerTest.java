package com.appsdeveloperblog.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.appsdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.mobileappws.ui.controller.UserController;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.UserRest;

class UserControllerTest {

	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	
	UserDto userDto;
	
	final String USER_ID = "xgp17asd0uto47kfkmbrdp";
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		userDto = new UserDto();
		userDto.setFirstName("Apiwit");
		userDto.setLastName("Wutithumkitti");
		userDto.setEmail("apiwit.wutithumkitti@test.com");
		userDto.setEmailVerificationStatus(Boolean.FALSE);
		userDto.setEmailVerificationToken(null);
		userDto.setUserId(USER_ID);
		userDto.setAddresses(getAddressesDto());
		userDto.setEncryptedPassword("xds9joraq");
	}

	@Test
	void testGetUserIntInt() {
		when (userService.getUserByUserId(anyString())).thenReturn(userDto);
		
		UserRest userRest = userController.getUser(USER_ID);
		
		assertNotNull(userRest);
		assertEquals(USER_ID, userRest.getUserId());
		assertEquals(userDto.getFirstName(), userRest.getFirstName());
		assertEquals(userDto.getLastName(), userRest.getLastName());
		assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
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

}
