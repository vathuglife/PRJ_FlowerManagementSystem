<%-- 
    Document   : error
    Created on : Mar 15, 2023, 8:49:35 AM
    Author     : VietAnhOdyssey
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Current message</h1>
        <%
            String msg = (String)request.getAttribute("msg");
            if(msg!=null){
            %>
                <h2><%=msg%></h2>  
            <%
            
            }
        %>
        <a href="index.jsp">Back to home</a>
    </body>
</html>
