package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of
 * code is currently causing issues and should be fixed at
 * some future point.
 * 
 * @author Hunter Bragg
 * @since 1.3
 */
@Target({TYPE, FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, MODULE})
@Retention(RetentionPolicy.SOURCE)
public @interface Broken {

	/**
	 * Used to specify the date of which a segment of broken
	 * code was discovered.
	 * 
	 * @return The discovered date
	 * @since 1.3.1
	 */
	String value() default "";
	
}
