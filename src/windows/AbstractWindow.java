package windows;

import java.awt.Choice;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import services.DatabaseConnectionService;

public abstract class AbstractWindow {
	private JFrame frame;
	private JPanel[] panels;
	private int openSlot = 1;
	private JPanel curPanel;
	private int buttonSelection = -1;
	private JButton btnGo;
	private JButton backBtn;
	private TextArea textArea;
	private Choice[] choices = new Choice[3];
	private List<String> returnedList;
	private DatabaseConnectionService dbService;
	private int choiceIndex;
	private boolean gameIsSelected;
	private boolean seasonIsSelected;
	private String year;
	private int panelIndex;
	
	public abstract void startWindow(DatabaseConnectionService dbService);
	
	protected abstract void backButton();
	
	protected void setupPanels() {
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 110, 400, 328);
		frame.getContentPane().add(panel_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(405, 110, 400, 328);
		frame.getContentPane().add(panel_2);

		JPanel panel_3 = new JPanel();
		panel_3.setBounds(810, 110, 400, 328);
		frame.getContentPane().add(panel_3);
		
		panels = new JPanel[3];
		panels[0] = panel_1;
		panels[1] = panel_2;
		panels[2] = panel_3;
	}
	protected void setCurrentPanel() {
		if (openSlot == 1) {
			curPanel = panels[0];
			panelIndex = 0;
		} else if (openSlot == 2) {
			curPanel = panels[1];
			panelIndex = 1;
		} else if (openSlot == 3) {
			curPanel = panels[2];
			panelIndex = 2;
		}else {
			curPanel = null;
		}
	}
	
	protected void clearWindow() {
		panels[0].removeAll();
		panels[1].removeAll();
		panels[2].removeAll();
		panels[0].repaint();
		panels[1].repaint();
		panels[2].repaint();
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
		setUpChoiceWindow();
	}
	
	protected void displayInfo(String labelText) {
		String info = "";
		for (int i = 0; i < returnedList.size(); i++) {
			info += returnedList.get(i);
		}
		textArea = new TextArea();
		textArea.setText(info);
		textArea.setBounds(0, 30, 310, 129);
		textArea.setEditable(false);
		curPanel.removeAll();
		curPanel.add(textArea);
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
		if(choices[panelIndex]==null)
			choices[panelIndex] = new Choice();
		choices[panelIndex].setBounds(0, 30, 300, 22);
		btnGo = new JButton("Go");
		btnGo.setBounds(310, 30, 50, 20);
		
		btnGo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateCurPanel((JButton)e.getSource());
				goButton();
			}

			
		});
		curPanel.add(choices[panelIndex]);
		curPanel.add(btnGo);
		curPanel.repaint();
	}
	private void updateCurPanel(JButton e) {
		JPanel source = (JPanel)e.getParent();
		if(source.equals(panels[0])) {
			curPanel = panels[0];
			panelIndex = 0;
		}
		if(source==panels[1]) {
			curPanel = panels[1];
			panelIndex = 1;
		}
		if(source==panels[2]) {
			curPanel = panels[2];
			panelIndex = 2;
		}
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
			choices[panelIndex].removeAll();
			for (int i = 0; i < returnedList.size(); i++) {
				choices[panelIndex].add(returnedList.get(i));
			}
			choiceIndex = choices[panelIndex].getSelectedIndex();
			return;
		}
	}
	
	protected int getSelectedIndex() {
		return choices[panelIndex].getSelectedIndex();
	}
	
	protected void setReturnedList(List<String> stringList) {
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
				updateCurPanel((JButton)e.getSource());
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
	
	protected void getSelectedButton(JRadioButton rdbtnGame, JRadioButton rdbtnSeason, JRadioButton rdbtnOverall) {
		if(rdbtnGame.isSelected()){
			buttonSelection = 1;
		}
		if(rdbtnSeason.isSelected()) {
			buttonSelection = 2;
		}
		if(rdbtnOverall.isSelected()) {
			buttonSelection = 3;
		}
	}
}
