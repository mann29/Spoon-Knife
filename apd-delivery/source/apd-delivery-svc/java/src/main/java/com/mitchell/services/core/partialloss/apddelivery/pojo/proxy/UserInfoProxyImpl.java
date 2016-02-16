package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.utils.misc.AppUtilities;
import java.util.logging.Logger;

/**
 * The Class UserInfoProxyImpl.
 */
public class UserInfoProxyImpl implements UserInfoProxy {

	/**
	 * class name.
	 */
	private static final String CLASS_NAME = UserInfoProxyImpl.class.getName();
	/**
	 * logger instance.
	 */
	private static Logger logger = Logger.getLogger(CLASS_NAME);

	/**
	 * Gets the user info.
	 * 
	 * @param orgId
	 *            the org id
	 * @return the user info
	 * @throws MitchellException
	 *             the mitchell exception
	 */
	public UserInfoDocument getUserInfo(final long orgId)
			throws MitchellException {
		String methodName = "getUserInfo";
		logger.entering(CLASS_NAME, methodName);
		
		UserInfoDocument userInfoDoc = null;

		final UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();

		try {
			userInfoDoc = ejb.getUserInfo(orgId);
		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with orgId:")
					.append(orgId).append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getUserInfo", desc, e);
		}
		logger.exiting(CLASS_NAME, methodName);
		return userInfoDoc;
	}

	/**
	 * Gets the user info.
	 * 
	 * @param coCd
	 *            the co cd
	 * @param userId
	 *            the user id
	 * @return the user info
	 * @throws MitchellException
	 *             the mitchell exception
	 */
	public UserInfoDocument getUserInfo(String coCd, String userId)
			throws MitchellException {
		String methodName = "getUserInfo";
		logger.entering(CLASS_NAME, methodName);
		
		UserInfoDocument userInfoDoc = null;

		final UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();

		try {
			userInfoDoc = ejb.getUserInfo(coCd, userId, (String) null);
		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with coCd:")
					.append(coCd).append(",userId:").append(userId)
					.append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getUserInfo", desc, e);
		}
		logger.exiting(CLASS_NAME, methodName);
		return userInfoDoc;
	}

	/**
	 * Gets the user detail.
	 * 
	 * @param orgId
	 *            the org id
	 * @return the user detail
	 * @throws MitchellException
	 *             the mitchell exception
	 */
	public UserDetailDocument getUserDetail(long orgId)
			throws MitchellException {
		String methodName = "getUserDetail";
		logger.entering(CLASS_NAME, methodName);
		
		UserDetailDocument userDetailDoc = null;

		final UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();

		try {
			userDetailDoc = ejb.getUserDetail(orgId);
		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with orgId:")
					.append(orgId).append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getUserDetail", desc, e);
		}
		logger.exiting(CLASS_NAME, methodName);
		return userDetailDoc;
	}

	/**
	 * Gets the user detail.
	 * 
	 * @param coCd
	 *            the co cd
	 * @param userId
	 *            the user id
	 * @return the user detail
	 * @throws MitchellException
	 *             the mitchell exception
	 */
	public UserDetailDocument getUserDetail(String coCd, String userId)
			throws MitchellException {
		String methodName = "getUserDetail";
		logger.entering(CLASS_NAME, methodName);
				
		UserDetailDocument userDetailDoc = null;

		final UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();

		try {
			userDetailDoc = ejb.getUserDetail(coCd, userId);
		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with coCd:")
					.append(coCd).append(",userId:").append(userId)
					.append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getUserDetail", desc, e);
		}
		logger.exiting(CLASS_NAME, methodName);
		return userDetailDoc;
	}

	/**
	 * returns the userinfo of associated shop or IA
	 * 
	 * @param orgId
	 *            org id of the buinesspartner
	 * @return UserInfoDocument userinfo of the shop or IA
	 * @throws MitchellException
	 *             Mitchell Exception
	 */
	public UserInfoDocument getAssociatedShopUserInfo(final long orgId)
			throws MitchellException {

		String methodName = "getAssociatedShopUserInfo";
		logger.entering(CLASS_NAME, methodName);

		UserInfoDocument userInfoDoc = null;

		final UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();

		try {

			CrossOverUserInfoDocument crossOverUserInfoDoc = ejb
					.getCrossOverUserInfoByOrgID(orgId);
			userInfoDoc = ejb.getUserInfo(crossOverUserInfoDoc
					.getCrossOverUserInfo().getNonStaffInfo()
					.getNonStaffOrgID());

		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with orgId:")
					.append(orgId).append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getAssociatedShopUserInfo", desc, e);
		}
		logger.exiting(CLASS_NAME, methodName);
		return userInfoDoc;
	}

