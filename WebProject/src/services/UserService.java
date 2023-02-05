package services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.*;

import enums.Role;
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
			System.out.println(path);
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
			if (u.getUsername() == username){
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
	
	private ArrayList<User> getAllBasicUsers() {
		ArrayList<User> buyers = new ArrayList<User>();
		for (User u : users) {
			if (u.getRole() == Role.USERBASIC) {
				buyers.add(u);
			}
		}
		return buyers;
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
	
	//TODO - prosiri da se menjaju i ostale stavke + proveri da li radi ovako
	public void modifyUser(User u) {
		User modified = getByUsername(u.getUsername());
		modified.setFirstName(u.getFirstName());
		modified.setLastName(u.getLastName());
		modified.setPassword(u.getPassword());
		// promeni profilnu sliku
		ObjectMapper mapper = new ObjectMapper();
		if (modified.getRole() == Role.USERBASIC) {
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

//		else if (u.getRole() == Role.SELLER) {
//			Seller seller = new Seller(u.getUsername(), u.getPassword(), u.getFirstName(), u.getLastName(), u.getGender(),
//					u.getDateOfBirth(), u.getRole(), u.getDeleted(), new ArrayList<>(), new ArrayList<>());
//			
//			users.add(seller);
//			allUsers.add(seller);;
//			ObjectMapper mapper = new ObjectMapper();
//			try {
//				mapper.writeValue(Paths.get(path + "sellers.json").toFile(), getAllSellers());
//			} catch (IOException e) {
//				System.out.println("Error while writing!");
//			}
//		}
	}
	
	public User login(String username, String password) {
		for (User u : users) {
			if (u.getUsername().equals(username)){
				if(u.getPassword().equals(password) && u.getDeleted() == false) {
					return u;
				}
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
			
			User basicUser = new User(u.getUsername(), u.getPassword(), u.getEmail(), u.getFirstName(), u.getLastName(), u.getGender(),
					u.getDateOfBirth(), u.getRole(), u.getAccStatus(), u.getDeleted());

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
	

	
//	public void cancelTicket(Ticket k, User trenutni) {
//		Buyer kupac = (Buyer) trenutni;
//		double bodovi = (double) Math.round(k.getPrice() / 1000 * 133 * 4);
//		kupac.setPoints(kupac.getPoints() - bodovi);
//		if (kupac.getUserType() == UserTypeName.GOLD && kupac.getPoints() < 1) {
//			kupac.setPoints(5500);
//			kupac.setUserType(UserTypeName.SILVER);
//
//		}
//		if (kupac.getUserType() == UserTypeName.SILVER && kupac.getPoints() < 1) {
//			kupac.setPoints(1500);
//			kupac.setUserType(UserTypeName.BRONZE);;
//
//		}
//		if (kupac.getUserType() == UserTypeName.BRONZE && kupac.getPoints() < 1) {
//			kupac.setPoints(0);
//		}
//
//		modifyUser(kupac);
//	}
	
}
