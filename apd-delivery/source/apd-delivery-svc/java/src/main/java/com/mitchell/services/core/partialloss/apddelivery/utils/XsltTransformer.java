package com.mitchell.services.core.partialloss.apddelivery.utils; 

import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Logger;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.utils.misc.AppUtilities;

public class XsltTransformer {
    
    private static final String CLASS_NAME = XsltTransformer.class.getName(); 
    private static Logger logger = Logger.getLogger(XsltTransformer.class.getName());    
    
    private SystemConfigurationProxy systemConfigurationProxy;
    
    public String transformXmlString(String xmlString) throws MitchellException {
        
        String methodName = "transformXmlString";
        logger.entering(CLASS_NAME, methodName);
        String result = null;
        
        try {
        	
        	String fileName = systemConfigurationProxy.getXsltFilePath();;
            TransformerFactory factory = TransformerFactory.newInstance();
            
            Templates template = factory.newTemplates(new StreamSource(fileName));
            
            StringWriter writer = new StringWriter();
            Transformer transformer = template.newTransformer();
            transformer.transform(new StreamSource(new StringReader(xmlString)), new StreamResult(writer));
            
            result = writer.getBuffer().toString();
        } catch (Exception e) {
            
            throw new MitchellException(CLASS_NAME, methodName, AppUtilities.getStackTraceString(e, true));
        }
        logger.exiting(CLASS_NAME, methodName);
        return result;
    }

	/**
	 * @return the systemConfigurationProxy
	 */
	public SystemConfigurationProxy getSystemConfigurationProxy() {
		return systemConfigurationProxy;
	}

	/**
	 * @param systemConfigurationProxy the systemConfigurationProxy to set
	 */
	public void setSystemConfigurationProxy(
			SystemConfigurationProxy systemConfigurationProxy) {
		this.systemConfigurationProxy = systemConfigurationProxy;
	}
} 
