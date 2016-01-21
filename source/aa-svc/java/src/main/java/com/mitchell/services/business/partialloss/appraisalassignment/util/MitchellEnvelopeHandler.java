package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.util.Calendar;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.VehicleLocationGeoCodeType.AddressValidStatus;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public interface MitchellEnvelopeHandler
{

  public AssignmentAddRqDocument fetchAssignmentAddRqDocument(
      MitchellEnvelopeHelper meHelper)
      throws Exception;

  public void assignMitchellEnvelope(Long documentId, String dispatchCenter,
      UserInfoDocument assigneeInfo, UserDetailDocument assigneeUserDetail,
      String userType, Calendar scheduleDateTime,
      UserInfoDocument logdInUsrInfo, String workItemID)
      throws Exception;

  public long getEstimateDocId(MitchellEnvelopeHelper mitchellEnvelopeHelper)
      throws MitchellException;

  public AddressValidStatus.Enum validateInspectionSiteAddress(
      MitchellEnvelopeHelper meHelper, UserInfoDocument logdInUsrInfo)
      throws MitchellException;

  public CIECADocument getCiecaFromME(MitchellEnvelopeHelper meHelper)
      throws Exception;

  public AdditionalTaskConstraintsDocument getAdditionalTaskConstraintsFromME(
      MitchellEnvelopeHelper meHelper)
      throws Exception;

  public void updateBMS(Long documentId, Long tcn,
      MitchellEnvelopeDocument mitchellEnvDoc, UserInfoDocument logdInUsrInfo)
      throws MitchellException;

  public CIECADocument getCiecaDocFromMitchellEnv(
      MitchellEnvelopeDocument mEnvDoc, String workItemId)
      throws Exception, MitchellException;

  public Long saveBMS(long claimId, long claimExposureId,
      long relatedEstimateDocID, MitchellEnvelopeDocument mitchellEnvDoc,
      UserInfoDocument logdInUsrInfo)
      throws Exception;

  public void unScheduleMitchellEnvelope(Long documentId,
      UserInfoDocument logdInUsrInfo)
      throws Exception;

  public void unScheduleMitchellEnvelope(final Long documentId,
	      final UserInfoDocument logdInUsrInfo,ItineraryViewDocument itineraryXML)
	      throws Exception;
  
  public String getAssigneeIdFromCiecaDocument(CIECADocument ciecaDocument);

  public String getGroupCodeFromCiecaDocument(CIECADocument ciecaDocument);

  public Calendar getScheduleDateTime(
      AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument);

  public AdditionalAppraisalAssignmentInfoDocument getAdditionalAppraisalAssignmentInfoDocumentFromME(
      MitchellEnvelopeHelper mitchellEnvelopeHelper)
      throws Exception;

  public void updateGroupIdInMitchellEnvelope(final Long documentId,
      final String workItemID, final String claimNumber,
      final String dispatchCenter,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException;

  public long getMOIOrgIdFromMitchellEnvelop(
      final MitchellEnvelopeHelper mitchellEnvelopeHelper)
      throws Exception;

  public boolean checkServiceCenterFromMitchellEnvelop(
      final MitchellEnvelopeHelper mitchellEnvelopeHelper)
      throws Exception;
}
