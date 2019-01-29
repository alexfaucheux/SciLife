package UserManagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.*;
import java.awt.Dimension;

public class UserPassword 
{
	String[] masterUser = new String[2];
	String[] usernameList = new String[10];
	String[] passwordList = new String[10];
	public String staff;

	/* updateUser		addUser
	   updateMaster		removeUser */
	   
	public String username_password() throws FileNotFoundException {
		int loop = 0;
		File file = new File("Username&Password");
		File file2 = new File("MasterUser");
		Scanner sc = new Scanner(file);
		Scanner sc1 = new Scanner(file2);

		masterUser = sc1.nextLine().split(" ");
		
		// Makes password and username array
		while (sc.hasNext())
		{
			String[] makeList = sc.nextLine().split(" ");

			if (makeList[0] != null) {
				passwordList[loop] = makeList[1];
				usernameList[loop] = makeList[0];
			}
			loop += 1;
		}
		
		sc1.close();
		sc.close();
		boolean master = false;
		boolean correct = false;
		int loginOpt = 0;

		// Loops when username and password combination does not exist
		while (loginOpt == 0) 
		{
			JTextField username = new JTextField();
			JTextField password = new JPasswordField();
			Object[] loginMessage = { "Username:", username, "Password:", password };
			loginOpt = JOptionPane.showConfirmDialog(null, loginMessage, "Login", JOptionPane.OK_CANCEL_OPTION);
			
			// If user clicks OK
			if (loginOpt == JOptionPane.OK_OPTION) {

				// makes sure they entered something
				if (!username.getText().equals("") && !password.getText().equals("")) {

					// checks if the user name and password is correct
					for (int i = 0; i < usernameList.length; i++) 
					{
						if (username.getText().equals(masterUser[0]) && password.getText().equals(masterUser[1])) {
							staff = masterUser[0];
							master = true;
							correct = true;
							break;
						} 
						
						else if (username.getText().equals(usernameList[i]) && password.getText().equals(passwordList[i])) 
						{
							staff = usernameList[i];
							correct = true;
							break;
						}
						
						if (usernameList[i] == null) 
						{
							break;
						}
					}
					
					if (correct && master) return "master";
					else if(correct && !master) return "admin";
					
					else JOptionPane.showMessageDialog(null, "Username or password was incorrect");
				}
					
			}

			else return "cancel";
					
		} 
		return "";	
	}

		
	
	// Runs if the user name and password is the master login
	// and if the master user clicks "Manage Users"
	public void displayOptions() throws FileNotFoundException
	{
		int masterOptResult = 0;
		while (masterOptResult == 0 || masterOptResult == 1) {

			
			Object[] masterOPt = { "Add a new user", "Remove user", "Display Users", "Back" };
			masterOptResult = JOptionPane.showOptionDialog(null, "Welcome Master", "Administrater",
							  JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, masterOPt,
							  masterOPt[0]);
			
			// Add user
			if (masterOptResult == 0) 
			{
				addNewUser();
				updateUser();
			} 
			
			// Remove User
			else if (masterOptResult == 1) 
			{
				removeUser();
				updateUser();
			}
			
			// Display Users
			else if(masterOptResult == 2)
			{
				Object[][] rowData = new Object[usernameList.length][1];
				
				for(int i=0; i<rowData.length; i++)
				{
					rowData[i][0] = usernameList[i];
				}
				
				Object[]colName = {"Users"};
				JTable table = new JTable(rowData, colName);
				javax.swing.table.TableColumn column = null;
				
				
				column = table.getColumnModel().getColumn(0);
				
				column.setPreferredWidth(250);
					
				
				UIManager.put("OptionPane.minimumSize", new Dimension(100, 0));
				JOptionPane.showMessageDialog(null, new JScrollPane(table));
				UIManager.put("OptionPane.minimumSize", new Dimension(100, 100));
			}
		}
	}		

							
	// Update user file if anything is added or removed
	public void updateUser() throws FileNotFoundException {
		String print_file = "";
		for (int j = 0; j < usernameList.length; j++) {

			if (usernameList[j] != null) {
				print_file += (usernameList[j] + " ");
				print_file += (passwordList[j] + "\n");
			}
		}
		String fileName = "Username&Password";
		PrintWriter outputStream = new PrintWriter(fileName);
		outputStream.println(print_file);
		outputStream.close();
	}
	
