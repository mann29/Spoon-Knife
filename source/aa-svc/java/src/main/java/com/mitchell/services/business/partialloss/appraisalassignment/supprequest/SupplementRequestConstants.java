package com.mitchell.services.business.partialloss.appraisalassignment.supprequest;

import com.mitchell.utils.misc.ErrorCode;

public class SupplementRequestConstants 
{
  
// App/Module
  public static final String APP_NAME = "PARTIALLOSS";
  public static final String MODULE_NAME = "APPRAISAL_ASSIGNMENT_SERVICE";

  public static final String SUPPLEMENT_REQUEST_PREFIX = "SupplementRequest_";
  public static final String FILE_EXTENSION_TXT = ".txt";
  
  public static final String SPACE=" ";
  
// Error Codes
  public static ErrorCode ERR_NOSUCHUSER = new ErrorCode( 108348, "No user found in userinfo service." );  
  public static ErrorCode ERR_SUBMIT_SUPPLEMENT_REQUEST = new ErrorCode( 108349, "Error occurred in submitting the supplement request " );

} 
