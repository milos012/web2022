package controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import enums.Role;
import models.FriendRequest;
import models.Post;
import models.User;
import services.FriendRequestService;
import services.PostService;
import services.UserService;

@Path("/friendrequests")
public class FriendRequestController {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private final String bojanpath = "D:\\Users\\HpZbook15\\Desktop\\web2022\\WebProject\\WebContent\\"; 
	
	
	//TODO: srediti putanju da pokazuje na WebContent folder?
	private FriendRequestService getFriendRequestService() {
		FriendRequestService friendRequestService = (FriendRequestService) ctx.getAttribute("FriendRequestService");
		if (friendRequestService == null) {
			friendRequestService = new FriendRequestService(bojanpath);
			ctx.setAttribute("FriendRequestService", friendRequestService);
		}
		return friendRequestService;
	}
	
	private UserService getUserService() {
		UserService userService = (UserService) ctx.getAttribute("UserService");
		if (userService == null) {
			userService = new UserService(ctx.getRealPath("."));
			ctx.setAttribute("UserService", userService);
		}
		return userService;
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public FriendRequest createFR(FriendRequest k) {
//		FriendRequest trenutni = (FriendRequest) request.getSession().getAttribute("frq");
//		if(trenutni == null) {
//			FriendRequest novifr = getFriendRequestService().addFR(k);
//			
//			if(novifr != null) {
//				request.getSession().setAttribute("frq", novifr);
//			}
//			return novifr;
//		}
		FriendRequest novifr = getFriendRequestService().addFR(k);
		return novifr;
			
//		return null;
	}
	
	
	@POST
	@Path("/accept")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response acceptFR(FriendRequest k) {
		User trenutni = (User)request.getSession().getAttribute("user");
		
		if(trenutni != null && trenutni.equals(getUserService().getByUsername(trenutni.getUsername()))) {
			if (trenutni.getRole() == Role.USERBASIC) {
				return Response.status(Status.OK).entity(getFriendRequestService().acceptFR(k)).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllFriendRequests(){
		User trenutni = (User)request.getSession().getAttribute("user");
		if(trenutni != null && trenutni.equals(getUserService().getByUsername(trenutni.getUsername()))) {
			if (trenutni.getRole() == Role.USERBASIC) {
				return Response.status(Status.OK).entity(getFriendRequestService().getPrimljeniZahteviNaCekanju(trenutni)).build();
			}
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

}
