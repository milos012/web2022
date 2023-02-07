package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import services.LocalDateSerializer;
import services.LocalDateDeserializer;

import enums.Gender;
import enums.Role;
import enums.UserStatus;

public class User {
	
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private Gender gender;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateOfBirth;
	private Role role;
	private UserStatus accStatus;
	private String profilePicture;	//TODO: mozda treba i komentar?
	private List<Post> posts;
	private List<Picture> pictures;
	private List<FriendRequest> requests;
	private List<User> friends;
	private Boolean deleted;
	
	public User() {
		super();
	}
	
	public User(User u1) {
		this.username = u1.getUsername();
		this.password = u1.getPassword();
		this.email = u1.getEmail();
		this.firstName = u1.getFirstName();
		this.lastName = u1.getLastName();
		this.gender = u1.getGender();
		this.dateOfBirth = u1.getDateOfBirth();
		this.role = u1.getRole();
		this.accStatus = u1.getAccStatus();
		this.profilePicture = u1.getProfilePicture();
		this.posts = new ArrayList<Post>();
		this.pictures = new ArrayList<Picture>();
		this.requests = new ArrayList<FriendRequest>();
		this.friends = new ArrayList<User>();
		this.deleted = u1.getDeleted();
	}

	public User(String username, String password, String email, String firstName, String lastName, Gender gender,
			LocalDate dateOfBirth, Role role, UserStatus accStatus, String profilePicture, List<Post> posts,
			List<Picture> pictures, List<FriendRequest> requests, List<User> friends, Boolean deleted) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.accStatus = accStatus;
		this.profilePicture = profilePicture;
		this.posts = new ArrayList<Post>();
		this.pictures = new ArrayList<Picture>();
		this.requests = new ArrayList<FriendRequest>();
		this.friends = new ArrayList<User>();
		this.deleted = deleted;
	}

	public User(String username, String password, String email, String firstName, String lastName, Gender gender,
			LocalDate dateOfBirth, Role role, UserStatus accStatus, Boolean deleted) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = role;
		this.accStatus = accStatus;
		this.deleted = deleted;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public UserStatus getAccStatus() {
		return accStatus;
	}

	public void setAccStatus(UserStatus accStatus) {
		this.accStatus = accStatus;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public List<FriendRequest> getRequests() {
		return requests;
	}

	public void setRequests(List<FriendRequest> requests) {
		this.requests = requests;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	

	@Override
	public String toString() {
		return  username + "," + password + "," + email + "," + firstName + "," + lastName + "," + gender + "," + dateOfBirth + "," + role + "," + deleted;
	}

	
}
