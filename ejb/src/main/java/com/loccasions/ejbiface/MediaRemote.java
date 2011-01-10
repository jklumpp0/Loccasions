package com.loccasions.ejbiface;

import java.util.List;

import javax.ejb.Remote;

import com.loccasions.model.Media;

@Remote
public interface MediaRemote {
	public abstract void createMedia(String name, String desc, String mime, String path);
	public abstract void createMedia(Media media);
	public abstract List<Media> getMedia();
	public abstract Media getMedia(int id);
}
