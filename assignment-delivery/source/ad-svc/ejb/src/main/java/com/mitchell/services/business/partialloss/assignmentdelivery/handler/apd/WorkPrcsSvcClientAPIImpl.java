package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.workprocess.WorkProcessServiceClient;
import com.mitchell.services.core.workprocess.WorkProcessServiceClientAPI;
import com.mitchell.services.core.workprocessservice.WPKeyRequestDocument;

/**
 * Pass thru class to shield dependency
 * 
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv
 *         Khanwale </a> Created/Modified on Dec 15, 2010
 */
public class WorkPrcsSvcClientAPIImpl implements WorkProcessServiceClientAPI {

    /**
     * Delegate. A simple pass-thru mechanism.
     */
    private final WorkProcessServiceClientAPI delegate;

    public WPKeyRequestDocument initWPKeyRequest(final String arg0) throws MitchellException {
        return delegate.initWPKeyRequest(arg0);
    }

    public void registerWorkProcessKey(final String arg0, final WPKeyRequestDocument arg1, final String arg2)
            throws MitchellException {
        delegate.registerWorkProcessKey(arg0, arg1, arg2);
    }

    public String retrieveWorkProcessKey(final String arg0, final WPKeyRequestDocument arg1) throws MitchellException {
        return delegate.retrieveWorkProcessKey(arg0, arg1);
    }

    public WorkPrcsSvcClientAPIImpl() {
        delegate = new WorkProcessServiceClient();
    }

    public void removeWorkProcessKey(final String key, final WPKeyRequestDocument query) throws MitchellException {
        delegate.removeWorkProcessKey(key, query);
    }

}
