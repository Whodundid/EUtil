package eutil.datatypes.util;

import eutil.datatypes.Box2;
import eutil.datatypes.EArrayList;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

/**
 * An ArrayList wrapper which manages standard two item Boxes.
 * 
 * <p>This should not be used in place of hashmaps due to the time
 * complexity of each operation being O(n) at best.
 * 
 * @param <A> The first type
 * @param <B> The second type
 * 
 * @author Hunter Bragg
 * @since 1.0.0
 */
public class BoxList<A, B> implements Iterable<Box2<A, B>> {
	
	private final EArrayList<Box2<A, B>> createdList = new EArrayList();
	public boolean allowDuplicates = false;
	
	//--------------
	// Constructors
	//--------------
	
	/** Creates a new BoxList that does allow duplicate object entries. */
	public BoxList() { this(true); }
	
	/** Creates a new BoxList in which duplicates can be allowed or not. */
	public BoxList(boolean allowDuplicatesIn) {
		allowDuplicates = allowDuplicatesIn;
	}
	
	public BoxList(A obj, B val) {
		this(true);
		add(obj, val);
	}
	
	public BoxList(boolean allowDuplicatesIn, A obj, B val) {
		this(allowDuplicatesIn);
		add(obj, val);
	}
	
	public BoxList(BoxList<A, B> holderIn) {
		this(true);
		holderIn.forEach(s -> add(s.getA(), s.getB()));
	}
	
	//-----------
	// Overrides
	//-----------
	
	@Override
	public Iterator<Box2<A, B>> iterator() {
		return createdList.iterator();
	}
	
	@Override
	public String toString() {
		String returnString = "[";
		for (int i = 0; i < createdList.size(); i++) {
			returnString += ("(" + getA(i) + ", " + getB(i) + (i == createdList.size() - 1 ? ")" : "), "));
		}
		returnString += "]";
		return returnString;
	}
	
	//---------
	// Methods
	//---------
	
	public Stream<Box2<A, B>> stream() { return createdList.stream(); }
	
	public BoxList<A, B> sort() { return stream().sorted().collect(toBoxHolder()); }
	
	public <T> BoxList<A, B> sort(Comparator<? super Box2<A, B>> comparator) {
		return stream().sorted(comparator).collect(toBoxHolder());
	}
	
	/** Returns the total number of boxes in this holder. */
	public int size() { return createdList.size(); }
	/** Same as size() but just adds a call to unify language across Strings, Arrays, and now lists. */
	public int length() { return createdList.size(); }
	/** Returns true if this holder does not contain any boxes. */
	public boolean isEmpty() { return createdList.isEmpty(); }
	/** Returns true if this holder does contain boxes. */
	public boolean isNotEmpty() { return !createdList.isEmpty(); }
	/** Removes every box from this holder. */
	public void clear() { createdList.clear(); }
	
	/** Sets this box to not have duplicates and procedes to purge any and all duplicates from this holder. */
	public BoxList<A, B> noDuplicates() { allowDuplicates = false; purgeDuplicates(this); return this; }
	/** Sets this box to have duplicates or not. If no, all duplicates are purged from this holder. */
	public BoxList<A, B> setAllowDuplicates(boolean val) { allowDuplicates = val; if (!allowDuplicates) { purgeDuplicates(this); } return this; }
	
	/** Returns true if this holder has any box with the specified A value. */
	public boolean contains(A obj) {
		for (Box2<A, B> getBox : createdList) {
			if (getBox.containsA(obj)) return true;
		}
		return false;
	}
	
	public boolean contains(Box2<A, B> boxIn) {
		for (Box2<A, B> b : createdList) {
			if (b.compare(boxIn)) return true;
		}
		return false;
	}
	
	/** Returns true if this holder has any box with both the specified A and B pair. */
	public boolean containsBoth(A obj1, B obj2) {
		for (Box2<A, B> getBox : createdList) {
			if (getBox.compare(obj1, obj2)) return true;
		}
		return false;
	}
	
	//------------------
	// Methods : Adders
	//------------------
	
