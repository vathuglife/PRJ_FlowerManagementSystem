<%-- 
    Document   : UserDetailsPage
    Created on : Mar 3, 2023, 10:12:05 AM
    Author     : VietAnhOdyssey
--%>

<%@page import="DTO.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Update Profile</title>
    </head>
    <body>
        <h2>Update Profile</h2>
        
        <%
        HttpSession currentSession = request.getSession();
        Account receivedAcc = (Account) currentSession.getAttribute("loggedInAccount");
        if (receivedAcc!=null) {
            %>
            <form action="MainController" method="post">
                <table>
                    <th>
                        <td>Information</td>
                        <td>Current</td>
                        <td>New</td>                    
                    </th>
                    <tr>
                        <td>Name</td>
                        <td><%=receivedAcc.getFullname()%></td>
                        <td><input type="text" name="newFullname"></td>                    
                    </tr>
                     <tr>
                        <td>Phone</td>
                        <td><%=receivedAcc.getPhone()%></td>
                        <td><input type="text" name="newPhone"></td>                    
                    </tr>                   
                </table>
                <input type="submit" value="UpdateDetails" name="action">
            </form>
            <%
        }
        %>
    </body>
</html>
