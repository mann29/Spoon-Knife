package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.util;

import java.util.Locale;
import java.util.ResourceBundle;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.utils.misc.AppUtilities;

public class InternationlizeDataImpl implements InternationlizeData{

	/**
	    * Classname for logging ..
	    */    
	    private static final String CLASS_NAME = InternationlizeDataImpl.class.getName();

	    /**
	    * Logger reference ..
	    */    
	    //private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	    
	    
	public ResourceBundle getResourceBundle(Locale locale)throws MitchellException{		
				
		ResourceBundle recBundle = null;
		
		try{
		
			recBundle = ResourceBundle.getBundle("AssignmentDeliveryResource",locale);
			
			if(recBundle == null){
				
				throw new MitchellException(AssignmentDeliveryErrorCodes.ERROR_GETTING_RESOURCE_BUNDLE, 
		                CLASS_NAME,
		                "getResourceBundle", 
		                "", 
		                AssignmentDeliveryErrorCodes.ERROR_GETTING_RESOURCE_BUNDLE_MSG + "\n" + "ResourceBundle is null");
		            
			}			
		
		}catch(MitchellException me){			
			me.setSeverity(MitchellException.SEVERITY.FATAL);
            me.setApplicationName(AssignmentDeliveryConstants.APPLICATION_NAME);
            me.setModuleName(AssignmentDeliveryConstants.MODULE_NAME);
            
            throw me;			
			
		}catch(Exception e){
			MitchellException me = new MitchellException(AssignmentDeliveryErrorCodes.ERROR_GETTING_RESOURCE_BUNDLE, 
	                CLASS_NAME,
	                "getResourceBundle", 
	                "", 
	                AssignmentDeliveryErrorCodes.ERROR_GETTING_RESOURCE_BUNDLE_MSG + "\n" + AppUtilities.getStackTraceString(e, true));
	            me.setSeverity(MitchellException.SEVERITY.FATAL);
	            me.setApplicationName(AssignmentDeliveryConstants.APPLICATION_NAME);
	            me.setModuleName(AssignmentDeliveryConstants.MODULE_NAME);
	            
	        throw me;			
			
		}
		return recBundle;
	}
	
	/**
	 *  This method updates the dynamic data for emails
	 */	
	
	public Locale getLocaleByLanguage(String language){
		
		String langValue = null;
        int pos = language.indexOf("-");
        if(pos > 0){
        	langValue = language.substring(0, pos);
        }else{        	
        	langValue = language;
        }
        Locale locale = new Locale(langValue);        
        
        return locale;		
	}
	
	
}
