package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

/**
 * The Interface UserInfoProxy.
 */
public interface UserInfoProxy {
    
    /**
     * Gets the user info.
     *
     * @param orgId the org id
     * @return the user info
     * @throws MitchellException the mitchell exception
     */
    UserInfoDocument getUserInfo(long orgId) throws MitchellException;

    /**
     * Gets the user info.
     *
     * @param coCd the co cd
     * @param userId the user id
     * @return the user info
     * @throws MitchellException the mitchell exception
     */
    UserInfoDocument getUserInfo(String coCd, String userId) throws MitchellException;

    /**
     * Gets the user detail.
     *
     * @param orgId the org id
     * @return the user detail
     * @throws MitchellException the mitchell exception
     */
    UserDetailDocument getUserDetail(long orgId) throws MitchellException;
    
    /**
     * Gets the user detail.
     *
     * @param coCd the co cd
     * @param userId the user id
     * @return the user detail
     * @throws MitchellException the mitchell exception
     */
    UserDetailDocument getUserDetail(String coCd, String userId) throws MitchellException;
 
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
    throws MitchellException ; 
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
    public String getUserType(String coCode, String userId)throws MitchellException ;  
    
    /**
     * This method gets user info.
     * @param coCode
     * @param userId
     * @param uuId
     * @return
     * @throws MitchellException
     */
    public UserInfoDocument getUserInfo(String coCode, String userId, String uuId) throws MitchellException;

    /**
     * This method returns orgInfo.
     * @param insCoCode
     * @param coCode
     * @return
     * @throws MitchellException
     */
    public OrgInfoDocument getOrgInfo(String insCoCode, String coCode) throws MitchellException ;
    
    /**
     * This method gets the crossOverUserInfo using OrgId.
     * @param orgId
     * @return
     * @throws MitchellException
     */
    public CrossOverUserInfoDocument  getCrossOverUserInfoByOrgID(long orgId) throws MitchellException;


    String getCompanyCode(String shopUserOrgId, String workItemId) throws MitchellException;
    String getOrgCode(String shopUserOrgId, String workItemId) throws MitchellException;
}
