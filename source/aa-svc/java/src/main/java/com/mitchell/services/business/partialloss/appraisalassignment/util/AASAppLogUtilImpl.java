package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.workassignment.AssigneeUserType;
import com.mitchell.schemas.workassignment.PrimaryIDsType;
import com.mitchell.schemas.workassignment.ScheduleInfoType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.AppLogProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.SystemConfigProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.UserInfoProxy;
import com.mitchell.services.business.partialloss.monitor.MonitoringLogger;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument.AppLoggingToEventPublishingContext;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument.AppLoggingToEventPublishingContext.EventDetails;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument.AppLoggingToEventPublishingContext.EventDetails.NameValuePair;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * This class is responsible for AppraisalAssignment Service App/Event Logging.
 * 
 */
public class AASAppLogUtilImpl implements AASAppLogUtil
{

  private final Logger logger = Logger.getLogger(AASAppLogUtil.class.getName());
  private static final String CLASS_NAME = AASAppLogUtil.class.getName();
  private UserInfoProxy userInfoProxy = null;
  private MitchellEnvelopeHandler mitchellEnvelopeHandler = null;
  private AppLogProxy appLogProxy = null;
  private SystemConfigProxy systemConfigProxy = null;

  public void setUserInfoProxy(UserInfoProxy userInfoProxy)
  {
    this.userInfoProxy = userInfoProxy;
  }

  public void setMitchellEnvelopeHandler(
      final MitchellEnvelopeHandler mitchellEnvelopeHandler)
  {

    this.mitchellEnvelopeHandler = mitchellEnvelopeHandler;
  }

  public void setAppLogProxy(AppLogProxy appLogProxy)
  {
    this.appLogProxy = appLogProxy;
  }

  public void setSystemConfigProxy(SystemConfigProxy systemConfigProxy)
  {
    this.systemConfigProxy = systemConfigProxy;
  }

  /**
   * Utility class that is used to do AppLogging. The actual Applog is done
   * only once with a status of either success or fail. In between the
   * applogging name value pairs are continuously updated to indicate the
   * current progress of the workflow.
   */

  public void appLog(int eventId, WorkAssignment workAssignment,
      UserInfoDocument userInfoDocument,
      MitchellEnvelopeDocument mitchellEnvelopeDocument)
      throws MitchellException
  {
    appLog(eventId, workAssignment, userInfoDocument, mitchellEnvelopeDocument,
        0);
  }

  /**
   * 
   * @param eventId
   *          EventId
   * @param workAssignment
   *          WorkAssignment
   * @param userInfoDocument
   *          UserInfoDocument
   * @param mitchellEnvelopeDocument
   *          MitchellEnvelopeDocument
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   */
  public void appLog(int eventId, WorkAssignment workAssignment,
      UserInfoDocument userInfoDocument,
      MitchellEnvelopeDocument mitchellEnvelopeDocument, long startTime)
      throws MitchellException
  {
    appLog(eventId, workAssignment, userInfoDocument, mitchellEnvelopeDocument,
        startTime, (AppLoggingNVPairs) null);
  }

