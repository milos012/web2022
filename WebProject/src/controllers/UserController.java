package controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import enums.Role;
import models.User;
import services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

@Path("/users")
public class UserController {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private UserService getUserService() {
		UserService userService = (UserService) ctx.getAttribute("UserService");
		if (userService == null) {
			userService = new UserService(ctx.getRealPath("."));
			ctx.setAttribute("UserService", userService);
		}
		return userService;
	}
	
	@GET
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public User login(@QueryParam("username") String username, @QueryParam("password") String password) {
		User user = getUserService().login(username, password);
		
		if(user != null) {
			request.getSession().setAttribute("user", user);
		}
		
		return user;
	}
	
	@GET
	@Path("/logout")
	public void logout() {
		User u = (User)request.getSession().getAttribute("user");
		if(u != null)
			request.getSession().setAttribute("user", null);
	}
	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User register(User k) {
		User trenutni = (User) request.getSession().getAttribute("user");
		if(trenutni == null) {
			User user = getUserService().register(k);
			
			if(user != null) {
				request.getSession().setAttribute("user", user);
			}
			return user;
		}
		
//		if(trenutni.equals(getUserService().getByUsername(trenutni.getUsername())) && trenutni.getRole() == Role.ADMIN) {
//			k.setRole(Role.SELLER);
//			User user = getUserService().register(k);
//			return user;
//		}
		
		return null;
	}
	
	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	public User activeUser() {
		try {
			User user = (User)request.getSession().getAttribute("user");
			return user;
		} catch(Exception e){
			return null;
		}
	}
	
	@GET
	@Path("/edit")
	@Produces(MediaType.APPLICATION_JSON)
	public User editUser(@QueryParam("password") String password, @QueryParam("firstName") String fName, @QueryParam("lastName") String lName ) {
		User trenutni = (User)request.getSession().getAttribute("user");
		if(trenutni != null && trenutni.equals(getUserService().getByUsername(trenutni.getUsername()))) {
			trenutni.setFirstName(fName);
			trenutni.setLastName(lName);
			trenutni.setPassword(password);
			request.getSession().setAttribute("user", trenutni);
			getUserService().modifyUser(trenutni);
			return trenutni;
		}
		return null;
	}
	
	@GET
	@Path("/delete")
	public void deleteUser(@QueryParam("username") String username ) {
		User trenutni = (User)request.getSession().getAttribute("user");
		if(trenutni != null && trenutni.equals(getUserService().getByUsername(trenutni.getUsername())) && trenutni.getRole() == Role.ADMIN) {
			getUserService().deleteUser(username);
		}
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAll(){
		User trenutni = (User)request.getSession().getAttribute("user");
		if(trenutni != null && trenutni.equals(getUserService().getByUsername(trenutni.getUsername()))) {
			if(trenutni.getRole() == Role.ADMIN) {
				request.getSession().setAttribute("KorisniciIspis", getUserService().getAllUsers());
				return getUserService().getAllUsers();
			}else if(trenutni.getRole() == Role.SELLER) {
				//TODO
				return null;
			}
		}
		
		return null;
	}
	
	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<User> search(Object upit) {
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) upit;
		UserService userService = getUserService();
		String ime = map.get("firstName").trim().toLowerCase();
		String prezime = map.get("lastName").trim().toLowerCase();
		String username = map.get("username").trim();
		Collection<User> result = new ArrayList<User>(userService.getAllUsers());
		if (!username.equals("")) {
			User k = userService.getByUsername(username);
			result.clear();
			if(k != null) {
				result.add(k);
			}
		}
		if (!ime.equals("")) {
			result = userService.getByFirstName(result,ime);
		}
		if (!prezime.equals("")) {
			result = userService.getByLastName(result,prezime);
		}
		request.getSession().setAttribute("KorisniciIspis", result);
		return result;
	}
	
	@POST
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<User> filter(Collection<String> listaUslova) {
		//uslovi{admin prodavac kupac zlatni srebrni bronzani}
		UserService userService = getUserService();
		@SuppressWarnings("unchecked")
		Collection<User> kolekcija = ((Collection<User>) request.getSession().getAttribute("KorisniciIspis"));
		Collection<User> result;
		if(kolekcija == null)	result= new ArrayList<User>(userService.getAllUsers());
		else result= new ArrayList<User>(kolekcija);
		
		//TODO: Filtriranje na osnovu uslova
//		if (listaUslova.isEmpty()) {
//			return result;
//		}
//		result = daokorisnici.filtriraj(result, listaUslova, getTicketService());
		return result;
	}
	
	@GET
	@Path("/sort/{idSorta}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> sortAdmin(@PathParam("idSorta") int idSorta) {
		UserService userService = getUserService();
		@SuppressWarnings("unchecked")
		Collection<User> kolekcija = ((Collection<User>) request.getSession().getAttribute("KorisniciIspis"));
		List<User> result;
		
		if(kolekcija == null)	result= new ArrayList<User>(userService.getAllUsers());
		else result= new ArrayList<User>(kolekcija);
		
		
		switch (idSorta) {
		case 1:
			result = userService.sortByFName(result, false);
			break;
		case 2:
			result = userService.sortByFName(result, true);
			break;
		case 3:
			result = userService.sortByLName(result, false);
			break;
		case 4:
			result = userService.sortByLName(result, true);
			break;
		case 5:
			result = userService.sortByUsername(result, false);
			break;
		case 6:
			result = userService.sortByUsername(result, true);
			break;
		case 7:
			result = userService.sortByPoints(result, false);
			break;
		case 8:
			result = userService.sortByPoints(result, true);
			break;
		default:
			result = new ArrayList<User>();
			break;
		}
		return result;
	}
	
}
