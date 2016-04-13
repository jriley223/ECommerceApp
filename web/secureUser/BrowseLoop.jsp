<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %><html>
    <head>
        <meta http-equiv="content-type" content="text/html;charset=utf-8">
        <link rel="stylesheet" type="text/css" href="style.css">
        <title>Browse My Stuff</title>
    </head> 
    <body> 
        <table border>
            <tr>
                <td>
                    <table border>
                        <tr> 
                            <td rowspan="4" align="right">
                                <core:forEach var="oneItem" items="${allItems}">
                                    <form method='post' action="https://localhost:8443/UserManagement/Controller">
                                        <p>
                                            ${oneItem.name}
                                            <input type='hidden' name='itemID'value='${oneItem.itemId}'>
                                            <input type='submit' name='viewItem' value='View Item'>
                                    </form>
                                </core:forEach>
                                <core:if test="${CatalogItem.itemId != null}">
                                <td>
                                    Name: ${CatalogItem.name} 
                            <tr>
                                <td> 
                                    Description: ${CatalogItem.description}
                            <tr>
                                <td>
                                    Price: $${CatalogItem.price}
                            <tr>
                                <td>
                            <form action="https://localhost:8443/UserManagement/Controller" method="post">
                                <p> <input type="hidden" name="itemID"  value="${CatalogItem.itemId}">
                                    <input type="submit" name="addCart"value="Add To Cart">
                            </form>
                </tr>
            </core:if>
        </table>
    <tr>
        <td>
            <table border>
                <tr>
                    <td colspan="2">
                        <form action="https://localhost:8443/UserManagement/Controller" method="post">
                            <p>
                                <input type="submit" name="viewCart" value="View Cart">
                        </form>
            </table>
        </table>
        <p></p>
    </body>
</html>
