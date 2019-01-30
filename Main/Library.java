/*
Last modified: 1/29/2019

	Data Structures Used:
		Stack - Used to emulate returning/checking out books, books being returned/checked out
			by a user are added to a stack, so the librarian must check books in/out from the top
			of the stack first.
		Linked List - Used a linked list for library because of its ease in adding and
			removing titles from the library, and from its simplicity to sort by different
			parameters.
		2D Array - Used to make a table of book properties in order to facilitate printing the
			library both to the console and to a GUI.
*/

import Tools.*;
import Tools.StacksQueues.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

import java.util.Arrays;

public class Library
{
	public static List<Book> books = new List<Book>();
	public static String largestTitle;
	public static String largestAuthor;
	static Tools tools = new Tools();
	public static SearchSort mod = new SearchSort();
	static Library library = new Library();
	private Stack<Book> returns = new Stack<Book>();


	// Used to repeat characters
	public static String repeat(int count, String with) {
		return new String(new char[count]).replace("\0", with);
	}

	// Prints current state of library to console
	public String[][] printLibrary()
	{
		return printLibrary(true);
	}

	// Prints current state of library to console.
	// Also used to return current state of library.
	// Avoids printing to console when print == false.
	public String[][] printLibrary(boolean print)
	{
		String[][] library = new String[books.GetSize()][6];
		largestTitle = "";
		largestAuthor = "";
		String currAuthor;
		String currTitle;

		books.First();
		for(int i=0; i<books.GetSize(); i++, books.Next())
		{
			library[i][0] = currTitle = books.GetValue().getTitle();
			library[i][1] = currAuthor = books.GetValue().getAuthor();
			library[i][2] = Integer.toString(books.GetValue().getStatus());
			library[i][3] = Integer.toString(books.GetValue().getImportance());
			library[i][4] = books.GetValue().getOwner();
			library[i][5] = books.GetValue().getStaff();

			if(currTitle.length() > largestTitle.length()) largestTitle = currTitle;
			if(currAuthor.length() > largestAuthor.length()) largestAuthor = currAuthor;
		}

		if(print) 
		{
			System.out.println("Title:" + repeat(largestTitle.length() - "Titles:".length() + 3, " ") +
								"Author:" + repeat(largestAuthor.length() - "Author:".length() + 1, " ") +
								"Checked In/Out:	 Importance:\n");
			// Prints 2d array
			for(int i=0; i<library.length; i++)
			{
				for(int k=0; k<4; k++)
				{
					String spacing = " ";
					if(k == 0) spacing = repeat(largestTitle.length() - library[i][k].length() + 2, " ");
					if(k == 1) spacing = repeat(largestAuthor.length() - library[i][k].length() + 1, " ");
					if(k == 2) {spacing = repeat("Checked In/Out:".length() - library[i][k].length() + 2, " ");
								if(library[i][k].equals("1")) library[i][k] = "In ";
								if(library[i][k].equals("0")) library[i][k] = "Out";
								}

					System.out.print(library[i][k] + spacing);
				}
				System.out.println(" ");
			}
		}

		return library;
	}

	// Brute force searches linked list for title or author and sets curr to search
	// value location. Token is title or author, key is value to search for
	public boolean Contains(String token, String key, List<Book> books)
	{
		String bookToken = "";
		books.First();
		for(int i=0; i<books.GetSize(); i++, books.Next())
		{
			if(token.equals("author")) bookToken = books.GetValue().getAuthor();
			if(token.equals("title")) bookToken = books.GetValue().getTitle();
			if(bookToken.equals(key))
			{
				int pos = books.GetPos();
				this.books.SetPos(pos);
				return true;
			}
		}
		return false;
	}


	// In the event of an emergency, function will quick sort books by importance
	// and return list of books currently checked-in in order of importance
	public List<Book> Emergency()
	{
		books = mod.quickSort(books, "importance");
		return books;
	}

	// Allows regular user to add book to be returned to the returns stack
	public void returnBook(String title)
	{
		Book toReturn = mod.searchByTitle(title, books);
		returns.Push(toReturn);
	}

	// Override
	public void check(String op)
	{
		check(op, null, null);
	}
	
	// Allows admin/librarian to check in all books in the returns stack
	public void check(String op, String staff, String owner)
	{
		int status = (op.equals("in") ? 1 : op.equals("out") ? 0 : -1);
		
		while(!returns.IsEmpty())
		{
			Contains("title", returns.Pop().getTitle(), books);
			books.GetValue().setStatus(status);
			if(status == 1)
			{
				books.GetValue().setOwner(" ");
				books.GetValue().setStaff(" ");
			}
			
			if(status == 0)
			{
				books.GetValue().setOwner(owner);
				books.GetValue().setStaff(staff);
			}
		}
	}

	// Outputs library to a text file and terminates program execution
	public void endProgram() throws IOException
	{
		books = mod.quickSort(books, "title");
		
		// Prints output to file, from Blue Pelican Java
		FileWriter fw = new FileWriter("booksCopy.txt");
		PrintWriter output = new PrintWriter(fw);
		books.First();
		for(int j = 0; j < books.GetSize(); j++, books.Next())
			output.println(books.GetValue().getTitle() + ", " +
						   books.GetValue().getAuthor() + ", " +
						   books.GetValue().getStatus() + ", " +
						   books.GetValue().getImportance() + ", " + 
						   books.GetValue().getOwner() + ", " +
						   books.GetValue().getStaff() + ", ");
		output.close();
		fw.close();
		System.exit(0);
	}

	public int getStackSize()
	{
		return returns.GetSize();
	}

}
