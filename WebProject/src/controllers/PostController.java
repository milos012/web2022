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
import services.PostService;

@Path("/posts")
public class PostController {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	
	//TODO: srediti putanju da pokazuje na WebContent folder?
	private PostService getPostService() {
		PostService postService = (PostService) ctx.getAttribute("PostService");
		if (postService == null) {
			postService = new PostService(ctx.getRealPath("."));
			ctx.setAttribute("PostService", postService);
		}
		return postService;
	}
	
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Post register(Post k) {
		Post trenutni = (Post) request.getSession().getAttribute("post");
		if(trenutni == null) {
			Post novipost = getPostService().addPost(k);
			
			if(novipost != null) {
				request.getSession().setAttribute("post", novipost);
			}
			return novipost;
		}
			
		return null;
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
