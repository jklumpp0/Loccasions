package com.loccasions.site;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.loccasions.ejbiface.LocationRemote;
import com.loccasions.model.Location;

@ManagedBean(name="locationConverterBean")
@FacesConverter("locationConverter")
public class LocationConverter implements Converter {
	@EJB
	private LocationRemote mLocations;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Location l = null;
		int id = Integer.parseInt(value);
		l = mLocations.getLocation(id);
		return l;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		return ((Location)value).getId().toString();
	}

}
