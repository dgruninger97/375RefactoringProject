package Domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import presentationWindows.NBADatabaseWindow;

public class Main {

	public static void main(String[] args) {
		String serverName = "";
		String username = "";
		String password = "";
	    try {
	        File serverFile = new File("server.txt");
	        Scanner myReader = new Scanner(serverFile);
            serverName =  myReader.nextLine();
            myReader.close();
            File userFile = new File("username.txt");
            myReader = new Scanner(userFile);
            username = myReader.nextLine();
            myReader.close();
            File passFile = new File("password.txt");
            myReader = new Scanner(passFile);
            password = myReader.nextLine();
            myReader.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
		DatabaseConnectionService dbService = new DatabaseConnectionService(serverName,
				"TestDatabase");
		dbService.connect(username, password);
		NBADatabaseWindow mainWin = new NBADatabaseWindow();
		mainWin.startMainWindow(dbService);
	}

}
