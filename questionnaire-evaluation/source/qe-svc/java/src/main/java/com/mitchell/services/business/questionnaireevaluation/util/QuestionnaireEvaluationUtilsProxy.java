package com.mitchell.services.business.questionnaireevaluation.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.xmlbeans.XmlObject;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.questionnaireevaluation.QuestionnaireEvaluationContext;
import com.mitchell.services.business.questionnaireevaluation.dto.QuestionnaireRqRsDTO;
import com.mitchell.services.business.questionnaireevaluation.proxy.AppLogProxy;
import com.mitchell.services.business.questionnaireevaluation.proxy.SystemConfigurationProxy;

public interface QuestionnaireEvaluationUtilsProxy {

	/**
	 * For logging application events using APPLOG service.
	 * 
	 * @param responseMap
	 * <code>Map</code> responseMap
	 * 
	 * @param userInfo
	 * <code>UserInfoDocument</code> userInfo
	 * 
	 * @param appEventCode
	 * <code>String</code> appEventCode
	 * 
	 * @throws MitchellException
	 *  in case not able to log
	 * 
	 */
	void logAppEvent(Map responseMap,
			UserInfoDocument userInfo, String appEventCode)
			throws MitchellException;

	/**
	 * This method checks whether the Logger Level is FINE and then
	 * logs the entire XML as a String.
	 * 
	 * @param message
	 *           Message
	 * 
	 */
	void logSEVEREMessage(String message);

	/**
	 * This method checks whether the Logger Level is FINE and then
	 * logs the entire XML as a String.
	 * 
	 * @param message
	 * <code>String</code> message
	 */

	void logFINEMessage(String message);

	/**
	 * This method checks whether the Logger Level is INFO and then
	 * logs the message as a String.
	 * 
	 * @param message
	 * <code>String</code> message
	 */

	void logINFOMessage(String message);

	/**
	 * THis method checks for the validity of XMlObject.
	 * @return true - if xml validates schema else false
	 * @param xo - XmlObject
	 */

	boolean isValid(XmlObject xo);

	/**
	 * Method to convert a stream to String.
	 * 
	 * @param is - InputStream
	 * 
	 * @return String
	 * 
	 * @throws IOException
	 * in case any exception with streams
	 */

	 String convertStreamToString(InputStream is)
			throws IOException;

	/**
	 * Method to retrieve the path for sub-directory based on current date.
	 * 
	 * @return subDirPath
	 * <code>String</code>, subDirPath in year/month/day form.
	 * 
	 */
	// @TODO Move into utility class.s
	String getCurrentDatePath();

	/**
	 * Method to check the valid Company codes from SET file.
	 * 
	 * @param coCode
	 * <code>String</code> coCode
	 * 
	 * @return flag
	 * 
	 */
	 boolean validateCompanyCodes(String coCode);

	/*
	 *  Method to publish the message to the message bus
	 * 
	 * @param MessagingContext object
	 */
	  void publishToMessageBus(MessagingContext msgContext)
			throws MitchellException;

	/*
	 * Method to create a MitchellEnvelopDocument document 
	 *  @ param QuestionnaireEvaluationContext object
	 *  @ returns MitchellEnvelopeDocument
	 */
	  MitchellEnvelopeDocument populateMeDocument(
			QuestionnaireEvaluationContext evalContext)
			throws MitchellException;

	 void copyFileToNAS(String evaluationXMLReturned, Long docStoreId,String workItemId)
    			throws MitchellException;
    
	 AppLogProxy getAppLogProxy();
	
	 void setAppLogProxy(AppLogProxy appLogProxy);
	
	 SystemConfigurationProxy getSystemConfigProxy();

	 void setSystemConfigProxy(SystemConfigurationProxy systemConfigProxy);
	
	 /**
		 * Generates Random Evaluation ID
		 * 
		 * @return String
		 */
	 
	String generateEvaluationID();
	
	 /**
	 * Converts DTO to JSON
	 * 
	 * @param requestDto
	 * @return
	 */
	String dtoToJson(QuestionnaireRqRsDTO responseDto);
	
	 /**
	  * Converts jSON to DTO
	  * @param inputJson
	  * @return
	  */
	QuestionnaireRqRsDTO jsonToDto(String inputJson);
}