package sodabase.services;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;

import javax.swing.JList;
import javax.swing.JOptionPane;
import java.awt.Choice;
import java.awt.Label;
import java.awt.TextArea;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PlayerWindow {

	private JFrame frame;
	private DatabaseConnectionService dbservice = null;
	private String firstName;
	private String lastName;
	private boolean game;
	private boolean season;
	private boolean career;
	private String year;
	private ArrayList<String> returnedList;
	private Choice choice;
	private TextArea textArea;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel curPanel;
	private int openSlot = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, DatabaseConnectionService dbService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayerWindow window = new PlayerWindow();
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
	public PlayerWindow() {
		initialize();
	}

	public void setService(DatabaseConnectionService dbService) {
		this.dbservice = dbService;
		System.out.println("player set " + dbservice);
	}

	public DatabaseConnectionService getService() {
		return this.dbservice;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 586, 425);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblSearchForPlayer = new JLabel("First Name:");
		lblSearchForPlayer.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchForPlayer.setBounds(12, 33, 85, 16);
		frame.getContentPane().add(lblSearchForPlayer);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblLastName.setBounds(222, 33, 85, 16);
		frame.getContentPane().add(lblLastName);

		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(459, 29, 97, 25);
		frame.getContentPane().add(btnSearch);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(93, 30, 117, 22);
		frame.getContentPane().add(formattedTextField);

		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(301, 30, 151, 22);
		frame.getContentPane().add(formattedTextField_1);
		
		panel_1 = new JPanel();
		panel_1.setBounds(12, 110, 165, 255);
		frame.getContentPane().add(panel_1);
		
		panel_2 = new JPanel();
		panel_2.setBounds(202, 110, 165, 255);
		frame.getContentPane().add(panel_2);
		
		panel_3 = new JPanel();
		panel_3.setBounds(389, 110, 165, 255);
		frame.getContentPane().add(panel_3);

		JRadioButton rdbtnGame = new JRadioButton("Game");
		rdbtnGame.setBounds(18, 72, 61, 25);
		frame.getContentPane().add(rdbtnGame);
		
		JRadioButton rdbtnSeason = new JRadioButton("Season");
		rdbtnSeason.setBounds(106, 72, 71, 25);
		frame.getContentPane().add(rdbtnSeason);
		
		JRadioButton rdbtnCareer = new JRadioButton("Career");
		rdbtnCareer.setBounds(202, 72, 76, 25);
		frame.getContentPane().add(rdbtnCareer);
		ButtonGroup group = new ButtonGroup();
		
		group.add(rdbtnGame);
		group.add(rdbtnSeason);
		group.add(rdbtnCareer);
		rdbtnGame.setSelected(true);
		
		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (openSlot == 1) curPanel = panel_1;
				else if (openSlot == 2) curPanel = panel_2;
				else if (openSlot == 3) curPanel = panel_3;
				else {
					JOptionPane.showMessageDialog(null, "You are at max view");
					return;
				}
				curPanel.removeAll();
				lastName = formattedTextField_1.getText();
				firstName = formattedTextField.getText();
				game = rdbtnGame.isSelected();
				season = rdbtnSeason.isSelected();
				career = rdbtnCareer.isSelected();
				
				if(firstName.isEmpty() || lastName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "You need to enter a first and last name");
					return;
				}
				
				if (game) {
					year = JOptionPane.showInputDialog(frame, "Enter a year (2000 - 2019)");
					if (year.isEmpty() || Integer.parseInt(year) < 2000 || Integer.parseInt(year) > 2019) {
						JOptionPane.showMessageDialog(null, "You didn't enter a valid year");
						return;
					}
				}
				
				if (game || season) {
					choice = new Choice();
					choice.setBounds(0, 30, 165, 22);
					curPanel.add(choice);
				}
			
				if (career) {
					textArea = new TextArea();
					textArea.setBounds(0, 30, 165, 129);
					textArea.setEditable(false);
					curPanel.add(textArea);
				}
				
				Label label = new Label(firstName + " " + lastName);
				label.setAlignment(Label.CENTER);
				label.setFont(new Font("Arial", Font.BOLD, 12));
				label.setBounds(0, 0, 165, 24);
				curPanel.add(label);
				
				callPlayerService();

			}
		});

		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.setBounds(283, 72, 97, 25);
		frame.getContentPane().add(btnClearAll);
		
		
		btnClearAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel_1.removeAll();
				panel_2.removeAll();
				panel_3.removeAll();
				openSlot = 1;
			}
		});

		JButton btnHomeButton = new JButton("Home");
		btnHomeButton.setBounds(12, 4, 67, 20);
		frame.getContentPane().add(btnHomeButton);
		
		JButton btnAddNew = new JButton("Add new player");
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openSlot++;
			}
		});
		btnAddNew.setBounds(398, 72, 126, 25);
		frame.getContentPane().add(btnAddNew);
		
		btnHomeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				NBADatabaseWindow newWin = new NBADatabaseWindow();
				newWin.main(new String[0], getService());
			}
		});

	}

	protected void callPlayerService() {
		// TODO Auto-generated method stub
		System.out.println(firstName + " " + lastName + " " + game);
		PlayerService playerServe = new PlayerService(this.dbservice);
		returnedList = playerServe.getPlayerInformation(firstName, lastName, game, season, career, (String) year);
		if (game || season) {
			for (int i = 0; i < returnedList.size(); i++) {
				choice.add(returnedList.get(i));
			}
		} else {
			String careerInfo = "";
			for (int i = 0; i < returnedList.size(); i++) {
				careerInfo += returnedList.get(i);
			}
			textArea.setText(careerInfo);
		}
	}
}
