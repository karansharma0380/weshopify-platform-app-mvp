package com.weshopifyplatform.app.beans;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class CustomerBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7279204172177283257L;
	
	private String Id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String email;
	private String mobile;
	private String role;
}
