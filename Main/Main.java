import Tools.*;
import Tools.StacksQueues.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import javax.swing.*;
import javafx.scene.control.TableColumn;
import java.awt.Dimension;
import java.io.FileNotFoundException;

public class Main 
{
	static List<String> title = new List<String>();
	static Library library = new Library();
	
	public void search()
	{
		JTextField Title = new JTextField(5);
		JTextField Author = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Title:"));
		myPanel.add(Title);
		myPanel.add(Box.createHorizontalStrut(15)); // adminOpt spacer
		myPanel.add(new JLabel("Author:"));
		myPanel.add(Author);

		int result = JOptionPane.showConfirmDialog(null, myPanel, 
			   "Please enter one below.", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) 
		{
		 if(!Title.getText().equals("") && !Author.getText().equals(""))
		 {
			 boolean AuthorExist = false;
			 boolean BookExist = false;
			 boolean checkedIn = true;
			 
			 Book book = library.mod.searchByTitle(Title.getText(), library.books);
			 List<Book> books = library.mod.booksByAuthor(Author.getText(), library.books);
			 
			 if(!books.IsEmpty())
			 {
				AuthorExist = true;
				books.First();
				for(int i=0; i<books.GetSize(); i++, books.Next())
				{
					if(book != null) if(books.GetValue().getTitle().equals(book.getTitle()) &&
					   books.GetValue().getStatus() == 1) {BookExist = true; break;}
					   
					if(book != null) if(books.GetValue().getTitle().equals(book.getTitle()) &&
					   books.GetValue().getStatus() == 0) {checkedIn = false; break;}
				}
				
				if(BookExist) printTable(book);
			 }
			 
			 if(!AuthorExist || !BookExist)
			 {
				 String diag = (!AuthorExist ? "Author does not exist in library!" :
								!BookExist && checkedIn ? "Book does not exist in library!" : 
								"Book checked out!");
								
				 JFrame parent = new JFrame();
				 JOptionPane.showMessageDialog(parent, diag);
			 }
				 
		 }
		 
		 else if(!Title.getText().equals(""))
		 {
			 Book book = library.mod.searchByTitle(Title.getText(), library.books);
			 if(book != null && book.getStatus() != 0)
				 printTable(book);
			 else
			 {
				 String diag = (book == null ? "Not found in library!" : "Book checked out!");
				 JFrame parent = new JFrame();
				 JOptionPane.showMessageDialog(parent, diag);
			 }
		 }
		 
		 else if(!Author.getText().equals(""))
		 {
			 List<Book> books = library.mod.booksByAuthor(Author.getText(), library.books);
			 if(!books.IsEmpty())
			 {
				books.First();
				for(int i=0; i<books.GetSize(); i++, books.Next())
				{
					if(books.GetValue().getStatus() == 0) books.Remove();
				}
				
				if(!books.IsEmpty()) printTable(books);
			 }
			 if(books.IsEmpty())
			 {
				 String diag = "Author has no books in Library!";
				 JFrame parent = new JFrame();
				 JOptionPane.showMessageDialog(parent, diag);
			 }
		 }
		 
		}
	}
	
	
	
	//prints entire library
	public void printTable()
	{
		printTable(null, null);
	}
	
	//prints only one book
	public void printTable(Book name)
	{
		printTable(name, null);
	}
	
	//prints specified list of books
	public void printTable(List<Book> books)
	{
		printTable(null, books);
	}
	
