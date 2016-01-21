package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import javax.ejb.Stateless;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.utils.xml.MIEnvelopeHelper;

/**
 * This handler is to support an error condition that would likely only occur in
 * the midst of coding a new parallel handler.
 * 
 */
@Stateless
public class AASUnsupportedHandlerBean implements AASUnsupportedHandler
{

  public MitchellEnvelopeDocument processRequest(AASParallelContext ctx,
      MIEnvelopeHelper meHelper)
      throws MitchellException
  {
    throw new MitchellException(
        AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR, this
            .getClass().getName(), "processRequest",
        "HandlerType is not supportted.");
  }

  public MitchellEnvelopeDocument processRequestError(AASParallelContext ctx,
      MIEnvelopeHelper meHelper)
      throws MitchellException
  {
    throw new MitchellException(
        AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR, this
            .getClass().getName(), "processRequestError",
        "HandlerType is not supportted.");
  }
}
