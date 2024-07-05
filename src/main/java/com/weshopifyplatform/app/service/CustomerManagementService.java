package com.weshopifyplatform.app.service;

import java.util.List;

import com.weshopifyplatform.app.beans.AuthenticationBean;
import com.weshopifyplatform.app.beans.CustomerBean;

public interface CustomerManagementService {
	
	CustomerBean registerCustomer(CustomerBean customerBean);
	AuthenticationBean authenticateBean(AuthenticationBean authenticationBean);
	List<CustomerBean> findAllCustomer();
	
	List<CustomerBean> deleteCustomerById(String custId); //We will delete this customer with id and return
	// the rest of the customers
	CustomerBean findCustomerById(String customerId);
}
