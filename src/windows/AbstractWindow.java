package windows;

import java.awt.Choice;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import services.DatabaseConnectionService;

public abstract class AbstractWindow {
	private JFrame frame;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private int openSlot = 1;
	private JPanel curPanel;
	private int buttonSelection = -1;
	private JButton btnGo;
	private JButton backBtn;
	private TextArea textArea;
	private Choice choice;
	private ArrayList<String> returnedList;
	private DatabaseConnectionService dbService;
	private int choiceIndex;
	private boolean gameIsSelected;
	private boolean seasonIsSelected;
	private String year;
	
	public abstract void startWindow(DatabaseConnectionService dbService);
	
	protected abstract void backButton();
	
	protected void setupPanels() {
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
	protected void setCurrentPanel() {
		if (openSlot == 1)
			curPanel = panel_1;
		else if (openSlot == 2)
			curPanel = panel_2;
		else if (openSlot == 3)
			curPanel = panel_3;
		else {
			curPanel = null;
		}
	}
	
	protected void clearWindow() {
		panel_1.removeAll();
		panel_2.removeAll();
		panel_3.removeAll();
		panel_1.repaint();
		panel_2.repaint();
		panel_3.repaint();
		openSlot = 1;
		buttonSelection = -1;
	}
	protected void setupAddButton(String addText) {
		JButton btnAddNew = new JButton(addText);
		btnAddNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (openSlot >= 3) {
					JOptionPane.showMessageDialog(null, "You are at max view");
					return;
				} else {
					openSlot++;
				}
			}
		});
		btnAddNew.setBounds(497, 72, 126, 25);
		frame.getContentPane().add(btnAddNew);
	}
	protected void backButton(String labelText) {
		curPanel.removeAll();
		curPanel.add(btnGo);
		curPanel.add(choice);
		Label label = new Label(labelText);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		curPanel.add(label);
		curPanel.repaint();
	}
	
	protected void displayInfo() {
		curPanel.removeAll();
		textArea = new TextArea();
		textArea.setBounds(0, 30, 310, 129);
		textArea.setEditable(false);
		curPanel.add(textArea);
	}
	protected void retrieveCareerInfo(String labelText) {
		String careerInfo = "";
		for (int i = 0; i < returnedList.size(); i++) {
			careerInfo += returnedList.get(i);
		}
		textArea.setText(careerInfo);
		Label label = new Label(labelText);
		label.setAlignment(Label.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setBounds(0, 0, 165, 24);
		curPanel.add(label);
	}
	
	protected boolean checkInvalidEntry() {
		if (returnedList == null) {
			JOptionPane.showMessageDialog(null, "Invalid Entry, try again.");
			frame.dispose();
			startWindow(getService());
			return true;
		}
		return false;
	}
	protected void setUpChoiceWindow() {
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
	
	protected void paintBackButton() {
		curPanel.add(backBtn);
		curPanel.repaint();
	}
	
	protected void removeAllFromPanel() {
		curPanel.removeAll();
	}
	
	protected void addLabelToPanel(Label label) {
		curPanel.add(label);
	}
	
	protected void getAvailableYears() {
		if (gameIsSelected || seasonIsSelected) {
			for (int i = 0; i < returnedList.size(); i++) {
				choice.add(returnedList.get(i));
			}
			choiceIndex = choice.getSelectedIndex();
			return;
		}
	}
	
	protected int getSelectedIndex() {
		return choice.getSelectedIndex();
	}
	
	protected void setReturnedList(ArrayList<String> stringList) {
		returnedList = stringList;
	}
	
	protected DatabaseConnectionService getService() {
		return this.dbService;
	}
	
	public void setService(DatabaseConnectionService newService) {
		this.dbService = newService;
	}
	
	protected abstract void callService(int methodType);
	
	protected void goButton() {
		choiceIndex = getSelectedIndex();
		if(gameIsSelected) {
			callService(2);
		}
		else if(seasonIsSelected) {
			callService(3);
		}
		backBtn = new JButton("Back");
		backBtn.setBounds(310, 30, 80, 20);
		paintBackButton();
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backButton();
			}
		});
	}
	
	protected boolean checkYearInput() {
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
	
	
	protected boolean promptForYear() {
		if (gameIsSelected) {
			year = JOptionPane.showInputDialog(frame, "Enter a year (2000 - 2019)");
			if(checkYearInput()) {
				return true;
			}
		}
		if (gameIsSelected || seasonIsSelected) {
			setUpChoiceWindow();
		}
		return false;
	}
	
	protected void getGameAndSeasonSelections(JRadioButton rdbtnGame, JRadioButton rdbtnSeason) {
		gameIsSelected = rdbtnGame.isSelected();
		seasonIsSelected = rdbtnSeason.isSelected();
	}
	
	protected boolean getGameSelected() {
		return gameIsSelected;
	}
	
	protected boolean getSeasonSelected() {
		return seasonIsSelected;
	}
	protected String getYear() {
		return year;
	}
	protected void initializeServiceAndFrame(DatabaseConnectionService dbService, AbstractWindow window) {
		setService(dbService);
		frame.setVisible(true);
	}
	protected void setUpFrame() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1225, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	protected void setUpHomeButton() {
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
	
	protected JRadioButton setUpSeasonRadialButton() {
		JRadioButton rdbtnSeason = new JRadioButton("Season");
		rdbtnSeason.setBounds(117, 72, 71, 25);
		frame.getContentPane().add(rdbtnSeason);
		return rdbtnSeason;
	}

	protected JRadioButton setUpGameRadialButton() {
		JRadioButton rdbtnGame = new JRadioButton("Game");
		rdbtnGame.setBounds(18, 72, 61, 25);
		frame.getContentPane().add(rdbtnGame);
		return rdbtnGame;
	}
	protected void setUpClearButton() {
		JButton btnClearAll = new JButton("Clear All");
		btnClearAll.setBounds(356, 72, 97, 25);
		frame.getContentPane().add(btnClearAll);
		btnClearAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearWindow();
			}

		});
	}
	protected JButton createSearchButton() {
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(573, 29, 97, 25);
		frame.getContentPane().add(btnSearch);
		return btnSearch;
	}
	
	protected JFrame getFrame() {
		return this.frame;
	}
	protected JRadioButton setUpOverallRadialButton() {
		JRadioButton rdbtnCareer = new JRadioButton("Overall");
		rdbtnCareer.setBounds(237, 72, 76, 25);
		frame.getContentPane().add(rdbtnCareer);
		return rdbtnCareer;
	}
}
