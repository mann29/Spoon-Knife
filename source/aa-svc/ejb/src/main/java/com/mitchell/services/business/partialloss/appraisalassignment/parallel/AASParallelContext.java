package com.mitchell.services.business.partialloss.appraisalassignment.parallel;

import java.io.Serializable;

/**
 * Context object that is used by the parallel processing infrastructure.
 * 
 */
public class AASParallelContext implements Serializable
{
  private static final long serialVersionUID = 1L;

  private String workItemId = null;
  private String companyCode = null;
  private long startTime = System.currentTimeMillis();

  public AASParallelContext()
  {
  }

  public String getWorkItemId()
  {
    return workItemId;
  }

  public void setWorkItemId(String workItemId)
  {
    this.workItemId = workItemId;
  }

  public String getCompanyCode()
  {
    return companyCode;
  }

  public void setCompanyCode(String companyCode)
  {
    this.companyCode = companyCode;
  }

  public long getStartTime()
  {
    return startTime;
  }

  public void setStartTime(long startTime)
  {
    this.startTime = startTime;
  }

}
