package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import com.mitchell.common.exception.MitchellException;

/**
 * Created by ss101449 on 1/21/2015.
 */
public interface CarrErrorDao {
    /**
     * Retrieves the CARR error code associated with the String description.
     * @param carrMessage The error message output by CARR.
     * @return The CARR error code.
     * @throws MitchellException
     */
    long getErrorCode(String carrMessage) throws MitchellException;
}
