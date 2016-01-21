package com.mitchell.services.business.partialloss.appraisalassignment.ejb.misc;

import com.mitchell.common.aop.MitchellCommonExceptionInterceptor;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;

/**
 * AppraisalAssignmentService Exception Intercepter.
 * 
 */
public class AASExceptionIntercepter extends MitchellCommonExceptionInterceptor
{
  public AASExceptionIntercepter()
  {
  }

  protected String getAppName()
  {
    return AppraisalAssignmentConstants.APP_NAME;
  }

  protected String getModuleName()
  {
    return AppraisalAssignmentConstants.MODULE_NAME;
  }

}
