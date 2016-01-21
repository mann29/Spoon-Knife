package com.mitchell.services.business.partialloss.appraisalassignment.external;

import javax.ejb.Local;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.utils.xml.MIEnvelopeHelper;

@Local
public interface AASExternalAccessor
{

  public void logCommonError(MitchellException me);

  public void logError(MitchellException me);

  public String logError(int errorType, String correlationId, String className,
      String methodName, String severity, String workItemID,
      String description, String companyCode, int orgID, String details,
      String detailType);

  public MIEnvelopeHelper buildMEHelper(MitchellEnvelopeDocument meDoc);

  public String getSystemConfigValue(String settingName);

}

