package data.structures.linear;

public class Stack<E>  {

	private LinkedList<E> stackList = new LinkedList<E>();

	public void push(E e) {
		this.stackList.addFirst(e);
	}

	public void pop() {
		this.stackList.removeFirst();
	}

	public Object peek() {
		return this.stackList.get(0);

	}

	public void empty() {
		this.stackList = new LinkedList<E>();
	}

	public String toString() {
		return this.stackList.toString();
	}

}
