package webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import webapp.model.DataSource;
import webapp.model.Giocatore;
import webapp.model.Squadra;

public class DAOGiornata {
	
	private static DAOSquadra daoSquadra = new DAOSquadra();
	
	public Long getGiornataDaGiocare() throws Exception {
		DataSource instance = DataSource.getInstance();
		Long idGiornata = null;
		String query = "SELECT * FROM giornata_da_giocare";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				idGiornata = mapRowGiornataDaGiocare(resultSet);

			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return idGiornata;
	}

	public List<String> getScontriByIdGiornataDaGiocare(Long id) throws Exception {
		DataSource instance = DataSource.getInstance();
		List<String> scontri = new ArrayList<String>();
		String query = "SELECT * FROM giornata WHERE giornata.id = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				scontri = mapRowGiornata(resultSet);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return scontri;
	}

	private List<String> mapRowGiornata(ResultSet resultSet) throws Exception {
		List<String> scontri = new ArrayList<String>();
		String primoScontro = resultSet.getString(2);
		String secondoScontro = resultSet.getString(3);
		scontri.add(primoScontro);
		scontri.add(secondoScontro);
		return scontri;
	}
	
	private Long mapRowGiornataDaGiocare(ResultSet resultSet) throws Exception {
		Long idGiornata = resultSet.getLong(2);
		return idGiornata;
	}

	public void update(Giocatore giocatore) throws Exception {
		DataSource instance = DataSource.getInstance();
		String query = "UPDATE giocatore SET nickname = ?, ruolo = ?, overall = ?, prezzo = ?, link_foto = ?, riserva = ?, id_squadra = ? WHERE id = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, giocatore.getNickname());
			statement.setString(2, giocatore.getRuolo());
			statement.setInt(3, giocatore.getOverall());
			statement.setInt(4, giocatore.getPrezzo());
			statement.setString(5, giocatore.getLinkFoto());
			statement.setBoolean(6, giocatore.getRiserva());
			statement.setLong(7, giocatore.getSquadra().getId());
			statement.setLong(8, giocatore.getId());
			statement.executeUpdate();
		}
	}

	public void createScontri() throws Exception {
		DataSource instance = DataSource.getInstance();
		List<Squadra> squadre = daoSquadra.findAll();
		Long id1 = squadre.get(0).getId();
		Long id2 = squadre.get(1).getId();
		Long id3 = squadre.get(2).getId();
		Long id4 = squadre.get(3).getId();
		List<String> scontri = new ArrayList<String>(Arrays.asList(id1+"-"+id2, id3+"-"+id4, id1+"-"+id3, id2+"-"+id4, id1+"-"+id4, id2+"-"+id3));
		String query = "UPDATE giornata SET primo_scontro = ?, secondo_scontro = ? WHERE id = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			Long id = 1L;
			for (int i = 0; i < 6; i+=2) {
				statement.setString(1, scontri.get(i));
				statement.setString(2, scontri.get(i+1));
				statement.setLong(3, id);
				statement.executeUpdate();
				id++;
			}
		}
		
	}

	

}
