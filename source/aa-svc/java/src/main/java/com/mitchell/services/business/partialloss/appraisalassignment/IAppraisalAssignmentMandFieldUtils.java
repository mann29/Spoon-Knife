package com.mitchell.services.business.partialloss.appraisalassignment;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.appraisalassignment.AdditionalAppraisalAssignmentInfoDocument;
import com.mitchell.schemas.dispatchservice.AdditionalTaskConstraintsDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.utils.xml.MitchellEnvelopeHelper;

public interface IAppraisalAssignmentMandFieldUtils {

	public abstract void setAppraisalAssignmentUtils(
			IAppraisalAssignmentUtils appraisalAssignmentUtils);

	public abstract void setCommonUtils(AASCommonUtils commonUtils);

	/**
	 * <p>
	 * This method performs business-rule validation of the input
	 * MitchellEnvelopeDoc to a set of mandatory WCAA Custom Setting fields
	 * </p>
	 * <ul>
	 * <br>
	 * ** NOTE: <br>
	 * ** Mandatory field business-rule validation is based on comparison of XML
	 * fields <br>
	 * ** in the provided Appraisal Assignment MitchellEnvelope with the <br>
	 * ** WCAA Company-Level Custom Settings for confirmation that all <br>
	 * ** required/mandatory fields are present.
	 * </ul>
	 * 
	 * @param mitchellEnvDoc
	 *          Object of MitchellEnvelopeDocument
	 * @return boolean - returns TRUE if all mandatory fields are present.
	 * @throws MitchellException
	 *           Throws MitchellException to the caller in case of any
	 *           exception arise.
	 * 
	 */
	public abstract boolean hasAllMandatoryAppraisalAssignmentFields(
			MitchellEnvelopeHelper meHelper,
			AdditionalAppraisalAssignmentInfoDocument additionalAAInfoDoc,
			AdditionalTaskConstraintsDocument additionalTaskConstraintsDoc)
			throws MitchellException;

	/**
	 * This utility method retrieves WCA Custom Settings for Mandatory Fields
	 * based on Company Code.
	 * 
	 * @param coCode
	 *          Mitchell Company Code for the carrier.
	 * @return Returns Array of Mandatory Carrier Settings (having a required
	 *         value of "Y")
	 * @throws MitchellException
	 *           Throws an Exception in case of unable to retrieve carrier
	 *           custom settings.
	 */
	public abstract java.util.ArrayList retrieveCarrierSettingsForMandatoryFieldsInArrayList(
			String coCode) throws MitchellException;

	/**
	 * This method is a helper method which extracts
	 * AdditionalAppraisalAssignmentInfo from MitchellEnvelope Document.
	 * 
	 * @return AdditionalAppraisalAssignmentInfoDocument
	 * @throws Exception
	 *           in case MitchellEnvelope Document doesn't contains
	 *           AdditionalAppraisalAssignmentInfoDocument
	 */
	public abstract AdditionalAppraisalAssignmentInfoDocument getAdditionalAppraisalAssignmentInfoDocumentFromME(
			MitchellEnvelopeHelper meHelper) throws Exception;

	/**
	 * Gets AdditionalTaskConstraintsDocument from MitchellEnvelope
	 * 
	 * @param meHelper
	 * @return AdditionalTaskConstraintsDocument
	 * @throws MitchellException
	 */
	public abstract AdditionalTaskConstraintsDocument getAdditionalTaskConstraintsFromME(
			MitchellEnvelopeHelper meHelper) throws MitchellException;

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	/**
	 * Utility Method for handling Business Rule Logic for Dispatch with or
	 * without a Valid Vehicle Inspection Site Address
	 * 
	 * @param mitchellEnvDoc
	 *          MitchellEnvelope Document containing BMS and AAAInfo xml
	 * @return boolean return True, if dispatch assignment with invalid address
	 *         custom setting is yes.
	 * 
	 */
	public abstract boolean isVehicleReadyForDispatch(
			MitchellEnvelopeHelper meHelper,
			AdditionalAppraisalAssignmentInfoDocument addAppAssignInfoDoc)
			throws Exception;

	//
	// Retrieve getInspectionSiteGeoCode Valid/Invalid Status in
	// additionalAAInfoDoc
	//
	public abstract String getValidVehicleLocationAddress(
			AdditionalAppraisalAssignmentInfoDocument additionalAAInfoDoc,
			String companyCode) throws Exception;

}