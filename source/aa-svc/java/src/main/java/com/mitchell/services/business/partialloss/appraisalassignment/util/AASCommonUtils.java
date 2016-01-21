package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.util.Calendar;
import java.util.logging.Logger;

import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.task.TaskDocument;
import com.mitchell.schemas.workassignment.PriorityType;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.core.geoservice.dto.GeoResult;

public interface AASCommonUtils
{

  public String getSystemConfigurationSettingValue(String settingName,
      String defaultValue);

  public MitchellException logError(int errorType, java.lang.String classNm,
      java.lang.String methodNm, java.lang.String desc, java.lang.Throwable t);

  public MitchellException createMitchellException(int errorType,
      java.lang.String classNm, java.lang.String methodNm,
      java.lang.String desc, java.lang.Throwable t);

  public void logAndThrowError(int errorType, java.lang.String classNm,
      java.lang.String methodNm, java.lang.String desc, java.lang.Throwable t,
      Logger outLogger)
      throws MitchellException;

  public void validateTaskDocument(TaskDocument taskDocument, String msgDetail,
      String workItemID, UserInfoDocument logdInUsrInfo)
      throws MitchellException;

  public XmlOptions getXmlOptions(java.util.ArrayList validationErrorsArrayList);

  public boolean validate(XmlObject doc);

  public String getXmlValidationErrors(
      java.util.ArrayList validationErrorsArrayList);

  public void saveExposureActivityLog(String comment, Long exposureId,
      Long referenceId, String workItemID, UserInfoDocument logdInUsrInfo)
      throws Exception;

  public void saveClaimExpActivtyLogForSuppRequestDoc(String comment,
      Long exposureId, Long referenceId, UserInfoDocument logdInUsrInfo)
      throws Exception;

  public GeoResult validateAddress(String street, String city, String state,
      String zip, String country)
      throws Exception;

  public void doDriveInAppointmentActivityLog(Long claimExposureId,
      Calendar scheduleDateTime, UserInfoDocument logedInUserInfoDocument,
      String workItemID,
      AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo, AppraisalAssignmentDTO inputAppraisalAssignmentDTO)
      throws Exception;

  public PriorityType convertPriorityToString(int priorityTypeInt) throws MitchellException;
  
  public com.mitchell.schemas.dispatchservice.PriorityType priorityTypeToString(int priorityTypeInt)
			throws MitchellException;
}
