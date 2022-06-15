package eutil.debug;

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
@Target({ TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, PACKAGE, TYPE_PARAMETER, TYPE_USE })
public @interface Experimental {
	
	/**
	 * The object's experimental version. By default it is set to the current EUtil
	 * library version.
	 */
	public String since() default EUtil.version;
	
}
