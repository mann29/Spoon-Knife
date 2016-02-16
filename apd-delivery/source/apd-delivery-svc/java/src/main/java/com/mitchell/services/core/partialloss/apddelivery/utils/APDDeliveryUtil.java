package com.mitchell.services.core.partialloss.apddelivery.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;

import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.services.core.eclaimalert.EClaimAlertRequestDocument;
import com.mitchell.services.technical.partialloss.estimate.bo.Attachment;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

public interface APDDeliveryUtil {

	/**
	 * This method determines delivery endpoint.
	 * 
	 * @param userInfo
	 * User Info of target
	 * @return boolean
	 * If delivery endpoint is Platform it returns true else returns false
	 */
	public abstract boolean isEndpointPlatform(UserInfoType userInfo);

	/**
	 * This method determines delivery endpoint.
	 * 
	 * @param baseAPDCommonType
	 * BaseAPDCommonType
	 * @return boolean
	 * If delivery endpoint is Platform it returns true else returns false
	 * @throws Exception    Exception
	 */
	public abstract boolean isEndpointPlatform2(
			BaseAPDCommonType baseAPDCommonType) throws Exception;

	/**
	 * Formats the given Date into a date/time string.
	 * 
	 * @param date
	 * Date to be formatted into date/time string
	 * @param fmtStr
	 * Format string e.g. yyyy-MM-dd'T'HH:mm:ss
	 * @return String
	 * Formatted date/time string
	 */
	public abstract String getDateStrForFormat(java.util.Date date,
			String fmtStr);

	/**
	 * This method determines if a user is a Shop User.
	 * <p>
	 * 
	 * @param userInfo
	 * User Info of Shop user
	 * @return boolean
	 * Returns true is this user is a Shop User,
	 * otherwise returns false
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract boolean isShopUser(UserInfoType userInfo)
			throws MitchellException;

	/**
	 * This method determines if a user is a Staff User.
	 * <p>
	 * 
	 * @param userInfo
	 * User Info of Staff user
	 * @return boolean
	 * Returns true is this user is a Shop User,
	 * otherwise returns false
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract boolean isStaffUser(UserInfoType userInfo)
			throws MitchellException;

	/**
	 * This method returns the User Type of given User.
	 * <p>
	 * 
	 * @param coCode
	 * User's Company Code
	 * @param userId
	 * User ID
	 * @return String
	 * User Type of the User
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract String getUserType(String coCode, String userId)
			throws MitchellException;

	/**
	 * This method return the UserInfo of a user 
	 * for given Company Code and User ID.
	 * <p>
	 * 
	 * @param companyCode
	 * Compnay code
	 * @param userID
	 * User ID
	 * @param uuId
	 * UUID
	 * @return UserInfoDocument
	 * User Info
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract UserInfoDocument getUserInfo(String companyCode,
			String userID, String uuId) throws MitchellException;

	/**
	 * This method return the UserInfo of a user 
	 * for given Company Code and User ID.
	 * <p>
	 * 
	 * @param orgId
	 * orgId
	 * @return UserInfoDocument
	 * User Info
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract UserInfoDocument getUserInfo(long orgId)
			throws MitchellException;

	/**
	 * This method validates values of following APDDeliveryContext properties
	 * <ul>
	 * <li>TargetDRPUserInfo
	 * <li>Notes
	 * <li>CorrectionNumber
	 * <li>MessageGlobalId
	 * </ul>
	 * It throws a MitchellException in case of one or more invalid properties.
	 * <p>
	 * 
	 * @param apdDeliveryDoc
	 * An object of type APDDeliveryContextDocument.
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void isAPDDeliveryContextValidForEstimate(
			APDDeliveryContextDocument apdDeliveryDoc) throws MitchellException;

	/**
	 * <p>
	 * This method receives an XML Bean object, validates it against schema
	 * and returns false if validation fails.
	 * </p>
	 * 
	 * @param xo
	 * XML to be validated
	 * @return boolean
	 * false if XML object is not validated
	 */
	public abstract boolean isValid(XmlObject xo);

