package com.loccasions.site;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.richfaces.component.UIExtendedDataTable;

import com.loccasions.model.Location;
import com.loccasions.model.Media;

@ManagedBean(name="tbl")
@SessionScoped
public class TableBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9003859243580425223L;
	private UIExtendedDataTable table;
	private List<Location> sel = new Vector<Location>();
	private Collection<Object> selection;
	private int val = 0;
	private String text = "blah";
	
	public String getText() {
		return text;
	}
	
	public void setTable(UIExtendedDataTable table) {
		this.table = table;
	}

	public UIExtendedDataTable getTable() {
		return this.table;
	}
	
	public String update() {
		val++;
		return null;
	}

	public String getRand() {
		return new Integer(val++ ).toString();
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
		text = "";
		sel.clear();
		for (Object selkey : selection) {
			dataTable.setRowKey(selkey);
			if(dataTable.isRowAvailable()) {
				sel.add((Location)dataTable.getRowData());
				text = text + "<br/>" + dataTable.getRowData().toString();
			}
		}
		dataTable.setRowKey(key);
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
}
