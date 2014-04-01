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
            <a id="headerButton" href="Logout">Logout</a>
            <a id="headerButton" href="Profile">My profile</a>
            <a id="headerButton" href="Search">Find</a>           
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


                <div id="listBox">
                    All Schools:
                    <ul id="list">
                        <c:forEach var="school" items="${allschools}">  
                            <li >
                                <a href="SchoolPage?s=${school.schoolname}">${school.schoolname}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <div id="listBox">
                    All users:
                    <ul>
                        <c:forEach var="userdet" items="${allusers}">
                            <li id="list">
                                ${userdet.username} - <a href="Profile?u=${userdet.username}">${userdet.firstname} ${userdet.lastname}</a>
                            </li>    
                        </c:forEach>
                    </ul>    
                </div>




            </div>

        </div>








    </body>
</html>