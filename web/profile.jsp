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
                <c:if test="${userDetails.username == user.username}">
                    <br><br>    
                    <form action="EditProfile" method="post">
                        Edit first name: <input class="input" type="text" name="firstname" value="${userDetails.firstname}"/><br><br>
                        Edit last name: <input class="input" type="text" name="lastname" value="${userDetails.lastname}"/><br><br>
                        Edit description: <textarea class="input" name="description" rows="14" cols="20">${userDetails.description}</textarea><br><br>
                        <input class="button" type="submit" value="Update">
                    </form>   

                </c:if>
            </div>

            <div id="rightnav">
                <div class="messages">
                    <div class="writeMessage">

                        <form action="Messager" method="POST">
                            <textarea class="writeMessageArea" name="messagetext" rows="3" cols="8"></textarea>
                            <input type="hidden" name="recipient" value="${userDetails.username}">
                            <input type="hidden" name="from" value="${userDetails.username}">
                            <input class="messageSend" type="submit" value="send" >                        
                        </form>

                    </div>

                    <c:forEach var="messagethread" items="${messagethreads}">

                        <div class="messageBlock">
                            <div class="conversationPartner">Conversation with:<br> 
                                
                                <c:choose>
                                    <c:when test="${messagethread.seneder==user.username}">
                                        ${messagethread.recFullname}                                        
                                    </c:when>
                                    <c:otherwise>
                                        ${messagethread.senFullname}
                                    </c:otherwise>                                                                    
                                </c:choose>
                                
                                
                                
                                
                                
                                
                                <c:if test="${user.username==messagethread.recFullname}">
                                    ${messagethread.recFullname} 
                                </c:if>
                                
                                <c:if test="${user.username==messagethread.recFullname}">
                                    ${messagethread.recFullname} 
                                </c:if>
                                
                                
                                
                            </div>

                            <c:forEach var="message" items="${messagethread.messages}" varStatus="status">
                                <c:choose>
                                    <c:when test="${status.index==0}">
                                        <div class="message">
                                            <div class="messageText">${message.messageText}</div>
                                            <div class="messageDetails">From: ${message.senFullname}<br>  Sent at: ${message.dateTimeSent.toString()}  </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="messageReply">
                                            <div class="messageText">${message.messageText}</div>
                                            <div class="messageDetails">From: ${message.senFullname}<br>  Sent at: ${message.dateTimeSent.toString()}  </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose> 

                            </c:forEach>

                            <div class="writeReply">
                                <form action="Messager" method="POST">
                                    <textarea class="writeMessageArea" name="messagetext" rows="2" cols="10"></textarea>
                                    <input type="hidden" name="replyto" value="${messagethread.messageThreadId}">
                                    <input type="hidden" name="from" value="${userDetails.username}">
                                    <input class="messageSend" type="submit" value="reply">
                                </form>    
                            </div>
                        </div>

                    </c:forEach>        

                </div>
            </div>

            <div id="body">
                <div id="profileName">${userDetails.firstname} ${userDetails.lastname}</div>

                <div id="infoBox">
                    <div id="description">
                        Description:
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
                            <div class="userSchoolBoxComponent">
                                School : <a href="SchoolPage?s=${school.schoolnameUrl}">${school.schoolname}</a><br> 
                                Started in: ${school.startDate}<br> 
                                Finished in: ${school.finishDate}
                            </div>
                        </c:forEach>
                    </div>

                    <div id="schoolmateBox">
                        Schoolmates:
                        <c:forEach var="profile" items="${schoolmates}">

                            <div class="schoolmateBoxComponent">
                                <a href="Profile?u=${profile.username}">${profile.firstname} ${profile.lastname}</a> 
                            </div>          
                        </c:forEach>
                    </div>

                </div>

            </div>

        </div>








    </body>
</html>
