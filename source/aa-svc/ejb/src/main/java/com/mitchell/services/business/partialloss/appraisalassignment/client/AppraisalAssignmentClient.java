/*
 * Class filename: AppraisalAssignmentClient.java
 * 
 * Created on Aug 17, 2012
 * Created by at101102
 * 
 * Copyright(c) 2005 Mitchell International
 * All rights reserved.
 */
package com.mitchell.services.business.partialloss.appraisalassignment.client;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.ejb.AppraisalAssignmentService;
import com.mitchell.services.business.partialloss.appraisalassignment.ejb.AppraisalAssignmentServiceLocal;
import com.mitchell.services.business.partialloss.appraisalassignment.ejb.AppraisalAssignmentServiceRemote;
import com.mitchell.systemconfiguration.SystemConfiguration;

/**
 * 
 * Client class for AppraisalAssignmentService.
 * 
 */
public class AppraisalAssignmentClient
{
  private static final String APPRAISAL_ASSIGNMENT_REMOTE_EJB_JNDI = "/AppraisalAssignmentClient/Remote/EJBJndi";
  private static final String APPRAISAL_ASSIGNMENT_REMOTE_PROVIDER_URL = "/AppraisalAssignmentClient/Remote/ProviderUrl";
  private static final String APPRAISAL_ASSIGNMENT_REMOTE_JNDI_FACTORY = "/AppraisalAssignmentClient/Remote/JNDIFactory";
  private static final String APPRAISAL_ASSIGNMENT_LOCAL_EJB_JNDI = "/AppraisalAssignmentClient/Local/EJBJndi";
  private static final String APPRAISAL_ASSIGNMENT_LOCAL_PROVIDER_URL = "/AppraisalAssignmentClient/Local/ProviderUrl";
  private static final String APPRAISAL_ASSIGNMENT_LOCAL_JNDI_FACTORY = "/AppraisalAssignmentClient/Local/JNDIFactory";

  /**
   * Gets AppraisalAssignment remote object for Client.
   * 
   * @return AppraisalAssignmentService - Stub for AppraisalAssignment Remote
   *         EJB
   * 
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   */
  public static AppraisalAssignmentServiceRemote getAppraisalAssignmentEJB()
      throws MitchellException
  {
    return (AppraisalAssignmentServiceRemote) getEJB(
        APPRAISAL_ASSIGNMENT_REMOTE_EJB_JNDI,
        APPRAISAL_ASSIGNMENT_REMOTE_PROVIDER_URL,
        APPRAISAL_ASSIGNMENT_REMOTE_JNDI_FACTORY);
  }

  private static AppraisalAssignmentService getEJB(final String jndiNamePath,
      final String providerURLPath, final String connectionFactoryPath)
      throws MitchellException
  {
    AppraisalAssignmentService appraisalAssignmentService = null;

    try {
      final String appraisalAssignmentEJBname = SystemConfiguration
          .getSettingValue(jndiNamePath);
      final String providerURL = SystemConfiguration
          .getSettingValue(providerURLPath);
      final String jndiConFactory = SystemConfiguration
          .getSettingValue(connectionFactoryPath);
      final Properties environment = new Properties();
      environment.put(Context.INITIAL_CONTEXT_FACTORY, jndiConFactory);
      environment.put(Context.PROVIDER_URL, providerURL);
      Context ctx = new InitialContext(environment);
      Object obj = ctx.lookup(appraisalAssignmentEJBname);
      appraisalAssignmentService = (AppraisalAssignmentService) obj;
    } catch (javax.naming.NamingException namingException) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_CLIENT_EJB,
          "AppraisalAssignmentClient", "getAppraisalAssignmentEJB",
          "Naming exception getting AppraisalAssignment EJB.", namingException);
    } catch (Throwable throwable) {
      throw new MitchellException(
          AppraisalAssignmentConstants.ERROR_CLIENT_EJB,
          "AppraisalAssignmentClient", "getAppraisalAssignmentEJB",
          "Remote exception getting AppraisalAssignment EJB.", throwable);
    }

    return appraisalAssignmentService;
  }

  /**
   * Gets AppraisalAssignment EJB's local object for Client.
   * 
   * @return AppraisalAssignmentService - Stub for AppraisalAssignment Local
   *         EJB
   * 
   * @throws MitchellException
   *           Throws MitchellException to the caller in case of any
   *           exception arise.
   * 
   */
  public static AppraisalAssignmentServiceLocal getAppraisalAssignmentLocalEJB()
      throws MitchellException
  {
    return (AppraisalAssignmentServiceLocal) getEJB(
        APPRAISAL_ASSIGNMENT_LOCAL_EJB_JNDI,
        APPRAISAL_ASSIGNMENT_LOCAL_PROVIDER_URL,
        APPRAISAL_ASSIGNMENT_LOCAL_JNDI_FACTORY);
  }

  /**
   * @param workAssignmentTaskID -
   *          WorkAssignment Task ID of the WorkAssignment . This was
   *          returned when the assignment was saved.
   * @param newDispositionCode -
   *          WorkAssignment Disposition .
   * @param reasonCode -
   *          Reason code for assignment update
   * @param comment -
   *          If user provided note for status update
   * @param requestTCN -
   *          TCN value of the WorkAssignment database table
   * @param loggedInUserInfoDocument -
   *          UserInfoDocument of updater.
   * @return Zero for SUCCESS, One for FAILURE, TWO for STALE DATA
   * @throws MitchellException -
   *           Throws MitchellException to the caller in case of any
   *           business exception arise.
   * @throws java.rmi.RemoteException -
   *           Throws MitchellException to the caller in case of any remote
   *           exception arise.
   */
  public static int assignmentStatusUpdate(long workAssignmentTaskID,
      String newDispositionCode, String reasonCode, String comment,
      long requestTCN, UserInfoDocument loggedInUserInfoDocument)
      throws MitchellException, java.rmi.RemoteException
  {
    int result = AppraisalAssignmentConstants.FAILURE;
    AppraisalAssignmentServiceRemote appraisalAssignmentServiceRemote = getAppraisalAssignmentEJB();
    result = appraisalAssignmentServiceRemote.assignmentStatusUpdate(
        workAssignmentTaskID, newDispositionCode, reasonCode, comment,
        requestTCN, loggedInUserInfoDocument);

    return result;
  }
}