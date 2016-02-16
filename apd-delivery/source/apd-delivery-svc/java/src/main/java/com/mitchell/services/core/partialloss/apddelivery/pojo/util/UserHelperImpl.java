package com.mitchell.services.core.partialloss.apddelivery.pojo.util; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.apddelivery.APDBroadcastMessageType;
import com.mitchell.schemas.apddelivery.APDRecipientsListType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.SystemConfigurationProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.UserInfoProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryUtil;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.ApdJdbcDao;

import java.util.logging.Logger;


/**
 * The Class UserHelperImpl.
 * 
 * @see UserHelper
 */
public class UserHelperImpl implements UserHelper { 
    
    
    public static final String CLASS_NAME =  UserHelperImpl.class.getName();
	public static final String APPCODE_ESTIMATOR = "CMAPES";
	public static final String APPDELIVERY_BASIC_APPCODE="/APDDeliveryService/ShopBasicAppCode";
	public static final String APPDELIVERY_PREMIUM_APPCODE="/APDDeliveryService/ShopPremiumAppCode";
    
     /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
    
    private UserInfoProxy userInfoProxy;
    
    private SystemConfigurationProxy systemConfigurationProxy;
    
    private APDDeliveryUtil apdUtil;
	
	private ApdJdbcDao apdJdbcDao;
    
    /**
     * @param bMsg
     * APDBroadcastMessageType object
     * @return UserInfoDocument
     * @throws MitchellException
     * Mitchell Exception
     */
    public UserInfoDocument getRecipientUserInfo(
                                            final APDBroadcastMessageType bMsg)
                                            throws MitchellException {
    	String methodName = "getRecipientUserInfo";
        logger.entering(CLASS_NAME, methodName);
        UserInfoDocument userInfo = null;
        APDRecipientsListType recListType = bMsg.getRecipientsList();
        long[] recList = recListType.getRecipientOrgIdArray();
        if (recList.length > 0) {
            userInfo = userInfoProxy.getUserInfo(recList[0]);
        }
        logger.exiting(CLASS_NAME, methodName);
        return userInfo;
    }
    
    public UserInfoDocument getSenderUserInfo(final APDBroadcastMessageType bMsg)
                                            throws MitchellException{
        
        UserInfoDocument userInfo = null;        
        userInfo = userInfoProxy.getUserInfo(bMsg.getSenderOrgId());
        
        return userInfo;
                                                
    }
   /**
     * returns the userinfo of associated shop or IA
     * @param orgId
     * org id of the buinesspartner
     * @return UserInfoDocument
     *  userinfo of the shop or IA
     *  @throws MitchellException
     *  Mitchell Exception
     */ 
    public UserInfoDocument getAssociatedShopUserInfo(final long orgId)
                                            throws MitchellException {
        String methodName = "getAssociatedShopUserInfo";
        logger.entering(CLASS_NAME, methodName);
        
        UserInfoDocument userInfoDoc = userInfoProxy.getAssociatedShopUserInfo(orgId);
        
        logger.exiting(CLASS_NAME, methodName);
        return userInfoDoc;
        
    }
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
    public String getUserType(String coCode, String userId) 
                                    throws MitchellException {
        String methodName = "getUserType";
        logger.entering(CLASS_NAME, methodName);
        
        String userType = null;
        
        userType = userInfoProxy.getUserType(coCode,userId);
        
        logger.exiting(CLASS_NAME, methodName);
        return userType;                                            
    }
    /**
     * Gets the UserInfoProxy.
     *
     * @return UserInfoProxy
     */
    public UserInfoProxy getUserInfoProxy() {
        return userInfoProxy;
    }

    /**
     * Sets the UserInfoProxy.
     *
     * @param userInfoProxy
     */
    public void setUserInfoProxy(final UserInfoProxy userInfoProxy) {
        this.userInfoProxy = userInfoProxy;
    }
    
	public void setDao(ApdJdbcDao dao) {
        this.apdJdbcDao = dao;
    }

    /* (non-Javadoc)
     * @see com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelper#isNonNetworkShop(java.lang.String)
     */
    public boolean isNonNetworkShop(String orgId) throws MitchellException {
        
        final String flag = apdJdbcDao.getNetworkFlag(String.valueOf(orgId));
        
        boolean isNonNetworkShop = false;
        if (flag != null && flag.trim().equalsIgnoreCase("n")) {
            isNonNetworkShop = true;
        }
        return isNonNetworkShop;
    }

