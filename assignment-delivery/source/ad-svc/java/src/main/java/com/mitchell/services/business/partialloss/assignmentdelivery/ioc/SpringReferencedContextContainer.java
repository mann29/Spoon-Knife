package com.mitchell.services.business.partialloss.assignmentdelivery.ioc;

/**
 * Interface for a Spring Context container used to access via
 * reference counting the spring context object.
 */
public interface SpringReferencedContextContainer
{
  public void addContextReference();

  public void removeContextReference();

}
