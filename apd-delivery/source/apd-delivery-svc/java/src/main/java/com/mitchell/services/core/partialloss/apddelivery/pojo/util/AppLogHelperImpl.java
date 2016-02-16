package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.apddelivery.APDBroadcastMessageType;
import com.mitchell.services.core.applicationlogging.AppLoggingDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingType;
import com.mitchell.services.core.applog.client.AppLogging;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.APDCommonUtilProxy;
import com.mitchell.services.core.partialloss.apddelivery.pojo.proxy.AppLogProxy;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryConstants;
import com.mitchell.services.core.partialloss.apddelivery.utils.APDDeliveryContext;

/**
 * The Class AppLogHelperImpl.
 */
public final class AppLogHelperImpl implements AppLogHelper
{

  /**
   * class name.
   */
  private static final String CLASS_NAME = AppLogHelperImpl.class.getName();
  /**
   * logger instance.
   */
  private static Logger logger = Logger.getLogger(CLASS_NAME);

  /**
   * The app log proxy.
   */
  private AppLogProxy appLogProxy;

  private APDCommonUtilProxy apdCommonUtilProxy;

  /**
   * @param bMsg
   *          APDBroadcastMessageType object
   * @param recipientUserInfo UserInfoType object
   * @param messageId String object
   * @param message String object
   * @throws MitchellException
   *           Mitchell Exception
   */
  public void appLogBroadcastMsg(APDBroadcastMessageType bMsg,
      UserInfoType recipientUserInfo, String messageId, String message)
      throws MitchellException
  {
    String methodName = "appLogBroadcastMsg";
    logger.entering(CLASS_NAME, methodName);
    AppLoggingDocument logDoc = createAppLoggingDocumentForBroadcastMessage(
        messageId, bMsg);

    AppLoggingNVPairs appLoggingNVPairs = createNVPairForBroadcastMessage(bMsg,
        message, recipientUserInfo);

    UserInfoDocument senderUserInfoDoc = UserInfoDocument.Factory.newInstance();
    senderUserInfoDoc.addNewUserInfo();

    senderUserInfoDoc.setUserInfo(bMsg.getAPDCommonInfo().getSourceUserInfo()
        .getUserInfo());
    appLogProxy.logAppEvent(logDoc, senderUserInfoDoc, bMsg.getAPDCommonInfo()
        .getWorkItemId(), APDDeliveryConstants.APP_NAME,
        APDDeliveryConstants.BUSINESS_SERVICE_NAME, appLoggingNVPairs);
    logger.exiting(CLASS_NAME, methodName);

  }

  /**
   * @param transactionType
   *          App Code
   * @param bMsg
   *          APDBroadcastMessageType object
   */
  private AppLoggingDocument createAppLoggingDocumentForBroadcastMessage(
      final String transactionType, final APDBroadcastMessageType bMsg)
  {

    AppLoggingDocument logDoc = AppLoggingDocument.Factory.newInstance();

    AppLoggingType appType = logDoc.addNewAppLogging();

    appType.setMitchellUserId(bMsg.getAPDCommonInfo().getSourceUserInfo()
        .getUserInfo().getUserID());
    appType.setModuleName(APDDeliveryConstants.MODULE_NAME);
    appType.setStatus(AppLogging.APPLOG_ST_OK);
    appType.setTransactionType(transactionType);
    appType.setWorkItemType("UNKNOWN");

    return logDoc;
  }

  /**
   * @param bMsg
   *          APDBroadcastMessageType object
   * @param message
   *          App log message
   */
  private AppLoggingNVPairs createNVPairForBroadcastMessage(
      APDBroadcastMessageType bMsg, String message,
      UserInfoType recipientUserInfo)
  {

    AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();

    appLoggingNVPairs.addPair("MessageType",
        APDDeliveryConstants.BROADCAST_MESSAGE_TYPE);
    appLoggingNVPairs.addPair("Message", message);
    appLoggingNVPairs.addPair("DateTime", bMsg.getAPDCommonInfo().getDateTime()
        .toString());
    appLoggingNVPairs.addPair("SenderUserInfo", bMsg.getAPDCommonInfo()
        .getSourceUserInfo().getUserInfo().toString());

    appLoggingNVPairs
        .addPair("RecipientUserInfo", recipientUserInfo.toString());

    return appLoggingNVPairs;
  }

  /**
   * This method calls AppLogging service to do Applog.
   * 
   * @param apdContext
   * @param userInfoDoc
   * @throws MitchellException
   */
  public void doAppLog(APDDeliveryContext apdContext,
      UserInfoDocument userInfoDoc)
      throws MitchellException
  {

    String methodName = "doAppLog";
    logger.entering(CLASS_NAME, methodName);
    if (userInfoDoc != null) {
      AppLoggingNVPairs appLoggingNVPairs = new AppLoggingNVPairs();

      Map contextMap = apdContext.getMap();
      if (contextMap != null) {
        Iterator iterator = contextMap.keySet().iterator();

        String key = null;
        String value = null;

        while (iterator.hasNext()) {
          key = iterator.next().toString();
          value = contextMap.get(key).toString();
          appLoggingNVPairs.addPair(key, value);
        }
      }
      AppLoggingDocument logDoc = AppLoggingDocument.Factory.newInstance();

      AppLoggingType appType = logDoc.addNewAppLogging();

      if (apdContext.getClaimNumber() != null) {
        appType.setClaimNumber(apdContext.getClaimNumber());
      }

      appType.setClaimId(new Long(String.valueOf(apdContext.getClaimId()))
          .longValue());
      appType.setClaimExposureId(new Long(String.valueOf(apdContext
          .getSuffixId())).longValue());
      appType.setMitchellUserId(apdContext.getUserId());
      appType.setModuleName(APDDeliveryConstants.MODULE_NAME);
      appType.setStatus(AppLogging.APPLOG_ST_OK);
      appType.setTransactionType(apdContext.getTransactiontype());
      appType.setWorkItemType("UNKNOWN");

      apdCommonUtilProxy
          .logINFOMessage("**** Calling AppLogging to do applog ****");
      appLogProxy.logAppEvent(logDoc, userInfoDoc, apdContext.getWorkItemId(),
          APDDeliveryConstants.APP_NAME, appLoggingNVPairs);
    }
    logger.exiting(CLASS_NAME, methodName);
  }

  /**
   * Gets the AppLogProxy.
   * 
   * @return AppLogProxy
   */
  public AppLogProxy getAppLogProxy()
  {
    return appLogProxy;
  }

  /**
   * Sets the AppLogProxy.
   * 
   * @param appLogProxy
   */
  public void setAppLogProxy(AppLogProxy appLogProxy)
  {
    this.appLogProxy = appLogProxy;
  }

  /**
   * @return the apdCommonUtilProxy
   */
  public APDCommonUtilProxy getApdCommonUtilProxy()
  {
    return apdCommonUtilProxy;
  }

  /**
   * @param apdCommonUtilProxy the apdCommonUtilProxy to set
   */
  public void setApdCommonUtilProxy(APDCommonUtilProxy apdCommonUtilProxy)
  {
    this.apdCommonUtilProxy = apdCommonUtilProxy;
  }

}
