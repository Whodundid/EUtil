package eutil.datatypes;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import eutil.datatypes.util.EList;

public class EStack<E> implements EList<E> {

	private EArrayList<E> elements = new EArrayList<>();
	private int maxSize;
	
	//--------------
	// Constructors
	//--------------
	
	public EStack() {}
	
	public EStack(Collection<E> collectionIn) {
		if (collectionIn == null) return;
		Iterator<E> it = collectionIn.iterator();
		while (it.hasNext()) elements.add(it.next());
	}
	
	public EStack(E... dataIn) {
		for (E e : dataIn) elements.add(e);
	}
	
	//-----------
	// Overrides
	//-----------
	
	public E get(int index) { return elements.get(index); }
	public int size() { return elements.size(); }
	public boolean addAll(Collection<? extends E> c) { return elements.addAll(c); }
	
	//---------
	// Methods
	//---------
	
	public EStack<E> setMaxSize(int val) {
		maxSize = val;
		elements.trimToSize(val);
		return this;
	}
	
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		for (E e : c) push(e);
		return true;
	}
	
	@Override public EStack<E> reverse() { return new EStack(elements.reverse()); }
	@Override public boolean isEmpty() { return elements.isEmpty(); }
	@Override public boolean contains(Object o) { return elements.contains(o); }
	@Override public Iterator<E> iterator() { return elements.iterator(); }
	@Override public Object[] toArray() { return elements.toArray(); }
	@Override public <T> T[] toArray(T[] a) { return elements.toArray(a); }
	@Override public boolean add(E e) { return false; }
	@Override public boolean remove(Object o) { return elements.remove(o); }
	@Override public boolean containsAll(Collection<?> c) { return elements.containsAll(c); }
	@Override public boolean removeAll(Collection<?> c) { return elements.removeAll(c); }
	@Override public boolean retainAll(Collection<?> c) { return elements.retainAll(c); }
	@Override public void clear() { elements.clear(); }
	@Override public E set(int index, E element) { return elements.set(index, element); }
	@Override public void add(int index, E element) { elements.set(index, element); }
	@Override public E remove(int index) { return elements.remove(index); }
	@Override public int indexOf(Object o) { return elements.indexOf(o); }
	@Override public int lastIndexOf(Object o) { return elements.lastIndexOf(o); }
	@Override public ListIterator<E> listIterator() { return elements.listIterator(); }
	@Override public ListIterator<E> listIterator(int index) { return elements.listIterator(index); }
	@Override public List<E> subList(int fromIndex, int toIndex) { return elements.subList(fromIndex, toIndex); }
	@Override public void ensureCapacity(int size) { elements.ensureCapacity(size); }
	
}
