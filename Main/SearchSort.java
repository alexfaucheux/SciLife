import Tools.StacksQueues.*;

class SearchSort
{
	//Performs a quick sort operation based on token parameter
	//	Options for token are: title, author, or importance
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
		if(token.equals("importance"))
		{
			if(pivot.getStatus() == 0)
			{
				books.Last();
				while(books.GetValue().getStatus() == 0)
				{
					books.Prev();
					if(books.GetValue().getStatus() == 1)
						pivot = books.GetValue();
				}
				books.First();
			}
			for(int j = 0; j < books.GetSize()-1; j++, books.Next())
	        {
				if(books.GetValue().getStatus() != 0)
				{
		            if(pivot.getImportance() > books.GetValue().getImportance())
		                lessThan.InsertAfter(books.GetValue());
		            else if(pivot.getImportance() < books.GetValue().getImportance())
		                greaterThan.InsertAfter(books.GetValue());
		            else
		                lessThan.InsertAfter(books.GetValue());
				}
	        }
		}
		else
		{
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
		}

        lessThan = quickSort(lessThan, token);
        greaterThan = quickSort(greaterThan, token);
        lessThan.InsertAfter(pivot);
        lessThan = lessThan.Add(greaterThan);
        return lessThan;
    }

	//Searches the list of books for any books containing a specified
	//	substring
	public List<Book> partialSearch(String searchVal, List<Book> books)
	{
		books = quickSort(books, "title");
		List<Book> bookList = new List<Book>();

		books.First();
		for(int j = 0; j < books.GetSize(); j++, books.Next())
			if(books.GetValue().getTitle().contains(searchVal))
				bookList.InsertAfter(books.GetValue());

//This was me being dumb, please ignore
/*
		books.First();
		//If searching for one word (i.e. all titles containing "The")
		if(searchVal.split(" ").length == 1)
		{
			for(int j = 0; j < books.GetSize(); j++, books.Next())
				for(int k = 0; k < books.GetValue().getTitle().split(" ").length; k++)
					if(books.GetValue().getTitle().split(" ")[k].equals(searchVal))
						bookList.InsertAfter(books.GetValue());
		}
		//If searching for a string within a title
		else
		{
			for(int j = 0; j < books.GetSize(); j++, books.Next())
				for(int k = 0; (k + searchVal.length()) < books.GetValue().getTitle().length(); k++)
					if(searchVal.equals(books.GetValue().getTitle().substring(k, (k + searchVal.length()))))
						bookList.InsertAfter(books.GetValue());
		}
*/

		return bookList;
	}

	//default method call for searchByTitle, sorts books by title and then calls binary
	//	search method with appropriate parameters
	public Book searchByTitle(String searchVal, List<Book> books)
	{
		books = quickSort(books, "title");
		return searchByTitle(0, books.GetSize(), searchVal, books);
	}

	//Binary searches books for book title
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

	//Binary searches for book written by author in books and returns location of author instance
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

	//Returns List of type Book containing all books written by input author
	public List<Book> booksByAuthor(String author, List<Book> books)
	{
		books = quickSort(books, "author");
		List<Book> booksByAuth = new List<Book>();
		int authorIndex = searchByAuthor(0, books.GetSize(), author, books);

		//Does not execute if author is not found
		if(authorIndex != -1)
		{
			//Moves forward in list until first book by author is found
			books.SetPos(authorIndex);
			while(books.GetValue().getAuthor().equals(author))
			{
				books.Prev();
				if(books.GetValue().getAuthor().equals(author))
					authorIndex = books.GetPos();
			}

			//Appends books to booksByAuth until book not written by input author is found
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
