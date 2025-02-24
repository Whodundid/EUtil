package eutil.lambda;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

/**
 * A collection of various reduction logic structures.
 * 
 * @author Hunter Bragg
 * @since 1.1.1
 */
public final class Predicates {
    
    //----------------------
    
    private Predicates() {}
    
    //----------------------
    
    /** A general condition that reduces to only null values. */
    public static final Predicate<Object> IS_NULL = (o) -> o == null;
    
    /** A general condition that reduces to only non-null values. */
    public static final Predicate<Object> NOT_NULL = (o) -> o != null;
    
    /** A general condition that checks if a list has exactly one entry. */
    public static final Predicate<List> HAS_ONE = (s) -> s.size() == 1;
    
    /** A general condition that checks if a list is empty. */
    public static final Predicate<List> IS_EMPTY = (s) -> s.isEmpty();
    
    /** A general condition that checks if a list is not empty. */
    public static final Predicate<List> IS_NOT_EMPTY = (s) -> !s.isEmpty();
    
    /** Checks that a given File is both not null and actually exists. */
    public static final Predicate<File> FILE_EXISTS = (f) -> f != null && f.exists();
    
    /** Checks that a given Path is both not null and actually exists. */
    public static final Predicate<Path> PATH_EXISTS = (p) -> Files.exists(p);
    
    /**
     * Checks that a given File is either null or does not exist.
     * @since 3.0.0
     */
    public static final Predicate<File> FILE_NOT_EXISTS = (f) -> f == null || !f.exists();
    
    /**
     * Checks that a given Path is either null or does not exist.
     * @since 3.0.0
     */
    public static final Predicate<File> PATH_NOT_EXISTS = (f) -> f == null || !f.exists();
    
}
