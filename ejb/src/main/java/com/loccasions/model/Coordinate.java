package com.loccasions.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Represent a physical location
 * 
 * @author Jared
 */
@Entity
public class Coordinate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7378960774598785605L;

	@Id
	@GeneratedValue
	private Long id;
	
	private double latitude;
	private double longitude;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}	
}
