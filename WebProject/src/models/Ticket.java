package models;

import enums.TicketStatus;
import enums.TicketType;
import services.LocalDateTimeDeserializer;
import services.LocalDateTimeSerializer;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Ticket {
    private String id;
    private Manifestation manifestation;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime manifestationDateTime;
    private double price;
    private Buyer buyer;
    private TicketStatus ticketStatus;
    private TicketType ticketType;

    public Ticket() {
    }

    public Ticket(String id, Manifestation manifestation, LocalDateTime manifestationDateTime, double price, Buyer buyer, TicketStatus ticketStatus, TicketType ticketType) {
        this.id = id;
        this.manifestation = manifestation;
        this.manifestationDateTime = manifestationDateTime;
        this.price = price;
        this.buyer = buyer;
        this.ticketStatus = ticketStatus;
        this.ticketType = ticketType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manifestation) {
        this.manifestation = manifestation;
    }

    public LocalDateTime getManifestationDateTime() {
        return manifestationDateTime;
    }

    public void setManifestationDateTime(LocalDateTime manifestationDateTime) {
        this.manifestationDateTime = manifestationDateTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
