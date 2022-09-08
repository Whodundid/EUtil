package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of code is very
 * inefficient in its current implementation and should probably be
 * improved at some point in the future.
 * 
 * @author Hunter Bragg
 * @since 1.6.2
 */
@Target({TYPE, FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE, MODULE})
@Retention(RetentionPolicy.SOURCE)
public @interface Inefficient {
	
	/**
	 * Describes the reason for why the code is actually inefficient. This
	 * reason may be referring to a specific line of written code or a
	 * particular algorithm that was used.
	 */
	String reason() default "";
	
	/**
	 * A description of other systems or sections of code that are somehow
	 * affected by this specific code being inefficient.
	 */
	String affects() default"";
	
}