	public boolean add() { return add(null, null); }
	/** Creates a new StorageBox with the given A and B values and then adds it to the specified position of this holder. */
	public void add(int pos, A obj, B value) {
		if (allowDuplicates || !contains(obj)) createdList.add(pos, new Box2<A, B>(obj, value));
	}
	
	/** Creates a new StorageBox with the given A and B values and then adds it to the end of this holder. */
	public boolean add(A obj, B value) {
		return (allowDuplicates || !contains(obj)) ? createdList.add(new Box2<A, B>(obj, value)) : false;
	}
	/** Adds the specified box if it is not null to this BoxList. */
	public boolean add(Box2<A, B> boxIn) {
		return (boxIn != null && (allowDuplicates || !contains(boxIn))) ? createdList.add(boxIn) : false;
	}
	
	/** Creates a new StorageBox with the given A and B values and then adds it to the end of this holder. */
	public <R> R addR(A obj, B value, R returnVal) {
		if (allowDuplicates || !contains(obj)) createdList.add(new Box2<A, B>(obj, value));
		return returnVal;
	}
	/** Adds the specified box if it is not null to this BoxList. */
	public <R> R addR(Box2<A, B> boxIn, R returnVal) {
		if (boxIn != null && (allowDuplicates || !contains(boxIn))) createdList.add(boxIn);
		return returnVal;
	}
	
	/** Creates a new StorageBox with the given A and B values and then adds it to the end of this holder. */
	public BoxList<A, B> addRT(A obj, B value) {
		if (allowDuplicates || !contains(obj)) createdList.add(new Box2<A, B>(obj, value));
		return this;
	}
	/** Adds the specified box if it is not null to this BoxList. */
	public BoxList<A, B> addRT(Box2<A, B> boxIn) {
		if (boxIn != null && (allowDuplicates || !contains(boxIn))) createdList.add(boxIn);
		return this;
	}
	
	public boolean addIf(boolean condition, A obj, B value) { if (condition) return add(obj, value); return false; }
	public boolean addIf(boolean condition, Box2<A, B> boxIn) { if (condition) return add(boxIn); return false; }
	
	public BoxList<A, B> addAll(List<A> a, List<B> b) {
		if (a.size() == b.size()) {
			for (int i = 0; i < a.size(); i++) {
				add(a.get(i), b.get(i));
			}
			return this;
		}
		throw new RuntimeException("Sizes do not match!");
	}
	
	/** Adds all the boxes from one BoxList into this one. */
	public BoxList<A, B> addAll(BoxList<A, B> in) {
		if (in != null) in.forEach(b -> add(b));
		return this;
	}
	/** Adds all of the boxes from a list into this BoxList. */
	public BoxList<A, B> addAll(List<Box2<A, B>> listIn) {
		if (listIn != null) listIn.forEach(b -> add(b));
		return this;
	}
	
	/** Adds if the object does not already exist, or updates the B value of the existing box with the given A value. */
	public void put(A obj, B value) {
		Box2<A, B> box = getBoxWithA(obj);
		if (box != null) box.setB(value);
		else add(obj, value);
	}
	
	//--------------------
	// Methods : Removers
	//--------------------
	
	/** Removes the box at the specified point number. */
	public boolean remove(int pointNumber) { return createdList.remove(pointNumber) != null; }
	
	/** Removes every box that contains the given A value. */
	public List<Box2<A, B>> removeBoxesContainingA(A obj) {
		List<Box2<A, B>> returnList = new EArrayList();
		Iterator<Box2<A, B>> i = createdList.iterator();
		while (i.hasNext()) {
			Box2<A, B> getBox = i.next();
			if (getBox.contains(obj)) {
				returnList.add(getBox);
				i.remove();
			}
		}
		return returnList;
	}
	
	/** Removes every box that has the exact A and B values. */
	public List<Box2<A, B>> removeBoxesWithSaidValues(A obj1, B obj2) {
		List<Box2<A, B>> returnList = new EArrayList();
		Iterator<Box2<A, B>> i = createdList.iterator();
		while (i.hasNext()) {
			Box2<A, B> getBox = i.next();
			if (getBox.compare(obj1, obj2)) {
				returnList.add(getBox);
				i.remove();
			}
		}
		return returnList;
	}
	
