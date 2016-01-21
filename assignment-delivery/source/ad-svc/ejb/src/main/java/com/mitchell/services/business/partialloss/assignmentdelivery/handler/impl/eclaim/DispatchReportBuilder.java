package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.eclaim;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConfig;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryConstants;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryLogger;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryUtils;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AbstractDispatchReportBuilder;
import com.mitchell.utils.misc.UUIDFactory;

public class DispatchReportBuilder extends AbstractDispatchReportBuilder {
	private final String CLASS_NAME = "EClaimDeliveryHandler";

	// This property set by SpringFramework IOC
	private String customSettingForXsltPath;

	public String getCustomSettingForXsltPath() {
		return customSettingForXsltPath;
	}

	public void setCustomSettingForXsltPath(String customSettingForXsltPath) {
		this.customSettingForXsltPath = customSettingForXsltPath;
	}

	private final AssignmentDeliveryLogger mLogger = new AssignmentDeliveryLogger(
			this.getClass().getName());

	public File createDispatchReport(AssignmentServiceContext context)
			throws Exception {

		// Get XSLT file from custom setting
		final UserInfoDocument userInfo = context.getUserInfo();
		final String workItemId = context.getWorkItemId();
		final MitchellEnvelopeDocument mitchellEnvDoc = context
				.getMitchellEnvDoc();

		return createDispatchReport(userInfo, workItemId, mitchellEnvDoc);
	}

	/**
	 * @param userInfo
	 * @param workItemId
	 * @param mitchellEnvDoc
	 * @return
	 * @throws AssignmentDeliveryException
	 * @throws UnsupportedEncodingException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerConfigurationException
	 * @throws TransformerException
	 */
	public File createDispatchReport(final UserInfoDocument userInfo,
			final String workItemId,
			final MitchellEnvelopeDocument mitchellEnvDoc)
			throws AssignmentDeliveryException, UnsupportedEncodingException,
			TransformerFactoryConfigurationError,
			TransformerConfigurationException, TransformerException {
		AssignmentDeliveryUtils assignDeliveryUtils = new AssignmentDeliveryUtils();
		final String methodName = "createDispatchReport(AssignmentServiceContext context)";
		mLogger.entering(CLASS_NAME, methodName);
		String dispatchReportXsltFilePath = assignDeliveryUtils
				.getUserCustomSetting(userInfo, customSettingForXsltPath);
		if (mLogger.isLoggable(Level.INFO)) {
			mLogger.info("DispatchReportXsltFilePath = "
					+ dispatchReportXsltFilePath);
		}

		// Create input stream from MitchellEnvelope
		String meStr = mitchellEnvDoc.xmlText();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(
				meStr.getBytes("UTF-8"));
		StreamSource streamSrc = new StreamSource(inputStream);

		// Create output file for dispatch report
		String dispatchReportFileName = getTempDir() + File.separator
				+ userInfo.getUserInfo().getOrgCode() + "." + workItemId + "."
				+ UUIDFactory.getInstance().getUUID() + ".txt";
		File dispatchReportFile = new File(dispatchReportFileName);
		if (mLogger.isLoggable(Level.INFO)) {
			mLogger.info("DispatchReportFilePath = "
					+ dispatchReportFile.getAbsolutePath());
		}

		// Apply XSLT to MitchellEnvelope
		javax.xml.transform.TransformerFactory tFactory = javax.xml.transform.TransformerFactory
				.newInstance();
		javax.xml.transform.Transformer transformer = tFactory
				.newTransformer(new javax.xml.transform.stream.StreamSource(
						dispatchReportXsltFilePath));
		transformer
				.transform(streamSrc,
						new javax.xml.transform.stream.StreamResult(
								dispatchReportFile));

		if (mLogger.isLoggable(Level.INFO)) {
			mLogger.info("DispatchReport = \n" + dispatchReportFile.toString());
		}

		// Return reference to result TXT file
		mLogger.exiting(CLASS_NAME, methodName);
		return dispatchReportFile;
	}

	private String getTempDir() throws AssignmentDeliveryException {
		String tempdir = AssignmentDeliveryConfig.getTempDir();
		if (tempdir == null || tempdir.length() == 0) {
			mLogger.warning("Temporary directory is not defined in System configuration, so using default one = "
					+ AssignmentDeliveryConstants.DEFAULT_TEMP_DIR);
			tempdir = AssignmentDeliveryConstants.DEFAULT_TEMP_DIR;
		}

		return tempdir;
	}
}
