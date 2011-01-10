package com.loccasions.ejbiface;

import java.util.List;

import javax.ejb.Remote;

import com.loccasions.model.Location;

@Remote
public interface LocationRemote {
	public abstract void createLocation(String name, String tag, String desc);
	public abstract List<Location> getLocations();
	public abstract Location getLocation(int id);
	public abstract void createLocation(Location loccasion);
}
