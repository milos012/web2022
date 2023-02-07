package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;

import enums.Role;
import models.FriendRequest;
import models.Post;
import models.User;

public class UserService {
	private ArrayList<User> users;
	private String path;
	private ArrayList<User> allUsers;
	private Comparator<User> sorterFName, sorterLName, sorterUsername;
	
	public Collection<User> getAllUsers() {
		return users;
	}
	
	
	public UserService() {
		super();
	}
	
	public UserService(String path) {
		users = new ArrayList<User>();
		allUsers = new ArrayList<User>();
		initSorters();
		loadUsers(path);
	}

	private void loadUsers(String filePath){
		ObjectMapper mapper = new ObjectMapper();
		path = filePath + File.separator;

		List<User> admins = null;
		List<User> basicusers = null;
		
		try {
			admins = Arrays.asList(mapper.readValue(Paths.get(path + "admins.json").toFile(), User[].class));
			System.out.println("Ucitavanje admina uspesno===");
			System.out.println("iz +" + path);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
				e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		
		for (User u : admins) {
			if (!u.getDeleted()) {
				users.add(u);
			}
			allUsers.add(u);
		}
		
		try {
			basicusers = Arrays.asList(mapper.readValue(Paths.get(path + "users.json").toFile(), User[].class));
			System.out.println("Ucitavanje korisnika uspesno===");
			System.out.println("iz +" + path);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
				e.printStackTrace();
		} catch (IOException e) {
		e.printStackTrace();
		}
		
		for (User u : basicusers) {
			if (!u.getDeleted()) {
				users.add(u);
			}
			allUsers.add(u);
		}
	}
	
	public User getByUsername(String username) {
		for (User u : users) {
			if (Objects.equals(u.getUsername(), username)){
				return u;
			}
		}
		return null;
	}
	
	public Collection<User> getByFirstName(Collection<User> users, String name) {
		ArrayList<User> filteredList = new ArrayList<User>();
		for (User u : users) {
			if (u.getFirstName().toLowerCase().contains(name)) {
				filteredList.add(u);
			}
		}
		return filteredList;
	}

	public Collection<User> getByLastName(Collection<User> users, String prezime) {
		ArrayList<User> filteredList = new ArrayList<User>();
		for (User u : users) {
			if (u.getLastName().toLowerCase().contains(prezime)) {
				filteredList.add(u);
			}
		}
		return filteredList;
	}
	
	private ArrayList<User> getAllAdmins() {
		ArrayList<User> admins = new ArrayList<User>();
		for (User u : users) {
			if (u.getRole() == Role.ADMIN) {
				admins.add(u);
			}
		}
		return admins;
	}
	
	public ArrayList<User> getAllBasicUsers() {
		ArrayList<User> buyers = new ArrayList<User>();
		for (User u : users) {
			if (u.getRole() == Role.USERBASIC) {
				buyers.add(u);
			}
		}
		return buyers;
	}
	
	public ArrayList<User> getFriends(User drugar) {
		ArrayList<User> friends = new ArrayList<User>();
		for (User u : users) {
			if (u.getUsername().equals(drugar.getUsername())) {
				friends = (ArrayList<User>) u.getFriends();
				return friends;
			}
		}
		return friends;
	}
	
	public ArrayList<User> getAllUsersFromFiles() {
		ArrayList<User> all = new ArrayList<User>();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			List<User> basicusers = Arrays.asList(mapper.readValue(Paths.get(path + "users.json").toFile(), User[].class));
			List<User> admins = Arrays.asList(mapper.readValue(Paths.get(path + "admins.json").toFile(), User[].class));
			all.addAll(basicusers);
			all.addAll(admins);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return null;
		}
		
		return all;
	}
	
	public void deleteUser(String username) {
		User u = getByUsername(username);
		u.setDeleted(true);
		ObjectMapper mapper = new ObjectMapper();
		if (u.getRole() == Role.USERBASIC) {
			try {
				mapper.writeValue(Paths.get(path + "users.json").toFile(), getAllBasicUsers());
			} catch (IOException e) {
				System.out.println("Error! Writing to file was unsuccessful.");
			}
		}
	}
	
