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

public class DAOGiocatore {

	private static DAOSquadra daoSquadra = new DAOSquadra();

	public List<Giocatore> findAll() throws Exception {
		DataSource instance = DataSource.getInstance();
		List<Giocatore> giocatori = new ArrayList<Giocatore>();
		String query = "SELECT * FROM giocatore";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Giocatore giocatore = mapRow(resultSet);
				giocatori.add(giocatore);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return giocatori;
	}

	private Giocatore mapRow(ResultSet resultSet) throws Exception {
		Long id = resultSet.getLong(1);
		String nickname = resultSet.getString(2);
		String ruolo = resultSet.getString(3);
		int overall = resultSet.getInt(4);
		int prezzo = resultSet.getInt(5);
		String linkFoto = resultSet.getString(6);
		Boolean riserva = resultSet.getBoolean(7);
		Long idSquadra = resultSet.getLong(8);
		Squadra squadra = daoSquadra.findById(idSquadra);
		return new Giocatore(id, nickname, ruolo, overall, prezzo, linkFoto, riserva, squadra);
	}

	public Giocatore findById(Long id) throws Exception {
		DataSource instance = DataSource.getInstance();
		Giocatore giocatore = new Giocatore();
		String query = "SELECT * FROM giocatore WHERE giocatore.id = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				giocatore = mapRow(resultSet);
			}
		}
		return giocatore;
	}

	public void update(Giocatore giocatore) throws Exception {
		DataSource instance = DataSource.getInstance();
		String query = "UPDATE `progetto_ispw`.`giocatore` SET `nickname` = ?, `ruolo` = ?, `overall` = ?, `prezzo` = ?, `link_foto` = ?, `riserva` = ?, `id_squadra` = ? WHERE (`id` = '?')";
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
			statement.executeQuery();
		}
	}

	public List<Giocatore> findAllMercatoOrderByOverall() throws Exception {
		DataSource instance = DataSource.getInstance();
		List<Giocatore> giocatori = new ArrayList<Giocatore>();
		String query = "SELECT * FROM giocatore WHERE giocatore.id_squadra is null ORDER BY giocatore.overall DESC";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Giocatore giocatore = mapRow(resultSet);
				giocatori.add(giocatore);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return giocatori;
	}

	public List<Giocatore> findAllMyTeam(Long idSquadra) throws Exception {
		DataSource instance = DataSource.getInstance();
		List<Giocatore> giocatori = new ArrayList<Giocatore>();
		String query = "SELECT * FROM giocatore WHERE giocatore.id_squadra = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, idSquadra);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Giocatore giocatore = mapRow(resultSet);
				giocatori.add(giocatore);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return giocatori;
	}

	public void cambia(Giocatore giocatore) throws Exception {
		DataSource instance = DataSource.getInstance();
		String ruolo = giocatore.getRuolo();
		List<Giocatore> giocatori = findAllMyTeam(giocatore.getSquadra().getId());
		for (Giocatore g : giocatori) {
			if (g.getRuolo().equals(ruolo) && !g.getRiserva()) {
				String query = "UPDATE giocatore SET riserva = ? WHERE (id = ?)";
				try (Connection connection = instance.getConnection();
						PreparedStatement statement = connection.prepareStatement(query);) {
					statement.setBoolean(1, true);
					statement.setLong(2, g.getId());
					statement.executeUpdate();
					g.setRiserva(true);
					statement.setBoolean(1, false);
					statement.setLong(2, giocatore.getId());
					statement.executeUpdate();
					giocatore.setRiserva(false);
				}
				break;
			}
		}
	}

	public Giocatore compraGiocatore(Giocatore giocatore, Long idSquadra) throws Exception {
		DataSource instance = DataSource.getInstance();
		Boolean riserva = false;
		String ruolo = giocatore.getRuolo();
		List<Giocatore> giocatori = findAllMyTeam(idSquadra);
		for (Giocatore g : giocatori) {
			if (g.getRuolo().equals(ruolo) && !g.getRiserva()) {
				riserva = true;
				break;
			}
		}
		String query = "UPDATE `progetto_ispw`.`giocatore` SET `id_squadra` = ?, `riserva` = ? WHERE (`id` = ?)";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, idSquadra);
			statement.setBoolean(2, riserva);
			statement.setLong(3, giocatore.getId());
			statement.executeUpdate();
			giocatore.setSquadra(daoSquadra.findById(idSquadra));
			giocatore.setRiserva(riserva);
		}
		return giocatore;
	}

	public List<Giocatore> orderTeamView(List<Giocatore> giocatori) {
		List<String> ruoli = new ArrayList<String>(Arrays.asList("TOP", "JNG", "MID", "ADC", "SUPPORT"));
		List<Giocatore> giocatoriView = new ArrayList<Giocatore>();
		for (int i = 0; i < 5; i++) {
			for (Giocatore giocatore : giocatori) {
				if (giocatore.getRuolo().equals(ruoli.get(i))) {
					giocatoriView.add(giocatore);
					break;
				}
			}
		}
		return giocatoriView;
	}
	public Giocatore vendiGiocatore(Giocatore giocatore, Long idSquadra) throws Exception {
		DataSource instance = DataSource.getInstance();
		String query = "UPDATE `progetto_ispw`.`giocatore` SET `id_squadra` = null, `riserva` = 0 WHERE (`id` = ?)";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, giocatore.getId());
			statement.executeUpdate();
		}
		return giocatore;
	}

}
