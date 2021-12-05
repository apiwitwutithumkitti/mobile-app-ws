package com.appsdeveloperblog.app.ws.mobileappws.ui.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetModel {
	private String token;
	
	private String password;
}
