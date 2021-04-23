package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DatabaseQueries.DatabaseQuery;
import DomainObjects.PlayerName;
import Domain.DatabaseConnectionService;
import PlayerQueries.*;

class PlayerQueryTests extends EasyMockSupport {
	
	@Mock
	DatabaseConnectionService fakeDatabase;
	@Mock
	Connection fakeConnection;
	@Mock
	CallableStatement fakeStatement;
	@Mock
	ResultSet fakeResults;
	
	PlayerName playerName;
	String seasonYear;
	String gameID;
	String result;

	
	@BeforeEach
	void Setup() {
		playerName = new PlayerName("Trevor", "Strahdslayer");
		seasonYear = "550";
		gameID = "42";
		result = "This is a test Result";

		
		fakeDatabase = createNiceMock(DatabaseConnectionService.class);
		fakeConnection = createNiceMock(Connection.class);
		fakeStatement = createNiceMock(CallableStatement.class);
		fakeResults = createNiceMock(ResultSet.class);
	}
	
	@AfterEach
	void TearDown() {
		verifyAll();
	}
	
	@Test
	void testPlayerCareerDataQuery() {
		/*
		 * Tests : PlayerCareerDataQuery.runQuery()
		 * 
		 * Expects that PlayerCareerDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement the first and last name
		 *  - Executes the query and saves the results
		 */
		
		PlayerCareerDataQuery instance = new PlayerCareerDataQuery(fakeDatabase, playerName);
		
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			preparePlayerName();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}

	private void preparePlayerName() throws SQLException {
		fakeStatement.setString(EasyMock.anyInt(), EasyMock.same(playerName.firstName));
		EasyMock.expectLastCall();
		fakeStatement.setString(EasyMock.anyInt(), EasyMock.same(playerName.lastName));
		EasyMock.expectLastCall();
	}
	
	@Test
	void testPlayerSeasonDataQuery() {
		/* Tests : PlayerSeasonDataQuery.runQuery()
		 * 
		 * Expects that PlayerSeasonDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement the first, last name, and seasonYear
		 *  - Executes the query and saves the results
		 */
		
		PlayerSeasonDataQuery instance = new PlayerSeasonDataQuery(fakeDatabase, playerName, seasonYear);

		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			preparePlayerName();
			fakeStatement.setInt(EasyMock.anyInt(), EasyMock.eq(Integer.valueOf(seasonYear)));
			EasyMock.expectLastCall();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	@Test
	void testPlayerSeasonsPlayedDataQuery() {
		/* Tests : PlayerSeasonDataQuery.runQuery()
		 * 
		 * Expects that PlayerSeasonDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement the first, last name, and seasonYear
		 *  - Executes the query and saves the results
		 */
		
		PlayerSeasonsPlayedDataQuery instance = new PlayerSeasonsPlayedDataQuery(fakeDatabase, playerName);
		
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			preparePlayerName();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	@Test
	void testPlayerGameDataQuery() {
		/* Tests : PlayerGameDataQuery.runQuery()
		 * 
		 * Expects that PlayerGameDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement the first, last name, gameID
		 *  - Executes the query and saves the results
		 */
		
		PlayerGameDataQuery instance = new PlayerGameDataQuery(fakeDatabase, playerName, gameID);
		
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			preparePlayerName();
			fakeStatement.setInt(EasyMock.anyInt(), EasyMock.eq((Integer.valueOf(gameID))));
			EasyMock.expectLastCall();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	
	
	@Test
	void testPlayerGamesPlayedInSeasonDataQuery() {
		/* Tests : PlayerGamesPlayedInSeasonDataQuery.runQuery()
		 * 
		 * Expects that PlayerGamesPlayedInSeasonDataQuery
		 *  - Gets a connection from the database service
		 *  - Gets a callable statement from the connection
		 *  - Gives the statement the first and last name
		 *  - Executes the query and saves the results
		 */
		
		PlayerGamesPlayedInSeasonDataQuery instance = new PlayerGamesPlayedInSeasonDataQuery(fakeDatabase, playerName, seasonYear);
		
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			preparePlayerName();
			fakeStatement.setInt(EasyMock.anyInt(), EasyMock.eq(Integer.valueOf(seasonYear)));
			EasyMock.expectLastCall();
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		runQueryTest(instance);
	}
	
	@Test
	void testGamesPlayedGetResults() {
		DatabaseQuery instance = new PlayerGamesPlayedInSeasonDataQuery(fakeDatabase, playerName, seasonYear);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);
	}
	
	@Test
	void testGamesDataGetResults() {
		DatabaseQuery instance = new PlayerGameDataQuery(fakeDatabase, playerName, gameID);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);	
	}
	
	@Test
	void testSeasonsPlayedGetResults() {
		DatabaseQuery instance = new PlayerSeasonsPlayedDataQuery(fakeDatabase, playerName);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);	
	}
	
	@Test
	void testCareerDataGetResults() {
		DatabaseQuery instance = new PlayerCareerDataQuery(fakeDatabase, playerName);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);	
	}
	
	@Test
	void testSeasonDataGetResults() {
		DatabaseQuery instance = new PlayerSeasonDataQuery(fakeDatabase, playerName, seasonYear);
		setExpectationsForCheckResults(instance, result);
		checkResults(instance, result);
	}
	
	private void setExpectationsForCheckResults(DatabaseQuery query, String inputString) {
		EasyMock.expect(fakeDatabase.getConnection()).andReturn(fakeConnection);
		try {
			EasyMock.expect(fakeConnection.prepareCall(EasyMock.anyString())).andReturn(fakeStatement);
			EasyMock.expect(fakeStatement.executeQuery()).andReturn(fakeResults);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try {
			EasyMock.expect(fakeResults.next()).andReturn(true).times(2);
			EasyMock.expect(fakeResults.getString(EasyMock.anyInt())).andReturn(result).atLeastOnce();
			EasyMock.expect(fakeResults.next()).andReturn(false).times(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void checkResults(DatabaseQuery query, String expectedValue) {
		replayAll();
		
		List<String> results = null;
		
		try {
			results = query.getResults();
		} catch (SQLException e) {
			e.printStackTrace();
			fail("SQL Exception");
		}
		
		for(String s : results) {
			assertTrue(s.contains(expectedValue));
		}
	}

	private void runQueryTest(DatabaseQuery query) {
		replayAll();
		
		try {
			query.runQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
