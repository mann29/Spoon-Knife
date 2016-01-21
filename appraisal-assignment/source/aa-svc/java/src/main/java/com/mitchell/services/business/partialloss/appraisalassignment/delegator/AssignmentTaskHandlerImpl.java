package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.logging.Level;

import org.apache.xmlbeans.XmlException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.APDRequestStaffSupplementInfoType;
import com.mitchell.schemas.apddelivery.APDRequestStaffSupplementRejectedReasonType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.workassignment.AssignorInfoType;
import com.mitchell.schemas.workassignment.EventDefinitionListType;
import com.mitchell.schemas.workassignment.EventDefinitionType;
import com.mitchell.schemas.workassignment.PersonInfoType;
import com.mitchell.schemas.workassignment.PrimaryIDsType;
import com.mitchell.schemas.workassignment.PriorityType;
import com.mitchell.schemas.workassignment.ScheduleInfoType;
import com.mitchell.schemas.workassignment.ScheduleMethodType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentReferenceType;
import com.mitchell.schemas.workassignment.WorkAssignmentStatusType;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.AASUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.APDProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.AppLogProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ClaimProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ErrorLogProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.WorkAssignmentProxy;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.services.core.workassignment.dao.DAOException;
import com.mitchell.services.technical.claim.common.DTO.ClaimInfoDTO;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;

/**
 * This class is the implementation of AssignmentTaskHandler.
 * 
 * Please note the singleton approach of this class. If in future, any state of
 * the class
 * is required, the approach needs to be revisited.
 * 
 * @author xs97167
 */
public final class AssignmentTaskHandlerImpl implements AssignmentTaskHandler
{
  private static final String CLASS_NAME = AssignmentTaskHandlerImpl.class
      .getName();
  private static final java.util.logging.Logger LOGGER = java.util.logging.Logger
      .getLogger(CLASS_NAME);
  private static final String REQ_SUPP_AGN_WA_TYPE = "REQ_SUPP_ASGN";
  private static final String APD_MSG_TYPE = "REQ_SUPP_ASGN";
  private static final String DISP_NEW = "NEW";
  private static final String DISP_CANCELLED = "CANCELLED";
  private static final String DISP_REJECTED = "REJECTED";
  private static final String APD_MSG_STATUS = "REJECTED";

  private static final String EST_DOC_REF_TYPE = "ESTIMATE_DOC";

  private static AssignmentTaskHandler handler = new AssignmentTaskHandlerImpl();

  private APDProxy apdProxy;
  private AppLogProxy appLogProxy;
  private ClaimProxy claimProxy;
  private ErrorLogProxy errorLogProxy;
  private EstimatePackageProxy estimatePackageProxy;
  private UserInfoProxy userInfoProxy;
  private WorkAssignmentProxy workAssignmentProxy;
  private AASUtils aasUtils;

  /**
   * No state is involved in any instance variable. Therefore the sigleton
   * approach
   * is still valid.
   * 
   * @return
   */
  public static AssignmentTaskHandler getHandler()
  {
    return handler;
  }

  private AssignmentTaskHandlerImpl()
  {
  }

