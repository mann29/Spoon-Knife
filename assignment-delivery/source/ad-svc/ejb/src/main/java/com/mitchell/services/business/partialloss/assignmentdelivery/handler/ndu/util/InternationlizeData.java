package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util;

import java.util.Locale;
import java.util.ResourceBundle;
import com.mitchell.common.exception.MitchellException;

/**
 * @author Sharma.preeti
 *
 */
public interface InternationlizeData {

		
	public ResourceBundle getResourceBundle(Locale locale)throws MitchellException;		
	
	public Locale getLocaleByLanguage(String language);
	
}
