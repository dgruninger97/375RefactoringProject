package functionalTests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import DataParsing.DataGenerator;

class DataGeneratorTests {

	ArrayList<Character> numbers = new ArrayList<Character>() {{
		add('0');
		add('1');
		add('2');
		add('3');
		add('4');
		add('5');
		add('6');
		add('7');
		add('8');
		add('9');
	}};
	
	@Test
	void testGeneratedFileAgainstMasterCopy() {
		String[] args = null;
		try {
			DataGenerator.main(args);
		} catch (IOException e) {
			Assertions.fail("Data generator threw an IO Exception");
		}
		
		try {
			FileReader masterCopyReader = new FileReader("DataSheet4-testingCopy.csv");
			FileReader generatedFileReader = new FileReader("DataSheet4.csv");
			int i = 0, j = 0;
			while(i != -1 && j != -1) {
				i = masterCopyReader.read();
				j = generatedFileReader.read();
				
				while((char) i == '\r' || (char) i == '\n' || (char) i == ',') {
					i = masterCopyReader.read();
				}
				
				while((char) j == '\r' || (char) j == '\n' || (char) j == ',') {
					j = generatedFileReader.read();
				}
				
				if((char) i == '%') { //Filler for a random string
					do {
						j = generatedFileReader.read();
					} while(',' != (char) j);
					masterCopyReader.read();
					continue;
				} else if ((char) i == '&') { //Filler for a random number
					do {
						assertTrue(numbers.contains((char) j));
						j = generatedFileReader.read();
					} while(',' != (char) j);
					masterCopyReader.read();
					continue;
				}
				
				char a = (char) i;
				char b = (char) j;
				assertEquals(a, b);
			}
			masterCopyReader.close();
			generatedFileReader.close();
			if(i != -1 || j != -1)
				Assertions.fail("Files were not identical");
		
		} catch (FileNotFoundException e) {
			Assertions.fail("Test threw File Not Found Exception");
		} catch (IOException e) {
			Assertions.fail("Test threw IO Exception");
		}
	}
}
