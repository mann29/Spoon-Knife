package com.mitchell.services.business.partialloss.appraisalassignment.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

public interface UserInfoUtils {

    public UserInfoDocument getEstimatorInfo(long estimateDocID) throws Exception;

    public UserInfoDocument getUserInfoDoc(long orgId) throws MitchellException;

    public UserDetailDocument getUserDetailDoc(String coCode, String userId) throws Exception;

    public UserInfoDocument getUserInfoDocOfReviewerForEstimator(long estimatorOrgId) throws MitchellException;

    public UserInfoDocument getUserInfoDoc(String coCd, String orgCd, String methodName) throws MitchellException;

    public String getUserType(String companyCode, String userId) throws Exception;

    public UserInfoDocument retrieveUserInfo(String companyCode, String userId) throws Exception;

    public String getScheduleFlagForStaff(final String orgCoCode) throws Exception ;
      
    public String retrieveUserDispatchCenter(final String coCode, final String userID) throws MitchellException ;

}
