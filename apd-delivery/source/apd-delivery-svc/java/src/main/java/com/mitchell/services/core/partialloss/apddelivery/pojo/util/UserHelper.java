package com.mitchell.services.core.partialloss.apddelivery.pojo.util; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDBroadcastMessageType;

/**
 * The Interface UserHelper.
 */
public interface UserHelper { 
    
    /**
     * @param bMsg
     * APDBroadcastMessageType object
     * @return UserInfoDocument
     * @throws MitchellException
     * Mitchell Exception
     */
    public UserInfoDocument getRecipientUserInfo(
                                            final APDBroadcastMessageType bMsg)
                                            throws MitchellException;
                                            
    public UserInfoDocument getSenderUserInfo(final APDBroadcastMessageType bMsg)
                                            throws MitchellException;
                                            
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
    public String getUserType(String coCode, String userId) 
                                            throws MitchellException;
    
    /**
     * This method returns true if the user is NOT in network.
     * @param orgId
     * @return boolean
     * @throws MitchellException
     */
    boolean isNonNetworkShop(String orgId)
            throws MitchellException;
    
    /**
     * This method return true if Shop User.
     * @param coCode
     * @param userId
     * @return boolean
     * @throws MitchellException
     */
    boolean isShopUser(String coCode, String userId)
            throws MitchellException;
    
    /**
     * This method return true if HideNonNetworkSolution is Y.
     * @return boolean
     */
    boolean isHideNonNetworkSolution();
    
    /**
     * This method return true if NonNetworkShop is Allowed to Receive Assignment.
     * @param userInfoType
     * @return boolean
     */
    boolean isNonNetworkShopAllowedToReceiveAssignment(
            UserInfoType userInfoType);
    
    /**
     * This method returns UserData for userInfo.
     * @param userInfoDoc
     * @return UserData
     * @throws Exception
     */
    UserData checkUserData(UserInfoDocument userInfoDoc) throws Exception;
    
	   
} 
