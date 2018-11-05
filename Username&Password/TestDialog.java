package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TestDialog {

	static int remove_user = 0;
	static String masterUser = "Lori";
	static String masterPassword = "Jacques";
	static String[] usernameList = new String[10];
	static String[] passwordList = new String[10];

	public static void main(String[] args) throws FileNotFoundException {

		username_password();
	}

	/**
	 * @throws FileNotFoundException
	 */
	private static void username_password() throws FileNotFoundException {
		int loop = 0;
		File file = new File(
				"C:\\Users\\Joshw\\Desktop\\Computer Science\\Data structure\\test\\src\\test\\Username&Password");

		Scanner sc = new Scanner(file);
		while (sc.hasNext()) {
			String[] makeList = sc.nextLine().split(" ");

			if (makeList[0] != null) {
				passwordList[loop] = makeList[1];
				usernameList[loop] = makeList[0];
			}
			loop += 1;
		}

		sc.close();
		boolean master = false;
		boolean correct = false;
		int loginOpt = 0;

		while (loginOpt == 0) {
			JTextField username = new JTextField();
			JTextField password = new JPasswordField();
			Object[] loginMessage = { "Username:", username, "Password:", password };
			loginOpt = JOptionPane.showConfirmDialog(null, loginMessage, "Login", JOptionPane.OK_CANCEL_OPTION);
			if (loginOpt == JOptionPane.OK_OPTION) {

				// makes sure they entered something
				if (!username.getText().equals("") && !password.getText().equals("")) {

					// checks if the user name and password is correct
					for (int i = 0; i < usernameList.length; i++) {
						if (username.getText().equals(masterUser) && password.getText().equals(masterPassword)) {
							master = true;
							correct = true;
							break;
						} else if (username.getText().equals(usernameList[i])
								&& password.getText().equals(passwordList[i])) {
							correct = true;

							break;
						}
						if (usernameList[i] == null) {
							break;

						}
					}

					if (correct) {
						int masterOptResult = 0;
						int notMasterOPtResult = 0;
						while ((masterOptResult == 0 || masterOptResult == 1)
								&& (notMasterOPtResult == 0 || notMasterOPtResult == 1)) {

							// if the user name and password is the master it will go here
							if (master) {
								Object[] masterOPt = { "Add a new user", "Remove user", "No, thanks", "back" };
								masterOptResult = JOptionPane.showOptionDialog(null, "Welcome Master", "Administrater",
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, masterOPt,
										masterOPt[0]);
								if (masterOptResult == 0) {
									addNewUser();
									updateUser();
								} else if (masterOptResult == 1) {
									removeUser();
									updateUser();
								}

							}

							// if not the master it will go here
							else if (!master) {
								Object[] notMasterOPt = { "Other options", "No, thanks", "Back" };
								notMasterOPtResult = JOptionPane.showOptionDialog(null, "Welcome", "Administrater",
										JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
										notMasterOPt, notMasterOPt[0]);
							}
						}
					}

					// test
					if (correct) {
						correct = false;
						master = false;
					} else
						JOptionPane.showMessageDialog(null, "Username or password was incorrect");
				} else
					System.out.println("Login canceled");
			}
		}
	}

	/**
	 * @throws FileNotFoundException
	 */
	private static void updateUser() throws FileNotFoundException {
		String print_file = "";
		for (int j = 0; j < usernameList.length; j++) {

			if (usernameList[j] != null) {
				print_file += (usernameList[j] + " ");
				print_file += (passwordList[j] + "\n");
			}
		}
		String fileName = "C:\\Users\\Joshw\\Desktop\\Computer Science\\Data structure\\test\\src\\test\\Username&Password";
		PrintWriter outputStream = new PrintWriter(fileName);
		outputStream.println(print_file);
		outputStream.close();
	}

	/**
	 * 
	 */
	private static void removeUser() {
		int remove_loginOpt = 0;
		JTextField remove_username = new JTextField();
		JTextField remove_password = new JPasswordField();
		Object[] remove_loginMessage = { "Username:", remove_username, "Password:", remove_password };
		remove_loginOpt = JOptionPane.showConfirmDialog(null, remove_loginMessage, "Login",
				JOptionPane.OK_CANCEL_OPTION);
		if (remove_loginOpt == JOptionPane.OK_OPTION) {

			// makes sure they entered something
			if (!remove_username.getText().equals("") && !remove_password.getText().equals("")) {

				// checks if the user name and password is correct

				if (remove_username.getText().equals(masterUser) && remove_password.getText().equals(masterPassword)) {
					while (remove_user == 0) {
						String enteredUsername = JOptionPane
								.showInputDialog("What is the username that you would like to remove?");

						if (enteredUsername == null)
							remove_user = 1;
						else if (!enteredUsername.equals("")) {
							for (int j = 0; j < usernameList.length; j++) {
								if (usernameList[j].equals(enteredUsername)) {
									usernameList[j] = null;
									passwordList[j] = null;
									int answer = JOptionPane.showConfirmDialog(null,
											"Would you like to remove another user?", "Master",
											JOptionPane.YES_NO_OPTION);

									if (answer != 0) {
										remove_user = 1;
									}
									break;
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

	private static void addNewUser() {

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
