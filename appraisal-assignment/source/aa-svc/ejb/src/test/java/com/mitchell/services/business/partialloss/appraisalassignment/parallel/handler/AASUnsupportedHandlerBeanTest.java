package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.utils.xml.MIEnvelopeHelper;

public class AASUnsupportedHandlerBeanTest
{
  protected AASUnsupportedHandlerBean testClass;
  protected IntegrationTestHelperClient itHelper;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AASUnsupportedHandlerBean.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  @Test
  public void processRequestTest()
      throws Exception
  {
    AASParallelContext ctx = null;
    MIEnvelopeHelper meHelper = null;

    // Call method in test
    when(this.testClass.processRequest(ctx, meHelper)).thenCallRealMethod();
    MitchellException me = null;
    try {
      this.testClass.processRequest(ctx, meHelper);
    } catch (MitchellException e) {
      me = e;
    }
    assertNotNull(me);
  }

  @Test
  public void processRequestErrorTest()
      throws Exception
  {
    AASParallelContext ctx = null;
    MIEnvelopeHelper meHelper = null;

    // Call method in test
    when(this.testClass.processRequestError(ctx, meHelper))
        .thenCallRealMethod();
    MitchellException me = null;
    try {
      this.testClass.processRequestError(ctx, meHelper);
    } catch (MitchellException e) {
      me = e;
    }
    assertNotNull(me);
  }

}
