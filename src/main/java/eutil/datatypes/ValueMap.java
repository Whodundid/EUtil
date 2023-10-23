package eutil.datatypes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A specialized type of hash map that will return implementation specified
 * datatypes for any given key value.
 * <p>
 * @implNote This class does NOT ensure value type safety in the slightest! As such, any invalid
 * returning datatypes very well may cause {@code ClassCastException}s to be thrown in executed code.
 * 
 * @see ClassCastException
 * 
 * @since 2.6.0
 * 
 * @author Hunter Bragg
 */
public class ValueMap implements Iterable<Map.Entry<String, Object>> {

    /** The internal map that holds all items. */
    private Map<String, Object> internalMap = new HashMap<>();
    
    @Override
    public Iterator<Map.Entry<String, Object>> iterator() {
        return internalMap.entrySet().iterator();
    }
    
    public void put(String name, Object value) {
        internalMap.put(name, value);
    }
    
    /**
     * Returns the value under the given key name.
     * 
     * @param <T>  The implementation specified datatype to return as
     * @param name The key of the value to retrieve
     * 
     * @return The value under the given key name or null if it did not exist
     */
    public <T> T get(String name) {
        return (T) internalMap.get(name);
    }
    
    /**
     * Returns the value under the given key name or the given default value if
     * a value under the given name did not actually exist.
     * 
     * @param <T>          The implementation specified datatype to return as
     * @param name         The key of the value to retrieve
     * @param defaultValue A value to return if this map doesn't contain the
     *                     given key
     * 
     * @return The value under the given key name or the 'defaultValue' if it
     *         did not exist
     */
    public <T> T getOrDefault(String name, T defaultValue) {
        return (T) internalMap.getOrDefault(name, defaultValue);
    }
    
    /**
     * Returns the value under the given 'name' key if it is present, otherwise
     * this method will create the given 'valueToCreate' within the map using
     * the given key name and then return it.
     * 
     * @param <T>           The implementation specified datatype to return as
     * @param name          The key of the value to retrieve
     * @param valueToCreate A value to create under the given key if a value
     *                      didn't already exist for it
     *                      
     * @return The value under the given key
     */
    public <T> T getOrCreate(String name, T valueToCreate) {
        if (!internalMap.containsKey(name)) internalMap.put(name, valueToCreate);
        return (T) internalMap.get(name);
    }
    
    /**
     * Removes the given key from this map and returns whatever value was
     * currently there (if any).
     * 
     * @param <T>  The implementation specified datatype to return as
     * @param name The key of the value to retrieve
     * 
     * @return The value under the given key
     */
    public <T> T delete(String name) {
        var value = internalMap.get(name);
        internalMap.put(name, null);
        return (T) value;
    }
    
    public Map<String, Object> getInternalMap() { return internalMap; }
    
    public boolean containsKey(String key) { return internalMap.containsKey(key); }
    public boolean containsValue(Object value) { return internalMap.containsValue(value); }
    
}
