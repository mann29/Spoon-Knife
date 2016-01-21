package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import com.mitchell.carr.services.helper.client.CARRHelperClient;
import com.mitchell.carr.services.helper.ejb.CARRHelperRemote;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.utils.misc.AppUtilities;

public class CARRHelperProxyImpl implements CARRHelperProxy
{

  private CARRHelperRemote carrHelperRemoteEJB;

  Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.appraisalassignment.proxy.CARRHelperProxyImpl");

  public void init()
  {
    try {
      carrHelperRemoteEJB = CARRHelperClient.getCARRHelpEJB();
    } catch (final Exception ex) {
      logger.error(AppUtilities.getStackTraceString(ex, true));
    }

  }

  public void updateReviewAssignmentForSupplement(
      long relatedEstimateDocumentId, UserInfoDocument estimatorUserInfo,
      UserInfoDocument logdInUsrInfo)
      throws RemoteException, MitchellException
  {
	if(logger.isDebugEnabled()) {
		StringBuilder builder = new StringBuilder();
		builder.append("Got request for reassign review. \n");
		builder.append("RelatedEstimateDocumentId : ");
		builder.append(relatedEstimateDocumentId);
		
		if(estimatorUserInfo!=null) {
			builder.append("; EstimatorUserInfo :");
			builder.append(estimatorUserInfo.getUserInfo().getOrgCode() + " " + estimatorUserInfo.getUserInfo().getUserID());
		}
		
		if(logdInUsrInfo!=null) {
			builder.append("; LoggedInUsrInfo :");
			builder.append(logdInUsrInfo.getUserInfo().getOrgCode() + " " + logdInUsrInfo.getUserInfo().getUserID());
		}		
		
		logger.debug(builder.toString());
	}
    carrHelperRemoteEJB.updateReviewAssignmentForSupplement(
        relatedEstimateDocumentId, estimatorUserInfo, logdInUsrInfo);
  }

}
