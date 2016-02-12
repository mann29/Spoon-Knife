package com.mitchell.services.business.questionnaireevaluation.restws;

import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.questionnaireevaluation.client.QuestionnaireEvaluationClient;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.business.questionnaireevaluation.ejb.QuestionnaireEvaluationEJB;
import com.mitchell.services.business.questionnaireevaluation.ejb.QuestionnaireEvaluationEJBRemote;

@Path("/Question")
public class QuestionnairEvaluationRestWS {
	/**
	 * class name.
	 */
	private static final String CLASS_NAME = QuestionnaireEvaluationEJB.class
			.getName();

	/**
	 * logger.
	 */
	private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);

	/**
	 * This will be the entry point WS for saving questionnaire evaluation that
	 * will come from FNOL.
	 * 
	 * @param evaluationDetailsJson
	 *            : This object will contain details of the question that needs
	 *            to be saved in the questionnaire format.
	 * @return Response : Response will contain the next question that user
	 *         needs to answer.
	 * @throws MitchellException
	 */

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.TEXT_PLAIN)
	public Response saveUpdateEvalAndGetNxtQustn(String evaluationDetailsJson)
			throws MitchellException {
		final String methodName = "saveUpdateEvalAndGetNxtQustn";
		LOGGER.entering(CLASS_NAME, methodName);
		String nxtQustnQustnnrRes = null;
		QuestionnaireEvaluationEJBRemote qustnnrEvltnEJBRemote = null;
		try {
			qustnnrEvltnEJBRemote = QuestionnaireEvaluationClient.getEJB();
			nxtQustnQustnnrRes = qustnnrEvltnEJBRemote
					.saveUpdateEvalAndGetNxtQustn(evaluationDetailsJson);
		} catch (MitchellException me) {
			throw me;
		} catch (Exception e) {
			 throw new MitchellException(QuestionnaireEvaluationConstants.ERROR_SAVING_QNTNRE_EVAL, CLASS_NAME, methodName, e.getMessage(), e);
		}
		LOGGER.exiting(CLASS_NAME, methodName);
		return Response
				.ok(nxtQustnQustnnrRes)
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods",
						"GET, POST, DELETE, PUT").build();
	}
	/**
	 * This will be the entry point WS for getting the next question of partially saved
	 *  evaluation. 
	 * @param CompanyCd , DocumentId  of the saved evaluation
	 * @return Response : Response will contain the next question that user
	 *         needs to answer.
	 * @throws MitchellException
	 */

	@GET
	@Path("/{companyCd}/{documentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNextPartialSavedQuestion(@PathParam("companyCd") String companyCd, @PathParam("documentId") String documentId) throws MitchellException{
	String methodName = "getNextPartialSavedQuestion";
		String firstQustnRes = null;
		QuestionnaireEvaluationEJBRemote qustnnrEvltnEJBRemote = null;
		LOGGER.entering(CLASS_NAME, methodName);
		
		try {
			qustnnrEvltnEJBRemote = QuestionnaireEvaluationClient.getEJB();
			firstQustnRes = qustnnrEvltnEJBRemote.getNextPartialSavedQuestion(companyCd , documentId);
		} catch (MitchellException me) {
			throw me;

		}catch (Exception e) {
			 throw new MitchellException(QuestionnaireEvaluationConstants.ERROR_GETTING_FIRST_QUSTN, CLASS_NAME, methodName,QuestionnaireEvaluationConstants.ERROR_GETTING_FIRST_QUSTN_MSG , e);
		}
		LOGGER.exiting(CLASS_NAME, methodName);
		return Response
		.ok(firstQustnRes)
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Allow-Methods",
				"GET, POST, DELETE, PUT").build();

		
	}
	/**
	 * This will be the entry point WS for getting the first question of the questionnaire
	 * @param CompanyCd 
	 * @return Response : Response will contain the next question that user
	 *         needs to answer.
	 * @throws MitchellException
	 */
	@GET
	@Path("/{companyCd}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFirstQuestion(@PathParam("companyCd") String companyCd) throws MitchellException{
		String firstQustnRes = null;
		String methodName = "getFirstQuestion";
		QuestionnaireEvaluationEJBRemote qustnnrEvltnEJBRemote = null;
		LOGGER.entering(CLASS_NAME, methodName);
		try {
			qustnnrEvltnEJBRemote = QuestionnaireEvaluationClient.getEJB();
			firstQustnRes = qustnnrEvltnEJBRemote.getFirstQuestion(companyCd);
		} catch (MitchellException me) {
			throw me;

		}catch (Exception e) {
			 throw new MitchellException(QuestionnaireEvaluationConstants.ERROR_GETTING_NEXT_SAVED_QUSTN, CLASS_NAME, methodName, QuestionnaireEvaluationConstants.ERROR_GETTING_NEXT_SAVED_QUSTN_MSG, e);
			}
		LOGGER.exiting(CLASS_NAME, methodName);
		return Response
		.ok(firstQustnRes)
		.header("Access-Control-Allow-Origin", "*")
		.header("Access-Control-Allow-Methods",
				"GET, POST, DELETE, PUT").build();

		
	}
	
	
}