  public void createSupplementTask(final String claimSuffixNumber,
      final UserInfoDocument bodyShopUserInfo, final String workItemId,
      final String note, final String reviewCoCd, final String reviewUserId)
      throws MitchellException
  {

    checkInput(claimSuffixNumber, bodyShopUserInfo, reviewCoCd, reviewUserId);
    final UserInfoDocument reviewerUserInfo = userInfoProxy.getUserInfo(
        reviewCoCd, reviewUserId);

    final AssignmentTaskContext context = new AssignmentTaskContext();
    context.setClaimSuffixNumber(claimSuffixNumber);
    context.setReviewerUserInfo(reviewerUserInfo);
    context.setBodyShopUserInfo(bodyShopUserInfo);
    context.setWorkItemId(workItemId);
    context.setNote(note);

    readClaimAndExpoureInfo(context);
    boolean notifyAPD = false;
    if (context.getClaimID() > 0) {
      if (context.getExposureID() > 0) {
        final Estimate est = findLatestEstimate(context);
        if (est != null) {
          final WorkAssignment workAssignment = findOpenTask(context);
          if (workAssignment == null) {
            findEstimateDocInfo(est, context);
            final WorkAssignmentDocument workAssignmentDoc = createWorkAssignmentDocument(context);
            saveWorkAssignment(workAssignmentDoc);

            final String COMMENT_CREATE = "Request to Create Supplement Assignment Task Created";
            createSuffixActivityLog(COMMENT_CREATE, context);

            logEvent(
                AppraisalAssignmentConstants.APPLOG_CREATE_TASK_REQ_SUPP_SUCCESS,
                context);
          } else {
            //Open Task already exist status as Return message
            context.setRejectReason(AssignmentTaskContext.TASK_ALREADY_EXISTS);
            notifyAPD = true;
          }
        } else {
          //Estimate not exist status as Return message
          context.setRejectReason(AssignmentTaskContext.NO_ESTIMATE_AVAILABLE);
          notifyAPD = true;
        }
      } else {
        //Suffix not exist status as Return message
        context.setRejectReason(AssignmentTaskContext.NO_MATCHING_CLAIM_EXISTS);
        notifyAPD = true;
      }
    } else {
      //Claim Not Exist Status as Return message
      context.setRejectReason(AssignmentTaskContext.NO_MATCHING_CLAIM_EXISTS);
      notifyAPD = true;
    }

    if (notifyAPD) {
      invokeAPD(context);
      logEvent(AppraisalAssignmentConstants.APPLOG_APD_CALL_SUCCESS, context);
    }
  }
  
  
  public void createAssignSupplementTaskToNCRTUSer(final String claimSuffixNumber,
			final UserInfoDocument bodyShopUserInfo, final String workItemId, final String note, final String reviewCoCd,
			final String reviewUserId) throws MitchellException {

		checkInput(claimSuffixNumber, bodyShopUserInfo, reviewCoCd, reviewUserId);
		final UserInfoDocument reviewerUserInfo = userInfoProxy.getUserInfo(reviewCoCd, reviewUserId);

		final AssignmentTaskContext context = new AssignmentTaskContext();
		context.setClaimSuffixNumber(claimSuffixNumber);
		context.setReviewerUserInfo(reviewerUserInfo);
		context.setBodyShopUserInfo(bodyShopUserInfo);
		context.setWorkItemId(workItemId);
		context.setNote(note);

		readClaimAndExpoureInfo(context);
		boolean notifyAPD = false;
		if (context.getClaimID() > 0) {
			if (context.getExposureID() > 0) {
				final Estimate est = findLatestEstimate(context);
				if (est != null) {
					final WorkAssignment workAssignment = findOpenTask(context);
					if (workAssignment == null) {
						findEstimateDocInfo(est, context);
						final WorkAssignmentDocument workAssignmentDoc = createWorkAssignmentDocument(context);

						if (LOGGER.isLoggable(Level.INFO)) {
							LOGGER.info("Before manual assign to workAssignmentDoc : " + workAssignmentDoc.toString());
						}

						/*
						 * Add PersonInfo detail
						 */
						PersonInfoType personinfo = workAssignmentDoc.getWorkAssignment().getCurrentSchedule().getAssignee();

						if (personinfo == null) {
							ScheduleInfoType sInfo = workAssignmentDoc.getWorkAssignment().getCurrentSchedule();
							sInfo.setAssigneeID(reviewerUserInfo.getUserInfo().getUserID());
							sInfo.addNewAssignee();
							sInfo.getAssignee().addNewPersonName();
							sInfo.getAssignee().getPersonName().setFirstName(reviewerUserInfo.getUserInfo().getFirstName());
							sInfo.getAssignee().getPersonName().setLastName(reviewerUserInfo.getUserInfo().getLastName());

						} else if (personinfo != null && null != personinfo.getPersonName()) {
							personinfo.getPersonName().setFirstName(reviewerUserInfo.getUserInfo().getFirstName());
							personinfo.getPersonName().setLastName(reviewerUserInfo.getUserInfo().getLastName());
							workAssignmentDoc.getWorkAssignment().getCurrentSchedule()
									.setAssigneeID(reviewerUserInfo.getUserInfo().getUserID());
						}

						/*
						 * Set Assigner detail of bodyShopUserInfo in WAS Doc
						 */
						AssignorInfoType assinorInfo = workAssignmentDoc.getWorkAssignment().getAssignorInfo();
						if (assinorInfo != null && assinorInfo.getAssignor() != null) {
							assinorInfo.setAssignorID(bodyShopUserInfo.getUserInfo().getUserID());
							assinorInfo.getAssignor().getPersonName()
									.setFirstName(bodyShopUserInfo.getUserInfo().getFirstName());
							assinorInfo.getAssignor().getPersonName()
									.setLastName(bodyShopUserInfo.getUserInfo().getLastName());
						}

						/*
						 * Add Priority detail
						 */
						PriorityType priorityType = workAssignmentDoc.getWorkAssignment().getScheduleConstraints()
								.addNewPriority();
						priorityType.setStringValue("STANDARD_PRIORITY");
						priorityType.setPriorityValue(99);

						if (LOGGER.isLoggable(Level.INFO)) {
							LOGGER.info("After manual assign to workAssignmentDoc : " + workAssignmentDoc.toString());
						}

						saveWorkAssignment(workAssignmentDoc);

						final String COMMENT_CREATE = "Request to Create Supplement Assignment Task Created";
						createSuffixActivityLog(COMMENT_CREATE, context);

						logEvent(AppraisalAssignmentConstants.APPLOG_CREATE_TASK_REQ_SUPP_SUCCESS, context);
					} else {
						// Open Task already exist status as Return message
						context.setRejectReason(AssignmentTaskContext.TASK_ALREADY_EXISTS);
						notifyAPD = true;
					}
				} else {
					// Estimate not exist status as Return message
					context.setRejectReason(AssignmentTaskContext.NO_ESTIMATE_AVAILABLE);
					notifyAPD = true;
				}
			} else {
				// Suffix not exist status as Return message
				context.setRejectReason(AssignmentTaskContext.NO_MATCHING_CLAIM_EXISTS);
				notifyAPD = true;
			}
		} else {
			// Claim Not Exist Status as Return message
			context.setRejectReason(AssignmentTaskContext.NO_MATCHING_CLAIM_EXISTS);
			notifyAPD = true;
		}

		if (notifyAPD) {
			invokeAPD(context);
			logEvent(AppraisalAssignmentConstants.APPLOG_APD_CALL_SUCCESS, context);
		}
	}
  

