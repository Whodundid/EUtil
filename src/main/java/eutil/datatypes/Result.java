package eutil.datatypes;

import eutil.EUtil;

/**
 * A returned result bundled with an optional reason String.
 * 
 * @param <T> The datatype of this result
 * 
 * @param value The value of this result
 * @param reason The optional reason that the result was given
 * 
 * @author Hunter Bragg
 * @since 2.7
 */
public record Result<T>(T value, String reason) {
    
    //==============
    // Constructors
    //==============
    
    /**
     * Creates a new Result with the given value without a provided reason.
     * 
     * @param valueIn The value to return
     */
    public Result(T value) {
        this(value, null);
    }
    
    /**
     * Creates a new Result with the given value and provided reason.
     * 
     * @param valueIn The value to return
     * @param reasonIn The reason the value was returned
     */
    public Result(T value, String reason) {
        this.value = value;
        this.reason = reason;
    }
    
    //=========
    // Methods
    //=========
    
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Result<?>)) return false;
        
        Result<?> r = (Result<?>) o;
        return EUtil.isEqual(value, r.value) && EUtil.isEqual(reason, r.reason);
    }
    
    //================
    // Static Methods
    //================
    
    /** Produces a successful boolean (true) result with no reason. */
    public static Result<Boolean> success() { return new Result(true); }
    /** Produces a successful boolean (true) result with the given reason. */
    public static Result<Boolean> success(String reason) { return new Result(true, reason); }
    
    /** Produces an unsuccessful boolean (false) result with no reason. */
    public static Result<Boolean> fail() { return new Result(false); }
    /** Produces an unsuccessful boolean (false) result with the given reason. */
    public static Result<Boolean> fail(String reason) { return new Result(false, reason); }
    
    /** Produces a result with the given value with no reason. */
    public static <T> Result<T> wrap(T value) { return new Result(value); }
    /** Produces a result with the given value with the provided reason. */
    public static <T> Result<T> wrap(T value, String reason) { return new Result(value, reason); }
    
}
