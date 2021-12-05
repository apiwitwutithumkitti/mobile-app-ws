package com.appsdeveloperblog.app.ws.mobileappws.service;

import com.appsdeveloperblog.app.ws.mobileappws.shared.dto.UserDto;

public interface EmailService {
	public void sendVerificationEmail(UserDto user);

	public boolean sendPasswordResetRequest(String firstName, String email, String token);
}
