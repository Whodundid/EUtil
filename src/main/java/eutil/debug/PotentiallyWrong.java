package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of code is
 * potentially not implemented in a way that it was intended for or in a
 * way that does not make logical sense given the code's surrounding usage
 * or evolution of such usage.
 * <p>
 * PotentiallyWrong statements do not necessarily indicate broken code.
 * Infact, PotentiallyWrong statements may execute without issue and may
 * very well even produce working results given its use case. However,
 * PotentiallyWrong should be used to indicate that a marked segment of
 * code has the possibility of producing invalid results based on specific
 * inputs or if called in an unintended way. Another possibility for
 * PotentiallyWrong code segments to exist occurs when the actual
 * utilization of the code segment in question may have shifted from its
 * original design concept.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.5.4
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,
	     ANNOTATION_TYPE, PACKAGE, TYPE_PARAMETER, TYPE_USE, MODULE, RECORD_COMPONENT})
@Retention(RetentionPolicy.CLASS)
public @interface PotentiallyWrong {

	/**
	 * Used to specify the date of which a segment of potentially wrong
	 * code was discovered.
	 * 
	 * @return The discovered date
	 */
	String since() default "";
	
	/**
	 * The underlying reason for why the specified code segment is potentially wrong.
	 * 
	 * @return The reason for being potentially wrong
	 */
	String value() default "";
	
}
