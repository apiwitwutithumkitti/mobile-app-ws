package com.appsdeveloperblog.app.ws.mobileappws.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.appsdeveloperblog.app.ws.mobileappws.exceptions.UserServiceException;
import com.appsdeveloperblog.app.ws.mobileappws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.mobileappws.io.repositories.UserRepository;
import com.appsdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.Utils;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.ErrorMessages;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {

        if (userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        String publicUserID = utils.generateUserId(30);
        userEntity.setUserId(publicUserID);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        

        UserEntity storedUserDetails = userRepository.save(userEntity);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);

        return returnValue;
    }
    
    @Override
    public UserDto getUser(String email) {
    	UserEntity userEntity = userRepository.findByEmail(email);
    	
    	if (userEntity == null) throw new UsernameNotFoundException("User with Email : " + email + " not found.");
    	
    	UserDto returnValue = new UserDto();
    	BeanUtils.copyProperties(userEntity, returnValue);
    	return returnValue;
    }
    
    @Override
    public UserDto getUserByUserId(String userId) {
    	UserEntity userEntity = userRepository.findByUserId(userId);
    	
    	if (userEntity == null) throw  new UsernameNotFoundException("User with Id : " + userId + " not found.");
    	
    	UserDto returnValue = new UserDto();
    	BeanUtils.copyProperties(userEntity, returnValue);
    	
    	return returnValue;
    }
    
    @Override
    public UserDto updateUser(String userId, UserDto user) {
    	UserEntity userEntity = userRepository.findByUserId(userId);
    	
    	if (userEntity == null)
    		throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
    	
    	UserDto returnValue = new UserDto();
    	
    	userEntity.setFirstName(user.getFirstName());
    	userEntity.setLastName(user.getLastName());
    	
    	UserEntity updateUserDetails = userRepository.save(userEntity);
    	
    	BeanUtils.copyProperties(updateUserDetails, returnValue);
    	
    	return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
       
        if (userEntity == null) throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

	@Override
	public List<UserDto> getUser(int page, int limit) {
		
		if (page > 0) page -= 1;
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		
		List<UserDto> returnValue = new ArrayList<>();
		
		for (UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity, userDto);
			returnValue.add(userDto);
		}
		
		return returnValue;
	}
	
	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		if (userEntity == null)
			throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
			
		userRepository.delete(userEntity);
	}
    
}
