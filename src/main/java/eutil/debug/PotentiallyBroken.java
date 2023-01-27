package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of
 * code is potentially not working as it was intended to and
 * will require further investigation to prove this functionality.
 * 
 * @author Hunter Bragg
 * @since 2.2.0
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,
	     ANNOTATION_TYPE, PACKAGE, TYPE_PARAMETER, TYPE_USE, MODULE, RECORD_COMPONENT})
@Retention(RetentionPolicy.CLASS)
public @interface PotentiallyBroken {

	/**
	 * Used to specify the date of which a segment of potentially broken
	 * code was discovered.
	 * 
	 * @return The discovered date
	 */
	String since() default "";
	
	/**
	 * The underlying reason for why the specified code segment is potentially broken.
	 * 
	 * @return The reason for being potentially broken
	 */
	String value() default "";
	
}
