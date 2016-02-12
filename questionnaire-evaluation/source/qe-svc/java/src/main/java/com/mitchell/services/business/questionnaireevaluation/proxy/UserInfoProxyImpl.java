package com.mitchell.services.business.questionnaireevaluation.proxy;

import org.apache.log4j.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.core.userinfo.client.UserInfoClient;
import com.mitchell.services.core.userinfo.ejb.UserInfoServiceEJBRemote;
import com.mitchell.utils.misc.AppUtilities;

public class UserInfoProxyImpl implements UserInfoProxy {

	private static final String CLASS_NAME = UserInfoProxyImpl.class.getName();
	private Logger logger = Logger.getLogger(CLASS_NAME);

	public UserInfoDocument getUserInfo(final String coCode, final String userID)
			throws MitchellException {

		UserInfoDocument userInfoDocument = null;

		try {
			
			UserInfoServiceEJBRemote userInfoServiceEJBRemote = UserInfoClient
					.getUserInfoEJB();
			userInfoDocument = userInfoServiceEJBRemote.getUserInfo(coCode,
					userID, null);
		} catch (MitchellException me) {
			final String desc = "Exception from UserInfoProxy:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));
			throw new MitchellException(
					QuestionnaireEvaluationConstants.QE_CALL_USERINFO_SERVICE_ERROR,
					CLASS_NAME, "getUserInfo", desc, me);
		}catch (Exception me) {
			final String desc = "Exception from UserInfoProxy:: " + me;
			logger.fatal("Got Exception:"
					+ AppUtilities.getStackTraceString(me));
			throw new MitchellException(
					QuestionnaireEvaluationConstants.QE_CALL_SERVICE_UNKNOWN_ERROR,
					CLASS_NAME, "getUserInfo", desc, me);
		}

		return userInfoDocument;
	}

}
