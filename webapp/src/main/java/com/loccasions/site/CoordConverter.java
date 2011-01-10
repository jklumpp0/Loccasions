package com.loccasions.site;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.loccasions.model.Coordinate;

@FacesConverter("CoordConv")
public class CoordConverter implements Converter {

	public Object getAsObject(FacesContext arg0, UIComponent arg1, String s) {
		String[] parts = s.split(",");
		Coordinate coord = new Coordinate();
		
		if (parts.length != 2) {
			coord.setLatitude(0);
			coord.setLongitude(0);
		} else {
			coord.setLatitude(Double.parseDouble(parts[0]));
			coord.setLongitude(Double.parseDouble(parts[1]));
		}
		
		return coord;
	}

	public String getAsString(FacesContext context, UIComponent component,
			Object v) {
		if (v == null) {
			return "";
		} 
		
		Coordinate c = (Coordinate)v;
		return String.format("%1$f, %2$f", c.getLatitude(), c.getLongitude());
	}

}
