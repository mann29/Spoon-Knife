package com.mitchell.services.business.partialloss.appraisalassignment.parallel;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;

public class AASJMSParallelManagerTest
{
  protected AASJMSParallelManager testClass;
  protected IntegrationTestHelperClient itHelper;

  @Before
  public void setUp()
      throws Exception
  {
    this.itHelper = new IntegrationTestHelperClient();

    this.testClass = mock(AASJMSParallelManager.class);
    this.testClass.extProxy = mock(AASExternalAccessor.class);

  }

  @After
  public void tearDown()
      throws Exception
  {
    this.testClass = null;
    this.itHelper = null;
  }

  @Test
  public void getSysConfLongTest()
      throws Exception
  {
    String settingName = "aName";
    long defaultValue = 42;

    when(this.testClass.extProxy.getSystemConfigValue(settingName)).thenReturn(
        "33");

    // Call method in test
    when(this.testClass.getSysConfLong(settingName, defaultValue))
        .thenCallRealMethod();
    long retval = this.testClass.getSysConfLong(settingName, defaultValue);
    assertTrue(retval == 33);

  }

  @Test
  public void getSysConfLongTestDefaultEmpty()
      throws Exception
  {
    String settingName = "aName";
    long defaultValue = 42;

    when(this.testClass.extProxy.getSystemConfigValue(settingName)).thenReturn(
        "");

    // Call method in test
    when(this.testClass.getSysConfLong(settingName, defaultValue))
        .thenCallRealMethod();
    long retval = this.testClass.getSysConfLong(settingName, defaultValue);
    assertTrue(retval == 42);

  }

  @Test
  public void getSysConfLongTestDefaultNull()
      throws Exception
  {
    String settingName = "aName";
    long defaultValue = 42;

    when(this.testClass.extProxy.getSystemConfigValue(settingName)).thenReturn(
        (String) null);

    // Call method in test
    when(this.testClass.getSysConfLong(settingName, defaultValue))
        .thenCallRealMethod();
    long retval = this.testClass.getSysConfLong(settingName, defaultValue);
    assertTrue(retval == 42);

  }

}
