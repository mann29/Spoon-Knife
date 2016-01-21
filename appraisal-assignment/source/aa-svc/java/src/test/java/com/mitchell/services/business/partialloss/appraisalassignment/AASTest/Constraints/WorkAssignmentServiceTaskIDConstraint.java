package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.Constraints;

import org.jmock.core.Constraint;

public class WorkAssignmentServiceTaskIDConstraint implements Constraint {
	private long taskID = -1; 
	public boolean eval(Object o) {
		return ((Long)o).longValue() == taskID;
	}
	public WorkAssignmentServiceTaskIDConstraint (final long taskID) {
		this.taskID = taskID;
	}
	public StringBuffer describeTo(StringBuffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
