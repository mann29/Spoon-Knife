package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.utils.xml.MIEnvelopeHelper;

public class AASParallelHandlerProcessorBeanTest
{
  protected AASParallelHandlerProcessorBean testClass;
  protected IntegrationTestHelperClient itHelper;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AASParallelHandlerProcessorBean.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  @Test
  public void getHandlerTestUnsupported()
      throws Exception
  {
    AASParallelHandlerType handlerType = AASParallelHandlerType.UNSUPPORTED;

    this.testClass.unsupportedHandler = mock(AASUnsupportedHandler.class);

    // Call method in test
    when(this.testClass.getHandler(handlerType)).thenCallRealMethod();
    AASParallelHandler retval = this.testClass.getHandler(handlerType);
    assertNotNull(retval);
    assertTrue(this.testClass.unsupportedHandler.equals(retval));
  }

  @Test
  public void getHandlerTestAssignSchedule()
      throws Exception
  {
    AASParallelHandlerType handlerType = AASParallelHandlerType.ASSIGN_SCHEDULE;

    this.testClass.assignScheduleHandler = mock(AASAssignScheduleHandler.class);

    // Call method in test
    when(this.testClass.getHandler(handlerType)).thenCallRealMethod();
    AASParallelHandler retval = this.testClass.getHandler(handlerType);
    assertNotNull(retval);
    assertTrue(this.testClass.assignScheduleHandler.equals(retval));
  }

  @Test
  public void processRequestTest()
      throws Exception
  {
    AASParallelHandlerType handlerType = AASParallelHandlerType.ASSIGN_SCHEDULE;
    AASParallelContext ctx = mock(AASParallelContext.class);
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);
    boolean isError = false;

    AASParallelHandler handler = mock(AASParallelHandler.class);
    when(this.testClass.getHandler(handlerType)).thenReturn(handler);

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    when(handler.processRequest(ctx, meHelper)).thenReturn(meDoc);

    // Call method in test
    when(this.testClass.processRequest(handlerType, ctx, meHelper, isError))
        .thenCallRealMethod();
    MitchellEnvelopeDocument retval = this.testClass.processRequest(
        handlerType, ctx, meHelper, isError);
    assertNotNull(retval);

    verify(this.testClass).getHandler(handlerType);
    verify(handler).processRequest(ctx, meHelper);
    verify(handler, never()).processRequestError(ctx, meHelper);

    assertTrue(meDoc.equals(retval));

  }

  @Test
  public void processRequestTestError()
      throws Exception
  {
    AASParallelHandlerType handlerType = AASParallelHandlerType.ASSIGN_SCHEDULE;
    AASParallelContext ctx = mock(AASParallelContext.class);
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);
    boolean isError = true;

    AASParallelHandler handler = mock(AASParallelHandler.class);
    when(this.testClass.getHandler(handlerType)).thenReturn(handler);

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    when(handler.processRequestError(ctx, meHelper)).thenReturn(meDoc);

    // Call method in test
    when(this.testClass.processRequest(handlerType, ctx, meHelper, isError))
        .thenCallRealMethod();
    MitchellEnvelopeDocument retval = this.testClass.processRequest(
        handlerType, ctx, meHelper, isError);
    assertNotNull(retval);

    verify(this.testClass).getHandler(handlerType);
    verify(handler).processRequestError(ctx, meHelper);
    verify(handler, never()).processRequest(ctx, meHelper);

    assertTrue(meDoc.equals(retval));

  }

}