  public void cancelSupplementTask(final String claimSuffixNumber,
      final UserInfoDocument bodyShopUserInfo, final String note,
      final String reviewCoCd, final String reviewUserId)
      throws MitchellException
  {

    checkInput(claimSuffixNumber, bodyShopUserInfo, reviewCoCd, reviewUserId);

    final UserInfoDocument reviewerUserInfo = userInfoProxy.getUserInfo(
        reviewCoCd, reviewUserId);

    final AssignmentTaskContext context = new AssignmentTaskContext();
    context.setClaimSuffixNumber(claimSuffixNumber);
    context.setReviewerUserInfo(reviewerUserInfo);
    context.setBodyShopUserInfo(bodyShopUserInfo);
    context.setNote(note);

    readClaimAndExpoureInfo(context);
    if (context.getClaimID() > 0) {
      if (context.getExposureID() > 0) {
        final WorkAssignment workAssignment = findOpenTask(context);
        if (workAssignment != null) {

          final WorkAssignmentDocument workAssignmentDoc = findWADocByTaskID(context);

          updateWorkAssignmentDocument(workAssignmentDoc, context,
              DISP_CANCELLED);
          saveWorkAssignment(workAssignmentDoc);

          final String COMMENT_CANCEL = "Request to Create Supplement Assignment Task Cancelled";
          createSuffixActivityLog(COMMENT_CANCEL, context);

          logEvent(
              AppraisalAssignmentConstants.APPLOG_CANCEL_TASK_REQ_SUPP_SUCCESS,
              context);
        }
      }
    }
  }

  private void invokeAPD(final AssignmentTaskContext context)
      throws MitchellException
  {

    final APDDeliveryContextDocument apdContextDoc = APDDeliveryContextDocument.Factory
        .newInstance();
    apdContextDoc.setAPDDeliveryContext(createAPDContext(context));

    apdProxy.deliverArtifact(apdContextDoc);

    if (LOGGER.isLoggable(Level.INFO)) {
      LOGGER.info("APD is invoked OK to send reason:"
          + context.getRejectReason());
    }

  }

  private APDDeliveryContextType createAPDContext(
      final AssignmentTaskContext context)
      throws MitchellException
  {
    final APDDeliveryContextType apdContextDoc = APDDeliveryContextType.Factory
        .newInstance();
    apdContextDoc.setMessageType(APD_MSG_TYPE);
    apdContextDoc.setVersion(apdContextDoc.getVersion());

    final APDRequestStaffSupplementInfoType reqStaffSuppInfoType = apdContextDoc
        .addNewAPDRequestStaffSupplementInfo();

    // rejected by system, not user
    reqStaffSuppInfoType.setSystemRejected(true);
    // no task to create
    reqStaffSuppInfoType.setTaskCreatedFlag(false);
    reqStaffSuppInfoType.setMessageStatus(APD_MSG_STATUS);
    reqStaffSuppInfoType
        .setRejectReason(APDRequestStaffSupplementRejectedReasonType.Enum
            .forString(context.getRejectReason()));

    final BaseAPDCommonType baseApdCommonType = reqStaffSuppInfoType
        .addNewAPDCommonInfo();

    if (context.getReviewerUserInfo() != null
        && context.getReviewerUserInfo().getUserInfo() != null) {
      baseApdCommonType.setInsCoCode(context.getReviewerUserInfo()
          .getUserInfo().getOrgCode());
      baseApdCommonType.addNewSourceUserInfo().setUserInfo(
          context.getReviewerUserInfo().getUserInfo());
      baseApdCommonType.addNewTargetDRPUserInfo().setUserInfo(
          context.getBodyShopUserInfo().getUserInfo());

      baseApdCommonType.addNewTargetUserInfo().setUserInfo(
          context.getReviewerUserInfo().getUserInfo());
    }

    if (context.getClaimSuffixNumber() != null) {
      baseApdCommonType.setClientClaimNumber(context.getClaimSuffixNumber());
    }

    baseApdCommonType.setWorkItemId(context.getWorkItemId());

    baseApdCommonType.setDateTime(java.util.Calendar.getInstance());
    return apdContextDoc;
  }

