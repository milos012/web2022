package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Location;
import models.Manifestation;

public class ManifestationService {
    private ArrayList<Manifestation> manifestations;
    private String path;
    private ArrayList<Manifestation> allManifestations;
    ArrayList<Location> allLocations = new ArrayList<>();

    public ManifestationService(String path) {
        this.path = path;
        load(path);
		loadLocations(path);
    }

    public ArrayList<Manifestation> getManifestations() {
        return manifestations;
    }
    
    public void setManifestation(ArrayList<Manifestation> manifestations) {
		this.manifestations = manifestations;
	}
    
    public boolean addManifestation(Manifestation manifestation) {
		if(!checkDateAndLocation(manifestation.getManifestationDateTime(), manifestation.getLocation().getAddress()))
			return false;
		
		manifestation.setPosterPath("../images/default.png");
		manifestations.add(manifestation);
		writeManifestations();
		
		return true;
	}
    
    public boolean checkDateAndLocation(LocalDateTime localDateTime, String adresa) {
    	for (Manifestation manifestation : manifestations) {
			if(manifestation.isActive()) {
				if(manifestation.getLocation().getAddress().equals(adresa) && localDateTime.toLocalDate().equals(manifestation.getManifestationDateTime())) {
					return false;
				}
			}
		}
    	return true;
    }
    
    private void writeManifestations() {
		String data = path + File.separator;
		ObjectMapper maper = new ObjectMapper();
		try {
			maper.writeValue(Paths.get(data + "manifestations.json").toFile(), manifestations);
			System.out.println("Uspesno upisivanje manifestacija======");
		} catch (IOException e) {
			System.out.println("Error, writing manifestations failed!");
		}
		
	}
    
    private void load(String path) {

    	manifestations = new ArrayList<Manifestation>();
		ObjectMapper mapper = new ObjectMapper();
		String data = path + File.separator;
		List<Manifestation> lista = null;

		try {

			lista = Arrays.asList(mapper.readValue(Paths.get(data + "manifestations.json").toFile(), Manifestation[].class));

			for (Manifestation m : lista) {
				if(!m.getDeleted()) {
					manifestations.add(m);
				}
				allManifestations.add(m);
			}
			System.out.println("Ucitavanje manifestacija uspesno=====");

		} catch (IOException e) {
			System.out.println("Error, loading manifestations failed!");
		}

	}
    
    private void loadLocations(String path) {
		ObjectMapper mapper = new ObjectMapper();
		String data = path + java.io.File.separator;
		List<Location> temp;
		try {
			temp = Arrays.asList(mapper.readValue(Paths.get(data + "locations.json").toFile(), Location[].class));
			for(Location l: temp)
				allLocations.add(l);
		} catch (IOException e) {
			System.out.println("Error, loading locations failed!");		
		}
	}
}
