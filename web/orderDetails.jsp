<%-- 
    Document   : orderDetails
    Created on : Feb 18, 2023, 12:01:38 PM
    Author     : VietAnhOdyssey
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="DTO.OrderDetails"%>
<%@page import="DAO.OrderDAO"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table id="result-table">
		<tr>
		  <th>Order ID</th>
		  <th>Plant ID</th>
                  <th>Plant Name</th>
                  <th>Image</th>
                  <th>Price</th>
                  <th>Quantity</th>
		</tr>                
	  
	<%
            ArrayList<OrderDetails> orderDetailList = OrderDAO.getOrderDetails
                                                    (Integer.parseInt(request.getParameter("orderId")));
            if(orderDetailList!=null){
                Iterator<OrderDetails> orderIterator= orderDetailList.iterator();                
                while(orderIterator.hasNext()){
                    OrderDetails eachOrderDetails = orderIterator.next();
        %>
        
                    <tr>                        
                        <td><%=eachOrderDetails.getOrderID()%></td>
                        <td><%=eachOrderDetails.getPlantID()%></td>
                        <td><%=eachOrderDetails.getPlantName()%></td>
                        <td><img src="<%=eachOrderDetails.getImgPath()%>"/></td>
                        <td><%=eachOrderDetails.getPrice()%></td>
                        <td><%=eachOrderDetails.getQuantity()%></td>                         
                    </tr>
         <%
                }                        
            }
        %>
	</table>                     
    </body>
</html>