	public void modifyUser(User u) {
		
		for (User user : users) {
			if (user.getUsername().equals(u.getUsername())) {
				user.setFirstName(u.getFirstName());
				user.setLastName(u.getLastName());
				user.setPassword(u.getPassword());
				user.setGender(u.getGender());
				user.setAccStatus(u.getAccStatus());
				user.setProfilePicture(u.getProfilePicture());
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		if (u.getRole() == Role.USERBASIC) {
			try {
				mapper.writeValue(Paths.get(path + "users.json").toFile(), getAllBasicUsers());
				System.out.println("Korisnik uspesno izmenjen.");
			} catch (IOException e) {
				System.out.println("Error! Writing to file was unsuccessful.");
			}
		}  else {
			try {
				mapper.writeValue(Paths.get(path + "admins.json").toFile(), getAllAdmins());
			} catch (IOException e) {
				System.out.println("Error! Writing to file was unsuccessful.");
			}
		}
	}
	
	public void addPostToUser(User u, Post np) {
		for (User user : users) {
			if (user.getUsername().equals(u.getUsername())) {
				user.getPosts().add(np);
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		if (u.getRole() == Role.USERBASIC) {
			try {
				mapper.writeValue(Paths.get(path + "users.json").toFile(), getAllBasicUsers());
			} catch (IOException e) {
				System.out.println("Error! Writing to file was unsuccessful.");
			}
		}  else {
			try {
				mapper.writeValue(Paths.get(path + "admins.json").toFile(), getAllAdmins());
			} catch (IOException e) {
				System.out.println("Error! Writing to file was unsuccessful.");
			}
		}
	}
	
	public void addUser(User u) {
		if (getByUsername(u.getUsername()) != null) {
			System.out.println("User already exists");
			return;
		}

		if (u.getRole() == Role.USERBASIC) {
			User basicuser = new User(u.getUsername(), u.getPassword(), u.getEmail(), u.getFirstName(), u.getLastName(), u.getGender(),
					u.getDateOfBirth(), u.getRole(), u.getAccStatus(), u.getProfilePicture(), u.getPosts(), u.getPictures(),
					u.getRequests(), u.getFriends(), u.getDeleted());
			
			users.add(basicuser);
			allUsers.add(basicuser);
			ObjectMapper mapper = new ObjectMapper();
			try {
				mapper.writeValue(Paths.get(path + "users.json").toFile(), getAllBasicUsers());
				System.out.println("user upisan u:" + path);
			} catch (IOException e) {
				System.out.println("Error while writing!");
			}
		}
	}
	
	public User login(String username, String password) {
		for (User u : users) {
			if (u.getUsername().equals(username) && u.getPassword().equals(password) && !u.getDeleted()){
				return u;
			}
		}
		return null;
	}
	
	
	public User register(User u) {
		for (User usr : users) {
			if (usr.getUsername() == u.getUsername()){
				return null;
			}
		}
		
		if (u.getRole() == Role.USERBASIC) {
//			User basicUser = new User(u.getUsername(), u.getPassword(), u.getEmail(), u.getFirstName(), u.getLastName(), u.getGender(),
//					u.getDateOfBirth(), u.getRole(), u.getAccStatus(), u.getProfilePicture(), u.getPosts(), u.getPictures(),
//					u.getRequests(), u.getFriends(), u.getDeleted());
			
			User basicUser = new User(u);

			users.add(basicUser);
			allUsers.add(basicUser);
			ObjectMapper mapper = new ObjectMapper();
			try {
				mapper.writeValue(Paths.get(path + "users.json").toFile(), getAllBasicUsers());
				System.out.println("Korisnik je uspesno kreiran");
				System.out.println("na putanji: + " + path);
			} catch (IOException e) {
				System.out.println("Error while writing!");
				return null;
			}
			return basicUser;
		}
		return null;
	}
	
	public List<User> sortByFName(List<User> result, boolean opadajuce) {

		Collections.sort(result, sorterFName);
		if (opadajuce) {
			Collections.reverse(result);
		}
		return result;
	}

	public List<User> sortByLName(List<User> result, boolean opadajuce) {
		Collections.sort(result, sorterLName);
		if (opadajuce) {
			Collections.reverse(result);
		}
		return result;
	}
	
	public List<User> sortByUsername(List<User> result, boolean opadajuce) {
		Collections.sort(result, sorterUsername);
		if (opadajuce) {
			Collections.reverse(result);
		}
		return result;
	}
	
	
	private void initSorters() {

		sorterFName = new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o1.getFirstName().toLowerCase().compareTo(o2.getFirstName().toLowerCase());
			}

		};

		sorterLName = new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o1.getLastName().toLowerCase().compareTo(o2.getLastName().toLowerCase());
			}

		};

		sorterUsername = new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
				return o1.getUsername().toLowerCase().compareTo(o2.getUsername().toLowerCase());
			}

		};
	}


	public boolean changeBlockStatus(String username) {
		boolean changed = false;
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		
		List<User> users = null;
		try {
			users = Arrays.asList(mapper.readValue(Paths.get(path + "admins.json").toFile(), User[].class));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		for (User u: users) {
			if (Objects.equals(u.getUsername(), username)) {
				u.setDeleted(!u.getDeleted());
				changed = true;
			}
		}
		
		if (changed) {
			try {
				writer.writeValue(Paths.get(path + "admins.json").toFile(), users);
				loadUsers(path);
				return true;
			} catch (IOException e) {
				System.out.println("Error while writing!");
				return false;
			}
		}
		
		try {
			users = Arrays.asList(mapper.readValue(Paths.get(path + "users.json").toFile(), User[].class));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for (User u: users) {
			if (Objects.equals(u.getUsername(), username)) {
				u.setDeleted(!u.getDeleted());
				changed = true;
			}
		}
		
		if (changed) {
			try {
				writer.writeValue(Paths.get(path + "users.json").toFile(), users);
				loadUsers(path);
				return true;
			} catch (IOException e) {
				System.out.println("Error while writing!");
				return false;
			}
		}
		
		return false;
	}
	
	public List<Post> getAllPostsForUser(String username) {
		User u = getByUsername(username);
		return u.getPosts();
	}


	public List<Post> deleteUsersPost(String username, String tekst) {
		// TODO Auto-generated method stub
		List<Post> posts = getAllPostsForUser(username);
		for (Post p : posts) {
			if (p.getTekst().equals(tekst)) {
				p.setDeleted(true);
			}
		}
		
		
		return posts;
	}
	
	
	
}
