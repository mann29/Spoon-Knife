package com.mitchell.services.business.partialloss.appraisalassignment.mapping;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.xmlbeans.XmlException;
import com.cieca.bms.AddressType;
import com.cieca.bms.AdminInfoType;
import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.CommunicationsType;
import com.cieca.bms.ContactInfoType;
import com.cieca.bms.DocumentVerType;
import com.cieca.bms.EstimatorIDsTypeType;
import com.cieca.bms.EstimatorType;
import com.cieca.bms.ExteriorInteriorType.Color;
import com.cieca.bms.GenericPartyType;
import com.cieca.bms.LicenseType;
import com.cieca.bms.OrgInfoType;
import com.cieca.bms.PersonInfoType;
import com.cieca.bms.VehicleDescType;
import com.cieca.bms.VehicleInfoType;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.schemas.workassignment.AssigneeUserType;
import com.mitchell.schemas.workassignment.AssignorInfoType;
import com.mitchell.schemas.workassignment.CommQualiferCodeType;
import com.mitchell.schemas.workassignment.EventDefinitionListType;
import com.mitchell.schemas.workassignment.EventDefinitionType;
import com.mitchell.schemas.workassignment.LocationInfoType;
import com.mitchell.schemas.workassignment.LocationType;
import com.mitchell.schemas.workassignment.PrimaryIDsType;
import com.mitchell.schemas.workassignment.PriorityType;
import com.mitchell.schemas.workassignment.PropertyInfoType;
import com.mitchell.schemas.workassignment.ScheduleMethodType;
import com.mitchell.schemas.workassignment.SkillsListType;
import com.mitchell.schemas.workassignment.UserIDListType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentReferenceType;
import com.mitchell.schemas.workassignment.WorkAssignmentStatusType;
import com.mitchell.schemas.workassignment.WorkAssignmentType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.UserInfoUtils;
import com.mitchell.services.business.partialloss.monitor.MonitoringLogger;
import com.mitchell.services.technical.claim.client.ClaimServiceClient;
import com.mitchell.utils.misc.StringUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

/**
 * This class contains the implmentation of the: Standard Assignment BMS to Task
 * (Work Assignment) Datamap The requirements listed in the datamap are
 * documented throughout this class as: Req: xxxx, where xxx is as listed in the
 * map column labeled "Reqt ID".
 */
public final class WorkAssignmentMapper {
    private static Logger logger = Logger
            .getLogger("com.mitchell.services.business.partialloss.appraisalassignment.mapping");

    private static final int MAPPING_ERROR_CODE = AppraisalAssignmentConstants.BMS_TO_WA_MAPPING_ERROR;
    private static final String ME_BMS_ASSIGNMENT_ID = "CIECABMSAssignmentAddRq";
    private static final String ME_ADDITIONAL_TASK_CONST_ID = "AdditionalTaskConstraints";
    private static final String ME_ADDITIONAL_INF_CONST_ID = "AdditionalAppraisalAssignmentInfo";

    // Global Variable TYPE values
    private static final String GL_LEVEL_USER = "USER";
    private static final String GL_LEVEL_GROUP = "GROUP";
    private static final String GL_FORM_SIMPLE = "SIMPLE";
    private static final String GL_FORM_ENHANCED = "ENHANCED";
    private static final String GL_TYPE_ORIG = "ORIG";
    private static final String GL_TYPE_SUPP = "SUPP";
    private static final String GL_TYPE_OTHER = "OTHER";

    // Members
    private MitchellEnvelopeDocument meDoc = null;
    private AssignmentAddRqDocument.AssignmentAddRq addRq = null;
    private AdditionalTaskConstraintsDocument taskConstDoc = null;
    private AdditionalAppraisalAssignmentInfoDocument addAAInfoDoc = null;
    private String workItemId = null;
    private String companyCode = null;
    private String disposition = null;
    private String status = null;
    private  long claimId;
    private  long claimExposureId;
    private String claimNumberMask = null;
    private String bmsDocumentId = "";
    private String systemUserId = null;
    private String event = null;
    private String updatedById = null;

    private long taskID = 0L;

    private String global_TYPE = null;
    private String global_FORM = null;
    private String global_LEVEL = null;

    private IAppraisalAssignmentUtils appraisalAssignmentUtils ;
    private UserInfoUtils userInfoUtils;


    public void setAppraisalAssignmentUtils(IAppraisalAssignmentUtils appraisalAssignmentUtils){
         this.appraisalAssignmentUtils = appraisalAssignmentUtils;
    }
    public void setUserInfoUtils(UserInfoUtils userInfoUtils){
        this.userInfoUtils = userInfoUtils;
   }
    /**
     * This method drives the Mapping of a BMS assignment to a WorkAssignment
     * document.
     * 
     * Assumptions: it is assumed that the meDoc will contain: - a BMS
     * Assignment as a body item - optionally an AdditionalTaskConstraints
     * document as a body item - The following name/value pairs:
     * StdADWFConstants.MITCHELL_ENV_NAME_COMPANY_CODE,
     * StdADWFConstants.MITCHELL_ENV_NAME_MITCHELL_WORKITEMID,
     * StdADWFConstants.MITCHELL_ENV_NAME_CLAIM_MASK,
     * StdADWFConstants.MITCHELL_ENV_NAME_ASSIGNMENT_DOC_ID
     * 
     * 
     * @param meDoc
     *            MitchellEnvelope containing the BMS assignment and optionally
     *            an AdditionalTaskConstraints doc.
     * @param claimId
     *            Claim ID
     * @param claimExposureId
     *            ClaimExposure ID
     * @param documentId
     *            Document ID
     * @param disposition
     *            WorkAssignment Disposition
     * @param status
     *            WorkAssignment Status
     * @param event
     *            WorkAssignment Event
     * @param taskID
     *            WorkAssignment TaskID
     * @param claimNumberMask
     *            ClaimNumber Mask
     * @param updatedById
     *            Updated By
     * @return Returns the created WorkAssignment document.
     * @throws MitchellException
     *             Throws MitchellException to the caller in case of any
     *             exception arise.
     */
    public WorkAssignmentDocument createworkAssignmentDoc(final MitchellEnvelopeDocument meDoc,
            final long claimId, final long claimExposureId, final Long documentId, final String disposition,
            final String status, final String event, final long taskID, final String claimNumberMask,
            final String updatedById) throws MitchellException {
       initWorkAssignmentMapper(meDoc, claimId, claimExposureId, documentId,
                disposition, status, event, taskID, claimNumberMask, updatedById);
        return doMapping();
    }

    // ********************************************* Private
    // **************************************

    /**
     * Internal constructor
     */
    private void initWorkAssignmentMapper(final MitchellEnvelopeDocument _meDoc, final long _claimId, final long _clmExposureId,
            final Long documentId, final String _disposition, final String _status, final String _event,
            final long _taskID, final String _claimNumberMask, final String _updatedById) {
        this.meDoc = _meDoc;
        this.addRq = null;
        this.taskConstDoc = null;
        this.claimId = _claimId;
        this.claimExposureId = _clmExposureId;
        if (documentId != null) {
            this.bmsDocumentId = documentId.toString();
        }
        this.disposition = _disposition;
        this.status = _status;
        this.event = _event;
        this.taskID = _taskID;
        this.claimNumberMask = _claimNumberMask;
        this.updatedById = _updatedById;

    }

