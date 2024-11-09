package eutil.lambda;

import static eutil.lambda.Predicates.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import eutil.EUtil;
import eutil.datatypes.EArrayList;
import eutil.datatypes.util.EList;

/**
 * A Java implementation of a sudo-Lambda-For loop structure.
 * 
 * <p>
 * Lambda-For loops expand upon the existing capability of traditional for-each loops by introducing
 * the ability to perform functional operations on iterable inputs. Furthermore, Lambda-For
 * loops possess access to both the current loop integer index as well as the element
 * produced by each loop iteration. Passing an iterable object to this structure will produce an Iterable
 * consisting of lambda productions each with their proper element and corresponding loop index. The loop can
 * also be set to start at a specific index as well as be incremented by a specific amount.
 * 
 * @implSpec
 * <p>Example:
 * 
 * <pre>
 * {@code
 * ArrayList<String> words = new ArrayList<String>();
 * 
 * words.add("This");
 * words.add("is");
 * words.add("an");
 * words.add("example!");
 * 
 * //--------------------------------------------------------------------------------------------------------------------------
 * // Example 1:
 * // Basic Lambda-For demonstrating that each production contains both the loop's index as well as the corresponding element.
 * //--------------------------------------------------------------------------------------------------------------------------
 * 
 * for (var s : LF.of(words)) {
 *     System.out.println("[index: " + s.index + " : element: " + s.element + "]");
 * }
 * 
 * 
 * //---------------------------------------------------------------
 * // Example 2:
 * // This Lambda-For will produce the string length for each word.
 * //---------------------------------------------------------------
 * 
 * for (var i : LF.of(words).map(w -> w.length())) {
 *     System.out.println(i);
 * }
 * 
 * </pre>
 * 
 * @param <E> type The type of element produced from iteration
 * 
 * @author Hunter Bragg
 * @see Iterable
 * @since 1.1.0
 */
public class LF<E> implements Iterable<LF.Production<E>> {
    
    static {
        var l = EList.of("dog", "cat", "lizzard");
        
        for (var p : LF.of(l)) {
            System.out.println(p.element);
        }
    }
    
    //========
    // Fields
    //========
    
    private int curIndex = 0;
    private int start = 0, by = 0, size = 0;
    private final EList<Production<E>> productions = EList.newList();
    
    //==============
    // Constructors
    //==============
    
    private LF(int startIn, int byIn, Iterable<E> dataIn) { this(startIn, byIn, dataIn.iterator()); }
    private LF(int startIn, int byIn, Stream<E> dataIn) { this(startIn, byIn, dataIn.iterator()); }
    
    private LF(int startIn, int byIn, Iterator<E> it) {
        if (it == null || start < 0 || by < 0) { return; }
        start = startIn;
        by = byIn;
        
        while (curIndex < start && it.hasNext()) {
            curIndex++;
            it.next();
        }
        
        while (it.hasNext()) {
            productions.add(new Production(curIndex++, it.next()));
            int i = 1;
            while (i < by && it.hasNext()) {
                i++;
                curIndex++;
                it.next();
            }
        }
        
        size = productions.size();
    }
    
    //---------------------------------------------------------------------------------------
    
    @Override public Iterator<Production<E>> iterator() { return new Itr(); }
    
    //---------------------------------------------------------------------------------------
    
    /** Isolates the elements from the production list. */
    private Stream<E> stream() { return productions.stream().map(d -> d.element); }
    
    //---------------------------------------------------------------------------------------
    
    /** Generates an Iterable containing lambda productions for the given iterable object. */
    public static <E> LF<E> of(Iterable<E> dataIn) { return new LF(0, 1, dataIn); }
    /** Generates an Iterable containing lambda productions for the given typed array. */
    public static <E> LF<E> of(E... dataIn) { return new LF(0, 1, new EArrayList(dataIn)); }
    /** Generates an Iterable containing lambda productions for the given iterable object. Furthermore, this specifies the starting index. */
    public static <E> LF<E> of(int start, Iterable<E> dataIn) { return new LF(start, 1, dataIn); }
    /** Generates an Iterable containing lambda productions for the given iterable object. Furthermore, this specifies the starting index as well as the increment amount. */
    public static <E> LF<E> of(int start, int by, Iterable<E> dataIn) { return new LF(start, by, dataIn); }
    
    /** Performs a mapping function across each element in the given iterable object. */
    public <T> LF<T> map(Function<? super E, ? extends T> mapper) { return new LF(start, by, stream().map(mapper)); }
    /** Performs a filtering function across each element in the given iterable object. */
    public LF<E> filter(Predicate<? super E> filter) { return new LF(start, by, stream().filter(filter)); }
    /** Performs a filtering function which removes null objects within the given iterable object. */
    public LF<E> filterNull() { return new LF(start, by, stream().filter(NOT_NULL)); }
    /** Performs a filtering function which removes null objects as well as the specified condition within the given iterable object. */
    public LF<E> filterNull(Predicate<? super E> filter) { return new LF(start, by, stream().filter(NOT_NULL).filter(filter)); }
    
    //---------------------------------------------------------------------------------------
    
    /** A single production from a given FE (for each) structure containing both an index and an object. */
    public static class Production<E> {
        
        /** The index of this element. */
        public final int index;
        /** The element itself. */
        public final E element;
        
        /** Private to prevent outside instantiation. */
        private Production(int indexIn, E objectIn) {
            index = indexIn;
            element = objectIn;
        }
        
        @Override
        public String toString() {
            return String.valueOf(element);
        }
        
        @Override
        /** Overriding equals so that the comparison is against the element instead of this production. */
        public boolean equals(Object obj) {
            return EUtil.isEqual(element, obj);
        }
        
        /** Returns this production's element. */
        public E get() { return element; }
        
        /** Returns true if this production's element is null. */
        public boolean isNull() { return element == null; }
        
    }
    
    //==================
    // Internal Classes
    //==================
    
    private class Itr implements Iterator<Production<E>> {
        
        //========
        // Fields
        //========
        
        private int cur, last;
        
        //===========
        // Overrides
        //===========
        
        @Override
        public boolean hasNext() { return cur != productions.size(); }
        
        @Override
        public Production<E> next() {
            int i = cur;
            if (i >= size) throw new NoSuchElementException();
            Object[] elementData = productions.toArray();
            if (i >= elementData.length) throw new ConcurrentModificationException();
            cur = i + 1;
            return (Production<E>) elementData[last = i];
        }
        
        @Override
        public void forEachRemaining(Consumer action) {
            Objects.requireNonNull(action);
            int i = cur;
            if (i < size) {
                Object[] elementData = productions.toArray();
                if (i >= elementData.length) throw new ConcurrentModificationException();
                for (; i < size; i++) {
                    Production<E> p = (Production<E>) elementData[i];
                    action.accept((E) p.element);
                }
                cur = i;
                last = i - 1;
            }
        }
    }
    
}
