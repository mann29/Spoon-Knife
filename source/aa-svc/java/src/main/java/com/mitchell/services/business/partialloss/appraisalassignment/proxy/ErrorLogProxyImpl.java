package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;

public class ErrorLogProxyImpl implements ErrorLogProxy {
    private AASCommonUtils aasCommonUtil;

    public void setAASCommonUtils(final AASCommonUtils aasCommonUtil) {
        this.aasCommonUtil = aasCommonUtil;
    }

    // @Override
    public void logAndThrowError(final int errorType, final String classNm, final String methodNm, final String desc,
            final Throwable t) throws MitchellException {

        final MitchellException mitExc = new MitchellException(errorType, classNm, methodNm, desc, t);
        mitExc.setSeverity(MitchellException.SEVERITY.WARNING);

        final String correlationID = ErrorLoggingService.logCommonError(mitExc);
        mitExc.setCorrelationId(correlationID);

        throw mitExc;

    }

    // @Override
    public void logAndThrowError(final int errorType, final String classNm, final String methodNm, final String desc,
            final Throwable t, final Logger logger) throws MitchellException {
        aasCommonUtil.logAndThrowError(errorType, classNm, methodNm, desc, t, logger);
    }

}
