package com.mitchell.services.business.partialloss.assignmentdelivery;
/**
 * Static class to help extract class name.
 * Keeps the class name from being hardcoded and hance in sync with the actual code even 
 * after refactorings. 
 * @author <a href="mailto://prashant.khanwale@mitchell.com"> Prashant sadashiv Khanwale </a>
 * Created/Modified on Jul 20, 2010
 * Note : Design decision to make this static as there are 
 * no external dependencies.
 */
public class ExtractClassName {
	static private String from (String in){
		if ( in == null ) return "";
		final int pos = in.lastIndexOf('.');
		if ( pos != -1 ){
			return in.substring(pos+1);
		}else{
			return in;
		}
	}

	public static String from (Class clazz){
		if ( clazz == null ) return "";
		final String in  = clazz.getName();
		final int pos = in.lastIndexOf('.');
		if ( pos != -1 ){
			return in.substring(pos+1);
		}else{
			return in;
		}
	}
}
