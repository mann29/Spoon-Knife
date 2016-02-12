package com.mitchell.services.business.questionnaireevaluation.proxy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;

public class ErrorLogProxyImplTest
{

  @Test
  public void updateToDefaultTestLessThanMin()
      throws Exception
  {
    ErrorLogProxyImpl testClass = new ErrorLogProxyImpl();

    MitchellException me = new MitchellException(-1, "a", "b", "c");

    // Call method in test
    MitchellException retval = testClass.updateToDefault(me);
    assertNotNull(retval);

    assertTrue(retval.getType() == QuestionnaireEvaluationConstants.ERROR_UNKNOWN);
    assertTrue(QuestionnaireEvaluationConstants.APPLICATION_NAME.equals(retval
        .getApplicationName()));
    assertTrue(QuestionnaireEvaluationConstants.MODULE_NAME.equals(retval
        .getModuleName()));

  }

  @Test
  public void updateToDefaultTestGreaterThanMax()
      throws Exception
  {
    ErrorLogProxyImpl testClass = new ErrorLogProxyImpl();

    MitchellException me = new MitchellException(
        QuestionnaireEvaluationConstants.MAX_QEVAL_ERROR_CODE + 1, "a", "b",
        "c");

    // Call method in test
    MitchellException retval = testClass.updateToDefault(me);
    assertNotNull(retval);

    assertTrue(retval.getType() == QuestionnaireEvaluationConstants.ERROR_UNKNOWN);
    assertTrue(QuestionnaireEvaluationConstants.APPLICATION_NAME.equals(retval
        .getApplicationName()));
    assertTrue(QuestionnaireEvaluationConstants.MODULE_NAME.equals(retval
        .getModuleName()));

  }

  @Test
  public void updateToDefaultTestNoChangeMin()
      throws Exception
  {
    ErrorLogProxyImpl testClass = new ErrorLogProxyImpl();

    MitchellException me = new MitchellException(
        QuestionnaireEvaluationConstants.MIN_QEVAL_ERROR_CODE, "a", "b", "c");
    me.setApplicationName("d");
    me.setModuleName("e");

    // Call method in test
    MitchellException retval = testClass.updateToDefault(me);
    assertNotNull(retval);

    assertTrue(retval.getType() == QuestionnaireEvaluationConstants.MIN_QEVAL_ERROR_CODE);
    assertTrue(QuestionnaireEvaluationConstants.APPLICATION_NAME.equals(retval
        .getApplicationName()));
    assertTrue(QuestionnaireEvaluationConstants.MODULE_NAME.equals(retval
        .getModuleName()));

  }

  @Test
  public void updateToDefaultTestNoChangeMax()
      throws Exception
  {
    ErrorLogProxyImpl testClass = new ErrorLogProxyImpl();

    MitchellException me = new MitchellException(
        QuestionnaireEvaluationConstants.MAX_QEVAL_ERROR_CODE, "a", "b", "c");
    me.setApplicationName("d");
    me.setModuleName("e");

    // Call method in test
    MitchellException retval = testClass.updateToDefault(me);
    assertNotNull(retval);

    assertTrue(retval.getType() == QuestionnaireEvaluationConstants.MAX_QEVAL_ERROR_CODE);
    assertTrue(QuestionnaireEvaluationConstants.APPLICATION_NAME.equals(retval
        .getApplicationName()));
    assertTrue(QuestionnaireEvaluationConstants.MODULE_NAME.equals(retval
        .getModuleName()));

  }

}
