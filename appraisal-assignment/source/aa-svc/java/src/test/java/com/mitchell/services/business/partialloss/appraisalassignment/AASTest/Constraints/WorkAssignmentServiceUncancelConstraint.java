package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.Constraints;

import org.jmock.core.Constraint;

import com.mitchell.schemas.workassignment.ScheduleInfoType;
import com.mitchell.schemas.workassignment.WorkAssignmentDocument;

public class WorkAssignmentServiceUncancelConstraint implements Constraint {
	private WorkAssignmentDocument workAssignmentDocument =null; 
	public boolean eval(Object o) {
		workAssignmentDocument = (WorkAssignmentDocument) o;
		return checkEstimatorIsEmpty();
	}

	public StringBuffer describeTo(StringBuffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}
	private boolean checkEstimatorIsEmpty() {
		boolean isEmptyEstimator = false;
		ScheduleInfoType scheduleInfoType = workAssignmentDocument.getWorkAssignment().getCurrentSchedule();
		if(scheduleInfoType != null && scheduleInfoType.getAssignee() == null && scheduleInfoType.getAssigneeID() == null && scheduleInfoType.getAssigneeUserType() == null)
			isEmptyEstimator = true;
		return isEmptyEstimator;
	}
}
