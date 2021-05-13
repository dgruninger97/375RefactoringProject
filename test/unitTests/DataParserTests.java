package unitTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import DataParsing.DataParser;
import Domain.DatabaseConnectionService;

class DataParserTests {

	@Test
	void testInsertNextLineToDB() {
		DatabaseConnectionService mockDatabase = EasyMock.createMock(DatabaseConnectionService.class);
		Connection mockConnection = EasyMock.createMock(Connection.class);
		CallableStatement mockStatement = EasyMock.createMock(CallableStatement.class);

		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("parserTestFile.txt"));
		} catch (FileNotFoundException e1) {
			Assertions.fail("Failed to find the test file");
		}
		
		try {
			EasyMock.expect(mockDatabase.getConnection()).andReturn(mockConnection);
			EasyMock.expect(mockConnection.prepareCall(EasyMock.anyString())).andReturn(mockStatement);
			
			mockStatement.registerOutParameter(1, Types.INTEGER);
			EasyMock.expectLastCall();
			
			mockStatement.setString(EasyMock.anyInt(), EasyMock.anyString());
			EasyMock.expectLastCall().atLeastOnce();
			
			EasyMock.expect(mockStatement.execute()).andReturn(true);
		} catch (SQLException e) {
			Assertions.fail("Mock expectations threw an exception");
		}
		
		EasyMock.replay(mockDatabase, mockConnection, mockStatement);
		
		DataParser.insertNextLineToDB(mockDatabase, scanner);
		
		EasyMock.verify(mockDatabase, mockConnection, mockStatement);
	}
}
