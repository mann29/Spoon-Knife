package com.mitchell.services.business.partialloss.assignmentdelivery.handler.apd.impl;

import java.util.ResourceBundle;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class AnnotatedLogger  {
	private Logger delegate;
	private String annotation;
	private AnnotatedLogger(Logger delegate) {
		this.delegate = delegate;
	}
	public static AnnotatedLogger annotate (Logger delegate){
		return new AnnotatedLogger(delegate);
	}
	public AnnotatedLogger  with(String annotation){
		this.annotation = annotation;
		return this;
	}
	public void addHandler(Handler handler) throws SecurityException {
		delegate.addHandler(handler);
	}
	public void config(String msg) {
		delegate.config(msg);
	}
	public void entering(String sourceClass, String sourceMethod) {
		delegate.entering(sourceClass, sourceMethod);
	}
	public void entering(String sourceClass, String sourceMethod, Object param1) {
		delegate.entering(sourceClass, sourceMethod, param1);
	}
	public void entering(String sourceClass, String sourceMethod,
			Object[] params) {
		delegate.entering(sourceClass, sourceMethod, params);
	}
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}
	public void exiting(String sourceClass, String sourceMethod) {
		delegate.exiting(sourceClass, sourceMethod);
	}
	public void exiting(String sourceClass, String sourceMethod, Object result) {
		delegate.exiting(sourceClass, sourceMethod, result);
	}
	public void fine(String msg) {
		delegate.fine(msg + "[" +annotation+"]" );
	}
	public void finer(String msg) {
		delegate.finer(msg+ "[" +annotation+"]");
	}
	public void finest(String msg) {
		delegate.finest(msg+ "[" +annotation+"]");
	}
	public Filter getFilter() {
		return delegate.getFilter();
	}
	public Handler[] getHandlers() {
		return delegate.getHandlers();
	}
	public Level getLevel() {
		return delegate.getLevel();
	}
	public String getName() {
		return delegate.getName();
	}
	public Logger getParent() {
		return delegate.getParent();
	}
	public ResourceBundle getResourceBundle() {
		return delegate.getResourceBundle();
	}
	public String getResourceBundleName() {
		return delegate.getResourceBundleName();
	}
	public boolean getUseParentHandlers() {
		return delegate.getUseParentHandlers();
	}
	public int hashCode() {
		return delegate.hashCode();
	}
	public void info(String msg) {
		delegate.info(msg+ "[" +annotation+"]");
	}
	public boolean isLoggable(Level level) {
		return delegate.isLoggable(level);
	}
	public void log(LogRecord record) {
		delegate.log(record);
	}
	public void log(Level level, String msg) {
		delegate.log(level, msg);
	}
	public void log(Level level, String msg, Object param1) {
		delegate.log(level, msg, param1);
	}
	public void log(Level level, String msg, Object[] params) {
		delegate.log(level, msg, params);
	}
	public void log(Level level, String msg, Throwable thrown) {
		delegate.log(level, msg, thrown);
	}
	public void logp(Level level, String sourceClass, String sourceMethod,
			String msg) {
		delegate.logp(level, sourceClass, sourceMethod, msg);
	}
	public void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Object param1) {
		delegate.logp(level, sourceClass, sourceMethod, msg, param1);
	}
	public void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Object[] params) {
		delegate.logp(level, sourceClass, sourceMethod, msg, params);
	}
	public void logp(Level level, String sourceClass, String sourceMethod,
			String msg, Throwable thrown) {
		delegate.logp(level, sourceClass, sourceMethod, msg, thrown);
	}
	public void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg) {
		delegate.logrb(level, sourceClass, sourceMethod, bundleName, msg);
	}
	public void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Object param1) {
		delegate.logrb(level, sourceClass, sourceMethod, bundleName, msg,
				param1);
	}
	public void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Object[] params) {
		delegate.logrb(level, sourceClass, sourceMethod, bundleName, msg,
				params);
	}
	public void logrb(Level level, String sourceClass, String sourceMethod,
			String bundleName, String msg, Throwable thrown) {
		delegate.logrb(level, sourceClass, sourceMethod, bundleName, msg,
				thrown);
	}
	public void removeHandler(Handler handler) throws SecurityException {
		delegate.removeHandler(handler);
	}
	public void setFilter(Filter newFilter) throws SecurityException {
		delegate.setFilter(newFilter);
	}
	public void setLevel(Level newLevel) throws SecurityException {
		delegate.setLevel(newLevel);
	}
	public void setParent(Logger parent) {
		delegate.setParent(parent);
	}
	public void setUseParentHandlers(boolean useParentHandlers) {
		delegate.setUseParentHandlers(useParentHandlers);
	}
	public void severe(String msg) {
		delegate.severe(msg+ "[" +annotation+"]");
	}
	public void throwing(String sourceClass, String sourceMethod,
			Throwable thrown) {
		delegate.throwing(sourceClass, sourceMethod, thrown);
	}
	public String toString() {
		return delegate.toString();
	}
	public void warning(String msg) {
		delegate.warning(msg+ "[" +annotation+"]");
	}
	
}
