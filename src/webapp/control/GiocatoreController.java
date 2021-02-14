package webapp.control;

import java.util.ArrayList;
import java.util.List;

import webapp.dao.DAOAllenatore;
import webapp.dao.DAOGiocatore;
import webapp.model.Allenatore;
import webapp.model.Giocatore;
import webapp.model.Squadra;

public class GiocatoreController {

	private static GiocatoreController INSTANCE = null;

	private static DAOAllenatore daoAllenatore = new DAOAllenatore();
	private static DAOGiocatore daoGiocatore = new DAOGiocatore();

	private GiocatoreController() {
	}

	public static GiocatoreController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new GiocatoreController();
		}
		return INSTANCE;
	}

	public List<Giocatore> findAll() {
		List<Giocatore> giocatori = new ArrayList<Giocatore>();
		try {
			giocatori = daoGiocatore.findAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return giocatori;
	}

	public List<Giocatore> findAllMercatoOrderByOverall() {
		List<Giocatore> giocatori = new ArrayList<Giocatore>();
		try {
			giocatori = daoGiocatore.findAllMercatoOrderByOverall();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return giocatori;
	}

	public List<Giocatore> findAllMyTeam(Squadra squadra) {
		List<Giocatore> giocatori = new ArrayList<Giocatore>();
		try {
			giocatori = daoGiocatore.findAllMyTeam(squadra.getId());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return giocatori;
	}

	public List<Giocatore> orderTeamView(List<Giocatore> giocatori) {
		List<Giocatore> giocatoriView = daoGiocatore.orderTeamView(giocatori);
		return giocatoriView;
	}

	public Giocatore compraGiocatore(Giocatore giocatore, Long idSquadra, Allenatore allenatore) {
		try {
			if (allenatore.getCrediti() >= giocatore.getPrezzo()) {
				giocatore = daoGiocatore.compraGiocatore(giocatore, idSquadra);
				Integer crediti = 0 - giocatore.getPrezzo();
				daoAllenatore.modificaCrediti(allenatore, crediti);
			} else {
				giocatore = null;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return giocatore;
	}

	public Giocatore findById(Long id) {
		Giocatore giocatore = null;
		try {
			giocatore = daoGiocatore.findById(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return giocatore;
	}

	public void cambia(Giocatore giocatore) {
		try {
			daoGiocatore.cambia(giocatore);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public Giocatore vendiGiocatore(Giocatore giocatore, Long idSquadra, Allenatore allenatore) {
		try {
				giocatore = daoGiocatore.vendiGiocatore(giocatore, idSquadra);
				Integer crediti = 0 + giocatore.getPrezzo();
				daoAllenatore.modificaCrediti(allenatore, crediti);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return giocatore;
	}
}
