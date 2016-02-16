package com.mitchell.services.core.partialloss.apddelivery.pojo.delegator; 

// Fix 97154 : Remove obsolete imports.
import org.apache.xmlbeans.XmlException;
//import com.bea.xml.XmlException;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.apddelivery.APDBroadcastMessageType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDRecipientsListType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.APDDeliveryHandler;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.AppLogHelper;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.JMSSender;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.utils.misc.AppUtilities;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.jms.JMSException;

/**
 * This class traverses through Broadcast Messages and 
 * sends that to APDDeliveryServiceBroadcastMessageHandler.
 *  
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 * @see APDDeliveryHandler
 */
public class APDBroadcastMessageHandler implements APDDeliveryHandler { 
    
    /**
     * class name.
     */
    private static final String CLASS_NAME =  
                            APDBroadcastMessageHandler.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private AppLogHelper appLogHelper;
    private JMSSender jmsSender;
    private APDCommonUtilProxy apdCommonUtilProxy;
    
    /**
     * This class traverses through Broadcast Messages and 
     * sends that to APDDeliveryServiceBroadcastMessageHandler.
     * 
     * @param context
     * APDDeliveryContextDocument object
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifact(APDDeliveryContextDocument context) 
                                                    throws MitchellException {
        String methodName = "deliverArtifact";
        logger.entering(CLASS_NAME, methodName);
        
        APDBroadcastMessageType apdBmsg = null;
        
        apdBmsg = context.getAPDDeliveryContext().getAPDBroadcastMessage();
        long [] recipients = 
            apdBmsg.getRecipientsList().getRecipientOrgIdArray();
        try {
            // loop through the list of recipents
            for (int i = 0;i < recipients.length;i++) {
                APDDeliveryContextDocument apdContextForRec =
                    populateMessageForSingleRecipient(
                                        context, 
                                        recipients[i], 
                                        false);
                // post message to queue to deliver broadcast message  
                // for each recipient
                jmsSender.sendMessageToBroadcastMessageQueue(
                                                apdContextForRec.toString(), 
                                                null, 
                                                null);
                apdCommonUtilProxy.logFINEMessage("APD Broadcast Message Delivery: " 
                    + "message sent to Broadcast Message Queue for User# "
                    + (i+1));
            }
            
            // post message to queue to deliver broadcast message
            // for sender
            if (apdBmsg.getEmailSender()) {
                APDDeliveryContextDocument apdContextForSender = 
                    populateMessageForSingleRecipient(context, 0, true);
                jmsSender.sendMessageToBroadcastMessageQueue(
                                                apdContextForSender.toString(), 
                                                null, 
                                                null);
                apdCommonUtilProxy.logFINEMessage("APD Broadcast Message Delivery: " 
                                    + "Email notification sent to Sender");
            }
        } catch (JMSException jmse) {
            throw new MitchellException(
                APDDeliveryConstants.ERROR_SENDING_BROADCAST_MSG_JMS_QUEUE,
                CLASS_NAME,
                methodName,
                apdBmsg.getAPDCommonInfo().getWorkItemId(),
                APDDeliveryConstants.ERROR_SENDING_BROADCAST_MSG_JMS_QUEUE_MSG 
                    + "\n" 
                    + AppUtilities.getStackTraceString(jmse));
        } catch (XmlException xe) {
            throw new MitchellException(
                APDDeliveryConstants.ERROR_UNEXPECTED,
                CLASS_NAME,
                methodName,
                apdBmsg.getAPDCommonInfo().getWorkItemId(),
                APDDeliveryConstants.ERROR_UNEXPECTED_MSG 
                    + "\n" 
                    + AppUtilities.getStackTraceString(xe));
        } catch (Exception e) {            
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_SENDING_BROADCAST_MSG_RUNTIME_ERROR,
                        CLASS_NAME,
                        methodName,
                        apdBmsg.getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_SENDING_BROADCAST_MSG_RUNTIME_ERROR_MSG
                        + "\n"
                        + AppUtilities.getStackTraceString(e));
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * @param incomingAPDContext
     * APDDeliveryContextDocument object
     * @param recipientOrgId
     * Org ID of Broadcast Message recipient
     * @param emailToSender
     * true- set EmailSender field
     * false- don't set EmailSender field
     * @return APDDeliveryContextDocument
     * @throws XmlException
     * XmlException
     */
    private APDDeliveryContextDocument populateMessageForSingleRecipient(
                                            APDDeliveryContextDocument incomingAPDContext, 
                                            long recipientOrgId, 
                                            boolean emailToSender) 
                                            throws XmlException {
        APDDeliveryContextDocument apdContext = 
            APDDeliveryContextDocument.Factory.parse(incomingAPDContext.toString());
                
        APDBroadcastMessageType apdBmType = 
            apdContext.getAPDDeliveryContext().getAPDBroadcastMessage();
        
        if (recipientOrgId != 0) {
            APDRecipientsListType recList = 
                APDRecipientsListType.Factory.newInstance();
            recList.setRecipientOrgIdArray(new long[] {recipientOrgId});
            apdBmType.setRecipientsList(recList);
        }
        apdBmType.setEmailSender(emailToSender);
        
        return apdContext;
    }
    
    /**
     * Gets the AppLogHelper.
     *
     * @return AppLogHelper
     */
    public AppLogHelper getAppLogHelper() {
        return appLogHelper;
    }

    /**
     * Sets the AppLogHelper.
     *
     * @param appLogHelper
     */
    public void setAppLogHelper(AppLogHelper appLogHelper) {
        this.appLogHelper = appLogHelper;
    }
    
    /**
     * Gets the JMS Sender.
     *
     * @return JMSSender
     */
    public JMSSender getJMSSender() {
        return jmsSender;
    }

    /**
     * Sets the JMS Sender.
     *
     * @param jmsSender
     */
    public void setJMSSender(final JMSSender jmsSender) {
        this.jmsSender = jmsSender;
    }
    
    /**
     * This method is not needed fro Broadcast Messages.
     * 
     * @param context
     * @param attachments
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifact(
                            APDDeliveryContextDocument context, 
                            ArrayList attachments) 
                            throws MitchellException {
        // no-op
    }

    /**
     * This method is not needed fro Broadcast Messages.
     * 
     * @param context
     * @param attachments
     * @throws MitchellException
     * Mitchell Exception
     */
    public void deliverArtifactNotification(APDDeliveryContextDocument context) 
                                                    throws MitchellException {
        // no-op
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
