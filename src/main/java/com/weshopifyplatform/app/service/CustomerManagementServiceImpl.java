package com.weshopifyplatform.app.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.weshopifyplatform.app.beans.AuthenticationBean;
import com.weshopifyplatform.app.beans.CustomerBean;
import com.weshopifyplatform.app.models.Customer;
import com.weshopifyplatform.app.repos.CustomerRepository;

@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {
	
	//private static LinkedHashMap<String,CustomerBean> IN_MEMORY_DB= new LinkedHashMap<>();
	//why this as an in memeory database and TreeHashmap could have been a better choice why?
	//
	//private static LinkedHashMap<String,CustomerBean> CUSTOMER_IN_MEMORY_DB = new LinkedHashMap<>();
	
	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public CustomerBean registerCustomer(CustomerBean customerBean) {
//		This is all for the in-memory DB
//		if(StringUtils.hasText(customerBeanplaydesi.getRole())) {
//			/*
//			 *  Customer Registration!
//			 * */
//			if(!StringUtils.hasText(customerBean.getId())) {
//				customerBean.setId(UUID.randomUUID().toString());
//			}
//			CUSTOMER_IN_MEMORY_DB.put(customerBean.getId(), customerBean);
//		}
//		else {
//			/*
//			 *  User Registration
//			 * */
//			customerBean.setId(UUID.randomUUID().toString());
//			IN_MEMORY_DB.put(customerBean.getUserName(), customerBean);
//			}
//		return customerBean;
		
		if(!StringUtils.hasText(customerBean.getRole())) {
			customerBean.setRole("user");
		}
		
		Customer customer = mapBeanToEntity(customerBean); // Mapped our bean from user data to customer entity
		customerRepo.save(customer); //Save to the database
		customerBean = mapEntityToBean(customer); //Now this bean will have ID our database has to the entity. 
		
		return customerBean;
	}

	@Override
	public AuthenticationBean authenticateBean(AuthenticationBean authenticationBean) {
		
		Customer custB  = customerRepo.findCustomerByUserName(authenticationBean.getUserName());
		
		//CustomerBean custB = IN_MEMORY_DB.get(authenticationBean.getUserName()); in-Memory DB
		if(custB!=null && custB.getPassword().equals(authenticationBean.getPassword())) {
			authenticationBean.setAuthenticated(true);
		}
		return authenticationBean;
	}

	@Override
	public List<CustomerBean> findAllCustomer() {
		// this .findAll() will return an Iterable object means you can loop over it
		//Since this is coming from the DB, it will be cusomter Object. 
		// so need to map it to customerBean
		
		Iterable<Customer> customerList =  customerRepo.findAll();
		
		List<CustomerBean> customerBeanList = new ArrayList<>();
		customerList.forEach(customerEntity -> {
			CustomerBean customerBean = mapEntityToBean(customerEntity);
			customerBeanList.add(customerBean);
		});
		
		//customerList.addAll(CUSTOMER_IN_MEMORY_DB.values());
		return customerBeanList;
	}

	@Override
	public List<CustomerBean> deleteCustomerById(String custId) {
		//CUSTOMER_IN_MEMORY_DB.remove(custId);
		customerRepo.deleteById(Integer.valueOf(custId));
		return findAllCustomer();
	}

	@Override
	public CustomerBean findCustomerById(String customerId) {
		Customer customer = customerRepo.findById(Integer.valueOf(customerId)).get();
		//return CUSTOMER_IN_MEMORY_DB.get(customerId);
		return mapEntityToBean(customer);
	}
	
	
	private Customer mapBeanToEntity(CustomerBean customerBean) {
		
		Customer customer = new Customer();
		if(customerBean.getId()!=null ) {
			customer.setCustId(Integer.valueOf(customerBean.getId()));
		}
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
	    customerBean.setId(String.valueOf(customer.getCustId()));
		
		return customerBean;
	}

	@Override
	public List<CustomerBean> findAllCustomer(int currPage, int noOfRecPerPage) {
		// TODO Auto-generated method stub
		PageRequest page = PageRequest.of(currPage, noOfRecPerPage);
		//Page<Customer> customerPage = customerRepo.findAll(page);
		//List<Customer> customersList = customerPage.getContent();

		List<Customer> customersList = customerRepo.findAll(Sort.by(Direction.DESC, "email"));
		List<CustomerBean> custBList = new ArrayList<>();
		customersList.forEach(customerEntity -> {
			CustomerBean customerBean = mapEntityToBean(customerEntity);
			custBList.add(customerBean);
		});
		
		return custBList;
	}
	
	

}
