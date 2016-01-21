package com.mitchell.services.business.partialloss.appraisalassignment.parallel;

/**
 * Constants used by the Parallel Processing infrastructure.
 */
public class AASParallelConstants
{
//@formatter:off 

  public static final String ME_WORK_ITEM_ID = "WORK_ITEM_ID";
  public static final String ME_COMPANY_CODE = "COMPANY_CODE";
  public static final String ME_PROCESSING_TYPE = "PROCESSING_TYPE";

  public static final String ME_LOGGED_IN_USERINFO_ID = "LOGGED_IN_USERINFO";
  public static final String ME_LOGGED_IN_USERINFO_TYPE = "UserInfoDocument";

  public static final String ME_ASSIGN_TASK_TYPE_ID = "ASSIGN_TASK_TYPE";
  public static final String ME_ASSIGN_TASK_TYPE_TYPE = "AssignTaskType";
  public static final String ME_ASSIGN_TASK_TYPE_RESULT = "ASSIGN_TASK_TYPE_RESULT";

  public static final String SYSCONF_PBASE = "/AppraisalAssignment/ParallelProcessing/";
  public static final String SYSCONF_QUEUE_CONN_FACTORY_NAME = SYSCONF_PBASE + "QueueConnectionFactoryName";
  public static final String SYSCONF_INPUT_QUEUE_NAME = SYSCONF_PBASE + "InputQueueName";
  public static final String SYSCONF_RESPONSE_QUEUE_NAME = SYSCONF_PBASE + "ResponseQueueName";
  public static final String SYSCONF_MESSAGE_EXPIRATION_SECS = SYSCONF_PBASE + "MessageExpirationSecs";
  public static final String SYSCONF_MAX_RESPONSE_WAITTIME = SYSCONF_PBASE + "MaxResponseWaitTimeMillis";
  public static final String SYSCONF_PARALLEL_MIN_LIST_SIZE = SYSCONF_PBASE + "MinParallelListSize";


//@formatter:on 
}