	/**
	 * This method validates Message/Artifact Type.
	 * 
	 * @param artifactType
	 * The Message/Artifact Type
	 * @throws Exception
	 * Exception
	 */
	public abstract void isValidArtifactType(String artifactType)
			throws Exception;

	/**
	 * This method return the OrgInfo of a Company
	 * for given Company Code.
	 * <p>
	 * 
	 * @param insCoCode
	 * Company Code
	 * @return OrgInfoDocument
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract OrgInfoDocument getOrgInfo(String insCoCode)
			throws MitchellException;

	/**
	 * This method creates an Alert request.
	 * 
	 * @param apdContext
	 * An instance of APDDeliveryContextDocument
	 * @param userInfo
	 * Target user info
	 * @return EClaimAlertRequestDocument
	 */
	public abstract EClaimAlertRequestDocument createAlertRequest(
			APDDeliveryContextDocument apdContext, UserInfoType userInfo);

	/**
	 * This method populates an object of type APDDeliveryContext.
	 * 
	 * @param baseAPDCommonType
	 * BaseAPDCommonType
	 * @param map
	 * Key-value pairs for AppLog
	 * @param transactionType
	 * App code
	 * @return APDDeliveryContext
	 * Used for App Logging.
	 */
	public abstract APDDeliveryContext populateContextForAppLog(
			BaseAPDCommonType baseAPDCommonType, Map map, int transactionType);

	/**
	 * This method will return XZUserInfo.
	 * @param userInfo  UserInfoDocument
	 * @return UserInfoDocument UserInfoDocument
	 * @throws Exception    Exception
	 */
	public abstract UserInfoDocument getXZUserInfo(UserInfoDocument userInfo)
			throws MitchellException;

	/**
	 * This method will populate private index for alerts.
	 * @param coCode Company Code
	 * @param workItemId    workItemId
	 * @param workProcessUpdateMessageContext   workProcessUpdateMessageContext
	 * @throws Exception Exception
	 */
	public abstract void populatePrivateIndexForAlerts(String coCode,
			String workItemId,
			WorkProcessUpdateMessageContext workProcessUpdateMessageContext)
			throws Exception;

	/**
	 * This method will get private index based on WorkItemId and CompanyCode.
	 * @param coCode Company Code
	 * @param workItemId    workItemId
	 * @return privateIndex String privateIndex
	 * @throws Exception Exception
	 */
	public abstract String getPrivateIndexBasedOnCoCdAndWorkItemId(
			String coCode, String workItemId) throws Exception;

	/**
	 * This method will get private index based on estimateDocId and CompanyCode.
	 * @param coCode Company Code
	 * @param estimateDocId    Estimate Doc Id
	 * @return privateIndex String privateIndex
	 * @throws Exception Exception
	 */
	public abstract String getPrivateIndexBasedOnCoCdAndEstDocId(String coCode,
			long estimateDocId) throws Exception;

	/**
	 * This method retrieves a public WorkProcess Key.
	 * 
	 * @param clientClaimNumber
	 * Claim number
	 * @param coCode
	 * Company code that is associated with the key that is to be retrieved.
	 * @return String
	 * The Public WorkProcessKey or null if none found.
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract String getPublicIndex(String clientClaimNumber,
			String coCode) throws MitchellException;

	/**
	 * @param coCode
	 * @param workAsmtId
	 * @param assigneeOrgId
	 */
	public abstract String getPrivateIndexForRepairAssignment(String coCode,
			long workAsmtId, long assigneeOrgId) throws MitchellException;

	/**
	 * @param notificationid
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract String getPrivateIndexForApprAsmtNtfn(long notificationid)
			throws MitchellException;

	/**
	 * @param notificationid
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract void registerPrivateKeyForApprAsmtNtfn(long notificationid,
			long xzUserOrgId, String coCd, String privateIndex)
			throws MitchellException;

	/**
	 * This method is used to get user info with special permissions.
	 * @param coCode company code
	 * @return UserInfoDocument
	 */
	public abstract UserInfoDocument getClaimSystemUserInfoDoc(String coCode);

