package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of
 * code is currently unused.
 * 
 * @author Hunter Bragg
 * @since 1.3.2
 */
@Target({TYPE, FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, MODULE})
@Retention(RetentionPolicy.SOURCE)
public @interface Unused {
	
	/**
	 * Describes the reason for why the code segment is not being used.
	 * 
	 * @since 1.8.0
	 */
	String reason() default "";
	
}
