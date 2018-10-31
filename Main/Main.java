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
	
	
	public static void main(String[] args) throws FileNotFoundException
	{
		Library library = new Library();
		Scanner file = new Scanner(new File("books.txt"));
		
		
		while(file.hasNextLine())
		{
			String line = file.nextLine();
			String[] split = line.split(", ");
			library.books.InsertAfter(new Book(split[0], split[1], Integer.parseInt(split[2]), Integer.parseInt(split[3])));
		}

		file.close();
		
		int n = 0;
		while (n == 0 || n == 1)
		{
			Object[] options = {"Administrater", "Student", "Exit Library"};
			n = JOptionPane.showOptionDialog( null, "Please select", "SciFiL", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);	
			if (n == 0)
			{
				int a = 0;
				while (a == 0 || a == 1 || a == 2 || a == 3 || a == 4)
				{
					Object[] options2 = {"Sort","Search", "Check in ", "Check out", "Emergancy", "Back"};
					a = JOptionPane.showOptionDialog( null, "What would you like to do?", "Administrater", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);
					if (a == 0)
					{
						Object[] Sort_options = {"Title", "Author"};
						int i = JOptionPane.showOptionDialog( null, "How would you like it sorted?", "Administrater", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, Sort_options, Sort_options[0]);
						if (i == 0)
						{
							
							String[][]rowData = library.printLibrary(false);
							Object[]colName = {"Author","Title","Status","Importance"};
								JTable table = new JTable(rowData, colName);
								javax.swing.table.TableColumn column = null;
								for (int b = 0; b < 4; b++) 
								{
									column = table.getColumnModel().getColumn(b);
									if (b == 0) 
									{
										column.setPreferredWidth(library.largestTitle.length() + 100); //First column is bigger
									} 
									else if (b == 1)
									{
										column.setPreferredWidth(library.largestAuthor.length() + 100); //Second column
									}
									else 
									{
										column.setPreferredWidth(50);
									}
								}
								JOptionPane.showMessageDialog(null, new JScrollPane(table));
						}
						else if (i == 1)
						{
							String Title = "The house on a hill";
							String Author = "John Smith";
							String Status = "IN";
							int Importance = 12;
							Object[][] rows = {
									{Title,Author,Status,Importance}
								};
								Object[] cols = {"Author","Title","Status","Importance"};
								JTable table = new JTable(rows, cols);
								javax.swing.table.TableColumn column = null;
								for (int b = 0; b < 4; b++) 
								{
									column = table.getColumnModel().getColumn(b);
									if (b == 0) 
									{
										column.setPreferredWidth(150); //First column is bigger
									} 
									else if (b == 1)
									{
										column.setPreferredWidth(100); //Second column
									}
									else 
									{
										column.setPreferredWidth(50);
									}
								}
								JOptionPane.showMessageDialog(null, new JScrollPane(table));
						}
						else;
							
					
					}

					if (a == 1)
					{
						  JTextField Title = new JTextField(5);
						  JTextField Author = new JTextField(5);

						  JPanel myPanel = new JPanel();
						  myPanel.add(new JLabel("Title:"));
						  myPanel.add(Title);
						  myPanel.add(Box.createHorizontalStrut(15)); // a spacer
						  myPanel.add(new JLabel("Author:"));
						  myPanel.add(Author);

						  int result = JOptionPane.showConfirmDialog(null, myPanel, 
								   "Please enter one below.", JOptionPane.OK_CANCEL_OPTION);
						  if (result == JOptionPane.OK_OPTION) {
							 System.out.println(Title.getText());
							 System.out.println(Author.getText());
						  }
					}	
					if (a == 2)
					{
						String input = JOptionPane.showInputDialog("Enter book title");
						JFrame frame = new JFrame();
						int result = JOptionPane.showConfirmDialog(frame, "Are you sure?");
						System.out.print(result);
					}
					else if (a == 3)
					{	
						String input = JOptionPane.showInputDialog("Enter book title");
						JFrame frame = new JFrame();
						int result = JOptionPane.showConfirmDialog(frame, "Are you sure?");
						System.out.print(result);


					
					}
					else if (a == 4)
					{
						System.out.println("FIRE");
					}
				}
			}
			
			else if (n == 1) 
			{
				Object[] options3 = {"Sort A-Z","Search", "Back"};
				int c = JOptionPane.showOptionDialog( null, "What would you like to do?", "SciFiL", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options3, options3[1]);
				
			
				if (c == 0)
				{
					System.out.println(0);
				}
				else if(c == 1)
				{
								
					JTextField Title = new JTextField(5);
					JTextField Author = new JTextField(5);
					
					JPanel myPanel = new JPanel();
					myPanel.add(new JLabel("Title:"));
					myPanel.add(Title);
					myPanel.add(Box.createHorizontalStrut(15)); // a spacer
					myPanel.add(new JLabel("Author:"));
					myPanel.add(Author);
					
					int result = JOptionPane.showConfirmDialog(null, myPanel, "Please enter one below.", JOptionPane.OK_CANCEL_OPTION);
					
					if (result == JOptionPane.OK_OPTION) 
					{
						 System.out.println(Title.getText());
						 System.out.println(Author.getText());
					}
				}
			}		
		}
		System.exit(0);
	}
}
	


