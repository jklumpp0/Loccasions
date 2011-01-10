package com.loccasions.site;

import java.util.List;
import java.util.Vector;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.loccasions.ejbiface.LocationRemote;
import com.loccasions.ejbiface.MediaRemote;
import com.loccasions.model.Location;
import com.loccasions.model.Media;

@ManagedBean
@RequestScoped
public class AssocBean {
	@EJB 
	private LocationRemote mLocations;
	
	@EJB
	private MediaRemote mMedia;
	
	private Location locSelection;
	
	private List<Media> mSel = new Vector<Media>();
	
	public String submit() {
		if (locSelection != null && mSel.size() > 0) {
			locSelection.setMedia(mSel);
			mLocations.createLocation(locSelection);
		}
		return "index.loc";
	}
	
	public List<Location> getLocations() {
		return mLocations.getLocations();
	}
	
	public void setLocation(Location loc) {
		locSelection = loc;
	}
	
	public Location getLocation() {
		return locSelection;
	}
	
	public List<Media> getMedia() {
		return mMedia.getMedia();
	}
	
	public void setMediaSel(List<Media> sel) {
		mSel = sel;
	}
	
	public List<Media> getMediaSel() {
		return mSel;
	}
}
