package eutil.datatypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.EmptyStackException;
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
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eutil.EUtil;
import eutil.datatypes.util.EList;
import eutil.random.ERandomUtil;

/**
 * A customized wrapper implementation of a normal ArrayList.
 * Includes numerous functions for Stream manipulation and conversions as well as
 * many useful methods for finding, getting, and removing specific types of objects.
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class EArrayList<E> extends ArrayList<E> implements Deque<E>, EList<E> {
	
	private List<E> list;
	private boolean allowDuplicates = true;
	static final Set<Collector.Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
	
	//--------------
	// Constructors
	//--------------
	
	public EArrayList() { list = new ArrayList(); }
	public EArrayList(int initialCapacity) { list = new ArrayList(initialCapacity); }
	
	// Removing in favor of Iterable argument constructor -- much more flexible
	//public EArrayList(Collection<? extends E> c) { list = new ArrayList(c); }
	
	/** Internal constructor used to wrap an existing list. */
	private EArrayList(List<E> listIn) {
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
	
	//---------------------
	// ArrayList Overrides
	//---------------------
	
	@Override public void trimToSize() { ((ArrayList<E>) list).trimToSize(); }
	@Override public void ensureCapacity(int minCapacity) { ((ArrayList<E>) list).ensureCapacity(minCapacity); }
	@Override public int hashCode() { return list.hashCode(); }
	@Override public List<E> subList(int fromIndex, int toIndex) { return list.subList(fromIndex, toIndex); }
	@Override public void replaceAll(UnaryOperator<E> operator) { list.replaceAll(operator); }
	@Override public void sort(Comparator<? super E> c) { list.sort(c); }
	
	//------------------------
	// AbstractList Overrides
	//------------------------
	
	@Override public boolean add(E object) { return list.add(object); }
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
	
	//------------------------------
	// AbstractCollection Overrides
	//------------------------------

	@Override public int size() { return list.size(); }
	@Override public boolean isEmpty() { return list.isEmpty(); }
	@Override public boolean contains(Object o) { return list.contains(o); }
	@Override public Object[] toArray() { return list.toArray(); }
	@Override public <T> T[] toArray(T[] a) { return list.toArray(a); }
	
	//--------------------
	// WrapperList Methods
	//--------------------
	
	/**
	 * Returns true if each object specified is within this list.
	 * Returns false if given 
	 * 
	 * @param objs
	 * @return
	 */
	public boolean containsEach(E... objs) {
		if (objs.length == 0) return false;
		for (int i = 0; i < objs.length; i++) {
			if (!contains(objs[i])) return false;
		}
		return true;
	}
	
	/** Performs a size reduction on this list by cutting off all values after the given size. */
	public EArrayList<E> trimToSize(int sizeIn) {
		list = subList(0, sizeIn);
		return this;
	}
	
	/** If the given value is currently present within the list, the value is replaced, otherwise it is added. */
	public E put(E val) {
		int i = indexOf(val);
		if (i >= 0) set(i, val);
		else add(val);
		return val;
	}
	
	/** Returns true if any object within this list matches the given predicate. */
	public boolean anyMatch(Predicate<? super E> condition) {
		return stream().anyMatch(condition);
	}
	
	/** Returns true if the given index is at the start of this list. */
	public boolean atStart(int i) { return i == 0; }
	
	/** Returns true if the given index is at the end of this list. */
	public boolean atEnd(int i) { return i == size() - 1; }
	
	/** Pushes the given value onto the front of this list and subsequently shifts each of the
	 *  additional values to the right by one. */
	@Override
	public void push(E value) {
		add(0, value);
	}
	
	/**
	 * Pushes and then returns the given value.
	 * 
	 * @param value The value to add
	 * @return The value added
	 * @since 1.6.3
	 */
	public E pushR(E value) {
		add(0, value);
		return value;
	}
	
	public EArrayList<E> pushRT(E value) {
		push(value);
		return this;
	}
	
	/**
	 * Similar to 'pop' where the first element is removed and then returned
	 * but no 'EmptyStackException' is thrown if the list is empty.
	 * 
	 * @return The 'next' element at the beginning of the list
	 * @since 1.6.3
	 */
	public E next() {
		if (isEmpty()) return null;
		return pop();
	}
	
	/** Removes the first value from this list then shifts each of the remaining values to the
	 *  left by one.@return */
	@Override
	public E pop() {
		E val = removeFirst();
		if (val == null) throw new EmptyStackException();
		return val;
	}
	
	/** Retrieves, but does not remove the top element of the array as if this were a stack. */
	@Override
	public E peek() {
		if (size() == 0) throw new EmptyStackException();
		return get(0);
	}
	
	@Override public void addFirst(E e) { add(0, e); }
	@Override
	public void addLast(E e) {
		if (isEmpty()) add(e);
		else add(size() - 1, e);
	}
	
	@Override public boolean offerFirst(E e) { addFirst(e); return true; }
	@Override public boolean offerLast(E e) { addLast(e); return false; }
	@Override public boolean offer(E e) { return offerLast(e); }
	
	@Override public E pollFirst() { return removeFirst(); }
	@Override public E pollLast() { return removeLast(); }
	@Override public E poll() { return pollFirst(); }
	
	@Override public E peekFirst() { return null; }
	@Override public E peekLast() { return null; }
	@Override public E element() { return peek(); }
	
	@Override public E remove() { return removeFirst(); }
	
	@Override
	public boolean removeFirstOccurrence(Object o) {
		if (isEmpty()) return false;
		int s = size();
		for (int i = 0; i < s; i++) {
			if (EUtil.isEqual(get(i), o)) {
				remove(i);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean removeLastOccurrence(Object o) {
		if (isEmpty()) return false;
		int s = size();
		for (int i = s - 1; i >= 0; i--) {
			if (EUtil.isEqual(get(i), o)) {
				remove(i);
				return true;
			}
		}
		return false;
	}
	
	@Override public Iterator<E> descendingIterator() { return iterator(); }
	
	/** Replaces a range of values in this list with the given value. */
	public EArrayList<E> replaceFrom(int from, E value) { return replaceFrom(from, size(), value); }
	/** Replaces a range of values in this list with the given value. */
	public EArrayList<E> replaceFrom(int from, int to, E value) {
		if (from < 0) return this;
		if (to >= size()) return this;
		set(from, value);
		for (int i = from + 1; i < to; i++) {
			remove(from + 1);
		}
		return this;
	}
	
	/** Removes a range of values from this list. */
	public EArrayList<E> removeFrom(int from) { return removeFrom(from, size()); }
	/** Removes a range of values from this list. */
	public EArrayList<E> removeFrom(int from, int to) {
		if (from >= 0) {
			for (int i = from; i < to; i++) {
				remove(from);
			}
		}
		return this;
	}
	
	public EArrayList<E> shiftLeft(int amount) {
		EArrayList<E> r = new EArrayList(this);
		for (int i = 0; i < amount; i++) {
			r.add(r.remove(0));
		}
		return r;
	}
	
	public EArrayList<E> shiftRight(int amount) {
		EArrayList<E> r = new EArrayList(this);
		for (int i = 0; i < amount; i++) {
			r.add(0, r.removeLast());
		}
		return r;
	}
	
	/** Adds a range of values from a different list into this one. */
	public EArrayList<E> addFrom(List<E> in, int from) { return addFrom(in, from, (in != null) ? in.size() : -1); }
	/** Adds a range of values from a different list into this one. */
	public EArrayList<E> addFrom(List<E> in, int from, int to) {
		if (in != null && from >= 0) {
			for (int i = from; i < to; i++) {
				add(in.get(i));
			}
		}
		return this;
	}
	
	public E getRandom() { return (isEmpty()) ? null : get(ERandomUtil.getRoll(0, size() - 1)); }
	public E removeRandom() { return (isEmpty()) ? null : remove(ERandomUtil.getRoll(0, size() - 1)); }
	public E removeIfContains(E object) {
		int index = indexOf(object);
		if (index >= 0) return remove(index);
		else return null;
	}
	
	/** Swaps the values at the given indexes. */
	public EArrayList<E> swap(int indexA, int indexB) {
		if (indexA < 0 || indexA >= size()) { throw new IndexOutOfBoundsException("Index: " + indexA + ", Size: " + size()); }
		if (indexB < 0 || indexA >= size()) { throw new IndexOutOfBoundsException("Index: " + indexB + ", Size: " + size()); }
		
		E temp = get(indexA);
		set(indexA, get(indexB));
		set(indexB, temp);
		
		return this;
	}
	
	/** Returns true if there is only a single element in this list. */
	public boolean hasOne() { return size() == 1; }
	
	/** Sets the entire range of this list to the given value.
	 *  Note: this will overwrite existing values. */
	public EArrayList<E> fillWith(E val) {
		for (int i = 0; i < size(); i++) {
			set(i, val);
		}
		return this;
	}
	
	/** Returns true if any of the elements in this list match the given condition. */
	public boolean containsProperty(Predicate<? super E> condition) {
		for (int i = 0; i < size(); i++) {
			if (condition.test(get(i))) { return true; }
		}
		return false;
	}
	
	/** Prints this list to the console. */
	public EArrayList<E> print() { return print(false); }
	/** Prints this list to the console with optional numbering. */
	public EArrayList<E> print(boolean numbering) {
		for (int i = 0; i < size(); i++) {
			System.out.println(((numbering) ? i + ": " : "") + get(i));
		}
		return this;
	}
	
	/** Removes all null values from this list. */
	public EArrayList<E> purgeNulls() {
		int s = size();
		
		for (int i = 0; i < s; i++) {
			Object o = get(i);
			
			if (o == null) {
				remove(i);
				s--;
				i--;
			}
		}
		
		return this;
	}
	
	public E getFirst(Predicate<? super E> condition) {
		for (int i = 0; i < size(); i++) {
			E e = get(i);
			if (condition.test(e)) return e;
		}
		return null;
	}
	
	public E getLast(Predicate<? super E> condition) {
		for (int i = size() - 1; i >= 0; i--) {
			E e = get(i);
			if (condition.test(e)) return e;
		}
		return null;
	}
	
	public E removeFirst(Predicate<? super E> condition) {
		for (int i = 0; i < size(); i++) {
			E e = remove(i);
			if (condition.test(e)) return e;
		}
		return null;
	}
	
	public E removeLast(Predicate<? super E> condition) {
		for (int i = size() - 1; i >= 0; i--) {
			E e = remove(i);
			if (condition.test(e)) return e;
		}
		return null;
	}
	
	public EArrayList<E> addThen(E valIn) {
		add(valIn);
		return this;
	}
	
	/** Same as size() but just adds a call to unify language across Strings, Arrays, and now lists. */
	public int length() { return size(); }
	
	/** Removes and returns the first element in this list. If the list is empty, null is returned instead. */
	public E removeFirst() { return (size() > 0) ? remove(0) : null; }
	/** Removes and returns the last element in this list. If the list is empty, null is returned instead. */
	public E removeLast() { return (size() > 0) ? remove(size() - 1) : null; }
	
	//filters
	/** Returns a new WrapperList consisting of elements from this list reduced by the provided filter. */
	public EArrayList<E> filter(Predicate<? super E> filter) { return new EArrayList<E>(EUtil.filter(this, filter)); }
	/** Returns a new WrapperList consisting of elements from this list with null values removed. */
	public EArrayList<E> filterNull() { return new EArrayList<E>(EUtil.filterNull(this)); }
	/** Returns a new WrapperList consisting of elements from this list reduced by the provided filter with null elements also filtered out. */
	public EArrayList<E> filterNull(Predicate<? super E> filter) { return new EArrayList<E>(EUtil.filterNull(this, filter)); }
	/** Adds the elements from an existing array that match the given condition. */
	public EArrayList<E> filterAdd(E[] arr, Predicate<? super E> condition) { EUtil.filterForEach(arr, condition, this::add); return this; }
	/** Adds the elements from an existing collection that match the given condition. */
	public EArrayList<E> filterAdd(Collection<? extends E> c, Predicate<? super E> condition) { EUtil.filterForEach(c, condition, this::add); return this; }
	/** Adds the elements from an existing array that match the given condition. */
	public EArrayList<E> filterNullAdd(E[] arr, Predicate<? super E> condition) { EUtil.filterNullForEach(arr, condition, this::add); return this; }
	/** Adds the elements from an existing collection that match the given condition. */
	public EArrayList<E> filterNullAdd(Collection<? extends E> c, Predicate<? super E> condition) { EUtil.filterNullForEach(c, condition, this::add); return this; }
	
	//mappers
	/** Returns a new WrapperList<T> formed by converting each of the existing elements in this list to the given new type. */
	public <T> EArrayList<T> map(Function<? super E, ? extends T> mapper) { return new EArrayList<T>(stream().map(mapper)); }
	
	//forEach methods
	/** Performs both a filtering operation as well as the given consumer action on the remaining elements. */
	public void filterForEach(Predicate<? super E> filter, Consumer<? super E> action) { EUtil.filterForEach(this, filter, action); }
	/** Performs both a null filtering operation as well as the given consumer action on the remaining elements. */
	public void filterNullForEach(Consumer<? super E> action) { EUtil.filterNullForEach(this, action); }
	/** Performs both the given filtering operation including a null filter as well as the given consumer action on the remaining elements. */
	public void filterNullForEach(Predicate<? super E> filter, Consumer<? super E> action) { EUtil.filterNullForEach(this, filter, action); }
	/** Performs the given consumer action on the existing elements and then returns the given value. */
	public <R> R forEachR(Consumer<? super E> action, R returnVal) { forEach(action); return returnVal; }
	/** Performs the given consumer action on the existing elements if the given condition is true, then returns the result of the condition. */
	public boolean ifForEach(boolean check, Consumer<? super E> action) { return EUtil.ifForEach(check, this, action); }
	
	//collectors
	/** Takes the contents of this list and filters them into another form using the given collector. */
	public <R, A> R filterInto(Predicate<? super E> filter, Collector<? super E, A, R> collector) { return EUtil.filterInto(this, filter, collector); }
	
	/** Prevents any additional values which already exist in this list from being added.
	 *  Note: this does not remove any existing duplicate values already present. */
	public EArrayList<E> noDuplicates() { return setAllowDuplicates(false); }

	/** Returns a new typed WrapperList formed from combining each of the given lists.
	 *  This works by first adding all of the elements from list A, then adds all of the
	 *  elements in list B. */
	public static <E> EArrayList<E> combineLists(List<E> a, List<E> b) {
		EArrayList<E> l = new EArrayList();
		if (a != null) l.addAll(a);
		if (b != null) l.addAll(b);
		return l;
	}
	
	/** Returns a new typed WrapperList formed by interweaving values from both of the given lists.
	 *  Values will continue to be interwoven until one list is empty. Once this happens,
	 *  remaining values from the list with more values will be added until it is empty. */
	public static <E> EArrayList<E> weaveLists(List<E> a, List<E> b) {
		EArrayList<E> l = new EArrayList();
		
		int i = 0;
		for (; i < a.size() && i < b.size(); i++) {
			l.add(a.get(i));
			l.add(b.get(i));
		}
		while (i < a.size()) {
			l.add(a.get(i++));
		}
		while (i < b.size()) {
			l.add(b.get(i++));
		}
		
		return l;
	}

	/** Returns, but does not remove, the first element in this list. If this list is empty, null is returned instead. */
	public E getFirst() { return (size() > 0) ? get(0) : null; }
	/** Returns, but does not remove, the last element in this list. If this list is empty, null is returned instead. */
	public E getLast() { return (size() > 0) ? get(size() - 1) : null; }
	/** Returns true if there is at least one element in this list. */
	public boolean isNotEmpty() { return size() > 0; }
	/** Returns true if this list does not contain the given object. */
	public boolean notContains(Object o) { return !contains(o); }
	
	/** Returns the first element in this list that is an instance of the given class. */
	public E getFirstInstanceOf(Class<?> cIn) {
		for (Object e : list) {
			if (cIn.isInstance(e)) return (E) e;
		}
		return null;
	}

	/** Returns a list of elements in this list that are instances of the given class. */
	public EArrayList<E> getAllInstancesOf(Class<?> cIn) {
		EArrayList<E> instances = new EArrayList();
		for (Object e : list) {
			if (cIn.isInstance(e)) instances.add((E) e);
		}
		return instances;
	}
	
	/** Removes and returns a list of elements in this list that are instances of the given class. */
	public EArrayList<E> removeAllInstancesOf(Class<?> cIn) {
		EArrayList<E> toBeRemoved = new EArrayList();
		for (Object e : list) {
			if (cIn.isInstance(e)) toBeRemoved.add((E) e);
		}
		for (E e : toBeRemoved) remove(e);
		return this;
	}

	/** Returns true if any of the elements in this list are an instance of the given class. */
	public boolean containsInstanceOf(Class<?> cIn) {
		for (Object e : list) {
			if (cIn.isInstance(e)) return true;
		}
		return false;
	}

	/** Returns true if none of the elements in this list are an instance of the given class. */
	public boolean containsNoInstanceOf(Class<?> cIn) {
		for (Object e : list) {
			if (cIn.isInstance(e)) return false;
		}
		return true;
	}
	
	/**
	 * Returns an EArrayList with reversed elements from this one.
	 * 
	 * @since 1.6.0
	 */
	public EArrayList<E> reverse() {
		EArrayList<E> r = new EArrayList<>(size());
		for (int i = size() - 1; i >= 0; i--) {
			r.add(get(i));
		}
		return r;
	}

	//-------------
	// Add Methods
	//-------------
	
	public EArrayList<E> clearThenAdd(E... e) {
		clear();
		return add(e);
	}

	/** Adds each of the elements to this list. */
	public EArrayList<E> add(E... e) {
		for (int i = 0; i < e.length; i++) add(e[i]);
		return this;
	}
	
	public EArrayList<E> clearThenAddA(E[] e) {
		clear();
		return addA(e);
	}
	
	/**
	 * Adds each of the elements in the given array to this list.
	 */
	public EArrayList<E> addA(E[] e) {
		for (E val : e) add(val);
		return this;
	}
	
	/**
	 * Adds the given object to this list then returns it.
	 */
	public E addR(E e) {
		add(e);
		return e;
	}
	
	/**
	 * Adds the given object to this list then returns the given returnVal.
	 */
	public <R> R addR(E e, R returnVal) {
		add(e);
		return returnVal;
	}
	
	/**
	 * Adds the given object at the specified index and then returns the
	 * object.
	 * 
	 * @param index The index to add the object to
	 * @param value The object to add
	 * @return The object being added
	 * @since 1.6.3
	 */
	public E addR(int index, E value) {
		add(value);
		return value;
	}
	
	/**
	 * Adds the given object at the specified index and then returns the given
	 * 'returnVal'.
	 * 
	 * @param <R> The type of object being returned
	 * @param index The index to add the object to
	 * @param value The object to add
	 * @param returnVal The object to return
	 * @return The given 'returnVal'
	 * @since 1.6.3
	 */
	public <R> R addR(int index, E value, R returnVal) {
		add(index, value);
		return returnVal;
	}
	
	/**
	 * Adds the given object to this list and then returns this list itself.
	 */
	public EArrayList<E> addRT(E e) {
		add(e);
		return this;
	}
	
	/**
	 * Adds the given object at the specified index and then returns this
	 * EArrayList.
	 * 
	 * @param index The index to add the object to
	 * @param value The object to add
	 * @return This EArrayList
	 * @since 1.6.3
	 */
	public EArrayList<E> addRT(int index, E value) {
		add(index, value);
		return this;
	}

	/**
	 * Adds the given value if and only if the given condition is true, then
	 * the result of the given condition is returned.
	 */
	public boolean addIf(boolean condition, E e) {
		if (condition) add(e);
		return condition;
	}
	
	/**
	 * Adds the given value if and only if the given condition is true, then
	 * the returnVal is returned.
	 */
	public <R> R addIfR(boolean condition, E e, R returnVal) {
		addIf(condition, e);
		return returnVal;
	}

	/**
	 * Adds each of the given values to this list if and only if the current
	 * value is not null.
	 */
	public void addIfNotNull(E... e) {
		for (E entry : e) {
			if (entry != null) add(entry);
		}
	}

	/** Adds the element(s) if they are not already present in this list. */
	public void addIfNotContains(E... e) { filterAdd(e, this::notContains); }
	
	/** Adds the element(s) if they are not already present in this list and only if they are not null. */
	public void addNullContains(E... e) { filterNullAdd(e, this::notContains); }

	/** Removes any values from this list which are not present in the given stream. */
	public EArrayList<E> reduceTo(Stream<E> s) {
		Objects.requireNonNull(s);
		List<E> l = s.collect(Collectors.toList());
		retainAll(l);
		return this;
	}

	//----------------
	// Remove Methods
	//----------------
	
	/**
	 * Removes all of the given object(s) from this list.
	 * 
	 * @param objects The object(s) to remove
	 * @since 1.5.2
	 */
	public void remove(E... objects) {
		for (E o : objects) remove(o);
	}
	
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
	public static <T> EArrayList<T> wrap(List<T> listIn) {
		return (listIn != null) ? new EArrayList(listIn) : null;
	}
	
	/** Collector implementation used to be able to convert a typed stream of data into an EArrayList of the same type. */
	public static <T> Collector<T, ?, EArrayList<T>> toEArrayList() {
		return new ECollector<>((Supplier<List<T>>) EArrayList::new, List::add, (left, right) -> { left.addAll(right); return left; }, CH_ID);
	}
	
	/** Returns a new EArrayList<T> created from values of the given typed array. */
	public static <T> EArrayList<T> of(T... vals) {
		return new EArrayList<T>().add(vals);
	}
	
	/** Returns a new EArrayList<T> created from values of the given list. */
	public static <T> EArrayList<T> of(EArrayList<T> in) {
		return new EArrayList(in);
	}
	
	public static <T> EArrayList<T> of(Collection<T> in) {
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