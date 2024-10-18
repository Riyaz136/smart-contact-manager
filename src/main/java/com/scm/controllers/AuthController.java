package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepo userRepo;

	@GetMapping("/verify-email")
	public String verifyEmail(@RequestParam String token, HttpSession session) {
		User user = userRepo.findByEmailToken(token).orElse(null);
		if (user != null) {
			// iska matlb user fetch hua hai user aaya hai

			if (user.getEmailToken().equals(token)) {
				
				System.out.println("emial method verification");

				user.setEmailVerified(true);
				user.setEnabled(true);
				userRepo.save(user);
				

				session.setAttribute("message", Message.builder()
						.content("Email is  verified : login with your account")
						.type(MessageType.green).build());
				return "success_page";
			}
			
			
			session.setAttribute("message", Message.builder()
					.content("Email is not verified : Token is not associated with user")
					.type(MessageType.red).build());

			return "error_page";
		}
		
		session.setAttribute("message", Message.builder()
				.content("Email is not verified : Token is not associated with user")
				.type(MessageType.red).build());

		return "error_page";

	}

}