	/**
	 * @param disposition
	 */
	public abstract String getActivityOperation(String disposition,
			boolean isRework);

	/**
	 * 
	 */
	public abstract void registerPublicKey(String clientClaimNumber,
			String coCd, String publicIndex) throws MitchellException;

	/**
	 * 
	 */
	public abstract void registerPrivateKey(long taskId, long xzUserOrgId,
			String coCd, String privateIndex) throws Exception;

	/**
	 * @param estimate
	 * @return String
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract String getEstimateAuthorType(Estimate estimate)
			throws MitchellException;

	/**
	 * @param estDocId
	 * @return Estimate
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract Estimate getEstimate(long estDocId)
			throws MitchellException;

	/**
	 * @param estDocId
	 * @return Attachment[]
	 * @throws MitchellException
	 * Mitchell Exception
	 */
	public abstract Attachment[] getEstimateAttachments(long estDocId)
			throws MitchellException;

	/**
	 * This method determines whether end point is Staff or not.
	 * @param userInfoType UserInfoType 
	 * @returns boolean true if endpoint is Staff, false otherwise.
	 */
	public abstract boolean isEndPointStaff(UserInfoType userInfoType);

	/**
	 * This method returns a date/time string against 
	 * given Calendar object as per format.
	 * Format examples:
	 * "MM/dd/yyyy"
	 * "MM/dd/yyyy hh:mm:ss a"
	 * 
	 * @param calendarDate
	 * Calendar object
	 * @param format
	 * Format string
	 * @return String
	 * date/time string
	 */
	public abstract String calendarToString(Calendar calendarDate, String format);

	/**
	 * Utility method to convert java.util.Date to java.util.Calendar.
	 * If format is provided, returned Calendar will be as per the format.
	 * 
	 * @param date - java.util.Date
	 * @param format
	 * @return java.util.Calendar
	 * @throws ParseException
	 */
	public abstract Calendar dateToCalendar(java.util.Date date, String format)
			throws ParseException;


    /**
     * This method determines delivery endpoint for Appraisal Assignment/ Alert Delivery.
     *
     * @param baseAPDCommonType
     * BaseAPDCommonType
     * @return boolean
     * If delivery endpoint is RCWeb it returns true else returns false
     * @throws Exception
     */
    public abstract boolean isEndpointRCWeb(
            BaseAPDCommonType baseAPDCommonType) throws Exception;

    /**
	 * This method determines delivery endpoint for Appraisal Assignment/ Alert Delivery.
	 * 
	 * @param baseAPDCommonType
	 * BaseAPDCommonType
	 * @return boolean
	 * If delivery endpoint is Platform it returns true else returns false
	 * @throws Exception
	 */
	public abstract boolean isEndpointPlatformWithOverride(
			BaseAPDCommonType baseAPDCommonType) throws Exception;
	
	/**
	 * This method returns true if Shop User.
	 * @param userType
	 * @return boolean
	 * @throws MitchellException
	 */
	public boolean isShopUser(String userType) throws MitchellException;
	
	/**
	 * This method returns true if staff user.
	 * @param userType
	 * @return boolean
	 * @throws MitchellException
	 */
	public boolean isStaffUser(String userType) throws MitchellException;
	
	/**
	 * This method returns true if IA user.
	 * @param userType
	 * @return boolean
	 * @throws MitchellException
	 */
	public boolean isIAUser(String userType) throws MitchellException;
	
	/**
	 * This method returns userInfoDocument from APDDeliveryContextDocument.
	 * @param apdDeliveryContextDocument
	 * @return UserInfoDocument
	 */
	public UserInfoDocument getUserInfoDocumentFromAPDContext(
			APDDeliveryContextDocument apdDeliveryContextDocument);
	
	/**
	 * This method checks if an application code exists in a UserInfo from BaseAPDCommonType.
	 * @param baseAPDCommonType
	 * @param applicationCode
	 * @return boolean
	 */
	public boolean evaluateUserInfoApplicationCode(
			BaseAPDCommonType baseAPDCommonType, String applicationCode);

}