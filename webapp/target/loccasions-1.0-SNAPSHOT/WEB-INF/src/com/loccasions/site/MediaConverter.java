package com.loccasions.site;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.loccasions.ejbiface.MediaRemote;
import com.loccasions.model.Media;

@ManagedBean(name="mediaConverterBean")
@FacesConverter(forClass = Media.class, value = "mediaConverter")
public class MediaConverter implements Converter {
	@EJB
	private MediaRemote mMedia;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Media m = null;
		
			//InitialContext ic = new InitialContext();
			//MediaRemote rem = (MediaRemote)ic.lookup("java:global/LoccasionEJB/MediaEJB");
			int id = Integer.parseInt(value);
			m = mMedia.getMedia(id);	
		
		
		return m;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return ((Media)value).getId().toString();
	}

}
