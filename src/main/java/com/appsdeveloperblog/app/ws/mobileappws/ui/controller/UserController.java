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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

@Slf4j
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
    	
    	log.info("Request Get All Users");
    	List<UserRest> returnValue = new ArrayList<>();
    	
    	List<UserDto> users = userService.getUser(page, limit);
    	
    	for (UserDto userDto : users) {
    		UserRest userModel = new UserRest();
    		BeanUtils.copyProperties(userDto, userModel);
    		returnValue.add(userModel);
    	}
    	
    	log.debug("Response");
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String json = "\n" + gson.toJson(returnValue);
    	log.debug(json);
    	
    	return returnValue;
    }
    
    @GetMapping(path="/{id}", produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id)
    {
    	log.info("Request specific user");
    	
    	UserRest returnValue = new UserRest();
    	
    	ModelMapper modelMapper = new ModelMapper();
    	
    	UserDto userDto = userService.getUserByUserId(id);
    	
    	returnValue = modelMapper.map(userDto, UserRest.class);
//    	BeanUtils.copyProperties(userDto, returnValue);
    	
    	log.debug("Response");
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String json = "\n" + gson.toJson(returnValue);
    	log.debug(json);
    	
        return returnValue;
    }

    @PostMapping(
    		consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    		)
    public UserRest createUser(@RequestBody UserDetailsRequsetModel userDetails) throws Exception
    {
    	log.info("Request create user");
    	log.debug("Request Body");
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String json = gson.toJson(userDetails);
    	log.debug(json);
        
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

        log.debug("Response");
    	json = "\n" + gson.toJson(returnValue);
    	log.debug(json);
        
        return returnValue;
    }

    @PutMapping(path="/{id}",
    		consumes= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    		)
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequsetModel userDetails)
    {
    	
    	log.info("Request update user");
    	log.debug("User id : " + id);
    	log.debug("Request Body");
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String json = gson.toJson(userDetails);
    	log.debug(json);
    	
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        
        UserDto updateUser = userService.updateUser(id, userDto);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(updateUser, returnValue);
        
        log.debug("Response");
    	json = "\n" + gson.toJson(returnValue);
    	log.debug(json);
        
        return returnValue;
    }

    @DeleteMapping(path="/{id}",
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    		)
    public OperationStatusModel deleteUser(@PathVariable String id)
    {
    	log.info("Request delete user");
    	log.debug("User id : " + id);

    	OperationStatusModel returnValue = new OperationStatusModel();
    	returnValue.setOperationName(RequestOperationName.DELETE.name());
    	
    	userService.deleteUser(id);
    	
    	returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
    	
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String json = gson.toJson(returnValue);
    	log.debug(json);
    	
    	
        return returnValue;
    }
    
    @GetMapping(path="/{id}/addresses",
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String id) {
    		
    	log.info("Request get specific user and list all addresses");
    	log.debug("User id : " + id);
    	
    	List<AddressesRest> returnValue = new ArrayList<>();
    	
    	List<AddressDTO> addressesDTO = addressesService.getAddresses(id);
    	
    	if (addressesDTO != null && !addressesDTO.isEmpty()) {
    		Type listType = new TypeToken<List<AddressesRest>>() {}.getType();
        	returnValue = new ModelMapper().map(addressesDTO,listType);
        	
        	for (AddressesRest addressRest : returnValue) {
        		Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
        				.getUserAddress(id, addressRest.getAddressId()))
        				.withSelfRel();
        		addressRest.add(selfLink);
        	}
    	}
    	
    	Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(id).withRel("user");
    	Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
    			.getUserAddresses(id))
    			.withSelfRel();
//    	
//    	log.debug("Response");
//    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    	String json = gson.toJson(returnValue);
//    	log.debug(json);
    	  	
    	return CollectionModel.of(returnValue, userLink, selfLink);
    	
    }
    
    @GetMapping(path="/{userId}/addresses/{addressId}",
    		produces= {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public EntityModel<AddressesRest> getUserAddress(@PathVariable String userId, @PathVariable String addressId) {
    	
    	log.info("Request get specific user and specific addresses");
    	log.debug("User Id : " + userId + ", Address Id : " + addressId);
    	
    	AddressDTO addressesDto = addressService.getAddress(addressId);
    	
    	ModelMapper modelMapper = new ModelMapper();
    	AddressesRest returnValue = modelMapper.map(addressesDto, AddressesRest.class);
    	
    	// http://localhost:8080/users/<userId>/addresses/<addressId>
    	Link userLink = WebMvcLinkBuilder.linkTo(UserController.class).slash(userId).withRel("user");
    	Link userAddressesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(userId))
//    			.slash(userId)
//    			.slash("addresses")
    			.withRel("addresses");
    	Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddress(userId, addressId))
//    			.slash(userId)
//    			.slash("addresses")
//    			.slash(addressId)
    			.withSelfRel();
    	
//    	
//    	returnValue.add(userLink);
//    	returnValue.add(userAddressesLink);
//    	returnValue.add(selfLink);
    	
    	log.debug("Response");
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	String json = gson.toJson(returnValue);
    	log.debug(json);
    	  	
    	
    	return EntityModel.of(returnValue, Arrays.asList(userLink, userAddressesLink, selfLink));
    	
    }

}
