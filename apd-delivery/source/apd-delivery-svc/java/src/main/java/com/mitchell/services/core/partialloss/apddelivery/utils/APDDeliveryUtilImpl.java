package com.mitchell.services.core.partialloss.apddelivery.utils; 

// Fix 97154 : Replace obsolete imports. 
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.NodeType;
import com.mitchell.common.types.OnlineInfoType;
import com.mitchell.common.types.OnlineUserType;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserHierType;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDAlertInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDEstimateStatusInfoType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.eclaimalert.EClaimAlertRequestDocument;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ECAlertProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.EstimatePackageProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.UserInfoProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.WorkProcessServiceProxy;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.services.core.workprocessservice.WPKeyRequestDocument;
import com.mitchell.services.core.workprocessservice.WPKeyRequestType;
import com.mitchell.services.schemas.mcf.MCFPackageTyp;
import com.mitchell.services.technical.partialloss.estimate.bo.Attachment;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.utils.misc.AppUtilities;
/**
 * 
 * @version %I%, %G%
 * @since 1.0
 */
public class APDDeliveryUtilImpl implements APDDeliveryUtil { 
    
    /**
     * class name.
     */
    private static final String CLASS_NAME = APDDeliveryUtilImpl.class.getName();
    
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private UserInfoProxy userInfoProxy;
    
    private WorkProcessServiceProxy workProcessServiceProxy;
    
    private EstimatePackageProxy estimatePackageProxy;
    
    private ECAlertProxy ecAlertProxy;
    
    private SystemConfigurationProxy systemConfigurationProxy;
    
    private APDCommonUtilProxy apdCommonUtilProxy;

