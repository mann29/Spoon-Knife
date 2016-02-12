/**
 * 
 */
package com.mitchell.services.business.questionnaireevaluation.proxy;

import java.util.List;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.questionnaireevaluation.constants.QuestionnaireEvaluationConstants;
import com.mitchell.services.core.caching.client.ICachingService;
import com.mitchell.services.core.caching.client.aop.impl.DefaultCacheKey;
import com.mitchell.services.core.caching.client.impl.CachingServiceClient;
import com.mitchell.services.core.caching.client.misc.CacheKeyException;
import com.mitchell.services.core.caching.client.misc.Constants;

/**
 * @author Priya.Mandhan
 *
 */
public class CachingServiceProxyImpl implements CachingServiceProxy {
	
	/**
	   * class name..
	   */
	  private static final String CLASS_NAME = CachingServiceProxyImpl.class
	      .getName();
	
	/* (non-Javadoc)
	 * @see com.mitchell.services.technical.workcenter.edog.pojo.proxy.CachingServiceProxy#get(java.lang.String)
	 */
	public Object get(String key) {
		Object cachingKeyValue = null;
		CachingServiceClient cachingClient = new CachingServiceClient(Constants.LONG_REGION,QuestionnaireEvaluationConstants.MODULE_NAME);
		cachingKeyValue = cachingClient.get(key);
		return cachingKeyValue;
	}
	/* (non-Javadoc)
	 * @see com.mitchell.services.technical.workcenter.edog.pojo.proxy.CachingServiceProxy#put(java.lang.String, java.lang.Object)
	 */
	public boolean put(String key, Object value) {		
		boolean cachingSuccess = false;
		CachingServiceClient cachingClient = new CachingServiceClient(Constants.LONG_REGION,QuestionnaireEvaluationConstants.MODULE_NAME);
		cachingSuccess = cachingClient.put(key, value);		
		return cachingSuccess;
	}
	
	/* (non-Javadoc)
	 * @see com.mitchell.services.technical.workcenter.edog.pojo.proxy.CachingServiceProxy#isExist(java.lang.String)
	 */
	public boolean isExist(String key) {
		boolean keyExists = false;
		CachingServiceClient cachingClient = new CachingServiceClient(Constants.LONG_REGION,QuestionnaireEvaluationConstants.MODULE_NAME);
		keyExists = cachingClient.isExist(key);
		return keyExists;
	}
	
	public boolean removeAllPatternMatchedKeys(List<String> patterns) {
		boolean removePatternMatchedKeys = false;
		CachingServiceClient cachingClient = new CachingServiceClient(Constants.LONG_REGION, QuestionnaireEvaluationConstants.MODULE_NAME);
		removePatternMatchedKeys = cachingClient.removeAllPatternMatchedKeys(patterns);
		return removePatternMatchedKeys;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.mitchell.services.business.questionnaireevaluation.proxy.CachingServiceProxy#generateKey(java.lang.String, java.lang.Object[])
	 */
	public String generateKey(String methodName, Object[] params) throws MitchellException {

		DefaultCacheKey keyGenerator=new DefaultCacheKey();
		String cachekKey=null;
		ICachingService cachingClient=null;
		try {
			cachekKey=keyGenerator.generateKey(methodName, params);
		} catch (CacheKeyException e) {
			throw new MitchellException(QuestionnaireEvaluationConstants.ERROR_CREATING_CACHE_KEY_FOR_QUESTIONNAIRE, CLASS_NAME, "generateKey", QuestionnaireEvaluationConstants.ERROR_CREATING_CACHE_KEY_FOR_QUESTIONNAIRE_DESC,e);
		}
		return cachekKey;
	}
}
