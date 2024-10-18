package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helper.Helper;
import com.scm.services.UserService;

@ControllerAdvice // iska matlb jo bhi method honge iske andar vo har ak requst ke liye execue
					// honge like/profile / dashborad
public class RootController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@ModelAttribute
	public void addLoggedInUserInformation(Model model, Authentication authentication) {

		if(authentication==null) {
			return;
		}
		
		System.out.println(" adding user to  the attribute model");
		String username = Helper.getEmailOfLoggedInUser(authentication);

		logger.info("user loged int with = {}", username);
		User user = userService.getUserByEmail(username);
		
		System.out.println(user);
		System.out.println(user.getEmail());
		System.out.println(user.getName());
		model.addAttribute("loggedInUser", user);
		
	}

}
