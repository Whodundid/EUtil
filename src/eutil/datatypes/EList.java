package eutil.datatypes;

import java.util.Collection;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eutil.EUtil;
import eutil.random.RandomUtil;

/**
 * An extension of List and Deque that provides a wide variety of
 * additional function and utility methods.
 * 
 * @param <E> The type of elements in this list
 * 
 * @author Hunter Bragg
 */
public interface EList<E> extends List<E>, Deque<E> {
	
	//----------------------
	// Overrides : Deque<E>
	//----------------------
	
	/**
	 * Removes the first value from this list then shifts each of the
	 * remaining values to the left by one.@return
	 */
	@Override
	default E pop() {
		E val = removeFirst();
		if (val == null) throw new EmptyStackException();
		return val;
	}
	
	/**
	 * Retrieves, but does not remove the top element of the array as if
	 * this were a stack.
	 */
	@Override
	default E peek() {
		if (size() == 0) throw new EmptyStackException();
		return get(0);
	}
	
	@Override default void push(E value) { add(0, value); }
	
	@Override default void addFirst(E e) { add(0, e); }
	@Override default void addLast(E e) { add(size() - 1, e); }
	
	@Override default boolean offerFirst(E e) { addFirst(e); return true; }
	@Override default boolean offerLast(E e) { addLast(e); return false; }
	@Override default boolean offer(E e) { return offerLast(e); }
	
	@Override default E pollFirst() { return removeFirst(); }
	@Override default E pollLast() { return removeLast(); }
	@Override default E poll() { return pollFirst(); }
	
	@Override default E peekFirst() { return null; }
	@Override default E peekLast() { return null; }
	@Override default E element() { return peek(); }
	
	@Override default E remove() { return removeFirst(); }
	
