package com.mitchell.services.business.partialloss.appraisalassignment.util;

import java.util.Locale;
import java.util.ResourceBundle;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestConstants;
import com.mitchell.utils.misc.AppUtilities;

public class InternationlizeDataUtil {

	private static final String CLASS_NAME = InternationlizeDataUtil.class
			.getName();

	
	/**
	 * get the resource bundle
	 * 
	 * @param locale
	 * @return
	 * @throws MitchellException
	 */
	public ResourceBundle getResourceBundle(Locale locale)
			throws MitchellException {

		ResourceBundle recBundle = null;

		try {
			recBundle = ResourceBundle.getBundle(AppraisalAssignmentConstants.APPRAISAL_ASSIGN_RES, locale);
			if (recBundle == null) {

				throw new MitchellException(
						AppraisalAssignmentConstants.ERROR_GETTING_RESOURCE_BUNDLE,
						CLASS_NAME,
						"getResourceBundle",
						"",
						AppraisalAssignmentConstants.ERROR_GETTING_RESOURCE_BUNDLE_MSG
								+ "\n" + "ResourceBundle is null");

			}
		} catch (Exception e) {		
				MitchellException me = new MitchellException(
						AppraisalAssignmentConstants.ERROR_GETTING_RESOURCE_BUNDLE,
						CLASS_NAME,
						"getResourceBundle",
						"",
						AppraisalAssignmentConstants.ERROR_GETTING_RESOURCE_BUNDLE_MSG
								+ "\n"
								+ AppUtilities.getStackTraceString(e, true));
				me.setSeverity(MitchellException.SEVERITY.FATAL);
				me.setApplicationName(SupplementRequestConstants.APP_NAME);
				me.setModuleName(SupplementRequestConstants.MODULE_NAME);

				throw me;
			

		}
		return recBundle;
	}

	/**
	 * get translated value of key on the basis of culture.
	 * 
	 * @param key
	 * @param culture
	 * @return
	 * @throws MitchellException
	 */

	public String getTranslatedValue(String pkey, String culture)
			throws MitchellException {
		String key = pkey.replaceAll("\\s", "");
		Locale locale = new Locale(culture);
		ResourceBundle rsBundle = getResourceBundle(locale);
		if (rsBundle.containsKey(key)) {
			return rsBundle.getString(key);
		} else {
			return key;
		}
	}

}
