package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;

public interface UserInfoProxy {
    public UserInfoDocument getUserInfo(final String coCode,final String userID) throws  MitchellException;

    
}
