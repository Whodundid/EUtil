package eutil.debug;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;

/**
 * An annotation used to denote that a specific segment of
 * code is currently causing issues and should be fixed at
 * some future point.
 * 
 * @author Hunter Bragg
 * @since 1.3
 */
@Target({ TYPE, FIELD, METHOD, LOCAL_VARIABLE, PACKAGE })
public @interface CurrentlyBugged {}
