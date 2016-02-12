package com.mitchell.services.business.questionnaireevaluation.proxy;

import com.mitchell.common.exception.MitchellException;

/**
 * The Interface ErrorLogProxy.
 */
public interface ErrorLogProxy {

	/**
     * Log error.
     *
     * @param mitchellException the mitchell exception
     * @return the string
     */
    String logError(
                MitchellException mitchellException);

	}