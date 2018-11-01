package Tools.StacksQueues;


public class Stack<Type> extends List
{
	public void Push(Type data)
	{
		super.Last();
		super.InsertAfter(data);
	}

	public void Pop()
	{
		super.Last();
		super.Remove();
	}

	public Type Peek()
	{
		super.Last();
		try
		{
			Type data = (Type) super.GetValue();
			return data;
		}
		catch(NullPointerException e)
		{
			return null;
		}
	}
}
