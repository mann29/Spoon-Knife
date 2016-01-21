package com.mitchell.services.business.partialloss.assignmentdelivery;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BmsExtPhoneFormatTest
{

  @Test
  public void formatCieca210PhoneNoTest()
      throws Exception
  {
    // Call method in test
    String testPhoneNumber = "+001-858-4446551A+1234567890";
    String retPhone = BmsExtPhoneFormat.formatCieca210PhoneNo(testPhoneNumber);
    assertTrue("8584446551A".equals(retPhone));

  }

  @Test
  public void formatCieca210PhoneNoTest2()
      throws Exception
  {
    // Call method in test
    String testPhoneNumber = "+91-858-1116550+11";
    String retPhone = BmsExtPhoneFormat.formatCieca210PhoneNo(testPhoneNumber);
    assertTrue("8581116550".equals(retPhone));

  }

}
