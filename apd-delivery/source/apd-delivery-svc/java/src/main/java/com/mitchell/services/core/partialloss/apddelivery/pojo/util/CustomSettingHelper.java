package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;

/**
 * The Interface CustomSettingHelper.
 */
public interface CustomSettingHelper {
    
    /**
     * @param userInfoDocument
     * UserInfoDocument object
     * @return String
     * Custom setting value
     * @throws MitchellException
     * Mitchell Exception
     */
    String getSIPAssignmentDeliveryDestination(UserInfoDocument userInfoDocument)
                                                    throws MitchellException;
    
    /**
     * @param userInfoDocument
     * UserInfoDocument object
     * @return String
     * Custom setting value
     * @throws MitchellException
     * Mitchell Exception
     */
    String isEmailRequired(UserInfoDocument userInfoDocument)
                                                    throws MitchellException;
}
