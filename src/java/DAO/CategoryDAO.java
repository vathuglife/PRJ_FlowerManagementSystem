/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Category;
import Tools.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author VietAnhOdyssey
 */
public class CategoryDAO {
    public static ArrayList<Category> getCategories(){
        ArrayList<Category> cateList = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){
                cateList = new ArrayList<Category>();
                String query = "SELECT CateID,CateName from Categories";
                pst = cn.prepareStatement(query);
                rst = pst.executeQuery();
                
                while(rst!=null && rst.next()){
                    Category eachCate = new Category(rst.getInt("CateID"),
                            rst.getString("CateName"));
                    cateList.add(eachCate);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        
        }
        return cateList;
    }
    public static boolean addCategory(String newCate){
        boolean isAdded = false;
        Connection cn = null;
        PreparedStatement pst = null;
        
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){
                
                String query = "INSERT INTO Categories (CateName) VALUES (?)";
                pst = cn.prepareStatement(query);
                pst.setString(1,newCate);
                int result = pst.executeUpdate();
                if(result!=0) isAdded = true;                
            }
        }catch(Exception e){
            e.printStackTrace();
        
        }
        return isAdded;
    }
    public static boolean updateCategory(int CateId,String newCatename){
        boolean isDeleted = false;
        Connection cn = null;
        PreparedStatement pst = null;
        
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){
                
                String query = "UPDATE Categories SET CateName = ? WHERE CateID = ?";
                pst = cn.prepareStatement(query);
                pst.setString(1,newCatename);
                pst.setInt(2,CateId);
                int result = pst.executeUpdate();
                if(result!=0) isDeleted = true;                
            }
        }catch(Exception e){
            e.printStackTrace();
        
        }
        return isDeleted;
    }
}