	// For updating arrays for username or password
	public void updateList(String[] list)
	{ 
		int k = -1;
		for(int i=0; i<list.length; i++)
		{
			if(list[i] == null) k = i;
			if(list[i] != null && k != -1){ list[k] = list[i]; list[i] = null; k = i;}
		}
	}
			
	
	// For updating master
	public void updateMaster() throws FileNotFoundException {
		String print_file = (masterUser[0] + " " + masterUser[1]);
		String fileName = "MasterUser";
		PrintWriter outputStream = new PrintWriter(fileName);
		outputStream.println(print_file);
		outputStream.close();
	}
	
	// Remove a user, only ran by Master User with "Manage Users" option
	public void removeUser() throws FileNotFoundException {
		int remove_loginOpt = 0;
		JTextField remove_username = new JTextField();
		JTextField remove_password = new JPasswordField();
		
		Object[] remove_loginMessage = { "Username:", remove_username, "Password:", remove_password };
		remove_loginOpt = JOptionPane.showConfirmDialog(null, remove_loginMessage, " Master Login", JOptionPane.OK_CANCEL_OPTION);
				
		if (remove_loginOpt == JOptionPane.OK_OPTION) {

			// makes sure they entered something
			if (!remove_username.getText().equals("") && !remove_password.getText().equals("")) 
			{
				// checks if the user name and password is correct
				if (remove_username.getText().equals(masterUser[0]) && remove_password.getText().equals(masterUser[1])) 
				{
					int remove_user = 0;
					while (remove_user == 0) 
					{
						String enteredUsername = JOptionPane.showInputDialog("What is the username that you would like to remove?");

						if (enteredUsername == null)
							break;
						
						else if (!enteredUsername.equals(""))
						{
							// If regular user was entered to be "removed"
							// Master username & password is requested once more
							// and user is removed if the user exists.
							if (!enteredUsername.equals(masterUser[0])) 
							{
								boolean remove = false;
								for (int j = 0; j < usernameList.length; j++) 
								{
									if (usernameList[j] != null) 
									{
										if (usernameList[j].equals(enteredUsername)) 
										{
											usernameList[j] = null;
											passwordList[j] = null;
											remove = true;
											updateList(usernameList);
											updateList(passwordList);
											int answer = JOptionPane.showConfirmDialog(null,
													"Would you like to remove another user?", "Master",
													JOptionPane.YES_NO_OPTION);

											if (answer == 1) 
											{
												remove_user = 1;
											}
											break;
										}
									}
									else break;
								}
								if (!remove) {
									JOptionPane.showMessageDialog(null,
											"The username does not exist. \nPlease try again");
								}
							} 
							
							// If master user was entered to be "removed"
							// the user is prompted to add a user and make it
							// the new master and former master is deleted
							else if (enteredUsername.equals(masterUser[0])) {
								int areUsure = JOptionPane.showConfirmDialog(null,
										"Are you sure you want to change the master user?", "Master",
										JOptionPane.YES_NO_OPTION);
								boolean stop = false;
								while (!stop) {
									if (areUsure == 0) {
										JTextField addUserName = new JTextField();
										JTextField addUserPassword = new JPasswordField();
										JTextField confirmPassword = new JPasswordField();
										Object[] addUserMessage = { "New Username:", addUserName, " New Password:",
												addUserPassword, "Confirm Password", confirmPassword };
										int changeMaster = JOptionPane.showConfirmDialog(null, addUserMessage, "Master",
												JOptionPane.OK_CANCEL_OPTION);

										if (changeMaster == 0) {
											if (!addUserName.getText().equals("")
													&& !addUserPassword.getText().equals("")
													&& !confirmPassword.getText().equals("")) {
												// makes sure the password and the confirm password is the same
												if (addUserPassword.getText().equals(confirmPassword.getText())) {
													masterUser[0] = addUserName.getText();
													masterUser[1] = addUserPassword.getText();
													updateMaster();
													stop = true;
													JOptionPane.showMessageDialog(null, "New user successfully added.",
															null, JOptionPane.WARNING_MESSAGE);
												} else {
													JOptionPane.showMessageDialog(null,
															"Passwords did not match please try again.");
												}
											} else {
												JOptionPane.showMessageDialog(null,
														"Nothing was entered please try again");
											}
										} else {
											stop = true;
										}

									} else {
										stop = true;
									}
								}
							}
						} else if (enteredUsername.equals(""))
							JOptionPane.showMessageDialog(null, "Nothing was entered please try again");
					}
					remove_user = 0;
				} else {
					JOptionPane.showMessageDialog(null,
							"The master username or password was incorrect \nPlease try again");
					removeUser();
				}

			} else {
				JOptionPane.showMessageDialog(null, "Nothing was entered please try again");
				removeUser();
			}
		}
	}

