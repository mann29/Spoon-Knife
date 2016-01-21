package com.mitchell.services.business.partialloss.appraisalassignment.process;

import javax.ejb.Local;

@Local
public interface ErrorLoggingProcess
{
  public void handleMDBException(boolean isRedelivered, String className,
      String methodName, Throwable e, String desc, int errorNo, String details);

}
