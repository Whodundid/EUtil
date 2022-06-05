package eutil.util;

import static java.lang.annotation.ElementType.*;

import eutil.EUtil;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a feature is currently being tested
 * and may not produce accurate or reliable results.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
@Target({ TYPE, METHOD, PACKAGE, FIELD })
public @interface Experimental {
	
	/** The object's experimental version.
	 *  By default it is set to the current EUtil library version. */
	public String since() default EUtil.version;
	
}
