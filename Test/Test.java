package SciLife.Test;

 
import SciLife.StacksQueues.*;
import java.io.*;

class Test
{
	public void endProgram(List<Book> books) throws IOException
	{
		books = new Tools().quickSort(books, "title");
		//Prints output to file, from Blue Pelican Java
		FileWriter fw = new FileWriter("libraryFile.txt");
		PrintWriter output = new PrintWriter(fw);
		books.First();
		for(int j = 0; j < books.GetSize(); j++, books.Next())
			output.println(books.GetValue().getTitle() + ", " +
						   books.GetValue().getAuthor() + ", " +
						   books.GetValue().getStatus() + ", " +
						   books.GetValue().getImportance());
		output.close();
		fw.close();
		System.exit(0);
	}
}
