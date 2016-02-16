package com.mitchell.services.core.partialloss.apddelivery.pojo.proxy;

import java.util.List;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.technical.partialloss.estimate.bo.Document;
import com.mitchell.services.technical.partialloss.estimate.bo.Estimate;
import com.mitchell.services.technical.partialloss.estimate.client.EstimatePackageClient;

/**
 * @author jagdish.kumar
 * 
 */
public class EstimatePackageProxyImpl implements EstimatePackageProxy {
	
	/**
     * class name.
     */
    private static final String CLASS_NAME = 
    	EstimatePackageProxyImpl.class.getName();
    /**
     * logger instance.
     */
    private static Logger logger = Logger.getLogger(CLASS_NAME);
	/**
	 * This method returns estimate by estimate doc id.
	 * 
	 * @param estimateDocId
	 * @return Estimate
	 * @throws MitchellException
	 */
	public Estimate getEstimateByEstimateDocId(Long estimateDocId)
			throws MitchellException {
		String methodName = "getEstimateByEstimateDocId";
		logger.entering(CLASS_NAME, methodName);
		
		EstimatePackageClient estimateClient = new EstimatePackageClient();
		Estimate estimate = estimateClient
				.getEstimateByEstimateDocId(estimateDocId);
		logger.exiting(CLASS_NAME, methodName);
		return estimate;
	}

	/**
	 * This method gets estimate.
	 * 
	 * @param estimateDocId
	 * @param includeSubObjects
	 * @return Estimate
	 * @throws MitchellException
	 */
	public Estimate getEstimateAndDocByDocId(long estimateDocId,
			boolean includeSubObjects) throws MitchellException {
		String methodName = "getEstimateAndDocByDocId";
		logger.entering(CLASS_NAME, methodName);
		
		EstimatePackageClient estimateClient = new EstimatePackageClient();
		Estimate estimate = estimateClient.getEstimateAndDocByDocId(
				estimateDocId, includeSubObjects);
		logger.exiting(CLASS_NAME, methodName);
		return estimate;
	}

	/**
	 * This method gets document by document id.
	 * 
	 * @param documentId
	 * @return Document
	 * @throws MitchellException
	 */
	public Document findDocumentByDocumentId(Long documentId)
			throws MitchellException {
		String methodName = "findDocumentByDocumentId";
		logger.entering(CLASS_NAME, methodName);
		
		EstimatePackageClient estimateClient = new EstimatePackageClient();
		Document doc = estimateClient.findDocumentByDocumentId(documentId);
		logger.exiting(CLASS_NAME, methodName);
		return doc;
	}

	/**
	 * This method gets document by estimate doc id.
	 * 
	 * @param estDocId
	 * @return Document
	 * @throws MitchellException
	 */
	public Document findDocumentByDocIdWithRelated(Long estDocId)
			throws MitchellException {
		String methodName = "findDocumentByDocIdWithRelated";
		logger.entering(CLASS_NAME, methodName);
		
		EstimatePackageClient estimateClient = new EstimatePackageClient();
		Document doc = estimateClient.findDocumentByDocIdWithRelated(estDocId);
		logger.exiting(CLASS_NAME, methodName);
		return doc;
	}
	
	/**
	 * This method returns Documents for ExposureId and DocTypes.
	 * @param exposureId
	 * @param docTypes
	 * @return Documents
	 * @throws MitchellException
	 */
	public Document[] getDocumentsByExposureId (Long exposureId,  List<String> docTypes) throws MitchellException {
		String methodName = "getDocumentsByExposureId";
		logger.entering(CLASS_NAME, methodName);
		Document[] photoDocs= null; 
		EstimatePackageClient estimateClient = new EstimatePackageClient();
		List<Document>	photoDocsList = estimateClient.getDocumentsByExposureId(exposureId, docTypes);	
		if (photoDocsList != null && !photoDocsList.isEmpty()){
			photoDocs = (Document[])photoDocsList.toArray(new Document[photoDocsList.size()]);
		}
		logger.exiting(CLASS_NAME, methodName);
		return photoDocs;
	}
}
