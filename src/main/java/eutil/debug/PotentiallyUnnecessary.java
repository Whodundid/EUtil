package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of
 * code may be entirely unnecessary.
 * 
 * @author Hunter Bragg
 * @since 2.2.0
 */
@Target({TYPE, METHOD, PACKAGE, FIELD, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, MODULE})
@Retention(RetentionPolicy.SOURCE)
public @interface PotentiallyUnnecessary {
	
	/**
	 * Describes the reason for why the code segment may not actually be needed.
	 */
	String reason() default "";
	
}
