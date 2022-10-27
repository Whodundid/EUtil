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
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE,
	     ANNOTATION_TYPE, PACKAGE, TYPE_PARAMETER, TYPE_USE, MODULE, RECORD_COMPONENT})
@Retention(RetentionPolicy.CLASS)
public @interface Broken {

	/**
	 * Used to specify the date of which a segment of broken
	 * code was discovered.
	 * 
	 * @apiNote 1.8.0 - Renamed 'value' -> 'since'
	 * 
	 * @return The discovered date
	 * @since 1.3.1
	 */
	String since() default "";
	
	/**
	 * The underlying reason for why the specified code segment is broken.
	 * 
	 * @return The reason for being broken
	 * @since 1.8.1
	 */
	String value() default "";
	
}
