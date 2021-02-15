<% Allenatore allenatore = (Allenatore) session.getAttribute("allenatore"); 
   if (allenatore == null) {	%>
<jsp:forward page="index.jsp"></jsp:forward>
<% } %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webapp.control.SquadraController" %>
<%@ page import="webapp.control.GiocatoreController" %>
<%@ page import="webapp.model.Squadra" %>
<%@ page import="webapp.model.Allenatore" %>
<%@ page import="webapp.model.Giocatore" %>
<!DOCTYPE html>
<html lang="it">
   <head>
      <meta charset="UTF-8">
      <title>Fanta LEC</title>
      <style>
         <%@include file="/WEB-INF/css/cover.css"%>
         <%@include file="/WEB-INF/css/bootstrap.min.css"%>
         <%@include file="/WEB-INF/css/skinHeader.css"%>
      </style>
   </head>
   <body>
      <jsp:include page="header.jsp"/>
      <div class="container">
         <% 
            SquadraController squadraController = SquadraController.getInstance();
            GiocatoreController giocatoreController = GiocatoreController.getInstance();
            String nomeSquadra = request.getParameter("nomeSquadra");
            Squadra squadra = (Squadra) session.getAttribute("squadra");
            if (nomeSquadra != null) {
            	squadra = squadraController.save(nomeSquadra, allenatore);
            	session.setAttribute("squadra", squadra);
            }
            List<Giocatore> giocatoriTitolari = new ArrayList<Giocatore>();
            if (squadra != null) {
            	List<Giocatore> giocatoriTotali = giocatoreController.findAllMyTeam(squadra);
            	for (Giocatore giocatore : giocatoriTotali) {
            		if (!giocatore.getRiserva()) {
            			giocatoriTitolari.add(giocatore);
            		}
            	}
            	Boolean areSquadreCreated = squadraController.areSquadreCreated(squadra.getId());
            	List<Giocatore> giocatori = giocatoreController.findAllMercatoOrderByOverall();
            	%>
         <div class="row">
            <h2 class="display-4 site-header" style="margin-top:5%; text-align:center;">CREA LA TUA SQUADRA!</h2>
            <p class="lead site-header" style="text-align: center">Clicca sul prezzo per acquistare un giocatore.</p>
            <% if (giocatoriTitolari.size() == 5 && !areSquadreCreated) { %>
            <form action="/Progetto_Marco/creaSquadre" method="POST">
               <button type="submit" class="btn btn-primary btn-block button-crea-squadre" name="idMiaSquadra" value="<%= squadra.getId() %>">CREA ALTRE SQUADRE</button>
            </form>
            <% } %>
            <p style="text-align: right">I miei crediti: <%=allenatore.getCrediti() %></p>
            <table aria-label="" class="table table-secondary table-striped">
               <thead>
                  <tr>
                     <th scope="col">Overall</th>
                     <th scope="col">Giocatore</th>
                     <th scope="col">Ruolo</th>
                     <th scope="col">Prezzo</th>
                  </tr>
               </thead>
               <tbody>
                  <% for (Giocatore giocatore : giocatori) { %>
                  <tr>
                     <th scope="row"><%=giocatore.getOverall()%></th>
                     <td><%=giocatore.getNickname()%></td>
                     <td><%=giocatore.getRuolo()%></td>
                     <td>
                        <form action="/Progetto_Marco/compra" method="POST">
                           <button type="submit" class="btn btn-acquisto" name="giocatore" value="<%=giocatore.getId()%>"><%=giocatore.getPrezzo()%>c</button>
                        </form>
                     </td>
                  </tr>
                  <% } %>
               </tbody>
            </table>
         </div>
         <% } else { %>
         <div class="row text-center" style="margin-top:10% ">
            <div class="col-md-4"></div>
            <div class="col-md-4">
               <h2 style="Color:black; background-image: linear-gradient(to right, #09DAC3, #04E0C0);">INSERISCI NOME SQUADRA!</h2>
               <form action="mercato.jsp" name="formRegisterSquadra" method="POST">
                  <div class="form-group form-input">
                     <input type="text" class="form-control margin-top-10" name="nomeSquadra" placeholder="Nome Squadra" required>
                  </div>
                  <input type="submit" class="btn btn-outline-light btn-lg margin-top-10" value="Crea Squadra"></input>
               </form>
            </div>
            <div class="col-md-4"></div>
         </div>
         <% } %>
      </div>
   </body>
</html>