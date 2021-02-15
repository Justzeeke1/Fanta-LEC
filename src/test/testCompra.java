package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import webapp.dao.DAOGiocatore;
import webapp.model.Giocatore;

public class testCompra {
	private static DAOGiocatore daoGiocatore;
	private Giocatore giocatore;

    @BeforeEach                                         
    public void setUp() throws Exception {
        daoGiocatore = new DAOGiocatore();
        giocatore = new Giocatore(1L, "test", "test", 90, 100, "linkTest", false, null);
    }

    @Test                                               
    @DisplayName("La funzionalità 'Compra Giocatore' dovrebbe funzionare")   
    public void testBuyPlayer() {
    	try {
			assertEquals(giocatore, daoGiocatore.compraGiocatore(giocatore, (long) 1),      
			"La funzionalità 'Compra Giocatore' dovrebbe funzionare");
		} catch (Exception e) {
			e.printStackTrace();
		}          
    }
}
