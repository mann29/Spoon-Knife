package com.mitchell.services.business.partialloss.appraisalassignment.parallel.submit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.xmlbeans.XmlException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.schemas.MitchellEnvelopeType;
import com.mitchell.schemas.schedule.AssignTaskType;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.external.AASExternalAccessor;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASParallelHandlerType;
import com.mitchell.utils.misc.UUIDFactory;
import com.mitchell.utils.xml.MIEnvelopeHelper;

@Stateless
public class AASAssignScheduleSubmitEJBBean extends AASParallelSubmitBase
    implements AASAssignScheduleSubmitEJB
{

  @EJB
  protected AASExternalAccessor extAccess;

  /**
   * Submit the Assign Schedule Appraisal Assignment parallel request.
   */
  public HashMap submitAssignScheduleAppraisalAssignment(AssignTaskType[] tdoc,
      UserInfoDocument assignorUserInfoDocument)
      throws MitchellException
  {
    // build the ME docs for the submit request and the list of strings of the docs
    List<String> assignList = buildAssignScheduleList(tdoc,
        assignorUserInfoDocument);

    // submit the docs
    String pGroupId = this.pEjb.submitRequests(assignList);

    if (logger.isLoggable(java.util.logging.Level.INFO)) {
      logger.info("submitAssignScheduleAppraisalAssignment. GroupId:"
          + pGroupId + ", Count:" + assignList.size());
    }

    // Retrieve the results
    List<String> responseList = this.pEjb.retrieveResponses(pGroupId,
        assignList.size());

    // Build the required return from the responses
    java.util.HashMap<Long, Integer> mapResultSet = buildReturnMap(responseList);

    // Reconcile any missing responses
    mapResultSet = reconcileMissingResponses(tdoc, mapResultSet);

    // 
    return mapResultSet;
  }

  /**
   * Build the Assign Schedule List that is used to submit the requests.
   */
  protected List<String> buildAssignScheduleList(AssignTaskType[] tdoc,
      UserInfoDocument assignorUserInfoDocument)
  {
    ArrayList<String> assignList = new ArrayList<String>();
    String workItemId = UUIDFactory.getInstance().getUUID();

    //
    for (AssignTaskType assignTask : tdoc) {

      // Build the assign task specific ME
      MitchellEnvelopeDocument meDoc = buildAssignScheduleItemME(assignTask,
          assignorUserInfoDocument, workItemId);

      // Add the ME as a string to the list
      assignList.add(meDoc.toString());

    }

    //
    return assignList;
  }

  /**
   * Build the Assign Schedule Item Mitchell Envelope.
   */
  protected MitchellEnvelopeDocument buildAssignScheduleItemME(
      AssignTaskType assignTask, UserInfoDocument assignorUserInfoDocument,
      String workItemId)
  {
    // Init the ME Doc and Helper
    MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
        .newInstance();
    MitchellEnvelopeType meType = meDoc.addNewMitchellEnvelope();
    MIEnvelopeHelper meHelper = this.extAccess.buildMEHelper(meDoc);

    // Add the AssignTask Document
    meHelper.addNewEnvelopeBody(AASParallelConstants.ME_ASSIGN_TASK_TYPE_ID,
        assignTask, AASParallelConstants.ME_ASSIGN_TASK_TYPE_TYPE);

    // Add the UserInfoDocument
    meHelper.addNewEnvelopeBody(AASParallelConstants.ME_LOGGED_IN_USERINFO_ID,
        assignorUserInfoDocument,
        AASParallelConstants.ME_LOGGED_IN_USERINFO_TYPE);

    // Add the processing type NV Pair
    meHelper.addEnvelopeContextNVPair(AASParallelConstants.ME_PROCESSING_TYPE,
        AASParallelHandlerType.ASSIGN_SCHEDULE.name());

    // Add the common items
    addMECommon(meHelper, workItemId, assignorUserInfoDocument.getUserInfo()
        .getOrgCode());

    //
    return meDoc;
  }

  /**
   * Add Common Items the the MitchellEnvelope.
   */
  protected void addMECommon(MIEnvelopeHelper meHelper, String workItemId,
      String companyCode)
  {
    // Add the workItemId
    meHelper.addEnvelopeContextNVPair(AASParallelConstants.ME_WORK_ITEM_ID,
        workItemId);

    // Add the company code
    meHelper.addEnvelopeContextNVPair(AASParallelConstants.ME_COMPANY_CODE,
        companyCode);

  }

  /**
   * Reconcile the response Map to see if there are any missing responses.
   * Returns the updated response Map.
   */
  protected java.util.HashMap<Long, Integer> reconcileMissingResponses(
      AssignTaskType[] tdoc, java.util.HashMap<Long, Integer> mapResultSet)
  {
    // If we are missing something in the response
    if (tdoc.length > mapResultSet.size()) {

      int lunchCount = 0;
      int unknownCount = 0;
      int appAsgCount = 0;
      for (AssignTaskType assignTask : tdoc) {

        // Skip lunches
        if (!(assignTask.isSetAssignmentType() && AppraisalAssignmentConstants.LUNCH_ASSIGNMENT_TYPE
            .equalsIgnoreCase(assignTask.getAssignmentType()))) {

          appAsgCount++;

          // If this taskId is not in the list then mark it as unknown
          Long taskId = Long.valueOf(assignTask.getTaskId());
          Integer ival = mapResultSet.get(taskId);
          if (ival == null) {
            mapResultSet.put(taskId,
                Integer.valueOf(AppraisalAssignmentConstants.RESULT_UNKNOWN));

            unknownCount++;

            if (logger.isLoggable(java.util.logging.Level.INFO)) {
              logger.info("Reconciled unknown parallel result for taskId: "
                  + taskId.longValue());
            }

          }

        } else {
          lunchCount++;
        }
      }

      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger
            .info("AASAssignScheduleSubmitEJBBean reconciled parallel requests. LunchCount: "
                + lunchCount
                + ", AppraisalAsgCount: "
                + appAsgCount
                + ", UnknownCount: " + unknownCount + ", Total: " + tdoc.length);
      }

    }

    //
    return mapResultSet;
  }

  /**
   * Builds the return list of responses. Lunch assignments are ignored.
   * 
   * @throws XmlException
   * @throws MitchellException
   * 
   */
  protected java.util.HashMap<Long, Integer> buildReturnMap(
      List<String> responseList)
      throws MitchellException
  {
    java.util.HashMap<Long, Integer> mapResultSet = new java.util.HashMap<Long, Integer>();

    try {

      for (String meStr : responseList) {
        MitchellEnvelopeDocument meDoc = MitchellEnvelopeDocument.Factory
            .parse(meStr);
        MIEnvelopeHelper meHelper = this.extAccess.buildMEHelper(meDoc);

        // Get the assign task from the response
        AssignTaskType assignTaskType = extractAssignTaskFromResponseME(meHelper);

        // Ignore lunch responses
        if (!(assignTaskType.isSetAssignmentType() && AppraisalAssignmentConstants.LUNCH_ASSIGNMENT_TYPE
            .equalsIgnoreCase(assignTaskType.getAssignmentType()))) {

          // Get the taskId
          long taskId = assignTaskType.getTaskId();

          // Get Result
          int resultInt = extractResultIndicatorFromResponseME(meHelper);

          // Store result
          mapResultSet.put(Long.valueOf(taskId), Integer.valueOf(resultInt));

        }

      }

    } catch (XmlException xmle) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_PARALLEL_ASSIGNSCHEDULE_PROCESSING_ERROR,
          this.getClass().getName(), "buildReturnMap", "XMLException", xmle);
    }

    //
    return mapResultSet;
  }

  /**
   * Extract the TaskId From a Response ME.
   * 
   * @throws MitchellException
   * @throws XmlException
   * 
   */
  protected AssignTaskType extractAssignTaskFromResponseME(
      MIEnvelopeHelper meHelper)
      throws MitchellException, XmlException
  {
    EnvelopeBodyType envelopeBodyAT = meHelper
        .getEnvelopeBody(AASParallelConstants.ME_ASSIGN_TASK_TYPE_ID);
    String assignTaskStr = meHelper
        .getEnvelopeBodyContentAsString(envelopeBodyAT);
    AssignTaskType assignTaskType = AssignTaskType.Factory.parse(assignTaskStr);
    return assignTaskType;
  }

  /**
   * Extract that Result Indicator From a Response ME.
   */
  protected int extractResultIndicatorFromResponseME(MIEnvelopeHelper meHelper)
  {
    String resultStr = meHelper
        .getEnvelopeContextNVPairValue(AASParallelConstants.ME_ASSIGN_TASK_TYPE_RESULT);
    return Integer.parseInt(resultStr);
  }
}
