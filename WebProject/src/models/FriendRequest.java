package models;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import enums.RequestStatus;
import services.LocalDateDeserializer;
import services.LocalDateSerializer;

public class FriendRequest {
	
	private User posiljalac;
	private User primalac;
	private RequestStatus status;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateSent;
	
	
	public FriendRequest() {
		super();
	}
	
	public FriendRequest(User posiljalac, User primalac, RequestStatus status, LocalDate dateSent) {
		super();
		this.posiljalac = posiljalac;
		this.primalac = primalac;
		this.status = status;
		this.dateSent = dateSent;
	}
	
	public User getPosiljalac() {
		return posiljalac;
	}
	
	public void setPosiljalac(User posiljalac) {
		this.posiljalac = posiljalac;
	}
	
	public User getPrimalac() {
		return primalac;
	}
	
	public void setPrimalac(User primalac) {
		this.primalac = primalac;
	}
	
	public RequestStatus getStatus() {
		return status;
	}
	
	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	
	public LocalDate getDateSent() {
		return dateSent;
	}
	
	public void setDateSent(LocalDate dateSent) {
		this.dateSent = dateSent;
	}
	
	@Override
	public String toString() {
		return "FriendRequest [posiljalac=" + posiljalac + ", primalac=" + primalac + ", status=" + status
				+ ", dateSent=" + dateSent + "]";
	}
	
	

}
