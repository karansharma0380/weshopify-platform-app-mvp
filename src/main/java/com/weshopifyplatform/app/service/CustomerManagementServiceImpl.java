package com.weshopifyplatform.app.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.weshopifyplatform.app.beans.AuthenticationBean;
import com.weshopifyplatform.app.beans.CustomerBean;
import com.weshopifyplatform.app.models.Customer;
import com.weshopifyplatform.app.repos.CustomerRepository;

@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {
	
	private static LinkedHashMap<String,CustomerBean> IN_MEMORY_DB= new LinkedHashMap<>();
	//why this as an in memeory database and TreeHashmap could have been a better choice why?
	//
	private static LinkedHashMap<String,CustomerBean> CUSTOMER_IN_MEMORY_DB = new LinkedHashMap<>();
	
	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public CustomerBean registerCustomer(CustomerBean customerBean) {
		
		if(StringUtils.hasText(customerBean.getRole())) {
			/*
			 *  Customer Registration!
			 * */
			if(!StringUtils.hasText(customerBean.getId())) {
				customerBean.setId(UUID.randomUUID().toString());
			}
			CUSTOMER_IN_MEMORY_DB.put(customerBean.getId(), customerBean);
		}
		else {
			/*
			 *  User Registration
			 * */
			customerBean.setId(UUID.randomUUID().toString());
			IN_MEMORY_DB.put(customerBean.getUserName(), customerBean);
			}
		return customerBean;
	}

	@Override
	public AuthenticationBean authenticateBean(AuthenticationBean authenticationBean) {
		CustomerBean custB = IN_MEMORY_DB.get(authenticationBean.getUserName());
		if(custB!=null && custB.getPassword().equals(authenticationBean.getPassword())) {
			authenticationBean.setAuthenticated(true);
		}
		return authenticationBean;
	}

	@Override
	public List<CustomerBean> findAllCustomer() {
		List<CustomerBean> customerList = new ArrayList<>();
		customerList.addAll(CUSTOMER_IN_MEMORY_DB.values());
		return customerList;
	}

	@Override
	public List<CustomerBean> deleteCustomerById(String custId) {
		CUSTOMER_IN_MEMORY_DB.remove(custId);
		return findAllCustomer();
	}

	@Override
	public CustomerBean findCustomerById(String customerId) {
		return CUSTOMER_IN_MEMORY_DB.get(customerId);
	}
	
	
	private Customer mapBeanToEntity(CustomerBean customerBean) {
		
		Customer customer = new Customer();
		customer.setFirstName(customerBean.getFirstName());
		customer.setLastName(customerBean.getLastName());
		customer.setEmail(customerBean.getEmail());
		customer.setPassword(customerBean.getPassword());
		customer.setMobile(customerBean.getMobile());
		customer.setUserName(customerBean.getUserName());
		
		return customer;
	}
	
     private CustomerBean mapEntityToBean(Customer customer) {
		
	    CustomerBean customerBean = new CustomerBean();
	    customerBean.setFirstName(customer.getFirstName());
	    customerBean.setLastName(customer.getLastName());
	    customerBean.setEmail(customer.getEmail());
	    customerBean.setPassword(customer.getPassword());
	    customerBean.setMobile(customer.getMobile());
	    customerBean.setUserName(customer.getUserName());
		
		return customerBean;
	}
	
	

}
