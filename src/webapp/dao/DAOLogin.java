package webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import webapp.model.Allenatore;
import webapp.model.DataSource;

public class DAOLogin {

	public Allenatore validate(String username, String password) throws Exception {
		DataSource instance = DataSource.getInstance();
		Allenatore allenatore = null;
		String query = "SELECT * FROM allenatore WHERE allenatore.username = ? AND allenatore.password = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, username.trim());
			statement.setString(2, password.trim());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				allenatore = mapRow(resultSet);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return allenatore;
	}

	private Allenatore mapRow(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong(1);
		String username = resultSet.getString(2);
		String password = resultSet.getString(3);
		Integer crediti = resultSet.getInt(4);
		Allenatore allenatore = new Allenatore(id, username, password, crediti);
		return allenatore;
	}

	public Allenatore register(String username, String password) throws Exception {
		DataSource instance = DataSource.getInstance();
		Allenatore allenatore = new Allenatore();
		if (username.contains(" ") || password.contains(" ")) {
			return null;
		}
		String query = "INSERT INTO allenatore (username, password, crediti) VALUES (?, ?, '500');";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, username.trim());
			statement.setString(2, password.trim());
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				Long id = generatedKeys.getLong(1);
				allenatore.setId(id);
				allenatore.setUsername(username);
				allenatore.setPassword(password);
				allenatore.setCrediti(500);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return allenatore;
	}

}
