/**
 * 
 */
package com.mitchell.services.business.questionnaireevaluation.proxy;

import java.util.List;

import com.mitchell.common.exception.MitchellException;

/**
 * @author jagdish.kumar
 *
 */
public interface CachingServiceProxy {

	/**
	 * This method returns value for a cached key.
	 * @param key
	 * @return
	 */
	public Object get(String key);
	/**
	 * This method add key value to caching.
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(String key, Object value);
	
	/**
	 * This method removes all keys matching a particular pattern.
	 * @param patterns
	 * @return
	 */
	public boolean removeAllPatternMatchedKeys(List<String> patterns);
	
	/**
	 * This method checks if a key exists in cache.
	 * @param key
	 * @return
	 */
	public boolean isExist(String key);
	
	
	/**
	 * This method generates the key to for caching.
	 * @param methodName
	 * @param params
	 * @return
	 * @throws MitchellException 
	 */
	public String generateKey(String methodName, Object[] params) throws MitchellException;
	
}
