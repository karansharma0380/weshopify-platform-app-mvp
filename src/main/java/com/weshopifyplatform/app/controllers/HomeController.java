package com.weshopifyplatform.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weshopifyplatform.app.beans.AuthenticationBean;
import com.weshopifyplatform.app.beans.CustomerBean;
import com.weshopifyplatform.app.service.CustomerManagementServiceImpl;

@Controller
public class HomeController {
	
	@Autowired
	private CustomerManagementServiceImpl customerService;

	@RequestMapping(value={"/","/home","/loginPage"})
	public String viewHomePage(Model model){
		model.addAttribute("authnBean", new AuthenticationBean());
		//Whenever submit /,/home,/login on our application we send the home.html with an empty authnBean. 
		//And when we fill the form the field data of form binds to the properties of the bean.
		return "home.html";
	}
	
	@RequestMapping(value= {"/signupPage"})
	public String viewSignupPage(Model model) {
		model.addAttribute("customerBean", new CustomerBean());//This will tell the thymeleaf that we want to bind the data of this form
	// to the properties of this bean. And we are sending the empty CustomerBean object along with the sign-up.html, 
	// we are using addAttribute(String name, Object obj) method of model. Now go to the sign-up.html and inside the form tag add the attributes of thymeleaf
		//Model is used to exchange information from the controller to the UI(View Layer pages)
		return "sign-up.html";
	}
	
	@RequestMapping(value= {"/signup"})
	public String handleSignupData(@ModelAttribute("customerBean") CustomerBean customerBean,Model model) {
		// customerBean - Name of the bean which we sent with the sign-up.html
		//				  See sign-up.html it is the name to which we are binding the fields of the form to.
		//	And we are extracting the bean with same name customerBean
		System.out.println(customerBean.toString()); //To see the object info
		
		customerBean = customerService.registerCustomer(customerBean);//we registered the new customer
		boolean isRegistered=false;
		if(customerBean!=null && customerBean.getId()!=null) {
			isRegistered=true;
		}
		model.addAttribute("isRegistered", isRegistered);
		
		//Tried 1
		//After getting the information from user we are again sending the sign-up.html page with a message.
		//Since we are again exchanging information from the UI/view layer page we will use Model obj model
		//Go to the sign-up.html page now to incorporate this message
		//String message  = "Customer Registered!!! Please Login Here";
		//model.addAttribute("message", message);
		
		
		//Tried 2
		//String message = null;
		//if(customerBean!=null && customerBean.getId()!=null) {
		//	message = "Customer Created Successfully !! Please Login now.";
		//}
		//else {
		//	message = "Customer Registration Failed !! Please try with Proper Data";
		//}
		//model.addAttribute("message", message);
		return "sign-up.html";
	}
	
	@RequestMapping(value= {"/login"})//This controller handling the submit after login/home.html
	//We are returing the dashboard which has list of customers too just like in "Customers/add customers"
	// When we add info of the customer it shows the list of customer.
	// We want to achieve it on our dshboard as well.
	public String doLogin(@ModelAttribute("authnBean") AuthenticationBean authnBean, Model model) {
		System.out.println(authnBean.toString());
		authnBean = customerService.authenticateBean(authnBean);
		
		if(authnBean.isAuthenticated()) {
			boolean customersFound = false;
			List<CustomerBean> customerList = customerService.findAllCustomer();
			//Now to send this data to front-end we need Model obj
			model.addAttribute("listOfCustomers", customerList);
			if(!CollectionUtils.isEmpty(customerList)) {
				model.addAttribute("listOfCustomers", customerList);
				customersFound=true;
			}
			model.addAttribute("customersFound",customersFound);
		
			return "dashboard.html";
		}
		else {
			String message = "Authentication Failed!! Kindly enter the correct User Name and password";
			model.addAttribute("message", message);
			//model.addAttribute("authnBean", new AuthenticationBean());
			return "home.html";
		}
	}
}
