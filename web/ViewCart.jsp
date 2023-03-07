<%-- 
    Document   : ViewCart
    Created on : Feb 20, 2023, 4:57:42 PM
    Author     : VietAnhOdyssey
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="DAO.PlantDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap,java.util.Set"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    </head>
    <body>
        <h1>Current User's Cart</h1>
        <a href="MainController">Back to home</a>
         <tr>
                                <th>Product ID</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Image</th>
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
                    int total = 0;
                    for(String eachPlantId:plantIdSet){
                        //Gets the quantity of a plant, based on its PlantID.
                        int quantity = cart.get(eachPlantId);
                        //Contains the image path, and price for each plant.
                        ArrayList<String> plantDetails = PlantDAO.getPlantDetails(Integer.parseInt(eachPlantId));
                        //Adds each plant's price to the total, for each plant selected from the plantDetails List.
                        total+=Integer.parseInt(plantDetails.get(0))*quantity;
                        %>

                        
                        <form action="MainController" method="post"> 
                                <tr>
                                    <td>
                                        <input type="hidden" value="<%=eachPlantId%>" name="plantID"> 
                                        <a href="MainController?action=showPlantDetails&plantId=<%=eachPlantId%>"><%=eachPlantId%></a>
                                    </td>                                        
                                    <td>                                 
                                        <input type="number" value="<%=quantity%>" name="quantity">                                    
                                    </td>
                                    <td> 
                                        <%=plantDetails.get(0)%>
                                    </td>
                                    <td> 
                                        <img 
                                            style="height:100px;width:100px"
                                            src="<%=plantDetails.get(1)%>">
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
                    <h2>Total price: $<%=total%></h2>                    
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
