package eutil.datatypes.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import eutil.EUtil;
import eutil.datatypes.EArrayList;
import eutil.debug.PlannedForRefactor;
import eutil.random.ERandomUtil;

/**
 * An interface that wraps the functionality of enhanced lists.
 * 
 * @param <E> List type
 * 
 * @author Hunter Bragg
 * 
 * @since 2.0.0
 */
public interface EList<E> extends List<E>, Deque<E> {
	
	static <E> EList<E> newList() { return new EArrayList<>(); }
	static <E> EList<E> newList(E... objects) { return new EArrayList<>(objects); }
	static <E> EList<E> newList(Collection<E> collection) { return new EArrayList<>(collection); }
	static <E> EList<E> newList(List<E> list) { return new EArrayList<>(list); }
	static <E> EList<E> newList(EList<E> list) { return new EArrayList<>(list); }
	static <E> EList<E> newList(Stream<E> stream) { return new EArrayList<>(stream); }
	
	void ensureCapacity(int size);
	
	/**
	 * Returns true if each object specified is within this list. Returns false
	 * if given
	 * 
	 * @param objs
	 * @return true if contains all given
	 */
	default boolean containsEach(E... objs) {
		if (objs.length == 0) return false;
		for (int i = 0; i < objs.length; i++) { if (!contains(objs[i])) return false; }
		return true;
	}
	
	/**
	 * Returns true if any object within this list matches the given predicate.
	 */
	default boolean anyMatch(Predicate<? super E> condition) {
		return stream().anyMatch(condition);
	}
	
	/**
	 * If the given value is currently present within the list, the value is
	 * replaced, otherwise it is added.
	 */
	default E put(E val) {
		int i = indexOf(val);
		if (i >= 0) set(i, val);
		else add(val);
		return val;
	}
	
	/** Returns true if the given index is at the start of this list. */
	default boolean atStart(int i) {
		return i == 0;
	}
	
	/** Returns true if the given index is at the end of this list. */
	default boolean atEnd(int i) {
		return i == size() - 1;
	}
	
	/**
	 * Pushes the given value onto the front of this list and subsequently
	 * shifts each of the additional values to the right by one.
	 */
	@Override
	default void push(E value) {
		add(0, value);
	}
	
	/**
	 * Pushes and then returns the given value.
	 * 
	 * @param value The value to add
	 * 
	 * @return The value added
	 * 
	 * @since 1.6.3
	 */
	default E pushR(E value) {
		add(0, value);
		return value;
	}
	
	default EList<E> pushRT(E value) {
		push(value);
		return this;
	}
	
	/**
	 * Similar to 'pop' where the first element is removed and then returned
	 * but no 'EmptyStackException' is thrown if the list is empty.
	 * 
	 * @return The 'next' element at the beginning of the list
	 * 
	 * @since 1.6.3
	 */
	default E next() {
		if (isEmpty()) return null;
		return pop();
	}
	
	/**
	 * Removes the first value from this list then shifts each of the remaining
	 * values to the left by one.@return
	 */
	@Override
	default E pop() {
		if (size() == 0) throw new EmptyStackException();
		E val = removeFirst();
		return val;
	}
	
	/**
	 * Retrieves, but does not remove the top element of the array as if this
	 * were a stack.
	 */
	@Override
	default E peek() {
		if (size() == 0) throw new EmptyStackException();
		return get(0);
	}
	
	@Override
	default void addFirst(E e) {
		add(0, e);
	}
	
	@Override
	default void addLast(E e) {
		add(e);
	}
	
	@Override
	default boolean offerFirst(E e) {
		addFirst(e);
		return true;
	}
	
	@Override
	default boolean offerLast(E e) {
		addLast(e);
		return false;
	}
	
	@Override
	default boolean offer(E e) {
		return offerLast(e);
	}
	
	@Override
	default E pollFirst() {
		return removeFirst();
	}
	
	@Override
	default E pollLast() {
		return removeLast();
	}
	
	@Override
	default E poll() {
		return pollFirst();
	}
	
