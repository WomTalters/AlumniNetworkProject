<%-- 
    Document   : search
    Created on : 01-Apr-2014, 00:57:56
    Author     : Tom
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
        <link href="Style.css" rel="stylesheet" type="text/css">

    </head>
    <body>



        <div id="header">
            <img id="headerImg" src="Logo.png" alt="Buddy_Book">
            <div id="headerLinks">
                <a class="headerButton" href="Logout">Logout</a>
                <a class="headerButton" href="Profile">My profile</a>
                <a class="headerButton" href="Search">Find</a>  
            </div>    
        </div>


        <div id="error">           
            <h1>${error}</h1>
        </div> 

        <div id="container">
            <div id="leftnav">

            </div>

            <div id="rightnav">


            </div>


            <div id="body">


                <div class="listBoxLeft">
                    All Schools:
                    <ul class="list">
                        <c:forEach var="school" items="${allschools}">  
                            <li >
                                <a href="SchoolPage?s=${school.schoolnameUrl}">${school.schoolname}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div class="listBoxRight">
                    All users:
                    <ul>
                        <c:forEach var="userdet" items="${allusers}">
                            <li class="list">
                                ${userdet.username} - <a href="Profile?u=${userdet.username}">${userdet.firstname} ${userdet.lastname}</a>
                            </li>    
                        </c:forEach>
                    </ul>    
                </div>




            </div>

        </div>








    </body>
</html>