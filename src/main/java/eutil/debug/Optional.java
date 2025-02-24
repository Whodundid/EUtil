package eutil.debug;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * An annotation used to denote a parameter or record component
 * is optional.
 * 
 * @author Hunter Bragg
 * @since 3.0.0
 */
@Retention(SOURCE)
@Target({ PARAMETER, LOCAL_VARIABLE, TYPE_PARAMETER, RECORD_COMPONENT })
public @interface Optional {
    
    /**
     * Describes why this parameter is optional.
     */
    String reason() default "";
    
}
