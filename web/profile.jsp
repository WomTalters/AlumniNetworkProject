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

        <h1>${error}</h1>

        <c:if test="${profileUser.username == user.username}">
            <div id="editBox">
                <form action="EditProfile" method="post">
                    Edit firstname: <input type="text" name="firstname" value="${profileUser.firstname}"/>
                    Edit lastname: <input type="text" name="lastname" value="${profileUser.lastname}"/>
                    Edit description: <textarea name="description" rows="5" cols="5">${profileUser.description}</textarea>
                    <input type="submit" value="Update">
                </form>   
            </div>
        </c:if>

        <a href="Logout">Logout</a>

        <h1>${profileUser.firstname} ${profileUser.lastname}</h1>
        <div id="infoBox">
            Description:
            </br>
            <p>
                <c:choose>
                    <c:when test="${profileUser.description == null}">


                        <c:choose>
                            <c:when test="${profileUser.username == user.username}">
                                You haven't entered a description. You can add one
                                by clicking using the edit box.

                            </c:when>
                            <c:otherwise>
                                This person hasn't entered a description
                            </c:otherwise>    

                        </c:choose>
                    </c:when>  
                    <c:otherwise>
                        ${profileUser.description}
                    </c:otherwise>
                </c:choose>

            </p>            

        </div>

    </body>
</html>
