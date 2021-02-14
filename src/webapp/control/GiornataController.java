package webapp.control;

import java.util.ArrayList;
import java.util.List;

import webapp.dao.DAOGiornata;

public class GiornataController {

    private static GiornataController INSTANCE = null;
    
    private static DAOGiornata daoGiornata = new DAOGiornata();

    private GiornataController() {}

    public static GiornataController getInstance() {
        if(INSTANCE==null) {
            INSTANCE = new GiornataController();
        }
        return INSTANCE;
    }

    public Long getGiornataDaGiocare() {
    	Long idGiornata = null;
    	try {
			idGiornata = daoGiornata.getGiornataDaGiocare();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return idGiornata;
    }
    
    public List<String> getScontriByIdGiornataDaGiocare(Long id) {
    	List<String> scontri = new ArrayList<String>();
    	try {
			scontri = daoGiornata.getScontriByIdGiornataDaGiocare(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	return scontri;
    }
    
    public void createScontri() {
    	try {
			daoGiornata.createScontri();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
   
    
}
