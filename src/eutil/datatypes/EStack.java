package eutil.datatypes;

import java.util.Collection;
import java.util.Iterator;

public class EStack<E> {

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
	
	public EStack<E> reverse() { return new EStack(elements.flip()); }
	
	public EStack<E> setMaxSize(int val) {
		maxSize = val;
		elements.trimToSize(val);
		return this;
	}
	
	public E pop() { return elements.pop(); }
	
	public EStack<E> push(E val) {
		elements.push(val);
		return this;
	}
	
}
