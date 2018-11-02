package Tools.StacksQueues;

public class Stack<Type> extends List
{
	public void Push(Type data)
	{
		super.Last();
		super.InsertAfter(data);
	}
	
	public Type Pop()
	{
		super.Last();
		Type data = (Type) super.GetValue();
		super.Remove();
		return data;
	}
	
	public Type Peek()
	{
		super.Last();
		Type data = (Type) super.GetValue();
		return data;
	}
}