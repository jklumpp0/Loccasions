package com.loccasions.ejb;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.loccasions.ejbiface.MediaRemote;
import com.loccasions.model.Media;

@Stateless
public class MediaEJB implements MediaRemote, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4822815979582948835L;
	
	@PersistenceContext(name = "LocationDB")
	private EntityManager mDB;
	
	@Override
	public void createMedia(String name, String desc, String mime, String path) {
		Media m = new Media(name, desc, mime, path);
		mDB.persist(m);		
	}

	@Override
	public void createMedia(Media media) {
		mDB.persist(media);
	}

	@Override
	public List<Media> getMedia() {
		return new Vector<Media>(mDB.createNamedQuery("findAllMedia").getResultList());
	}

	@Override
	public Media getMedia(int id) {
		List<Media> results = mDB.createNamedQuery("findMediaByID").setParameter("id", id).getResultList();

		if (results != null && results.size() == 1) {
			return results.get(0);
		}
		
		return null;
	}

}
