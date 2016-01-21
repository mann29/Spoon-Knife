package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.services.business.partialloss.eclaiminboxsvc.ECInboxSvcClient;
import com.mitchell.services.business.partialloss.eclaiminboxsvc.ECInboxSvcException;
/**
 * A wrapper of sorts over {@link com.mitchell.services.business.partialloss.eclaiminboxsvc.ECInboxSvcClient ECInboxSvcClient}
 * to keep the code testable and free of hard-wired dependencies. For API documentation, refer to the {@link com.mitchell.services.business.partialloss.eclaiminboxsvc.ECInboxSvcClient ECInboxSvcClient} class.
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv Khanwale </a>
 * Created/Modified on Jul 22, 2010
 * @see com.mitchell.services.business.partialloss.eclaiminboxsvc.ECInboxSvcClient ECInboxSvcClient </p>
 */
public class EclaimsInBoxServiceClientWrapperImpl implements EclaimsInBoxServiceClient {
	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.EclaimsInBoxServiceClient#sendMessage(java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String)
	 */
	public void sendMessage(String companyCode, String userID, String msgBody,
			boolean logIfNoInboxFound, String workItemID)
			throws ECInboxSvcException {
		ECInboxSvcClient.sendMessage(companyCode, userID, msgBody,
				logIfNoInboxFound, workItemID);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.EclaimsInBoxServiceClient#sendFile(java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String)
	 */
	public void sendFile(String companyCode, String userID, String fromPath,
			boolean moveFlag, String workItemID) throws ECInboxSvcException {
		ECInboxSvcClient.sendFile(companyCode, userID, fromPath, moveFlag,
				workItemID);
	}

	/* (non-Javadoc)
	 * @see com.mitchell.services.business.partialloss.assignmentdelivery.EclaimsInBoxServiceClient#sendFile(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean, java.lang.String)
	 */
	public void sendFile(String companyCode, String userID,
			String reviewerCompanyCode, String reviewerUserID, String fromPath,
			boolean moveFlag, String workItemID) throws ECInboxSvcException {
		ECInboxSvcClient.sendFile(companyCode, userID, reviewerCompanyCode,
				reviewerUserID, fromPath, moveFlag, workItemID);
	}

}
