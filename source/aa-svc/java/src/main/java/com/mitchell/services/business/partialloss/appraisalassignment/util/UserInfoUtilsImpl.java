package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestConstants;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;
import com.mitchell.services.technical.partialloss.estimate.ejb.EstimatePackageRemote;

/**
 * 
 * Class responsible for User Info related Utilities
 * 
 */
public class UserInfoUtilsImpl implements UserInfoUtils {
    private static Logger logger = Logger.getLogger(UserInfoUtils.class.getName());
    private static final String CLASS_NAME = UserInfoUtils.class.getName();

    private UserInfoProxy userInfoProxy = null;
    private AASCommonUtils commonUtils = null;

    private IAppraisalAssignmentUtils appraisalAssignmentUtils ;
   


    public void setAppraisalAssignmentUtils(IAppraisalAssignmentUtils appraisalAssignmentUtils){
         this.appraisalAssignmentUtils = appraisalAssignmentUtils;
    }

    public void setCommonUtils(final AASCommonUtils commonUtils) {

        this.commonUtils = commonUtils;
    }

    public void setUserInfoProxy(final UserInfoProxy userInfoProxy) {
        this.userInfoProxy = userInfoProxy;
    }

    /**
     * This method is utility method which generates Unique WorkItemID. This
     * method is used for GETLATESTESTIMATE method only.
     */
    /*
     * Commented to resolve the codepro comments. private void
     * generateWorkItemID() {
     * 
     * this.workItemID = UUIDFactory.getInstance().getUUID();
     * this.logINFOMessage("Generated unique workItemId: " +this.workItemID); }
     */
    public UserInfoDocument getEstimatorInfo(final long estimateDocID) throws Exception {
        final String METHOD_NAME = "getEstimatorInfo";

        logger.entering(CLASS_NAME, METHOD_NAME);
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Input Received :: estimateDocID : " + estimateDocID);
        }
        Long orgIdOfEstimator = null;
        UserInfoDocument estimatorUserInfoDoc = null;

