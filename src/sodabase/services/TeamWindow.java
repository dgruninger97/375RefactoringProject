package sodabase.services;

import java.awt.Choice;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class TeamWindow {

	private JFrame frame;
	private String teamName;
	private boolean game;
	private boolean season;
	private boolean franchise;
	private String year;
	private Choice choice;
	private TextArea textArea;
	private DatabaseConnectionService dbService;
	private ArrayList<String> returnedList;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel curPanel;
	private int openSlot = 1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args, DatabaseConnectionService newService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeamWindow window = new TeamWindow();
					window.setService(newService);
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
	public TeamWindow() {
		initialize();
	}
	
	public void setService(DatabaseConnectionService newService) {
		this.dbService = newService;
	}
	
	public DatabaseConnectionService getService() {
		return this.dbService;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 585, 426);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblTeamName = new JLabel("Team Name:");
		lblTeamName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTeamName.setBounds(12, 42, 85, 16);
		frame.getContentPane().add(lblTeamName);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(109, 39, 193, 22);
		frame.getContentPane().add(formattedTextField);

		JButton button = new JButton("Search");
		button.setBounds(314, 38, 136, 25);
		frame.getContentPane().add(button);

		JButton button_1 = new JButton("Clear All");
		button_1.setBounds(459, 38, 97, 25);
		frame.getContentPane().add(button_1);
		
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panel_1.removeAll();
			}
		});
		
		panel_1 = new JPanel();
		panel_1.setBounds(12, 106, 165, 259);
		frame.getContentPane().add(panel_1);
		
		panel_2 = new JPanel();
		panel_2.setBounds(202, 106, 165, 259);
		frame.getContentPane().add(panel_2);
		
		panel_3 = new JPanel();
		panel_3.setBounds(391, 106, 165, 259);
		frame.getContentPane().add(panel_3);
		
		JRadioButton rdbtnGame = new JRadioButton("Game");
		rdbtnGame.setBounds(18, 72, 61, 25);
		frame.getContentPane().add(rdbtnGame);
		
		JRadioButton rdbtnSeason = new JRadioButton("Season");
		rdbtnSeason.setBounds(106, 72, 71, 25);
		frame.getContentPane().add(rdbtnSeason);
		
		JRadioButton rdbtnFranchise = new JRadioButton("Franchise");
		rdbtnFranchise.setBounds(202, 72, 97, 25);
		frame.getContentPane().add(rdbtnFranchise);
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnGame);
		group.add(rdbtnSeason);
		group.add(rdbtnFranchise);
		rdbtnGame.setSelected(true);
		
		button.addActionListener(new ActionListener() {

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
				teamName = formattedTextField.getText();
				game = rdbtnGame.isSelected();
				season = rdbtnSeason.isSelected();
				franchise = rdbtnFranchise.isSelected();
				
				if(teamName.isEmpty()) {
					JOptionPane.showMessageDialog(null, "You need to enter a team name");
					return;
				}
				if (game) {
					year = JOptionPane.showInputDialog(frame, "Enter a year (2000 - 2019)");
					if(year.isEmpty() || Integer.parseInt(year) < 2000 || Integer.parseInt(year) > 2019) {
						JOptionPane.showMessageDialog(null, "You didn't enter a valid year");
						return;
					}
				}
				if (game || season) {
					choice = new Choice();
					choice.setBounds(0, 30, 165, 22);
					curPanel.add(choice);
				}

				Label label = new Label(teamName);
				label.setAlignment(Label.CENTER);
				label.setFont(new Font("Arial", Font.BOLD, 12));
				label.setBounds(0, 0, 165, 24);
				curPanel.add(label);
				if (franchise) {
					textArea = new TextArea();
					textArea.setBounds(0, 30, 165, 129);
					textArea.setEditable(false);
					curPanel.add(textArea);
				}
				
				callTeamService();

			}

		});
		JButton btnHomeButton = new JButton("Home");
		btnHomeButton.setBounds(12, 4, 67, 20);
		frame.getContentPane().add(btnHomeButton);
		
		btnHomeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				NBADatabaseWindow newWin = new NBADatabaseWindow();
				newWin.main(new String[0], getService());
			}
		});
		
		JButton btnAddNewTeam = new JButton("Add new Team");
		btnAddNewTeam.setBounds(314, 72, 136, 25);
		frame.getContentPane().add(btnAddNewTeam);
		btnAddNewTeam.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openSlot++;
			}
		});
	}

	protected void callTeamService() {
		// TODO Auto-generated method stub
		TeamService teamService = new TeamService(this.dbService);
		returnedList = teamService.getTeamInformation(teamName, game, season, franchise, (String) year);
		if (game || season) {
			for (int i = 0; i < returnedList.size(); i++) {
				choice.add(returnedList.get(i));
			}
		} else {
			String franchiseInfo = "";
			for (int i = 0; i < returnedList.size(); i++) {
				franchiseInfo += returnedList.get(i);
			}
			textArea.setText(franchiseInfo);
		}
	}
}
