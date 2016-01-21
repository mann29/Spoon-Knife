package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.utils.xml.MIEnvelopeHelper;

@Stateless
public class AASParallelHandlerProcessorBean implements
    AASParallelHandlerProcessor
{

  @EJB
  protected AASUnsupportedHandler unsupportedHandler;

  @EJB
  protected AASAssignScheduleHandler assignScheduleHandler;

  /**
   * Main entry point to handle the processing request.
   */
  public MitchellEnvelopeDocument processRequest(
      AASParallelHandlerType handlerType, AASParallelContext ctx,
      MIEnvelopeHelper meHelper, boolean isError)
      throws MitchellException
  {
    AASParallelHandler handler = getHandler(handlerType);
    MitchellEnvelopeDocument meDoc = null;
    if (!isError) {
      meDoc = handler.processRequest(ctx, meHelper);
    } else {
      meDoc = handler.processRequestError(ctx, meHelper);
    }
    return meDoc;
  }

  /**
   * Factory method to get the correct handler. The following steps are required
   * to add a new parallel handler.
   * 
   * 1) Add a new Enum to AASParallelHandlerType.
   * 2) Create a new hander derived from AASBaseHandler. See
   * AASAssignScheduleHandler as an example.
   * 3) Inject the new handler into this class.
   * 4) Update the switch statement in this method.
   * 5) See AASAssignScheduleSubmitEJB for an example of the submit side of
   * parallelization.
   */
  protected AASParallelHandler getHandler(AASParallelHandlerType handlerType)
      throws MitchellException
  {
    AASParallelHandler retval = null;
    switch (handlerType) {

      case ASSIGN_SCHEDULE:
        retval = assignScheduleHandler;
        break;

      default:
        retval = unsupportedHandler;
        break;

    }
    return retval;
  }

}
