import StacksQueues.*;

public class Tools
{
	//Converts String to UpperCase
    public static String Upper(String name)
    {
        String[] split = name.trim().split(" ");
		name = "";
		for(int i=0; i<split.length; i++)
			name += split[i].substring(0, 1).toUpperCase() + split[i].substring(1) + " ";
        return name;
    }

    //Sorts list alphabetically
    public static void insertionSort(String[] list)
    {
        //sorts names alphabetically by last name
        for(int i=1; i <= list.length - 1; i++)
        {
            int k = i - 1;
            while(k >= 0)
            {
                String name1 = list[i];
                String name2 = list[k];

                if(name1.compareTo(name2) >= 0) {break;}
                else
                {
                    list[k] = name1;
                    list[i] = name2;
                    k -= 1;
                    i -= 1;
                }
            }
        }
    }

	public List<Book> quickSort(List<Book> books, String token)
    {
        if((books.GetSize() == 1) || (books.IsEmpty()))
            return books;

        String pivotToken = "";
        String booksToken = "";

        Book pivot;
        List<Book> lessThan = new List<Book>();
        List<Book> greaterThan = new List<Book>();
        books.Last();
        pivot = books.GetValue();

        books.First();
        for(int j = 0; j < books.GetSize()-1; j++, books.Next())
        {
            if(token.equals("title")) {pivotToken = pivot.getTitle(); booksToken = books.GetValue().getTitle();}
            else if(token.equals("author")) {pivotToken = pivot.getAuthor(); booksToken = books.GetValue().getAuthor();}

            if(pivotToken.compareTo(booksToken) > 1)
                lessThan.InsertAfter(books.GetValue());
            else if(pivotToken.compareTo(booksToken) < 1)
                greaterThan.InsertAfter(books.GetValue());
            else
                lessThan.InsertAfter(books.GetValue());
        }

        lessThan = quickSort(lessThan, token);
        greaterThan = quickSort(greaterThan, token);
        lessThan.InsertAfter(pivot);
        lessThan = lessThan.Add(greaterThan);
        return lessThan;
    }

	public Book searchByTitle(String searchVal, List<Book> books)
	{
		books = quickSort(books, "title");
		return searchByTitle(0, books.GetSize(), searchVal, books);
	}

	public Book searchByTitle(int low, int high, String searchVal, List<Book> books)
	{
		if(high < low)
			return null;

		int mid = (high + low)/2;
		books.SetPos(mid);
		if(searchVal.compareTo(books.GetValue().getTitle()) == 0)
			return books.GetValue();
		else if(searchVal.compareTo(books.GetValue().getTitle()) < 0)
			return searchByTitle(low, mid-1, searchVal, books);
		else if(searchVal.compareTo(books.GetValue().getTitle()) > 0)
			return searchByTitle(mid+1, high, searchVal, books);

		return null;
	}

	public int searchByAuthor(int low, int high, String searchVal, List<Book> books)
	{
		if(high < low)
			return -1;

		int mid = (high + low)/2;
		books.SetPos(mid);
		if(searchVal.compareTo(books.GetValue().getAuthor()) == 0)
			return books.GetPos();
		else if(searchVal.compareTo(books.GetValue().getAuthor()) < 0)
			return searchByAuthor(low, mid-1, searchVal, books);
		else if(searchVal.compareTo(books.GetValue().getAuthor()) > 0)
			return searchByAuthor(mid+1, high, searchVal, books);

		return -1;
	}

	public List<Book> booksByAuthor(String author, List<Book> books)
	{
		books = quickSort(books, "author");
		List<Book> booksByAuth = new List<Book>();
		int authorIndex = searchByAuthor(0, books.GetSize(), author, books);

		if(authorIndex != -1)
		{
			books.SetPos(authorIndex);
			while(books.GetValue().getAuthor().equals(author))
			{
				books.Prev();
				if(books.GetValue().getAuthor().equals(author))
					authorIndex = books.GetPos();
			}

			books.SetPos(authorIndex);
			while(books.GetValue().getAuthor().equals(author))
			{
				booksByAuth.InsertAfter(books.GetValue());
				books.Next();
			}
		}

		return booksByAuth;
	}
}
