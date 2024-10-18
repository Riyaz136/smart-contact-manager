package com.scm.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helper.AppConstant;
import com.scm.helper.Helper;
import com.scm.helper.Message;
import com.scm.helper.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

	@Autowired
	private ContactService contactService;

	@Autowired
	private UserService userService;

	@Autowired
	private ImageService imageService;

	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForm contactForm = new ContactForm();

		model.addAttribute("contactForm", contactForm);

		return "user/add_contact";

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
			Authentication authentication, HttpSession session) {

		// validation logic
		// agar kisi field mai error hogi to ye code chalega
		if (result.hasErrors()) {

			session.setAttribute("message",
					Message.builder().content("Please correct the following errors").type(MessageType.red).build());
			return "/user/add_contact";
		}

		String username = Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(username);

		// 2 process the contact picture

		// image process

		// logger.info( "file information{}",
		// contactForm.getContactImage().getOriginalFilename()); //image ka naam dekhne
		// ke liye

		// uplod karne ka code
		Contact contact = new Contact();
		contact.setName(contactForm.getName());
		contact.setFavorite(contactForm.isFavorite());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setUser(user);
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setWebSiteLink(contactForm.getWebsiteLink());

		if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {

			String filename = UUID.randomUUID().toString();
			String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
			contact.setPicture(fileURL);
			contact.setCloudinaryImagePublicId(filename);

		}

		contactService.save(contact);
		System.out.println(contactForm);

		session.setAttribute("message",
				Message.builder().content("contact uploaded successfully").type(MessageType.green).build());
		return "redirect:/user/contacts/add";

	}

	@RequestMapping
	public String viewContact(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
			@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
			Authentication authentication) {

		// here we load our all the contact

		String username = Helper.getEmailOfLoggedInUser(authentication);
		User user = userService.getUserByEmail(username);

		Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
		model.addAttribute("pageContact", pageContact);
		model.addAttribute("pageSize", AppConstant.PAGE_SIZE);

		model.addAttribute("contactSearchForm", new ContactSearchForm());

		return "user/contacts";
	}

	// search handler of contact

	@RequestMapping("/search")
	public String searchHandler(@ModelAttribute ContactSearchForm contactSearchForm,
			@RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
			Authentication authentication) {

		logger.info("field{} , value{}", contactSearchForm.getField(), contactSearchForm.getValue());

		var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

		Page<Contact> pageContact = null;

		if (contactSearchForm.getField().equalsIgnoreCase("name")) {
			pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
					user);

		} else if (contactSearchForm.getField().equalsIgnoreCase("email")) {

			pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
					user);
		} else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
			pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
					direction, user);

		}

		model.addAttribute("pageContact", pageContact);
		model.addAttribute("contactSearchForm", contactSearchForm);
		model.addAttribute("pageSize", AppConstant.PAGE_SIZE);

		return "user/search";
	}

	@RequestMapping("/delete/{contactId}")
	public String deleteContact(@PathVariable("contactId") String contactId, HttpSession session) {

		contactService.delete(contactId);

		session.setAttribute("message",
				Message.builder().content("Contact deleted successfully!!").type(MessageType.green).build());
		return "redirect:/user/contacts";

	}

	// this handler for update view

	@RequestMapping("/view/{contactId}")
	public String updateContactFormView(@PathVariable("contactId") String contactId, Model model) {

		var contact = contactService.getById(contactId);

		ContactForm contactForm = new ContactForm();
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setFavorite(contact.isFavorite());
		contactForm.setWebsiteLink(contact.getWebSiteLink());
		contactForm.setLinkedInLink(contact.getLinkedInLink());
		contactForm.setPicture(contact.getPicture());
		model.addAttribute("contactForm", contactForm);
		model.addAttribute("contactId", contactId);

		return "user/update_contact_view";
	}

	@RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
	public String updateContact(@PathVariable("contactId") String contactId,
			@Valid @ModelAttribute ContactForm contactForm, Model model, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			return "user/update_contact_view";
		}

		var con = contactService.getById(contactId);
		con.setId(contactId);
		con.setName(contactForm.getName());
		con.setEmail(contactForm.getEmail());
		con.setPhoneNumber(contactForm.getPhoneNumber());
		con.setAddress(contactForm.getAddress());
		con.setDescription(contactForm.getDescription());
		con.setFavorite(contactForm.isFavorite());
		con.setWebSiteLink(contactForm.getWebsiteLink());
		con.setLinkedInLink(contactForm.getLinkedInLink());
		// con.setPicture(contactForm.getPicture());
		// image process

		if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {

			logger.info("file is not emply");
			String fileName = UUID.randomUUID().toString();
			String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
			con.setCloudinaryImagePublicId(fileName);
			con.setPicture(imageUrl);
			contactForm.setPicture(imageUrl);

		} else {
			logger.info("file is empty");
		}

		var updateCon = contactService.update(con);

		logger.info("updated contact", updateCon);
		model.addAttribute("message", Message.builder().content("Contact Update").type(MessageType.green).build());

		return "redirect:/user/contacts/view/" + contactId;

	}

}
