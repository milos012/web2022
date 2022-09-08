package controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import enums.Role;
import models.Buyer;
import models.Comment;
import models.User;
import services.CommentService;
import services.UserService;

@Path("/comments")
public class CommentController {
	
	@Context
	ServletContext ctx;
	@Context
	HttpServletRequest request;
	
	private CommentService getCommentService() {
		CommentService commentService = (CommentService) ctx.getAttribute("CommentService");	
		if (commentService == null) {
			commentService = new CommentService(ctx.getRealPath("."));
			ctx.setAttribute("CommentService", commentService);
		}
		return commentService;
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
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	public Boolean addComment(Comment kom) {
		User trenutni = (User) request.getSession().getAttribute("user");
		if(trenutni == null)
			return false;
		
		if(trenutni.getRole() == Role.BUYER) {
			Buyer k = (Buyer) trenutni;
			kom.setBuyer(k);
			getCommentService().addComment(kom);
			return true;
		}
		
		return false;
	}

}
