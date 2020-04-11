package data.structures.nonlinear;

import java.util.Iterator;

import data.structures.linear.Vector;

public class BinaryTree<E> implements Iterable<E> {

	private int size;
	private int depthLeft;
	private int depthRight;
	private Node<E> rootNode;

	public int size() {
		return this.size;
	}

	public int treeHeight() {
		return (this.depthLeft > this.depthRight) ? this.depthLeft : this.depthRight;
	}

	public void add(E content) {
		int key = this.generateKey(content);
		if (this.rootNode == null) {
			this.rootNode = new Node<>(key, content, null, null);
			this.size++;
		} else {
			Node<E> node = this.rootNode;
			while (key != node.getKey()) {
				if (key < node.getKey()) {
					if (node.getLeftNode() != null) {
						node = node.getLeftNode();
					} else {
						node.setLeftNode(new Node<E>(key, content, null, null));
						this.depthLeft++;
						this.size++;
						break;
					}
				} else if (key > node.getKey()) {
					if (node.getRightNode() != null) {
						node = node.getRightNode();
					} else {
						node.setRightNode(new Node<E>(key, content, null, null));
						this.depthRight++;
						this.size++;
						break;
					}
				}
			}
		}
	}

	public boolean contens(E content) {
		if (this.rootNode != null) {
			Node<E> node = this.rootNode;
			int key = this.generateKey(content);
			while (node != null) {
				if (key < node.getKey()) {
					node = node.getLeftNode();
				} else if (key > node.getKey()) {
					node = node.getRightNode();
				} else if (node != null) {
					return true;
				}
			}
		}
		return false;
	}

	public void remove(E content) {
		if (!this.contens(content)) {
			return;
		}
		Node<E> removeParent = this.rootNode;
		Node<E> removeNode = this.rootNode;
		int key = this.generateKey(content);
		while (removeNode.getKey() != key) {
			if (key < removeNode.getKey()) {
				removeParent = removeNode;
				removeNode = removeNode.getLeftNode();
			} else {
				removeParent = removeNode;
				removeNode = removeNode.getRightNode();
			}
		}
		if (removeNode == this.rootNode) {
			this.removeRootNode(removeNode);
			return;
		}
		if (removeNode.getLeftNode() == null && removeNode.getRightNode() == null) {
			this.removeLeafNode(removeNode, removeParent);
			return;
		}
		this.removeNodeOnBranch(removeNode, removeParent);
	}

	private void removeRootNode(Node<E> removeNode) {
		if (this.size == 1) {
			this.rootNode = null;
		} else if (removeNode.getLeftNode() == null || removeNode.getRightNode() == null) {
			if (removeNode.getLeftNode() == null) {
				this.rootNode = removeNode.getRightNode();
			} else {
				this.rootNode = removeNode.getLeftNode();
			}
		} else {
			Node<E> substituteParent = null;
			Node<E> substituteNode = removeNode.getLeftNode();
			if (substituteNode.getRightNode() != null) {
				while (substituteNode.getRightNode() != null) {
					substituteParent = substituteNode;
					substituteNode = substituteNode.getRightNode();
				}
				if (substituteNode.getLeftNode() != null) {
					substituteParent.setRightNode(substituteNode.getLeftNode());
				} else {
					substituteParent.setRightNode(null);
				}
				substituteNode.setLeftNode(removeNode.getLeftNode());
			}
			substituteNode.setRightNode(removeNode.getRightNode());
			this.rootNode = substituteNode;
		}
		this.size--;
	}

	private void removeLeafNode(Node<E> removeNode, Node<E> removeParent) {
		if (removeParent.getLeftNode() == removeNode) {
			removeParent.setLeftNode(null);
		} else {
			removeParent.setRightNode(null);
		}
		this.size--;
	}

	private void removeNodeOnBranch(Node<E> removeNode, Node<E> removeParent) {
		Node<E> substituteNode;
		Node<E> substituteParent = null;
		if (removeNode.getLeftNode() != null) {
			substituteNode = removeNode.getLeftNode();
			if (substituteNode.getRightNode() != null) {
				while (substituteNode.getRightNode() != null) {
					substituteParent = substituteNode;
					substituteNode = substituteNode.getRightNode();
				}
				if (substituteNode.getLeftNode() != null) {
					substituteParent.setRightNode(substituteNode.getLeftNode());
				} else {
					substituteParent.setRightNode(null);
				}
				substituteNode.setLeftNode(removeNode.getLeftNode());
			}
			if (removeNode.getRightNode() != null) {
				substituteNode.setRightNode(removeNode.getRightNode());				
			}
			if (removeParent.getRightNode() == removeNode) {
				removeParent.setRightNode(substituteNode);
			} else {
				removeParent.setLeftNode(substituteNode);
			}
		} else {
			substituteNode = removeNode.getRightNode();
			if (removeParent.getRightNode() == removeNode) {
				removeParent.setRightNode(removeNode.getRightNode());
			} else {
				removeParent.setLeftNode(removeNode.getRightNode());
			}
		}
		this.size--;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return this.makeString(this.rootNode, sb);
	}

	private String makeString(Node<E> node, StringBuilder sb) {
		if (node != null) {
			sb.append("(" + node.getContent());
		}
		if (node.getLeftNode() != null) {
			this.makeString(node.getLeftNode(), sb);
		}
		if (node.getRightNode() != null) {
			this.makeString(node.getRightNode(), sb);
		}
		sb.append(")");
		return (this.rootNode == node) ? new String(sb) : null;
	}

	private int generateKey(E e) {
		return (e.hashCode());
	}

	@Override
	public Iterator<E> iterator() {
		return (Iterator<E>) new DataTreeIterator(this.toArray());
	}

	private Vector<Object> toArray() {
		Vector<Object> vector = new Vector<Object>();
		this.getContentToArray(this.rootNode, vector);
		return vector;
	}

	private void getContentToArray(Node<E> node, Vector<Object> vector) {
		if (node.getLeftNode() != null) {
			this.getContentToArray(node.getLeftNode(), vector);			
		}
		vector.add(node.getContent());
		if (node.getRightNode() != null) {
			this.getContentToArray(node.getRightNode(), vector);			
		}
	}
}

class Node<E> {
	private int key;
	private E content;
	private Node<E> leftNode, rightNode;

	public Node(int key, E content, Node<E> leftNode, Node<E> rightNode) {
		this.key = key;
		this.content = content;
		this.leftNode = leftNode;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public E getContent() {
		return content;
	}

	public void setContent(E content) {
		this.content = content;
	}

	public Node<E> getLeftNode() {
		return leftNode;
	}

	public void setLeftNode(Node<E> leftNode) {
		this.leftNode = leftNode;
	}

	public Node<E> getRightNode() {
		return rightNode;
	}

	public void setRightNode(Node<E> rightNode) {
		this.rightNode = rightNode;
	}

}

class DataTreeIterator implements Iterator<Object> {
	private int position = 0;
	private Vector<Object> vector;

	DataTreeIterator(Vector<Object> vector) {
		this.vector = vector;
	}

	public boolean hasNext() {
		return (position < vector.size()) ? true : false;
	}

	public Object next() {
		return (this.hasNext()) ? vector.get(position++) : null;
	}
}