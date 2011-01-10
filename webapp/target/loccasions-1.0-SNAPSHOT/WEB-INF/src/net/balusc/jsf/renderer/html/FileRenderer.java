package net.balusc.jsf.renderer.html;

import java.io.File;
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;

import net.balusc.http.multipart.MultipartRequest;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.TextRenderer;

@FacesRenderer(componentFamily = "javax.faces.Input", rendererType = "javax.faces.File")
public class FileRenderer extends TextRenderer {
	private static final String EMPTY_STRING = "";
	private static final Attribute[] INPUT_ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.INPUTTEXT);
	
	@Override
	protected void getEndTextToRender(FacesContext context, UIComponent component,
			String currentValue) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("input", component);
		writeIdAttributeIfNecessary(context, writer, component);
		writer.writeAttribute("type", "file", null);
		writer.writeAttribute("name", component.getClientId(context), "clientId");
		String styleClass = (String) component.getAttributes().get("styleClass");
		
		if (styleClass != null) {
			writer.writeAttribute("class", styleClass, "styleClass");
		}
		
		RenderKitUtils.renderPassThruAttributes(context, writer, component, INPUT_ATTRIBUTES, getNonOnChangeBehaviors(component));
		RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
		RenderKitUtils.renderOnchange(context, component, false);
		writer.endElement("input");
	}

	@Override
	public Object getConvertedValue(FacesContext context, UIComponent component,
			Object submittedValue) throws ConverterException {
		return (submittedValue != EMPTY_STRING) ? submittedValue : null;
	}

	@Override
	public void decode(FacesContext context, UIComponent component) {
		rendererParamsNotNull(context, component);
		
		if (!shouldDecode(component)) {
			return;
		}
		
		String clientId = decodeBehaviors(context, component);
		if (clientId == null) {
			clientId = component.getClientId(context);
		}
		
		File file = ((MultipartRequest)context.getExternalContext().getRequest()).getFile(clientId);
		((UIInput)component).setSubmittedValue((file != null) ? file : EMPTY_STRING);
	}

}