	// Add a user, will only run by Master User by "Manage Users" option
	public void addNewUser() {

		// if the list of user's are full it will change to true and not allow you to
		// add another
		boolean maxAdmin = false;

		// checks if the user name list is full
		for (int i = 0; i < usernameList.length; i++) {
			if (i == usernameList.length - 1 && usernameList[i] != null) {
				JOptionPane.showMessageDialog(null, "You are at the max amount of user. You cant add anymore.", null,
						JOptionPane.WARNING_MESSAGE);
				maxAdmin = true;
			}
		}

		boolean dittoPassword = false;

		// if it is not full it asks for a new user name and password
		while (!dittoPassword) {
			if (!maxAdmin) {
				JTextField addUserName = new JTextField();
				JTextField addUserPassword = new JPasswordField();
				JTextField confirmPassword = new JPasswordField();
				Object[] addUserMessage = { "Username:", addUserName, "Password:", addUserPassword, "Confirm Password",
						confirmPassword };
				int addUserOpt = JOptionPane.showConfirmDialog(null, addUserMessage, "Login",
						JOptionPane.OK_CANCEL_OPTION);
				if (addUserOpt == 0) {

					if (!addUserName.getText().equals("") && !addUserPassword.getText().equals("")
							&& !confirmPassword.getText().equals("")) {
						// makes sure the password and the confirm password is the same
						if (addUserPassword.getText().equals(confirmPassword.getText())) {

							// makes user the user name is not already in the usernameList
							for (int i = 0; i < usernameList.length; i++) {
								dittoPassword = true;
								if (usernameList[i] != null && usernameList[i].equals(addUserName.getText())) {
									JOptionPane.showMessageDialog(null,
											"User Name already exist.\nPlease choose another username.", "New User",
											JOptionPane.WARNING_MESSAGE);
									addNewUser();

								}
								// if the user name is not in the usernameList already it adds the new user name
								// to the List
								else if (usernameList[i] == null) {
									int areUsure = JOptionPane.showConfirmDialog(null,
											"Are you sure you want to add this user?", "Master",
											JOptionPane.YES_NO_OPTION);
									if (areUsure == 0) {
										passwordList[i] = confirmPassword.getText();
										usernameList[i] = addUserName.getText();

										JOptionPane.showMessageDialog(null, "New user successfully added.", null,
												JOptionPane.WARNING_MESSAGE);
									}
									break;
								}
							}

						} else {
							JOptionPane.showMessageDialog(null, "Passwords did not match please try again.");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Nothing was entered please try again");
					}
				} else {
					dittoPassword = true;
				}

			} else
				dittoPassword = true;
		}
	}
}
