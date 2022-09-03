package eutil.debug;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

import eutil.EUtil;

/**
 * An annotation used to denote that a feature is currently being tested
 * and may not produce accurate or reliable results.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
@Target({TYPE, METHOD, PACKAGE, FIELD})
public @interface Experimental {

	/**
	 * The object's experimental version. By default it is set to the current EUtil
	 * library version.
	 */
	public String since() default EUtil.version;
	
}
