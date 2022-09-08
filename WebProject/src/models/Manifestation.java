package models;

import enums.ManifestationType;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import services.LocalDateTimeDeserializer;
import services.LocalDateTimeSerializer;

public class Manifestation {
	private int id;
    private String name;
    private ManifestationType manifestationType;
    private int capacity;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime manifestationDateTime;
    private double priceOfRegularTicket;
    private boolean isActive;
    private Location location;
    private String posterPath;
    private Boolean deleted;

    public Manifestation() {
    }

    public Manifestation(int id, String name, ManifestationType manifestationType, int capacity,  LocalDateTime manifestationDateTime, double priceOfRegularTicket, boolean isActive, Location location, String posterPath, Boolean deleted) {
        this.id = id;
    	this.name = name;
        this.manifestationType = manifestationType;
        this.capacity = capacity;
        this.manifestationDateTime = manifestationDateTime;
        this.priceOfRegularTicket = priceOfRegularTicket;
        this.isActive = isActive;
        this.location = location;
        this.posterPath = posterPath;
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManifestationType getManifestationType() {
        return manifestationType;
    }

    public void setManifestationType(ManifestationType manifestationType) {
        this.manifestationType = manifestationType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getManifestationDateTime() {
        return manifestationDateTime;
    }

    public void setManifestationDateTime(LocalDateTime manifestationDateTime) {
        this.manifestationDateTime = manifestationDateTime;
    }

    public double getPriceOfRegularTicket() {
        return priceOfRegularTicket;
    }

    public void setPriceOfRegularTicket(double priceOfRegularTicket) {
        this.priceOfRegularTicket = priceOfRegularTicket;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
