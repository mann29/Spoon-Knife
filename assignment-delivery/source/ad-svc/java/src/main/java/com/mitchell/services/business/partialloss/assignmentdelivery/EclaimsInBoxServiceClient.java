package com.mitchell.services.business.partialloss.assignmentdelivery;

import com.mitchell.services.business.partialloss.eclaiminboxsvc.ECInboxSvcException;

public interface EclaimsInBoxServiceClient {

	/**
	 * 
	 * @param companyCode
	 *            String containing the eClaim user's company code.
	 * @param userID
	 *            String containing the eClaim user's user ID.
	 * @param msgBody
	 *            String containing the body of the message to be sent.
	 * @param logIfNoInboxFound
	 *            boolean flag indicating whether to log if no inbox found for
	 *            user.
	 * @param workItemID
	 *            String containing the work item id.
	 * 
	 * @throws ECInboxSvcException
	 */
	public abstract void sendMessage(String companyCode, String userID,
			String msgBody, boolean logIfNoInboxFound, String workItemID)
			throws ECInboxSvcException;

	/**
	 * <p>
	 * This method is called to send a file to an eClaim user's unbox. The
	 * method performs the work described in this class' description.
	 * 
	 * @param companyCode
	 *            String containing the eClaim user's company code.
	 * @param userID
	 *            String containing the eClaim user's user ID.
	 * @param fromPath
	 *            String containing the path to file to be sent.
	 * @param moveFlag
	 *            boolean flag indicating whether to move (=true) or copy
	 *            (=false) to inbox.
	 * @param workItemID
	 *            String containing the work item id.
	 * 
	 * @throws ECInboxSvcException
	 */
	public abstract void sendFile(String companyCode, String userID,
			String fromPath, boolean moveFlag, String workItemID)
			throws ECInboxSvcException;

	/**
	 * 
	 * @param companyCode
	 *            String containing the eClaim user's company code.
	 * @param userID
	 *            String containing the eClaim user's user ID.
	 * @param reviewerCompanyCode
	 *            String containing, if DRP, the Reviewer Company Code
	 * @param reviewerUserID
	 *            String containing, if DRP, the Reviewer UserID
	 * @param fromPath
	 *            String containing the path to file to be sent.
	 * @param moveFlag
	 *            boolean flag indicating whether to move (=true) or copy
	 *            (=false) to inbox.
	 * @param workItemID
	 *            String containing the work item id.
	 * 
	 * @throws ECInboxSvcException
	 */
	public abstract void sendFile(String companyCode, String userID,
			String reviewerCompanyCode, String reviewerUserID, String fromPath,
			boolean moveFlag, String workItemID) throws ECInboxSvcException;

}