package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enums.Gender;
import enums.Role;

public class Buyer extends User{
	private List<String> tickets; // ticket ids
	private double points;
	//private UserTypeName userType;
	
	public Buyer() {
		super();
	}

//	public Buyer(String username, String password, String firstName, String lastName, Gender gender,
//			LocalDate dateOfBirth, Role role, Boolean deleted, List<String> tickets, double points) {
//		super(username, password, firstName, lastName, gender, dateOfBirth, role, deleted);
//		this.tickets = tickets;
//		this.points = points;
//	}
	
	public List<String> getTickets() {
		return tickets;
	}
	public void setTickets(List<String> tickets) {
		this.tickets = tickets;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	
	
}
