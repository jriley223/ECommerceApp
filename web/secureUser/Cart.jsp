<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html;charset=utf-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>User's Cart</title>
    </head>
    <body>
        <p>
            Here are the contents of your cart.
        <hr>
        <table border>
            <core:forEach var="oneItem" items="${cart}">
                <tr>
                    <td>Name: ${oneItem.name}
                    <td>Description: ${oneItem.description}
                    <td>Price: $${oneItem.price}
                    </core:forEach>
        </table>
        <form method="post" action="https://localhost:8443/UserManagement/Controller">
            <input type="submit" name="continueButton" value="Continue Shopping">
            <input type="submit" name="emptyCart" value="Empty Cart">
            <input type="submit" name="billingInfo" value="Purchase">
            <input type="submit" name="purchaseHistory" value="Purchase History">
        </form>
    </body>
</html>
