package com.mitchell.services.business.partialloss.appraisalassignment.pmdb;

import javax.jms.JMSException;
import javax.jms.Message;

public class BaseParallelMDB
{

  protected boolean getIsRedelivered(Message msg)
  {
    boolean isRedelivered = false;
    try {
      isRedelivered = msg.getJMSRedelivered();
    } catch (JMSException je) {
      isRedelivered = false;
    }
    return isRedelivered;
  }

}
