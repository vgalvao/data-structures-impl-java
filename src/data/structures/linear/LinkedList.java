package data.structures.linear;

public class LinkedList<E> {

	private Node firstNode;
	private Node lastNode;
	private int totalOfNodes;

	public LinkedList() {
	}

	public void addFirst(E e) {
		if (this.totalOfNodes == 0) {
			Node newNode = new Node(e);
			this.firstNode = newNode;
			this.lastNode = newNode;

		} else {
			Node newNode = new Node(e, this.firstNode);
			this.firstNode.setPreviousNode(newNode);
			this.firstNode = newNode;
		}
		this.totalOfNodes++;
	}

	public void addLast(E e) {
		if (this.totalOfNodes == 0) {
			this.addFirst(e);

		} else {
			Node newNode = new Node(e);
			this.lastNode.setNextNode(newNode);
			newNode.setPreviousNode(lastNode);
			this.lastNode = newNode;
			this.totalOfNodes++;
		}
	}

	public void add(E e) {
		this.addLast(e);
	}

	public void add(int index, E e) {
		if (this.checkIndex(index))
			throw new IndexOutOfBoundsException();

		if (index == 0) {
			this.addFirst(e);

		} else if (index == this.totalOfNodes) {
			this.addLast(e);

		} else {
			Node prevNode = this.getNodeByIndex(index - 1);
			Node nextNode = prevNode.getNextNode();
			Node newNode = new Node(e, nextNode, prevNode);
			prevNode.setNextNode(newNode);
			nextNode.setPreviousNode(newNode);
			this.totalOfNodes++;
		}
	}

	public Object get(int index) {
		if (this.checkIndex(index))
			throw new IndexOutOfBoundsException();

		return this.getNodeByIndex(index).getContent();
	}

	public void removeFirst() {
		if (this.totalOfNodes == 0)
			return;

		this.firstNode = this.firstNode.getNextNode();
		this.totalOfNodes--;

		if (this.totalOfNodes == 0)
			this.lastNode = null;
	}

	public void removeLast() {
		if (this.totalOfNodes == 1) {
			this.removeFirst();

		} else {
			Node node = this.lastNode.getPreviousNode();
			node.setNextNode(null);
			this.lastNode = node;
			this.totalOfNodes--;
		}
	}

	public void remove(int index) {
		if (this.checkIndex(index))
			throw new IndexOutOfBoundsException();

		if (index == 0) {
			this.removeFirst();

		} else if (index == this.totalOfNodes - 1) {
			this.removeLast();

		} else {
			Node node = this.firstNode;
			for (int i = 0; i < index; i++) {
				node = node.getNextNode();
			}
			Node prev = node.getPreviousNode();
			Node next = node.getNextNode();
			prev.setNextNode(next);
			next.setPreviousNode(prev);
			this.totalOfNodes--;
		}
	}

	public void remove(Object obj) {
		Node node = this.firstNode;
		for (int i = 0; i < this.totalOfNodes; i++) {
			if (node.getContent() == obj) {
				this.remove(i);
				return;
			}
			node = node.getNextNode();
		}
	}

	public boolean contens(Object obj) {
		Node node = this.firstNode;
		while (node != null) {
			if (node.getContent() == obj) {
				return true;
			}
			node = node.getNextNode();
		}
		return false;
	}

	public int size() {
		return this.totalOfNodes;
	}

	private Node getNodeByIndex(int index) {
		if (this.checkIndex(index))
			throw new IndexOutOfBoundsException();

		Node node = this.firstNode;
		for (int i = 0; i < index; i++)
			node = node.getNextNode();

		return node;
	}

	private boolean checkIndex(int index) {
		return (index < 0 || index >= this.totalOfNodes) ? true : false;
	}

	@Override
	public String toString() {
		if (this.totalOfNodes == 0)
			return "[]";

		Node node = this.firstNode;
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < this.totalOfNodes; i++) {
			sb.append(node.getContent()).append(",");
			node = node.getNextNode();
		}
		sb.deleteCharAt(sb.length() - 1).append("]");

		return new String(sb);
	}
}

class Node {

	private Object content;
	private Node nextNode;
	private Node previousNode;

	public Node(Object content, Node next, Node prev) {
		this.content = content;
		this.nextNode = next;
		this.previousNode = prev;
	}

	public Node(Object content, Node next) {
		this(content, next, null);
	}

	public Node(Object content) {
		this(content, null, null);
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public Node getNextNode() {
		return nextNode;
	}

	public void setNextNode(Node nextNode) {
		this.nextNode = nextNode;
	}

	public Node getPreviousNode() {
		return previousNode;
	}

	public void setPreviousNode(Node previousNode) {
		this.previousNode = previousNode;
	}
}