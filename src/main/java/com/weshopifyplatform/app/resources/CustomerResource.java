package com.weshopifyplatform.app.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.weshopifyplatform.app.beans.CustomerBean;

import com.weshopifyplatform.app.repos.CustomerRepository;
import com.weshopifyplatform.app.service.CustomerManagementService;

@RestController
@RequestMapping(path = "/api") //root resource 
public class CustomerResource {
	
	@Autowired
	private CustomerManagementService customerManagementService;
	
	@GetMapping(value = {"/customers"}) //sub resource 
	public @ResponseBody List<CustomerBean> findAllCustomer(){
		return customerManagementService.findAllCustomer();
	}
	
	
	@GetMapping(value="/customers/{currPage}/{noOfRecPage}") //subresource can have data dynamically using dynamic variables
	public @ResponseBody List<CustomerBean> findAllCustomerByPaging(@PathVariable("currPage") int currPage, 
			@PathVariable("noOfRecPage") int noOfRecPage){
		if(currPage!=0) {
			currPage=currPage-1;
		}
		return customerManagementService.findAllCustomer(currPage, noOfRecPage);
	}
	
	@GetMapping(value = {"/customers/{customerId}"})
	public @ResponseBody CustomerBean findCustomerById(@PathVariable("customerId") int customerId){
		return customerManagementService.findCustomerById(String.valueOf(customerId));
	}
	
	@DeleteMapping(value={"/customers/{customerId}"})
	public @ResponseBody List<CustomerBean> deleteCustomerById(@PathVariable("customerId") int customerId){
		return customerManagementService.deleteCustomerById(String.valueOf(customerId));
	}
	
	
	@DeleteMapping(path = {"/customers"})
	public @ResponseBody CustomerBean createCustomer(@RequestBody CustomerBean customerBean ) {
		customerBean = customerManagementService.registerCustomer(customerBean);
		return customerBean;
		
	}
}
