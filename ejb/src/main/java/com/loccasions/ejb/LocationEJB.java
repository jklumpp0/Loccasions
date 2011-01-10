package com.loccasions.ejb;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.loccasions.ejbiface.LocationRemote;
import com.loccasions.model.Coordinate;
import com.loccasions.model.Location;

@Stateless
public class LocationEJB implements LocationRemote, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6869468010769899013L;
	
	@PersistenceContext(name="LocationDB")
	private EntityManager mEM;
	
	public List<Location> getLocations() {
		return new Vector<Location>(mEM.createNamedQuery("findAllLocations").getResultList());
	}
	
	public void createLocation(String name, String tag, String desc) {
		Location l = new Location();
		l.setCoord(null);
		l.setTag(tag);
		l.setDescription(desc);
		l.setName(name);
		Coordinate coord = new Coordinate();
		coord.setLatitude(-33);
		coord.setLongitude(44);
		l.setCoord(coord);
		mEM.persist(l);
	}

	@Override
	public void createLocation(Location loccasion) {
		if (loccasion.getId() != null) { 
			mEM.merge(loccasion);
		} else {
			mEM.persist(loccasion);
		}
	}

	@Override
	public Location getLocation(int id) {
		List<Location> results = mEM.createNamedQuery("findLocationByID").setParameter("id", id).getResultList();
		
		if (results != null && results.size() == 1) {
			return results.get(0);
		}
		
		return null;
	}
}
