package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.services.business.partialloss.appraisalassignment.pejb.AASParallelEJB;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.utils.xml.MIEnvelopeHelper;

public class AASParallelRequestManagerBeanTest
{
  protected AASParallelRequestManagerBean testClass;
  protected IntegrationTestHelperClient itHelper;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AASParallelRequestManagerBean.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  @Test
  public void processRequestByTypeTestBadProcessType()
      throws Exception
  {
    String processType = "junk";
    AASParallelContext ctx = null;
    MIEnvelopeHelper meHelper = null;
    boolean isError = false;

    // Call method in test
    when(
        this.testClass
            .processRequestByType(processType, ctx, meHelper, isError))
        .thenCallRealMethod();
    MitchellException me = null;
    try {
      MitchellEnvelopeDocument retval = this.testClass.processRequestByType(
          processType, ctx, meHelper, isError);
    } catch (MitchellException e) {
      me = e;
    }
    assertNotNull(me);
  }

  @Test
  public void processRequestByTypeTest()
      throws Exception
  {
    String processType = AASParallelHandlerType.ASSIGN_SCHEDULE.toString();
    AASParallelContext ctx = mock(AASParallelContext.class);
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);
    boolean isError = false;

    this.testClass.parallelProcessor = mock(AASParallelHandlerProcessor.class);

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    when(
        this.testClass.parallelProcessor.processRequest(
            AASParallelHandlerType.valueOf(processType), ctx, meHelper, isError))
        .thenReturn(meDoc);

    // Call method in test
    when(
        this.testClass
            .processRequestByType(processType, ctx, meHelper, isError))
        .thenCallRealMethod();
    MitchellEnvelopeDocument retval = this.testClass.processRequestByType(
        processType, ctx, meHelper, isError);
    assertNotNull(retval);

    assertTrue(meDoc.equals(retval));

