package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;

import com.mitchell.schemas.NameValuePairType;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;

import com.cieca.bms.AddressType;
import com.cieca.bms.AdminInfoType;
import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.AssignmentAddRqDocument.AssignmentAddRq;
import com.cieca.bms.AssignmentEventType;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.CIECADocument.CIECA;
import com.cieca.bms.ClaimantType;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.DocumentInfoType;
import com.cieca.bms.DocumentVerType;
import com.cieca.bms.EstimatorIDsTypeType;
import com.cieca.bms.EstimatorType;
import com.cieca.bms.InsuranceCompanyType;
import com.cieca.bms.PartyType;
import com.cieca.bms.PolicyHolderType;
import com.cieca.bms.VehicleDamageAssignmentType;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument;
import com.cieca.bms.VehicleDamageEstimateAddRqDocument.VehicleDamageEstimateAddRq;
import com.cieca.bms.VehicleInfoType;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.EnvelopeBodyMetadataType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.appraisalassignment.PropertyInfoType;
import com.mitchell.schemas.appraisalassignment.VehicleLocationGeoCodeType.AddressValidStatus;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EmailMessageDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.schemas.schedule.AssignTaskType;
import com.mitchell.schemas.task.TaskDocument;
import com.mitchell.schemas.workassignment.EventDefinitionType;
import com.mitchell.schemas.workassignment.EventInfoType;
import com.mitchell.schemas.workassignment.HoldInfoType;
import com.mitchell.schemas.workassignment.PersonInfoType;
import com.mitchell.schemas.workassignment.PersonNameType;
import com.mitchell.schemas.workassignment.PrimaryIDsType;
import com.mitchell.schemas.workassignment.ScheduleInfoType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentDAO;
import com.mitchell.services.business.partialloss.appraisalassignment.HoldInfo;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentMandFieldUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CARRHelperProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ClaimProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.CustomSettingProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SuppRequestSystemConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SuppRequestUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementNotification;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementReqBO;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestDocBuildr;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASAppLogUtil;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASErrorLogUtil;
import com.mitchell.services.business.partialloss.appraisalassignment.util.BeanLocator;
import com.mitchell.services.business.partialloss.appraisalassignment.util.DraftEstimateComparator;
import com.mitchell.services.business.partialloss.appraisalassignment.util.EstimateComparator;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.util.UserInfoUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.WorkAssignmentHandler;
import com.mitchell.services.business.partialloss.monitor.MonitoringLogger;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.services.core.userinfo.utils.UserTypeConstants;
import com.mitchell.services.core.workassignment.WorkAssignmentException;
import com.mitchell.services.core.workassignment.bo.AssignmentSubType;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.services.technical.claim.common.DTO.BmsClmInputDTO;
import com.mitchell.services.technical.claim.common.DTO.BmsClmOutDTO;
import com.mitchell.services.technical.claim.dao.vo.ClaimProperty;
import com.mitchell.services.technical.claim.exception.ClaimException;
import com.mitchell.services.technical.claim.mcf.McfClmOutDTO;
import com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignmentUpdateFieldEnum;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.utils.misc.FileUtils;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class AppraisalAssignmentServiceHandlerImpl implements
    AppraisalAssignmentServiceHandler
{

    private static final String CLASS_NAME = AppraisalAssignmentServiceHandlerImpl.class
      .getName();
  private static final Logger logger = Logger
      .getLogger(AppraisalAssignmentServiceHandlerImpl.class.getName());

  protected static final String SCHEDULE_METHOD_MANUAL = "MANUAL";
  protected static final String SCHEDULE_METHOD_APPT_BOOKED = "PRE_COMMITTED";

  protected WorkAssignmentHandler workAssignmentHandler = null;
  protected MitchellEnvelopeHandler mitchellEnvelopeHandler = null;

  protected AASErrorLogUtil errorLogUtil;
  protected AASAppLogUtil appLogUtil;
  protected AASCommonUtils commonUtils;
  protected UserInfoUtils userInfoUtils;
  protected ClaimProxy claimProxy;
  protected CARRHelperProxy carrHelperProxy;
  protected EstimatePackageProxy estimatePackageProxy;
  protected AppraisalAssignmentDAO appraisalAssignmentDAO;
  protected IAppraisalAssignmentUtils appraisalAssignmentUtils;
  protected IAppraisalAssignmentMandFieldUtils appraisalAssignmentMandFieldUtils;
  protected IAppraisalAssignmentConfig appraisalAssignmentConfig;
  protected TestAssignmentHandler testAssignmentHandler;
  private MessageBusHandler messageBusHandler;
  
  private CustomSettingProxy customSettingProxy;

  public void setTestAssignmentHandler(
      TestAssignmentHandler testAssignmentHandler)
  {
    this.testAssignmentHandler = testAssignmentHandler;
  }

  public void setAppraisalAssignmentUtils(
      IAppraisalAssignmentUtils appraisalAssignmentUtils)
  {
    this.appraisalAssignmentUtils = appraisalAssignmentUtils;
  }

  public void setAppraisalAssignmentMandFieldUtils(
      IAppraisalAssignmentMandFieldUtils appraisalAssignmentMandFieldUtils)
  {
    this.appraisalAssignmentMandFieldUtils = appraisalAssignmentMandFieldUtils;
  }

  public void setMessageBusHandler(MessageBusHandler messageBusHandler)
  {
    this.messageBusHandler = messageBusHandler;
  }

  public MessageBusHandler getMessageBusHandler()
  {
    return this.messageBusHandler;
  }

  public void setClaimProxy(final ClaimProxy claimProxy)
  {

    this.claimProxy = claimProxy;
  }

  public void setAppLogUtil(final AASAppLogUtil appLogUtil)
  {

    this.appLogUtil = appLogUtil;
  }

  public void setErrorLogUtil(final AASErrorLogUtil errorLogUtil)
  {

    this.errorLogUtil = errorLogUtil;
  }

  public void setWorkAssignmentHandler(
      final WorkAssignmentHandler workAssignmentHandler)
  {

    this.workAssignmentHandler = workAssignmentHandler;
  }

  public void setCommonUtils(final AASCommonUtils commonUtils)
  {

    this.commonUtils = commonUtils;
  }

  public void setMitchellEnvelopeHandler(
      final MitchellEnvelopeHandler mitchellEnvelopeHandler)
  {

    this.mitchellEnvelopeHandler = mitchellEnvelopeHandler;
  }

  public void setUserInfoUtils(final UserInfoUtils userInfoUtils)
  {

    this.userInfoUtils = userInfoUtils;
  }

  public void setCarrHelperProxy(final CARRHelperProxy carrHelperProxy)
  {

    this.carrHelperProxy = carrHelperProxy;
  }

  public void setEstimatePackageProxy(
      final EstimatePackageProxy estimatePackageProxy)
  {

    this.estimatePackageProxy = estimatePackageProxy;
  }

  public void setAppraisalAssignmentDAO(
      final AppraisalAssignmentDAO appraisalAssignmentDAO)
  {

    this.appraisalAssignmentDAO = appraisalAssignmentDAO;
  }

  public void setAppraisalAssignmentConfig(
      final IAppraisalAssignmentConfig appraisalAssignmentConfig)
  {
    this.appraisalAssignmentConfig = appraisalAssignmentConfig;
  }

  public int cancelAppraisalAssignment(final long workAssignmentTaskID,
      final String cancellationReason, final long TCN, final String notes,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {

    final String METHOD_NAME = "cancelAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();

    // Set errorLog Details and initialize ErrorLog, AppLog.

    final StringBuffer localMethodParams = new StringBuffer();
    final StringBuffer logSuffix = new StringBuffer();

    localMethodParams.append("WorkAssignmentTaskID :")
        .append(workAssignmentTaskID).append("\ncancellationReason : ")
        .append(cancellationReason).append("\nRequest TCN : ").append(TCN)
        .append("\nLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    int isAsmtCancelledSuccessfully = AppraisalAssignmentConstants.FAILURE;
    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignmentDocument updatedWorkAssignmentDocument = null;
    Long claimExposureId = null;
    Long referenceId = null;
    String eventName = null;
    String eventDesc = null;
    String claimNumber = null;
    String workItemID = null;
    StringBuffer msgDetail = null;

    msgDetail = new StringBuffer("Method Parameters : ")
        .append(localMethodParams);
    final String beanName = "WorkAssignmentHandler";
    try {

      logSuffix.append("\t[WorkAssignment task ID:")
          .append(workAssignmentTaskID).append("]\t");

      /*
       * Retrieve work assignment details using taskID from Work
       * Assignment
       */

      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      /*
       * CHECK TCN for stale data check and if assignment is stale then
       * return as stale data exists
       */
      if (checkStaleData(workAssignment.getTcn(), TCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }

      claimExposureId = workAssignment.getClaimExposureID();
      referenceId = workAssignment.getReferenceId();
      msgDetail.append("\tDocumentID/ReferenceID : ").append(referenceId);

      /*
       * Retrieve work assignment xml from CLOB.
       */

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();

      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Retrieved Work Assignment Document from WorkAssignmentService"
                + logSuffix);
      }

      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();
      msgDetail.append("\tClaimNumber : ").append(claimNumber);
      // / Setting Claim Number in StringBuffer for Log Prefix
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");

      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();

      /*
       * Update work assignment xml with CANCELLED request.
       */

      updatedWorkAssignmentDocument = workAssignmentHandler
          .setupWorkAssignmentRequest(workAssignmentDocument,
              AppraisalAssignmentConstants.DISPOSITION_CANCELLED, "cancel",
              loggedInUserInfoDocument);

      // Update Work Assignment CLOB "EventReasonCode" and Work Assignment
      // Table "EVENT_MEMO" column with Cancellation reason

      final WorkAssignmentType workAssignmentType = updatedWorkAssignmentDocument
          .getWorkAssignment();

      if (cancellationReason != null
          && !"".equalsIgnoreCase(cancellationReason)) {

        workAssignmentType.setEventReasonCode(cancellationReason);
      }

      if (notes != null && !"".equalsIgnoreCase(notes.trim())) {
        StringBuffer statusNotes = new StringBuffer();

        workAssignmentType.getEventInfo().setEventMemo(
            statusNotes
                .append(AppraisalAssignmentConstants.DISPOSITION_CANCELLED)
                .append(AppraisalAssignmentConstants.STATUS_NOTES_SEPERATOR)
                .append(notes).toString());
      }

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Work Assignment CLOB 'EventReasonCode' set with Cancellation reason:: "
                + cancellationReason + logSuffix);
      }
      /*
       * Save updated work assignment xml to Work Assignment Service.
       */

      workAssignment = workAssignmentHandler.saveWorkAssignment(
          updatedWorkAssignmentDocument, loggedInUserInfoDocument);

      final Long documentId = workAssignment.getReferenceId();

      msgDetail.append(msgDetail).append("\tDocumentID : ").append(documentId);

      // Claim-Suffix Activity Log

      if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
          .equalsIgnoreCase(workAssignmentDocument.getWorkAssignment()
              .getType())) {
        commonUtils.saveExposureActivityLog(
            // "The Appraisal Assignment Cancelled Successfully",
            appraisalAssignmentConfig.getCancelAAActivityLog()
                + " - SUPPLEMENT", claimExposureId, referenceId, workItemID,
            loggedInUserInfoDocument);
      } else {
        commonUtils.saveExposureActivityLog(
            // "The Appraisal Assignment Cancelled Successfully",
            appraisalAssignmentConfig.getCancelAAActivityLog() + " - ORIGINAL",
            claimExposureId, referenceId, workItemID, loggedInUserInfoDocument);
      }

      // eventName will come from CONSTANT File and eventDesc will be
      // fetched from SET file
      eventDesc = appraisalAssignmentConfig.getCancelAAAssignmentActivityLog();
      eventName = AppraisalAssignmentConstants.DB_CANCEL_AA_ASSIGNMENT_ACTIVITYLOG;

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Calling writeAssignmentActivityLog method with EventName::"
                + eventName + "\tEventDesc::" + eventDesc + logSuffix);
      }
      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_CANCEL_ASSIGNMENT_SUCCESS,
          workAssignment, loggedInUserInfoDocument, null, startTime);

      isAsmtCancelledSuccessfully = AppraisalAssignmentConstants.SUCCESS;

      // PBI 270344 - Reset Schedule Method to MANUAL for cancelAppraisalAssignment use case
      updateScheduleMethodManual(workAssignment, workItemID, claimNumber,
          loggedInUserInfoDocument, METHOD_NAME);

      if (logger.isLoggable(Level.INFO)) {
        logger.info(">>>> cancelAppraisalAssignment::SCHEDULE_METHOD "
            + "After updateAppraisalAssignmentScheduleMethod: ReferenceId= [ "
            + workAssignment.getReferenceId() + " ]");
      }

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_CANCEL_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_CANCEL_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);
    return isAsmtCancelledSuccessfully;
  }

  /**
   * <p>
   * This method retrieves latest estimate for a claim number. Method calls
   * EstimatePackage Service to retrieve all available estimates. Then it finds
   * latest estimate and returns to caller.For collaborative, it skips the Estimates with Doc Category 
   * COPY and get the latest estimate on the basis of Supplement no and Doc Revision No. For Non-Collaborative,
   * it gets the latest Estimate on the basis of CommitDate, Supplement No and correction No.
   * </p>
   * 
   * @param insuranceCarrierCoCode
   *          Mitchell Company code of Insurance Company.
   * @param clientClaimNumber
   *          Claim Number
   * @param estimatorUserInfoDocument
   *          Estimator's UserInfo Document
   * @return latest Estimate object.
   * @throws MitchellException
   */
  public Estimate getLatestEstimate(final String insuranceCarrierCoCode,
      final String clientClaimNumber,
      final UserInfoDocument estimatorUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "getLatestEstimate";
    logger.entering(CLASS_NAME, METHOD_NAME);

    // Set errorLog Details and initialize ErrorLog, AppLog
    final StringBuffer logSuffix = new StringBuffer(); 

    if (logger.isLoggable(Level.FINE)) {
      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("InsuranceCarrierCoCode : ")
          .append(insuranceCarrierCoCode).append("\tClientClaimNumber : ")
          .append(clientClaimNumber).append("\tEstimator UserInfoDocument : ")
          .append(estimatorUserInfoDocument);
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    Estimate currentEstimate = null;
    Estimate maxEstimate = null;
    boolean isThisEstimateGood = false;
    boolean isCollaborative = false;
    
    final String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    logSuffix.append("\t[Client Claim Number:").append(clientClaimNumber)
        .append("]\t");

    try {

      // Calling Estimate Package Service to retrieve all available estimates
      // for a claim number.
      Estimate[] estimateList = estimatePackageProxy.findEstimateByClaimNumber(
          insuranceCarrierCoCode, null, clientClaimNumber);

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Retrieved all available estimates: "
            + (estimateList != null ? estimateList.length : 0) + logSuffix);
      }
      List<Estimate> estimateListArray = new ArrayList<Estimate>();
      // If estimate list is not null then iterate and get latest estimate.
      if (estimateList != null && estimateList.length > 0) {

    	// read Collaborative Customsetting	to differentiate for colloboration workflow and existing workflow.		
    	  isCollaborative = readColloborativeCustomSetting(insuranceCarrierCoCode);
  		
  		 if (logger.isLoggable(Level.INFO)) {
  	        logger.info("Collaborative Setting Value: "
  	            + isCollaborative);
  	     }
  		 
  		 // If Collaborative Custom Setting is No then run the existing flow
  	
        for (Estimate element : estimateList) {
          isThisEstimateGood = false;
          currentEstimate = element;
          // Staff
          if (logger.isLoggable(Level.INFO)) {
            logger.info("Checking that if current estimator is a STAFF user"
                + logSuffix);
          }
          if (estimatorUserInfoDocument == null
              && currentEstimate.getBusinessPartnerId() == null) {
            isThisEstimateGood = true;
          }
          // Non-Staff
          else if (estimatorUserInfoDocument != null
              && currentEstimate.getBusinessPartnerId() != null) {
            if (logger.isLoggable(Level.INFO)) {
              logger
                  .info("Checking that if current estimator is a NON-STAFF user"
                      + logSuffix);
            }
            if (estimatorUserInfoDocument
                .getUserInfo()
                .getOrgID()
                .equalsIgnoreCase(
                    currentEstimate.getBusinessPartnerId().toString())) {
              isThisEstimateGood = true;
            }
          }
          
          if(isCollaborative && element.getEstimateDocCategory() != null 
    					//Skip the estimate with Doc Category COPY as they don't have the clientEstimateId associated
    					&& element.getEstimateDocCategory().equalsIgnoreCase(AppraisalAssignmentConstants.ESTIMATECATEGORY_COPY)){
        	  isThisEstimateGood = false; 
          }

          // If we have a non-excluded estimate
          if (isThisEstimateGood) {
            estimateListArray.add(element);
          }
        }

        // extracting latest estimate
        maxEstimate = (!estimateListArray.isEmpty()) ?
                sortEstimateArray(estimateListArray, isCollaborative).get(estimateListArray.size() - 1)
                : null;

	    if (logger.isLoggable(Level.INFO)) {
          logger.info("Estimate Found---" + maxEstimate);
        }
  	  }
    }
    catch (MitchellException me) {
      logMEAndThrowError(me,clientClaimNumber,estimatorUserInfoDocument);
    }
    catch (final Exception exception) {
      final String workItemID = "UNKNOWN";
      final int errorCode = AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE_MSG;

      StringBuffer msgDetail = new StringBuffer();
      msgDetail.append("InsuranceCarrierCoCode : ")
          .append(insuranceCarrierCoCode).append("\tClientClaimNumber : ")
          .append(clientClaimNumber).append("\tEstimator UserInfoDocument : ")
          .append(estimatorUserInfoDocument);

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, estimatorUserInfoDocument);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return maxEstimate;
  }

  /**
   * <p>
   * This method retrieves latest estimate for a claim number for Cross. Method calls
   * EstimatePackage Service to retrieve all available estimates. Then it finds
   * latest estimate and returns to caller.For collaborative, it skips the Estimates with Doc Category
   * COPY and get the latest estimate on the basis of Supplement no and Doc Revision No. For Non-Collaborative,
   * it gets the latest Estimate on the basis of CommitDate, Supplement No and correction No.
   * </p>
   *
   * @param insuranceCarrierCoCode
   *          Mitchell Company code of Insurance Company.
   * @param clientClaimNumber
   *          Claim Number
   *
   * @return latest Estimate object.
   * @throws MitchellException
   */
  public Estimate getLatestEstimateNoFiltering(final String insuranceCarrierCoCode,
                                    final String clientClaimNumber)
          throws MitchellException
  {
    final String METHOD_NAME = "getLatestEstimateNoFiltering";
    logger.entering(CLASS_NAME, METHOD_NAME);

    // Set errorLog Details and initialize ErrorLog, AppLog
    final StringBuffer logSuffix = new StringBuffer();

    if (logger.isLoggable(Level.FINE)) {
      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("InsuranceCarrierCoCode : ")
              .append(insuranceCarrierCoCode).append("\tClientClaimNumber : ")
              .append(clientClaimNumber);
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    Estimate maxEstimate = null;
    boolean isCollaborative = false;

    logSuffix.append("\t[Client Claim Number:").append(clientClaimNumber)
            .append("]\t");

    try {

      // Calling Estimate Package Service to retrieve all available estimates
      // for a claim number.
      Estimate[] estimateList = estimatePackageProxy.findEstimateByClaimNumber(
              insuranceCarrierCoCode, null, clientClaimNumber);

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Retrieved all available estimates: "
                + (estimateList != null ? estimateList.length : 0) + logSuffix);
      }

      List<Estimate> estimateListArray = new ArrayList<Estimate>();

      if (estimateList != null && estimateList.length > 0) {

        // read Collaborative Customsetting	to differentiate for colloboration workflow and existing workflow.
        isCollaborative = readColloborativeCustomSetting(insuranceCarrierCoCode);

        if (logger.isLoggable(Level.INFO)) {
          logger.info("Collaborative Setting Value: "
                  + isCollaborative);
        }

        //Skip the estimate with Doc Category COPY as they don't have the clientEstimateId associated

        for (Estimate element : estimateList) {
          if(!isCollaborative
                  || !AppraisalAssignmentConstants.ESTIMATECATEGORY_COPY.equalsIgnoreCase(element.getEstimateDocCategory())) {
            estimateListArray.add(element);
          }
        }

        // extracting latest estimate
        maxEstimate = (!estimateListArray.isEmpty()) ?
                sortEstimateArray(estimateListArray, isCollaborative).get(estimateListArray.size() - 1)
                : null;

        if (logger.isLoggable(Level.INFO)) {
          logger.info("Estimate Found---" + maxEstimate);
        }
      }
    }
    catch (MitchellException me) {
      logMEAndThrowError(me,clientClaimNumber,null);
    }
    catch (final Exception exception) {

      StringBuffer msgDetail = new StringBuffer();
      msgDetail.append("InsuranceCarrierCoCode : ")
              .append(insuranceCarrierCoCode).append("\tClientClaimNumber : ")
              .append(clientClaimNumber);

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE,
              AppraisalAssignmentConstants.ERROR_GET_LATEST_ESTIMATE_MSG, "UNKNOWN",
              AppraisalAssignmentConstants.CLAIM_NUMBER, msgDetail.toString(),
              exception, null);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return maxEstimate;
  }
  
  /*
   * Reads the Collaborative Custom Setting for the company Code
   *
   */
  private boolean readColloborativeCustomSetting(String companyCode)
			throws MitchellException {
		boolean isCollaborative = false;
		String groupName = AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_GROUPNAME;
		String settingName = AppraisalAssignmentConstants.SYSCONF_COLLOBORATIVE_SETTINGNAME;		
		String settingValue = customSettingProxy.getCompanyCustomSettings(companyCode,
				groupName, settingName);
		if(AppraisalAssignmentConstants.STATUS_Y.equalsIgnoreCase(settingValue)){
			isCollaborative = true;
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("setting groupName:" + groupName);
			logger.info("settingName:" + settingName);
			logger.info("settingValue:" + settingValue);
		}
		return isCollaborative;
	}

  private List<Estimate> sortEstimateArray(List<Estimate> estimates, boolean isCollaborative) {
    //Sort the Estimates to find the latest Estimate.

    if(estimates != null &&
            estimates.size() > 0 ){
      if(!isCollaborative){
        Collections.sort(estimates, new EstimateComparator());
      } else {
        Collections.sort(estimates, new DraftEstimateComparator());
      }
    }

    return estimates;
  }

  /**
   * Method added for errorlogging. Used in MAXIMA for Error Logging and
   * throwing error back to the callee.
   * 
   * @param errorEvent
   *          errorEvent
   * @param errorMessage
   *          errorMessage
   * @param workItemID
   *          workItemID
   * @param claimNumber
   *          claimNumber
   * @param exception
   *          exception
   * @return
   * @throws MitchellException
   *           MitchellException
   */
  private void logAndThrowError(final String className,
      final String methodName, final int errorEvent, final String errorMessage,
      final String workItemID, final String claimNumber,
      final String msgDetail, final Exception exception,
      final UserInfoDocument userInfoDocument)
      throws MitchellException
  {

    errorLogUtil.logAndThrowError(errorEvent, className, methodName,
        ErrorLoggingService.SEVERITY.FATAL, errorMessage, msgDetail, exception,
        userInfoDocument, workItemID, claimNumber);

  }

  private void logCorrelatedAndThrowError(final String className,
      final String methodName, final int errorEvent, final String errorMessage,
      final String workItemID, final String claimNumber,
      final String msgDetail, final Exception exception,
      final UserInfoDocument userInfoDocument)
      throws MitchellException
  {
    errorLogUtil.logCorrelatedAndThrowError(errorEvent, className, methodName,
        ErrorLoggingService.SEVERITY.FATAL, errorMessage, msgDetail, exception,
        userInfoDocument, workItemID, claimNumber);
  }
  
  private void logMEAndThrowError(MitchellException mitchellException,
	      String claimNumber, UserInfoDocument userInfoDocument)
	      throws MitchellException
	  {
	    errorLogUtil.logAndThrowError(mitchellException, claimNumber, userInfoDocument);
	 }

  public void logError(int errorType, String correlationId, String className,
      String methodName, String severity, String workItemID,
      String description, String companyCode, int orgID, Exception exception)
      throws MitchellException
  {

    errorLogUtil.logError(errorType, correlationId, className, methodName,
        severity, workItemID, description, companyCode, orgID, exception);
  }

  /**
   * <p>
   * This method is responsible for create and/or save of Appraisal Assignment.
   * This method does following major tasks:
   * </p>
   * <ul>
   * <li>Create/Save Claim information base on rules to Claim Service.</li>
   * <li>Create work assignment using WorkAssignment Service</li>
   * <li>Save MitchellEnvelope Document to EstimatePackage Service.</li>
   * <li>Update Work Assignment.</li>
   * <li>Create Claim-suffix Activity Log.</li>
   * </ul>
   * 
   * @param inputAppraisalAssignmentDTO
   *          Object of AppraisalAssignmentDTO.
   * @param logedInUserInfoDocument
   *          UserInfo Document of Logged in User.
   * @return Updated AppraisalAssignmentDTO which has latest MitchellEnvelope
   *         Document along with other data like TCN, WorkAssignmentTaskID,
   *         DocumentID etc.
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public AppraisalAssignmentDTO saveAppraisalAssignment(
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
      final UserInfoDocument logedInUserInfoDocument)
      throws MitchellException
  {
    long startTime = System.currentTimeMillis();
    long subStartTime = 0;

    // Start changes: for merge the saveAppraisalAssignment and
    // saveSupplementAppraisalAssignment into an one method
    final String METHOD_NAME = "saveAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);

    logger
        .info("Coming in Save Appraisal Assignment of Service Implementation::::");

    // Set errorLog Details and initialize ErrorLog, AppLog
    String workItemID = validateMethodInputs(inputAppraisalAssignmentDTO,
        logedInUserInfoDocument);

    if (logger.isLoggable(Level.FINE)) {
      final StringBuilder localMethodParams = new StringBuilder();
      localMethodParams
          .append("InputAppraisalAssignmentDTO : MitchellEnvelopeDocument : ")
          .append(
              inputAppraisalAssignmentDTO.getMitchellEnvelopDoc().toString())
          .append("\nWorkItemID : ")
          .append(inputAppraisalAssignmentDTO.getWorkItemID())
          .append("\nLogged In UserInfoDocument : ")
          .append(logedInUserInfoDocument.toString());
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    Long documentId = null;
    long claimId = 0;
    String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    long claimExposureId = 0;
    String claimMask = null;
    long relatedEstimateDocumentId = 0L;
    McfClmOutDTO claimOutputDTO = null;
    WorkAssignment workAssignment = null;
    AppraisalAssignmentDTO outputAppraisalAssignmentDTO = null;
    com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = null;
    boolean isAllMandatoryFieldExist = false;
    CIECADocument ciecaDocument = null;
    String assigneeId = null;
    String groupCode = null;
    String eventName = null;
    String eventDesc = null;
    String activityLogDesc = null;
    MitchellEnvelopeHelper meHelper = null;
    MitchellEnvelopeDocument mitchellEnvDoc = null;
    Estimate estimate = null;
    final boolean isOriginalAssignment = inputAppraisalAssignmentDTO
        .isOriginalAssignment();
    AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = null;
    java.util.Calendar scheduleDateTime = null;
    StringBuffer logSuffix = new StringBuffer("");
    String clientEstimateId = null;
    // Initialize and extract different required information from input XmlBeans.
    String passThruInfo = null;
    VehicleDamageEstimateAddRq vehicleDamageEstimateAddRq = null;
    try {

      meHelper = new MitchellEnvelopeHelper(
          inputAppraisalAssignmentDTO.getMitchellEnvelopDoc());
      final AssignmentAddRqDocument assignmentAddRqDocument = mitchellEnvelopeHandler
          .fetchAssignmentAddRqDocument(meHelper);
      claimNumber = assignmentAddRqDocument.getAssignmentAddRq().getClaimInfo()
          .getClaimNum();

      logSuffix.append(" ClaimNumber: " + claimNumber);

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Fetched AssignmentAddRq Document." + logSuffix);
      }

      // Validating Inspection Site Address in case it is not validated before.
      AddressValidStatus.Enum addressValidStsEnum = null;

      subStartTime = System.currentTimeMillis();
      addressValidStsEnum = mitchellEnvelopeHandler
          .validateInspectionSiteAddress(meHelper, logedInUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "validateInspectionSiteAddress:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // Fetch ClaimMask from Custom Settings
      subStartTime = System.currentTimeMillis();
      claimMask = fetchClaimMask(workItemID, logedInUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "fetchClaimMask:"
            + claimNumber, System.currentTimeMillis() - subStartTime);
      }

      // Retrieve data from input DTO
      claimId = inputAppraisalAssignmentDTO.getClaimId();
      claimExposureId = inputAppraisalAssignmentDTO.getClaimExposureId();
      if (!isOriginalAssignment && (claimId == 0 || claimExposureId == 0)) {
        throw new MitchellException(CLASS_NAME, METHOD_NAME,
            "ClaimID and ExposureID can NOT be null. Received ClaimID : "
                + claimId + "\tClaimExposureID : " + claimExposureId);
      }

      mitchellEnvDoc = meHelper.getDoc();

      // Fetching assigneeID,groupCode from Mitchellenvelope inside AADTO
      ciecaDocument = mitchellEnvelopeHandler.getCiecaFromME(meHelper);
      assigneeId = getAssigneeIdFromCiecaDocument(ciecaDocument);
      groupCode = getGroupCodeFromCiecaDocument(ciecaDocument);

      // Fetching scheduleDateTime from Mitchellenvelope
      additionalTaskConstraintsDocument = mitchellEnvelopeHandler
          .getAdditionalTaskConstraintsFromME(meHelper);
      scheduleDateTime = getScheduleDateTime(additionalTaskConstraintsDocument);

      // The first parameter i.e. isAllMandatoryfieldExist will be coming
      // from isAssignmentReadyForDispatch() and will be set in workAssignment
      isAllMandatoryFieldExist = this.isAssignmentDataReady(mitchellEnvDoc);
      if (logger.isLoggable(Level.INFO)) {
        logger.info("isAllMandatoryFieldExist in " + METHOD_NAME + " : "
            + isAllMandatoryFieldExist);
      }

      final String disposition = this.isAssignmentReadyForDispatch(
          isAllMandatoryFieldExist, assigneeId, groupCode, scheduleDateTime,
          logedInUserInfoDocument);

      // Start changes: for FAILED_NOTREADY integration.
      if (AppraisalAssignmentConstants.DISPOSITION_DISPATCHED
          .equalsIgnoreCase(inputAppraisalAssignmentDTO.getDisposition())
          && AppraisalAssignmentConstants.DISPOSITION_NOT_READY
              .equalsIgnoreCase(disposition)) {
        logger
            .severe("Received 'NOT READY' Assignment with disposition as DISPATCHED. \nClaimId : "
                + claimId);
        throw new MitchellException(
            AppraisalAssignmentConstants.ERROR_FAILED_NOTREADY, CLASS_NAME,
            METHOD_NAME,
            "Received 'NOT READY' Assignment with disposition as DISPATCHED. \nClaimId: "
                + claimId);
      }

      // End changes: for FAILED_NOTREADY integration.
      // Setting the updated disposition in DTO
      inputAppraisalAssignmentDTO.setDisposition(disposition);
      if (isAllMandatoryFieldExist) {
        inputAppraisalAssignmentDTO.setReqAssociateDataCompletedInd("Y");
      } else {
        inputAppraisalAssignmentDTO.setReqAssociateDataCompletedInd("N");
      }

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Claim Suffix ID ::" + claimExposureId);
      }

      if (claimExposureId <= 0) {

        subStartTime = System.currentTimeMillis();
        claimOutputDTO = storeClaimInfo(claimMask, meHelper, workItemID,
            logedInUserInfoDocument, logSuffix, inputAppraisalAssignmentDTO);
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "storeClaimInfo:"
              + claimNumber, System.currentTimeMillis() - subStartTime);
        }

        claimId = claimOutputDTO.getClaimID().longValue();
        claimNumber = claimOutputDTO.getClaim().getClaimNumber();
        claimExposureId = claimOutputDTO.getExposureID().longValue();

      }
      meHelper.addEnvelopeContextNVPair("ClaimId", "" + claimId);
      meHelper.addEnvelopeContextNVPair("ExposureId", "" + claimExposureId);

      // Create Claim-Suffix Activity Log for Address Validation

      if (addressValidStsEnum != null
          && !addressValidStsEnum.equals(AddressValidStatus.UNKNOWN)) {
        String logMsg = "";
        if (addressValidStsEnum.equals(AddressValidStatus.VALID)) {
          if (logger.isLoggable(Level.INFO)) {
            logger.info("Address Status for Valid");
          }
          logMsg = appraisalAssignmentConfig
              .getAddressValidateAAActivityLogPassed();
        } else {
          logMsg = appraisalAssignmentConfig
              .getAddressValidateAAActivityLogFailed();
        }

        subStartTime = System.currentTimeMillis();
        commonUtils.saveExposureActivityLog(logMsg,
            Long.valueOf(claimExposureId), documentId, workItemID,
            logedInUserInfoDocument);
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "commonUtils.saveExposureActivityLog:" + claimNumber,
              System.currentTimeMillis() - subStartTime);
        }

      }
      if (!isOriginalAssignment) {
        relatedEstimateDocumentId = mitchellEnvelopeHandler
            .getEstimateDocId(meHelper);

        if (logger.isLoggable(Level.INFO)) {
          logger.info("AAS relatedEstimateDocumentId."
              + relatedEstimateDocumentId);
        }

        if (relatedEstimateDocumentId > 0)
          estimate = estimatePackageProxy.getEstimateAndDocByDocId(
              relatedEstimateDocumentId, true);

        if (null != estimate) {
          clientEstimateId = estimate.getClientEstimateId();

          if (logger.isLoggable(Level.INFO)) {
            logger.info("AAS clientEstimateId." + clientEstimateId);
          }

          try {
            VehicleDamageEstimateAddRqDocument vdDoc = VehicleDamageEstimateAddRqDocument.Factory
                .parse(estimate.getEstimateXmlEstimate().getXmlData());

            vehicleDamageEstimateAddRq = vdDoc.getVehicleDamageEstimateAddRq();

            DocumentInfoType docInfoType = vehicleDamageEstimateAddRq
                .getDocumentInfo();
            if (docInfoType.isSetReferenceInfo()) {
              if (docInfoType.getReferenceInfo().getPassThroughInfo() != null) {
                passThruInfo = docInfoType.getReferenceInfo()
                    .getPassThroughInfo();
                if (logger.isLoggable(Level.INFO)) {
                  logger.info("AAS PassThroughInfo." + passThruInfo);
                }

              }
            }
          } catch (XmlException xmle) {
            throw new MitchellException(this.getClass().getName(),
                "getPassThroughData", "Exception occured parsing BMS xml: "
                    + xmle.getMessage(), xmle);
          }

          // Setting the Passtrough in the Assignment BMS
          if (logger.isLoggable(Level.INFO)) {
            logger
                .info("getDocumentInfo().getReferenceInfo().setPassThroughInfo");
          }

          if (null != passThruInfo) {

            if (ciecaDocument.getCIECA().getAssignmentAddRq().getDocumentInfo()
                .getReferenceInfo() != null
                && ciecaDocument.getCIECA().getAssignmentAddRq()
                    .getDocumentInfo().getReferenceInfo()
                    .isSetPassThroughInfo()) {

              if (logger.isLoggable(Level.INFO)) {
                logger.info("AAS PassThroughInfo in Reference Info.");
              }

              ciecaDocument.getCIECA().getAssignmentAddRq().getDocumentInfo()
                  .getReferenceInfo().setPassThroughInfo(passThruInfo);
            } else {
              if (logger.isLoggable(Level.INFO)) {
                logger.info("AAS NO PassThroughInfo ....adding Refernce Info");
              }
              if (ciecaDocument.getCIECA().getAssignmentAddRq()
                  .getDocumentInfo().getReferenceInfo() == null) {
                ciecaDocument.getCIECA().getAssignmentAddRq().getDocumentInfo()
                    .addNewReferenceInfo().setPassThroughInfo(passThruInfo);
              } else {
                ciecaDocument.getCIECA().getAssignmentAddRq().getDocumentInfo()
                    .getReferenceInfo().setPassThroughInfo(passThruInfo);
              }
            }
          }
          if (logger.isLoggable(Level.INFO)) {
            logger.info("ciecaDocument::::" + ciecaDocument);
          }
          final EnvelopeBodyType envelopeBody = meHelper
              .getEnvelopeBody(AppraisalAssignmentConstants.ME_METADATA_CIECA_IDENTIFIER);
          meHelper.updateEnvelopeBodyContent(envelopeBody, ciecaDocument);

          mitchellEnvDoc = meHelper.getDoc();

          if (logger.isLoggable(Level.INFO)) {
            logger.info("mitchellEnvDoc::::" + mitchellEnvDoc);
          }

        }
        meHelper.addEnvelopeContextNVPair("ClientEstimateId", ""
            + clientEstimateId);
      }

      // Save Original/Supplement MitchellEnvelope Document to
      // EstimatePackage Service.

      subStartTime = System.currentTimeMillis();
      documentId = mitchellEnvelopeHandler.saveBMS(claimId, claimExposureId,
          relatedEstimateDocumentId, mitchellEnvDoc, logedInUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "mitchellEnvelopeHandler.saveBMS:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // update Managed By Id In Claim
      subStartTime = System.currentTimeMillis();
      long moiOrgID = updateManagedByIdInClaim(isOriginalAssignment,
          logedInUserInfoDocument, claimExposureId, meHelper);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "updateManagedByIdInClaim:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // Create new Work Assignment in Work Assignment Service.
      subStartTime = System.currentTimeMillis();
      workAssignment = workAssignmentHandler.saveWorkAssignment(documentId,
          claimId, claimExposureId, claimMask,
          AppraisalAssignmentConstants.WORK_ASSIGNMENT_CREATE_EVENT,
          inputAppraisalAssignmentDTO, logedInUserInfoDocument, null, moiOrgID);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "workAssignmentHandler.saveWorkAssignment:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // Create Claim-Suffix Activity Log
      if (isOriginalAssignment) {
        activityLogDesc = appraisalAssignmentConfig.getCreateAAActivityLog();

      } else {
        activityLogDesc = appraisalAssignmentConfig
            .getCreateSupplementAAActivityLog();
      }

      // The Appraisal Assignment Created Successfully
      AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = mitchellEnvelopeHandler
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      if (scheduleDateTime != null && addAppAsmtInfo != null) {
        subStartTime = System.currentTimeMillis();
        commonUtils.doDriveInAppointmentActivityLog(
            Long.valueOf(claimExposureId), scheduleDateTime,
            logedInUserInfoDocument, workItemID, addAppAsmtInfo,
            inputAppraisalAssignmentDTO);
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "commonUtils.doDriveInAppointmentActivityLog:" + claimNumber,
              System.currentTimeMillis() - subStartTime);
        }
      }

      // save Exposure Activity Log
      subStartTime = System.currentTimeMillis();
      commonUtils.saveExposureActivityLog(activityLogDesc,
          Long.valueOf(claimExposureId), documentId, workItemID,
          logedInUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "commonUtils.saveExposureActivityLog:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // Populate Appraisal Assignment DTO with IDs and return it back to
      // caller application.

      outputAppraisalAssignmentDTO = populateDTO(inputAppraisalAssignmentDTO,
          appraisalAssignmentDTO, meHelper, documentId, claimId,
          claimExposureId, workAssignment, "save", workItemID,
          logedInUserInfoDocument);

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Updated OutputAppraisalAssignmentDTO");
      }

      // eventName will come from UI and eventDesc will be fetched from
      // SET file.
      if (isOriginalAssignment) {
        eventName = AppraisalAssignmentConstants.CREATE_AA_ASSIGNMENT_ACTIVITYLOG;
        eventDesc = appraisalAssignmentConfig
            .getCreateAAAssignmentActivityLog();
      } else {
        eventName = AppraisalAssignmentConstants.CREATE_SUPPLEMENT_AA_ASSIGNMENT_ACTIVITYLOG;
        eventDesc = appraisalAssignmentConfig
            .getCreateSupplementAAAssignmentActivityLog();
      }

      // Activity log
      subStartTime = System.currentTimeMillis();
      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, logedInUserInfoDocument.getUserInfo()
              .getUserID());
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "writeAssignmentActivityLog:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // AppLog
      subStartTime = System.currentTimeMillis();
      appLogUtil
          .appLog(AppraisalAssignmentConstants.APPLOG_SAVE_ASSIGNMENT_SUCCESS,
              workAssignment, logedInUserInfoDocument, meHelper.getDoc(),
              startTime);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "appLogUtil.appLog:"
            + claimNumber, System.currentTimeMillis() - subStartTime);
      }

    } catch (final Exception exception) {
      // Start changes: for FAILED_NOTREADY integration.

      if (exception instanceof MitchellException
          && ((MitchellException) exception).getType() == AppraisalAssignmentConstants.ERROR_FAILED_NOTREADY) {
        throw (MitchellException) exception;
      } else {
        final int errorCode = AppraisalAssignmentConstants.ERROR_SAVE_AA;
        final String errorDescription = AppraisalAssignmentConstants.ERROR_SAVE_AA_MSG;

        // Build details

        StringBuilder msgDetail = new StringBuilder();
        msgDetail
            .append("InputAppraisalAssignmentDTO : MitchellEnvelopeDocument : ")
            .append(
                inputAppraisalAssignmentDTO.getMitchellEnvelopDoc().toString())
            .append("\nWorkItemID : ")
            .append(inputAppraisalAssignmentDTO.getWorkItemID())
            .append("\nLogged In UserInfoDocument : ")
            .append(logedInUserInfoDocument.toString());
        msgDetail.append("ClaimNumber : ").append(claimNumber);
        msgDetail.append("claimId : ").append(claimId)
            .append("claimExposureId : ").append(claimExposureId);
        msgDetail.append("\tDocumentID: ").append(documentId);
        if (workAssignment != null && workAssignment.getTaskID() != null) {
          msgDetail.append("\tWorkAssignmentTaskID : ").append(
              workAssignment.getTaskID().toString());
        }

        logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
            errorDescription, workItemID, claimNumber, msgDetail.toString(),
            exception, logedInUserInfoDocument);
      }
      // End changes: for FAILED_NOTREADY integration.
    }

    if (MonitoringLogger.isLoggableTimer()) {
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "fullMethodTime:"
          + claimNumber, System.currentTimeMillis() - startTime);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return outputAppraisalAssignmentDTO;
    // End changes: for merge the saveAppraisalAssignment and
    // saveSupplementAppraisalAssignment into an one method
  }

  /**
   * <p>
   * This method is responsible for save/save&send/save&dispatch of
   * AppraisalAssignment from New Assignemnt Page. This method does following
   * major tasks:
   * </p>
   * <ul>
   * <li>Create work assignment using WorkAssignment Service</li>
   * <li>Save MitchellEnvelope Document to EstimatePackage Service.</li>
   * <li>Dispatch Work Assignment for save & dispatch fucntionality.</li>
   * <li>Create Claim-suffix Activity Log.</li>
   * <li>Does App Log.</li>
   * </ul>
   * 
   * @param inputAppraisalAssignmentDTO
   *          Object of AppraisalAssignmentDTO.
   * @param logedInUserInfoDocument
   *          UserInfo Document of Logged in User.
   * @return Updated AppraisalAssignmentDTO.
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public AppraisalAssignmentDTO saveSSOAppraisalAssignment(
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
      final UserInfoDocument logedInUserInfoDocument)
      throws MitchellException
  {
    long startTime = System.currentTimeMillis();
    final String METHOD_NAME = "saveSSOAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
        "Start Calling AAS.saveSSOAppraisalAssignment()");

    StringBuffer msgDetail = null;
    final String workItemID = validateMethodInputs(inputAppraisalAssignmentDTO,
        logedInUserInfoDocument);
    final StringBuffer localMethodParams = new StringBuffer();

    localMethodParams
        .append("InputAppraisalAssignmentDTO : MitchellEnvelopeDocument : ")
        .append(inputAppraisalAssignmentDTO.getMitchellEnvelopDoc().toString())
        .append("\nWorkItemID : ")
        .append(inputAppraisalAssignmentDTO.getWorkItemID())
        .append("\nLogged In UserInfoDocument : ")
        .append(logedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    Long documentId = null;

    String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    long relatedEstimateDocumentId = 0L;
    WorkAssignment workAssignment = null;
    AppraisalAssignmentDTO outputAppraisalAssignmentDTO = null;
    boolean isAllMandatoryFieldExist = false;
    String activityLogDesc = null;
    final boolean isSaveFromSSOPage = inputAppraisalAssignmentDTO
        .getIsSaveFromSSOPage();
    final boolean saveAndSendFlag = inputAppraisalAssignmentDTO
        .getSaveAndSendFlag();
    final StringBuffer logSuffix = new StringBuffer();
    String workAssignmentDisposition = null;

    try {

      final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
          inputAppraisalAssignmentDTO.getMitchellEnvelopDoc());
      final AssignmentAddRqDocument assignmentAddRqDocument = mitchellEnvelopeHandler
          .fetchAssignmentAddRqDocument(meHelper);
      claimNumber = assignmentAddRqDocument.getAssignmentAddRq().getClaimInfo()
          .getClaimNum();
      msgDetail.append("ClaimNumber : ").append(claimNumber);

      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Fetched AssignmentAddRq Document" + logSuffix);
      }

      // Validating Inspection Site Address
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "Start Calling mitchellEnvelopeHandler.validateInspectionSiteAddress() for GEO service for claimNumber::"
                  + claimNumber);
      final AddressValidStatus.Enum addressValidStsEnum = mitchellEnvelopeHandler
          .validateInspectionSiteAddress(meHelper, logedInUserInfoDocument);
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "End Calling mitchellEnvelopeHandler.validateInspectionSiteAddress() for GEO service for claimNumber::"
                  + claimNumber);

      // Fetching ClaimMask from Custom Settings
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start Calling fetchClaimMask() for Claim service for claimNumber::"
              + claimNumber);
      final String claimMask = fetchClaimMask(workItemID,
          logedInUserInfoDocument);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End Calling fetchClaimMask() for Claim service for claimNumber::"
              + claimNumber);

      final long claimId = inputAppraisalAssignmentDTO.getClaimId();
      final long claimExposureId = inputAppraisalAssignmentDTO
          .getClaimExposureId();

      // Throw Exception if input claimID or ClaimExposureID is <=0
      if (claimId <= 0 || claimExposureId <= 0) {
        throw new MitchellException(CLASS_NAME, METHOD_NAME,
            "ClaimID and ExposureID can NOT be <= 0 . Received ClaimID : "
                + claimId + "\tClaimExposureID : " + claimExposureId);
      }

      // Fetch Data from input ME
      final MitchellEnvelopeDocument mitchellEnvDoc = meHelper.getDoc();
      final CIECADocument ciecaDocument = mitchellEnvelopeHandler
          .getCiecaFromME(meHelper);
      final String assigneeId = getAssigneeIdFromCiecaDocument(ciecaDocument);
      final String groupCode = getGroupCodeFromCiecaDocument(ciecaDocument);
      final AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = mitchellEnvelopeHandler
          .getAdditionalTaskConstraintsFromME(meHelper);
      final java.util.Calendar scheduleDateTime = getScheduleDateTime(additionalTaskConstraintsDocument);

      // Check Mandatory Fields set :: Check for invalid assignment address
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "Start Calling isAssignmentDataReadyForSSO() for Mandatory fields / Vehicle Ready for claimNumber::"
                  + claimNumber);
      isAllMandatoryFieldExist = this
          .isAssignmentDataReadyForSSO(mitchellEnvDoc);
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "End Calling isAssignmentDataReadyForSSO() for Mandatory fields / Vehicle Ready for claimNumber::"
                  + claimNumber);

      if (logger.isLoggable(Level.INFO)) {
        StringBuffer logMessage = new StringBuffer();
        logMessage.append("isAllMandatoryFieldExist in ");
        logMessage.append(METHOD_NAME);
        logMessage.append(" : ");
        logMessage.append(isAllMandatoryFieldExist);
        logMessage.append(logSuffix);
        logger.info(logMessage.toString());
        logMessage = null;
      }
      // Determine assignment disposition
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "Start Calling isAssignmentReadyForDispatchForSSO() for UserInfo Service and  appraisalAssignmentDAO.getWorkGroupType() for claimNumber::"
                  + claimNumber);
      workAssignmentDisposition = this.isAssignmentReadyForDispatchForSSO(
          isAllMandatoryFieldExist, assigneeId, groupCode, scheduleDateTime,
          logedInUserInfoDocument);
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "End Calling isAssignmentReadyForDispatchForSSO() for UserInfo Service and  appraisalAssignmentDAO.getWorkGroupType() for claimNumber::"
                  + claimNumber);

      inputAppraisalAssignmentDTO.setDisposition(workAssignmentDisposition);

      // Set Mandatory Field flag in inputAADTO
      if (isAllMandatoryFieldExist) {
        inputAppraisalAssignmentDTO.setReqAssociateDataCompletedInd("Y");
      } else {
        inputAppraisalAssignmentDTO.setReqAssociateDataCompletedInd("N");
      }

      meHelper.addEnvelopeContextNVPair("ClaimId", "" + claimId);
      meHelper.addEnvelopeContextNVPair("ExposureId", "" + claimExposureId);

      // Do Claim activity logging for Address Validation.
      if (addressValidStsEnum != null
          && !addressValidStsEnum.equals(AddressValidStatus.UNKNOWN)) {
        String addressValidationLogMessage = "";
        if (addressValidStsEnum.equals(AddressValidStatus.VALID)) {
          if (logger.isLoggable(Level.INFO)) {
            logger.info("Address Status for Valid" + logSuffix);
          }
          addressValidationLogMessage = appraisalAssignmentConfig
              .getAddressValidateAAActivityLogPassed();
        } else {
          addressValidationLogMessage = appraisalAssignmentConfig
              .getAddressValidateAAActivityLogFailed();
        }

        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "Start Calling commonUtils.saveExposureActivityLog() for claimNumber::"
                + claimNumber);

        commonUtils.saveExposureActivityLog(addressValidationLogMessage,
            Long.valueOf(claimExposureId), documentId, workItemID,
            logedInUserInfoDocument);

        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "End Calling commonUtils.saveExposureActivityLog() for claimNumber::"
                + claimNumber);
      }

      //Save Original MitchellEnvelope Document to EstimatePackage Service.
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "Start Calling mitchellEnvelopeHandler.saveBMS() for Estimate Package service for claimNumber::"
                  + claimNumber);
      documentId = mitchellEnvelopeHandler.saveBMS(claimId, claimExposureId,
          relatedEstimateDocumentId, mitchellEnvDoc, logedInUserInfoDocument);
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "End Calling mitchellEnvelopeHandler.saveBMS() for Estimate Package service for claimNumber::"
                  + claimNumber);

      msgDetail.append("\tDocumentID: ").append(documentId);
      long moiOrgID = updateManagedByIdInClaim(true, logedInUserInfoDocument,
          claimExposureId, meHelper);
      // Create WorkAssignment task.
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start calling workAssignmentHandler.saveWorkAssignment() for claimNumber::"
              + claimNumber);
      workAssignment = workAssignmentHandler.saveWorkAssignment(documentId,
          claimId, claimExposureId, claimMask,
          AppraisalAssignmentConstants.WORK_ASSIGNMENT_CREATE_EVENT,
          inputAppraisalAssignmentDTO, logedInUserInfoDocument, null, moiOrgID);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End calling workAssignmentHandler.saveWorkAssignment() for claimNumber::"
              + claimNumber);

      msgDetail.append("\tWorkAssignmentTaskID : ").append(
          workAssignment.getTaskID().toString());

      // Do Drive-In Claim activity logging
      AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = mitchellEnvelopeHandler
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      if (scheduleDateTime != null && addAppAsmtInfo != null) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "Start calling commonUtils.doDriveInAppointmentActivityLog() for claimNumber::"
                + claimNumber);
        commonUtils.doDriveInAppointmentActivityLog(
            Long.valueOf(claimExposureId), scheduleDateTime,
            logedInUserInfoDocument, workItemID, addAppAsmtInfo,
            inputAppraisalAssignmentDTO);
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "End calling commonUtils.doDriveInAppointmentActivityLog() for claimNumber::"
                + claimNumber);
      }

      // 1. Dispatch assignment for save&send click and READY Disposition
      // 2. Update Claim activity logMessage for dispatch
      if (saveAndSendFlag
          && AppraisalAssignmentConstants.DISPOSITION_READY
              .equalsIgnoreCase(workAssignmentDisposition)) {
        final WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
            .parse(workAssignment.getWorkAssignmentCLOBB());
        // updateWorkAssignmentDocument for dispatch
        final WorkAssignmentDocument updatedWorkAssignmentDocument = workAssignmentHandler
            .setupWorkAssignmentRequest(workAssignmentDocument,
                AppraisalAssignmentConstants.DISPOSITION_DISPATCHED,
                "dispatch", logedInUserInfoDocument);
        MonitoringLogger
            .doLog(
                CLASS_NAME,
                METHOD_NAME,
                "Start calling workAssignmentHandler.saveWorkAssignment() for dispatch claimNumber::"
                    + claimNumber);
        workAssignment = workAssignmentHandler.saveWorkAssignment(
            updatedWorkAssignmentDocument, logedInUserInfoDocument);
        MonitoringLogger
            .doLog(
                CLASS_NAME,
                METHOD_NAME,
                "End calling workAssignmentHandler.saveWorkAssignment() for dispatch claimNumber::"
                    + claimNumber);
        // Set ClaimActivity log for Dispatching the assignment.
        activityLogDesc = this
            .getDispatchClaimActivityLogMessage(workAssignment);
      } else {
        activityLogDesc = appraisalAssignmentConfig.getCreateAAActivityLog();
      }

      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start calling commonUtils.saveExposureActivityLog() for claimNumber::"
              + claimNumber);
      commonUtils.saveExposureActivityLog(activityLogDesc,
          Long.valueOf(claimExposureId), documentId, workItemID,
          logedInUserInfoDocument);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End calling commonUtils.saveExposureActivityLog() for claimNumber::"
              + claimNumber);

      // Populate output AppraisalAssignmentDTO
      outputAppraisalAssignmentDTO = populateDTO(inputAppraisalAssignmentDTO,
          null, meHelper, documentId, claimId, claimExposureId, workAssignment,
          "save", workItemID, logedInUserInfoDocument);

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Updated OutputAppraisalAssignmentDTO" + logSuffix);
      }

      final String workAssignmentClobString = workAssignment
          .getWorkAssignmentCLOBB();
      final WorkAssignmentDocument updatedWorkAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(workAssignmentClobString);
      if (updatedWorkAssignmentDocument.getWorkAssignment() != null
          && updatedWorkAssignmentDocument.getWorkAssignment().getEventInfo() != null
          && updatedWorkAssignmentDocument.getWorkAssignment().getEventInfo()
              .getEvent() != null) {
        publishSANSaveAndSendEvent(logedInUserInfoDocument, saveAndSendFlag,
            claimNumber, updatedWorkAssignmentDocument.getWorkAssignment()
                .getEventInfo().getEvent().toString(),
            updatedWorkAssignmentDocument, true, null);
      }

      // Do applogging and Publish the CREATED Event to EventPublishing service.
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start calling appLogUtil.appLog() for claimNumber::" + claimNumber);
      appLogUtil
          .appLog(AppraisalAssignmentConstants.APPLOG_SAVE_ASSIGNMENT_SUCCESS,
              workAssignment, logedInUserInfoDocument, meHelper.getDoc(),
              startTime);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End calling appLogUtil.appLog() for claimNumber::" + claimNumber);

    } catch (final Exception exception) {

      final int errorCode = AppraisalAssignmentConstants.ERROR_SAVE_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_SAVE_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, logedInUserInfoDocument);
    }
    MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
        "End Calling AAS.saveSSOAppraisalAssignment()");
    logger.exiting(CLASS_NAME, METHOD_NAME);
    // Return outputDTO from AppraisalAssignmentService
    return outputAppraisalAssignmentDTO;
  }

  public AppraisalAssignmentDTO saveSSOAppraisalAssignment(
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
      final UserInfoDocument logedInUserInfoDocument,
      final boolean createClaimIfNeeded)
      throws MitchellException
  {

    return saveSSOAppraisalAssignmentInternal(inputAppraisalAssignmentDTO,
        logedInUserInfoDocument, createClaimIfNeeded);
  }

  private AppraisalAssignmentDTO saveSSOAppraisalAssignmentInternal(
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
      final UserInfoDocument logedInUserInfoDocument,
      final boolean createClaimIfNeeded)
      throws MitchellException
  {
	  
	  
    long startTime = System.currentTimeMillis();
    final String METHOD_NAME = "saveSSOAppraisalAssignmentInternal";
    logger.entering(CLASS_NAME, METHOD_NAME);
    MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
        "Start Calling AAS.saveSSOAppraisalAssignmentInternal() ");
    StringBuffer msgDetail = null;
    final String workItemID = validateMethodInputs(inputAppraisalAssignmentDTO,
        logedInUserInfoDocument);
    final StringBuffer localMethodParams = new StringBuffer();

    localMethodParams
        .append("InputAppraisalAssignmentDTO : MitchellEnvelopeDocument : ")
        .append(inputAppraisalAssignmentDTO.getMitchellEnvelopDoc().toString())
        .append("\nWorkItemID : ")
        .append(inputAppraisalAssignmentDTO.getWorkItemID())
        .append("\nLogged In UserInfoDocument : ")
        .append(logedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    Long documentId = null;

    String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    long relatedEstimateDocumentId = 0L;
    WorkAssignment workAssignment = null;
    AppraisalAssignmentDTO outputAppraisalAssignmentDTO = null;
    boolean isAllMandatoryFieldExist = false;
    String activityLogDesc = null;
    final boolean isSaveFromSSOPage = inputAppraisalAssignmentDTO
        .getIsSaveFromSSOPage();
    final boolean saveAndSendFlag = inputAppraisalAssignmentDTO
        .getSaveAndSendFlag();
    final StringBuffer logSuffix = new StringBuffer();
    String workAssignmentDisposition = null;
    McfClmOutDTO claimOutputDTO = null;

    try {

      final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
          inputAppraisalAssignmentDTO.getMitchellEnvelopDoc());
      final AssignmentAddRqDocument assignmentAddRqDocument = mitchellEnvelopeHandler
          .fetchAssignmentAddRqDocument(meHelper);
      claimNumber = assignmentAddRqDocument.getAssignmentAddRq().getClaimInfo()
          .getClaimNum();
      msgDetail.append("ClaimNumber : ").append(claimNumber);

      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Fetched AssignmentAddRq Document " + logSuffix);
      }

      // Validating Inspection Site Address
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "Start Calling mitchellEnvelopeHandler.validateInspectionSiteAddress() for GEO service for claimNumber ::"
                  + claimNumber);
      final AddressValidStatus.Enum addressValidStsEnum = mitchellEnvelopeHandler
          .validateInspectionSiteAddress(meHelper, logedInUserInfoDocument);
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "End Calling mitchellEnvelopeHandler.validateInspectionSiteAddress() for GEO service for claimNumber ::"
                  + claimNumber);

      // Fetching ClaimMask from Custom Settings
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start Calling fetchClaimMask() for Claim service for claimNumber ::"
              + claimNumber);
      final String claimMask = fetchClaimMask(workItemID,
          logedInUserInfoDocument);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End Calling fetchClaimMask() for Claim service for claimNumber ::"
              + claimNumber);

      long claimId = inputAppraisalAssignmentDTO.getClaimId();
      long claimExposureId = inputAppraisalAssignmentDTO.getClaimExposureId();
      

      /*
       * 
       * SV Refactored.
       * New mode createClaimIfNeeded indicates if claim needs to be created.
       * if true and
       * input doesn't contain claim or exposure id, claim will be created
       * input contains claim & exposure id, claim/exposure will not be created
       * or updated
       * if false and
       * input doesn't contain claim or exposure id, exception will be thrown
       */
      if (claimId <= 0 || claimExposureId <= 0) {
        // New Mode for GW Integration
        if (createClaimIfNeeded) {
        	
          if (logger.isLoggable(Level.INFO)) {
            logger.info("Claim Suffix ID ::" + claimExposureId + logSuffix);
          }

          if (claimExposureId <= 0) {
            MonitoringLogger
                .doLog(
                    CLASS_NAME,
                    METHOD_NAME,
                    "Start Calling storeClaimInfo() For CLaim Service for Claim service for claimNumber ::"
                        + claimNumber);

            claimOutputDTO = storeClaimInfo(claimMask, meHelper, workItemID,
                logedInUserInfoDocument, logSuffix, inputAppraisalAssignmentDTO);

            MonitoringLogger
                .doLog(
                    CLASS_NAME,
                    METHOD_NAME,
                    "End Calling storeClaimInfo() For CLaim Service for Claim service for claimNumber ::"
                        + claimNumber);

            claimId = claimOutputDTO.getClaimID().longValue();
            claimNumber = claimOutputDTO.getClaim().getClaimNumber();
            claimExposureId = claimOutputDTO.getExposureID().longValue();

            msgDetail.append("After storeClaimInfo :: claimId : ")
                .append(claimId).append("claimNumber : ").append(claimNumber)
                .append("claimExposureId : ").append(claimExposureId);
          }
          meHelper.addEnvelopeContextNVPair("ClaimId", "" + claimId);
          meHelper.addEnvelopeContextNVPair("ExposureId", "" + claimExposureId);

        }
        // old mode for PGR integration
        else {
          throw new MitchellException(CLASS_NAME, METHOD_NAME,
              "ClaimID and ExposureID can NOT be <= 0 . Received ClaimID : "
                  + claimId + "\tClaimExposureID : " + claimExposureId);

        }

      }

      // Fetch Data from input ME
      final MitchellEnvelopeDocument mitchellEnvDoc = meHelper.getDoc();
      final CIECADocument ciecaDocument = mitchellEnvelopeHandler
          .getCiecaFromME(meHelper);
      final String assigneeId = getAssigneeIdFromCiecaDocument(ciecaDocument);
      final String groupCode = getGroupCodeFromCiecaDocument(ciecaDocument);
      final AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = mitchellEnvelopeHandler
          .getAdditionalTaskConstraintsFromME(meHelper);
      final java.util.Calendar scheduleDateTime = getScheduleDateTime(additionalTaskConstraintsDocument);

      // Check Mandatory Fields set :: Check for invalid assignment address
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "Start Calling isAssignmentDataReadyForSSO() for Mandatory fields / Vehicle Ready for claimNumber ::"
                  + claimNumber);
      isAllMandatoryFieldExist = this
          .isAssignmentDataReadyForSSO(mitchellEnvDoc);
      
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "End Calling isAssignmentDataReadyForSSO() for Mandatory fields / Vehicle Ready for claimNumber ::"
                  + claimNumber);

      if (logger.isLoggable(Level.INFO)) {
        StringBuffer logMessage = new StringBuffer();
        logMessage.append("isAllMandatoryFieldExist in ");
        logMessage.append(METHOD_NAME);
        logMessage.append(" : ");
        logMessage.append(isAllMandatoryFieldExist);
        logMessage.append(logSuffix);
        logger.info(logMessage.toString());
        logMessage = null;
      }
      // Determine assignment disposition
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "Start Calling isAssignmentReadyForDispatchForSSO() for UserInfo Service and  appraisalAssignmentDAO.getWorkGroupType() for claimNumber ::"
                  + claimNumber);
      workAssignmentDisposition = this.isAssignmentReadyForDispatchForSSO(
          isAllMandatoryFieldExist, assigneeId, groupCode, scheduleDateTime,
          logedInUserInfoDocument);
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "End Calling isAssignmentReadyForDispatchForSSO() for UserInfo Service and  appraisalAssignmentDAO.getWorkGroupType() for claimNumber ::"
                  + claimNumber);

      inputAppraisalAssignmentDTO.setDisposition(workAssignmentDisposition);

      // Set Mandatory Field flag in inputAADTO
      if (isAllMandatoryFieldExist) {
        inputAppraisalAssignmentDTO.setReqAssociateDataCompletedInd("Y");
      } else {
        inputAppraisalAssignmentDTO.setReqAssociateDataCompletedInd("N");
      }

      meHelper.addEnvelopeContextNVPair("ClaimId", "" + claimId);
      meHelper.addEnvelopeContextNVPair("ExposureId", "" + claimExposureId);

      // sv added - not sure why below are not populated if claim doesn't exist
      if (createClaimIfNeeded) {
        // adding WorkItemID
        meHelper.addEnvelopeContextNVPair(
            AppraisalAssignmentConstants.MITCHELL_ENV_NAME_MITCHELL_WORKITEMID,
            "" + workItemID);

        // adding SystemUserId
        meHelper.addEnvelopeContextNVPair("SystemUserId", ""
            + logedInUserInfoDocument.getUserInfo().getUserID());
      }

      // Do Claim activity logging for Address Validation.
      if (addressValidStsEnum != null
          && !addressValidStsEnum.equals(AddressValidStatus.UNKNOWN)) {
        String addressValidationLogMessage = "";
        if (addressValidStsEnum.equals(AddressValidStatus.VALID)) {
          if (logger.isLoggable(Level.INFO)) {
            logger.info("Address Status for Valid" + logSuffix);
          }
          addressValidationLogMessage = appraisalAssignmentConfig
              .getAddressValidateAAActivityLogPassed();
        } else {
          addressValidationLogMessage = appraisalAssignmentConfig
              .getAddressValidateAAActivityLogFailed();
        }

        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "Start Calling commonUtils.saveExposureActivityLog() for claimNumber::"
                + claimNumber);

        commonUtils.saveExposureActivityLog(addressValidationLogMessage,
            Long.valueOf(claimExposureId), documentId, workItemID,
            logedInUserInfoDocument);

        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "End Calling commonUtils.saveExposureActivityLog() for claimNumber::"
                + claimNumber);
      }

      //Save Original MitchellEnvelope Document to EstimatePackage Service.
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "Start Calling mitchellEnvelopeHandler.saveBMS() for Estimate Package service for claimNumber ::"
                  + claimNumber);
      documentId = mitchellEnvelopeHandler.saveBMS(claimId, claimExposureId,
          relatedEstimateDocumentId, mitchellEnvDoc, logedInUserInfoDocument);
      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "End Calling mitchellEnvelopeHandler.saveBMS() for Estimate Package service for claimNumber ::"
                  + claimNumber);

      msgDetail.append("\tDocumentID: ").append(documentId);
      long moiOrgID = updateManagedByIdInClaim(true, logedInUserInfoDocument,
          claimExposureId, meHelper);

      MonitoringLogger
          .doLog(
              CLASS_NAME,
              METHOD_NAME,
              "ME before calling workAssignmentHandler.saveWorkAssignment() for claimNumber ::"
                  + meHelper.getDoc().toString());

      // Create WorkAssignment task.
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start calling workAssignmentHandler.saveWorkAssignment() for claimNumber ::"
              + claimNumber);
      workAssignment = workAssignmentHandler.saveWorkAssignment(documentId,
          claimId, claimExposureId, claimMask,
          AppraisalAssignmentConstants.WORK_ASSIGNMENT_CREATE_EVENT,
          inputAppraisalAssignmentDTO, logedInUserInfoDocument, null, moiOrgID);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End calling workAssignmentHandler.saveWorkAssignment() for claimNumber ::"
              + claimNumber);

      msgDetail.append("\tWorkAssignmentTaskID : ").append(
          workAssignment.getTaskID().toString());

      // Do Drive-In Claim activity logging
      AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = mitchellEnvelopeHandler
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      if (scheduleDateTime != null && addAppAsmtInfo != null) {
        MonitoringLogger
            .doLog(
                CLASS_NAME,
                METHOD_NAME,
                "Start calling commonUtils.doDriveInAppointmentActivityLog() for claimNumber ::"
                    + claimNumber);
        commonUtils.doDriveInAppointmentActivityLog(
            Long.valueOf(claimExposureId), scheduleDateTime,
            logedInUserInfoDocument, workItemID, addAppAsmtInfo,
            inputAppraisalAssignmentDTO);
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "End calling commonUtils.doDriveInAppointmentActivityLog() for claimNumber ::"
                + claimNumber);
      }

      // 1. Dispatch assignment for save&send click and READY Disposition
      // 2. Update Claim activity logMessage for dispatch
      if (saveAndSendFlag
          && AppraisalAssignmentConstants.DISPOSITION_READY
              .equalsIgnoreCase(workAssignmentDisposition)) {
        final WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
            .parse(workAssignment.getWorkAssignmentCLOBB());
        // updateWorkAssignmentDocument for dispatch
        final WorkAssignmentDocument updatedWorkAssignmentDocument = workAssignmentHandler
            .setupWorkAssignmentRequest(workAssignmentDocument,
                AppraisalAssignmentConstants.DISPOSITION_DISPATCHED,
                "dispatch", logedInUserInfoDocument);
        MonitoringLogger
            .doLog(
                CLASS_NAME,
                METHOD_NAME,
                "Start calling workAssignmentHandler.saveWorkAssignment() for dispatch claimNumber ::"
                    + claimNumber);
        workAssignment = workAssignmentHandler.saveWorkAssignment(
            updatedWorkAssignmentDocument, logedInUserInfoDocument);
        MonitoringLogger
            .doLog(
                CLASS_NAME,
                METHOD_NAME,
                "End calling workAssignmentHandler.saveWorkAssignment() for dispatch claimNumber ::"
                    + claimNumber);
        // Set ClaimActivity log for Dispatching the assignment.
        activityLogDesc = this
            .getDispatchClaimActivityLogMessage(workAssignment);
      } else {
        activityLogDesc = appraisalAssignmentConfig.getCreateAAActivityLog();
      }

      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start calling commonUtils.saveExposureActivityLog() for claimNumber::"
              + claimNumber);
      commonUtils.saveExposureActivityLog(activityLogDesc,
          Long.valueOf(claimExposureId), documentId, workItemID,
          logedInUserInfoDocument);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End calling commonUtils.saveExposureActivityLog() for claimNumber::"
              + claimNumber);

      // Populate output AppraisalAssignmentDTO
      outputAppraisalAssignmentDTO = populateDTO(inputAppraisalAssignmentDTO,
          null, meHelper, documentId, claimId, claimExposureId, workAssignment,
          "save", workItemID, logedInUserInfoDocument);

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Updated OutputAppraisalAssignmentDTO" + logSuffix);
      }

      final String workAssignmentClobString = workAssignment
          .getWorkAssignmentCLOBB();
      final WorkAssignmentDocument updatedWorkAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(workAssignmentClobString);
      if (updatedWorkAssignmentDocument.getWorkAssignment() != null
          && updatedWorkAssignmentDocument.getWorkAssignment().getEventInfo() != null
          && updatedWorkAssignmentDocument.getWorkAssignment().getEventInfo()
              .getEvent() != null) {
        publishSANSaveAndSendEvent(logedInUserInfoDocument, saveAndSendFlag,
            claimNumber, updatedWorkAssignmentDocument.getWorkAssignment()
                .getEventInfo().getEvent().toString(),
            updatedWorkAssignmentDocument, true, addAppAsmtInfo);
      }

      // Do applogging and Publish the CREATED Event to EventPublishing service.
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start calling appLogUtil.appLog() for claimNumber::" + claimNumber);
      appLogUtil
          .appLog(AppraisalAssignmentConstants.APPLOG_SAVE_ASSIGNMENT_SUCCESS,
              workAssignment, logedInUserInfoDocument, meHelper.getDoc(),
              startTime);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End calling appLogUtil.appLog() for claimNumber::" + claimNumber);

    } catch (final Exception exception) {

      final int errorCode = AppraisalAssignmentConstants.ERROR_SAVE_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_SAVE_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, logedInUserInfoDocument);
    }
    MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
        "End Calling AAS.saveSSOAppraisalAssignment()");
    logger.exiting(CLASS_NAME, METHOD_NAME);
    // Return outputDTO from AppraisalAssignmentService
    return outputAppraisalAssignmentDTO;
  }

  /**
   * Method validate the input parameters and return WorkItemID
   * scheduleDateTime .
   * 
   * @param inputAppraisalAssignmentDTO
   *          AppraisalAssignmentDTO.
   * @param UserInfoDocument
   *          userInfoDocument.
   * @return Calendar scheduleDateTime.
   * @throws mitchellException
   *           MitchellException.
   */
  private String validateMethodInputs(
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
      final UserInfoDocument userInfoDocument)
      throws MitchellException
  {
    String workItemID = null;
    if (userInfoDocument == null) {
      throw new MitchellException(CLASS_NAME, "validateMethodInputs",
          "Received NULL UserInfoDocument in AppraisalAssignment Service.");
    }
    if (inputAppraisalAssignmentDTO != null) {
      if (inputAppraisalAssignmentDTO.getMitchellEnvelopDoc() == null) {
        throw new MitchellException(CLASS_NAME, "validateMethodInputs",
            "Received NULL MitchellEnvelopeDocument in AppraisalAssignmentDTO.");
      } else {
        workItemID = inputAppraisalAssignmentDTO.getWorkItemID();
      }
    }
    return workItemID;
  }

  /**
   * This is helper method which calls carrier specific custom settings to
   * retrieve Claim Mask Parsing Rule.
   * 
   * @return Claim Mask Parsing Rule.
   * @throws Exception
   */
  private String fetchClaimMask(final String workItemID,
      final UserInfoDocument userInfoDocument)
      throws Exception
  {
    final String METHOD_NAME = "fetchClaimMask";
    logger.entering(CLASS_NAME, METHOD_NAME);
    final String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    final String msgDetail = null;
    String claimMask = "";
    try {
      final String orgCoCode = userInfoDocument.getUserInfo().getOrgCode();
      if (logger.isLoggable(Level.INFO)) {
        logger.info("Fetched orgCoCode from User Info: " + orgCoCode);
      }
      claimMask = appraisalAssignmentUtils
          .retrieveCustomSettings(
              orgCoCode,
              orgCoCode,
              AppraisalAssignmentConstants.CSET_CLIAM_GROUP_NAME,
              AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CLAIMSERVICES_CLAIM_CLAIMNUMBERMASK);
      if (logger.isLoggable(Level.INFO)) {
        logger.info("Fetched claimMask from AppraisalAssignmentUtils: "
            + claimMask);
      }
    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_AA_FETCHCLAIMMASK;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_AA_FETCHCLAIMMASK_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail, exception,
          userInfoDocument);

    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return claimMask;
  }

  /**
   * Method return AssigneeId from CIECADocument.
   * 
   * @param ciecaDocument
   *          CIECADocument.
   * @return String AssigneeId.
   */
  private String getAssigneeIdFromCiecaDocument(
      final CIECADocument ciecaDocument)
  {
    String assigneeId = null;

    if (ciecaDocument != null
        && ciecaDocument.getCIECA().isSetAssignmentAddRq()
        && ciecaDocument.getCIECA().getAssignmentAddRq()
            .isSetVehicleDamageAssignment()
        && ciecaDocument.getCIECA().getAssignmentAddRq()
            .getVehicleDamageAssignment().isSetEstimatorIDs()
        && ciecaDocument.getCIECA().getAssignmentAddRq()
            .getVehicleDamageAssignment().getEstimatorIDs()
            .isSetCurrentEstimatorID()) {
      assigneeId = ciecaDocument.getCIECA().getAssignmentAddRq()
          .getVehicleDamageAssignment().getEstimatorIDs()
          .getCurrentEstimatorID();
    }
    return assigneeId;
  }

  /**
   * Method return GroupCode from CIECADocument.
   * 
   * @param ciecaDocument
   *          CIECADocument.
   * @return String GroupCode.
   */
  private String getGroupCodeFromCiecaDocument(final CIECADocument ciecaDocument)
  {
    String groupCode = null;
    if (ciecaDocument != null
        && ciecaDocument.getCIECA().isSetAssignmentAddRq()
        && ciecaDocument.getCIECA().getAssignmentAddRq()
            .isSetVehicleDamageAssignment()
        && ciecaDocument.getCIECA().getAssignmentAddRq()
            .getVehicleDamageAssignment().isSetEstimatorIDs()
        && ciecaDocument.getCIECA().getAssignmentAddRq()
            .getVehicleDamageAssignment().getEstimatorIDs()
            .isSetRoutingIDInfo()) {
      groupCode = ciecaDocument.getCIECA().getAssignmentAddRq()
          .getVehicleDamageAssignment().getEstimatorIDs().getRoutingIDInfo()
          .getIDNum();
    }
    return groupCode;
  }

  /**
   * Method return scheduleDateTime from AdditionalTaskConstraintsDocument.
   * 
   * @param additionalTaskConstraintsDocument
   *          AdditionalTaskConstraintsDocument.
   * @return Calendar scheduleDateTime.
   */
  private Calendar getScheduleDateTime(
      final AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument)
  {
    Calendar scheduleDateTime = null;
    if (additionalTaskConstraintsDocument != null
        && additionalTaskConstraintsDocument.getAdditionalTaskConstraints() != null
        && additionalTaskConstraintsDocument.getAdditionalTaskConstraints()
            .getScheduleInfo() != null
        && additionalTaskConstraintsDocument.getAdditionalTaskConstraints()
            .getScheduleInfo().getScheduledDateTime() != null) {
      scheduleDateTime = additionalTaskConstraintsDocument
          .getAdditionalTaskConstraints().getScheduleInfo()
          .getScheduledDateTime();
    }
    return scheduleDateTime;
  }

  /**
   * <p>
   * This method performs business-rule validation of the input
   * MitchellEnvelopeDoc to verify that Appraisal Assignment IsReady for
   * Dispatch
   * </p>
   * <ul>
   * <br>
   * ** NOTE1: <br>
   * ** Mandatory field business-rule validation is based on comparison of XML
   * fields <br>
   * ** in the provided Appraisal Assignment MitchellEnvelope with the <br>
   * ** WCAA Company-Level Custom Settings for confirmation that all <br>
   * ** required/mandatory fields are present. <br>
   * ** NOTE2: <br>
   * ** Additionally, the Appraisal Assignment Svc Custom Setting
   * DISPATCH_WITH_INVALID_ADDRESS <br>
   * ** is referenced to provide an override option for dispatching Carrier-Feed
   * Assignments <br>
   * ** even for the case of an Invalid/Missing InspectionSite Address
   * </ul>
   * 
   * @param mitchellEnvDoc
   *          Object of MitchellEnvelopeDocument
   * @param loggedInUserInfoDocument
   *          UserInfo Document of Logged In user.
   * @return boolean - returns TRUE if all mandatory fields are present and
   *         Vehicle Location Business rule is TRUE
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   * 
   */
  public boolean isAssignmentDataReady(
      final MitchellEnvelopeDocument mitchellEnvDoc)
      throws MitchellException
  {

    final String METHOD_NAME = "isAssignmentDataReady";
    logger.entering(CLASS_NAME, METHOD_NAME);
    boolean isVehicleReady = false;
    boolean isAssignmentReady = false;
    boolean hasAllMandatoryFields = false;

    try {

      // get AdditionalAppraisalAssignmentInfo and AdditionalTaskConstraints documents
      MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
          mitchellEnvDoc);
      AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument = appraisalAssignmentMandFieldUtils
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = appraisalAssignmentMandFieldUtils
          .getAdditionalTaskConstraintsFromME(meHelper);

      // Business Rule1: Verify that all WCAA Carrier Mandatory Fields are
      // present

      long subStartTime = System.currentTimeMillis();
      hasAllMandatoryFields = appraisalAssignmentMandFieldUtils
          .hasAllMandatoryAppraisalAssignmentFields(meHelper,
              additionalAppraisalAssignmentInfoDocument,
              additionalTaskConstraintsDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "appraisalAssignmentMandFieldUtils", System.currentTimeMillis()
                - subStartTime);
      }

      // Business Rule2: Verify whether the Vehicle Location
      // (InspectionSite GeoCoded Address) is Present and Valid

      subStartTime = System.currentTimeMillis();
      isVehicleReady = appraisalAssignmentMandFieldUtils
          .isVehicleReadyForDispatch(meHelper,
              additionalAppraisalAssignmentInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "isVehicleReadyForDispatch", System.currentTimeMillis()
                - subStartTime);
      }

    } catch (final Exception e) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS,
          CLASS_NAME, METHOD_NAME,
          AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS_MSG
              + e.getMessage(), e);
    }
    
    if (hasAllMandatoryFields && isVehicleReady) {
      isAssignmentReady = true;
    }
    if (logger.isLoggable(Level.INFO)) {
      logger
          .info("return value - isAssignmentReadyForDispatch::isAssignmentReady"
              + isAssignmentReady);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAssignmentReady;
  }

  /**
   * This method checks whether the Mandatory/Required fields are filled up or
   * not
   * 
   * @param mandatoryFieldFlag
   * @param assigneeID
   * @param groupCode
   * @param scheduleDateTime
   * @return String
   * 
   */
  public String isAssignmentReadyForDispatch(final boolean mandatoryFieldFlag,
      final String assigneeID, final String groupCode,
      final java.util.Calendar scheduleDateTime,
      final UserInfoDocument userInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "isAssignmentReadyForDispatch";
    logger.entering(CLASS_NAME, METHOD_NAME);

    long methodStartTime = System.currentTimeMillis();
    long subStartTime = 0;

    if (logger.isLoggable(Level.FINE)) {
      StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("MandatoryFieldFlag : ")
          .append(mandatoryFieldFlag).append("\tassigneeID : ")
          .append(assigneeID).append("\ngroupCode : ").append(groupCode)
          .append("\nscheduleDateTime : ").append(scheduleDateTime)
          .append("\nUserInfoDocument : ").append(userInfoDocument.toString());
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    String flag = "N";
    String disposition = AppraisalAssignmentConstants.DISPOSITION_NOT_READY;
    final String claimNumber = null;
    final String workItemID = null;
    String reqAssigneeType = null;
    try {
      if (userInfoDocument != null && userInfoDocument.getUserInfo() != null) {
        final String orgCoCode = userInfoDocument.getUserInfo().getOrgCode();
        if (logger.isLoggable(Level.INFO)) {
          logger.info("Fetched orgCoCode from User Info: " + orgCoCode);
        }
        String workGroupType = null;
        reqAssigneeType = userInfoUtils.getUserType(orgCoCode, assigneeID);

        if (logger.isLoggable(Level.INFO)) {
          logger.info("Fetched reqAssigneeType from : " + reqAssigneeType);
        }

        if (groupCode != null) {

          subStartTime = System.currentTimeMillis();
          workGroupType = appraisalAssignmentDAO.getWorkGroupType(orgCoCode,
              groupCode);
          if (MonitoringLogger.isLoggableTimer()) {
            MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
                "appraisalAssignmentDAO.getWorkGroupType: " + assigneeID,
                System.currentTimeMillis() - subStartTime);
          }

          if (logger.isLoggable(Level.INFO)) {
            logger.info("Fetched workGroup from : " + workGroupType);
          }
        }
        if (AppraisalAssignmentConstants.SERVICE_CENTER.equals(workGroupType)) {
          if (assigneeID != null && mandatoryFieldFlag) {
            disposition = AppraisalAssignmentConstants.DISPOSITION_READY;
          }
        } else {
          if (UserTypeConstants.STAFF_TYPE.equalsIgnoreCase(reqAssigneeType)) {
            flag = userInfoUtils.getScheduleFlagForStaff(orgCoCode);
            if (logger.isLoggable(Level.INFO)) {
              logger.info("Fetched ScheduleFlagForStaff : " + flag);
            }
          }
          if ("Y".equalsIgnoreCase(flag)) {
            if (assigneeID != null && scheduleDateTime != null
                && mandatoryFieldFlag) {
              disposition = AppraisalAssignmentConstants.DISPOSITION_READY;
            }

          } else {
            if (assigneeID != null && mandatoryFieldFlag) {
              disposition = AppraisalAssignmentConstants.DISPOSITION_READY;
            }
          }
        }

      }
    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_ASSIGNMENT_READYFORDISPATCH;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_ASSIGNMENT_READYFORDISPATCH_MSG;

      StringBuffer msgDetail = new StringBuffer();
      msgDetail.append("MandatoryFieldFlag : ").append(mandatoryFieldFlag)
          .append("\tassigneeID : ").append(assigneeID)
          .append("\ngroupCode : ").append(groupCode)
          .append("\nscheduleDateTime : ").append(scheduleDateTime)
          .append("\nUserInfoDocument : ").append(userInfoDocument.toString());

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, userInfoDocument);
    }

    if (MonitoringLogger.isLoggableTimer()) {
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "fullMethodTime: "
          + assigneeID, System.currentTimeMillis() - methodStartTime);
    }

    if (logger.isLoggable(Level.INFO)) {
      logger.info("Disposition from isAssignmentReadyForDispatch : "
          + disposition);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return disposition;
  }

  /**
   * The method is responsible for creating and updating a claim structure
   * 
   * @param parsingRule
   *          This is carrier specific ClaimMask parssing rule.
   * @return McfClmOutDTO
   * @throws Exception
   *           Throws Exception to the caller in case of any exception
   *           arise.
   */
  private McfClmOutDTO storeClaimInfo(final String parsingRule,
      final MitchellEnvelopeHelper meHelper, final String workItemID,
      final UserInfoDocument userInfoDocument, StringBuffer logSuffix, AppraisalAssignmentDTO inputAppraisalAssignmentDTO)
      throws MitchellException
  {
    final String METHOD_NAME = "storeClaimInfo";
    logger.entering(CLASS_NAME, METHOD_NAME);
    if (logger.isLoggable(Level.INFO)) {
      logger.info("Input Received :: parsingRule: " + parsingRule + logSuffix);
    }
    Long claimId = null;
    Long exposureID = null;
    McfClmOutDTO resultDTO = null;
    BmsClmInputDTO inputDTO = null;
    StringBuffer msgDetail = new StringBuffer();

    /*
     * adjusterInfo will be null in case of create
     */
    UserInfoDocument adjusterUserInfo = null;
    AssignmentAddRqDocument assignAddRqDoc = null;
    try {
      assignAddRqDoc = mitchellEnvelopeHandler
          .fetchAssignmentAddRqDocument(meHelper);
      final AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = mitchellEnvelopeHandler
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      String userId = null;
      // Getting company code from logdInUsrInfo
      final String companyCode = userInfoDocument.getUserInfo().getOrgCode();
      if (logger.isLoggable(Level.INFO)) {
        logger.info("CompanyCode fetched from logdInUsrInfo = " + companyCode
            + logSuffix);
      }
      PropertyInfoType cmltype = null;
      final ClaimProperty clmProperty = new ClaimProperty();
      if (assignAddRqDoc != null
          && assignAddRqDoc.getAssignmentAddRq().getAdminInfo().isSetAdjuster()) {
        if (assignAddRqDoc.getAssignmentAddRq().getAdminInfo().getAdjuster()
            .getParty().isSetPersonInfo()) {
          if ((assignAddRqDoc.getAssignmentAddRq().getAdminInfo().getAdjuster()
              .getParty().getPersonInfo().getIDInfoArray() != null)
              && (assignAddRqDoc.getAssignmentAddRq().getAdminInfo()
                  .getAdjuster().getParty().getPersonInfo().getIDInfoArray().length >= 1)) {
            userId = assignAddRqDoc.getAssignmentAddRq().getAdminInfo()
                .getAdjuster().getParty().getPersonInfo().getIDInfoArray(0)
                .getIDNum();
            if (logger.isLoggable(Level.INFO)) {
              logger.info("UserId fetched from assignAddRqDoc = " + userId
                  + logSuffix);
            }
            adjusterUserInfo = userInfoUtils.retrieveUserInfo(companyCode,
                userId);
            if (logger.isLoggable(Level.FINE)) {
              logger.fine("Retrieved adjusterUserInfo from retrieveUserInfo = "
                  + adjusterUserInfo + logSuffix);
            }
          }
        }
      }
      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("adjusterUserInfo passed to createInputDTO method of Claim API = "
                + adjusterUserInfo + logSuffix);
      }

      inputDTO = claimProxy.createInputDTO(assignAddRqDoc, adjusterUserInfo,
          parsingRule, userInfoDocument);
      
      if (logger.isLoggable(Level.INFO)) {
        logger.info("Fetched InputDTO from createInputDTO of Claim Service"
            + inputDTO + logSuffix);
      }
      // property info
      if (aaaInfoDoc != null) {
        cmltype = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .getPropertyInfo();
        // Address
        if (cmltype != null && cmltype.getPropertyDamageAssignment() != null) {
          if (cmltype.getPropertyDamageAssignment().isSetPropertyAddress()) {
            final com.mitchell.schemas.appraisalassignment.AddressType propertyAddress = cmltype
                .getPropertyDamageAssignment().getPropertyAddress();
            if (propertyAddress.isSetAddress1()) {
              clmProperty.setAddress1(propertyAddress.getAddress1());
            }

            if (propertyAddress.isSetAddress2()) {
              clmProperty.setAddress2(propertyAddress.getAddress2());
            }

            if (propertyAddress.isSetCity()) {
              clmProperty.setCity(propertyAddress.getCity());
            }

            if (propertyAddress.isSetStateProvince()) {
              clmProperty.setStateProvince(propertyAddress.getStateProvince());
            }

            if (propertyAddress.isSetPostalCode()) {
              clmProperty.setZipPostalCode(propertyAddress.getPostalCode());
            }
          }
          // property descrioption

          if (cmltype.getPropertyDamageAssignment().isSetPropertyDescription()) {
            clmProperty.setPropertyDescription(cmltype
                .getPropertyDamageAssignment().getPropertyDescription());
          }
          // property type
          if (cmltype.getPropertyDamageAssignment().getPropertyType() != null) {
            inputDTO.setPropertyType(cmltype.getPropertyDamageAssignment()
                .getPropertyType());
          }

          // setting the claim property type in the DTO
          if (logger.isLoggable(Level.INFO)) {
            logger.info("set the clmProperty" + logSuffix);
          }
          inputDTO.setClaimProperty(clmProperty);

        }

        if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo() != null
            && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getAdditionalAssignmentDetails() != null
            && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getAdditionalAssignmentDetails().isSetIsCAT()) {
          inputDTO.setCatIndicator(aaaInfoDoc
              .getAdditionalAppraisalAssignmentInfo()
              .getAdditionalAssignmentDetails().getIsCAT());
        }
      }
      
      //set suffix status in inputDTO such as ON_HOLD
		if (inputAppraisalAssignmentDTO.getSuffixStatus() != null
				&& !inputAppraisalAssignmentDTO.getSuffixStatus().isEmpty()) {

			logger.info("set the suffix status: "
					+ inputAppraisalAssignmentDTO.getSuffixStatus()
					+ " in inputDTO");
			inputDTO.setSuffixStatus(inputAppraisalAssignmentDTO
					.getSuffixStatus());
		}
		
		//set ShopEstimateAllowed flag in inputDTO
		if (inputAppraisalAssignmentDTO.getShopEstimateAllowed() != null
				&& !inputAppraisalAssignmentDTO.getShopEstimateAllowed()
						.isEmpty()) {

			logger.info("set the shopEstimateAllowed: "
					+ inputAppraisalAssignmentDTO.getShopEstimateAllowed()
					+ " in inputDTO");
			inputDTO.setAssignmentPullFlag(inputAppraisalAssignmentDTO
					.getShopEstimateAllowed());
		}
		
      resultDTO = claimProxy.saveClaimFromAssignmentBms(inputDTO);
      claimId = resultDTO.getClaimID();
      exposureID = resultDTO.getExposureID();

      if (logger.isLoggable(Level.INFO)) {
        final StringBuffer logMessage = new StringBuffer();
        logMessage.append("Claim Saved Successfully with claimID = ");
        logMessage.append(claimId);
        logMessage.append(", exposureID = ");
        logMessage.append(exposureID);
        logMessage.append(logSuffix);
        logger.info(logMessage.toString());

      }

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_SAVE_AA_STORECLAIMINFO;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_SAVE_AA_STORECLAIMINFO_MSG;

      if (assignAddRqDoc != null) {
        msgDetail.append(assignAddRqDoc.toString());
      }

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID,
          AppraisalAssignmentConstants.CLAIM_NUMBER, msgDetail.toString(),
          exception, userInfoDocument);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return resultDTO;
  }

  private AppraisalAssignmentDTO populateDTO(
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
      final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO,
      final MitchellEnvelopeHelper meHelper, final Long documentId,
      final long claimId, final long claimExposureId,
      final WorkAssignment workAssignment, final String method,
      final String workItemID, final UserInfoDocument logedUserInfoDocument)
      throws MitchellException
  {
    final AppraisalAssignmentDTO outputAppraisalAssignmentDTO = new AppraisalAssignmentDTO();
    final String methodName = "populateDTO";
    long wasTaskId = 0;

    try {
      wasTaskId = workAssignment.getTaskID().longValue();

      outputAppraisalAssignmentDTO.setMitchellEnvelopDoc(meHelper.getDoc());
      outputAppraisalAssignmentDTO.setWaTaskId(wasTaskId);

      outputAppraisalAssignmentDTO.setWorkAssignmentTcn(workAssignment.getTcn()
          .longValue());
      if (appraisalAssignmentDTO != null)
        outputAppraisalAssignmentDTO.setTcn(appraisalAssignmentDTO
            .getEstPkgAppAsgDoc().getEstPkgAppraisalAssignment().getTCN());
      outputAppraisalAssignmentDTO.setClaimId(claimId);
      outputAppraisalAssignmentDTO.setClaimExposureId(claimExposureId);
      outputAppraisalAssignmentDTO.setDocumentID(documentId.longValue());
      outputAppraisalAssignmentDTO.setStatus(workAssignment
          .getWorkAssignmentStatus());
      outputAppraisalAssignmentDTO.setDisposition(workAssignment
          .getAssignmentDisposition().getDispositionCode());
      if (logger.isLoggable(Level.INFO)) {
        logger.info("TimeZone ::::" + workAssignment.getTimeZone());
      }
      outputAppraisalAssignmentDTO.setTimeZone(workAssignment.getTimeZone());

      if (method.equalsIgnoreCase("update")) {
        outputAppraisalAssignmentDTO
            .setAssignmentHasBeenUpdate(inputAppraisalAssignmentDTO
                .getAssignmentHasBeenUpdate());
      }
      if (inputAppraisalAssignmentDTO.getShopEstimateAllowed() != null) {
			outputAppraisalAssignmentDTO
					.setShopEstimateAllowed(inputAppraisalAssignmentDTO
							.getShopEstimateAllowed());
		}
      outputAppraisalAssignmentDTO.setAssignmentID(workAssignment.getId()
          .longValue());
      outputAppraisalAssignmentDTO
          .setClientClaimNumber(inputAppraisalAssignmentDTO
              .getClientClaimNumber());
      outputAppraisalAssignmentDTO
          .setReqAssociateDataCompletedInd(inputAppraisalAssignmentDTO
              .getReqAssociateDataCompletedInd());

      // Set SubType
      final AssignmentSubType assignmentSubType = workAssignment
          .getAssignmentSubType();
      if (assignmentSubType != null
          && assignmentSubType.getAssignmentSubTypeCode() != null)
        outputAppraisalAssignmentDTO.setSubType(assignmentSubType
            .getAssignmentSubTypeCode());

      // Duration
      final String WorkAssignmentCLOB = workAssignment.getWorkAssignmentCLOBB();
      WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(WorkAssignmentCLOB);
      if (workAssignmentDocument.getWorkAssignment() != null
          && workAssignmentDocument.getWorkAssignment()
              .getScheduleConstraints() != null
          && workAssignmentDocument.getWorkAssignment()
              .getScheduleConstraints().getDuration() != null) {
        outputAppraisalAssignmentDTO.setDuration(workAssignmentDocument
            .getWorkAssignment().getScheduleConstraints().getDuration());
      }
      // Priority
      final Integer priority = workAssignment.gettaskPriority();
      if (priority != null)
        outputAppraisalAssignmentDTO.setPriority(priority);

    } catch (XmlException xmle) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_AA_POPULATEAADTO, CLASS_NAME,
          methodName, "Exception Parsing WorkAssignment clob. TaskId="
              + wasTaskId + ", " + xmle.getMessage(), xmle);
    }

    return outputAppraisalAssignmentDTO;
  }

  /**
   * This method performs AssignmentHistory Logging
   * 
   * @param eventName
   * @param eventDescription
   * @param workAssignment
   * @throws MitchellException
   * @throws Exception
   *           Throws Exception to the caller in case of any exception
   *           arise.
   * 
   */
  public void writeAssignmentActivityLog(final long assignmentId,
      final String eventName, final String eventDescription,
      final String createdBy)
      throws MitchellException
  {
    final String METHOD_NAME = "writeAssignmentActivityLog";
    logger.entering(CLASS_NAME, METHOD_NAME);
    final String claimNumber = null;
    final String workItemID = null;

    if (logger.isLoggable(Level.FINE)) {
      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("assignmentId : ").append(assignmentId)
          .append("\neventName : ").append(eventName)
          .append("\neventDescription : ").append(eventDescription)
          .append("\ncreatedBy : ").append(createdBy);
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    try {
      appraisalAssignmentDAO.writeAssignmentActivityLog(assignmentId,
          eventName, eventDescription, new java.util.Date(), createdBy);
    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_WRITEING_ASSIGNMENTACTIVITYLOG;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_WRITEING_ASSIGNMENTACTIVITYLOG_MSG;

      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("assignmentId : ").append(assignmentId)
          .append("\neventName : ").append(eventName)
          .append("\neventDescription : ").append(eventDescription)
          .append("\ncreatedBy : ").append(createdBy);

      errorLogUtil.logWarning(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription + localMethodParams, workItemID, exception,
          claimNumber, null);
    }

    logger.info("AssignmentActivityLog Done Successfully !!!");

    logger.exiting(CLASS_NAME, METHOD_NAME);
  }

  /**
   * <p>
   * This method is responsible for UNcancelling of Appraisal Assignment. This
   * method does following:
   * </p>
   * <ul>
   * <li>Retrieves work assignment using taskID from Work Assignment Service.</li>
   * <li>Updates work assignment with NOT READY/UNCANCELLED request.</li>
   * <li>Saves updated work assignment to Work Assignment Service.</li>
   * <li>Retrieves MitchellEnvelope from EstimatePackage Service and removes
   * Estimator information and saves MitchellEnvelope to EstimatePackage Service
   * </li>
   * <li>Creates Claim-Suffix Activity Log.</li>
   * </ul>
   * 
   * @param workAssignmentTaskId
   *          WorkAssignmentTaskID of Work Assignment Service.
   * @param loggedInUserInfoDocument
   *          UserInfo Document of Logged In user.
   * @return <code>true</code> if successfully processed the request and
   *         <code>false</code> if unsuccessfully processed.
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   * 
   * @deprecated This method has been replaced by {@link use this method
   *             #HashMap unCancelMultipleAppraisalAssignments(HashMap
   *             workAssignmentTaskID_TCN, UserInfoDocument
   *             assignorUserInfoDocument))}
   */
  @Deprecated
  public boolean uncancelAppraisalAssignment(final long workAssignmentTaskID,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {

    final String METHOD_NAME = "uncancelAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();
    final StringBuffer msgDetail = new StringBuffer();
    String groupCode = null;
    final StringBuffer logSuffix = new StringBuffer();
    /*
     * Set errorLog Details and initialize ErrorLog, AppLog
     */
    StringBuffer localMethodParams = null;
    /*
     * Set errorLog Details and initialize ErrorLog, AppLog
     */

    localMethodParams = new StringBuffer("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\tLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    WorkAssignment workAssignment = null;
    WorkAssignment updatedWorkAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignmentDocument updatedWorkAssignmentDocument = null;
    Long claimExposureId = null;
    Long referenceId = null;
    String disposition = null;
    boolean isAsmtUncancelledSuccessfully = false;
    boolean isAllMandatoryFieldExist = false;
    String workItemID = null;
    String eventName = null;
    String assigneeId = null;
    String eventDesc = null;
    final java.util.Calendar scheduleDateTime = null;
    msgDetail.append("Method Parameters ::").append(localMethodParams);
    logSuffix.append("\t[WorkAssignment task ID:").append(workAssignmentTaskID)
        .append("]\t");
    // Setting work assignment task ID in StringBuffer for Log Prefix
    /*
     * Retrieve work assignment using taskID from Work Assignment Service.
     */
    try {
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);
      claimExposureId = workAssignment.getClaimExposureID();
      workAssignment.getClaimID().longValue();
      referenceId = workAssignment.getReferenceId();
      msgDetail.append("\tDocumentID/ReferenceID : ").append(referenceId);

      /*
       * Parse Work Assignemnt XML to Document.
       */

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      if (logger.isLoggable(Level.FINE)) {
        logger.fine("WorkAssignemntDocument CLOB fetched from DB is: "
            + workAssignmentDocument + logSuffix);
      }
      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();
      msgDetail.append("\tClaimNumber : ").append(claimNumber);

      // Setting Claim Number in StringBuffer for Log Prefix
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();
      // Fetching assigneeID,groupCode and scheduleDateTime from
      // workAssignmentDocument .
      assigneeId = getAssigneeIdFromWorkAssignmentDocument(workAssignmentDocument);
      groupCode = getGroupCodeFromWorkAssignmentDocument(workAssignmentDocument);
      // scheduleDateTime = getScheduleDateTime(workAssignmentDocument);

      if (workAssignment.getReqAssocTaskDataCompleted() != null
          && "Y"
              .equalsIgnoreCase(workAssignment.getReqAssocTaskDataCompleted())) {
        isAllMandatoryFieldExist = true;
      }
      if (logger.isLoggable(Level.INFO)) {
        final StringBuffer logMessage = new StringBuffer();
        logMessage.append("isAllMandatoryFieldExist in ");
        logMessage.append(METHOD_NAME);
        logMessage.append(": ");
        logMessage.append(isAllMandatoryFieldExist);
        logMessage.append(logSuffix);
        logger.info(logMessage.toString());
      }

      disposition = this.isAssignmentReadyForDispatch(isAllMandatoryFieldExist,
          assigneeId, groupCode, scheduleDateTime, loggedInUserInfoDocument);

      /*
       * update Work Assignment XML to NOT READY and uncancel state.
       */
      updatedWorkAssignmentDocument = workAssignmentHandler
          .setupWorkAssignmentRequest(workAssignmentDocument, disposition,
              "uncancel", loggedInUserInfoDocument);

      /*
       * Save update work assignment xml to Work Assignment Service.
       */
      updatedWorkAssignment = workAssignmentHandler.uncancelWorkAssignment(
          updatedWorkAssignmentDocument, loggedInUserInfoDocument);

      // Update AssignmentHasBeenUpdateFlag to 'N'.

      workAssignmentHandler.saveAssignmentBeenUpdatedFlag(workAssignmentTaskID,
          "N", loggedInUserInfoDocument);

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Updated AssignmentHasBeenUpdateFlag through saveAssignmentBeenUpdatedFlag method of workAssignment Service"
                + logSuffix);
      }

      // eventName will come from CONSTANT File and eventDesc will be
      // fetched from SET file
      eventName = AppraisalAssignmentConstants.UNCANCEL_AA_ASSIGNMENT_ACTIVITYLOG;
      eventDesc = appraisalAssignmentConfig
          .getUnCancelAAAssignmentActivityLog();

      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

      /*
       * Creating claim suffix activity log
       */

      if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
          .equalsIgnoreCase(workAssignmentDocument.getWorkAssignment()
              .getType())) {
        commonUtils.saveExposureActivityLog(
            appraisalAssignmentConfig.getUnCancelAAActivityLog()
                + " - SUPPLEMENT", claimExposureId, referenceId, workItemID,
            loggedInUserInfoDocument);
      } else {
        commonUtils.saveExposureActivityLog(
            appraisalAssignmentConfig.getUnCancelAAActivityLog()
                + " - ORIGINAL", claimExposureId, referenceId, workItemID,
            loggedInUserInfoDocument);
      }
      isAsmtUncancelledSuccessfully = true;

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_UNCANCEL_ASSIGNMENT_SUCCESS,
          updatedWorkAssignment, loggedInUserInfoDocument, null, startTime);

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_UNCANCELAPPRAISALASSIGNMENT;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_ERROR_UNCANCELAPPRAISALASSIGNMENT_MSG;
      logAndThrowError(CLASS_NAME, METHOD_NAME, errorCode, errorDescription,
          workItemID, claimNumber, msgDetail.toString(), exception,
          loggedInUserInfoDocument);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAsmtUncancelledSuccessfully;
  }

  /**
   * Method return AssigneeId from WorkAssignmentDocument.
   * 
   * @param workAssignmentDocument
   *          WorkAssignmentDocument.
   * @return String AssigneeId.
   */
  private String getAssigneeIdFromWorkAssignmentDocument(
      final WorkAssignmentDocument workAssignmentDocument)
  {
    String assigneeId = null;
    if (workAssignmentDocument.getWorkAssignment().getCurrentSchedule()
        .isSetAssigneeID()) {
      assigneeId = workAssignmentDocument.getWorkAssignment()
          .getCurrentSchedule().getAssigneeID();
    }
    return assigneeId;
  }

  /**
   * Method return GroupCode from CIECADocument.
   * 
   * @param workAssignmentDocument
   *          WorkAssignmentDocument.
   * @return String GroupCode.
   */
  private String getGroupCodeFromWorkAssignmentDocument(
      final WorkAssignmentDocument workAssignmentDocument)
  {
    String groupCode = null;
    if (workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
        .isSetGroupID()) {
      groupCode = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getGroupID();
    }
    return groupCode;
  }

  /**
   * 
   * <p>
   * 
   * This method is responsible for dispatching of Appraisal Assignment. This
   * 
   * method does following:
   * 
   * </p>
   * 
   * <ul>
   * 
   * <li>Retrieves and updates Work Assginment to set disposition to
   * 
   * "Dispatched".</li>
   * 
   * <li>Creates Claim Activity Log.</li>
   * 
   * </ul>
   * 
   * 
   * 
   * @param workAssignmentTaskId
   * 
   *          WorkAssignmentTaskID of Work Assignment Service.
   * 
   * @param loggedInUserInfoDocument
   * 
   *          UserInfo Document of Logged In user.
   * 
   * @return <code>true</code> if successfully processed the request and
   * 
   *         <code>false</code> if unsuccessfully processed.
   * 
   * @throws MitchellException
   * 
   *           Throws MitchellException to the caller in case of any
   * 
   *           exception arise.
   * 
   * 
   * 
   * 
   * 
   * 
   * 
   * @deprecated This method has been replaced by {@link use this method #int
   *             dispatchAppraisalAssignment()}
   */

  @Deprecated
  public boolean dispatchAppraisalAssignment(final long workAssignmentTaskID,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {

    final String METHOD_NAME = "dispatchAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();
    final StringBuffer msgDetail = new StringBuffer();
    StringBuffer localMethodParams = new StringBuffer();
    /*
     * Set errorLog Details and initialize ErrorLog, AppLog
     */
    localMethodParams = new StringBuffer("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\tLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    Long documentId = null;
    long relatedEstimateDocumentId;
    String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    WorkAssignment workAssignment = null;
    String workAsmtType = null;
    Long claimExposureId = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignmentDocument updatedWorkAssignmentDocument = null;
    String eventName = null;
    String eventDesc = null;
    String workItemID = null;
    MitchellEnvelopeHelper mitchellEnvelopeHelper = null;

    MitchellEnvelopeDocument mitchellEnvelopeDoc = null;

    msgDetail.append("Method Parameters ::").append(localMethodParams);

    boolean isAsmtDispatchedSuccessfully = false;

    /*
     * Retrieving Work Assignment by Task ID. Updating disposition to
     * "DISPATCHED" Saving updated Work Assignment to Work Assignment
     * Service.
     */
    try {
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      claimExposureId = workAssignment.getClaimExposureID();

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();

      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);

      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();

      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();

      msgDetail.append("\tClaimNumber : ").append(claimNumber);
      updatedWorkAssignmentDocument = workAssignmentHandler
          .setupWorkAssignmentRequest(workAssignmentDocument,
              AppraisalAssignmentConstants.DISPOSITION_DISPATCHED, "dispatch",
              loggedInUserInfoDocument);

      workAssignmentHandler.saveWorkAssignment(updatedWorkAssignmentDocument,
          loggedInUserInfoDocument);

      documentId = workAssignment.getReferenceId();

      msgDetail.append("\tDocumentID/ReferenceID : ").append(documentId);

      /*
       * Retrieve latest MitchellEnvelope Document using EstimatePackage
       * Service.
       */

      try {

        final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO =

        estimatePackageProxy.getAppraisalAssignmentDocument(documentId
            .longValue());

        logger
            .info("Fetched AppraisalAssignmentDTO from getAppraisalAssignmentDocument method of EstimatePackage Service");

        if (appraisalAssignmentDTO == null) {
          throw new MitchellException(
              CLASS_NAME,
              METHOD_NAME,
              "Received NULL AppraisalAssignmentDTO object From EstimatePackage Service. Document ID : "
                  + documentId);
        }

        final String mitchellEnvelopeStr = appraisalAssignmentDTO
            .getAppraisalAssignmentMEStr();

        if (mitchellEnvelopeStr != null && !mitchellEnvelopeStr.equals("")) {
          mitchellEnvelopeDoc = MitchellEnvelopeDocument.Factory
              .parse(mitchellEnvelopeStr);
          mitchellEnvelopeHelper = new MitchellEnvelopeHelper(
              mitchellEnvelopeDoc);
          logger.info("meHelper created");
        }
      } catch (final Exception exception) {

        final int errorCode = AppraisalAssignmentConstants.ERROR_DISPATCH_AA_RETRIEVE_ME;

        final String errorDescription = AppraisalAssignmentConstants.ERROR_DISPATCH_AA_RETRIEVE_ME_MSG;

        logAndThrowError(CLASS_NAME, METHOD_NAME, errorCode, errorDescription,
            workItemID, claimNumber, null, exception, loggedInUserInfoDocument);

      }

      /*
       * Call CARRHelper Service to update review assignment for
       * supplement assignment & dispatch request.
       */

      workAsmtType = workAssignmentDocument.getWorkAssignment().getType();
      if (logger.isLoggable(Level.INFO)) {
        logger.info("workAsmtType for ASSIGNMENT TYPE ====>" + workAsmtType);
      }

      if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
          .equalsIgnoreCase(workAsmtType)) {

        relatedEstimateDocumentId = mitchellEnvelopeHandler
            .getEstimateDocId(mitchellEnvelopeHelper);

        final UserInfoDocument estimatorUserInfo = userInfoUtils
            .getEstimatorInfo(relatedEstimateDocumentId);

        updateReviewAssignment(relatedEstimateDocumentId, estimatorUserInfo,
            loggedInUserInfoDocument);
        logger
            .info("Supplement Assignment has been successfully updated for review by CARR Service.Method Name is :: dispatchAppraisalAssignment");
      }
      /*
       * Create Claim Activity Log
       */
      if (logger.isLoggable(Level.INFO)) {
        final StringBuffer logMessage = new StringBuffer();
        logMessage.append(claimExposureId);
        logMessage.append(" : ");
        logMessage.append(workAssignment.getReferenceId());
        logger.info(logMessage.toString());
      }
      final PersonInfoType personInfo = updatedWorkAssignmentDocument
          .getWorkAssignment().getCurrentSchedule().getAssignee();

      final String assigneeFirstName = personInfo.getPersonName()
          .getFirstName();

      final String assigneeLastName = personInfo.getPersonName().getLastName();

      if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
          .equalsIgnoreCase(workAsmtType)) {

        eventName = AppraisalAssignmentConstants.DISPATCHSUPPLEMENT_AA_ASSIGNMENT_ACTIVITYLOG;

        eventDesc = appraisalAssignmentConfig
            .getDispatchSupplementAAAssignmentActivityLog();

        commonUtils.saveExposureActivityLog(

        appraisalAssignmentConfig.getDispatchAAActivityLog() + " "
            + ((assigneeFirstName != null) ? assigneeFirstName : "") + " "
            + ((assigneeLastName != null) ? assigneeLastName : "")
            + " - SUPPLEMENT",

        claimExposureId, workAssignment.getReferenceId(), workItemID,
            loggedInUserInfoDocument);

      } else {

        eventName = AppraisalAssignmentConstants.DISPATCH_AA_ASSIGNMENT_ACTIVITYLOG;

        eventDesc = appraisalAssignmentConfig
            .getDispatchAAAssignmentActivityLog();

        commonUtils.saveExposureActivityLog(
            appraisalAssignmentConfig.getDispatchAAActivityLog() + " "
                + ((assigneeFirstName != null) ? assigneeFirstName : "") + " "
                + ((assigneeLastName != null) ? assigneeLastName : "")
                + " - ORIGINAL", claimExposureId,
            workAssignment.getReferenceId(), workItemID,
            loggedInUserInfoDocument);

      }

      isAsmtDispatchedSuccessfully = true;

      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_SAVE_AND_SEND_AA_SUCCESS,
          workAssignment, loggedInUserInfoDocument, null, startTime);

    } catch (final Exception exception) {

      final int errorCode = AppraisalAssignmentConstants.ERROR_DISPATCH_AA;

      final String errorDescription = AppraisalAssignmentConstants.ERROR_DISPATCH_AA_MSG;

      // context.setRollbackOnly();

      logAndThrowError(CLASS_NAME, METHOD_NAME, errorCode, errorDescription,
          workItemID, claimNumber, null, exception, loggedInUserInfoDocument);

    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAsmtDispatchedSuccessfully;

  }

  /**
   * This method accepts EstimateDocumentID and calls CARRHelper to Update
   * Review Assignment for Supplement case.
   * 
   * @param relatedEstimateDocumentId
   * @throws MitchellException
   * 
   */
  public void updateReviewAssignment(final long relatedEstimateDocumentId,
      final UserInfoDocument estimatorUserInfo,
      final UserInfoDocument logdInUsrInfo)
      throws MitchellException
  {
    final String METHOD_NAME = "updateReviewAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    // UserInfoDocument estimatorUserInfo = null;
    try {

      if (logger.isLoggable(Level.FINE)) {
        logger.fine("updateReviewAssignment Input Received: "
            + relatedEstimateDocumentId);
      }

      carrHelperProxy.updateReviewAssignmentForSupplement(
          relatedEstimateDocumentId, estimatorUserInfo, logdInUsrInfo);

    } catch (final Exception exception) {
      final String workItemID = "UNKNOWN";
      final StringBuffer errorMessage = new StringBuffer(
          "Exception while calling updateReviewAssignmentForSupplement with estimator ID : ");
      errorMessage.append(relatedEstimateDocumentId);
      errorMessage.append("\nEstimatorUserInfo : ");
      errorMessage.append(estimatorUserInfo.toString());
      logger.severe(errorMessage.toString());
      final int errorCode = AppraisalAssignmentConstants.ERROR_UPDATE_AA_UPDATEREVIEWASSIGNMENT;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_UPDATE_AA_UPDATEREVIEWASSIGNMENT_MSG;

      final StringBuffer msgDetail = new StringBuffer();
      final StringBuffer localMethodParams = new StringBuffer();

      localMethodParams.append("relatedEstimateDocumentId : ")
          .append(relatedEstimateDocumentId).append("\t estimatorUserInfo: ")
          .append(estimatorUserInfo).append("\t loggedUserInfo: ")
          .append(logdInUsrInfo);
      msgDetail.append("Method Parameters ::").append(
          localMethodParams.toString());

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID,
          AppraisalAssignmentConstants.CLAIM_NUMBER, msgDetail.toString(),
          exception, logdInUsrInfo);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
  }

  /**
   * <p>
   * This method is responsible for dispatching supplement Appraisal Assignment.
   * This method internally calls dispatchAppraisalAssignment() method.
   * </p>
   * 
   * @param workAssignmentTaskId
   *          WorkAssignmentTaskID of Work Assignment Service.
   * @param loggedInUserInfoDocument
   *          UserInfo Document of Logged In user.
   * @return <code>true</code> if successfully processed the request and
   *         <code>false</code> if unsuccessfully processed.
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public boolean dispatchSupplementAppraisalAssignment(
      final long workAssignmentTaskID, final long estimateDocId,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "dispatchSupplementAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    StringBuffer localMethodParams = null;
    StringBuffer msgDetail = null;
    /*
     * Set errorLog Details and initialize ErrorLog, AppLog
     */
    /*
     * Set errorLog Details and initialize ErrorLog, AppLog
     */
    localMethodParams = new StringBuffer("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\tDocument ID : ")
        .append(estimateDocId).append("\tLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    Long documentId = null;
    String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    WorkAssignment workAssignment = null;
    Long claimExposureId = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignmentDocument updatedWorkAssignmentDocument = null;
    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);

    boolean isAsmtDispatchedSuccessfully = false;
    String eventName = null;
    String eventDesc = null;
    String workItemID = null;
    final StringBuffer logSuffix = new StringBuffer();
    logSuffix.append("\t[WorkAssignment task ID:").append(workAssignmentTaskID)
        .append("]\t");
    /*
     * Retrieving Work Assignment by Task ID. Updating disposition to
     * "DISPATCHED" Saving updated Work Assignment to Work Assignment
     * Service.
     */
    try {
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);
      claimExposureId = workAssignment.getClaimExposureID();
      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();

      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();

      msgDetail.append("\tClaimNumber : ").append(claimNumber);
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");
      updatedWorkAssignmentDocument = workAssignmentHandler
          .setupWorkAssignmentRequest(workAssignmentDocument,
              AppraisalAssignmentConstants.DISPOSITION_DISPATCHED, "dispatch",
              loggedInUserInfoDocument);
      workAssignmentHandler.saveWorkAssignment(updatedWorkAssignmentDocument,
          loggedInUserInfoDocument);

      documentId = workAssignment.getReferenceId();

      msgDetail.append("\tDocumentID : ").append(documentId);

      /*
       * Create Claim Activity Log
       */
      final PersonInfoType pT = updatedWorkAssignmentDocument
          .getWorkAssignment().getCurrentSchedule().getAssignee();
      String assigneeFirstName = "";
      String assigneeLastName = "";

      if (pT != null && pT.getPersonName() != null
          && pT.getPersonName().getFirstName() != null) {
        assigneeFirstName = pT.getPersonName().getFirstName();
      }
      if (pT != null && pT.getPersonName() != null
          && pT.getPersonName().getLastName() != null) {
        assigneeLastName = pT.getPersonName().getLastName();
      }
      if (logger.isLoggable(Level.INFO)) {
        final StringBuffer logMessage = new StringBuffer();
        logMessage
            .append("Calling saveExposureActivityLog method claimExposureID : ");
        logMessage.append(claimExposureId);
        logMessage.append("WorkAssignment Reference ID : ");
        logMessage.append(workAssignment.getReferenceId());
        logMessage.append(logSuffix);
        logger.info(logMessage.toString());
      }
      
      commonUtils.saveExposureActivityLog(
          appraisalAssignmentConfig.getDispatchAAActivityLog() + " "
              + assigneeFirstName + " " + assigneeLastName + " - SUPPLEMENT",
          claimExposureId, workAssignment.getReferenceId(), workItemID,
          loggedInUserInfoDocument);      
      isAsmtDispatchedSuccessfully = true;

      // eventName will come from CONSTANT File and eventDesc will be
      // fetched from SET file
      eventName = AppraisalAssignmentConstants.DISPATCHSUPPLEMENT_AA_ASSIGNMENT_ACTIVITYLOG;
      eventDesc = appraisalAssignmentConfig
          .getDispatchSupplementAAAssignmentActivityLog();

      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_DISPATCH__SUPPLEMENT_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_DISPATCH__SUPPLEMENT_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAsmtDispatchedSuccessfully;
  }

  /**
   * <p>
   * This method is responsible for updating Appraisal Assignment. This method
   * does following major tasks:
   * </p>
   * <ul>
   * <li>Retrieves Claim Mask for carrier.</li>
   * <li>Retrieves work assignment from WorkAssignment Service.</li>
   * <li>Updates MitchellEnvelope using EstimatePackage Service</li>
   * <li>Saves updated Work Assignment in WorkAssignment Service.</li>
   * <li>Creates Claim-Suffix Activity Log.</li>
   * </ul>
   * 
   * @param inputAppraisalAssignmentDTO
   *          Object of AppraisalAssignmentDTO.
   * @param logedUserInfoDocument
   *          UserInfo Document of Logged in User.
   * @return Updated AppraisalAssignmentDTO which has latest MitchellEnvelope
   *         Document along with other data like TCN, WorkAssignmentTaskID,
   *         DocumentID etc.
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public AppraisalAssignmentDTO updateAppraisalAssignment(
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
      final UserInfoDocument logedUserInfoDocument)
      throws MitchellException
  {
    long startTime = System.currentTimeMillis();
    long subStartTime = 0;
    final String METHOD_NAME = "updateAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);

    final boolean saveAndSendFlag = inputAppraisalAssignmentDTO
        .getSaveAndSendFlag();

    // Set errorLog Details and initialize ErrorLog, AppLogl

    String workItemID = validateMethodInputs(inputAppraisalAssignmentDTO,
        logedUserInfoDocument);

    if (logger.isLoggable(Level.FINE)) {
      StringBuffer localMethodParams = new StringBuffer();
      localMethodParams
          .append("InputAppraisalAssignmentDTO : MitchellEnvelopeDocument : ")
          .append(
              inputAppraisalAssignmentDTO.getMitchellEnvelopDoc().toString())
          .append("\nWorkItemID : ")
          .append(inputAppraisalAssignmentDTO.getWorkItemID())
          .append("\nClaimID : ")
          .append(inputAppraisalAssignmentDTO.getClaimId())
          .append("\nClaimExposureID : ")
          .append(inputAppraisalAssignmentDTO.getClaimExposureId())
          .append("\nLogged In UserInfoDocument : ")
          .append(logedUserInfoDocument.toString());
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    Long documentId = null;
    long workAssignmentTaskId = 0;
    long tcn;
    String event = null;

    String claimMask = null;
    String workAssignmentType;
    String dbAssigneeId = null;
    String reqAssigneeId = null;
    long relatedEstimateDocumentId;
    WorkAssignment workAssignment = null;
    AppraisalAssignmentDTO outputAppraisalAssignmentDTO = null;
    long claimId = 0;
    long claimExposureId = 0;
    boolean isOriginal = false;
    boolean isAllMandatoryFieldExist = false;
    CIECADocument ciecaDocument = null;
    String assigneeId = null;
    String groupCode = null;
    String eventName = "";
    String eventDesc = "";
    MitchellEnvelopeDocument mitchellEnvDoc = null;
    MitchellEnvelopeHelper meHelper = null;
    AdditionalTaskConstraintsDocument additionalTaskConstraintsDocument = null;
    java.util.Calendar scheduleDateTime = null;
    final StringBuffer logSuffix = new StringBuffer();

    try {
      meHelper = new MitchellEnvelopeHelper(
          inputAppraisalAssignmentDTO.getMitchellEnvelopDoc());
      logSuffix.append("\t[Claim Number:")
          .append(inputAppraisalAssignmentDTO.getClientClaimNumber())
          .append("]\t");

      subStartTime = System.currentTimeMillis();
      AddressValidStatus.Enum addressValidStsEnum = mitchellEnvelopeHandler
          .validateInspectionSiteAddress(meHelper, logedUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "mitchellEnvelopeHandler.validateInspectionSiteAddress:"
                + claimNumber, System.currentTimeMillis() - subStartTime);
      }

      // fetching ClaimMask from Custom Settings
      subStartTime = System.currentTimeMillis();
      claimMask = fetchClaimMask(workItemID, logedUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "fetchClaimMask:"
            + claimNumber, System.currentTimeMillis() - subStartTime);
      }

      subStartTime = System.currentTimeMillis();
      claimId = inputAppraisalAssignmentDTO.getClaimId();
      claimExposureId = inputAppraisalAssignmentDTO.getClaimExposureId();
      if (claimId == 0 || claimExposureId == 0) {
        throw new MitchellException(CLASS_NAME, METHOD_NAME,
            "ClaimID and Claim ExposureID can NOT be null. Received ClaimID : "
                + claimId + "\tClaimExposureID : " + claimExposureId);
      }
      workAssignmentTaskId = inputAppraisalAssignmentDTO.getWaTaskId();
      if (workAssignmentTaskId == 0) {
        throw new MitchellException(CLASS_NAME, METHOD_NAME,
            "Received NULL/0 work assignment task Id from received AppraisalAssignmentDTO.");
      }
      tcn = inputAppraisalAssignmentDTO.getTcn();
      if (tcn < 0) {
        throw new MitchellException(CLASS_NAME, METHOD_NAME,
            "Received NULL/LESS THAN '0' TCN from received AppraisalAssignmentDTO.");
      }

      // Retrieving data from input DTO

      String disposition = null;
      try {

        // Fetching assigneeID from Mitchellenvelope inside AADTO
        ciecaDocument = mitchellEnvelopeHandler.getCiecaFromME(meHelper);
        assigneeId = getAssigneeIdFromCiecaDocument(ciecaDocument);
        groupCode = getGroupCodeFromCiecaDocument(ciecaDocument);

        // Fetching scheduleDateTime from Mitchellenvelope
        additionalTaskConstraintsDocument = mitchellEnvelopeHandler
            .getAdditionalTaskConstraintsFromME(meHelper);
        scheduleDateTime = getScheduleDateTime(additionalTaskConstraintsDocument);

      } catch (final Exception exception) {
        throw new MitchellException(
            AppraisalAssignmentConstants.ERROR_UPDATE_AA_GETCONTAINERME,
            CLASS_NAME, METHOD_NAME,
            AppraisalAssignmentConstants.ERROR_UPDATE_AA_GETCONTAINERME_MSG,
            exception);
      }

      // The first parameter i.e. isAllMandatoryfieldExist will be coming
      // from isAssignmentReadyForDispatch()
      // and will be set in workAssignment---- should be invoked before
      // this condition
      mitchellEnvDoc = meHelper.getDoc();

      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "GettingStuffFromME:"
            + claimNumber, System.currentTimeMillis() - subStartTime);
      }

      subStartTime = System.currentTimeMillis();
      isAllMandatoryFieldExist = this.isAssignmentDataReady(mitchellEnvDoc);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "isAssignmentDataReady:" + claimNumber, System.currentTimeMillis()
                - subStartTime);
      }

      if (logger.isLoggable(Level.INFO)) {
        StringBuffer logMessage = new StringBuffer();
        logMessage.append("isAllMandatoryFieldExist in ");
        logMessage.append(METHOD_NAME);
        logMessage.append(": ");
        logMessage.append(isAllMandatoryFieldExist);
        logMessage.append(logSuffix);
        logger.info(logMessage.toString());
      }

      // is Assignment Ready For Dispatch
      subStartTime = System.currentTimeMillis();
      disposition = this.isAssignmentReadyForDispatch(isAllMandatoryFieldExist,
          assigneeId, groupCode, scheduleDateTime, logedUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "isAssignmentReadyForDispatch:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      if (AppraisalAssignmentConstants.DISPOSITION_DISPATCHED
          .equalsIgnoreCase(inputAppraisalAssignmentDTO.getDisposition())
          && AppraisalAssignmentConstants.DISPOSITION_NOT_READY
              .equalsIgnoreCase(disposition)) {
        logger
            .severe("Received 'NOT READY' Assignment with disposition as DISPATCHED. \nClaimId : "
                + claimId);
        throw new MitchellException(
            AppraisalAssignmentConstants.ERROR_FAILED_NOTREADY, CLASS_NAME,
            METHOD_NAME,
            "Received 'NOT READY' Assignment with disposition as DISPATCHED. \nClaimId: "
                + claimId);
      } else if (AppraisalAssignmentConstants.DISPOSITION_DISPATCHED
          .equalsIgnoreCase(inputAppraisalAssignmentDTO.getDisposition())
          && AppraisalAssignmentConstants.DISPOSITION_READY
              .equalsIgnoreCase(disposition)) {
        disposition = AppraisalAssignmentConstants.DISPOSITION_DISPATCHED;
      }

      // Setting the updated disposition in DTO
      inputAppraisalAssignmentDTO.setDisposition(disposition);

      if (isAllMandatoryFieldExist) {
        inputAppraisalAssignmentDTO.setReqAssociateDataCompletedInd("Y");
      } else {
        inputAppraisalAssignmentDTO.setReqAssociateDataCompletedInd("N");
      }

      // getting documentId from WorkAssignment Service
      subStartTime = System.currentTimeMillis();
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskId, logedUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "workAssignmentHandler.getWorkAssignmentByTaskId:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      subStartTime = System.currentTimeMillis();
      documentId = workAssignment.getReferenceId();
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Fetched documentId from WorkAssignment Service Document ID : "
                + documentId + logSuffix);
      }

      final WorkAssignmentDocument workAsmtCLOB = WorkAssignmentDocument.Factory
          .parse(workAssignment.getWorkAssignmentCLOBB());
      claimNumber = workAsmtCLOB.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();

      workAssignmentType = workAsmtCLOB.getWorkAssignment().getType();
      if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
          .equalsIgnoreCase(workAssignmentType)) {
        isOriginal = false;
      } else {
        isOriginal = true;
      }
      if (logger.isLoggable(Level.INFO)) {
        logger.info("isOriginal is : " + isOriginal + logSuffix);
      }
      if (workAsmtCLOB.getWorkAssignment().getCurrentSchedule()
          .isSetAssigneeID()) {
        dbAssigneeId = workAsmtCLOB.getWorkAssignment().getCurrentSchedule()
            .getAssigneeID();
      }
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "ParsingInfoFromWorkAssignment:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // Create Claim-Suffix Activity Log for Address Validation
      if (addressValidStsEnum != null
          && !addressValidStsEnum.equals(AddressValidStatus.UNKNOWN)) {
        subStartTime = System.currentTimeMillis();
        String logMsg = "";
        if (addressValidStsEnum.equals(AddressValidStatus.VALID)) {
          logMsg = appraisalAssignmentConfig
              .getAddressValidateAAActivityLogPassed();
        } else {
          logMsg = appraisalAssignmentConfig
              .getAddressValidateAAActivityLogFailed();
        }

        commonUtils.saveExposureActivityLog(logMsg,
            Long.valueOf(claimExposureId), documentId, workItemID,
            logedUserInfoDocument);
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "commonUtils.saveExposureActivityLog:" + claimNumber,
              System.currentTimeMillis() - subStartTime);
        }

      }

      // Update AssignmentHasBeen update flag
      subStartTime = System.currentTimeMillis();
      workAssignmentHandler.saveAssignmentBeenUpdatedFlag(workAssignmentTaskId,
          "Y", logedUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "workAssignmentHandler.saveAssignmentBeenUpdatedFlag:"
                + claimNumber, System.currentTimeMillis() - subStartTime);
      }

      // Call CARRHelper Service to update review assignment for
      // supplement assignment & dispatch request.

      if (inputAppraisalAssignmentDTO.getDisposition().equalsIgnoreCase(
          AppraisalAssignmentConstants.DISPOSITION_DISPATCHED)
          && AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
              .equalsIgnoreCase(workAssignmentType)) {
        subStartTime = System.currentTimeMillis();
        relatedEstimateDocumentId = mitchellEnvelopeHandler
            .getEstimateDocId((new MitchellEnvelopeHelper(
                inputAppraisalAssignmentDTO.getMitchellEnvelopDoc())));

        final UserInfoDocument estimatorUserInfo = userInfoUtils
            .getEstimatorInfo(relatedEstimateDocumentId);

        updateReviewAssignment(relatedEstimateDocumentId, estimatorUserInfo,
            logedUserInfoDocument);
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "updateReviewAssignment:" + claimNumber,
              System.currentTimeMillis() - subStartTime);
        }

      }

      // updating BMS in EstimatePackage Service

      subStartTime = System.currentTimeMillis();
      if (tcn > 0) {
        mitchellEnvelopeHandler.updateBMS(documentId, Long.valueOf(tcn),
            mitchellEnvDoc, logedUserInfoDocument);
      } else {
        mitchellEnvelopeHandler.updateBMS(documentId, (Long) null,
            mitchellEnvDoc, logedUserInfoDocument);
      }
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "mitchellEnvelopeHandler.updateBMS:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Updated BMS through updateBMS method for documentID :"
            + documentId.longValue() + logSuffix);
      }

      // updating work assignment in WorkAssignment Service
      disposition = inputAppraisalAssignmentDTO.getDisposition();

      final AssignmentAddRqDocument assignAddRqDoc = mitchellEnvelopeHandler
          .fetchAssignmentAddRqDocument(meHelper);
      if (assignAddRqDoc.getAssignmentAddRq().getVehicleDamageAssignment()
          .isSetEstimatorIDs()) {
        reqAssigneeId = assignAddRqDoc.getAssignmentAddRq()
            .getVehicleDamageAssignment().getEstimatorIDs()
            .getCurrentEstimatorID();
      }
      boolean reassignFlag = false;

      if (dbAssigneeId != null && !"".equals(dbAssigneeId)) {
        if (!dbAssigneeId.equals(reqAssigneeId)) {
          reassignFlag = true;
        }
      }
      if (reassignFlag) {
        event = AppraisalAssignmentConstants.WORK_ASSIGNMENT_REASSIGNED_EVENT;
      } else if (AppraisalAssignmentConstants.DISPOSITION_DISPATCHED
          .equalsIgnoreCase(disposition)) {
        event = AppraisalAssignmentConstants.WORK_ASSIGNMENT_DISPATCHED_EVENT;
      } else {
        event = AppraisalAssignmentConstants.WORK_ASSIGNMENT_UPDATE_EVENT;
      }

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Event to be published is : " + event + logSuffix);
      }

      long moiOrgID = AppraisalAssignmentConstants.MOI_ORG_ID_BLANK;
      if (isOriginal) {

        subStartTime = System.currentTimeMillis();
        if (mitchellEnvelopeHandler
            .checkServiceCenterFromMitchellEnvelop(meHelper)) {
          moiOrgID = mitchellEnvelopeHandler
              .getMOIOrgIdFromMitchellEnvelop(meHelper);
          if (workAssignment.getManagedByOrgID() == null
              || (moiOrgID != workAssignment.getManagedByOrgID().longValue()))
            claimProxy.setManagedById(logedUserInfoDocument,
                Long.valueOf(claimExposureId), Long.valueOf(moiOrgID));
        } else {
          claimProxy.setManagedById(logedUserInfoDocument,
              Long.valueOf(claimExposureId), null);
        }
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "claimProxy.setManagedById:" + claimNumber,
              System.currentTimeMillis() - subStartTime);
        }

      } else {
        if (workAssignment.getManagedByOrgID() != null)
          moiOrgID = workAssignment.getManagedByOrgID().longValue();
        else
          moiOrgID = AppraisalAssignmentConstants.MOI_ORG_ID_UNSET_IN_CLOB;
      }

      // save WorkAssignment     
      subStartTime = System.currentTimeMillis();
      workAssignment = workAssignmentHandler.saveWorkAssignment(documentId,
          claimId, claimExposureId, claimMask, event,
          inputAppraisalAssignmentDTO, logedUserInfoDocument, workAsmtCLOB
              .getWorkAssignment().getHoldInfo(), moiOrgID);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "workAssignmentHandler.saveWorkAssignment:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      String exposureActivityLogComment = "";
      final WorkAssignmentDocument updatedWorkAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(workAssignment.getWorkAssignmentCLOBB());
      final PersonInfoType pT = updatedWorkAssignmentDocument
          .getWorkAssignment().getCurrentSchedule().getAssignee();

      String assigneeFirstName = null;
      String assigneeLastName = null;

      if (pT != null && pT.getPersonName() != null
          && pT.getPersonName().getFirstName() != null) {
        assigneeFirstName = pT.getPersonName().getFirstName();
      }

      if (pT != null && pT.getPersonName() != null
          && pT.getPersonName().getLastName() != null) {
        assigneeLastName = pT.getPersonName().getLastName();
      }

      if (AppraisalAssignmentConstants.WORK_ASSIGNMENT_REASSIGNED_EVENT
          .equals(event)) {
        if (reqAssigneeId == null || "".equals(reqAssigneeId)) {
          exposureActivityLogComment = appraisalAssignmentConfig
              .getUnScheduleAAActivityLog();
        } else {
          if (isOriginal) {
            ScheduleInfoType oldAssigneeInfo = workAsmtCLOB.getWorkAssignment()
                .getCurrentSchedule();
            String previousAssignFirstName = getFirstName(oldAssigneeInfo);
            String previousAssignLastName = getLastName(oldAssigneeInfo);
            exposureActivityLogComment = appraisalAssignmentConfig
                .getReAssignAAActivityLog()
                + " "
                + previousAssignFirstName
                + " "
                + previousAssignLastName
                + " "
                + "to the "
                + assigneeFirstName + " " + assigneeLastName + " - ORIGINAL";
          } else {
            exposureActivityLogComment = appraisalAssignmentConfig
                .getReAssignAAActivityLog()
                + " "
                + assigneeFirstName
                + " "
                + assigneeLastName + " - SUPPLEMENT";
          }
        }
      } else if (AppraisalAssignmentConstants.WORK_ASSIGNMENT_DISPATCHED_EVENT
          .equals(event)) {

        if (isOriginal) {
          exposureActivityLogComment = appraisalAssignmentConfig
              .getDispatchAAActivityLog()
              + " "
              + assigneeFirstName
              + " "
              + assigneeLastName + " - ORIGINAL";
        } else {
          exposureActivityLogComment = appraisalAssignmentConfig
              .getDispatchAAActivityLog()
              + " "
              + assigneeFirstName
              + " "
              + assigneeLastName + " - SUPPLEMENT";
        }
      } else {

        if (isOriginal) {
          exposureActivityLogComment = appraisalAssignmentConfig
              .getUpdateAAActivityLog() + " - ORIGINAL";
        } else {
          exposureActivityLogComment = appraisalAssignmentConfig
              .getUpdateAAActivityLog() + " - SUPPLEMENT";
        }
      }

      // creating claim suffix activity log

      AdditionalAppraisalAssignmentInfoDocument addAppAsmtInfo = mitchellEnvelopeHandler
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);

      if (scheduleDateTime != null && addAppAsmtInfo != null) {
        subStartTime = System.currentTimeMillis();
        commonUtils.doDriveInAppointmentActivityLog(
            Long.valueOf(claimExposureId), scheduleDateTime,
            logedUserInfoDocument, workItemID, addAppAsmtInfo,
            inputAppraisalAssignmentDTO);
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "commonUtils.doDriveInAppointmentActivityLog:" + claimNumber,
              System.currentTimeMillis() - subStartTime);
        }

      }

      // save Exposure Activity Log
      subStartTime = System.currentTimeMillis();
      commonUtils.saveExposureActivityLog(exposureActivityLogComment,
          Long.valueOf(claimExposureId), documentId, workItemID,
          logedUserInfoDocument);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "commonUtils.saveExposureActivityLog:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // Populate DTO
      subStartTime = System.currentTimeMillis();
      outputAppraisalAssignmentDTO = populateDTO(inputAppraisalAssignmentDTO,
          null, meHelper, documentId, claimId, claimExposureId, workAssignment,
          "update", workItemID, logedUserInfoDocument);

      if (updatedWorkAssignmentDocument.getWorkAssignment().getHoldInfo() != null) {
        outputAppraisalAssignmentDTO = populateDTOWithWAHoldInfo(
            outputAppraisalAssignmentDTO, updatedWorkAssignmentDocument
                .getWorkAssignment().getHoldInfo());
      }
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "populateDTO:"
            + claimNumber, System.currentTimeMillis() - subStartTime);
      }

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Updated OuptputDTO" + logSuffix);
      }

      // eventName will come from UI will be multiple in case of UPDATE
      // and eventDesc will be fetched from SET file

      final java.util.ArrayList eventNameList = inputAppraisalAssignmentDTO
          .getEventNameList();
      if (eventNameList != null) {
        subStartTime = System.currentTimeMillis();
        for (int i = 0; i < eventNameList.size(); i++) {
          eventName = (String) eventNameList.get(i);

          if (eventName
              .equalsIgnoreCase(AppraisalAssignmentConstants.DISPATCH_AA_ASSIGNMENT_ACTIVITYLOG)) {
            eventDesc = appraisalAssignmentConfig
                .getDispatchAAAssignmentActivityLog();
          }
          if (eventName
              .equalsIgnoreCase(AppraisalAssignmentConstants.DISPATCHSUPPLEMENT_AA_ASSIGNMENT_ACTIVITYLOG)) {
            eventDesc = appraisalAssignmentConfig
                .getDispatchSupplementAAAssignmentActivityLog();
          }
          if (eventName
              .equalsIgnoreCase(AppraisalAssignmentConstants.ASSIGN_AA_ASSIGNMENT_ACTIVITYLOG)) {
            eventDesc = appraisalAssignmentConfig
                .getAssignAAAssignmentActivityLog();
          }
          if (eventName
              .equalsIgnoreCase(AppraisalAssignmentConstants.REASSIGN_AA_ASSIGNMENT_ACTIVITYLOG)) {
            eventDesc = appraisalAssignmentConfig
                .getReAssignAAAssignmentActivityLog();
          }

          this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
              eventName, eventDesc, logedUserInfoDocument.getUserInfo()
                  .getUserID());
        }
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "EventActivityLogging:" + claimNumber, System.currentTimeMillis()
                  - subStartTime);
        }
      }

      subStartTime = System.currentTimeMillis();
      publishSANSaveAndSendEvent(logedUserInfoDocument, saveAndSendFlag,
          claimNumber, event, updatedWorkAssignmentDocument, false, addAppAsmtInfo);
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "publishSANSaveAndSendEvent:" + claimNumber,
            System.currentTimeMillis() - subStartTime);
      }

      // added below if/else for defect fix (bug 106974)
      subStartTime = System.currentTimeMillis();
      if (AppraisalAssignmentConstants.DISPOSITION_DISPATCHED
          .equals(workAssignment.getAssignmentDisposition()
              .getDispositionCode())) {
        appLogUtil
            .appLog(
                AppraisalAssignmentConstants.APPLOG_SAVE_AND_SEND_AA_SUCCESS,
                workAssignment, logedUserInfoDocument, meHelper.getDoc(),
                startTime);
      } else {
        appLogUtil
            .appLog(
                AppraisalAssignmentConstants.APPLOG_UPDATE_ASSIGNMENT_SUCCESS,
                workAssignment, logedUserInfoDocument, meHelper.getDoc(),
                startTime);
      }
      if (MonitoringLogger.isLoggableTimer()) {
        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "appLogUtil:appLog"
            + claimNumber, System.currentTimeMillis() - subStartTime);
      }

    } catch (final Exception exception) {
      // Start changes: for FAILED_NOTREADY integration.
      if (exception instanceof MitchellException
          && ((MitchellException) exception).getType() == AppraisalAssignmentConstants.ERROR_FAILED_NOTREADY) {
        throw (MitchellException) exception;
      } else {
        final int errorCode = AppraisalAssignmentConstants.ERROR_UPDATE_AA_GETAA;
        final String errorDescription = AppraisalAssignmentConstants.ERROR_UPDATE_AA_GETAA_MSG;

        StringBuffer msgDetail = new StringBuffer("");
        StringBuffer localMethodParams = new StringBuffer();
        localMethodParams
            .append("InputAppraisalAssignmentDTO : MitchellEnvelopeDocument : ")
            .append(
                inputAppraisalAssignmentDTO.getMitchellEnvelopDoc().toString())
            .append("\nWorkItemID : ")
            .append(inputAppraisalAssignmentDTO.getWorkItemID())
            .append("\nClaimID : ")
            .append(inputAppraisalAssignmentDTO.getClaimId())
            .append("\nClaimExposureID : ")
            .append(inputAppraisalAssignmentDTO.getClaimExposureId())
            .append("\nLogged In UserInfoDocument : ")
            .append(logedUserInfoDocument.toString());
        msgDetail.append("Method Parameters ::").append(localMethodParams);
        msgDetail.append("WorkAssignmentTaskID : ")
            .append(workAssignmentTaskId);
        msgDetail.append("\tDocumentID : ").append(documentId);

        logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
            errorDescription, workItemID,
            AppraisalAssignmentConstants.CLAIM_NUMBER, msgDetail.toString(),
            exception, logedUserInfoDocument);
      }
    }

    if (MonitoringLogger.isLoggableTimer()) {
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME, "totalMethodTime:"
          + claimNumber, System.currentTimeMillis() - startTime);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return outputAppraisalAssignmentDTO;
  }

  private void publishSANSaveAndSendEvent(
      final UserInfoDocument logedUserInfoDocument,
      final boolean saveAndSendFlag, String claimNumber, String event,
      final WorkAssignmentDocument updatedWorkAssignmentDocument,
      final boolean skipCustomSetting, AdditionalAppraisalAssignmentInfoDocument additionalAppraisalAssignmentInfoDocument)
      throws MitchellException, XmlException, JMSException
  {
     if (saveAndSendFlag
        && (AppraisalAssignmentConstants.WORK_ASSIGNMENT_UPDATE_EVENT
            .equals(event) || AppraisalAssignmentConstants.WORK_ASSIGNMENT_CREATE_EVENT
            .equals(event))) {
      Integer customSetting = null;

      if (!skipCustomSetting) {
        final String companyCode = logedUserInfoDocument.getUserInfo()
            .getOrgCode();

        final String customSettingStringValue = appraisalAssignmentUtils
            .retrieveCustomSettings(
                companyCode,
                companyCode,
                AppraisalAssignmentConstants.CSET_GROUP_NAME,
                AppraisalAssignmentConstants.CSET_SETTING_REQUEST_CREATE_TASK_SAN_EVENT_CODE_ID);

        if (customSettingStringValue != null
            && !"".equalsIgnoreCase(customSettingStringValue)) {
          customSetting = Integer.valueOf(customSettingStringValue);
        }

        if (logger.isLoggable(Level.INFO)) {
          logger.info("Retrieved custom setting value::" + customSetting);
        }

      } else {
        if (logger.isLoggable(Level.INFO)) {
          logger.info("Skipping custom setting check!");
        }
        customSetting = Integer
            .valueOf(AppraisalAssignmentConstants.SAVE_AND_SEND_EVENT);
      }

      if (customSetting != null) {
				if (additionalAppraisalAssignmentInfoDocument == null) {
					messageBusHandler.publishEventToMessageBus(
							customSetting.intValue(),
							updatedWorkAssignmentDocument,
							logedUserInfoDocument);
				} else {
					messageBusHandler.publishEventToMessageBus(
							customSetting.intValue(),
							updatedWorkAssignmentDocument,
							logedUserInfoDocument,
							additionalAppraisalAssignmentInfoDocument);
				}

			}
    }
  }

  private com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO getEstimateAppraisalAssignmentDocument(
      final Long documentId)
      throws MitchellException
  {
    com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = null;
    final String METHOD_NAME = "getEstimateAppraisalAssignmentDocument";

    appraisalAssignmentDTO = estimatePackageProxy
        .getAppraisalAssignmentDocument(documentId.longValue());

    if (appraisalAssignmentDTO == null) {
      throw new MitchellException(CLASS_NAME, METHOD_NAME,
          "Null appraisalAssignmentDTO from EstimatePackage Service. documentId: "
              + documentId.longValue());
    }

    if (logger.isLoggable(Level.INFO)) {
      logger
          .info("Fetched appraisalAssignmentDTO from EstimatePackage Service for Document ID :"
              + documentId.longValue());
    }

    return appraisalAssignmentDTO;
  }

  /**
   * This method creates/updates the LuchType assignment.
   * 
   * @param taskDocumentXmlObject
   *          of TaskDocument type.
   * @param assignorUserInfoDocument
   * @throws MitchellException
   *           Throws Exception to the caller in case of any exception
   *           arise.
   * 
   */
  public long saveLunchAssignmentType(final XmlObject taskDocumentXmlObject,
      final UserInfoDocument assignorUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "saveLunchAssignmentType";
    logger.entering(CLASS_NAME, METHOD_NAME);
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignment retWorkAssignment = null;
    final TaskDocument taskDocument = (TaskDocument) taskDocumentXmlObject;

    if (logger.isLoggable(Level.FINE)) {
      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("taskDocument: ").append(taskDocument)
          .append("\nassignorUserInfoDocument: ")
          .append(assignorUserInfoDocument);
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    try {

      commonUtils.validateTaskDocument(taskDocument, "validateTaskDocument",
          (String) null, assignorUserInfoDocument);

      workAssignmentDocument = workAssignmentHandler.populateWorkAssignment(
          taskDocument, assignorUserInfoDocument);

      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("Populated WorkAssignmentDocument for LunchAssignment task with WorkAssignmentDocument ::"
                + workAssignmentDocument.toString());
      }

      retWorkAssignment = workAssignmentHandler.saveWorkAssignment(
          workAssignmentDocument, assignorUserInfoDocument);

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Save WorkAssignment Successfuly with taskID::"
            + retWorkAssignment.getTaskID());
      }

    } catch (final Exception exception) {

      StringBuffer msgDetail = new StringBuffer("Method Parameters::");
      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("taskDocument: ").append(taskDocument)
          .append("\nassignorUserInfoDocument: ")
          .append(assignorUserInfoDocument);
      msgDetail.append(localMethodParams.toString());

      String claimNumber = null;
      String workItemID = null;
      if (workAssignmentDocument != null) {
        claimNumber = workAssignmentDocument.getWorkAssignment()
            .getPrimaryIDs().getClaimNumber();
        msgDetail = new StringBuffer(", ClaimNumber::").append(claimNumber);
        workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
            .getWorkItemID();
      }

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME,
          AppraisalAssignmentConstants.ERROR_SAVE_LUNCH_ASSIGNMENT,
          AppraisalAssignmentConstants.ERROR_SAVE_LUNCH_ASSIGNMENT_MSG,
          workItemID, claimNumber, msgDetail.toString(), exception,
          assignorUserInfoDocument);

    }

    logger.info("saveLunchAssignmentType executed Successfully !!!");
    logger.exiting(CLASS_NAME, METHOD_NAME);

    if (retWorkAssignment == null) {
      return 0;
    } else {
      return retWorkAssignment.getTaskID().longValue();
    }

  }

  /**
   * <p>
   * This method retrieves a Supplement Request Doc (text) for use as Email
   * Notification or UI Print-Preview
   * </p>
   * 
   * @param estimateDocId
   *          Estimate DocID for original Estimate.
   * @param estimatorOrgId
   *          OrgId of the estimator
   * @param reviewerOrgId
   *          OrgId of the reviewer
   * @return Supplement Request Doc (text).
   * @throws MitchellException
   * 
   */
  public String retrieveSupplementRequestDoc(final long estimateDocId,
      final long estimatorOrgId, final long reviewerOrgId)
      throws MitchellException
  {

    final String METHOD_NAME = "retrieveSupplementRequestDoc";
    final StringBuffer logSuffix = new StringBuffer();
    logger.entering(CLASS_NAME, METHOD_NAME);

    final StringBuffer localMethodParams = new StringBuffer();
    localMethodParams.append("estimateDocId: ").append(estimateDocId)
        .append("\t estimatorOrgId: ").append(estimatorOrgId)
        .append("\t reviewerOrgId: ").append(reviewerOrgId);

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    // / Setting EstimateId in StringBuffer for Log Prefix
    logSuffix.append("\t[Estimate Doc ID:").append(estimateDocId).append("]\t");
    String supplementRequestDocText = "";

    /*
     * orgId of the estimator - Shop Name, Shop email address orgId of the
     * reviewer - Contact information of reviewer EstimateDocId for the
     * estimate, for which we would need to create the supp request
     */

    boolean isEmail = false;

    SupplementRequestDocument suppReqDocument = null;

    long t1 = 0;
    long t2 = 0;
    t1 = System.currentTimeMillis();

    try {

      // Get User Info for estimator
      final UserInfoDocument estimatorUserInfo = userInfoUtils
          .getUserInfoDoc(estimatorOrgId);

      // Get User Info for reviewer
      final UserInfoDocument reviewerUserInfo = userInfoUtils
          .getUserInfoDoc(reviewerOrgId);

      // Get the UserDetail for Estimator
      final UserDetailDocument estimatorUserDetail = userInfoUtils
          .getUserDetailDoc(estimatorUserInfo.getUserInfo().getOrgCode(),
              estimatorUserInfo.getUserInfo().getUserID());

      // Get the UserDetail for Reviewer
      final UserDetailDocument reviewerUserDetail = userInfoUtils
          .getUserDetailDoc(reviewerUserInfo.getUserInfo().getOrgCode(),
              reviewerUserInfo.getUserInfo().getUserID());

      // Currently generating Supplement Request Doc in Email format only
      isEmail = true;

      // validate the EstDocID
      if (estimateDocId <= 0) {
        throw new RuntimeException(" The EstimateDocument ID is not valid "
            + estimateDocId);
      }

      // Populate the SuppReqBO
      final SupplementReqBO suppBO = new SupplementReqBO();

      suppBO.setEmail(isEmail);
      suppBO.setEstimateDocId(estimateDocId);
      suppBO.setEstimatorOrgId(estimatorOrgId);
      suppBO.setReviewerOrgId(reviewerOrgId);
      suppBO.setEstimatorUserInfo(estimatorUserInfo);
      suppBO.setEstimatorUserDetail(estimatorUserDetail);
      suppBO.setReviewerUserDetail(reviewerUserDetail);
      suppBO.setReviewerUserInfo(reviewerUserInfo);

      // populate the estimate details to SuppReqBO
      SuppRequestUtils.populateEstimateInfo(suppBO);
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("In retrieveSupplementRequestDoc, Populated the Estimate Details "
                + logSuffix);
      }

      // Get the Supp Request Document
      final SupplementRequestDocBuildr suppBldDoc = new SupplementRequestDocBuildr();

      // Get the Supp Req. Document
      suppReqDocument = suppBldDoc.populateSupplementRequest(suppBO);

      // Use the formatter to format the email
      final SupplementNotification suppNotification = new SupplementNotification();

      // Generate/Retrieve Supplement Notification doc -
      supplementRequestDocText = suppNotification.retrieveNotificationDoc(
          suppBO, suppReqDocument);
      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("Fetched SupplementRequestDocText from retrieveNotificationDoc Method: "
                + supplementRequestDocText + logSuffix);
      }
      t2 = System.currentTimeMillis();
      if (logger.isLoggable(Level.INFO)) {
        logger.info("SuppCreate Req Took " + (t2 - t1) + ": ms" + logSuffix);
      }
      logger.exiting(CLASS_NAME, METHOD_NAME);
      return supplementRequestDocText;

    } catch (final MitchellException me) {
      throw me;
    } catch (final Throwable t) {
      throw new MitchellException(
          SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST.getCode(),
          "AppraisalAssignmentService", "retrieveSupplementRequestDoc",
          SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST
              .getDescription(), t);
    }
  }

  /**
   * <p>
   * This interface method that generates/retrieves a Supplement Request HTML
   * document from required inputs (a) an Estimate Document Id and (b) a pair of
   * UserInfo Ids provided in the input AssignmentServiceContext asgSvcCtx
   * </p>
   * 
   * @param context
   *          Assignment Delivery - AssignmentServiceContext object
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns Supplement Request HTML document
   *         (MitchellEnvelopeDocument)
   * @throws MitchellException
   * 
   */
  public MitchellEnvelopeDocument retrieveSupplementRequestXMLDocAsMEDoc(
      final AssignmentDeliveryServiceDTO asgSvcCtx, final String workItemId)
      throws MitchellException
  {

    final String methodName = "AAS: retrieveSupplementRequestXMLDocAsMEDoc";
    logger.entering(CLASS_NAME, methodName);
    UserInfoDocument senderUserInfo = null;
    MitchellEnvelopeDocument mitchellEnvDoc_ADS = null;
    MitchellEnvelopeDocument mitchellEnvDoc = null;
    String senderUserId = "";
    String systemUserId = "";
    String claimId_Str = "";
    String exposureId_Str = "";
    String estimatorCompanyCode = "";
    String srcUserInfo_dbg = "";
    Long documentId = null;
    // Debug
    if (logger.isLoggable(Level.INFO)) {
      logger
          .info("\n\n*****Debug: retrieveSupplementRequestXMLDocAsMEDoc, workItemId= "
              + workItemId);
      if (asgSvcCtx.isDrp()) {
        logger
            .info("*****AAS-Debug: retrieveSupplementRequestXMLDocAsMEDoc, TRUE, Value of asgSvcCtx.isDrp()= "
                + asgSvcCtx.isDrp());
      } else {
        logger
            .info("*****AAS-Debug: retrieveSupplementRequestXMLDocAsMEDoc, FALSE, Value of asgSvcCtx.isDrp()= "
                + asgSvcCtx.isDrp());
      }
      logger
          .info("\n\n*****AAS-Debug: retrieveSupplementRequestXMLDocAsMEDoc, asgSvcCtx.getUserInfo().getUserInfo()= \n"
              + asgSvcCtx.getUserInfo().getUserInfo().toString());
      logger
          .info("\n\n*****AAS-Debug: retrieveSupplementRequestXMLDocAsMEDoc, asgSvcCtx.getDrpUserInfo()= \n"
              + asgSvcCtx.getDrpUserInfo().getUserInfo().toString() + "\n\n");
    }
    // Debug
    // Primary steps --
    // 1. Generate a Supplement Request HTML doc
    // createSupplementRequestHTMLDoc()
    // 2. Save Supplement Request HTML doc to DocStore
    // saveSuppRequestXMLDocToDocStore()
    // 3. Create claim exposure activity log
    // saveClaimExpActivtyLogForSuppRequestDoc(
    // 4. Update Mitchell Envelope to include new EmailMessage Doc as new
    // body.
    // updateMitchellEnvelopeWithSuppRequestHTMLDocBody()

    try {

      // -----------------------------------------------------------------------
      // 1. createSupplementRequestHTMLDoc()

      logger
          .info("\n\n********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, Before createSupplementRequestHTMLDoc");

      mitchellEnvDoc = createSupplementRequestHTMLDoc(asgSvcCtx, workItemId);
      logger
          .info("\n\n********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, After createSupplementRequestHTMLDoc");
      // -----------------------------------------------------------------------
      // 2. saveSuppRequestXMLDocToDocStore()

      estimatorCompanyCode = asgSvcCtx.getUserInfo().getUserInfo().getOrgCode();
      final CIECADocument ciecaDoc = mitchellEnvelopeHandler
          .getCiecaDocFromMitchellEnv(asgSvcCtx.getMitchellEnvDoc(), workItemId);
      final String claimNumber = ciecaDoc.getCIECA().getAssignmentAddRq()
          .getClaimInfo().getClaimNum();

      final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
          asgSvcCtx.getMitchellEnvDoc());
      systemUserId = meHelper
          .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.SYSTEM_USERID);

      claimId_Str = meHelper
          .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.CLAIM_ID);
      exposureId_Str = meHelper
          .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.EXPOSURE_ID);
      final long claimID = Long.parseLong(claimId_Str);
      final long exposureID = Long.parseLong(exposureId_Str);

      // Get SenderUserInfo using SenderID or SystemUserID
      //
      senderUserId = getSenderIDFromCieca(ciecaDoc);
      if (senderUserId.length() > 0) {
        senderUserInfo = userInfoUtils.retrieveUserInfo(estimatorCompanyCode,
            senderUserId);
        srcUserInfo_dbg = "using senderUserInfo";
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, getSenderIDFromCieca, "
                  + "senderUserId=  " + senderUserId);
        }

        // Else (Default-1), use the SystemUserID from ME Doc and then
        // get the SystemUserInfo
      } else {
        senderUserInfo = userInfoUtils.retrieveUserInfo(estimatorCompanyCode,
            systemUserId);
        srcUserInfo_dbg = "using systemUserInfo";
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, Default-1 using SystemUserID to get senderUserInfo!! ");
          logger
              .info("********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, get SystemUserID from MEDOC, "
                  + "systemUserId=  " + systemUserId);
        }
      }

      // Else (Default-2), then use the DRPUserInfo provided in
      // AssignmentServiceContext
      if (senderUserInfo == null) {
        senderUserInfo = asgSvcCtx.getDrpUserInfo();
        srcUserInfo_dbg = "using DRPUserInfo";
        logger
            .info("********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, Default-2 - using DrpUserInfo as senderUserInfo!!");
      }

      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, Sender UserInfo Source: "
                + srcUserInfo_dbg + "\n\n senderUserInfo=  " + senderUserInfo);
        logger
            .fine("********AAS - Debug: Before saveSuppRequestXMLDocToDocStore!!");
      }

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("\n\n********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, Before saveSuppRequestXMLDocToDocStore, "
                + "claimId_Str=  "
                + claimId_Str
                + "\texposureId_Str= "
                + exposureId_Str + "\tsystemUserId= " + systemUserId);
      }

      documentId = saveSuppRequestXMLDocToDocStore(mitchellEnvDoc,
          senderUserInfo, estimatorCompanyCode, claimID, exposureID, workItemId);



      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("\n\n********AAS - Debug: retrieveSupplementRequestXMLDocAsMEDoc, "
                + "After saveSuppRequestXMLDocToDocStore, documentId= "
                + documentId);
      }
      // ----------------------------------------------------------------------------------------------------

      //
      // 3. saveClaimExpActivtyLogForSuppRequestDoc() - Create claim
      // exposure activity log
      // this.logdInUsrInfo = senderUserInfo;
      try {

        logger
            .info("********AAS - Debug: Before saveClaimExpActivtyLogForSuppRequestDoc!!");

        commonUtils.saveClaimExpActivtyLogForSuppRequestDoc(
            AppraisalAssignmentConstants.SUP_REQUEST_ACT_LOG_MESSAGE,
            Long.valueOf(exposureID), documentId, null);

        logger
            .info("********AAS - Debug: After saveClaimExpActivtyLogForSuppRequestDoc!!");

      } catch (final Exception e) {
        final StringBuffer errormessage = new StringBuffer(
            AppraisalAssignmentConstants.ERROR_SUPP_REQUEST_EMAIL_EXPOSUREACTIVITYLOG_MSG);
        errormessage.append(", claimNumber= [ ");
        errormessage.append(claimNumber);
        errormessage.append(" ], ");
        errormessage.append(e.getMessage());
        logger.severe(errormessage.toString());

        logError(
            AppraisalAssignmentConstants.ERROR_SUPP_REQUEST_EMAIL_EXPOSUREACTIVITYLOG,
            null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
            workItemId, errormessage.toString(), null, 0, e);
        throw new MitchellException(CLASS_NAME, methodName,
            errormessage.toString(), e);
      }

      //
      // ----------------------------------------------------------------------------------------------------
      //
      // 4. Update Mitchell Envelope to include new EmailMessage Doc as
      // new body.
      //
      // Get Original MitchellEnvelope from ADS Context
      mitchellEnvDoc_ADS = asgSvcCtx.getMitchellEnvDoc();

      // Get EmailMessageDocument content body from new MitchellEnvelope
      final MitchellEnvelopeHelper inputHelper = new MitchellEnvelopeHelper(
          mitchellEnvDoc);
      final EmailMessageDocument supplementRequestEmailDoc = getSuppRequestEmailDocFromME(inputHelper);

      if (logger.isLoggable(Level.FINE)) {
        logger.fine("\n\n****** AAS: retrieveSupplementRequestXMLDocAsMEDoc: "
            + "Input ME (to be updated with EmailMessageDoc) ********");
        logger.fine("retrieveSupplementRequestXMLDocAsMEDoc "
            + "- Input mitchellEnvDoc_ADS= " + mitchellEnvDoc_ADS.toString());

        logger.fine("****** AAS: retrieveSupplementRequestXMLDocAsMEDoc "
            + "- Input supplementRequestEmailDoc= "
            + supplementRequestEmailDoc.toString());
        logger.fine("****** AAS: retrieveSupplementRequestXMLDocAsMEDoc: "
            + "Input ME (to be updated with EmailMessageDoc) ********\n\n");
      }

      // Update Inbound mitchellEnvDoc with new EmailMessageDocument
      // content body
      mitchellEnvDoc_ADS = updateMitchellEnvelopeWithSuppRequestHTMLDocBody(
          mitchellEnvDoc_ADS, supplementRequestEmailDoc);

    } catch (final Exception ex) {
      final String message = "Exception occurred while retrieving Supplement Request Doc, "
          + ex.getMessage();

      logError(
          AppraisalAssignmentConstants.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, message, null, 0, ex);

      throw new MitchellException(CLASS_NAME, methodName, message, ex);
    }

    logger
        .info("\n\n****** Leaving AAS: retrieveSupplementRequestXMLDocAsMEDoc ********\n\n");

    logger.exiting(CLASS_NAME, methodName);

    if (documentId != null) {
        long docStoreId = estimatePackageProxy.getDocStoreIdByDocumentId(documentId, AppraisalAssignmentConstants.SUP_AA_REQ_EMAIL_XML_ATTACHMENT_TYPE);

        NameValuePairType nameValuePair = mitchellEnvDoc_ADS
                .getMitchellEnvelope()
                .getEnvelopeContext()
                .addNewNameValuePair();

        nameValuePair.setName(AppraisalAssignmentConstants.SUPPLEMENT_REQUEST_DOC_STORE_ID);
        nameValuePair.addValue(String.valueOf(docStoreId));
    }

    return mitchellEnvDoc_ADS;
  }

  /**
   * <p>
   * This method is responsible for Uncancelling the Appraisal Assignment. This
   * method does following:
   * </p>
   * <ul>
   * <li>Retrieves work assignment using taskID from Work Assignment Service.</li>
   * <li>Updates work assignment with NOT READY/UNCANCELLED request.</li>
   * <li>Saves updated work assignment to Work Assignment Service.</li>
   * <li>Retrieves MitchellEnvelope from EstimatePackage Service and removes
   * Estimator information and saves MitchellEnvelope to EstimatePackage Service
   * </li>
   * </ul>
   * 
   * @param workAssignmentTaskID
   *          - workAssignmentTaskID object
   * @param requestTCN
   *          - requestTCN object
   * @param loggedInUserInfoDocument
   *          - loggedInUserInfoDocument object
   * @return int - int value
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   */
  public int uncancelAppraisalAssignment(final long workAssignmentTaskID,
      final long requestTCN, final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    long startTime = System.currentTimeMillis();
    final String METHOD_NAME = "uncancelAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    final StringBuffer localMethodParams = new StringBuffer();

    localMethodParams.append("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\tTCN : ").append(requestTCN)
        .append("\tLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    String claimNumber = null;
    String workItemID = null;
    StringBuffer msgDetail = null;
    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignmentDocument updatedWorkAssignmentDocument = null;
    Long claimExposureId = null;
    Long referenceId = null;
    String assigneeId = null;
    String groupCode = null;
    String disposition = null;
    int isAsmtUncancelledSuccessfully = AppraisalAssignmentConstants.FAILURE;
    String eventName = null;
    String eventDesc = null;
    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    boolean isAllMandatoryFieldExist = false;
    final java.util.Calendar scheduleDateTime = null;
    WorkAssignment updatedWorkAssignment = null;
    final StringBuffer logSuffix = new StringBuffer();

    try {

      // / Setting work assignment task ID in StringBuffer for Log Prefix
      logSuffix.append("\t[WorkAssignment task ID:")
          .append(workAssignmentTaskID).append("]\t");
      // /
      /*
       * Retrieve work assignment using taskID from Work Assignment
       * Service.
       */
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      /*
       * CHECK TCN for stale data check and if assignment is stale then
       * return as stale data exists
       */
      if (checkStaleData(workAssignment.getTcn(), requestTCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }
      claimExposureId = workAssignment.getClaimExposureID();
      referenceId = workAssignment.getReferenceId();
      msgDetail.append("\tDocumentID/ReferenceID : ").append(referenceId);

      /*
       * Parse Work Assignemnt XML to Document.
       */
      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      if (logger.isLoggable(Level.FINE)) {
        logger.fine("WorkAssignemntDocument CLOB fetched from DB is: "
            + workAssignmentDocument + logSuffix);
      }
      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();
      msgDetail.append("\tClaimNumber : ").append(claimNumber);
      // Setting Claim Number in StringBuffer for Log Prefix
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");
      //
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();

      /************** SET DISPOSITION BY CHECKING THE MANDATORY FLAG && ASSIGNEEID *******/

      // Fetching assigneeID,groupCode and scheduleDateTime from
      // workAssignmentDocument .
      //assigneeId = getAssigneeIdFromWorkAssignmentDocument(workAssignmentDocument);
      groupCode = getGroupCodeFromWorkAssignmentDocument(workAssignmentDocument);
       // scheduleDateTime = getScheduleDateTime(workAssignmentDocument);

      if (workAssignment.getReqAssocTaskDataCompleted() != null
          && "Y"
              .equalsIgnoreCase(workAssignment.getReqAssocTaskDataCompleted())) {
        isAllMandatoryFieldExist = true;
      }
      if (logger.isLoggable(Level.INFO)) {
        logger.info("isAllMandatoryFieldExist in " + METHOD_NAME + ": "
            + isAllMandatoryFieldExist + logSuffix);
      }
      disposition = this.isAssignmentReadyForDispatch(isAllMandatoryFieldExist,
          assigneeId, groupCode, scheduleDateTime, loggedInUserInfoDocument);

      /*
       * update Work Assignment XML to NOT READY and uncancel state.
       */
      if (logger.isLoggable(Level.INFO)) {
        logger.info("Calling setupWorkAssignmentRequest" + logSuffix);
      }
      updatedWorkAssignmentDocument = workAssignmentHandler
          .setupWorkAssignmentRequest(workAssignmentDocument, disposition,
              "uncancel", loggedInUserInfoDocument);
      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("Called setupWorkAssignmentRequest with updatedWorkAssignmentDocument:\n"
                + updatedWorkAssignmentDocument.toString() + logSuffix);
      }
      // TODO
      /******** REMOVE CANCELLATION REASON FROM WA CLOB **************/
      /*************************************************************/

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("uncancelAppraisalAssignment:: Getting getWorkAssignment from updatedWorkAssignmentDocument"
                + logSuffix);
      }
      final WorkAssignmentType workAssignmentType = updatedWorkAssignmentDocument
          .getWorkAssignment();
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info(" uncancelAppraisalAssignment:: Got getWorkAssignment from updatedWorkAssignmentDocument as :\n"
                + workAssignmentType.toString() + logSuffix);
      }
      if (workAssignmentType.isSetEventReasonCode()) {
        // Unset EventMemo
        if (workAssignmentType.isSetEventInfo()
            && workAssignmentType.getEventInfo().isSetEventMemo()) {
          workAssignmentType.getEventInfo().unsetEventMemo();
        }
        workAssignmentType.unsetEventReasonCode();
      }

      //Remove assignee from WorkAssignment/ MitchellEnvelope
      final ScheduleInfoType scheduleInfoType = workAssignmentType
          .getCurrentSchedule();

      if (scheduleInfoType != null) {
        if (scheduleInfoType.isSetAssignee()) {
          scheduleInfoType.unsetAssignee();
        }
        if (scheduleInfoType.isSetAssigneeID()) {
          scheduleInfoType.unsetAssigneeID();
          //Remove assignee from MitchellEnvelope
          if (workAssignment.getReferenceId() != null)
            mitchellEnvelopeHandler.unScheduleMitchellEnvelope(
                workAssignment.getReferenceId(), loggedInUserInfoDocument);
        }
        if (scheduleInfoType.isSetAssigneeUserType()) {
          scheduleInfoType.unsetAssigneeUserType();
        }
      }

      // Save update work assignment xml to Work Assignment Service.

      updatedWorkAssignment = workAssignmentHandler.uncancelWorkAssignment(
          updatedWorkAssignmentDocument, loggedInUserInfoDocument);

      // Update AssignmentHasBeenUpdateFlag to 'N'.

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("UnCancelled through unCancel method of WorkAssignment Service :isUncancelled value "
                + logSuffix);
      }
      if (updatedWorkAssignment == null) {
        throw new MitchellException(
            AppraisalAssignmentConstants.ERROR_UNCANCEL_AA_WASAVE,
            CLASS_NAME,
            METHOD_NAME,
            "Received Null WorkAssignment from WorAssignment Service in uncancelAppraisalAssignment.");
      }

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info(" Calling workAssignmentFacadeEJB.saveAssignmentBeenUpdatedFlag"
                + logSuffix);
      }

      // Update AssignmentHasBeenUpdateFlag to 'N'.
      workAssignmentHandler.saveAssignmentBeenUpdatedFlag(workAssignmentTaskID,
          "N", loggedInUserInfoDocument);

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Called workAssignmentFacadeEJB.saveAssignmentBeenUpdatedFlag"
                + logSuffix);
        logger
            .info("Updated AssignmentHasBeenUpdateFlag through saveAssignmentBeenUpdatedFlag method of WorkAssignment Service"
                + logSuffix);
      }

      if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
          .equalsIgnoreCase(workAssignmentDocument.getWorkAssignment()
              .getType())) {
        commonUtils.saveExposureActivityLog(
            // "The Appraisal Assignment UnCancelled Successfully",
            appraisalAssignmentConfig.getUnCancelAAActivityLog()
                + " - SUPPLEMENT", claimExposureId, referenceId, workItemID,
            loggedInUserInfoDocument);
      } else {
        commonUtils.saveExposureActivityLog(
            // "The Appraisal Assignment UnCancelled Successfully",
            appraisalAssignmentConfig.getUnCancelAAActivityLog()
                + " - ORIGINAL", claimExposureId, referenceId, workItemID,
            loggedInUserInfoDocument);
      }

      // eventName will come from CONSTANT File and eventDesc will be
      // fetched from SET file
      eventDesc = appraisalAssignmentConfig
          .getUnCancelAAAssignmentActivityLog();
      eventName = AppraisalAssignmentConstants.DB_UNCANCEL_AA_ASSIGNMENT_ACTIVITYLOG;
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Calling writeAssignmentActivityLog method with EventName::"
                + eventName + "\tEventDesc::" + eventDesc + logSuffix);
      }

      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());
      if (logger.isLoggable(Level.INFO)) {
        logger.info("Called writeAssignmentActivityLog method with EventName::"
            + eventName + "\tEventDesc::" + eventDesc + logSuffix);
      }

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_UNCANCEL_ASSIGNMENT_SUCCESS,
          updatedWorkAssignment, loggedInUserInfoDocument, null, startTime);

      isAsmtUncancelledSuccessfully = AppraisalAssignmentConstants.SUCCESS;

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_UNCANCELLING_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_UNCANCELLING_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAsmtUncancelledSuccessfully;
  }

  /**
   * <p>
   * This private method generates/retrieves a Supplement Request HTML document
   * from required inputs (a) an Estimate Document Id and (b) a pair of UserInfo
   * Ids provided in the AssignmentServiceContext asgSvcCtx
   * </p>
   * 
   * NOTE: The Retrieval method "retrieveSupplementRequestDoc" is a WCAA
   * refactored CARRHelper method
   * 
   * @param context
   *          Assignment Delivery - AssignmentServiceContext object
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns Supplement Request HTML document (String)
   * @throws MitchellException
   * 
   */
  private MitchellEnvelopeDocument createSupplementRequestHTMLDoc(
      final AssignmentDeliveryServiceDTO asgSvcCtx, final String workItemId)
      throws MitchellException
  {

    final String methodName = "createSupplementRequestHTMLDoc";
    logger.entering(CLASS_NAME, methodName);
    String suppNotificationHTMLDoc = "";
    String estimatorCompanyCode = "";
    String mitchellCompanyCode = "";
    String senderUserId = "";
    UserInfoDocument senderUserInfo = null;
    MitchellEnvelopeDocument mitchellEnvDoc = null;

    try {

      long senderOrgID = -1;
      long estimateDocId = -1;
      long reviewerOrgId = -1;
      final long estimatorOrgId = Long.parseLong(asgSvcCtx.getUserInfo()
          .getUserInfo().getOrgID());

      // First, Get EstimateDocID from
      // AdditionalAppraisalAssignmentInfoDocument
      String sentToEmailAddresses = " ";
      AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = null;
      aaaInfoDoc = mitchellEnvelopeHandler
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(new MitchellEnvelopeHelper(
              asgSvcCtx.getMitchellEnvDoc()));
      if (aaaInfoDoc != null) {
        if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .isSetAssignmentDetails()) {
          if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getAssignmentDetails().isSetRelatedEstimateDocumentID()) {
            estimateDocId = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
                .getAssignmentDetails().getRelatedEstimateDocumentID();
            if (logger.isLoggable(Level.INFO)) {
              logger
                  .info("********AAS - Debug: createSupplementRequestHTMLDoc, have RelatedEstimateDocumentID, "
                      + "estimateDocId= [ " + estimateDocId + " ]\n");
            }
          }
        }

        // Get SentTo Email Addresses from AdditionalInfo Aggregate,
        sentToEmailAddresses = getSentToEmailAddressesFromAAAInfoDoc(aaaInfoDoc);

        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("*****AAS - Debug: createSupplementRequestHTMLDoc, sentToEmailAddresses: "
                  + sentToEmailAddresses);
        }

      }

      // If there is an estimateDocID, then Retrieve HTML version of the
      // Supplement Request Notification
      if (estimateDocId > 0) {

        // ---------------------------------------------------------------------
        // Get CIECADocument from MithellEnvelope
        //
        final CIECADocument ciecaDoc = mitchellEnvelopeHandler
            .getCiecaDocFromMitchellEnv(asgSvcCtx.getMitchellEnvDoc(),
                workItemId);

        String clientClaimNumber = " ";
        if (ciecaDoc != null) {

          if (logger.isLoggable(Level.FINE)) {
            logger
                .fine("*****AAS - Debug: createSupplementRequestHTMLDoc, inbound ciecaDoc: "
                    + ciecaDoc.toString());
          }

          clientClaimNumber = ciecaDoc.getCIECA().getAssignmentAddRq()
              .getClaimInfo().getClaimNum();
        }

        // Company Code from "Carrier" Estimator UserInfo
        estimatorCompanyCode = asgSvcCtx.getUserInfo().getUserInfo()
            .getOrgCode();

        // Mitchell Company Code for the BodyShop - should be BS
        mitchellCompanyCode = asgSvcCtx.getDrpUserInfo().getUserInfo()
            .getOrgCode();

        // Get SenderID from Cieca Doc - if available
        senderUserId = getSenderIDFromCieca(ciecaDoc);
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("*****AAS - Debug: createSupplementRequestHTMLDoc, senderUserId: "
                  + senderUserId);
          logger
              .info("*****AAS - Debug: createSupplementRequestHTMLDoc, estimatorCompanyCode: "
                  + estimatorCompanyCode);
        }

        // Get senderOrgID fom senderUserInfo
        //
        String fName = null;
        String lName = null;
        final StringBuffer sb_1 = new StringBuffer();
        String sentToName = " ";
        if (senderUserId.length() > 0) {
          //  final AppraisalAssignmentUtils appraisalAssignmentUtils = new AppraisalAssignmentUtils();
          senderUserInfo = appraisalAssignmentUtils.retrieveUserInfo(
              estimatorCompanyCode, senderUserId);
          if (senderUserInfo != null) {
            senderOrgID = Long.parseLong(senderUserInfo.getUserInfo()
                .getOrgID());
            fName = senderUserInfo.getUserInfo().getFirstName();
            lName = senderUserInfo.getUserInfo().getLastName();
            if (fName != null) {
              sb_1.append(fName);
            }
            if (lName != null) {
              sb_1.append(" ");
              sb_1.append(lName);
            }
            if (sb_1.toString().length() > 0) {
              sentToName = sb_1.toString();
            }

            if (logger.isLoggable(Level.FINE)) {
              logger
                  .fine("*****createSupplementRequestHTMLDoc, senderUserInfo: "
                      + senderUserInfo);
            }
            if (logger.isLoggable(Level.INFO)) {
              logger.info("*****createSupplementRequestHTMLDoc, senderOrgID: "
                  + senderOrgID);
            }
          }
        }

        // Get claimID and ExposureID from ME Doc
        String claimId = "";
        String exposureId = "";
        String systemUserID = "";
        final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
            asgSvcCtx.getMitchellEnvDoc());
        claimId = meHelper
            .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.CLAIM_ID);
        exposureId = meHelper
            .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.EXPOSURE_ID);
        systemUserID = meHelper
            .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.SYSTEM_USERID);

        // Set sentDateTime to current date/time
        final Calendar cal = Calendar.getInstance();
        final SimpleDateFormat dateFmtString = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss Z");
        Date dt = new Date();
        dt = cal.getTime();
        final String sentDateTime = dateFmtString.format(dt);

        // Get Estimator UserID, First and Last Name
        String estimatorUserID = " ";
        String estimatorName = " ";
        estimatorUserID = asgSvcCtx.getUserInfo().getUserInfo().getUserID();

        fName = null;
        lName = null;
        final StringBuffer sb_2 = new StringBuffer();
        fName = asgSvcCtx.getUserInfo().getUserInfo().getFirstName();
        lName = asgSvcCtx.getUserInfo().getUserInfo().getLastName();
        if (fName != null) {
          sb_2.append(fName);
        }
        if (lName != null) {
          sb_2.append(" ");
          sb_2.append(lName);
        }
        if (sb_2.toString().length() > 0) {
          estimatorName = sb_2.toString();
        }

        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("********Debug: createSupplementRequestHTMLDoc, Have a Shop assignment? = "
                  + asgSvcCtx.isDrp() + "\n\n");
        }

        // Get Reviewer UserID, First and Last Name
        //
        String shopUserName = " ";
        String reviewerUserID = " ";
        fName = null;
        lName = null;
        final StringBuffer sb_3 = new StringBuffer();
        if (asgSvcCtx.isDrp()) {
          reviewerUserID = asgSvcCtx.getDrpUserInfo().getUserInfo().getUserID();
          fName = asgSvcCtx.getDrpUserInfo().getUserInfo().getFirstName();
          lName = asgSvcCtx.getDrpUserInfo().getUserInfo().getLastName();
          if (fName != null) {
            sb_3.append(fName);
          }
          if (lName != null) {
            sb_3.append(" ");
            sb_3.append(lName);
          }
          if (sb_3.toString().length() > 0) {
            shopUserName = sb_3.toString();
          }
        }

        // If Cieca has Sender UserID, then use the Sender OrgID,

        if (senderOrgID > 0) {
          reviewerOrgId = senderOrgID;

          // Else use the estimatorOrgId or Drp OrgID
        } else {
          if (asgSvcCtx.isDrp()) {
            reviewerOrgId = Long.parseLong(asgSvcCtx.getDrpUserInfo()
                .getUserInfo().getOrgID());

          } else {
            // TODO need Company System User OrgId for Staff
            // Supplement case ...
            // As alternative is EstimatorOrgID for now...
            reviewerOrgId = estimatorOrgId;
          }
        }

        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("\n\n********Debug: createSupplementRequestHTMLDoc, before retrieveSupplementRequestDoc");
          logger
              .info("********AAS - Debug: Integ. Testing ejb.retrieveSupplementRequestDoc");
          logger
              .info("********AAS - Debug: createSupplementRequestHTMLDoc, estimateDocId= [ "
                  + estimateDocId + " ]");
          logger
              .info("********AAS - Debug: createSupplementRequestHTMLDoc, estimatorOrgId= [ "
                  + estimatorOrgId + " ]");
          logger
              .info("********AAS - Debug: createSupplementRequestHTMLDoc, reviewerOrgId= [ "
                  + reviewerOrgId + " ]\n\n");
        }

        logger
            .info("********Debug: before calling retrieveSupplementRequestXMLDoc...");

        // Retrieve HTML version of the Supplement Request Notification

        /**
         * Modify for non-network shop supplement email.
         */
        suppNotificationHTMLDoc = retrieveSupplementRequestXMLDoc(
            estimateDocId, estimatorOrgId, reviewerOrgId, asgSvcCtx);

        EmailMessageDocument supplementRequestEmailDoc = null;
        if (suppNotificationHTMLDoc.length() > 0) {

          logger
              .info("********Debug: createSupplementRequestHTMLDoc Have a suppNotificationHTMLDoc!!");
          // Parse Supplement Notification HTML into an
          // EmailMessageDocument
          String supplementRequestHTML = "";
          String supplementRequestText = "";
          supplementRequestEmailDoc = EmailMessageDocument.Factory
              .parse(suppNotificationHTMLDoc);
          supplementRequestHTML = supplementRequestEmailDoc.getEmailMessage()
              .getHTMLFormat();
          supplementRequestText = supplementRequestEmailDoc.getEmailMessage()
              .getTextFormat();

          // Get config setting to request writing HTML Request to
          // disk
          final String outputHTMLDocsFlag = getOutputHTMLDocsFlag();
          if (!outputHTMLDocsFlag.equalsIgnoreCase("PROD")) {
            final String fileExt1 = AppraisalAssignmentConstants.FILE_EXTENSION_HTML;
            final String filePrefix1 = AppraisalAssignmentConstants.SUPPLEMENT_REQUEST_PREFIX;
            writeSupplementRequestDocFile(supplementRequestHTML, filePrefix1,
                fileExt1, workItemId);
          }

          if (logger.isLoggable(Level.FINE)) {
            final boolean isValid = supplementRequestEmailDoc.validate();
            logger
                .fine("********AAS - Debug: after calling retrieveSupplementRequestXMLDoc, isValid= "
                    + isValid);
            logger.fine("\n********AAS - Debug: suppNotificationHTMLDoc= \n"
                + suppNotificationHTMLDoc);
            logger
                .fine("\n\n********AAS - Debug: supplementRequestEmailDoc= \n"
                    + supplementRequestEmailDoc.toString());
            logger
                .fine("\n\n********AAS - Debug: supplementRequestDoc(HTML only)= \n"
                    + supplementRequestHTML);
            logger
                .fine("\n\n********AAS - Debug: supplementRequestDoc(Text only)= \n"
                    + supplementRequestText + "\n\n");
          }

          // Generate a Mitchell Envelope containing the an
          // EmailMessageDocument and N/V Params

          // addSuppRequestHTMLDocToNewMitchellEnvelope
          // addSuppRequestHTMLDocToNewMitchellEnvelope
          mitchellEnvDoc = addSuppRequestHTMLDocToNewMitchellEnvelope(
              supplementRequestEmailDoc, estimatorCompanyCode,
              mitchellCompanyCode, systemUserID, workItemId, clientClaimNumber,
              claimId, exposureId, estimatorUserID, reviewerUserID,
              estimatorName, shopUserName, sentToName, senderUserId,
              sentToEmailAddresses, sentDateTime, estimateDocId);

          if (logger.isLoggable(Level.FINE)) {
            logger
                .fine("\n\n********AAS - Debug: After addSuppRequestHTMLDocToNewMitchellEnvelope,"
                    + " mitchellEnvDoc= \n" + mitchellEnvDoc.toString());
          }
          // Store ME Doc for Supp Request to disk
          if (!outputHTMLDocsFlag.equalsIgnoreCase("PROD")) {
            final String fileExt2 = AppraisalAssignmentConstants.FILE_EXTENSION_XML;
            final String filePrefix2 = AppraisalAssignmentConstants.ME_DOC_SUPPLEMENT_REQUEST_PREFIX;
            writeSupplementRequestDocFile(mitchellEnvDoc.toString(),
                filePrefix2, fileExt2, workItemId);
          }
        }

      } else {
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("********AAS - Debug: createSupplementRequestHTMLDoc, Invalid estimateDocId!! estimateDocId=  "
                  + estimateDocId + "\n\n");
        }

        final String errmsg = "AAS- createSupplementRequestHTMLDoc: Invalid or Missing RelatedEstimateDocumentID, Not found in AdditionalAppraisalAssignmentInfo XML doc.";
        logger.severe(errmsg);

        logError(
            AppraisalAssignmentConstants.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
            null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
            workItemId, errmsg, null, 0, null);

        throw new MitchellException(CLASS_NAME, methodName, errmsg, null);
      }

      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("********AAS - Debug: createSupplementRequestHTMLDoc, after retrieveSupplementRequestDoc, suppNotificationHTMLDoc= \n "
                + suppNotificationHTMLDoc + "\n\n");
      }

    } catch (final Exception ex) {
      logger.severe(ex.getMessage());
      final String message = "Exception occurred while retrieving Supplement Request Doc, "
          + ex.getMessage();

      logError(
          AppraisalAssignmentConstants.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, message, null, 0, ex);

      throw new MitchellException(CLASS_NAME, methodName, message, ex);
    }

    logger.exiting(CLASS_NAME, methodName);
    return mitchellEnvDoc;
  }

  // ------------------------------------------------------------------------------------------------------
  // New private method for Supplement Capture Project (Q1-2010)
  //
  /*
   * Commented to resolve the codepro comments. private String
   * getCompanyNameFromCieca(CIECADocument ciecaDoc){ String methodName =
   * "getCompanyNameFromCieca"; logger.entering(CLASS_NAME, methodName);
   * 
   * String companyName = null;
   * if(ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo() != null) {
   * if(ciecaDoc
   * .getCIECA().getAssignmentAddRq().getAdminInfo().getInsuranceCompany() !=
   * null) {if(ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().
   * getInsuranceCompany().getParty() != null) {
   * if(ciecaDoc.getCIECA().getAssignmentAddRq
   * ().getAdminInfo().getInsuranceCompany().getParty().getOrgInfo() != null)
   * {if(ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().
   * getInsuranceCompany().getParty().getOrgInfo().getCompanyName() != null) {
   * companyName =
   * ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().getInsuranceCompany
   * ().getParty().getOrgInfo().getCompanyName(); } } } } }
   * 
   * logger.exiting(CLASS_NAME, methodName); return companyName; }
   */
  // ------------------------------------------------------------------------------------------------------
  // New private method for Supplement Capture Project (Q1-2010)
  //
  private String getSenderIDFromCieca(final CIECADocument ciecaDoc)
  {
    final String methodName = "getSenderFromCieca";
    logger.entering(CLASS_NAME, methodName);

    String senderID = "";
    if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo() != null) {
      if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().getSender() != null) {
        if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo().getSender()
            .getParty() != null) {
          if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
              .getSender().getParty().getPersonInfo() != null) {
            if (ciecaDoc.getCIECA().getAssignmentAddRq().getAdminInfo()
                .getSender().getParty().getPersonInfo().getIDInfoArray(0) != null) {
              senderID = ciecaDoc.getCIECA().getAssignmentAddRq()
                  .getAdminInfo().getSender().getParty().getPersonInfo()
                  .getIDInfoArray(0).getIDNum();

            }
          }
        }
      }
    }

    logger.exiting(CLASS_NAME, methodName);
    return senderID;
  }

  /**
   * <p>
   * Stores a MitchellEnvelopeDocument containing a EmailMessageDocument into
   * DocStore
   * <p>
   * 
   * @param mEnvDoc
   *          MitchellEnvelopeDocument containing a EmailMessageDocument to
   *          be saved
   * @param userInfoDoc
   *          UserInfoDocument for user requesting the save
   * @param companyCode
   *          Insurance Carrier Company code(String) related to Document
   * @param claimId
   *          Claim Id (long) related to the document
   * @param claimExposureId
   *          Claim Exposure Id (long) related to the document
   * @param workItemId
   *          workItemID (String) for this workflow instance
   * 
   * @return documentId Returns the stored DocumentId
   * @throws MitchellException
   * 
   */
  private java.lang.Long saveSuppRequestXMLDocToDocStore(
      final MitchellEnvelopeDocument mEnvDoc,
      final UserInfoDocument userInfoDoc, final String companyCode,
      final long claimId, final long claimExposureId, final String workItemId)
      throws MitchellException
  {
    final String methodName = "saveSuppRequestXMLDocToDocStore";
    logger.entering(CLASS_NAME, methodName);

    java.lang.Long documentId = null;

    if (logger.isLoggable(Level.FINE)) {
      logger
          .fine("\n\n********AAS-Debug: saveSuppRequestXMLDocToDocStore - Input mEnvDoc= "
              + mEnvDoc.toString());
      logger
          .fine("\n\n********AAS-Debug: saveSuppRequestXMLDocToDocStore - Input userInfoDoc= "
              + userInfoDoc.toString());
    }

    try {
      documentId = estimatePackageProxy.saveSuppAppraisalAssignmentEmailDoc(
          mEnvDoc, userInfoDoc, companyCode, claimId, claimExposureId);
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("********AAS - Debug: After EPS:saveSuppAppraisalAssignmentEmailDoc, documentId= "
                + documentId);
      }

    } catch (final Exception e) {

      final String message = AppraisalAssignmentConstants.ERROR_SAVING_AA_SUPP_EMAIL_DOC_MSG
          + " " + e.getMessage();
      logger.severe(message);
      logError(AppraisalAssignmentConstants.ERROR_SAVING_AA_SUPP_EMAIL_DOC,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, message, null, 0, e);
      throw new MitchellException(CLASS_NAME, methodName, message, e);
    }

    logger.exiting(CLASS_NAME, methodName);
    return documentId;
  }

  /**
   * Update an existing MitchellEnvelopeDocument adding a new
   * EmailMessageDocument as new content body
   * 
   * @param inputMEDoc
   *          Input MitchellEnvelopeDocument to be updated
   * @param supplementRequestEmailDoc
   *          EmailMessageDocument
   * 
   * @return mitchellEnvelopeDocument an updated MitchellEnvelopeDocument
   *         containing an EmailMessageDocument as a new Envelope Body
   * 
   */
  private MitchellEnvelopeDocument updateMitchellEnvelopeWithSuppRequestHTMLDocBody(
      final MitchellEnvelopeDocument inputMEDoc,
      final EmailMessageDocument supplementRequestEmailDoc)
  {

    final String methodName = "updateMitchellEnvelopeWithSuppRequestHTMLDocBody";
    logger.entering(CLASS_NAME, methodName);

    if (logger.isLoggable(Level.FINE)) {
      logger
          .fine("\n\n********AAS - Debug: Input ME (to be updated with EmailMessageDoc) ********");
      logger.fine("updateMitchellEnvelopeWithSuppRequestHTMLDocBody "
          + "- Input inputMEDoc= " + inputMEDoc.toString());

      logger
          .fine("********AAS - Debug: updateMitchellEnvelopeWithSuppRequestHTMLDocBody "
              + "- Input supplementRequestEmailDoc= "
              + supplementRequestEmailDoc.toString());
      logger
          .fine("******Input ME (to be updated with EmailMessageDoc) ********\n\n");
    }

    final MitchellEnvelopeHelper inputHelper = new MitchellEnvelopeHelper(
        inputMEDoc);
    inputHelper.addNewEnvelopeBody("supplementrequestemail",
        supplementRequestEmailDoc, "supplementrequestemail");

    final MitchellEnvelopeDocument newMeDoc = inputHelper.getDoc();

    if (logger.isLoggable(Level.FINE)) {
      logger
          .fine("\n\n********AAS - Debug: Output ME (updated with EmailMessageDoc) ********");
      logger.fine("New envelope document is "
          + (commonUtils.validate(newMeDoc) ? "valid" : "invalid"));
      logger.fine("Updated MitchellEnvelope XmlBean\n" + newMeDoc.toString());
      logger
          .fine("******Output ME (updated with EmailMessageDoc) ********\n\n");
    }

    logger.exiting(CLASS_NAME, methodName);

    return inputHelper.getDoc();
  }

  /**
   * This method is a helper method which extracts EmailMessageDocument from
   * MitchellEnvelope Document.
   * 
   * @param meHelper
   *          MitchellEnvelopeHelper, input
   * 
   * @return EmailMessageDocument EmailMessageDocument
   * 
   * @throws Exception
   *           in case MitchellEnvelope Document doesn't contains
   *           EmailMessageDocument
   */
  private EmailMessageDocument getSuppRequestEmailDocFromME(
      final MitchellEnvelopeHelper meHelper)
      throws Exception
  {

    final String methodName = "getSuppRequestEmailDocFromME";
    logger.entering(CLASS_NAME, methodName);

    EmailMessageDocument supplementRequestDoc = null;
    String contentString = null;

    if (logger.isLoggable(Level.FINE)) {
      logger
          .fine("getSuppRequestEmailDocFromME - \nInput Received: MitchellEnvelopeDocument: "
              + meHelper.getDoc());
    }

    final EnvelopeBodyType envelopeBody = meHelper
        .getEnvelopeBody("supplementrequestemail");
    final EnvelopeBodyMetadataType metadata = envelopeBody.getMetadata();
    final String xmlBeanClassname = metadata.getXmlBeanClassname();

    contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);

    if (logger.isLoggable(Level.FINE)) {
      logger
          .fine("getSuppRequestEmailDocFromME: Retrieved EmailMessageDocument"
              + " from meHelper as String is:" + contentString);
    }

    if (xmlBeanClassname == null
        || xmlBeanClassname.equals(EmailMessageDocument.class.getName())) {

      supplementRequestDoc = EmailMessageDocument.Factory.parse(contentString);

      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("getSuppRequestEmailDocFromME: Retrieved EmailMessageDocument"
                + " by parsing ContentString:" + supplementRequestDoc);
      }

    } else {
      final String errMsg = "MitchellEnvelope does not contains EmailMessageDocument";
      throw new MitchellException(CLASS_NAME, "getSuppRequestEmailDocFromME",
          errMsg);
    }

    logger.exiting(CLASS_NAME, methodName);

    return supplementRequestDoc;
  }

  /**
   * This method checks for the stale data
   * 
   * @param workAssignmentTCN
   * @param requestTCN
   * @return
   */
  protected boolean checkStaleData(final Long workAssignmentTCN,
      final long requestTCN)
  {
    boolean isAssignmentStaleData = false;

    // requestTCN if supplied will always be > 0
    if (requestTCN > 0 && workAssignmentTCN != null
        && workAssignmentTCN.longValue() != requestTCN) {
      isAssignmentStaleData = true;
    }
    if (logger.isLoggable(Level.INFO)) {
      logger.info("checkStaleData:" + isAssignmentStaleData);
    }
    return isAssignmentStaleData;
  }

  /**
   * This method is a helper method which extracts EmailTo Address list (if
   * exists) from the AdditionalAppraisalAssignmentInfo Document.
   * 
   * @param aaaInfoDoc
   *          AdditionalAppraisalAssignmentInfoDocument
   * 
   * @return sendToEmaiAddresses EmailTo Addresses found in
   *         AdditionalAppraisalAssignmentInfo Document else returns blank
   *         string.
   */
  private String getSentToEmailAddressesFromAAAInfoDoc(
      final AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc)
  {

    final String methodName = "getSentToEmailAddressesFromAAAInfoDoc";
    logger.entering(CLASS_NAME, methodName);

    String sendToEmaiAddresses = " ";
    String toAddresses = " ";
    String emailRecipients[] = null;
    boolean toEmailNotificationFlag = false;

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("*****getSentToEmailAddressesFromAAAInfoDoc, aaaInfoDoc:\n "
          + aaaInfoDoc.toString());
    }

    if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo() != null
        && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .isSetNotificationDetails()
        && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .getNotificationDetails().isSetNotificationEmailTo()) {
      toEmailNotificationFlag = aaaInfoDoc
          .getAdditionalAppraisalAssignmentInfo().getNotificationDetails()
          .getNotificationEmailTo().getNotifyRecipients();
      if (logger.isLoggable(Level.INFO)) {
        logger.info("\n\n*****Additional Info Doc toEmailNotificationFlag = "
            + toEmailNotificationFlag);
      }
    }

    // If alternate ToEmail Addresses found, append to list
    if (toEmailNotificationFlag) {
      if (aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
          .isSetNotificationDetails()
          && aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
              .getNotificationDetails().isSetNotificationEmailTo()) {
        emailRecipients = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .getNotificationDetails().getNotificationEmailTo()
            .getEmailAddressArray();

        final int len_toEmailRcpList = emailRecipients.length;
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("*****Additional Info Doc contains toAddresses, emailRecipients.length= "
                  + len_toEmailRcpList);
        }

        if (len_toEmailRcpList > 0) {
          for (int i = 0; i < len_toEmailRcpList; i++) {
            if (logger.isLoggable(Level.INFO)) {
              logger.info("*****Currrent i= " + i);
            }
            toAddresses += emailRecipients[i];
            if (logger.isLoggable(Level.INFO)) {
              logger.info("*****Adding another emailRecipients[i]= "
                  + emailRecipients[i] + " and i=" + i);
            }
            if (i < (len_toEmailRcpList - 1)) {
              toAddresses += ",";
              if (logger.isLoggable(Level.INFO)) {
                logger
                    .info("***** Append comma, Updated list for toAddresses= "
                        + toAddresses + " and i=" + i);
              }
            }
            if (logger.isLoggable(Level.INFO)) {
              logger.info("***** Updated list for toAddresses= " + toAddresses
                  + " and i=" + i);
            }
          }
        } else {
          logger.info("*****Additional Info Doc does not contain toAddresses");
          toAddresses = " ";
          toEmailNotificationFlag = false;
        }
      }
    }

    if (toEmailNotificationFlag) {
      sendToEmaiAddresses = toAddresses;
    }

    logger.exiting(CLASS_NAME, methodName);
    return sendToEmaiAddresses;
  }

  // ------------------------------------------------------------------------------------------------------
  // New private method for Supplement Capture Project (Q1-2010)
  //
  private String getOutputHTMLDocsFlag()
  {
    final String outputHTMLDocsFlag = SuppRequestSystemConfig
        .getOutputHTMLDocsFlag().trim();
    if (outputHTMLDocsFlag == null || outputHTMLDocsFlag.length() == 0) {
      logger
          .warning("outputHTMLDocsFlag is not defined in System Configuration!!");
    }
    if (logger.isLoggable(Level.INFO)) {
      logger.info("outputHTMLDocsFlag: " + outputHTMLDocsFlag);
    }

    return outputHTMLDocsFlag;
  }

  /**
   * This method writes the Supplement Request Text buffer
   * (suppNotificationDoc) to a uniquely named Disk File for use as an
   * attachement.
   * 
   * @param suppNotificationDoc
   *          Supplement Request Notification String buffer
   * @param filePrefix
   *          Prefix for Filename for the DiskFile
   * @param fileExtension
   *          Extension for Filename for the DiskFile
   * @param workItemId
   *          workItemID for this workflow instance
   * 
   * @return Returns suppNotificationDocFile - a uniquely named Supplement
   *         Request doc (disk file)
   * @throws Exception
   *           , MitchellException
   * 
   */
  private File writeSupplementRequestDocFile(final String suppNotificationDoc,
      String filePrefix, String fileExtension, final String workItemId)
      throws MitchellException
  {
    final String methodName = "writeSupplementRequestDocFile";
    logger.entering(CLASS_NAME, methodName);

    Writer writer = null;
    File suppNotificationDocFile = null;

    if (filePrefix == null) {
      filePrefix = AppraisalAssignmentConstants.SUPPLEMENT_REQUEST_PREFIX;
    }
    if (fileExtension == null) {
      fileExtension = AppraisalAssignmentConstants.FILE_EXTENSION_TXT;
    }

    try {

      // Get unique filename
      suppNotificationDocFile = FileUtils.createUniqueFile(
          getSuppRequestTempDir(), filePrefix + System.currentTimeMillis(),
          fileExtension, false);

      final String suppAbsFilePath = suppNotificationDocFile.getAbsolutePath();

      // Write out the Supplement Request Text buffer
      // (suppNotificationDoc) to a Disk File
      writer = new BufferedWriter(new FileWriter(suppNotificationDocFile));
      writer.write(suppNotificationDoc);
      if (suppNotificationDocFile.exists()) {
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("********Debug: writeSupplementRequestDocFile, suppNotificationDocFile exists!! \n\nsuppNotificationDoc FilePath= "
                  + suppAbsFilePath + "\n\n");
        }
      }

    } catch (final FileNotFoundException ex) {

      logger.severe("writeSupplementRequestDocFile: File not found error.");
      logger.severe(ex.getMessage());

      final String message = "Exception while writing SupplementRequestDocFile: File not found error. "
          + ex.getMessage();
      logError(
          AppraisalAssignmentConstants.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, "writeSupplementRequestDocFile: File not found error.",
          null, 0, null);

      throw new MitchellException(CLASS_NAME, methodName, message, ex);

    } catch (final IOException ex) {

      logger.severe("writeSupplementRequestDocFile: File I/O exception.");
      logger.severe(ex.getMessage());
      final String message = "Exception while writing SupplementRequestDocFile: File I/O exception. "
          + ex.getMessage();

      logError(
          AppraisalAssignmentConstants.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
          null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
          workItemId, "writeSupplementRequestDocFile: File I/O exception.",
          null, 0, null);

      throw new MitchellException(CLASS_NAME, methodName, message, ex);

    } finally {
      try {
        if (writer != null) {
          writer.close();
        }
      } catch (final IOException ex) {

        logger.severe("writeSupplementRequestDocFile: File I/O exception.");
        logger.severe(ex.getMessage());
        final String message = "Exception while writing SupplementRequestDocFile: File I/O exception. "
            + ex.getMessage();

        logError(
            AppraisalAssignmentConstants.ERROR_RETRIEVING_SUPPLEMENT_REQUEST,
            null, CLASS_NAME, methodName, ErrorLoggingService.SEVERITY.FATAL,
            workItemId, "writeSupplementRequestDocFile: File I/O exception.",
            null, 0, null);

        throw new MitchellException(CLASS_NAME, methodName, message, ex);

      }
    }

    logger.exiting(CLASS_NAME, methodName);
    return suppNotificationDocFile;
  }

  /**
   * <p>
   * This method retrieves a Supplement Request Doc in XML format for use as
   * Email Notification or UI Print-Preview
   * </p>
   * 
   * @param estimateDocId
   *          Estimate DocID for original Estimate.
   * @param estimatorOrgId
   *          OrgId of the estimator
   * @param reviewerOrgId
   *          OrgId of the reviewer
   * @return Supplement Request Doc in XML format.
   * @throws MitchellException
   * 
   */
  public String retrieveSupplementRequestXMLDoc(final long estimateDocId,
      final long estimatorOrgId, final long reviewerOrgId)
      throws MitchellException
  {
    EmailMessageDocument supplementRequestDoc = null;
    final RetriveSupReqDelegator supDel = new RetriveSupReqDelegator(
        estimateDocId, estimatorOrgId, reviewerOrgId);
    supplementRequestDoc = supDel.retriveSupplementRequest();
    return supplementRequestDoc.toString();
  }

  /**
   * Added for non-network shop supplement email.
   * 
   * @param estimateDocId
   * @param estimatorOrgId
   * @param reviewerOrgId
   * @param svcContext
   * @return
   * @throws MitchellException
   */
  public String retrieveSupplementRequestXMLDoc(final long estimateDocId,
      final long estimatorOrgId, final long reviewerOrgId,
      final AssignmentDeliveryServiceDTO svcContext)
      throws MitchellException
  {
    EmailMessageDocument supplementRequestDoc = null;
    final RetriveSupReqDelegator supDel = new RetriveSupReqDelegator(
        estimateDocId, estimatorOrgId, reviewerOrgId);
    supDel.setTaskId(svcContext.getWorkAssignmentId());
    supDel.setNonNetworkShop(svcContext.isNonNetWorkShop());
    supDel.setShopPremium(svcContext.isShopPremium());
    supDel.setNetWorkShop(svcContext.isNetWorkShop());

    supplementRequestDoc = supDel.retriveSupplementRequest();
    return supplementRequestDoc.toString();
  }

  // ------------------------------------------------------------------------------------------------------
  // New private method for Supplement Capture Project (Q1-2010)
  //
  /**
   * Create a new MitchellEnvelopeDocument containing a EmailMessageDocument
   * as its content body Also, adds Assignment & Email "reference" Name/Value
   * pairs in the Envelope Context
   * 
   * @param supplementRequestEmailDoc
   *          EmailMessageDocument
   * @param several
   *          N/V Pairs params Various input params for creation of
   *          Name/Value Pairs
   * 
   * @return mitchellEnvelopeDocument a MitchellEnvelopeDocument containing
   *         EmailMessageDoc in the Envelope Body and N/V Pairs in Envelope
   *         Contex
   * 
   */
  private MitchellEnvelopeDocument addSuppRequestHTMLDocToNewMitchellEnvelope(
      final EmailMessageDocument supplementRequestEmailDoc,
      final String estimatorCompanyCode, final String mitchellCompanyCode,
      final String systemUserID, final String workItemId,
      final String clientClaimNumber, final String claimId,
      final String exposureId, final String estimatorUserID,
      final String reviewerUserID, final String estimatorName,
      final String shopUserName, final String sentToName,
      final String senderUserId, final String sentToEmailAddresses,
      final String sentDateTime, final long estimateDocId)
  {

    final String methodName = "addSuppRequestHTMLDocToNewMitchellEnvelope";
    logger.entering(CLASS_NAME, methodName);

    MitchellEnvelopeHelper meHelper = null;
    MitchellEnvelopeDocument meDoc = null;

    meHelper = MitchellEnvelopeHelper.newInstance();

    logger
        .info("******************** Add various NVPairs to Context ********************");

    meHelper.addEnvelopeContextNVPair("MitchellCompanyCode",
        mitchellCompanyCode);
    meHelper.addEnvelopeContextNVPair("ReviewerCompanyCode",
        estimatorCompanyCode);
    meHelper.addEnvelopeContextNVPair("MitchellUserID", reviewerUserID);
    meHelper.addEnvelopeContextNVPair("ReviewerUserID", estimatorUserID);

    meHelper.addEnvelopeContextNVPair("SystemUserId", systemUserID);
    meHelper.addEnvelopeContextNVPair("SenderUserId", senderUserId);
    meHelper.addEnvelopeContextNVPair("MitchellWorkItemId", workItemId);
    meHelper.addEnvelopeContextNVPair("ClientClaimNumber", clientClaimNumber);
    meHelper.addEnvelopeContextNVPair("ClaimId", claimId);
    meHelper.addEnvelopeContextNVPair("ExposureId", exposureId);
    meHelper.addEnvelopeContextNVPair("EstimateDocId",
        String.valueOf(estimateDocId));

    meHelper.addEnvelopeContextNVPair("MitchellUserName", estimatorName);
    meHelper.addEnvelopeContextNVPair("ShopUserName", shopUserName);
    meHelper.addEnvelopeContextNVPair("SentByName", sentToName); // Correct
    // SentTo/SentBy
    // NV pairs
    meHelper.addEnvelopeContextNVPair("SentToName", estimatorName); // Correct
    // SentTo/SentBy
    // NV
    // pairs
    meHelper.addEnvelopeContextNVPair("SentToEmail", sentToEmailAddresses);
    meHelper.addEnvelopeContextNVPair("SentDateTime", sentDateTime);

    // -- DEBUG ---
    if (logger.isLoggable(Level.FINE)) {
      logger.fine("addSuppRequestHTMLDocToNewMitchellEnvelope "
          + "- Input supplementRequestEmailDoc= "
          + supplementRequestEmailDoc.toString());

      logger
          .fine("****************** Retrieve NVPair value from Context *******************");
      String value = meHelper.getEnvelopeContextNVPairValue("SentByName");
      logger.fine("Value for Context NVPair [SentByName] is:\n" + value);

      value = meHelper.getEnvelopeContextNVPairValue("SentToName");
      logger.fine("Value for Context NVPair [SentToName] is:\n" + value);

      value = meHelper.getEnvelopeContextNVPairValue("SentDateTime");
      logger.fine("Value for Context NVPair [SentDateTime] is:\n" + value);

      value = meHelper.getEnvelopeContextNVPairValue("SentToEmail");
      logger.fine("Value for Context NVPair [SentToEmail] is:\n" + value);
    }
    // -- DEBUG ---

    meHelper.addNewEnvelopeBody("supplementrequestemail",
        supplementRequestEmailDoc, "supplementrequestemail");
    meDoc = meHelper.getDoc();

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("New envelope document is "
          + (commonUtils.validate(meDoc) ? "valid" : "invalid"));
      logger.fine("Created MitchellEnvelope XmlBean\n" + meDoc.toString());
    }

    logger.exiting(CLASS_NAME, methodName);
    return meDoc;
  }

  // ------------------------------------------------------------------------------------------------------
  // New private method for Supplement Capture Project (Q1-2010)
  //
  private String getSuppRequestTempDir()
  {

    String suppRequestTempDir = SuppRequestSystemConfig.getTempDir().trim();
    if (suppRequestTempDir == null || suppRequestTempDir.length() == 0) {
      logger
          .warning("SuppRequestTempDir is not defined in System Configuration, so using default one = "
              + SuppRequestSystemConfig.CONFIG_TEMP_DIR);

      suppRequestTempDir = SuppRequestSystemConfig.CONFIG_TEMP_DIR;

    }

    if (logger.isLoggable(Level.INFO)) {
      logger.info("suppRequestTempDir: " + suppRequestTempDir);
    }

    return suppRequestTempDir;
  }

  /**
   * <p>
   * This method is responsible for determining if the assignment needs to be
   * dispatched or not. if dispatch is needed calls
   * internalAssignScheduleAppraisalAssignment
   * </p>
   * <ul>
   * <li>Retrieves work assignment from WorkAssignment Service.</li>
   * </ul>
   * 
   * @param assignTaskXmlObject
   *          - XmlObject object of AssignTaskType containing schedule info
   * @param loggedInUserInfoDocument
   *          - UserInfoDocument for logged in user
   * @return <code>0</code> if successfully processed the request and
   *         <code>1</code> if unsuccessfully processed. <code>2</code> if
   *         stale data.
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   */
  public int assignScheduleAppraisalAssignment(
      final XmlObject assignTaskXmlObject,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    String METHOD_NAME = "assignScheduleAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();
    int isAsmtAssignedSuccessfully = AppraisalAssignmentConstants.FAILURE;

    String claimNumber = null;
    String workItemID = null;

    AssignTaskType assignTaskType = (AssignTaskType) assignTaskXmlObject;
    long workAssignmentTaskID = assignTaskType.getTaskId();

    if (logger.isLoggable(Level.FINE)) {
      StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("AssignTaskType : ").append(assignTaskType)
          .append("\nLogged In UserInfoDocument : ")
          .append(loggedInUserInfoDocument.toString());

      logger.fine("Input Received: " + localMethodParams);
    }

    try {

      //
      String newResourceUserID = null;
      if (assignTaskType.isSetAssigneeId()) {
        newResourceUserID = assignTaskType.getAssigneeId();
      }

      Calendar newScheduleDateTime = null;
      if (assignTaskType.isSetScheduleDateTime()) {
        newScheduleDateTime = assignTaskType.getScheduleDateTime();
      }

      long requestTCN = 0;
      if (assignTaskType.isSetAppraisalAssignmentTCN()) {
        requestTCN = assignTaskType.getAppraisalAssignmentTCN();
      }

      WorkAssignment workAssignment = workAssignmentHandler
          .getWorkAssignmentByTaskId(workAssignmentTaskID,
              loggedInUserInfoDocument);
      String XmlW = workAssignment.getWorkAssignmentCLOBB();
      WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(XmlW);

      WorkAssignmentType workAssignmentType = workAssignmentDocument
          .getWorkAssignment();
      PrimaryIDsType pidsType = workAssignmentType.getPrimaryIDs();
      ScheduleInfoType currentSchedType = workAssignmentType
          .getCurrentSchedule();

      workItemID = pidsType.getWorkItemID();
      claimNumber = pidsType.getClaimNumber();
      String dbDisposition = workAssignmentType.getDisposition();
      String dbAssigneeID = currentSchedType.getAssigneeID();

      Calendar calendar = null;
      if (newScheduleDateTime != null) {
        calendar = newScheduleDateTime;
        if (logger.isLoggable(Level.INFO)) {
          logger.info("newScheduleDateTime: " + newScheduleDateTime);
        }
      } else {
        calendar = Calendar.getInstance();
        logger.info("Created schedule calendar.");
      }

      // Main Logic Condition for nudge workflow. If stmt for pre nudge workflow. else stmt for nudge workflow
      //
      if (newScheduleDateTime == null
          || dbAssigneeID == null
          || !(newScheduleDateTime.get(newScheduleDateTime.DAY_OF_MONTH) == calendar
              .get(calendar.DAY_OF_MONTH)
              && dbDisposition
                  .equalsIgnoreCase(AppraisalAssignmentConstants.DISPOSITION_DISPATCHED) && dbAssigneeID
              .equalsIgnoreCase(newResourceUserID))) {

        // For Dispatch or ReDispatch cases where Schedule Date NOT TODAY
        //    Call internalAssignScheduleAppraisalAssignment for current workflow
        // 
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info(METHOD_NAME
                  + ":: calling internalAssignScheduleAppraisalAssignment for current workflow");
        }
        isAsmtAssignedSuccessfully = internalAssignScheduleAppraisalAssignment(
            assignTaskXmlObject, loggedInUserInfoDocument);
      } else {

        // NEW PBI 380201 for CULDESAC RE-DISPATCH
        // Retrieve CustomSetting for Carrier Specific workflow feature
        // - CustomSetting ENABLE_WC_DISPATCH_CULDESAC can be Y or N
        final String companyCode = loggedInUserInfoDocument.getUserInfo()
            .getOrgCode();
        final String culdasacYN = appraisalAssignmentUtils
            .retrieveCustomSettings(
                companyCode,
                companyCode,
                AppraisalAssignmentConstants.CSET_GROUP_NAME,
                AppraisalAssignmentConstants.CSET_SETTING_ENABLE_WC_DISPATCH_CULDESAC);

        if ("Y".equals(culdasacYN)) {
          if (logger.isLoggable(Level.INFO)) {
            logger.info(METHOD_NAME
                + ":: Before internalAssignScheduleAppraisalAssignment "
                + " for CULDESAC workflow");
          }

          // Execute RE-DISPATCH / NUDGE CASE, Send Dispatch Event Back to Carrier
          isAsmtAssignedSuccessfully = internalAssignScheduleAppraisalAssignment(
              assignTaskXmlObject, loggedInUserInfoDocument);

          if (logger.isLoggable(Level.INFO)) {
            logger.info(METHOD_NAME
                + ":: After internalAssignScheduleAppraisalAssignment "
                + " for CULDESAC workflow");
          }

        } else { // BEGIN else case for NEW PBI 380201 for CULDESAC RE-DISPATCH

          //need to update and save workAssignment
          if (logger.isLoggable(Level.INFO)) {
            logger
                .info(METHOD_NAME
                    + ":: no dispatch needed, need to update and save workAssignment");
          }

          // CHECK TCN for stale data check and if assignment is stale then
          // return as stale data exists.

          if (checkStaleData(workAssignment.getTcn(), requestTCN)) {
            return AppraisalAssignmentConstants.STALE_DATA;
          }

          //update scheduled date/time in the workassignment
          currentSchedType.setScheduleStartDateTime(newScheduleDateTime);

          //set eventInfo for an update event
          EventInfoType eventInfo = EventInfoType.Factory.newInstance();
          Calendar eventCalendar = Calendar.getInstance();
          eventInfo.setEventDateTime(eventCalendar);
          eventInfo.setUpdatedByID(loggedInUserInfoDocument.getUserInfo()
              .getUserID());
          eventInfo.setEvent(EventDefinitionType.UPDATED_EVENT);
          workAssignmentType.setEventInfo(eventInfo);

          //save workassignment
          workAssignment = workAssignmentHandler.saveWorkAssignment(
              workAssignmentDocument, loggedInUserInfoDocument);

          //set success
          isAsmtAssignedSuccessfully = AppraisalAssignmentConstants.SUCCESS;

          // Schedule Method
          doScheduleMethodUpdateForAssignTask(assignTaskType,
              workAssignment.getReferenceId(), loggedInUserInfoDocument);

          // get AppraisalAssignment MitchellEnvelope for app log
          final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appAsmtDTO = estimatePackageProxy
              .getAppraisalAssignmentDocument(workAssignment.getReferenceId()
                  .longValue());
          final MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
              .parse(appAsmtDTO.getAppraisalAssignmentMEStr());

          appLogUtil.appLog(
              AppraisalAssignmentConstants.APPLOG_UPDATE_ASSIGNMENT_SUCCESS,
              workAssignment, loggedInUserInfoDocument, meDoc, startTime);

        } // END else case for NEW PBI 380201 for CULDESAC RE-DISPATCH    	  

      }

    } catch (final Exception exception) {

      final int errorCode = AppraisalAssignmentConstants.ERROR_SCHEDULING_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_SCHEDULING_AA_MSG;

      StringBuffer msgDetail = new StringBuffer("");

      msgDetail.append("AssignTaskType: ").append(assignTaskType);
      msgDetail.append(", WorkAssignmentTaskId: ").append(workAssignmentTaskID);
      msgDetail.append(", UserInfo: ").append(
          loggedInUserInfoDocument.toString());

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);
    if (logger.isLoggable(Level.INFO)) {
      logger.info(METHOD_NAME + ":: int value returned:"
          + isAsmtAssignedSuccessfully + ", TaskId: " + workAssignmentTaskID);
    }

    return isAsmtAssignedSuccessfully;
  }

  /**
   * <p>
   * This method is responsible for assigning assignee to Appraisal Assignment
   * from dispatch board. This method does following major tasks:
   * </p>
   * <ul>
   * <li>Retrieves assignee's user information from UserInfo Service.</li>
   * <li>Retrieves, updates and saves work assignment to WorkAssignment Service.
   * </li>
   * <li>Saves updates MitchellEnvelope to EstimatePackage Service</li>
   * <li>Creates Claim-Suffix Activity Log.</li>
   * </ul>
   * 
   * @param assignTaskXmlObject
   *          - XmlObject object of AssignTaskType containing schedule info
   * @param loggedInUserInfoDocument
   *          - UserInfoDocument for logged in user
   * @param workAssignment
   *          - WorkAssignment object
   * @return <code>0</code> if successfully processed the request and
   *         <code>1</code> if unsuccessfully processed. <code>2</code> if
   *         stale data.
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   * 
   * 
   */
  protected int internalAssignScheduleAppraisalAssignment(
      final XmlObject assignTaskXmlObject,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "internalAssignScheduleAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();
    final StringBuffer logSuffix = new StringBuffer();
    String disposition = null;
    final AssignTaskType assignTaskType = (AssignTaskType) assignTaskXmlObject;

    AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();

    if (logger.isLoggable(Level.FINE)) {
      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("AssignTaskType : ").append(assignTaskType)
          .append("\nLogged In UserInfoDocument : ")
          .append(loggedInUserInfoDocument.toString());
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    final long workAssignmentTaskID = assignTaskType.getTaskId();

    String newResourceUserID = null;
    final String workGroupCode = null;
    Calendar scheduleDateTime = null;
    boolean dispatchedAfterAutoScheduleInd = false;
    long requestTCN = 0;
    String userType = null;

    if (assignTaskType.isSetAssigneeId()) {
      newResourceUserID = assignTaskType.getAssigneeId();
    }
    if (assignTaskType.isSetScheduleDateTime()) {
      scheduleDateTime = assignTaskType.getScheduleDateTime();
    }
    if (assignTaskType.isSetDispatchedAfterAutoScheduleInd()) {
      dispatchedAfterAutoScheduleInd = assignTaskType
          .getDispatchedAfterAutoScheduleInd();
    }
    if (assignTaskType.isSetAppraisalAssignmentTCN()) {
      requestTCN = assignTaskType.getAppraisalAssignmentTCN();
    }
    String claimNumber = null;
    String workItemID = null;

    Long documentId = null;
    String dbAssigneeID = null;
    UserDetailDocument assigneeUserDetail = null;
    UserInfoDocument assigneeUserInfoDocument = null;
    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    String companyCode = null;
    int isAsmtAssignedSuccessfully = AppraisalAssignmentConstants.FAILURE;
    boolean isAllMandatoryFieldExist = false;
    String eventName = null;
    String eventDesc = null;
    String dispatchCenter = null;

    try {

      final java.util.ArrayList validationErrorsArrayList = new java.util.ArrayList();

      if (!assignTaskType.validate(commonUtils
          .getXmlOptions(validationErrorsArrayList))) {
        logger.severe("Error validating assignTaskType :: "
            + validationErrorsArrayList);
        throw new MitchellException(CLASS_NAME, METHOD_NAME,
            "Invalid AssignTaskType: " + assignTaskType + "\nError :"
                + validationErrorsArrayList);
      }

      if (scheduleDateTime == null && newResourceUserID == null) {
        logger
            .severe("AssigneeID and ScheduleDateTime are both not set in AssignTaskType. Set either AssigneeID or ScheduleDateTime.");
        throw new MitchellException(
            CLASS_NAME,
            METHOD_NAME,
            "AssigneeID and ScheduleDateTime are both not set in AssignTaskType. Set either AssigneeID or ScheduleDateTime.");
      }
      companyCode = loggedInUserInfoDocument.getUserInfo().getOrgCode();
      if (logger.isLoggable(Level.INFO)) {
        logger.info(METHOD_NAME + ":: COMPANY CODE::" + companyCode);
      }

      // Setting work assignment task ID in StringBuffer for Log Prefix.
      logSuffix.append("\t[WorkAssignment task ID:")
          .append(workAssignmentTaskID).append("]\t");

      // retrieving work assignment for task ID
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      // CHECK TCN for stale data check and if assignment is stale then
      // return as stale data exists
      if (checkStaleData(workAssignment.getTcn(), requestTCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();
      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();

      // Setting Claim Number in StringBuffer for Log Prefix.
      dbAssigneeID = workAssignmentDocument.getWorkAssignment()
          .getCurrentSchedule().getAssigneeID();

      if (newResourceUserID != null) {
        // Check: For any supplement re-assign, only STAFF user can be
        // reassigned to another STAFF user
        final boolean isSuppReassignAllowedFlag = isSupplementReassignAllowed(
            newResourceUserID, companyCode, workAssignmentDocument
                .getWorkAssignment().getCurrentSchedule().getAssigneeID(),
            workAssignmentDocument.getWorkAssignment().getType());

        if (!isSuppReassignAllowedFlag) {
          return AppraisalAssignmentConstants.FAILURE;
        }

        // Retrieving UserInfo of assigned Estimator
        assigneeUserInfoDocument = userInfoUtils.retrieveUserInfo(companyCode,
            newResourceUserID);
        assigneeUserDetail = userInfoUtils.getUserDetailDoc(companyCode,
            newResourceUserID);
        userType = userInfoUtils.getUserType(assigneeUserInfoDocument
            .getUserInfo().getOrgCode(), assigneeUserInfoDocument.getUserInfo()
            .getUserID());

        if (logger.isLoggable(Level.INFO)) {
          final StringBuffer logMessage = new StringBuffer();
          logMessage.append(METHOD_NAME
              + ":: ****************************** companyCode : ");
          logMessage.append(companyCode);
          logMessage.append("\t newResourceUserID");
          logMessage.append(newResourceUserID);
          logMessage.append("\t workGroupCode");
          logMessage.append(workGroupCode);
          logMessage.append(logSuffix);
          logger.info(logMessage.toString());
        }

        dispatchCenter = assignTaskType.getWorkGroupCode();

        if (logger.isLoggable(Level.INFO)) {
          logger
              .info(METHOD_NAME
                  + ":: Fetched dispatchCenter from retrieveUserDispatchCenter of AppraisalAssignmentUtils : "
                  + dispatchCenter + logSuffix);
        }

      }

      final String dbDisposition = workAssignmentDocument.getWorkAssignment()
          .getDisposition();

      // 
      if (AppraisalAssignmentConstants.DISPOSITION_NOT_READY
          .equalsIgnoreCase(dbDisposition)) {
        if (workAssignment.getReqAssocTaskDataCompleted() != null
            && "Y".equalsIgnoreCase(workAssignment
                .getReqAssocTaskDataCompleted())) {
          isAllMandatoryFieldExist = true;
        }

        if (logger.isLoggable(Level.INFO)) {
          final StringBuffer logMessage = new StringBuffer();
          logMessage.append("isAllMandatoryFieldExist in ");
          logMessage.append(METHOD_NAME);
          logMessage.append(": ");
          logMessage.append(isAllMandatoryFieldExist);
          logger.info(logMessage.toString() + logSuffix);
        }

        String groupCode = getGroupCodeFromWorkAssignmentDocument(workAssignmentDocument);
        disposition = this.isAssignmentReadyForDispatch(
            isAllMandatoryFieldExist, newResourceUserID, groupCode,
            scheduleDateTime, loggedInUserInfoDocument);

      } else {
        disposition = dbDisposition;
      }

      if (logger.isLoggable(Level.INFO)) {
        logger.info(METHOD_NAME + ":: Disposition is::" + disposition);
      }

      if (AppraisalAssignmentConstants.DISPOSITION_READY
          .equalsIgnoreCase(disposition) && dispatchedAfterAutoScheduleInd) {
        disposition = AppraisalAssignmentConstants.DISPOSITION_DISPATCHED;

        if (logger.isLoggable(Level.INFO)) {
          logger.info(METHOD_NAME
              + ":: disptachAfterAutoScheduleInd is true! disposition set to::"
              + disposition + logSuffix);
        }

      }

      // Updating and saving work assignment
      long waStartTime = System.currentTimeMillis();
      workAssignment = workAssignmentHandler.assignWorkAssignment(
          workAssignmentDocument, disposition, dispatchCenter,
          assigneeUserInfoDocument, scheduleDateTime, assigneeUserDetail,
          loggedInUserInfoDocument);
      long waEndTime = System.currentTimeMillis();
      appLoggingNVPairs.addInfo("SaveWorkAssignmentTimingSec",
          String.valueOf((waEndTime - waStartTime) / 1000.0));

      documentId = workAssignment.getReferenceId();

      if (logger.isLoggable(Level.INFO)) {
        final StringBuffer logMessage = new StringBuffer();
        logMessage.append(METHOD_NAME
            + ":: Calling writeAssignmentActivityLog method with EventName::");
        logMessage.append(eventName);
        logMessage.append("\tEventDesc::");
        logMessage.append(eventDesc);
        logMessage.append(logSuffix);
        logger.info(logMessage.toString());
      }

      // Call CARRHelper Service to update review assignment for
      // supplement assignment & dispatch request.
      final String workAsmtType = workAssignmentDocument.getWorkAssignment()
          .getType();

      if (logger.isLoggable(Level.INFO)) {
        logger.info(METHOD_NAME + ":: workAsmtType for ASSIGNMENT TYPE ====>"
            + workAsmtType + logSuffix);
      }

      if (AppraisalAssignmentConstants.DISPOSITION_DISPATCHED
          .equalsIgnoreCase(disposition)
          && AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
              .equalsIgnoreCase(workAsmtType)) {

        if (logger.isLoggable(Level.INFO)) {
          logger.info(METHOD_NAME + ":: Calling EstimatePackageService for :"
              + documentId + logSuffix);
        }

        final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appAsmtDTO = estimatePackageProxy
            .getAppraisalAssignmentDocument(documentId.longValue());

        if (logger.isLoggable(Level.INFO)) {
          logger
              .info(METHOD_NAME
                  + ":: Successfully fetched AppraisalAssignmentDTO from Estimate Package Service for Document Id :"
                  + documentId + logSuffix.toString());
        }

        final MitchellEnvelopeDocument updatedMitchellEnvDoc = MitchellEnvelopeDocument.Factory
            .parse(appAsmtDTO.getAppraisalAssignmentMEStr());
        final MitchellEnvelopeHelper mitchellEnvelopeHelper = new MitchellEnvelopeHelper(
            updatedMitchellEnvDoc);
        final long relatedEstimateDocumentId = mitchellEnvelopeHandler
            .getEstimateDocId(mitchellEnvelopeHelper);
        final UserInfoDocument estimatorUserInfo = userInfoUtils
            .getEstimatorInfo(relatedEstimateDocumentId);
        updateReviewAssignment(relatedEstimateDocumentId, estimatorUserInfo,
            loggedInUserInfoDocument);

        if (logger.isLoggable(Level.INFO)) {
          logger
              .info(METHOD_NAME
                  + ":: Supplement Assignment has been successfully updated for review by "
                  + "CARR Service.Method Name is :: internalAssignScheduleAppraisalAssignment"
                  + logSuffix);
        }

      }

      final WorkAssignmentDocument updatedWorkAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(workAssignment.getWorkAssignmentCLOBB());
      String assigneeFirstName = "";
      String assigneeLastName = "";

      // Assignee Name
      final PersonInfoType pT = updatedWorkAssignmentDocument
          .getWorkAssignment().getCurrentSchedule().getAssignee();
      if (pT != null) {
        PersonNameType personNameType = pT.getPersonName();
        if (personNameType != null) {
          if (personNameType.getFirstName() != null) {
            assigneeFirstName = personNameType.getFirstName();
          }
          if (personNameType.getLastName() != null) {
            assigneeLastName = personNameType.getLastName();
          }
        }
      }

      StringBuffer nameBuff = new StringBuffer(assigneeFirstName).append(" ")
          .append(assigneeLastName);
      String fullNameString = nameBuff.toString();
      if (fullNameString.length() > 1) {
        appLoggingNVPairs.addInfo("AssigneeName", fullNameString);
      }

      // Activity Log Dispatched
      if (AppraisalAssignmentConstants.DISPOSITION_DISPATCHED
          .equalsIgnoreCase(disposition)) {

        StringBuffer sbuff = new StringBuffer(
            appraisalAssignmentConfig.getDispatchAAActivityLog());
        sbuff.append(" ");
        sbuff.append(fullNameString);

        if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
            .equalsIgnoreCase(workAssignmentDocument.getWorkAssignment()
                .getType())) {
          sbuff.append(" - SUPPLEMENT");
        } else {
          sbuff.append(" - ORIGINAL");
        }

        commonUtils.saveExposureActivityLog(sbuff.toString(),
            workAssignment.getClaimExposureID(), documentId, workItemID,
            loggedInUserInfoDocument);

        eventName = AppraisalAssignmentConstants.DB_DISPATCH_AA_ASSIGNMENT_ACTIVITYLOG;
        eventDesc = appraisalAssignmentConfig
            .getDispatchAAAssignmentActivityLog();

      } else if (newResourceUserID != null) {

        String activityLogMsg = null;
        if (dbAssigneeID == null || dbAssigneeID.trim().length() == 0
            || dbAssigneeID.equalsIgnoreCase(newResourceUserID)) {
          activityLogMsg = appraisalAssignmentConfig.getAssignAAActivityLog();
          eventName = AppraisalAssignmentConstants.DB_ASSIGN_AA_ASSIGNMENT_ACTIVITYLOG;
          eventDesc = appraisalAssignmentConfig
              .getAssignAAAssignmentActivityLog();
        } else {
          activityLogMsg = appraisalAssignmentConfig.getReAssignAAActivityLog();
          eventName = AppraisalAssignmentConstants.DB_REASSIGN_AA_ASSIGNMENT_ACTIVITYLOG;
          eventDesc = appraisalAssignmentConfig
              .getReAssignAAAssignmentActivityLog();
        }

        StringBuffer sbuff = new StringBuffer(activityLogMsg);
        sbuff.append(" ");
        sbuff.append(fullNameString);

        // creating claim suffix activity log
        if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
            .equalsIgnoreCase(workAssignmentDocument.getWorkAssignment()
                .getType())) {
          sbuff.append(" - SUPPLEMENT");
        } else {
          sbuff.append(" - ORIGINAL");
        }

        // "The Appraisal Assignment Assigned Successfully",
        commonUtils.saveExposureActivityLog(sbuff.toString(),
            workAssignment.getClaimExposureID(), documentId, workItemID,
            loggedInUserInfoDocument);

      }

      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

      // Schedule Method
      doScheduleMethodUpdateForAssignTask(assignTaskType,
          workAssignment.getReferenceId(), loggedInUserInfoDocument);

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_ASSIGN_ASSIGNMENT_SUCCESS,
          workAssignment, loggedInUserInfoDocument, null, startTime,
          appLoggingNVPairs);

      isAsmtAssignedSuccessfully = AppraisalAssignmentConstants.SUCCESS;

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info(METHOD_NAME
                + ":: >>>> internalAssignScheduleAppraisalAssignment::SCHEDULE_METHOD "
                + "After updateAppraisalAssignmentScheduleMethod: ReferenceId= [ "
                + workAssignment.getReferenceId() + " ]");
      }

    } catch (final Exception exception) {

      final int errorCode = AppraisalAssignmentConstants.ERROR_SCHEDULING_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_SCHEDULING_AA_MSG;

      StringBuffer msgDetail = new StringBuffer("<ProcessingInformation>");
      msgDetail.append("<InputAssignTaskType>")
          .append(assignTaskType.toString()).append("</InputAssignTaskType>");

      msgDetail.append("<InputUserInfoDocument>")
          .append(loggedInUserInfoDocument.toString())
          .append("</InputUserInfoDocument>");

      msgDetail.append("<InputWorkAssignmentTaskId>")
          .append(workAssignmentTaskID).append("</InputWorkAssignmentTaskId>");

      msgDetail.append("<InputWorkAssignmentRefId>").append(documentId)
          .append("</InputWorkAssignmentRefId>");

      msgDetail.append("</ProcessingInformation>");

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);

    }

    logger.exiting(CLASS_NAME, METHOD_NAME);
    if (logger.isLoggable(Level.INFO)) {
      logger.info(METHOD_NAME + ":: int value returned:"
          + isAsmtAssignedSuccessfully + logSuffix);
    }

    return isAsmtAssignedSuccessfully;
  }

  // Task 270336 -New Method for HT APPT BK ========================================
  //
  private void updateScheduleMethodManual(WorkAssignment workAssignment,
      String workItemID, String claimNumber,
      final UserInfoDocument loggedInUserInfoDocument, final String METHOD_NAME)
      throws MitchellException
  {

    if (logger.isLoggable(Level.INFO)) {
      logger.info(">>>> Entering from AAS::" + METHOD_NAME
          + " calling updateScheduleMethodManual::SCHEDULE_METHOD "
          + " \nInputs ReferenceId= [ " + workAssignment.getReferenceId()
          + " ]" + " \tclaimNumber= [ " + claimNumber + " ]"
          + " \tworkItemID= [ " + workItemID + " ]");
    }

    updateScheduleMethodInDatabase(workAssignment.getReferenceId(),
        loggedInUserInfoDocument, SCHEDULE_METHOD_MANUAL);
  }

  /**
   * Do ScheduleMethod Update for AssignTask.
   * 
   * PBI 270344 - Reset Schedule Method to MANUAL for
   * assignScheduleAppraisalAssignment (assign/reassign use cases)
   * 11/11/2013 - Logic updated to support the schedule method being providing
   * by the caller. Defaults to MANUAL when not provided.
   * 
   */
  protected void doScheduleMethodUpdateForAssignTask(
      AssignTaskType assignTaskType, long appraisalAsgDocumentId,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    String scheduleMethod = SCHEDULE_METHOD_MANUAL;
    if (assignTaskType.isSetScheduleMethod()
        && assignTaskType.getScheduleMethod() != null
        && assignTaskType.getScheduleMethod().length() > 0) {
      scheduleMethod = assignTaskType.getScheduleMethod();
    }
    updateScheduleMethodInDatabase(appraisalAsgDocumentId,
        loggedInUserInfoDocument, scheduleMethod);
  }

  /**
   * Update ScheduleMethod In Database.
   * 
   * @param appraisalAsgDocumentId
   * @param loggedInUserInfoDocument
   * @param scheduleMethod
   * @throws MitchellException
   */
  protected void updateScheduleMethodInDatabase(long appraisalAsgDocumentId,
      final UserInfoDocument loggedInUserInfoDocument, String scheduleMethod)
      throws MitchellException
  {
    // Validate Legal ScheduleMethod values
    if (SCHEDULE_METHOD_MANUAL.equals(scheduleMethod)
        || SCHEDULE_METHOD_APPT_BOOKED.equals(scheduleMethod)) {

      // Create an AppraisalAssignment DTO for the update and set the ScheduleMethod field
      com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment appraisalAssignment = new com.mitchell.services.technical.partialloss.estimate.bo.AppraisalAssignment();
      appraisalAssignment.setScheduleMethod(scheduleMethod);

      // Identify which field (ScheduleMethod) is to be updated
      List<AppraisalAssignmentUpdateFieldEnum> updateFields = new ArrayList<AppraisalAssignmentUpdateFieldEnum>();
      updateFields.add(AppraisalAssignmentUpdateFieldEnum.SCHEDULE_METHOD);

      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Calling estimatePackageProxy.updateAppraisalAssignmentScheduleMethod. DocId="
                + appraisalAsgDocumentId
                + ", ScheduleMethod: "
                + scheduleMethod);
      }

      // Call EstimatePackage to do the update.
      estimatePackageProxy.updateAppraisalAssignmentScheduleMethod(
          appraisalAsgDocumentId, loggedInUserInfoDocument,
          appraisalAssignment, updateFields);

    } else {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_SAVE_SUPP_AA_POPULATEAADTO, this
              .getClass().getName(), "updateScheduleMethodInDatabase",
          "Unsupported ScheduleMethod received: " + scheduleMethod);
    }
  }

  private boolean isSupplementReassignAllowed(final String reqAsigneeId,
      final String coCode, final String dbAsigneeId, final String assignmentType)
      throws MitchellException
  {
    final String METHOD_NAME = "isSupplementReassignAllowed";
    logger.entering(CLASS_NAME, METHOD_NAME);

    if (logger.isLoggable(Level.FINE)) {
      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("Input RequestAssignee: ").append(reqAsigneeId)
          .append("\n CompanyCode: ").append(coCode)
          .append("\nDatabaseAssignee: ").append(dbAsigneeId)
          .append("\nAssignment Type: ").append(assignmentType);
      logger.fine("Input Received ::\n" + localMethodParams.toString());
    }

    boolean isSupplementReassignFlag = true;
    try {
      if (dbAsigneeId != null
          && reqAsigneeId != null
          && dbAsigneeId.trim().length() > 0
          && reqAsigneeId.trim().length() > 0
          && !reqAsigneeId.equalsIgnoreCase(dbAsigneeId)
          && AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
              .equalsIgnoreCase(assignmentType)) {
        final String dbAssigneeType = userInfoUtils.getUserType(coCode,
            dbAsigneeId);
        final String reqAssigneeType = userInfoUtils.getUserType(coCode,
            reqAsigneeId);
        if (UserTypeConstants.STAFF_TYPE.equalsIgnoreCase(dbAssigneeType)
            && UserTypeConstants.STAFF_TYPE.equalsIgnoreCase(reqAssigneeType)) {
          isSupplementReassignFlag = true;
        } else {
          isSupplementReassignFlag = false;
        }
        if (logger.isLoggable(Level.INFO)) {
          logger.info("SupplementReassignFlag is set to : "
              + isSupplementReassignFlag);
        }
      }
    } catch (final Exception ex) {
      final StringBuffer errorMessage = new StringBuffer(
          "Exception while calling getUserType with DatabaseAsigneeId ID : ");
      errorMessage.append(dbAsigneeId).append("\nRequestAsigneeId : ")
          .append(reqAsigneeId).append("\nCompanyCode : ").append(coCode);
      logger.severe(errorMessage.toString());
      throw new MitchellException(CLASS_NAME, METHOD_NAME,
          errorMessage.toString(), ex);
    }
    if (logger.isLoggable(Level.INFO)) {
      logger.info("isSupplementReassignAllowed ::" + isSupplementReassignFlag);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return isSupplementReassignFlag;
  }

  /**
   * onHoldAppraisalAssignment each Assignment with workassignmenttaskId ,TCN
   * , selectedOnHoldTypeFromCarrier and assignorInfoDocument from Dispatch
   * Board
   * 
   * @param workAssignmentTaskID
   *          - workAssignmentTaskID object
   * @param TCN
   *          - TCN object
   * @param selectedOnHoldTypeFromCarrier
   *          - selectedOnHoldTypeFromCarrier object
   * @param loggedInUserInfoDocument
   *          - loggedInUserInfoDocument object
   * @param notes
   *          - notes object
   * @return int - int value
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public int onHoldAppraisalAssignment(final long workAssignmentTaskID,
      final long TCN, final String selectedOnHoldTypeFromCarrier,
      final String notes, final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {

    final String METHOD_NAME = "onHoldAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    // Set errorLog Details and initialize ErrorLog, AppLog.
    long startTime = System.currentTimeMillis();

    final StringBuffer localMethodParams = new StringBuffer();
    final StringBuffer logSuffix = new StringBuffer();

    localMethodParams.append("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\nTCN : ").append(TCN)
        .append("\nselectedOnHoldTypeFromCarrier : ")
        .append(selectedOnHoldTypeFromCarrier)
        .append("\nLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    String claimNumber = null;
    String workItemID = null;
    StringBuffer msgDetail = null;
    Long documentId = null;

    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignmentType workAssignmentType = null;
    int isAsmtOnHoldSuccessfully = AppraisalAssignmentConstants.FAILURE;
    String eventName = null;
    String eventDesc = null;

    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    try {
      // / Setting work assignment task ID in StringBuffer for Log Prefix
      logSuffix.append("\t[WorkAssignment task ID:")
          .append(workAssignmentTaskID).append("]\t");
      /*
       * retrieving work assignment for task ID
       */
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      /*
       * CHECK TCN for stale data check and if assignment is stale then
       * return as stale data exists
       */
      if (checkStaleData(workAssignment.getTcn(), TCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();
      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();
      msgDetail.append("\tClaimNumber : ").append(claimNumber);
      // / Setting Claim Number in StringBuffer for Log Prefix
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");
      workAssignmentType = workAssignmentDocument.getWorkAssignment();

      logger.info("Creating OnHoldInfoType");

      // Updating CLOB with OnHoldInfoType

      final HoldInfoType holdType = HoldInfoType.Factory.newInstance();

      holdType.setHoldUpdatedBy(loggedInUserInfoDocument.getUserInfo()
          .getUserID());
      holdType.setHoldUpdatedByDateTime(Calendar.getInstance());
      holdType.setHoldInd("Y");
      if (selectedOnHoldTypeFromCarrier != null
          && !selectedOnHoldTypeFromCarrier.equals("")) {
        holdType.setHoldReasonCode(selectedOnHoldTypeFromCarrier);
      }
      if (notes != null && !"".equalsIgnoreCase(notes)) {
        holdType.setHoldReasonNotes(notes);
      }

      final EventInfoType eventInfoType = workAssignmentType.getEventInfo();
      eventInfoType.setEvent(EventDefinitionType.UPDATED_EVENT);
      eventInfoType.setEventDateTime(Calendar.getInstance());
      eventInfoType.setUpdatedByID(loggedInUserInfoDocument.getUserInfo()
          .getUserID());

      workAssignmentType.setEventInfo(eventInfoType);

      workAssignmentType.setHoldInfo(holdType);

      /*
       * Making WorkAssignment object null, so we can be sure that new
       * WorkAssignment Object is not null.
       */

      workAssignment = null;

      /*
       * Updating and saving work assignment
       */
      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("Saving WorkAssignment for OnHold through WorkAssignmentService"
                + workAssignmentDocument + logSuffix);
      }
      workAssignment = workAssignmentHandler.saveWorkAssignment(
          workAssignmentDocument, loggedInUserInfoDocument);
      documentId = workAssignment.getReferenceId();
      msgDetail.append("\tDocumentID : ").append(documentId);

      // eventName will come from CONSTANT File and eventDesc will be
      // fetched from SET file
      eventDesc = appraisalAssignmentConfig.getOnHoldAAAssignmentActivityLog();
      eventName = AppraisalAssignmentConstants.DB_ONHOLD_AA_ASSIGNMENT_ACTIVITYLOG;

      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_ONHOLD_ASSIGNMENT_SUCESS,
          workAssignment, loggedInUserInfoDocument, null, startTime);

      isAsmtOnHoldSuccessfully = AppraisalAssignmentConstants.SUCCESS;

      // PBI 270344 - Reset Schedule Method to MANUAL for onHoldAppraisalAssignment use case
      //
      updateScheduleMethodManual(workAssignment, workItemID, claimNumber,
          loggedInUserInfoDocument, METHOD_NAME);

      if (logger.isLoggable(Level.INFO)) {
        logger.info(">>>> onHoldAppraisalAssignment::SCHEDULE_METHOD "
            + "After updateAppraisalAssignmentScheduleMethod: ReferenceId= [ "
            + workAssignment.getReferenceId() + " ]");
      }

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_ONHOLD_AA_SAVINGWA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_ONHOLD_AA_SAVINGWA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);

    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAsmtOnHoldSuccessfully;
  }

  /**
   * removeOnHoldAppraisalAssignment each Assignment with workassignmenttaskId
   * ,TCN and assignorInfoDocument from Dispatch Board
   * 
   * @param workAssignmentTaskID
   *          - workAssignmentTaskID object
   * @param TCN
   *          - TCN object
   * @param loggedInUserInfoDocument
   *          - loggedInUserInfoDocument object
   * @return int - int value
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public int removeOnHoldAppraisalAssignment(final long workAssignmentTaskID,
      final long TCN, final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {

    final String METHOD_NAME = "removeOnHoldAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();
    final StringBuffer localMethodParams = new StringBuffer();
    localMethodParams.append("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\nTCN : ").append(TCN)
        .append("\nLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    String claimNumber = null;
    StringBuffer msgDetail = null;
    final StringBuffer logSuffix = new StringBuffer();
    String workItemID = null;
    Long documentId = null;
    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignmentType workAssignmentType = null;
    int isAsmtRemovedFromOnHoldSuccessfully = AppraisalAssignmentConstants.FAILURE;
    String eventName = null;
    String eventDesc = null;

    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    try {
      // / Setting work assignment task ID in StringBuffer for Log Prefix
      logSuffix.append("\t[WorkAssignment task ID:")
          .append(workAssignmentTaskID).append("]\t");
      // retrieving work assignment for task ID
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      /*
       * CHECK TCN for stale data check and if assignment is stale then
       * return as stale data exists
       */
      if (checkStaleData(workAssignment.getTcn(), TCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();

      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();

      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();
      msgDetail.append("\tClaimNumber : ").append(claimNumber);

      // / Setting Claim Number in StringBuffer for Log Prefix
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");

      workAssignmentType = workAssignmentDocument.getWorkAssignment();
      // Unsetting HoldInfo from CLOB
      if (workAssignmentType.isSetHoldInfo()) {
        workAssignmentType.unsetHoldInfo();
      } else {
        return AppraisalAssignmentConstants.SUCCESS;
      }

      final EventInfoType eventInfoType = workAssignmentType.getEventInfo();
      eventInfoType.setEventDateTime(Calendar.getInstance());
      eventInfoType.setUpdatedByID(loggedInUserInfoDocument.getUserInfo()
          .getUserID());
      eventInfoType.setEvent(EventDefinitionType.UPDATED_EVENT);

      /*
       * Making WorkAssignment object null, so we can be sure that new
       * WorkAssignment Object is not null.
       */

      workAssignment = null;

      /*
       * Updating and saving work assignment
       */
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Saving WorkAssignment for removeOnHold through WorkAssignmentService"
                + logSuffix);
      }
      workAssignment = workAssignmentHandler.saveWorkAssignment(
          workAssignmentDocument, loggedInUserInfoDocument);

      documentId = workAssignment.getReferenceId();
      msgDetail.append("\tDocumentID : ").append(documentId);

      // eventName will come from CONSTANT File and eventDesc will be
      // fetched from SET file
      eventDesc = appraisalAssignmentConfig
          .getRemoveOnHoldAAAssignmentActivityLog();
      eventName = AppraisalAssignmentConstants.DB_REMOVE_ONHOLD_AA_ASSIGNMENT_ACTIVITYLOG;

      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_REMOVE_ONHOLD_ASSIGNMENT_SUCESS,
          workAssignment, loggedInUserInfoDocument, null, startTime);

      isAsmtRemovedFromOnHoldSuccessfully = AppraisalAssignmentConstants.SUCCESS;

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_UNHOLDLING_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_UNHOLDLING_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAsmtRemovedFromOnHoldSuccessfully;
  }

  /**
   * Unschedule each Assignment with workassignmenttaskId ,TCN and
   * assignorInfoDocument from Dispatch Board
   * 
   * @param workAssignmentTaskID
   *          - workAssignmentTaskID object
   * @param requestTCN
   *          - requestTCN object
   * @param loggedInUserInfoDocument
   *          - loggedInUserInfoDocument object
   * @return int - int value
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public int unScheduleAppraisalAssignment(final long workAssignmentTaskID,
      final long requestTCN, final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "unScheduleAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);

    int isAssignmentSuccessfullyUnscheduled = unScheduleAssignment(
        workAssignmentTaskID, requestTCN, null, null, loggedInUserInfoDocument);

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAssignmentSuccessfullyUnscheduled;
  }

  private int unScheduleAssignment(final long workAssignmentTaskID,
      final long requestTCN, final String reasonCode, final String reasonNotes,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "unScheduleAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    // Set errorLog Details and initialize ErrorLog, AppLog
    long startTime = System.currentTimeMillis();

    final StringBuffer localMethodParams = new StringBuffer();
    final StringBuffer logSuffix = new StringBuffer();

    localMethodParams.append("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\nTCN : ").append(requestTCN)
        .append("\nLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    String claimNumber = null;
    String workItemID = null;
    StringBuffer msgDetail = null;
    Long documentId = null;
    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    int isAssignmentSuccessfullyUnscheduled = AppraisalAssignmentConstants.FAILURE;
    String eventName = null;
    String eventDesc = null;

    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    try {
      // / Setting work assignment task ID in StringBuffer for Log Prefix
      logSuffix.append("\t[WorkAssignment task ID:")
          .append(workAssignmentTaskID).append("]\t");

      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      /*
       * CHECK TCN for stale data check and if assignment is stale then
       * return as stale data exists
       */
      if (checkStaleData(workAssignment.getTcn(), requestTCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();
      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();

      msgDetail.append("\tClaimNumber : ").append(claimNumber);
      // / Setting Claim Number in StringBuffer for Log Prefix
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");

      workAssignment = workAssignmentHandler.unscheduleWorkAssignment(
          workAssignment, reasonCode, reasonNotes, loggedInUserInfoDocument);
      documentId = workAssignment.getReferenceId();
      msgDetail.append("\tDocumentID : ").append(documentId);

      /*
       * Claim-Suffix Activity Log
       */

      commonUtils.saveExposureActivityLog(
          // "The Appraisal Assignment Unschedule Successfully",
          appraisalAssignmentConfig.getUnScheduleAAActivityLog(),
          workAssignment.getClaimExposureID(), documentId, workItemID,
          loggedInUserInfoDocument);

      // eventName will come from CONSTANT File and eventDesc will be
      // fetched from SET file
      eventDesc = appraisalAssignmentConfig
          .getUnScheduleAAAssignmentActivityLog();
      eventName = AppraisalAssignmentConstants.DB_UNSCHEDULE_AA_ASSIGNMENT_ACTIVITYLOG;
      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_UNSCHEDULE_ASSIGNMENT_SUCCESS,
          workAssignment, loggedInUserInfoDocument, null, startTime);

      isAssignmentSuccessfullyUnscheduled = AppraisalAssignmentConstants.SUCCESS;

      // PBI 270344 - Reset Schedule Method to MANUAL for unScheduleAssignment use case
      //
      updateScheduleMethodManual(workAssignment, workItemID, claimNumber,
          loggedInUserInfoDocument, METHOD_NAME);

      if (logger.isLoggable(Level.INFO)) {
        logger.info(">>>> unScheduleAssignment::SCHEDULE_METHOD "
            + "After updateAppraisalAssignmentScheduleMethod: ReferenceId= [ "
            + workAssignment.getReferenceId() + " ]");
      }

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAssignmentSuccessfullyUnscheduled;
  }

  
  private int unScheduleAssignment(final long workAssignmentTaskID, final long requestTCN, final String reasonCode,
			final String reasonNotes, final UserInfoDocument loggedInUserInfoDocument,
			final ItineraryViewDocument itineraryXML) throws MitchellException {

		logger.info("entering unSchedulingAssignment");
		final String METHOD_NAME = "unScheduleAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);
		// Set errorLog Details and initialize ErrorLog, AppLog
		long startTime = System.currentTimeMillis();

		final StringBuffer localMethodParams = new StringBuffer();
		final StringBuffer logSuffix = new StringBuffer();

		localMethodParams.append("WorkAssignmentTaskID : ").append(workAssignmentTaskID).append("\nTCN : ")
				.append(requestTCN).append("\nLogged In UserInfoDocument : ").append(loggedInUserInfoDocument.toString());

		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received ::\n" + localMethodParams);
		}
		String claimNumber = null;
		String workItemID = null;
		StringBuffer msgDetail = null;
		Long documentId = null;
		WorkAssignment workAssignment = null;
		WorkAssignmentDocument workAssignmentDocument = null;
		int isAssignmentSuccessfullyUnscheduled = AppraisalAssignmentConstants.FAILURE;
		String eventName = null;
		String eventDesc = null;

		msgDetail = new StringBuffer("Method Parameters ::").append(localMethodParams);
		try {
			// / Setting work assignment task ID in StringBuffer for Log Prefix
			logSuffix.append("\t[WorkAssignment task ID:").append(workAssignmentTaskID).append("]\t");

			workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(workAssignmentTaskID, loggedInUserInfoDocument);

			/*
			 * CHECK TCN for stale data check and if assignment is stale then
			 * return as stale data exists
			 */
			if (checkStaleData(workAssignment.getTcn(), requestTCN)) {
				return AppraisalAssignmentConstants.STALE_DATA;
			}

			final String XmlW = workAssignment.getWorkAssignmentCLOBB();
			workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
			workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getWorkItemID();
			claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs().getClaimNumber();

			msgDetail.append("\tClaimNumber : ").append(claimNumber);
			// / Setting Claim Number in StringBuffer for Log Prefix
			logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");

			logger.info("calling workAssignmentHandler.unscheduleWorkAssignment");
			workAssignment = workAssignmentHandler.unscheduleWorkAssignment(workAssignment, reasonCode, reasonNotes,
				loggedInUserInfoDocument, itineraryXML);
			documentId = workAssignment.getReferenceId();
			msgDetail.append("\tDocumentID : ").append(documentId);

			/*
			 * Claim-Suffix Activity Log
			 */

			commonUtils.saveExposureActivityLog(
				// "The Appraisal Assignment Unschedule Successfully",
				appraisalAssignmentConfig.getUnScheduleAAActivityLog(), workAssignment.getClaimExposureID(), documentId,
				workItemID, loggedInUserInfoDocument);

			// eventName will come from CONSTANT File and eventDesc will be
			// fetched from SET file
			eventDesc = appraisalAssignmentConfig.getUnScheduleAAAssignmentActivityLog();
			eventName = AppraisalAssignmentConstants.DB_UNSCHEDULE_AA_ASSIGNMENT_ACTIVITYLOG;
			this.writeAssignmentActivityLog(workAssignment.getId().longValue(), eventName, eventDesc,
				loggedInUserInfoDocument.getUserInfo().getUserID());

			appLogUtil.appLog(AppraisalAssignmentConstants.APPLOG_UNSCHEDULE_ASSIGNMENT_SUCCESS, workAssignment,
				loggedInUserInfoDocument, null, startTime);

			isAssignmentSuccessfullyUnscheduled = AppraisalAssignmentConstants.SUCCESS;

			// PBI 270344 - Reset Schedule Method to MANUAL for
			// unScheduleAssignment use case
			//
			updateScheduleMethodManual(workAssignment, workItemID, claimNumber, loggedInUserInfoDocument, METHOD_NAME);

			if (logger.isLoggable(Level.INFO)) {
				logger.info(">>>> unScheduleAssignment::SCHEDULE_METHOD "
						+ "After updateAppraisalAssignmentScheduleMethod: ReferenceId= [ " + workAssignment.getReferenceId()
						+ " ]");
			}

		} catch (final Exception exception) {
			final int errorCode = AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA;
			final String errorDescription = AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_MSG;
			logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode, errorDescription, workItemID, claimNumber,
				msgDetail.toString(), exception, loggedInUserInfoDocument);
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isAssignmentSuccessfullyUnscheduled;
	}

  
  /**
   * This method dispatches a single assignment.
   * 
   * @param workAssignmentTaskID
   *          - workAssignmentTaskID object
   * @param TCN
   *          - TCN object
   * @param loggedInUserInfoDocument
   *          - loggedInUserInfoDocument object
   * @return int - int value
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public int dispatchAppraisalAssignment(final long workAssignmentTaskID,
      final long TCN, final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "dispatchAppraisalAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();

    /*
     * Set errorLog Details and initialize ErrorLog, AppLog
     */

    final StringBuffer localMethodParams = new StringBuffer();
    StringBuffer msgDetail = null;
    final StringBuffer logSuffix = new StringBuffer();
    localMethodParams.append("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\nRequest TCN : ").append(TCN)
        .append("\nLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    int isAsmtDispatchedSuccessfully = AppraisalAssignmentConstants.FAILURE;
    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    WorkAssignmentDocument updatedWorkAssignmentDocument = null;
    Long claimExposureId = null;
    Long referenceId = null;
    String eventName = null;
    String eventDesc = null;
    final boolean isTestAssignmentDispatcher = testAssignmentHandler
        .isTestAssignmentUser(loggedInUserInfoDocument);

    // Local variables
    MitchellEnvelopeHelper mitchellEnvelopeHelper = null;
    MitchellEnvelopeDocument mitchellEnvelopeDoc = null;
    String workAsmtType = null;
    long relatedEstimateDocumentId;

    String claimNumber = null;

    String workItemID = null;

    try {

      // / Setting work assignment task ID in StringBuffer for Log Prefix
      logSuffix.append("\t[WorkAssignment task ID:")
          .append(workAssignmentTaskID).append("]\t");

      /*
       * Retrieve work assignment details using taskID from Work
       * Assignment
       */
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      /*
       * CHECK TCN for stale data check and if assignment is stale then
       * return as stale data exists
       */
      if (checkStaleData(workAssignment.getTcn(), TCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }
      claimExposureId = workAssignment.getClaimExposureID();

      referenceId = workAssignment.getReferenceId();
      msgDetail.append("\tDocumentID/ReferenceID : ").append(referenceId);

      /*
       * Retrieve work assignment xml from CLOB.
       */

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Retrieved Work Assignment Document from WorkAssignmentService"
                + logSuffix);
      }

      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();

      String disposition = workAssignmentDocument.getWorkAssignment()
          .getDisposition();

      if (AppraisalAssignmentConstants.DISPOSITION_NOT_READY
          .equalsIgnoreCase(disposition)) {

        logger
            .severe("Received 'NOT READY' Assignment with disposition as DISPATCHED. \nclaimNumber : "
                + claimNumber);
        throw new MitchellException(
            AppraisalAssignmentConstants.ERROR_FAILED_NOTREADY,
            CLASS_NAME,
            METHOD_NAME,
            "Received 'NOT READY' Assignment with disposition as DISPATCHED. \nclaimNumber: "
                + claimNumber);
      }

      msgDetail.append("\tClaimNumber : ").append(claimNumber);
      // / Setting Claim Number in StringBuffer for Log Prefix
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");
      // /
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();

      /*
       * Update work assignment xml with Dispatched request.
       */
      if (isTestAssignmentDispatcher
          && AppraisalAssignmentConstants.ORIGINAL_ASSIGNMENT_TYPE
              .equalsIgnoreCase(workAssignmentDocument.getWorkAssignment()
                  .getType())) {
        updatedWorkAssignmentDocument = testAssignmentHandler
            .updateTestWorkAssignmentDocument(workAssignmentDocument,
                loggedInUserInfoDocument);
      } else {
        updatedWorkAssignmentDocument = workAssignmentHandler
            .setupWorkAssignmentRequest(workAssignmentDocument,
                AppraisalAssignmentConstants.DISPOSITION_DISPATCHED,
                "dispatch", loggedInUserInfoDocument);
      }

      if (logger.isLoggable(Level.INFO)) {
        logger.info("Update Work Assignment with DISPATCHED disposition"
            + logSuffix);
      }

      /*
       * Save updated work assignment xml to Work Assignment Service.
       */

      workAssignment = workAssignmentHandler.saveUpdateWorkAssignment(
          updatedWorkAssignmentDocument, loggedInUserInfoDocument);

      final Long documentId = workAssignment.getReferenceId();
      msgDetail.append("\tDocumentID : ").append(documentId);

      /*
       * Call CARRHelper Service to update review assignment for
       * supplement assignment & dispatch request.
       */
      workAsmtType = workAssignmentDocument.getWorkAssignment().getType();
      if (logger.isLoggable(Level.INFO)) {
        logger.info("workAsmtType for ASSIGNMENT TYPE ====>" + workAsmtType
            + logSuffix);
      }
      if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
          .equalsIgnoreCase(workAsmtType)) {
        /*
         * Retrieve latest MitchellEnvelope Document using
         * EstimatePackage Service.
         */

        final com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO appraisalAssignmentDTO = estimatePackageProxy
            .getAppraisalAssignmentDocument(documentId.longValue());

        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("Fetched AppraisalAssignmentDTO from getAppraisalAssignmentDocument method of EstimatePackage Service"
                  + logSuffix);
        }

        if (appraisalAssignmentDTO == null) {
          throw new MitchellException(
              CLASS_NAME,
              METHOD_NAME,
              "Received NULL AppraisalAssignmentDTO object From EstimatePackage Service. Document ID : "
                  + documentId);
        }

        final String mitchellEnvelopeStr = appraisalAssignmentDTO
            .getAppraisalAssignmentMEStr();
        if (mitchellEnvelopeStr != null && !mitchellEnvelopeStr.equals("")) {
          mitchellEnvelopeDoc = MitchellEnvelopeDocument.Factory
              .parse(mitchellEnvelopeStr);
          mitchellEnvelopeHelper = new MitchellEnvelopeHelper(
              mitchellEnvelopeDoc);
          if (logger.isLoggable(Level.INFO)) {
            logger.info("meHelper created" + logSuffix);
          }
        }

        relatedEstimateDocumentId = mitchellEnvelopeHandler
            .getEstimateDocId(mitchellEnvelopeHelper);
        final UserInfoDocument estimatorUserInfo = userInfoUtils
            .getEstimatorInfo(relatedEstimateDocumentId);
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("Calling CARRHelper Service to update review assignment for supplement assignment & dispatch request "
                  + logSuffix);
        }
        updateReviewAssignment(relatedEstimateDocumentId, estimatorUserInfo,
            loggedInUserInfoDocument);
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("Supplement Assignment has been successfully updated for review by CARR Service. Method Name is :: dispatchAppraisalAssignment"
                  + logSuffix);
        }
      }

      final PersonInfoType pT = updatedWorkAssignmentDocument
          .getWorkAssignment().getCurrentSchedule().getAssignee();

      String assigneeFirstName = "";
      String assigneeLastName = "";

      if (pT != null && pT.getPersonName() != null
          && pT.getPersonName().getFirstName() != null) {
        assigneeFirstName = pT.getPersonName().getFirstName();
      }
      if (pT != null && pT.getPersonName() != null
          && pT.getPersonName().getLastName() != null) {
        assigneeLastName = pT.getPersonName().getLastName();
      }

      /*
       * Claim-Suffix Activity Log
       */

      if (AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_TYPE
          .equalsIgnoreCase(workAssignmentDocument.getWorkAssignment()
              .getType())) {

        commonUtils.saveExposureActivityLog(
            // "The Appraisal Assignment Dispatched Successfully",
            appraisalAssignmentConfig.getDispatchAAActivityLog() + " "
                + ((assigneeFirstName != null) ? assigneeFirstName : "") + " "
                + ((assigneeLastName != null) ? assigneeLastName : "")
                + " - SUPPLEMENT", claimExposureId, referenceId, workItemID,
            loggedInUserInfoDocument);
      } else {

        commonUtils.saveExposureActivityLog(
            // "The Appraisal Assignment Dispatched Successfully",
            appraisalAssignmentConfig.getDispatchAAActivityLog() + " "
                + assigneeFirstName + " " + assigneeLastName + " - ORIGINAL",
            claimExposureId, referenceId, workItemID, loggedInUserInfoDocument);
      }

      // eventName will come from CONSTANT File and eventDesc will be
      // fetched from SET file
      eventName = AppraisalAssignmentConstants.DB_DISPATCH_AA_ASSIGNMENT_ACTIVITYLOG;
      eventDesc = appraisalAssignmentConfig
          .getDispatchAAAssignmentActivityLog();
      this.writeAssignmentActivityLog(workAssignment.getId().longValue(),
          eventName, eventDesc, loggedInUserInfoDocument.getUserInfo()
              .getUserID());

      appLogUtil.appLog(
          AppraisalAssignmentConstants.APPLOG_SAVE_AND_SEND_AA_SUCCESS,
          workAssignment, loggedInUserInfoDocument, null, startTime);

      isAsmtDispatchedSuccessfully = AppraisalAssignmentConstants.SUCCESS;

      // PBI 270344 - Reset Schedule Method to MANUAL for dispatchAppraisalAssignment use case
      //
      updateScheduleMethodManual(workAssignment, workItemID, claimNumber,
          loggedInUserInfoDocument, METHOD_NAME);

      if (logger.isLoggable(Level.INFO)) {
        logger.info(">>>> dispatchAppraisalAssignment::SCHEDULE_METHOD "
            + "After updateAppraisalAssignmentScheduleMethod: ReferenceId= [ "
            + workAssignment.getReferenceId() + " ]");
      }

    } catch (final WorkAssignmentException workAssignmentException) {

      if (workAssignmentException.getCause() != null
          && workAssignmentException.getCause() instanceof com.mitchell.services.core.workassignment.dao.StaleObjectStateException) {
        logger
            .warning("StaleObjectStateException from WorkAssignmentService for workAssignmentTaskID:"
                + workAssignmentTaskID);
        return AppraisalAssignmentConstants.STALE_DATA;
      } else {
        final int errorCode = AppraisalAssignmentConstants.ERROR_DISPATCHING_AA;
        final String errorDescription = AppraisalAssignmentConstants.ERROR_DISPATCHING_AA_MSG;
        logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
            errorDescription, workItemID, claimNumber, msgDetail.toString(),
            workAssignmentException, loggedInUserInfoDocument);
      }
    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_DISPATCHING_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_DISPATCHING_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isAsmtDispatchedSuccessfully;
  }

  /**
   * Updates the Disposition in WorkAssignment and performs Claim Activity &
   * Assignment Activity Logging
   * 
   * @param workAssignmentTaskID
   * @param newDispositionCode
   *          Supports IN_PROGRESS, REJECTED, CANCELLED, COMPLETED
   * @param reasonCode
   * @param comment
   * @param requestTCN
   * @param loggedInUserInfoDocument
   * @return int value would be zero for SUCCESS, ONE for FAILURE, TWO for
   *         STALE DATA
   * @throws Exception
   *           Throws Exception to the caller in case of any exception
   *           arise.
   */
  public int assignmentStatusUpdate(final long workAssignmentTaskID,
      final String newDispositionCode, final String reasonCode,
      final String reasonComment, long requestTCN,
      final UserInfoDocument loggedInUserInfoDocument,ItineraryViewDocument itineraryXML)
      throws MitchellException
  {

		final String METHOD_NAME = "assignmentStatusUpdate";
		logger.entering(CLASS_NAME, METHOD_NAME);
		long startTime = System.currentTimeMillis();
		StringBuffer msgDetail = null;
		final StringBuffer localMethodParams = new StringBuffer();
		final StringBuffer logSuffix = new StringBuffer();
		localMethodParams.append("WorkAssignmentTaskID : ").append(workAssignmentTaskID).append("\tnewDispositionCode : ")
				.append(newDispositionCode).append("\treasonCode : ").append(reasonCode).append("\tcomment : ")
				.append(reasonComment).append("\tTCN : ").append(requestTCN).append("\nLogged In UserInfoDocument : ")
				.append(loggedInUserInfoDocument.toString());

		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Input Received ::\n" + localMethodParams);
		}
		msgDetail = new StringBuffer("Method Parameters : ").append(localMethodParams);
		WorkAssignment workAssignment = null;

		Long claimExposureId = null;
		Long referenceId = null;
		String eventName = null;
		String eventDesc = null;
		String activityLogging = null;
		final String claimNumber = null;
		final String workItemID = null;

		int isUpdateDispositionSuccessful = AppraisalAssignmentConstants.FAILURE;
		int isRescheduleOrIncompleteDispositionSuccessful = AppraisalAssignmentConstants.FAILURE;

		try {

			// Retrieve work assignment details using taskID from Work
			// Assignment
			workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(workAssignmentTaskID, loggedInUserInfoDocument);
			logger.info("Retrieved Work Assignment through getWorkAssignmentByTaskID method of Work Assignment Service \n"
					+ workAssignment);

			if (AppraisalAssignmentConstants.WA_STATUS_CLOSED.equalsIgnoreCase(workAssignment.getWorkAssignmentStatus())
					|| AppraisalAssignmentConstants.WA_STATUS_CANCELLED.equalsIgnoreCase(workAssignment
							.getWorkAssignmentStatus())) {
				return AppraisalAssignmentConstants.CANCELED_CLOSED_TASK;
			}

			StringBuffer comment = new StringBuffer();
			// ** NEW for Story #118818 - Always add event comment with
			// newDispositionCode
			if (AppraisalAssignmentConstants.DISPOSITION_IN_PROGRESS.equals(newDispositionCode)) {
				comment.append(AppraisalAssignmentConstants.STATUS_NOTES_DISPOSITION_IN_PROGRESS);
			} else {
				comment.append(newDispositionCode);
			}
			if (reasonComment != null && !"".equalsIgnoreCase(reasonComment.trim())) {

				comment.append(AppraisalAssignmentConstants.STATUS_NOTES_SEPERATOR).append(reasonComment);
			} else {
				comment.append(AppraisalAssignmentConstants.STATUS_NOTES_SEPERATOR);
			}
			// ** NEW for Story #118818 - Add comment by LoggedInUserID and
			// date/timestamp
			final Calendar cal = Calendar.getInstance();
			final SimpleDateFormat dateFmtString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dt = new Date();
			dt = cal.getTime();
			final String dateStr = dateFmtString.format(dt);

			String loggedinUserID = "";
			if (loggedInUserInfoDocument != null) {
				loggedinUserID = loggedInUserInfoDocument.getUserInfo().getUserID().toString();
			}
			comment.append("\n  by ").append(loggedinUserID).append(" - ").append(dateStr);

			if (AppraisalAssignmentConstants.DISPOSITION_RESCHEDULE.equalsIgnoreCase(newDispositionCode)
					|| AppraisalAssignmentConstants.DISPOSITION_INCOMPLETE.equalsIgnoreCase(newDispositionCode)) {
				isRescheduleOrIncompleteDispositionSuccessful = rescheduleOrIncompleteAssignment(workAssignmentTaskID,
					requestTCN, reasonCode, comment.toString(), loggedInUserInfoDocument, newDispositionCode);

				// Check STALE DATA
				if (isRescheduleOrIncompleteDispositionSuccessful == AppraisalAssignmentConstants.STALE_DATA) {
					return AppraisalAssignmentConstants.STALE_DATA;
				}
				// increment TCN for next WA get/save
				// requestTCN = requestTCN + 1;
				
				logger.info("calling unScheduleAssignment");
				isUpdateDispositionSuccessful = unScheduleAssignment(workAssignmentTaskID, requestTCN, reasonCode,
					comment.toString(), loggedInUserInfoDocument, itineraryXML);
			} else {

				// Check STALE DATA
				if (checkStaleData(workAssignment.getTcn(), requestTCN)) {
					return AppraisalAssignmentConstants.STALE_DATA;
				}

				claimExposureId = workAssignment.getClaimExposureID();
				referenceId = workAssignment.getReferenceId();
				msgDetail.append("\tDocumentID/ReferenceID : ").append(referenceId);

				if (!((AppraisalAssignmentConstants.DISPOSITION_IN_PROGRESS.equals(newDispositionCode))
						|| (AppraisalAssignmentConstants.DISPOSITION_REJECTED.equals(newDispositionCode))
						|| (AppraisalAssignmentConstants.DISPOSITION_COMPLETED.equals(newDispositionCode)) || (AppraisalAssignmentConstants.DISPOSITION_CANCELLED
							.equals(newDispositionCode)))) {
					throw new MitchellException(CLASS_NAME, METHOD_NAME, "This Disposition is not Supported : "
							+ localMethodParams);
				}

				// ** NEW for Story #118818 - set NewContentAdded for event
				// comments
				int newContentAddedForMemoFlag = 1;
				workAssignment.setNewContentAdded(Integer.valueOf(newContentAddedForMemoFlag));

				workAssignment = workAssignmentHandler.saveWorkAssignmentStatus(workAssignment, newDispositionCode,
					reasonCode, comment.toString(), loggedInUserInfoDocument, itineraryXML);
				// Add New
				// ClaimActivity Logging
				if (AppraisalAssignmentConstants.DISPOSITION_IN_PROGRESS.equals(newDispositionCode)) {
					activityLogging = appraisalAssignmentConfig.getInProgressAAActivityLog();
					commonUtils.saveExposureActivityLog(activityLogging, claimExposureId, referenceId, workItemID,
						loggedInUserInfoDocument);

					eventName = AppraisalAssignmentConstants.RECEIVED_AA_ASSIGNMENT_ACTIVITYLOG;
					eventDesc = appraisalAssignmentConfig.getReceivedAAAssignmentActivityLog();
				} else if (AppraisalAssignmentConstants.DISPOSITION_REJECTED.equals(newDispositionCode)) {
					activityLogging = appraisalAssignmentConfig.getRejectedAAActivityLog();
					commonUtils.saveExposureActivityLog(activityLogging, claimExposureId, referenceId, workItemID,
						loggedInUserInfoDocument);

					eventName = AppraisalAssignmentConstants.REJECTED_AA_ASSIGNMENT_ACTIVITYLOG;
					eventDesc = appraisalAssignmentConfig.getRejectedAAAssignmentActivityLog();
				} else if (AppraisalAssignmentConstants.DISPOSITION_COMPLETED.equals(newDispositionCode)) {
					activityLogging = appraisalAssignmentConfig.getCompleteAAActivityLog();
					commonUtils.saveExposureActivityLog(activityLogging, claimExposureId, referenceId, workItemID,
						loggedInUserInfoDocument);
					eventName = AppraisalAssignmentConstants.CLOSED_AA_ASSIGNMENT_ACTIVITYLOG;
					eventDesc = appraisalAssignmentConfig.getClosedAAAssignmentActivityLog();
				} else if (AppraisalAssignmentConstants.DISPOSITION_CANCELLED.equals(newDispositionCode)) {
					activityLogging = appraisalAssignmentConfig.getCancelAAActivityLog();
					commonUtils.saveExposureActivityLog(activityLogging, claimExposureId, referenceId, workItemID,
						loggedInUserInfoDocument);
					eventName = AppraisalAssignmentConstants.CANCEL_AA_ASSIGNMENT_ACTIVITYLOG;
					eventDesc = appraisalAssignmentConfig.getCancelAAAssignmentActivityLog();
				}

				// Assignment Activity Logging

				this.writeAssignmentActivityLog(workAssignment.getId().longValue(), eventName, eventDesc,
					loggedInUserInfoDocument.getUserInfo().getUserID());

				// Retrieving the LoggedInUserInfoDocument from UserInfoService
				// for AppLogging for PAUH.
				// SCR 0032594: NO Event publishing event for rejected WCA
				// assignment for SWE workflow.
				final UserInfoDocument appLoggingUserInfoDocument = userInfoUtils.retrieveUserInfo(loggedInUserInfoDocument
						.getUserInfo().getOrgCode(), loggedInUserInfoDocument.getUserInfo().getUserID());

				if (logger.isLoggable(Level.FINE)) {
					logger.fine("AppLogging UserInfoDocument " + appLoggingUserInfoDocument);
				}

				appLogUtil.appLog(AppraisalAssignmentConstants.APPLOG_UPDATE_DISPOSITION_ASSIGNMENT_SUCCESS, workAssignment,
					appLoggingUserInfoDocument, null, startTime);
			}

			if (itineraryXML != null) {
				this.mitchellEnvelopeHandler.unScheduleMitchellEnvelope(workAssignment.getReferenceId(),
					loggedInUserInfoDocument, itineraryXML);
			}

			isUpdateDispositionSuccessful = AppraisalAssignmentConstants.SUCCESS;

		} catch (final Exception exception) {
			final int errorCode = AppraisalAssignmentConstants.ERROR_STATUS_UPDATE_AA;
			final String errorDescription = AppraisalAssignmentConstants.ERROR_STATUS_UPDATE_AA_MSG;
			logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode, errorDescription, workItemID, claimNumber,
				msgDetail.toString(), exception, loggedInUserInfoDocument);
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isUpdateDispositionSuccessful;
	
	}
  
  
  /**
   * Updates the Disposition in WorkAssignment and performs Claim Activity &
   * Assignment Activity Logging
   * 
   * @param workAssignmentTaskID
   * @param newDispositionCode
   *          Supports IN_PROGRESS, REJECTED, CANCELLED, COMPLETED
   * @param reasonCode
   * @param comment
   * @param requestTCN
   * @param loggedInUserInfoDocument
   * @return int value would be zero for SUCCESS, ONE for FAILURE, TWO for
   *         STALE DATA
   * @throws Exception
   *           Throws Exception to the caller in case of any exception
   *           arise.
   */
  public int assignmentStatusUpdateItineraryView(ItineraryViewDocument itineraryXML,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
 {

		final long workAssignmentTaskID = itineraryXML.getItineraryView().getWorkAssignmentTaskID();
		final String newDispositionCode = itineraryXML.getItineraryView().getDisposition();
		final String reasonCode = itineraryXML.getItineraryView().getReasonCode();
		final String reasonComment = itineraryXML.getItineraryView().getNotes();
		long requestTCN = itineraryXML.getItineraryView().getTCN();

		final String METHOD_NAME = "assignmentStatusUpdateItineraryView";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isUpdateDispositionSuccessful = assignmentStatusUpdate(workAssignmentTaskID, newDispositionCode, reasonCode,
			reasonComment, requestTCN, loggedInUserInfoDocument,itineraryXML);
		
		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isUpdateDispositionSuccessful;
	}
  

  /**
   * Updates the Disposition in WorkAssignment without any Logging.
   * 
   * @param workAssignmentTaskID
   * @param newDispositionCode
   * @param reasonCode
   * @param comment
   * @param requestTCN
   * @param loggedInUserInfoDocument
   * @return int value would be zero for SUCCESS, ONE for FAILURE, TWO for
   *         STALE DATA
   * @throws Exception
   *           Throws Exception to the caller in case of any exception
   *           arise.
   */
  public int workAssignmentStatusUpdate(final long workAssignmentTaskID,
      final String newDispositionCode, final String reasonCode,
      final String comment, final long requestTCN,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "workAssignmentStatusUpdate";
    logger.entering(CLASS_NAME, METHOD_NAME);
    final StringBuffer logSuffix = new StringBuffer();

    if (logger.isLoggable(Level.FINE)) {
      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("WorkAssignmentTaskID : ")
          .append(workAssignmentTaskID).append("\tnewDispositionCode : ")
          .append(newDispositionCode).append("\treasonCode : ")
          .append(reasonCode).append("\tcomment : ").append(comment)
          .append("\tTCN : ").append(requestTCN)
          .append("\nLogged In UserInfoDocument : ")
          .append(loggedInUserInfoDocument.toString());
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    WorkAssignment workAssignment = null;
    Long referenceId = null;
    final String claimNumber = null;
    final String workItemID = null;
    int isUpdateDispositionSuccessful = AppraisalAssignmentConstants.FAILURE;

    try {

      logSuffix.append(workAssignmentTaskID);

      // Retrieve work assignment details using taskID from Work
      // Assignment
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);
      logger
          .info("Retrieved Work Assignment through getWorkAssignmentByTaskID method of Work Assignment Service");

      // Check STALE DATA
      if (checkStaleData(workAssignment.getTcn(), requestTCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }

      workAssignment.getClaimExposureID();
      referenceId = workAssignment.getReferenceId();

      workAssignmentHandler.saveWorkAssignmentStatus(workAssignment,
          newDispositionCode, reasonCode, comment, loggedInUserInfoDocument,null);
      isUpdateDispositionSuccessful = AppraisalAssignmentConstants.SUCCESS;

    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_STATUS_UPDATE_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_STATUS_UPDATE_AA_MSG;

      final StringBuffer localMethodParams = new StringBuffer();
      localMethodParams.append("WorkAssignmentTaskID : ")
          .append(workAssignmentTaskID).append("\tnewDispositionCode : ")
          .append(newDispositionCode).append("\treasonCode : ")
          .append(reasonCode).append("\tcomment : ").append(comment)
          .append("\tTCN : ").append(requestTCN)
          .append("\nLogged In UserInfoDocument : ")
          .append(loggedInUserInfoDocument.toString());

      StringBuffer msgDetail = new StringBuffer("Method Parameters :: ")
          .append(localMethodParams);
      msgDetail.append("\tDocumentID/ReferenceID : ").append(referenceId);

      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isUpdateDispositionSuccessful;
  }

  /**
   * This method cancel a request of Supplement Assigment for a cartain
   * claim-suffix number
   * 
   * @param claimSuffixNumber
   *          it contains calim_number-suffix-number
   * @param reviewerUserInfo
   *          it contains UserInfoDocument object with DRP user info
   * 
   * @throws MitchellException
   * 
   */
  public void cancelSupplementTask(final String claimSuffixNumber,
      final UserInfoDocument bodyShopUserInfo, final String note,
      final String reviewCoCd, final String reviewUserId)
      throws MitchellException
  {
    try {
      final String beanName = "AssignmentTaskHandler";
      final AssignmentTaskHandler handler = (AssignmentTaskHandler) BeanLocator
          .getBean(beanName);
      handler.cancelSupplementTask(claimSuffixNumber, bodyShopUserInfo, note,
          reviewCoCd, reviewUserId);
    } catch (final MitchellException e) {
      if (e.getType() <= 0) {
        e.setType(AppraisalAssignmentConstants.ERROR_CANCEL_TASK_REQ_SUPP);
      }
      throw e;
    } catch (final Throwable t) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_CANCEL_TASK_REQ_SUPP,
          "AppraisalAssignmentService", "cancelSupplementTask",
          "Exception in cancelling supplement task:" + t.getMessage(), t);
    }
  }

  /**
   * This method creates a request of Supplement Assignment on a certain
   * claim-suffix number
   * 
   * 
   * @param claimSuffixNumber
   *          calim_number-suffix-number
   * @param reviewerUserInfo
   *          UserInfoDocument object with DRP user info
   * @param workItemId
   *          work item id (PAUH has the info)
   * 
   * @throws MitchellException
   * 
   */
  public void createSupplementTask(final String claimSuffixNumber,
      final UserInfoDocument bodyShopUserInfo, final String workItemId,
      final String note, final String reviewCoCd, final String reviewUserId)
      throws MitchellException
  {

    try {
      final String beanName = "AssignmentTaskHandler";
      final AssignmentTaskHandler handler = (AssignmentTaskHandler) BeanLocator
          .getBean(beanName);
      handler.createSupplementTask(claimSuffixNumber, bodyShopUserInfo,
          workItemId, note, reviewCoCd, reviewUserId);
    } catch (final MitchellException e) {
      if (e.getType() <= 0) {
        e.setType(AppraisalAssignmentConstants.ERROR_CREATE_TASK_REQ_SUPP);
      }
      throw e;
    } catch (final Throwable t) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_CREATE_TASK_REQ_SUPP,
          "AppraisalAssignmentService", "createSupplementTask",
          "Exception in creating supplement task:" + t.getMessage(), t);
    }
  }

  /**
   * This method creates a request of Supplement Assignment on a certain
   * claim-suffix number
   * 
   * 
   * @param claimSuffixNumber
   *          calim_number-suffix-number
   * @param reviewerUserInfo
   *          UserInfoDocument object with DRP user info
   * @param workItemId
   *          work item id (PAUH has the info)
   * 
   * @throws MitchellException
   * 
   */
  public void createAssignSupplementTaskToNCRTUSer(
      final String claimSuffixNumber, final UserInfoDocument bodyShopUserInfo,
      final String workItemId, final String note, final String reviewCoCd,
      final String reviewUserId)
      throws MitchellException
  {

    try {
      final String beanName = "AssignmentTaskHandler";
      final AssignmentTaskHandler handler = (AssignmentTaskHandler) BeanLocator
          .getBean(beanName);
      handler.createAssignSupplementTaskToNCRTUSer(claimSuffixNumber,
          bodyShopUserInfo, workItemId, note, reviewCoCd, reviewUserId);
    } catch (final MitchellException e) {
      if (e.getType() <= 0) {
        e.setType(AppraisalAssignmentConstants.ERROR_CREATE_TASK_REQ_SUPP);
      }
      throw e;
    } catch (final Throwable t) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_CREATE_TASK_REQ_SUPP,
          "AppraisalAssignmentService", "createAssignSupplementTaskToNCRTUSer",
          "Exception in creating supplement task:" + t.getMessage(), t);
    }
  }

  /**
   * This method reject the request of Suplement Assignment
   * 
   * @param taskID
   *          task ID of the request.
   * @param estimatorUserInfo
   *          the WC user
   * 
   * @exception MitchellException
   */
  public void rejectSupplementTask(final long taskID,
      final UserInfoDocument estimatorUserInfo)
      throws MitchellException
  {
    try {
      final String beanName = "AssignmentTaskHandler";
      final AssignmentTaskHandler handler = (AssignmentTaskHandler) BeanLocator
          .getBean(beanName);
      handler.rejectSupplementTask(taskID, estimatorUserInfo);
    } catch (final MitchellException e) {
      if (e.getType() <= 0) {
        e.setType(AppraisalAssignmentConstants.ERROR_REJECT_TASK_REQ_SUPP);
      }
      throw e;
    } catch (final Throwable t) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_REJECT_TASK_REQ_SUPP,
          "AppraisalAssignmentService", "rejectSupplementTask",
          "Exception in rejecting supplement task:" + t.getMessage(), t);
    }
  }

  public void addVehLctnTrackingHist(final Long claimSuffixID,
      final String vehicleTrackingStatus, final String companyCode,
      final String reviewerId)
      throws MitchellException
  {
    final String METHOD_NAME = "addVehLctnTrackingHist";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();

    StringBuffer msgDetail = null;

    UserInfoDocument reviewerUserInfoDocument = null;
    final StringBuffer localMethodParams = new StringBuffer();

    final String claimNumber = null;
    final String workItemID = null;

    localMethodParams.append("Input Received ::\n")
        .append("vehicleTrackingStatus: ").append(vehicleTrackingStatus)
        .append("\n claimSuffixID: ").append(claimSuffixID)
        .append("\ncompanyCode: ").append(companyCode).append("\reviewerId: ")
        .append(reviewerId);

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    msgDetail = new StringBuffer("Method Parameters :: ")
        .append(localMethodParams);

    try {

      reviewerUserInfoDocument = userInfoUtils.retrieveUserInfo(companyCode,
          reviewerId);

      claimProxy.addVehLctnTrckngHist(claimSuffixID, vehicleTrackingStatus,
          reviewerUserInfoDocument);

      appLogUtil.appLogUpdate(
          AppraisalAssignmentConstants.APPLOG_VECHILE_UPDATE_SUCCESS,
          claimSuffixID.longValue(), vehicleTrackingStatus,
          reviewerUserInfoDocument, startTime);

      if (logger.isLoggable(Level.INFO)) {
        logger.info("App logging done for addVehLctnTrackingHist");
      }
    }

    catch (final ClaimException claimException) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_CLAIM_SERVICE;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_CLAIM_SERVICE_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          claimException, reviewerUserInfoDocument);
    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_VEHICLE_TRACKING_UPDATE;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_VEHICLE_TRACKING_UPDATE_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, reviewerUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);
  }

  private AppraisalAssignmentDTO populateDTOWithWAHoldInfo(
      final AppraisalAssignmentDTO appraisalAssignmentDTO,
      final HoldInfoType waHoldInfoType)
  {
    final String METHOD_NAME = "populateDTOWithWAHoldInfo";
    logger.entering(CLASS_NAME, METHOD_NAME);

    AppraisalAssignmentDTO retAppraisalAssignmentDTO = appraisalAssignmentDTO;

    HoldInfo holdInfo = new HoldInfo();
    holdInfo.setOnHold(waHoldInfoType.getHoldInd());
    if (waHoldInfoType.getHoldReasonCode() != null)
      holdInfo.setOnHoldReasonCode(waHoldInfoType.getHoldReasonCode());
    if (waHoldInfoType.getHoldReasonNotes() != null)
      holdInfo.setOnHoldReasonNotes(waHoldInfoType.getHoldReasonNotes());
    holdInfo.setOnHoldUpdatedBy(waHoldInfoType.getHoldUpdatedBy());
    holdInfo.setOnHoldUpdatedByDateTime(waHoldInfoType
        .getHoldUpdatedByDateTime());

    retAppraisalAssignmentDTO.setHoldInfo(holdInfo);

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return retAppraisalAssignmentDTO;
  }

  /*
   * This method is responsible for determining if the mandatory fields are set
   * and valid vehicle location address is present
   */
  public boolean isAssignmentDataReadyForSSO(
      final MitchellEnvelopeDocument mitchellEnvDoc)
      throws MitchellException
  {

    final String METHOD_NAME = "isAssignmentDataReadyForSSO";
    logger.entering(CLASS_NAME, METHOD_NAME);
    boolean isAssignmentDataReady = false;

    try {

      MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
          mitchellEnvDoc);
      AdditionalAppraisalAssignmentInfoDocument additionalAAInfoDoc = appraisalAssignmentMandFieldUtils
          .getAdditionalAppraisalAssignmentInfoDocumentFromME(meHelper);
      
      final String companyCode = meHelper
    	        .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_COMPANY_CODE);
      //final String validAddress = appraisalAssignmentMandFieldUtils
      //.getValidVehicleLocationAddress(mitchellEnvDoc);
      final String validAddress = appraisalAssignmentMandFieldUtils
          .getValidVehicleLocationAddress(additionalAAInfoDoc, companyCode);
      
      if ("Yes".equalsIgnoreCase(validAddress)) {
        isAssignmentDataReady = true;
      }
    } catch (final Exception e) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS,
          CLASS_NAME, METHOD_NAME,
          AppraisalAssignmentConstants.ERROR_VALIDATING_AA_MANDATORY_FIELDS_MSG
              + e.getMessage(), e);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);
    return isAssignmentDataReady;
  }

  /*
   * This method is responsible for building the Claim Activity Log message when
   * the assignment is dispatched.
   */
  private String getDispatchClaimActivityLogMessage(
      final WorkAssignment workAssignment)
      throws Exception
  {
    final String METHOD_NAME = "getDispatchClaimActivityLogMessage";
    logger.entering(CLASS_NAME, METHOD_NAME);
    final WorkAssignmentDocument updatedWorkAssignmentDocument = WorkAssignmentDocument.Factory
        .parse(workAssignment.getWorkAssignmentCLOBB());
    final PersonInfoType pT = updatedWorkAssignmentDocument.getWorkAssignment()
        .getCurrentSchedule().getAssignee();

    String assigneeFirstName = "";
    String assigneeLastName = "";

    if (pT != null && pT.getPersonName() != null
        && pT.getPersonName().getFirstName() != null) {
      assigneeFirstName = pT.getPersonName().getFirstName();
    }

    if (pT != null && pT.getPersonName() != null
        && pT.getPersonName().getLastName() != null) {
      assigneeLastName = pT.getPersonName().getLastName();
    }
    final StringBuffer claimActivityLogBuffer = new StringBuffer(
        appraisalAssignmentConfig.getDispatchAAActivityLog());
    claimActivityLogBuffer.append(" ").append(assigneeFirstName).append(" ")
        .append(assigneeLastName).append(" - ORIGINAL");
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return claimActivityLogBuffer.toString();
  }

  /*
   * This method is responsible for determining the assignment is ready for
   * DISPATCHING or not
   */
  public String isAssignmentReadyForDispatchForSSO(
      final boolean mandatoryFieldFlag, final String assigneeID,
      final String groupCode, final java.util.Calendar scheduleDateTime,
      final UserInfoDocument userInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "isAssignmentReadyForDispatchForSSO";
    logger.entering(CLASS_NAME, METHOD_NAME);
    final StringBuffer localMethodParams = new StringBuffer();
    StringBuffer msgDetail = null;

    localMethodParams.append("MandatoryFieldFlag : ")
        .append(mandatoryFieldFlag).append("\tassigneeID : ")
        .append(assigneeID).append("\ngroupCode : ").append(groupCode)
        .append("\nscheduleDateTime : ").append(scheduleDateTime)
        .append("\nUserInfoDocument : ").append(userInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    String scheduleRequiredflag = "N";
    String disposition = AppraisalAssignmentConstants.DISPOSITION_NOT_READY;
    final String claimNumber = null;
    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    final String workItemID = null;
    String reqAssigneeType = null;
    try {
      if (userInfoDocument != null && userInfoDocument.getUserInfo() != null) {
        final String orgCoCode = userInfoDocument.getUserInfo().getOrgCode();
        if (logger.isLoggable(Level.INFO)) {
          logger.info("Fetched orgCoCode from User Info: " + orgCoCode);
        }
        String workGroupType = null;

        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "Start Calling userInfoUtils.getUserType()");

        reqAssigneeType = userInfoUtils.getUserType(orgCoCode, assigneeID);

        MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
            "End Calling userInfoUtils.getUserType()");

        if (logger.isLoggable(Level.INFO)) {
          logger.info("Fetched reqAssigneeType from : " + reqAssigneeType);
        }
        if (groupCode != null) {
          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "Start Calling appraisalAssignmentDAO.getWorkGroupType()"
                  + claimNumber);

          workGroupType = appraisalAssignmentDAO.getWorkGroupType(orgCoCode,
              groupCode);

          MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
              "End Calling appraisalAssignmentDAO.getWorkGroupType()"
                  + claimNumber);
          if (logger.isLoggable(Level.INFO)) {
            logger.info("Fetched workGroup from : " + workGroupType);
          }
        }
        if (AppraisalAssignmentConstants.SERVICE_CENTER.equals(workGroupType)) {
          if (assigneeID != null && mandatoryFieldFlag) {
            disposition = AppraisalAssignmentConstants.DISPOSITION_READY;
          }
        } else {
          if (UserTypeConstants.STAFF_TYPE.equalsIgnoreCase(reqAssigneeType)) {

            scheduleRequiredflag = "Y";
            
						String scheduleDateFlag = customSettingProxy
								.getCompanyCustomSettings(
										orgCoCode,
										AppraisalAssignmentConstants.CSET_GROUP_NAME_WCA_REQD_FLDS,
										AppraisalAssignmentConstants.CSET_SETTING_WCA_SCHED_REQD_STAFF);
            scheduleRequiredflag = AppraisalAssignmentConstants.STATUS_Y.equalsIgnoreCase(scheduleDateFlag) ? AppraisalAssignmentConstants.STATUS_Y : AppraisalAssignmentConstants.STATUS_N;
            
            if (logger.isLoggable(Level.INFO)) {
              logger.info("Fetched ScheduleFlagForStaff : "
                  + scheduleRequiredflag);
            }
          }
          if ("Y".equalsIgnoreCase(scheduleRequiredflag)) {     	  
            if (assigneeID != null && scheduleDateTime != null
                && mandatoryFieldFlag) {
              disposition = AppraisalAssignmentConstants.DISPOSITION_READY;
            }

          } else {
            if (assigneeID != null && mandatoryFieldFlag) {
              disposition = AppraisalAssignmentConstants.DISPOSITION_READY;
            }
          }
        }

      }
    } catch (final Exception exception) {
      final int errorCode = AppraisalAssignmentConstants.ERROR_ASSIGNMENT_READYFORDISPATCH;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_ASSIGNMENT_READYFORDISPATCH_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(),
          exception, userInfoDocument);
    }
    if (logger.isLoggable(Level.INFO)) {
      logger.info("Disposition from isAssignmentReadyForDispatchForSSO : "
          + disposition);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return disposition;
  }

  /**
   * This method for assigns the Assignment to a particular Dispatch Center
   * 
   * @param workAssignmentTaskID
   * @param dispatchCenter
   * @param TCN
   * @param loggedInUserInfoDocument
   * @return int
   * @exception MitchellException
   */
  public int assignedToDispatchCenter(final long workAssignmentTaskID,
      final String dispatchCenter,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "assignedToDispatchCenter";
    logger.entering(CLASS_NAME, METHOD_NAME);
    final StringBuffer msgDetail = new StringBuffer("Method Parameters : ");
    msgDetail.append("WorkAssignmentTaskID :").append(workAssignmentTaskID)
        .append(" dispatchCenter : ").append(dispatchCenter)
        .append(" Logged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());
    MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
        "Start calling AAS.assignedToDispatchCenter for taskID:"
            + workAssignmentTaskID);
    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received :: " + msgDetail);
    }

    int isAsmtAssignToDispCenterSuccessfully = AppraisalAssignmentConstants.FAILURE;
    String claimNumber = null;
    String workItemID = null;

    try {
      //Fetch the work assignment from WorkAssignmentService by TaskID
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start calling workAssignmentHandler.getWorkAssignmentByTaskId");
      final WorkAssignment workAssignment = workAssignmentHandler
          .getWorkAssignmentByTaskId(workAssignmentTaskID,
              loggedInUserInfoDocument);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End calling workAssignmentHandler.getWorkAssignmentByTaskId");
      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      final WorkAssignmentDocument workAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(XmlW);
      if (logger.isLoggable(Level.FINE)) {
        logger
            .fine("Retrieved Work Assignment Document from WorkAssignmentService"
                + workAssignmentDocument);
      }

      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();
      msgDetail.append(" ClaimNumber : ").append(claimNumber);
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();
      msgDetail.append(" WorkItemID : ").append(workItemID);

      //Update the WorkAssignmentDocument with Dispatch center in WorkAssignmentService
      final WorkAssignment updatedWorkAssignment = workAssignmentHandler
          .updateGroupIdInWorkAssignment(dispatchCenter,
              loggedInUserInfoDocument, workAssignmentDocument);

      final Long claimExposureId = updatedWorkAssignment.getClaimExposureID();
      msgDetail.append(" ClaimExposureID : ").append(claimExposureId);
      final Long documentId = updatedWorkAssignment.getReferenceId();
      msgDetail.append(" DocumentID/ReferenceID : ").append(documentId);

      //Update the MEDocument with Dispatch center in EstimatePackage Service
      mitchellEnvelopeHandler.updateGroupIdInMitchellEnvelope(documentId,
          workItemID, claimNumber, dispatchCenter, loggedInUserInfoDocument);

      // Do Claim-Suffix Activity Log For Dispatch Center Change
      commonUtils.saveExposureActivityLog(
          appraisalAssignmentConfig.getAssignToDispatchCenterActivityLog(),
          claimExposureId, documentId, workItemID, loggedInUserInfoDocument);

      // TODO## App Log/Event publishing For Dispatch Center Change

    } catch (final MitchellException mitchellException) {
      throw new MitchellException(CLASS_NAME, METHOD_NAME,
          "MitchellException occured updating the dispatch center",
          mitchellException);
    } catch (final Exception exception) {
      throw new MitchellException(CLASS_NAME, METHOD_NAME,
          "Exception occured updating the dispatch center", exception);
    }

    isAsmtAssignToDispCenterSuccessfully = AppraisalAssignmentConstants.SUCCESS;
    MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
        "End calling AAS.assignedToDispatchCenter for taskID:"
            + workAssignmentTaskID);
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return isAsmtAssignToDispCenterSuccessfully;
  }

  // get the expertise based on the Vehicle Type

  public String getExpertiseSkillsByVehicleType(String vehicleType)
      throws MitchellException
  {
    // Put This in Appraisal Assignment SET file
    String expertiseSkill = "";
    if (logger.isLoggable(Level.INFO)) {
      logger.info("Before Expertise by Vehicle Type ::\n");
    }

    expertiseSkill = appraisalAssignmentUtils
        .getExpertiseSkillsByVehicleType(vehicleType);
    if (logger.isLoggable(Level.INFO)) {
      logger.info("After Expertise by Vehicle Type ::\n" + expertiseSkill);
    }
    return expertiseSkill;
  }

  private int rescheduleOrIncompleteAssignment(final long workAssignmentTaskID,
      final long requestTCN, final String reasonCode, final String reasonNotes,
      final UserInfoDocument loggedInUserInfoDocument,
      final String newDispositionCode)
      throws MitchellException
  {

    final String METHOD_NAME = "rescheduleOrIncompleteAssignment";
    logger.entering(CLASS_NAME, METHOD_NAME);
    long startTime = System.currentTimeMillis();

    final StringBuffer localMethodParams = new StringBuffer();
    final StringBuffer logSuffix = new StringBuffer();

    localMethodParams.append("WorkAssignmentTaskID : ")
        .append(workAssignmentTaskID).append("\nTCN : ").append(requestTCN)
        .append("\nLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }
    String claimNumber = null;
    String workItemID = null;
    StringBuffer msgDetail = null;
    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    int isRescheduleOrIncompleteAssignment = AppraisalAssignmentConstants.FAILURE;

    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);
    try {
      // Setting work assignment task ID in StringBuffer for Log Prefix
      logSuffix.append("\t[WorkAssignment task ID:")
          .append(workAssignmentTaskID).append("]\t");

      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(
          workAssignmentTaskID, loggedInUserInfoDocument);

      /*
       * CHECK TCN for stale data check and if assignment is stale then
       * return as stale data exists
       */
      if (checkStaleData(workAssignment.getTcn(), requestTCN)) {
        return AppraisalAssignmentConstants.STALE_DATA;
      }

      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      workAssignmentDocument = WorkAssignmentDocument.Factory.parse(XmlW);
      workItemID = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getWorkItemID();
      claimNumber = workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
          .getClaimNumber();

      msgDetail.append("\tClaimNumber : ").append(claimNumber);
      // Setting Claim Number in StringBuffer for Log Prefix
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");

      if (logger.isLoggable(Level.FINE)) {
        logger.fine("Calling saveRescheduleOrIncompleteWorkAssignment ::\n"
            + "reasonCode = " + reasonCode + " reasonNotes " + reasonNotes
            + " newDispositionCode " + newDispositionCode);
      }

      workAssignment = workAssignmentHandler
          .saveRescheduleOrIncompleteWorkAssignment(workAssignment, reasonCode,
              reasonNotes, loggedInUserInfoDocument, newDispositionCode);

      if (AppraisalAssignmentConstants.DISPOSITION_RESCHEDULE
          .equalsIgnoreCase(newDispositionCode)) {

        appLogUtil.appLog(
            AppraisalAssignmentConstants.APPLOG_RESCHEDULE_SUCCESS,
            workAssignment, loggedInUserInfoDocument, null, startTime);
      } else {

        appLogUtil.appLog(
            AppraisalAssignmentConstants.APPLOG_INCOMPLETE_SUCCESS,
            workAssignment, loggedInUserInfoDocument, null, startTime);
      }

      isRescheduleOrIncompleteAssignment = AppraisalAssignmentConstants.SUCCESS;
    } catch (Exception e) {

      final int errorCode = AppraisalAssignmentConstants.ERROR_RESCHEDULE_OR_INCOMPLETE_AA;
      final String errorDescription = AppraisalAssignmentConstants.ERROR_RESCHEDULE_OR_INCOMPLETE_AA_MSG;
      logCorrelatedAndThrowError(CLASS_NAME, METHOD_NAME, errorCode,
          errorDescription, workItemID, claimNumber, msgDetail.toString(), e,
          loggedInUserInfoDocument);
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);

    return isRescheduleOrIncompleteAssignment;
  }

  public long updateManagedByIdInClaim(final boolean isOriginalAssignment,
      final UserInfoDocument logedInUserInfoDocument,
      final long claimExposureId, final MitchellEnvelopeHelper meHelper)
      throws Exception
  {

    long moiOrgID = AppraisalAssignmentConstants.MOI_ORG_ID_BLANK;
    if (isOriginalAssignment) {
      if (mitchellEnvelopeHandler
          .checkServiceCenterFromMitchellEnvelop(meHelper)) {
        moiOrgID = mitchellEnvelopeHandler
            .getMOIOrgIdFromMitchellEnvelop(meHelper);
        if (moiOrgID != -1)
          claimProxy.setManagedById(logedInUserInfoDocument,
              Long.valueOf(claimExposureId), Long.valueOf(moiOrgID));
      } else
        claimProxy.setManagedById(logedInUserInfoDocument,
            Long.valueOf(claimExposureId), null);
    } else
      moiOrgID = AppraisalAssignmentConstants.MOI_ORG_ID_UNSET_IN_CLOB;

    return moiOrgID;
  }

  public void doAppraisalAssignmentAppLog(final int eventId, final long taskId,
      final UserInfoDocument userInfoDocument,
      final XmlObject mitchellEnvelopeDocument)
      throws MitchellException
  {
    final String METHOD_NAME = "doAppraisalAssignmentAppLog";
    if (mitchellEnvelopeDocument != null
        && mitchellEnvelopeDocument instanceof MitchellEnvelopeDocument) {
      final WorkAssignment workAssignment = workAssignmentHandler
          .getWorkAssignmentByTaskId(taskId, userInfoDocument);
      appLogUtil.appLog(eventId, workAssignment, userInfoDocument,
          (MitchellEnvelopeDocument) mitchellEnvelopeDocument);
    } else {
      throw new MitchellException(
          CLASS_NAME,
          METHOD_NAME,
          "mitchellEnvelopeDocument is either null or is not of type MitchellEnvelopeDocument");
    }
  }

  /*
   * Method for updating the appraisal assignment
   * address in the work assignment document.
   * 
   * @param inputAppraisalAssignmentDTO Object of AppraisalAssignmentDTO.
   * 
   * @param loggedInUserInfoDocument UserInfoDocument of the user performing the
   * update operation on the assignment.
   * 
   * @return java.util.HashMap with TaskId:TCN/Result key value pairs. Zero for
   * SUCCESS, One for FAILURE, Two for STALE DATA.
   * 
   * @exception MitchellException
   */
  public java.util.HashMap updateAppraisalAssignmentAddress(
      final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
      final UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {

    final String METHOD_NAME = "updateAppraisalAssignmentAddress";
    logger.entering(CLASS_NAME, METHOD_NAME);
    MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
        "Start Calling AAS.updateAppraisalAssignmentAddress()");
    logger
        .info("Entering updateAppraisalAssignmentAddress of Service Implementation::::");

    // Set errorLog Details
    StringBuffer msgDetail = null;
    String workItemID = validateMethodInputs(inputAppraisalAssignmentDTO,
        loggedInUserInfoDocument);
    final StringBuffer localMethodParams = new StringBuffer();

    localMethodParams
        .append("InputAppraisalAssignmentDTO : MitchellEnvelopeDocument : ")
        .append(inputAppraisalAssignmentDTO.getMitchellEnvelopDoc().toString())
        .append("\nWorkItemID : ").append(workItemID)
        .append("\nLogged In UserInfoDocument : ")
        .append(loggedInUserInfoDocument.toString());

    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Input Received ::\n" + localMethodParams);
    }

    msgDetail = new StringBuffer("Method Parameters ::")
        .append(localMethodParams);

    // Initialize variables
    WorkAssignment workAssignment = null;
    WorkAssignmentDocument workAssignmentDocument = null;
    com.mitchell.schemas.workassignment.AddressType workAssignAddress;
    String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
    MitchellEnvelopeDocument mitchellEnvDoc = null;
    final StringBuffer logSuffix = new StringBuffer();
    java.util.HashMap ret = new java.util.HashMap();
    int result = AppraisalAssignmentConstants.FAILURE;
    long taskID = inputAppraisalAssignmentDTO.getWaTaskId();
    long wasTCN = inputAppraisalAssignmentDTO.getWorkAssignmentTcn();
    String key = taskID + ":" + wasTCN;

    try {

      msgDetail.append("WorkAssignmentTaskID : ").append(taskID);

      // Checking workAssignmentTaskID and TCN for validity
      if (taskID == 0) {
        throw new MitchellException(
            AppraisalAssignmentConstants.ERROR_MISSING_OR_NULL_TASKID,
            CLASS_NAME, METHOD_NAME,
            AppraisalAssignmentConstants.ERROR_MISSING_OR_NULL_TASKID_MSG);
      }
      if (wasTCN < 0) {
        throw new MitchellException(
            AppraisalAssignmentConstants.ERROR_NULL_OR_LESS_THEN_0_TCN,
            CLASS_NAME, METHOD_NAME,
            AppraisalAssignmentConstants.ERROR_NULL_OR_LESS_THEN_0_TCN_MSG);
      }

      //Retrieving data from input DTO
      MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
          inputAppraisalAssignmentDTO.getMitchellEnvelopDoc());
      mitchellEnvDoc = meHelper.getDoc();

      final AssignmentAddRqDocument assignmentAddRqDocument = mitchellEnvelopeHandler
          .fetchAssignmentAddRqDocument(meHelper);
      claimNumber = assignmentAddRqDocument.getAssignmentAddRq().getClaimInfo()
          .getClaimNum();

      msgDetail.append("AssignmentAddRq Document ClaimNumber : ").append(
          claimNumber);
      logSuffix.append("\t[Claim Number:").append(claimNumber).append("]\t");
      if (logger.isLoggable(Level.INFO)) {
        logger.info("Fetched AssignmentAddRq Document" + logSuffix);
      }

      // getting WorkAssignment from WorkAssignment Service by workAssignmentTaskId
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "Start calling WAS.getWorkAssignmentByTaskId() for claimNumber::"
              + claimNumber);
      workAssignment = workAssignmentHandler.getWorkAssignmentByTaskId(taskID,
          loggedInUserInfoDocument);
      MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
          "End calling WAS.getWorkAssignmentByTaskId() for claimNumber::"
              + claimNumber);

      // CHECK TCN for stale data check and if assignment is stale then
      // return as stale data exists
      if (checkStaleData(workAssignment.getTcn(), wasTCN)) {

        if (logger.isLoggable(Level.INFO)) {
          logger.info("Stale data detected:\n" + "WorkAssignment TCN: "
              + workAssignment.getTcn().toString() + "\nPassed in TCN: "
              + wasTCN);
        }

        result = AppraisalAssignmentConstants.STALE_DATA;
        ret.put(key, Integer.valueOf(result));
        return ret;
      }

      // getting workAssignmentDocument from reterived workAssignment
      workAssignmentDocument = WorkAssignmentDocument.Factory
          .parse(workAssignment.getWorkAssignmentCLOBB());

      // update input DTO with disposition and status from the work assignment document
      inputAppraisalAssignmentDTO.setDisposition(workAssignmentDocument
          .getWorkAssignment().getDisposition());
      inputAppraisalAssignmentDTO.setStatus(workAssignment
          .getWorkAssignmentStatus());

      // -----------------------------------------------------------
      //  
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("**** In updateAppraisalAssignmentAddress, "
                + " Before workAssignmentDocument.getWorkAssignment().getSubType(), "
                + " workAssignmentDocument.getWorkAssignment().getType= [ "
                + workAssignmentDocument.getWorkAssignment().getType()
                + " ]"
                + " workAssignmentDocument.getWorkAssignment()getClientClaimNumber= [ "
                + workAssignmentDocument.getWorkAssignment().getPrimaryIDs()
                    .getClientClaimNumber() + " ]");
      }

      // Bug # 347991 resolution
      // Set Assignment SubType, if present.
      if (workAssignmentDocument.getWorkAssignment().getSubType() != null) {
        inputAppraisalAssignmentDTO.setSubType(workAssignmentDocument
            .getWorkAssignment().getSubType());

        if (logger.isLoggable(Level.INFO)) {
          logger.info("**** In updateAppraisalAssignmentAddress, "
              + " workAssignmentDocument.getWorkAssignment().getSubType()= [ "
              + workAssignmentDocument.getWorkAssignment().getSubType() + " ]"
              + " and inputAppraisalAssignmentDTO.getSubType= [ "
              + inputAppraisalAssignmentDTO.getSubType() + " ]");
        }
      }
      // -----------------------------------------------------------

      // update appraisalAssignment and workAssignment documents using updateAppraisalAssignment()
      AppraisalAssignmentDTO updatedAppraisalAssignmentDTO = updateAppraisalAssignment(
          inputAppraisalAssignmentDTO, loggedInUserInfoDocument);
      long updatedWorkAssignmentTaskId = updatedAppraisalAssignmentDTO
          .getWaTaskId();

      workAssignment = null;

      workAssignment = workAssignmentHandler
          .getWorkAssignmentByTaskId(
              updatedAppraisalAssignmentDTO.getWaTaskId(),
              loggedInUserInfoDocument);

      long updatedWorkAssignmentTCN = workAssignment.getTcn();

      //  Set return results to SUCCESS
      //  Get new TCN for return key
      key = updatedWorkAssignmentTaskId + ":" + updatedWorkAssignmentTCN;
      result = AppraisalAssignmentConstants.SUCCESS;

    } catch (final Exception exception) {

      result = AppraisalAssignmentConstants.FAILURE;
      logCorrelatedAndThrowError(
          CLASS_NAME,
          METHOD_NAME,
          AppraisalAssignmentConstants.ERROR_UPDATING_APPRAISAL_ASSIGNMENT_ADDRESS,
          AppraisalAssignmentConstants.ERROR_UPDATING_APPRAISAL_ASSIGNMENT_ADDRESS_MSG,
          workItemID, claimNumber, msgDetail.toString(), exception,
          loggedInUserInfoDocument);
    }

    logger
        .info("Extiting updateAppraisalAssignmentAddress of Service Implementation::::");
    MonitoringLogger.doLog(CLASS_NAME, METHOD_NAME,
        "End Calling AAS.updateAppraisalAssignmentAddress()");
    logger.exiting(CLASS_NAME, METHOD_NAME);

    // Return result
    ret.put(key, Integer.valueOf(result));
    return ret;
  }

  public String createMinimalSupplementAssignmentBMS(String claimSuffix,
      long estimateDocumentID, long assignorOrgId)
      throws MitchellException
  {
    final String METHOD_NAME = "createMinimalSupplementAssignmentBMS";
    logger.entering(CLASS_NAME, METHOD_NAME);
    String exceptionMsg = null;

    // UserInfo document 
    UserInfoDocument assignorUserInfoDocument = null;

    BmsClmOutDTO bmsClmOutDTO = null;
    AssignmentAddRqDocument assignmentAddRq = null;
    Estimate est = null;
    UserInfoDocument estimatorUserInfoDoc = null;
    final StringBuffer logBuffer = new StringBuffer();
    logBuffer.append("claimSuffix: ");
    logBuffer.append(claimSuffix);
    logBuffer.append(" ::assignorOrgId: ");
    logBuffer.append(assignorOrgId);
    logBuffer.append(" ::estimateDocumentID: ");
    logBuffer.append(estimateDocumentID);

    try {
      assignorUserInfoDocument = userInfoUtils.getUserInfoDoc(assignorOrgId);
      exceptionMsg = "Error calling readClaimInfo method of ClaimService for Claim with \t"
          + logBuffer.toString();
      //Claim BMS info
      bmsClmOutDTO = claimProxy.readClaimInfo(assignorUserInfoDocument,
          claimSuffix);

      if (bmsClmOutDTO.getXmlObject() != null) {
        assignmentAddRq = (AssignmentAddRqDocument) bmsClmOutDTO.getXmlObject();
        /*
         * if (logger.isLoggable(java.util.logging.Level.INFO)) {
         * logger.info("In " + methodName
         * + " Received AssignmentAddRqDocument from ClaimService is : \n"
         * + assignmentAddRq.toString() + "\n\n");
         * }
         */
        String insuredClaimantType = retrieveClaimantInsuredType(assignmentAddRq);
        if (!checkNullEmptyString(insuredClaimantType)) {
          exceptionMsg = "Error: No Insured/Claimant info in AssignmentAddRqDocument received from ClaimService for Claim with \t"
              + logBuffer.toString();
          logger.severe(exceptionMsg);
          throw new MitchellException(CLASS_NAME, METHOD_NAME, exceptionMsg);

        }
        assignmentAddRq = sanitizeBMS(assignmentAddRq);

      } else {
        exceptionMsg = "Error in AssignmentAddRqDocument received from ClaimService for Claim with \t"
            + logBuffer.toString();
        logger.severe(exceptionMsg);
        throw new MitchellException(CLASS_NAME, METHOD_NAME, exceptionMsg);
      }
    } catch (Exception exception) {
      logger.severe(exceptionMsg);
      throw new MitchellException(CLASS_NAME, METHOD_NAME, exceptionMsg);
    }

    try {
      exceptionMsg = "Error in retrieveing Estimate from estimatePkgRemote.getEstimateAndDocByDocId for Claim with \t"
          + logBuffer.toString();
      /* Getting AssigneeID from the supplement Estimate doc */
      if (logger.isLoggable(Level.INFO)) {
        logger
            .info("Calling getEstimateAndDocByDocId method of EstimatePackage Service. "
                + logBuffer.toString());
      }
      Long orgIdOfEstimator = null;
      est = estimatePackageProxy.getEstimateAndDocByDocId(estimateDocumentID,
          true);
      if (est == null) {
        logger.severe(exceptionMsg);
        throw new MitchellException(CLASS_NAME, METHOD_NAME, exceptionMsg);
      }
      // Non-Staff (DRP value)
      if (est.getBusinessPartnerId() != null) {
        if (logger.isLoggable(Level.INFO)) {
          logger
              .info("Fetching BusinessPartnerId for Non-Staff User from estimate."
                  + logBuffer.toString());
        }
        orgIdOfEstimator = est.getBusinessPartnerId();
        logBuffer.append(" ::BusinessPartnerId: ");
        logBuffer.append(orgIdOfEstimator);
      }// Staff
      else {
        if (logger.isLoggable(Level.INFO)) {
          logger.info("Fetching Document from estimate for Staff User."
              + logBuffer.toString());
        }

        exceptionMsg = "Error while retrieving Document from EstimatePackageService for Claim with \t"
            + logBuffer.toString();
        Document doc = est.getDocument();
        //doc.getDocTypeCode()
        if (doc == null) {
          logger.severe(exceptionMsg);
          throw new MitchellException(CLASS_NAME, METHOD_NAME, exceptionMsg);
        }
        if (logger.isLoggable(Level.INFO)) {
          logger.info("Fetching ServiceProviderId from Document for Staff User"
              + logBuffer.toString());
        }
        orgIdOfEstimator = doc.getServiceProviderId();
        logBuffer.append(" ::ServiceProviderId: ");
        logBuffer.append(orgIdOfEstimator);
      }

      if (orgIdOfEstimator != null) {
        exceptionMsg = "Error: Retrieving Estimator UserInfoDocument from UserInfo Service: "
            + logBuffer.toString();
        if (logger.isLoggable(Level.INFO)) {
          logger.info(exceptionMsg);
        }
        estimatorUserInfoDoc = userInfoUtils.getUserInfoDoc(orgIdOfEstimator
            .longValue());
        if (estimatorUserInfoDoc != null) {
          if (logger.isLoggable(Level.INFO)) {
            logger
                .info("Retrieving Estimator UserInfoDocument from UserInfo Service: "
                    + estimatorUserInfoDoc + logBuffer.toString());
          }
        }
      }
    } catch (Exception exception) {
      logger.severe(exceptionMsg);
      MitchellException myMitchellException = new MitchellException(CLASS_NAME,
          METHOD_NAME, exceptionMsg, exception);
      throw myMitchellException;

    }

    CIECADocument ciecaDoc = CIECADocument.Factory.newInstance();
    CIECA cieca = ciecaDoc.addNewCIECA();
    cieca.setAssignmentAddRq(assignmentAddRq.getAssignmentAddRq());

    //Add new aggregates from existing ME
    AssignmentAddRq assignmentAddRqNew = cieca.getAssignmentAddRq();
    assignmentAddRqNew.setRqUID(UUIDFactory.getInstance().getCeicaUUID());
    DocumentInfoType documentInfo = null;
    DocumentVerType documentVer = null;
    AssignmentEventType assignmentEvent = null;
    com.cieca.bms.EventInfoType eventInfo = null;

    documentInfo = assignmentAddRqNew.addNewDocumentInfo();
    documentVer = documentInfo.addNewDocumentVer();
    documentVer
        .setDocumentVerCode(AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_DOC_VERSION_CODE); //constant for Supplement assignment
    documentVer.setDocumentVerNum(0);

    documentInfo
        .setBMSVer(AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_BMS_VERSION);
    documentInfo
        .setDocumentType(AppraisalAssignmentConstants.SUPPLEMENT_ASSIGNMENT_DOC_TYPE);
    documentInfo.setCreateDateTime(Calendar.getInstance());
    if (eventInfo == null) {
      eventInfo = assignmentAddRqNew.addNewEventInfo();
      assignmentEvent = eventInfo.addNewAssignmentEvent();
      assignmentEvent.setCreateDateTime(Calendar.getInstance());
    }

    //Add EstimatorIDs info if its available
    if (!assignmentAddRqNew.getVehicleDamageAssignment().isSetEstimatorIDs()
        && estimatorUserInfoDoc != null) {
      EstimatorIDsTypeType estimatorIDsType = assignmentAddRqNew
          .getVehicleDamageAssignment().addNewEstimatorIDs();
      estimatorIDsType.setCurrentEstimatorID(estimatorUserInfoDoc.getUserInfo()
          .getUserID());
    }

    //Add InsuranceComapny node to AdminInfo
    if (!assignmentAddRqNew.getAdminInfo().isSetInsuranceAgent()
        && estimatorUserInfoDoc != null) {
      InsuranceCompanyType genParty = assignmentAddRqNew.getAdminInfo()
          .addNewInsuranceCompany();
      PartyType insParty = genParty.addNewParty();
      //get the company level node
      if ("COMPANY".equalsIgnoreCase(estimatorUserInfoDoc.getUserInfo()
          .getUserHier().getHierNode().getLevel())) {
        insParty.addNewOrgInfo().setCompanyName(
            estimatorUserInfoDoc.getUserInfo().getUserHier().getHierNode()
                .getName());
      }
    }

    if (logger.isLoggable(java.util.logging.Level.INFO)) {
      logger.info("In " + METHOD_NAME + " Constructed CIECADocument is: \n"
          + ciecaDoc.toString());
    }

    logger.exiting(CLASS_NAME, METHOD_NAME);
    return ciecaDoc.xmlText();

  }

  /**
   * This method is a utility method to sanitize BMS for SIP Assignment WF feed
   * It unsets the Affiliation element for Adjustor/Estimator and defaults the
   * country to 'US'
   * [in case the country code is not present]
   * for all parties (Insured/Claimant/Owner/Policyholder/Inspection
   * Site/Adjustor/Estimator)
   * 
   * @param assignmentAddRq Object of AssignmentAddRqDocument
   * @return AssignmentAddRqDocument
   */
  protected AssignmentAddRqDocument sanitizeBMS(
      AssignmentAddRqDocument assignmentAddRq)
  {
    String METHOD_NAME = "sanitizeBMS";
    logger.entering(CLASS_NAME, METHOD_NAME);
    VehicleDamageAssignmentType vehicleDamageAssignmentType = null;

    /* Adjustor */
    if (assignmentAddRq.getAssignmentAddRq().getAdminInfo().isSetAdjuster()) {
      /*
       * If affiliation is set and has length more than 2 chars, unset it
       * otherwise it will fail SIP WF validation
       */
      if (assignmentAddRq.getAssignmentAddRq().getAdminInfo().getAdjuster()
          .isSetAffiliation()
          && assignmentAddRq.getAssignmentAddRq().getAdminInfo().getAdjuster()
              .getAffiliation().length() > 2) {
        assignmentAddRq.getAssignmentAddRq().getAdminInfo().getAdjuster()
            .unsetAffiliation();
      }
      sanitizeParty(assignmentAddRq.getAssignmentAddRq().getAdminInfo()
          .getAdjuster().getParty());
    }

    /*
     * Estimator:: If affiliation is set and has length more than 2 chars, unset
     * it otherwise it will fail SIP WF validation
     */
    EstimatorType[] estType = assignmentAddRq.getAssignmentAddRq()
        .getAdminInfo().getEstimatorArray();
    if (estType != null && estType.length > 0) {
      for (EstimatorType estimatorType : estType) {
        if (estimatorType.isSetAffiliation()
            && estimatorType.getAffiliation().length() > 2) {
          estimatorType.unsetAffiliation();
        }
        sanitizeParty(estimatorType.getParty());
      }
    }
    /* Claimant */
    if (assignmentAddRq.getAssignmentAddRq().getAdminInfo().isSetClaimant()) {
      sanitizeParty(assignmentAddRq.getAssignmentAddRq().getAdminInfo()
          .getClaimant().getParty());
    }
    /* Insured */
    if (assignmentAddRq.getAssignmentAddRq().getAdminInfo().isSetInsured()) {
      sanitizeParty(assignmentAddRq.getAssignmentAddRq().getAdminInfo()
          .getInsured().getParty());
    }
    /* Owner */
    if (assignmentAddRq.getAssignmentAddRq().getAdminInfo().isSetOwner()) {
      sanitizeParty(assignmentAddRq.getAssignmentAddRq().getAdminInfo()
          .getOwner().getParty());
    }
    /* PolicyHolder */
    if (assignmentAddRq.getAssignmentAddRq().getAdminInfo().isSetPolicyHolder()) {
      sanitizeParty(assignmentAddRq.getAssignmentAddRq().getAdminInfo()
          .getPolicyHolder().getParty());
    }
    /* InspectionSite */
    if (assignmentAddRq.getAssignmentAddRq().getAdminInfo()
        .isSetInspectionSite()) {
      sanitizeParty(assignmentAddRq.getAssignmentAddRq().getAdminInfo()
          .getInspectionSite().getParty());
    }

    /********* Remove tags not present in CSAA carrier feed ***************/
    if (assignmentAddRq.getAssignmentAddRq().isSetVehicleDamageAssignment()) {
      vehicleDamageAssignmentType = assignmentAddRq.getAssignmentAddRq()
          .getVehicleDamageAssignment();
      VehicleInfoType vehicleInfoType = vehicleDamageAssignmentType
          .getVehicleInfo();

      /* Powertrain */
      if (vehicleInfoType.isSetPowertrain()) {
        if (vehicleInfoType.getPowertrain().isSetEngineOptions()) {
          vehicleInfoType.getPowertrain().unsetEngineOptions();
        }
        /* Need to unset TransmissionInfo, if transmission code is not set */
        if (vehicleInfoType.getPowertrain().isSetTransmissionInfo()) {
          if (vehicleInfoType.getPowertrain().getTransmissionInfo()
              .getTransmissionCode() == null) {
            vehicleInfoType.getPowertrain().unsetTransmissionInfo();
          }
        }
      }
      /* Condition */
      if (vehicleInfoType.isSetCondition()) {
        if (vehicleInfoType.getCondition().isSetConditionCode()
            && vehicleInfoType.getCondition().getConditionCode().length() > 2)
          vehicleInfoType.getCondition().unsetConditionCode();
      }
      /* Vehicle Description */
      if (vehicleInfoType.isSetVehicleDesc()) {
        /* Odometer */
        if (vehicleInfoType.getVehicleDesc().isSetOdometerInfo()) {
          if (vehicleInfoType.getVehicleDesc().getOdometerInfo()
              .isSetOdometerReadingCode()) {
            vehicleInfoType.getVehicleDesc().getOdometerInfo()
                .unsetOdometerReadingCode();
          }
          if (vehicleInfoType.getVehicleDesc().getOdometerInfo()
              .isSetOdometerReadingMeasure()) {
            vehicleInfoType.getVehicleDesc().getOdometerInfo()
                .unsetOdometerReadingMeasure();
          }
        }
        /* Vehicle Type */
        if (vehicleInfoType.getVehicleDesc().isSetVehicleType()
            && vehicleInfoType.getVehicleDesc().getVehicleType().length() > 2) {
          vehicleInfoType.getVehicleDesc().unsetVehicleType();
        }

      }
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
    return assignmentAddRq;
  }

  /**
   * This method is a hack to sanitize BMS for SIP Assignment WF feed
   * It defaults the country to 'US'
   * for all parties (Insured/Claimant/Policyholder/Inspection Site)
   * 
   * @param party Object of PartyType
   */
  private void sanitizeParty(PartyType party)
  {
    CommunicationsType[] communicationArray = null;
    CommunicationsType communicationType = null;
    String qualifierString = null;
    AddressType communicationAddress = null;
    String name = null;
    String firstName = null;
    String lastName = null;

    if (party != null && party.isSetPersonInfo()) {
      communicationArray = party.getPersonInfo().getCommunicationsArray();

      if (party.getPersonInfo().getPersonName().isSetLastName()) {
        lastName = party.getPersonInfo().getPersonName().getLastName();
      }
      if (party.getPersonInfo().getPersonName().isSetFirstName()) {
        firstName = party.getPersonInfo().getPersonName().getFirstName();
      }
      name = firstName + " " + lastName;

    } else if (party != null && party.isSetOrgInfo()) {
      communicationArray = party.getOrgInfo().getCommunicationsArray();

      if (party.getOrgInfo().isSetCompanyName()) {
        name = party.getOrgInfo().getCompanyName();
      }
    }

    if (logger.isLoggable(Level.INFO)) {

      logger.info("CommunicationsType length for: " + name + ":: "
          + communicationArray.length);
    }
    for (int i = 0; i < communicationArray.length; i++) {
      communicationType = communicationArray[i];

      if (communicationType != null) {
        //Get the Qualifier for the communication
        qualifierString = communicationType.getCommQualifier();
        if (logger.isLoggable(Level.INFO)) {
          logger.info("qualifierString:" + qualifierString);
        }

        if (qualifierString != null) {
          // Check the qualifier value for AL, HP, WP, CP, FX, EM           
          if ("AL".equalsIgnoreCase(qualifierString)) {
            communicationAddress = communicationType.getAddress();
            if (communicationAddress != null) {
              String country = communicationAddress.getCountryCode();
              if (logger.isLoggable(Level.INFO)) {
                logger.info("CountryCode in BMS for : " + name + ":: "
                    + country);
              }
              // set to default
              if (country == null
                  || (country != null && country.trim().length() > 2)) {
                communicationAddress.setCountryCode("US");
              }
            }
            break;
          }
        }
      }
    }

  }

  /**
   * Retrieve Claimant/Insured Type from the BMS
   */
  protected String retrieveClaimantInsuredType(
      final AssignmentAddRqDocument assignmentAddRqDocument)
  {
    if (logger.isLoggable(Level.INFO)) {
      logger.info("Start retrieveClaimantInsuredType()...");
    }
    String claimantInsuredType = null;
    AdminInfoType adminInfo = null;

    final AssignmentAddRqDocument.AssignmentAddRq addRq = assignmentAddRqDocument
        .getAssignmentAddRq();
    if (addRq != null) {
      adminInfo = addRq.getAdminInfo();
    }

    if (adminInfo != null && adminInfo.isSetClaimant()) {
      final ClaimantType claimant = adminInfo.getClaimant();
      if (claimant != null) {
        final PartyType claimantParty = claimant.getParty();
        if (claimantParty != null) {
          final com.cieca.bms.PersonInfoType claimantPersonInfo = claimantParty
              .getPersonInfo();
          if (claimantPersonInfo != null) {
            if (isClaimantInsured(claimantPersonInfo)) {
              claimantInsuredType = AppraisalAssignmentConstants.PRIMARY_CONTRACT_TYPE_CLAIMNT;
            }
          }
        }
      }
    }

    if (claimantInsuredType == null && adminInfo != null
        && adminInfo.isSetPolicyHolder()) {
      final PolicyHolderType policyHolderType = adminInfo.getPolicyHolder();
      if (policyHolderType != null) {
        final PartyType policyHolder = policyHolderType.getParty();
        if (policyHolder != null) {
          final com.cieca.bms.PersonInfoType policyHolderPersonInfo = policyHolder
              .getPersonInfo();
          if (policyHolderPersonInfo != null) {
            if (isClaimantInsured(policyHolderPersonInfo)) {
              claimantInsuredType = AppraisalAssignmentConstants.PRIMARY_CONTRACT_TYPE_INSURED;
            }
          }
        }
      }
    }

    if (logger.isLoggable(Level.INFO)) {
      logger.info("Exit retrieveClaimantInsuredType() :: "
          + claimantInsuredType);
    }
    return claimantInsuredType;
  }

  protected boolean isClaimantInsured(
      final com.cieca.bms.PersonInfoType personInfo)
  {
    boolean isClaimantInsured = false;
    AddressType communicationAddress = null;
    com.cieca.bms.CommunicationsType[] communicationArray = null;

    final com.cieca.bms.PersonNameType personNameType = personInfo
        .getPersonName();

    if (personNameType != null
        && (checkNullEmptyString(personNameType.getFirstName()) || checkNullEmptyString(personNameType
            .getLastName()))) {
      isClaimantInsured = true;
    }

    if (!isClaimantInsured) {
      if (personInfo.sizeOfCommunicationsArray() > 0) {
        communicationArray = personInfo.getCommunicationsArray();
        for (int claimantArrayInx = 0; claimantArrayInx < communicationArray.length; claimantArrayInx++) {
          final CommunicationsType communication = communicationArray[claimantArrayInx];
          if (communication != null) {
            /* Get the Qualifier for the communication */
            String qualifierString = communication.getCommQualifier();
            if (qualifierString != null) {
              qualifierString = qualifierString.toUpperCase();
              if ("AL".equals(qualifierString) || "HA".equals(qualifierString)
                  || "MA".equals(qualifierString)
                  || "SA".equals(qualifierString)
                  || "WA".equals(qualifierString)) {
                communicationAddress = communication.getAddress();
                if (communicationAddress != null) {
                  if (checkNullEmptyString(communicationAddress.getAddress1())
                      || checkNullEmptyString(communicationAddress
                          .getAddress2())
                      || checkNullEmptyString(communicationAddress.getCity())
                      || checkNullEmptyString(communicationAddress
                          .getCountryCode())
                      || checkNullEmptyString(communicationAddress
                          .getPostalCode())
                      || checkNullEmptyString(communicationAddress
                          .getStateProvince())) {
                    isClaimantInsured = true;
                  }
                }
              }
            }
          }
        }
      }
    }

    if (logger.isLoggable(Level.INFO)) {
      logger.info("Claimant/Insured present in CIECA " + isClaimantInsured);
    }

    //
    return isClaimantInsured;
  }

  private boolean checkNullEmptyString(final String inputString)
  {
    return (inputString != null && !inputString.trim().equals(""));
  }

  private String getFirstName(ScheduleInfoType oldAssigneeInfo)
  {
    String firstName = "";
    if (oldAssigneeInfo.getAssignee() != null
        && oldAssigneeInfo.getAssignee().getPersonName().getFirstName() != null)
      firstName = oldAssigneeInfo.getAssignee().getPersonName().getFirstName();
    return firstName;
  }

  private String getLastName(ScheduleInfoType oldAssigneeInfo)
  {
    String lastName = "";
    if (oldAssigneeInfo.getAssignee() != null
        && oldAssigneeInfo.getAssignee().getPersonName().getLastName() != null)
      lastName = oldAssigneeInfo.getAssignee().getPersonName().getLastName();
    return lastName;
  }


public CustomSettingProxy getCustomSettingProxy() {
	return customSettingProxy;
}

public void setCustomSettingProxy(CustomSettingProxy customSettingProxy) {
	this.customSettingProxy = customSettingProxy;
}

}