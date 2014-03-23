<%-- 
    Document   : profile
    Created on : 22-Mar-2014, 21:51:24
    Author     : Tom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<jsp:useBean id="user" type="Models.User" scope="session" />
<jsp:useBean id="profileUser" type="Models.User" scope="request" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${profileUser.firstname}'s Profile</title>

    </head>
    <body>
        <c:if test="${profileUser.username == user.username}">
            <a href="Profile?edit=true">Edit Profile</a>
        </c:if>
            
            <a href="Logout">Logout</a>

        <h1>${profileUser.firstname} ${profileUser.lastname}</h1>
        <div id="infoBox">
            <c:if test="${profileUser.description == null}">
                <p>
                    Description:
                    </br>
                    <c:choose>
                        <c:when test="${profileUser.username == user.username}">
                            You haven't entered a description. You can add one
                            by clicking the edit profile button.

                        </c:when>
                        <c:otherwise>
                            This person hasn't entered a description
                        </c:otherwise>    

                    </c:choose>
                </c:if>  
            </p>            

        </div>

    </body>
</html>
