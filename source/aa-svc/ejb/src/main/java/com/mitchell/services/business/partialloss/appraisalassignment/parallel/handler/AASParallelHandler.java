package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.utils.xml.MIEnvelopeHelper;

public interface AASParallelHandler
{

  public MitchellEnvelopeDocument processRequest(AASParallelContext ctx,
      MIEnvelopeHelper meHelper)
      throws MitchellException;

  public MitchellEnvelopeDocument processRequestError(AASParallelContext ctx,
      MIEnvelopeHelper meHelper)
      throws MitchellException;

}
