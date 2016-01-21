package com.mitchell.services.business.partialloss.appraisalassignment.pmdb;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.ejb.MessageDrivenContext;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASParallelRequestManager;
import com.mitchell.services.business.partialloss.appraisalassignment.process.ErrorLoggingProcessRemote;

public class AASParallelMDBTest
{
  protected AASParallelMDB testClass;

  @Before
  public void setUp()
      throws Exception
  {
    this.testClass = mock(AASParallelMDB.class);
    this.testClass.parallelProcessor = mock(AASParallelRequestManager.class);
    this.testClass.messageCtx = mock(MessageDrivenContext.class);
    this.testClass.errorLogProcess = mock(ErrorLoggingProcessRemote.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
  }

  @Test
  public void onMessageTest()
      throws Exception
  {
    TextMessage msg = mock(TextMessage.class);

    when(msg.getText()).thenReturn("hello");

    // Call method in test
    doCallRealMethod().when(this.testClass).onMessage(msg);
    this.testClass.onMessage(msg);

    verify(this.testClass.parallelProcessor).handleProcessingRequest(msg);

  }

  @Test
  public void onMessageTestMitchellException()
      throws Exception
  {
    TextMessage msg = mock(TextMessage.class);

    MitchellException meThrow = new MitchellException("a", "bb", "ccc");
    when(msg.getText()).thenReturn("hello");
    when(this.testClass.getIsRedelivered(msg)).thenReturn(false);
    doThrow(meThrow).when(this.testClass.parallelProcessor)
        .handleProcessingRequest(msg);

    // Call method in test
    doCallRealMethod().when(this.testClass).onMessage(msg);
    this.testClass.onMessage(msg);

    verify(this.testClass.messageCtx).setRollbackOnly();

    verify(this.testClass).getIsRedelivered(msg);

    verify(this.testClass.errorLogProcess).handleMDBException(false,
        this.testClass.getClass().getName(), "OnMessage", meThrow,
        "MitchellException: " + meThrow.getMessage(),
        AppraisalAssignmentConstants.ERROR_UNEXPECTED_EXCEPTION, "hello");
  }

  @Test
  public void onMessageTestThrowable()
      throws Exception
  {
    TextMessage msg = mock(TextMessage.class);

    IllegalArgumentException meThrow = new IllegalArgumentException("dddd");
    when(msg.getText()).thenReturn("hello");
    when(this.testClass.getIsRedelivered(msg)).thenReturn(false);
    doThrow(meThrow).when(this.testClass.parallelProcessor)
        .handleProcessingRequest(msg);

    // Call method in test
    doCallRealMethod().when(this.testClass).onMessage(msg);
    this.testClass.onMessage(msg);

    verify(this.testClass.messageCtx).setRollbackOnly();

    verify(this.testClass).getIsRedelivered(msg);

    verify(this.testClass.errorLogProcess).handleMDBException(false,
        this.testClass.getClass().getName(), "OnMessage", meThrow,
        "Unexpected Exception: " + meThrow.getMessage(),
        AppraisalAssignmentConstants.ERROR_UNEXPECTED_EXCEPTION, "hello");
  }

}
