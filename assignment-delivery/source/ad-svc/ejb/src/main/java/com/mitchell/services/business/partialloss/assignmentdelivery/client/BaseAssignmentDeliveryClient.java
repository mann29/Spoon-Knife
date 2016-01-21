package com.mitchell.services.business.partialloss.assignmentdelivery.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.mitchell.common.exception.MitchellException;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfig;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryRemote;



public abstract class BaseAssignmentDeliveryClient {

	protected AssignmentDeliveryRemote getEJB(final AssignmentDeliveryLogger logger, final String workItemId) throws AssignmentDeliveryException{
		AssignmentDeliveryRemote ejb = null;
		String providerUrl = null;
	    String jndiName = null;
	    String contextFactory = null;
	    try{
	    	providerUrl = AssignmentDeliveryConfig.getJNDIProviderUrl();
	    	jndiName = AssignmentDeliveryConfig.getEJBName();
	    	contextFactory = AssignmentDeliveryConfig.getJNDIFactory();
	    	Properties environment = new Properties();
	        environment.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
	        environment.put(Context.PROVIDER_URL, providerUrl);
	        Context ctx = new InitialContext(environment);
	        ejb = (AssignmentDeliveryRemote)ctx.lookup(jndiName);
	       
	        if (ejb == null) {
	            throw new MitchellException(this.getClass().getName(), "getEJB",
	                "Create returned a null remote interface. " + "ProviderUrl="
	                    + providerUrl + ", jndiName=" + jndiName + ", contextFactory="
	                    + contextFactory);
	          }

	        } catch (javax.naming.NamingException ne) {
	        	logger.severe("NamingException occurred. Throwing AssignmentDeliveryException.");
	            throw logger.createException(AssignmentDeliveryErrorCodes.JNDI_LOOKUP_ERROR, ne, workItemId);
	        } catch (Exception e) {
	        	logger.severe("Exception occurred. Throwing AssignmentDeliveryException.");
	            throw logger.createException(AssignmentDeliveryErrorCodes.GENERAL_ERROR, e, workItemId);
	        }
	        finally {
	            logger.exiting();
	        }
		
		return ejb;
	}
}