  /**
   * 
   * @param eventId
   *          EventId
   * @param workAssignment
   *          WorkAssignment
   * @param userInfoDocument
   *          UserInfoDocument
   * @param mitchellEnvelopeDocument
   *          MitchellEnvelopeDocument
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   */
  public void appLog(final int eventId, final WorkAssignment workAssignment,
      final UserInfoDocument userInfoDocument,
      final MitchellEnvelopeDocument mitchellEnvelopeDocument, long startTime,
      AppLoggingNVPairs appLoggingNVPairsIn)
      throws MitchellException
  {

    logger.entering(CLASS_NAME, "Entering to do APPLOG");
    String claimNumber = null;
    String clientClaimNumber = null;
    String workItemID = null;
    int claimExposureID = 0;
    int claimID = 0;

    try {

      final AppLoggingDocument logDoc = AppLoggingDocument.Factory
          .newInstance();
      final AppLoggingType appType = logDoc.addNewAppLogging();

      EventDetails eventDetails = null;
      final String XmlW = workAssignment.getWorkAssignmentCLOBB();
      final WorkAssignmentDocument workAssignmentDoc = WorkAssignmentDocument.Factory
          .parse(XmlW);

      AppLoggingNVPairs appLoggingNVPairs = appLoggingNVPairsIn;
      if (appLoggingNVPairs == null) {
        appLoggingNVPairs = new AppLoggingNVPairs();
      }

      WorkAssignmentType workAssignmentType = workAssignmentDoc
          .getWorkAssignment();
      final PrimaryIDsType primaryIDsType = workAssignmentType.getPrimaryIDs();
      ScheduleInfoType currentSchedule = workAssignmentType
          .getCurrentSchedule();

      claimNumber = primaryIDsType.getClaimNumber();
      claimID = workAssignment.getClaimID().intValue();
      claimExposureID = workAssignment.getClaimExposureID().intValue();
      clientClaimNumber = primaryIDsType.getClientClaimNumber();
      workItemID = primaryIDsType.getWorkItemID();

      appType.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
      appType.setWorkItemType(AppraisalAssignmentConstants.WORK_ITEM_TYPE);
      if (clientClaimNumber != null && !"".equals(clientClaimNumber)) {
        appType.setClaimNumber(clientClaimNumber);
      } else {
        appType.setClaimNumber(claimNumber);
      }
      appType.setClaimExposureId(claimExposureID);
      appType.setClaimId(claimID);
      appType.setTransactionType(Long.toString(eventId));
      appType.setCurrentWorkflowId(workItemID);
      appType.setStatus(AppLogging.APPLOG_ST_OK);

      if (userInfoDocument != null) {
        appType.setMitchellUserId(userInfoDocument.getUserInfo().getUserID());
      } else {
        appType
            .setMitchellUserId(AppraisalAssignmentConstants.DEFAULT_WORKFLOW_USERID);
      }

      // Event Publishing
      final AppLoggingToEventPublishingContextDocument appLoggingToEventPublishingContextDoc = AppLoggingToEventPublishingContextDocument.Factory
          .newInstance();
      final AppLoggingToEventPublishingContext appLoggingToEventPublishingContext = appLoggingToEventPublishingContextDoc
          .addNewAppLoggingToEventPublishingContext();
      // appLoggingToEventPublishingContext.setEventDescriptionOverride("Event Description Override for Claim #"+
      // claimNumber);
      if (clientClaimNumber != null && !"".equals(clientClaimNumber)) {
        appLoggingToEventPublishingContext.setClaimNumber(clientClaimNumber);
      } else {
        appLoggingToEventPublishingContext.setClaimNumber(claimNumber);
      }

      appLoggingToEventPublishingContext.setClaimExposureNumber(primaryIDsType
          .getClaimSuffix());
      // i am not sure what is mean of below code
      // appLoggingToEventPublishingContext.setClaimActivityLogOverride("ActivityLog Override for Claim #"+
      // claimNumber);

      eventDetails = appLoggingToEventPublishingContext.addNewEventDetails();

      // set the event id

      if (logger.isLoggable(java.util.logging.Level.FINE)) {
        logger.fine("Generated APPLOG Document is : \n" + logDoc.toString());
      }
      // Fields from WorkAssignment
      addNVPairToEventDetails("ASSIGNMENT_TYPE", workAssignment
          .getAssignmentType().getAssignmentTypeCode(), eventDetails);
      addNVPairToEventDetails("DISPOSITION", workAssignment
          .getAssignmentDisposition().getDispositionCode(), eventDetails);
      addNVPairToEventDetails("WORKASSIGNMENT_TASK_ID",
          String.valueOf(workAssignment.getTaskID()), eventDetails);
      addNVPairToEventDetails("DOCUMENT_ID",
          String.valueOf(workAssignment.getReferenceId()), eventDetails);
      addNVPairToEventDetails("ASSIGNMENT_STATUS",
          workAssignment.getWorkAssignmentStatus(), eventDetails);

      if (mitchellEnvelopeDocument != null) {

        long subStartTime = System.currentTimeMillis();
        transformMEToAppLogNVPair(mitchellEnvelopeDocument, eventDetails);
        if (MonitoringLogger.isLoggableTimer()) {
          MonitoringLogger.doLog(CLASS_NAME, "appLog",
              "transformMEToAppLogNVPair", System.currentTimeMillis()
                  - subStartTime);
        }

        // Taking the siteLocationID from WorkAssignment and populating with orgCode.
        // fetch MOI ID

        long moiOrgID = 0;
        long siteLocationID = 0;
        String orgCodeSiteLocation = null;
        String orgCodeMOIID = null;

        AdditionalAppraisalAssignmentInfoDocument additionalAppraisalInfo = mitchellEnvelopeHandler
            .getAdditionalAppraisalAssignmentInfoDocumentFromME(new MitchellEnvelopeHelper(
                mitchellEnvelopeDocument));

        if (additionalAppraisalInfo != null
            && additionalAppraisalInfo.getAdditionalAppraisalAssignmentInfo() != null
            && additionalAppraisalInfo.getAdditionalAppraisalAssignmentInfo()
                .getMOIDetails() != null
            && additionalAppraisalInfo.getAdditionalAppraisalAssignmentInfo()
                .getMOIDetails().getUserSelectedMOI() != null) {
          moiOrgID = additionalAppraisalInfo
              .getAdditionalAppraisalAssignmentInfo().getMOIDetails()
              .getUserSelectedMOI().getMOIOrgID();

        }
        if (workAssignment.getSiteLocationIdentifier() != null) {
          siteLocationID = workAssignment.getSiteLocationIdentifier()
              .longValue();

        }
        if (moiOrgID > 0) {
          orgCodeMOIID = retrieveOrgCode(moiOrgID);
          if (orgCodeMOIID != null) {
            addNVPairToEventDetails("MOI_ID", orgCodeMOIID, eventDetails);
          }
        }

        if (siteLocationID > 0) {
          if (siteLocationID == moiOrgID && moiOrgID > 0) {
            addNVPairToEventDetails("Site_Location_ID", orgCodeMOIID,
                eventDetails);
          } else {
            orgCodeSiteLocation = retrieveOrgCode(siteLocationID);
            if (orgCodeSiteLocation != null) {
              addNVPairToEventDetails("Site_Location_ID", orgCodeSiteLocation,
                  eventDetails);
            }
          }
        }

        if (workAssignment.getAssignmentSubType() != null
            && workAssignment.getAssignmentSubType().getAssignmentSubTypeCode() != null) {
          addNVPairToEventDetails("Assignment_Subtype", workAssignment
              .getAssignmentSubType().getAssignmentSubTypeCode(), eventDetails);
        }

      } else {

        // Set the Event detail from WorkAssignment

        // addNVPairToEventDetails("WORK_ITEM_ID",workItemID);

        addNVPairToEventDetails("ASSIGNEE_ID", currentSchedule.getAssigneeID(),
            eventDetails);

        addNVPairToEventDetails("CLAIM_EXPOSURE_ID",
            String.valueOf(claimExposureID), eventDetails);

        addNVPairToEventDetails("CLAIM_ID", String.valueOf(claimID),
            eventDetails);

        if (currentSchedule.getAssigneeUserType() != null) {
          final String userType = currentSchedule.getAssigneeUserType()
              .toString();
          if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.info("UserType in AppLog::" + userType);
          }
          if ((AssigneeUserType.STAFF.toString()).equals(userType)) {
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
              logger.info("APPRAISAL_AFFLIATION::"
                  + AppraisalAssignmentConstants.APPRAISER_AFFILIATION_INSURER);
            }
            addNVPairToEventDetails("APPRAISAL_AFFLIATION",
                AppraisalAssignmentConstants.APPRAISER_AFFILIATION_INSURER,
                eventDetails);
          } else if ((AssigneeUserType.INDEPENDENT_APP.toString())
              .equals(userType)) {
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
              logger
                  .info("APPRAISAL_AFFLIATION::"
                      + AppraisalAssignmentConstants.APPRAISER_AFFILIATION_INDP_APPRAISER);
            }
            addNVPairToEventDetails(
                "APPRAISAL_AFFLIATION",
                AppraisalAssignmentConstants.APPRAISER_AFFILIATION_INDP_APPRAISER,
                eventDetails);
          } else if ((AssigneeUserType.BODYSHOP.toString()).equals(userType)) {
            if (logger.isLoggable(java.util.logging.Level.INFO)) {
              logger
                  .info("APPRAISAL_AFFLIATION::"
                      + AppraisalAssignmentConstants.APPRAISER_AFFILIATION_REPAIR_FACILITY);
            }
            addNVPairToEventDetails(
                "APPRAISAL_AFFLIATION",
                AppraisalAssignmentConstants.APPRAISER_AFFILIATION_REPAIR_FACILITY,
                eventDetails);
          }
        }