  /***************************************************************************
   * findLatestEstimate
   * Loop thru the estimateList to find out the latest estimate.
   * 
   * 
   * 
   * @return Estimate
   * @throws MitchellException
   *           *****************************************************************
   *           ***************************
   */
  private Estimate findLatestEstimate(final AssignmentTaskContext context)
      throws MitchellException
  {
    Estimate latestEstimate = null;
    try {

      final UserInfoDocument reviewerUserInfo = context.getReviewerUserInfo();
      final String claimSuffixNumber = context.getClaimSuffixNumber();

      // the null in the input is OK.
      final Estimate[] estimateList = estimatePackageProxy
          .findEstimateByClaimNumber(reviewerUserInfo.getUserInfo()
              .getOrgCode(), null, claimSuffixNumber);

      //Need to loop thru the estimateList to find out the latest estimate
      if (estimateList != null && estimateList.length > 0) {

        for (int i = 0; i < estimateList.length; i++) {
          Estimate currentEstimate = estimateList[i];
          if (latestEstimate != null) {
            // check commit date
            if (currentEstimate.getCommitDate().getTime() > latestEstimate
                .getCommitDate().getTime()) {
              latestEstimate = currentEstimate;
            } else if (currentEstimate.getCommitDate().getTime() == latestEstimate
                .getCommitDate().getTime()) {
              // Same commit date, check supplement number
              if (currentEstimate.getSupplementNumber().longValue() > latestEstimate
                  .getSupplementNumber().longValue()) {
                latestEstimate = currentEstimate;
              }
              // same supplement numbers, check correction number
              else if (currentEstimate.getSupplementNumber().longValue() == latestEstimate
                  .getSupplementNumber().longValue()) {
                if (currentEstimate.getCorrectionNumber().longValue() > latestEstimate
                    .getCorrectionNumber().longValue()) {
                  latestEstimate = currentEstimate;
                }
              }
            }
          } else {
            // First estimate found(if latestEstimate is null)
            latestEstimate = currentEstimate;
          }
        }
      }

    } catch (Exception e) {
      final String desc = "Exception in finding Estimate:" + e;
      errorLogProxy.logAndThrowError(
          AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE, CLASS_NAME,
          "findLatestEstimate", desc, e);
    }
    return latestEstimate;
  }

  private WorkAssignment findOpenTask(final AssignmentTaskContext context)
      throws MitchellException
  {
    WorkAssignment workAssignment = null;

    final long claimID = context.getClaimID();
    final long exposureID = context.getExposureID();

    if (claimID > 0 && exposureID > 0) {
      final UserInfoDocument reviewerUserInfo = context.getReviewerUserInfo();

      // can create constant for the assignment type
      final WorkAssignment[] waList = workAssignmentProxy
          .getWorkAssignmentsByClaimIdExposureId(reviewerUserInfo.getUserInfo()
              .getOrgCode(), claimID, exposureID,
              new String[] { REQ_SUPP_AGN_WA_TYPE });

      if (waList != null && waList.length > 0) {
        for (int i = 0; i < waList.length; i++) {
          WorkAssignment wa = waList[i];
          if (wa != null
              && WorkAssignmentStatusType.OPENED.toString().equals(
                  wa.getWorkAssignmentStatus())
              && isCreatedBySameUser(wa, reviewerUserInfo)) {
            workAssignment = wa;
            context.setTaskID(wa.getTaskID().longValue());
            break;
          }
        }
      }
    }

    return workAssignment;
  }

  private boolean isCreatedBySameUser(final WorkAssignment workAssignment,
      final UserInfoDocument userInfoDoc)
  {
    boolean flag = false;

    if (workAssignment != null && userInfoDoc != null
        && null != userInfoDoc.getUserInfo()) {

      if (workAssignment.getCreatedByUserId() != null
          && workAssignment.getCreatedByUserId().longValue() > 0) {
        if (String.valueOf(workAssignment.getCreatedByUserId()).equals(
            userInfoDoc.getUserInfo().getOrgID())) {
          flag = true;
        }
      } else if (workAssignment.getCreatedBy() != null) {
        if (workAssignment.getCreatedBy().equals(
            userInfoDoc.getUserInfo().getUserID())) {
          flag = true;
        }
      }
    }

    return flag;
  }

  private void findEstimateDocInfo(final Estimate est,
      final AssignmentTaskContext context)
      throws MitchellException
  {
    try {

      Document document = null;
      if (est.getId() != null) {

        document = estimatePackageProxy.getDocumentByEstimateId(est.getId()
            .longValue());
        if (document != null) {

          if (document.getServiceProviderId() != null) {
            final Long estimatorOrgId = document.getServiceProviderId();
            final UserInfoDocument estimatorInfo = userInfoProxy
                .getUserInfo(estimatorOrgId.longValue());
            context.setEstimatorUserInfo(estimatorInfo);
          }
          context.setEstimateDocID(document.getId().longValue());
        }
      }
    } catch (Exception e) {
      final String desc = "Exception in finding estimator:" + e;
      errorLogProxy.logAndThrowError(
          AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE, CLASS_NAME,
          "getEstimatorUserInfo", desc, e);
      // errorLogProxy.logAndThrowError(AppraisalAssignmentConstants.ERROR_REMOTE_EXCEPTION,CLASS_NAME,"getEstimatorUserInfo",desc,e);
    }
  }

