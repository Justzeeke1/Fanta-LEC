package webapp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webapp.control.SquadraController;


@WebServlet("/creaSquadre")
public class ServletCreaSquadre extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static SquadraController squadraController = SquadraController.getInstance();
       

    public ServletCreaSquadre() {
        super();
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idMiaSquadraString = request.getParameter("idMiaSquadra");
    	Long idMiaSquadra = Long.parseLong(idMiaSquadraString);
    	squadraController.creaSquadre(idMiaSquadra);
		response.sendRedirect("/Progetto_Marco/mercato.jsp"); 
	}

}
