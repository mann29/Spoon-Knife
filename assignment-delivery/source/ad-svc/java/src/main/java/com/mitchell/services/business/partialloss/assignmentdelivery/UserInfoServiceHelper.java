package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.util.logging.Logger;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.dto.UserInfoDTO;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;

public class UserInfoServiceHelper
{

  private static final Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.assignmentdelivery.UserInfoServiceHelper");

  public static String getCompanyCode(String coCode, String userID)
      throws Exception
  {
    String companyID = null;
    UserInfoDocument userInfo = retrieveUserInfo(coCode, userID);
    if (userInfo != null) {
      if (isDRPUser(userInfo)) {
        UserInfoDocument drpUserInfo = retrieveDRPUserInfo(coCode, userID);
        companyID = drpUserInfo.getUserInfo().getOrgCode();
      } else {
        companyID = userInfo.getUserInfo().getOrgCode();
      }
    }
    return companyID;

  }

  public static String getUserID(String coCode, String userID)
      throws Exception
  {
    String estimatorID = null;
    UserInfoDocument userInfo = retrieveUserInfo(coCode, userID);
    if (userInfo != null) {
      if (isDRPUser(userInfo)) {
        UserInfoDocument drpUserInfo = retrieveDRPUserInfo(coCode, userID);
        estimatorID = drpUserInfo.getUserInfo().getUserID();
      } else {
        estimatorID = userInfo.getUserInfo().getUserID();
      }
    }
    return estimatorID;

  }

  public static UserInfoDocument retrieveUserInfo(String companyCode,
      String userId)
      throws Exception
  {
    UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
    UserInfoDocument userInfoDoc = ejb.getUserInfo(companyCode, userId, "");
    return userInfoDoc;
  }

  public static UserInfoDocument retrieveDRPUserInfo(String companyCode,
      String userId)
      throws Exception
  {
    UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
    UserInfoDocument drpUserInfo = null;

    UserInfoDTO dto = ejb.getUserInfo(companyCode, userId, "", true); // do cross over check

    if (dto == null || dto.getUserInfo() == null) {
      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("No UserInfoDocument found for DRP user: " + companyCode
            + "/" + userId);
      }
    } else {
      drpUserInfo = dto.getUserInfo();
    }

    return drpUserInfo;
  }

  private static boolean isDRPUser(UserInfoDocument userInfo)
      throws Exception
  {
    boolean isDRP = false;
    UserInfoServiceEJBRemote ejb = UserInfoClient.getUserInfoEJB();
    if (ejb.validateDRP(userInfo.getUserInfo().getUserID(), userInfo
        .getUserInfo().getOrgCode())) {
      isDRP = true;
    }
    return isDRP;
  }

}
