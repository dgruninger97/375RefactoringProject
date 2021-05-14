package integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domain.DatabaseConnectionService;
import Domain.PlayerService;

class PlayerServiceTests {

	DatabaseConnectionService mockDatabase;
	PlayerService instance;
	String firstName = "TestFirstName";
	String lastName = "TestLastName";
	String year = "TestYear";
	
	@BeforeEach
	void setUp() {
		mockDatabase = EasyMock.createNiceMock(DatabaseConnectionService.class);
	}
	
	@Test
	void testGetPlayerInformationGame() {
		instance = EasyMock.partialMockBuilder(PlayerService.class)
				.addMockedMethod("getPlayerGameInformation")
				.createMock();
		
		List<String> gameList = new ArrayList<String>();
		gameList.add("Game1");
		
		int choiceIndex = 0;
		try {
			EasyMock.expect(instance.getPlayerGameInformation(firstName, lastName, year, choiceIndex))
			.andReturn(gameList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EasyMock.replay(instance);
		
		List<String> returnedList = instance.getPlayerInformation(firstName, lastName, true, false, false, year, choiceIndex);
		
		EasyMock.verify(instance);
		assertEquals(gameList, returnedList);
	}
	
	@Test
	void testGetPlayerInformationSeason() {
		instance = EasyMock.partialMockBuilder(PlayerService.class)
				.addMockedMethod("getPlayerSeasonInformation")
				.createMock();
		
		List<String> seasonList = new ArrayList<String>();
		seasonList.add("Season1");
		
		int choiceIndex = 0;
		try {
			EasyMock.expect(instance.getPlayerSeasonInformation(firstName, lastName, choiceIndex))
			.andReturn(seasonList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EasyMock.replay(instance);
		
		List<String> returnedList = instance.getPlayerInformation(firstName, lastName, false, true, false, year, choiceIndex);
		
		EasyMock.verify(instance);
		assertEquals(seasonList, returnedList);
	}
	
	@Test
	void testGetPlayerInformationCareer() {
		instance = EasyMock.partialMockBuilder(PlayerService.class)
				.addMockedMethod("getPlayerCareerInformation")
				.createMock();
		
		List<String> careerList = new ArrayList<String>();
		careerList.add("Career1");
		
		int choiceIndex = 0;
		try {
			EasyMock.expect(instance.getPlayerCareerInformation(firstName, lastName))
			.andReturn(careerList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		EasyMock.replay(instance);
		
		List<String> returnedList = instance.getPlayerInformation(firstName, lastName, false, false, true, year, choiceIndex);
		
		EasyMock.verify(instance);
		assertEquals(careerList, returnedList);
	}
}
