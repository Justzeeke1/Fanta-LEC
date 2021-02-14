<% Allenatore allenatore = (Allenatore) session.getAttribute("allenatore"); 
   if (allenatore == null) {	%>
<jsp:forward page="index.jsp"></jsp:forward>
<% } %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ page import="webapp.control.SquadraController" %>
<%@ page import="webapp.control.GiocatoreController" %>
<%@ page import="webapp.model.Squadra" %>
<%@ page import="webapp.model.Allenatore" %>
<%@ page import="webapp.model.Giocatore" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html class="h-100">
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
      <% 
         GiocatoreController giocatoreController = GiocatoreController.getInstance();
         Squadra squadra = (Squadra) session.getAttribute("squadra");
         List<Giocatore> giocatori = new ArrayList<Giocatore>();
         List<Giocatore> giocatoriRiserva = new ArrayList<Giocatore>();
         	if (squadra != null) {
         		giocatori = giocatoreController.findAllMyTeam(squadra);
         	}
         if (giocatori.isEmpty()) { %>
      <div
         class="px-3 py-3 pt-md-5 pb-md-2 mx-auto text-center">
         <h1 class="display-4 site-header">NON HAI GIOCATORI!</h1>
         <h3 class="lead site-header">Clicca il tasto "MERCATO" in alto per comprare dei giocatori e iniziare la tua carriera.</h3>
      </div>
      <% } else { %>
      <div
         class="px-3 py-3 pb-md-2 mx-auto text-center">
         <h2 class="display-4 site-header">MODIFICA LA TUA SQUADRA</h2>
         <p class="lead site-header">Clicca il tasto "CAMBIA" se vuoi effettuare un cambio tra i tuoi giocatori, oppure clicca il tasto "X" per vendere il giocatore.</p>
      </div>
      <div style="margin-left:8.5%">
         <h1>Nome squadra: <%=squadra.getNome() %></h1>
      </div>
      <div class="row text-center" style="margin-top:2%">
         <div class="col-md-1"></div>
         <% List<Giocatore> giocatoriView = new ArrayList<Giocatore>();
            for (Giocatore giocatore : giocatori) { 
            		if (!giocatore.getRiserva()) {
            			giocatoriView.add(giocatore);
            		} else {
            			giocatoriRiserva.add(giocatore);
            		}
            }
            giocatoriView = giocatoreController.orderTeamView(giocatoriView);
            for (Giocatore giocatore : giocatoriView) { 	
            %>
         <div class="col-md-2">
            <div class="card mb-4 shadow-sm">
               <div class="card-header">
                  <h4 class="my-0 fw-normal">
                  		<form action="/Progetto_Marco/vendi" method="POST" style="text-align: right">
                           <button type="submit" style="text-align: right" class="btn btn-outline-danger" name="giocatore" value="<%=giocatore.getId()%>">X</button>
                        </form>
                        <%=giocatore.getOverall()%>   <%=giocatore.getNickname()%> </h4>
               </div>
               <div class="card-body">
                  <h1 class="card-title pricing-card-title"><%=giocatore.getRuolo()%></h1>
                  <div class="img-player-container">
                     <img src="imgPlayers/<%=giocatore.getLinkFoto() %>" class="img-fluid img-player">
                  </div>
               </div>
            </div>
         </div>
         <% 
            } %>
         <div class="col-md-1"></div>
      </div>
      <% if (!giocatoriRiserva.isEmpty()) { %>
      <h1 style="margin-left:8.5%">Riserve:</h1>
      <div class="row text-center" style="margin-top:2%">
         <div class="col-md-1"></div>
         <% for (Giocatore giocatore : giocatoriRiserva) { %>
         <div class="col-md-2">
            <div class="card mb-4 shadow-sm">
               <div class="card-header">
                  <h4 class="my-0 fw-normal">
                  		<form action="/Progetto_Marco/vendi" method="POST" style="text-align: right">
                           <button type="submit" style="text-align: right" class="btn btn-outline-danger" name="giocatore" value="<%=giocatore.getId()%>">X</button>
                        </form>
                  <%=giocatore.getOverall()%>   <%=giocatore.getNickname()%></h4>
               </div>
               <div class="card-body">
                  <h1 class="card-title pricing-card-title"><%=giocatore.getRuolo()%></h1>
                  <div class="img-player-container">
                     <img src="imgPlayers/<%=giocatore.getLinkFoto() %>" class="img-fluid img-player" style="margin-bottom:2%">
                  </div>
                  <form action="/Progetto_Marco/cambia" method="POST">
                     <button type="submit" class="w-100 btn btn-lg btn-outline-primary button-cambia" name="giocatore" value=<%=giocatore.getId()%>>CAMBIA</button>
                  </form>
               </div>
            </div>
         </div>
         <% } 
            } else { %>
         <h2 class="display-4 site-header" style="margin-top:5%; text-align:center;">NON HAI RISERVE!</h2>
         <% } %>
      </div>
      <% } %>
   </body>
</html>