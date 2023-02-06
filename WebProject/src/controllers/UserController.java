package controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.databind.ObjectMapper;

import dtos.LoginDTO;
import enums.Role;
import models.User;
import services.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("/users")
public class UserController {
	
	@Context
	private ServletContext ctx;
	
	@Context
	private HttpServletRequest request;
	
	//TODO: promeniti putanju?
	private UserService getUserService() {
		UserService userService = (UserService) ctx.getAttribute("UserService");
		if (userService == null) {
			userService = new UserService("D:\\Users\\HpZbook15\\Desktop\\web2022\\WebProject\\WebContent/");
			ctx.setAttribute("UserService", userService);
		}
		return userService;
	}
	
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login() throws IOException {
		LoginDTO login = null;
		try {
			ServletInputStream reader = request.getInputStream();
			byte[] all = reader.readAllBytes();
			String str = new String(all, StandardCharsets.UTF_8);

			ObjectMapper mapper = new ObjectMapper();
			login = mapper.readValue(str, LoginDTO.class);
	    } catch (Exception e) { 
	    	return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
	    }

		User user = getUserService().login(login.getUsername(), login.getPassword());
		
		if(user != null) {
			request.getSession().setAttribute("user", user);
			return Response.status(Status.OK).entity(user).build();
		}
		
		return Response.status(Status.BAD_REQUEST).entity("user not found!").build();
	}
	
	@GET
	@Path("/logout")
	public Response logout() {
		User u = (User)request.getSession().getAttribute("user");
		if(u != null) {
			request.getSession().setAttribute("user", null);
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
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
	public Response activeUser() {
		try {
			User user = (User)request.getSession().getAttribute("user");
			return Response.status(Status.OK).entity(user).build();
		} catch(Exception e){
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
			
		}
	}
	
	// TODO dodati i promenu profilne slike
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
	
	@PUT
	@Path("/block/{username}")
	public Response blockUser(@PathParam("username") String username ) {
		User trenutni = (User)request.getSession().getAttribute("user");
		if(trenutni != null && trenutni.equals(getUserService().getByUsername(trenutni.getUsername()))) {
			if (trenutni.getRole() == Role.ADMIN) {
				return Response.status(Status.OK).entity(getUserService().changeBlockStatus(username)).build();
			} else {
				return Response.status(Status.UNAUTHORIZED).build();
			}
			
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll(){
		User trenutni = (User)request.getSession().getAttribute("user");
		if(trenutni != null && trenutni.equals(getUserService().getByUsername(trenutni.getUsername()))) {
			if(trenutni.getRole() == Role.ADMIN) {
//				request.getSession().setAttribute("KorisniciIspis", getUserService().getAllUsers());
				return Response.status(Status.OK).entity(getUserService().getAllUsers()).build();
			}else if (trenutni.getRole() == Role.USERBASIC) {
				//TODO
				return Response.status(Status.UNAUTHORIZED).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/adminAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllForAdmin(){
		User trenutni = (User)request.getSession().getAttribute("user");
		if(trenutni != null && trenutni.equals(getUserService().getByUsername(trenutni.getUsername()))) {
			if(trenutni.getRole() == Role.ADMIN) {
//				request.getSession().setAttribute("KorisniciIspis", getUserService().getAllUsers());
				
				return Response.status(Status.OK).entity(getUserService().getAllUsersFromFiles()).build();
			}else if(trenutni.getRole() == Role.USERBASIC) {
				//TODO
				return Response.status(Status.UNAUTHORIZED).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	// TODO proveri = nije menjano naknadno
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
		default:
			result = new ArrayList<User>();
			break;
		}
		return result;
	}
	
}
