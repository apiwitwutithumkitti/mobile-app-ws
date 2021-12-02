package com.appsdeveloperblog.app.ws.mobileappws.ui.controller;

import com.appsdeveloperblog.app.ws.mobileappws.service.AddressService;
import com.appsdeveloperblog.app.ws.mobileappws.service.UserService;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.AddressDTO;
import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.request.UserDetailsRequsetModel;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.AddressesRest;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.OperationStatusModel;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.RequestOperationStatus;
import com.appsdeveloperblog.app.ws.mobileappws.ui.model.response.UserRest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
    
    @Autowired
    AddressService addressService;
    
    @Autowired
    AddressService addressesService;
    
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
    	
    	ModelMapper modelMapper = new ModelMapper();
    	
    	UserDto userDto = userService.getUserByUserId(id);
    	
    	returnValue = modelMapper.map(userDto, UserRest.class);
//    	BeanUtils.copyProperties(userDto, returnValue);
    	
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
        
//        UserDto userDto = new UserDto();
//        BeanUtils.copyProperties(userDetails, userDto);
        
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);

        UserDto createUser = userService.createUser(userDto);
//        BeanUtils.copyProperties(createUser, returnValue);
        returnValue = modelMapper.map(createUser, UserRest.class);

        return returnValue;
    }

    @PutMapping(path="/{id}",
    		consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    		)
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequsetModel userDetails)
    {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        
        UserDto updateUser = userService.updateUser(id, userDto);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(updateUser, returnValue);
        
        return returnValue;
    }

    @DeleteMapping(path="/{id}",
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    		)
    public OperationStatusModel deleteUser(@PathVariable String id)
    {
    	OperationStatusModel returnValue = new OperationStatusModel();
    	returnValue.setOperationName(RequestOperationName.DELETE.name());
    	
    	userService.deleteUser(id);
    	
    	returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
    	
        return returnValue;
    }
    
    @GetMapping(path="/{id}/addresses",
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<AddressesRest> getUserAddesses(@PathVariable String id) {
    	List<AddressesRest> returnValue = new ArrayList<>();
    	
    	List<AddressDTO> addressesDTO = addressesService.getAddresses(id);
    	
    	if (addressesDTO != null && !addressesDTO.isEmpty()) {
    		Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
        	returnValue = new ModelMapper().map(addressesDTO,listType);
    	}
    	  	
    	return returnValue;
    	
    }
    
    @GetMapping(path="/{userId}/addresses/{addressId}",
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public AddressesRest getUserAddesses1(@PathVariable String addressId) {
    	
    	AddressDTO addressDto = addressService.getAddress(addressId);
    	
    	ModelMapper modelMapper = new ModelMapper();
    	
    	return modelMapper.map(addressDto, AddressesRest.class);
    	
    }

}