    /**
     * default constructor.
     * Fix 117663
     */
    public APDDeliveryUtilImpl() { }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isEndpointPlatform(com.mitchell.common.types.UserInfoType)
	 */
    public boolean isEndpointPlatform(UserInfoType userInfo) {
        
        String methodName = "isEndpointPlatform";
        logger.entering(CLASS_NAME, methodName);
        
        boolean isPlatform = false;
        String[] appCodeArray = null;
        // check the app code from userInfo
        appCodeArray = userInfo.getAppCodeArray();
        for (int i = 0; i < appCodeArray.length; i++) {
            if (systemConfigurationProxy.getAppCode().
                                        equalsIgnoreCase(appCodeArray[i])) {
                isPlatform = true;
                break;
            }
        }
        
        logger.exiting(CLASS_NAME, methodName);
        
        return isPlatform;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isEndpointPlatform2(com.mitchell.schemas.apddelivery.BaseAPDCommonType)
	 */
    public boolean isEndpointPlatform2(BaseAPDCommonType baseAPDCommonType) throws Exception {
        
        String methodName = "isEndpointPlatform2";
        logger.entering(CLASS_NAME, methodName);
        
        boolean isPlatform = false;
        String[] appCodeArray = null;
        UserInfoDocument targetUserInfo = null;
        UserInfoDocument targetDRPUserInfo = null;
        // populate target user info
        UserInfoType targetUserInfoType = baseAPDCommonType.getTargetUserInfo().getUserInfo();
        targetUserInfo = UserInfoDocument.Factory.newInstance();
        targetUserInfo.setUserInfo(targetUserInfoType);
        
        // populate DRP target user info
        if (baseAPDCommonType.isSetTargetDRPUserInfo()) {
            UserInfoType targetDRPUserInfoType = baseAPDCommonType.getTargetDRPUserInfo().getUserInfo();
            targetDRPUserInfo = UserInfoDocument.Factory.newInstance();
            targetDRPUserInfo.setUserInfo(targetDRPUserInfoType);
        }
        
        apdCommonUtilProxy.logINFOMessage(" ^^^^^ Before checking targetDRPUserInfo ... ");
        
        if (targetDRPUserInfo == null) {
            isPlatform = isEndPointStaff(targetUserInfoType);
            return isPlatform;            
        } else {
            
        	apdCommonUtilProxy.logINFOMessage(" ^^^^^ About to get XZ UserInfo  ... ");
            
            // get the XZUserInfo based on targetUserInfo
            UserInfoDocument xzUserInfo = getXZUserInfo(targetUserInfo);
            apdCommonUtilProxy.logINFOMessage(" ^^^^^ Got XZ UserInfo  ... \n " + xzUserInfo);
            
            if (xzUserInfo != null) {
                // check the app code from userInfo
                appCodeArray = xzUserInfo.getUserInfo().getAppCodeArray();
                for (int i = 0; i < appCodeArray.length; i++) {
                	if (systemConfigurationProxy.getAppCode().equalsIgnoreCase(appCodeArray[i])) {
                        isPlatform = true;
                        break;
                    }
                }                            
            }
        }
        
        logger.exiting(CLASS_NAME, methodName);
        
        return isPlatform;
    }    
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getDateStrForFormat(java.util.Date, java.lang.String)
	 */
    public String getDateStrForFormat(
                                    java.util.Date date, String fmtStr) {
        String methodName = "getDateStrForFormat";
        logger.entering(CLASS_NAME, methodName);
        String dateStr = null;
        SimpleDateFormat fmt = new SimpleDateFormat(fmtStr);
        dateStr = fmt.format(date);
        
        logger.exiting(CLASS_NAME, methodName);
        return dateStr;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isShopUser(com.mitchell.common.types.UserInfoType)
	 */
    public boolean isShopUser(UserInfoType userInfo) 
                                                throws MitchellException {
        
        String methodName = "isShopUser";
        logger.entering(CLASS_NAME, methodName);
        
        boolean isShopUser = false;
        String userType = null;
        
        userType = getUserType(userInfo.getOrgCode(), 
                                            userInfo.getUserID()); 
        
        if (UserTypeConstants.SHOP_TYPE.equals(userType) 
                        || UserTypeConstants.DRP_SHOP_TYPE.equals(userType)) {
            isShopUser = true;
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return isShopUser;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isStaffUser(com.mitchell.common.types.UserInfoType)
	 */
    public boolean isStaffUser(UserInfoType userInfo) 
                                                throws MitchellException {
        
        String methodName = "isStaffUser";
        logger.entering(CLASS_NAME, methodName);
        
        boolean isStaffUser = false;
        String userType = null;
        
        userType = getUserType(userInfo.getOrgCode(), 
                                            userInfo.getUserID()); 
        
        if (UserTypeConstants.STAFF_TYPE.equals(userType) ) {
            isStaffUser = true;
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return isStaffUser;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getUserType(java.lang.String, java.lang.String)
	 */
    public String getUserType(String coCode, String userId) 
                                                throws MitchellException {
        
        String methodName = "getUserType";
        logger.entering(CLASS_NAME, methodName);
        
        String userType = null;
        
        try {
        	userType = userInfoProxy.getUserType(coCode, userId);
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_USER_INFO_SERVICE,
                        CLASS_NAME,
                        methodName,
                        null,
                        APDDeliveryConstants.ERROR_USER_INFO_SERVICE_MSG
                            + "\n" 
                            + AppUtilities.getStackTraceString(e, true));
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return userType;
    }
    
    
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getUserInfo(java.lang.String, java.lang.String, java.lang.String)
	 */
    public UserInfoDocument getUserInfo(String companyCode,
                                                String userID,
                                                String uuId)
                                                throws MitchellException {
        String methodName = "getUserInfo";
        logger.entering(CLASS_NAME, methodName);
        
        UserInfoDocument userInfo = null;
        
        try {
            userInfo = userInfoProxy.getUserInfo(companyCode, userID, uuId);
            if (userInfo == null) {
                throw new Exception(
                        "Cannot find UserInfo for " 
                            + companyCode 
                            + " - " 
                            + userID);                
            }
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_USER_INFO_SERVICE,
                        CLASS_NAME,
                        methodName,
                        null,
                        APDDeliveryConstants.ERROR_USER_INFO_SERVICE_MSG
                            + "\n" 
                            + AppUtilities.getStackTraceString(e, true));
        }
        logger.exiting(CLASS_NAME, methodName);
        return userInfo;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getUserInfo(long)
	 */
    public UserInfoDocument getUserInfo(long orgId)
                                                throws MitchellException {
        String methodName = "getUserInfo";
        logger.entering(CLASS_NAME, methodName);
        
        UserInfoDocument userInfo = null;
        
        try {
            userInfo = userInfoProxy.getUserInfo(orgId);
            if (userInfo == null) {
                throw new Exception(
                        "Cannot find UserInfo for " 
                            + orgId);                
            }
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_USER_INFO_SERVICE,
                        CLASS_NAME,
                        methodName,
                        null,
                        APDDeliveryConstants.ERROR_USER_INFO_SERVICE_MSG
                            + "\n" 
                            + AppUtilities.getStackTraceString(e, true));
        }
        logger.exiting(CLASS_NAME, methodName);
        return userInfo;
    }    
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isAPDDeliveryContextValidForEstimate(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
    public void isAPDDeliveryContextValidForEstimate(
                                    APDDeliveryContextDocument apdDeliveryDoc)
                                    throws MitchellException {
        String methodName = "isAPDDeliveryContextValidForBaseAPDCommonType";
        logger.entering(CLASS_NAME, methodName);
        
        StringBuffer msg = new StringBuffer();
        
        APDEstimateStatusInfoType estStatusInfoType = 
            apdDeliveryDoc.getAPDDeliveryContext().getAPDEstimateStatusInfo();
        //fetching the BaseAPDCommonType from i/p
        BaseAPDCommonType baseAPDCommonType = 
            apdDeliveryDoc.getAPDDeliveryContext()
                .getAPDEstimateStatusInfo().getAPDCommonInfo();
        
        if (baseAPDCommonType.getTargetDRPUserInfo() ==  null 
                || "".equals(baseAPDCommonType.getTargetDRPUserInfo()
                                                        .toString().trim())) {
            msg.append("TargetDRPUserInfo is null or empty");
        } else if (estStatusInfoType.getEstimateStatusEvent() == null
                || "".equals(estStatusInfoType.getEstimateStatusEvent().toString().trim())) {
           msg.append("Estimate Status is null or empty"); 
        } else if (estStatusInfoType.getAPDCommonInfo().getClaimNumber() == null 
                || "".equals(estStatusInfoType.getAPDCommonInfo().getClaimNumber().trim())) {
           msg.append("Claim Number is null or empty");    
        } else if (estStatusInfoType.getAPDCommonInfo().getInsCoCode() == null 
                || "".equals(estStatusInfoType.getAPDCommonInfo().getInsCoCode().trim())) {
           msg.append("Company code is null or empty");
        }     
             
        if (msg.length() > 0) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_INSUFFICIENT_ESTIMATE_INPUT, 
                        CLASS_NAME, 
                        methodName, 
                        estStatusInfoType.getAPDCommonInfo().getWorkItemId(), 
                        APDDeliveryConstants.ERROR_INSUFFICIENT_ESTIMATE_INPUT_MSG
                            + "\n" 
                            + msg.toString());
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isValid(org.apache.xmlbeans.XmlObject)
	 */
    public boolean isValid(XmlObject xo) {
        
        String methodName = "isValid";
        logger.entering(CLASS_NAME, methodName);
		// Commented for Manoj's Testing....
        /* 
        boolean isValid = false;
        
      	if (xo == null) {
            return false;    
        }
        
        XmlOptions options = new XmlOptions();
        ArrayList schemaValidationErrors = new ArrayList();
        options.setErrorListener(schemaValidationErrors);
        
        isValid = xo.validate(options);
        
        if (!isValid) {
            
            logger.info(" XMLDocument is invalid !!!! ");
            
            for (int i = 0; i < schemaValidationErrors.size(); i++) {
                XmlError error = (XmlError) schemaValidationErrors.get(i);
                
                logger.info(" Message " + error.getMessage());
                logger.info(" Location of invalid XML " + error.getCursorLocation().xmlText());
            }
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return isValid; */ 
		return true;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isValidArtifactType(java.lang.String)
	 */
    public void isValidArtifactType(String artifactType) 
                                                            throws Exception {
        
        String methodName = "isValidArtifactType";
        logger.entering(CLASS_NAME, methodName);
        
        if (!(APDDeliveryConstants.ALERT_ARTIFACT_TYPE.equalsIgnoreCase(artifactType) 
                || APDDeliveryConstants.ESTIMATE_STATUS_ARTIFACT_TYPE
                                                .equalsIgnoreCase(artifactType) 
                || APDDeliveryConstants.SUPPLEMENT_ARTIFACT_TYPE
                                                .equalsIgnoreCase(artifactType) 
                || APDDeliveryConstants.REPAIR_ASSIGNMENT_ARTIFACT_TYPE
                                                .equalsIgnoreCase(artifactType) 
                || APDDeliveryConstants.REWORK_ASSIGNMENT_ARTIFACT_TYPE
                                                .equalsIgnoreCase(artifactType) 
                || APDDeliveryConstants.ORIGINAL_ESTIMATE_ARTIFACT_TYPE
                                                .equalsIgnoreCase(artifactType)
                || APDDeliveryConstants.REQUEST_STAFF_SUPPLEMENT_ARTIFACT_TYPE
                                                .equalsIgnoreCase(artifactType)
                || APDDeliveryConstants.APPR_ASMT_NTFN_ARTIFACT_TYPE
                                                .equalsIgnoreCase(artifactType)
                || APDDeliveryConstants.BROADCAST_MESSAGE_TYPE
                                                .equalsIgnoreCase(artifactType)
				|| APDDeliveryConstants.NICB_REPORT_TYPE
                                                .equalsIgnoreCase(artifactType))) {
            throw new Exception(
                        APDDeliveryConstants.ERROR_INVALID_ARTIFACT_TYPE_MSG);
        }
        
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getOrgInfo(java.lang.String)
	 */
    public OrgInfoDocument getOrgInfo(String insCoCode) 
                                                throws MitchellException {
        
        String methodName = "getOrgInfo";
        logger.entering(CLASS_NAME, methodName);
        
        OrgInfoDocument orgInfo = null;
        
        try {
            orgInfo = userInfoProxy.getOrgInfo(
                    insCoCode,
                    insCoCode);
            
            if (orgInfo == null) {
                throw new Exception(" Cannot find OrgInfo for " + insCoCode);                
            }
        } catch (Exception e) {
            throw new MitchellException(
                        APDDeliveryConstants.ERROR_USER_INFO_SERVICE,
                        CLASS_NAME,
                        methodName,
                        null,
                        APDDeliveryConstants.ERROR_USER_INFO_SERVICE_MSG
                            + "\n" 
                            + AppUtilities.getStackTraceString(e, true));
        }        
        logger.exiting(CLASS_NAME, methodName);
        return orgInfo;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#createAlertRequest(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument, com.mitchell.common.types.UserInfoType)
	 */
    public EClaimAlertRequestDocument createAlertRequest(
                                    APDDeliveryContextDocument apdContext, 
                                    UserInfoType userInfo) {
        
        String methodName = "getOrgInfo";
        logger.entering(CLASS_NAME, methodName);
        
        APDAlertInfoType apdAlertInfoType = 
                            apdContext.getAPDDeliveryContext().getAPDAlertInfo();
                            
        String origin = apdAlertInfoType.getOrigin();
        String message = apdAlertInfoType.getMessage();
        String workItemId = apdAlertInfoType.getAPDCommonInfo().getWorkItemId();
        String coCode = userInfo.getOrgCode();
        String userId = userInfo.getUserID();
        MCFPackageTyp.Enum mcfPackagetype = 
            MCFPackageTyp.Enum.forString(
                            apdAlertInfoType.getMcfPackageType().toString());
        EClaimAlertRequestDocument doc = ecAlertProxy.createAlertRequest(
                coCode,
                userId,
                origin,
                message,
                workItemId,
                apdAlertInfoType.getEclaimEstId(),
                Integer.parseInt(apdAlertInfoType.getSupplementNumber()),
                Integer.parseInt(apdAlertInfoType.getCorrectionNumber()),
                Long.toString(apdAlertInfoType.getFolderAI()),
                apdAlertInfoType.getMcfFileName(),
                mcfPackagetype);
        
        if (apdAlertInfoType.isSetEstimateDate()) {
            doc.getEClaimAlertRequest().setEstimateDate(trimCommitDate(
                apdAlertInfoType.getEstimateDate()));
        }
        
        apdCommonUtilProxy.logINFOMessage("The Created AlertReqDoc is : " + doc);
        apdCommonUtilProxy.logINFOMessage("The Created apdContext is : " + apdContext);
        
        
        logger.exiting(CLASS_NAME, methodName);
        return doc;
    }
    
        
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#populateContextForAppLog(com.mitchell.schemas.apddelivery.BaseAPDCommonType, java.util.Map, int)
	 */
    public APDDeliveryContext populateContextForAppLog(
                                        BaseAPDCommonType baseAPDCommonType,
                                        Map map,
                                        int transactionType) {
        
        String methodName = "populateContextForAppLog";
        logger.entering(CLASS_NAME, methodName);
                
        APDDeliveryContext context = new APDDeliveryContext();
        
        context.setClaimNumber(baseAPDCommonType.getClaimNumber());
        context.setClaimId(baseAPDCommonType.getClaimId());
        context.setSuffixId(baseAPDCommonType.getSuffixId());
        context.setWorkItemId(baseAPDCommonType.getWorkItemId());
        context.setTransactiontype(Integer.toString(transactionType)); 
        context.setUserId(baseAPDCommonType.getSourceUserInfo().getUserInfo().getUserID());
        context.setMap(map);    
           
        logger.exiting(CLASS_NAME, methodName);        
        return context; 
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getXZUserInfo(com.mitchell.common.types.UserInfoDocument)
	 */
    public UserInfoDocument getXZUserInfo(UserInfoDocument userInfo) throws MitchellException 
    {
        
        String methodName = "getXZUserInfo";
        logger.entering(CLASS_NAME, methodName);
        
        CrossOverUserInfoDocument ciDoc = null;
        UserInfoDocument xzUserInfo = null;
        
        // Fix 97154 : Remove Try-Catch as RemoteException is not thrown anymore.
        ciDoc = userInfoProxy.getCrossOverUserInfoByOrgID(Long.parseLong(userInfo.getUserInfo().getOrgID()));
                 
        apdCommonUtilProxy.logINFOMessage(" ^^^^^ Got CrossOver DOc  ... \n " + ciDoc);
        
        if (ciDoc != null) {

            OnlineInfoType onlineInfo = null;
            OnlineUserType[] onlineUsers = null;

            onlineInfo = ciDoc.getCrossOverUserInfo().getOnlineInfo();
            if (onlineInfo != null) {
                onlineUsers = onlineInfo.getOnlineOffice().getOnlineUsersArray();
                
                OnlineUserType onlineUser = null;
                if (onlineUsers != null && onlineUsers.length > 0) {
                    for (int i = 0; i < onlineUsers.length; i++) {
                        if (onlineUsers[i].getOnlineUserOrgCode().isSetIsPrimaryUser()
                            && onlineUsers[i].getOnlineUserOrgCode().getIsPrimaryUser()) {
                            onlineUser = onlineUsers[i];
                            break;        
                        }
                    }
                }        
                
                if (onlineUser == null) {
                    
                	apdCommonUtilProxy.logINFOMessage("Could not get online user for org id: "
                                        + userInfo.getUserInfo().getOrgID());
                } else {
                    long xzOrgId = onlineUser.getOnlineUserOrgID();
                    xzUserInfo = getUserInfo(xzOrgId);    
                }            
            }
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return xzUserInfo;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#populatePrivateIndexForAlerts(java.lang.String, java.lang.String, com.mitchell.services.core.partialloss.apddelivery.utils.WorkProcessUpdateMessageContext)
	 */
    public void populatePrivateIndexForAlerts(
        String coCode,
        String workItemId,
        WorkProcessUpdateMessageContext workProcessUpdateMessageContext
        ) throws Exception {
    	
    	WPKeyRequestDocument keyDoc = workProcessServiceProxy.initWPKeyRequest(workProcessServiceProxy.CTX_PRIVATE_WORKITEMID);
        keyDoc.getWPKeyRequest().setWorkItemId(workItemId);
        String privateKey = workProcessServiceProxy.retrieveWorkProcessKey(coCode, keyDoc);
        // set wp update message
        if (privateKey != null && privateKey.length() > 0) {
            workProcessUpdateMessageContext.setPrivateIndex(privateKey);
        } else {
            throw new RuntimeException("Could not get private key based on CoCd =" + coCode 
                + " and keydoc - " + keyDoc);
        }
        
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getPrivateIndexBasedOnCoCdAndWorkItemId(java.lang.String, java.lang.String)
	 */
    public String getPrivateIndexBasedOnCoCdAndWorkItemId(String coCode,
                                                                 String workItemId)
                                                                        throws Exception {
                                                    
        String methodName = "getPrivateIndexBasedOnCoCdAndWorkItemId";
        
        logger.entering(CLASS_NAME, methodName);
        WPKeyRequestDocument keyDoc = workProcessServiceProxy
				.initWPKeyRequest(workProcessServiceProxy.CTX_PRIVATE_WORKITEMID);

		keyDoc.getWPKeyRequest().setWorkItemId(workItemId);

		String privateKey = workProcessServiceProxy.retrieveWorkProcessKey(coCode, keyDoc);
            
        // set wp update message
        if (privateKey != null && privateKey.length() > 0) {
            
            return privateKey;
        
        } else {
            
            throw new RuntimeException("Could not get private key based on CoCd =" + coCode 
                + " and keydoc - " + keyDoc);
        }
        
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getPrivateIndexBasedOnCoCdAndEstDocId(java.lang.String, long)
	 */
    public String getPrivateIndexBasedOnCoCdAndEstDocId(String coCode,
                                                                 long estimateDocId)
                                                                        throws Exception {
                                                    
        String methodName = "getPrivateIndexBasedOnCoCdAndWorkItemId";
        
        logger.entering(CLASS_NAME, methodName);
        
        WPKeyRequestDocument keyDoc = workProcessServiceProxy
				.initWPKeyRequest(workProcessServiceProxy.CTX_PRIVATE_ESTIMATE_DOCID);

		keyDoc.getWPKeyRequest().setDocumentId(estimateDocId);

		String privateKey = workProcessServiceProxy.retrieveWorkProcessKey(coCode, keyDoc);    
        // set wp update message
        if (privateKey != null && privateKey.length() > 0) {
            
            return privateKey;
        
        } else {
            
            throw new RuntimeException("Could not get private key based on CoCd =" + coCode 
                + " and keydoc - " + keyDoc);
        }
        
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getPublicIndex(java.lang.String, java.lang.String)
	 */
    public String getPublicIndex(
                                    String clientClaimNumber, 
                                    String coCode) 
                                    throws MitchellException {
                                        
        String methodName = "getPublicIndex";
        logger.entering(CLASS_NAME, methodName);
        // Populate WPKeyRequestDocument
        WPKeyRequestDocument keyDoc = workProcessServiceProxy.initWPKeyRequest(
        		workProcessServiceProxy.CTX_PUBLIC_CLIENT_CLAIM_NUMBER);
        
        keyDoc.getWPKeyRequest().setClientClaimNumber(clientClaimNumber);
            
        String publicKey = workProcessServiceProxy.retrieveWorkProcessKey(coCode, keyDoc);
        logger.exiting(CLASS_NAME, methodName);
        
        return publicKey;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getPrivateIndexForRepairAssignment(java.lang.String, long, long)
	 */
    public String getPrivateIndexForRepairAssignment(
                                                    String coCode,
                                                    long workAsmtId,
                                                    long assigneeOrgId) 
                                                    throws MitchellException {
            
        String methodName = "getPrivateIndexForRepairAssignment";
        logger.entering(CLASS_NAME, methodName);
        // Fix 117663
        WPKeyRequestDocument keyDoc = workProcessServiceProxy.initWPKeyRequest(
        		workProcessServiceProxy.CTX_PRIVATE_REPAIR_ASSIGNMENT);
            
            keyDoc.getWPKeyRequest().setWorkAssignmentId(workAsmtId);
            keyDoc.getWPKeyRequest().setOrgId(assigneeOrgId);
                
            String privateKey = workProcessServiceProxy.retrieveWorkProcessKey(coCode, keyDoc);
        logger.exiting(CLASS_NAME, methodName);
        
        return privateKey;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getPrivateIndexForApprAsmtNtfn(long)
	 */
    public String getPrivateIndexForApprAsmtNtfn(long notificationid) 
                                                    throws MitchellException {
            
        String methodName = "getPrivateIndexForApprAsmtNtfn";
        logger.entering(CLASS_NAME, methodName);
        // Fix 117663
        
        WPKeyRequestDocument keyDoc = workProcessServiceProxy.initWPKeyRequest(
        		workProcessServiceProxy.CTX_PRIVATE_RC_NOTIFICATION_ID);
        keyDoc.getWPKeyRequest().setReferenceId(notificationid);
            
        String privateKey = workProcessServiceProxy.retrieveWorkProcessKey(Long.toString(notificationid), keyDoc);
        logger.exiting(CLASS_NAME, methodName);
        
        return privateKey;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#registerPrivateKeyForApprAsmtNtfn(long, long, java.lang.String, java.lang.String)
	 */
    public void registerPrivateKeyForApprAsmtNtfn(
                                                    long notificationid, 
                                                    long xzUserOrgId, 
                                                    String coCd, 
                                                    String privateIndex) 
                                                    throws MitchellException {
            
        String methodName = "registerPrivateKeyForApprAsmtNtfn";
        logger.entering(CLASS_NAME, methodName);
        
        WPKeyRequestDocument keyDoc = WPKeyRequestDocument.Factory.newInstance();
        WPKeyRequestType wpKey = keyDoc.addNewWPKeyRequest();
        
        wpKey.setContextIdentifier(workProcessServiceProxy.CTX_PRIVATE_RC_NOTIFICATION_ID);
        wpKey.setOrgId(xzUserOrgId);
        wpKey.setReferenceId(notificationid);
        
        logger.exiting(CLASS_NAME, methodName);
        workProcessServiceProxy.registerWorkProcessKey(
                            coCd, 
                            keyDoc, 
                            privateIndex);                    
        logger.exiting(CLASS_NAME, methodName);
    }
    
    /**
     * This method trims date.
     * @param calendar  calendar
     * @return Calendar Calendar
     */
    private Calendar trimCommitDate(Calendar calendar) {
        if (calendar != null) {
            calendar.clear(Calendar.MILLISECOND);
            calendar.clear(Calendar.ZONE_OFFSET);    
        }  
        return calendar;  
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getClaimSystemUserInfoDoc(java.lang.String)
	 */
    public UserInfoDocument getClaimSystemUserInfoDoc(String coCode) {

        if (coCode != null) {

        // Create a System User Info Document needed to call certain claim service methods

        UserInfoDocument doc = UserInfoDocument.Factory.newInstance();

        UserInfoType userInfo = doc.addNewUserInfo();

        userInfo.setUserID("System");

        userInfo.setFirstName("System");   

        userInfo.setLastName("User");

        userInfo.setOrgCode(coCode);

        UserHierType userHier = userInfo.addNewUserHier();

        NodeType node = userHier.addNewHierNode();

        node.setLevel("Level");

        node.setCode(coCode);

        String [] permissions = {"CMALL", "CMCRC", "CMEDC", "CMEDE", "CMCRD"};

        if (permissions != null) {

            for (int i = 0; i < permissions.length; i++) {

            userInfo.addAppCode(permissions[i]);

            }

        }        

        return doc;

        } else {

            return null;

        }

    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getActivityOperation(java.lang.String, boolean)
	 */
    public String getActivityOperation(String disposition,boolean isRework) {
        
        String methodName = "getActivityOperation";
        logger.entering(CLASS_NAME, methodName);
        
        String activityOperation = null;
        
        if (APDDeliveryConstants.RA_MESSAGE_STATUS_UPDATE.equals(disposition)) {
            if(isRework) {
                activityOperation = APDDeliveryConstants.ACTIVITY_OPERATION_UPDATE_REWORK;
            }
            else {
            activityOperation = APDDeliveryConstants.ACTIVITY_OPERATION_UPDATE_RA;
            }
            
        } else if (APDDeliveryConstants.RA_MESSAGE_STATUS_CANCEL.equals(disposition)) {
            if(isRework) {
                activityOperation = APDDeliveryConstants.ACTIVITY_OPERATION_CANCEL_REWORK;
            }
            else {
            activityOperation = APDDeliveryConstants.ACTIVITY_OPERATION_CANCEL_RA;
            }            
        } else if (APDDeliveryConstants.RA_MESSAGE_STATUS_COMPLETE.equals(disposition)) {
            
            if(isRework) {
                activityOperation = APDDeliveryConstants.ACTIVITY_OPERATION_COMPLETE_REWORK;
            }
            else {
            activityOperation = APDDeliveryConstants.ACTIVITY_OPERATION_COMPLETE_RA;
        }
        }
        
        logger.exiting(CLASS_NAME, methodName);
        return activityOperation;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#registerPublicKey(java.lang.String, java.lang.String, java.lang.String)
	 */
    public void registerPublicKey(
                                    String clientClaimNumber, 
                                    String coCd, 
                                    String publicIndex) 
                                    throws MitchellException {
        // Fix 117663
        
    	WPKeyRequestDocument keyDoc = WPKeyRequestDocument.Factory.newInstance();
        
        WPKeyRequestType wpKey = keyDoc.addNewWPKeyRequest();
        
        wpKey.setContextIdentifier(workProcessServiceProxy.CTX_PUBLIC_CLIENT_CLAIM_NUMBER);
        wpKey.setClientClaimNumber(clientClaimNumber);
        workProcessServiceProxy.registerWorkProcessKey(
                                    coCd,
                                    keyDoc, 
                                    publicIndex);
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#registerPrivateKey(long, long, java.lang.String, java.lang.String)
	 */
    public void registerPrivateKey(
                                long taskId, 
                                long xzUserOrgId, 
                                String coCd, 
                                String privateIndex) 
                                throws Exception {
    	// Fix 117663
       
    	 WPKeyRequestDocument keyDoc = WPKeyRequestDocument.Factory.newInstance();
         
         WPKeyRequestType wpKey = keyDoc.addNewWPKeyRequest();
         
         wpKey.setContextIdentifier(workProcessServiceProxy.CTX_PRIVATE_REPAIR_ASSIGNMENT);
         
         wpKey.setWorkAssignmentId(taskId);
         
         wpKey.setOrgId(xzUserOrgId);
         
         workProcessServiceProxy.registerWorkProcessKey(
                             coCd, 
                             keyDoc, 
                             privateIndex);
    }
   
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getEstimateAuthorType(com.mitchell.services.technical.partialloss.estimate.bo.Estimate)
	 */
    public String getEstimateAuthorType(Estimate estimate) 
                                                    throws MitchellException {
                    
        String methodName = "getEstimateAuthorType";
        logger.entering(CLASS_NAME, methodName);  
        
        String estimateAuthorType = null;
        UserInfoDocument userInfo = null;
    
        // Fix 97154 : Remove Try-Catch as RemoteException is not thrown anymore.
            
            if(estimate!= null ) {
                if (estimate.getBusinessPartnerId() == null) {
                    estimateAuthorType = "STAFF";
                } else {
                    // Fix 117663 : using userInfoProxy for UserInfoClient
                    userInfo = userInfoProxy.getUserInfo(estimate.getBusinessPartnerId().longValue());
                    String userType = userInfoProxy.getUserType(
                                                userInfo.getUserInfo().getOrgCode(), 
                                                userInfo.getUserInfo().getUserID());
        
                    if (userType == null) {
                        // throws mitchell exception that estimate uploader is not found.
                        throw new MitchellException(
                                            CLASS_NAME, 
                                            methodName, 
                                            "estimate uploader is not found ????");
                    }   
                    estimateAuthorType = userType;
                }
            }           
        
        return estimateAuthorType; 
    }    
    
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getEstimate(long)
	 */
    public Estimate getEstimate(long estDocId) 
                                                    throws MitchellException {
                    
        String methodName = "getEstimate";
        logger.entering(CLASS_NAME, methodName);  
        
        Estimate estimate = null;
    
       
        	// Fix 97154 : Remove Try-Catch as RemoteException is not thrown anymore.
        	// Fix 97154 : get EstimatePackageClient using new instead of estimatePackageRemote.
        	// Fix 117663
        	// get the document
            estimate = estimatePackageProxy.getEstimateAndDocByDocId(
                estDocId, true);    
                
            if(!(estimate!=null && estimate.getEstimateXmlEstimate()!=null &&
                estimate.getEstimateXmlEstimate().getXmlData()!=null )) {
                // throws mitchell exception that estimate not found.
                throw new MitchellException(
                                    CLASS_NAME, 
                                    methodName, 
                                    "Cannot get Estimate for docid " + estDocId);
            }           
        
        return estimate; 
    }    
    
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getEstimateAttachments(long)
	 */
    public Attachment[] getEstimateAttachments(long estDocId) 
                                                    throws MitchellException {
                    
        String methodName = "getEstimateAttachments";
        logger.entering(CLASS_NAME, methodName);  
        
        Estimate estimate = null;
        Attachment[] attachments = null;
    
     
        	
        	estimate = estimatePackageProxy.getEstimateAndDocByDocId(estDocId, true);    
            // get the attachments            
            Document doc = estimatePackageProxy.findDocumentByDocIdWithRelated(Long.valueOf(estDocId));
            if (doc != null && doc.getAttachments() != null) {
            	Set<Attachment> attachmentSet =  doc.getAttachments();
            	attachments = (Attachment[])attachmentSet.toArray(new Attachment[attachmentSet.size()]);            	
            }
                
            if(!(attachments!=null && attachments.length > 0)) {
                // throws mitchell exception that estimate not found.
                throw new MitchellException(
                                    CLASS_NAME, 
                                    methodName, 
                                    "Cannot get Attachments for docid " + estDocId);
            }         
        
        return attachments; 
    }     
    
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isEndPointStaff(com.mitchell.common.types.UserInfoType)
	 */
    public boolean isEndPointStaff(UserInfoType userInfoType) {
        
        String methodName = "isEndPointStaff";
        
        logger.entering(CLASS_NAME, methodName);
        
        boolean isEndPointStaff = false;
        
        String[] appCodeArray = null;
        
        // check the app code from userInfo
        appCodeArray = userInfoType.getAppCodeArray();
        
        if (appCodeArray != null) {
        
            for (int i = 0; i < appCodeArray.length; i++) {
            	// Fix 117663
                if (systemConfigurationProxy.getAppCodeForStaff().
                                            equalsIgnoreCase(appCodeArray[i])) {
                                                
                    isEndPointStaff = true;
                    
                    break;
                }
            }
        }
        
        logger.exiting(CLASS_NAME, methodName);
        
        return isEndPointStaff;
    
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#calendarToString(java.util.Calendar, java.lang.String)
	 */
    public String calendarToString(Calendar calendarDate, 
                                            String format) {
        String strdate = null;
        
        if (format != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (calendarDate != null) {
                strdate = sdf.format(calendarDate.getTime());
            }
        }
        return strdate;
    }
    
    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#dateToCalendar(java.util.Date, java.lang.String)
	 */
    public Calendar dateToCalendar(java.util.Date date, String format) 
                                                            throws ParseException {
        Calendar cal=Calendar.getInstance();
        
        if (format != null) {
            DateFormat df = new SimpleDateFormat(format) ;
            String dateStr = df.format(date);
            apdCommonUtilProxy.logINFOMessage("dateStr: " + dateStr);
            date = (java.util.Date)df.parse(dateStr);
        }
        
        cal.setTime(date);
        return cal;
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isEndpointRCWeb(com.mitchell.schemas.apddelivery.BaseAPDCommonType)
	 */
    public boolean isEndpointRCWeb(BaseAPDCommonType baseAPDCommonType) throws Exception {

        String methodName = "isEndpointRCWeb";
        this.logger.entering(CLASS_NAME, methodName);

        this.apdCommonUtilProxy.logINFOMessage("Determining if endpoint is RCWeb..." + baseAPDCommonType);

        if(!baseAPDCommonType.isSetTargetDRPUserInfo() || baseAPDCommonType.getTargetUserInfo() == null) {
            this.logger.exiting(CLASS_NAME, methodName);
            this.apdCommonUtilProxy.logINFOMessage("Either TargetDRPUserInfo or TargetUserInfo is not set (null)");
            return false;
        }

        UserInfoType targetDRPUserInfo = baseAPDCommonType.getTargetDRPUserInfo().getUserInfo();
        UserInfoType targetUserInfo = baseAPDCommonType.getTargetUserInfo().getUserInfo();

        this.apdCommonUtilProxy.logINFOMessage("TargetUserInfo is:" + targetUserInfo + "TargetDRPUserInfo is:" + targetDRPUserInfo);

        //Checking if user has App Code to ignore RC Connect & Comms
        boolean shouldUserIgnoreRcConnectAndComms = hasAppCode(targetUserInfo, APDDeliveryAppCodeConstants.IGNORE_RCCC_APP_CODE);
        this.apdCommonUtilProxy.logINFOMessage("Boolean value of shouldUserIgnoreRcConnectAndComms:" + shouldUserIgnoreRcConnectAndComms);

        //Checking if shop has App Code to prefer RC Web
        boolean isShopLicensedForRcWeb = hasAppCode(targetDRPUserInfo, APDDeliveryAppCodeConstants.PREFER_RCWEB_APP_CODE);
        this.apdCommonUtilProxy.logINFOMessage("Boolean value of isShopLicensedForRCWeb:" + isShopLicensedForRcWeb);

        this.logger.exiting(CLASS_NAME, methodName);
        return isShopLicensedForRcWeb && shouldUserIgnoreRcConnectAndComms;
    }

    private boolean hasAppCode(UserInfoType userInfoType, String appCodeToCheckFor) {

        String methodName = "hasAppCode";
        this.logger.entering(CLASS_NAME, methodName);

        String[] appCodes = userInfoType.getAppCodeArray();

        if(appCodes == null || appCodes.length == 0) {
            this.logger.exiting(CLASS_NAME, methodName);
            return false;
        }

        for (int i = 0; i < appCodes.length; i++) {
            this.apdCommonUtilProxy.logINFOMessage("User has app code:" + appCodes[i]);
            if (appCodeToCheckFor.equalsIgnoreCase(appCodes[i])) {
                this.logger.exiting(CLASS_NAME, methodName);
                return true;
            }
        }

        this.logger.exiting(CLASS_NAME, methodName);
        return false;
    }

    /* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isEndpointPlatformWithOverride(com.mitchell.schemas.apddelivery.BaseAPDCommonType)
	 */
    public boolean isEndpointPlatformWithOverride(BaseAPDCommonType baseAPDCommonType) throws Exception {
        
        String methodName = "isEndpointPlatformWithOverride";
        logger.entering(CLASS_NAME, methodName);
        
        boolean endPointPlatform = false;
        boolean ignoreEClaimRoute = false;
        UserInfoDocument targetDRPUserInfo = null;        
        // populate DRP target user info
        if (baseAPDCommonType.isSetTargetDRPUserInfo()) {
            UserInfoType targetDRPUserInfoType = baseAPDCommonType.getTargetDRPUserInfo().getUserInfo();
            targetDRPUserInfo = UserInfoDocument.Factory.newInstance();
            targetDRPUserInfo.setUserInfo(targetDRPUserInfoType);
        }  
        endPointPlatform = isEndpointPlatform2(baseAPDCommonType);
        ignoreEClaimRoute = evaluateIgnoreEClaimRoute(baseAPDCommonType);
        // Check if OverrideRCEndPointEnabled is true in set file.
        if (endPointPlatform && targetDRPUserInfo != null  && !ignoreEClaimRoute) {
        	// Fix 117663
            if(APDDeliveryConstants.TRUE.equalsIgnoreCase(systemConfigurationProxy.getOverrideRCEndPointEnabled()) &&
            targetDRPUserInfo.getUserInfo()!= null && targetDRPUserInfo.getUserInfo().getAppCodeArray()!= null) {                
                
            	apdCommonUtilProxy.logINFOMessage("Fetching AppCode from Set file for routing Assignments to eClaim Instead of RC.");
                String appCodeRCOverride = systemConfigurationProxy.getAppCodeRCOverride();
                String[] appCodeArray = targetDRPUserInfo.getUserInfo().getAppCodeArray();
                if (appCodeArray != null && appCodeArray.length > 0 && appCodeRCOverride != null) {
                	// check the app code from userInfo
                    for (int i = 0; i < appCodeArray.length; i++) {                        
                        if (appCodeRCOverride.equalsIgnoreCase(appCodeArray[i])) {
                            // Route Assignments to eClaim Instead of RC
                        	apdCommonUtilProxy.logINFOMessage("Route Assignments to eClaim instead of RC.");                            
                            endPointPlatform = false;
                            break;
                        }
                    }
                }
                                        
             } else {
                return endPointPlatform;                                   
            }            
        }
        logger.exiting(CLASS_NAME, methodName);
        
        return endPointPlatform;
    }
    
	/* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isShopUser(java.lang.String)
	 */
	public boolean isShopUser(String userType) throws MitchellException {
		String methodName = "isShopUser";
		logger.entering(CLASS_NAME, methodName);

		boolean isShopUser = false;
		if (UserTypeConstants.SHOP_TYPE.equals(userType)
				|| UserTypeConstants.DRP_SHOP_TYPE.equals(userType)) {
			isShopUser = true;
		}

		logger.exiting(CLASS_NAME, methodName);
		return isShopUser;
	}
    
    /* (non-Javadoc)
     * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isStaffUser(java.lang.String)
     */
    public boolean isStaffUser(String userType) throws MitchellException {
		String methodName = "isStaffUser";
		logger.entering(CLASS_NAME, methodName);

		boolean isStaffUser = false;
		if (UserTypeConstants.STAFF_TYPE.equals(userType)) {
			isStaffUser = true;
		}

		logger.exiting(CLASS_NAME, methodName);
		return isStaffUser;
	}
	
	/* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#isIAUser(java.lang.String)
	 */
	public boolean isIAUser(String userType) throws MitchellException {
		String methodName = "isIAUser";
		logger.entering(CLASS_NAME, methodName);

		boolean isIAUser = false;
		if (UserTypeConstants.IA_TYPE.equals(userType)
				|| UserTypeConstants.DRP_IA_TYPE.equals(userType)) {
			isIAUser = true;
		}

		logger.exiting(CLASS_NAME, methodName);
		return isIAUser;
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil#getUserInfoDocumentFromAPDContext(com.mitchell.schemas.apddelivery.APDDeliveryContextDocument)
	 */
	public UserInfoDocument getUserInfoDocumentFromAPDContext(
			APDDeliveryContextDocument apdDeliveryContextDocument) {
		final UserInfoDocument userInfoDocument = UserInfoDocument.Factory
				.newInstance();
		userInfoDocument.setUserInfo(apdDeliveryContextDocument
				.getAPDDeliveryContext().getAPDAppraisalAssignmentInfo()
				.getAPDCommonInfo().getTargetUserInfo().getUserInfo());
		return userInfoDocument;
	}

    /**
	 * This method evaluates if EClaim route needs to be ignored.
	 * @param baseAPDCommonType
	 * @return
	 */
	private boolean evaluateIgnoreEClaimRoute(BaseAPDCommonType baseAPDCommonType) {
		
		boolean ignoreEClaimRoute = false;
		UserInfoDocument targetUserInfo = null;
		if (baseAPDCommonType.getTargetUserInfo() != null) {
			// populate target user info
	        UserInfoType targetUserInfoType = baseAPDCommonType.getTargetUserInfo().getUserInfo();
	        targetUserInfo = UserInfoDocument.Factory.newInstance();
	        targetUserInfo.setUserInfo(targetUserInfoType);
			String[] appCodeArray = targetUserInfo.getUserInfo().getAppCodeArray();
			String eClaimRouteAppCode = systemConfigurationProxy.getIgnoreEclaimRoute();
			if (appCodeArray != null && appCodeArray.length > 0 && eClaimRouteAppCode != null) {
				for (int i = 0; i < appCodeArray.length; i++) {
		            if (eClaimRouteAppCode.equalsIgnoreCase(appCodeArray[i])) {
		                // Route Assignments to RC.
		            	apdCommonUtilProxy.logINFOMessage("Ignore Routing Artifact to eClaim.");	                
		            	ignoreEClaimRoute = true;
		                break;
		            }
		        }				
			}	        			
		}	
		return ignoreEClaimRoute;
	}

	/**
	 * This method evaluates if a particular Application Code exists in UserInfo.
	 * @param baseAPDCommonType
	 * @return
	 */
	public boolean evaluateUserInfoApplicationCode(BaseAPDCommonType baseAPDCommonType, String applicationCode) {
		
		boolean appCodeExists = false;
		UserInfoDocument targetUserInfo = null;
		if (baseAPDCommonType.getTargetUserInfo() != null) {
			// populate target user info
	        UserInfoType targetUserInfoType = baseAPDCommonType.getTargetUserInfo().getUserInfo();
	        targetUserInfo = UserInfoDocument.Factory.newInstance();
	        targetUserInfo.setUserInfo(targetUserInfoType);
			String[] appCodeArray = targetUserInfo.getUserInfo().getAppCodeArray();			
			if (appCodeArray != null && appCodeArray.length > 0 && applicationCode != null) {
				for (int i = 0; i < appCodeArray.length; i++) {
		            if (applicationCode.equalsIgnoreCase(appCodeArray[i])) {		                
		            	apdCommonUtilProxy.logINFOMessage("UserInfo contains - " + applicationCode);	                
		            	appCodeExists = true;
		                break;
		            }
		        }				
			}	        			
		}	
		return appCodeExists;
	}
	/**
	 * @return the userInfoProxy
	 */
	public UserInfoProxy getUserInfoProxy() {
		return userInfoProxy;
	}

	/**
	 * @param userInfoProxy the userInfoProxy to set
	 */
	public void setUserInfoProxy(UserInfoProxy userInfoProxy) {
		this.userInfoProxy = userInfoProxy;
	}

	/**
	 * @return the workProcessServiceProxy
	 */
	public WorkProcessServiceProxy getWorkProcessServiceProxy() {
		return workProcessServiceProxy;
	}

	/**
	 * @param workProcessServiceProxy the workProcessServiceProxy to set
	 */
	public void setWorkProcessServiceProxy(
			WorkProcessServiceProxy workProcessServiceProxy) {
		this.workProcessServiceProxy = workProcessServiceProxy;
	}

	/**
	 * @return the estimatePackageProxy
	 */
	public EstimatePackageProxy getEstimatePackageProxy() {
		return estimatePackageProxy;
	}

	/**
	 * @param estimatePackageProxy the estimatePackageProxy to set
	 */
	public void setEstimatePackageProxy(EstimatePackageProxy estimatePackageProxy) {
		this.estimatePackageProxy = estimatePackageProxy;
	}

	/**
	 * @return the ecAlertProxy
	 */
	public ECAlertProxy getEcAlertProxy() {
		return ecAlertProxy;
	}

	/**
	 * @param ecAlertProxy the ecAlertProxy to set
	 */
	public void setEcAlertProxy(ECAlertProxy ecAlertProxy) {
		this.ecAlertProxy = ecAlertProxy;
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
