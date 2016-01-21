package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import javax.ejb.Local;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.utils.xml.MIEnvelopeHelper;

@Local
public interface AASParallelHandlerProcessor
{

  public MitchellEnvelopeDocument processRequest(
      AASParallelHandlerType handlerType, AASParallelContext ctx,
      MIEnvelopeHelper meHelper, boolean isError)
      throws MitchellException;

}
