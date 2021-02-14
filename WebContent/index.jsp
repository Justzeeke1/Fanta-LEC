<%@ page import="webapp.control.AllenatoreController" %>
<!doctype html>
<html lang="en" class="h-100">
   <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <title>Fanta LEC</title>
      <style>
         .bd-placeholder-img {
	         font-size: 1.125rem;
	         text-anchor: middle;
	         -webkit-user-select: none;
	         -moz-user-select: none;
	         user-select: none;
         }
         @media ( min-width : 768px) {
	         .bd-placeholder-img-lg {
	         	font-size: 3.5rem;
	         }
         }
         <%@include file="/WEB-INF/css/bootstrap.min.css"%>
         <%@include file="/WEB-INF/css/cover.css"%>
      </style>
   </head>
   <body class="d-flex h-100 text-center text-white bg-dark">
      <% AllenatoreController allenatoreController = AllenatoreController.getInstance();
         String token = (String) request.getAttribute("loginToken");%>
      <div class="container d-flex w-100 h-100 p-3 mx-auto flex-column">
         <div class="mb-auto"></div>
         <div class="row" style="margin-top:10%">
            <div class="col-md-4">
               <h1 style="Color:black">Benvenuto!</h1>
               <p class="lead">Clicca "Continua" per effettuare il login.
                  <% if (allenatoreController.findAll().size() < 4) { %>
                  <br>Oppure, "Gioca" per iniziare una nuova partita.
               </p>
               <form action="/Progetto_Marco/setLoginToken" name="formSetTokenRegister" method="POST">
                  <input type="hidden" name="token" value="Registrati">
                  <input type="submit" class="btn btn-outline-light btn-lg" value="GIOCA"></input>
               </form>
               <p class="lead">o</p>
               <% } %>
               <form action="/Progetto_Marco/setLoginToken" name="formSetTokenLogin" method="POST">
                  <input type="hidden" name="token" value="Login">
                  <input type="submit" class="btn btn-outline-light btn-lg" value="CONTINUA"></input>
               </form>
            </div>
            <div class="col-md-4"></div>
            <div class="col-md-4">
               <% if (token != null) { %>
               <h2 style="Color:black">INSERISCI I TUOI DATI!</h2>
               <form action="<%=token%>" name="formLoginRegister" method="POST">
                  <div class="form-group form-input">
                     <input
                        type="text" class="form-control" name="usernameLogin" placeholder="Username" required autofocus>
                  </div>
                  <div class="form-group form-input">
                     <input
                        type="password" class="form-control margin-top-8" name="passwordLogin"
                        placeholder="Password" required>
                  </div>
                  <input type="submit" class="btn btn-outline-light btn-lg margin-top-10" value="<%=token%>"></input>
               </form>
               <% } %>
            </div>
         </div>
         <div class="row" style="margin-top:5%">
            <div class="col-md-4">
               <h3 style="Color:black">Come ho realizzato la Web App?</h3>
               <p>La parte grafica è stata fatta interamente con librerie Bootstrap, mentre la parte logica con jsp, aggiungendo anche un database
                  realizzato con MySQL.
               </p>
            </div>
            <div class="col-md-4"></div>
            <div class="col-md-4">
               <h4 style="Color:black">A cosa mi sono ispirato e perchè?</h4>
               <p>Mi sono ispirato alla parte competitiva e professionistica del gioco "League of Legends", prenderne spunto è stato abbastanza facile,
                  ed ho realizzato questo progetto perché fino ad ora una versione di "Fanta Calcio" per questo gioco non esisteva.
               </p>
            </div>
         </div>
         <div class="row" style="margin-top:5%">
            <div class="col-md-4">
               <h3 style="Color:black">Cosa significa "LEC"?</h3>
               <p>"LEC" sta per League of Legends European Championship.</p>
            </div>
            <div class="col-md-4"></div>
            <div class="col-md-4">
               <h3 style="Color:black">Cosa fa questa Web App?</h3>
               <p>Questa Web App non è altro che un gioco stile Fanta Calcio dove i giocatori sono i professionisti della scena competitiva europea di League of Legends.<br>
                  Puoi comprare giocatori, sistemare la tua formazione e simulare le partite.
               </p>
            </div>
         </div>
         <footer class="mt-auto text-white-50">
            <p>All right reserved to their respective owners ©.</p>
         </footer>
      </div>
   </body>
</html>