	public void printTable(Book name, List<Book> books)
	{
		String[][] rowData = null;
		
		//rowData contains all books in library
		if(name == null && books == null) 
			rowData = library.printLibrary(false);
		
		//rowData contains only one book
		else if(books == null)
		{
			rowData = new String[1][4];
			rowData[0][0] = name.getTitle(); 
			rowData[0][1] = name.getAuthor();
			rowData[0][2] = "In";
			rowData[0][3] = Integer.toString(name.getImportance());
		}
		
		//rowData contains list of books of certain author
		else
		{
			books.First();
			rowData = new String[books.GetSize()][4];
			for(int i=0; i<books.GetSize(); i++, books.Next())
			{
				name = books.GetValue();
				rowData[i][0] = name.getTitle(); 
				rowData[i][1] = name.getAuthor();
				rowData[i][2] = "In";
				rowData[i][3] = Integer.toString(name.getImportance());
				
			}
		}
		
		
		Object[]colName = {"Title","Author","Status","Importance"};
		JTable table = new JTable(rowData, colName);
		javax.swing.table.TableColumn column = null;
		
		for (int cell = 0; cell < 4; cell++) 
		{
			column = table.getColumnModel().getColumn(cell);
			if (cell == 0) 
			{
				column.setPreferredWidth(250); //First column is bigger
			} 
			else if (cell == 1)
			{
				column.setPreferredWidth(100); //Second column
			}
			else 
			{
				column.setPreferredWidth(60);
			}
		}
		JOptionPane.showMessageDialog(null, new JScrollPane(table));
	}
	
	public static void main(String[] args) throws FileNotFoundException
	{
		Main main = new Main();
		Scanner file = new Scanner(new File("books.txt"));
		
		
		while(file.hasNextLine())
		{
			String line = file.nextLine();
			String[] split = line.split(", ");
			library.books.InsertAfter(new Book(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3])));
		}

		file.close();
		
		int user = 0;
		while (user == 0 || user == 1)
		{
			Object[] options = {"Administrater", "Student", "Exit Library"};
			user = JOptionPane.showOptionDialog( null, "Please select", "SciFiLi", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);	
			
			//Administrator
			if (user == 0)
			{
				int adminOpt = 0;
				while (adminOpt  == 0 || adminOpt == 1 || adminOpt == 2 || adminOpt == 3 || adminOpt == 4 || adminOpt == 5)
				{
					
					Object[] options2 = {"Sort By Title", "Sort By Author", "Search", "Check in ", "Check out", "Emergency", "Back"};
					adminOpt = JOptionPane.showOptionDialog( null, "What would you like to do?", "Administrater", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);
					
					//Sort
					if (adminOpt  == 0 || adminOpt == 1)
					{
						String sortby = (adminOpt == 0 ? "title" : "author");
						library.books = library.mod.quickSort(library.books, sortby);
						String[][]rowData = library.printLibrary(false);
						main.printTable();
					}

					//Search
					if (adminOpt  == 2)
					{
					  	main.search();
					}
					
					//Check In
					if (adminOpt  == 3)
					{
						String input = JOptionPane.showInputDialog("Enter book title");
						JFrame frame = new JFrame();
						if(library.Contains("title", input, library.books))
						{
							if(library.books.GetValue().getStatus() == 0)
							{
								library.returnBook(input);
								library.checkInBooks();
								JOptionPane.showMessageDialog(frame, "Book Successfully Returned");
							}
							
							else JOptionPane.showMessageDialog(frame, "Book Already Checked In!");
						}
						
						else JOptionPane.showMessageDialog(frame, "Book Does Not Exist!");
						
						
						//int result = JOptionPane.showConfirmDialog(frame, "Are you sure?");
						//System.out.print(result);
					}
					
					//Check Out
					else if (adminOpt  == 4)
					{	
						String input = JOptionPane.showInputDialog("Enter book title");
						JFrame frame = new JFrame();
						int result = JOptionPane.showConfirmDialog(frame, "Are you sure?");
						System.out.print(result);


					
					}
					else if (adminOpt  == 5)
					{
						System.out.println("FIRE");
					}
				}
			}
			
			//Student
			else if (user== 1) 
			{
				Object[] options3 = {"Sort by Title", "Sort by Author", "Search", "Back"};
				int studentOpt = 0; 
				
				while (studentOpt  == 0 || studentOpt == 1 || studentOpt == 2)
				{
					studentOpt = JOptionPane.showOptionDialog( null, "What would you like to do?", "SciFiL", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options3, options3[1]);
					if (studentOpt  == 0 || studentOpt == 1)
					{
						String sortby = (studentOpt == 0 ? "title" : "author");
						library.books = library.mod.quickSort(library.books, sortby);
						String[][]rowData = library.printLibrary(false);
						main.printTable();
					}
					
					else if(studentOpt == 2)
					{
						main.search();
					}
				}
			}		
		}
		System.exit(0);
	}
}
	