  private WorkAssignmentDocument createWorkAssignmentDocument(
      final AssignmentTaskContext context)
      throws MitchellException
  {

    final WorkAssignmentDocument waDoc = WorkAssignmentDocument.Factory
        .newInstance();
    final WorkAssignmentType waType = waDoc.addNewWorkAssignment();
    waType.setVersion(waType.getVersion());
    waType.setType(REQ_SUPP_AGN_WA_TYPE);
    waType.setStatus(WorkAssignmentStatusType.OPENED);

    waType.setDisposition(DISP_NEW);

    if (context.getNote() != null && !"".equals((String) context.getNote()))
      waType.setWorkAssignmentMemo(context.getNote());
    // get all events
    final UserInfoDocument reviewerUserInfo = context.getReviewerUserInfo();

    final java.util.Map map = aasUtils.retrieveCarrierSettings(reviewerUserInfo
        .getUserInfo().getOrgCode());

    // register close and reject
    if (map.size() > 0) {
      final EventDefinitionListType subEvents = waType.addNewSubscribedEvents();

      Object event = null;

      // close event
      event = map.get(AppraisalAssignmentUtils.MAP_CLOSE_REQ_NAME);
      if (event != null && !"".equals((String) event)) {
        subEvents.setCompletedEvent(new BigInteger((String) event));
      }

      // reject event
      event = map.get(AppraisalAssignmentUtils.MAP_REJECT_REQ_NAME);
      if (event != null && !"".equals((String) event)) {
        subEvents.setCancelledEvent(new BigInteger((String) event));
      }
    }
    final com.mitchell.schemas.workassignment.EventInfoType eventInfo = waType
        .addNewEventInfo();
    // Setting EventDateTime as current DateTime
    eventInfo.setEventDateTime(Calendar.getInstance());
    eventInfo.setUpdatedByID(reviewerUserInfo.getUserInfo().getUserID());
    eventInfo.setEvent(EventDefinitionType.CREATED_EVENT);

    setWAPrimaryIds(waType, context);
    setWAScheduleConstraints(waType);

    final UserInfoDocument estimatorUserInfoDoc = context
        .getEstimatorUserInfo();

    if (estimatorUserInfoDoc != null
        && userInfoProxy.isUserStaff(estimatorUserInfoDoc)) {
      setWACurrentSchedule(waType, estimatorUserInfoDoc);
    } else {
      waType.addNewCurrentSchedule();
    }
    setWAAssignorInfo(waType, context);
    setWAReference(waType, context);

    if (LOGGER.isLoggable(java.util.logging.Level.FINEST))
      LOGGER.finest("Output WA DOC: " + waDoc);

    return waDoc;
  }

  private WorkAssignmentDocument updateWorkAssignmentDocument(
      final WorkAssignmentDocument workAssignmentDocument,
      final AssignmentTaskContext context, final String disp)
      throws MitchellException
  {

    final WorkAssignmentType waType = workAssignmentDocument
        .getWorkAssignment();
    waType.setStatus(WorkAssignmentStatusType.CANCELLED);
    waType.setDisposition(disp);

    if (!waType.isSetEventInfo()) {
      waType.addNewEventInfo();
    }
    final com.mitchell.schemas.workassignment.EventInfoType destEventInfo = waType
        .getEventInfo();
    // Setting EventDateTime as current DateTime
    destEventInfo.setEventDateTime(Calendar.getInstance());
    UserInfoDocument reviewerUserInfo = context.getReviewerUserInfo();
    if (reviewerUserInfo == null) {
      reviewerUserInfo = context.getEstimatorUserInfo();
    }

    destEventInfo.setUpdatedByID(reviewerUserInfo.getUserInfo().getUserID());
    destEventInfo.setEvent(EventDefinitionType.CANCELLED_EVENT);

    if (waType.getPrimaryIDs() != null
        && waType.getPrimaryIDs().getWorkItemID() != null) {
      context.setWorkItemId(waType.getPrimaryIDs().getWorkItemID());
    }
    return workAssignmentDocument;
  }

  private void saveWorkAssignment(final WorkAssignmentDocument workAssignmentDoc)
      throws MitchellException
  {

    workAssignmentProxy.save(workAssignmentDoc);

  }

  private void createSuffixActivityLog(final String comment,
      final AssignmentTaskContext context)
      throws MitchellException
  {
    final long exposureID = context.getExposureID();
    final UserInfoDocument reviewerUserInfo = context.getReviewerUserInfo();
    if (exposureID > 0) {
      claimProxy.writeExposureActLog(Long.valueOf(exposureID), comment,
          reviewerUserInfo, true);
    }
  }

