package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

/**
 * Interface for a Spring Context container used to access via
 * reference counting the spring context object.
 */
public interface SpringReferencedContextContainer
{
  public void addContextReference();

  public void removeContextReference();

}