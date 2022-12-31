package eutil.datatypes;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collector;
import java.util.stream.Stream;

import eutil.datatypes.util.EList;

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
public class BoxList<A, B> implements EList<Box2<A, B>> {
	
	private final EArrayList<Box2<A, B>> createdList = new EArrayList<>();
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
	
	public BoxList(A a, B b) {
		this(true);
		add(a, b);
	}
	
	public BoxList(boolean allowDuplicatesIn, A a, B b) {
		this(allowDuplicatesIn);
		add(a, b);
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
		var r = new StringBuilder("[");
		for (int i = 0; i < createdList.size(); i++) {
			r.append("(" + getA(i) + ", " + getB(i) + (i == createdList.size() - 1 ? ")" : "), "));
		}
		r.append("]");
		return r.toString();
	}
	
	//---------
	// Methods
	//---------
	
	public Stream<Box2<A, B>> stream() { return createdList.stream(); }
	
	public BoxList<A, B> sort() { return stream().sorted().collect(toBoxHolder()); }
	
	public <T> BoxList<A, B> sortAll(Comparator<? super Box2<A, B>> comparator) {
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
	
	/** Sets this box to not have duplicates and proceeds to purge any and all duplicates from this holder. */
	public BoxList<A, B> noDuplicates() {
		allowDuplicates = false;
		purgeDuplicates(this);
		return this;
	}
	
	/** Sets this box to have duplicates or not. If no, all duplicates are purged from this holder. */
	public BoxList<A, B> setAllowDuplicates(boolean val) {
		allowDuplicates = val;
		if (!allowDuplicates) purgeDuplicates(this);
		return this;
	}
	
	/** Returns true if this holder has any box with the specified A value. */
	public boolean containsA(A a) {
		for (Box2<A, B> getBox : createdList) {
			if (getBox.containsA(a)) return true;
		}
		return false;
	}
	
	/** Returns true if this holder has any box with the specified B value. */
	public boolean containsB(B b) {
		for (Box2<A, B> getBox : createdList) {
			if (getBox.containsB(b)) return true;
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
	public boolean containsBoth(A a, B b) {
		for (Box2<A, B> getBox : createdList) {
			if (getBox.compare(a, b)) return true;
		}
		return false;
	}
	
	//------------------
	// Methods : Adders
	//------------------
	
	public boolean add() { return add(null, null); }
	
	/** Creates a new StorageBox with the given A and B values and then adds it to the end of this holder. */
	public boolean add(A a, B b) {
		return (allowDuplicates || !contains(a)) ? createdList.add(new Box2<A, B>(a, b)) : false;
	}
	
	/** Creates a new StorageBox with the given A and B values and then adds it to the specified position of this holder. */
	public void add(int pos, A a, B b) {
		if (allowDuplicates || !contains(a)) createdList.add(pos, new Box2<A, B>(a, b));
	}

	/** Adds the specified box if it is not null to this BoxList. */
	public boolean add(Box2<A, B> boxIn) {
		return (boxIn != null && (allowDuplicates || !contains(boxIn))) ? createdList.add(boxIn) : false;
	}
	
	/** Creates a new StorageBox with the given A and B values and then adds it to the end of this holder. */
	public <R> R addR(A a, B b, R returnVal) {
		if (allowDuplicates || !contains(a)) createdList.add(new Box2<A, B>(a, b));
		return returnVal;
	}
	/** Adds the specified box if it is not null to this BoxList. */
	public <R> R addR(Box2<A, B> boxIn, R returnVal) {
		if (boxIn != null && (allowDuplicates || !contains(boxIn))) createdList.add(boxIn);
		return returnVal;
	}
	
	/** Creates a new StorageBox with the given A and B values and then adds it to the end of this holder. */
	public BoxList<A, B> addRT(A a, B b) {
		if (allowDuplicates || !contains(a)) createdList.add(new Box2<A, B>(a, b));
		return this;
	}
	/** Adds the specified box if it is not null to this BoxList. */
	public BoxList<A, B> addRT(Box2<A, B> boxIn) {
		if (boxIn != null && (allowDuplicates || !contains(boxIn))) createdList.add(boxIn);
		return this;
	}
	
	public boolean addIf(boolean condition, A a, B b) { if (condition) return add(a, b); return false; }
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
	public void put(A a, B b) {
		Box2<A, B> box = getBoxWithA(a);
		if (box != null) box.setB(b);
		else add(a, b);
	}
	
	//--------------------
	// Methods : Removers
	//--------------------
	
	/** Removes the box at the specified point number. */
	public Box2<A, B> remove(int index) { return createdList.remove(index); }
	
	/** Removes every box that contains the given A value. */
	public List<Box2<A, B>> removeBoxesContainingA(A a) {
		EArrayList<Box2<A, B>> returnList = new EArrayList<>();
		Iterator<Box2<A, B>> it = createdList.iterator();
		while (it.hasNext()) {
			Box2<A, B> getBox = it.next();
			if (getBox.containsA(a)) {
				returnList.add(getBox);
				it.remove();
			}
		}
		return returnList;
	}
	
	/** Removes every box that contains the given B value. */
	public List<Box2<A, B>> removeBoxesContainingB(B b) {
		EArrayList<Box2<A, B>> returnList = new EArrayList<>();
		Iterator<Box2<A, B>> it = createdList.iterator();
		while (it.hasNext()) {
			Box2<A, B> getBox = it.next();
			if (getBox.containsB(b)) {
				returnList.add(getBox);
				it.remove();
			}
		}
		return returnList;
	}
	
	/** Removes every box that has the exact A and B values. */
	public List<Box2<A, B>> removeBoxesWithSaidValues(A a, B b) {
		EArrayList<Box2<A, B>> returnList = new EArrayList<>();
		Iterator<Box2<A, B>> it = createdList.iterator();
		while (it.hasNext()) {
			Box2<A, B> getBox = it.next();
			if (getBox.compare(a, b)) {
				returnList.add(getBox);
				it.remove();
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
	public A getFirstA() { return (isNotEmpty()) ? get(0).getA() : null; }
	public B getFirstB() { return (isNotEmpty()) ? get(0).getB() : null; }
	
	public Box2<A, B> getLast() { return (isNotEmpty()) ? get(size() - 1) : null; }
	public A getLastA() { return (isNotEmpty()) ? get(size() - 1).getA() : null; }
	public B getLastB() { return (isNotEmpty()) ? get(size() - 1).getB() : null; }
	
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
	public static <A, B> BoxList<A, B> createBox(List<A> a, List<B> b) {
		if (a == null || b == null) return null;
		if (a.size() != b.size()) return null;
		BoxList<A, B> newHolder = new BoxList<>();
		for (int i = 0; i < a.size(); i++) {
			newHolder.add(a.get(i), b.get(i));
		}
		return newHolder;
	}
	
	/** Static method used to create a new BoxList parametized with the given object and value types.
	 *  A variable sized list of argument is passed to initialize the values of this holder. For every argument
	 *  passed, a corresponding value must also be passed along with it so that is adheres to the <Object, Value>
	 *  relationship. */
	public static <A, B> BoxList<A, B> of(Class<A> aType, Class<B> bType, Object... dataIn) {
		if (dataIn.length % 2 == 1) return null;
		BoxList<A, B> newHolder = new BoxList<>();
		for (int i = 0; i < dataIn.length; i += 2) {
			newHolder.add((A) dataIn[i], (B) dataIn[i + 1]);
		}
		return newHolder;
	}
	
	/** Collector implementation used to be able to convert a typed stream of data into an EArrayList of the same type. */
	public static <X, Y> Collector<Box2<X, Y>, ?, BoxList<X, Y>> toBoxHolder() {
		return Collector.of(BoxList::new, BoxList::add, (left, right) -> { left.addAll(right); return left; });
	}
	
	//------------------------
	// Private Static Methods
	//------------------------
	
	/** Internal function used to remove duplicates from a specified holder. */
	private static void purgeDuplicates(BoxList holderIn) {
		if (holderIn == null) return;
		EArrayList<Box2> noDups = new EArrayList<>();
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
	
	@Override
	public boolean contains(Object o) {
		return createdList.contains(o);
	}

	@Override
	public Object[] toArray() {
		Object[] arr = new Object[createdList.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = createdList.get(i);
		}
		return arr;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean remove(Object o) {
		if (o instanceof Box2<?, ?> box) {
			return createdList.remove(box);
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return createdList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends Box2<A, B>> c) {
		return createdList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Box2<A, B>> c) {
		return createdList.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return createdList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return createdList.retainAll(c);
	}

	@Override
	public Box2<A, B> set(int index, Box2<A, B> element) {
		return createdList.set(index, element);
	}

	@Override
	public void add(int index, Box2<A, B> element) {
		createdList.add(index, element);
	}

	@Override
	public int indexOf(Object o) {
		return createdList.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return createdList.lastIndexOf(o);
	}

	@Override
	public ListIterator<Box2<A, B>> listIterator() {
		return createdList.listIterator();
	}

	@Override
	public ListIterator<Box2<A, B>> listIterator(int index) {
		return createdList.listIterator(index);
	}

	@Override
	public List<Box2<A, B>> subList(int fromIndex, int toIndex) {
		return createdList.subList(fromIndex, toIndex);
	}

	@Override
	public void ensureCapacity(int size) {
		createdList.ensureCapacity(size);
	}
	
}