    // Internal mapping driver method
    private WorkAssignmentDocument doMapping() throws MitchellException {
        final WorkAssignmentDocument waDoc = WorkAssignmentDocument.Factory.newInstance();
        final WorkAssignmentType waType = waDoc.addNewWorkAssignment();
        try {
            initFromEnvelope(meDoc);
            if (logger.isLoggable(java.util.logging.Level.FINEST)) {
                logger.finest("Input workItemId: " + this.workItemId);
                logger.finest("Input BMS: " + this.addRq.toString());
            }
            validateRequired(this.addRq);
            initGlobals(this.addRq);
            // setting "Version"
            waType.setVersion(new BigDecimal("1.0"));
            // set "Type"
            if (this.global_TYPE.equals(GL_TYPE_ORIG)) {
                waType.setType("ORIGINAL_ESTIMATE");
            } else if (this.global_TYPE.equals(GL_TYPE_SUPP)) {
                waType.setType("SUPPLEMENT");
            }
            // setting "WorkAssignmentMemo"
            if (this.addRq.isSetVehicleDamageAssignment()) {
                final String[] assMemos = this.addRq.getVehicleDamageAssignment().getAssignmentMemoArray();
                if (assMemos != null && assMemos.length > 0) {
                    final StringBuffer sbuff = new StringBuffer("");
                    String startCh = "";
                    for (int i = 0; i < assMemos.length; i++) {
                        if (assMemos[i] != null && assMemos[i].length() > 0) {
                            sbuff.append(startCh);
                            sbuff.append(assMemos[i]);
                            startCh = " ";
                        }
                    }
                    final String s = StringUtilities.truncateMaxEnd(sbuff.toString(), 2000);
                    if (s.length() > 0) {
                        waType.setWorkAssignmentMemo(s);
                    }
                }
            }
            // settting "WorkAssignmentReference"
            final WorkAssignmentReferenceType waRef = waType.addNewWorkAssignmentReference();
            final WorkAssignmentReferenceType.ReferenceID waRefId = waRef.addNewReferenceID();
            waRefId.setID(new BigInteger(this.bmsDocumentId));
            waRefId.setReferenceType("APPRASIAL_ASSIGN_DOC");

            /*
             * waType.setStatus( WorkAssignmentStatusType.OPENED );
             * 
             * if ( this.GL_LEVEL_USER.equals( this.global_LEVEL ) ) {
             * waType.setDisposition( "DISPATCHED" ); } else if (
             * this.GL_LEVEL_GROUP.equals( this.global_LEVEL ) ) {
             * waType.setDisposition( "NEW" ); EventDefinitionListType subEvents
             * = waType.addNewSubscribedEvents(); subEvents.setDispatchedEvent(
             * new BigInteger( this.DISPATCH_EVENT_ID ) ); }else { throw new
             * MitchellException( MAPPING_ERROR_CODE, "WorkAssignmentMapper",
             * "createworkAssignmentDoc", "Unsupported LEVEL: " +
             * this.global_LEVEL ); }
             */
            waType.setDisposition(this.disposition);

            mapStatus(waType);
            mapSubscribedEvents(waType);
            mapWAEventInfo(waType);
            mapWAPrimaryIds(waType);
            mapWAPropertyInfo(waType);
            mapWALocation(waType);
            mapWAScheduleConstraints(waType);
            mapWACurrentSchedule(waType);
            mapWAAssignorInfo(waType);
            // holdInfo Changes
            // mapWAHoldInfo(waType);
            if (logger.isLoggable(java.util.logging.Level.FINEST)) {
                logger.finest("Output WA: " + waDoc);
            }
        } catch (final MitchellException e) {
            if (e.getType() < 1) {
                e.setType(MAPPING_ERROR_CODE);
            }
            throw e;
        } catch (final XmlException e) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "createworkAssignmentDoc",
                    "XML exception mapping BMS document.", e);
        } catch (final Exception e) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "createworkAssignmentDoc",
                    "Unexpected exception mapping BMS document.", e);
        }
        return waDoc;
    }

    /**
     * Get the AssignmentAddRq from the Mitchell Envelope.
     */
    private void initFromEnvelope(final MitchellEnvelopeDocument meDoc) throws MitchellException, XmlException {
        final MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(meDoc);
        // Assignment Rq
        EnvelopeBodyType envelopeBody = meHelper.getEnvelopeBody(ME_BMS_ASSIGNMENT_ID);
        if (envelopeBody == null) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "getAssignRqFromEnvelope",
                    "Mitchell envelope does not contain a " + ME_BMS_ASSIGNMENT_ID);
        }
        String contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);
        final CIECADocument ciecaDocument = CIECADocument.Factory.parse(contentString);
        this.addRq = ciecaDocument.getCIECA().getAssignmentAddRq();

        // Additional Task Constraints
        envelopeBody = meHelper.getEnvelopeBody(ME_ADDITIONAL_TASK_CONST_ID);
        if (envelopeBody != null) {
            contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);
            this.taskConstDoc = AdditionalTaskConstraintsDocument.Factory.parse(contentString);
        }

        // Additional Info
        envelopeBody = meHelper.getEnvelopeBody(ME_ADDITIONAL_INF_CONST_ID);
        if (envelopeBody != null) {
            contentString = meHelper.getEnvelopeBodyContentAsString(envelopeBody);
            this.addAAInfoDoc = AdditionalAppraisalAssignmentInfoDocument.Factory.parse(contentString);
        }

        // Mitchell Company Code
        this.companyCode = meHelper
                .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_COMPANY_CODE);
        if (this.companyCode == null) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "getAssignRqFromEnvelope",
                    "Mitchell envelope does not contain a MitchellCompanyCode NV pair.");
        }

        // WorkItemId
        this.workItemId = meHelper
                .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_MITCHELL_WORKITEMID);
        if (this.workItemId == null) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "getAssignRqFromEnvelope",
                    "Mitchell envelope does not contain a workItemId NV pair.");
        }

        // Claim Number Mask
        // this.claimNumberMask = meHelper.getEnvelopeContextNVPairValue(
        // AppraisalAssignmentConstants.MITCHELL_ENV_NAME_CLAIM_MASK );
        if (this.claimNumberMask == null) {
            this.claimNumberMask = "";
        }

        // Appraisal BMS Assignment DocumentId
        // this.bmsDocumentId = meHelper.getEnvelopeContextNVPairValue(
        // AppraisalAssignmentConstants.MITCHELL_ENV_NAME_ASSIGNMENT_DOC_ID );
        // if ( this.bmsDocumentId == null || this.bmsDocumentId.length() == 0 )
        // {
        /*
         * throw new MitchellException( MAPPING_ERROR_CODE,
         * "WorkAssignmentMapper", "getAssignRqFromEnvelope", "Mitchell envelope
         * does not contain an Appraisal Assignment DocumentId." );
         */
        // this.bmsDocumentId = "";
        // }
        // System User ID
        this.systemUserId = meHelper
                .getEnvelopeContextNVPairValue(AppraisalAssignmentConstants.MITCHELL_ENV_NAME_SYSTEM_USER_ID);
        if (this.systemUserId == null) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "getAssignRqFromEnvelope",
                    "Mitchell envelope does not contain a SystemUserId.");
        }

        // disposition
        // this.disposition = meHelper.getEnvelopeContextNVPairValue(
        // AppraisalAssignmentServiceConstants.MITCHELL_ENV_NAME_DISPOSITION );
        if (this.disposition == null || "".equals(disposition)) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "getAssignRqFromEnvelope",
                    "Mitchell envelope does not contain a Disposition.");
        }

        // status
        // this.status = meHelper.getEnvelopeContextNVPairValue(
        // AppraisalAssignmentServiceConstants.MITCHELL_ENV_NAME_STATUS );
        if (this.status == null || "".equals(status)) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "getAssignRqFromEnvelope",
                    "Mitchell envelope does not contain a Status.");
        }

        /*
         * if ( this.claimId == null ) this.claimId = "";
         * 
         * if ( this.claimExposureId == null ) this.claimExposureId = "";
         */
    }

    /**
     * Validate fields that must be set and throw an error if they are missing.
     */
    private void validateRequired(final AssignmentAddRqDocument.AssignmentAddRq rq) throws MitchellException {
        boolean isValid = true;
        String validationDesc = null;
        /*
         * Manoj- Deepak, I am commenting below mentioned code, we can discuss
         * if ( isValid && !rq.isSetVehicleDamageAssignment() ) { isValid =
         * false; validationDesc = "BMS is missing: VehicleDamageAssignment"; }
         * if ( isValid && !rq.getVehicleDamageAssignment().isSetEstimatorIDs()
         * ) { isValid = false; validationDesc = "BMS is missing:
         * VehicleDamageAssignment/EstimatorIDs"; }
         */
        if (isValid && !this.addRq.isSetClaimInfo()) {
            isValid = false;
            validationDesc = "BMS is missing: ClaimInfo";
        }
        if (!isValid) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "getAssignRqFromEnvelope",
                    validationDesc);
        }
    }

    /***************************************************************************
     * Initialize the global variables of the mapping.
     * **************************
     * ******************************************************************
     */
    private void initGlobals(final AssignmentAddRqDocument.AssignmentAddRq rq) throws MitchellException {
        // Req: PSEUDO 1: LEVEL
        this.global_LEVEL = null;
        if (rq.getVehicleDamageAssignment().isSetEstimatorIDs()) {
            final EstimatorIDsTypeType estIds = rq.getVehicleDamageAssignment().getEstimatorIDs();
            if (estIds != null) {
                if (estIds.isSetCurrentEstimatorID()) {
                    this.global_LEVEL = GL_LEVEL_USER;
                } else if (estIds.isSetRoutingIDInfo()) {
                    this.global_LEVEL = GL_LEVEL_GROUP;
                }
            }
        }

        /*
         * Deepak : This is being commented because in Jetta the assignment can
         * come without "estimator & dispatch center" if ( this.global_LEVEL ==
         * null ) { throw new MitchellException( MAPPING_ERROR_CODE,
         * "WorkAssignmentMapper", "initGlobals", "Missing source data error.
         * Req: PSEUDO 1: LEVEL. No data present in BMS." ); }
         */

        // Req: PSEUDO 2: FORM
        if (this.taskConstDoc != null) {
            this.global_FORM = GL_FORM_ENHANCED;
        } else {
            this.global_FORM = GL_FORM_SIMPLE;
        }

        // Req: PSEUDO 3: TYPE - ver 1.6
        this.global_TYPE = GL_TYPE_OTHER;
        final DocumentVerType docVer[] = rq.getDocumentInfo().getDocumentVerArray();
        if (null != docVer && docVer.length > 0) {
            if (docVer[0].isSetDocumentVerCode()) {
                final String s = docVer[0].getDocumentVerCode();
                if (s != null) {
                    if ("EM".equals(s)) {
                        this.global_TYPE = GL_TYPE_ORIG;
                    } else if ("SV".equals(s)) {
                        this.global_TYPE = GL_TYPE_SUPP;
                    }
                }
            }
        }

        if (logger.isLoggable(java.util.logging.Level.FINE)) {
            logger.fine("GLOBALS: Level=" + this.global_LEVEL + ", Form=" + this.global_FORM + ", Type="
                    + this.global_TYPE);
        }
    }

    /***************************************************************************
     * Main WorkAssignment Status mapping.
     * **************************************
     * ******************************************************
     */
    private void mapStatus(final WorkAssignmentType waType) {
        if (this.status.equalsIgnoreCase("OPENED")) {
            waType.setStatus(WorkAssignmentStatusType.OPENED);
        } else if (this.status.equalsIgnoreCase("ClOSED")) {
            waType.setStatus(WorkAssignmentStatusType.CLOSED);
        } else {
            // for cancelled
            waType.setStatus(WorkAssignmentStatusType.CANCELLED);
        }
    }

    /***************************************************************************
     * HoldInfo mapping
     * *********************************************************
     * ***********************************
     */
    /*
     * private void mapWAHoldInfo(WorkAssignmentType waType) { HoldInfoType
     * waHoldInfo = null; OnHoldInfoType meHoldInfo = null;
     * 
     * if (waType.isSetHoldInfo()) waHoldInfo = waType.getHoldInfo(); else
     * waHoldInfo = waType.addNewHoldInfo();
     * 
     * if (this.taskConstDoc != null) { if
     * (this.taskConstDoc.getAdditionalTaskConstraints() != null &&
     * this.taskConstDoc.getAdditionalTaskConstraints().isSetOnHoldInfo()) {
     * 
     * meHoldInfo =
     * this.taskConstDoc.getAdditionalTaskConstraints().getOnHoldInfo(); if
     * (meHoldInfo.getOnHoldInd() != null &&
     * !"".equals(meHoldInfo.getOnHoldInd()))
     * waHoldInfo.setHoldInd(meHoldInfo.getOnHoldInd()); if
     * (meHoldInfo.getOnHoldReasonCode() != null &&
     * !"".equals(meHoldInfo.getOnHoldReasonCode()))
     * waHoldInfo.setHoldReasonCode(meHoldInfo.getOnHoldReasonCode()); if
     * (meHoldInfo.getOnHoldReasonNotes() != null &&
     * !"".equals(meHoldInfo.getOnHoldReasonNotes()))
     * waHoldInfo.setHoldReasonNotes(meHoldInfo.getOnHoldReasonNotes()); if
     * (meHoldInfo.getOnHoldUpdatedBy() != null &&
     * !"".equals(meHoldInfo.getOnHoldUpdatedBy()))
     * waHoldInfo.setHoldUpdatedBy(meHoldInfo.getOnHoldUpdatedBy()); if
     * (meHoldInfo.getOnHoldUpdatedByDateTime() != null)
     * waHoldInfo.setHoldUpdatedByDateTime
     * (meHoldInfo.getOnHoldUpdatedByDateTime()); }
     * 
     * else { if (waType.isSetHoldInfo()) { waType.unsetHoldInfo(); } } } }
     */

    /***************************************************************************
     * Main WorkAssignment Subscribed Events mapping.
     * ***************************
     * *****************************************************************
     */
    private void mapSubscribedEvents(final WorkAssignmentType waType) throws Exception {
        HashMap map = new HashMap();
      //  final AppraisalAssignmentUtils appraisalAssignmentUtils = new AppraisalAssignmentUtils();
        map = appraisalAssignmentUtils.retrieveCarrierSettings(this.companyCode);
        if (map.size() > 0) {
            final EventDefinitionListType subEvents = waType.addNewSubscribedEvents();
            Object event = null;
            event = map.get("Create");
            if (event != null && !"".equals(event)) {
                subEvents.setCreatedEvent(new BigInteger((String) event));
            }
            event = map.get("Update");
            if (event != null && !"".equals(event)) {
                subEvents.setUpdatedEvent(new BigInteger((String) event));
            }
            event = map.get("Reassign");
            if (event != null && !"".equals(event)) {
                subEvents.setReassignedEvent(new BigInteger((String) event));
            }
            event = map.get("Cancel");
            if (event != null && !"".equals(event)) {
                subEvents.setCancelledEvent(new BigInteger((String) event));
            }
            event = map.get("Complete");
            if (event != null && !"".equals(event)) {
                subEvents.setCompletedEvent(new BigInteger((String) event));
            }
            event = map.get("Overdue");
            if (event != null && !"".equals(event)) {
                subEvents.setOverdueEvent(new BigInteger((String) event));
            }
            event = map.get("Dispatch");
            if (event != null && !"".equals(event)) {
                subEvents.setDispatchedEvent(new BigInteger((String) event));
            }
        }
    }

    /***************************************************************************
     * Main WorkAssignment PrimaryIDs mapping.
     * **********************************
     * **********************************************************
     */
    private void mapWAPrimaryIds(final WorkAssignmentType waType) throws MitchellException {
        final PrimaryIDsType mapTo = waType.addNewPrimaryIDs();
        // setting "ExternalWorkAssignmentID"
        if (this.global_FORM.equals(this.GL_FORM_ENHANCED)) {
            if (this.taskConstDoc.getAdditionalTaskConstraints().isSetExternalWorkAssignmentID()) {
                mapTo.setExternalWorkAssignmentID(this.taskConstDoc.getAdditionalTaskConstraints()
                        .getExternalWorkAssignmentID());
            }
        }
        // setting taskID if avilable.
        if (this.taskID > 0) {
            mapTo.setWorkAssignmentID(this.taskID);
        }
        // setting "WorkItemID"
        mapTo.setWorkItemID(this.workItemId);
        // setting "CompanyCode"
        mapTo.setCompanyCode(this.companyCode);
        // setting "GroupID"
        if (this.addRq.getVehicleDamageAssignment().isSetEstimatorIDs()) {
            final EstimatorIDsTypeType estIds = this.addRq.getVehicleDamageAssignment().getEstimatorIDs();
            if (estIds.isSetRoutingIDInfo()) {
                final String qCode = estIds.getRoutingIDInfo().getIDQualifierCode();
                if (!"WAGroupID".equals(qCode)) {
                    // Warning for unspecified reason in requirements mapping
                    logger.warning("Req: T.PID.GI - incorrect Group ID Qualifier in BMS Assignment: " + qCode
                            + ", CompanyCode=" + this.companyCode);
                }
                final String idn = estIds.getRoutingIDInfo().getIDNum();
                if (idn.length() <= 20) {
                    mapTo.setGroupID(idn);
                } else {
                    throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "initGlobals",
                            "Req: T.PID.GI. Length of groupID value exceeds maximum (20). Length is " + idn.length()
                                    + ", IDNum=" + idn);
                }
            }
        }
        // setting "ClaimNumber" & "ClaimSuffix"
        final String claimNum = this.addRq.getClaimInfo().getClaimNum();
        // Setting clientClaimNumber
        mapTo.setClientClaimNumber(claimNum);
        MonitoringLogger.doLog("WorkAssignmentMapper", "mapWAPrimaryIds", "Start Calling ClaimClientSupport.parseClaimNumberWithMask");
        final String[] cncs = ClaimServiceClient.parseClaimNumberWithMask(claimNum, this.claimNumberMask);
        MonitoringLogger.doLog("WorkAssignmentMapper", "mapWAPrimaryIds", "End Calling ClaimClientSupport.parseClaimNumberWithMask");
        if (cncs == null || cncs.length != 2 || cncs[0] == null || cncs[0].length() == 0) {
            throw new MitchellException(MAPPING_ERROR_CODE, "WorkAssignmentMapper", "initGlobals",
                    "Req: T.PID.CN. Could not get a parsed claim number from: [" + claimNum + "]");
        }
        mapTo.setClaimNumber(cncs[0]);
        mapTo.setClaimSuffix(cncs[1]);

        // setting "ClaimID" & "ClaimExposureID"
        // if(!"".equals(this.claimId))
        mapTo.setClaimID(this.claimId);
        // if(!"".equals(this.claimExposureId))
        mapTo.setClaimExposureID(this.claimExposureId);
    }

    /***************************************************************************
     * Main WorkAssignment PropertyInfo mapping.
     * ********************************
     * ************************************************************
     */
    private void mapWAPropertyInfo(final WorkAssignmentType waType) throws MitchellException {
        OrgInfoType orgInfo = null;
        PersonInfoType perInfo = null;
        VehicleDescType vehDesc = null;
        ContactInfoType ownerContactInfo = null;

        final AdminInfoType ait = this.addRq.getAdminInfo();
        final PropertyInfoType mapTo = waType.addNewPropertyInfo();
        final VehicleInfoType vit = this.addRq.getVehicleDamageAssignment().getVehicleInfo();

        // Extracting Data from BMS
        if (vit.isSetVehicleDesc()) {
            vehDesc = vit.getVehicleDesc();
        }
        if (ait.isSetOwner()) {
            if (ait.getOwner().getParty().isSetPersonInfo()) {
                perInfo = ait.getOwner().getParty().getPersonInfo();
            }
            if (ait.getOwner().getParty().isSetOrgInfo()) {
                orgInfo = ait.getOwner().getParty().getOrgInfo();
            }
            if (ait.getOwner().getParty().getContactInfoArray() != null
                    && ait.getOwner().getParty().getContactInfoArray().length > 0) {
                ownerContactInfo = ait.getOwner().getParty().getContactInfoArray()[0];
            }
        }

        // setting "Type"
        String pt = "VEHICLE";
        if (vehDesc != null) {
            if (vehDesc.isSetVehicleType()) {
                final String s = vehDesc.getVehicleType();
                if ("BV".equals(s)) {
                    pt = "BOAT";
                } else if ("MC".equals(s)) {
                    pt = "MOTORCYCLE";
                } else if ("RV".equals(s)) {
                    pt = "RV";
                }
            }
        }
        mapTo.setType(pt);

        // setting "Description" which is a combination of several tags like
        // model,submodel,year,color,
        // numberplate,drivable,memo etc.
        if (vehDesc != null) {
            final StringBuffer sbuff = new StringBuffer("");
            String year = "";
            if (vehDesc.isSetModelYear()) {
                final Calendar cal = vehDesc.getModelYear();
                if (cal != null) {
                    final int yyyy = cal.get(Calendar.YEAR);
                    if (yyyy > 1900) {
                        year = String.valueOf(yyyy);
                    }
                }
            }
            // Make,Model,Submodel,Year etc.
            final String make = vehDesc.isSetMakeDesc() ? vehDesc.getMakeDesc() : "";
            final String model = vehDesc.isSetModelName() ? vehDesc.getModelName() : "";
            final String subModel = vehDesc.isSetSubModelDesc() ? vehDesc.getSubModelDesc() : "";
            String preStr = "";
            if (year.length() > 0) {
                sbuff.append(year);
                preStr = " ";
            }
            if (make.length() > 0) {
                sbuff.append(preStr + make);
                preStr = " ";
            }
            if (model.length() > 0) {
                sbuff.append(preStr + model);
                preStr = " ";
            }
            if (subModel.length() > 0) {
                sbuff.append(preStr + subModel);
                preStr = " ";
            }
            if (preStr.length() > 0) {
                preStr = ", ";
            }
            // Color
            if (vit.isSetPaint()) {
                if (vit.getPaint().isSetExterior()) {
                    final Color[] ca = vit.getPaint().getExterior().getColorArray();
                    if (ca != null && ca.length > 0 && ca[0].isSetColorName()) {
                        sbuff.append(preStr + ca[0].getColorName());
                    }
                }
            }
            if (preStr.length() > 0) {
                sbuff.append(".");
                preStr = " ";
            }
            // Number Plate
            if (vit.isSetLicense()) {
                String endStr = null;
                final LicenseType lic = vit.getLicense();
                if (lic.isSetLicensePlateStateProvince()) {
                    sbuff.append(preStr + "Plate: " + lic.getLicensePlateStateProvince());
                    preStr = " ";
                    endStr = ".";
                    if (lic.isSetLicensePlateNum()) {
                        sbuff.append(preStr + lic.getLicensePlateNum());
                        preStr = " ";
                        endStr = ".";
                    }
                } else if (lic.isSetLicensePlateNum()) {
                    sbuff.append(preStr + "Plate: " + lic.getLicensePlateNum());
                    preStr = " ";
                    endStr = ".";
                }
                if (endStr != null) {
                    sbuff.append(endStr);
                }
            }
            // Drivable
            if (vit.isSetCondition()) {
                if (vit.getCondition().isSetDrivableInd()) {
                    final String s = vit.getCondition().getDrivableInd();
                    if (s != null && s.length() > 0) {
                        if (s.charAt(0) == 'Y') {
                            sbuff.append(preStr + "Vehicle is drivable.");
                            preStr = " ";
                        } else if (s.charAt(0) == 'N') {
                            sbuff.append(preStr + "Vehicle is NOT drivable.");
                            preStr = " ";
                        } else if (s.charAt(0) == 'U') {
                            sbuff.append(preStr + "It is unknown if vehicle is drivable.");
                            preStr = " ";
                        }
                    }
                }
            }
            // Memo
            if (vehDesc.isSetVehicleDescMemo()) {
                sbuff.append(preStr + "Memo: " + vehDesc.getVehicleDescMemo());
            }
            // Seting the sbuff into description which is a combination of
            // several tags like model,submodel,year,color,
            // numberplate,drivable,memo etc.
            mapTo.setDescription(sbuff.toString());
        }

        // setting "PropertyOwner"
        com.mitchell.schemas.workassignment.PersonInfoType propertyOwner = null;
        if (perInfo != null) {
            propertyOwner = mapTo.addNewPropertyOwner();
            final com.mitchell.schemas.workassignment.PersonNameType propOwnerName = propertyOwner.addNewPersonName();
            mapPersonName(perInfo.getPersonName(), propOwnerName);
            if (!propOwnerName.isSetLastName() && orgInfo != null && orgInfo.isSetCompanyName()) {
                propOwnerName.setLastName(StringUtilities.truncateMaxEnd(orgInfo.getCompanyName(), 60));
            }
        } else if (orgInfo != null && orgInfo.isSetCompanyName()) {
            propertyOwner = mapTo.addNewPropertyOwner();
            final com.mitchell.schemas.workassignment.PersonNameType personName = propertyOwner.addNewPersonName();
            personName.setLastName(StringUtilities.truncateMaxEnd(orgInfo.getCompanyName(), 60));
        }

        CommunicationsType[] comms = null;
        CommunicationsType[] pcommsAddress = null;
        if (perInfo != null ) {
           
          if(perInfo.getCommunicationsArray() != null){ 
            pcommsAddress = perInfo.getCommunicationsArray();
           
                mapCommunicatonAddress(pcommsAddress, propertyOwner);
            }
            
            if (ownerContactInfo !=null && ownerContactInfo.getCommunicationsArray() != null) {
                mapCommunicationsContactInfo(ownerContactInfo.getCommunicationsArray(), propertyOwner);
            }
        } else if (orgInfo != null && orgInfo.getCommunicationsArray() != null) {
            comms = orgInfo.getCommunicationsArray();
        
                mapCommunications(comms, propertyOwner);
            }
      

        // setting "PropertyContact"
        if (ownerContactInfo != null && ownerContactInfo.isSetContactName()) {
            final com.mitchell.schemas.workassignment.PersonInfoType propertyContact = mapTo.addNewPropertyContact();
            final com.mitchell.schemas.workassignment.PersonNameType ctName = propertyContact.addNewPersonName();
            mapPersonName(ownerContactInfo.getContactName(), ctName);
            if (ownerContactInfo.getCommunicationsArray() != null) {
                mapCommunications(ownerContactInfo.getCommunicationsArray(), propertyContact);
            }
        }
    }

    /***************************************************************************
     * Main WorkAssignment Location mapping.
     * ************************************
     * ********************************************************
     */
    private void mapWALocation(final WorkAssignmentType waType) throws MitchellException {
        GenericPartyType locType = null;
        if (this.addRq.getAdminInfo().isSetVehicleLocationSite()) {
            locType = this.addRq.getAdminInfo().getVehicleLocationSite();
        } else if (this.addRq.getAdminInfo().isSetInspectionSite()) {
            locType = this.addRq.getAdminInfo().getInspectionSite();
        }

        if (locType != null) {
            // Initialize some needed source variables
            CommunicationsType[] comms = null;
            if (locType.getParty().isSetPersonInfo()) {
                comms = locType.getParty().getPersonInfo().getCommunicationsArray();
            } else if (locType.getParty().isSetOrgInfo()) {
                comms = locType.getParty().getOrgInfo().getCommunicationsArray();
            }

            // Work Assignemnt Schema Requires an Address in LocationInfoType
            CommunicationsType ctAddr = null;
            if (comms != null) {
                for (int i = 0; i < comms.length; i++) {
                    final com.cieca.bms.CommunicationsType tc = comms[i];
                    if (tc.isSetAddress()) {
                        ctAddr = comms[i];
                        break;
                    }
                }
            }

            if (ctAddr != null) {
                final LocationInfoType locationInfo = waType.addNewLocationInfo();
                // setting "Type"
                if (locType.getParty().isSetPersonInfo()) {
                    locationInfo.setType(LocationType.HOME);
                } else if (locType.getParty().isSetOrgInfo()) {
                    locationInfo.setType(LocationType.BUSINESS);
                }

                // Site Location ID
                if (this.addAAInfoDoc != null
                        && this.addAAInfoDoc.getAdditionalAppraisalAssignmentInfo() != null
                        && this.addAAInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails() != null
                        && this.addAAInfoDoc.getAdditionalAppraisalAssignmentInfo().getAssignmentDetails()
                                .isSetSiteLocationId()) {
                    locationInfo.setLocationSiteOrgId(this.addAAInfoDoc.getAdditionalAppraisalAssignmentInfo()
                            .getAssignmentDetails().getSiteLocationId());
                }

                // setting "LocationName"
                if (locType.getParty().isSetOrgInfo() && locType.getParty().getOrgInfo().isSetCompanyName()) {
                    locationInfo.setLocationName(locType.getParty().getOrgInfo().getCompanyName());
                }

                // setting "Address"
                final com.mitchell.schemas.workassignment.AddressType addr = locationInfo.addNewAddress();
                mapAddress(ctAddr.getAddress(), addr);

                // setting "LocationContact"
                if (locType.getParty().getContactInfoArray() != null
                        && locType.getParty().getContactInfoArray().length > 0) {
                    final ContactInfoType contactInfo = locType.getParty().getContactInfoArray()[0];
                    if (contactInfo.isSetContactName()) {
                        final com.mitchell.schemas.workassignment.PersonInfoType locContact = locationInfo
                                .addNewLocationContact();
                        final com.mitchell.schemas.workassignment.PersonNameType destName = locContact
                                .addNewPersonName();
                        mapPersonName(contactInfo.getContactName(), destName);
                        if (contactInfo.getCommunicationsArray() != null) {
                            mapCommunications(contactInfo.getCommunicationsArray(), locContact);
                        }
                    } // isSetContactName
                } // getContactInfoArray
            } // ctAddr
        } // locType
    }

    /***************************************************************************
     * Main WorkAssignment Schedule Constraints mapping.
     * ************************
     * ********************************************************************
     */
    private void mapWAScheduleConstraints(final WorkAssignmentType waType) throws MitchellException {
        com.mitchell.schemas.dispatchservice.ScheduleConstraintsType sourceSchedC = null;
        // com.mitchell.schemas.dispatchservice.SiteInfoType siteInfoC = null;
        final com.mitchell.schemas.workassignment.ScheduleConstraintsType destSchedC = waType
                .addNewScheduleConstraints();
        // com.mitchell.schemas.workassignment.SiteInfoType destSiteInfoC =
        // null;

        // getting "ScheduleConstraints" from AdditionalTaskConstraints
        // EnvelopBody(in BMS) into variable named sourceSchedC
        if (this.taskConstDoc != null) {
            if (this.taskConstDoc.getAdditionalTaskConstraints().isSetScheduleConstraints()) {
                sourceSchedC = this.taskConstDoc.getAdditionalTaskConstraints().getScheduleConstraints();
            }
            /*
             * if
             * (this.taskConstDoc.getAdditionalTaskConstraints().isSetSiteInfo
             * ()) { siteInfoC =
             * this.taskConstDoc.getAdditionalTaskConstraints().getSiteInfo();
             * destSiteInfoC = waType.addNewSiteInfo(); // setting "SiteInfo"
             * destSiteInfoC.setSiteName(siteInfoC.getSiteName());
             * destSiteInfoC.setSiteType(siteInfoC.getSiteType());
             * destSiteInfoC.setSiteLocationId(siteInfoC.getSiteLocationId()); }
             */
        }

        // setting "ScheduleMethod"

        destSchedC.setScheduleMethod(ScheduleMethodType.MANUAL);

        if (this.global_FORM.equals(this.GL_FORM_ENHANCED)) {
            if (sourceSchedC != null) {
                // Req: T.SC.PV (It's not really clear to me how to do this.
                // This is a guess.)
                // setting "Priority"
                if (sourceSchedC.isSetPriority()) {
                    final PriorityType pt = destSchedC.addNewPriority();
                    final String taskPriorityTypeStr = sourceSchedC.getPriority().getStringValue();
                    pt.setStringValue(taskPriorityTypeStr);
                    int priorityIntValue = 0;
                    if (taskPriorityTypeStr.equalsIgnoreCase("STANDARD_PRIORITY")) {
                        priorityIntValue = AppraisalAssignmentConstants.STANDARD_PRIORITY_INT;
                    } else if (taskPriorityTypeStr.equalsIgnoreCase("ELEVATED_PRIORITY")) {
                        priorityIntValue = AppraisalAssignmentConstants.ELEVATED_PRIORITY_INT;
                    } else if (taskPriorityTypeStr.equalsIgnoreCase("HIGH_PRIORITY")) {
                        priorityIntValue = AppraisalAssignmentConstants.HIGH_PRIORITY_INT;
                    } else if (taskPriorityTypeStr.equalsIgnoreCase("MUST_SEE_DATE")) {
                        priorityIntValue = AppraisalAssignmentConstants.MUST_SEE_DATE_PRIORITY_INT;
                    } else if (taskPriorityTypeStr.equalsIgnoreCase("MUST_SEE_TIME")) {
                        priorityIntValue = AppraisalAssignmentConstants.MUST_SEE_TIME_PRIORITY_INT;
                    }
                    pt.setPriorityValue(priorityIntValue);
                }
                // setting "Duration"
                if (sourceSchedC.isSetDuration()) {
                    destSchedC.setDuration(sourceSchedC.getDuration());
                }
                // settting "RequiredStartDateTime"
                if (sourceSchedC.isSetRequiredStartDateTime()) {
                    destSchedC.setRequiredStartDateTime(sourceSchedC.getRequiredStartDateTime());
                }
                // settting "RequiredEndDateTime"
                if (sourceSchedC.isSetRequiredEndDateTime()) {
                    destSchedC.setRequiredEndDateTime(sourceSchedC.getRequiredEndDateTime());
                }
                // setting "RequiredAssigneeList"
                if (sourceSchedC.isSetRequiredAssigneeList()) {
                    final String[] userIds = sourceSchedC.getRequiredAssigneeList().getUserIDArray();
                    if (userIds != null && userIds.length > 0) {
                        final UserIDListType uids = destSchedC.addNewRequiredAssigneeList();
                        for (int i = 0; i < userIds.length; i++) {
                            uids.addUserID(userIds[i]);
                        }
                    }
                }
                // setting "PreferredAssigneeList"
                if (sourceSchedC.isSetPreferredAssigneeList()) {
                    final String[] userIds = sourceSchedC.getPreferredAssigneeList().getUserIDArray();
                    if (userIds != null && userIds.length > 0) {
                        final UserIDListType uids = destSchedC.addNewPreferredAssigneeList();
                        for (int i = 0; i < userIds.length; i++) {
                            uids.addUserID(userIds[i]);
                        }
                    }
                }
                // setting "UnauthorizedAssigneeList"
                if (sourceSchedC.isSetUnauthorizedAssigneeList()) {
                    final String[] userIds = sourceSchedC.getUnauthorizedAssigneeList().getUserIDArray();
                    if (userIds != null && userIds.length > 0) {
                        final UserIDListType uids = destSchedC.addNewUnauthorizedAssigneeList();
                        for (int i = 0; i < userIds.length; i++) {
                            uids.addUserID(userIds[i]);
                        }
                    }
                }
                // setting "RequiredSkillsList"
                if (sourceSchedC.isSetRequiredSkillsList()) {
                    final String[] skills = sourceSchedC.getRequiredSkillsList().getSkillArray();
                    if (skills != null && skills.length > 0) {
                        final SkillsListType sks = destSchedC.addNewRequiredSkillsList();
                        for (int i = 0; i < skills.length; i++) {
                            sks.addSkill(skills[i]);
                        }
                    }
                }
                // settting "DueDateTime"
                if (sourceSchedC.isSetDueDateTime()) {
                    destSchedC.setDueDateTime(sourceSchedC.getDueDateTime());
                }

                // Preffered Schedule Information
                if (sourceSchedC.isSetPreferredScheduleDate()) {
                    destSchedC.setPreferredScheduleDate(sourceSchedC.getPreferredScheduleDate());
                }

                if (sourceSchedC.isSetPreferredScheduleStartTime()) {
                    destSchedC.setPreferredScheduleStartTime(sourceSchedC.getPreferredScheduleStartTime());
                }

                if (sourceSchedC.isSetPreferredScheduleEndTime()) {
                    destSchedC.setPreferredScheduleEndTime(sourceSchedC.getPreferredScheduleEndTime());
                }
            }
        }
    }

    /***************************************************************************
     * Main WorkAssignment Current Schedule mapping.
     * ****************************
     * ****************************************************************
     */
    private void mapWACurrentSchedule(final WorkAssignmentType waType) throws MitchellException {
        final com.mitchell.schemas.workassignment.ScheduleInfoType destSched = waType.addNewCurrentSchedule();

        if (this.global_LEVEL != null && this.global_LEVEL.equals(this.GL_LEVEL_USER)) {
            com.mitchell.schemas.workassignment.PersonInfoType assignee = null;
            final EstimatorType[] estimators = this.addRq.getAdminInfo().getEstimatorArray();
            EstimatorType estimator0 = null;
            if (estimators != null && estimators.length > 0 && estimators[0] != null) {
                estimator0 = estimators[0];

                // setting "Assignee"
                if (assignee == null) {
                    assignee = destSched.addNewAssignee();
                }
                final com.mitchell.schemas.workassignment.PersonNameType destName = assignee.addNewPersonName();
                if (estimator0.getParty().isSetPersonInfo()) {
                    mapPersonName(estimator0.getParty().getPersonInfo().getPersonName(), destName);
                }
                // If BMS does not contain person info then get it from EPD if
                // available
                else if (this.addRq.getVehicleDamageAssignment().getEstimatorIDs().isSetCurrentEstimatorID()) {
                    final String userId = this.addRq.getVehicleDamageAssignment().getEstimatorIDs()
                            .getCurrentEstimatorID();
                    mapUserInfoToPersonName(userId, this.companyCode, destName);
                }
                CommunicationsType[] comms = null;
                CommunicationsType[] pcommsAddress = null;
               
                if (estimator0.getParty().isSetPersonInfo() ) {
                    if(estimator0.getParty().getPersonInfo().getCommunicationsArray() != null){
                    pcommsAddress = estimator0.getParty().getPersonInfo().getCommunicationsArray();
                    mapCommunicatonAddress(pcommsAddress, assignee);
                    }
                    
                    if (estimator0.getParty().getContactInfoArray() != null && estimator0.getParty().getContactInfoArray().length > 0 && estimator0.getParty().getContactInfoArray(0) != null && estimator0.getParty().getContactInfoArray(0).getCommunicationsArray() != null) {
                        comms = estimator0.getParty().getContactInfoArray(0).getCommunicationsArray();
                        
                        if (assignee == null) {
                            assignee = destSched.addNewAssignee();
                        }
                        mapCommunicationsContactInfo(comms, assignee);
                    }
                } else if (estimator0.getParty().isSetOrgInfo()) {
                    if (estimator0.getParty().getOrgInfo().getCommunicationsArray() != null) {
                        comms = estimator0.getParty().getOrgInfo().getCommunicationsArray();
                
                    if (assignee == null) {
                        assignee = destSched.addNewAssignee();
                    }
                    mapCommunications(comms, assignee);
                }
                }


                // settting "AssigneeUserType"
                if (estimator0.isSetAffiliation()) {
                    final String aff = estimator0.getAffiliation();
                    if ("IN".equals(aff) || "Insurer".equals(aff)) {
                        destSched.setAssigneeUserType(AssigneeUserType.STAFF);
                    } else if ("IP".equals(aff) || "Independent Appraiser".equals(aff)) {
                        destSched.setAssigneeUserType(AssigneeUserType.INDEPENDENT_APP);
                    } else if ("69".equals(aff) || "Repair Facility".equals(aff)) {
                        destSched.setAssigneeUserType(AssigneeUserType.BODYSHOP);
                    } else {
                        destSched.setAssigneeUserType(AssigneeUserType.OTHER);
                    }
                }
            }

            // setting "AssigneeID"
            if (this.addRq.isSetVehicleDamageAssignment()) {
                if (this.addRq.getVehicleDamageAssignment().isSetEstimatorIDs()) {
                    if (this.addRq.getVehicleDamageAssignment().getEstimatorIDs().isSetCurrentEstimatorID()) {
                        final String ss = StringUtilities.truncateMaxEnd(this.addRq.getVehicleDamageAssignment()
                                .getEstimatorIDs().getCurrentEstimatorID(), 8);
                        destSched.setAssigneeID(ss);
                    }
                }
            }
        } /*
           * else if ( this.global_LEVEL.equals( this.GL_LEVEL_GROUP )) { //Do
           * nothing }
           */
        // setting scheduledateTime
        if (this.taskConstDoc.getAdditionalTaskConstraints().isSetScheduleInfo()) {
            if (this.taskConstDoc.getAdditionalTaskConstraints().getScheduleInfo().isSetScheduledDateTime()) {
                destSched.setScheduleStartDateTime(this.taskConstDoc.getAdditionalTaskConstraints().getScheduleInfo()
                        .getScheduledDateTime());
            }
        }
    }

    /***************************************************************************
     * Main WorkAssignment EventInfo mapping.
     * 
     * Added By: Nirmal Date:30/07/09
     * *******************************************
     * *************************************************
     */
    private void mapWAEventInfo(final WorkAssignmentType waType) throws MitchellException {
        final com.mitchell.schemas.workassignment.EventInfoType destEventInfo = waType.addNewEventInfo();

        // Setting EventDateTime as current DateTime
        destEventInfo.setEventDateTime(Calendar.getInstance());
        destEventInfo.setUpdatedByID(this.updatedById);
        if (this.event != null && !"".equals(this.event)) {
            destEventInfo.setEvent(EventDefinitionType.Enum.forString(this.event));
        }

        /*
         * EventDefinitionType.Enum eventDefinition; //Getting all the
         * Name/Value pairs NameValuePairType[] nameValuePairArray =
         * getNameValuePairTypeArray(); for(int
         * k=0;k<nameValuePairArray.length;k++){ NameValuePairType nameValuePair
         * = nameValuePairArray[k]; String nameBMS = nameValuePair.getName();
         * 
         * if("SystemUserId".equals(nameBMS)){
         * destEventInfo.setUpdatedByID(nameValuePair.getValueArray(0)); } else
         * if("EventMemo".equals(nameBMS)){
         * destEventInfo.setEventMemo(nameValuePair.getValueArray(0)); } else
         * if("EventDefinitionType".equals(nameBMS)){ eventDefinition =
         * EventDefinitionType.Enum.forString(nameValuePair.getValueArray(0));
         * destEventInfo.setEvent(eventDefinition); } }
         */
    }

    /***************************************************************************
     * Main WorkAssignment Assignor Information mapping.
     * ************************
     * ********************************************************************
     */
    private void mapWAAssignorInfo(final WorkAssignmentType waType) throws MitchellException, Exception {
        final AssignorInfoType assignorInfo = waType.addNewAssignorInfo();

        // Bug 193865  --- CSAA IVW AssignedBy  -- USE logdInUsrInfo (updatedById) NOT systemUserId !!!!
        // assignorInfo.setAssignorID(this.systemUserId);
        // final UserInfoDocument userInfo = retrieveUserInfo(this.companyCode, this.systemUserId); 
        
        String assignorId = null;
        if (this.updatedById != null && this.updatedById.length() > 0) {
        	assignorId = this.updatedById;
        } 
        else if (this.systemUserId != null && this.systemUserId.length() > 0) {
        	assignorId = this.systemUserId;	
        }
        if (assignorId != null ) {
            assignorInfo.setAssignorID(assignorId);            
            final UserInfoDocument userInfo = retrieveUserInfo(this.companyCode, assignorId);
            final UserInfoType userInfoType = userInfo.getUserInfo();
            final String firstName = userInfoType.getFirstName();
            final String lastName = userInfoType.getLastName();
            if ((firstName != null && !"".equals(firstName)) || (lastName != null && !"".equals(lastName))) {
                final com.mitchell.schemas.workassignment.PersonInfoType personInfo = assignorInfo.addNewAssignor();
                final com.mitchell.schemas.workassignment.PersonNameType personName = personInfo.addNewPersonName();
                if (firstName != null && !"".equals(firstName)) {
                    personName.setFirstName(firstName);
                }
                if (lastName != null && !"".equals(lastName)) {
                    personName.setLastName(lastName);
                }
            }        	
        }        
    }

    private UserInfoDocument retrieveUserInfo(final String companyCode, final String userId) throws MitchellException,
            Exception {

        final String methodName = "retrieveUserInfo";
        if (logger.isLoggable(Level.INFO)) {
            logger.info("*****Enter in method :" + methodName + "*****");
        }
        UserInfoDocument userInfoDoc = null;
        MonitoringLogger.doLog("WorkAssignmentMapper", "retrieveUserInfo", "Start Calling userInfoUtils.getUserInfoDoc for user :" + userId);
        userInfoDoc = this.userInfoUtils.getUserInfoDoc(companyCode, userId, "");
        MonitoringLogger.doLog("WorkAssignmentMapper", "retrieveUserInfo", "End Calling userInfoUtils.getUserInfoDoc for user :" + userId);
        if (userInfoDoc == null) {
            throw new MitchellException("WorkAssignmentMapper.java", "retrieveUserInfo",
                    "Received NULL user information document from UserInfo Service. CompanyCode : " + companyCode
                            + "\tUserID : " + userId);
        }

        if (logger.isLoggable(Level.INFO)) {
            logger.info("*****Exit from method :" + methodName + "*****");
        }
        return userInfoDoc;
    }

    /***************************************************************************
     * Map a BMS Communications Type to a Work Assignment Communications Type
     * ***
     * ***********************************************************************
     * ******************
     */
    private void mapCommunications(final com.cieca.bms.CommunicationsType[] comms,
            final com.mitchell.schemas.workassignment.PersonInfoType pInfo) throws MitchellException {

        boolean doEmail = true;
        boolean doAddress = true;
        for (int i = 0; i < comms.length; i++) {
            final com.cieca.bms.CommunicationsType tc = comms[i];

            // Email

            if (doEmail && tc.isSetCommEmail() && tc.getCommEmail() != null && tc.getCommEmail().length() > 0) {
                final com.mitchell.schemas.workassignment.CommunicationsType waComm = pInfo.addNewCommunications();
                waComm.setCommQualifier(CommQualiferCodeType.EM);
                waComm.setCommEmail(tc.getCommEmail());
                if (tc.isSetPreferredInd()) {
                    waComm.setPreferredInd(tc.getPreferredInd());
                }
                doEmail = false;
            }

            // Address

            else if (doAddress && tc.isSetAddress()) {
                final AddressType tAddr = tc.getAddress();

                final com.mitchell.schemas.workassignment.CommunicationsType waComm = pInfo.addNewCommunications();
                waComm.setCommQualifier(CommQualiferCodeType.AL);
                if (tc.isSetPreferredInd()) {
                    waComm.setPreferredInd(tc.getPreferredInd());
                }
                final com.mitchell.schemas.workassignment.AddressType addr = waComm.addNewAddress();

                mapAddress(tAddr, addr);
                doAddress = false;

            }

            // Mapping says to do first occurrence of each only.

            if (!doEmail && !doAddress) {
                break;
            }

        }

        // ----------------------------------------------------------------------------
        // Phone
        // General idea: Loop over the list looking for the first occurrence of
        // types
        // HP, WP, CP, WC. Map each first occurrence and mark that type and item
        // as having been mapped. If after the first pass through the list
        // we have not mapped all four types then loop through the list
        // again mapping any previously unmapped items (type of the source
        // does not matter) to the unmapped types.
        // In the end we will end up with the WorkAassign list with at most 1 of
        // each of the four types and a maximum of four items.
        // For example: Source list is: HP, WP, HP, CP, CP, CP
        // Destination list is: HP, WP, CP, WC (2nd HP being the source of the
        // WC)

        boolean hpDone = false;
        boolean wpDone = false;
        boolean cpDone = false;
        boolean wcDone = false;
        final Hashtable phHash = new Hashtable();

        for (int i = 0; i < comms.length; i++) {
            final CommunicationsType tc = comms[i];
            if (tc.isSetCommPhone()) {
                final String phtype = tc.getCommQualifier();
                if ("HP".equals(phtype) && !hpDone) {
                    phHash.put(phtype + String.valueOf(i), tc);
                    hpDone = true;
                } else if ("WP".equals(phtype) && !wpDone) {
                    phHash.put(phtype + String.valueOf(i), tc);
                    wpDone = true;
                } else if ("CP".equals(phtype) && !cpDone) {
                    phHash.put(phtype + String.valueOf(i), tc);
                    cpDone = true;
                } else if ("WC".equals(phtype) && !wcDone) {
                    phHash.put(phtype + String.valueOf(i), tc);
                    wcDone = true;
                }
            }
            if (phHash.size() == 4) {
                break;
            }
        }

        // 2nd loop to fill in that that mighe be missing

        for (int i = 0; i < comms.length; i++) {
            if (phHash.size() == 4) {
                break;
            }
            final CommunicationsType tc = comms[i];
            if (tc.isSetCommPhone()) {
                final String phtype = tc.getCommQualifier();
                final String key = phtype + String.valueOf(i);

                // This one is not already mapped

                if (!phHash.containsKey(key)) {
                    if (!hpDone) {
                        phHash.put("HP" + String.valueOf(i), tc);
                        hpDone = true;
                    } else if (!wpDone) {
                        phHash.put("WP" + String.valueOf(i), tc);
                        wpDone = true;
                    } else if (!cpDone) {
                        phHash.put("CP" + String.valueOf(i), tc);
                        cpDone = true;
                    } else if (!wcDone) {
                        phHash.put("WC" + String.valueOf(i), tc);
                        wcDone = true;
                    }
                }
            }
        }

        // Map what we have gathered.

        if (phHash.size() > 0) {
            final Set keys = phHash.keySet();
            final Iterator iter = keys.iterator();
            while (iter.hasNext()) {
                final String key = (String) iter.next();
                final CommunicationsType tc = (CommunicationsType) phHash.get(key);
                final com.mitchell.schemas.workassignment.CommunicationsType waComm = pInfo.addNewCommunications();

                waComm.setCommPhone(tc.getCommPhone());

                if (tc.isSetPreferredInd()) {
                    waComm.setPreferredInd(tc.getPreferredInd());
                }

                if (key.startsWith("HP")) {
                    waComm.setCommQualifier(CommQualiferCodeType.HP);
                } else if (key.startsWith("CP")) {
                    waComm.setCommQualifier(CommQualiferCodeType.CP);
                } else if (key.startsWith("WC")) {
                    waComm.setCommQualifier(CommQualiferCodeType.WC);
                } else {
                    waComm.setCommQualifier(CommQualiferCodeType.WP);
                }

            }
        }
    }

    /***************************************************************************
     * WorkAssignment PropertyInfo/PropertyOwner/Communications mapping.
     * ********
     * ******************************************************************
     * ******************
     */
    /*
     * Commented to resolve the codepro comments. private void
     * mapPropertyOwnerComm(com.mitchell.schemas.workassignment.PersonInfoType
     * propertyOwner, PersonInfoType perInfo, OrgInfoType orgInfo) throws
     * MitchellException {
     * 
     * // Get the communications info either from personInfo or orgInfo
     * 
     * CommunicationsType[] comms = null; if (perInfo != null &&
     * perInfo.getCommunicationsArray() != null) { comms =
     * perInfo.getCommunicationsArray(); } else if (orgInfo != null &&
     * orgInfo.getCommunicationsArray() != null) { comms =
     * orgInfo.getCommunicationsArray(); }
     * 
     * // If we have communications info then map it to the work assignment
     * 
     * if (comms != null) { mapCommunications(comms, propertyOwner); } }
     */
    /***************************************************************************
     * Map a BMS Communications Type to a Work Assignment Communications Type
     * ***
     * ***********************************************************************
     * ******************
     */
    private void mapCommunicationsContactInfo(final com.cieca.bms.CommunicationsType[] comms,
            final com.mitchell.schemas.workassignment.PersonInfoType pInfo) throws MitchellException {

        boolean doEmail = true;
        for (int i = 0; i < comms.length; i++) {
            final com.cieca.bms.CommunicationsType tc = comms[i];

            // Email

            if (doEmail && tc.isSetCommEmail() && tc.getCommEmail() != null && tc.getCommEmail().length() > 0) {
                final com.mitchell.schemas.workassignment.CommunicationsType waComm = pInfo.addNewCommunications();
                waComm.setCommQualifier(CommQualiferCodeType.EM);
                waComm.setCommEmail(tc.getCommEmail());
                if (tc.isSetPreferredInd()) {
                    waComm.setPreferredInd(tc.getPreferredInd());
                }
                doEmail = false;
            }

            // Mapping says to do first occurrence of each only.

            if (!doEmail) {
                break;
            }

        }

        // ----------------------------------------------------------------------------
        // Phone
        // General idea: Loop over the list looking for the first occurrence of
        // types
        // HP, WP, CP, WC. Map each first occurrence and mark that type and item
        // as having been mapped. If after the first pass through the list
        // we have not mapped all four types then loop through the list
        // again mapping any previously unmapped items (type of the source
        // does not matter) to the unmapped types.
        // In the end we will end up with the WorkAassign list with at most 1 of
        // each of the four types and a maximum of four items.
        // For example: Source list is: HP, WP, HP, CP, CP, CP
        // Destination list is: HP, WP, CP, WC (2nd HP being the source of the
        // WC)

        boolean hpDone = false;
        boolean wpDone = false;
        boolean cpDone = false;
        boolean wcDone = false;
        final Hashtable phHash = new Hashtable();

        for (int i = 0; i < comms.length; i++) {
            final CommunicationsType tc = comms[i];
            if (tc.isSetCommPhone()) {
                final String phtype = tc.getCommQualifier();
                if ("HP".equals(phtype) && !hpDone) {
                    phHash.put(phtype + String.valueOf(i), tc);
                    hpDone = true;
                } else if ("WP".equals(phtype) && !wpDone) {
                    phHash.put(phtype + String.valueOf(i), tc);
                    wpDone = true;
                } else if ("CP".equals(phtype) && !cpDone) {
                    phHash.put(phtype + String.valueOf(i), tc);
                    cpDone = true;
                } else if ("WC".equals(phtype) && !wcDone) {
                    phHash.put(phtype + String.valueOf(i), tc);
                    wcDone = true;
                }
            }
            if (phHash.size() == 4) {
                break;
            }
        }

        // 2nd loop to fill in that that mighe be missing

        for (int i = 0; i < comms.length; i++) {
            if (phHash.size() == 4) {
                break;
            }
            final CommunicationsType tc = comms[i];
            if (tc.isSetCommPhone()) {
                final String phtype = tc.getCommQualifier();
                final String key = phtype + String.valueOf(i);

                // This one is not already mapped

                if (!phHash.containsKey(key)) {
                    if (!hpDone) {
                        phHash.put("HP" + String.valueOf(i), tc);
                        hpDone = true;
                    } else if (!wpDone) {
                        phHash.put("WP" + String.valueOf(i), tc);
                        wpDone = true;
                    } else if (!cpDone) {
                        phHash.put("CP" + String.valueOf(i), tc);
                        cpDone = true;
                    } else if (!wcDone) {
                        phHash.put("WC" + String.valueOf(i), tc);
                        wcDone = true;
                    }
                }
            }
        }

        // Map what we have gathered.

        if (phHash.size() > 0) {
            final Set keys = phHash.keySet();
            final Iterator iter = keys.iterator();
            while (iter.hasNext()) {
                final String key = (String) iter.next();
                final CommunicationsType tc = (CommunicationsType) phHash.get(key);
                final com.mitchell.schemas.workassignment.CommunicationsType waComm = pInfo.addNewCommunications();

                waComm.setCommPhone(tc.getCommPhone());

                if (tc.isSetPreferredInd()) {
                    waComm.setPreferredInd(tc.getPreferredInd());
                }

                if (key.startsWith("HP")) {
                    waComm.setCommQualifier(CommQualiferCodeType.HP);
                } else if (key.startsWith("CP")) {
                    waComm.setCommQualifier(CommQualiferCodeType.CP);
                } else if (key.startsWith("WC")) {
                    waComm.setCommQualifier(CommQualiferCodeType.WC);
                } else {
                    waComm.setCommQualifier(CommQualiferCodeType.WP);
                }

            }
        }
    }

    private boolean mapCommunicatonAddress(final com.cieca.bms.CommunicationsType[] comms,
            final com.mitchell.schemas.workassignment.PersonInfoType pInfo) {

        boolean doAddress = true;
        for (int i = 0; i < comms.length; i++) {
            final com.cieca.bms.CommunicationsType tc = comms[i];
            if (tc.isSetAddress()) {

                final AddressType tAddr = tc.getAddress();

                final com.mitchell.schemas.workassignment.CommunicationsType waComm = pInfo.addNewCommunications();
                waComm.setCommQualifier(CommQualiferCodeType.AL);

                if (tc.isSetPreferredInd()) {
                    waComm.setPreferredInd(tc.getPreferredInd());
                }
                final com.mitchell.schemas.workassignment.AddressType addr = waComm.addNewAddress();

                mapAddress(tAddr, addr);
                doAddress = false;
            }

            if (!doAddress) {
                break;
            }
        }
        return doAddress;
    }

    /**
     * Map a BMS NameType to a WorkAssignment NameType
     */
    private void mapPersonName(final com.cieca.bms.PersonNameType sourceName,
            final com.mitchell.schemas.workassignment.PersonNameType destName) {
        if (sourceName.isSetNamePrefix()) {
            destName.setNamePrefix(sourceName.getNamePrefix());
        }
        if (sourceName.isSetFirstName()) {
            destName.setFirstName(sourceName.getFirstName());
        }
        if (sourceName.getMiddleNameArray().length > 0) {
            destName.setMiddleNameArray(new String[]{sourceName.getMiddleNameArray()[0]});
        }
        if (sourceName.isSetLastName()) {
            destName.setLastName(sourceName.getLastName());
        }
        if (sourceName.isSetNameSuffix()) {
            destName.setNameSuffix(sourceName.getNameSuffix());
        }
        if (sourceName.getAliasNameArray().length > 0) {
            destName.setAliasName(StringUtilities.truncateMaxEnd(sourceName.getAliasNameArray()[0], 35));
        }
    }

    /**
     * Map a UserInfo to a PersonName
     */
    private void mapUserInfoToPersonName(final String userId, final String coCode,
            final com.mitchell.schemas.workassignment.PersonNameType destName) {
        try {
            MonitoringLogger.doLog("WorkAssignmentMapper", "mapUserInfoToPersonName", "Start Calling userInfoUtils.getUserInfoDoc for user :" + userId);
            final UserInfoDocument userInfoDoc = this.userInfoUtils.getUserInfoDoc(coCode, userId, "1234567890");
            MonitoringLogger.doLog("WorkAssignmentMapper", "mapUserInfoToPersonName", "End Calling userInfoUtils.getUserInfoDoc for user :" + userId);
            if (userInfoDoc != null) {

                if (userInfoDoc.getUserInfo().isSetFirstName()) {
                    destName.setFirstName(userInfoDoc.getUserInfo().getFirstName());
                }

                if (userInfoDoc.getUserInfo().isSetLastName()) {
                    destName.setLastName(userInfoDoc.getUserInfo().getLastName());
                }

            }
        }

        // This is best effort, not required, so we can swallow the exception
        catch (final Exception e) {
            logger.warning("Exception ignored in mapUserInfoToPersonName: " + e.toString());
            logger.warning("UserId=" + userId + ", CoCode=" + coCode);
        }
    }

    /**
     * Map a BMS Address to a WorkAssignment Address
     */
    private void mapAddress(final com.cieca.bms.AddressType sourceAddr,
            final com.mitchell.schemas.workassignment.AddressType destAddr) {
        if (sourceAddr.isSetAddress1()) {
            destAddr.setAddress1(StringUtilities.truncateMaxEnd(sourceAddr.getAddress1(), 55));
        }
        if (sourceAddr.isSetAddress2()) {
            destAddr.setAddress2(StringUtilities.truncateMaxEnd(sourceAddr.getAddress2(), 55));
        }
        if (sourceAddr.isSetCity()) {
            destAddr.setCity(StringUtilities.truncateMaxEnd(sourceAddr.getCity(), 30));
        }
        if (sourceAddr.isSetCountryCode()) {
            destAddr.setCountryCode(StringUtilities.truncateMaxEnd(sourceAddr.getCountryCode(), 80));
        }
        if (sourceAddr.isSetPostalCode()) {
            destAddr.setPostalCode(StringUtilities.truncateMaxEnd(sourceAddr.getPostalCode(), 15));
        }
        if (sourceAddr.isSetStateProvince()) {
            destAddr.setStateProvince(StringUtilities.truncateMaxEnd(sourceAddr.getStateProvince(), 30));
        }
    }

}
