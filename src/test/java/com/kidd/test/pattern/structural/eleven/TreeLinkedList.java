package com.kidd.test.pattern.structural.eleven;

public class TreeLinkedList implements Tree {
	// Pointer point to parent
	private TreeLinkedList parent;
	// Element
	private Object element;
	// Pointer point to firstChild
	private TreeLinkedList firstChild;
	// Pointer point to nextSibling
	private TreeLinkedList nextSibling;

	// Constructor
	public TreeLinkedList() {
		this(null, null, null, null);
	}

	// Constructor with parameters
	public TreeLinkedList(TreeLinkedList p, Object e, TreeLinkedList f,
			TreeLinkedList n) {
		this.parent = p;
		this.element = e;
		this.firstChild = f;
		this.nextSibling = n;
	}

	// Get size of tree which recent node as parent
	public int getSize() {
		int size = 1; // Recent node also include own children
		TreeLinkedList subTree = firstChild;// Start with first child
		while (null != subTree) {
			size += subTree.getSize();
			subTree = subTree.getNextSibling();// Get all descendants
		}
		return size;
	}

	// Get height of recent node
	public int getHeight() {
		int height = -1;// Recent node's(parent) height
		TreeLinkedList subTree = firstChild;// Start with first child

		while (null != subTree) {
			height = Math.max(height, subTree.getHeight());// Get the max height
			subTree = subTree.getNextSibling();
		}
		return height + 1;// Get recent node height
	}

	// Get depth of recent node
	public int getDepth() {
		int depth = 0;
		TreeLinkedList p = parent;// Start with parent
		while (p != null) {
			depth++;
			p = p.getParent();// Get all parents of every node
		}
		return depth;
	}

	// Get element of recent node,if nothing return null
	public Object getElement() {
		return this.element;
	}

	// Set element of recent node,return former element
	public Object setElement(Object newElement) {
		Object swap;
		swap = this.element;
		this.element = newElement;
		return this.element;
	}

	// Get parent of recent node,if nothing return null
	public TreeLinkedList getParent() {
		return parent;
	}

	// Get first child of recent node,if nothing return null
	public TreeLinkedList getFirstChild() {
		return firstChild;
	}

	// Get biggest sibling of recent node,if nothing return null
	public TreeLinkedList getNextSibling() {
		return nextSibling;
	}
}
