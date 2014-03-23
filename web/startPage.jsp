<%-- 
    Document   : startPage
    Created on : 22-Mar-2014, 20:47:53
    Author     : Tom
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buddy Book</title>
    </head>
    <body>

        <div id="error">           
            <h1>${error}</h1>
        </div>    

        <div id="login">
            <h1>Login</h1>
            <form action="Login" method="post">
                Username: <input type="text" name="username"/>
                Password: <input type="password" name="password"/>
                <input type="submit" value="login"/>
            </form>
        </div>
        <div id="signUp">
            <h1>Sign up</h1>
            <form action="SignUp" method="post">
                Username: <input type="text" name="username"/>
                Password: <input type="password" name="password"/>
                </br>
                Firstname: <input type="text" name="firstname"/>
                Lastname: <input type="text" name="lastname"/>
                <input type="submit" value="Sign up"/>
            </form>
        </div>
    </body>
</html>
