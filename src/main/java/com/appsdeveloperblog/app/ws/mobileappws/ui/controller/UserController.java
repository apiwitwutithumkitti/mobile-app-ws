package com.appsdeveloperblog.app.ws.mobileappws.ui.controller;

import com.appsdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.request.UserDetailsRequsetModel;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.UserRest;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users") // http://localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;
    
    @GetMapping(produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUser(@RequestParam(value="page", defaultValue="0") int page, 
    		@RequestParam(value="limit", defaultValue="2") int limit) {
    	List<UserRest> returnValue = new ArrayList<>();
    	
    	List<UserDto> users = userService.getUser(page, limit);
    	
    	for (UserDto userDto : users) {
    		UserRest userModel = new UserRest();
    		BeanUtils.copyProperties(userDto, userModel);
    		returnValue.add(userModel);
    	}
    	
    	return returnValue;
    }
    
    @GetMapping(path="/{id}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id)
    {
    	UserRest returnValue = new UserRest();
    	
    	UserDto userDto = userService.getUserByUserId(id);
    	BeanUtils.copyProperties(userDto, returnValue);
    	
        return returnValue;
    }

    @PostMapping(
    		consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    		)
    public UserRest createUser(@RequestBody UserDetailsRequsetModel userDetails) throws Exception
    {
        
        if (userDetails.getFirstName().isEmpty()) 
        	throw new NullPointerException("The object is null");
        
        UserRest returnValue = new UserRest();
        
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createUser, returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser()
    {
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser()
    {
        return "delete user was called";
    }

}
