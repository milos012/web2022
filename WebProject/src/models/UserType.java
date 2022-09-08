package models;

import enums.UserTypeName;

public class UserType {
	
	private UserTypeName userTypeName;
	private double discount;
	private double requiredPoints;
	
	public UserType(UserTypeName userTypeName, double discount, double requiredPoints) {
		super();
		this.userTypeName = userTypeName;
		this.discount = discount;
		this.requiredPoints = requiredPoints;
	}

	public UserTypeName getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(UserTypeName userTypeName) {
		this.userTypeName = userTypeName;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getRequiredPoints() {
		return requiredPoints;
	}

	public void setRequiredPoints(double requiredPoints) {
		this.requiredPoints = requiredPoints;
	}
	
	

}