  /***************************************************************************
   * Main WorkAssignment PrimaryIDs mapping.
   * ***************************************************************************
   * *****************
   */
  private void setWAPrimaryIds(final WorkAssignmentType waType,
      final AssignmentTaskContext context)
  {
    final PrimaryIDsType idType = waType.addNewPrimaryIDs();

    // setting "WorkItemID"
    if (context.getWorkItemId() == null) {
      final String workItemID = com.mitchell.utils.misc.UUIDFactory
          .getInstance().getCeicaUUID();
      idType.setWorkItemID(workItemID);
    } else {
      idType.setWorkItemID(context.getWorkItemId());
    }

    final UserInfoDocument reviewerUserInfo = context.getReviewerUserInfo();
    idType.setCompanyCode(reviewerUserInfo.getUserInfo().getOrgCode());

    final String claimNumber = context.getClaimNumber();
    idType.setClaimNumber(claimNumber);

    final String exposureNumber = context.getExposureNumber();
    idType.setClaimSuffix(exposureNumber);

    final long claimID = context.getClaimID();
    idType.setClaimID(claimID);

    final long exposureID = context.getExposureID();
    idType.setClaimExposureID(exposureID);

    final String claimSuffixNumber = context.getClaimSuffixNumber();
    idType.setClientClaimNumber(claimSuffixNumber);
  }

  /***************************************************************************
   * Main WorkAssignment Schedule Constraints mapping.
   * ***************************************************************************
   * *****************
   */
  private void setWAScheduleConstraints(final WorkAssignmentType waType)
  {
    final com.mitchell.schemas.workassignment.ScheduleConstraintsType scType = waType
        .addNewScheduleConstraints();
    scType.setScheduleMethod(ScheduleMethodType.MANUAL);
    final java.util.Calendar cal = java.util.Calendar.getInstance();
    scType.setDueDateTime(cal);
  }

  /***************************************************************************
   * Main WorkAssignment Current Schedule mapping.
   * ***************************************************************************
   * *****************
   */
  private void setWACurrentSchedule(final WorkAssignmentType waType,
      final UserInfoDocument estimatorUserInfoDoc)
  {
    final com.mitchell.schemas.workassignment.ScheduleInfoType sInfo = waType
        .addNewCurrentSchedule();
    sInfo.setAssigneeID(estimatorUserInfoDoc.getUserInfo().getUserID());
    sInfo.addNewAssignee();
    sInfo.getAssignee().addNewPersonName();
    sInfo.getAssignee().getPersonName()
        .setFirstName(estimatorUserInfoDoc.getUserInfo().getFirstName());
    sInfo.getAssignee().getPersonName()
        .setLastName(estimatorUserInfoDoc.getUserInfo().getLastName());
  }

  /***************************************************************************
   * Main WorkAssignment Assignor Information mapping.
   * ***************************************************************************
   * *****************
   */
  private void setWAAssignorInfo(final WorkAssignmentType waType,
      final AssignmentTaskContext context)
  {
    final com.mitchell.schemas.workassignment.AssignorInfoType assinorInfo = waType
        .addNewAssignorInfo();

    final UserInfoDocument reviewerUserInfo = context.getReviewerUserInfo();
    assinorInfo.setAssignorID(reviewerUserInfo.getUserInfo().getUserID());
    assinorInfo.addNewAssignor();
    assinorInfo.getAssignor().addNewPersonName();
    assinorInfo.getAssignor().getPersonName()
        .setFirstName(reviewerUserInfo.getUserInfo().getFirstName());
    assinorInfo.getAssignor().getPersonName()
        .setLastName(reviewerUserInfo.getUserInfo().getLastName());
  }

  private void setWAReference(final WorkAssignmentType waType,
      final AssignmentTaskContext context)
  {
    if (context.getEstimateDocID() > 0) {
      final WorkAssignmentReferenceType refType = waType
          .addNewWorkAssignmentReference();
      refType.addNewReferenceID();
      refType.getReferenceID().setReferenceType(EST_DOC_REF_TYPE);
      refType.getReferenceID().setID(
          new BigInteger(String.valueOf(context.getEstimateDocID())));
    }
  }

  private void logEvent(final int transactionType,
      final AssignmentTaskContext context)
      throws MitchellException
  {
    final AppLoggingDocument logDoc = AppLoggingDocument.Factory.newInstance();
    final AppLoggingType appType = logDoc.addNewAppLogging();
    final AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();

    appType.setTransactionType(String.valueOf(transactionType));
    appType.setWorkItemType(AppraisalAssignmentConstants.WORK_ITEM_TYPE);
    appType.setStatus(AppLogging.APPLOG_ST_OK);
    appType
        .setMitchellUserId(AppraisalAssignmentConstants.DEFAULT_WORKFLOW_USERID);
    appType.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);

    final UserInfoDocument reviewerUserInfo = context.getReviewerUserInfo();

    final String claimSuffixNumber = context.getClaimSuffixNumber();

    if (claimSuffixNumber != null) {
      appType.setClaimNumber(claimSuffixNumber);
    }
    if (context.getClaimID() > 0) {
      appType.setClaimId((int) context.getClaimID());
    }
    if (context.getExposureID() > 0) {
      appType.setClaimExposureId((int) context.getExposureID());
    }

    if (context.getRejectReason() != null) {
      appLoggingNVPairs.addPair("RejectReason", context.getRejectReason());
    }

