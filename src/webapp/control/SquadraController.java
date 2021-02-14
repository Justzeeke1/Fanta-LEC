package webapp.control;

import java.util.ArrayList;
import java.util.List;

import webapp.dao.DAOSquadra;
import webapp.model.Allenatore;
import webapp.model.Squadra;

public class SquadraController {

	private static SquadraController INSTANCE = null;

	private static DAOSquadra daoSquadra = new DAOSquadra();

	private SquadraController() {
	}

	public static SquadraController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SquadraController();
		}
		return INSTANCE;
	}
	
	public Squadra findById(Long id) {
		Squadra squadra = null;
		try {
			squadra = daoSquadra.findById(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return squadra;
	}

	public Squadra checkSquadraPresente(Allenatore allenatore) {
		Squadra squadraPresente = null;
		try {
			squadraPresente = daoSquadra.squadraPresente(allenatore.getId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return squadraPresente;
	}

	public Squadra save(String nomeSquadra, Allenatore allenatore) {
		Squadra squadra = new Squadra();
		try {
			squadra = daoSquadra.save(nomeSquadra, allenatore);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return squadra;
	}

	public Squadra getSquadraAvversaria(List<String> scontri, Long id) {
		Squadra squadra = new Squadra();
		Long idSquadraAvversaria = daoSquadra.getSquadraAvversaria(scontri, id);
		try {
			squadra = daoSquadra.findById(idSquadraAvversaria);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return squadra;
	}
	
	public List<Squadra> findAllOrderByPunteggio() {
		List<Squadra> squadre = new ArrayList<Squadra>();
		try {
			squadre = daoSquadra.findAllOrderByPunteggio();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return squadre;
	}
	
	public void gioca(List<String> scontri, Long idGiornata) {
		try {
			daoSquadra.gioca(scontri, idGiornata);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Boolean areSquadreCreated(Long idMiaSquadra) {
		Boolean areSquadreCreated = null;
		try {
			List<Squadra> squadre = daoSquadra.findAllOrderByPunteggio();
			areSquadreCreated = daoSquadra.areSquadreCreated(squadre, idMiaSquadra);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return areSquadreCreated;
	}

	public void creaSquadre(Long idMiaSquadra) {
		try {
			daoSquadra.creaSquadre(idMiaSquadra);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
