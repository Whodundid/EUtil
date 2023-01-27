package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of code will
 * eventually be changed in some way that will inevitably break existing
 * functionality.
 * 
 * @author Hunter Bragg
 * 
 * @since 2.2.0
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,
	     ANNOTATION_TYPE, PACKAGE, TYPE_PARAMETER, TYPE_USE, MODULE, RECORD_COMPONENT})
@Retention(RetentionPolicy.CLASS)
public @interface PlannedForRefactor {

	/**
	 * Used to specify the date of which this code segment was 
	 * 
	 * @return The discovered date
	 */
	String since() default "";
	
	/**
	 * The underlying reason for why the specified code segment will be changed.
	 * 
	 * @return The reason that this code segment will eventually be refactored
	 */
	String reason() default "";
	
}
