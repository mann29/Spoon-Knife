package com.mitchell.services.business.partialloss.appraisalassignment.ejb;

import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.ejb3.annotation.RemoteBinding;

import com.cieca.bms.CIECADocument;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.ItineraryViewDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EmailMessageDocument;
import com.mitchell.schemas.schedule.AssignTaskType;
import com.mitchell.schemas.schedule.ScheduleInfoXMLDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.AppraisalAssignmentServiceHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.RetriveSupReqDelegator;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AppraisalAssignmentDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.dto.AssignmentDeliveryServiceDTO;
import com.mitchell.services.business.partialloss.appraisalassignment.ejb.misc.AASExceptionIntercepter;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.submit.AASAssignScheduleSubmitEJB;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.EstimatePackageProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASErrorLogUtil;
import com.mitchell.services.business.partialloss.appraisalassignment.util.BeanLocator;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.util.SpringReferencedContextContainer;
import com.mitchell.services.core.errorlog.client.ErrorLoggingService;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.systemconfiguration.SystemConfiguration;
import com.mitchell.utils.logging.MIProcessingInstanceLogger;
import com.mitchell.utils.misc.AppUtilities;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

@Stateless(name = "AppraisalAssignmentService")
@Remote(AppraisalAssignmentServiceRemote.class)
@Local(AppraisalAssignmentServiceLocal.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@RemoteBinding(jndiBinding = "com.mitchell.services.business.partialloss.appraisalassignment.ejb.AppraisalAssignmentServiceRemote")
@LocalBinding(jndiBinding = "com.mitchell.services.business.partialloss.appraisalassignment.ejb.AppraisalAssignmentServiceLocal")
@Interceptors({ AASExceptionIntercepter.class })
public class AppraisalAssignmentServiceBean implements
		AppraisalAssignmentServiceLocal, AppraisalAssignmentServiceRemote,
		AppraisalAssignmentService {

	private static final String APPRAISAL_ASSIGNMENT_REMOTE_EJB_JNDI = "/AppraisalAssignmentClient/Remote/EJBJndi";
	private static final String APPRAISAL_ASSIGNMENT_REMOTE_PROVIDER_URL = "/AppraisalAssignmentClient/Remote/ProviderUrl";
	private static final String APPRAISAL_ASSIGNMENT_REMOTE_JNDI_FACTORY = "/AppraisalAssignmentClient/Remote/JNDIFactory";

	private static final String CLASS_NAME = AppraisalAssignmentServiceBean.class
			.getName();
	private static final Logger logger = Logger
			.getLogger(AppraisalAssignmentServiceBean.class.getName());

	private static final MIProcessingInstanceLogger miProcLogger = new MIProcessingInstanceLogger();

	private transient AppraisalAssignmentServiceHandler assignmentServiceHandler;

	@EJB
	protected AASAssignScheduleSubmitEJB assignScheduleSubmitEjb;
	
	@EJB
	protected AASExternalAccessor extAccess;
	
	protected EstimatePackageProxy estimatePackageProxy;
	
	protected MitchellEnvelopeHandler mitchellEnvelopeHandler;

	@Resource
	private SessionContext context;

	// Spring Referenced Counted Context Container
	private SpringReferencedContextContainer springContext = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mitchell.services.business.partialloss.appraisalassignment.ejb.
	 * AppraisalAssignmentService2#initialize()
	 */
	@PostConstruct
	public void initialize() {
		this.springContext = (SpringReferencedContextContainer) new BeanLocator();
		this.springContext.addContextReference();

		logger.info("AppraisalAssignmentServiceBean: Initializing");
		try {
			assignmentServiceHandler = (AppraisalAssignmentServiceHandler) BeanLocator
					.getBean("AppraisalAssignmentServiceHandler");
			logger.info("Succesfully initialized service handler");
			estimatePackageProxy = (EstimatePackageProxy) BeanLocator
					.getBean("EstimatePackageProxy");
			mitchellEnvelopeHandler = (MitchellEnvelopeHandler) BeanLocator
					.getBean("MitchellEnvelopeHandler");		

		} catch (IllegalAccessException e) {

			assignmentServiceHandler = null;

			logger.severe("AppraisalAssignmentServiceBean or AASExternalAccessor initialize exception: "
					+ AppUtilities
							.getCleansedAppServerStackTraceString(e, true));
		}

		logger.info("AppraisalAssignmentServiceBean: Initialized");

	}

	/**
	 * Remove the reference from the Spring Context for this EJB at time of EJB
	 * destruction.
	 */
	@PreDestroy
	public void releaseResources() {
		if (this.springContext != null) {
			this.springContext.removeContextReference();
		}
	}

	@AroundInvoke
	public Object miProcLoggerInterceptor(InvocationContext ctx)
			throws Exception {
		long startTime = System.currentTimeMillis();
		Object retval = ctx.proceed();

		if (miProcLogger.isLoggable()) {
			miProcLogger.logProcessingInfoTotalTime(
					AppraisalAssignmentConstants.MODULE_NAME, "NA",
					System.currentTimeMillis() - startTime, ctx.getMethod()
							.getName(), "SUCCESS", "");
		}

		return retval;
	}

	/**
	 * 
	 * Method added for errorlogging. Used in MAXIMA for Error Logging and
	 * throwing error back to the callee.
	 * 
	 * @param errorEvent
	 *            errorEvent
	 * @param errorMessage
	 *            errorMessage
	 * @param workItemID
	 *            workItemID
	 * @param claimNumber
	 *            claimNumber
	 * @param exception
	 *            exception
	 * @return
	 * @throws MitchellException
	 *             MitchellException
	 */
	private void logError(final String className, final String methodName,
			final int errorEvent, final String errorMessage,
			final Exception exception, final UserInfoDocument userInfoDocument)
			throws MitchellException {

		final String claimNumber = null;
		final String workItemID = null;
		final String msgDetail = null;

		AASErrorLogUtil objAASErrorLogUtil = null;
		try {
			objAASErrorLogUtil = (AASErrorLogUtil) BeanLocator
					.getBean("AASErrorLogUtil");
		} catch (IllegalAccessException ee) {
			objAASErrorLogUtil = null;
			logger.severe("SpringBean Exception trying to log another exception: "
					+ AppUtilities.getCleansedAppServerStackTraceString(ee,
							true));
		}

		if (objAASErrorLogUtil != null) {

			objAASErrorLogUtil.logError(errorEvent, className, methodName,
					ErrorLoggingService.SEVERITY.FATAL, errorMessage,
					msgDetail, exception, userInfoDocument, workItemID,
					claimNumber);

		} else {
			if (exception != null) {
				logger.severe("Unable to log exception: "
						+ AppUtilities.getCleansedAppServerStackTraceString(
								exception, true));
			}
			throw new MitchellException(errorEvent, className, methodName,
					errorMessage, exception);
		}

	}

	/**
	 * <p>
	 * This method retrieves a Supplement Request Doc in XML format for use as
	 * Email Notification or UI Print-Preview
	 * </p>
	 * 
	 * @param estimateDocId
	 *            Estimate DocID for original Estimate.
	 * @param estimatorOrgId
	 *            OrgId of the estimator
	 * @param reviewerOrgId
	 *            OrgId of the reviewer
	 * @return Supplement Request Doc in XML format.
	 * @throws MitchellException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String retrieveSupplementRequestXMLDoc(final long estimateDocId,
			final long estimatorOrgId, final long reviewerOrgId)
			throws MitchellException {

		EmailMessageDocument supplementRequestDoc = null;

		try {

			final RetriveSupReqDelegator supDel = new RetriveSupReqDelegator(
					estimateDocId, estimatorOrgId, reviewerOrgId);

			supplementRequestDoc = supDel.retriveSupplementRequest();
		} catch (final MitchellException me) {
			throw me;
		} catch (final Exception e) {

			AASCommonUtils aasCommonUtils = null;

			try {
				aasCommonUtils = (AASCommonUtils) BeanLocator
						.getBean("AASCommonUtils");
			} catch (IllegalAccessException e1) {
				logger.severe("Exception getting SpringBean for logging another exception: "
						+ AppUtilities.getCleansedAppServerStackTraceString(e1,
								true));
				aasCommonUtils = null;
			}

			if (aasCommonUtils != null) {
				aasCommonUtils
						.logAndThrowError(
								SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST
										.getCode(),
								"AppraisalAssignmentService",
								"retrieveSupplementRequestXMLDoc",
								SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST
										.getDescription() + "\n" + e, e, logger);
			} else {
				logger.severe("Exception: "
						+ AppUtilities.getCleansedAppServerStackTraceString(e,
								true));
				throw new MitchellException(
						SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST
								.getCode(),
						"AppraisalAssignmentService",
						"retrieveSupplementRequestXMLDoc",
						SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST
								.getDescription(), e);
			}
		}
		return supplementRequestDoc.toString();
	}

	// ============================================= MAXIMA Dispatch Board
	// Functionality =============================================

	/**
	 * This method cancels multiple assignments. This methods internally invokes
	 * the single cancelAppraisalAssignment method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param cancellationReason
	 *            - cancellationReason object
	 * @param notes
	 *            - notes object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public java.util.HashMap cancelMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final String cancellationReason, final String notes,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "cancelMultipleAppraisalAssignments";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(Level.FINE)) {
			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("WorkAssignmentTaskID_TCN with size::")
					.append(workAssignmentTaskID_TCN.size())
					.append("\ncancellationReason ::")
					.append(cancellationReason).append("\nnotes ::")
					.append(notes).append("\n assignorUserInfoDocument ::")
					.append(assignorUserInfoDocument.toString());
			logger.fine("Input Received ::\n" + localMethodParams);
		}

		final java.util.HashMap ret = new java.util.HashMap();
		final Set keySet = workAssignmentTaskID_TCN.keySet();
		int result = AppraisalAssignmentConstants.FAILURE;
		Long key = null;
		Long value = null;
		final java.util.Iterator itr = keySet.iterator();
		while (itr.hasNext()) {
			key = (Long) itr.next();
			value = (Long) workAssignmentTaskID_TCN.get(key);
			try {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Calling AAS->cancelAppraisalAssignment from AAS.cancelMultipleAppraisalAssignments for taskID::"
							+ key + "\tvalue::" + value);
				}
				result = getAppraisalAssignmentEJB()
						.cancelAppraisalAssignment_Requires_NewTX(
								key.longValue(), cancellationReason,
								value.longValue(), notes,
								assignorUserInfoDocument);
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Called AAS->cancelAppraisalAssignment from AAS.cancelMultipleAppraisalAssignments for taskID::"
							+ key + "\tvalue::" + value);
				}
			} catch (final Exception exception) {
				logger.severe("Error Message in " + METHOD_NAME + "\t:: "
						+ exception.getMessage());
				result = AppraisalAssignmentConstants.FAILURE;

				logError(
						CLASS_NAME,
						METHOD_NAME,
						AppraisalAssignmentConstants.ERROR_CANCELLING_MULTIPLE_AA,
						AppraisalAssignmentConstants.ERROR_CANCELLING_MULTIPLE_AA_MSG
								+ "\tTask ID:" + key, exception,
						assignorUserInfoDocument);
			}
			ret.put(key, Integer.valueOf(result));
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return ret;
	}

	/**
	 * This method accepts EstimateDocumentID and calls CARRHelper to Update
	 * Review Assignment for Supplement case.
	 * 
	 * @param relatedEstimateDocumentId
	 * @throws MitchellException
	 * 
	 */
	public void updateReviewAssignment(long relatedEstimateDocumentId,
			UserInfoDocument estimatorUserInfo, UserInfoDocument logdInUsrInfo)
			throws MitchellException {
		final String METHOD_NAME = "updateReviewAssignment";
		logger.entering(CLASS_NAME, METHOD_NAME);
		assignmentServiceHandler.updateReviewAssignment(
				relatedEstimateDocumentId, estimatorUserInfo, logdInUsrInfo);
		logger.exiting(CLASS_NAME, METHOD_NAME);
	}

	/**
	 * This method uncancels multiple assignments. This methods internally
	 * invokes the single uncancelAppraisalAssignment method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             - Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public java.util.HashMap unCancelMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "unCancelMultipleAppraisalAssignments";
		logger.entering(CLASS_NAME, METHOD_NAME);
		if (logger.isLoggable(Level.FINE)) {
			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("WorkAssignmentTaskID_TCN with size::")
					.append(workAssignmentTaskID_TCN.size())
					.append("\nassignorUserInfoDocument ::")
					.append(assignorUserInfoDocument.toString());
			logger.fine("Input Received ::\n" + localMethodParams);
		}
		final java.util.HashMap ret = new java.util.HashMap();
		final Set keySet = workAssignmentTaskID_TCN.keySet();
		int result = AppraisalAssignmentConstants.FAILURE;
		Long key = null;
		Long value = null;
		final java.util.Iterator itr = keySet.iterator();
		while (itr.hasNext()) {
			key = (Long) itr.next();
			value = (Long) workAssignmentTaskID_TCN.get(key);

			try {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Calling AAS->uncancelAppraisalAssignment from AAS.unCancelMultipleAppraisalAssignments for taskID::"
							+ key + "\tvalue::" + value);
				}
				result = getAppraisalAssignmentEJB()
						.uncancelAppraisalAssignment_Requires_NewTX(
								key.longValue(), value.longValue(),
								assignorUserInfoDocument);
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Calling AAS->uncancelAppraisalAssignment from AAS.unCancelMultipleAppraisalAssignments for taskID::"
							+ key + "\tvalue::" + value);
				}
			} catch (final Exception exception) {
				logger.severe("Error Message in " + METHOD_NAME + "\t:: "
						+ exception.getMessage());
				result = AppraisalAssignmentConstants.FAILURE;

				logError(
						CLASS_NAME,
						METHOD_NAME,
						AppraisalAssignmentConstants.ERROR_UNCANCELLING_MULTIPLE_AA,
						AppraisalAssignmentConstants.ERROR_UNCANCELLING_MULTIPLE_AA_MSG
								+ "\tTask ID:" + key, exception,
						assignorUserInfoDocument);
			}

			ret.put(key, Integer.valueOf(result));
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);

		return ret;
	}

	public String getExpertiseSkillsByVehicleType(final String vehicleType)
			throws MitchellException {
		final String METHOD_NAME = "getExpertiseSkillsByVehicleType";
		logger.entering(CLASS_NAME, METHOD_NAME);
		String expertiseSkill = assignmentServiceHandler
				.getExpertiseSkillsByVehicleType(vehicleType);
		logger.info("expertiseSkill in getExpertiseSkillsByVehicleType EJB:::"
				+ expertiseSkill);
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return expertiseSkill;
	}

	/**
	 * This method Assign/ReAssign with Assignee id and ScheduleInformation from
	 * Dispatch Board This methods internally invokes the single
	 * assignScheduleAppraisalAssignment_Requires_NewTX method or invokes
	 * parallel processing if the number of items in the requests meets the
	 * minimum parallel request size as defined in the service settings file.
	 * 
	 * @param scheduleInfoXMLDocument
	 *            - XmlObject object of ScheduleInfoXMLDocument.
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return java.util.HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public java.util.HashMap assignScheduleResourceToMultipleAssignments(
			final XmlObject scheduleInfoXmlObject,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "assignScheduleResourceToMultipleAssignments";
		logger.entering(CLASS_NAME, METHOD_NAME);

		java.util.HashMap mapResultSet = null;

		final ScheduleInfoXMLDocument scheduleInfoXMLDocument = (ScheduleInfoXMLDocument) scheduleInfoXmlObject;

		if (logger.isLoggable(Level.INFO)) {
			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("ScheduleInfoXMLDocument :")
					.append(scheduleInfoXMLDocument)
					.append("\nLogged In UserInfoDocument : ")
					.append(assignorUserInfoDocument.toString());
			logger.info("Input Received ::\n" + localMethodParams);
		}

		// Init
		int assignResult = AppraisalAssignmentConstants.FAILURE;
		long taskId = 0;

		final AssignTaskType[] tdoc = scheduleInfoXMLDocument
				.getScheduleInfoXML().getAssignTaskArray();

		// Invoke Parallel call as required.
		if (this.isAssignScheduleParallel(tdoc)) {

			if (logger.isLoggable(Level.INFO)) {
				logger.info("Invoking parallel AssignSchedule. NumItems: "
						+ tdoc.length);
			}

			mapResultSet = this.assignScheduleSubmitEjb
					.submitAssignScheduleAppraisalAssignment(tdoc,
							assignorUserInfoDocument);

			if (logger.isLoggable(Level.INFO)) {
				logger.info("Parallel processing for AssignSchedule complete.");
			}

		} else {

			// Non-parallel
			mapResultSet = new java.util.HashMap();

			for (int i = 0; i < tdoc.length; i++) {
				taskId = tdoc[i].getTaskId();
				try {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("Calling AAS.assignScheduleAppraisalAssignmentAssignTask_Requires_NewTX from AAS.assignScheduleResourceToMultipleAssignments for AssignTask :: "
								+ tdoc[i].toString());
					}
					assignResult = this.getAppraisalAssignmentEJB()
							.assignScheduleAppraisalAssignment_Requires_NewTX(
									tdoc[i], assignorUserInfoDocument);
					if (logger.isLoggable(Level.INFO)) {
						logger.info("Called AAS.assignScheduleAppraisalAssignmentAssignTask_Requires_NewTX from AAS.assignScheduleResourceToMultipleAssignments for AssignTask :: "
								+ tdoc[i].toString());
					}
				} catch (final Exception exception) {
					logger.severe("Error Message in " + METHOD_NAME + "\t:: "
							+ exception.getMessage());
					assignResult = AppraisalAssignmentConstants.FAILURE;

					logError(
							CLASS_NAME,
							METHOD_NAME,
							AppraisalAssignmentConstants.ERROR_SCHEDULING_MULTIPLE_AA,
							AppraisalAssignmentConstants.ERROR_SCHEDULING_MULTIPLE_AA_MSG
									+ "\tTask ID:" + taskId, exception,
							assignorUserInfoDocument);
				}

				// Populate HashMap
				mapResultSet.put(Long.valueOf(taskId),
						Integer.valueOf(assignResult));
			}

		}

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			final java.util.Iterator outboundit = mapResultSet.entrySet()
					.iterator();
			while (outboundit.hasNext()) {
				final java.util.Map.Entry me = (java.util.Map.Entry) outboundit
						.next();
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Key -> " + me.getKey() + " : Value -> "
							+ me.getValue());
				}
			}
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return mapResultSet;
	}

	/**
	 * Unschedule Assignments with workassignmenttaskIds_TCN and
	 * assignorInfoDocument from Dispatch Board This methods internally invokes
	 * the single unScheduleAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return java.util.HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public java.util.HashMap unScheduleMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "unScheduleMultipleAppraisalAssignments";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(Level.FINE)) {
			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams
					.append("mapTaskIdDispositionStat HashMap SIZE : ")
					.append(workAssignmentTaskID_TCN.size())
					.append("\nassignorUserInfoDocument :")
					.append(assignorUserInfoDocument);
			logger.fine("Input Received ::\n" + localMethodParams);
		}

		final java.util.HashMap ret = new java.util.HashMap();
		int result = AppraisalAssignmentConstants.FAILURE;
		Long workAssignmentTaskId = null;
		Long requestTCN = null;
		final Set keySet = workAssignmentTaskID_TCN.keySet();
		final java.util.Iterator itr = keySet.iterator();

		while (itr.hasNext()) {
			workAssignmentTaskId = (Long) itr.next();
			requestTCN = (Long) workAssignmentTaskID_TCN
					.get(workAssignmentTaskId);
			try {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Calling AAS.unScheduleAppraisalAssignment from AppraisalAssignmentService for taskID::"
							+ workAssignmentTaskId);
				}
				result = getAppraisalAssignmentEJB()
						.unScheduleAppraisalAssignment_Requires_NewTX(
								workAssignmentTaskId.longValue(),
								requestTCN.longValue(),
								assignorUserInfoDocument);
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Called AAS.unScheduleAppraisalAssignment from AppraisalAssignmentService for taskID::"
							+ workAssignmentTaskId);
				}
			} catch (final Exception exception) {
				logger.severe("Error Message in " + METHOD_NAME + "\t:: "
						+ exception.getMessage());
				result = AppraisalAssignmentConstants.FAILURE;

				logError(
						CLASS_NAME,
						METHOD_NAME,
						AppraisalAssignmentConstants.ERROR_UNSCHEDULING_MULTIPLE_AA,
						AppraisalAssignmentConstants.ERROR_UNSCHEDULING_MULTIPLE_AA_MSG
								+ "\tTask ID:" + workAssignmentTaskId,
						exception, assignorUserInfoDocument);

			}
			ret.put(workAssignmentTaskId, Integer.valueOf(result));
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);
		return ret;
	}

	/**
	 * onHoldAppraisalAssignment with workassignmenttaskIds_TCN ,
	 * selectedOnHoldTypeFromCarrier and assignorInfoDocument from Dispatch
	 * Board This methods internally invokes the single
	 * onHoldAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param selectedOnHoldTypeFromCarrier
	 *            - selectedOnHoldTypeFromCarrier object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @param notes
	 *            - notes object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public java.util.HashMap onHoldMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final String selectedOnHoldTypeFromCarrier, final String notes,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "onHoldMultipleAppraisalAssignments";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(Level.FINE)) {
			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("WorkAssignmentTaskID_TCN with size::")
					.append(workAssignmentTaskID_TCN.size())
					.append("\nselectedOnHoldTypeFromCarrier ::")
					.append(selectedOnHoldTypeFromCarrier).append("\nnotes ::")
					.append(notes).append("\nloggedInUserInfoDocument ::")
					.append(loggedInUserInfoDocument.toString());
			logger.fine("Input Received ::\n" + localMethodParams);
		}
		final java.util.HashMap ret = new java.util.HashMap();
		int result = AppraisalAssignmentConstants.FAILURE;
		Long workAssignmentTaskId = null;
		Long TCN = null;
		final Set keySet = workAssignmentTaskID_TCN.keySet();

		final java.util.Iterator itr = keySet.iterator();

		while (itr.hasNext()) {
			workAssignmentTaskId = (Long) itr.next();
			TCN = (Long) workAssignmentTaskID_TCN.get(workAssignmentTaskId);
			try {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Calling AAS.onHoldMultipleAppraisalAssignments from AppraisalAssignmentService for taskID::"
							+ workAssignmentTaskId);
				}
				result = onHoldAppraisalAssignment_Requires_NewTX(
						workAssignmentTaskId.longValue(), TCN.longValue(),
						selectedOnHoldTypeFromCarrier, notes,
						loggedInUserInfoDocument);
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Called AAS.onHoldMultipleAppraisalAssignments from AppraisalAssignmentService for taskID::"
							+ workAssignmentTaskId);
				}
			} catch (final Exception exception) {
				logger.severe("Error Message in " + METHOD_NAME + "\t:: "
						+ exception.getMessage());
				result = AppraisalAssignmentConstants.FAILURE;

				logError(
						CLASS_NAME,
						METHOD_NAME,
						AppraisalAssignmentConstants.ERROR_HOLDLING_MULTIPLE_AA,
						AppraisalAssignmentConstants.ERROR_HOLDLING_MULTIPLE_AA_MSG
								+ "\tTask ID:" + workAssignmentTaskId,
						exception, loggedInUserInfoDocument);
			}
			ret.put(workAssignmentTaskId, Integer.valueOf(result));
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return ret;
	}

	/**
	 * This method internally calls the removeOnHoldAppraisalAssignment method
	 * to remove an AppraisalAssignment from hold.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int removeOnHoldAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long TCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {

		final String METHOD_NAME = "removeOnHoldAppraisalAssignment_Requires_NewTX";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isAsmtRemovedFromOnHoldSuccessfully = AppraisalAssignmentConstants.FAILURE;

		try {
			isAsmtRemovedFromOnHoldSuccessfully = assignmentServiceHandler
					.removeOnHoldAppraisalAssignment(workAssignmentTaskID, TCN,
							loggedInUserInfoDocument);
		} catch (final Exception exception) {
			logger.severe("Exception occured in " + METHOD_NAME + "\t:: "
					+ exception.getMessage());

			logError(CLASS_NAME, METHOD_NAME,
					AppraisalAssignmentConstants.ERROR_UNHOLDLING_AA,
					AppraisalAssignmentConstants.ERROR_UNHOLDLING_AA_MSG
							+ " Task ID:" + workAssignmentTaskID, exception,
					loggedInUserInfoDocument);

			context.setRollbackOnly();
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isAsmtRemovedFromOnHoldSuccessfully;
	}

	/**
	 * removeOnHoldMultipleAppraisalAssignments with workassignmenttaskIds_TCN
	 * and assignorInfoDocument from Dispatch Board This methods internally
	 * invokes the single removeOnHoldAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return java.util.HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public java.util.HashMap removeOnHoldMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "removeOnHoldMultipleAppraisalAssignments";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(Level.FINE)) {
			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams
					.append("MapTaskIdDispositionStat HashMap SIZE : ")
					.append(workAssignmentTaskID_TCN.size())
					.append("\nloggedInUserInfoDocument :")
					.append(loggedInUserInfoDocument);
			logger.fine("Input Received ::\n" + localMethodParams);
		}
		final java.util.HashMap ret = new java.util.HashMap();
		int result = AppraisalAssignmentConstants.FAILURE;
		Long workAssignmentTaskId = null;
		Long TCN = null;
		final Set keySet = workAssignmentTaskID_TCN.keySet();

		final java.util.Iterator itr = keySet.iterator();

		while (itr.hasNext()) {
			workAssignmentTaskId = (Long) itr.next();
			TCN = (Long) workAssignmentTaskID_TCN.get(workAssignmentTaskId);
			try {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Calling AAS.removeOnHoldMultipleAppraisalAssignments from AppraisalAssignmentService for taskID::"
							+ workAssignmentTaskId);
				}
				result = getAppraisalAssignmentEJB()
						.removeOnHoldAppraisalAssignment_Requires_NewTX(
								workAssignmentTaskId.longValue(),
								TCN.longValue(), loggedInUserInfoDocument);
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Called AAS.removeOnHoldMultipleAppraisalAssignments from AppraisalAssignmentService for taskID::"
							+ workAssignmentTaskId);
				}
			} catch (final Exception exception) {
				logger.severe("Error Message in " + METHOD_NAME + "\t:: "
						+ exception.getMessage());
				result = AppraisalAssignmentConstants.FAILURE;

				logError(
						CLASS_NAME,
						METHOD_NAME,
						AppraisalAssignmentConstants.ERROR_UNHOLDLING_MULTIPLE_AA,
						AppraisalAssignmentConstants.ERROR_UNHOLDLING_MULTIPLE_AA_MSG
								+ "\tTask ID:" + workAssignmentTaskId,
						exception, loggedInUserInfoDocument);
			}
			ret.put(workAssignmentTaskId, Integer.valueOf(result));
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);
		return ret;
	}

	/**
	 * 
	 * This method dispatches multiple assignments for Jetta This method
	 * internally invokes the single dispatchAppraisalAssignment
	 * 
	 * @deprecated This method has been replaced by {@link use this method
	 *             #HashMap dispatchMultipleAppraisalAssignments(HashMap
	 *             workAssignmentTaskID_TCN, UserInfoDocument
	 *             assignorUserInfoDocument))}
	 */

	public java.util.HashMap dispatchMultipleAppraisalAssignments(
			long[] workAssignmentTaskID,
			UserInfoDocument assignorUserInfoDocument) throws MitchellException {
		final String methodName = "dispatchMultipleAppraisalAssignments";
		String claimNumber = AppraisalAssignmentConstants.CLAIM_NUMBER;
		logger.entering(CLASS_NAME, methodName);
		int workAssignmentTaskIDLength = workAssignmentTaskID.length;
		java.util.HashMap mapResultSet = new java.util.HashMap();
		boolean dispatchResult = false;

		for (int waTaskID = 0; waTaskID < workAssignmentTaskIDLength; waTaskID++) {
			try {
				// Call AAS Single Dispatch
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Calling dispatchAppraisalAssignment method with workAssignmentTaskID:"
							+ workAssignmentTaskID[waTaskID]);
				}

				dispatchResult = assignmentServiceHandler
						.dispatchAppraisalAssignment(
								workAssignmentTaskID[waTaskID],
								assignorUserInfoDocument);

			} catch (Exception exception) {
				String workItemID = "UNKNOWN";
				dispatchResult = false;
				String msgDetails = "";
				logger.severe("Exception occured in dispatchMultipleAppraisalAssignments() with WorkAssignmentTaskID="
						+ workAssignmentTaskID[waTaskID]
						+ "\nException: "
						+ exception.getMessage());

				logError(
						CLASS_NAME,
						methodName,
						AppraisalAssignmentConstants.ERROR_DISPATCHING_MULTIPLE_AA,
						AppraisalAssignmentConstants.ERROR_DISPATCHING_MULTIPLE_AA_MSG
								+ "\tTask ID:", exception,
						assignorUserInfoDocument);
			}
			mapResultSet.put(Long.valueOf(workAssignmentTaskID[waTaskID]),
					new Boolean(dispatchResult));

		}
		logger.exiting(CLASS_NAME, methodName);
		return mapResultSet;

	}

	/**
	 * This method dispatches multiple assignments from the Dispatch Board. This
	 * methods internally invokes the single
	 * dispatchAppraisalAssignment_Requires_NewTX method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             - Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public HashMap dispatchMultipleAppraisalAssignments(
			final HashMap workAssignmentTaskID_TCN,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "dispatchMultipleAppraisalAssignments";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(Level.FINE)) {
			StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("workAssignmentTaskID_TCN with size::")
					.append(workAssignmentTaskID_TCN.size())
					.append("\nassignorUserInfoDocument ::")
					.append(assignorUserInfoDocument.toString());
			logger.fine("Input Received ::\n" + localMethodParams);
		}

		final java.util.HashMap ret = new java.util.HashMap();
		final Set keySet = workAssignmentTaskID_TCN.keySet();
		int result = AppraisalAssignmentConstants.FAILURE;
		Long key = null;
		Long value = null;

		final java.util.Iterator itr = keySet.iterator();
		while (itr.hasNext()) {
			key = (Long) itr.next();
			value = (Long) workAssignmentTaskID_TCN.get(key);

			try {
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Calling AAS->dispatchAppraisalAssignment from AAS.dispatchMultipleAppraisalAssignments for taskID::"
							+ key + "\tvalue::" + value);
				}
				result = getAppraisalAssignmentEJB()
						.dispatchAppraisalAssignment_Requires_NewTX(
								key.longValue(), value.longValue(),
								assignorUserInfoDocument);
				if (logger.isLoggable(Level.INFO)) {
					logger.info("Called AAS->dispatchAppraisalAssignment from AAS.dispatchMultipleAppraisalAssignments for taskID::"
							+ key + "\tvalue::" + value);
				}
			} catch (final Exception exception) {
				logger.severe("Error Message in " + METHOD_NAME + "\t:: "
						+ exception.getMessage());
				result = AppraisalAssignmentConstants.FAILURE;

				logError(
						CLASS_NAME,
						METHOD_NAME,
						AppraisalAssignmentConstants.ERROR_DISPATCHING_MULTIPLE_AA,
						AppraisalAssignmentConstants.ERROR_DISPATCHING_MULTIPLE_AA_MSG
								+ "\tTask ID:" + key, exception,
						assignorUserInfoDocument);

			}

			ret.put(key, Integer.valueOf(result));
		}
		logger.exiting(CLASS_NAME, METHOD_NAME);

		return ret;
	}

	/**
	 * <p>
	 * This method is responsible for canceling of Appraisal Assignment from
	 * dispatch board. This method does following:
	 * </p>
	 * <ul>
	 * <li>Retrieves work assignment using taskID from Work Assignment Service.</li>
	 * <li>Updates work assignment with CANCELLED request and cancellation
	 * reason.</li>
	 * <li>Saves updated work assignment to Work Assignment Service.</li>
	 * <li>Creates Claim Activity Log.</li>
	 * </ul>
	 * 
	 * @param workAssignmentTaskID
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * @param cancellationReason
	 *            cancellation reason for cancelling Work Assignment.
	 * @param TCN
	 *            TCN from request to check stale data.
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @param notes
	 *            notes
	 * @return <code>true</code> if successfully processed the request and
	 *         <code>false</code> if unsuccessfully processed.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public int cancelAppraisalAssignment(final long workAssignmentTaskID,
			final String cancellationReason, final long TCN,
			final String notes, final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.cancelAppraisalAssignment(
				workAssignmentTaskID, cancellationReason, TCN, notes,
				loggedInUserInfoDocument);
	}

	/**
	 * <p>
	 * This method retrieves latest estimate for a claim number. Method calls
	 * EstimatePackage Service to retrieve all available estimates. Then it
	 * finds latest estimate and returns to caller.
	 * </p>
	 * 
	 * @param insuranceCarrierCoCode
	 *            Mitchell Company code of Insurance Company.
	 * @param clientClaimNumber
	 *            Claim Number
	 * @param estimatorUserInfoDocument
	 *            Estimator's UserInfo Document
	 * @return latest Estimate object.
	 * @throws MitchellException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Estimate getLatestEstimate(final String insuranceCarrierCoCode,
			final String clientClaimNumber,
			final UserInfoDocument estimatorUserInfoDocument)
			throws MitchellException {

		return assignmentServiceHandler.getLatestEstimate(
				insuranceCarrierCoCode, clientClaimNumber,
				estimatorUserInfoDocument);

	}

	/**
	 * <p>
	 * This method retrieves latest estimate for a claim number. Method calls
	 * EstimatePackage Service to retrieve all available estimates. Then it
	 * finds latest estimate and returns to caller.
	 * </p>
	 *
	 * @param insuranceCarrierCoCode
	 *            Mitchell Company code of Insurance Company.
	 * @param clientClaimNumber
	 *            Claim Number
	 * @return latest Estimate object.
	 * @throws MitchellException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Estimate getLatestEstimateNoFiltering(final String insuranceCarrierCoCode,
									  final String clientClaimNumber)
			throws MitchellException {

		return assignmentServiceHandler.getLatestEstimateNoFiltering(
				insuranceCarrierCoCode, clientClaimNumber);
	}

	/**
	 * <p>
	 * This method is responsible for create and/or save of Appraisal
	 * Assignment. This method does following major tasks:
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
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedInUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @return Updated AppraisalAssignmentDTO which has latest MitchellEnvelope
	 *         Document along with other data like TCN, WorkAssignmentTaskID,
	 *         DocumentID etc.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public AppraisalAssignmentDTO saveAppraisalAssignment(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedInUserInfoDocument)
			throws MitchellException {

		return assignmentServiceHandler.saveAppraisalAssignment(
				inputAppraisalAssignmentDTO, logedInUserInfoDocument);

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
	 * ** is referenced to provide an override option for dispatching
	 * Carrier-Feed Assignments <br>
	 * ** even for the case of an Invalid/Missing InspectionSite Address
	 * </ul>
	 * 
	 * @param mitchellEnvDoc
	 *            Object of MitchellEnvelopeDocument
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @return boolean - returns TRUE if all mandatory fields are present and
	 *         Vehicle Location Business rule is TRUE
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public boolean isAssignmentDataReady(
			final MitchellEnvelopeDocument mitchellEnvDoc)
			throws MitchellException {
		return assignmentServiceHandler.isAssignmentDataReady(mitchellEnvDoc);
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
	 */
	public String isAssignmentReadyForDispatch(
			final boolean mandatoryFieldFlag, final String assigneeID,
			final String groupCode, final java.util.Calendar scheduleDateTime,
			final UserInfoDocument userInfoDocument) throws MitchellException {
		return assignmentServiceHandler.isAssignmentReadyForDispatch(
				mandatoryFieldFlag, assigneeID, groupCode, scheduleDateTime,
				userInfoDocument);
	}

	/**
	 * This method performs AssignmentHistory Logging
	 * 
	 * @param eventName
	 * @param eventDescription
	 * @param workAssignment
	 * @throws MitchellException
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public void writeAssignmentActivityLog(final long assignmentId,
			final String eventName, final String eventDescription,
			final String createdBy) throws MitchellException {

		assignmentServiceHandler.writeAssignmentActivityLog(assignmentId,
				eventName, eventDescription, createdBy);
	}

	/**
	 * <p>
	 * This method is responsible for Uncancelling the Appraisal Assignment.
	 * This method does following:
	 * </p>
	 * <ul>
	 * <li>Retrieves work assignment using taskID from Work Assignment Service.</li>
	 * <li>Updates work assignment with NOT READY/UNCANCELLED request.</li>
	 * <li>Saves updated work assignment to Work Assignment Service.</li>
	 * <li>Retrieves MitchellEnvelope from EstimatePackage Service and removes
	 * Estimator information and saves MitchellEnvelope to EstimatePackage
	 * Service</li>
	 * </ul>
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public boolean uncancelAppraisalAssignment(final long workAssignmentTaskID,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.uncancelAppraisalAssignment(
				workAssignmentTaskID, loggedInUserInfoDocument);

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
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * 
	 * @param loggedInUserInfoDocument
	 * 
	 *            UserInfo Document of Logged In user.
	 * 
	 * @return <code>true</code> if successfully processed the request and
	 * 
	 *         <code>false</code> if unsuccessfully processed.
	 * 
	 * @throws MitchellException
	 * 
	 *             Throws MitchellException to the caller in case of any
	 * 
	 *             exception arise.
	 * 
	 * 
	 * @deprecated This method has been replaced by {@link use this method #int
	 *             dispatchAppraisalAssignment()}
	 */
	public boolean dispatchAppraisalAssignment(final long workAssignmentTaskID,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.dispatchAppraisalAssignment(
				workAssignmentTaskID, loggedInUserInfoDocument);

	}

	/**
	 * <p>
	 * This method is responsible for dispatching supplement Appraisal
	 * Assignment. This method internally calls dispatchAppraisalAssignment()
	 * method.
	 * </p>
	 * 
	 * @param workAssignmentTaskId
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @return <code>true</code> if successfully processed the request and
	 *         <code>false</code> if unsuccessfully processed.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public boolean dispatchSupplementAppraisalAssignment(
			final long workAssignmentTaskID, final long estimateDocId,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.dispatchSupplementAppraisalAssignment(
				workAssignmentTaskID, estimateDocId, loggedInUserInfoDocument);

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
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @return Updated AppraisalAssignmentDTO which has latest MitchellEnvelope
	 *         Document along with other data like TCN, WorkAssignmentTaskID,
	 *         DocumentID etc.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public AppraisalAssignmentDTO updateAppraisalAssignment(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.updateAppraisalAssignment(
				inputAppraisalAssignmentDTO, logedUserInfoDocument);

	}

	/**
	 * This method creates/updates the LuchType assignment.
	 * 
	 * @param taskDocumentXmlObject
	 *            of TaskDocument type.
	 * @param assignorUserInfoDocument
	 * @throws MitchellException
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public long saveLunchAssignmentType(final XmlObject taskDocumentXmlObject,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.saveLunchAssignmentType(
				taskDocumentXmlObject, assignorUserInfoDocument);

	}

	/**
	 * <p>
	 * This method retrieves a Supplement Request Doc (text) for use as Email
	 * Notification or UI Print-Preview
	 * </p>
	 * 
	 * @param estimateDocId
	 *            Estimate DocID for original Estimate.
	 * @param estimatorOrgId
	 *            OrgId of the estimator
	 * @param reviewerOrgId
	 *            OrgId of the reviewer
	 * @return Supplement Request Doc (text).
	 * @throws MitchellException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public String retrieveSupplementRequestDoc(final long estimateDocId,
			final long estimatorOrgId, final long reviewerOrgId)
			throws MitchellException {
		return assignmentServiceHandler.retrieveSupplementRequestDoc(
				estimateDocId, estimatorOrgId, reviewerOrgId);

	}

	/**
	 * <p>
	 * This interface method that generates/retrieves a Supplement Request HTML
	 * document from required inputs (a) an Estimate Document Id and (b) a pair
	 * of UserInfo Ids provided in the input AssignmentServiceContext asgSvcCtx
	 * </p>
	 * 
	 * @param context
	 *            Assignment Delivery - AssignmentServiceContext object
	 * @param workItemId
	 *            workItemID for this workflow instance
	 * 
	 * @return Returns Supplement Request HTML document
	 *         (MitchellEnvelopeDocument)
	 * @throws MitchellException
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public MitchellEnvelopeDocument retrieveSupplementRequestXMLDocAsMEDoc(
			final AssignmentDeliveryServiceDTO asgSvcCtx,
			final String workItemId) throws MitchellException {
		return assignmentServiceHandler.retrieveSupplementRequestXMLDocAsMEDoc(
				asgSvcCtx, workItemId);

	}

	/**
	 * <p>
	 * This method internally calls the uncancelAppraisalAssignment method for
	 * Uncancelling the Appraisal Assignment.
	 * </p>
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int uncancelAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "uncancelAppraisalAssignment_Requires_NewTX";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isAsmtUncancelledSuccessfully = AppraisalAssignmentConstants.FAILURE;

		try {
			isAsmtUncancelledSuccessfully = assignmentServiceHandler
					.uncancelAppraisalAssignment(workAssignmentTaskID,
							requestTCN, loggedInUserInfoDocument);

		} catch (final Exception exception) {
			logger.severe("Exception occured in " + METHOD_NAME + "\t:: "
					+ exception.getMessage());

			logError(CLASS_NAME, METHOD_NAME,
					AppraisalAssignmentConstants.ERROR_UNCANCELLING_AA,
					AppraisalAssignmentConstants.ERROR_UNCANCELLING_AA_MSG
							+ " Task ID:" + workAssignmentTaskID, exception,
					loggedInUserInfoDocument);

			context.setRollbackOnly();
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isAsmtUncancelledSuccessfully;

	}

	/**
	 * <p>
	 * This method is responsible for Uncancelling the Appraisal Assignment.
	 * This method does following:
	 * </p>
	 * <ul>
	 * <li>Retrieves work assignment using taskID from Work Assignment Service.</li>
	 * <li>Updates work assignment with NOT READY/UNCANCELLED request.</li>
	 * <li>Saves updated work assignment to Work Assignment Service.</li>
	 * <li>Retrieves MitchellEnvelope from EstimatePackage Service and removes
	 * Estimator information and saves MitchellEnvelope to EstimatePackage
	 * Service</li>
	 * </ul>
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public int uncancelAppraisalAssignment(final long workAssignmentTaskID,
			final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.uncancelAppraisalAssignment(
				workAssignmentTaskID, requestTCN, loggedInUserInfoDocument);

	}

	/*
	 * public String retrieveSupplementRequestXMLDoc(long estimateDocId,long
	 * estimatorOrgId,long reviewerOrgId) throws MitchellException{ return null;
	 * }
	 */

	/**
	 * <p>
	 * This method internally calls the cancelAppraisalAssignment method for
	 * cancelling the Appraisal Assignment.
	 * </p>
	 * 
	 * @param workAssignmentTaskID
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * @param cancellationReason
	 *            cancellation reason for cancelling Work Assignment.
	 * @param TCN
	 *            TCN from request to check stale data.
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @param notes
	 *            notes
	 * @return <code>true</code> if successfully processed the request and
	 *         <code>false</code> if unsuccessfully processed.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int cancelAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final String cancellationReason,
			final long TCN, final String notes,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "cancelAppraisalAssignment_Requires_NewTX";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isAsmtCancelledSuccessfully = AppraisalAssignmentConstants.FAILURE;

		try {
			isAsmtCancelledSuccessfully = assignmentServiceHandler
					.cancelAppraisalAssignment(workAssignmentTaskID,
							cancellationReason, TCN, notes,
							loggedInUserInfoDocument);

		} catch (final Exception exception) {
			logger.severe("Exception occured in " + METHOD_NAME + "\t:: "
					+ exception.getMessage());

			logError(CLASS_NAME, METHOD_NAME,
					AppraisalAssignmentConstants.ERROR_CANCELLING_AA,
					AppraisalAssignmentConstants.ERROR_CANCELLING_AA_MSG
							+ " Task ID:" + workAssignmentTaskID, exception,
					loggedInUserInfoDocument);

			context.setRollbackOnly();
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isAsmtCancelledSuccessfully;

	}

	/**
	 * <p>
	 * This method internally calls the assignScheduleAppraisalAssignment method
	 * for assigning assignee to Appraisal Assignment
	 * </p>
	 * 
	 * @param assignTaskXmlObject
	 *            - XmlObject object of AssignTaskType containing schedule info
	 * @param loggedInUserInfoDocument
	 *            - UserInfoDocument for logged in user
	 * @return <code>0</code> if successfully processed the request and
	 *         <code>1</code> if unsuccessfully processed. <code>2</code> if
	 *         stale data.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int assignScheduleAppraisalAssignment_Requires_NewTX(
			final XmlObject assignTaskXmlObject,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "assignScheduleAppraisalAssignment_Requires_NewTX";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isAsmtAssignedSuccessfully = AppraisalAssignmentConstants.FAILURE;

		try {
			isAsmtAssignedSuccessfully = assignmentServiceHandler
					.assignScheduleAppraisalAssignment(assignTaskXmlObject,
							loggedInUserInfoDocument);

		} catch (final Exception exception) {
			logger.severe("Exception occured in " + METHOD_NAME + "\t:: "
					+ exception.getMessage());

			logError(CLASS_NAME, METHOD_NAME,
					AppraisalAssignmentConstants.ERROR_SCHEDULING_AA,
					AppraisalAssignmentConstants.ERROR_SCHEDULING_AA_MSG,
					exception, loggedInUserInfoDocument);

			context.setRollbackOnly();
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isAsmtAssignedSuccessfully;
	}

	/**
	 * <p>
	 * This method is responsible for assigning assignee to Appraisal Assignment
	 * from dispatch board. This method does following major tasks:
	 * </p>
	 * <ul>
	 * <li>Retrieves assignee's user information from UserInfo Service.</li>
	 * <li>Retrieves, updates and saves work assignment to WorkAssignment
	 * Service.</li>
	 * <li>Saves updates MitchellEnvelope to EstimatePackage Service</li>
	 * <li>Creates Claim-Suffix Activity Log.</li>
	 * </ul>
	 * 
	 * @param assignTaskXmlObject
	 *            - XmlObject object of AssignTaskType containing schedule info
	 * @param loggedInUserInfoDocument
	 *            - UserInfoDocument for logged in user
	 * @return <code>0</code> if successfully processed the request and
	 *         <code>1</code> if unsuccessfully processed. <code>2</code> if
	 *         stale data.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public int assignScheduleAppraisalAssignment(
			final XmlObject assignTaskXmlObject,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.assignScheduleAppraisalAssignment(
				assignTaskXmlObject, loggedInUserInfoDocument);
	}

	/**
	 * This method internally calls the onHoldAppraisalAssignment method to put
	 * AppraisalAssignment on hold.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param selectedOnHoldTypeFromCarrier
	 *            - selectedOnHoldTypeFromCarrier object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @param notes
	 *            - notes object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int onHoldAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long TCN,
			final String selectedOnHoldTypeFromCarrier, final String notes,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "onHoldAppraisalAssignment_Requires_NewTX";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isAsmtOnHoldedSuccessfully = AppraisalAssignmentConstants.FAILURE;

		try {
			isAsmtOnHoldedSuccessfully = assignmentServiceHandler
					.onHoldAppraisalAssignment(workAssignmentTaskID, TCN,
							selectedOnHoldTypeFromCarrier, notes,
							loggedInUserInfoDocument);

		} catch (final Exception exception) {
			logger.severe("Exception occured in " + METHOD_NAME + "\t:: "
					+ exception.getMessage());

			logError(CLASS_NAME, METHOD_NAME,
					AppraisalAssignmentConstants.ERROR_ONHOLD_AA_SAVINGWA,
					AppraisalAssignmentConstants.ERROR_ONHOLD_AA_SAVINGWA_MSG
							+ " Task ID:" + workAssignmentTaskID, exception,
					loggedInUserInfoDocument);

			context.setRollbackOnly();
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isAsmtOnHoldedSuccessfully;
	}

	/**
	 * onHoldAppraisalAssignment each Assignment with workassignmenttaskId ,TCN
	 * , selectedOnHoldTypeFromCarrier and assignorInfoDocument from Dispatch
	 * Board
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param selectedOnHoldTypeFromCarrier
	 *            - selectedOnHoldTypeFromCarrier object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @param notes
	 *            - notes object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public int onHoldAppraisalAssignment(final long workAssignmentTaskID,
			final long TCN, final String selectedOnHoldTypeFromCarrier,
			final String notes, final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.onHoldAppraisalAssignment(
				workAssignmentTaskID, TCN, selectedOnHoldTypeFromCarrier,
				notes, loggedInUserInfoDocument);
	}

	/**
	 * removeOnHoldAppraisalAssignment each Assignment with workassignmenttaskId
	 * ,TCN and assignorInfoDocument from Dispatch Board
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public int removeOnHoldAppraisalAssignment(final long workAssignmentTaskID,
			final long TCN, final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.removeOnHoldAppraisalAssignment(
				workAssignmentTaskID, TCN, loggedInUserInfoDocument);
	}

	/**
	 * Unschedule each Assignment with workassignmenttaskId ,TCN and
	 * assignorInfoDocument from Dispatch Board This method internally calls the
	 * unScheduleAppraisalAssignment method.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int unScheduleAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "unScheduleAppraisalAssignment_Requires_NewTX";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isAsmtUnscheduledSuccessfully = AppraisalAssignmentConstants.FAILURE;

		try {
			isAsmtUnscheduledSuccessfully = assignmentServiceHandler
					.unScheduleAppraisalAssignment(workAssignmentTaskID,
							requestTCN, loggedInUserInfoDocument);

		} catch (final Exception exception) {
			logger.severe("Exception occured in " + METHOD_NAME + "\t:: "
					+ exception.getMessage());

			logError(CLASS_NAME, METHOD_NAME,
					AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA,
					AppraisalAssignmentConstants.ERROR_UNSCHEDULE_AA_MSG
							+ " Task ID:" + workAssignmentTaskID, exception,
					loggedInUserInfoDocument);

			context.setRollbackOnly();
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isAsmtUnscheduledSuccessfully;
	}

	/**
	 * Unschedule each Assignment with workassignmenttaskId ,TCN and
	 * assignorInfoDocument from Dispatch Board
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param requestTCN
	 *            - requestTCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public int unScheduleAppraisalAssignment(final long workAssignmentTaskID,
			final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.unScheduleAppraisalAssignment(
				workAssignmentTaskID, requestTCN, loggedInUserInfoDocument);
	}

	/**
	 * This method dispatches a single assignment.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public int dispatchAppraisalAssignment(final long workAssignmentTaskID,
			final long TCN, final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.dispatchAppraisalAssignment(
				workAssignmentTaskID, TCN, loggedInUserInfoDocument);

	}

	/**
	 * This method dispatches a single assignment. This method internally calls
	 * the dispatchAppraisalAssignment method.
	 * 
	 * @param workAssignmentTaskID
	 *            - workAssignmentTaskID object
	 * @param TCN
	 *            - TCN object
	 * @param loggedInUserInfoDocument
	 *            - loggedInUserInfoDocument object
	 * @return int - int value
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int dispatchAppraisalAssignment_Requires_NewTX(
			final long workAssignmentTaskID, final long TCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "dispatchAppraisalAssignment_Requires_NewTX";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isAsmtDispatchedSuccessfully = AppraisalAssignmentConstants.FAILURE;

		try {
			isAsmtDispatchedSuccessfully = assignmentServiceHandler
					.dispatchAppraisalAssignment(workAssignmentTaskID, TCN,
							loggedInUserInfoDocument);
			if (isAsmtDispatchedSuccessfully == AppraisalAssignmentConstants.STALE_DATA)
				context.setRollbackOnly();

		} catch (final Exception exception) {
			logger.severe("Exception occured in " + METHOD_NAME + "\t:: "
					+ exception.getMessage());

			logError(CLASS_NAME, METHOD_NAME,
					AppraisalAssignmentConstants.ERROR_DISPATCH_AA,
					AppraisalAssignmentConstants.ERROR_DISPATCH_AA_MSG
							+ " Task ID:" + workAssignmentTaskID, exception,
					loggedInUserInfoDocument);

			context.setRollbackOnly();
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
	 *            Supports IN_PROGRESS, REJECTED, CANCELLED, COMPLETED
	 * @param reasonCode
	 * @param comment
	 * @param requestTCN
	 * @param loggedInUserInfoDocument
	 * @return int value would be zero for SUCCESS, ONE for FAILURE, TWO for
	 *         STALE DATA
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public int assignmentStatusUpdate(final long workAssignmentTaskID,
			final String newDispositionCode, final String reasonCode,
			final String comment, final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.assignmentStatusUpdate(
				workAssignmentTaskID, newDispositionCode, reasonCode, comment,
				requestTCN, loggedInUserInfoDocument, null);

	}

	/**
	 * Updates ItineraryView Disposition in WorkAssignment and performs Claim
	 * Activity & Assignment Activity Logging
	 * 
	 * @param workAssignmentTaskID
	 * @param newDispositionCode
	 *            Supports IN_PROGRESS, REJECTED, CANCELLED, COMPLETED
	 * @param reasonCode
	 * @param comment
	 * @param requestTCN
	 * @param loggedInUserInfoDocument
	 * @return int value would be zero for SUCCESS, ONE for FAILURE, TWO for
	 *         STALE DATA
	 * @throws Exception
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int assignmentStatusUpdateItineraryView(XmlObject ItineraryXMLObj,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {

		int isUpdateDispositionSuccessful = 1; // failure
		ItineraryViewDocument ItineraryXML = (ItineraryViewDocument) ItineraryXMLObj;

		try {
			/*
			 * Calling assignmentServiceHandler to save detail in Work
			 * assignment service
			 */
			isUpdateDispositionSuccessful = assignmentServiceHandler
					.assignmentStatusUpdateItineraryView(ItineraryXML,
							loggedInUserInfoDocument);

		} catch (Exception e) {

			logger.severe(AppUtilities.getStackTraceString(e));

			throw new MitchellException(
					this.getClass().getName(),
					"assignmentStatusUpdateItineraryView",
					"Unexpected exception occured while parsing MitchellEnvelopeDocument for assignment.",
					e);

		}
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
	 *             Throws Exception to the caller in case of any exception
	 *             arise.
	 */
	public int workAssignmentStatusUpdate(final long workAssignmentTaskID,
			final String newDispositionCode, final String reasonCode,
			final String comment, final long requestTCN,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.workAssignmentStatusUpdate(
				workAssignmentTaskID, newDispositionCode, reasonCode, comment,
				requestTCN, loggedInUserInfoDocument);

	}

	/**
	 * This method cancel a request of Supplement Assigment for a cartain
	 * claim-suffix number
	 * 
	 * @param claimSuffixNumber
	 *            it contains calim_number-suffix-number
	 * @param reviewerUserInfo
	 *            it contains UserInfoDocument object with DRP user info
	 * 
	 * @throws MitchellException
	 * 
	 */

	public void cancelSupplementTask(final String claimSuffixNumber,
			final UserInfoDocument bodyShopUserInfo, final String note,
			final String reviewCoCd, final String reviewUserId)
			throws MitchellException {
		assignmentServiceHandler.cancelSupplementTask(claimSuffixNumber,
				bodyShopUserInfo, note, reviewCoCd, reviewUserId);

	}

	/**
	 * This method creates a request of Supplement Assignment on a certain
	 * claim-suffix number
	 * 
	 * 
	 * @param claimSuffixNumber
	 *            calim_number-suffix-number
	 * @param reviewerUserInfo
	 *            UserInfoDocument object with DRP user info
	 * @param workItemId
	 *            work item id (PAUH has the info)
	 * 
	 * @throws MitchellException
	 */
	public void createSupplementTask(final String claimSuffixNumber,
			final UserInfoDocument bodyShopUserInfo, final String workItemId,
			final String note, final String reviewCoCd,
			final String reviewUserId) throws MitchellException {
		assignmentServiceHandler.createSupplementTask(claimSuffixNumber,
				bodyShopUserInfo, workItemId, note, reviewCoCd, reviewUserId);
	}

	/**
	 * This method creates a request of Supplement Assignment on a certain
	 * claim-suffix number
	 * 
	 * 
	 * @param claimSuffixNumber
	 *            calim_number-suffix-number
	 * @param reviewerUserInfo
	 *            UserInfoDocument object with DRP user info
	 * @param workItemId
	 *            work item id (PAUH has the info)
	 * 
	 * @throws MitchellException
	 */
	public void createAssignSupplementTaskToNCRTUSer(
			final String claimSuffixNumber,
			final UserInfoDocument bodyShopUserInfo, final String workItemId,
			final String note, final String reviewCoCd,
			final String reviewUserId) throws MitchellException {
		assignmentServiceHandler.createAssignSupplementTaskToNCRTUSer(
				claimSuffixNumber, bodyShopUserInfo, workItemId, note,
				reviewCoCd, reviewUserId);
	}

	/**
	 * This method reject the request of Suplement Assignment
	 * 
	 * @param taskID
	 *            task ID of the request.
	 * @param estimatorUserInfo
	 *            the WC user
	 * 
	 * @exception MitchellException
	 * 
	 */
	public void rejectSupplementTask(final long taskID,
			final UserInfoDocument estimatorUserInfo) throws MitchellException {
		assignmentServiceHandler
				.rejectSupplementTask(taskID, estimatorUserInfo);

	}

	/**
	 * 
	 * 
	 * @exception MitchellException
	 */
	public void addVehLctnTrackHist(final Long claimSuffixID,
			final String vehicleTrackingStatus, final String companyCode,
			final String reviewerId) throws MitchellException {

		assignmentServiceHandler.addVehLctnTrackingHist(claimSuffixID,
				vehicleTrackingStatus, companyCode, reviewerId);
	}

	/**
	 * This method added for temp Gets AppraisalAssignment remote object for
	 * Client.
	 * 
	 * @return AppraisalAssignmentService - Stub for AppraisalAssignment Remote
	 *         EJB
	 * 
	 * @throws MitchellException
	 * 
	 */
	protected AppraisalAssignmentServiceRemote getAppraisalAssignmentEJB()
			throws MitchellException {

		AppraisalAssignmentServiceRemote appraisalAssignmentServiceRemote = null;

		try {

			final String appraisalAssignmentEJBname = SystemConfiguration
					.getSettingValue(APPRAISAL_ASSIGNMENT_REMOTE_EJB_JNDI);
			final String providerURL = SystemConfiguration
					.getSettingValue(APPRAISAL_ASSIGNMENT_REMOTE_PROVIDER_URL);
			final String jndiConFactory = SystemConfiguration
					.getSettingValue(APPRAISAL_ASSIGNMENT_REMOTE_JNDI_FACTORY);
			final Properties environment = new Properties();
			environment.put(Context.INITIAL_CONTEXT_FACTORY, jndiConFactory);
			environment.put(Context.PROVIDER_URL, providerURL);
			Context ctx = new InitialContext(environment);
			Object obj = ctx.lookup(appraisalAssignmentEJBname);
			appraisalAssignmentServiceRemote = (AppraisalAssignmentServiceRemote) obj;

		} catch (javax.naming.NamingException namingException) {
			throw new MitchellException(
					AppraisalAssignmentConstants.ERROR_CLIENT_EJB,
					"AppraisalAssignmentClient", "getAppraisalAssignmentEJB",
					"Naming exception getting AppraisalAssignment EJB.",
					namingException);
		} catch (Throwable throwable) {
			throw new MitchellException(
					AppraisalAssignmentConstants.ERROR_CLIENT_EJB,
					"AppraisalAssignmentClient", "getAppraisalAssignmentEJB",
					"Remote exception getting AppraisalAssignment EJB.",
					throwable);
		}

		return appraisalAssignmentServiceRemote;
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
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedInUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @return Updated AppraisalAssignmentDTO.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public AppraisalAssignmentDTO saveSSOAppraisalAssignment(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedInUserInfoDocument)
			throws MitchellException {
		return assignmentServiceHandler.saveSSOAppraisalAssignment(
				inputAppraisalAssignmentDTO, logedInUserInfoDocument);
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
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedInUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @return Updated AppraisalAssignmentDTO.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public AppraisalAssignmentDTO saveSSOAppraisalAssignment(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedInUserInfoDocument,
			final boolean createClaimIfNeeded) throws MitchellException {
		return assignmentServiceHandler.saveSSOAppraisalAssignment(
				inputAppraisalAssignmentDTO, logedInUserInfoDocument, true);
	}

	/**
	 * This method assign assignment(s) to dispatch center. This methods
	 * internally invokes the single assignedToDispatchCenter_Requires_NewTX
	 * method
	 * 
	 * @param workAssignmentTaskID_TCN
	 *            - workAssignmentTaskID_TCN object
	 * @param dispatchCenter
	 *            - dispatchCenter object
	 * @param assignorUserInfoDocument
	 *            - assignorUserInfoDocument object
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public java.util.HashMap assignedToDispatchCenter(
			final HashMap workAssignmentTaskIDAndTCN,
			final String dispatchCenter,
			final UserInfoDocument assignorUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "assignedToDispatchCenter";
		logger.entering(CLASS_NAME, METHOD_NAME);

		if (logger.isLoggable(Level.FINE)) {
			final StringBuffer localMethodParams = new StringBuffer();
			localMethodParams.append("WorkAssignmentTaskID_TCN with size::")
					.append(workAssignmentTaskIDAndTCN.size())
					.append(" dispatch Center ::").append(dispatchCenter)
					.append(" assignorUserInfoDocument ::")
					.append(assignorUserInfoDocument.toString());
			logger.fine("Input Received ::" + localMethodParams);
		}

		final java.util.HashMap taskIdResultMap = new java.util.HashMap();
		int result = AppraisalAssignmentConstants.FAILURE;
		final Set keySet = workAssignmentTaskIDAndTCN.keySet();
		final java.util.Iterator itr = keySet.iterator();

		while (itr.hasNext()) {
			final Long key = (Long) itr.next();
			try {
				result = getAppraisalAssignmentEJB()
						.assignedToDispatchCenter_Requires_NewTX(
								key.longValue(), dispatchCenter,
								assignorUserInfoDocument);
			} catch (final Exception assignedToDispatchCenterException) {

				StringBuffer errorMessage = new StringBuffer("Error in ")
						.append(METHOD_NAME)
						.append(" ")
						.append(AppUtilities
								.getStackTraceString(assignedToDispatchCenterException));

				logger.severe(errorMessage.toString());

				result = AppraisalAssignmentConstants.FAILURE;

				logError(
						CLASS_NAME,
						METHOD_NAME,
						AppraisalAssignmentConstants.ERROR_ASSIGN_TO_DISPATCH_CENTER_AA,
						AppraisalAssignmentConstants.ERROR_ASSIGN_TO_DISPATCH_CENTER_AA_MSG
								+ " TaskID " + key,
						assignedToDispatchCenterException,
						assignorUserInfoDocument);
			}
			taskIdResultMap.put(key, Integer.valueOf(result));
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return taskIdResultMap;
	}

	/**
	 * <p>
	 * This method changes the dispatch center for a single assignment.
	 * </p>
	 * 
	 * @param workAssignmentTaskID
	 *            WorkAssignmentTaskID of Work Assignment Service.
	 * @param dispatchCenter
	 *            dispatchCenter.
	 * @param loggedInUserInfoDocument
	 *            UserInfo Document of Logged In user.
	 * @return <code>true</code> if successfully processed the request and
	 *         <code>false</code> if unsuccessfully processed.
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int assignedToDispatchCenter_Requires_NewTX(
			final long workAssignmentTaskID, final String dispatchCenter,
			final UserInfoDocument loggedInUserInfoDocument)
			throws MitchellException {
		final String METHOD_NAME = "assignedToDispatchCenter_Requires_NewTX";
		logger.entering(CLASS_NAME, METHOD_NAME);

		int isAsmtAssignToDispCenterSuccessfully = AppraisalAssignmentConstants.FAILURE;

		try {
			isAsmtAssignToDispCenterSuccessfully = assignmentServiceHandler
					.assignedToDispatchCenter(workAssignmentTaskID,
							dispatchCenter, loggedInUserInfoDocument);
		} catch (final Exception assignedToDispatchCenterException) {

			StringBuffer errorMessage = new StringBuffer("Error in ")
					.append(METHOD_NAME)
					.append(" ")
					.append(AppUtilities
							.getStackTraceString(assignedToDispatchCenterException));
			logger.severe(errorMessage.toString());
			context.setRollbackOnly();
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);

		return isAsmtAssignToDispCenterSuccessfully;

	}

	/**
	 * <p>
	 * This method does the appLogging for AppraisalAssignmentService
	 * </p>
	 * 
	 * @param eventId
	 *            eventId of Appraisal Assignment applog ID.
	 * @param taskId
	 *            taskId for the workAssignment taskfor which applogging needs
	 *            to be done.
	 * @param userInfoDocument
	 *            UserInfo Document of user initiating the applog.
	 * @param mitchellEnvelopeDocument
	 *            MitchellEnvelopeDocument for AppraisalAssignment
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 */
	public void doAppraisalAssignmentAppLog(final int eventId,
			final long taskId, final UserInfoDocument userInfoDocument,
			final XmlObject mitchellEnvelopeDocument) throws MitchellException {
		final String METHOD_NAME = "doAppraisalAssignmentAppLog";
		logger.entering(CLASS_NAME, METHOD_NAME);
		assignmentServiceHandler.doAppraisalAssignmentAppLog(eventId, taskId,
				userInfoDocument, mitchellEnvelopeDocument);
		logger.exiting(CLASS_NAME, METHOD_NAME);

	}

	/**
	 * <p>
	 * This method is responsible for updating Appraisal Assignment Address.
	 * This method does following major tasks:
	 * </p>
	 * <ul>
	 * <li>Retrieves work assignment from WorkAssignment Service.</li>
	 * <li>Updates MitchellEnvelope using EstimatePackage Service</li>
	 * <li>Saves updated Work Assignment in WorkAssignment Service.</li>
	 * <li>Creates Claim-Suffix Activity Log.</li>
	 * </ul>
	 * 
	 * @param inputAppraisalAssignmentDTO
	 *            Object of AppraisalAssignmentDTO.
	 * @param logedUserInfoDocument
	 *            UserInfo Document of Logged in User.
	 * @return HashMap - HashMap object
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 * @ejbgen:remote-method transaction-attribute="Required"
	 * @ejbgen:local-method transaction-attribute="Required"
	 */
	public java.util.HashMap updateAppraisalAssignmentAddress(
			final AppraisalAssignmentDTO inputAppraisalAssignmentDTO,
			final UserInfoDocument logedInUserInfoDocument)
			throws MitchellException {

		final String METHOD_NAME = "updateAppraisalAssignmentAddress";
		logger.entering(CLASS_NAME, METHOD_NAME);
		java.util.HashMap ret = assignmentServiceHandler
				.updateAppraisalAssignmentAddress(inputAppraisalAssignmentDTO,
						logedInUserInfoDocument);
		logger.exiting(CLASS_NAME, METHOD_NAME);
		return ret;
	}

	/**
	 * <p>
	 * This method creates the Supplement Assignment BMS with the minimum
	 * required aggregates
	 * </p>
	 * 
	 * @param claimSuffix
	 *            object of claimSuffix
	 * @param estimateDocumentID
	 *            document id of estimate assignorOrgId orgId of assignor for
	 *            the estimate
	 * @return String object of supplement assignment BMS
	 * @throws MitchellException
	 *             Throws MitchellException to the caller in case of any
	 *             exception arise.
	 * 
	 */
	public String createMinimalSupplementAssignmentBMS(String claimSuffix,
			long estimateDocumentID, long assignorOrgId)
			throws MitchellException {

		final String METHOD_NAME = "createMinimalSupplementAssignmentBMS";
		logger.entering(CLASS_NAME, METHOD_NAME);

		String ciecaStr = assignmentServiceHandler
				.createMinimalSupplementAssignmentBMS(claimSuffix,
						estimateDocumentID, assignorOrgId);

		if (logger.isLoggable(java.util.logging.Level.INFO)) {
			logger.info("In " + METHOD_NAME
					+ " Constructed CIECADocument is: \n" + ciecaStr);
		}

		logger.exiting(CLASS_NAME, METHOD_NAME);
		return ciecaStr;

	}

	/**
	 * Is Parallel Processing needed for AssignScheduleTask. Returns true if
	 * either the number of items in the list meets the minimum parallel
	 * processing threshold value in the settings file or there exists at least
	 * one "LUNCH" assignment.
	 */
	protected boolean isAssignScheduleParallel(AssignTaskType[] tdoc) {
		boolean retval = isParallelMinListSize(tdoc.length);

		// If minimum parallel size is not met may need to go parallel if lunch
		// assignments are included
		if (!retval) {
			for (AssignTaskType ast : tdoc) {
				if (ast.isSetAssignmentType()
						&& AppraisalAssignmentConstants.LUNCH_ASSIGNMENT_TYPE
								.equalsIgnoreCase(ast.getAssignmentType())) {
					retval = true;

					if (logger.isLoggable(Level.INFO)) {
						logger.info("AssignScheduleParallel because of lunch.");
					}

					break;
				}
			}
		}

		//
		return retval;
	}

	/**
	 * Returns true if the provided listSize is >= the defined minimum parallel
	 * processing size, false otherwise.
	 * 
	 */
	protected boolean isParallelMinListSize(int listSize) {
		boolean retval = false;
		String ss = extAccess
				.getSystemConfigValue(AASParallelConstants.SYSCONF_PARALLEL_MIN_LIST_SIZE);
		if (ss != null && ss.length() > 0) {
			int minSize = 5;
			try {
				minSize = Integer.parseInt(ss);
			} catch (Exception e) {
				minSize = 5;
			}
			if (listSize >= minSize) {
				retval = true;
			}
		}
		return retval;
	}
	/***
	 * This method will fetch the appraisal assignment
	 * using estimate package client and will
	 * process ME of appraisal assignment DTO
	 * and will return the CIECADocument
	 * @param documentId
	 * @return
	 * @throws MitchellException
	 */
	public XmlObject getCiecaFromAprasgDto(long documentId)
			throws MitchellException {
		CIECADocument ciecaDoc = null;
		final String method_Name = "getciecaFromAprAsgDto";
		com.mitchell.services.technical.partialloss.estimate.client.AppraisalAssignmentDTO apprAsgDto = estimatePackageProxy
				.getAppraisalAssignmentDocument(documentId);
		if (apprAsgDto != null) {
			try {
				MitchellEnvelopeDocument mitchellEnvDoc = MitchellEnvelopeDocument.Factory
						.parse(apprAsgDto.getAppraisalAssignmentMEStr());
				MitchellEnvelopeHelper meHelper = new MitchellEnvelopeHelper(
						mitchellEnvDoc);
				ciecaDoc = mitchellEnvelopeHandler.getCiecaFromME(meHelper);
			} catch (XmlException xmlExc) {
				final String errMsg = "XmlException occurred in parsing the Mitchell envelope";
				throw new MitchellException(CLASS_NAME, method_Name, errMsg,
						xmlExc);
			} catch (Exception exception) {
				final String errMsg = "Error parsing and creating object of CIECADocument";
				throw new MitchellException(CLASS_NAME, method_Name, errMsg,
						exception);
			}
		}
		return ciecaDoc;
	}
}
