package com.mitchell.services.business.partialloss.appraisalassignment.util;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.MitchellEnvelopeDocument;
import com.mitchell.services.core.applicationlogging.AppLoggingToEventPublishingContextDocument.AppLoggingToEventPublishingContext.EventDetails;
import com.mitchell.services.core.applog.client.AppLoggingNVPairs;
import com.mitchell.services.core.workassignment.bo.WorkAssignment;

public interface AASAppLogUtil
{

  public void appLog(int eventId, WorkAssignment workAssignment,
      UserInfoDocument userInfoDocument,
      MitchellEnvelopeDocument mitchellEnvelopeDocument, long startTime,
      AppLoggingNVPairs appLoggingNVPairs)
      throws MitchellException;

  public void appLog(int eventId, WorkAssignment workAssignment,
      UserInfoDocument userInfoDocument,
      MitchellEnvelopeDocument mitchellEnvelopeDocument, long startTime)
      throws MitchellException;

  public void appLog(int eventId, WorkAssignment workAssignment,
      UserInfoDocument userInfoDocument,
      MitchellEnvelopeDocument mitchellEnvelopeDocument)
      throws MitchellException;

  public void appLog(int eventId, WorkAssignment workAssignment,
      UserInfoDocument userInfoDocument)
      throws MitchellException;

  public void transformMEToAppLogNVPair(
      MitchellEnvelopeDocument mitchellEnvelopeDocument,
      EventDetails eventDetailsIn)
      throws MitchellException;

  public void addNVPair(String key, String value,
      AppLoggingNVPairs appLoggingNVPairs);

  public void addNVPairToEventDetails(String key, String value,
      EventDetails eventDetails);

  public void appLogUpdate(int eventId, long claimSuffixId,
      String vehTrackingStatus, UserInfoDocument userInfoDocument,
      long startTime)
      throws MitchellException;

}
