package com.mitchell.services.business.partialloss.appraisalassignment.AASTest.helper;

import java.io.IOException;

import com.mitchell.services.inttesthelper.client.IntegrationTestHelperClient;

public class AASTestHelper {
	final static IntegrationTestHelperClient testHelper = new IntegrationTestHelperClient();
	public static String getResourcePath(final String fileName) {
		String filePath = null;
		try {
			filePath = testHelper.makeResourceFilePath(fileName);
		} catch (IOException e) {
			
		}
		return filePath;
	}
}
