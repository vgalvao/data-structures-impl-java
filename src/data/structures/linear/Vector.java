package data.structures.linear;

public class Vector<E> {

	private int size = 0;
	private Object[] objects;

	public Vector(int size) {
		this.objects = new Object[size];
	}

	public Vector() {
		this(100);
	}

	public void add(E e) {
		this.checkSize();
		this.objects[this.size] = e;
		this.size++;
	}

	public void add(int index, E e) {
		this.checkSize();
		if (this.checkAddIndex(index))
			throw new IllegalArgumentException("Invalid index ");

		for (int i = this.size - 1; i >= index; i--) {
			this.objects[i + 1] = this.objects[i];
		}
		this.objects[index] = e;
		this.size++;
	}

	public Object get(int index) {
		if (this.checkIndex(index))
			throw new IndexOutOfBoundsException("There is no index [" + index + "] in the vector");

		return this.objects[index];
	}

	public void remove(int index) {
		if (this.checkIndex(index))
			throw new IndexOutOfBoundsException("IThere is no index [" + index + "] in the vector");

		for (int i = index; i < this.size; i++) {
			this.objects[i] = this.objects[i + 1];
		}
		this.size--;
	}

	public void remove(Object obj) {
		for (int i = 0; i < this.size; i++) {
			if (this.objects[i].equals(obj))
				this.remove(i);
		}
	}

	public boolean contens(Object obj) {
		for (int i = 0; i < this.size; i++) {
			if (this.objects[i].equals(obj))
				return true;
		}
		return false;
	}

	public int size() {
		return this.size;
	}

	private boolean checkIndex(int index) {
		return (index < 0 && index >= this.size) ? true : false;
	}

	private boolean checkAddIndex(int index) {
		return (index < 0 && index > this.size) ? true : false;
	}

	private void checkSize() {
		if (this.size == this.objects.length) {
			Object[] newObject = new Object[this.objects.length * 2];
			for (int i = 0; i < this.size; i++) {
				newObject[i] = this.objects[i];
			}
			this.objects = newObject;
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < this.size; i++)
			sb.append(this.objects[i] + ",");
		sb.deleteCharAt(sb.length() - 1).append("]");
		
		return new String(sb);
	}

}
