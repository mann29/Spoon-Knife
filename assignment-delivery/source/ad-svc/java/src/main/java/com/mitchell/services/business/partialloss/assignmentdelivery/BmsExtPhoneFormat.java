package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mitchell.utils.misc.AppUtilities;

public class BmsExtPhoneFormat
{

  private static final String PHONE_REGEX_1 = "(\\+\\d{1,5}-\\d{1,5}-|\\d{1,5}-){0,1}([a-zA-Z0-9]{3,8})(\\+\\d{1,10}){0,1}";
  private static final String PHONE_REGEX_2 = "(\\+\\d{1,5})(-\\d{1,5}-)|(\\d{1,5}-)";

  private static final Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.assignmentdelivery.BmsExtPhoneFormat");

  /*
   * Utility method to format the phone number field from MCF xml to store in
   * the claim DB. Claim DB accepts 10 Varchar. This method removes all "-",
   * "+" and prefix "1". Also extract extension out of number to store
   * separately.
   * 
   * @param phoneNo : <code>String</code> Phone number string to format
   * 
   * @return String : <code>String</code> 10 digit phone number
   */
  public static String formatCieca210PhoneNo(String phoneNo)
  {

    String result[] = new String[4];

    Pattern pattern;
    Matcher matcher;
    boolean found = false;

    pattern = Pattern.compile(PHONE_REGEX_1);
    matcher = pattern.matcher(phoneNo);

    try {
      found = matcher.find();
      if (found) {
        for (int i = 0; i <= matcher.groupCount(); i++) {
          if (i > 0) {
            result[i] = matcher.group(i);
          }
        }
      }
    } catch (IllegalStateException ex) {
      logger.warning("Could not parse the phone number :" + phoneNo);
      logger.warning(AppUtilities.getStackTraceString(ex, true));
    }
    found = false;

    if (result[1] != null) {
      try {
        pattern = Pattern.compile(PHONE_REGEX_2);
        matcher = pattern.matcher(result[1]);
        found = matcher.find();
        if (found) {
          for (int i = 0; i <= matcher.groupCount(); i++) {
            if (i > 0) {
              if (matcher.group(i) != null) {
                if (i == 3) {
                  result[i - 2] = matcher.group(i);
                } else {
                  result[i - 1] = matcher.group(i);
                }
              }
            }
          }
        }
      } catch (IllegalStateException ex) {
        logger.warning("Could not parse the local and country number :"
            + phoneNo);
        logger.warning(AppUtilities.getStackTraceString(ex, true));
      }
    }

    if (result != null && result.length > 0)
      for (int i = 0; i < result.length; i++) {
        if (result[i] != null) {
          result[i] = cleanString(result[i]);
        }
      }

    StringBuilder retPhone = new StringBuilder();
    for (int i = 0; i < result.length; i++) {
      if (i == 1 || i == 2) {
        retPhone.append(result[i]);
      }
    }

    return retPhone.toString();
  }

  public static String cleanString(String input)
  {
    String retVal = input.trim();
    retVal = retVal.replaceAll("\\+", "");
    retVal = retVal.replace('-', ' ');
    retVal = retVal.trim();
    return retVal;
  }

}
