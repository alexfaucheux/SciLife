/*
Created by: Alex Faucheux, Josh Romero, and Eric Pitts
Date last modified: 11/9/2018


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
import UserManagement.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javafx.scene.control.TableColumn;


public class Main
{
	// List<Type> is a custom Linked List class
	static List<String> title = new List<String>();
	static Library library = new Library();
	
	// Searches the list of books by title or/and author and displays the results.
	// it also can display a partial title search. 
	public void search() throws IOException
	{
		
		// Ask for the title or/and author the user is looking for
		JTextField Title = new JTextField(5);
		JTextField Author = new JTextField(5);

		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Title:"));
		myPanel.add(Title);
		myPanel.add(Box.createHorizontalStrut(15)); // adminOpt spacer
		myPanel.add(new JLabel("Author:"));
		myPanel.add(Author);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Please enter one below.", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) 
		{
			
			// Checks if the user inputed a title and author
			if(!Title.getText().equals("") && !Author.getText().equals(""))
			{
				 boolean AuthorExist = false;
				 boolean BookExist = false;
				 boolean checkedIn = true;
				 
				 // Searches for all the books matches the author the user inputed and stores it in a linked list 
				 List<Book> books = library.mod.booksByAuthor(Author.getText(), library.books);
				 
				 // If list of books is not empty 
				 if(!books.IsEmpty()) 
				 {
					 
					 // If author exist and part of the title was entered it will 
					 // removes all books that does not match the partial title that was inputed 
					 AuthorExist = true;
					 books = library.mod.partialSearch(Title.getText(), books);
				 }
				 
				 // True if author and the partial title search has at least 1 book
				 if(!books.IsEmpty())
				 {
					 BookExist = true;
					 books.First();
					 for(int i=0; i<books.GetSize(); i++, books.Next())
					 {
						 Book book = books.GetValue();
						 if(book.getStatus() == 0) books.Remove();
					 }
					 printTable(books);
				 }

				 // If author or title does not exist 
				 if(!AuthorExist || !BookExist)
				 {
					 String diag = (!AuthorExist ? "Author does not exist in library!" :
									!BookExist ? "Book does not exist in library!" : 
									"Book checked out!");
									
					 JFrame parent = new JFrame();
					 JOptionPane.showMessageDialog(parent, diag);
				 }
			}
		 
			// If the user only inputed a title 
			// it searches for all the title for what the inputed
			else if(!Title.getText().equals(""))
			{
				
				// finds and saves all the titles that is related to what the user inputed in books
				List<Book> books = library.mod.partialSearch(Title.getText(), library.books);
				List<Book> checkedIn = new List<Book>();
				 
				// All the list books in books that are checked in 
				// is inserted in the checkedIn list
				if(!books.IsEmpty())
				{
					books.First();
					for(int i=0; i<books.GetSize(); i++, books.Next())
					{
						if(books.GetValue().getStatus() != 0) checkedIn.InsertAfter(books.GetValue());
					}
					 
					if(!checkedIn.IsEmpty()) printTable(checkedIn);
				}
				
				// If books or checkedIn is empty it displays a warning
				if(books.IsEmpty() || checkedIn.IsEmpty())
				{
					String diag = (books.IsEmpty() ? "No results match your search!" : "No results are checked in!");
					JFrame parent = new JFrame();
					JOptionPane.showMessageDialog(parent, diag);
				}
			}
			
			// If the user only inputed an author 
			else if(!Author.getText().equals(""))
			{
				
				 // Buts all the books that mathch the user input in a list of books
				 List<Book> books = library.mod.booksByAuthor(Author.getText(), library.books);
				 if(!books.IsEmpty())
				 {
					 
					// Searchs throw the books list and removes all the books that are checked out
					books.First();
					for(int i=0; i<books.GetSize(); i++, books.Next())
					{
						if(books.GetValue().getStatus() == 0) books.Remove();
					}
					
					if(!books.IsEmpty()) printTable(books);
				 }
				 
				// If the books list is empty is displays a warning
				if(books.IsEmpty())
				{
					 String diag = "Author has no books in Library!";
					 JFrame parent = new JFrame();
					 JOptionPane.showMessageDialog(parent, diag);
				}
			}
		}
	}
	
	
	
	// Prints entire library
	public void printTable() throws IOException
	{
		printTable(null, null, 0);
	}
	
	// Prints only one book
	public void printTable(Book name) throws IOException
	{
		printTable(name, null, 0);
	}
	
	// Prints specified list of books
	public void printTable(List<Book> books) throws IOException
	{
		printTable(null, books, 0);
	}
	
	// Prints table with color
	public void printTable(List<Book> books, int num) throws IOException
	{
		printTable(null, books, num);
	}
	
	// Prints its parameters to a 2D Jtable
	public void printTable(Book name, List<Book> books, int num) throws IOException
	{
		String[][] rowData = null;
		
		// RowData contains all books in library
		if(name == null && books == null) 
			rowData = library.printLibrary(false);
		
		// RowData contains only one book
		else if(books == null)
		{
			rowData = new String[1][6];
			rowData[0][0] = name.getTitle(); 
			rowData[0][1] = name.getAuthor();
			rowData[0][2] = "In";
			rowData[0][3] = Integer.toString(name.getImportance());
			rowData[0][4] = name.getOwner();
			rowData[0][5] = name.getStaff();
		}
		
		// RowData contains list of books of certain author
		else
		{
			books.First();
			rowData = new String[books.GetSize()][6];
			for(int i=0; i<books.GetSize(); i++, books.Next())
			{
				name = books.GetValue();
				rowData[i][0] = name.getTitle(); 
				rowData[i][1] = name.getAuthor();
				rowData[i][2] = "In";
				rowData[i][3] = Integer.toString(name.getImportance());
				rowData[i][4] = name.getOwner();
				rowData[i][5] = name.getStaff();
				
			}
		}
		
		// Displays the books when sorted by author or title is selected 
		Object[]colName = {"Title","Author","Status","Importance","Owner","Staff"};
		JTable table = new JTable(rowData, colName);
		javax.swing.table.TableColumn column = null;
		
		for (int cell = 0; cell < 4; cell++) 
		{
			column = table.getColumnModel().getColumn(cell);
			
			// creates the appropriate size for the sections
			if (cell == 0) column.setPreferredWidth(250); 
			else if (cell == 1) column.setPreferredWidth(100); 
			else column.setPreferredWidth(60);
			
		}
		
		// if emergency is selected it changes the color of the Jtable 
		if(num == 1)
		{
			UIManager UIManager = new UIManager();
			UIManager.put("OptionPane.background", new ColorUIResource(255, 0, 0));
			UIManager.put("OptionPane.minimumSize", new Dimension(750, 500));
			UIManager.put("Panel.background", new ColorUIResource(255, 0, 0));
			JOptionPane.showMessageDialog(null, new JScrollPane(table), "SetColor", JOptionPane.WARNING_MESSAGE);
			library.endProgram();
		}
		
		else
		{
			UIManager UIManager = new UIManager();
			UIManager.put("OptionPane.minimumSize", new Dimension(1000, 500));
			JOptionPane.showMessageDialog(null, new JScrollPane(table));
			UIManager.put("OptionPane.minimumSize", new Dimension(250, 0));
		}
	}
	
	// Main
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Main main = new Main();
		UserPassword credentials = new UserPassword();
		
		/*
		IF there is not a custom database located in directory (namely booksCopy.txt),
		open and load the original (books.txt).
		When program is exited, it will create a booksCopy file that will save the current
		state of the database, and if the file is not deleted, the program will load booksCopy when ran.
		After creating the booksCopy file the program will only alter the booksCopy file
		*/
		try
		{
			Scanner file = new Scanner(new File("booksCopy.txt"));
			while(file.hasNextLine())
			{
				
				String line = file.nextLine();
				String[] split = line.split(", ");
				library.books.InsertAfter(new Book(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), split[4], split[5]));
			}
			
			file.close();
		}
		
		// If booksCopy does not exist
		catch (Exception e)
		{
			Scanner names = new Scanner(new File("Names.txt"));
			Scanner file = new Scanner(new File("books.txt"));
			
			while(file.hasNextLine())
			{
				String name = " ";
				String staff = " ";
				
				String line = file.nextLine();
				String[] split = line.split(", ");
				if(Integer.parseInt(split[2]) == 0)
				{
					name = names.nextLine();
					staff = "Lori";
				}
				
				library.books.InsertAfter(new Book(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), name, staff));
			}
			
			names.close();
			file.close();
		}
		
		// This screen will continue until the user clicks Exit Library or the 'X' at the 
		// top right of the menu
		int user = 0;
		while (user == 0 || user == 1)
		{
			Object[] options = {"Administrator", "Student", "Exit Library"};
			user = JOptionPane.showOptionDialog( null, "Please select", "SciFiLi", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);	
			
			//Administrator
			if (user == 0)
			{
				
				String masteropt = null;
				
				// If Admin is selected it will prompt for a username and password
				String type = credentials.username_password();
				
				// If canacel is not selected
				if(!type.equals("cancel"))
				{
					
					// if the username and password equaled the masters info
					if(type.equals("master")) masteropt = "Manage Users";
					int adminOpt = 0;
					
					// Will continue until the user clicks back or the 'X' in the top right corner
					while (adminOpt	 == 0 || adminOpt == 1 || adminOpt == 2 || adminOpt == 3 || adminOpt == 4 || adminOpt == 5 || (adminOpt == 6 && type.equals("master")))
					{
						
						// Checks if the user is the master or not 
						// The master is the only person that can Manage User
						Object[] options2 = (type.equals("master") ? new Object[]{"Sort By Title", "Sort By Author", "Search", "Check in ", "Check out", "Emergency", masteropt, "Back"} : 
											new Object[]{"Sort By Title", "Sort By Author", "Search", "Check in ", "Check out", "Emergency", "Back"}); 
						
						adminOpt = JOptionPane.showOptionDialog( null, "What would you like to do?", "Administrator", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);
						
						// Sorts by title or author
						if (adminOpt  == 0 || adminOpt == 1)
						{
							String sortby = (adminOpt == 0 ? "title" : "author");
							library.books = library.mod.quickSort(library.books, sortby);
							String[][]rowData = library.printLibrary(false);
							main.printTable();
						}

						// Search
						if (adminOpt  == 2)
						{
							main.search();
						}
						
						// Check In
						if (adminOpt  == 3)
						{
							int result = 1;
							while(result == 1)
							{
								Object[] options1 = { "Submit", "Enter Another Book", "Cancel" };
								
								// Ask for the title of the book that is being checked in
								// and has the option to check in multiple books 
								JFrame frame = new JFrame();
								JPanel panel = new JPanel();
								panel.add(new JLabel("Book Title:"));
								JTextField textField = new JTextField(10);
								panel.add(textField);

								result = JOptionPane.showOptionDialog(null, panel, "Administrator", 
										 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
										 null, options1, null);
								
								if(result != 2)
								{
									
									String input = textField.getText();
									
									if(library.Contains("title", input, library.books))
									{
										if(library.books.GetValue().getStatus() == 0) 
											library.returnBook(input);		
										
										else JOptionPane.showMessageDialog(frame, "Book Already Checked In!");
									}
									
									else
										JOptionPane.showMessageDialog(frame, "Book Does Not Exist!");
									
									int size = library.getStackSize();
									
									if(result == 0 && size != 0)
									{
										library.check("in");
										JOptionPane.showMessageDialog(frame, (size + " Book(s) Successfully Came Back To Jail."));
									}
								}
							}
						}
					
						
						// Check Out
						else if (adminOpt  == 4)
						{	
							JFrame frame = new JFrame();
							
							// Ask the user for the name of the person who is checking the book out
							String owner = JOptionPane.showInputDialog(frame, "Enter Customer's Full Name: ");
							if(owner != null) if(owner.split(" ").length == 2)
							{
								int result = 1;
								while(result == 1)
								{
									Object[] options1 = { "Submit", "Enter Another Book", "Cancel" };
									JPanel panel = new JPanel();
									
									// Ask the user for the book title
									panel.add(new JLabel(" Book Title:"));
									
									JTextField textField = new JTextField(10);
									panel.add(textField);

									result = JOptionPane.showOptionDialog(null, panel, "Administrator", 
											 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
											 null, options1, null);
									
									if(result != 2)
									{
										String input = textField.getText();
										
										if(library.Contains("title", input, library.books))
										{
											if(library.books.GetValue().getStatus() == 1) 
												library.returnBook(input);		
											
											else JOptionPane.showMessageDialog(frame, "Book Already Checked Out!");
										}
										
										else JOptionPane.showMessageDialog(frame, "Book Does Not Exist!");
										
										int size = library.getStackSize();
										
										if(result == 0 && size != 0)
										{
											library.check("out", credentials.staff, owner);
											JOptionPane.showMessageDialog(frame, (size + " Book(s) Successfully Left The Building."));
										}
									}
								}
							}
							else if(owner != null) JOptionPane.showMessageDialog(frame, "You Did Not Enter a Full Name!");
						}

						// If admin clicked the emergency
						// Sorts all checked in books by importance and stores it 
						// to booksCopy then exits the program
						else if (adminOpt  == 5)
						{
							main.printTable(library.Emergency(), 1);
						}
						
						// The master is the only one that can manage the users
						else if(adminOpt == 6 && type.equals("master"))
						{
							credentials.displayOptions();
						}
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
					if (studentOpt	== 0 || studentOpt == 1)
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
		library.endProgram();
	}

}
	


