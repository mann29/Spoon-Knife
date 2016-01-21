package com.mitchell.services.business.partialloss.appraisalassignment.pejb;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASJMSParallelManager;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;

public class AASParallelEJBBeanTest
{
  protected AASParallelEJBBean testClass;
  protected IntegrationTestHelperClient itHelper;
  protected AASJMSParallelManager pMgr;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AASParallelEJBBean.class);
    this.testClass.extAccess = mock(AASExternalAccessor.class);

    this.pMgr = mock(AASJMSParallelManager.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
    this.pMgr = null;
  }

  @Test
  public void getParallelResponseQueueNameTest()
      throws Exception
  {
    when(
        this.testClass.extAccess
            .getSystemConfigValue(AASParallelConstants.SYSCONF_RESPONSE_QUEUE_NAME))
        .thenReturn("ABC");

    // Call method in test
    when(this.testClass.getParallelResponseQueueName()).thenCallRealMethod();
    String retval = this.testClass.getParallelResponseQueueName();
    assertNotNull(retval);
    assertTrue("ABC".equals(retval));
  }

  @Test
  public void getParallelMDBInputQueueNameTest()
      throws Exception
  {
    when(
        this.testClass.extAccess
            .getSystemConfigValue(AASParallelConstants.SYSCONF_INPUT_QUEUE_NAME))
        .thenReturn("DEF");

    // Call method in test
    when(this.testClass.getParallelMDBInputQueueName()).thenCallRealMethod();
    String retval = this.testClass.getParallelMDBInputQueueName();
    assertNotNull(retval);
    assertTrue("DEF".equals(retval));
  }

  @Test
  public void getQueueConnectionFactoryNameTest()
      throws Exception
  {
    when(
        this.testClass.extAccess
            .getSystemConfigValue(AASParallelConstants.SYSCONF_QUEUE_CONN_FACTORY_NAME))
        .thenReturn("GHI");

    // Call method in test
    when(this.testClass.getQueueConnectionFactoryName()).thenCallRealMethod();
    String retval = this.testClass.getQueueConnectionFactoryName();
    assertNotNull(retval);
    assertTrue("GHI".equals(retval));
  }

  @Test
  public void extractMitchellEnvelopeFromMessageTest()
      throws Exception
  {
    TextMessage msg = mock(TextMessage.class);

    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    meDoc.addNewMitchellEnvelope().setVersion("THIS IS A TEST");
    when(msg.getText()).thenReturn(meDoc.toString());

    // Call method in test
    when(this.testClass.extractMitchellEnvelopeFromMessage(msg))
        .thenCallRealMethod();
    MitchellEnvelopeDocument retval = this.testClass
        .extractMitchellEnvelopeFromMessage(msg);
    assertNotNull(retval);
    assertTrue(meDoc.toString().equals(retval.toString()));

  }

  @Test
  public void submitResponseTest()
      throws Exception
  {
    TextMessage tMsg = mock(TextMessage.class);
    String msg = new String("ABC");

    when(this.testClass.getQueueConnectionFactoryName()).thenReturn("QCF");

    when(this.testClass.newJMSParallelManager("QCF")).thenReturn(this.pMgr);

    // Call method in test
    doCallRealMethod().when(this.testClass).submitResponse(tMsg, msg);
    this.testClass.submitResponse(tMsg, msg);

    verify(this.testClass).getQueueConnectionFactoryName();
    verify(this.testClass).newJMSParallelManager("QCF");
    verify(this.pMgr).submitProcessingResponse(tMsg, msg);

  }

  @Test
  public void retrieveResponsesTest()
      throws Exception
  {
    String responseGroupId = "abc";
    long maxNumberToReceive = 1234;

    when(this.testClass.getQueueConnectionFactoryName()).thenReturn("QCF");
    when(this.testClass.getParallelResponseQueueName()).thenReturn("RESP");
    when(this.testClass.newJMSParallelManager("QCF", "RESP")).thenReturn(
        this.pMgr);

    List<Message> aList = new ArrayList<Message>();
    TextMessage t1 = mock(TextMessage.class);
    when(t1.getText()).thenReturn("T1");
    aList.add(t1);
    TextMessage t2 = mock(TextMessage.class);
    when(t2.getText()).thenReturn("T2");
    aList.add(t2);
    when(
        this.pMgr.retrieveProcessingResponses(responseGroupId,
            maxNumberToReceive)).thenReturn(aList);

    // Call method in test
    when(this.testClass.retrieveResponses(responseGroupId, maxNumberToReceive))
        .thenCallRealMethod();
    List<String> retval = this.testClass.retrieveResponses(responseGroupId,
        maxNumberToReceive);
    assertNotNull(retval);
    assertTrue(retval.size() == 2);

    verify(this.testClass).getQueueConnectionFactoryName();
    verify(this.testClass).getParallelResponseQueueName();
    verify(this.pMgr).retrieveProcessingResponses(responseGroupId,
        maxNumberToReceive);

    assertTrue("T1".equals(retval.get(0)));
    assertTrue("T2".equals(retval.get(1)));

  }

  @Test
  public void retrieveResponsesTestNullList()
      throws Exception
  {
    String responseGroupId = "abc";
    long maxNumberToReceive = 1234;

    when(this.testClass.getQueueConnectionFactoryName()).thenReturn("QCF");
    when(this.testClass.getParallelResponseQueueName()).thenReturn("RESP");
    when(this.testClass.newJMSParallelManager("QCF", "RESP")).thenReturn(
        this.pMgr);

    List<Message> aList = null;
    when(
        this.pMgr.retrieveProcessingResponses(responseGroupId,
            maxNumberToReceive)).thenReturn(aList);

    // Call method in test
    when(this.testClass.retrieveResponses(responseGroupId, maxNumberToReceive))
        .thenCallRealMethod();
    List<String> retval = this.testClass.retrieveResponses(responseGroupId,
        maxNumberToReceive);
    assertNull(retval);

    verify(this.testClass).newJMSParallelManager("QCF", "RESP");
    verify(this.testClass).getQueueConnectionFactoryName();
    verify(this.testClass).getParallelResponseQueueName();

  }

  @Test
  public void submitRequestsTest()
      throws Exception
  {
    List<String> meStrings = new ArrayList<String>();

    when(this.testClass.getQueueConnectionFactoryName()).thenReturn("QCF");
    when(this.testClass.getParallelResponseQueueName()).thenReturn("RESP");
    when(this.testClass.getParallelMDBInputQueueName()).thenReturn("INP");
    when(this.testClass.newJMSParallelManager("QCF", "INP", "RESP"))
        .thenReturn(this.pMgr);

    when(this.pMgr.submitProcessingRequests(meStrings)).thenReturn("JHBU");

    // Call method in test
    when(this.testClass.submitRequests(meStrings)).thenCallRealMethod();
    String retval = this.testClass.submitRequests(meStrings);
    assertTrue("JHBU".equals(retval));

    verify(this.testClass).newJMSParallelManager("QCF", "INP", "RESP");
    verify(this.pMgr).submitProcessingRequests(meStrings);
    verify(this.testClass).getQueueConnectionFactoryName();
    verify(this.testClass).getParallelResponseQueueName();
    verify(this.testClass).getParallelMDBInputQueueName();

  }
}
