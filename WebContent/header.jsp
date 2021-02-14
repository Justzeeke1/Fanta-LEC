<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
      <style>
         <%@include file="/WEB-INF/css/skinHeader.css"%>
         <%@include file="/WEB-INF/css/bootstrap.min.css"%>
      </style>
      <meta charset="UTF-8">
      <title>Insert title here</title>
   </head>
   <body>
      <header class="site-header sticky-top py-1">
         <nav class="container d-flex flex-column flex-md-row justify-content-between">
            <a class="py-2 d-none d-md-inline-block"></a>
            <a class="py-2 fantalec">
            <img class="header-img" src="logoLec.png">Fanta LEC
            </a>
            <a class="py-2 d-md-inline-block"></a>
            <a class="py-2 d-md-inline-block"></a>
            <a class="py-2 d-md-inline-block"></a>
            <a class="py-2 d-md-inline-block"></a>
            <a class="py-2 d-md-inline-block item" href="home.jsp">Home</a>
            <a class="py-2 d-md-inline-block item" href="myteam.jsp">My Team</a>
            <a class="py-2 d-md-inline-block item" href="mercato.jsp">Mercato</a>
            <a class="py-2 d-md-inline-block item" href="/Progetto_Marco/logout">Logout</a>
         </nav>
      </header>
   </body>
</html>