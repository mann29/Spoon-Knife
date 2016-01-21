package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import javax.ejb.Stateless;

import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.schedule.AssignTaskType;
import com.mitchell.schemas.task.TaskDocument;
import com.mitchell.schemas.task.TaskType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.delegator.AppraisalAssignmentServiceHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelContext;
import com.mitchell.services.business.partialloss.appraisalassignment.util.BeanLocator;
import com.mitchell.utils.xml.MIEnvelopeHelper;

@Stateless
public class AASAssignScheduleHandlerBean extends AASBaseHandler implements
    AASAssignScheduleHandler
{

  protected static final String LUNCH_CLAIM_NUMBER = "Lunch";

  /**
   * Main entry point for Parallel AssignScheduling Processing.
   */
  public MitchellEnvelopeDocument processRequest(AASParallelContext ctx,
      MIEnvelopeHelper meHelper)
      throws MitchellException
  {
    MitchellEnvelopeDocument meDocReturn = null;

    try {

      // Get the UserInfo
      UserInfoDocument loggedInUserInfoDocument = extractLoggedInUserInfo(meHelper);

      // Get the AssignTask
      EnvelopeBodyType envelopeBodyAT = meHelper
          .getEnvelopeBody(AASParallelConstants.ME_ASSIGN_TASK_TYPE_ID);
      String assignTaskStr = meHelper
          .getEnvelopeBodyContentAsString(envelopeBodyAT);
      AssignTaskType assignTaskType = AssignTaskType.Factory
          .parse(assignTaskStr);

      // Do the work
      int isAsmtAssignedSuccessfully = doAssignScheduleOrLunch(assignTaskType,
          loggedInUserInfoDocument);

      // Update the ME with the results
      meHelper.addEnvelopeContextNVPair(
          AASParallelConstants.ME_ASSIGN_TASK_TYPE_RESULT,
          String.valueOf(isAsmtAssignedSuccessfully));

      // Get the MEDoc to return
      meDocReturn = meHelper.getDoc();

    } catch (MitchellException e) {
      if (e.getType() < 1) {
        e.setType(AppraisalAssignmentConstants.ERROR_PARALLEL_ASSIGNSCHEDULE_PROCESSING_ERROR);
      }
      throw e;
    } catch (Exception e) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_PARALLEL_ASSIGNSCHEDULE_PROCESSING_ERROR,
          this.getClass().getName(), "processRequest",
          "Exception processing request.", e);
    }

    return meDocReturn;
  }

  /**
   * Process a request from the error queue.
   */
  public MitchellEnvelopeDocument processRequestError(AASParallelContext ctx,
      MIEnvelopeHelper meHelper)
      throws MitchellException
  {
    MitchellEnvelopeDocument meDocReturn = null;

    try {

      // Update the ME with the results
      meHelper.addEnvelopeContextNVPair(
          AASParallelConstants.ME_ASSIGN_TASK_TYPE_RESULT,
          String.valueOf(AppraisalAssignmentConstants.FAILURE));

      // Get the MEDoc to return
      meDocReturn = meHelper.getDoc();

    } catch (Exception e) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_PARALLEL_ASSIGNSCHEDULE_PROCESSING_ERROR,
          this.getClass().getName(), "processRequestError",
          "Exception processing parallel deadletter queue message.", e);
    }

    return meDocReturn;
  }

  /**
   * Assign Schedule Appraisal Assignment. Invokes the functionality that
   * pre-dated the introduction of parallel processing.
   * 
   */
  protected int doAssignScheduleOrLunch(XmlObject assignTaskXmlObject,
      UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    int retval = 0;
    AssignTaskType assignTask = (AssignTaskType) assignTaskXmlObject;

    if (assignTask.isSetAssignmentType()
        && AppraisalAssignmentConstants.LUNCH_ASSIGNMENT_TYPE
            .equalsIgnoreCase(assignTask.getAssignmentType())) {
      retval = assignScheduleLunchAssignment(assignTaskXmlObject,
          loggedInUserInfoDocument);
    } else {
      retval = assignScheduleAppraisalAssignment(assignTaskXmlObject,
          loggedInUserInfoDocument);
    }

    //
    return retval;

  }

  /**
   * Assign Schedule Appraisal Assignment. Invokes the functionality that
   * pre-dated the introduction of parallel processing.
   * 
   */
  protected int assignScheduleAppraisalAssignment(
      XmlObject assignTaskXmlObject, UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    AppraisalAssignmentServiceHandler assignmentServiceHandler = getServiceHandler();
    return assignmentServiceHandler.assignScheduleAppraisalAssignment(
        assignTaskXmlObject, loggedInUserInfoDocument);
  }

  /**
   * Assign Schedule Lunch Assignment. Invokes the functionality that
   * pre-dated the introduction of parallel processing.
   * 
   */
  protected int assignScheduleLunchAssignment(XmlObject assignTaskXmlObject,
      UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException
  {
    // Build Lunch Request from AssignTask
    AssignTaskType assignTask = (AssignTaskType) assignTaskXmlObject;
    TaskDocument taskDocument = buildTaskDocumentForLunch(assignTask,
        loggedInUserInfoDocument.getUserInfo().getOrgCode());

    if (logger.isInfoEnabled()) {
      logger
          .info("AASAssignScheduleHandlerBean received LUNCH request. CoCode: "
              + taskDocument.getTask().getCompanyCode() + ", AssigneeId: "
              + taskDocument.getTask().getAssigneeId());
    }

    // Do Lunch
    AppraisalAssignmentServiceHandler assignmentServiceHandler = getServiceHandler();
    long taskId = assignmentServiceHandler.saveLunchAssignmentType(
        taskDocument, loggedInUserInfoDocument);

    // If we get back a taskId then we are good
    int retval = AppraisalAssignmentConstants.FAILURE;
    if (taskId > 0) {
      retval = AppraisalAssignmentConstants.SUCCESS;

      if (logger.isInfoEnabled()) {
        logger.info("AASAssignScheduleHandlerBean create/update LUNCH taskID: "
            + taskId);
      }

    }

    //
    return retval;
  }

  /**
   * Build Task Document For Lunch.
   */
  protected TaskDocument buildTaskDocumentForLunch(AssignTaskType assignTask,
      String companyCode)
  {
    TaskDocument taskDocument = TaskDocument.Factory.newInstance();
    TaskType taskType = taskDocument.addNewTask();

    taskType.setAssigneeId(assignTask.getAssigneeId());
    taskType
        .setAssignmentType(AppraisalAssignmentConstants.LUNCH_ASSIGNMENT_TYPE);
    taskType.setCompanyCode(companyCode);
    taskType.setDispatchCenterName(assignTask.getWorkGroupCode());
    taskType.setDisposition(assignTask.getDisposition());
    taskType.setDuration(assignTask.getDuration());
    taskType.setScheduleDateTime(assignTask.getScheduleDateTime());
    taskType.setClaimNumber(LUNCH_CLAIM_NUMBER);

    if (assignTask.getTaskId() > 0) {
      taskType.setTaskId(assignTask.getTaskId());
    }

    //
    return taskDocument;
  }

  /**
   * Spring layer invocation of functionality that pre-dated the introduction of
   * parallel processing.
   */
  protected AppraisalAssignmentServiceHandler getServiceHandler()
      throws MitchellException
  {
    AppraisalAssignmentServiceHandler h = null;

    try {
      h = (AppraisalAssignmentServiceHandler) BeanLocator
          .getBean("AppraisalAssignmentServiceHandler");
    } catch (IllegalAccessException e) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_PARALLEL_ASSIGNSCHEDULE_PROCESSING_ERROR,
          this.getClass().getName(),
          "getServiceHandler",
          "Exception getting SpringBean instance, probably no reference count.",
          e);
    }

    return h;
  }

}
