package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.Constraints;

import org.jmock.core.Constraint;

import com.cieca.bms.AssignmentAddRqDocument;
import com.cieca.bms.CIECADocument;
import com.cieca.bms.EstimatorType;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandler;
import com.mitchell.services.business.partialloss.appraisalassignment.util.MitchellEnvelopeHandlerImpl;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public class EstimatePackageUncancelConstraint implements Constraint {
	private MitchellEnvelopeDocument mitchellEnvelopeDocument = null;
	public EstimatePackageUncancelConstraint() {

	}
	public boolean eval(Object o) {
		this.mitchellEnvelopeDocument = (MitchellEnvelopeDocument) o;
		return checkMEUnscheduled();
	}

	public StringBuffer describeTo(StringBuffer buffer) {
		return null;
	}
	private boolean checkMEUnscheduled() {
		boolean isMEUnscheduled = false;
		try {
			final MitchellEnvelopeHandler mitchellEnvelopeHandler =  new MitchellEnvelopeHandlerImpl();
			final MitchellEnvelopeHelper mitchellEnvelopeHelper = new MitchellEnvelopeHelper(this.mitchellEnvelopeDocument);
			final CIECADocument cIECADocument = mitchellEnvelopeHandler.getCiecaFromME(mitchellEnvelopeHelper);
			final AssignmentAddRqDocument.AssignmentAddRq assignmentAddRq =  cIECADocument.getCIECA().getAssignmentAddRq();
			final String currentEstimatorID = assignmentAddRq.getVehicleDamageAssignment().getEstimatorIDs().getCurrentEstimatorID();
			EstimatorType[] estimators = assignmentAddRq.getAdminInfo().getEstimatorArray();
			if( currentEstimatorID == null &&  estimators.length == 0)
				isMEUnscheduled = true;
		} catch (Exception exception) {
			isMEUnscheduled =  false;
		}
		return isMEUnscheduled;
	}
}
