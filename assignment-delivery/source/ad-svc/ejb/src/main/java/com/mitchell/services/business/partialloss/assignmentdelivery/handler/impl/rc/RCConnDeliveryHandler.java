package com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.rc;

import java.util.logging.Logger;

import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentDeliveryException;
import com.mitchell.services.business.partialloss.assignmentdelivery.AssignmentServiceContext;
import com.mitchell.services.business.partialloss.assignmentdelivery.handler.AssignmentDeliveryHandler;

/**
 * RCConnect Assignment Delivery Handler. Initial version of this handler does
 * nothing because RCConnect is email only that is driven by logic that resides
 * external to this handler. This handler was originally put in place because
 * the code requires that some handler be defined. The other available handlers
 * produce unwanted/unneeded artifacts that just collect up in the RCConnect use
 * case. This handler, doing nothing, provides the means to not create the
 * unwanted artifacts in the case of RCConnect.
 * 
 */
public class RCConnDeliveryHandler implements AssignmentDeliveryHandler
{
  private static final Logger logger = Logger
      .getLogger("com.mitchell.services.business.partialloss.assignmentdelivery.handler.impl.rc.RCConnDeliveryHandler");

  public void deliverAssignment(AssignmentServiceContext context)
      throws AssignmentDeliveryException
  {
    if (logger.isLoggable(java.util.logging.Level.INFO)) {
      logger.info("IN RCConnDeliveryHandler deliverAssignment.");
    }
  }

  public void cancelAssignment(AssignmentServiceContext context)
      throws AssignmentDeliveryException
  {
    if (logger.isLoggable(java.util.logging.Level.INFO)) {
      logger.info("IN RCConnDeliveryHandler cancelAssignment.");
    }
  }

}