	@Override
	default E peekFirst() {
		if (isEmpty()) throw new EmptyStackException();
		return getFirst();
	}
	
	@Override
	default E peekLast() {
		if (isEmpty()) throw new EmptyStackException();
		return getLast();
	}
	
	@Override
	default E element() {
		return peek();
	}
	
	@Override
	default E remove() {
		return removeFirst();
	}
	
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
		EList<E> r = new EArrayList<>(this);
		for (int i = 0; i < amount; i++) { r.add(r.remove(0)); }
		return r;
	}
	
	default EList<E> shiftRight(int amount) {
		EList<E> r = new EArrayList<>(this);
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
	
	default E getRandom() {
		return (isEmpty()) ? null : get(ERandomUtil.getRoll(0, size() - 1));
	}
	
	default E removeRandom() {
		return (isEmpty()) ? null : remove(ERandomUtil.getRoll(0, size() - 1));
	}
	
	default E removeIfContains(E object) {
		int index = indexOf(object);
		if (index >= 0) return remove(index);
		else return null;
	}
	
	/** Swaps the values at the given indexes. */
	default EList<E> swap(int indexA, int indexB) {
		if (indexA < 0 || indexA >= size())
			throw new IndexOutOfBoundsException("Index: " + indexA + ", Size: " + size());
		if (indexB < 0 || indexA >= size())
			throw new IndexOutOfBoundsException("Index: " + indexB + ", Size: " + size());
		
		E temp = get(indexA);
		set(indexA, get(indexB));
		set(indexB, temp);
		
		return this;
	}
	
	/** Returns true if there is only a single element in this list. */
	default boolean hasOne() {
		return size() == 1;
	}
	
	/**
	 * Sets the entire range of this list to the given value. Note: this will
	 * overwrite existing values.
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
	@PlannedForRefactor(reason="This should produce a String that contains the 'printing' instead of printing it directly to sysout", since = "2.2.0")
	default EList<E> print() {
		return print(false);
	}
	
	/** Prints this list to the console with optional numbering. */
	@PlannedForRefactor(reason="This should produce a String that contains the 'printing' instead of printing it directly to sysout", since = "2.2.0")
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
	
	default EList<E> addThen(E valIn) {
		add(valIn);
		return this;
	}
	
	/**
	 * Same as size() but just adds a call to unify language across Strings,
	 * Arrays, and now lists.
	 */
	default int length() {
		return size();
	}
	
	/**
	 * Removes and returns the first element in this list. If the list is
	 * empty, null is returned instead.
	 */
	default E removeFirst() {
		return (size() > 0) ? remove(0) : null;
	}
	/**
	 * Removes and returns the last element in this list. If the list is empty,
	 * null is returned instead.
	 */
	default E removeLast() {
		return (size() > 0) ? remove(size() - 1) : null;
	}
	
	//=========
	// Filters
	//=========
	
	/**
	 * Returns a new WrapperList consisting of elements from this list reduced
	 * by the provided filter.
	 */
	default EList<E> filter(Predicate<? super E> filter) {
		return new EArrayList<>(EUtil.filter(this, filter));
	}
	
	/**
	 * Returns a new WrapperList consisting of elements from this list with
	 * null values removed.
	 */
	default EList<E> filterNull() {
		return new EArrayList<>(EUtil.filterNull(this));
	}
	
	/**
	 * Returns a new WrapperList consisting of elements from this list reduced
	 * by the provided filter with null elements also filtered out.
	 */
	default EList<E> filterNull(Predicate<? super E> filter) {
		return new EArrayList<>(EUtil.filterNull(this, filter));
	}
	
	/**
	 * Adds the elements from an existing array that match the given condition.
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
	 * Adds the elements from an existing array that match the given condition.
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
	
	//=========
	// Mappers
	//=========
	
	/**
	 * Returns a new List<T> formed by converting each of the existing
	 * elements in this list to the given new type.
	 */
	default <T> EList<T> map(Function<? super E, ? extends T> mapper) {
		return new EArrayList<>(stream().map(mapper));
	}
	
	//=================
	// ForEach Methods
	//=================
	
	/**
	 * Performs both a filtering operation as well as the given consumer action
	 * on the remaining elements.
	 */
	default void filterForEach(Predicate<? super E> filter, Consumer<? super E> action) {
		EUtil.filterForEach(this, filter, action);
	}
	
	/**
	 * Performs both a null filtering operation as well as the given consumer
	 * action on the remaining elements.
	 */
	default void filterNullForEach(Consumer<? super E> action) {
		EUtil.filterNullForEach(this, action);
	}
	
	/**
	 * Performs both the given filtering operation including a null filter as
	 * well as the given consumer action on the remaining elements.
	 */
	default void filterNullForEach(Predicate<? super E> filter, Consumer<? super E> action) {
		EUtil.filterNullForEach(this, filter, action);
	}
	
	/**
	 * Performs the given consumer action on the existing elements and then
	 * returns the given value.
	 */
	default <R> R forEachR(Consumer<? super E> action, R returnVal) {
		forEach(action);
		return returnVal;
	}
	
	/**
	 * Performs the given consumer action on the existing elements if the given
	 * condition is true, then returns the result of the condition.
	 */
	default boolean ifForEach(boolean check, Consumer<? super E> action) {
		return EUtil.ifForEach(check, this, action);
	}
	
	//============
	// Collectors
	//============
	
	/**
	 * Takes the contents of this list and filters them into another form using
	 * the given collector.
	 */
	default <R, A> R filterInto(Predicate<? super E> filter, Collector<? super E, A, R> collector) {
		return EUtil.filterInto(this, filter, collector);
	}
	
	/**
	 * Returns, but does not remove, the first element in this list. If this
	 * list is empty, null is returned instead.
	 */
	default E getFirst() {
		return (size() > 0) ? get(0) : null;
	}
	
	/**
	 * Returns, but does not remove, the last element in this list. If this
	 * list is empty, null is returned instead.
	 */
	default E getLast() {
		return (size() > 0) ? get(size() - 1) : null;
	}
	
	/** Returns true if there is at least one element in this list. */
	default boolean isNotEmpty() {
		return size() > 0;
	}
	
	/** Returns true if this list does not contain the given object. */
	default boolean notContains(Object o) {
		return !contains(o);
	}
	
	/**
	 * Returns the first element in this list that is an instance of the given
	 * class.
	 */
	default E getFirstInstanceOf(Class<?> cIn) {
		for (E e : this) { if (cIn.isInstance(e)) return (E) e; }
		return null;
	}
	
	/**
	 * Returns a list of elements in this list that are instances of the given
	 * class.
	 */
	default EList<E> getAllInstancesOf(Class<?> cIn) {
		EList<E> instances = EList.newList();
		for (E e : this) if (cIn.isInstance(e)) instances.add((E) e);
		return instances;
	}
	
	/**
	 * Removes and returns a list of elements in this list that are instances
	 * of the given class.
	 */
	default EList<E> removeAllInstancesOf(Class<?> cIn) {
		EList<E> toBeRemoved = EList.newList();
		for (E e : this) if (cIn.isInstance(e)) toBeRemoved.add((E) e);
		for (E e : toBeRemoved) remove(e);
		return this;
	}
	
	/**
	 * Returns true if any of the elements in this list are an instance of the
	 * given class.
	 */
	default boolean containsInstanceOf(Class<?> cIn) {
		for (E e : this) { if (cIn.isInstance(e)) return true; }
		return false;
	}
	
	/**
	 * Returns true if none of the elements in this list are an instance of the
	 * given class.
	 */
	default boolean containsNoInstanceOf(Class<?> cIn) {
		for (E e : this) { if (cIn.isInstance(e)) return false; }
		return true;
	}
	
	/**
	 * Returns an EArrayList with reversed elements from this one.
	 * 
	 * @since 1.6.0
	 */
	default EList<E> reverse() {
		return EList.reverse(this);
	}
	
	//=============
	// Add Methods
	//=============
	
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
	
	/**
	 * Clears the contents of this list and then adds all of the elements from
	 * the given collection.
	 * 
	 * @param c The collection to add
	 * 
	 * @return This list
	 * 
	 * @since 2.0.1
	 */
	default EList<E> clearThenAddAll(Collection<? extends E> c) {
		clear();
		addAll(c);
		return this;
	}
	
	/**
	 * Adds each of the elements in the given array to this list.
	 */
	default EList<E> addA(E[] e) {
		for (E val : e) add(val);
		return this;
	}
	
	/**
	 * Adds the given object to this list then returns it.
	 */
	default E addR(E e) {
		add(e);
		return e;
	}
	
	/**
	 * Adds the given object to this list then returns the given returnVal.
	 */
	default <R> R addR(E e, R returnVal) {
		add(e);
		return returnVal;
	}
	
	/**
	 * Adds the given object at the specified index and then returns the
	 * object.
	 * 
	 * @param index The index to add the object to
	 * @param value The object to add
	 * 
	 * @return The object being added
	 * 
	 * @since 1.6.3
	 */
	default E addR(int index, E value) {
		add(value);
		return value;
	}
	
	/**
	 * Adds the given object at the specified index and then returns the given
	 * 'returnVal'.
	 * 
	 * @param <R>       The type of object being returned
	 * @param index     The index to add the object to
	 * @param value     The object to add
	 * @param returnVal The object to return
	 * 
	 * @return The given 'returnVal'
	 * 
	 * @since 1.6.3
	 */
	default <R> R addR(int index, E value, R returnVal) {
		add(index, value);
		return returnVal;
	}
	
	/**
	 * Adds the given object to this list and then returns this list itself.
	 */
	default EList<E> addRT(E e) {
		add(e);
		return this;
	}
	
	/**
	 * Adds the given object at the specified index and then returns this
	 * EArrayList.
	 * 
	 * @param index The index to add the object to
	 * @param value The object to add
	 * 
	 * @return This EArrayList
	 * 
	 * @since 1.6.3
	 */
	default EList<E> addRT(int index, E value) {
		add(index, value);
		return this;
	}
	
	/**
	 * Adds the given value if and only if the given condition is true, then
	 * the result of the given condition is returned.
	 */
	default boolean addIf(boolean condition, E e) {
		if (condition) add(e);
		return condition;
	}
	
	/**
	 * Adds the given value if and only if the given condition is true, then
	 * the returnVal is returned.
	 */
	default <R> R addIfR(boolean condition, E e, R returnVal) {
		addIf(condition, e);
		return returnVal;
	}
	
	/**
	 * Adds each of the given values to this list if and only if the current
	 * value is not null.
	 */
	default void addIfNotNull(E... e) {
		for (E entry : e) { if (entry != null) add(entry); }
	}
	
	/** Adds the element(s) if they are not already present in this list. */
	default void addIfNotContains(E... e) {
		filterAdd(e, this::notContains);
	}
	
	/**
	 * Adds the element(s) if they are not already present in this list and
	 * only if they are not null.
	 */
	default void addNullContains(E... e) {
		filterNullAdd(e, this::notContains);
	}
	
	/**
	 * Removes any values from this list which are not present in the given
	 * stream.
	 */
	default EList<E> reduceTo(Stream<E> s) {
		Objects.requireNonNull(s);
		List<E> l = s.collect(Collectors.toList());
		retainAll(l);
		return this;
	}
	
	//================
	// Remove Methods
	//================
	
	/**
	 * Removes all of the given object(s) from this list.
	 * 
	 * @param objects The object(s) to remove
	 * 
	 * @since 1.5.2
	 */
	default void remove(E... objects) {
		for (E o : objects) remove(o);
	}
	
	//================
	// Helper Methods
	//================
	
	/**
	 * Creates a new list containing shallow copies of the contents of this
	 * list and then returns it.
	 * 
	 * @return A shallow copy of this list
	 * @since 2.3.5
	 */
	default EList<E> copy() {
		return EList.of(this);
	}
	
	/**
	 * Wraps this list into a new unmodifiable list instance and returns it.
	 * 
	 * @return An unmodifiable version of this list
	 * @since 2.3.5
	 */
	default EList<E> toUnmodifiableList() {
		return EList.unmodifiableList(this);
	}
	
	/**
	 * Up-casts this EList as a standard Java Collections List.
	 * <p>
	 * Effectively, the returned object is still an EList at its base, but it
	 * is just presented as a Java Collections List.
	 * 
	 * @return A List version of this EList.
	 */
	default List<E> toList() {
		return (List<E>) this;
	}
	
	/**
	 * Takes the values of this EList and places them in a set.
	 * 
	 * @return A hash set containing the elements of this list
	 * @since 2.3.5
	 */
	default Set<E> toSet() {
		return new HashSet<E>(this);
	}
	
	//================
	// Static Methods
	//================
	
	/**
	 * Returns a new typed WrapperList formed from combining each of the given
	 * lists. This works by first adding all of the elements from list A, then
	 * adds all of the elements in list B.
	 */
	static <E> EList<E> combineLists(List<E> a, List<E> b) {
		EList<E> l = new EArrayList<>();
		if (a != null) l.addAll(a);
		if (b != null) l.addAll(b);
		return l;
	}
	
	/**
	 * Returns a new typed WrapperList formed by interweaving values from both
	 * of the given lists. Values will continue to be interwoven until one list
	 * is empty. Once this happens, remaining values from the list with more
	 * values will be added until it is empty.
	 */
	static <E> EList<E> weaveLists(List<E> a, List<E> b) {
		EList<E> l = new EArrayList<>();
		
		int i = 0;
		for (; i < a.size() && i < b.size(); i++) {
			l.add(a.get(i));
			l.add(b.get(i));
		}
		while (i < a.size()) { l.add(a.get(i++)); }
		while (i < b.size()) { l.add(b.get(i++)); }
		
		return l;
	}
	
	static <E> EList<E> reverse(EList<E> listIn) {
		EList<E> r = new EArrayList<>(listIn.size());
		for (int i = listIn.size() - 1; i >= 0; i--) { r.add(listIn.get(i)); }
		return r;
	}
	
	/**
	 * Does not create a new internal list but instead wraps EArrayList
	 * functionality around the given one.
	 */
	static <T> EList<T> wrap(List<T> listIn) {
		return new EArrayList<>(listIn);
	}
	
	/**
	 * Returns an empty unmodifiable EList.
	 * 
	 * @param <T> The list type
	 * 
	 * @return An unmodifiable, empty EList.
	 * 
	 * @since 2.3.2
	 */
	static <T> EList<T> emptyUnmodifiableList() {
		return unmodifiableList(EList.newList());
	}
	
	/**
	 * Returns an unmodifiable view of the given list instance wrapped in an
	 * EList shell.
	 * 
	 * @param <T>    The list type
	 * @param listIn The list to wrap
	 * 
	 * @return An unmodifiable version of the given list in an EList form
	 * 
	 * @since 2.3.2
	 */
	static <T> EList<T> unmodifiableList(List<T> listIn) {
		return wrap(Collections.unmodifiableList(listIn));
	}
	
	/**
	 * Returns a new EArrayList<T> created from values of the given typed
	 * array.
	 */
	static <T> EList<T> of(T... vals) {
		return new EArrayList<T>(vals);
	}
	
	/** Returns a new EArrayList<T> created from values of the given list. */
	static <T> EList<T> of(EArrayList<T> in) {
		return new EArrayList<>(in);
	}
	
	static <T> EList<T> of(Collection<T> in) {
		return new EArrayList<>(in);
	}

	/** Collector implementation used to be able to convert a typed stream of data into an EArrayList of the same type. */
	public static <T> Collector<T, ?, EArrayList<T>> toEList() {
		return new ECollector<>((Supplier<List<T>>) EArrayList::new,
								List::add, (left, right) -> { left.addAll(right); return left; },
								Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH)));
	}
	
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
