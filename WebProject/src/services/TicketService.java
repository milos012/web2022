package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.Role;
import enums.TicketStatus;
import enums.TicketType;
import models.Ticket;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TicketService {
    private ArrayList<Ticket> tickets;
    private String path;
    private static long lastID = 0;
    private Comparator<Ticket> sorterPrice,sorterDate;
    private Comparator<SimpleEntry<Ticket, String>>sorterName;
    private HashMap<String, ArrayList<String>> canceledTickets;

    public TicketService(String path) {
        tickets = new ArrayList<>();
        loadTickets(path);
        initSorters();
    }

    public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private void loadTickets(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        //String data = filePath + File.separator + "data" + File.separator;
        String data = filePath + File.separator;

        path = data;
        try {
            List<Ticket> ticketList = Arrays.asList(mapper.readValue(Paths.get(data + "tickets.json").toFile(), Ticket[].class));
            System.out.println("Ucitavanje karata uspesno===");

            for (Ticket t : ticketList) {
                tickets.add(t);
            }

        } catch (IOException e) {
            System.out.println("Error while loading tickets!");
        }
    }

    public List<Ticket> getTickets(Role role, String username){
        ArrayList<Ticket> ticketArrayList = new ArrayList<Ticket>();

        if(role == Role.ADMIN )
        {
            return tickets;
        }

        else if(role == Role.SELLER)
        {
            for(Ticket t : tickets)
            {
                if(t.getTicketStatus() == TicketStatus.RESERVED)
                {
                    ticketArrayList.add(t);
                }
            }

        }
        else {
            for(Ticket t : tickets)
            {
                if(t.getBuyer().getUsername().equals(username))
                {
                    ticketArrayList.add(t);
                }
            }
        }

        return ticketArrayList;

    }
    
    public Ticket getByID(String id) {
		for (Ticket t : tickets) {
			if (t.getId() == id){
				return t;
			}
		}
		return null;
	}
    
    public ArrayList<String> generateID(int numOfIds) {
		ArrayList<String> result = new ArrayList<String>();
		for(int i=0;i<numOfIds;i++)
		{
			String sifra;
			while(true)
			{
				long id = System.currentTimeMillis() % 10000000000L;
				sifra = String.valueOf(id);
				if((getByID(sifra) != null) && id != lastID) {
					lastID = id;
					break;
				}
			}
			result.add(sifra);
			System.out.println(sifra);
		}

		return result;

	}
    
    
    public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void DodajNoveKarte(ArrayList<Ticket> tics)
	{
		for(Ticket k : tics)
		{
			tickets.add(k);
			
		}
		
		upisiKarte();
	}
	
	public void upisiKarte() {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(path + "tickets.json").toFile(), getTickets());
		} catch (IOException e) {
			System.out.println("Error while writing tickets!");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadCanceled(String filePath) {
		ObjectMapper mapper = new ObjectMapper();
		//String data = filePath + File.separator + "data" + File.separator;
		String data = filePath + File.separator;
		
		path = data;
		try {
			Map<String, Object> tempMap = mapper.readValue(new File( path +
                    "canceledTickets.json"), new TypeReference<Map<String, Object>>() {
            });

			for(String kor: tempMap.keySet()) {
				canceledTickets.put(kor, (ArrayList<String>) tempMap.get(kor));
			}
			System.out.println("Ucitavanje otkazanih karata uspesno===");

		} catch (IOException e) {
			System.out.println("Error while loading canceled tickets!");
		}
	}
	
	
	// Search & filter
	
	public Collection<Ticket> searchNaziv(Collection<Ticket> kolekcija, String name) {
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		for(Ticket k : kolekcija)
		{
			String res = k.getManifestation().getName();
			if(res.toLowerCase().equals(name))
				result.add(k);
		}
		
		return result;
	}
	
	public Collection<Ticket> searchCenaOd(Collection<Ticket> kolekcija, String priceMin) {
		try {
			double price = Double.parseDouble(priceMin);
			List<Ticket> rez = kolekcija.stream().filter(k -> k.getPrice() > price ).collect(Collectors.toList());
			return new ArrayList<Ticket>(rez);
		}catch (Exception e){
			return kolekcija;
		}

		
	}

	public Collection<Ticket> searchCenaDo(Collection<Ticket> kolekcija, String priceMax) {
		try {
			double price = Double.parseDouble(priceMax);
			List<Ticket> rez = kolekcija.stream().filter(k -> k.getPrice() < price ).collect(Collectors.toList());
			return new ArrayList<Ticket>(rez);
		}catch (Exception e){
			return kolekcija;
		}
	}
	
	public Collection<Ticket> searchDatumOd(Collection<Ticket> kolekcija, String datumOd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt;
		try {
			dt=sdf.parse(datumOd);
			List<Ticket> rez = kolekcija.stream().filter(k -> java.sql.Timestamp.valueOf(k.getManifestationDateTime()).after(dt)).collect(Collectors.toList());
			return new ArrayList<Ticket>(rez);
		}catch (Exception e){
			return kolekcija;
		}
	}

	public Collection<Ticket> searchDatumDo(Collection<Ticket> kolekcija, String datumDo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt;
		try {
			dt=sdf.parse(datumDo);
			List<Ticket> rez = kolekcija.stream().filter(k -> java.sql.Timestamp.valueOf(k.getManifestationDateTime()).before(dt)).collect(Collectors.toList());
			return new ArrayList<Ticket>(rez);
		}catch (Exception e){
			return kolekcija;
		}
	}
	
	public Collection<Ticket> filter(Collection<Ticket> kolekcija, Collection<String> listaUslova) {
		List<Ticket> result = new ArrayList<Ticket>();
		ArrayList<Ticket>temp;
		Set<String>uslovi = new HashSet<String>(listaUslova);
		if(uslovi.contains("vip"))
		{
			temp = (ArrayList<Ticket>) kolekcija.stream().filter(k -> k.getTicketType()==TicketType.VIP).collect(Collectors.toList());
			result.addAll(temp);
		}
		if(uslovi.contains("regular"))
		{
			temp = (ArrayList<Ticket>) kolekcija.stream().filter(k -> k.getTicketType()==TicketType.REGULAR).collect(Collectors.toList());
			result.addAll(temp);
		}
		if(uslovi.contains("fanpit"))
		{
			temp = (ArrayList<Ticket>) kolekcija.stream().filter(k -> k.getTicketType()==TicketType.FAN_PIT).collect(Collectors.toList());	
			result.addAll(temp);
		}
		if(uslovi.contains("reserved"))
		{
			temp = (ArrayList<Ticket>) kolekcija.stream().filter(k -> k.getTicketStatus()==TicketStatus.RESERVED).collect(Collectors.toList());
			result.addAll(temp);
		}
		if(uslovi.contains("canceled"))
		{
			temp = (ArrayList<Ticket>) kolekcija.stream().filter(k -> k.getTicketStatus()==TicketStatus.CANCELED).collect(Collectors.toList());
			result.addAll(temp);
		}
	 
		
		return result;
	}
	
	// Sort
	public List<Ticket> sortByManifestationName(List<Ticket> kolekcija, boolean opadajuce) {

		ArrayList<SimpleEntry<Ticket, String>>mapa =new ArrayList<SimpleEntry<Ticket,String>>();
		ArrayList<Ticket> result = new ArrayList<Ticket>();
		for(Ticket k : kolekcija)
		{
			String manifestacija = k.getManifestation().getName();
			SimpleEntry<Ticket, String> par = new SimpleEntry<Ticket, String>(k, manifestacija);
			mapa.add(par);
		}
		
		Collections.sort(mapa,sorterName);
		if (opadajuce) {
			Collections.reverse(mapa);
		}
		for(SimpleEntry<Ticket, String> p : mapa )
		{
			result.add(p.getKey());
		}
		
		return result;
	}
	
	public List<Ticket> sortirajPoCeniKarte(List<Ticket> kolekcija, boolean opadajuce) {

		Collections.sort(kolekcija, sorterPrice);
		if (opadajuce) {
			Collections.reverse(kolekcija);
		}
		ArrayList<Ticket> result = new ArrayList<>(kolekcija);
		return result;
	}
	
	public List<Ticket> sortirajPoDatumu(List<Ticket> kolekcija, boolean opadajuce) {

		Collections.sort(kolekcija, sorterDate);
		if (opadajuce) {
			Collections.reverse(kolekcija);
		}
		ArrayList<Ticket> result = new ArrayList<>(kolekcija);
		return result;
	}
	
	private void initSorters()
	{
		sorterPrice = new Comparator<Ticket>() {

			@Override
			public int compare(Ticket o1, Ticket o2) {
				return  Double.compare(o1.getPrice(), o2.getPrice());
			}
		};
		
		sorterDate = new Comparator<Ticket>() {

			@Override
			public int compare(Ticket o1, Ticket o2) {
				return  o1.getManifestationDateTime().compareTo(o2.getManifestationDateTime());
			}

		};
		
		sorterName = new Comparator<SimpleEntry<Ticket, String>>() {

			@Override
			public int compare(SimpleEntry<Ticket, String> o1, SimpleEntry<Ticket, String> o2) {
				
				return o1.getValue().toLowerCase().compareTo(o2.getValue().toLowerCase());

			}
	
		};
	
	
	}
	
}
