package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.implementation.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

	// hard core coding yeh bas user jo hum create karenge wahi login kar skte hai
	// but hume aisa karna hai
	// ki jo bhi signup kre jiski information database mai ho vo login kr le isliye
	// ye sara code comment kiya hai

//	@Bean
//	public UserDetailsService userDetailsService() { // this class is creating for login configuration
//
//		UserDetails user1 = User.withDefaultPasswordEncoder()
//				.username("riyaz123") // we can create multiple user
//				.password("riyaz123")
//				.roles("ADMIN", "USER").build();
//		
//
//		UserDetails user2 = User.withDefaultPasswordEncoder()
//				.username("admin") // we can create multiple user
//				.password("admin")
//				.build();	
//				
//
//		var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1, user2); // we can pass multiple user here
//
//		return inMemoryUserDetailsManager;
//
//	}

	 @Autowired
	private OAuthAuthenticationSuccessHandler handler;
	
	@Autowired
	private SecurityCustomUserDetailService userDetailService;
	
	@Autowired
	private  AuthFailureHandler authFailureHandler;
	

	// configuration of authentication provider

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		// user detail services ka object lana hai
		daoAuthenticationProvider.setUserDetailsService(userDetailService);

		// password encoder ka object lana hai
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

		return daoAuthenticationProvider;

	}

	// yha hum btayenge spring security ko ki kon sa page secure karna hai ya kon sa
	// route secure karna hai
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		// url ko configure kara hai kon se public rahenge kon se authenticated
		httpSecurity.authorizeHttpRequests(authorize -> {

			// authorize.requestMatchers("/home", "/signup" , "/services",
			// "/login").permitAll();
			authorize.requestMatchers("/user/**").authenticated();
			authorize.anyRequest().permitAll();

		});

		// form default login
		// ager kuch change krna hai to yha karenge form login se related
		httpSecurity.formLogin(formLogin -> {

			formLogin.loginPage("/login");
			formLogin.successForwardUrl("/user/profile");
 			formLogin.loginProcessingUrl("/authenticate");		
//			formLogin.failureForwardUrl("/login?error=true");
			formLogin.usernameParameter("email");
			formLogin.passwordParameter("password");

			
			/*
			formLogin.failureHandler(new AuthenticationFailureHandler() {

				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					// TODO Auto-generated method stub
				}
			});
			
			
			
			
			formLogin.successHandler(new AuthenticationSuccessHandler() {
				
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					// TODO Auto-generated method stub
					
				}
			});
			
			*/
			

			  formLogin.failureHandler(authFailureHandler);
				  
				  
			
				
				
			
			
		});
		
		
		 httpSecurity.csrf(AbstractHttpConfigurer::disable); 
		 httpSecurity.logout(logoutForm -> {
	            logoutForm.logoutUrl("/do-logout");
	            logoutForm.logoutSuccessUrl("/login?logout=true");
	        });

		 
		 
		 
       httpSecurity.oauth2Login(oauth->{
    	   oauth.loginPage("/login");
    	   oauth.successHandler(handler);
    	   
       } );
		 
		return httpSecurity.build();

	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}

}
