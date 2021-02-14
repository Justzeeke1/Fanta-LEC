<% Allenatore allenatore = (Allenatore) session.getAttribute("allenatore"); 
   if (allenatore == null) {	%>
<jsp:forward page="index.jsp"></jsp:forward>
<% } %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="webapp.control.SquadraController" %>
<%@ page import="webapp.control.GiocatoreController" %>
<%@ page import="webapp.control.GiornataController" %>
<%@ page import="webapp.model.Squadra" %>
<%@ page import="webapp.model.Allenatore" %>
<%@ page import="webapp.model.Giocatore" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
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
         <% Squadra squadra = (Squadra) session.getAttribute("squadra");
            SquadraController squadraController = SquadraController.getInstance();
            List<Squadra> classifica = squadraController.findAllOrderByPunteggio();
            GiornataController giornataController = GiornataController.getInstance();
            if (classifica.size() == 4) {
            	giornataController.createScontri();
            }
            Long idGiornata = giornataController.getGiornataDaGiocare();
            List<String> scontri = giornataController.getScontriByIdGiornataDaGiocare(idGiornata);
            Squadra squadraAvversaria = null;
            if (squadra != null) {
            	Long idMiaSquadra = squadra.getId();
            	squadraAvversaria = squadraController.getSquadraAvversaria(scontri, idMiaSquadra);
            }
            GiocatoreController giocatoreController = GiocatoreController.getInstance();
            List<Giocatore> giocatoriTotali = new ArrayList<Giocatore>();
            List<Giocatore> giocatoriTitolari = new ArrayList<Giocatore>();
            List<Giocatore> giocatoriTotaliAvversario = new ArrayList<Giocatore>();
            List<Giocatore> giocatoriTitolariAvversario = new ArrayList<Giocatore>();
            	if (squadra != null) {
            		giocatoriTotali = giocatoreController.findAllMyTeam(squadra);
            	}
            for (Giocatore giocatore : giocatoriTotali) {
            	if (!giocatore.getRiserva()) {
            		giocatoriTitolari.add(giocatore);
            	}
            }
            if (giocatoriTotali.isEmpty()) { %>
         <div
            class="pricing-header px-3 py-3 pt-md-5 pb-md-2 mx-auto text-center">
            <h1 class="display-4 site-header">NON HAI GIOCATORI!</h1>
            <h3 class="lead site-header">Clicca il tasto "MERCATO" in alto per comprare dei giocatori e iniziare la tua carriera.</h3>
         </div>
         <% } else { 
            giocatoriTitolari = giocatoreController.orderTeamView(giocatoriTitolari); %>
         <div class="row">
            <div class="col-md-5">
               <h5 class="site-header" style="margin-top:15%">La tua squadra:</h5>
               <table class="table table-primary table-striped table-sm">
                  <tbody>
                     <% for (Giocatore giocatore : giocatoriTitolari) { %>
                     <tr>
                        <th scope="row"><%=giocatore.getOverall()%></th>
                        <td><%=giocatore.getNickname()%></td>
                        <td><%=giocatore.getRuolo()%></td>
                     </tr>
                     <% } %>
                  </tbody>
               </table>
            </div>
            <div class="col-md-2">
               <h1 style="text-align:center; margin-top: 135px;">VS</h1>
            </div>
            <% if (squadraAvversaria != null) {
               	giocatoriTotaliAvversario = giocatoreController.findAllMyTeam(squadraAvversaria);
               }
               for (Giocatore giocatore : giocatoriTotaliAvversario) {
               		if (!giocatore.getRiserva()) {
               			giocatoriTitolariAvversario.add(giocatore);
               		}
               }
               giocatoriTitolariAvversario = giocatoreController.orderTeamView(giocatoriTitolariAvversario);%>
            <div class="col-md-5">
               <h5 style="margin-top:15%; background:#00e4be">Squadra avversaria:</h5>
               <table class="table table-danger table-striped table-sm" >
                  <tbody>
                     <% for (Giocatore giocatore : giocatoriTitolariAvversario) { %>
                     <tr>
                        <th scope="row"><%=giocatore.getOverall()%></th>
                        <td><%=giocatore.getNickname()%></td>
                        <td><%=giocatore.getRuolo()%></td>
                     </tr>
                     <% } %>
                  </tbody>
               </table>
            </div>
         </div>
         <div class="row" style="margin-top:2%">
            <div class="col-md-5"></div>
            <div class="col-md-2">
               <div class="d-grid gap-2">
                  <% if (giocatoriTitolari.size() < 5) { %>
                  <h3 class="errorFormazione site-header">La tua squadra non è al completo. Devi avere 5 giocatori per avviare lo scontro!</h3>
                  <% } else if (giocatoriTitolariAvversario.size() < 5) { %>
                  <h3 class="errorFormazioneAvv site-header">Attendi che l'avversario sistemi la formazione per giocare!</h3>
                  <% } else { %>
                  <form action="/Progetto_Marco/gioca" method="POST">
                     <input type="hidden" name="idGiornata" value="<%=idGiornata%>"/>
                     <input type="hidden" name="scontro1" value="<%=scontri.get(0)%>"/>
                     <input type="hidden" name="scontro2" value="<%=scontri.get(1)%>"/>
                     <button type="submit" class="btn btn-primary btn-block button-gioca">GIOCA</button>
                  </form>
                  <% } %>
               </div>
            </div>
            <div class="col-md-5"></div>
         </div>
         <div class="row">
            <div class="col-md-4" style="margin-top:8%">
               <table class="table table-borderless table-info">
                  <thead>
                     <tr>
                        <th scope="col"></th>
                        <th scope="col">Squadra</th>
                        <th scope="col">Punti</th>
                        <th scope="col">Vittorie</th>
                        <th scope="col">Sconfitte</th>
                     </tr>
                  </thead>
                  <tbody>
                     <%
                        int i = 1;
                        for (Squadra s : classifica) { %>
                     <tr>
                        <th scope="row"><%=i %></th>
                        <td><%=s.getNome()%></td>
                        <td><%=s.getPunteggio() %></td>
                        <td><%=s.getVittorie() %></td>
                        <td><%=s.getSconfitte() %></td>
                     </tr>
                     <% i++; } %>
                  </tbody>
               </table>
            </div>
            <div class="col-md-4"></div>
            <div class="col-md-4" style="margin-top:4%; cursor: default;">
               <h1 style="color:red">Istruzioni:</h1>
               <p>- Compra i giocatori all'interno della pagina "Mercato".</p>
               <p>- Modifica i giocatori all'interno della formazione nella pagina "My Team".</p>
               <p>- Clicca il pulsante gioca per iniziare la partita.</p>
               <h2 style="text-align: center"> GOOD LUCK :)</h2>
            </div>
         </div>
         <% } %>
      </div>
   </body>
</html>