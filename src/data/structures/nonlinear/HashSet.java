package data.structures.nonlinear;

import java.util.Iterator;

import data.structures.linear.LinkedList;
import data.structures.linear.Vector;

public class HashSet<E> implements Iterable<E> {

	private int sizeDensity;
	Vector<LinkedList<E>> table = new Vector<LinkedList<E>>();

	public HashSet(Density den) {
		this.setDensity(den);
		for (int i = 0; i <= this.sizeDensity; i++)
			table.add(new LinkedList<E>());
	}

	public void add(E e) {
		int indexTable = this.getHashIndexOfTable(e);
		LinkedList<E> list = (LinkedList<E>) this.table.get(indexTable);
		if (!list.contens(e))
			list.add(e);

	}

	public boolean contens(E e) {
		int indexTable = this.getHashIndexOfTable(e);
		LinkedList<E> list = (LinkedList<E>) this.table.get(indexTable);
		return list.contens(e);
	}

	public void remove(E e) {
		int indexTable = this.getHashIndexOfTable(e);
		LinkedList<E> list = (LinkedList<E>) this.table.get(indexTable);
		list.remove(e);
	}

	public int size() {
		int size = 0;
		for (int i = 0; i < this.sizeDensity; i++) {
			LinkedList<E> list = (LinkedList<E>) this.table.get(i);
			size += list.size();
		}
		return size;
	}

	@Override
	public Iterator<E> iterator() {
		return (Iterator<E>) new DataSetIterator(this.toArray());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{\n");
		for (int i = 0; i < this.table.size(); i++) {
			sb.append(this.table.get(i).toString() + "\n");
		}
		sb.deleteCharAt(sb.length() - 1).append("\n}");
		return new String(sb);
	}

	public enum Density {
		LOW_8, DEFAULT_16, MEDIUM_32, HIGH_64, VERY_HIGH_128
	};

	private void setDensity(Density density) {
		switch (density) {
		case LOW_8:
			this.sizeDensity = 7;
			break;
		case DEFAULT_16:
			this.sizeDensity = 15;
			break;
		case MEDIUM_32:
			this.sizeDensity = 31;
			break;
		case HIGH_64:
			this.sizeDensity = 63;
			break;
		case VERY_HIGH_128:
			this.sizeDensity = 127;
			break;
		default:
			this.sizeDensity = 15;
			break;
		}
	}

	private Vector<Object> toArray() {
		Vector<Object> vector = new Vector<Object>();
		for (int i = 0; i < this.sizeDensity; i++) {
			LinkedList<Object> list = (LinkedList<Object>) this.table.get(i);
			for (int y = 0; y < list.size(); y++) {
				vector.add(list.get(y));
			}
		}
		return vector;
	}

	private int getHashIndexOfTable(Object object) {
		return (object == null) ? 0 : (object.hashCode() & this.sizeDensity);
	}

}

class DataSetIterator implements Iterator<Object> {

	private int position = 0;
	private Vector<Object> vector;

	DataSetIterator(Vector<Object> vector) {
		this.vector = vector;
	}

	public boolean hasNext() {
		return (position < vector.size()) ? true : false;
	}

	public Object next() {
		return (this.hasNext()) ? vector.get(position++) : null;
	}
}