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

import models.Post;
import models.User;
import services.PostService;
import services.UserService;

@Path("/posts")
public class PostController {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private final String bojanpath = "D:\\Users\\HpZbook15\\Desktop\\web2022\\WebProject\\WebContent\\"; 
	
	//TODO: srediti putanju da pokazuje na WebContent folder?
	private PostService getPostService() {
		PostService postService = (PostService) ctx.getAttribute("PostService");
		if (postService == null) {
			postService = new PostService(bojanpath);
			ctx.setAttribute("PostService", postService);
		}
		return postService;
	}
	
	//TODO: srediti putanju da pokazuje na WebContent folder?
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
	public Post createPost(Post k) {
		User trenutni = (User) request.getSession().getAttribute("user");
		if(trenutni == null) {
			return null;
		}
		Post novipost = getPostService().addPost(k);
		
		if(novipost != null) {
			getUserService().addPostToUser(trenutni, novipost);
			request.getSession().setAttribute("post", novipost);
		}
			
		return novipost;
	}
	
	@GET
	@Path("/selectedPost")
	@Produces(MediaType.APPLICATION_JSON)
	public Post selectedPost() {
		try {
			Post selected = (Post)request.getSession().getAttribute("selectedPost");
			return selected;
		} catch(Exception e){
			return null;
		}
	}
	


}
