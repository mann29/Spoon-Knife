package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.JMSException;

import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoType;
import com.mitchell.schemas.appraisalassignment.AssignmentDetailsType;
import org.apache.xmlbeans.XmlException;

import com.cieca.bms.AdminInfoType;
import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.PersonInfoType;
import com.cieca.bms.PersonNameType;
import com.mitchell.common.exception.MICommonException;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.exception.ServiceLocatorException;
import com.mitchell.common.types.CrossOverUserInfoDocument;
import com.mitchell.common.types.MitchellWorkflowMessageDocument;
import com.mitchell.common.types.OnlineUserType;
import com.mitchell.common.types.OrgInfoDocument;
import com.mitchell.common.types.TrackingInfoDocument;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.DefinitionType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.RoleCollaboratorPairType;
import com.mitchell.schemas.WorkProcessInitiationMessage;
import com.mitchell.schemas.WorkProcessInitiationMessageDocument;
import com.mitchell.schemas.apddelivery.APDAppraisalAssignmentInfoType;
import com.mitchell.schemas.apddelivery.APDDeliveryContextDocument;
import com.mitchell.schemas.apddelivery.APDDeliveryContextType;
import com.mitchell.schemas.apddelivery.BaseAPDCommonType;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.ExtractClassName;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractAssignmentDeliveryHandler;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractHandlerUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.BmsConverterFactory;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.CiecaBmsConverter;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.DocStoreClientProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.EstimatePackageClientProxy;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.MessagePostingAgent;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim.Converter;
import com.mitchell.services.core.messagebus.BadXmlFormatException;
import com.mitchell.services.core.messagebus.WorkflowMsgUtil;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.services.core.workprocess.WorkProcessServiceClientAPI;
import com.mitchell.services.core.workprocessservice.WPKeyRequestDocument;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public abstract class AbstractMsgBusDelHndlr extends
    AbstractAssignmentDeliveryHandler implements MsgBusDelHandler
{

    private static final boolean DO_NOT_RETURN_EXCEPTION = false;
  private static final Integer GATHER_WP_KEYS = new Integer(106831);
  private static final Integer EXTRACT_USER_INFO_AND_WORK_PROCESS_ENVELOPE = new Integer(
      106832);
  private static final Integer ADD_CIECA_TO_OUT_BOUND_ENVELOPE = new Integer(
      106833);
  private static final Integer ADD_ATTACHMENTS_TO_OUT_BOUND_ENVELOPE = new Integer(
      106834);
  private static final Integer DEPENDENCY_AND_SANITY_CHECK = new Integer(106835);
  private static final Integer REMOVE_PRIVATE_KEYS = new Integer(106836);
  private static final Integer PROCESS_FOR_WP_UPDATE_MESSAGE = new Integer(
      106837);
  private static final Integer PROCESS_FOR_WP_INIT_MESSAGE = new Integer(106838);
  private static final Integer POST_TO_MESSAGE_BUS = new Integer(106839);
  private static final Integer WRITE_APP_LOG = new Integer(106840);
  private static final String SUPPLEMENT = "SUPPLEMENT";
  protected static final String PRIVATE = "PRIVATE";
  protected static final String PUBLIC = "PUBLIC";
  protected static final String WORK_PROCESS_UPDATE_MESSAGE = "WorkProcessUpdateMessage";
  protected static final String UPDATE_APPRAISAL_ASSIGNMENT = "UPDATE_APPRAISAL_ASSIGNMENT";
  protected static final String UPDATE_SUPPLEMENT_ASSIGNMENT = "UPDATE_SUPPLEMENT_ASSIGNMENT";
  protected static final String CANCEL_APPRAISAL_ASSIGNMENT = "CANCEL_APPRAISAL_ASSIGNMENT";
  protected static final String CANCEL_SUPPLEMENT_ASSIGNMENT = "CANCEL_SUPPLEMENT_ASSIGNMENT";
  protected static final String COMPLETE_APPRAISAL_ASSIGNMENT = "COMPLETE_APPRAISAL_ASSIGNMENT";
  protected static final String WORK_PROCESS_INITIATION_MESSAGE = "WorkProcessInitiationMessage";
  private static final String DEFINITION_VERSION = "1.0";
  private static final String WORK_PROCESS_INIT_MESSAGE = "1.0";
  private static final String CIECA_BMS_ASG_XML = "CIECA_BMS_ASG_XML";
  private static final String ASSIGNMENT_BMS = "AssignmentBMS";
  private static final String UPDATED_ASSIGNMENT_BMS = "UpdatedAssignmentBMS";
  public static final String PRIMARY_CONTACT_TYPE_CLAIMNT = "CLAIMANT";
  public static final String PRIMARY_CONTACT_TYPE_INSURED = "INSURED";

  // -- MetaData for AttachmentInfo
  protected static final String ATTACHMENT_INFO = "AttachmentInfo";
  protected static final String WC_ATTACHMENT_INFO = "WCAttachmentInfo";
  protected static final String UPDATED_WC_ATTACHMENT_INFO = "UpdatedWCAttachmentInfo";
  protected static final String ATTACHMENT_INFO_XML_CLASSNAME = "com.mitchell.types.AttachmentInfoDocument";

  // -- MetaData for ArtifactContext
  protected static final String ARTIFACT_CTX_VERSION = "1.0";
  protected static final String ARTIFACT_CONTEXT = "ARTIFACT_CONTEXT_XML"; // "PlatformArtifactContext";   // "ArtifactContext";                      
  protected static final String WC_ARTIFACT_CTX = "PlatformArtifactContext"; // "ARTIFACT_CONTEXT_XML";      // "WCArtifactContext";
  protected static final String UPDATED_WC_ARTIFACT_CTX = "PlatformArtifactContext"; // "ARTIFACT_CONTEXT_XML";      // "UpdatedWCArtifactContext";
  protected static final String ARTIFACT_CTX_XML_CLASSNAME = "com.mitchell.schemas.ArtifactContextDocument";

  protected static final String CALLER_METHOD = "deliverAssignment(APDDeliveryContextDocument)";
  protected static final String SHOP = "SHOP";
  private static final String CARRIER = "CARRIER";
  private static final String ORIGINAL_ESTIMATE = "ORIGINAL_ESTIMATE";
  protected static final String DISPATCH_REPORT = "DISPATCH_REPORT";
  protected static final int CANCEL = 0;
  private static final int DISPATCH = 1;
  protected static final int COMPLETE = 2;
  protected static final String MIME_TEXT_TYPE = "text/plain";
  private static final String CANCEL_STRING = "CANCEL";
  private static final String CANCELLED_STRING = "CANCELLED";
  private static final String CANCEL_REASON_STRING = "CancelReason";
  private static final String LAST_MODIFIED_DATE_TIME = "LastModifiedDateTime";
  private static final String CANCEL_REASON_DESC_STRING = "CancelReasonDescription";
  private static final String DISPATCHED_STRING = "DISPATCHED";
  protected static final String COMPLETED_STRING = "COMPLETED";
  private static final int APD_DELIVERY_TYPE = 155101;
  private static final boolean NEW = false;
  private static final boolean UPDATE = !NEW;
  protected static final long NOT_ARCHIVED = 0;
  private static final boolean INCLUDE_NESTED_STACKTRACE = true;
  private static final String ESTIMATE_DOCUMENT_ID_NV_KEY = "EstimateDocumentId";
  protected AbstractHandlerUtils handlerUtility;

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.mitchell.services.business.partialloss.assignmentdelivery.handler
   * .apd.
   * impl.MsgBusDelHandler#deliverAssignment(com.mitchell.schemas.apddelivery
   * .APDDeliveryContextDocument)
   */
  public void deliverAssignment(final APDDeliveryContextDocument document)
      throws AssignmentDeliveryException
  {
    final String THIS_METHOD = CALLER_METHOD;
    Integer operation = null;// Set in this method so that in case of an
    // exception, it points to the last area worked
    // on. Used later in the catch block.
    try {
      /* Initial Sanity check */
      operation = DEPENDENCY_AND_SANITY_CHECK;
      final APDDeliveryContextType apdDeliveryContext = document
          .getAPDDeliveryContext();
      guard(this);
      mLogger.entering(THIS_METHOD);
      guard(apdDeliveryContext);
      final String workItemId = apdDeliveryContext
          .getAPDAppraisalAssignmentInfo().getAPDCommonInfo().getWorkItemId();
      // Adds [<work item id>] at the end of every log line.
      final AnnotatedLogger log = annotate(mLogger).with(workItemId);
      if (log.isLoggable(Level.FINE)) {
        log.fine("Starting " + THIS_METHOD);
        log.fine("All dependencies are staisfied for " + THIS_METHOD);
      }
      /* Start setting preconditions */
      final boolean isSupplement = SUPPLEMENT.equals(apdDeliveryContext
          .getMessageType());

      final int requestStatus = gatherRequestStatusFrom(apdDeliveryContext);
      final HashMap workProcessKeysMutableDoNotUse = new HashMap(2);
      operation = GATHER_WP_KEYS;
      final boolean isReDispatch = gatherWorkPrecessKeysAndDecideReDispatch(
          apdDeliveryContext, workProcessKeysMutableDoNotUse, log);
      /* Gather/create essential skeletal data */
      // Immutable work process keys, so safe from here on.
      final Map workProcessKeys = Collections
          .unmodifiableMap(workProcessKeysMutableDoNotUse);
      final BaseAPDCommonType apdCommonInfo = apdDeliveryContext
          .getAPDAppraisalAssignmentInfo().getAPDCommonInfo();

      operation = EXTRACT_USER_INFO_AND_WORK_PROCESS_ENVELOPE;
      final UserInfoType userInfo = extractUserInfo(apdCommonInfo);
      final UserInfoType staffUserInfo = extractStaffUserInfo(apdCommonInfo);
      final MitchellEnvelopeHelper inboundEnvelope = extractInboundEnvelope(apdDeliveryContext);
      if (log.isLoggable(Level.FINE)) {
        log.fine("Inbound envelope.[" + inboundEnvelope.getEnvelope() + "]");
      }
      final MitchellEnvelopeHelper outboundEnvelope = MitchellEnvelopeHelper
          .newInstance();
      CIECADocument cieca;
      cieca = handlerUtility.getCiecaDocumentFromMitchellEnvelope(
          inboundEnvelope.getDoc(),
          AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER, workItemId);

      // Get primaryContactType from AdditionalAppraisalAssignmentInfoDoc: Online or CarrierFeed / Group-Level or User-Level 
      // then add to Outbound Context in new N/V pair
      String primaryContactType = getPrimaryContactTypefromAdditionalAppraisalAssignmentInfoDoc(
          inboundEnvelope, workItemId, log);
      if (log.isLoggable(Level.INFO)) {
        log.info("Return from getPrimaryContactTypefromAdditionalAppraisalAssignmentInfoDoc: primaryContactType =[ "
            + primaryContactType + " ]");
      }

      // Determine "default" PrimaryContactType from CiecaBMS
      //      For case where AdditionalAppraisalAssignmentInfoDoc is not provided, (User-level assignments)
      //      primaryContactType must be determined from CiecaBMS
      // then add to Outbound Context in new N/V pair
      if (!checkNullEmptyString(primaryContactType)) {
        primaryContactType = determinePrimaryContactTypefromCiecaBmsDoc(cieca,
            log);
        if (log.isLoggable(Level.INFO)) {
          log.info("Return from determinePrimaryContactTypefromCiecaBmsDoc: primaryContactType =[ "
              + primaryContactType + " ]");
        }
      }

      Long relatedEstimateDocumentId = getRelatedEstimateDocIdFromAAInfoDoc(inboundEnvelope, workItemId, log);

      setNameValuePairs(outboundEnvelope, cieca, apdDeliveryContext,
          primaryContactType, relatedEstimateDocumentId, isReDispatch, isSupplement, log);
      if (log.isLoggable(Level.FINE)) {
        log.fine("Assigned name-value pairs to MEDoc.");
      }

      // Not adding BMS and Attachment for MessageStatus="COMPLETED"
      if (allowBMSAndAttachmentInMitchellEnvelope(requestStatus)) {
        /* Transform CiecaBMS for special cases, else return original CiecaBMS */
        CIECADocument outCiecaBms = performTransformOfCiecaBmsInInboundPayload(
            cieca, apdCommonInfo, staffUserInfo, log);

        // Replace existing BMS envelope with the updated BMS envelope in inboundEnvelope
        if (outCiecaBms != null) {
          inboundEnvelope.updateEnvelopeBodyContent(
              inboundEnvelope.getEnvelopeBody(), outCiecaBms);
          if (log.isLoggable(Level.FINE)) {
            log.fine("Updated InboundEnvelope.["
                + inboundEnvelope.getEnvelope() + "]");
          }
        }

        /* Add CIECA BMS to out-bound envelope */
        operation = ADD_CIECA_TO_OUT_BOUND_ENVELOPE;
        processContextForCiecaBms(outboundEnvelope, inboundEnvelope,
            workItemId, apdDeliveryContext, log, isReDispatch);
        if (log.isLoggable(Level.FINE)) {
          log.fine("Attached Cieca BMS to outbound MEDoc.");
        }
        /* Add attachments to out-bound envelope */
        operation = ADD_ATTACHMENTS_TO_OUT_BOUND_ENVELOPE;
        processContextForWorkCenterAttachments(apdDeliveryContext,
            inboundEnvelope, outboundEnvelope, userInfo, log, isReDispatch);
        if (log.isLoggable(Level.FINE)) {
          log.fine("Attached work center attachments to outbound MEDoc.");
        }
        /* Add work process data as applicable */
      }

      if (requestStatus == DISPATCH) {
        if (!isReDispatch) {
          if (log.isLoggable(Level.FINE)) {
            log.fine("Fresh dispatch");
          }
          operation = PROCESS_FOR_WP_INIT_MESSAGE;
          processContextForWorkProcessInitiationMessage(apdDeliveryContext,
              log, staffUserInfo, inboundEnvelope, outboundEnvelope,
              isSupplement);
        } else {
          if (log.isLoggable(Level.FINE)) {
            log.fine("Redispatch");
          }
          operation = PROCESS_FOR_WP_UPDATE_MESSAGE;
          processContextForWorkProcessUpdateMessage(apdDeliveryContext, log,
              staffUserInfo, inboundEnvelope, outboundEnvelope,
              workProcessKeys, isSupplement);
        }
      } else if (requestStatus == COMPLETE) {
        operation = PROCESS_FOR_WP_UPDATE_MESSAGE;
        processContextForWorkProcessUpdateMessage(apdDeliveryContext, log,
            staffUserInfo, inboundEnvelope, outboundEnvelope, workProcessKeys,
            isSupplement);
      } else { //WRONG CODE -by PK
        operation = PROCESS_FOR_WP_UPDATE_MESSAGE;
        processContextForWorkProcessUpdateMessage(apdDeliveryContext, log,
            staffUserInfo, inboundEnvelope, outboundEnvelope, workProcessKeys,
            isSupplement);
        /*
         * Remove private keys, so a later incarnation of a work process
         * for the same context, such as an UNCANCEL will not trip over
         * previous keys.
         */
        operation = REMOVE_PRIVATE_KEYS;
        removePrivateKeys(apdDeliveryContext);
      }
      if (log.isLoggable(Level.FINE)) {
        log.fine("Attached Work Flow Initiation Message to outbound MEDoc");
      }
      /* Post to message bus */
      operation = POST_TO_MESSAGE_BUS;
      final UserInfoDocument userInfoForMessagebus = UserInfoDocument.Factory
          .newInstance();
      userInfoForMessagebus.setUserInfo(apdCommonInfo.getTargetUserInfo()
          .getUserInfo());
      if (log.isLoggable(Level.FINE)) {
        log.fine("Outbound envelope being posted to message bus.["
            + outboundEnvelope.getEnvelope() + "]");
      }
      final String messageId = postToMessageBus(outboundEnvelope, workItemId,
          userInfoForMessagebus);
      if (log.isLoggable(Level.INFO)) {
        log.info("Sent mitchell envelope to its destination. Message Id is ["
            + messageId + "]");
        log.info("Finished " + THIS_METHOD);
      }
      operation = WRITE_APP_LOG;
      writeApplogEvent(workItemId, cieca, userInfoForMessagebus, requestStatus,
          isReDispatch);

      // NEW Feature --- For Shop Supplements Send Annotated Shop Supplement Email Notification
      if (protocolRequiresShopSupplementNotification()) {
        final boolean isDRPShopAssignment = (apdCommonInfo
            .getTargetDRPUserInfo() != null);
        if (isSupplement && isDRPShopAssignment
            && (CANCEL != gatherRequestStatusFrom(apdDeliveryContext))
            && (COMPLETE != gatherRequestStatusFrom(apdDeliveryContext))) {
          deliverShopSuppNotificationEmail(apdDeliveryContext, apdCommonInfo,
              workItemId, log);
        }
      }

    } catch (final AssignmentDeliveryException e) {
      mLogger.severe(e.getMessage());
      mLogger.severe((AppUtilities.getStackTraceString(e,
          INCLUDE_NESTED_STACKTRACE)));
      if (e.getCause() != null) {
        mLogger.severe(e.getCause().getMessage());
        mLogger.severe(AppUtilities.getStackTraceString(e,
            INCLUDE_NESTED_STACKTRACE));
      }
      throw e;
    } catch (final Exception e) {
      logAndRethrowAsAssignmentDeliveryException(operation, e);
    }
  }

    private Long getRelatedEstimateDocIdFromAAInfoDoc(
            MitchellEnvelopeHelper inboundEnvelope,
            String workItemId,
            AnnotatedLogger log) {

        try {
            AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = findAdditionalAppraisalAssignmentInfoDocument(
                    inboundEnvelope.getDoc(), workItemId);

            if (aaaInfoDoc == null) {
                return null;
            }

            AdditionalAppraisalAssignmentInfoType aaInfoType = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo();

            if (aaInfoType == null) {
                return null;
            }

            AssignmentDetailsType assignmentDetails = aaInfoType.getAssignmentDetails();

            if (assignmentDetails == null) {
                return null;
            }

            if (!assignmentDetails.isSetRelatedEstimateDocumentID()) {
                return null;
            }

            return assignmentDetails
                    .getRelatedEstimateDocumentID();

        } catch (Exception e) {
            log.severe(AppUtilities.getStackTraceString(e, true));
            log.severe("Error in getRelatedEstimateDocIdFromAAInfoDoc()");
            return null;
        }
    }

    /**
   * @param inboundEnvelope
   * @param workItemId
   * @param log
   * @return
   * @throws Exception
   */
  private String getPrimaryContactTypefromAdditionalAppraisalAssignmentInfoDoc(
      MitchellEnvelopeHelper inboundEnvelope, String workItemId,
      AnnotatedLogger log)
      throws Exception
  {
    String retval = "";
    try {
      AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = findAdditionalAppraisalAssignmentInfoDocument(
          inboundEnvelope.getDoc(), workItemId);
      if (log.isLoggable(Level.INFO)) {
        log.info("getPrimaryContactTypefromAdditionalAppraisalAssignmentInfoDoc, aaaInfoDoc =[ "
            + aaaInfoDoc + " ]");
      }
      if (aaaInfoDoc != null) {
        retval = aaaInfoDoc.getAdditionalAppraisalAssignmentInfo()
            .getAssignmentDetails().getPrimaryContactType().toString()
            .toUpperCase();
      }
    } catch (Exception e) {
      log.severe(AppUtilities.getStackTraceString(e, true));
      log.severe("Error in getPrimaryContactTypefromAdditionalAppraisalAssignmentInfoDoc()");
    }

    if (log.isLoggable(Level.INFO)) {
      log.info("getPrimaryContactTypefromAdditionalAppraisalAssignmentInfoDoc, retval =[ "
          + retval + " ]");
    }

    return retval;
  }

  /**
   * Determines the default PrimaryContactType, if Claimant (first) or Insured
   * (second) in present
   * 
   * @param cieca
   * @param log
   * @return String for default primaryContactType, CLAIMAINT or INSURED
   */
  protected String determinePrimaryContactTypefromCiecaBmsDoc(
      CIECADocument cieca, final AnnotatedLogger log)
  {
    String primaryContactType = "";
    try {
      AdminInfoType adminInfo = null;
      AssignmentAddRqDocument.AssignmentAddRq assignmentAddRqDoc = cieca
          .getCIECA().getAssignmentAddRq();
      if (assignmentAddRqDoc != null)
        adminInfo = assignmentAddRqDoc.getAdminInfo();

      if (adminInfo != null && adminInfo.getClaimant() != null
          && adminInfo.getClaimant().getParty() != null
          && adminInfo.getClaimant().getParty().getPersonInfo() != null) {
        if (isClaimantInsured(adminInfo.getClaimant().getParty()
            .getPersonInfo()))
          primaryContactType = PRIMARY_CONTACT_TYPE_CLAIMNT;
      }

      if (!checkNullEmptyString(primaryContactType) && adminInfo != null
          && adminInfo.getPolicyHolder() != null
          && adminInfo.getPolicyHolder().getParty() != null
          && adminInfo.getPolicyHolder().getParty().getPersonInfo() != null) {
        if (isClaimantInsured(adminInfo.getPolicyHolder().getParty()
            .getPersonInfo()))
          primaryContactType = PRIMARY_CONTACT_TYPE_INSURED;
      }

    } catch (Exception e) {
      log.severe(AppUtilities.getStackTraceString(e, true));
      log.severe("Error in determinePrimaryContactTypefromCiecaBmsDoc()");
    }

    if (log.isLoggable(Level.INFO)) {
      log.info("Return from determinePrimaryContactTypefromCiecaBMSDoc: primaryContactType =[ "
          + primaryContactType + " ]");
    }
    return primaryContactType;
  }

  /**
   * Determines if PersonInfoType, Insured or Claimant contains minimum First or
   * Last Name
   * 
   * @param PersonInfoType
   * @return boolean Boolean value true if First or Last Name present in
   *         PersonInfoType
   */
  private boolean isClaimantInsured(PersonInfoType personInfo)
  {
    boolean isClaimantInsured = false;
    PersonNameType personNameType = personInfo.getPersonName();
    if (personNameType != null
        && (checkNullEmptyString(personNameType.getFirstName()) || checkNullEmptyString(personNameType
            .getLastName())))
      isClaimantInsured = true;
    return isClaimantInsured;
  }

  /**
   * Checks for null and empty String objects.
   * 
   * @param inputString String to be checked for null and empty values.
   * @return boolean Boolean value true if string is not null or not empty.
   */
  private static boolean checkNullEmptyString(String inputString)
  {
    return (inputString != null && !inputString.trim().equals(""));
  }

  /**
   * @param cieca
   * @param apdCommonInfo
   * @param staffUserInfo
   * @param log
   * @return TODO
   * @throws RemoteException
   * @throws MitchellException
   */
  protected CIECADocument performTransformOfCiecaBmsInInboundPayload(
      CIECADocument cieca, final BaseAPDCommonType apdCommonInfo,
      final UserInfoType staffUserInfo, final AnnotatedLogger log)
      throws RemoteException, MitchellException
  {

    /*
     * Prepare BmsConverterFactoryImpl for a customized Cieca BMS transformation
     * based on Company Code and Staff or Shop
     */
    // Provided by dependency injection ---
    // final BmsConverterFactoryImpl bmsConverterFactory = new BmsConverterFactoryImpl();

    // Prepare map context for selection of BMSConverter
    // 1. CompanyCode
    Map context = new HashMap(2);
    final String companyCode = extractCompanyCodefromUserInfo(staffUserInfo); // TODO: Verify which UserInfo required here!!
    context.put(BmsConverterFactory.COMPANY_CODE, companyCode);

    // 2. Staff/Shop boolean
    // if (protocolRequiresShopSupplementNotification()) {
    final boolean isShop = (apdCommonInfo.getTargetDRPUserInfo() != null);
    if (isShop) {
      context.put(BmsConverterFactory.SHOP, Boolean.TRUE);
    } else {
      context.put(BmsConverterFactory.SHOP, Boolean.FALSE);
    }

    // Now call the BMS converter based on map context		
    CiecaBmsConverter converter = bmsConverterFactory
        .createCiecaBmsConvertor(context);
    CIECADocument outCiecaBms = converter.convert(cieca);

    return outCiecaBms;
  }

  /**
   * @param apdDeliveryContext
   * @param apdCommonInfo
   * @param workItemId
   * @param log
   * @throws Exception
   */
  protected void deliverShopSuppNotificationEmail(
      final APDDeliveryContextType apdDeliveryContext,
      final BaseAPDCommonType apdCommonInfo, final String workItemId,
      final AnnotatedLogger log)
      throws Exception
  {
    if (log.isLoggable(Level.INFO)) {
      log.info("Have Shop Supplement Case:  Assemble new AssignmentServiceContext ... ");
    }

    final MitchellEnvelopeDocument mEnvDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    final MitchellEnvelopeType mEnvType = apdDeliveryContext
        .getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope()
        .getMitchellEnvelope();
    mEnvDoc.setMitchellEnvelope(mEnvType);

    final AssignmentServiceContext asgSvcCtx = new AssignmentServiceContext();
    ArrayList attObjList = new ArrayList();
    boolean drpUserFlag = true;
    asgSvcCtx.setMitchellEnvDoc(mEnvDoc);
    asgSvcCtx.setWorkItemId(workItemId);
    asgSvcCtx.setDrp(drpUserFlag);

    if ((mEnvType != null)
        && (apdCommonInfo.getTargetUserInfo().getUserInfo() != null)
        && (apdCommonInfo.getTargetDRPUserInfo().getUserInfo() != null)) {

      final UserInfoDocument srcUserInfoDoc = UserInfoDocument.Factory
          .newInstance();
      srcUserInfoDoc.setUserInfo(apdCommonInfo.getTargetUserInfo()
          .getUserInfo());
      // srcUserInfoDoc.setUserInfo(apdCommonInfo.getSourceUserInfo().getUserInfo());
      asgSvcCtx.setUserInfo(srcUserInfoDoc);

      final UserInfoDocument drpUserInfoDoc = UserInfoDocument.Factory
          .newInstance();
      drpUserInfoDoc.setUserInfo(apdCommonInfo.getTargetDRPUserInfo()
          .getUserInfo());
      asgSvcCtx.setDrpUserInfo(drpUserInfoDoc);
      asgSvcCtx.setAttachmentObjects(attObjList);

      if (log.isLoggable(Level.INFO)) {
        log.info("Have Shop Supplement Case:  now calling sendNonStaffSuppNotification ... ");
      }
      if (log.isLoggable(Level.FINE)) {
        log.fine("New Assembled AssignmentServiceContext= [ "
            + asgSvcCtx.toString() + " ]");
        log.fine("New Assembled srcUserInfoDoc= [ " + srcUserInfoDoc.toString()
            + " ]");
        log.fine("New Assembled drpUserInfoDoc= [ " + drpUserInfoDoc.toString()
            + " ]");
      }

      // handlerUtility.sendNonStaffSuppNotification(asgSvcCtx, workItemId, DO_NOT_RETURN_EXCEPTION);
      handlerUtility.sendNonStaffSuppNotification(asgSvcCtx, workItemId, false);
      if (log.isLoggable(Level.INFO)) {
        log.info("Have Shop Supplement Case:  After calling sendNonStaffSuppNotification ... ");
      }
    }

  }

  protected abstract boolean protocolRequiresShopSupplementNotification();

  /**
   * @param workItemId
   * @param cieca
   * @param userInfoForMessagebus
   * @param status
   *          TODO
   * @param isReDispatch
   *          TODO
   * @throws MitchellException
   */
  public abstract void writeApplogEvent(final String workItemId,
      final CIECADocument cieca, final UserInfoDocument userInfoForMessagebus,
      final int status, final boolean isReDispatch)
      throws MitchellException;

  private void removePrivateKeys(final APDDeliveryContextType apdDeliveryContext)
      throws MitchellException
  {
    final BaseAPDCommonType apdCommonInfo = apdDeliveryContext
        .getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
    final String insCoCode = apdCommonInfo.getInsCoCode();
    final WPKeyRequestDocument workProcessKeyPrivateRequest = workServiceClient
        .initWPKeyRequest(WorkProcessServiceClientAPI.CTX_PRIVATE_APPRAISAL_ASSIGNMENT);
    workProcessKeyPrivateRequest.getWPKeyRequest().setClientClaimNumber(
        apdCommonInfo.getClientClaimNumber());
    workProcessKeyPrivateRequest.getWPKeyRequest().setWorkAssignmentId(
        apdDeliveryContext.getAPDAppraisalAssignmentInfo().getTaskId());
    workProcessKeyPrivateRequest.getWPKeyRequest().setWorkItemId(
        apdCommonInfo.getWorkItemId());
    final UserInfoType userInfo = apdCommonInfo.getTargetUserInfo()
        .getUserInfo();
    workProcessKeyPrivateRequest.getWPKeyRequest().setOrgId(
        Long.valueOf(userInfo.getOrgID()).longValue());
    workServiceClient.removeWorkProcessKey(insCoCode,
        workProcessKeyPrivateRequest);
  }

  private void logAndRethrowAsAssignmentDeliveryException(final Integer error,
      final Exception e)
      throws AssignmentDeliveryException
  {
    if (error == null) {
      throw new IllegalArgumentException("Error number was not set");
    }
    mLogger.severe(e.getMessage());
    mLogger.severe((AppUtilities.getStackTraceString(e,
        INCLUDE_NESTED_STACKTRACE)));
    throw new AssignmentDeliveryException(error.intValue(), getClass()
        .getName(), "deliverAssignment", "Error delivering assignment", e);
  }

  protected abstract void processContextForWorkCenterAttachments(
      final APDDeliveryContextType context,
      final MitchellEnvelopeHelper inboundPayload,
      final MitchellEnvelopeHelper outboundPayload,
      final UserInfoType userInfo, final AnnotatedLogger log,
      final boolean isReDispatch)
      throws Exception;

  protected AdditionalAppraisalAssignmentInfoDocument findAdditionalAppraisalAssignmentInfoDocument(
      final MitchellEnvelopeDocument meDoc, final String workItemID)
      throws Exception
  {
    AdditionalAppraisalAssignmentInfoDocument aaaInfoDoc = handlerUtility
        .getAAAInfoDocFromMitchellEnv(meDoc, workItemID,
            DO_NOT_RETURN_EXCEPTION);
    return aaaInfoDoc;
  }

  /**
   * @param outboundPayload
   * @param inboundPayload
   * @param workItemId
   * @param context
   * @param log
   * @param isRedispatch
   * @throws MitchellException
   * @throws XmlException
   */
  private void processContextForCiecaBms(
      final MitchellEnvelopeHelper outboundPayload,
      final MitchellEnvelopeHelper inboundPayload, final String workItemId,
      final APDDeliveryContextType context, final AnnotatedLogger log,
      final boolean isRedispatch)
      throws MitchellException, XmlException
  {
    final EnvelopeBodyType bmsEnvelopeBody = outboundPayload
        .addNewEnvelopeBody(isRedispatch ? UPDATED_ASSIGNMENT_BMS
            : ASSIGNMENT_BMS, handlerUtility
            .getCiecaDocumentFromMitchellEnvelope(inboundPayload.getDoc(),
                AssignmentDeliveryConstants.ME_METADATA_CIECA_IDENTIFIER,
                workItemId), CIECA_BMS_ASG_XML);
    bmsEnvelopeBody.getMetadata().setXmlBeanClassname(
        "com.cieca.bms.CIECADocument");
  }

  /**
   * @param context
   * @param log
   * @param userInfo
   * @param inboundPayload
   * @param outboundPayload
   * @param isSupplement
   * @throws MitchellException
   * @throws RemoteException
   */
  private void processContextForWorkProcessInitiationMessage(
      final APDDeliveryContextType context, final AnnotatedLogger log,
      final UserInfoType userInfo, final MitchellEnvelopeHelper inboundPayload,
      final MitchellEnvelopeHelper outboundPayload, final boolean isSupplement)
      throws MitchellException, RemoteException
  {
    final WorkProcessInitiationMessageDocument wpimDocument = WorkProcessInitiationMessageDocument.Factory
        .newInstance();
    final WorkProcessInitiationMessage wpim = wpimDocument
        .addNewWorkProcessInitiationMessage();
    final String companyCode = extractCompanyCodefromUserInfo(userInfo);
    wpim.setVersion(WORK_PROCESS_INIT_MESSAGE);
    wpim.setWorkProcessCollaborator(companyCode); // **Change to companyCode for ArtifactUpload w/o Assignment 
    final DefinitionType definition = wpim.addNewDefinition();
    resolveWorkProcessType(isSupplement, definition);
    definition.setVersion(DEFINITION_VERSION);
    handleWorkProcessKeyForNewWorkProcess(context, wpim, log);
    final RoleCollaboratorPairType sourceRoleCollaboratorPair = wpim
        .addNewRoleMapping().addNewRoleCollaboratorPair();
    sourceRoleCollaboratorPair.setRole(CARRIER);
    sourceRoleCollaboratorPair.setCollaborator(companyCode); // **Change to companyCode for ArtifactUpload w/o Assignment
    final EnvelopeBodyType wpimBody = addRoutingDetails(context,
        outboundPayload, wpimDocument, wpim);
    wpimBody.getMetadata().setXmlBeanClassname(
        "com.mitchell.schemas.WorkProcessInitiationMessageDocument");
  }

  protected abstract void resolveWorkProcessType(final boolean isSupplement,
      final DefinitionType definition);

  /**
   * @param context
   * @param outboundPayload
   * @param wpimDocument
   * @param wpim
   * @return wpimBody
   * @throws MICommonException
   * @throws MitchellException
   */
  private EnvelopeBodyType addRoutingDetails(
      final APDDeliveryContextType context,
      final MitchellEnvelopeHelper outboundPayload,
      final WorkProcessInitiationMessageDocument wpimDocument,
      final WorkProcessInitiationMessage wpim)
      throws MICommonException, MitchellException
  {
    final RoleCollaboratorPairType targetRoleCollaboratorPair = wpim
        .getRoleMapping().addNewRoleCollaboratorPair();
    targetRoleCollaboratorPair.setRole(getTargetCollaboratorRole());// SHOP
    targetRoleCollaboratorPair.setCollaborator(String
        .valueOf(getTargetColaboratorIdFromContext(context)));
    final EnvelopeBodyType wpimBody = outboundPayload.addNewEnvelopeBody(
        WORK_PROCESS_INITIATION_MESSAGE, wpimDocument,
        WORK_PROCESS_INITIATION_MESSAGE);
    return wpimBody;
  }

  /**
   * Returns target collaborator
   * 
   * @return
   */
  protected abstract String getTargetCollaboratorRole();

  /**
   * @param context
   * @return
   * @throws MICommonException
   * @throws MitchellException
   */
  protected abstract long getTargetColaboratorIdFromContext(
      final APDDeliveryContextType context)
      throws MICommonException, MitchellException;

  /**
   * @param context
   * @return
   * @throws RemoteException
   * @throws MICommonException
   */
  protected CrossOverUserInfoDocument fishOutXOverInfo(
      final APDDeliveryContextType context)
      throws RemoteException, MICommonException
  {
    final BaseAPDCommonType apdCommonInfo = context
        .getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
    final CrossOverUserInfoDocument crossOverUserInfo = userInfoClient
        .getCrossOverUserInfo(apdCommonInfo.getInsCoCode(), apdCommonInfo
            .getTargetUserInfo().getUserInfo().getUserID());
    return crossOverUserInfo;
  }

  /**
   * @param crossOverUserInfo
   */
  protected long getTargetColaboratorId(
      final CrossOverUserInfoDocument crossOverUserInfo)
  {
    long returnValue = 0;
    final OnlineUserType[] onlineUsersArray = crossOverUserInfo
        .getCrossOverUserInfo().getOnlineInfo().getOnlineOffice()
        .getOnlineUsersArray();
    for (int i = 0; i < onlineUsersArray.length; i++) {
      final OnlineUserType onlineUser = onlineUsersArray[i];
      if (mLogger.isLoggable(Level.FINE)) {
        mLogger.fine("Online User Details" + String.valueOf(onlineUser));
      }
      if (onlineUser.getOnlineUserOrgCode().getIsPrimaryUser()) {
        if (mLogger.isLoggable(Level.FINE)) {
          mLogger.fine("Last Online User made the cut as primary user "
              + onlineUser.getOnlineUserOrgID());
        }
        returnValue = onlineUser.getOnlineUserOrgID();
      }
      if (mLogger.isLoggable(Level.FINE)) {
        mLogger.fine("Last Online User did not make the cut as primary user "
            + onlineUser.getOnlineUserOrgID());
      }
    }
    return returnValue;
  }

  protected abstract void processContextForWorkProcessUpdateMessage(
      final APDDeliveryContextType apdDeliveryContext,
      final AnnotatedLogger log, final UserInfoType userInfo,
      final MitchellEnvelopeHelper inboundPayload,
      final MitchellEnvelopeHelper outboundPayload, final Map workProcessKeys,
      final boolean isSupplement)
      throws MitchellException, RemoteException;

  /**
   * @param userInfo
   * @return
   * @throws RemoteException
   * @throws MitchellException
   */
  public String extractCompanyCodefromUserInfo(final UserInfoType userInfo)
      throws RemoteException, MitchellException
  {
    final OrgInfoDocument orgInfo = userInfoClient.getOrgInfo(
        userInfo.getOrgCode(), userInfo.getOrgCode(),
        UserInfoClient.ORG_TYPE_COMPANY);
    final String companyCode = orgInfo.getOrgInfo().getCompanyCode();
    return companyCode;
  }

  /**
   * @param context
   * @param wpim
   * @throws MitchellException
   */
  private void handleWorkProcessKeyForNewWorkProcess(
      final APDDeliveryContextType context,
      final WorkProcessInitiationMessage wpim, final AnnotatedLogger log)
      throws MitchellException
  {
    final BaseAPDCommonType apdCommonInfo = context
        .getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
    final WPKeyRequestDocument publicWorkProcessKeyRequest = workServiceClient
        .initWPKeyRequest(WorkProcessServiceClientAPI.CTX_PUBLIC_CLIENT_CLAIM_NUMBER);
    publicWorkProcessKeyRequest.getWPKeyRequest().setClientClaimNumber(
        apdCommonInfo.getClientClaimNumber());
    // Get the public key
    final String insCoCode = apdCommonInfo.getInsCoCode();
    String publicWorkProcessKey = workServiceClient.retrieveWorkProcessKey(
        insCoCode, publicWorkProcessKeyRequest);
    if (publicWorkProcessKey == null) {
      publicWorkProcessKey = UUIDFactory.getInstance().getUUID();
      workServiceClient.registerWorkProcessKey(insCoCode,
          publicWorkProcessKeyRequest, publicWorkProcessKey);
    }
    wpim.setPublicIndex(publicWorkProcessKey);
    if (log.isLoggable(Level.INFO)) {
      log.info(">> handleWorkProcessKeyForNewWorkProcess:: WPIM.public key after setting public key, publicWorkProcessKey= ["
          + wpim.getPublicIndex() + "]");
    }
    final WPKeyRequestDocument privateWorkProcessKeyRequest = workServiceClient
        .initWPKeyRequest(WorkProcessServiceClientAPI.CTX_PRIVATE_APPRAISAL_ASSIGNMENT);
    privateWorkProcessKeyRequest.getWPKeyRequest().setClientClaimNumber(
        apdCommonInfo.getClientClaimNumber());
    privateWorkProcessKeyRequest.getWPKeyRequest().setWorkAssignmentId(
        context.getAPDAppraisalAssignmentInfo().getTaskId());
    privateWorkProcessKeyRequest.getWPKeyRequest().setWorkItemId(
        apdCommonInfo.getWorkItemId());
    final UserInfoType userInfo = apdCommonInfo.getTargetUserInfo()
        .getUserInfo();
    privateWorkProcessKeyRequest.getWPKeyRequest().setOrgId(
        Long.valueOf(userInfo.getOrgID()).longValue());
    String privateWorkProcessKey = workServiceClient.retrieveWorkProcessKey(
        insCoCode, privateWorkProcessKeyRequest);
    if (privateWorkProcessKey == null) {
      privateWorkProcessKey = UUIDFactory.getInstance().getUUID();
      workServiceClient.registerWorkProcessKey(insCoCode,
          privateWorkProcessKeyRequest, privateWorkProcessKey);
    }
    wpim.setPrivateIndex(privateWorkProcessKey);
    if (log.isLoggable(Level.FINE)) {
      log.fine("WPIM.keys after setting public and private key ["
          + wpim.getPublicIndex() + "," + wpim.getPrivateIndex() + "]");
    }
  }

  private void setNameValuePairs(final MitchellEnvelopeHelper outboundPayload,
      final CIECADocument cieca, final APDDeliveryContextType context,
      String primaryContactType, Long estimateDocumentId,
      final boolean isRedispatch,
      final boolean isSupplement, final AnnotatedLogger log)
      throws MICommonException, RemoteException, MitchellException
  {
    if (estimateDocumentId != null) {
        outboundPayload.addEnvelopeContextNVPair(ESTIMATE_DOCUMENT_ID_NV_KEY, String.valueOf(estimateDocumentId));
    }

    if (CANCEL == gatherRequestStatusFrom(context)) {
      assignCommonNameValuePairsTo(outboundPayload, context, CANCELLED_STRING,
          UPDATE, isSupplement, primaryContactType, log);
    } else if (COMPLETE == gatherRequestStatusFrom(context)) {
      assignCommonNameValuePairsTo(outboundPayload, context, COMPLETED_STRING,
          UPDATE, isSupplement, primaryContactType, log);
    } else if (DISPATCH == gatherRequestStatusFrom(context)) {
      assignCommonNameValuePairsTo(outboundPayload, context, DISPATCHED_STRING,
          isRedispatch, isSupplement, primaryContactType, log);
      // This is only applicable to dispatch.
      if (getPassThruInfoFromCieca(cieca) != null) {
        final String[][] passThruInfo = { { "PassThroughInfo",
            getPassThruInfoFromCieca(cieca) } };
        assign(passThruInfo, outboundPayload, log);
      }
      if (getNotesFromBmsAssignmentMemo(cieca, log) != null) {
        final String[][] bmsNotesNVPair = { {
            isRedispatch ? "UpdatedNotes" : "Notes",
            getNotesFromBmsAssignmentMemo(cieca, log) } };
        assign(bmsNotesNVPair, outboundPayload, log);
        if (log.isLoggable(Level.FINE)) {
          log.fine("setNameValuePairs::  BmsAssignmentMemo->Notes [ "
              + bmsNotesNVPair + " ]");
        }
      }
    }
  }

  /**
   * @param outboundPayload
   * @param context
   * @param isSupplement
   * @param primaryContactType
   * @param log
   * @throws MitchellException
   * @throws MICommonException
   * @throws RemoteException
   */
  private void assignCommonNameValuePairsTo(
      final MitchellEnvelopeHelper outboundPayload,
      final APDDeliveryContextType context, final String status,
      final boolean isUpdate, final boolean isSupplement,
      String primaryContactType, final AnnotatedLogger log)
      throws MICommonException, MitchellException, RemoteException
  {
    final APDDeliveryContextType apdDeliveryContext = context;
    final APDAppraisalAssignmentInfoType apdAppraisalAssignmentInfo = apdDeliveryContext
        .getAPDAppraisalAssignmentInfo();
    final BaseAPDCommonType apdCommonInfo = apdAppraisalAssignmentInfo
        .getAPDCommonInfo();
    final boolean noDRPInfoFound = (apdCommonInfo.getTargetDRPUserInfo() == null);
    final String userCoCd = findUserCoCdforTargetUserInfo(apdCommonInfo,
        noDRPInfoFound);
    final String userId = findUserIdforTargetUserInfo(apdCommonInfo,
        noDRPInfoFound);
    String reviewerId = apdCommonInfo.getTargetUserInfo().getUserInfo()
        .getUserID();
    String reviewerCoCd = apdCommonInfo.getTargetUserInfo().getUserInfo()
        .getOrgCode();
    final String clientClaimNumber = context.getAPDAppraisalAssignmentInfo()
        .getAPDCommonInfo().getClientClaimNumber();
    if (noDRPInfoFound) { // Do Not Set ReviewerID for Staff Assignments
      reviewerId = "";
      reviewerCoCd = "";
    }
    if (log.isLoggable(Level.INFO)) {
      log.info("assignCommonNameValuePairsTo::  noDRPInfoFound [ "
          + noDRPInfoFound + " ]");
      log.info("assignCommonNameValuePairsTo::  status [ " + status + " ]");
      log.info("assignCommonNameValuePairsTo::  userCoCd [ " + userCoCd + " ]");
      log.info("assignCommonNameValuePairsTo::  userId [ " + userId + " ]");
      log.info("assignCommonNameValuePairsTo::  apdCommonInfo ["
          + apdCommonInfo + "]");
      log.info("assignCommonNameValuePairsTo::  primaryContactType ["
          + primaryContactType + "]");
    }
    final String[][] nameValuePairs = {
        { "MessageType", context.getMessageType() },
        { "MessageStatus", status },
        { isUpdate ? "UpdatedNotes" : "Notes", apdCommonInfo.getNotes() },
        { "MitchellCompanyCode", apdCommonInfo.getInsCoCode() },
        { "ClaimNumber", clientClaimNumber },
        { "MitchellWorkItemId", apdCommonInfo.getWorkItemId() },
        { "TaskId", apdAppraisalAssignmentInfo.getTaskId() + "", },
        { "ReviewerCoCd", reviewerCoCd },
        { "ShopId", getTargetColaboratorIdFromContext(context) + "" },
        { "ReviewerId", reviewerId }, { "UserCoCd", userCoCd },
        { "UserId", userId }, { "PrimaryContactType", primaryContactType } };
    // Add check for NULL getTargetUserInfo
    assign(nameValuePairs, outboundPayload, log);
    // Assign CancelReason if exists
    assignCancelReasonToOutboundPayload(outboundPayload, context, status, log);
    // Add LastModifiedDateTime to Name Value Pair
    assignLastModifiedDateTimeToNVPair(outboundPayload, context, log);
  }

  private void assignLastModifiedDateTimeToNVPair(
      final MitchellEnvelopeHelper outboundPayload,
      final APDDeliveryContextType context, final AnnotatedLogger log)
  {
    final MitchellEnvelopeHelper inboundEnvelope = extractInboundEnvelope(context);
    final String lastModifiedDateTime = inboundEnvelope
        .getEnvelopeContextNVPairValue(LAST_MODIFIED_DATE_TIME);

    if (log.isLoggable(Level.FINE)) {
      log.fine("lastModifiedDateTime retrieved from MitchellEnvelope is:"
          + lastModifiedDateTime);
    }

    if (lastModifiedDateTime != null) {
      final String[][] nameValuePair = { { LAST_MODIFIED_DATE_TIME,
          lastModifiedDateTime } };
      assign(nameValuePair, outboundPayload, log);
    }
  }

  /**
   * @param outboundPayload
   * @param context
   * @param status
   * @param log
   */
  protected void assignCancelReasonToOutboundPayload(
      final MitchellEnvelopeHelper outboundPayload,
      final APDDeliveryContextType context, final String status,
      final AnnotatedLogger log)
  {
    final boolean isCancelledCase = status.equalsIgnoreCase(CANCELLED_STRING);
    if (isCancelledCase) {
      if (log.isLoggable(Level.FINE)) {
        log.fine("*********** Entering assignCancelReasonToOutboundPayload::  ");
      }
      final MitchellEnvelopeHelper inboundEnvelope = extractInboundEnvelope(context);
      final String cancelReason = inboundEnvelope
          .getEnvelopeContextNVPairValue(CANCEL_REASON_STRING);
      final String cancelReasonDescription = inboundEnvelope
          .getEnvelopeContextNVPairValue(CANCEL_REASON_DESC_STRING);

      if ((cancelReason != null && cancelReason.length() > 0)
          || (cancelReasonDescription != null && cancelReasonDescription
              .length() > 0)) {
        final String[][] nameValuePairs = {
            { CANCEL_REASON_STRING, cancelReason },
            { CANCEL_REASON_DESC_STRING, cancelReasonDescription } };
        if (log.isLoggable(Level.INFO)) {
          log.info("*********** cancelReason =[ " + cancelReason + " ]");
          log.info("*********** cancelReasonDescription =[ "
              + cancelReasonDescription + " ]");
        }
        assign(nameValuePairs, outboundPayload, log);
      }
      if (log.isLoggable(Level.FINE)) {
        log.fine("*********** Exiting assignCancelReasonToOutboundPayload::  ");
      }
    }
  }

  protected abstract String findUserIdforTargetUserInfo(
      final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound);

  protected abstract String findUserCoCdforTargetUserInfo(
      final BaseAPDCommonType apdCommonInfo, final boolean noDRPInfoFound);

  private void assign(final String[][] nameValuePairs,
      final MitchellEnvelopeHelper outboundPayload, final AnnotatedLogger log)
  {
    final int NAME = 0;
    final int VALUE = 1;
    for (int i = 0; i != nameValuePairs.length; i++) {
      if (log.isLoggable(Level.FINE)) {
        log.fine("Adding name value pair to platform MEDoc ["
            + nameValuePairs[i][NAME] + "," + nameValuePairs[i][VALUE] + "]");
      }
      if (nameValuePairs[i][VALUE] != null) {
        outboundPayload.addEnvelopeContextNVPair(nameValuePairs[i][NAME],
            nameValuePairs[i][VALUE]);
      }
    }
  }

  private String getPassThruInfoFromCieca(final CIECADocument doc)
  {
    if (doc != null
        && doc.getCIECA() != null
        && doc.getCIECA().getAssignmentAddRq() != null
        && doc.getCIECA().getAssignmentAddRq().getDocumentInfo() != null
        && doc.getCIECA().getAssignmentAddRq().getDocumentInfo()
            .getReferenceInfo() != null
        && doc.getCIECA().getAssignmentAddRq().getDocumentInfo()
            .getReferenceInfo().getPassThroughInfo() != null) {
      return doc.getCIECA().getAssignmentAddRq().getDocumentInfo()
          .getReferenceInfo().getPassThroughInfo();
    } else {
      return null;
    }
  }

  private String getNotesFromBmsAssignmentMemo(final CIECADocument doc,
      AnnotatedLogger log)
  {
    String retval = null;
    if (doc != null && doc.getCIECA() != null
        && doc.getCIECA().getAssignmentAddRq() != null) {
      if (doc.getCIECA().getAssignmentAddRq().isSetVehicleDamageAssignment()) {
        String[] assMemos = doc.getCIECA().getAssignmentAddRq()
            .getVehicleDamageAssignment().getAssignmentMemoArray();
        if (assMemos != null && assMemos.length > 0) {
          StringBuffer sbuff = new StringBuffer("");
          String startCh = "";
          for (int i = 0; i < assMemos.length; i++) {
            if (assMemos[i] != null && assMemos[i].length() > 0) {
              sbuff.append(startCh);
              sbuff.append(assMemos[i]);
              startCh = " ";
            }
          }
          retval = sbuff.toString();
        }
      }
    }
    if (log.isLoggable(Level.FINE)) {
      log.fine("getNotesFromBmsAssignmentMemo::  retval [ " + retval + " ]");
    }
    return retval;
  }

  /**
   * @param apdCommonInfo
   * @return
   */
  private UserInfoType extractUserInfo(final BaseAPDCommonType apdCommonInfo)
  {
    final boolean isDrp = (apdCommonInfo.getTargetDRPUserInfo() != null);
    final UserInfoType userInfo = (isDrp ? apdCommonInfo.getTargetDRPUserInfo()
        .getUserInfo() : apdCommonInfo.getTargetUserInfo().getUserInfo());
    return userInfo;
  }

  private UserInfoType extractStaffUserInfo(
      final BaseAPDCommonType apdCommonInfo)
  {
    return (apdCommonInfo.getTargetUserInfo().getUserInfo());
  }

  private MitchellEnvelopeHelper extractInboundEnvelope(
      final APDDeliveryContextType context)
  {
    final MitchellEnvelopeType mitchellEnvelope = context
        .getAPDAppraisalAssignmentInfo().getAssignmentMitchellEnvelope()
        .getMitchellEnvelope();
    final MitchellEnvelopeHelper helper = MitchellEnvelopeHelper.newInstance();
    helper.getDoc().setMitchellEnvelope(mitchellEnvelope);
    return helper;
  }

  /**
   * @param outboundPayload
   * @param workItemId
   * @param userInfo
   * @return
   * @throws XmlException
   * @throws BadXmlFormatException
   * @throws ServiceLocatorException
   * @throws JMSException
   */
  private String postToMessageBus(final MitchellEnvelopeHelper outboundPayload,
      final String workItemId, final UserInfoDocument userInfo)
      throws XmlException, BadXmlFormatException, ServiceLocatorException,
      JMSException
  {
    final TrackingInfoDocument trackInfoDoc = WorkflowMsgUtil
        .createTrackingInfo(AssignmentDeliveryConstants.APPLICATION_NAME,
            "AssignmentDeliveryService",
            AssignmentDeliveryConstants.MODULE_NAME, workItemId, userInfo,
            (String) null);
    MitchellWorkflowMessageDocument mwmDoc = WorkflowMsgUtil
        .createWorkflowMessage(trackInfoDoc);
    mwmDoc = WorkflowMsgUtil.insertDataBlock(mwmDoc, outboundPayload.getDoc(),
        APD_DELIVERY_TYPE);
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger.info("About to post message [" + mwmDoc.toString() + "]");
    }
    return messagePostingAgent.postMessage(mwmDoc);
  }

  protected int gatherRequestStatusFrom(final APDDeliveryContextType context)
  {
    if (CANCEL_STRING.equals(context.getAPDAppraisalAssignmentInfo()
        .getMessageStatus())) {
      return CANCEL;
    } else if (DISPATCHED_STRING.equals(context.getAPDAppraisalAssignmentInfo()
        .getMessageStatus())) {
      return DISPATCH;
    } else if (COMPLETED_STRING.equals(context.getAPDAppraisalAssignmentInfo()
        .getMessageStatus())) {
      return COMPLETE;
    } else {
      throw new IllegalStateException(
          "Only cancel and dispatch are supported. Job with  ["
              + context.getAPDAppraisalAssignmentInfo().getMessageStatus()
              + "] status was sent instead. Set APDDeliveryContextType/APDAppraisalAssignmentInfo/MessageStatus correctly.");
    }
  }

  private void guard(final AbstractMsgBusDelHndlr my)
  {
    final StringBuffer reason = new StringBuffer();
    if (converter == null) {
      reason.append("Converter, ");
    }
    if (handlerUtility == null) {
      reason.append("HandlerUtility, ");
    }
    if (mLogger == null) {
      reason.append("Logger, ");
    }
    if (drBuilder == null) {
      reason.append("Dispatch Report Builder, ");
    }
    if (documentStoreClientProxy == null) {
      reason.append("Document Store Proxy, ");
    }
    // Uncomment as needed
    // if (estimatePackageClientProxy == null) {
    //     reason.append("EstimatePackage Client Proxy, ");
    // }
    if (messagePostingAgent == null) {
      reason.append("Message Posting Agent, ");
    }
    if (workServiceClient == null) {
      reason.append("Work Process Service Client, ");
    }
    if (appLoggerBridge == null) {
      reason.append("App Logger Bridge, ");
    }
    if (errorLoggingService == null) {
      reason.append("Error Logging Service, ");
    }
    if (userInfoClient == null) {
      reason.append("User Info Client");
    }
    if (reason.length() != 0) {
      throw new IllegalStateException(
          "Make sure all the dependencies are initailized. "
              + "The following dependencies are un-satisfied [" + reason + "]");
    }
  }

  /**
   * @param context
   * @param outboundPayload
   * @param isReDispatch
   * @return mieFile
   * @throws Exception
   */
  private File createMieFileFrom(final APDDeliveryContextType context,
      final MitchellEnvelopeHelper outboundPayload, final boolean isReDispatch)
      throws Exception
  {
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("AbstractMsgBusDelHndlrIn::createMieFileFrom, Before converter.transformBmsAsgToMieAsg.. ");
      mLogger
          .info("AbstractMsgBusDelHndlrIn::createMieFileFrom, isReDispatch= "
              + isReDispatch);
    }
    final File mieFile = converter.transformBmsAsgToMieAsg(context,
        outboundPayload.getDoc(), isReDispatch ? UPDATED_ASSIGNMENT_BMS
            : ASSIGNMENT_BMS);
    if (mLogger.isLoggable(Level.INFO)) {
      mLogger
          .info("AbstractMsgBusDelHndlrIn::createMieFileFrom, After converter.transformBmsAsgToMieAsg.. ");
    }
    return mieFile;

  }

  private void guard(final APDDeliveryContextType context)
  {
    if (context == null) {
      mLogger.severe("APD Context should not be NULL");
      throw new IllegalArgumentException("APD Context should not be NULL");
    }
    validate(context);
    mLogger.fine("Context is valid.");
    final int status = gatherRequestStatusFrom(context);
    if (status != CANCEL && status != DISPATCH && status != COMPLETE) {
      // not a valid status
      throw new IllegalArgumentException(
          "Currently this component can only handle CANCEL, COMPLETED and DISPATCHED assignments");
    }
    if (SUPPLEMENT.equals(context.getMessageType())
        || ORIGINAL_ESTIMATE.equals(context.getMessageType())) {
      // Do nothing, we have correct type.
    } else {
      throw new IllegalArgumentException(
          "Only SUPPLEMENT and ORGINAL_ESTIMATE messages are supported, got["
              + context.getMessageType() + "]");
    }
    // All is well.
  }

  /**
   * @param apdDeliveryContext
   * @param keys
   * @param log
   * @return
   * @throws MitchellException
   */
  private boolean gatherWorkPrecessKeysAndDecideReDispatch(
      final APDDeliveryContextType apdDeliveryContext, final Map keys,
      final AnnotatedLogger log)
      throws MitchellException
  {
    final BaseAPDCommonType apdCommonInfo = apdDeliveryContext
        .getAPDAppraisalAssignmentInfo().getAPDCommonInfo();
    final WPKeyRequestDocument workProcessKeyPublicRequest = workServiceClient
        .initWPKeyRequest(WorkProcessServiceClientAPI.CTX_PUBLIC_CLIENT_CLAIM_NUMBER);

    workProcessKeyPublicRequest.getWPKeyRequest().setClientClaimNumber(
        apdCommonInfo.getClientClaimNumber());
    final String publicWorkProcessKey = workServiceClient
        .retrieveWorkProcessKey(apdCommonInfo.getInsCoCode(),
            workProcessKeyPublicRequest);
    final int status = gatherRequestStatusFrom(apdDeliveryContext);

    if (log.isLoggable(Level.INFO)) {
      log.info(">> gatherWorkPrecessKeysAndDecideReDispatch:: Claim Number= ["
          + apdCommonInfo.getClientClaimNumber()
          + "] Public key query (Null for dispatch), publicWorkProcessKey= ["
          + publicWorkProcessKey
          + "] Request status within ADS (Cancel = 0, Dispatch = 1), RequestStatus= ["
          + status
          + "]"
          + " Message status coming in is ["
          + apdDeliveryContext.getAPDAppraisalAssignmentInfo()
              .getMessageStatus() + "]");
    }

    if (log.isLoggable(Level.INFO)) {
      log.info("Public key query" + workProcessKeyPublicRequest);
      log.info("Public key query result " + publicWorkProcessKey);
    }
    if (publicWorkProcessKey == null
        && (status == CANCEL || status == COMPLETE)) { // Cancel || Completed
                                                       // must have this
      throw new MitchellException(CLASS_NAME, CALLER_METHOD,
          "Error retrieving public work process key, " + "CompanyCode: [ "
              + apdCommonInfo.getInsCoCode() + " ], " + "ClaimNumber: [ "
              + apdCommonInfo.getClientClaimNumber() + " ].");
    } else {
      if (publicWorkProcessKey != null) {
        keys.put(PUBLIC, publicWorkProcessKey);
      } else {
        if (log.isLoggable(Level.INFO)) {
          log.info("New Dispatch case ");
        }
        return false;
      }
    }
    final WPKeyRequestDocument workProcessKeyPrivateRequest = workServiceClient
        .initWPKeyRequest(WorkProcessServiceClientAPI.CTX_PRIVATE_APPRAISAL_ASSIGNMENT);
    workProcessKeyPrivateRequest.getWPKeyRequest().setClientClaimNumber(
        apdCommonInfo.getClientClaimNumber());
    workProcessKeyPrivateRequest.getWPKeyRequest().setWorkAssignmentId(
        apdDeliveryContext.getAPDAppraisalAssignmentInfo().getTaskId());
    workProcessKeyPrivateRequest.getWPKeyRequest().setWorkItemId(
        apdCommonInfo.getWorkItemId());
    final UserInfoType userInfo = apdCommonInfo.getTargetUserInfo()
        .getUserInfo();
    workProcessKeyPrivateRequest.getWPKeyRequest().setOrgId(
        Long.valueOf(userInfo.getOrgID()).longValue());
    final String privateWorkProcessKey = workServiceClient
        .retrieveWorkProcessKey(apdCommonInfo.getInsCoCode(),
            workProcessKeyPrivateRequest);
    if (log.isLoggable(Level.INFO)) {
      log.info("Private key query" + workProcessKeyPrivateRequest);
      log.info("Private key query result " + privateWorkProcessKey);
    }
    if (privateWorkProcessKey != null) {
      keys.put(PRIVATE, privateWorkProcessKey);
      if (gatherRequestStatusFrom(apdDeliveryContext) == DISPATCH) {
        if (log.isLoggable(Level.INFO)) {
          log.info("Redispatch case ");
        }
        return true;// Redispatch
      }
    }
    if (log.isLoggable(Level.INFO)) {
      log.info("New Dispatch case ");
    }
    return false;
  }

  protected static boolean DUPLICATES_ALLOWED = true;
  protected DocStoreClientProxy documentStoreClientProxy;
  protected EstimatePackageClientProxy estimatePackageClientProxy;
  private MessagePostingAgent messagePostingAgent;
  private UserInfoServiceEJBRemote userInfoClient;
  private WorkProcessServiceClientAPI workServiceClient;
  private BmsConverterFactory bmsConverterFactory;

  public BmsConverterFactory getBmsConverterFactory()
  {
    return bmsConverterFactory;
  }

  public void setBmsConverterFactory(BmsConverterFactory bmsConverterFactory)
  {
    this.bmsConverterFactory = bmsConverterFactory;
  }

  public UserInfoServiceEJBRemote getUserInfoClient()
  {
    return userInfoClient;
  }

  public void setUserInfoClient(final UserInfoServiceEJBRemote userInfoClient)
  {
    this.userInfoClient = userInfoClient;
  }

  private void validate(final APDDeliveryContextType context)
  {
    context.validate();
  }

  private AnnotatedLogger annotate(final Logger logger)
  {
    return AnnotatedLogger.annotate(logger);
  }

  public DocStoreClientProxy getDocumentStoreClientProxy()
  {
    return documentStoreClientProxy;
  }

  public void setDocumentStoreClientProxy(
      final DocStoreClientProxy documentStoreClientProxy)
  {
    this.documentStoreClientProxy = documentStoreClientProxy;
  }

  public EstimatePackageClientProxy getEstimatePackageClientProxy()
  {
    return estimatePackageClientProxy;
  }

  public void setEstimatePackageClientProxy(
      final EstimatePackageClientProxy estimatePackageClientProxy)
  {
    this.estimatePackageClientProxy = estimatePackageClientProxy;
  }

  public Converter getConverter()
  {
    return converter;
  }

  public void setConverter(final Converter converter)
  {
    this.converter = converter;
  }

  public AbstractHandlerUtils getHandlerUtils()
  {
    return handlerUtility;
  }

  public void setHandlerUtils(final AbstractHandlerUtils handlerUtility)
  {
    this.handlerUtility = handlerUtility;
  }

  private static String MIE_DOCUMENT_TYPE = MIME_TEXT_TYPE;
  protected static final String CLASS_NAME = ExtractClassName
      .from(ApdIntAssDelHandlerImpl.class);

  public AbstractMsgBusDelHndlr()
  {
    super();
  }

  protected String getClassName()
  {
    return CLASS_NAME;
  }

  public void setMessagePostingAgent(
      final MessagePostingAgent messagePostingAgent)
  {
    this.messagePostingAgent = messagePostingAgent;
  }

  public MessagePostingAgent getMessagePostingAgent()
  {
    return messagePostingAgent;
  }

  public void setWorkServiceClient(
      final WorkProcessServiceClientAPI workServiceClient)
  {
    this.workServiceClient = workServiceClient;
  }

  public WorkProcessServiceClientAPI getWorkServiceClient()
  {
    return workServiceClient;
  }

  protected abstract boolean allowBMSAndAttachmentInMitchellEnvelope(
      final int status);

}