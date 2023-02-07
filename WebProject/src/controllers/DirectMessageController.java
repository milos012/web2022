package controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import models.DirectMessage;
import services.DirectMessageService;

@Path("/directmessages")
public class DirectMessageController {
	
	@Context
	ServletContext ctx;
	
	@Context
	HttpServletRequest request;
	
	private final String bojanpath = "D:\\Users\\HpZbook15\\Desktop\\web2022\\WebProject\\WebContent\\"; 
	
	private DirectMessageService getDirectMessageService() {
		DirectMessageService dmService = (DirectMessageService) ctx.getAttribute("DirectMessageService");
		if (dmService == null) {
			dmService = new DirectMessageService(bojanpath);
			ctx.setAttribute("DirectMessageService", dmService);
		}
		return dmService;
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DirectMessage createDirectMessage(DirectMessage k) {
//		DirectMessage trenutni = (DirectMessage) request.getSession().getAttribute("dm");
//		if(trenutni == null) {
//			DirectMessage noviDM = getDirectMessageService().createDM(k);
//			
//			if(noviDM != null) {
//				request.getSession().setAttribute("dm", noviDM);
//			}
//			return noviDM;
//		}
//			
//		return null;
		DirectMessage noviDM = getDirectMessageService().createDM(k);
		return noviDM;
		
	}

}
