package com.weshopifyplatform.app.beans;

import java.io.Serializable;

import lombok.Data;


@Data
public class AuthenticationBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1430143080502662482L;
	
	private String userName;
	private String password;
	
	private boolean isAuthenticated = false;
}
