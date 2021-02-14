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
import webapp.model.Allenatore;


@WebServlet("/Registrati")
public class ServletRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static AllenatoreController allenatoreController = AllenatoreController.getInstance();
       

    public ServletRegister() {
        super();
    }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String username = request.getParameter("usernameLogin");
		String password = request.getParameter("passwordLogin");
		Allenatore allenatore = allenatoreController.register(username, password);
		RequestDispatcher rd = null;
		if (allenatore != null) {
			session.setAttribute("allenatore", allenatore);
			session.setAttribute("squadra", null);
			response.sendRedirect("home.jsp"); 
		} else {
			request.setAttribute("token", "Registrati");
			rd = request.getRequestDispatcher("/setLoginToken"); 
			rd.forward(request, response);
		}
		
		
	}

}
