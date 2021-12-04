package com.appsdeveloperblog.app.ws.mobileappws.ui.model.response;

import java.util.List;

import lombok.Data;

@Data
public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressesRest> addresses;
    
}
