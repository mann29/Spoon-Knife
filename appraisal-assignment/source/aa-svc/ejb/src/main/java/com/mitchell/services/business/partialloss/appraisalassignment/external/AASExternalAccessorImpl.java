package com.mitchell.services.business.partialloss.appraisalassignment.external;

import javax.ejb.Stateless;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.systemconfiguration.SystemConfiguration;
import com.mitchell.utils.xml.MIEnvelopeHelper;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * External Access class.
 * 
 */
@Stateless
public class AASExternalAccessorImpl implements AASExternalAccessor
{

  /**
   * ErrorLoggingService
   */
  public void logCommonError(MitchellException me)
  {
    ErrorLoggingService.logCommonError(me);
  }

  /**
   * ErrorLoggingService
   */
  public void logError(MitchellException me)
  {
    ErrorLoggingService.logError(me);
  }

  /**
   * ErrorLoggingService
   */
  public String logError(int errorType, String correlationId, String className,
      String methodName, String severity, String workItemID,
      String description, String companyCode, int orgID, String details,
      String detailType)
  {
    return ErrorLoggingService.logError(errorType, correlationId, className,
        methodName, severity, workItemID, description, companyCode, orgID,
        details, detailType);
  }

  /**
   * Isolate MitchellEnvelopeHelper because it is not Mockable.
   */
  public MIEnvelopeHelper buildMEHelper(MitchellEnvelopeDocument meDoc)
  {
    return new MitchellEnvelopeHelper(meDoc);
  }

  /**
   * SystemConfiguration
   */
  public String getSystemConfigValue(String settingName)
  {
    return SystemConfiguration.getSettingValue(settingName);
  }

}