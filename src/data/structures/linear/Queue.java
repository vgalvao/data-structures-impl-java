package data.structures.linear;

public class Queue<E> {

	private LinkedList<E> queueList = new LinkedList<E>();

	public void offer(E e) {
		this.queueList.addLast(e);
	}

	public Object poll() {
		Object e = this.queueList.get(0);
		queueList.remove(0);
		return e;
	}

	public Object peek() {
		Object e = this.queueList.get(0);
		return e;
	}

	public void empty() {
		this.queueList = new LinkedList<E>();
	}

}