        if (AppraisalAssignmentConstants.DISPOSITION_CANCELLED
            .equals(workAssignmentType.getDisposition())
            && workAssignmentType.getEventReasonCode() != null
            && !"".equals(workAssignmentType.getEventReasonCode()))
          addNVPairToEventDetails("CANCEL_REASON_CD",
              workAssignmentType.getEventReasonCode(), eventDetails);

        // Add ExternalAssignmentId
        if (null != workAssignment.getExternalID()
            && !workAssignment.getExternalID().trim().equals("")) {
          addNVPairToEventDetails("EXTERNAL_ASSIGNMENT_ID",
              workAssignment.getExternalID(), eventDetails);
        }

      }

      // Taking scheduleDateTime from WorkAssignment Now.
      if (currentSchedule.getScheduleStartDateTime() != null) {
        final String offsetScheduleDateTime = this
            .appendPSTOffset(currentSchedule.getScheduleStartDateTime()
                .toString().trim());
        addNVPairToEventDetails("SCHEDULE_DATE_TIME", offsetScheduleDateTime,
            eventDetails);
      }

      addNVPair("CLIENT_CLAIM_NUMBER", clientClaimNumber, appLoggingNVPairs);
      addNVPair("DOCUMENT_ID", String.valueOf(workAssignment.getReferenceId()),
          appLoggingNVPairs);
      addNVPair("CLAIM_EXPOSURE_ID", String.valueOf(claimExposureID),
          appLoggingNVPairs);
      addNVPair("CLAIM_ID", String.valueOf(claimID), appLoggingNVPairs);
      addNVPair("COMPANY_CODE", workAssignment.getCompanyCode(),
          appLoggingNVPairs);
      addNVPair("ASSIGNMENT_TYPE", workAssignment.getAssignmentType()
          .getAssignmentTypeCode(), appLoggingNVPairs);
      addNVPair("DISPOSITION", workAssignment.getAssignmentDisposition()
          .getDispositionCode(), appLoggingNVPairs);
      addNVPair("ASSIGNMENT_STATUS", workAssignment.getWorkAssignmentStatus(),
          appLoggingNVPairs);
      addNVPair("ASSIGNEE_ID", currentSchedule.getAssigneeID(),
          appLoggingNVPairs);

      addNVPair("WORKASSIGNMENT_TASK_ID",
          String.valueOf(workAssignment.getTaskID()), appLoggingNVPairs);
      addNVPair("WORK_ITEM_ID", workItemID, appLoggingNVPairs);
      addNVPair("EXTERNAL_ASSIGNMENT_ID",workAssignment.getExternalID(), appLoggingNVPairs);
      if (currentSchedule.getScheduleStartDateTime() != null) {
        addNVPair("SCHEDULE_DATE_TIME", currentSchedule
            .getScheduleStartDateTime().toString(), appLoggingNVPairs);
      }

      // Add Processing Info

      addTimingAndMachineApplogInfo(appLoggingNVPairs, startTime);

      appType
          .setAppLoggingToEventPublishingContext(appLoggingToEventPublishingContext);

      if (logger.isLoggable(java.util.logging.Level.FINE)) {
        logger.fine("AppLog Document before sending is::" + logDoc);
        logger.fine("NVPAIR Size::" + appLoggingNVPairs.getPairs().size());
      }

      final MitchellWorkflowMessageDocument mwmd = this.appLogProxy
          .logAppEvent(logDoc, userInfoDocument, workItemID,
              AppraisalAssignmentConstants.APP_NAME,
              AppraisalAssignmentConstants.MODULE_NAME, appLoggingNVPairs);

    } catch (final Exception ex) {

      logger.severe("Exception while doing apploging" + ex.getMessage());
      // call error log service to do error log .. do'nt throw error log
    }

  }

  /**
   * 
   * @param eventId
   *          EventId
   * @param workAssignment
   *          WorkAssignment
   * @param userInfoDocument
   *          UserInfoDocument
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   */

  public void appLog(final int eventId, final WorkAssignment workAssignment,
      final UserInfoDocument userInfoDocument)
      throws MitchellException
  {

    appLog(eventId, workAssignment, userInfoDocument, null);

  }

  /**
   * A function to transform the elements in ME to Event Details NV Pairs.
   * 
   * @param mitchellEnvelopeDocument
   *          MitchellEnvelopeDocument
   * @param eventDetailsIn
   *          EventDetails
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   */
  public void transformMEToAppLogNVPair(
      final MitchellEnvelopeDocument mitchellEnvelopeDocument,
      final EventDetails eventDetailsIn)
      throws MitchellException
  {
    final String METHOD_NAME = "transformMEToAppLogNVPair";
    logger.entering(CLASS_NAME, METHOD_NAME);
    final String inputMEXML = mitchellEnvelopeDocument.xmlText();
    final String appLogXSLFilePath = this.systemConfigProxy
        .getSettingValue("/AppraisalAssignment/AppLogging/AppLoggingTemplateBaseDir")
        + File.separator
        + this.systemConfigProxy
            .getSettingValue("/AppraisalAssignment/AppLogging/AppLoggingTemplateXslFile");
    if (logger.isLoggable(java.util.logging.Level.INFO)) {
      logger.info("AppLoggingXSLFilePath is ::" + appLogXSLFilePath);
      logger.info("Input ME is ::" + inputMEXML);
    }

    try {

      final XsltTransformer xsltTranformer = new XsltTransformer(
          appLogXSLFilePath);
      xsltTranformer.createTransformer();
      final String appLogXML = xsltTranformer.transformXmlString(inputMEXML);
      if (appLogXML == null) {
        throw new MitchellException(CLASS_NAME, METHOD_NAME,
            "Null String returned while tranforming the MitchellEnvelopeDocument!!");
      }
      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("APPLogXML Is:: " + appLogXML);
      }
      final InputStream inputAppLogStream = new ByteArrayInputStream(
          appLogXML.getBytes());

      final SAXParserFactory saxParserfactory = SAXParserFactory.newInstance();
      final SAXParser saxParser = saxParserfactory.newSAXParser();
      final DefaultHandler defaultHandler = new DefaultHandler() {
        StringBuffer textBuffer;

        public void startElement(final String uri, final String localName,
            final String qName, final Attributes attributes)
            throws SAXException
        {
        }

        public void endElement(final String uri, final String localName,
            final String qName)
            throws SAXException
        {

          if (!"root".equals(qName)) {
            if (logger.isLoggable(java.util.logging.Level.FINE)) {
              logger.fine("Key::" + qName);
              logger.fine("Value::" + textBuffer.toString());
            }
            final NameValuePair nvPair = eventDetailsIn.addNewNameValuePair();
            nvPair.setName(qName);
            nvPair.addValue(textBuffer.toString());
          }
          textBuffer = null;
        }

        public void characters(final char buf[], final int offset, final int len)
            throws SAXException
        {
          final String textValue = new String(buf, offset, len);
          if (textBuffer == null) {
            textBuffer = new StringBuffer(textValue);

          } else {

            textBuffer.append(textValue);
          }
        }
      };
      saxParser.parse(inputAppLogStream, defaultHandler);
      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("Parsing completed!!");
      }
    } catch (final MitchellException me) {
      throw me;
    } catch (final Exception exception) {
      logger.severe("Unexpected exception occured::" + exception.getMessage());

      throw new MitchellException(CLASS_NAME, METHOD_NAME,
          "Unexpected exception occured!!", exception);
    }
    logger.exiting(CLASS_NAME, METHOD_NAME);
  }

  /**
   * A function to add AppLogging NameValue pair
   * 
   * The type of Name value pair being added. Example- file, directory etc. It
   * should correspond to one of the types defined in "AppLoggingNVPairs"
   * class.
   * 
   * @param key
   *          key for the pair
   * @param value
   *          value for the pair
   */
  public void addNVPair(final String key, final String value,
      final AppLoggingNVPairs appLoggingNVPairs)
  {
    // check key and value are not null
    if (key != null && key.length() > 0 && value != null && value.length() > 0) {

      // Add the pair passed in. Check the type and call the corresponding
      // AppLoggingNVPairs function
      appLoggingNVPairs.addPair(key, value);

    }

  }

  /**
   * A function to add NameValue pair in Event Details
   * 
   * 
   * @param key
   *          key for the pair
   * @param value
   *          value for the pair
   */
  public void addNVPairToEventDetails(final String key, final String value,
      final EventDetails eventDetails)
  {

    // check key and value are not null
    NameValuePair nv = null;

    if (key != null && key.length() > 0 && value != null && value.length() > 0) {

      // Add the pair passed in.
      nv = eventDetails.addNewNameValuePair();
      nv.setName(key);
      nv.addValue(value);

    }

  }

  public String appendPSTOffset(final String dateString)
      throws ParseException
  {

    if (logger.isLoggable(java.util.logging.Level.INFO)) {
      logger.info("Schedule Date Time is" + dateString);
    }

    String formattedString = null;

    try {
      final SimpleDateFormat sdfSourceDateTime = new SimpleDateFormat(
          "yyyy-MM-dd'T'HH:mm:ss");
      final Date srcDate = sdfSourceDateTime.parse(dateString);

      // yyyy-MM-dd'T'HH:mm:ss.SSSZ.
      final TimeZone time = TimeZone.getTimeZone("PST");
      final SimpleDateFormat sdfTargetDateTime = new SimpleDateFormat(
          "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
      sdfTargetDateTime.setTimeZone(time);
      formattedString = sdfTargetDateTime.format(srcDate);

      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("Transformed Date Time is" + formattedString);
      }

      StringBuffer sb = new StringBuffer(formattedString.substring(0,
          formattedString.length() - 2)).append(":");
      sb.append(formattedString.substring(formattedString.length() - 2));

      // to add colon in timezone pattern
      formattedString = sb.toString();

    } catch (final ParseException parseException) {
      logger.severe("Exception occured while parsing the date time field::"
          + parseException.getMessage());
      throw parseException;
    }

    return formattedString;
  }

  public void appLogUpdate(final int eventId, final long claimSuffixId,
      final String vehTrackingStatus, final UserInfoDocument userInfoDocument,
      long startTime)
      throws MitchellException
  {

    logger.entering(CLASS_NAME, "Entering to do APPLOG");
    final AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();
    try {

      final AppLoggingDocument logDoc = AppLoggingDocument.Factory
          .newInstance();
      final AppLoggingType appType = logDoc.addNewAppLogging();
      final String workItemID = "UNKNOWN";
      appType.setTransactionType(Long.toString(eventId));
      appType.setModuleName(AppraisalAssignmentConstants.MODULE_NAME);
      appType.setStatus(AppLogging.APPLOG_ST_OK);
      appType.setClaimExposureId(Long.valueOf(claimSuffixId).intValue());
      if (userInfoDocument != null) {
        appType.setMitchellUserId(userInfoDocument.getUserInfo().getUserID());
      } else {
        appType
            .setMitchellUserId(AppraisalAssignmentConstants.DEFAULT_WORKFLOW_USERID);
      }
      // ApplogNVPairs applogNVPair = new ApplogNVPair();
      addNVPair("Claim_Suffix_Id", String.valueOf(claimSuffixId),
          appLoggingNVPairs);
      addNVPair("Vehicle_Tracking_Status", vehTrackingStatus, appLoggingNVPairs);

      // Add Processing Info

      addTimingAndMachineApplogInfo(appLoggingNVPairs, startTime);

      if (logger.isLoggable(java.util.logging.Level.FINE)) {
        logger.fine("AppLog Document before sending is::" + logDoc);
        logger.fine("NVPAIR Size::" + appLoggingNVPairs.getPairs().size());
      }
      final MitchellWorkflowMessageDocument mwmd = this.appLogProxy
          .logAppEvent(logDoc, userInfoDocument, workItemID,
              AppraisalAssignmentConstants.APP_NAME,
              AppraisalAssignmentConstants.MODULE_NAME, appLoggingNVPairs);

      logger.exiting(CLASS_NAME,
          "Exting from APPLOG. APPLOG Done with MitchellWorkflowMessageDocument: \n"
              + mwmd.toString());
    } catch (final Exception ex) {

      logger.severe("Exception while doing apploging" + ex.getMessage());
      // call error log service to do error log .. do'nt throw error log
    }
  }

  private String retrieveOrgCode(long locationID)
  {
    String orgCode = null;
    try {
      if (locationID != 0) {
        OrgInfoDocument orgInfoDocument = this.userInfoProxy
            .getOrgInfo(locationID);

        if (logger.isLoggable(java.util.logging.Level.FINE)) {
          logger.fine("OrgInfoDocument is::" + orgInfoDocument);
        }

        if (orgInfoDocument != null && orgInfoDocument.getOrgInfo() != null
            && orgInfoDocument.getOrgInfo().getOrgCode() != null) {
          orgCode = orgInfoDocument.getOrgInfo().getOrgCode();
        }

      }
    } catch (MitchellException mitchellException) {
      logger
          .warning("MitchellException occured fetching OrgInfodocument for Location  ID::"
              + locationID
              + " "
              + AppUtilities.getStackTraceString(mitchellException));
      // Consuming MitchellException so that the flow does not break. 
    } catch (Exception exception) {
      logger
          .warning("Exception occured fetching OrgInfodocument for Location ID ::"
              + locationID + " " + AppUtilities.getStackTraceString(exception));
      // Consuming Exception so that the flow does not break. 
    }

    return orgCode;
  }

  /**
   * This method adds two AppLog Info entries to the provided AppLoggingNVPairs.
   * First is the time difference in seconds between current time and the
   * provided startTime value. Second is the processing server name.
   */
  protected AppLoggingNVPairs addTimingAndMachineApplogInfo(
      AppLoggingNVPairs appLogPairs, long startTime)
  {
    // Processing time

    if (startTime > 0) {
      long endTime = System.currentTimeMillis();
      double totalTime = ((endTime - startTime) / 1000.0);
      appLogPairs.addInfo("TotalProcessingTimeSecs", String.valueOf(totalTime));
    }

    // Machine Name/Server Name

    String machineInfo = AppUtilities.buildServerName();
    if (machineInfo != null && machineInfo.length() > 0) {
      appLogPairs.addInfo("ProcessingMachineInfo", machineInfo);
    }

    return appLogPairs;
  }

}
