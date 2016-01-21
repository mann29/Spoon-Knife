package com.mitchell.services.business.partialloss.appraisalassignment.util;

/**
 * Interface for a Spring Context container used to access via
 * reference counting the spring context object.
 */
public interface SpringReferencedContextContainer
{
  public void addContextReference();

  public void removeContextReference();

}