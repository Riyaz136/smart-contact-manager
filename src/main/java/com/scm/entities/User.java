package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

	@Id
	public String userId;

	@Column(name = "user_name ", nullable = false)
	public String name;

	@Column(nullable = false, unique = true)
	public String email;

	public String password;

	@Column(length = 1000)
	public String about;

	@Column(length = 1000)
	public String profilePic;

	public String phoneNumber;

	// information
	private boolean enabled = false;
	private boolean emailVerified = false;
	private boolean phoneVerified = false;

	// self, google, github, facebook, linkedin

	@Enumerated(value = EnumType.STRING)
	private Providers provider = Providers.SELF;
	private String providerUserId;

	// add more field if needed

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Contact> contacts = new ArrayList<>();
	
	
	private String emailToken;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isPhoneVerified() {
		return phoneVerified;
	}

	public void setPhoneVerified(boolean phoneVerified) {
		this.phoneVerified = phoneVerified;
	}

	public Providers getProvider() {
		return provider;
	}

	public void setProvider(Providers provider) {
		this.provider = provider;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	
	
	public String getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}

	public User(String userId, String name, String email, String password, String about, String profilePic,
			String phoneNumber, boolean enabled, boolean emailVerified, boolean phoneVerified, Providers provider,
			String providerUserId, List<Contact> contacts, String emailToken) {
		super();
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		this.about = about;
		this.profilePic = profilePic;
		this.phoneNumber = phoneNumber;
		this.enabled = enabled;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
		this.provider = provider;
		this.providerUserId = providerUserId;
		this.contacts = contacts;
		this.emailToken = emailToken;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", email=" + email + ", password=" + password + ", about="
				+ about + ", profilePic=" + profilePic + ", phoneNumber=" + phoneNumber + ", enabled=" + enabled
				+ ", emailVerified=" + emailVerified + ", phoneVerified=" + phoneVerified + ", provider=" + provider
				+ ", providerUserId=" + providerUserId + ", contacts=" + contacts + "]";
	}
	
	

	// user builder manully created

	public static class UserBuilder {
		private String userId;
		private String name;
		private String email;
		private String password;
		private String about;
		private String profilePic;
		private String phoneNumber;
		private boolean enabled;
		private boolean emailVerified;
		private boolean phoneVerified;
		private Providers provider;
		private String providerUserId;
		private List<Contact> contacts;
		private String emailToken;

		public UserBuilder() {
		}

		public UserBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}

		public UserBuilder name(String name) {
			this.name = name;
			return this;
		}

		public UserBuilder email(String email) {
			this.email = email;
			return this;
		}

		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}

		public UserBuilder about(String about) {
			this.about = about;
			return this;
		}

		public UserBuilder profilePic(String profilePic) {
			this.profilePic = profilePic;
			return this;
		}

		public UserBuilder phoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
			return this;
		}

		public UserBuilder enabled(boolean enabled) {
			this.enabled = enabled;
			return this;
		}

		public UserBuilder emailVerified(boolean emailVerified) {
			this.emailVerified = emailVerified;
			return this;
		}

		public UserBuilder phoneVerified(boolean phoneVerified) {
			this.phoneVerified = phoneVerified;
			return this;
		}

		public UserBuilder provider(Providers provider) {
			this.provider = provider;
			return this;
		}

		public UserBuilder providerUserId(String providerUserId) {
			this.providerUserId = providerUserId;
			return this;
		}

		public UserBuilder contacts(List<Contact> contacts) {
			this.contacts = contacts;
			return this;
		}
		
		public UserBuilder emailToken(String emailToken) {	
			this.emailToken=emailToken;
			return this;
		}

		public User build() {
			return new User(userId, name, email, password, about, profilePic, phoneNumber, enabled, emailVerified,
					phoneVerified, provider, providerUserId, contacts, emailToken);
		}
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roleList = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// list of roles[USER,ADMIN]
		// Collection of SimpGrantedAuthority[roles{ADMIN,USER}]
		Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		return roles;
	}

	// for this project is email id hee user name hai jo bhi user name hoga usi ko
	// yha likhenge // ye override userdeatails ka method hai

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public boolean IsEnabled() {
		 return this.enabled;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

}
