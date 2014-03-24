<%-- 
    Document   : schoolPage
    Created on : 24-Mar-2014, 19:14:11
    Author     : Tom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:useBean id="school" type="Models.School" scope="request" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${school.schoolname}</title>
    </head>
    <body>
        <h1>${school.schoolname}</h1>
        <p>Location: ${school.location} Website: ${school.webSiteAddress}</p>
    </body>
</html>
