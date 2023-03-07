/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Order;
import DTO.OrderDetails;
import Tools.DBUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author VietAnhOdyssey
 */
public class OrderDAO {
    public static ArrayList<Order>getOrders(String email,int option){
      //options: 1: in progress, 2: completed, 3: canceled. 4: all.
      ArrayList<Order> orderList = null;        
      Connection cn = null;
      PreparedStatement pst = null;
      ResultSet rst = null;
      
      try{            
          cn = DBUtils.makeConnection();
          if(cn!=null){
              orderList = new ArrayList<Order>();
              String query = "SELECT o.OrderID,o.OrdDate,o.shipdate,o.status,a.accID \n" +
                        "FROM Accounts a JOIN Orders o ON a.accID = o.AccID\n" +
                        "WHERE a.email = ? ";
                            
              switch(option){
                  case 1:
                      query = query + "AND o.status=1";
                      break;
                  case 2:
                      query = query + "AND o.status=2";
                      break;
                  case 3:
                      query = query + "AND o.status=3";
                      break;                  
              }
              pst = cn.prepareStatement(query);
              pst.setString(1,email);
              rst = pst.executeQuery();
              while(rst.next()){
                  Order eachOrder = new Order(
                          Integer.parseInt(rst.getString("OrderID")),
                          rst.getString("OrdDate"),
                          rst.getString("shipdate"),
                          Integer.parseInt(rst.getString("status")),
                          Integer.parseInt(rst.getString("accID"))
                  );
                  orderList.add(eachOrder);
              }              
          }
      }catch(Exception e){
          System.out.println(e.getMessage());
      }
      return orderList;
    }
    
    public static ArrayList<OrderDetails>getOrderDetails(int orderID){
      ArrayList<OrderDetails> orderDetailsList = null;        
      Connection cn = null;
      PreparedStatement pst = null;
      ResultSet rst = null;
      
      try{            
          cn = DBUtils.makeConnection();
          if(cn!=null){
              orderDetailsList = new ArrayList<OrderDetails>();
              String query = "SELECT od.DetailId,od.OrderID,p.PID,p.PName,p.price,p.imgPath,od.quantity \n" +
                            "FROM OrderDetails od JOIN Plants p \n" +
                            "ON od.FID = p.PID\n" +
                            "WHERE OrderID = ?";
              pst = cn.prepareStatement(query);
              pst.setInt(1,orderID);
              rst = pst.executeQuery();
              //If query result is found within the application:
              while(rst.next()){
                  OrderDetails eachOrderDetails = new OrderDetails(
                          rst.getInt("DetailID"),
                          rst.getInt("OrderID"),
                          rst.getInt("PID"),
                          rst.getString("PName"),
                          rst.getInt("price"),
                          rst.getString("imgPath"),
                          rst.getInt("quantity")
                  );
                  orderDetailsList.add(eachOrderDetails);
              }
          }
      }catch(Exception e){
          System.out.println(e.getMessage());
      }
      return orderDetailsList;
    }
    public static boolean AddOrder(String email,HashMap<String,Integer> cart){
        boolean isAdded = false;        
        PreparedStatement pst = null;
        ResultSet rst = null;
        try{
            Connection cn = DBUtils.makeConnection();
            if(cn!=null){
                //Turns off AutoCommit.
                /*AutoCommit On: AUTOMATICALLY RUNS EACH SQL Statement.
                  AutoCommit Off: Gathers a list of SQL Statements into 1 SINGLE GROUP 
                                  (call this A). If one of those SQL Statements fails, 
                                   the entire group WILL BE CANCELLED, to prevent unwanted
                                   changes in the database. If nothing goes wrong, everything will be 
                                   run like normal.
                */
                cn.setAutoCommit(false);
                //Step 1: Get the accId of the target user, base
                //We can't get the Id right from the Account class (object), because
                //there are no such attribute in there.
                String sqlQuery = "SELECT accID FROM Accounts WHERE Accounts.email="+"\'"+email+"\'";
                pst = cn.prepareStatement(sqlQuery);                
                rst = pst.executeQuery();
                int foundAccId = 0;
                //For some reasons, WITHOUT rst.next(), THE CODE WILL BE FUCKED UP.
                if(rst!=null && rst.next()){
                    foundAccId = rst.getInt("accID");
                }                
                //Step 2: Insert the new order of the found user in the database.
                sqlQuery = "INSERT INTO Orders (OrdDate,status,AccID)" +
                                  "VALUES (?,?,?)";
                Date d = new Date(System.currentTimeMillis());
                int status = 1;
                pst = cn.prepareStatement(sqlQuery);
                pst.setDate(1,d);
                pst.setInt(2,status);
                pst.setInt(3,foundAccId);                                             
                pst.executeUpdate();
                
                //Step 3: Get the Id of the order added at step 2, in order to add
                //that into the OrderDetails table (OrderID,FID,Quantity)of the PlantShop Database.
                //Because it is a newly added order, it SHOULD BE at the FINAL POSITION OF THE TABLE.
                //The order detail can be found in the cart (HashMap<String,Integer> cart).                
                sqlQuery = "SELECT TOP 1 OrderID FROM Orders order by orderId desc";                           
                
                pst = cn.prepareStatement(sqlQuery);                                                 
                rst = pst.executeQuery();
                int foundOrderId = 0;
                if (rst!=null && rst.next()){
                    foundOrderId = rst.getInt("OrderID");                    
                }                
                //Step 4: Insert the new Order Details into the OrderDetails table.
                Set<String> plantIdList = cart.keySet();
                for(String eachPlantId:plantIdList){
                    sqlQuery = "INSERT INTO OrderDetails(OrderID,FID,quantity)\n" +
                                "VALUES (?,?,?)";
                    pst = cn.prepareStatement(sqlQuery);
                    pst.setInt(1,foundOrderId);
                    pst.setInt(2,Integer.parseInt(eachPlantId.trim()));
                    pst.setInt(3,cart.get(eachPlantId)); //gets the quantity of the plantId.
                    pst.executeUpdate();
                    cn.commit();                    
                    cn.setAutoCommit(true);                
                }
                isAdded = true;
            }
        }catch(Exception e){
            SQLException se = (SQLException)e;
            int errorCode = se.getErrorCode();
            System.out.println(errorCode);
            
        }
                
        return isAdded;
    }
    public static boolean Reorder(int orderID){
        boolean isReordered = false;
        PreparedStatement pst = null;        
        try{
            Connection cn = DBUtils.makeConnection();
            if(cn!=null){
                //Turns off AutoCommit.
                /*AutoCommit On: AUTOMATICALLY RUNS EACH SQL Statement.
                  AutoCommit Off: Gathers a list of SQL Statements into 1 SINGLE GROUP 
                                  (call this A). If one of those SQL Statements fails, 
                                   the entire group WILL BE CANCELLED, to prevent unwanted
                                   changes in the database. If nothing goes wrong, everything will be 
                                   run like normal.
                */                
                //Step 1: Get the accId of the target user, base
                //We can't get the Id right from the Account class (object), because
                //there are no such attribute in there.
                String sqlQuery = "UPDATE Orders\n" +
                                   "SET status = 1 WHERE OrderID = ?";
                pst = cn.prepareStatement(sqlQuery);                
                pst.setInt(1,orderID);
                int result = pst.executeUpdate();
                if(result!=1) isReordered = false;
                else isReordered = true;
                
            }
        }catch(Exception e){
            SQLException se = (SQLException)e;
            int errorCode = se.getErrorCode();
            System.out.println(errorCode);
            
        }
        return isReordered;
    }
}
