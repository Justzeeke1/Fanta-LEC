package webapp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webapp.control.AllenatoreController;
import webapp.control.SquadraController;
import webapp.model.Allenatore;
import webapp.model.Squadra;


@WebServlet("/Login")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AllenatoreController allenatoreController = AllenatoreController.getInstance();
	private static SquadraController squadraController = SquadraController.getInstance();
       

    public ServletLogin() {
        super();
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String username = request.getParameter("usernameLogin");
		String password = request.getParameter("passwordLogin");
		Allenatore allenatore = allenatoreController.validate(username, password);
		RequestDispatcher rd = null;
		if (allenatore != null) {
			session.setAttribute("allenatore", allenatore);
			Squadra squadra = squadraController.checkSquadraPresente(allenatore);
			session.setAttribute("squadra", squadra);
			response.sendRedirect("home.jsp");
		} else {
			request.setAttribute("token", "Login");
			rd = request.getRequestDispatcher("/setLoginToken"); 
			rd.forward(request, response);
		}
		
	}
}
