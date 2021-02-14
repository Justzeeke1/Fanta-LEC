package webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webapp.control.GiocatoreController;
import webapp.model.Allenatore;
import webapp.model.Giocatore;
import webapp.model.Squadra;


@WebServlet("/vendi")
public class ServletVendi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static GiocatoreController giocatoreController = GiocatoreController.getInstance();
       

    public ServletVendi() {
        super();
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String idGiocatoreString = request.getParameter("giocatore");
		Long idGiocatore = Long.parseLong(idGiocatoreString);
		Giocatore giocatore = giocatoreController.findById(idGiocatore);
		Squadra squadra = (Squadra) session.getAttribute("squadra");
		Allenatore allenatore = (Allenatore) session.getAttribute("allenatore");
		giocatoreController.vendiGiocatore(giocatore, squadra.getId(), allenatore);
		session.setAttribute("allenatore", allenatore); 
		response.sendRedirect("/Progetto_Marco/myteam.jsp"); 
	}

}
