package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;

public interface ErrorLogProxy {
    public void logAndThrowError(int errorType, java.lang.String classNm, java.lang.String methodNm,
            java.lang.String desc, java.lang.Throwable t) throws MitchellException;

    public void logAndThrowError(int errorType, java.lang.String classNm, java.lang.String methodNm,
            java.lang.String desc, java.lang.Throwable t, Logger logger) throws MitchellException;
}
