package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of code is
 * in need of better documentation as the current documentation
 * could either be lacking, out-dated, unrelated, or even non-existent.
 * 
 * @author Hunter Bragg
 * @since 2.2.0
 */
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE,
	     PACKAGE, TYPE_PARAMETER, TYPE_USE, MODULE, RECORD_COMPONENT})
@Retention(RetentionPolicy.SOURCE)
public @interface PoorlyDocumented {
	
	/**
	 * Describes the reason for why the code is actually poorly documented.
	 */
	String value() default "";
	
}
