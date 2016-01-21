package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.RemoveException;

import com.mitchell.common.exception.MICommonException;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.LocalizationInfoDocument;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.dto.UserInfoDTO;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.userinfo.types.OrgRelationshipInfoDocument;
import com.mitchell.services.core.userinfo.types.ServiceProviderType;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.services.core.userinfo.utils.MapAddress;

public class UserInfoClientProxyImpl implements UserInfoServiceEJBRemote {

    public CrossOverUserInfoDocument getCrossOverUserInfo(final String arg0, final String arg1) throws 
            MICommonException {
        return delegate.getCrossOverUserInfo(arg0, arg1);
    }

    public CrossOverUserInfoDocument getCrossOverUserInfoByOrgID(final long arg0) throws 
            MICommonException {
        return delegate.getCrossOverUserInfoByOrgID(arg0);
    }

    public LocalizationInfoDocument getLocalizationInfo(final String arg0) throws  MICommonException {
        return delegate.getLocalizationInfo(arg0);
    }

    public OrgInfoDocument getOrgInfo(final String arg0, final String arg1, final String arg2) throws 
            MitchellException {
        return delegate.getOrgInfo(arg0, arg1, arg2);
    }

    public UserInfoDocument getReviewerForEstimator(final long arg0) throws  MitchellException {
        return delegate.getReviewerForEstimator(arg0);
    }

    public UserInfoDocument getSupervisorForReinspector(final long arg0) throws  MitchellException {
        return delegate.getSupervisorForReinspector(arg0);
    }

    public UserInfoDocument getSupervisorForReviewer(final long arg0) throws  MitchellException {
        return delegate.getSupervisorForReviewer(arg0);
    }

    public UserDetailDocument getUserDetail(final long arg0) throws  MICommonException {
        return delegate.getUserDetail(arg0);
    }

    public UserDetailDocument getUserDetail(final String arg0, final String arg1) throws 
            MICommonException {
        return delegate.getUserDetail(arg0, arg1);
    }

    public UserInfoDocument getUserInfo(final long arg0) throws  MICommonException {
        return delegate.getUserInfo(arg0);
    }

    public UserInfoDTO getUserInfo(final String arg0, final String arg1, final String arg2, final boolean arg3)
            throws  MitchellException {
        return delegate.getUserInfo(arg0, arg1, arg2, arg3);
    }

    public UserInfoDocument getUserInfo(final String arg0, final String arg1, final String arg2)
            throws  MICommonException {
        return delegate.getUserInfo(arg0, arg1, arg2);
    }

    public String getUserType(final String arg0, final String arg1) throws  MitchellException {
        return delegate.getUserType(arg0, arg1);
    }

//    public boolean isIdentical(final EJBObject arg0) throws RemoteException {
//        return delegate.isIdentical(arg0);
//    }

    public Collection searchServiceProviderByAddress(final String arg0, final String arg1, final MapAddress arg2)
            throws  MICommonException {
        return delegate.searchServiceProviderByAddress(arg0, arg1, arg2);
    }

    public Collection searchServiceProviderByAddress(final String arg0, final String arg1, final String arg2,
            final MapAddress arg3) throws  MICommonException {
        return delegate.searchServiceProviderByAddress(arg0, arg1, arg2, arg3);
    }

    public ServiceProviderType searchServiceProviderByID(final String arg0, final String arg1) throws 
            MICommonException {
        return delegate.searchServiceProviderByID(arg0, arg1);
    }

    public Collection searchServiceProviderByName(final String arg0, final String arg1, final String arg2,
            final String arg3) throws  MICommonException {
        return delegate.searchServiceProviderByName(arg0, arg1, arg2, arg3);
    }

    public Collection searchServiceProviderByName(final String arg0, final String arg1, final String arg2)
            throws  MICommonException {
        return delegate.searchServiceProviderByName(arg0, arg1, arg2);
    }

    public Collection searchServiceProviderByOffice(final String arg0, final String arg1, final String arg2,
            final String arg3) throws  MICommonException {
        return delegate.searchServiceProviderByOffice(arg0, arg1, arg2, arg3);
    }

    public Collection searchServiceProviderByOffice(final String arg0, final String arg1, final String arg2)
            throws  MICommonException {
        return delegate.searchServiceProviderByOffice(arg0, arg1, arg2);
    }

    public OrgRelationshipInfoDocument getOrgRelationshipInfoByOrgID(final long arg0, final String[] arg1)
            throws  MICommonException {
        return delegate.getOrgRelationshipInfoByOrgID(arg0, arg1);
    }

    public OrgRelationshipInfoDocument getOrgRelationshipInfoByUserID(final String arg0, final String arg1,
            final String arg2, final String[] arg3) throws  MICommonException {
        return delegate.getOrgRelationshipInfoByUserID(arg0, arg1, arg2, arg3);
    }

    public void updateByRatings(final String arg0, final String arg1, final int arg2, final String arg3,
            final String arg4) throws  MICommonException {
        delegate.updateByRatings(arg0, arg1, arg2, arg3, arg4);
    }

    public boolean validateDRP(final String arg0, final String arg1) throws  MICommonException {
        return delegate.validateDRP(arg0, arg1);
    }

    private UserInfoServiceEJBRemote delegate;

    public UserInfoClientProxyImpl() {
        try {
            delegate = UserInfoClient.getUserInfoEJB();
        } catch (final MitchellException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public EJBHome getEJBHome() throws RemoteException {
        throw new UnsupportedOperationException();
    }

    public Handle getHandle() throws RemoteException {
        throw new UnsupportedOperationException();
    }

    public Object getPrimaryKey() throws RemoteException {
        throw new UnsupportedOperationException();
    }

    public void remove() throws RemoteException, RemoveException {
        throw new UnsupportedOperationException();
    }

    public OrgInfoDocument getOrgInfo(final long arg0) throws MICommonException {
        throw new UnsupportedOperationException();
    }

    public UserInfoDTO getUserInfoWithAltRoutingCD(final String arg0, final String arg1, final String arg2,
            final boolean arg3) throws MICommonException {
        // TODO Auto-generated method stub
        return null;
    }

    public String getOrgTypeCompany() {
        return UserInfoClient.ORG_TYPE_COMPANY;
    }
}
