package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import javax.ejb.Local;
import javax.jms.TextMessage;

import com.mitchell.common.exception.MitchellException;

@Local
public interface AASParallelRequestManager
{
  public void handleProcessingRequest(TextMessage msg)
      throws MitchellException;

  public void handleProcessingRequestError(TextMessage msg)
      throws MitchellException;

}
