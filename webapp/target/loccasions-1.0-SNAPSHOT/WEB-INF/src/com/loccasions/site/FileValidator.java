package com.loccasions.site;

import java.io.File;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("fileValidator")
public class FileValidator implements Validator {
	private static final int MAX_FILE_SIZE = 1000000;
	
	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		File file = (File)value;
		
		if (file != null && file.length() > MAX_FILE_SIZE) {
			file.delete();
			throw new ValidatorException(new FacesMessage(String.format("The file \"%1$s\" was larger than %2$d bytes.", file.getName(), MAX_FILE_SIZE)));
		}
	}

}