	//---------
	// Getters
	//---------
	
	/** Returns the box at the specified point number. */
	public Box2<A, B> get(int pointNumber) { return createdList.get(pointNumber); }
	/** Returns the object from the box at the specified point number. */
	public A getA(int pointNumber) { return createdList.get(pointNumber).getA(); }
	/** Returns the value from the box at the specified point number. */
	public B getB(int pointNumber) { return createdList.get(pointNumber).getB(); }
	
	/** Returns the 'B' value in a box with a given 'A' value. */
	public B get(A key) {
		Box2<A, B> box = getBoxWithA(key);
		return (box != null) ? box.getB() : null;
	}
	
	public Box2<A, B> getFirst() { return (isNotEmpty()) ? get(0) : null; }
	public A getFirstA() { return (isNotEmpty()) ? createdList.get(0).getA() : null; }
	public B getFirstB() { return (isNotEmpty()) ? createdList.get(0).getB() : null; }
	
	public Box2<A, B> getLast() { return (isNotEmpty()) ? get(size() - 1) : null; }
	public A getLastA() { return (isNotEmpty()) ? createdList.get(size() - 1).getA() : null; }
	public B getLastB() { return (isNotEmpty()) ? createdList.get(size() - 1).getB() : null; }
	
	/** Retrieves the first box that contains the specified A value. */
	public Box2<A, B> getBoxWithA(A objIn) {
		for (Box2<A, B> getBox : createdList) {
			if (getBox.containsA(objIn)) return getBox;
		}
		return null;
	}
	
	/** Returns the 'B' value in the first box which contains the given 'A' value. */
	public B getBFromBoxWithA(A objIn) {
		Box2<A, B> b = getBoxWithA(objIn);
		return (b != null) ? b.getB() : null;
	}
	
	/** Retrieves all boxes that contain the specified A value. */
	public EArrayList<Box2<A, B>> getAllBoxesWithA(A obj) { return createdList.filter(b -> b.getA().equals(obj)); }
	
	/** Returns a list of every A value in each box. */
	public EArrayList<A> getAVals() { return createdList.map(Box2::getA); }
	/** Returns a list of every B value in each box. */
	public EArrayList<B> getBVals() { return createdList.map(Box2::getB); }
	
	/** Returns a list containing every box in this holder. */
	public EArrayList<Box2<A, B>> getBoxes() { return new EArrayList<Box2<A, B>>(createdList); }
	
	/** Returns the boxes of this holder within an array of StorageBox objects with the corresponding parameters. */
	public Box2<A, B>[] getBoxesAsArray() {
		Box2<?, ?>[] arr = new Box2<?, ?>[createdList.size()];
		int i = 0;
		for (Box2<A, B> b : createdList) {
			arr[i++] = b;
		}
		return (Box2<A, B>[]) arr;
	}
	
	/** Returns a StorageBox in this holder that contains both A and B values. */
	public Box2<A, B> getBoxWithBoth(A a, B b) {
		for (Box2<A, B> box : createdList) {
			if (box.compare(a, b)) return box;
		}
		return null;
	}
	
	//---------
	// Setters
	//---------
	
	public Box2<A, B> setA(int index, A obj) { return createdList.get(index).setA(obj); }
	public Box2<A, B> setB(int index, B obj) { return createdList.get(index).setB(obj); }
	
	/** Replaces an in a box with the given A value with the specified new A value.
	 *  If the box does not exist, nothing is added and nothing is modified. */
	public BoxList<A, B> setAInBox(A obj, A newObj) {
		Box2<A, B> box = getBoxWithA(obj);
		if (box != null) box.setA(newObj);
		return this;
	}
	
	/** Replaces a value in a box with the given A value with the specified new B value.
	 *  If the box does not exist, nothing is added and nothing is modified. */
	public BoxList<A, B> setBInBox(A obj, B newVal) {
		Box2<A, B> box = getBoxWithA(obj);
		if (box != null) box.setB(newVal);
		return this;
	}
	
