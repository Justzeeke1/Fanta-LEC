package webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webapp.model.Allenatore;
import webapp.model.DataSource;

public class DAOAllenatore {
	
	public Allenatore findById(Long id) throws Exception {
		DataSource instance = DataSource.getInstance();
		Allenatore allenatore = new Allenatore();
		String query = "SELECT * FROM allenatore WHERE allenatore.id = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				allenatore = mapRow(resultSet);
			}
		}
		return allenatore;
	}
	
	private Allenatore mapRow(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong(1);
		String username = resultSet.getString(2);
		String password = resultSet.getString(3);
		int crediti = resultSet.getInt(4);
		return new Allenatore(id, username, password, crediti);
	}

	public Allenatore modificaCrediti(Allenatore allenatore, Integer crediti) throws Exception {
		DataSource instance = DataSource.getInstance();
		allenatore.setCrediti(allenatore.getCrediti() + crediti);
		String query = "UPDATE allenatore SET crediti = ? WHERE id = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, allenatore.getCrediti());
			statement.setLong(2, allenatore.getId());
			statement.executeUpdate();
		}
		return allenatore;
	}

	public List<Allenatore> findAll() throws Exception {
		DataSource instance = DataSource.getInstance();
		List<Allenatore> allenatori = new ArrayList<Allenatore>();
		String query = "SELECT * FROM allenatore";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Allenatore allenatore = mapRow(resultSet);
				allenatori.add(allenatore);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return allenatori;
	}

}
