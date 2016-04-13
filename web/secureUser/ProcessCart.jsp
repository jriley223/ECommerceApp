<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html;charset=utf-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Order Confirmation</title>
    </head>
    <body>
        <p>
            ENJOY MY STUFF :)
        <p>
        <hr>
        <table border>
            <core:forEach var="oneItem" items="${cart}">
                <tr>
                    <td>Name: ${oneItem.name}
                    <td>Price: $${oneItem.price}
                    <td>Description:${oneItem.description}
                    </core:forEach>
        </table>
        <p>
            Total Cost $${priceTotal}.
        <form method="post" action="https://localhost:8443/UserManagement/Controller">
            <input type="submit" name="continueButton" value="Continue Shopping">
        </form>
    </body>
</html>



