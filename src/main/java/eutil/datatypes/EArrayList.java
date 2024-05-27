package eutil.datatypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eutil.datatypes.util.EList;

/**
 * A customized wrapper implementation of a normal ArrayList.
 * Includes numerous functions for Stream manipulation and conversions as well as
 * many useful methods for finding, getting, and removing specific types of objects.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class EArrayList<E> implements EList<E> {
	
	private List<E> list;
	private boolean allowDuplicates = true;
	
	//--------------
	// Constructors
	//--------------
	
	public EArrayList() {
		list = new ArrayList<>();
	}
	
	public EArrayList(int initialCapacity) {
		list = new ArrayList<>(initialCapacity);
	}
	
	/** Internal constructor used to wrap an existing list. */
	private EArrayList(List<E> listIn) {
		Objects.requireNonNull(listIn);
		list = listIn;
	}
	
	/** Creates an EArrayList from a given Iterable object. */
	public EArrayList(Iterable<E> it) {
		this();
		Objects.requireNonNull(it);
		
		if (it instanceof List<E> l) {
			if (l instanceof ArrayList<E>) list = l;
			else list = new ArrayList<>(l);
		}
		else {
			for (var e : it) add(e);
		}
	}
	
	public EArrayList(E... objs) {
		list = new ArrayList<>(objs.length);
		for (E e : objs) add(e);
	}
	
	public EArrayList(Stream<E> streamIn) {
		this();
		Objects.requireNonNull(streamIn);
		
		Iterator<E> it = streamIn.iterator();
		while (it.hasNext()) list.add(it.next());
	}
	
	public EArrayList(List<E> in, int from) { this(in, from, (in != null) ? in.size() : -1); }
	public EArrayList(List<E> in, int from, int to) {
		if (in == null || from < 0 || to < 0) {
			list = new ArrayList<>();
			return;
		}
		
		list = new ArrayList<>(in.size());
		
		for (int i = from; i < to; i++) {
			add(in.get(i));
		}
	}
	
	//===========
	// Overrides
	//===========
	
	@Override
	public String toString() {
		return list.toString();
	}
	
	public void trimToSize() { ((ArrayList) list).trimToSize(); }
	@Override public void ensureCapacity(int minCapacity) { ((ArrayList) list).ensureCapacity(minCapacity); }
	
	//=================
	// EList Overrides
	//=================
	
	@Override public boolean addAll(int index, Collection<? extends E> c) { return list.addAll(index, c); }
	@Override public void replaceAll(UnaryOperator<E> operator) { list.replaceAll(operator); }
	@Override public void sort(Comparator<? super E> c) { list.sort(c); }
	@Override public E get(int index) { return list.get(index); }
	@Override public E set(int index, E element) { return list.set(index, element); }
	@Override public void add(int index, E element) { list.add(index, element); }
	@Override public E remove(int index) { return list.remove(index); }
	@Override public int indexOf(Object o) { return list.indexOf(o); }
	@Override public int lastIndexOf(Object o) { return list.lastIndexOf(o); }
	@Override public ListIterator<E> listIterator() { return list.listIterator(); }
	@Override public ListIterator<E> listIterator(int index) { return list.listIterator(index); }
	@Override public List<E> subList(int fromIndex, int toIndex) { return list.subList(fromIndex, toIndex); }
	@Override public void forEach(Consumer<? super E> action) { list.forEach(action); }
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof EArrayList<?>)) return false;
		
		boolean r = true;
		EArrayList<?> in = (EArrayList<?>) o;
		r &= in.allowDuplicates;
		r &= list.equals(in.list);
		
		return r;
	}
	
	@Override
	public int hashCode() {
		int internalListHash = list.hashCode();
		return 31 * internalListHash + super.hashCode();
	}
	
	@Override
	public Object clone() {
		try {
			EArrayList<E> v = (EArrayList<E>) super.clone();
			ArrayList<E> l = (ArrayList<E>) ((ArrayList<E>) list).clone();
			v.list = (ArrayList<E>) l;
			return v;
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
	
	//======================
	// Collection Overrides
	//======================

	@Override public int size() { return list.size(); }
	@Override public boolean isEmpty() { return list.isEmpty(); }
	@Override public boolean contains(Object o) { return list.contains(o); }
	@Override public Iterator<E> iterator() { return list.iterator(); }
	@Override public Object[] toArray() { return list.toArray(); }
	@Override public <T> T[] toArray(T[] a) { return list.toArray(a); }
	@Override public <T> T[] toArray(IntFunction<T[]> generator) { return list.toArray(generator); }
	@Override public boolean add(E object) { return list.add(object); }
	@Override public boolean remove(Object o) { return list.remove(o); }
	@Override public boolean containsAll(Collection<?> c) { return list.containsAll(c); }
	@Override public boolean addAll(Collection<? extends E> c) { return list.addAll(c); }
	@Override public boolean removeAll(Collection<?> c) { return list.removeAll(c); }
	@Override public boolean removeIf(Predicate<? super E> filter) { return list.removeIf(filter); }
	@Override public boolean retainAll(Collection<?> c) { return list.retainAll(c); }
	@Override public void clear() { list.clear(); }
	@Override public Spliterator<E> spliterator() { return list.spliterator(); }
	@Override public Stream<E> stream() { return list.stream(); }
	@Override public Stream<E> parallelStream() { return list.parallelStream(); }
	
	//=========
	// Getters
	//=========

	/** Returns true if this list allows adding duplicate values. */
	public boolean allowsDuplicates() { return allowDuplicates; }
	
	//=========
	// Setters
	//=========

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
	
	//================
	// Static Methods
	//================
    
    /**
     * Does not create a new internal list but instead wraps EArrayList
     * functionality around the given one.
     */
    public static <T> EArrayList<T> wrap(List<T> listIn) {
		return (listIn != null) ? new EArrayList<>(listIn) : null;
	}
	
    /**
     * Does not create a new internal list but instead wraps EArrayList
     * functionality around the given collection.
     */
	public static <T> EArrayList<T> wrap(Collection<T> collectionIn) {
	    return (collectionIn != null) ? new EArrayList<>(collectionIn) : null;
	}
	
}