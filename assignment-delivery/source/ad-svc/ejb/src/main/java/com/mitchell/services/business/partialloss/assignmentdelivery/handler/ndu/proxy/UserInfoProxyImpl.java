package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.utils.misc.AppUtilities;

public class UserInfoProxyImpl implements UserInfoProxy {

    public UserDetailDocument getUserDetail(long orgId)
            throws MitchellException {
        UserDetailDocument userDetailDoc = null;

        try {
            userDetailDoc = UserInfoClient
                    .getUserInfoEJB().getUserDetail(orgId);
        } catch (Exception e) {
            final String desc = new StringBuffer(
            "Exception calling UserInfoService with orgId:")
            .append(orgId)
            .append(",with stacktrace:")
            .append(AppUtilities.getStackTraceString(e, true))
            .toString();

            throw new MitchellException(
                    UserInfoProxyImpl.class.getName(),
            "getUserDetail", desc, e);
        }
        return userDetailDoc;
    }

}
