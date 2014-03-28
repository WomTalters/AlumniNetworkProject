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
    </head>
    <body>
        <h1>${school.schoolname}</h1>
        <p>Location: ${school.location} Website: ${school.webSiteAddress}</p>


        <c:choose>
            <c:when test="${schatt.startDate == null}">
                Did you go to this school? Add the your start and finish years if you did.</br>
                <form action="StudentSchool" method="post">
                    Start year: <input name="startyear" type="number" />
                    Finish year: <input name="finishyear" type="number" />
                    <input name="schoolname" type="hidden" value="${school.schoolname}"/>
                    <input type="submit" value="Add attendance"> 
                </form>
            </c:when>
            <c:otherwise>
                You can edit these dates by changing the values and clicking update.
                <form action="StudentSchool" method="post">
                    Start year: <input name="startyear" type="number" />
                    Finish year: <input name="finishyear" type="number" />
                    <input name="schoolname" type="hidden" value="${school.schoolname}"/>
                    <input type="submit" value="Update attendance"> 
                </form>

            </c:otherwise>    
        </c:choose>
    </body>
</html>
