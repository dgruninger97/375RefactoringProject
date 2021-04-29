package Domain;

import presentationWindows.NBADatabaseWindow;

public class Main {

	public static void main(String[] args) {
		DatabaseConnectionService dbService = new DatabaseConnectionService("nba-database.csse.rose-hulman.edu",
				"TestDatabase");
		dbService.connect("sa", "Thesquarerootoftheradiusofthesun123");
		NBADatabaseWindow mainWin = new NBADatabaseWindow();
		mainWin.startMainWindow(dbService);
	}

}
