package com.kidd.test.pattern.structural.eleven;

public interface Tree {
	// Get size of tree which recent node as parent
	public int getSize();

	// Get height of recent node
	public int getHeight();

	// Get depth of recent node
	public int getDepth();

	// Get element of recent node
	public Object getElement();

	// Set element of recent node, return former element
	public Object setElement(Object newElement);

	// Get parent of recent node
	public TreeLinkedList getParent();

	// Get first child of recent node
	public TreeLinkedList getFirstChild();

	// Get biggest sibling of recent node
	public TreeLinkedList getNextSibling();
}