	//----------------
	// Static Methods
	//----------------
	
	/** Static method used to create a new BoxList parametized with the given object and value types for each list.
	 *  If values are to be passed, they must be passed in list objects, and each list must have the same size. If both lists
	 *  are null, an empty parametized holder with be returned. If one list is null and the other is not, nothing is returned. */
	public static <thing1, thing2> BoxList<thing1, thing2> createBox(List<thing1> objectsIn, List<thing2> valuesIn) {
		if (objectsIn != null && valuesIn != null) {
			if (objectsIn.size() == valuesIn.size()) {
				BoxList<thing1, thing2> newHolder = new BoxList();
				for (int i = 0; i < objectsIn.size(); i++) {
					newHolder.add(objectsIn.get(i), valuesIn.get(i));
				}
				return newHolder;
			}
		}
		else if (objectsIn == null && valuesIn == null) {
			return new BoxList<thing1, thing2>();
		}
		return null;
	}
	
	/** Static method used to create a new BoxList parametized with the given object and value types.
	 *  A variable sized list of argument is passed to initialize the values of this holder. For every argument
	 *  passed, a corresponding value must also be passed along with it so that is adheres to the <Object, Value>
	 *  relationship. */
	public static <T, V> BoxList<T, V> of(Class<T> tType, Class<V> vType, Object... dataIn) {
		if (dataIn.length % 2 == 0) {
			BoxList<T, V> newHolder = new BoxList();
			for (int i = 0; i < dataIn.length; i += 2) {
				newHolder.add((T) dataIn[i], (V) dataIn[i + 1]);
			}
			return newHolder;
		}
		return null;
	}
	
	/** Collector implementation used to be able to convert a typed stream of data into an EArrayList of the same type. */
	public static <X, Y> Collector<Box2<X, Y>, ?, BoxList<X, Y>> toBoxHolder() {
		return Collector.of((Supplier<BoxList<X, Y>>) BoxList::new, BoxList::add, (left, right) -> { left.addAll(right); return left; });
	}
	
	//------------------------
	// Private Static Methods
	//------------------------
	
	/** Internal function used to remove duplicates from a specified holder. */
	private static void purgeDuplicates(BoxList holderIn) {
		if (holderIn != null) {
			EArrayList<Box2> noDups = new EArrayList();
			Iterator<Box2> it = holderIn.iterator();
			
			while (it.hasNext()) {
				Box2 box = it.next();
				if (box != null) {
					boolean contains = false;
					for (Box2 b : noDups) {
						if (Box2.compare(box, b)) {
							contains = true;
							break;
						}
					}
					if (!contains) noDups.add(box);
				}
				
				it.remove();
			}
			
			holderIn.addAll(noDups);
		}
	}
	
	//------------------
	// Internal Classes
	//------------------
	
	static class BoxCollector<T, A, R> implements Collector<T, A, R> {
		
		private static <I, R> Function<I, R> castingIdentity() { return i -> (R) i; }
		
		private final Supplier<A> supplier;
		private final BiConsumer<A, T> accumulator;
		private final BinaryOperator<A> combiner;
		private final Function<A, R> finisher;
		private final Set<Characteristics> characteristics;

		BoxCollector(Supplier<A> supplierIn, BiConsumer<A, T> accumulatorIn, BinaryOperator<A> combinerIn, Function<A, R> finisherIn) {
			supplier = supplierIn;
			accumulator = accumulatorIn;
			combiner = combinerIn;
			finisher = finisherIn;
			characteristics = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
		}

		BoxCollector(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner) {
			this(supplier, accumulator, combiner, castingIdentity());
		}

		@Override public BiConsumer<A, T> accumulator() { return accumulator; }
		@Override public Supplier<A> supplier() { return supplier; }
		@Override public BinaryOperator<A> combiner() { return combiner; }
		@Override public Function<A, R> finisher() { return finisher; }
		@Override public Set<Characteristics> characteristics() { return characteristics; }
	}
	
}
