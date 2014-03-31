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
                <div id="writeMessage">

                    <form action="Messager" method="POST">
                        <textarea name="messagetext" rows="1" cols="10"></textarea>
                        <input type="hidden" name="recipient" value="${userDetails.username}">
                        <input type="submit" value="send" >                        
                    </form>

                </div>

                <c:forEach var="messagethread" items="${messagethreads}">
                 
                        
                    
                    <div id="messageBlock">
                        <c:forEach var="message" items="${messagethread.messages}" varStatus="status">
                            <c:choose>
                                <c:when test="${status.index==0}">
                                    <div id="message">
                                        <div id="messageText">${message.messageText}</div>
                                        <div id="messageDetails">From: ${message.sender}  Sent at: ${message.dateTimeSent.toString()}  </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div id="messageReply">
                                        <div id="messageText">${message.messageText}</div>
                                        <div id="messageDetails">From: ${message.sender}  Sent at: ${message.dateTimeSent.toString()}  </div>
                                    </div>
                                </c:otherwise>
                            </c:choose> 

                        </c:forEach>

                        <div id="writeReply">
                            <form action="Messager" method="POST">
                                <textarea name="messagetext" rows="1" cols="10"></textarea>
                                <input type="hidden" name="replyto" value="${messagethread.messageThreadId}">
                                <input type="submit" value="reply">
                            </form>    
                        </div>
                    </div>
                                
                </c:forEach>        







            </div>


    </body>
</html>
