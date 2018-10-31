
import StacksQueues.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

public class Library
{
	static List<Book> books = new List<Book>();
	static Tools tools = new Tools();

	//Used to repeat characters
    public static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

	public void printLibrary()
	{
		String[][] library = new String[books.GetSize()][4];
		String winTitle = "";
		String winAuthor = "";
		String currAuthor;
		String currTitle;

		books.First();
		for(int i=0; i<books.GetSize(); i++, books.Next())
		{
			library[i][0] = currTitle = books.GetValue().getTitle();
			library[i][1] = currAuthor = books.GetValue().getAuthor();
			library[i][2] = Integer.toString(books.GetValue().getStatus());
			library[i][3] = Integer.toString(books.GetValue().getImportance());

			if(currTitle.length() > winTitle.length()) winTitle = currTitle;
			if(currAuthor.length() > winAuthor.length()) winAuthor = currAuthor;
		}

		System.out.println("Title:" + repeat(winTitle.length() - "Titles:".length() + 3, " ") +
						   "Author:" + repeat(winAuthor.length() - "Author:".length() + 1, " ") +
						   "Checked In/Out:    Importance:\n");
		//prints 2d array
        for(int i=0; i<library.length; i++)
        {
            for(int k=0; k<4; k++)
            {
				String spacing = " ";
				if(k == 0) spacing = repeat(winTitle.length() - library[i][k].length() + 2, " ");
				if(k == 1) spacing = repeat(winAuthor.length() - library[i][k].length() + 1, " ");
				if(k == 2) {spacing = repeat("Checked In/Out:".length() - library[i][k].length() + 2, " ");
					        if(library[i][k].equals("1")) library[i][k] = "In ";
							if(library[i][k].equals("0")) library[i][k] = "Out";
							}

				System.out.print(library[i][k] + spacing);
            }
            System.out.println(" ");
        }
	}

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

	public static void main(String [] args) throws FileNotFoundException
	{
		Library library = new Library();
		Scanner file = new Scanner(new File("books.txt"));

		while(file.hasNextLine())
		{
			String line = file.nextLine();
			String[] split = line.split(", ");
			books.InsertAfter(new Book(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3])));
		}

		file.close();
		
	}
}
