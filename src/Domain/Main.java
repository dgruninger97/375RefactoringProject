package Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import presentationWindows.NBADatabaseWindow;

public class Main {

	public static void main(String[] args) {
		String serverName = "";
		String username = "";
		String password = "";
		try {
			File serverFile = new File("serverConfiguration/server.txt");
			Scanner myReader = new Scanner(serverFile);
			serverName = myReader.nextLine();
			myReader.close();
			File userFile = new File("serverConfiguration/username.txt");
			myReader = new Scanner(userFile);
			username = myReader.nextLine();
			myReader.close();
			File passFile = new File("serverConfiguration/password.txt");
			myReader = new Scanner(passFile);
			password = myReader.nextLine();
			myReader.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"One or more of the configurations files do not exist, make sure the JAR file is in the correction directory");
			System.exit(0);
		}
		DatabaseConnectionService dbService = new DatabaseConnectionService(serverName, "TestDatabase");
		dbService.connect(username, password);
		NBADatabaseWindow mainWin = new NBADatabaseWindow();
		mainWin.startMainWindow(dbService);
	}

}
