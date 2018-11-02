package Tools.StacksQueues;

public class Queue<Type> extends List
{
	public void Enqueue(Type data)
	{
		super.Last();
		super.InsertAfter(data);
	}
	
	public Type Dequeue()
	{
		super.First();
		Type data = (Type) super.GetValue();
		super.Remove();
		return data;
	}
}