package sodabase.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

public class UserService {
	private static final Random RANDOM = new SecureRandom();
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();
	private DatabaseConnectionService dbService = null;

	public UserService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public boolean useApplicationLogins() {
		return false;
	}

	public boolean login(String username, String password) throws SQLException {
		// TODO: Complete this method.
		if(username == null || password == null) {
			JOptionPane.showMessageDialog(null, "Login failed.");
			return false;
		}
		Connection connection;
		PreparedStatement statement = null;
		String query = "select PasswordSalt, PasswordHash from dbo.[User] where Username = ?";
		String passwordHash = null;
		byte[] passwordSalt = null;
		try {
			connection = dbService.getConnection();
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				String saltString = rs.getString("PasswordSalt");
				passwordSalt = dec.decode(saltString);
				passwordHash = rs.getString("PasswordHash");
			}
		} catch (SQLException e) {
			System.err.println("SQL Exception");
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		String hashAttempt = hashPassword(passwordSalt, password);
		return(hashAttempt.equals(passwordHash)); 
	}

	public boolean register(String username, String password) {
		// TODO: Task 6
		byte[] salt = getNewSalt();
		String hashVal = hashPassword(salt, password);
		CallableStatement callableStatement = null;
		try {
			callableStatement = dbService.getConnection().prepareCall("{?=call Register(?, ?, ?)}");
			callableStatement.registerOutParameter(1, Types.INTEGER);
			callableStatement.setString(2, username);
			callableStatement.setString(3, getStringFromBytes(salt));
			callableStatement.setString(4, hashVal);
			callableStatement.execute();
			if (callableStatement.getInt(1) == 1 || callableStatement.getInt(1) == 2 || callableStatement.getInt(1) == 3) {
				JOptionPane.showMessageDialog(null, "Registration failed.");
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public byte[] getNewSalt() {
		byte[] salt = new byte[16];
		RANDOM.nextBytes(salt);
		return salt;
	}

	public String getStringFromBytes(byte[] data) {
		return enc.encodeToString(data);
	}

	public String hashPassword(byte[] salt, String password) {

		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKeyFactory f;
		byte[] hash = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = f.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
			e.printStackTrace();
		}
		return getStringFromBytes(hash);
	}

}