    /* (non-Javadoc)
     * @see com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelper#isShopUser(java.lang.String, java.lang.String)
     */
    public boolean isShopUser(String coCode, String userId)
            throws MitchellException {
        
        boolean isShopUser = false;
        
        final String userType = getUserType(coCode, userId);
        
        if (UserTypeConstants.SHOP_TYPE.equals(userType) 
                || UserTypeConstants.DRP_SHOP_TYPE.equals(userType)) {
            isShopUser = true;
        }
        
        return isShopUser;
    }
	
    public SystemConfigurationProxy getSystemConfigurationProxy() {
        return systemConfigurationProxy;
    }

    public void setSystemConfigurationProxy(
            SystemConfigurationProxy systemConfigurationProxy) {
        this.systemConfigurationProxy = systemConfigurationProxy;
    }
    
    /* (non-Javadoc)
     * @see com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelper#isHideNonNetworkSolution()
     */
    public boolean isHideNonNetworkSolution() {
        boolean flag = false;
        
        final String setting =
                this.systemConfigurationProxy
                .getSettingValue("/APDDeliveryService/HideNonDRP");
        
        if (setting != null && setting.trim().length() > 0
                && (setting.trim().toUpperCase().charAt(0) == 'Y'
                    || setting.trim().toUpperCase().charAt(0) == 'T')) {
            flag = true;
        }
        return flag;
    }
	
	/* (non-Javadoc)
	 * @see com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelper#isNonNetworkShopAllowedToReceiveAssignment(com.mitchell.common.types.UserInfoType)
	 */
	public boolean isNonNetworkShopAllowedToReceiveAssignment(
            UserInfoType userInfoType) {
        
        boolean flag = false;
        
        if (userInfoType != null
                && userInfoType.getAppCodeArray() != null
                && userInfoType.getAppCodeArray().length > 0) {

            final String setting = this.systemConfigurationProxy.getSettingValue(
                    "/APDDeliveryService/BroadcastMessageDelivery/EstimatorApplCode",
                    APPCODE_ESTIMATOR);
            
            for (int i = 0; i < userInfoType.getAppCodeArray().length; i++) {
                
                if (setting.equalsIgnoreCase(userInfoType.getAppCodeArray(i))) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /* (non-Javadoc)
     * @see com.mitchell.services.core.partialloss.apddelivery.pojo.util.UserHelper#checkUserData(com.mitchell.common.types.UserInfoDocument)
     */
    public UserData checkUserData(UserInfoDocument userInfoDoc)
    throws Exception {

        UserData userData = null;
        final UserInfoDocument xzUserInfo =
                apdUtil.getXZUserInfo(userInfoDoc);
        
        if (xzUserInfo != null && xzUserInfo.getUserInfo() != null
                && xzUserInfo.getUserInfo().getAppCodeArray() != null
                && xzUserInfo.getUserInfo().getAppCodeArray().length > 0) {
            
            final String shopBasicAppCode =
                    this.systemConfigurationProxy
                    .getSettingValue(APPDELIVERY_BASIC_APPCODE);
            
            final String shopPremiumAppCode =
                    this.systemConfigurationProxy
                    .getSettingValue(APPDELIVERY_PREMIUM_APPCODE);
            
            boolean isShopBasic = false;
            boolean isShopPremium = false;
            for (int i = 0; i < xzUserInfo.getUserInfo().getAppCodeArray().length; i++) {
                
                // no break, in case the user has both appl codes (you never know)
                // let the caller decides what to do
                if (shopBasicAppCode.equalsIgnoreCase(
                        xzUserInfo.getUserInfo().getAppCodeArray(i))) {
                    isShopBasic = true;
                }
                
                if (shopPremiumAppCode.equalsIgnoreCase(
                        xzUserInfo.getUserInfo().getAppCodeArray(i))) {
                    isShopPremium = true;
                }
            }
            
            // only if there is such appl_code
            if (isShopBasic || isShopPremium) {
                userData = new UserData(isShopBasic, isShopPremium);
            }
        }
        
        return userData;
    }
    
	public APDDeliveryUtil getApdUtil() {
        return apdUtil;
    }

    public void setApdUtil(APDDeliveryUtil apdUtil) {
        this.apdUtil = apdUtil;
    }
    
} 
