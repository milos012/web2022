package controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import models.FriendRequest;
import models.Post;
import services.FriendRequestService;
import services.PostService;

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

}
