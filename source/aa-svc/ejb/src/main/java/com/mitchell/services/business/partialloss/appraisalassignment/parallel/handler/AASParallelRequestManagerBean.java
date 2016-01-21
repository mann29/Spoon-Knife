package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.TextMessage;

import org.jboss.ejb3.annotation.Pool;
import org.jboss.ejb3.annotation.defaults.PoolDefaults;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.services.business.partialloss.appraisalassignment.pejb.AASParallelEJB;
import com.mitchell.utils.xml.MIEnvelopeHelper;

/**
 * This class provides the main entry point for parallel handler processing.
 * This class is called from the parallel MDBs.
 * 
 */
@Stateless
@Pool(value = PoolDefaults.POOL_IMPLEMENTATION_STRICTMAX, maxSize = 50, timeout = 10000)
public class AASParallelRequestManagerBean implements AASParallelRequestManager
{

  @EJB
  protected AASParallelEJB parallelEjb;

  @EJB
  protected AASParallelHandlerProcessor parallelProcessor;

  @EJB
  protected AASExternalAccessor extAccess;

  /**
   * Main entry for Primary Request MDB Processing.
   */
  public void handleProcessingRequest(TextMessage msg)
      throws MitchellException
  {
    this.doProcessingRequest(msg, false);
  }

  /**
   * Main entry for Error MDB Processing.
   */
  public void handleProcessingRequestError(TextMessage msg)
      throws MitchellException
  {
    this.doProcessingRequest(msg, true);
  }

  /**
   * Do Processing Request. Manages common parallel processing flow and
   * delegates specific processing functionality to lower level handlers.
   */
  protected void doProcessingRequest(TextMessage msg, boolean isError)
      throws MitchellException
  {
    AASParallelContext ctx = new AASParallelContext();
    String processType = "";

    try {

      // Get the MitchellEnvelope
      MitchellEnvelopeDocument meDoc = this.parallelEjb
          .extractMitchellEnvelopeFromMessage(msg);

      // Create a Helper
      MIEnvelopeHelper meHelper = this.extAccess.buildMEHelper(meDoc);

      // Initialize context from the request contents
      ctx = initContextFromRequest(meHelper, ctx);

      // Get the processing request type
      processType = meHelper
          .getEnvelopeContextNVPairValue(AASParallelConstants.ME_PROCESSING_TYPE);

      // Call the proper Processor
      MitchellEnvelopeDocument responseDoc = processRequestByType(processType,
          ctx, meHelper, isError);

      // Submit the response
      this.parallelEjb.submitResponse(msg, responseDoc.toString());

    } catch (Exception e) {
      MitchellException me = buildMitchellException(e, ctx,
          "Unexpected exception. ProcessingType: " + processType);
      throw me;
    }

  }

  /**
   * Initialize the context from information that is common to all parallel
   * requests.
   * 
   * @param meHelper A helper initialized with ME request.
   * 
   * @return Returns the update context.
   */
  protected AASParallelContext initContextFromRequest(
      MIEnvelopeHelper meHelper, AASParallelContext ctx)
  {
    ctx.setWorkItemId(meHelper
        .getEnvelopeContextNVPairValue(AASParallelConstants.ME_WORK_ITEM_ID));
    ctx.setCompanyCode(meHelper
        .getEnvelopeContextNVPairValue(AASParallelConstants.ME_COMPANY_CODE));
    return ctx;
  }

  /**
   * Process Request By Type, delegates to lower level specific handlers.
   * 
   * @throws MitchellException
   */
  protected MitchellEnvelopeDocument processRequestByType(String processType,
      AASParallelContext ctx, MIEnvelopeHelper meHelper, boolean isError)
      throws MitchellException
  {
    MitchellEnvelopeDocument respDoc = null;

    try {
      // Get the hander type Enum
      AASParallelHandlerType ht = AASParallelHandlerType.valueOf(processType);
      if (ht == null) {
        throw new MitchellException(
            AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR, this
                .getClass().getName(), "processRequestByType",
            "Unsupported processType: " + processType);
      }

      // Call the processing handler
      respDoc = this.parallelProcessor.processRequest(ht, ctx, meHelper,
          isError);

    } catch (IllegalArgumentException e) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR, this
              .getClass().getName(), "processRequestByType",
          "Illegal processType: " + processType, e);
    }

    //
    return respDoc;
  }

  /**
   * Build Mitchell Exception.
   */
  protected MitchellException buildMitchellException(Exception e,
      AASParallelContext ctx, String defaultDescription)
  {
    MitchellException me;
    if (!(e instanceof MitchellException)) {
      me = new MitchellException(
          AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR, this
              .getClass().getName(), "buildMitchellException",
          defaultDescription, e);
    } else {
      me = (MitchellException) e;
    }
    return populateExceptionInfo(me, ctx);
  }

  /**
   * Populate Exception Info from the context.
   */
  protected MitchellException populateExceptionInfo(MitchellException me,
      AASParallelContext ctx)
  {
    if (me.getType() < AppraisalAssignmentConstants.MIN_SERVICE_ERROR
        || me.getType() > AppraisalAssignmentConstants.MAX_SERVICE_ERROR) {
      me.setType(AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR);
    }
    me.setWorkItemId(ctx.getWorkItemId());
    me.setCompanyCode(ctx.getCompanyCode());
    return me;
  }

}
