package com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.schemas.EnvelopeBodyType;
import com.mitchell.services.business.partialloss.appraisalassignment.parallel.AASParallelConstants;
import com.mitchell.utils.xml.MIEnvelopeHelper;

/**
 * Parallel processing Base Handler for commonly used methods.
 */
public abstract class AASBaseHandler
{

  protected static Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.appraisalassignment.parallel.handler.AASBaseHandler");

  /**
   * Extract the Logged In UserInfo document from the MitchellEnvelope Parallel
   * processing request.
   * 
   * @param meHelper
   * @return Returns the extracted UserInfoDocument.
   * 
   * @throws MitchellException
   * @throws XmlException
   */
  public UserInfoDocument extractLoggedInUserInfo(MIEnvelopeHelper meHelper)
      throws MitchellException, XmlException
  {
    // Get the UserInfo
    EnvelopeBodyType envelopeBodyUserInfo = meHelper
        .getEnvelopeBody(AASParallelConstants.ME_LOGGED_IN_USERINFO_ID);
    String userInfoStr = meHelper
        .getEnvelopeBodyContentAsString(envelopeBodyUserInfo);
    UserInfoDocument loggedInUserInfoDocument = UserInfoDocument.Factory
        .parse(userInfoStr);

    return loggedInUserInfoDocument;
  }

}
