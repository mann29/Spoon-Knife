package com.mitchell.services.business.partialloss.appraisalassignment.pejb;

import java.util.List;

import javax.ejb.Local;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.xmlbeans.XmlException;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.schemas.MitchellEnvelopeDocument;

@Local
public interface AASParallelEJB
{
  public String submitRequests(List<String> meStrings)
      throws MitchellException;

  public List<String> retrieveResponses(String responseGroupId,
      long maxNumberToReceive)
      throws MitchellException;

  public MitchellEnvelopeDocument extractMitchellEnvelopeFromMessage(
      TextMessage msg)
      throws XmlException, JMSException;

  public void submitResponse(TextMessage tMsg, String msg)
      throws MitchellException;

}
