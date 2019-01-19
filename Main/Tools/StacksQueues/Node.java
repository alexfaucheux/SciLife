package Tools.StacksQueues;

// Node class for List<Type> (Custom Linked List)
class Node<Type>
{
	private Type data;
	private Node<Type> link;

	// Constructor for empty node
	public Node()
	{
		this.data = null;
		this.link = null;
	}
	
	// Constructor for set node
	public Node(Type data, Node<Type> link)
	{
		this.data = data;
		this.link = link;
	}

	// accessor and mutator for the data component
	public Type getData()
	{
		return this.data;
	}

	public void setData(Type data)
	{
		this.data = data;
	}

	// accessor and mutator for the link component
	public Node<Type> getNext()
	{
		return this.link;
	}

	public void setLink(Node<Type> link)
	{
		this.link = link;
	}
}