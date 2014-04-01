<%-- 
    Document   : schoolPage
    Created on : 24-Mar-2014, 19:14:11
    Author     : Tom
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="school" type="Models.School" scope="request" />
<jsp:useBean id="schatt" type="Models.SchoolAttendance" scope="request" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${school.schoolname}</title>
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
                <div id="schoolDetails">
                    School Details: <br><br><br>
                    <p>Location: ${school.location} <br><br>Website: ${school.webSiteAddress}</p>
                </div>
            </div>


            <div id="rightnav">
                <div id="schoolDate">
                    <c:choose>
                        <c:when test="${schatt.startDate == 0}">
                            Did you go to this school? Add the your start and finish years if you did.</br><br>
                            <form action="StudentSchool" method="post">
                                Start year: <input name="startyear" type="number" /><br>
                                Finish year: <input name="finishyear" type="number" /><br><br>
                                <input name="schoolname" type="hidden" value="${school.schoolname}"/>
                                <input type="submit" value="Add attendance"> 
                            </form>
                        </c:when>
                        <c:otherwise>

                            You can edit these dates by changing the values and clicking update.<br><br>
                            <form action="StudentSchool" method="post">
                                Start year: <input name="startyear" type="number" value="${schatt.startDate}" /><br>
                                Finish year: <input name="finishyear" type="number" value="${schatt.finishDate}"/><br><br>
                                <input name="schoolname" type="hidden" value="${school.schoolname}"/>
                                <input type="submit" value="Update attendance"> 
                            </form>

                        </c:otherwise>    
                    </c:choose>
                </div>
            </div>
            <div id="body">
                <div id ="profileName">${school.schoolname}</div>
                <div id="schoolPeople">
                    People who went to this school: <br><br>
                    <c:forEach var="profile" items="${profiles}">
                        <div class="profileListComponent">
                            <a href="Profile?u=${profile.username}">${profile.firstname} ${profile.lastname}</a>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>



    </body>
</html>