	@Override
	default boolean removeFirstOccurrence(Object o) {
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
	default boolean removeLastOccurrence(Object o) {
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
	
	@Override
	default Iterator<E> descendingIterator() {
		return iterator();
	}
	
	//--------------------
	// EList Base Methods
	//--------------------
	
	/** Retrieves the underlying List that backs this EList object. */
	public List<E> getWrappedList();
	
	/** Sets the underlying List that backs this EList object. */
	public void setWrappedList(List<E> listIn);
	
	//-----------------
	// Wrapper Methods
	//-----------------
	
	/**
	 * Returns true if each object specified is within this list.
	 * Returns false if given 
	 * 
	 * @param objs
	 * @return
	 */
	default boolean containsEach(E... objs) {
		if (objs.length == 0) return false;
		for (int i = 0; i < objs.length; i++) {
			if (!contains(objs[i])) return false;
		}
		return true;
	}
	
	/** Performs a size reduction on this list by cutting off all values after the given size. */
	default EList<E> trimToSize(int sizeIn) {
		setWrappedList(subList(0, sizeIn));
		return this;
	}
	
	/** If the given value is currently present within the list, the value is replaced, otherwise it is added. */
	default E put(E val) {
		int i = indexOf(val);
		if (i >= 0) set(i, val);
		else add(val);
		return val;
	}
	
	/** Returns true if any object within this list matches the given predicate. */
	default boolean anyMatch(Predicate<? super E> condition) {
		return stream().anyMatch(condition);
	}
	
	/** Returns true if the given index is at the start of this list. */
	default boolean atStart(int i) { return i == 0; }
	
	/** Returns true if the given index is at the end of this list. */
	default boolean atEnd(int i) { return i == size() - 1; }
	
	//---------
	// Methods
	//---------
	
	/**
	 * Same as size() but just adds a call to unify language across
	 * Strings, Arrays, and now lists.
	 */
	default int length() { return size(); }
	
	/** Returns true if there is at least one element in this list. */
	default boolean isNotEmpty() { return size() > 0; }
	
	/** Returns true if there is only a single element in this list. */
	default boolean hasOne() { return size() == 1; }
	
	/** Returns true if this list does not contain the given object. */
	default boolean notContains(Object o) { return !contains(o); }
	
	default E getRandom() {
		return (isEmpty()) ? null : get(RandomUtil.getRoll(0, size() - 1));
	}
	
	default E removeRandom() {
		return (isEmpty()) ? null : remove(RandomUtil.getRoll(0, size() - 1));
	}
	
	default E removeIfContains(E object) {
		int index = indexOf(object);
		if (index >= 0) return remove(index);
		return null;
	}
	
	/** Swaps the values at the given indexes. */
	default EList<E> swap(int indexA, int indexB) {
		if (indexA < 0 || indexA >= size()) {
			throw new IndexOutOfBoundsException("Index: " + indexA + ", Size: " + size());
		}
		if (indexB < 0 || indexA >= size()) {
			throw new IndexOutOfBoundsException("Index: " + indexB + ", Size: " + size());
		}
		
		E temp = get(indexA);
		set(indexA, get(indexB));
		set(indexB, temp);
		
		return this;
	}
	
	/** Returns a new list with reversed contents of this list. */
	default EList<E> flip() {
		EArrayList<E> l = new EArrayList();
		for (int i = size() - 1; i >= 0; i--) { l.add(get(i)); }
		return l;
	}
	
	/** Replaces a range of values in this list with the given value. */
	default EList<E> replaceFrom(int from, E value) {
		return replaceFrom(from, size(), value);
	}
	/** Replaces a range of values in this list with the given value. */
	default EList<E> replaceFrom(int from, int to, E value) {
		if (from < 0) return this;
		if (to >= size()) return this;
		set(from, value);
		for (int i = from + 1; i < to; i++) { remove(from + 1); }
		return this;
	}
	
	/** Removes a range of values from this list. */
	default EList<E> removeFrom(int from) {
		return removeFrom(from, size());
	}
	/** Removes a range of values from this list. */
	default EList<E> removeFrom(int from, int to) {
		if (from >= 0) { for (int i = from; i < to; i++) { remove(from); } }
		return this;
	}
	
	default EList<E> shiftLeft(int amount) {
		EArrayList<E> r = new EArrayList(this);
		for (int i = 0; i < amount; i++) { r.add(r.remove(0)); }
		return r;
	}
	
	default EList<E> shiftRight(int amount) {
		EArrayList<E> r = new EArrayList(this);
		for (int i = 0; i < amount; i++) { r.add(0, r.removeLast()); }
		return r;
	}
	
	/** Adds a range of values from a different list into this one. */
	default EList<E> addFrom(List<E> in, int from) {
		return addFrom(in, from, (in != null) ? in.size() : -1);
	}
	/** Adds a range of values from a different list into this one. */
	default EList<E> addFrom(List<E> in, int from, int to) {
		if (in != null && from >= 0) { for (int i = from; i < to; i++) { add(in.get(i)); } }
		return this;
	}
	
	/**
	 * Sets the entire range of this list to the given value. Note: this
	 * will overwrite existing values.
	 */
	default EList<E> fillWith(E val) {
		for (int i = 0; i < size(); i++) { set(i, val); }
		return this;
	}
	
	/**
	 * Returns true if any of the elements in this list match the given
	 * condition.
	 */
	default boolean containsProperty(Predicate<? super E> condition) {
		for (int i = 0; i < size(); i++) { if (condition.test(get(i))) { return true; } }
		return false;
	}
	
	/** Prints this list to the console. */
	default EList<E> print() {
		return print(false);
	}
	/** Prints this list to the console with optional numbering. */
	default EList<E> print(boolean numbering) {
		for (int i = 0; i < size(); i++) { System.out.println(((numbering) ? i + ": " : "") + get(i)); }
		return this;
	}
	
	/** Removes all null values from this list. */
	default EList<E> purgeNulls() {
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
	
	default E getFirst(Predicate<? super E> condition) {
		for (int i = 0; i < size(); i++) {
			E e = get(i);
			if (condition.test(e)) return e;
		}
		return null;
	}
	
	default E getLast(Predicate<? super E> condition) {
		for (int i = size() - 1; i >= 0; i--) {
			E e = get(i);
			if (condition.test(e)) return e;
		}
		return null;
	}
	
	default E removeFirst(Predicate<? super E> condition) {
		for (int i = 0; i < size(); i++) {
			E e = remove(i);
			if (condition.test(e)) return e;
		}
		return null;
	}
	
	default E removeLast(Predicate<? super E> condition) {
		for (int i = size() - 1; i >= 0; i--) {
			E e = remove(i);
			if (condition.test(e)) return e;
		}
		return null;
	}
	
	/**
	 * Removes and returns the first element in this list. If the list is
	 * empty, null is returned instead.
	 */
	default E removeFirst() {
		return (size() > 0) ? remove(0) : null;
	}
	/**
	 * Removes and returns the last element in this list. If the list is
	 * empty, null is returned instead.
	 */
	default E removeLast() {
		return (size() > 0) ? remove(size() - 1) : null;
	}
	
	//---------
	// Filters
	//---------
	
	/**
	 * Returns a new WrapperList consisting of elements from this list
	 * reduced by the provided filter.
	 */
	default EList<E> filter(Predicate<? super E> filter) {
		return new EArrayList<E>(EUtil.filter(this, filter));
	}
	/**
	 * Returns a new WrapperList consisting of elements from this list
	 * with null values removed.
	 */
	default EList<E> filterNull() {
		return new EArrayList<E>(EUtil.filterNull(this));
	}
	/**
	 * Returns a new WrapperList consisting of elements from this list
	 * reduced by the provided filter with null elements also filtered
	 * out.
	 */
	default EList<E> filterNull(Predicate<? super E> filter) {
		return new EArrayList<E>(EUtil.filterNull(this, filter));
	}
	/**
	 * Adds the elements from an existing array that match the given
	 * condition.
	 */
	default EList<E> filterAdd(E[] arr, Predicate<? super E> condition) {
		EUtil.filterForEach(arr, condition, this::add);
		return this;
	}
	/**
	 * Adds the elements from an existing collection that match the given
	 * condition.
	 */
	default EList<E> filterAdd(Collection<? extends E> c, Predicate<? super E> condition) {
		EUtil.filterForEach(c, condition, this::add);
		return this;
	}
	/**
	 * Adds the elements from an existing array that match the given
	 * condition.
	 */
	default EList<E> filterNullAdd(E[] arr, Predicate<? super E> condition) {
		EUtil.filterNullForEach(arr, condition, this::add);
		return this;
	}
	/**
	 * Adds the elements from an existing collection that match the given
	 * condition.
	 */
	default EList<E> filterNullAdd(Collection<? extends E> c, Predicate<? super E> condition) {
		EUtil.filterNullForEach(c, condition, this::add);
		return this;
	}
	
	//---------
	// Mappers
	//---------
	
	/**
	 * Returns a new WrapperList<T> formed by converting each of the
	 * existing elements in this list to the given new type.
	 */
	default <T> EList<T> map(Function<? super E, ? extends T> mapper) {
		return new EArrayList<T>(stream().map(mapper));
	}
	
	//-----------------
	// ForEach Methods
	//-----------------
	
	/**
	 * Performs both a filtering operation as well as the given consumer
	 * action on the remaining elements.
	 */
	default void filterForEach(Predicate<? super E> filter, Consumer<? super E> action) {
		EUtil.filterForEach(this, filter, action);
	}
	/**
	 * Performs both a null filtering operation as well as the given
	 * consumer action on the remaining elements.
	 */
	default void filterNullForEach(Consumer<? super E> action) {
		EUtil.filterNullForEach(this, action);
	}
	/**
	 * Performs both the given filtering operation including a null filter
	 * as well as the given consumer action on the remaining elements.
	 */
	default void filterNullForEach(Predicate<? super E> filter, Consumer<? super E> action) {
		EUtil.filterNullForEach(this, filter, action);
	}
	/**
	 * Performs the given consumer action on the existing elements and
	 * then returns the given value.
	 */
	default <R> R forEachR(Consumer<? super E> action, R returnVal) {
		forEach(action);
		return returnVal;
	}
	/**
	 * Performs the given consumer action on the existing elements if the
	 * given condition is true, then returns the result of the condition.
	 */
	default boolean ifForEach(boolean check, Consumer<? super E> action) {
		return EUtil.ifForEach(check, this, action);
	}
	
	//------------
	// Collectors
	//------------
	
	/**
	 * Takes the contents of this list and filters them into another form
	 * using the given collector.
	 */
	default <R, A> R filterInto(Predicate<? super E> filter, Collector<? super E, A, R> collector) {
		return EUtil.filterInto(this, filter, collector);
	}
	
	//---------
	// Getters
	//---------
	
	/**
	 * Returns, but does not remove, the first element in this list. If
	 * this list is empty, null is returned instead.
	 */
	default E getFirst() {
		return (size() > 0) ? get(0) : null;
	}
	/**
	 * Returns, but does not remove, the last element in this list. If
	 * this list is empty, null is returned instead.
	 */
	default E getLast() {
		return (size() > 0) ? get(size() - 1) : null;
	}
	
	/**
	 * Returns the first element in this list that is an instance of the
	 * given class.
	 */
	default E getFirstInstanceOf(Class<?> cIn) {
		for (Object e : this) { if (cIn.isInstance(e)) return (E) e; }
		return null;
	}
	
	/**
	 * Returns a list of elements in this list that are instances of the
	 * given class.
	 */
	default EArrayList<E> getAllInstancesOf(Class<?> cIn) {
		EArrayList<E> instances = new EArrayList();
		for (Object e : this) { if (cIn.isInstance(e)) instances.add((E) e); }
		return instances;
	}
	
	/**
	 * Removes and returns a list of elements in this list that are
	 * instances of the given class.
	 */
	default EList<E> removeAllInstancesOf(Class<?> cIn) {
		EArrayList<E> toBeRemoved = new EArrayList();
		for (Object e : this) { if (cIn.isInstance(e)) toBeRemoved.add((E) e); }
		for (E e : toBeRemoved) remove(e);
		return this;
	}
	
	/**
	 * Returns true if any of the elements in this list are an instance of
	 * the given class.
	 */
	default boolean containsInstanceOf(Class<?> cIn) {
		for (Object e : this) { if (cIn.isInstance(e)) return true; }
		return false;
	}
	
	/**
	 * Returns true if none of the elements in this list are an instance
	 * of the given class.
	 */
	default boolean containsNoInstanceOf(Class<?> cIn) {
		for (Object e : this) { if (cIn.isInstance(e)) return false; }
		return true;
	}
	
	//-------------
	// Add Methods
	//-------------
	
	default EList<E> pushRT(E value) {
		push(value);
		return this;
	}
	
	default EList<E> clearThenAdd(E... e) {
		clear();
		return add(e);
	}
	
	/** Adds each of the elements to this list. */
	default EList<E> add(E... e) {
		for (int i = 0; i < e.length; i++) add(e[i]);
		return this;
	}
	
	default EList<E> clearThenAddA(E[] e) {
		clear();
		return addA(e);
	}
	
	/** Adds each of the elements in the given array to this list. */
	default EList<E> addA(E[] e) {
		for (E val : e) add(val);
		return this;
	}
	
	/** Adds the given object to this list then returns it. */
	default E addR(E e) {
		add(e);
		return e;
	}
	
	/**
	 * Adds the given object to this list then returns the given
	 * returnVal.
	 */
	default <R> R addR(E e, R returnVal) {
		add(e);
		return returnVal;
	}
	
	/**
	 * Adds the given object to this list and then returns this list
	 * itself.
	 */
	default EList<E> addRT(E e) {
		add(e);
		return this;
	}
	
	/**
	 * Adds the given value if and only if the given condition is true,
	 * then the result of the given condition is returned.
	 */
	default boolean addIf(boolean condition, E e) {
		if (condition) add(e);
		return condition;
	}
	
	/**
	 * Adds the given value if and only if the given condition is true,
	 * then the returnVal is returned.
	 */
	default <R> R addIfR(boolean condition, E e, R returnVal) {
		addIf(condition, e);
		return returnVal;
	}
	
	/**
	 * Adds each of the given values to this list if and only if the
	 * current value is not null.
	 */
	default void addIfNotNull(E... e) {
		for (E entry : e) { if (entry != null) add(entry); }
	}
	
	/**
	 * Adds the element(s) if they are not already present in this list.
	 */
	default void addIfNotContains(E... e) {
		filterAdd(e, this::notContains);
	}
	
	/**
	 * Adds the element(s) if they are not already present in this list
	 * and only if they are not null.
	 */
	default void addNullContains(E... e) {
		filterNullAdd(e, this::notContains);
	}
	
	/**
	 * Removes any values from this list which are not present in the
	 * given stream.
	 */
	default EList<E> reduceTo(Stream<E> s) {
		retainAll(Objects.requireNonNull(s).collect(Collectors.toList()));
		return this;
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/**
	 * Returns a new typed WrapperList formed from combining each of the
	 * given lists. This works by first adding all of the elements from
	 * list A, then adds all of the elements in list B.
	 */
	public static <E> EArrayList<E> combineLists(List<E> a, List<E> b) {
		EArrayList<E> l = new EArrayList<>();
		if (a != null) l.addAll(a);
		if (b != null) l.addAll(b);
		return l;
	}
	
	/**
	 * Returns a new typed WrapperList formed by interweaving values from
	 * both of the given lists. Values will continue to be interwoven
	 * until one list is empty. Once this happens, remaining values from
	 * the list with more values will be added until it is empty.
	 */
	public static <E> EArrayList<E> weaveLists(List<E> a, List<E> b) {
		EArrayList<E> l = new EArrayList<>();
		
		int i = 0;
		for (; i < a.size() && i < b.size(); i++) {
			l.add(a.get(i));
			l.add(b.get(i));
		}
		while (i < a.size()) { l.add(a.get(i++)); }
		while (i < b.size()) { l.add(b.get(i++)); }
		
		return l;
	}
	
}
