package Tools.StacksQueues;
 
/*
Methods:
First		GetPos			Remove		Add
Last		GetValue		Replace		Contains
SetPos		GetSize			IsEmpty
Prev		InsertBefore	IsFull
Next		InsertAfter		Equals
*/
			
public class List<Type>
{
	public static final int MAX_SIZE = 5000;

	private Node<Type> head;
	private Node<Type> tail;
	private Node<Type> curr;
	private int num_items;
	private int position;
	private int size;

	// constructor
	public List()
	{
		head = tail = curr = new Node();
		size = -1;
		position = -1;
	}

	// copy constructor
	// clones the list l and sets the last element as the current
	public List(List<Type> list)
	{
		Node<Type> tmp = list.head;

		head = tail = curr = null;
		num_items = 0;

		while (tmp != null)
		{
			this.InsertAfter(tmp.getData());
			tmp = tmp.getNext();
		}
	}

	// navigates to the beginning of the list
	public void First()
	{
		curr = head;
	}

	// navigates to the end of the list
	// the end of the list is at the last valid item in the list
	public void Last()
	{
		Node<Type> tmp = head;
		Node<Type> tmp2 = null;
		while(tmp != null) 
		{
			tmp2 = tmp;
			tmp = tmp.getNext();
		}
		
		curr = tmp2;
	}

	// navigates to the specified element (0-index)
	// not possible for an empty list
	// not possible for invalid positions
	public void SetPos(int pos)
	{
		//curr = head;
		if(!this.IsEmpty() && pos < this.GetSize() && pos >= 0)
		{
			curr = head;
			for(int i=0; i<pos; i++)
			{
				curr = curr.getNext();
			}
		}
		
	}

	// navigates to the previous element
	// not possible for an empty list
	// there is no wrap-around
	public void Prev()
	{
		Node<Type> tmp = head;
		Node<Type> tmp2 = null;
		while(!this.IsEmpty() && tmp != curr)
		{
			tmp2 = tmp; 
			tmp = tmp.getNext();
		}	
	
		curr = tmp2;
	}

	
	// navigates to the next element
	// not possible for an empty list
	// there is no wrap-around
	public void Next()
	{
		if(!IsEmpty()) 
		{
			if(curr.getNext() != null) {curr = curr.getNext();}
		}
	}	 

	// returns the location of the current element (or -1)
	public int GetPos()
	{
		if(this.IsEmpty()){return -1;}
		int x = 0;
		Node<Type> tmp = head;
		while(tmp != curr)
		{
			tmp = tmp.getNext();
			x++;
		}
		return x;
	}

	// returns the value of the current element (or null)
	public Type GetValue()
	{
		return curr.getData();
	}

	// returns the size of the list
	// size does not imply capacity
	public int GetSize()
	{
		size = 0;
		Node<Type> tmp = head;
		while(tmp != null && !this.IsEmpty())
		{
			size++;
			tmp = tmp.getNext();
		}
		
		return size;
	}

	// inserts an item before the current element
	// the new element becomes the current
	// not possible for a full list
	public void InsertBefore(Type data)
	{
		Node<Type> tmp = head;
		Node<Type> tmp2 = null;
		if(!this.IsEmpty() && this.GetSize() < MAX_SIZE)
		{
			if(curr == head){head = new Node<Type>(data, head); curr = head;}
			else
			{
				while(tmp != curr) 
				{
					tmp2 = tmp;
					tmp = tmp.getNext();
				}
				
				tmp2.setLink(new Node<Type>(data, curr));
				this.curr = tmp2.getNext();
			}
		}
		
		else if(this.IsEmpty()){head = new Node<Type>(data, null); curr = head;}
	}

	// inserts an item after the current element
	// the new element becomes the current
	// not possible for a full list
	public void InsertAfter(Type data)
	{
		if(!this.IsEmpty() && this.GetSize() < MAX_SIZE)
		{
			curr.setLink(new Node<Type>(data, curr.getNext()));
			curr = curr.getNext();
		}
		
		else if(this.IsEmpty())
		{
			head = new Node<Type>(data, null); curr = head;
		}
	}

	// removes the current element 
	// not possible for an empty list
	public void Remove()
	{
		if(!this.IsEmpty())
		{
			Node<Type> tmp = curr;
			//remove the node
			if(curr == head){head = head.getNext(); curr = head;}
			else{this.Prev(); curr.setLink(tmp.getNext());}
		}

	}

	// replaces the value of the current element with the specified value
	// not possible for an empty list
	public void Replace(Type data)
	{
		if(!this.IsEmpty())
		curr.setData(data);
	}

	// returns if the list is empty
	public boolean IsEmpty()
	{
		if(head == null){return true;}
		if(head.getData() == null){return true;}
		return false;
	}

	// returns if the list is full
	public boolean IsFull()
	{
		if(this.GetSize() == MAX_SIZE){return true;}
		return false;
	}

	// returns if two lists are equal (by value)
	public boolean Equals(List<Type> l)
	{
		Node<Type> tmp = head;
		Node<Type> tmp2 = l.head;
		if(this.GetSize() != l.GetSize()){return false;}
		while(tmp != null && tmp2 != null)
		{
			if(!tmp.getData().equals(tmp2.getData())){return false;}
			tmp = tmp.getNext();
			tmp2 = tmp2.getNext();
		}
		return true;
	}

	// returns the concatenation of two lists
	// l is not modified
	// l is concatenated to the end of *this
	// the returned list does not exceed MAX_SIZE elements
	// the last element of the new list is the current
	public List<Type> Add(List<Type> l)
	{
		List<Type> tmpList = new List<Type>(this);
		if(!tmpList.IsEmpty() && !l.IsEmpty())
		{
			tmpList.Last();
			Node<Type> tmp2 = tmpList.curr;
			Node<Type> tmp = l.head;
			while(tmpList.GetSize() < MAX_SIZE && tmp != null)
			{
				Type data = tmp.getData();
				tmp2.setLink(new Node(data, null));
				tmp = tmp.getNext();
				tmp2 = tmp2.getNext();
			}
			tmpList.curr = tmp2;
		}
		else if(tmpList.IsEmpty())
		{
			tmpList = l;
			tmpList.Last();
		}
		
		return tmpList;
	}

	// returns true if data is in list
	// If true, curr is set to position of data
	public boolean Contains(Type data)
	{
		if(this.IsEmpty()){return false;}
		Node<Type> tmp = this.head;
		while(tmp != null)
		{
			if(tmp.getData().equals(data)){curr = tmp; return true;}
			tmp = tmp.getNext();
		}
		return false;
	}		
		
		
	// returns a string representation of the entire list (e.g., 1 2 3 4 5)
	// the string "NULL" is returned for an empty list
	public String toString()
	{
		if(this.IsEmpty()){return "NULL";}
		Node<Type> tmp = head;
		String s = "";
		while(tmp != null)
		{
			Type data = tmp.getData();
			if(data.equals(1)){s += " ";}
			else{s += data + " ";}
			//s+= data + " ";
			tmp = tmp.getNext();
		}
		
		return s;	
	}
}














































