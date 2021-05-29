package eutil.misc;

import eutil.storage.EArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * A method of implementing a sudo lambda for loop structure into Java.
 * Java Enhanced For loops do not possess the ability to have both an index as well as
 * the object produced at each iteration. Passing an iterable object to this
 * structure will produce an Iterable consiting of lambda productions each with their
 * proper element and coresponding index. The loop can also be set to start at a specific
 * index as well as increment by a specific amount.
 * 
 * @implSpec
 * <p>Example:
 * <pre>
 * {@code
 * ArrayList<String> words = new ArrayList<String>();
 * 
 * words.add("This");
 * words.add("is");
 * words.add("an");
 * words.add("example!");
 * 
 * for (FE.P<String> s : FE.of(words))
 *     System.out.println("[index: " + s.index + " : element: " + s.element + "]");
 * }
 * </pre>
 * 
 * @param <E> type The type of element produced from iteration
 * 
 * @author Hunter Bragg
 * @see Iterable
 * @since 1.2.1
 */
public class LF<E> implements Iterable<LF.P<E>> {

	private int curIndex = 0;
	private int start = 0, by = 0, size = 0;
	private final EArrayList<P<E>> data = new EArrayList();
	
	//------------------------------------------------------
	
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
			data.add(new P(curIndex++, it.next()));
			int i = 1;
			while (i < by && it.hasNext()) {
				i++;
				curIndex++;
				it.next();
			}
		}
		
		size = data.size();
	}
	
	//------------------------------------------------------
	
	@Override
	public Iterator<P<E>> iterator() {
		return new Itr();
	}
	
	//------------------------------------------------------
	
	/** Isolates the elements from the production list. */
	private Stream<E> stream() { return data.stream().map(d -> d.element); }
	
	//------------------------------------------------------
	
	public static <E> LF<E> of(Iterable<E> dataIn) { return new LF(0, 1, dataIn); }
	public static <E> LF<E> of(int start, Iterable<E> dataIn) { return new LF(start, 1, dataIn); }
	public static <E> LF<E> of(int start, int by, Iterable<E> dataIn) { return new LF(start, by, dataIn); }
	
	public <T> LF<T> map(Function<? super E, ? extends T> mapper) { return new LF(start, by, stream().map(mapper)); }
	public LF<E> filter(Predicate<? super E> filter) { return new LF(start, by, stream().filter(filter)); }
	public LF<E> filterNull() { return new LF(start, by, stream().filter(o -> o != null)); }
	public LF<E> filterNull(Predicate<? super E> filter) { return new LF(start, by, stream().filter(o -> o != null).filter(filter)); }
	
	//------------------------------------------------------
	
	/** A single production from a given FE (for each) structure containing both an index and an object. */
	public static class P<E> {
		
		/** The index of the given object. */
		public final int index;
		/** The object itself. */
		public final E element;
		
		/** Private to prevent outside instantiation. */
		private P(int indexIn, E objectIn) {
			index = indexIn;
			element = objectIn;
		}
		
		@Override
		public String toString() {
			return index + " : " + element;
		}
		
	}
	
	//------------------------------------------------------
	
	private class Itr implements Iterator<P<E>> {
		
		private int cur, last;
		
		//--------------
		// Constructors
		//--------------
		
		Itr() {}
		
		//-----------
		// Overrides
		//-----------
		
		@Override
		public boolean hasNext() { return cur != data.size(); }
		
		@Override
		public P<E> next() {
			int i = cur;
			if (i >= size) throw new NoSuchElementException();
			Object[] elementData = data.toArray();
			if (i >= elementData.length) throw new ConcurrentModificationException();
			cur = i + 1;
			return (P<E>) elementData[last = i];
		}
		
		@Override
		public void forEachRemaining(Consumer action) {
			Objects.requireNonNull(action);
			int i = cur;
			if (i < size) {
				Object[] elementData = data.toArray();
				if (i >= elementData.length) throw new ConcurrentModificationException();
				for (; i < size; i++) {
					P<E> p = (P<E>) elementData[i];
					action.accept((E) p.element);
				}
				cur = i;
				last = i - 1;
			}
		}
	}
	
}
