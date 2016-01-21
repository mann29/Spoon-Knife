package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.daytona.shop.identifier.ShopIdentifier;
import com.mitchell.daytona.shop.identifier.ShopIdentifierImpl;

public class ShopIdentifierProxyImpl implements ShopIdentifierProxy{

    ShopIdentifier shopIdentifier = new ShopIdentifierImpl();

    public boolean isDaytonaShop(UserInfoDocument crossOverUserInfo) throws MitchellException
    {
        return shopIdentifier.isDaytonaShop(crossOverUserInfo);
    }

}
