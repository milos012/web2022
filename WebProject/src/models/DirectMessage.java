package models;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import services.LocalDateDeserializer;
import services.LocalDateSerializer;

public class DirectMessage {
	private User posiljalac;
	private User primalac;
	private String sadrzaj;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateSent;
	
	
	public DirectMessage() {
		super();
	}

	public DirectMessage(User posiljalac, User primalac, String sadrzaj, LocalDate dateSent) {
		super();
		this.posiljalac = posiljalac;
		this.primalac = primalac;
		this.sadrzaj = sadrzaj;
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

	public String getSadrzaj() {
		return sadrzaj;
	}

	public void setSadrzaj(String sadrzaj) {
		this.sadrzaj = sadrzaj;
	}

	public LocalDate getDateSent() {
		return dateSent;
	}

	public void setDateSent(LocalDate dateSent) {
		this.dateSent = dateSent;
	}

	@Override
	public String toString() {
		return "DirectMessage [posiljalac=" + posiljalac + ", primalac=" + primalac + ", sadrzaj=" + sadrzaj
				+ ", dateSent=" + dateSent + "]";
	}
	
	
	
}
