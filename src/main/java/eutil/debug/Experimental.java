package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a feature is currently being tested
 * and may not produce accurate or reliable results.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
@Target({TYPE, METHOD, PACKAGE, FIELD, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, MODULE})
@Retention(RetentionPolicy.CLASS)
public @interface Experimental {

	/**
	 * The date that the specified experimental code segment was first
	 * introduced.
	 * 
	 * @apiNote 1.8.0 - No longer refers to the current EUtil library version
	 *          by default
	 */
	public String since() default "";
	
}
