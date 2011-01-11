package com.loccasions.site;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.richfaces.component.UIExtendedDataTable;

import com.loccasions.ejbiface.LocationRemote;
import com.loccasions.ejbiface.MediaRemote;
import com.loccasions.model.Location;
import com.loccasions.model.Media;

@ManagedBean(name="tbl")
@SessionScoped
public class TableBean implements Serializable {
	/*
	 * Serial ID 
	 */
	private static final long serialVersionUID = -9003859243580425223L;
	
	/*
	 * The data structure for the table  
	 */
	private UIExtendedDataTable table;
	
	/*
	 * Fields to hold selection and find related items
	 */
	private List<Location> sel = new Vector<Location>();
	private Collection<Object> selection;
	private List<Media> mSel = new Vector<Media>();
	
	/*
	 * EJB injected accessors
	 */
	@EJB 
	private LocationRemote mLocations;
	
	@EJB
	private MediaRemote mMedia;
	
	public void setTable(UIExtendedDataTable table) {
		this.table = table;
	}

	public UIExtendedDataTable getTable() {
		return this.table;
	}
	
	public Collection<Object> getSelection() {
		return selection;
	}
	
	public void setSelection(Collection<Object> sel) {
		this.selection = sel;
	}
	
	public void selectionListener(AjaxBehaviorEvent event) {
		UIExtendedDataTable dataTable = (UIExtendedDataTable)event.getSource();
		Object key = dataTable.getRowKey();

		sel.clear();
		for (Object selkey : selection) {
			dataTable.setRowKey(selkey);
			if(dataTable.isRowAvailable()) {
				sel.add((Location)dataTable.getRowData());
			}
		}
		
		dataTable.setRowKey(key);
	}
	
	public String getCurSel() {
		return (sel == null || sel.size() == 0 ? "None" : sel.get(0).getName());
	}
	
	public List<Long> getMedia() {
		List<Long> ids = new Vector<Long>();
		
		// Otherwise find the media ids for images
		for (Location l : sel) {
			for (Media m : l.getMedia()) {
				String mime = m.getMime();
				if (mime.startsWith("image") || mime.startsWith("video")) {
					ids.add(new Long(m.getId()));
				}
			}
		}
		
		return ids ;
	}
		
	public String submit() {

			Location l = sel.get(0);
			l.setMedia(mSel);
			mLocations.createLocation(l);

		mSel = null;
		return null;
	}
	
	public List<Media> getAllMedia() {
		return mMedia.getMedia();
	}
	
	public void setMediaSel(List<Media> sel) {
		mSel = sel;
	}
	
	public List<Media> getMediaSel() {
		List<Media> media = mSel;
		
		if (sel != null) {
			Location l = sel.get(0);
			media = new Vector<Media>(l.getMedia());
		}
		
		return media;
	}
}
