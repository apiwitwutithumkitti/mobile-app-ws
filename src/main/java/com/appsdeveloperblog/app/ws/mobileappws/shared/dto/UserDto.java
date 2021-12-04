package com.appsdeveloperblog.app.ws.mobileappws.shared.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class UserDto implements Serializable {
	
	private static final long serialVersionUID = -3923102606499238858L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private boolean emailVerificationStatus = false;
    private List<AddressDTO> addresses;

}
