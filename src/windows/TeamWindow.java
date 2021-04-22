package windows;

import java.awt.EventQueue;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import services.DatabaseConnectionService;
import services.TeamService;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFormattedTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class TeamWindow extends AbstractWindow {


	private String teamName;
	private boolean franchiseIsSelected;
	private static TeamService teamService;
	private int buttonSelection = -1;


	public void startWindow(DatabaseConnectionService dbService) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TeamWindow window = new TeamWindow();
					initializeServiceAndFrame(dbService, window);
					teamService = new TeamService(dbService);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			
		});
	}

	public TeamWindow() {
		initialize();
	}


	private void initialize() {
		setUpFrame();
		JFormattedTextField formattedTextField = displayTeamSearchBox();
		JButton search_button = createSearchButton();
		setUpClearButton();
		setupPanels();
		setUpRadialButtons(formattedTextField, search_button);
		setUpHomeButton();
		setUpAddTeamButton();
	}

	private void setUpRadialButtons(JFormattedTextField formattedTextField, JButton search_button) {
		JRadioButton rdbtnGame = setUpGameRadialButton();
		JRadioButton rdbtnSeason = setUpSeasonRadialButton();
		JRadioButton rdbtnFranchise = setUpOverallRadialButton();
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

	private void setUpAddTeamButton() {
		super.setupAddButton("Add New Team");
	}

	private JFormattedTextField displayTeamSearchBox() {
		JLabel lblTeamName = new JLabel("Team Name:");
		lblTeamName.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTeamName.setBounds(25, 42, 85, 16);
		super.getFrame().getContentPane().add(lblTeamName);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(122, 39, 237, 22);
		super.getFrame().getContentPane().add(formattedTextField);
		return formattedTextField;
	}
	@Override
	protected void callService(int methodType) {
		if (methodType == 1) {
			super.setReturnedList(teamService.getTeamInformation(teamName, super.getGameSelected(), super.getSeasonSelected(), franchiseIsSelected, super.getYear(), 0));
			if(checkInvalidEntry()) {
				super.clearWindow();
				return;
			}
			getAvailableYears();
			if (franchiseIsSelected) {
				super.displayInfo(teamName);
			}
			return;
		} else if (methodType == 2) {
			retrieveGameInfo();
		} else if (methodType == 3) {
			retrieveSeasonInfo();
		}
		else {
			retrieveOverallInfo();
		}
		super.displayInfo(teamName);
	}

	private void retrieveOverallInfo() {
		try {
			super.setReturnedList(teamService.getTeamFranchiseInfo(teamName));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void retrieveSeasonInfo() {
		try {
			super.setReturnedList(teamService.getTeamSeasonInfo(teamName, super.getSelectedIndex()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void retrieveGameInfo() {
		try {
			super.setReturnedList(teamService.getTeamGameInfo(teamName, super.getYear(), super.getSelectedIndex()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.displayInfo(teamName);
	}
	@Override
	protected void backButton() {
		super.backButton(teamName);
	}

	public void searchButton(JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnFranchise,
			JFormattedTextField formattedTextField) {
		if (buttonSelection == -1) {
			getSelectedButton(rdbtnGame, rdbtnSeason, rdbtnFranchise);
		}
		setCurrentPanelToOpenSlot();
		getSearchValues(rdbtnGame, rdbtnSeason, rdbtnFranchise, formattedTextField);
		if (teamName.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You need to enter a team name");
			return;
		}
		if(promptForYear()) {
			return;
		}
		callService(1);
	}

	private void getSearchValues(JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnFranchise,
			JFormattedTextField formattedTextField) {
		super.removeAllFromPanel();
		teamName = formattedTextField.getText();
		getGameAndSeasonSelections(rdbtnGame, rdbtnSeason);
		franchiseIsSelected = rdbtnFranchise.isSelected();
	}

}
