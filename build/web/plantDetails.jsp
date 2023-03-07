<%-- 
    Document   : plantDetails
    Created on : Mar 5, 2023, 11:27:39 AM
    Author     : VietAnhOdyssey
--%>

<%@page import="DTO.Plant"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%Plant foundPlant = (Plant)request.getAttribute("foundPlant");%>
        <h2>Plant Details</h2>
        <a href="ViewCart.jsp">Back to cart</a>
        <table>
            <th>
                <td>Name</td>
                <td>Price</td>
                <td>Image</td>
                <td>Description</td>
                <td>Status</td>
                <td>Category</td>
            </th>
            <tr>
                <td><%=foundPlant.getName()%></td>
                <td><%=foundPlant.getPrice()%></td>
                <td><img 
                        src="<%=foundPlant.getImgpath()%>"
                        style="height:100px;width:100px">
                </td>
                <td><%=foundPlant.getDescription()%></td>
                <td><%=foundPlant.getStatus()%></td>
                <td><%=foundPlant.getCatename()%></td>
            </tr>
        </table>
    </body>
</html>
