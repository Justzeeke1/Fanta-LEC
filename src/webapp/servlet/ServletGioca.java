package webapp.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webapp.control.SquadraController;
import webapp.model.Squadra;


@WebServlet("/gioca")
public class ServletGioca extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static SquadraController squadraController = SquadraController.getInstance();
       

    public ServletGioca() {
        super();
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	List<String> scontri = new ArrayList<String>();
    	Long idGiornata = Long.parseLong(request.getParameter("idGiornata"));
    	scontri.add(request.getParameter("scontro1"));
    	scontri.add(request.getParameter("scontro2"));
    	squadraController.gioca(scontri, idGiornata);
    	Squadra oldSquadra = (Squadra) session.getAttribute("squadra");
    	Squadra newSquadra = squadraController.findById(oldSquadra.getId());
    	String esito = null;
    	if (oldSquadra.getVittorie() != newSquadra.getVittorie()) {
    		esito = "VITTORIA";
    	} else {
    		esito = "SCONFITTA";
    	}
    	session.setAttribute("squadra", newSquadra);
    	request.setAttribute("esito", esito);
		RequestDispatcher rd = request.getRequestDispatcher("finePartita.jsp"); 
		rd.forward(request, response);
	}

}
