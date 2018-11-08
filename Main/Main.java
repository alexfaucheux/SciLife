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
	static List<String> title = new List<String>();
	static Library library = new Library();
	
	public void search() throws IOException
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
	public void printTable() throws IOException
	{
		printTable(null, null, 0);
	}
	
	//prints only one book
	public void printTable(Book name) throws IOException
	{
		printTable(name, null, 0);
	}
	
	//prints specified list of books
	public void printTable(List<Book> books) throws IOException
	{
		printTable(null, books, 0);
	}
	
	//prints table with color
	public void printTable(List<Book> books, int num) throws IOException
	{
		printTable(null, books, num);
	}
	
	
	public void printTable(Book name, List<Book> books, int num) throws IOException
	{
		String[][] rowData = null;
		
		//rowData contains all books in library
		if(name == null && books == null) 
			rowData = library.printLibrary(false);
		
		//rowData contains only one book
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
		
		//rowData contains list of books of certain author
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
		
		Object[]colName = {"Title","Author","Status","Importance","Owner","Staff"};
		JTable table = new JTable(rowData, colName);
		javax.swing.table.TableColumn column = null;
		
		for (int cell = 0; cell < 4; cell++) 
		{
			column = table.getColumnModel().getColumn(cell);
			
			if (cell == 0) column.setPreferredWidth(250); 
			else if (cell == 1) column.setPreferredWidth(100); 
			else column.setPreferredWidth(60);
			
		}
		
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
			UIManager.put("OptionPane.minimumSize", new Dimension(0, 0));
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		Main main = new Main();
		UserPassword credentials = new UserPassword();
		Scanner file = new Scanner(new File("booksCopy.txt"));
		Scanner names = new Scanner(new File("Names.txt"));
		
		
		while(file.hasNextLine())
		{
			String name = " ";
			String line = file.nextLine();
			String[] split = line.split(", ");
			if(Integer.parseInt(split[2]) == 0)
			{
				name = names.nextLine();
				//System.out.println(name);
			}
			
			library.books.InsertAfter(new Book(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3]), name, "Lori"));
		}
		
		names.close();
		file.close();
		
		int user = 0;
		while (user == 0 || user == 1)
		{
			Object[] options = {"Administrator", "Student", "Exit Library"};
			user = JOptionPane.showOptionDialog( null, "Please select", "SciFiLi", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);	
			
			//Administrator
			if (user == 0)
			{
				String masteropt = null;
				String type = credentials.username_password();
				if(!type.equals("cancel"))
				{
					if(type.equals("master")) masteropt = "Manage Users";
					int adminOpt = 0;
					while (adminOpt  == 0 || adminOpt == 1 || adminOpt == 2 || adminOpt == 3 || adminOpt == 4 || adminOpt == 5 || (adminOpt == 6 && type.equals("master")))
					{
						Object[] options2 = (type.equals("master") ? new Object[]{"Sort By Title", "Sort By Author", "Search", "Check in ", "Check out", "Emergency", masteropt, "Back"} : 
											new Object[]{"Sort By Title", "Sort By Author", "Search", "Check in ", "Check out", "Emergency", "Back"}); 
						//options2 = {"Sort By Title", "Sort By Author", "Search", "Check in ", "Check out", "Emergency", "Back"};}
						
						adminOpt = JOptionPane.showOptionDialog( null, "What would you like to do?", "Administrator", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);
						
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
							int result = 1;
							while(result == 1)
							{
								Object[] options1 = { "Submit", "Enter Another Book", "Cancel" };
								
								JFrame frame = new JFrame();
								JPanel panel = new JPanel();
								panel.add(new JLabel("Book Title:"));
								JTextField textField = new JTextField(10);
								panel.add(textField);

								result = JOptionPane.showOptionDialog(null, panel, "Administrator", 
										 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
										 null, options1, null);
								
								String input = textField.getText();
								
								
								
								if(library.Contains("title", input, library.books) && result != 2)
								{
									if(library.books.GetValue().getStatus() == 0) 
										library.returnBook(input);		
									
									else JOptionPane.showMessageDialog(frame, "Book Already Checked In!");
								}
								
								else if(result != 2)
									JOptionPane.showMessageDialog(frame, "Book Does Not Exist!");
								
								int size = library.getStackSize();
								if(result == 0 && size != 0)
								{
									library.check("in");
									JOptionPane.showMessageDialog(frame, (size + " Book(s) Successfully Came Back To Jail."));
								}
							}
						}
						
						//Check Out
						else if (adminOpt  == 4)
						{	
							int result = 1;
							while(result == 1)
							{
								Object[] options1 = { "Submit", "Enter Another Book", "Cancel" };
								
								JFrame frame = new JFrame();
								JPanel panel = new JPanel();
								panel.add(new JLabel("Book Title:"));
								JTextField textField = new JTextField(10);
								panel.add(textField);

								result = JOptionPane.showOptionDialog(null, panel, "Administrator", 
										 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
										 null, options1, null);
								
								String input = textField.getText();
								
								
								
								if(library.Contains("title", input, library.books) && result != 2)
								{
									if(library.books.GetValue().getStatus() == 1) 
										library.returnBook(input);		
									
									else JOptionPane.showMessageDialog(frame, "Book Already Checked Out!");
								}
								
								else if(result != 2)
									JOptionPane.showMessageDialog(frame, "Book Does Not Exist!");
								
								int size = library.getStackSize();
								if(result == 0 && size != 0)
								{
									library.check("out");
									JOptionPane.showMessageDialog(frame, (size + " Book(s) Successfully Left The Building"));
								}
							}


						
						}
						else if (adminOpt  == 5)
						{
							main.printTable(library.Emergency(), 1);
						}
						
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
		library.endProgram();
	}

}
	


