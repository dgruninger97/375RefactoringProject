import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import services.DatabaseConnectionService;
import services.PlayerService;
//import sodabase.ui.ApplicationRunner;
import windows.NBADatabaseWindow;

public class Main {

	public static void main(String[] args) {
		DatabaseConnectionService dbService = new DatabaseConnectionService("nba-database.csse.rose-hulman.edu", "TestDatabase");
		dbService.connect("sa", "Thesquarerootoftheradiusofthesun123");
		DataParser parser = new DataParser();
		parser.main(args, dbService);
		NBADatabaseWindow mainWin = new NBADatabaseWindow();
		mainWin.startMainWindow(dbService);
	}

}