    verify(this.testClass.parallelProcessor).processRequest(
        AASParallelHandlerType.valueOf(processType), ctx, meHelper, isError);
  }

  @Test
  public void initContextFromRequestTest()
  {
    AASParallelContext ctx = new AASParallelContext();
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);

    when(
        meHelper
            .getEnvelopeContextNVPairValue(AASParallelConstants.ME_WORK_ITEM_ID))
        .thenReturn("WID");

    when(
        meHelper
            .getEnvelopeContextNVPairValue(AASParallelConstants.ME_COMPANY_CODE))
        .thenReturn("MECO");

    // Call method in test
    when(this.testClass.initContextFromRequest(meHelper, ctx))
        .thenCallRealMethod();
    AASParallelContext retval = this.testClass.initContextFromRequest(meHelper,
        ctx);
    assertNotNull(retval);

    assertTrue("WID".equals(retval.getWorkItemId()));
    assertTrue("MECO".equals(retval.getCompanyCode()));

  }

  @Test
  public void doProcessingRequestTest()
      throws Exception
  {
    TextMessage msg = mock(TextMessage.class);
    boolean isError = false;

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();

    this.testClass.parallelEjb = mock(AASParallelEJB.class);
    when(this.testClass.parallelEjb.extractMitchellEnvelopeFromMessage(msg))
        .thenReturn(meDoc);

    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);
    this.testClass.extAccess = mock(AASExternalAccessor.class);
    when(this.testClass.extAccess.buildMEHelper(meDoc)).thenReturn(meHelper);

    AASParallelContext ctx = mock(AASParallelContext.class);
    when(
        this.testClass.initContextFromRequest((MIEnvelopeHelper) anyObject(),
            (AASParallelContext) anyObject())).thenReturn(ctx);

    String processType = "MEPT";
    when(
        meHelper
            .getEnvelopeContextNVPairValue(AASParallelConstants.ME_PROCESSING_TYPE))
        .thenReturn(processType);

    when(
        this.testClass
            .processRequestByType(processType, ctx, meHelper, isError))
        .thenReturn(meDoc);

    // Call method in test
    doCallRealMethod().when(this.testClass).doProcessingRequest(msg, isError);
    this.testClass.doProcessingRequest(msg, isError);

    // Verify
    verify(this.testClass.parallelEjb).extractMitchellEnvelopeFromMessage(msg);
    verify(this.testClass.extAccess).buildMEHelper(meDoc);
    verify(this.testClass).initContextFromRequest(
        (MIEnvelopeHelper) anyObject(), (AASParallelContext) anyObject());
    verify(meHelper).getEnvelopeContextNVPairValue(
        AASParallelConstants.ME_PROCESSING_TYPE);
    verify(this.testClass).processRequestByType(processType, ctx, meHelper,
        isError);
    verify(this.testClass.parallelEjb).submitResponse(msg, meDoc.toString());

  }

  @Test
  public void populateExceptionInfoTest()
      throws Exception
  {
    MitchellException me = new MitchellException("A", "B", "C");
    AASParallelContext ctx = new AASParallelContext();
    ctx.setWorkItemId("WID");
    ctx.setCompanyCode("MYCO");

    // Call method in test
    when(this.testClass.populateExceptionInfo(me, ctx)).thenCallRealMethod();
    MitchellException retval = this.testClass.populateExceptionInfo(me, ctx);

    assertNotNull(retval);
    assertTrue(me.getType() == AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR);
    assertTrue(ctx.getWorkItemId().equals(retval.getWorkItemId()));
    assertTrue(ctx.getCompanyCode().equals(retval.getCompanyCode()));

  }

  @Test
  public void populateExceptionInfoTestErrorNumber()
      throws Exception
  {
    MitchellException me = new MitchellException("A", "B", "C");
    me.setType(AppraisalAssignmentConstants.MIN_SERVICE_ERROR + 1);
    AASParallelContext ctx = new AASParallelContext();

    // Call method in test
    when(this.testClass.populateExceptionInfo(me, ctx)).thenCallRealMethod();
    MitchellException retval = this.testClass.populateExceptionInfo(me, ctx);

    assertNotNull(retval);
    assertTrue(me.getType() == AppraisalAssignmentConstants.MIN_SERVICE_ERROR + 1);
    assertTrue(AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR != (AppraisalAssignmentConstants.MIN_SERVICE_ERROR + 1));

  }

  @Test
  public void buildMitchellExceptionTest()
  {
    Exception e = new Exception("AA");
    AASParallelContext ctx = new AASParallelContext();
    String defaultDescription = "BB";

    when(
        this.testClass.populateExceptionInfo((MitchellException) anyObject(),
            (AASParallelContext) anyObject())).thenCallRealMethod();

    // Call method in test
    when(this.testClass.buildMitchellException(e, ctx, defaultDescription))
        .thenCallRealMethod();
    MitchellException me = this.testClass.buildMitchellException(e, ctx,
        defaultDescription);
    assertNotNull(me);

    assertTrue(me.getType() == AppraisalAssignmentConstants.ERROR_PARALLEL_PROCESSING_ERROR);
    assertTrue(defaultDescription.equals(me.getDescription()));

  }

  @Test
  public void buildMitchellExceptionTestME()
  {
    MitchellException e = new MitchellException("AA", "BB", "CC");
    AASParallelContext ctx = new AASParallelContext();
    String defaultDescription = "DD";

    when(
        this.testClass.populateExceptionInfo((MitchellException) anyObject(),
            (AASParallelContext) anyObject())).thenCallRealMethod();

    // Call method in test
    when(this.testClass.buildMitchellException(e, ctx, defaultDescription))
        .thenCallRealMethod();
    MitchellException me = this.testClass.buildMitchellException(e, ctx,
        defaultDescription);
    assertNotNull(me);

    assertTrue(e.getDescription().equals(me.getDescription()));

  }

  @Test
  public void handleProcessingRequestErrorTest()
      throws Exception
  {
    TextMessage msg = mock(TextMessage.class);

    // Call method in test
    doCallRealMethod().when(this.testClass).handleProcessingRequestError(msg);
    this.testClass.handleProcessingRequestError(msg);

    verify(this.testClass).doProcessingRequest(msg, true);

  }

  @Test
  public void handleProcessingRequestTest()
      throws Exception
  {
    TextMessage msg = mock(TextMessage.class);

    // Call method in test
    doCallRealMethod().when(this.testClass).handleProcessingRequest(msg);
    this.testClass.handleProcessingRequest(msg);

    verify(this.testClass).doProcessingRequest(msg, false);

  }

}
