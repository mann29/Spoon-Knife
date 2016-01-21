package com.mitchell.services.business.partialloss.appraisalassignment.proxy;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;
import com.mitchell.services.core.workassignment.client.WorkAssignmentClient;

public class WorkAssignmentProxyImpl implements WorkAssignmentProxy
{

  // @Override
  public WorkAssignment getWorkAssignmentByTaskID(Long taskId)
      throws MitchellException
  {
    final WorkAssignmentClient workAssignmentClient = new WorkAssignmentClient();
    return workAssignmentClient.getWorkAssignmentByTaskID(taskId);
  }

  // @Override
  public WorkAssignment[] getWorkAssignmentsByClaimIdExposureId(
      final String companyCode, final long claimId, final long exposureId,
      final String[] waType)
      throws MitchellException
  {
    final WorkAssignmentClient workAssignmentClient = new WorkAssignmentClient();
    return workAssignmentClient.getWorkAssignmentsByClaimIdExposureId(
        companyCode, claimId, exposureId, waType);
  }

  // @Override
  public WorkAssignment save(final WorkAssignmentDocument waDoc)
      throws MitchellException
  {
    final WorkAssignmentClient workAssignmentClient = new WorkAssignmentClient();
    return workAssignmentClient.save(waDoc);

  }

  public WorkAssignment unCancel(final WorkAssignmentDocument waDoc,
      final UserInfoDocument loggedInUserInfo)
      throws MitchellException
  {
    final WorkAssignmentClient workAssignmentClient = new WorkAssignmentClient();
    return workAssignmentClient.unCancel(waDoc, loggedInUserInfo);

  }

  public boolean saveAssignmentBeenUpdatedFlag(final long workAssignmentTaskId,
      final String value, final UserInfoDocument loggedInUserInfo)
      throws MitchellException
  {
    final WorkAssignmentClient workAssignmentClient = new WorkAssignmentClient();
    return workAssignmentClient.saveAssignmentBeenUpdatedFlag(
        Long.valueOf(workAssignmentTaskId), "Y", loggedInUserInfo);
  }

  public Long saveWorkAssignmentHist(long taskID, String event)
      throws MitchellException
  {

    WorkAssignmentClient workAssignmentClient = new WorkAssignmentClient();
    return workAssignmentClient.saveWorkAssignmentHist(taskID, event);
  }

}
