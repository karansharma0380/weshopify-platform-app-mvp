package com.weshopifyplatform.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.weshopifyplatform.app.beans.CustomerBean;
import com.weshopifyplatform.app.service.CustomerManagementService;

@Controller
public class CustomersController {
	
	@Autowired
	private CustomerManagementService customerManagementService;
	
	@RequestMapping(value= {"/addCustomerPage"})
	public String viewAddustomerPage(Model model) {
		model.addAttribute("customerBean", new CustomerBean());
		return "add-customers.html";
	}
	
	@RequestMapping(value= {"/addCustomer"})
	public String viewAddustomerPage(@ModelAttribute("customerBean") CustomerBean customerBean, Model model) {
		System.out.println(customerBean.toString());
		customerBean = customerManagementService.registerCustomer(customerBean);
		boolean isCustomerRegistered = false;
		if(StringUtils.hasText(customerBean.getId())) {
			List<CustomerBean> customerList = customerManagementService.findAllCustomer();
			model.addAttribute("listOfCustomers", customerList);
			isCustomerRegistered=true;
			model.addAttribute("isCustomerRegistered", isCustomerRegistered);
		}else {
			model.addAttribute("isCustomerRegistered", isCustomerRegistered);
		}
		return "list-customers.html";
	}
	
	@RequestMapping(value = {"/viewCustomersPage"})
	public String viewCustomers(Model model) {
		boolean customersFound = false;
		List<CustomerBean> customerList = customerManagementService.findAllCustomer();
		//Now to send this data to front-end we need Model obj
		model.addAttribute("listOfCustomers", customerList);
		if(!CollectionUtils.isEmpty(customerList)) {
			model.addAttribute("listOfCustomers", customerList);
			customersFound=true;
		}
		model.addAttribute("customersFound",customersFound);
	
		return "list-customers.html";
	}
	
	@RequestMapping(value = {"/deleteCustomer"})
	public String deleteCustomer(@RequestParam("custId") String customerId, Model model){
		System.out.println("Customer Id: "+customerId);
		List<CustomerBean> customerList =customerManagementService.deleteCustomerById(customerId);
		boolean customersFound=false;
		model.addAttribute("listOfCustomers", customerList);
		if(!CollectionUtils.isEmpty(customerList)) {
			model.addAttribute("listOfCustomers", customerList);
			customersFound=true;
		}
		model.addAttribute("customersFound",customersFound);
		return "list-customers.html";
	}
	
	@RequestMapping(value= {"/editCustomerPage"})
	public String editCustomerPage(@RequestParam("custId") String customerId, Model model) {
		CustomerBean customerBean = customerManagementService.findCustomerById(customerId);
		model.addAttribute("customerBean", customerBean);
		return "add-customers.html";
	}
	
	@RequestMapping(value= {"/view-customers"})
	public String viewAllPagingCustomers(
		    @RequestParam("pageNum") int currPage,
		    @RequestParam("noOfRecPerPage") int noOfRecPerPage,
		    Model model
		) {
		
		noOfRecPerPage=2;
			boolean customersFound = false;
			if(currPage!=0) {
				currPage = currPage-1;
			}
		    List<CustomerBean> customerList = customerManagementService.findAllCustomer(currPage, noOfRecPerPage);
		    
		    if(!CollectionUtils.isEmpty(customerList)) {
		    	model.addAttribute("listOfCustomers", customerList);
		    	customersFound=true;
		    }
		    
		    model.addAttribute("customersFound", customersFound);

		    List<CustomerBean> totalCustomersList = customerManagementService.findAllCustomer();
		    int totalCustomersCount = totalCustomersList.size();
		    int totalNoOfPages = (int) Math.ceil((double) totalCustomersCount / noOfRecPerPage);

		    model.addAttribute("totalRecInDB", totalCustomersCount);
		    model.addAttribute("totalPages", totalNoOfPages);
		    return "list-customers.html";
		}

	
}
