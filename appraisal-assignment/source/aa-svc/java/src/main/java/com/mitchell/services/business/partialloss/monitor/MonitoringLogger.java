package com.mitchell.services.business.partialloss.monitor;

import java.io.Serializable;
import java.util.logging.Logger;

public class MonitoringLogger implements Serializable
{
  // Don't mess with this name and logger - in dev see appasgmonitor.log
  private static final String ASSIGNMENT_MONITOR_LOGGER_NAME = "com.mitchell.services.appasgmonitor.MonitoringLogger";
  private final static Logger timerLogger = Logger
      .getLogger(ASSIGNMENT_MONITOR_LOGGER_NAME);

  private final static Logger logger = Logger.getLogger(MonitoringLogger.class
      .getName());
  private final static String CLASS_NAME = MonitoringLogger.class.getName();

  public static boolean isLoggable()
  {
    return logger.isLoggable(java.util.logging.Level.FINE);
  }

  public static void doLog(String className, String methodName, String msgLog)
  {
    if (logger.isLoggable(java.util.logging.Level.FINE)) {
      StringBuffer str = new StringBuffer().append(className).append(":")
          .append(methodName).append(":").append(msgLog);
      logger.fine(str.toString());
    }
  }

  public static boolean isLoggableTimer()
  {
    return timerLogger.isLoggable(java.util.logging.Level.INFO);
  }

  public static void doLog(String className, String methodName, String msgLog,
      long totalProcTimeMillis)
  {
    if (timerLogger.isLoggable(java.util.logging.Level.INFO)) {
      StringBuffer str = new StringBuffer()
          .append(String.valueOf(((double) totalProcTimeMillis) / 1000.0))
          .append(" ").append(className).append(" ").append(methodName)
          .append(" ").append(msgLog);
      timerLogger.info(str.toString());
    }
  }

}
