package com.mitchell.services.business.partialloss.appraisalassignment.proxy; 

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;

import java.rmi.RemoteException;

public interface CARRHelperProxy 
{ 
    
  public void  updateReviewAssignmentForSupplement(long relatedEstimateDocumentId,UserInfoDocument estimatorUserInfo,
                 UserInfoDocument   logdInUsrInfo)throws RemoteException, MitchellException;
} 
