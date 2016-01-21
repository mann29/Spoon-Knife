package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;

public interface ShopIdentifierProxy {

    public boolean isDaytonaShop(UserInfoDocument userInfoDocument)throws MitchellException;

}
