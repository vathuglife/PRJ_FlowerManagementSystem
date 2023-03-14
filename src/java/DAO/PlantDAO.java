/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Plant;
import Tools.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author VietAnhOdyssey
 */
public class PlantDAO {
    public static ArrayList<Plant> getPlants(String keyword,String searchby){
        ArrayList plants = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        Plant plant = null;
        try{
            cn = DBUtils.makeConnection();
            if (cn!=null){
                plants = new ArrayList<Plant>();
                String baseQuery = "SELECT * FROM Plants p INNER JOIN Categories c \n" +
                                    "ON p.CateID = c.CateID";
                String processedQuery = "";
                //If user prompts to search by name
                if(searchby.equals("name")){                    
                    processedQuery += baseQuery + 
                            " WHERE p.Pname like '%"+keyword+"%'";
                    
                }else if (searchby.equals("category")){ //If user prompts to search by category
                    processedQuery += baseQuery + 
                            " WHERE c.CateName like '%"+keyword+"%'";
                }else{
                    processedQuery += baseQuery;
                }
                pst = cn.prepareStatement(processedQuery);                                
                rst = pst.executeQuery();
                /*According to Mr. KhanhKT, we don't really need to check if rst 
                is null, as after the executeQuery() command, the result will
                always be an object.*/
                while(rst.next()){
                    int id = rst.getInt("PID");
                    String pname = rst.getString("PName");
                    int price = rst.getInt("price");
                    String imgPath = rst.getString("imgPath");
                    String desc = rst.getString("description");
                    int status = rst.getInt("status");
                    int cateId = rst.getInt("CateID");
                    String cateName = rst.getString("CateName");
                    plant = new Plant(id,pname,price,imgPath,desc,status,cateId,cateName);
                    plants.add(plant);
                }
                
                
            }            
        }catch(Exception e){
            e.printStackTrace();
        
        }
        return plants;
    }
    //Gets the price and the image path of a plant, based on its PlantId in the database.
    public static ArrayList<String> getPlantDetails(int PlantId){
        ArrayList plantDetails = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        Plant plant = null;
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){
                plantDetails = new ArrayList<String>();
                String baseQuery = "SELECT TOP 1 p.price,p.imgPath FROM OrderDetails o JOIN Plants p\n" +
                                    "ON o.FID = p.PID\n" +
                                    "WHERE p.PID = ?";
                pst = cn.prepareStatement(baseQuery);
                pst.setInt(1, PlantId);
                rst = pst.executeQuery();
                //Gets the image path, price of JUST 1 SINGLE PLANT.
                if(rst!=null && rst.next()==true) {
                    String price = Integer.toString(rst.getInt("price"));
                    String imgPath = rst.getString("imgPath");
                    plantDetails.add(price);
                    plantDetails.add(imgPath);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        
        }
        return plantDetails;
    }
    //Gets all the information of the plant (e.g. 
    public static Plant getSinglePlantDetail(int plantID){
        Plant foundPlant = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){                
                String query = "SELECT p.pid, p.PName,p.price,p.imgPath,p.description,p.status,p.CateID,c.CateName \n" +
                                "FROM Plants p JOIN Categories c ON p.CateId = c.CateID\n" +
                                "WHERE p.PID = ?";
                pst = cn.prepareStatement(query);
                pst.setInt(1, plantID);
                rst = pst.executeQuery();
                //Gets the image path, price of JUST 1 SINGLE PLANT.
                if(rst!=null && rst.next()==true) {
                    foundPlant = new Plant(
                            rst.getInt("pid"),
                            rst.getString("PName"),
                            rst.getInt("price"),
                            rst.getString("imgPath"),
                            rst.getString("description"),
                            rst.getInt("status"),
                            rst.getInt("CateID"),
                            rst.getString("CateName")
                    );
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
        
        return foundPlant;
        
    }
    public static boolean addPlant(String name,int price,String imgPath,
                String desc,int status,int category ){
        
        boolean isAdded = false;
        Connection cn = null;
        PreparedStatement pst = null;
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){
                String query = "INSERT INTO Plants(PName,price,imgPath,description,status,CateID)\n" +
                                "VALUES(?,?,?,?,?,?)";
                pst = cn.prepareStatement(query);
                pst.setString(1,name);
                pst.setInt(2,price);
                pst.setString(3,imgPath);
                pst.setString(4,desc);
                pst.setInt(5,status);
                pst.setInt(6,category);
                
                int result = pst.executeUpdate();
                if(result!=0) isAdded = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return isAdded;
    }
    public static boolean updatePlant(int plantId,String name,int price,String imgPath,
                String desc,int status,int category ){
        
        boolean isAdded = false;
        Connection cn = null;
        PreparedStatement pst = null;
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){
                String query = "UPDATE Plants\n" +
                                "SET PName = ?, price = ?, imgPath = ?, description = ?,\n" +
                                "status = ?, cateID = ?\n" +
                                "WHERE PID = ?";
                pst = cn.prepareStatement(query);
                pst.setString(1,name);
                pst.setInt(2,price);
                pst.setString(3,imgPath);
                pst.setString(4,desc);
                pst.setInt(5,status);
                pst.setInt(6,category);
                pst.setInt(7,plantId);
                
                int result = pst.executeUpdate();
                if(result!=0) isAdded = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return isAdded;
    }
}
