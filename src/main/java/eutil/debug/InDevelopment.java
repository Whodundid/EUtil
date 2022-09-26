package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a feature is actively being
 * developed and should not be considered final in any regard.
 * 
 * @author Hunter Bragg
 * @since 1.3
 */
@Target({TYPE, FIELD, METHOD, LOCAL_VARIABLE, PACKAGE})
@Retention(RetentionPolicy.CLASS)
public @interface InDevelopment {
	
	/**
	 * Used to specify the date of which this code segment was last worked on.
	 * 
	 * @return The date last worked on
	 * 
	 * @since 1.8.0
	 */
	String since() default "";
	
}
