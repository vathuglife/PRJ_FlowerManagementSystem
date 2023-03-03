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
}
