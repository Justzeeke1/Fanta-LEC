<% Allenatore allenatore = (Allenatore) session.getAttribute("allenatore"); 
   if (allenatore == null) {	%>
<jsp:forward page="index.jsp"></jsp:forward>
<% } %>
<!doctype html>
<html lang="en" class="h-100">
   <head>
      <meta charset="utf-8">
      <%@ page import="webapp.model.Allenatore" %>
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="description" content="">
      <meta name="author"
         content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
      <meta name="generator" content="Hugo 0.79.0">
      <title>Fanta LEC</title>
      <link rel="canonical"
         href="https://getbootstrap.com/docs/5.0/examples/cover/">
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
         <%@include file="/WEB-INF/css/skinFinePartita.css"%>
         <%@include file="/WEB-INF/css/skinButton.css"%>
      </style>
   </head>
   <body>
      <% String esito = (String) request.getAttribute("esito");
         if (esito != null && esito.equals("VITTORIA")) { %>
      <video autoplay muted class="myVideo">
         <source src="victory.mp4" type="video/mp4">
      </video>
      <p class="big-letter fade">V</p>
      <p class="little-letter fade">ICTOR</p>
      <p class="big-letter fade">Y</p>
      <section id="button-container" style="position: absolute !important">
         <div class="container" style="position: absolute !important">
            <div class="button v18" style="position: absolute !important">
               <a href="home.jsp" style="color: white !important">
               <span class="label fadeButton">CONTINUA</span>
               <span class="icon fadeButton">
               <span></span>
               </span>
               </a>
            </div>
         </div>
      </section>
      <% } else if (esito != null && esito.equals("SCONFITTA")) {%>
      <video autoplay muted class="myVideo">
         <source src="defeat.mp4" type="video/mp4">
      </video>
      <p class="big-letter fade">D</p>
      <p class="little-letter fade">EFEA</p>
      <p class="big-letter fade">T</p>
      <section id="button-container" style="position: absolute !important">
         <div class="container" style="position: absolute !important;">
            <div class="button v19" style="position: absolute !important">
               <a href="home.jsp" style="color: white !important">
               <span class="label fadeButton">CONTINUA</span>
               <span class="icon fadeButton">
               <span></span>
               </span>
               </a>
            </div>
         </div>
      </section>
      <% } %>
   </body>
</html>