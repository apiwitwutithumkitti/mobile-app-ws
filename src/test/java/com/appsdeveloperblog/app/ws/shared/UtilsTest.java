package com.appsdeveloperblog.app.ws.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.appsdeveloperblog.app.ws.mobileappws.shared.Utils;

class UtilsTest {
	
	@Autowired
	Utils utils;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGenerateUserId() {
		String userId = utils.generateUserId(30);
		String userId2 = utils.generateUserId(30);
		
		assertNotNull(userId);
		assertNotNull(userId2);
		
		assertTrue(userId.length() == 30);
		assertTrue(!userId.equalsIgnoreCase(userId));
	}

	@Test
	void testHasTokenExpired() {
		String token = utils.generateEmailVerificationToken("4fiag6lwvlpuctn73ou");
		assertNotNull(token);
		
		boolean hasTokenExpired = Utils.hasTokenExpired(token);
		
		assertFalse(hasTokenExpired);
	}
	
	@Test
	void testHasTokenNotExpired() {
		String expiredToken = "$2a$10$gno3qCpdDIWORdwd.Tx9sOEUr73IHAEczkaHaODI.gLXtQSLcJEqG";
		boolean hasTokenExpired = Utils.hasTokenExpired(expiredToken);
		
		assertTrue(hasTokenExpired);
	}

}
