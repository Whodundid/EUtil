package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;

/**
 * An annotation used to denote that a feature is actively being
 * developed and should not be considered final in any regard.
 * 
 * @author Hunter Bragg
 * @since 1.3
 */
@Target({ TYPE, FIELD, METHOD, LOCAL_VARIABLE, PACKAGE })
public @interface InProgress {}
