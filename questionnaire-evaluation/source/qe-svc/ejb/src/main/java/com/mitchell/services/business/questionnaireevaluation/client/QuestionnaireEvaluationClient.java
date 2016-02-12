package com.mitchell.services.business.questionnaireevaluation.client;

import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.questionnaireevaluation.ejb.QuestionnaireEvaluationEJBRemote;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxyImpl;


public class QuestionnaireEvaluationClient {

	/**
     * class name.. 
     */
    private static final String CLASS_NAME = QuestionnaireEvaluationClient.class.getName();
    
    /**
     * Logger.. 
     */
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
	QuestionnaireEvaluationClient(){
	}
	
	
	public static QuestionnaireEvaluationEJBRemote getEJB()throws MitchellException    {
		LOGGER.entering("In getEJB", "QuestionnaireEvaluationEJBRemote");
		
		QuestionnaireEvaluationEJBRemote ejb = null;
		String providerUrl = null;
		String jndiName = null;
		String contextFactory = null;
		SystemConfigurationProxyImpl systemConfigurationProxy = null;
		
	  try {
		
		systemConfigurationProxy = new SystemConfigurationProxyImpl();
	    
	    providerUrl = systemConfigurationProxy.getSettingValue("/QuestionnaireEvaluationService/QEJavaClient/ProviderUrl");
	    jndiName = systemConfigurationProxy.getSettingValue("/QuestionnaireEvaluationService/QEJavaClient/EJBJndi");
	    contextFactory = systemConfigurationProxy.getSettingValue("/QuestionnaireEvaluationService/QEJavaClient/JndiFactory");
	    	    
	    Properties environment = new Properties();
	    environment.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
	    environment.put(Context.PROVIDER_URL, providerUrl);
	    Context ctx = new InitialContext(environment);
	    
	    ejb = (QuestionnaireEvaluationEJBRemote) ctx.lookup(jndiName);
	    
	    if (ejb == null) {
	      throw new MitchellException("QuestionnaireEvaluationClient ", "getEJB",
	          "Create returned a null remote interface. " + "ProviderUrl="
	              + providerUrl + ", jndiName=" + jndiName + ", contextFactory="
	              + contextFactory);
	    }	
	  } catch (javax.naming.NamingException e) {
	    throw new MitchellException(" QuestionnaireEvaluationClient ", "getEJB ",
	        "Naming exception getting EJB.", e);
	  } catch (Exception e) {
	    throw new MitchellException("QuestionnaireEvaluationClient", " getEJB ",
	        "Unexpected exception. " + "ProviderUrl=" + providerUrl
	            + ", jndiName=" + jndiName + ", contextFactory=" + contextFactory,e);
	  }
	  
	  LOGGER.exiting("In getEJB", "QuestionnaireEvaluationEJBRemote");
	  return ejb;
    }	
}
