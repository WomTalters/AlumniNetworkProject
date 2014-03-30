<%-- 
    Document   : profile
    Created on : 22-Mar-2014, 21:51:24
    Author     : Tom
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<jsp:useBean id="user" type="Models.User" scope="session" />
<jsp:useBean id="userDetails" type="Models.UserDetails" scope="request" />


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${userDetails.firstname}'s Profile</title>

    </head>
    <body>



        <div id="headerBar">


            <div class="title"><h1 class="title">Buddy Book</h1></div>

            <div class="logout"><a class="logout" href="Logout">Logout</a></div>

        </div>


        <div id="error">           
            <h1>${error}</h1>
        </div> 

        <div class="contents">

            <c:if test="${userDetails.username == user.username}">
                <div id="editBox">
                    <form action="EditProfile" method="post">
                        Edit firstname: <input type="text" name="firstname" value="${userDetails.firstname}"/>
                        Edit lastname: <input type="text" name="lastname" value="${userDetails.lastname}"/>
                        Edit description: <textarea name="description" rows="5" cols="5">${userDetails.description}</textarea>
                        <input type="submit" value="Update">
                    </form>   
                </div>
            </c:if>



            <h1 id="profileName">${userDetails.firstname} ${userDetails.lastname}</h1>

            <div id="infoBox">
                <div id="description">
                    Description:</br>
                    <p>
                        <c:choose>
                            <c:when test="${userDetails.description == null}">
                                <c:choose>
                                    <c:when test="${userDetails.username == user.username}">
                                        You haven't entered a description. You can add one
                                        by using the edit box.

                                    </c:when>
                                    <c:otherwise>
                                        This person hasn't entered a description
                                    </c:otherwise>    

                                </c:choose>
                            </c:when>  
                            <c:otherwise>
                                ${userDetails.description}
                            </c:otherwise>
                        </c:choose>
                    </p>                                        
                </div>
                
                <div id="userSchoolBox">
                    Attended Schools:
                    <c:forEach var="school" items="${schools}">
                        <div id="userSchoolBoxComponent">
                            School : <a href="SchoolPage?s=${school.schoolname}">${school.schoolname}</a></br> 
                            Started in: ${school.startDate}</br> 
                            Finished in: ${school.finishDate}
                        </div>
                    </c:forEach>
                </div>

                <div id="schoolmateBox">
                    Schoolmates:
                    <c:forEach var="profile" items="${schoolmates}">

                        <div id="schoolmateBoxComponent">
                            <a href="Profile?u=${profile.username}">${profile.firstname} ${profile.lastname}</a> 
                        </div>          
                    </c:forEach>
                </div>
                
                <div id="schoolBox">
                    All Schools:
                    <c:forEach var="school" items="${allschools}">
                        <div id="schoolBoxComponent">
                            School : <a href="SchoolPage?s=${school.schoolname}">${school.schoolname}</a></br> 
                        </div>
                    </c:forEach>
                </div>
                
                <div id="messages">
                    <div id="messageBlock">
                        <div id="message">
                            <div id="messageText">hello fred</div>
                            <div id="messageDetails">From bert</div>
                        </div>
                        <div id="messageReply">
                            <div id="messageText">fuck off</div>
                            <div id="messageDetails">From fred</div>
                        </div>
                    </div>
                    
                    <div id="messageBlock">
                        <div id="message">
                            <div id="messageText">give me the money</div>
                            <div id="messageDetails">From bill</div>
                        </div>
                        <div id="messageReply">
                            <div id="messageText">i don't have any</div>
                            <div id="messageDetails">From bert</div>
                        </div>
                        <div id="messageBlock">                        
                        <div id="messageReply">
                            <div id="messageText">alright i'll take ur kidney then</div>
                            <div id="messageDetails">From bill</div>
                        </div>
                    </div>
                    </div>
                    
                </div>
                    

            </div>    
    </body>
</html>
