package com.appsdeveloperblog.app.ws.mobileappws.service;

import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
	UserDto getUserByUserId(String id);
	List<UserDto> getUser(int page, int limit);
	void deleteUser(String userId);
	UserDto updateUser(String id, UserDto userDto);

}
