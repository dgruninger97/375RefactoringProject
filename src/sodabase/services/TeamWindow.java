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
	private static TeamService teamService;
	private JButton btnGo;
	private JButton backBtn;
	private int choiceIndex;
	private int buttonSelection = -1;

	/**
	 * Launch the application.
	 */
	public static void startWindow(DatabaseConnectionService newService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeamWindow window = new TeamWindow();
					window.setService(newService);
					window.frame.setVisible(true);
					teamService = new TeamService(newService);
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
		setUpFrame();
		JFormattedTextField formattedTextField = displayTeamSearchBox();
		JButton search_button = displaySearchButton();
		displayClearButton();
		setupPanels();
		setUpRadialButtons(formattedTextField, search_button);
		setUpHomeButton();
		setUpAddTeamButton();
	}

	private void setUpFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1225, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	private void setUpRadialButtons(JFormattedTextField formattedTextField, JButton search_button) {
		JRadioButton rdbtnGame = setUpGameRadialButton();
		JRadioButton rdbtnSeason = setUpSeasonRadialButton();
		JRadioButton rdbtnFranchise = setUpFranchiseRadialButton();
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnGame);
		group.add(rdbtnSeason);
		group.add(rdbtnFranchise);
		rdbtnGame.setSelected(true);

		search_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchButton(rdbtnGame, rdbtnSeason, rdbtnFranchise, formattedTextField);
				buttonSelection = -1;
			}

		});
	}

	private JRadioButton setUpFranchiseRadialButton() {
		JRadioButton rdbtnFranchise = new JRadioButton("Franchise");
		rdbtnFranchise.setBounds(202, 72, 97, 25);
		frame.getContentPane().add(rdbtnFranchise);
		return rdbtnFranchise;
	}

	private JRadioButton setUpSeasonRadialButton() {
		JRadioButton rdbtnSeason = new JRadioButton("Season");
		rdbtnSeason.setBounds(106, 72, 71, 25);
		frame.getContentPane().add(rdbtnSeason);
		return rdbtnSeason;
	}

	private JRadioButton setUpGameRadialButton() {
		JRadioButton rdbtnGame = new JRadioButton("Game");
		rdbtnGame.setBounds(18, 72, 61, 25);
		frame.getContentPane().add(rdbtnGame);
		return rdbtnGame;
	}

	private void setUpAddTeamButton() {
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

	private void setUpHomeButton() {
		JButton btnHomeButton = new JButton("Home");
		btnHomeButton.setBounds(12, 4, 67, 20);
		frame.getContentPane().add(btnHomeButton);

		btnHomeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
				NBADatabaseWindow newWin = new NBADatabaseWindow();
				newWin.startMainWindow(getService());
			}
		});
	}

	private void setupPanels() {
		panel_1 = new JPanel();
		panel_1.setBounds(5, 110, 400, 328);
		frame.getContentPane().add(panel_1);

		panel_2 = new JPanel();
		panel_2.setBounds(405, 110, 400, 328);
		frame.getContentPane().add(panel_2);

		panel_3 = new JPanel();
		panel_3.setBounds(810, 110, 400, 328);
		frame.getContentPane().add(panel_3);
	}

	private void displayClearButton() {
		JButton clear_button = new JButton("Clear All");
		clear_button.setBounds(544, 38, 97, 25);
		frame.getContentPane().add(clear_button);
		clear_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearWindow();
			}
		});
	}

	private JButton displaySearchButton() {
		JButton search_button = new JButton("Search");
		search_button.setBounds(371, 38, 136, 25);
		frame.getContentPane().add(search_button);
		return search_button;
	}

	private JFormattedTextField displayTeamSearchBox() {
		JLabel lblTeamName = new JLabel("Team Name:");
		lblTeamName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTeamName.setBounds(25, 42, 85, 16);
		frame.getContentPane().add(lblTeamName);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(122, 39, 237, 22);
		frame.getContentPane().add(formattedTextField);
		return formattedTextField;
	}

	protected void callTeamService(int methodType) {
		if (methodType == 1) {
			returnedList = teamService.getTeamInformation(teamName, game, season, franchise, (String) year, 0);
			if (returnedList == null) {
				JOptionPane.showMessageDialog(null, "Invalid Team, try again.");
				frame.dispose();
				startWindow(getService());
				return;
			}
			if (game || season) {
				for (int i = 0; i < returnedList.size(); i++) {
					choice.add(returnedList.get(i));
				}
				choiceIndex = choice.getSelectedIndex();
				return;
			}
		} else if (methodType == 2) {
			retrieveGameInfo();
		} else if (methodType == 3) {
			retrieveSeasonInfo();
		}
		retrieveCareerInfo();
	}

	private void retrieveCareerInfo() {
		String careerInfo = "";
		for (int i = 0; i < returnedList.size(); i++) {
			careerInfo += returnedList.get(i);
		}
		textArea.setText(careerInfo);
		Label label = new Label(teamName);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		curPanel.add(label);
	}

	private void retrieveSeasonInfo() {
		returnedList = teamService.getTeamSeasonInfo(teamName, choiceIndex);
		curPanel.removeAll();
		textArea = new TextArea();
		textArea.setBounds(0, 30, 310, 129);
		textArea.setEditable(false);
		curPanel.add(textArea);
	}

	private void retrieveGameInfo() {
		returnedList = teamService.getTeamGameInfo(teamName, year, choiceIndex);
		curPanel.removeAll();
		textArea = new TextArea();
		textArea.setBounds(0, 30, 310, 129);
		textArea.setEditable(false);
		curPanel.add(textArea);
	}

	public void backButton() {
		curPanel.removeAll();
		curPanel.add(btnGo);
		curPanel.add(choice);
		Label label = new Label(teamName);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		curPanel.add(label);
		curPanel.repaint();
	}

	public void goButton() {
		choiceIndex = choice.getSelectedIndex();
		if (game) {
			callTeamService(2);
		} else if (season) {
			callTeamService(3);
		}
		backBtn = new JButton("Back");
		backBtn.setBounds(310, 30, 80, 20);
		curPanel.add(backBtn);
		curPanel.repaint();
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backButton();
			}
		});
	}

	public void searchButton(JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnFranchise,
			JFormattedTextField formattedTextField) {
		if (buttonSelection == -1) {
			getSelectedButton(rdbtnGame, rdbtnSeason, rdbtnFranchise);
		} else {
			displayErrorMessage(rdbtnGame, rdbtnSeason, rdbtnFranchise);
			return;
		}
		setCurrentPanel();
		if (curPanel == null) {
			JOptionPane.showMessageDialog(null, "You are at max view");
			return;
		}
		getSearchValues(rdbtnGame, rdbtnSeason, rdbtnFranchise, formattedTextField);
		if (teamName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You need to enter a team name");
			return;
		}
		if (game) {
			year = JOptionPane.showInputDialog(frame, "Enter a year (2000 - 2019)");
			if (checkForGameYearErrors()) {
				return;
			}
		}
		if (game || season) {
			setUpChoiceWindow();
		}
		setUpFranchiseView();
		callTeamService(1);
	}

	private void getSearchValues(JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnFranchise,
			JFormattedTextField formattedTextField) {
		curPanel.removeAll();
		teamName = formattedTextField.getText();
		game = rdbtnGame.isSelected();
		season = rdbtnSeason.isSelected();
		franchise = rdbtnFranchise.isSelected();
	}

	private void setUpFranchiseView() {
		Label label = new Label(teamName);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		curPanel.add(label);
		if (franchise) {
			curPanel.repaint();
			textArea = new TextArea();
			textArea.setBounds(0, 30, 310, 129);
			textArea.setEditable(false);
			curPanel.add(textArea);
		}
	}

	private void setUpChoiceWindow() {
		choice = new Choice();
		choice.setBounds(0, 30, 300, 22);

		btnGo = new JButton("Go");
		btnGo.setBounds(310, 30, 50, 20);

		btnGo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				goButton();
			}
		});
		curPanel.add(choice);
		curPanel.add(btnGo);
		curPanel.repaint();
	}

	private boolean checkForGameYearErrors() {
		if (year == null || year.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You didn't enter a valid year");
			return true;
		}
		for (int i = 0; i < year.length(); i++) {
			if (!Character.isDigit(year.charAt(i))) {
				JOptionPane.showMessageDialog(null, "You didn't enter a valid year");
				return true;
			}
		}
		if (Integer.parseInt(year) < 2000 || Integer.parseInt(year) > 2019) {
			JOptionPane.showMessageDialog(null, "You didn't enter a valid year");
			return true;
		}
		return false;
	}

	private void setCurrentPanel() {
		if (openSlot == 1) {
			curPanel = panel_1;
		} else if (openSlot == 2) {
			curPanel = panel_2;
		} else if (openSlot == 3) {
			curPanel = panel_3;
		} else {
			curPanel = null;
		}
	}

	private void displayErrorMessage(JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnFranchise) {
		if (buttonSelection == 1 && (rdbtnFranchise.isSelected() || rdbtnSeason.isSelected())) {
			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
		} else if (buttonSelection == 2 && (rdbtnFranchise.isSelected() || rdbtnGame.isSelected())) {
			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
		} else if (buttonSelection == 3 && (rdbtnSeason.isSelected() || rdbtnGame.isSelected())) {
			JOptionPane.showMessageDialog(null, "You must select the same comparison option");
		}
	}

	private void getSelectedButton(JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnFranchise) {
		if (rdbtnGame.isSelected()) {
			buttonSelection = 1;
		}
		if (rdbtnSeason.isSelected()) {
			buttonSelection = 2;
		}
		if (rdbtnFranchise.isSelected()) {
			buttonSelection = 3;
		}
	}

	public void clearWindow() {
		panel_1.removeAll();
		panel_2.removeAll();
		panel_3.removeAll();
		panel_1.repaint();
		panel_2.repaint();
		panel_3.repaint();
		openSlot = 1;
		buttonSelection = -1;
	}

}
