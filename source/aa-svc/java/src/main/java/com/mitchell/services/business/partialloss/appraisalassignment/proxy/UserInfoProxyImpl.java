package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.utils.misc.AppUtilities;

public class UserInfoProxyImpl implements UserInfoProxy
{

  private static final String CLZ_NAME = UserInfoProxyImpl.class.getName();

  private UserInfoServiceEJBRemote userInfoFacade;

  Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxyImpl");

  public void init()
  {

    try {
      userInfoFacade = UserInfoClient.getUserInfoEJB();
    } catch (final Exception ex) {
      logger.error(AppUtilities.getStackTraceString(ex, true));
    }

  }

  // @Override
  public UserInfoDocument getUserInfo(final long orgId)
      throws RemoteException, MitchellException
  {

    return userInfoFacade.getUserInfo(orgId);
  }

  // @Override
  public boolean isUserStaff(final UserInfoDocument userInfoDoc)
  {
    return UserInfoClient.isUserStaff(userInfoDoc);
  }

  public UserInfoDocument getUserInfo(final String coCd, final String orgCd,
      final String methodName)
      throws RemoteException, MitchellException
  {

    UserInfoDocument userInfoDoc = null;
    userInfoDoc = userInfoFacade.getUserInfo(coCd, orgCd, methodName);
    return userInfoDoc;
  }

  public UserDetailDocument getUserDetails(final String coCode,
      final String userId)
      throws RemoteException, MitchellException
  {
    UserDetailDocument userDetailDoc = null;
    userDetailDoc = userInfoFacade.getUserDetail(coCode, userId);
    return userDetailDoc;

  }

  public UserInfoDocument getReviewerForEstimators(final long estimatorOrgId)
      throws RemoteException, MitchellException
  {
    UserInfoDocument userInfoDoc = null;
    userInfoDoc = userInfoFacade.getReviewerForEstimator(estimatorOrgId);
    return userInfoDoc;
  }

  public UserInfoDocument getSupervisorForReviewers(final long orgId)
      throws RemoteException, MitchellException
  {
    UserInfoDocument userInfoDoc = null;
    userInfoDoc = userInfoFacade.getSupervisorForReviewer(orgId);
    return userInfoDoc;
  }

  public String getUserTypes(final String companyCode, final String userId)
      throws RemoteException, MitchellException
  {
    String userType = null;
    userType = userInfoFacade.getUserType(companyCode, userId);
    return userType;
  }

  public UserInfoDocument getUserInfo(final String coCd, final String userId)
      throws MitchellException
  {
    UserInfoDocument userInfo = null;

    userInfo = userInfoFacade.getUserInfo(coCd, userId, (String) null);

    if (userInfo == null || userInfo.getUserInfo() == null) {

      final String desc = new StringBuffer("NO user found for:").append(coCd)
          .append("/").append(userId).toString();

      throw new MitchellException(AppraisalAssignmentConstants.ERR_NOSUCHUSER,
          CLZ_NAME, "getUserInfo", desc);
    }

    return userInfo;
  }

  public OrgInfoDocument getOrgInfo(long orgID)
      throws MitchellException, RemoteException
  {
    return userInfoFacade.getOrgInfo(orgID);
  }

}