	/**
	 * This method returns the User Type of given User.
	 * <p>
	 * 
	 * @param coCode
	 *            User's Company Code
	 * @param userId
	 *            User ID
	 * @return String User Type of the User
	 * @throws MitchellException
	 *             Mitchell Exception
	 */
	public String getUserType(String coCode, String userId)
			throws MitchellException {

		String methodName = "getUserType";
		logger.entering(CLASS_NAME, methodName);

		String userType = null;

		try {
			UserInfoServiceEJBRemote remote = UserInfoClient.getUserInfoEJB();
			userType = remote.getUserType(coCode, userId);
		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with coCd:")
					.append(coCode).append(",userId:").append(userId)
					.append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getUserType", desc, e);
		}

		logger.exiting(CLASS_NAME, methodName);
		return userType;
	}

	public UserInfoDocument getUserInfo(String coCode, String userId,
			String uuId) throws MitchellException {
		String methodName = "getUserInfo";
		logger.entering(CLASS_NAME, methodName);

		UserInfoDocument userInfoDoc = null;
		try {
			UserInfoServiceEJBRemote remote = UserInfoClient.getUserInfoEJB();
			userInfoDoc = remote.getUserInfo(coCode, userId, uuId);

		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with coCd:")
					.append(coCode).append(",userId:").append(userId)
					.append(",uuId:").append(uuId).append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getUserInfo", desc, e);
		}

		logger.exiting(CLASS_NAME, methodName);

		return userInfoDoc;
	}

	public OrgInfoDocument getOrgInfo(String insCoCode, String coCode)
			throws MitchellException {
		String methodName = "getOrgInfo";
		logger.entering(CLASS_NAME, methodName);

		OrgInfoDocument orgInfoDoc = null;
		try {
			UserInfoServiceEJBRemote remote = UserInfoClient.getUserInfoEJB();
			orgInfoDoc = remote.getOrgInfo(insCoCode, coCode,
					UserInfoClient.ORG_TYPE_COMPANY);

		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with insCoCode:")
					.append(insCoCode).append(",coCode:").append(coCode)
					.append(",ORG_TYPE_COMPANY:")
					.append(UserInfoClient.ORG_TYPE_COMPANY)
					.append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getOrgInfo", desc, e);
		}

		logger.exiting(CLASS_NAME, methodName);

		return orgInfoDoc;
	}

	public CrossOverUserInfoDocument getCrossOverUserInfoByOrgID(long orgId)
			throws MitchellException {
		String methodName = "getCrossOverUserInfoByOrgID";
		logger.entering(CLASS_NAME, methodName);

		CrossOverUserInfoDocument ciDoc = null;
		try {
			UserInfoServiceEJBRemote remote = UserInfoClient.getUserInfoEJB();
			ciDoc = remote.getCrossOverUserInfoByOrgID(orgId);

		} catch (Exception e) {
			final String desc = new StringBuffer(
					"RemoteException calling UserInfoService with orgId:")
					.append(orgId).append(",with stacktrace:")
					.append(AppUtilities.getStackTraceString(e, true))
					.toString();

			throw new MitchellException(
					APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
					"getCrossOverUserInfoByOrgID", desc, e);
		}

		logger.exiting(CLASS_NAME, methodName);

		return ciDoc;
	}



    @Override
    public String getCompanyCode(String shopUserOrgId, String workItemId) throws MitchellException {
        CrossOverUserInfoDocument userInfoDocument = getCrossOverUserInfoByOrgID(Long.parseLong(shopUserOrgId));

        try{
            return userInfoDocument
                    .getCrossOverUserInfo()
                    .getOnlineInfo()
                    .getOnlineOffice()
                    .getOnlineOfficeCoCode();

        } catch (Exception e) {
            final String desc = new StringBuffer("Error parsing through UserInfoDocument for:").append(",with stacktrace:")
                    .append(AppUtilities.getStackTraceString(e, true)).toString();
            throw new MitchellException(
                    APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
                    "getCompanyCode", desc, e);
        }
    }

    @Override
    public String getOrgCode(String shopUserOrgId, String workItemId) throws MitchellException {
        CrossOverUserInfoDocument userInfoDocument = getCrossOverUserInfoByOrgID(Long.parseLong(shopUserOrgId));

        try{
            return userInfoDocument
                    .getCrossOverUserInfo()
                    .getOnlineInfo()
                    .getOnlineOffice()
                    .getOnlineOfficeOrgCode();

        } catch (Exception e) {
            final String desc = new StringBuffer("Error parsing through UserInfoDocument for:").append(",with stacktrace:")
                    .append(AppUtilities.getStackTraceString(e, true)).toString();
            throw new MitchellException(
                    APDDeliveryConstants.ERROR_USER_INFO_SERVICE, CLASS_NAME,
                    "getOrgCode", desc, e);
        }
    }




}
