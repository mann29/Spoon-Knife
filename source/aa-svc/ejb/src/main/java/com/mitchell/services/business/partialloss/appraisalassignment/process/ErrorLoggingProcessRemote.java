package com.mitchell.services.business.partialloss.appraisalassignment.process;

import javax.ejb.Remote;

@Remote
public interface ErrorLoggingProcessRemote
{
  public void handleMDBException(boolean isRedelivered, String className,
      String methodName, Throwable e, String desc, int errorNo, String details);

}
