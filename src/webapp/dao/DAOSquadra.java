package webapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import webapp.model.Allenatore;
import webapp.model.DataSource;
import webapp.model.Giocatore;
import webapp.model.Squadra;

public class DAOSquadra {

	private static DAOAllenatore daoAllenatore = new DAOAllenatore();
	private static DAOGiocatore daoGiocatore = new DAOGiocatore();

	public List<Squadra> findAllOrderByPunteggio() throws Exception {
		DataSource instance = DataSource.getInstance();
		List<Squadra> squadre = new ArrayList<Squadra>();
		String query = "SELECT * FROM squadra ORDER BY squadra.punteggio DESC";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Squadra squadra = mapRow(resultSet);
				squadre.add(squadra);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return squadre;
	}

	private Squadra mapRow(ResultSet resultSet) throws Exception {
		Long id = resultSet.getLong(1);
		String nome = resultSet.getString(2);
		int vittorie = resultSet.getInt(3);
		int sconfitte = resultSet.getInt(4);
		int punteggio = resultSet.getInt(5);
		Long idAllenatore = resultSet.getLong(6);
		Allenatore allenatore = daoAllenatore.findById(idAllenatore);
		return new Squadra(id, nome, vittorie, sconfitte, punteggio, allenatore);
	}

	public Squadra save(String nomeSquadra, Allenatore allenatore) throws Exception {
		Squadra squadra = new Squadra();
		DataSource instance = DataSource.getInstance();
		String query = "INSERT INTO squadra (nome, vittorie, sconfitte, punteggio, id_allenatore) VALUES (?, '0', '0', '0', ?)";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);) {
			statement.setString(1, nomeSquadra);
			statement.setLong(2, allenatore.getId());
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				Long id = generatedKeys.getLong(1);
				squadra.setId(id);
				squadra.setNome(nomeSquadra);
				squadra.setVittorie(0);
				squadra.setSconfitte(0);
				squadra.setPunteggio(0);
				squadra.setAllenatore(allenatore);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return squadra;
	}

	public Squadra findById(Long id) throws Exception {
		DataSource instance = DataSource.getInstance();
		Squadra squadra = new Squadra();
		String query = "SELECT * FROM squadra WHERE squadra.id = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				squadra = mapRow(resultSet);
			}
		}
		return squadra;
	}

	public Long getSquadraAvversaria(List<String> scontri, Long id) {
		String idString = id + "";
		String idSquadraAvversaria = null;
		for (String scontro : scontri) {
			if (scontro.split("-")[0].equals(idString)) {
				idSquadraAvversaria = scontro.split("-")[1];
			} else if (scontro.split("-")[1].equals(idString)) {
				idSquadraAvversaria = scontro.split("-")[0];
			}
		}
		return Long.parseLong(idSquadraAvversaria);
	}

	public void gioca(List<String> scontri, Long idGiornata) throws NumberFormatException, Exception {
		DataSource instance = DataSource.getInstance();
		idGiornata = getProssimaGiornata(idGiornata);
		String query = "UPDATE giornata_da_giocare SET id_giornata_da_giocare = ? WHERE (id = 1)";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, idGiornata);
			statement.executeUpdate();
		}
		Squadra squadra1 = null;
		Squadra squadra2 = null;
		for (String s : scontri) {
			int counterVittorieSquadra1 = 0;
			// PRENDO LE SQUADRE
			squadra1 = findById(Long.parseLong(s.split("-")[0]));
			squadra2 = findById(Long.parseLong(s.split("-")[1]));
			// PRENDO I GIOCATORI
			List<Giocatore> giocatoriSq1 = daoGiocatore.findAllMyTeam(squadra1.getId());
			giocatoriSq1 = daoGiocatore.orderTeamView(giocatoriSq1);
			List<Giocatore> giocatoriSq2 = daoGiocatore.findAllMyTeam(squadra2.getId());
			giocatoriSq2 = daoGiocatore.orderTeamView(giocatoriSq2);
			// FACCIO SCONTRARE I GIOCATORI SINGOLI CICLICAMENTE
			for (int i = 0; i < 5; i++) {
				Giocatore giocatore1 = giocatoriSq1.get(i);
				Giocatore giocatore2 = giocatoriSq2.get(i);
				// CALCOLO LA PROBABILITA
				int probVittoriaGiocatore1 = 50 + (giocatore1.getOverall() - giocatore2.getOverall()) * 2;
				if ((Math.random() * 100) + 1 <= probVittoriaGiocatore1) {
					counterVittorieSquadra1++;
				}
			}
			if (counterVittorieSquadra1 < 3) {
				squadra1.setSconfitte(squadra1.getSconfitte() + 1);
				squadra1.setPunteggio(squadra1.getPunteggio() + 1);
				squadra2.setVittorie(squadra2.getVittorie() + 1);
				squadra2.setPunteggio(squadra2.getPunteggio() + 3);
			} else {
				squadra2.setSconfitte(squadra2.getSconfitte() + 1);
				squadra2.setPunteggio(squadra2.getPunteggio() + 1);
				squadra1.setVittorie(squadra1.getVittorie() + 1);
				squadra1.setPunteggio(squadra1.getPunteggio() + 3);
			}
			query = "UPDATE squadra SET vittorie = ?, sconfitte = ?, punteggio = ? WHERE (id = ?)";
			try (Connection connection = instance.getConnection();
					PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setInt(1, squadra1.getVittorie());
				statement.setInt(2, squadra1.getSconfitte());
				statement.setInt(3, squadra1.getPunteggio());
				statement.setLong(4, squadra1.getId());
				statement.executeUpdate();
				statement.setInt(1, squadra2.getVittorie());
				statement.setInt(2, squadra2.getSconfitte());
				statement.setInt(3, squadra2.getPunteggio());
				statement.setLong(4, squadra2.getId());
				statement.executeUpdate();
			}
		}

	}

	private Long getProssimaGiornata(Long idGiornata) {
		if (idGiornata == 1) {
			idGiornata = 2L;
		} else if (idGiornata == 2) {
			idGiornata = 3L;
		} else if (idGiornata == 3) {
			idGiornata = 1L;
		}
		return idGiornata;
	}

	public Boolean areSquadreCreated(List<Squadra> squadre, Long idMiaSquadra) throws Exception {
		for (Squadra squadra : squadre) {
			if (!squadra.getId().equals(idMiaSquadra)) {
				List<Giocatore> giocatori = daoGiocatore.findAllMyTeam(squadra.getId());
				if (!giocatori.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	public void creaSquadre(Long idMiaSquadra) throws Exception {
		DataSource instance = DataSource.getInstance();
		List<String> ruoli = new ArrayList<String>(Arrays.asList("TOP", "JNG", "MID", "ADC", "SUPPORT"));
		List<Squadra> squadre = findAllOrderByPunteggio();
		List<Giocatore> giocatori = daoGiocatore.findAllMercatoOrderByOverall();
		for (Squadra squadra : squadre) {
			for (String ruolo : ruoli) {
				if (!squadra.getId().equals(idMiaSquadra)) {
					Giocatore giocatore = findGiocatore(giocatori, ruolo);
					String query = "UPDATE giocatore SET id_squadra = ? WHERE id = ?";
					try (Connection connection = instance.getConnection();
							PreparedStatement statement = connection.prepareStatement(query);) {
						statement.setLong(1, squadra.getId());
						statement.setLong(2, giocatore.getId());
						statement.executeUpdate();
					}
					giocatori.remove(giocatore);
				}
			}
		}
	}

	private Giocatore findGiocatore(List<Giocatore> giocatori, String ruolo) {
		for (Giocatore giocatore : giocatori) {
			if (giocatore.getRuolo().equals(ruolo)) {
				return giocatore;
			}
		}
		return new Giocatore();
	}

	public List<Squadra> findAll() throws Exception {
		DataSource instance = DataSource.getInstance();
		List<Squadra> squadre = new ArrayList<Squadra>();
		String query = "SELECT * FROM squadra ORDER BY squadra.id DESC";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Squadra squadra = mapRow(resultSet);
				squadre.add(squadra);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return squadre;
	}

	public Squadra squadraPresente(Long id) throws Exception {
		DataSource instance = DataSource.getInstance();
		Squadra squadra = null;
		String query = "SELECT * FROM squadra WHERE id_allenatore = ?";
		try (Connection connection = instance.getConnection();
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				squadra = mapRow(resultSet);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage(), e);
		}
		return squadra;
	}

}
