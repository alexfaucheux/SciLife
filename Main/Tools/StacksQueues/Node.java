package Tools.StacksQueues; 

/*
Was not sure if we could edit this file but since there was nothing in this file that
said otherwise, I assumed we could.

I changed "getLink" to "getNext" because it made programming the List class much easier and much more coherent for me. 
I need variables and methods to tell me exactly what they do for me to remember/understand how to use them.
So Curr.getNext() to refer to the next element in the linked list made so much more sense to me
than Curr.getLink() did. Some reason, using Curr.getLink() really confused me at the start so it had to change.

Also added a constuctor that took data and a link, which was primarily for insertAfter/insertBefore.
*/

class Node<Type>
{
	private Type data;
	private Node<Type> link;

	// constructor
	public Node()
	{
		this.data = null;
		this.link = null;
	}
	
	//Added constructor
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