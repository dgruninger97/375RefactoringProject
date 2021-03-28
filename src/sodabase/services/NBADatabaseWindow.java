package sodabase.services;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class NBADatabaseWindow {

	private JFrame frame;
	private DatabaseConnectionService dbService = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, DatabaseConnectionService dbService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NBADatabaseWindow window = new NBADatabaseWindow();
					window.setService(dbService);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NBADatabaseWindow() {
		initialize();
	}
	
	public void setService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}
	
	public DatabaseConnectionService getService() {
		return this.dbService;
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setupFramePane();
		displayPlayerButton();
		displayTeamButton();
		displayHomeScreenText();
	}

	private void displayPlayerButton() {
		JButton btnPlayers = new JButton("Players");
		btnPlayers.setBounds(62, 141, 97, 25);
		frame.getContentPane().add(btnPlayers);
		
		btnPlayers.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				PlayerWindow pw = new PlayerWindow();
				pw.main(new String[0], getService());
			}
		});
	}

	private void displayTeamButton() {
		JButton btnTeams = new JButton("Teams");
		btnTeams.setBounds(249, 141, 97, 25);
		frame.getContentPane().add(btnTeams);
		btnTeams.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				TeamWindow tw = new TeamWindow();
				tw.startWindow(new String[0], getService());
			}
		});
	}

	private void displayHomeScreenText() {
		JLabel lblWelcomeToThe = new JLabel("Welcome to the NBA Database!");
		lblWelcomeToThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToThe.setFont(new Font("Arial", Font.BOLD, 16));
		lblWelcomeToThe.setBounds(95, 27, 243, 16);
		frame.getContentPane().add(lblWelcomeToThe);
		JLabel lblWhatWouldYou = new JLabel("What would you like to compare?");
		lblWhatWouldYou.setFont(new Font("Arial", Font.PLAIN, 12));
		lblWhatWouldYou.setHorizontalAlignment(SwingConstants.CENTER);
		lblWhatWouldYou.setBounds(119, 93, 190, 16);
		frame.getContentPane().add(lblWhatWouldYou);
	}

	private void setupFramePane() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("NBA Database");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
}
