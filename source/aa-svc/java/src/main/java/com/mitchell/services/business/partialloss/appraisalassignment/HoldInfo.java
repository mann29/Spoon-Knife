package com.mitchell.services.business.partialloss.appraisalassignment; 
import java.util.Calendar;

	public class HoldInfo implements java.io.Serializable
	{ 
    private static final long serialVersionUID = 1L;
	public String getOnHold() {
			return OnHold;
		}
		public void setOnHold(String onHold) {
			OnHold = onHold;
		}
		public String getOnHoldUpdatedBy() {
			return OnHoldUpdatedBy;
		}
		public void setOnHoldUpdatedBy(String onHoldUpdatedBy) {
			OnHoldUpdatedBy = onHoldUpdatedBy;
		}
		public Calendar getOnHoldUpdatedByDateTime() {
			return OnHoldUpdatedByDateTime;
		}
		public void setOnHoldUpdatedByDateTime(Calendar onHoldUpdatedByDateTime) {
			OnHoldUpdatedByDateTime = onHoldUpdatedByDateTime;
		}
		public String getOnHoldReasonCode() {
			return OnHoldReasonCode;
		}
		public void setOnHoldReasonCode(String onHoldReasonCode) {
			OnHoldReasonCode = onHoldReasonCode;
		}
		public String getOnHoldReasonNotes() {
			return OnHoldReasonNotes;
		}
		public void setOnHoldReasonNotes(String onHoldReasonNotes) {
			OnHoldReasonNotes = onHoldReasonNotes;
		}
	private String OnHold;
	private String OnHoldUpdatedBy;
	private Calendar OnHoldUpdatedByDateTime;
	private String OnHoldReasonCode;
	private String OnHoldReasonNotes;
	} 
