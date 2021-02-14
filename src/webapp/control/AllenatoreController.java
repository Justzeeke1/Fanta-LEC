package webapp.control;

import java.util.ArrayList;
import java.util.List;

import webapp.dao.DAOAllenatore;
import webapp.dao.DAOLogin;
import webapp.model.Allenatore;

public class AllenatoreController {

    private static AllenatoreController INSTANCE = null;
    
    private static DAOLogin daoLogin = new DAOLogin();
    private static DAOAllenatore daoAllenatore = new DAOAllenatore();

    private AllenatoreController() {}

    public static AllenatoreController getInstance() {
        if(INSTANCE==null) {
            INSTANCE = new AllenatoreController();
        }
        return INSTANCE;
    }
    
    public Allenatore validate(String username, String password) {
    	Allenatore allenatore = null;
    	try {
			allenatore = daoLogin.validate(username, password);
			if (allenatore != null) {
				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	return allenatore;
    }

	public Allenatore register(String username, String password) {
		Allenatore allenatore = null;
    	try {
			allenatore = daoLogin.register(username, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	return allenatore;
	}
	
	public List<Allenatore> findAll() {
		List<Allenatore> allenatori = new ArrayList<Allenatore>();
		try {
			allenatori = daoAllenatore.findAll();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return allenatori;
	}
    
}
