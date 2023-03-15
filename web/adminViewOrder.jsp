<%-- 
    Document   : adminViewOrder
    Created on : Mar 14, 2023, 9:16:42 PM
    Author     : VietAnhOdyssey
--%>

<%@page import="DTO.Order"%>
<%@page import="DTO.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            ArrayList<Account> accList = (ArrayList<Account>)request.getAttribute("accList");
            if(accList!=null){
                %>
                <table>
                    <tr>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Action</th>                        
                        
                    </tr>
                    <%
                    for(Account eachAcc: accList){
                       %>
                       <tr>
                            <td><%=eachAcc.getFullname()%></td>
                            <td><%=eachAcc.getEmail()%></td>
                            <td><a href="MainController?action=viewOrders&isFromViewHyperlink=true&userEmail=<%=eachAcc.getEmail()%>">
                                    View Orders</a></td>                           
                       </tr>
                       
                        <%
                    
                    }
                    
                    %>
                </table>
        
                <%
            
            }
            //If the user already clicks on the view order hyperlink,
            //OR CLICKED ON THE FILTER DATE SECTION,
            //Searches the orders of that user in the ViewOrdersServlet, AND
            //DISPLAY THEM right underneath.
            if(request.getParameter("isFromViewHyperlink")!=null
            || request.getAttribute("isFromDateFilter")!=null){
                ArrayList<Order> orderList = (ArrayList<Order>)request.getAttribute("orderList");
                String email = request.getParameter("userEmail");
                %>
                <h2>List of orders </h2>
                <h2>Filter Date</h2>
                <form action="MainController">
                    From <input type="date" name="beginDate" 
                            placeholder="dd-mm-yyyy" value=""
                            min="2020-01-01" max="2025-12-31"> <br>
                    To <input type="date" name="endDate" 
                            placeholder="dd-mm-yyyy" value=""
                            min="2020-01-01" max="2025-12-31"> 
                    <input type="hidden" name="userEmail" value="<%=email%>" >
                    <input type="submit" name="action" value="getOrdersWithinDates">
                </form>                
                <table>
                    <tr>
                        <th>Order ID</th>
                        <th>Order Date</th>
                        <th>Order Ship Date</th>
                    </tr>
                    <%
                    for(Order eachOrder:orderList){
                        %>
                        <tr>
                            <td><%=eachOrder.getOrderID()%></td>
                            <td><%=eachOrder.getOrderDate()%></td>
                            <td><%=eachOrder.getShipDate()%></td>                        
                        </tr>   
                        
                    
                        <%
                    
                    }
                    %>
                    
                </table>
                <%
            }
        %>
    </body>
</html>
