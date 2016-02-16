package com.mitchell.services.core.partialloss.apddelivery.pojo.delegator; 

// Fix 97154 : Remove obsolete imports.
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.NodeType;
import com.mitchell.common.types.UserHierType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.BroadcastMessageDocument;
import com.mitchell.schemas.BroadcastMessageDocument.BroadcastMessage;
import com.mitchell.schemas.EnvelopeBodyContentType;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MimeTypeDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.MitchellEnvelopeType.EnvelopeBodyList;
import com.mitchell.schemas.ReceiverDocument;
import com.mitchell.schemas.SenderDocument;
import com.mitchell.schemas.apddelivery.APDBroadcastMessageType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.BroadcastMessageProcessorProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ECAlertProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.AppLogHelper;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.CustomSettingHelper;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.NotificationHelper;
import com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelper;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.MessagingContext;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * This class delivers Broadcast Messages to following kind of destinations.
 * <ul>
 * <li>WCAP
 * <li>Estimator (eClaim)
 * </ul>
 * <p>
 *  
 * @author vb100291
 * @version %I%, %G%
 * @since 1.0
 * @see APDBroadcastMessageDelegator
 */
public class APDBroadcastMessageDelegatorImpl 
                            implements APDBroadcastMessageDelegator { 
                                
    /**
     * class name.
     */
    private static final String CLASS_NAME = 
                            APDBroadcastMessageDelegatorImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    //private static final String ECLAIM_DELIVERY_SETTING = "ECLAIM_MCFXML";
    
    private NotificationHelper notificationHelper;
    private CustomSettingHelper customSettingHelper;
    private ECAlertProxy ecAlertProxy;
    private BroadcastMessageProcessorProxy broadcastMessageProcessorProxy;
    private APDCommonUtilProxy apdCommonUtilProxy;
    private UserHelper userHelper;
    private AppLogHelper appLogHelper;
    private SystemConfigurationProxy systemConfigurationProxy;
        
    /**
     * This method sends Broadcast Messages to Recipients/Senders.
     * 
     * @param apdContext
     * APDDeliveryContextDocument object
     * @throws MitchellException
     * Mitchell Exception
     */
    public void sendBroadcastMessage(APDDeliveryContextDocument apdContext) 
                                                    throws MitchellException {
        
        String methodName = "sendBroadcastMessage";
        logger.entering(CLASS_NAME, methodName);
        
        StringBuffer sbMessage = new StringBuffer();
        sbMessage.append("Broadcast Message Delivery: ");
        sbMessage.append("APDDeliveryContextDocument received in Internal listner is: "+apdContext.toString());
        apdCommonUtilProxy.logFINEMessage(sbMessage.toString());
        
        APDBroadcastMessageType bMsg = 
            apdContext.getAPDDeliveryContext().getAPDBroadcastMessage();
                        
        UserInfoDocument recipientUserInfoDoc = getRecipientUserInfo(bMsg);
        if(recipientUserInfoDoc == null){
            MitchellException me = new MitchellException(APDDeliveryConstants.ERROR_RECIPIENT_USERINFO, 
                    CLASS_NAME,
                    methodName, 
                    apdContext.getAPDDeliveryContext().getAPDBroadcastMessage().getAPDCommonInfo().getWorkItemId(), 
                    APDDeliveryConstants.ERROR_RECIPIENT_USERINFO_MSG);
                
                me.setCompanyCode(apdContext.getAPDDeliveryContext().getAPDBroadcastMessage().getAPDCommonInfo().getInsCoCode());
                me.setSeverity(MitchellException.SEVERITY.FATAL);
                me.setApplicationName(APDDeliveryConstants.APP_NAME);
                me.setModuleName(APDDeliveryConstants.MODULE_NAME);
                
                throw me;
        }        
        UserInfoType recipientUserInfo = recipientUserInfoDoc.getUserInfo();
        
        // Broadcasting Message to sender
        if (bMsg.getEmailSender()) {
            apdCommonUtilProxy.logFINEMessage("Broadcast Message Delivery: E-mail to be sent to Sender");
            notificationHelper.sendEmailToSender(apdContext);
        } else {
            // Broadcasting Message to recipient   
            if (isWCAPDestination(recipientUserInfo)) {
                // recipient is a WCAP user
                apdCommonUtilProxy.logFINEMessage("Broadcast Message Delivery: Recipient is WCAP"+recipientUserInfo.getOrgID());
                                
                String eventId = systemConfigurationProxy.getEventIdForWCAPUser();
                MitchellEnvelopeDocument meDoc = createMeDocForWCAPReceipient(apdContext);
                
                MessagingContext msgContext = new MessagingContext(Integer.parseInt(eventId),
                            meDoc,
                            recipientUserInfoDoc,//userinfo doc
                            bMsg.getAPDCommonInfo().getWorkItemId(),
                            "");
                
                broadcastMessageProcessorProxy.publishToMessageBus(msgContext);
                // app logging for send message to wCAP receipient
                appLogHelper.appLogBroadcastMsg(bMsg,
                                            recipientUserInfo,
                                            Integer.toString(APDDeliveryConstants.BROADCAST_MSG_WCAP_RECIPIENT),
                                            APDDeliveryConstants.BROADCAST_MSG_WCAP_RECIPIENT_MSG);
            } else if (isEstimator(recipientUserInfo)) {
                // recipient is an Estimator
                apdCommonUtilProxy.logFINEMessage("Broadcast Message Delivery: Recipient is Estimator"+recipientUserInfo.getOrgID());
                if (isEclaimDelivery(recipientUserInfoDoc)) {
                    
                    apdCommonUtilProxy.logFINEMessage("Broadcast Message Delivery: " 
                                + "Global Alert to be sent to Estimator on eClaim" +recipientUserInfo.getOrgID());
                    String eclaimAlertMessageContent = createEcAlertBroadcastMessage(bMsg);
                        
                    apdCommonUtilProxy.logFINEMessage("Global Alert message content: " + eclaimAlertMessageContent);                    
                    
                    UserInfoType receivingUser = recipientUserInfo;
                    //if recipientuserinfo is a staff user then we hav to send message to the recipientuserinfo
                    //if its non-staff then its associated bodyshop or independent appraiser need to be identified and will send to them.
                    if(!isStaffUser(recipientUserInfoDoc.getUserInfo())){
                       apdCommonUtilProxy.logFINEMessage("Non-Staff user"); 
                       UserInfoDocument shopUserInfo = userHelper.getAssociatedShopUserInfo(Long.parseLong(recipientUserInfoDoc.getUserInfo().getOrgID()));
                       receivingUser = shopUserInfo.getUserInfo();
                       apdCommonUtilProxy.logFINEMessage("receivingUser.getCrossOverInsuranceCompanyArray(0).getCompanyID()-"+receivingUser.getCrossOverInsuranceCompanyArray(0).getCompanyID());
                    }
                    apdCommonUtilProxy.logFINEMessage("receivingUser.getStaffType()-"+receivingUser.getStaffType());
                    apdCommonUtilProxy.logFINEMessage("receivingUser.getOrgCode()-"+receivingUser.getOrgCode());
                    
                    ecAlertProxy.deliverGlobalAlert(
                            receivingUser,
                            bMsg.getAPDCommonInfo().getWorkItemId(), 
                            APDDeliveryConstants.BROADCAST_ALERT_ORIGIN,
                            eclaimAlertMessageContent);
                    
                    //applogging for sending message to Estimator
                    appLogHelper.appLogBroadcastMsg(bMsg,
                                                recipientUserInfo,
                                                Integer.toString(APDDeliveryConstants.BROADCAST_MSG_ESTIMATOR_RECIPIENT),
                                                APDDeliveryConstants.BROADCAST_MSG_ESTIMATOR_RECIPIENT_MSG);
                }
            } else {
                // recipient is neither WCAP nor Estimator
                apdCommonUtilProxy.logFINEMessage("Broadcast Message Delivery: " 
                    + "Recipient is neither WCAP nor Estimator"+recipientUserInfo.getOrgID());
                // app log
                appLogHelper.appLogBroadcastMsg(bMsg,
                                                recipientUserInfo,
                                                Integer.toString(APDDeliveryConstants.BROADCAST_MSG_RECIPIENT_NOT_WCAP_OR_ESTIMATOR),
                                                APDDeliveryConstants.BROADCAST_MSG_RECIPIENT_NOT_WCAP_OR_ESTIMATOR_MSG);
            }
                        
            // send Broadcast Message e-mail to recipient
            boolean emailRquired = isEmailToBeSentToRecipient(recipientUserInfoDoc);
            if (emailRquired) {
                apdCommonUtilProxy.logFINEMessage("Broadcast Message Delivery: "
                    + "E-mail to be sent to Recipient");
                notificationHelper.sendEmailToRecipient(
                                                    recipientUserInfoDoc, 
                                                    apdContext);
            }
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method customizes Broadcast Message content for eClaim.
     * 
     * @param bMsg
     * @param String
     */
    private String createEcAlertBroadcastMessage(APDBroadcastMessageType bMsg) {
        
        String methodName = "createEcAlertBroadcastMessage";
        logger.entering(CLASS_NAME, methodName);
        
        StringBuffer message = null;//new StringBuffer();
        
        UserInfoType userInfo = 
            bMsg.getAPDCommonInfo().getSourceUserInfo().getUserInfo();
        String name = userInfo.getFirstName() + " " + userInfo.getLastName();
                
        String companyName = null;
        companyName = getCompanyName(userInfo);
        
        message = new StringBuffer(systemConfigurationProxy.getECAlertMessageDesc());
        
        int insertAtPosition = message.indexOf("1");
        message.replace(insertAtPosition,insertAtPosition+1,name);
        
        insertAtPosition = message.indexOf("2");
        message.replace(insertAtPosition,insertAtPosition+1,companyName);
        
        
        insertAtPosition = message.indexOf("3");        
        message.replace(insertAtPosition,insertAtPosition+1,bMsg.isSetMessageSubject()?bMsg.getMessageSubject():"");
        
        
        insertAtPosition = message.indexOf("4");
        message.replace(insertAtPosition,insertAtPosition+1,bMsg.isSetMessageContent()?bMsg.getMessageContent():"");
        
        logger.exiting(CLASS_NAME, methodName);
        return message.toString();
    }
    
    /**
     * This method returns UserInfo of Brodacast Message Recipient.
     * 
     * @param bMsg
     * APDBroadcastMessageType object
     * @return UserInfoDocument
     * UserInfo of the Brodacast Message recipient
     * @throws MitchellException
     * Mitchell Exception
     */
    private UserInfoDocument getRecipientUserInfo(APDBroadcastMessageType bMsg) 
                                                    throws MitchellException {
        String methodName = "getRecipientUserInfo";
        logger.entering(CLASS_NAME, methodName);
        
        UserInfoDocument recipientUserInfo = 
            userHelper.getRecipientUserInfo(bMsg); 
        
        
        logger.exiting(CLASS_NAME, methodName);
        return recipientUserInfo;
    }
    
    /**
     * This method determines if the given UserInfo belongs to a WCAP destination.
     * 
     * @param userInfo
     * UserInfoType object
     * @return boolean
     * true- WCAP
     * false- not WCAP
     * @throws MitchellException
     * Mitchell Exception
     */
    private boolean isWCAPDestination(UserInfoType userInfo) 
                                                    throws MitchellException {
        String methodName = "isWCAPDestination";
        logger.entering(CLASS_NAME, methodName);
        
        boolean wcap = false;
        
        if (apdCommonUtilProxy.checkApplCode(userInfo, systemConfigurationProxy.getWCAPUserApplCode())) {
            wcap = true;
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return wcap;
    }
    
    /**
     * This method determines if the given UserInfo belongs to an Estimator.
     * 
     * @param userInfo
     * UserInfoType object
     * @return boolean
     * true- Estimator
     * false- not Estimator
     * @throws MitchellException
     * Mitchell Exception
     */
    private boolean isEstimator(UserInfoType userInfo) 
                                                    throws MitchellException {
        String methodName = "isEstimator";
        logger.entering(CLASS_NAME, methodName);
        
        boolean estimator = false;
        
        if (apdCommonUtilProxy.checkApplCode(userInfo, systemConfigurationProxy.getEstimatorApplCode())) {
            estimator = true;
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return estimator;
    }
    
    /**
     * This method determines if the given UserInfo belongs to a eClaim user.
     * 
     * @param userInfo
     * UserInfoDocument object
     * @return boolean
     * true- eClaim user
     * false- not eClaim user
     * @throws MitchellException
     * Mitchell Exception
     */
    private boolean isEclaimDelivery(UserInfoDocument userInfo) 
            throws MitchellException {
        
        String methodName = "isEclaimDelivery";
        logger.entering(CLASS_NAME, methodName);
        
        String sipAssignmentDelivery = 
                customSettingHelper.getSIPAssignmentDeliveryDestination(
                                                    userInfo);
        String partialLossSIPDelDestinationEclaim = systemConfigurationProxy.getPartiallossSIPDelDestinationEclaim();
        
        boolean result = false;
        if (!apdCommonUtilProxy.isNullOrEmpty(sipAssignmentDelivery) && 
                sipAssignmentDelivery.matches(partialLossSIPDelDestinationEclaim)) {
            result = true;
        }
        logger.exiting(CLASS_NAME, methodName);
        return result;
    }
    
    /**
     * This method determines if e-mail notification is to be sent 
     * to Recipient of Broadcast Message.
     * 
     * @param userInfoDocument
     * UserInfoDocument object
     * @return boolean
     * true- send e-mail notification to recipient
     * false- do not send e-mail notification to recipient
     * @throws MitchellException
     * Mitchell Exception
     */
    private boolean isEmailToBeSentToRecipient(UserInfoDocument userInfoDocument) 
                throws MitchellException {
        
        String methodName = "isEmailToBeSentToRecipient";
        logger.entering(CLASS_NAME, methodName);
        
        String emailToRec = 
                customSettingHelper.isEmailRequired(userInfoDocument);
        boolean result = false;
        if (!apdCommonUtilProxy.isNullOrEmpty(emailToRec) && 
                "Y".equalsIgnoreCase(emailToRec)) {
            result = true;
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return result;
    }
    
    private MitchellEnvelopeDocument createMeDocForWCAPReceipient(APDDeliveryContextDocument apdContext)throws MitchellException{
        
        String methodName = "createMeDocForWCAPReceipient";
        logger.entering(CLASS_NAME, methodName);
        
        MitchellEnvelopeDocument  meDoc = null;   
        
        try{
            
         
        MitchellEnvelopeHelper meHelper = MitchellEnvelopeHelper.newInstance();        
        meDoc = meHelper.getDoc();
        
        MitchellEnvelopeType meType = meDoc.getMitchellEnvelope();
        meType.addNewEnvelopeContext();
        
        EnvelopeBodyList envBodyList = meType.addNewEnvelopeBodyList();
        EnvelopeBodyType envBodyType = envBodyList.addNewEnvelopeBody();

        EnvelopeBodyMetadataType envelopeBdMetaDataTyp = envBodyType.addNewMetadata();
        
        //adding metadata        
        envelopeBdMetaDataTyp.setIdentifier(APDDeliveryConstants.BC_MSG_IDENTIFIER);
        envelopeBdMetaDataTyp.setMitchellDocumentType(APDDeliveryConstants.BC_MSG_MITCHELLDOCTYPE);
        envelopeBdMetaDataTyp.setXmlBeanClassname(APDDeliveryConstants.BC_MSG_XMLBEANCLASSNAME);
        
        //creating content 
        BroadcastMessageDocument bcMsgDoc = BroadcastMessageDocument.Factory.newInstance();
        BroadcastMessage bcMessage = bcMsgDoc.addNewBroadcastMessage();
        
        bcMessage.setMessageId(apdContext.getAPDDeliveryContext().getAPDBroadcastMessage().getAPDCommonInfo().getMessageGlobalId());
        bcMessage.setDateSent(apdContext.getAPDDeliveryContext().getAPDBroadcastMessage().getAPDCommonInfo().getDateTime());
        
        //setting sender             
        UserInfoType senderUserInfo = null;
        
        UserInfoDocument senderUserInfoDoc = userHelper.getSenderUserInfo(apdContext.getAPDDeliveryContext().getAPDBroadcastMessage());
        senderUserInfo = senderUserInfoDoc.getUserInfo();
        
        SenderDocument senderDoc = SenderDocument.Factory.newInstance();
        SenderDocument.Sender sender = senderDoc.addNewSender();
        sender.setCompanyCode(senderUserInfo.getOrgCode());
        sender.setOrgId(senderUserInfo.getOrgID());
        sender.setUserId(senderUserInfo.getUserID());
        sender.setFirstName(senderUserInfo.getFirstName());
        sender.setLastName(senderUserInfo.getLastName());
        sender.setCompanyName(getCompanyName(senderUserInfo));
        
        bcMessage.setSender(sender);
        
        //setting Receiver
        UserInfoType recipientUserInfo = null;
        
        UserInfoDocument recipientUserInfoDoc = getRecipientUserInfo(apdContext.getAPDDeliveryContext().getAPDBroadcastMessage());
        recipientUserInfo = recipientUserInfoDoc.getUserInfo();
        
        ReceiverDocument recDoc = ReceiverDocument.Factory.newInstance();
        ReceiverDocument.Receiver rec = recDoc.addNewReceiver();
        rec.setCompanyCode(recipientUserInfo.getOrgCode());
        rec.setOrgId(recipientUserInfo.getOrgID());
        rec.setUserId(recipientUserInfo.getUserID());
        
        bcMessage.setReceiver(rec);
        
        bcMessage.setSubject(apdContext.getAPDDeliveryContext().getAPDBroadcastMessage().getMessageSubject());
        bcMessage.setMessageBody(apdContext.getAPDDeliveryContext().getAPDBroadcastMessage().getMessageContent());
        bcMessage.setMimeType(MimeTypeDocument.MimeType.TXT);
        
        EnvelopeBodyContentType envBdContentTyp = null;
        envBdContentTyp = EnvelopeBodyContentType.Factory.parse(bcMsgDoc.toString());
              
        //adding content to envelop body
        envBodyType.setContent(envBdContentTyp);    
        
        }catch(MitchellException me){
            throw me; 
        }catch(Exception e){
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_IN_POPULATING_MITCHELL_ENVELOPE,
                        CLASS_NAME,
                        methodName,
                        apdContext.getAPDDeliveryContext().getAPDBroadcastMessage().getAPDCommonInfo().getWorkItemId(),
                        APDDeliveryConstants.ERROR_IN_POPULATING_MITCHELL_ENVELOPE_MSG
                            + "\n"
                            + AppUtilities.getStackTraceString(e));
        }
        logger.exiting(CLASS_NAME, methodName);
        return meDoc;      
        
    }
    
    /**
     * gets the company name of given user
     */
    private String getCompanyName(UserInfoType userInfo){
        
        String methodName = "getCompanyName";
        logger.entering(CLASS_NAME, methodName);
        
        String companyName = null;
         UserHierType userHier = userInfo.getUserHier();
         NodeType node1 = userHier.getHierNode();
         NodeType node2 = node1.getHierNode();
         NodeType node3 = node2.getHierNode();
         NodeType node4 = node3.getHierNode();
         
        if (node1.getLevel().equalsIgnoreCase("COMPANY")) {
            companyName = node1.getName();
        } else if (node2.getLevel().equalsIgnoreCase("COMPANY")) {
            companyName = node2.getName();
        } else if (node3.getLevel().equalsIgnoreCase("COMPANY")) {
            companyName = node3.getName();
        } else if (node4.getLevel().equalsIgnoreCase("COMPANY")) {
            companyName = node4.getName();
        }  
        
        logger.exiting(CLASS_NAME, methodName);
        return companyName;  
    }
    /**
     * Gets the NotificationHelper.
     *
     * @return NotificationHelper
     */
    public NotificationHelper getNotificationHelper() {
        return notificationHelper;
    }

    /**
     * Sets the NotificationHelper.
     *
     * @param notificationHelper
     */
    public void setNotificationHelper(final NotificationHelper notificationHelper) {
        this.notificationHelper = notificationHelper;
    }
    
    /**
     * Gets the BroadcastMessageProcessorProxy.
     *
     * @return BroadcastMessageProcessorProxy
     */
    public BroadcastMessageProcessorProxy getBroadcastMessageProcessorProxy() {
        return broadcastMessageProcessorProxy;
    }

    /**
     * Sets the BroadcastMessageProcessorProxy.
     *
     * @param broadcastMessageProcessorProxy
     */
    public void setBroadcastMessageProcessorProxy(
        final BroadcastMessageProcessorProxy broadcastMessageProcessorProxy) {
        this.broadcastMessageProcessorProxy = broadcastMessageProcessorProxy;
    }
    
    /**
     * Gets the CustomSettingHelper.
     *
     * @return CustomSettingHelper
     */
    public CustomSettingHelper getCustomSettingHelper() {
        return customSettingHelper;
    }

    /**
     * Sets the CustomSettingHelper.
     *
     * @param customSettingHelper
     */
    public void setCustomSettingHelper(final CustomSettingHelper customSettingHelper) {
        this.customSettingHelper = customSettingHelper;
    }
    
    /**
     * Gets the ECAlertProxy.
     *
     * @return ECAlertProxy
     */
    public ECAlertProxy getECAlertProxy() {
        return ecAlertProxy;
    }

    /**
     * Sets the ECAlertProxy.
     *
     * @param ecAlertProxy
     */
    public void setECAlertProxy(final ECAlertProxy ecAlertProxy) {
        this.ecAlertProxy = ecAlertProxy;
    }
    
    /**
     * Gets the APDCommonUtilProxy.
     *
     * @return APDCommonUtilProxy
     */
    public APDCommonUtilProxy getAPDCommonUtilProxy() {
        return apdCommonUtilProxy;
    }

    /**
     * Sets the APDCommonUtilProxy.
     *
     * @param apdCommonUtilProxy
     */
    public void setAPDCommonUtilProxy(final APDCommonUtilProxy apdCommonUtilProxy) {
        this.apdCommonUtilProxy = apdCommonUtilProxy;
    }
    
    /**
     * Gets the UserHelper.
     *
     * @return UserHelper
     */
    public UserHelper getUserHelper() {
        return userHelper;
    }

    /**
     * Sets the UserHelper.
     *
     * @param userHelper
     */
    public void setUserHelper(final UserHelper userHelper) {
        this.userHelper = userHelper;
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
    public void setAppLogHelper(final AppLogHelper appLogHelper) {
        this.appLogHelper = appLogHelper;
    }
    
    /**
     * Gets the SystemConfigurationProxy.
     *
     * @return the SystemConfigurationProxy
     */
    public SystemConfigurationProxy getSystemConfigurationProxy() {
        return systemConfigurationProxy;
    }

    /**
     * Sets the SystemConfigurationProxy.
     *
     * @param systemConfigurationProxy
     */
    public void setSystemConfigurationProxy(SystemConfigurationProxy systemConfigurationProxy) {
        this.systemConfigurationProxy = systemConfigurationProxy;
    }
    /**
     * Identifies whether given user is staff user or not
     * @param UserInfoType userInfo
     * @return boolean
     */
    
    private boolean isStaffUser(UserInfoType userInfo) 
                                    throws MitchellException {
        
        boolean isStaffUser = false;
        String userType = null;        
        
        userType = userHelper.getUserType(userInfo.getOrgCode(), 
                                            userInfo.getUserID()); 
        apdCommonUtilProxy.logFINEMessage("userType-"+userType);
        if (UserTypeConstants.STAFF_TYPE.equals(userType)){
            isStaffUser = true;
        }
        return isStaffUser;
    }
        
} 
