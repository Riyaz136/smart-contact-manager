package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {

	// for save contact
	Contact save(Contact contact);

	// for update contact
	Contact update(Contact contact);

	// get all contact
	List<Contact> getAll();

	// get contact by id
	Contact getById(String id);

	// delete contact by id
	void delete(String id);

	// search contact by name
	Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy , String order, User user);

	Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy , String order, User user);

	Page<Contact> searchByPhoneNumber(String phoneNumberKeyword ,int size, int page, String sortBy , String order, User user);

	// get contact by user id
	List<Contact> getByUserId(String UserId);

	Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection);

}