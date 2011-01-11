package com.loccasions.site;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import com.loccasions.ejbiface.LocationRemote;
import com.loccasions.ejbiface.MediaRemote;
import com.loccasions.model.Location;
import com.loccasions.model.Media;

@ManagedBean(name="loc")
@RequestScoped
public class LocationBacking {
	@EJB
	private LocationRemote mLoc;
	
	private Date mDate;
	private Location loccasion;
	private Location lSel;
	private List<Location> locs;
	String type;

	public Location getSelection() {
		return lSel;
	}
	
	public void setSelection(Location sel) { 
		lSel = sel;
	}
	
	public Date getDate() {
		return mDate;
	}
	
	public void setDate(Date date) {
		mDate = date;
	}
	
	public LocationBacking() {
		mDate = new Date();
		loccasion = new Location();
	}
	
	@PostConstruct
	void init() {
		locs = mLoc.getLocations();
	}
	
	public String createLoc() {
		mLoc.createLocation(loccasion);
		loccasion = new Location();
		locs = mLoc.getLocations();
		return null;
	}
	
	public List<Location> getLocations() {
		return locs;
	}
	
	
	public Location getLoccasion() {
		return loccasion;
	}
	
	public void setLoccasion(Location loc) {
		loccasion = loc;
	}
	
	public int getMediaCount() {
		return (lSel == null ? 0 : lSel.getMedia().size());
	}
}
