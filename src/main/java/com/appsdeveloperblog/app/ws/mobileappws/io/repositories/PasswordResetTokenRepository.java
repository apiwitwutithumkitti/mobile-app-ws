package com.appsdeveloperblog.app.ws.mobileappws.io.repositories;

import org.springframework.data.repository.CrudRepository;

import com.appsdeveloperblog.app.ws.mobileappws.io.entity.PasswordResetTokenEntity;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long>{

	PasswordResetTokenEntity findByToken(String token);

}
