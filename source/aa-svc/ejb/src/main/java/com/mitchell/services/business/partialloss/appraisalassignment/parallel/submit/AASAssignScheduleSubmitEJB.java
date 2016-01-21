package com.mitchell.services.business.partialloss.appraisalassignment.parallel.submit;

import javax.ejb.Local;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.schedule.AssignTaskType;

@Local
public interface AASAssignScheduleSubmitEJB
{

  public java.util.HashMap submitAssignScheduleAppraisalAssignment(
      AssignTaskType[] tdoc, UserInfoDocument assignorUserInfoDocument)
      throws MitchellException;

}
