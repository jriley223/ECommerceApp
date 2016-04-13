<%-- 
    Document   : index
    Created on : Nov 29, 2009, 4:41:34 PM
    Author     : Tom Ellman
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Site</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <h3>
            Welcome to our web site!
            <br><br><br>
            <p><a href="https://localhost:8443/UserManagement/secureUser/pageU.html" >Login in here</a></p>
            <br><br><br>
            Sign up below:
            <form action="https://localhost:8443/UserManagement/Controller" method="POST">
                <input type="text" name="userName" value="${userName}" required /> Enter Username<br>
                <input type="text" name="emailAddress" value="${emailAddress}"  required/> Enter Email Address<br>
                <input type="password" name="password1" value="" required/> Enter Password<br>
                <input type="password" name="password2" value="" required/> Confirm Password<br>
                <input type="submit" value="Enroll" name="enrollButton" />
            </form>
        </h3>
    </body>
</html>
