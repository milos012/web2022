package controllers;

import models.Ticket;
import models.User;
import services.TicketService;
import services.UserService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

@Path("/tickets")
public class TicketController {
    @Context
    ServletContext ctx;

    @Context
    HttpServletRequest httpServletRequest;

    private TicketService getTicketService() {
        TicketService ticketService = (TicketService) ctx.getAttribute("TicketService");
        if (ticketService == null) {
            ticketService = new TicketService(ctx.getRealPath("."));
            ctx.setAttribute("TicketService", ticketService);
        }
        return ticketService;
    }

    private UserService getUserService() {
        UserService userService = (UserService) ctx.getAttribute("UserService");
        if (userService == null) {
            userService = new UserService(ctx.getRealPath("."));
            ctx.setAttribute("UserService", userService);
        }
        return userService;
    }
    
    //TODO: Dodati ManifestationService (nisam siguran jos uvek da li treba)

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ticket> getAllTickets() {
        TicketService ticketService = getTicketService();
        List<Ticket> tickets;
        User activeUser = (User) httpServletRequest.getSession().getAttribute("user");
        if (activeUser == null) {
            return null;
        }

        if (activeUser.equals(getUserService().getByUsername(activeUser.getUsername()))) {
            tickets = ticketService.getTickets( activeUser.getRole(), activeUser.getUsername());
        } else {
            return null;
        }

        httpServletRequest.getSession().setAttribute("activeTickets", tickets);
        return tickets;

    }
    
    @POST
	@Path("/filter")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Ticket> filterTickets(Collection<String> listaUslova) {
		TicketService ts = getTicketService();
		@SuppressWarnings("unchecked")
		Collection<Ticket> kolekcija = ((Collection<Ticket>) httpServletRequest.getSession().getAttribute("activeTickets"));
		Collection<Ticket> result;
		User trenutni = (User) httpServletRequest.getSession().getAttribute("user");
		if (!(trenutni.equals(getUserService().getByUsername(trenutni.getUsername())))) {
			return null;
		}

		if (kolekcija == null) {
			switch (trenutni.getRole()) {
			case ADMIN:
				kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.ADMIN, trenutni.getUsername()));
				break;
			case SELLER:
				kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.SELLER, trenutni.getUsername()));
				break;
			case BUYER:
				kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.BUYER, trenutni.getUsername()));
				break;
			default:
				return null;
			}
		}
		if (listaUslova.isEmpty()) {
			return kolekcija;
		}
		//ArrayList<String> uslovi = (ArrayList<String>) listaUslova;
		result = ts.filter(kolekcija, listaUslova);
		return result;
	}
    
    @POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Collection<Ticket> search(Object upit) {
		@SuppressWarnings("unchecked")
		LinkedHashMap<String, String> mapa = (LinkedHashMap<String, String>) upit;
		TicketService ts = getTicketService();
		User trenutni = (User) httpServletRequest.getSession().getAttribute("user");
		if (trenutni == null)
			return null;
		if (!(trenutni.equals(getUserService().getByUsername(trenutni.getUsername())))) {
			return null;
		}
		Collection<Ticket> kolekcija;
		switch (trenutni.getRole()) {
		case ADMIN:
			kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.ADMIN, trenutni.getUsername()));
			break;
		case SELLER:
			kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.SELLER, trenutni.getUsername()));
			break;
		case BUYER:
			kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.BUYER, trenutni.getUsername()));
			break;
		default:
			return null;

		}

		String naziv = mapa.get("naziv").trim().toLowerCase();
		String cenaOd = mapa.get("cenaod").trim().toLowerCase();
		String cenaDo = mapa.get("cenado").trim().toLowerCase();
		String datumOd = mapa.get("datumod").trim().toLowerCase();
		String datumDo = mapa.get("datumdo").trim().toLowerCase();

		if (!naziv.equals("")) {
			kolekcija = ts.searchNaziv(kolekcija, naziv);
			if (kolekcija.isEmpty()) {
				httpServletRequest.getSession().setAttribute("activeTickets", kolekcija);
				return kolekcija;
			}
		}
		if (!cenaOd.equals("")) {
			kolekcija = ts.searchCenaOd(kolekcija, cenaOd);
			if (kolekcija.isEmpty()) {
				httpServletRequest.getSession().setAttribute("activeTickets", kolekcija);
				return kolekcija;
			}
		}
		if (!cenaDo.equals("")) {
			kolekcija = ts.searchCenaDo(kolekcija, cenaDo);
			if (kolekcija.isEmpty()) {
				httpServletRequest.getSession().setAttribute("activeTickets", kolekcija);
				return kolekcija;
			}
		}
		if (!datumOd.equals("")) {
			kolekcija = ts.searchDatumOd(kolekcija, datumOd);
			if (kolekcija.isEmpty()) {
				httpServletRequest.getSession().setAttribute("activeTickets", kolekcija);
				return kolekcija;
			}
		}
		if (!datumDo.equals("")) {
			kolekcija = ts.searchDatumDo(kolekcija, datumDo);
			if (kolekcija.isEmpty()) {
				httpServletRequest.getSession().setAttribute("activeTickets", kolekcija);
				return kolekcija;
			}
		}

		httpServletRequest.getSession().setAttribute("activeTickets", kolekcija);
		return kolekcija;

	}
    
    @GET
	@Path("/sort/{idSorta}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ticket> sortTickets(@PathParam("idSorta") int idSorta) {
    	TicketService ts = getTicketService();
		@SuppressWarnings("unchecked")
		Collection<Ticket> kolekcija = ((Collection<Ticket>) httpServletRequest.getSession().getAttribute("activeTickets"));
		User trenutni = (User) httpServletRequest.getSession().getAttribute("user");
		if (!(trenutni.equals(getUserService().getByUsername(trenutni.getUsername())))) {
			return null;
		}

		if (kolekcija == null) {
			switch (trenutni.getRole()) {
			case ADMIN:
				kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.ADMIN, trenutni.getUsername()));
				break;
			case SELLER:
				kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.SELLER, trenutni.getUsername()));
				break;
			case BUYER:
				kolekcija = new ArrayList<Ticket>(ts.getTickets(Role.BUYER, trenutni.getUsername()));
				break;
			default:
				return null;
			}
		}
		List<Ticket> result = new ArrayList<Ticket>(kolekcija);
		switch (idSorta) {
		case 1:
			result = ts.sortirajPoCeniKarte(result, false);
			break;
		case 2:
			result = ts.sortirajPoCeniKarte(result, true);
			break;
		case 3:
			result = ts.sortirajPoDatumu(result, false);
			break;
		case 4:
			result = ts.sortirajPoDatumu(result, true);
			break;
		case 5:
			result = ts.sortByManifestationName(result, false);

			break;
		case 6:
			result = ts.sortByManifestationName(result, true);
			break;

		default:
			result = new ArrayList<Ticket>();
			break;
		}
		return result;
	}
    
}
