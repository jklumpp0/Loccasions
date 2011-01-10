package com.loccasions.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 * Represent a location - it's name, description and physical coordinates.
 * 
 * @author Jared
 */
@Entity
@NamedQueries({
	@NamedQuery(name="findAllLocations", query="SELECT l FROM Location l"),
	@NamedQuery(name="findLocationByID", query="SELECT l FROM Location l WHERE l.id=:id")
})
public class Location implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2718720499083171359L;
	@Id
	@GeneratedValue
	private Long id;
	private String description;
	
	@Column(length=400)
	private String tag;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Coordinate coord;
	
	@Column(length=100)
	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<Media> media;
	
	/**
	 * @return the unique identifier of the location
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * Set the unique identifier
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * The description of the location
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Set the description of the location
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the tag line associated with this location
	 */
	public String getTag() {
		return tag;
	}
	
	/**
	 * Set the tag associated with this location
	 * @param tag the short tag text associated with this location
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	/**
	 * 
	 * @return the coordinate of this location
	 */
	public Coordinate getCoord() {
		return coord;
	}
	
	/**
	 * Set the coordinate of this location
	 *  
	 * @param coord the coordinate of the location
	 */
	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (!(rhs instanceof Location))
			return false;
		
		return this.id.equals(((Location)rhs).id);
	}
	
	public Collection<Media> getMedia() {
		return media;
	}
	
	public void setMedia(Collection<Media> media) {
		this.media = media;
	}
}
