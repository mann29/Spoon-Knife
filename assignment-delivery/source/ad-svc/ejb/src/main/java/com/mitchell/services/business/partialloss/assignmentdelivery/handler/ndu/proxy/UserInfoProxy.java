package com.mitchell.services.business.partialloss.assignmentdelivery.handler.ndu.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;

public interface UserInfoProxy {
    UserDetailDocument getUserDetail(long orgId)
            throws MitchellException;

}
