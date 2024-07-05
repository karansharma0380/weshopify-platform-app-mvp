package com.weshopifyplatform.app.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
// A table with name 'Customer' will generate in the Database. If you want custom name use below annotation
//  @Table(name = 'WeshopifyCustomer')
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2767152150423849249L;
 //Every DB table should have Identity Value.
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int custId;
	
	
	
	private String firstName;
	private String lastName;
	
	@Column(name = "CustomerLogin", nullable = false,unique = true)
	private String userName;
	
	@Column(nullable = false, length=8)
	private String password;
	
	@Column(unique = true,nullable =false)
	private String email;
	
	@Column(unique=true, nullable = false)
	private String mobile;
	private String role;
	
}
