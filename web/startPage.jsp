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
        <link href="Style.css" rel="stylesheet" type="text/css">
        <title>Buddy Book</title>
    </head>
    <body>

        <div id="header">
            <img id="headerImg" src="Logo.png" alt="Buddy_Book">
        </div>  


        <div id="error">           
            <h1>${error}</h1>
        </div>    

        <div id="container">

            <div id="leftnav">
                <h1>Login</h1>
                <form action="Login" method="post">
                    Username: <br><input type="text" name="username"/><br><br>
                    Password: <br><input type="password" name="password"/><br><br>
                    <input type="submit" value="login"/>
                </form>
            </div>
            
            <div id="rightnav">
                <br><br><br><br>
                <ul>
                    <li>Find your school!</li>
                    <li>Find your friends!</li>
                    <li>Stay in touch!</li>
                    <li>Join Buddy Book today!</li>
                </ul>	

            </div>
            
            <div id="body">
                <br><br><br>
                <h1>Sign up</h1>
                <form action="SignUp" method="post">
                    <br><br><br>
                    Username: <input type="text" name="username"/>
                    Password: <input type="password" name="password"/>
                    <br><br>
                    Firstname: <input type="text" name="firstname"/>
                    Lastname: <input type="text" name="lastname"/><br><br>
                    <input type="submit" value="Sign up"/>
                </form>
            </div>
        </div>

    </body>
</html>
