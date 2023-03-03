<%-- 
    Document   : ViewCart
    Created on : Feb 20, 2023, 4:57:42 PM
    Author     : VietAnhOdyssey
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap,java.util.Set"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    </head>
    <body>
        <h1>Current User's Cart</h1>
         <tr>
                                <th>Product ID</th>
                                <th>Quantity</th>
                                <th>Action</th>
        </tr>
        <%
            HashMap<String,Integer> cart = (HashMap<String,Integer>)session.getAttribute("cart");
            if(cart!=null){
                //Creates a set of plantId, from the cart HashMap.
                //A set is an unordered collection of objects, WITHOUT DUPLICATES.
                %>
                
                <%
                    Set<String> plantIdSet = cart.keySet();
                    for(String eachPlantId:plantIdSet){
                        int quantity = cart.get(eachPlantId); //Gets the quantity of a plant, based on its PlantID.
                        %>

                        
                        <form action="MainController" method="post"> 
                                <tr>
                                    <td>
                                        <input type="hidden" value="<%=eachPlantId%>" name="plantID"> 
                                        <a href="#"><%=eachPlantId%></a>
                                    </td>                                                        
                                    <td>                                 
                                        <input type="number" value="<%=quantity%>" name="quantity">                                    
                                    </td>
                                     <td>                                 
                                        <input type="submit" value="update" name="action">
                                    </td>
                                    <td>                                 
                                        <input type="submit" value="delete" name="action">
                                    </td>
                                </tr>
                        </form>
                                
                    <%
                    }   
                    %>
                    <button name="action" value="submitOrder" onClick="location.href='MainController?action=submitOrder'">Create le new order
                                    </button>                                                        
                    
                    <%
            }else{
                %>
                <h2>No products found in the cart!</h2>
                <%
            }
        %>
        <%
            String message = (String)request.getAttribute("warningMsg");
            if(message!=null){
                %>
                <h2>${warningMsg}</h2>
                <%
            }
        %>
        
        
    </body>
</html>
