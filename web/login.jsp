
<%@taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        
    </head>
    <body>
        <h3>
        <form action="https://localhost:8443/UserManagement/j_security_check" method="POST">
            <input type="text" name="j_username"> Username<br>
            <input type="password" name="j_password"> Password<br>
            <input type="submit" value="Login" name="loggingIn" />
        </form>
        </h3>
    </body>
</html>
