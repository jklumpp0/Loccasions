package net.balusc.jsf.component.html;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlInputText;

@FacesComponent("HtmlInputFile")
public class HtmlInputFile extends HtmlInputText {

	@Override
	public String getRendererType() {
		return "javax.faces.File";
	}

}
