package controllers;

import models.Manifestation;
import models.User;
import services.ManifestationService;
import services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import enums.Role;

import java.util.List;

@Path("/manifestations")
public class ManifestationController {

    @Context
    ServletContext ctx;

    @Context
    HttpServletRequest httpServletRequest;

    private ManifestationService getManifestationService() {
        ManifestationService manifestationService = (ManifestationService) ctx.getAttribute("ManifestationService");
        if (manifestationService == null) {
            manifestationService = new ManifestationService(ctx.getRealPath("."));
            ctx.setAttribute("ManifestationService", manifestationService);
        }
        return manifestationService;
    }
    
    private UserService getUserService() {
        UserService userService = (UserService) ctx.getAttribute("UserService");
        if (userService == null) {
            userService = new UserService(ctx.getRealPath("."));
            ctx.setAttribute("UserService", userService);
        }
        return userService;
    }

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Manifestation> getAllManifestations() {
        ManifestationService manifestationService = getManifestationService();
        List<Manifestation> manifestations = manifestationService.getManifestations();
        httpServletRequest.getSession().setAttribute("manifestations", manifestations);
        return manifestations;
    }
    
    @POST
	@Path("/addManifestation")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addManifestation(Manifestation manifestation) {
    	 User activeUser = (User) httpServletRequest.getSession().getAttribute("user");
         if (activeUser == null) {
             return false;
         }

         if (activeUser.equals(getUserService().getByUsername(activeUser.getUsername())) && activeUser.getRole() == Role.SELLER) {
				return getManifestationService().addManifestation(manifestation);
		}
		return false;
	}

}