    String workItemId = context.getWorkItemId();
    if (workItemId == null) {
      workItemId = claimSuffixNumber;
    }

    appLogProxy.logAppEvent(logDoc, reviewerUserInfo, workItemId,
        AppraisalAssignmentConstants.APP_NAME,
        AppraisalAssignmentConstants.MODULE_NAME, appLoggingNVPairs);
  }

  public void rejectSupplementTask(final long taskId,
      final UserInfoDocument estimatorUserInfo)
      throws MitchellException
  {
    checkInput(taskId, estimatorUserInfo);
    final AssignmentTaskContext context = new AssignmentTaskContext();
    context.setTaskID(taskId);
    context.setEstimatorUserInfo(estimatorUserInfo);
    context.setReviewerUserInfo(estimatorUserInfo); // Setting reviewerUserInfo to fix app log issue
    final WorkAssignmentDocument workAssignmentDocument = findWADocByTaskID(context);

    if (workAssignmentDocument != null) {
      updateWorkAssignmentDocument(workAssignmentDocument, context,
          DISP_REJECTED);
      if (workAssignmentDocument.getWorkAssignment().getPrimaryIDs() != null) {
        if (workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
            .isSetClaimExposureID()) {
          context.setExposureID(workAssignmentDocument.getWorkAssignment()
              .getPrimaryIDs().getClaimExposureID());
        }
        if (workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
            .isSetClientClaimNumber()) {
          context.setClaimSuffixNumber(workAssignmentDocument
              .getWorkAssignment().getPrimaryIDs().getClientClaimNumber());
        } else if (workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
            .getClaimNumber() != null) {
          context.setClaimSuffixNumber(workAssignmentDocument
              .getWorkAssignment().getPrimaryIDs().getClaimNumber());
        }

      }
      saveWorkAssignment(workAssignmentDocument);

      final String COMMENT_CREATE = "Reject Task of Create Supplement Assignment.";
      createSuffixActivityLog(COMMENT_CREATE, context);

      logEvent(
          AppraisalAssignmentConstants.APPLOG_REJECT_TASK_REQ_SUPP_SUCCESS,
          context);
    }
  }

  private WorkAssignmentDocument findWADocByTaskID(
      final AssignmentTaskContext context)
      throws MitchellException
  {
    WorkAssignmentDocument workAssignmentDocument = null;
    try {
      final Long taskId = Long.valueOf(context.getTaskID());

      final WorkAssignment workAssignment = workAssignmentProxy
          .getWorkAssignmentByTaskID(taskId);

      // you will get exception if workAssignment does not exist for the task id
      // the null check is graceful, but not the reality as of now
      if (workAssignment != null) {
        final String clob = workAssignment.getWorkAssignmentCLOBB();

        workAssignmentDocument = WorkAssignmentDocument.Factory.parse(clob);
      }

    } catch (DAOException e) {
      final String desc = "WorkAssignment Service DAO Exception:" + e;
      errorLogProxy.logAndThrowError(
          AppraisalAssignmentConstants.ERROR_WAS_DAO_EXCEPTION, CLASS_NAME,
          "findWADocByTaskID", desc, e);
    } catch (XmlException e) {
      final String desc = "Xml message Exception:" + e;
      errorLogProxy.logAndThrowError(
          AppraisalAssignmentConstants.ERROR_INVALID_WA_CLOB, CLASS_NAME,
          "findWADocByTaskID", desc, e);
    }
    return workAssignmentDocument;
  }

  private void checkInput(final String claimSuffixNumber,
      final UserInfoDocument bodyShopUserInfo, final String reviewCoCd,
      final String reviewUserId)
  {
    if (claimSuffixNumber == null || claimSuffixNumber.trim().length() <= 0
        || bodyShopUserInfo == null || bodyShopUserInfo.getUserInfo() == null
        || reviewCoCd == null || reviewUserId == null) {
      throw new IllegalArgumentException(
          "ClaimSuffixNumber, bodyShop or Reviewer User Info is not provided.");
    }
  }

  private void checkInput(final long taskId,
      final UserInfoDocument estimatorUserInfo)
  {
    if (taskId <= 0 || estimatorUserInfo == null
        || estimatorUserInfo.getUserInfo() == null) {
      throw new IllegalArgumentException(
          "taskId or estimator user info is not provided.");
    }
  }

  private void readClaimAndExpoureInfo(final AssignmentTaskContext context)
      throws MitchellException
  {

    try {

      final ClaimInfoDTO dto = claimProxy.getSimpleClaimInfoByFullClaimNumber(
          context.getReviewerUserInfo(), context.getClaimSuffixNumber(),
          context.getReviewerUserInfo().getUserInfo().getOrgCode());
      if (dto != null) {
        if (dto.getClaimId() != null) {
          context.setClaimID(dto.getClaimId().longValue());
        }
        if (dto.getClaimExposureId() != null) {
          context.setExposureID(dto.getClaimExposureId().longValue());
        }

        if (dto.getClaimNumber() != null) {
          context.setClaimNumber(dto.getClaimNumber());
        }
        if (dto.getExposureNumber() != null) {
          context.setExposureNumber(dto.getExposureNumber());
        }
      }
    } catch (Exception e) {
      final String desc = "Exception invoking Claim Service:" + e;
      errorLogProxy.logAndThrowError(
          AppraisalAssignmentConstants.ERROR_CLAIM_SERVICE, CLASS_NAME,
          "readClaimAndExpoureInfo", desc, e);
      // errorLogProxy.logAndThrowError(AppraisalAssignmentConstants.ERROR_REMOTE_EXCEPTION,CLASS_NAME,"readClaimAndExpoureInfo",desc,e);
    }

  }

  public static class AssignmentTaskContext
  {
    private String claimSuffixNumber;
    private UserInfoDocument reviewerUserInfo;
    private String claimNumber;
    private String exposureNumber;
    private long claimID;
    private long exposureID;
    private long taskID;
    private UserInfoDocument estimatorUserInfo;
    private long estimateDocID;
    private String workItemId;
    private String note;
    private UserInfoDocument bodyShopUserInfo;
    public static final String NO_MATCHING_CLAIM_EXISTS = "NO_MATCHING_CLAIM_EXISTS";
    public static final String TASK_ALREADY_EXISTS = "TASK_ALREADY_EXISTS";
    public static final String NO_ESTIMATE_AVAILABLE = "NO_ESTIMATE_AVAILABLE";

    private String rejectReason;

    public String getClaimSuffixNumber()
    {
      return claimSuffixNumber;
    }

    public void setClaimSuffixNumber(String claimSuffixNumber)
    {
      this.claimSuffixNumber = claimSuffixNumber;
    }

    public UserInfoDocument getReviewerUserInfo()
    {
      return reviewerUserInfo;
    }

    public void setReviewerUserInfo(UserInfoDocument reviwerUserInfo)
    {
      this.reviewerUserInfo = reviwerUserInfo;
    }

    public String getClaimNumber()
    {
      return claimNumber;
    }

    public void setClaimNumber(String claimNumber)
    {
      this.claimNumber = claimNumber;
    }

    public String getExposureNumber()
    {
      return exposureNumber;
    }

    public void setExposureNumber(String exposureNumber)
    {
      this.exposureNumber = exposureNumber;
    }

    public long getClaimID()
    {
      return claimID;
    }

    public void setClaimID(long claimID)
    {
      this.claimID = claimID;
    }

    public long getExposureID()
    {
      return exposureID;
    }

    public void setExposureID(long exposureID)
    {
      this.exposureID = exposureID;
    }

    public long getTaskID()
    {
      return taskID;
    }

    public void setTaskID(long taskID)
    {
      this.taskID = taskID;
    }

    public UserInfoDocument getEstimatorUserInfo()
    {
      return estimatorUserInfo;
    }

    public void setEstimatorUserInfo(UserInfoDocument estimatorUserInfo)
    {
      this.estimatorUserInfo = estimatorUserInfo;
    }

    public String getRejectReason()
    {
      return rejectReason;
    }

    public void setRejectReason(String rejectReason)
    {
      this.rejectReason = rejectReason;
    }

    public long getEstimateDocID()
    {
      return estimateDocID;
    }

    public void setEstimateDocID(long estimateDocID)
    {
      this.estimateDocID = estimateDocID;
    }

    public String getWorkItemId()
    {
      return workItemId;
    }

    public void setWorkItemId(String workItemId)
    {
      this.workItemId = workItemId;
    }

    public String getNote()
    {
      return note;
    }

    public void setNote(String note)
    {
      this.note = note;
    }

    public UserInfoDocument getBodyShopUserInfo()
    {
      return bodyShopUserInfo;
    }

    public void setBodyShopUserInfo(UserInfoDocument bodyShopUserInfo)
    {
      this.bodyShopUserInfo = bodyShopUserInfo;
    }
  }

  public void setApdProxy(APDProxy apdProxy)
  {
    this.apdProxy = apdProxy;
  }

  public void setAppLogProxy(AppLogProxy appLogProxy)
  {
    this.appLogProxy = appLogProxy;
  }

  public void setClaimProxy(ClaimProxy claimProxy)
  {
    this.claimProxy = claimProxy;
  }

  public void setErrorLogProxy(ErrorLogProxy errorLogProxy)
  {
    this.errorLogProxy = errorLogProxy;
  }

  public void setEstimatePackageProxy(EstimatePackageProxy estimatePackageProxy)
  {
    this.estimatePackageProxy = estimatePackageProxy;
  }

  public void setUserInfoProxy(UserInfoProxy userInfoProxy)
  {
    this.userInfoProxy = userInfoProxy;
  }

  public void setWorkAssignmentProxy(WorkAssignmentProxy workAssignmentProxy)
  {
    this.workAssignmentProxy = workAssignmentProxy;
  }

  public void setAasUtils(AASUtils aasUtils)
  {
    this.aasUtils = aasUtils;
  }
}
