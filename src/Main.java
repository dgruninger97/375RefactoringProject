import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

import sodabase.services.DatabaseConnectionService;
import sodabase.services.NBADatabaseWindow;
import sodabase.services.PlayerService;
import sodabase.ui.ApplicationRunner;

public class Main {

	public static void main(String[] args) {
//		ApplicationRunner appRunner = new ApplicationRunner();
//		appRunner.runApplication(args);
		DatabaseConnectionService dbService = new DatabaseConnectionService("golem.csse.rose-hulman.edu", "NBADatabase19");
		dbService.connect("NBADatabase19", "Password123");
		NBADatabaseWindow mainWin = new NBADatabaseWindow();
		mainWin.main(args, dbService);
	}

}