        try {
            /* Getting AssigneeID from the Original Estimate doc */
 
            Estimate est = new EstimatePackageClient().getEstimateAndDocByDocId(estimateDocID);

            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info("Fetched Estimate From getEstimateAndDocByDocId Of Estimate Package Service estimate doc ID :"
                        + estimateDocID);
            }
            if (est == null) {
                logger.severe("Error while retrieveing Estimate from estimatePkgRemote.getEstimateAndDocByDocId for Estimate Document ID : "

                        + estimateDocID);
                throw new MitchellException(CLASS_NAME, "getEstimatorInfo- EstimateDocumentID",
                        "Received NULL Estimate object from estimatePkgRemote.getEstimateAndDocByDocId for Estimate Document ID : "
                                + estimateDocID);
            }
            // Non-Staff (DRP value)
            if (est.getBusinessPartnerId() != null) {
                orgIdOfEstimator = est.getBusinessPartnerId();
                if (logger.isLoggable(java.util.logging.Level.INFO)) {
                    logger.info("Fetched orgIdOfEstimator from the estimate just fetched");
                }
            }// Staff
            else {
                final Document doc = est.getDocument();
                if (doc == null) {
                    logger.severe("Error while retrieveing Document from EstimatePackageService for Estimate Document ID : "
                            + estimateDocID);
                    throw new MitchellException(CLASS_NAME, "getEstimatorInfo - EstimateDocumentID",
                            "Received NULL Document object from EstimatePackageService for Estimate Document ID : "
                                    + estimateDocID);
                }
                orgIdOfEstimator = doc.getServiceProviderId();
            }
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
                logger.info("OrgIdOfEstimator is ::" + orgIdOfEstimator);
            }
            if (orgIdOfEstimator != null) {
                estimatorUserInfoDoc = retrieveUserInfo(orgIdOfEstimator.longValue());
            }
        } catch (final Exception ex) {
            logger.severe("Exception occured in " + METHOD_NAME + " while fetching estimator info for EstimateDocID"
                    + estimateDocID + "Exception" + ex.getMessage());
            throw ex;
        }

        logger.exiting(CLASS_NAME, METHOD_NAME);

        return estimatorUserInfoDoc;
    }

    /**
     * 
     * @param orgId
     *            - Organization Id
     * @return - UserInfoDocument
     * @throws MitchellException
     *             - Throws MitchellException to the caller in case of any
     *             exception arise.
     */
    public UserInfoDocument getUserInfoDoc(final long orgId) throws MitchellException {

        UserInfoDocument userInfoDoc = null;

        try {

            // UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
            userInfoDoc = userInfoProxy.getUserInfo(orgId);

            if (userInfoDoc == null) {

                final String desc = SupplementRequestConstants.ERR_NOSUCHUSER.getDescription() + " with orgId:" + orgId;
                commonUtils.logAndThrowError(SupplementRequestConstants.ERR_NOSUCHUSER.getCode(),
                        UserInfoUtils.class.getName(), "getUserInfoDoc", desc, null, logger);
            }
        } catch (final Exception e) {

            if (e instanceof MitchellException) {

                final MitchellException me = (MitchellException) e;
                throw me;

            } else {

                final String desc = "Reading user with org id:" + orgId + "\nwith error: " + e;
                commonUtils.logAndThrowError(AppraisalAssignmentConstants.ERROR_ASSIGN_AA_GETUSERINFO,
                        UserInfoUtils.class.getName(), "getUserInfoDoc", desc, e, logger);
            }
        }

        return userInfoDoc;
    }

    /**
     * This method used by retrieve Supplement Request Doc - returns
     * UserInfoDetailDoc
     * 
     * @param coCd
     *            companyCode of userInfoDoc requested
     * @param userId
     *            userId of userInfoDoc requested
     * @param methodName
     *            methodName of caller
     * @return Object of UserInfo Document.
     * 
     * @throws Exception
     *             Throws Exception to the caller in case of any exception
     *             arise.
     */
    public UserDetailDocument getUserDetailDoc(final String coCode, final String userId) throws Exception {
        final String METHOD_NAME = "getUserDetailDoc";
        logger.entering(CLASS_NAME, METHOD_NAME);

        StringBuffer localMethodParams = null;
        if (logger.isLoggable(Level.INFO)) {
            localMethodParams = new StringBuffer();
            localMethodParams.append("coCode: " + coCode).append("\n userId: " + userId);
        }

        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Input Received ::\n" + localMethodParams);
        }
        UserDetailDocument userDetailDoc = null;
        // UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
        userDetailDoc = userInfoProxy.getUserDetails(coCode, userId);

        if (userDetailDoc == null) {
            throw new MitchellException(SupplementRequestConstants.ERR_NOSUCHUSER.getCode(),
                    "AppraisalAssignmentService", METHOD_NAME,
                    SupplementRequestConstants.ERR_NOSUCHUSER.getDescription() + " coCode=" + coCode + " userId="
                            + userId);
        }
        try {
            if (userDetailDoc.getUserDetail() != null && userDetailDoc.getUserDetail().getPhone() != null) {
                String phone = userDetailDoc.getUserDetail().getPhone();
                final String PHONE_MATCHING_PATTERN = "((\\+\\d{1,5}-\\d{1,5}-)|(\\d{1,5}-)){0,1}([a-zA-Z0-9]){3,8}(\\+\\d{1,10}){0,1}";
                if (!phone.matches(PHONE_MATCHING_PATTERN) && phone.length() == 10) {
                    final StringBuffer phoneNew = new StringBuffer(phone);
                    phoneNew.insert(3, '-');
                    if (logger.isLoggable(java.util.logging.Level.INFO)) {
                        logger.info("New Phone::" + phoneNew);
                    }
                    phone = phoneNew.toString();
                    if (phone.matches(PHONE_MATCHING_PATTERN)) {
                        if (logger.isLoggable(java.util.logging.Level.INFO)) {
                            logger.info("Setting New Phone in userDetailDoc::" + phone);
                        }
                        userDetailDoc.getUserDetail().setPhone(phone);
                    }
                }
            }
        } catch (final Exception e) {
            logger.warning("Error occured converting the Phone Number in getUserDetailDoc()!!" + e);
        }
        logger.exiting(CLASS_NAME, METHOD_NAME);
        return userDetailDoc;
    }

    /**
     * 
     * @param estimatorOrgId
     *            - EstimatorOrgId
     * @return - UserInfoDocument
     * @throws MitchellException
     *             - Throws MitchellException to the caller in case of any
     *             exception arise.
     */
    public UserInfoDocument getUserInfoDocOfReviewerForEstimator(final long estimatorOrgId) throws MitchellException {

        UserInfoDocument userInfoDoc = null;

        try {

            // UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
            userInfoDoc = userInfoProxy.getReviewerForEstimators(estimatorOrgId);
        } catch (final Exception e) {

            final String desc = "Reading user with estimatorOrgId:" + estimatorOrgId + "\nwith error: " + e;
            commonUtils.logAndThrowError(AppraisalAssignmentConstants.ERROR_ASSIGN_AA_GETUSERINFO,
                    UserInfoUtils.class.getName(), "getUserInfoDocOfReviewerForEstimator", desc, e, logger);
        }

        return userInfoDoc;
    }

    /**
     * 
     * @param coCd
     *            - Company Code
     * @param orgCd
     *            - Organization Code
     * @param methodName
     *            - Method Name
     * @return - UserInfoDocument
     * @throws MitchellException
     *             - Throws MitchellException to the caller in case of any
     *             exception arise.
     */
    public UserInfoDocument getUserInfoDoc(final String coCd, final String orgCd, final String methodName)
            throws MitchellException {

        UserInfoDocument userInfoDoc = null;

        try {

            // UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
            userInfoDoc = userInfoProxy.getUserInfo(coCd, orgCd, methodName);

            if (userInfoDoc == null) {

                final String desc = SupplementRequestConstants.ERR_NOSUCHUSER.getDescription() + " with coCode/userId:"
                        + coCd + "/" + orgCd;
                commonUtils.logAndThrowError(SupplementRequestConstants.ERR_NOSUCHUSER.getCode(),
                        UserInfoUtils.class.getName(), "getUserInfoDoc", desc, null, logger);
            }

        } catch (final Exception e) {

            if (e instanceof MitchellException) {

                final MitchellException me = (MitchellException) e;
                throw me;

            } else {

                final String desc = "Reading user with coCode/userId:" + coCd + "/" + orgCd + "\nwith error: " + e;
                commonUtils.logAndThrowError(AppraisalAssignmentConstants.ERROR_ASSIGN_AA_GETUSERINFO,
                        UserInfoUtils.class.getName(), "getUserInfoDoc", desc, e, logger);
            }
        }

        return userInfoDoc;
    }

    /**
     * 
     * @param orgId
     *            - orgId
     * @return - UserInfoDocument
     * @throws MitchellException
     *             - Throws MitchellException to the caller in case of any
     *             exception arise.
     */
    public UserInfoDocument getSupervisorUserInfoDoc(final long orgId) throws MitchellException {
        UserInfoDocument userInfoDoc = null;

        try {
            // UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
            userInfoDoc = userInfoProxy.getSupervisorForReviewers(orgId);

        } catch (final Exception e) {

            final String desc = "Reading supervisor with userId:" + orgId + "\nwith error: " + e;
            commonUtils.logAndThrowError(AppraisalAssignmentConstants.ERROR_ASSIGN_AA_GETUSERINFO,
                    UserInfoUtils.class.getName(), "getSupervisorUserInfoDoc", desc, e, logger);
        }
        return userInfoDoc;
    }

    /*---------------- Changed 18/2011
    
    /**
     * This method calls UserInfo Service to get User's information.
     * 
     * @param companyCode
     *            User's company code.
     * @param userId
     *            User's Id
     * @return UserInfoDocument object.
     */
    public UserInfoDocument retrieveUserInfo(final String companyCode, final String userId) throws Exception {

        final String METHOD_NAME = "retrieveUserInfo";
        logger.entering(CLASS_NAME, METHOD_NAME);

        StringBuffer localMethodParams = null;
        if (logger.isLoggable(Level.INFO)) {
            localMethodParams = new StringBuffer();
            localMethodParams.append("companyCode: " + companyCode).append("\n userId: " + userId);
        }
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Input Received ::\n" + localMethodParams);
        }
        UserInfoDocument userInfoDoc = null;
        // UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
        userInfoDoc = userInfoProxy.getUserInfo(companyCode, userId, "");
        if (userInfoDoc == null) {
            throw new MitchellException(CLASS_NAME, "retrieveUserInfo",
                    "Received NULL user information document from UserInfo Service. CompanyCode : " + companyCode
                            + "\tUserID : " + userId);
        }

        logger.exiting(CLASS_NAME, METHOD_NAME);
        return userInfoDoc;
    }

    /**
     * This method calls UserInfo Service to get User's information.
     * 
     * @param OrgCode
     *            OrgCode
     * @return UserInfoDocument object.
     */
    public UserInfoDocument retrieveUserInfo(final long orgCode) throws Exception {
        final String METHOD_NAME = "retrieveUserInfo";
        logger.entering(CLASS_NAME, METHOD_NAME);
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Input Received :: orgCode: " + orgCode);
        }
        UserInfoDocument userInfoDoc = null;
        // UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
        userInfoDoc = userInfoProxy.getUserInfo(orgCode);
        if (userInfoDoc == null) {
            throw new MitchellException(CLASS_NAME, "retrieveUserInfo",
                    "Received NULL user information document from UserInfo Service. CompanyCode : " + orgCode);
        }

        logger.exiting(CLASS_NAME, METHOD_NAME);
        return userInfoDoc;
    }

    /*
     * Get UserType for a given Co Code and userId return null if user ID not
     * present in system or userType is SALVAGE� etc.It was static method before
     * changes hve done for userInfoProxy.getUserTypes(companyCode, userId)
     */
    public String getUserType(final String companyCode, final String userId) throws Exception {
        final String METHOD_NAME = "getUserType";
        logger.info("Inside public String getUserType(String companyCode, String userId) ");
        logger.entering(CLASS_NAME, METHOD_NAME);
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Input Received :: companyCode: " + companyCode + "\t userId: " + userId);
        }

        String userType = null;
        // UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
        userType = userInfoProxy.getUserTypes(companyCode, userId);
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Fetched User Type from UserInfo Service: " + userType);
        }
        logger.exiting(CLASS_NAME, METHOD_NAME);
        return userType;
    }

    /**
     * Retireve User Dispatch Center by Passing company code and user id
     * 
     * @param coCode
     * @param userID
     * @return dispatchCenter
     * @throws MitchellException
     *             Throws MitchellException to the caller in case of any
     *             exception arise.
     * 
     * 
     */
    public String retrieveUserDispatchCenter(final String coCode, final String userID) throws MitchellException {
        //final AppraisalAssignmentUtils appraisalAssignmentUtils = new AppraisalAssignmentUtils();
        String dispatchCenter = null;
        final String METHOD_NAME = "retrieveUserDispatchCenter";
        try {
            dispatchCenter = appraisalAssignmentUtils.retrieveUserDispatchCenter(coCode, userID);
        } catch (final Exception e) {
            if (e instanceof MitchellException) {
            } else {
                new MitchellException(CLASS_NAME, METHOD_NAME, "Error in Retrieving Dispatch Center.");
            }
        }
        return dispatchCenter;
    }

    /**
     * This is helper method which calls carrier specific custom settings to
     * retrieve schedule config for Staff Appraiser.
     * 
     * @return Y or N or NULL
     * @throws Exception
     */
    public String getScheduleFlagForStaff(final String orgCoCode) throws Exception {
        final String METHOD_NAME = "getScheduleFlagForStaff";
        logger.entering(CLASS_NAME, METHOD_NAME);
        String retval = null;
        //final AppraisalAssignmentUtils appraisalAssignmentUtils = new AppraisalAssignmentUtils();
        retval = appraisalAssignmentUtils.getConfigScheduleInd(orgCoCode, orgCoCode,
                AppraisalAssignmentConstants.CSET_GROUP_NAME_WCA_REQD_FLDS,
                AppraisalAssignmentConstants.CSET_SETTING_WCA_SCHED_REQD_STAFF);
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("Fetched schedule config for Staff Appraiser from AppraisalAssignmentUtils: " + retval);
        }
        logger.exiting(CLASS_NAME, METHOD_NAME);
        return retval;
    }

}