package com.appsdeveloperblog.app.ws.mobileappws.io.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity(name="password_reset_tokens")
@Getter
@Setter
public class PasswordResetTokenEntity implements Serializable {
	
	private static final long serialVersionUID = -4677817303417607209L;

	@Id
	@GeneratedValue
	private long id;
	
	private String token;
	
	@OneToOne()
	@JoinColumn(name="users_id")
	private UserEntity userDetails;
}
