package com.loccasions.model;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllMedia", query = "select m from Media m"),
		@NamedQuery(name = "findMediaByID", query = "select m from Media m where m.id = :id")
})
public class Media implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8055055542864698035L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(length = 100)
	private String title;
	
	@Column(length = 400)
	private String description;
	
	private String path;

	@Transient
	private File mFile;
	
	@Column(length = 25)
	private String mime;
	
	@ManyToMany(mappedBy="media")
	private Collection<Location> locations;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	public Media() {
	}

	public Media(String title, String desc, String mime, String path) {
		this.setTitle(title);
		this.description = desc;
		this.path = path;
		this.mime = mime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	public InputStream getInput() {
		InputStream ret = null;
		
		if (path != null) {
			File file = new File(path);
			
			if (file.exists()) {
				try {
					ret = new FileInputStream(path);
				} catch (IOException e) {
					// Later ret will be set to an empty byte array stream
				}
			}
		}
		
		// If ret is still null return a new ByteArrayInputStream
		if (ret == null) {
			ret = new ByteArrayInputStream(new byte[0]);
		}
		
		return ret;
	}
	
	public long getSize() {
		if (path != null) {
			File file = new File(path);
			
			if (file.exists()) {
				return file.length();
			}
		}
		
		return -1;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getMime() {
		return mime;
	}
	
	@Override
	public String toString() {
		return String.format("[<%5$d> %1$s (%3$s): %2$d bytes - %4$s]", getTitle(), getSize(), getMime(), getPath(), getId());
	}
	
	public String getName() {
		if (path != null) {
			int idx = path.lastIndexOf(File.separator);
			
			if (idx > 0) {
				return path.substring(idx);
			}
		}
		
		return "";
	}
	
	@Override
	public boolean equals(Object rhs) {
		if (!(rhs instanceof Media))
			return false;
		
		return (this.id.equals(((Media)rhs).id));
	}
}
