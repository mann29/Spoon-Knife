package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.utils.misc.AppUtilities;

/**
 * The Class XsltTransformerImpl.
 */
public final class XsltTransformerImpl implements XsltTransformer {

    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            XsltTransformerImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private APDCommonUtilProxy apdCommonUtilProxy;

    /** A simple cache for template. Thread-Safe */
    private static final java.util.Map TEMPLATE_MAP
    = java.util.Collections.synchronizedMap(new java.util.HashMap());

    /**
     * Transform xml string.
     *
     * @param styleSheetFileName the style sheet file name
     * @param xmlString the xml string
     * @return the string
     * @throws MitchellException the mitchell exception
     */
    public String transformXmlString(final String styleSheetFileName,
            final String xmlString) throws MitchellException {
    	String methodName = "transformXmlString";
        logger.entering(CLASS_NAME, methodName);
        String result = null;

        final Templates template
        = getTemplates(styleSheetFileName);

        java.io.StringWriter writer = null;
        java.io.StringReader reader = null;

        try {
            writer = new java.io.StringWriter();
            reader = new java.io.StringReader(xmlString);

            final Transformer transformer = template.newTransformer();
            transformer.transform(
                    new StreamSource(reader), new StreamResult(writer));

            result = writer.getBuffer().toString();

        } catch (TransformerConfigurationException e) {
            final String desc = new StringBuffer()
            .append("TransformerConfigureaton error for xslt file:")
            .append(styleSheetFileName)
            .append(",").append(AppUtilities.getStackTraceString(e, true))
            .toString();
            apdCommonUtilProxy.logSEVEREMessage(desc);

            final MitchellException me = new MitchellException(
                    APDDeliveryConstants.ERROR_XSLT, CLASS_NAME,
                    "transformXmlString", desc, e);

            me.setSeverity(MitchellException.SEVERITY.FATAL);
            throw me;
        } catch (TransformerException e) {
            final String desc = new StringBuffer()
            .append("Transformer error for xslt file:")
            .append(styleSheetFileName)
            .append(",").append(AppUtilities.getStackTraceString(e, true))
            .toString();
            apdCommonUtilProxy.logSEVEREMessage(desc);

            final MitchellException me = new MitchellException(
                    APDDeliveryConstants.ERROR_XSLT, CLASS_NAME,
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
                apdCommonUtilProxy.logSEVEREMessage(desc);
            }

        }
        logger.exiting(CLASS_NAME, methodName);
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

        final String entryKey = makeKey(styleSheetFileName);

        TemplateEntry entry = (TemplateEntry) TEMPLATE_MAP.get(entryKey);
        final long currentTimeStamp = xsltFile.lastModified();

        Templates templates = null;
        if (entry == null
            || entry.getLastModifiedTimeStamp() != currentTimeStamp) {
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
            templates = factory.newTemplates(new StreamSource(xsltFile));
            TemplateEntry entry = new TemplateEntry();
            entry.setTemplate(templates);
            entry.setLastModifiedTimeStamp(currentTimeStamp);
            entry.setFileCanonicalPath(entryKey);

            TEMPLATE_MAP.put(entryKey, entry);
        } catch (TransformerConfigurationException e) {
            final String desc = new StringBuffer()
            .append("TransformerConfigureaton error for xslt file:")
            .append(entryKey)
            .append(",").append(AppUtilities.getStackTraceString(e, true))
            .toString();
            apdCommonUtilProxy.logSEVEREMessage(desc);

            MitchellException me = new MitchellException(
                    APDDeliveryConstants.ERROR_XSLT, CLASS_NAME, "creatTemplates", desc, e);

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
        String key = null;
        try {
            key = new java.io.File(styleSheetFileName).getCanonicalPath();
        } catch (IOException e) {
            final String desc = new StringBuffer()
            .append("IO Error accessing XSLT file:")
            .append(styleSheetFileName)
            .append(",").append(AppUtilities.getStackTraceString(e, true))
            .toString();

            apdCommonUtilProxy.logSEVEREMessage(desc);

            MitchellException me = new MitchellException(
                    APDDeliveryConstants.ERROR_XSLT, CLASS_NAME, "makeKey", desc, e);

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

	/**
	 * @return the apdCommonUtilProxy
	 */
	public APDCommonUtilProxy getApdCommonUtilProxy() {
		return apdCommonUtilProxy;
	}

	/**
	 * @param apdCommonUtilProxy the apdCommonUtilProxy to set
	 */
	public void setApdCommonUtilProxy(APDCommonUtilProxy apdCommonUtilProxy) {
		this.apdCommonUtilProxy = apdCommonUtilProxy;
	}

}
