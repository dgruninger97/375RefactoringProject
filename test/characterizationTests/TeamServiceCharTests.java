package characterizationTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DataParsing.DataGenerator;
import DatabaseQueries.RebuildDatabase;
import Domain.DatabaseConnectionService;
import Domain.TeamService;

class TeamServiceCharTests {

	TeamService instance;
	DatabaseConnectionService database;
	
	@BeforeEach
	void setup() {
		String serverName = "";
		String username = "";
		String password = "";
		try {
			File serverFile = new File("serverConfiguration/server.txt");
			Scanner myReader;
			myReader = new Scanner(serverFile);
			serverName = myReader.nextLine();
			myReader.close();
			File userFile = new File("serverConfiguration/username.txt");
			myReader = new Scanner(userFile);
			username = myReader.nextLine();
			myReader.close();
			File passFile = new File("serverConfiguration/password.txt");
			myReader = new Scanner(passFile);
			password = myReader.nextLine();
			myReader.close();
		} catch (FileNotFoundException e) {
			Assertions.fail("Server, username or password file readers failed");
		}
		
		database = new DatabaseConnectionService(serverName, "TestDatabase");
		database.connect(username, password);
		
		/* This isn't how we're supposed to reset the database
		 * 
		RebuildDatabase.clearDatabase(database);
		String[] args = {""};
		try {
			DataGenerator.main(args);
		} catch (IOException e) {
			Assertions.fail("Datagenerator failed");
		}
		*/
		
		instance = new TeamService(database);
	}

	@AfterEach
	void tearDown() {
		database.closeConnection();
	}
	
	@Test
	void TestGetTeamGamesPlayedInfo() {
		String[] teamList = DataGenerator.teamList;
		String[] yearList = DataGenerator.yearList;
		for(String teamName : teamList) {
			for(String year : yearList) {
				List<String> resultList = instance.getTeamInformation(teamName, true, false, false, year, 0);
				if(resultList != null && resultList.size() != 0) {
					System.out.println("Database didn't fail with GetTeamGames on " + teamName + " " + year);
				}
			}
		}
	}
	
	@Test
	void TestGetTeamSeasonsPlayedInfo() {
		String[] teamList = DataGenerator.teamList;
		for(String teamName : teamList) {
			List<String> resultList = instance.getTeamInformation(teamName, false, true, false, "", 0);
			if(resultList != null && resultList.size() != 0) {
				System.out.println("Database didn't fail with GetTeamSeasons on " + teamName);
			}
		}
	}
	
	@Test
	void TestGetTeamFranchiseInfo() {
		String[] teamList = DataGenerator.teamList;
		for(String teamName : teamList) {
			List<String> resultList = instance.getTeamInformation(teamName, false, false, true, "", 0);
			if(resultList != null && resultList.size() != 0) {
				System.out.println("Database didn't fail with GetTeamFranchise on " + teamName);
			}
		}
	}
	
	@Test
	void TestGetTeamGameInfo() {
		String[] teamList = DataGenerator.teamList;
		String[] yearList = DataGenerator.yearList;
		for(String teamName : teamList) {
			for(String year : yearList) {
				try {
					List<String> resultListA = instance.getTeamInformation(teamName, true, false, false, year, 0);
					if(resultListA != null) {
						List<String> resultListB = instance.getTeamGameInfo(teamName, year, 0);						
						System.out.println("Database didn't fail with GetTeamGameInfo on " + teamName);
					}
				} catch (SQLException e) {
				}			
			}
		}
	}
	
	@Test
	void TestGetTeamSeasonInfo() {
		String[] teamList = DataGenerator.teamList;
		for(String teamName : teamList) {
			try {
				List<String> resultListA = instance.getTeamInformation(teamName, false, true, false, "", 0);
				if(resultListA != null) {
					List<String> resultListB = instance.getTeamSeasonInfo(teamName, 0);					
					System.out.println("Database didn't fail with GetTeamSeasonInfo on " + teamName);
				}
			} catch (SQLException e) {
			}
		}
	}
}
