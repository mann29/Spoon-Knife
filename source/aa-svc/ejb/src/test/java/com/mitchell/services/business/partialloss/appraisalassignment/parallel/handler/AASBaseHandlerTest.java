package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;
import com.mitchell.utils.xml.MIEnvelopeHelper;

public class AASBaseHandlerTest
{
  protected AASBaseHandler testClass;
  protected IntegrationTestHelperClient itHelper;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AASBaseHandler.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  @Test
  public void extractLoggedInUserInfoTest()
      throws Exception
  {
    MIEnvelopeHelper meHelper = mock(MIEnvelopeHelper.class);

    EnvelopeBodyType envelopeBodyUserInfo = EnvelopeBodyType.Factory
        .newInstance();
    when(
        meHelper.getEnvelopeBody(AASParallelConstants.ME_LOGGED_IN_USERINFO_ID))
        .thenReturn(envelopeBodyUserInfo);

    UserInfoDocument uiDoc = UserInfoDocument.Factory.newInstance();
    uiDoc.addNewUserInfo().setUserID("me");
    String userInfoStr = uiDoc.toString();

    when(meHelper.getEnvelopeBodyContentAsString(envelopeBodyUserInfo))
        .thenReturn(userInfoStr);

    // Call method in test
    when(this.testClass.extractLoggedInUserInfo(meHelper)).thenCallRealMethod();
    UserInfoDocument retval = this.testClass.extractLoggedInUserInfo(meHelper);
    assertNotNull(retval);

    verify(meHelper).getEnvelopeBody(
        AASParallelConstants.ME_LOGGED_IN_USERINFO_ID);
    verify(meHelper).getEnvelopeBodyContentAsString(envelopeBodyUserInfo);

    assertTrue(uiDoc.getUserInfo().getUserID()
        .equals(retval.getUserInfo().getUserID()));

  }
}
