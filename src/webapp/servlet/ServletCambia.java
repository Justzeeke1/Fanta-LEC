package webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webapp.control.GiocatoreController;
import webapp.model.Giocatore;


@WebServlet("/cambia")
public class ServletCambia extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static GiocatoreController giocatoreController = GiocatoreController.getInstance();
       

    public ServletCambia() {
        super();
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idGiocatoreString = request.getParameter("giocatore");
		Long idGiocatore = Long.parseLong(idGiocatoreString);
		Giocatore giocatore = giocatoreController.findById(idGiocatore);
		giocatoreController.cambia(giocatore);
		response.sendRedirect("/Progetto_Marco/myteam.jsp"); 
	}

}
