package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Role;

public class Seller extends User{
	
	private ArrayList<Integer> manifestations;  // Manifestation IDs
	private List<Integer> tickets;  // ticket IDs
	
	
	public Seller() {
		super();
	}


	public Seller(String username, String password, String firstName, String lastName, Gender gender,
			LocalDate dateOfBirth, Role role, Boolean deleted, ArrayList<Integer> manifestations,
			List<Integer> tickets) {
		super(username, password, firstName, lastName, gender, dateOfBirth, role, deleted);
		this.manifestations = manifestations;
		this.tickets = tickets;
	}


	public ArrayList<Integer> getManifestations() {
		return manifestations;
	}


	public void setManifestations(ArrayList<Integer> manifestations) {
		this.manifestations = manifestations;
	}


	public List<Integer> getTickets() {
		return tickets;
	}


	public void setTickets(List<Integer> tickets) {
		this.tickets = tickets;
	}
	
	

}
