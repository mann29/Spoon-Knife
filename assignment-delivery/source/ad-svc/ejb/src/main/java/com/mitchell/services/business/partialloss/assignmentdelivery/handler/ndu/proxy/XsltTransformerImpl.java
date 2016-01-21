package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryErrorCodes;
import com.mitchell.utils.misc.AppUtilities;

/**
 * The Class XsltTransformerImpl.
 */
public final class XsltTransformerImpl implements XsltTransformer {

    /** The Constant CLZ_NAME. */
    private static final String CLZ_NAME = XsltTransformerImpl.class.getName();

    /** The Constant LOGGER. */
    private static final Logger LOGGER = Logger.getLogger(CLZ_NAME);

    /** A simple cache for template. Thread-Safe */
    private static final java.util.Map TEMPLATE_MAP
    = java.util.Collections.synchronizedMap(new java.util.HashMap());

    /* (non-Javadoc)
     */
    public String transformXmlString(final String styleSheetFileName,
            final String xmlString) throws MitchellException {
    	LOGGER.info("Entering XsltTransformerImpl#transformXmlString...................");
        String result = null;

    	LOGGER.info("Entering XsltTransformerImpl#transformXmlString...............styleSheetFileName...."+styleSheetFileName);

        final Templates template
        = getTemplates(styleSheetFileName);

        
    	LOGGER.info("Entering XsltTransformerImpl#transformXmlString.............template......"+template);

        java.io.StringWriter writer = null;
        java.io.StringReader reader = null;

        try {
            writer = new java.io.StringWriter();
            reader = new java.io.StringReader(xmlString);
           
            final Transformer transformer = template.newTransformer();
            transformer.transform(
                    new StreamSource(reader), new StreamResult(writer));

            result = writer.getBuffer().toString();

        	LOGGER.info("Entering XsltTransformerImpl#transformXmlString.............result......"+result);

            
        } catch (Exception e) {
            final String desc = new StringBuffer()
            .append("Transformer error for xslt file:")
            .append(styleSheetFileName)
            .append(",").append(AppUtilities.getStackTraceString(e, true))
            .toString();
            LOGGER.severe(desc);

            final MitchellException me = new MitchellException(
                    AssignmentDeliveryErrorCodes.ERROR_XSLT, CLZ_NAME,
                    "transformXmlString", desc, e);

            me.setSeverity(MitchellException.SEVERITY.FATAL);
            throw me;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }

            } catch (Exception e) {
                final String desc = new StringBuffer()
                .append("Error closing xslt reader/writer:")
                .append(AppUtilities.getStackTraceString(e, true))
                .toString();
                LOGGER.severe(desc);
            }

        }

        return result;
    }
    
   

    /**
     * Gets the templates.
     * Templates is Thread-Safe, so we can cache it.
     *
     * @param styleSheetFileName the style sheet file name
     * @return the templates
     * @throws MitchellException the mitchell exception
     */
    private Templates getTemplates(final String styleSheetFileName)
    throws MitchellException {

        java.io.File xsltFile = new java.io.File(styleSheetFileName);

         String entryKey = makeKey(styleSheetFileName);
        
        LOGGER.info("KEY RETRIEVED is as follows:::: "+entryKey);

        TemplateEntry entry = (TemplateEntry) TEMPLATE_MAP.get(entryKey);
        
        
        
        
        final long currentTimeStamp = xsltFile.lastModified();

        Templates templates = null;
        if (entry == null
            || entry.getLastModifiedTimeStamp() != currentTimeStamp) {
        	
        	
            LOGGER.info("Before calling createTemplates is as follows:::: "+xsltFile+"             "+entryKey+"        ");

            templates = creatTemplates(xsltFile, entryKey, currentTimeStamp);
        } else {
          templates = entry.getTemplate();
        }
        return templates;
    }

    /**
     * Creat templates.
     *
     * @param xsltFile the xslt file
     * @param entryKey the entry key
     * @param currentTimeStamp the current time stamp
     * @return the templates
     * @throws MitchellException the mitchell exception
     */
    private Templates creatTemplates(final java.io.File xsltFile,
            final String entryKey, final long currentTimeStamp)
            throws MitchellException {
        Templates templates = null;

        TransformerFactory factory = TransformerFactory.newInstance();
        
        try {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(xsltFile), AssignmentDeliveryConstants.DEFAULT_ENCODING)); 
            templates = factory.newTemplates(new StreamSource(reader));
            TemplateEntry entry = new TemplateEntry();
            entry.setTemplate(templates);
            entry.setLastModifiedTimeStamp(currentTimeStamp);
            entry.setFileCanonicalPath(entryKey);

            TEMPLATE_MAP.clear();
            TEMPLATE_MAP.put(entryKey, entry);
        } catch (Exception e) {
            final String desc = new StringBuffer()
            .append("TransformerConfigureaton error for xslt file:")
            .append(entryKey)
            .append(",").append(AppUtilities.getStackTraceString(e, true))
            .toString();
            LOGGER.severe(desc);

            MitchellException me = new MitchellException(
                    AssignmentDeliveryErrorCodes.ERROR_XSLT,
                    CLZ_NAME, "creatTemplates", desc, e);

            me.setSeverity(MitchellException.SEVERITY.FATAL);

            throw me;
        }

        return templates;
    }

    /**
     * Make key.
     *
     * @param styleSheetFileName the style sheet file name
     * @return the string
     * @throws MitchellException the mitchell exception
     */
    private String makeKey(final String styleSheetFileName)
    throws MitchellException {
    	LOGGER.info("Entering XsltTransformerImpl#transformXmlString.............makeKey......");

        String key = null;
        try {
        	
        	if(styleSheetFileName.contains("_fr")){
        		LOGGER.info("Entering XsltTransformerImpl#transformXmlString.............styleSheetFileName contains _fr......");
            key = new java.io.File(styleSheetFileName).getCanonicalPath()+"_fr";
        	}else if(styleSheetFileName.contains("_es")){
        		LOGGER.info("Entering XsltTransformerImpl#transformXmlString.............styleSheetFileName contains _es......");

                key = new java.io.File(styleSheetFileName).getCanonicalPath()+"_es";
            	}else{
            		LOGGER.info("Entering XsltTransformerImpl#transformXmlString.............styleSheetFileName is default i.e english......");

            		 key = new java.io.File(styleSheetFileName).getCanonicalPath();
            	}
        	
        	
        	
        	} catch (Exception e) {
            final String desc = new StringBuffer()
            .append("IO Error accessing XSLT file:")
            .append(styleSheetFileName)
            .append(",").append(AppUtilities.getStackTraceString(e, true))
            .toString();

            LOGGER.severe(desc);

            MitchellException me = new MitchellException(
                    AssignmentDeliveryErrorCodes.ERROR_XSLT, CLZ_NAME,
                    "makeKey", desc, e);

            me.setSeverity(MitchellException.SEVERITY.FATAL);

            throw me;
        }
        return key;
    }

    /**
     * The Class Template.
     */
    public static final class TemplateEntry {

        /** The template. */
        private Templates template;

        /** The last modified time stamp. */
        private long lastModifiedTimeStamp;

        /** The file canonical path. */
        private String fileCanonicalPath;

        /**
         * Gets the template.
         *
         * @return the template
         */
        public Templates getTemplate() {
            return template;
        }

        /**
         * Sets the template.
         *
         * @param template the new template
         */
        public void setTemplate(Templates template) {
            this.template = template;
        }

        /**
         * Gets the last modified time stamp.
         *
         * @return the last modified time stamp
         */
        public long getLastModifiedTimeStamp() {
            return lastModifiedTimeStamp;
        }

        /**
         * Sets the last modified time stamp.
         *
         * @param lastModifiedTimeStamp the new last modified time stamp
         */
        public void setLastModifiedTimeStamp(long lastModifiedTimeStamp) {
            this.lastModifiedTimeStamp = lastModifiedTimeStamp;
        }

        /**
         * Gets the file canonical path.
         *
         * @return the file canonical path
         */
        public String getFileCanonicalPath() {
            return fileCanonicalPath;
        }

        /**
         * Sets the file canonical path.
         *
         * @param fileCanonicalPath the new file canonical path
         */
        public void setFileCanonicalPath(String fileCanonicalPath) {
            this.fileCanonicalPath = fileCanonicalPath;
        }
    }

}
