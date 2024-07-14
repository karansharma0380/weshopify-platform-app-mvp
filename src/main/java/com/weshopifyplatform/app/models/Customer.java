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
	
	@Id //For making this property primary key. 
	@GeneratedValue(strategy = GenerationType.AUTO) //Diff DB use different PK generation method so now we dont have to worry about DB type
	private int custId;
	
	
	
	private String firstName; // If we don't provide the specific name for the column, this will have first_name at N, _ added  
	private String lastName;
	
	@Column(name = "CustomerLogin", nullable = false,unique = true) //cannot be null and always have unique values
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(unique = true,nullable =false)
	private String email;
	
	@Column(unique=true, nullable = false)
	private String mobile;
	private String role;
	
}
