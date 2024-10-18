package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactForm {

	    @NotBlank(message = "Name is required")
	    private String name;

	    @NotBlank(message = "Email is required")
	    @Email(message = "Invalid Email Address [ example@gmail.com ]")
	    private String email;

	    @NotBlank(message = "Phone Number is required")
	    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
	    private String phoneNumber;

	    @NotBlank(message = "Address is required")
	    private String address;

	    private String description;

	    private boolean favorite;

	    private String websiteLink;

	    private String linkedInLink;

	    // annotation create karenge jo file validate
	    // size
	    // resolution

	   // @ValidFile(message = "Invalid File")
	    private MultipartFile contactImage;

	    private String picture;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public boolean isFavorite() {
			return favorite;
		}

		public void setFavorite(boolean favorite) {
			this.favorite = favorite;
		}

		public String getWebsiteLink() {
			return websiteLink;
		}

		public void setWebsiteLink(String websiteLink) {
			this.websiteLink = websiteLink;
		}

		public String getLinkedInLink() {
			return linkedInLink;
		}

		public void setLinkedInLink(String linkedInLink) {
			this.linkedInLink = linkedInLink;
		}

		public MultipartFile getContactImage() {
			return contactImage;
		}

		public void setContactImage(MultipartFile contactImage) {
			this.contactImage = contactImage;
		}

		public String getPicture() {
			return picture;
		}

		public void setPicture(String picture) {
			this.picture = picture;
		}

		public ContactForm(@NotBlank(message = "Name is required") String name,
				@NotBlank(message = "Email is required") @Email(message = "Invalid Email Address [ example@gmail.com ]") String email,
				@NotBlank(message = "Phone Number is required") @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number") String phoneNumber,
				@NotBlank(message = "Address is required") String address, String description, boolean favorite,
				String websiteLink, String linkedInLink, MultipartFile contactImage, String picture) {
			super();
			this.name = name;
			this.email = email;
			this.phoneNumber = phoneNumber;
			this.address = address;
			this.description = description;
			this.favorite = favorite;
			this.websiteLink = websiteLink;
			this.linkedInLink = linkedInLink;
			this.contactImage = contactImage;
			this.picture = picture;
		}

		public ContactForm() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "ContactForm [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", address="
					+ address + ", description=" + description + ", favorite=" + favorite + ", websiteLink="
					+ websiteLink + ", linkedInLink=" + linkedInLink + ", contactImage=" + contactImage + ", picture="
					+ picture + "]";
		}
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}
