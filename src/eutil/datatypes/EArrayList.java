package eutil.datatypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A customized wrapper implementation of a normal ArrayList.
 * Includes numerous functions for Stream manipulation and conversions as well as
 * many useful methods for finding, getting, and removing specific types of objects.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class EArrayList<E> extends ArrayList<E> implements EList<E> {
	
	/** The underlying list being wrapped. */
	private List<E> list;
	/** True if duplicate entries are allowed. */
	private boolean allowDuplicates = true;
	
	private static final Set<Collector.Characteristics> CH_ID = Collections
		.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
	
	//--------------
	// Constructors
	//--------------
	
	public EArrayList() { list = new ArrayList<>(); }
	public EArrayList(int initialCapacity) { list = new ArrayList<>(initialCapacity); }
	
	// Removing in favor of Iterable argument constructor -- much more flexible
	//public EArrayList(Collection<? extends E> c) { list = new ArrayList(c); }
	
	/** Internal constructor used to wrap an existing list. */
	private EArrayList(List<E> listIn) {
		Objects.requireNonNull(listIn);
		list = listIn;
	}
	
	public EArrayList(EList<E> listIn) {
		Objects.requireNonNull(listIn);
		list = listIn;
	}
	
	/** Creates an EArrayList from a given Iterable object. */
	public EArrayList(Iterable<E> it) {
		this();
		it.forEach(i -> add(i));
	}
	
	public EArrayList(E... objs) {
		list = new ArrayList(objs.length);
		for (E e : objs) { add(e); }
	}
	
	public EArrayList(Stream<E> streamIn) {
		this();
		if (streamIn == null) { return; }
		Iterator<E> it = streamIn.iterator();
		while (it.hasNext()) { list.add(it.next()); }
	}
	
	public EArrayList(List<E> in, int from) { this(in, from, (in != null) ? in.size() : -1); }
	public EArrayList(List<E> in, int from, int to) {
		if (in == null || from < 0 || to < 0) { list = new ArrayList(); return; }
		list = new ArrayList(in.size());
		
		for (int i = from; i < to; i++) {
			add(in.get(i));
		}
	}
	
	//-------------------
	// Overrides : EList
	//-------------------
	
	public List<E> getWrappedList() { return list; }
	public void setWrappedList(List<E> listIn) { list = Objects.requireNonNull(listIn); }
	
	//-----------------------
	// Overrides : ArrayList
	//-----------------------
	
	@Override public void trimToSize() { ((ArrayList<E>) list).trimToSize(); }
	@Override public void ensureCapacity(int minCapacity) { ((ArrayList<E>) list).ensureCapacity(minCapacity); }
	@Override public int hashCode() { return list.hashCode(); }
	@Override public List<E> subList(int fromIndex, int toIndex) { return list.subList(fromIndex, toIndex); }
	@Override public void replaceAll(UnaryOperator<E> operator) { list.replaceAll(operator); }
	@Override public void sort(Comparator<? super E> c) { list.sort(c); }
	
	//--------------------------
	// Overrides : AbstractList
	//--------------------------
	
	@Override public boolean add(E e) { return list.add(e); }
	@Override public E get(int index) { return list.get(index); }
	@Override public E set(int index, E element) { return list.set(index, element); }
	@Override public void add(int index, E element) { list.add(index, element); }
	@Override public E remove(int index) { return list.remove(index); }
	@Override public int indexOf(Object o) { return list.indexOf(o); }
	@Override public int lastIndexOf(Object o) { return list.lastIndexOf(o); }
	@Override public void clear() { list.clear(); }
	@Override public boolean addAll(int index, Collection<? extends E> c) { return list.addAll(index, c); }
	@Override public boolean addAll(Collection<? extends E> c) { return list.addAll(c); }
	@Override public Iterator<E> iterator() { return list.iterator(); }
	@Override public Spliterator<E> spliterator() { return list.spliterator(); }
	@Override public ListIterator<E> listIterator() { return list.listIterator(); }
	@Override public ListIterator<E> listIterator(int index) { return list.listIterator(index); }
	@Override public boolean remove(Object o) { return list.remove(o); }
	@Override public boolean removeAll(Collection<?> c) { return list.removeAll(c); }
	@Override public boolean retainAll(Collection<?> c) { return list.retainAll(c); }
	@Override public Object clone() { return ((ArrayList<E>) list).clone(); }
	@Override public void forEach(Consumer<? super E> action) { list.forEach(action); }
	
	//--------------------------------
	// Overrides : AbstractCollection
	//--------------------------------

	@Override public int size() { return list.size(); }
	@Override public boolean isEmpty() { return list.isEmpty(); }
	@Override public boolean contains(Object o) { return list.contains(o); }
	@Override public Object[] toArray() { return list.toArray(); }
	@Override public <T> T[] toArray(T[] a) { return list.toArray(a); }
	
	//---------
	// Methods
	//---------
	
	/** Prevents any additional values which already exist in this list from being added.
	 *  Note: this does not remove any existing duplicate values already present. */
	public EArrayList<E> noDuplicates() { return setAllowDuplicates(false); }
	
	//--------------------
	// EArrayList Getters
	//--------------------

	/** Returns true if this list allows adding duplicate values. */
	public boolean allowsDuplicates() { return allowDuplicates; }
	
	//--------------------
	// EArrayList Setters
	//--------------------

	/** Sets whether this list will allow duplicate entries or not. If no, the list
	 *  removes duplicates. */
	public EArrayList<E> setAllowDuplicates(boolean val) {
		if (!val) {
			synchronized (this) {
				List<E> nonDuplicates = list.stream().distinct().collect(Collectors.toList());
				clear();
				addAll(nonDuplicates);
			}
		}
		return this;
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/** Does not create a new internal list but instead wraps EArrayList functionality around the given one. */
	public static <T> EList<T> wrap(List<T> listIn) {
		return (listIn != null) ? new EArrayList(listIn) : null;
	}
	
	/** Collector implementation used to be able to convert a typed stream of data into an EArrayList of the same type. */
	public static <T> Collector<T, ?, EList<T>> toEArrayList() {
		return new ECollector<>((Supplier<List<T>>) EArrayList::new, List::add, (left, right) -> { left.addAll(right); return left; }, CH_ID);
	}
	
	/** Returns a new EArrayList<T> created from values of the given typed array. */
	public static <T> EList<T> of(T... vals) {
		return new EArrayList<T>().add(vals);
	}
	
	/** Returns a new EArrayList<T> created from values of the given list. */
	public static <T> EList<T> of(List<T> in) {
		return new EArrayList(in);
	}
	
	public static <T> EList<T> of(Collection<T> in) {
		return new EArrayList(in);
	}
	
	//------------------
	// Internal Classes
	//------------------
	
	static class ECollector<T, A, R> implements Collector<T, A, R> {
		
		private static <I, R> Function<I, R> castingIdentity() { return i -> (R) i; }
		
		private final Supplier<A> supplier;
		private final BiConsumer<A, T> accumulator;
		private final BinaryOperator<A> combiner;
		private final Function<A, R> finisher;
		private final Set<Characteristics> characteristics;

		ECollector(Supplier<A> supplierIn, BiConsumer<A, T> accumulatorIn, BinaryOperator<A> combinerIn, Function<A, R> finisherIn, Set<Characteristics> characteristicsIn) {
			supplier = supplierIn;
			accumulator = accumulatorIn;
			combiner = combinerIn;
			finisher = finisherIn;
			characteristics = characteristicsIn;
		}

		ECollector(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Set<Characteristics> characteristics) {
			this(supplier, accumulator, combiner, castingIdentity(), characteristics);
		}

		@Override public BiConsumer<A, T> accumulator() { return accumulator; }
		@Override public Supplier<A> supplier() { return supplier; }
		@Override public BinaryOperator<A> combiner() { return combiner; }
		@Override public Function<A, R> finisher() { return finisher; }
		@Override public Set<Characteristics> characteristics() { return characteristics; }
	}
	
}
