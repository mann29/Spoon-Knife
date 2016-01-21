package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.rmi.RemoteException;

import com.mitchell.common.exception.MICommonException;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

public interface UserInfoProxy {
    public UserInfoDocument getUserInfo(long orgId) throws RemoteException, MitchellException, MICommonException;

    public boolean isUserStaff(UserInfoDocument userInfoDoc);

    public UserInfoDocument getUserInfo(String coCd, String orgCd, String methodName) throws RemoteException,
            MitchellException, MICommonException;

    public UserDetailDocument getUserDetails(String coCode, String userId) throws RemoteException, MitchellException,
            MICommonException;

    public UserInfoDocument getReviewerForEstimators(long estimatorOrgId) throws RemoteException, MitchellException,
            MICommonException;

    public UserInfoDocument getSupervisorForReviewers(long orgId) throws RemoteException, MitchellException,
            MICommonException;

    public String getUserTypes(String companyCode, String userId) throws RemoteException, MitchellException,
            MICommonException;

    UserInfoDocument getUserInfo(String coCd, String userId) throws MitchellException;
    
    public OrgInfoDocument getOrgInfo(long orgID) throws MitchellException, RemoteException;
}
