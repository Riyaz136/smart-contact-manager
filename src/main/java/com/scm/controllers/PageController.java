package com.scm.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {

	
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
			
		return "redirect:/home";
	}
	
	
	
	@GetMapping("/home")
	public String home( Model model) {
		model.addAttribute("name" , "riyaz ahmad");
		model.addAttribute("class" , "Mca");
		model.addAttribute("rollno" , "08311804423");
		return "home";
	}
	
	@GetMapping("/about")
	public String about() {
		System.out.println("about page is  rinning ");
		return "about";
	}
	
	@GetMapping("/services")
	public String pageServices() {
		System.out.println("services page is loading ");
		return "services";
	}
	
	@GetMapping("/contact")
	public String contact() {
		return "contact";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/signup")
	public String signup( Model model) {
		 UserForm userForm=new UserForm();
		 
//		  manully fill the  registration form data 
		 
//		 userForm.setName("riyaz");
//		 userForm.setEmail("riayaz@gmail.com");
//		 userForm.setPassword("mypassword");
//		 userForm.setPhoneNumber("857458966");
//		 userForm.setAbout("this is  about me any thing else you wanted to know about me  if yes please let me know");
		 
		 
		 model.addAttribute("userForm" , userForm);
		
		
		return "register";
	}
	
	
	
	@PostMapping("/do-register")
	public String processRegister(@Valid   @ModelAttribute UserForm userform , BindingResult rbinBindingResult , HttpSession session)  {
		
		// fecting data of  registration form  using userform object all the data coming in usserform object 
		System.out.println(userform);
		
		if(rbinBindingResult.hasErrors()) {
			return "register";
		}
		
//		   User user= User.builder()
//				   .name(userform.getName())
//				   .email(userform.getEmail())
//				   .password(userform.getPassword())
//				   .about(userform.getAbout())
//				   .phoneNumber(userform.getPhoneNumber())
//				   .profilePic("").build();
		
		User user=new User();
		user.setName(userform.getName());
		user.setEmail(userform.getEmail());
		user.setPassword(userform.getPassword());
		user.setAbout(userform.getAbout());
		user.setPhoneNumber(userform.getPhoneNumber());
		user.setEnabled(false); 
		user.setProfilePic("");	
		    		
		    			 
		userService.saveUser(user);
		
		
	Message message=Message.builder().content("registration successful").type(MessageType.blue).build();	
		
		session.setAttribute( "message", message);   
		
		
		
		return "redirect:/signup";
	}

	
	
	
	
}
