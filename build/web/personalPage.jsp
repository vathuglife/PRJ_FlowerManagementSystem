<%-- 
    Document   : personalPage
    Created on : Feb 14, 2023, 10:32:27 AM
    Author     : VietAnhOdyssey
--%>

<%@page import="DAO.AccountDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!--Allows this JSP page to get the data from the session.-->
<%@ page import="java.util.Iterator" %>
<%@ page session="true" %>
<%@page import="DTO.Order"%> 
<%@page import="DTO.Account"%> 
<%@page import="DAO.OrderDAO"%> 
<%@page import="java.util.ArrayList"%> 



<!DOCTYPE html>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Kore wa user page bietch</title>
    </head>
    <body>
        <a href="MainController">Back to home</a>
        <%
            /*Checks if the user is already logged in WHEN HE/SHE access this PersonalPage
            FROM ANOTHER PAGE (e.g. Home Page), BUT NOT FROM THE Login Page.
            This is also what's called a guard clause - kicks that person out if that person
            has not logged in yet.*/
            
            //If the currentAcc object is null, this means, the user
            //enters this personalPage from another page BUT NOT THE LoginPage.
            //So we need to check using the session cookie.
            Account receivedAcc = (Account)session.getAttribute("loggedInAccount");
            
            boolean isLoggedIn = false;
            Account account = null;
            Cookie[] c = request.getCookies();
            if(receivedAcc==null){                
                for(Cookie eachCookieAttribute:c){
                    if(eachCookieAttribute.getName().equals("userCookie")){
                        String token = eachCookieAttribute.getValue();  
                        account = AccountDAO.CheckAccount(token);
                        if(account !=null){                            
                            isLoggedIn = true;
                            break;
                        }
                    }
                    
                }
            }else if (receivedAcc!=null) {
                isLoggedIn = true;
                account = receivedAcc;
            }
            
            
            /*The data retrieval stage. Only shows appropriate 
            data when a user is logged in.*/
            if(isLoggedIn==false){
                %>
                <h2>User is NOT LOGGED IN YOU DUMMY 3K!</h2>
                <%            
            }else{                
                ArrayList<Order> orderList= null;
                if(request.getAttribute("orderList")==null){
                    //Case 1: User enters the personal page for THE FIRST TIME.
                    orderList = OrderDAO.getOrders(account.getEmail(),4);
                }else{
                    //Case 2: User changes the Order option (all/completed...), then hits enter. 
                    //This means, a new list (called A) containing THE ORDERS BY CATEGORY WILL 
                    //BE RECREATED.
                    //This also means, the list A is already created, and attached to the request, 
                    //thanks to ShowOrdersServlet.
                    orderList = (ArrayList<Order>)request.getAttribute("orderList");
                }

            %>                
                <h1>Kore wa user page bietch</h1>
                <h2>Username: <%=account.getFullname()%></h2>
                <h2>Email: <%=account.getEmail()%></h2>
                <h2>Phone: <%=account.getPhone()%></h2>
                     
             <div >                
                 <a href="UserDetailsPage.jsp">Update details</a>
                 <a href="MainController?action=Logout">Logout</a>
            </div>             
            
                <form action="MainController">               
                    <select name="sortOrdersBy">
                        <option value="all">All</option>
                        <option value="completed">Completed</option>
                        <option value="progress">In Progress</option>
                        <option value="canceled">Canceled</option>
                    </select>
                    <input type="submit" value="sortOrders" name="action">
                </form>
            <table id="result-table">
		<tr>
		  <th>Order ID</th>
		  <th>Order Date</th>
                  <th>Ship Date</th>
                  <th>Order's status</th>
                  <th>action</th>
		</tr>                
	  
	<%
            if(orderList!=null){
                Iterator<Order> orderIterator= orderList.iterator();                
                while(orderIterator.hasNext()){
                    Order eachOrder = orderIterator.next();
        %>
        
                    <tr>                        
                        <td><%=eachOrder.getOrderID()%></td>
                        <td><%=eachOrder.getOrderDate()%></td>
                        <td><%=eachOrder.getShipDate()%></td>
                        
                        <td>
                            <%
                                int status = eachOrder.getStatus();
                                if(status==1){
                                    %>Processing<%
                                }else if(status==2){
                                    %>In Progress<%
                                }else{
                                    %>Canceled
                                    <a href="MainController?action=submitOrder">Order Again </a>
                                    <%
                                    /*Attach each canceled Order into the request body.
                                        This is to help personalPage.jsp send this Order 
                                        to the SubmitOrderServlet, using the setAttribute
                                        method and getAttribute method.
                                        */
                                     session.setAttribute("currentOrder",eachOrder);
                                }
                            %>
                        </td>
                        <td><a href="orderDetails.jsp?orderId=<%=eachOrder.getOrderID()%>">Details</a></td>                        
                    </tr>
         <%
                }                        
            }
        %>
	</table>             
        <%}%>  
    </body>
</html>
