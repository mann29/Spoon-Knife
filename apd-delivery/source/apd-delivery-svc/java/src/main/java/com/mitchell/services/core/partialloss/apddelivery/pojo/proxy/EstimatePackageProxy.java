package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.List;

import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.common.exception.MitchellException;

/**
 * The Interface EstimatePackageProxy.
 */
public interface EstimatePackageProxy {

	/**
	 * This method returns estimate by estimate doc id.
	 * 
	 * @param estimateDocId
	 * @return Estimate
	 * @throws MitchellException
	 */
	Estimate getEstimateByEstimateDocId(Long estimateDocId)
			throws MitchellException;

	/**
	 * This method gets estimate.
	 * 
	 * @param estimateDocId
	 * @param includeSubObjects
	 * @return Estimate
	 * @throws MitchellException
	 */
	Estimate getEstimateAndDocByDocId(long estimateDocId,
			boolean includeSubObjects) throws MitchellException;

	/**
	 * This method gets document by document id.
	 * 
	 * @param documentId
	 * @return Document
	 * @throws MitchellException
	 */
	Document findDocumentByDocumentId(Long documentId) throws MitchellException;

	/**
	 * This method gets document by estimate doc id.
	 * 
	 * @param estDocId
	 * @return Document
	 * @throws MitchellException
	 */
	Document findDocumentByDocIdWithRelated(Long estDocId)
			throws MitchellException;
	
	/**
	 * This method returns Documents for ExposureId and DocTypes.
	 * @param exposureId
	 * @param docTypes
	 * @return Documents
	 * @throws MitchellException
	 */
	Document[] getDocumentsByExposureId (Long exposureId,  List<String> docTypes) throws MitchellException;

}
