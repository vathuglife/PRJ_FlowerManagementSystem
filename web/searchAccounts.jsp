<%-- 
    Document   : searchAccounts
    Created on : Mar 7, 2023, 2:32:34 PM
    Author     : VietAnhOdyssey
--%>

<%@page import="DTO.Account"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>        
                        
                <form action="MainController" method="post">
                    <h1>Type here to search: </h1>
                    <input type="text" name="searchAccInputBox">
                    <input type="submit" value="searchAccounts" name="action">
                </form>
                <a href="admin.jsp">Back to main admin page</a>
               

            <%
            Account foundAccount = (Account)request.getAttribute("foundAccount");
            if(foundAccount!=null){%>
                 <table>
                    <tr>
                        <th>Id</th>
                        <th>Email</th>
                        <th>Full Name</th>
                        <th>Status</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Action</th>
                    </tr>
                <tr>
                    <td><%=foundAccount.getAccID()%></td>
                    <td><%=foundAccount.getEmail()%></td>
                    <td><%=foundAccount.getFullname()%></td>
                    <td><%
                        int status = foundAccount.getStatus();
                        if(status==1){%>Blocked<%}
                        else{ %>Normal<%}
                    %>
                    </td>
                    <td><%=foundAccount.getPhone()%></td>                                                
                    <td><%
                        if(foundAccount.getRole()== 1) {
                            %>Administrator<%                            
                        }else{
                        %> Normie User<%
                        }   
            }else{
                %>
                    <h2>User is not found, or the server might be f-ed up.</h2>
                <%
            }

        %></table>
            
    </body>
</html>
