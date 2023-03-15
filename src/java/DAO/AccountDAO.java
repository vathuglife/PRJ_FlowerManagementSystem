/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Account;
import Tools.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author VietAnhOdyssey
 */
public class AccountDAO {
    public static boolean InsertAccount(Account newAcc){
        Connection cn = null;
        boolean result = false;
        try{
            /*General procedure to execute a SQL Query:
            - Make connection -> Setup the query string -> Add it to 
            PreparedStatement -> Execute PreparedStatement -> */
            cn = Tools.DBUtils.makeConnection();
            if(cn!=null){
                String addQuery = "INSERT INTO Accounts"
                        + "(email,password,fullname,phone,status,role)" +
                                   "VALUES (?,?,?,?,?,?);";
                PreparedStatement pst = cn.prepareStatement(addQuery);                
                pst.setString(1,newAcc.getEmail());
                pst.setString(2,newAcc.getPassword());
                pst.setString(3,newAcc.getFullname());
                pst.setString(4,newAcc.getPhone());
                pst.setInt(5,newAcc.getStatus());
                pst.setInt(6,newAcc.getRole());
                /*ExecuteUpdate basically modifies the table 
                (adds into the database new data) */               
                int execResult = pst.executeUpdate();
                if (execResult!=1) result = false;
                else result = true;
            }
        }catch(Exception e){
            System.out.println("Something went wrong. Here is the error: "
                    +e.getMessage());
        }
        return result;
    }
    public static ArrayList<Account> getAccounts(){
         Connection cn = null;
        ArrayList<Account> accList = null;
        PreparedStatement pst = null;
        String checkQuery = null;
        try{
            cn = Tools.DBUtils.makeConnection();
            if(cn!=null){
                accList = new ArrayList<Account>();
                checkQuery = "SELECT accID,email,password,fullname,status,role,phone FROM accounts a";

                pst = cn.prepareStatement(checkQuery);                                        

               
                ResultSet rst = pst.executeQuery();
                if(rst!=null && rst.next()){
                  int accID = rst.getInt("accID");
                  String mail = rst.getString("email");
                  String pass = rst.getString("password");
                  String fullname = rst.getString("fullname");
                  String phone = rst.getString("phone");
                  int status = rst.getInt("status");
                  int role = rst.getInt("role");
                  Account eachAcc = new Account(accID,mail,pass,fullname,phone,status,role);                  
                  accList.add(eachAcc);
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return accList;
    }
    public static Account CheckAccount(String email, String password){
        Connection cn = null;
        Account acc = null;
        PreparedStatement pst = null;
        String checkQuery = null;
        try{
            cn = Tools.DBUtils.makeConnection();
            if(cn!=null){
                if(email.equals("")==false && password.equals("")==false){
                    checkQuery = "SELECT accID,email,password,fullname,phone,"
                                    +"status,role FROM Accounts " +
                                    "WHERE email= ? AND password = ? "
                                    + "COLLATE Latin1_General_CS_AS";
                    pst = cn.prepareStatement(checkQuery);
                    pst.setString(1, email);
                    pst.setString(2, password);
                
                }else{
                    checkQuery = "SELECT accID,email,password,fullname,status,role,phone FROM accounts a\n" +
                                    "WHERE a.email = ?";
                    pst = cn.prepareStatement(checkQuery);
                    pst.setString(1, email);                    
                }
               
                ResultSet rst = pst.executeQuery();
                if(rst!=null && rst.next()){
                  int accID = rst.getInt("accID");
                  String mail = rst.getString("email");
                  String pass = rst.getString("password");
                  String fullname = rst.getString("fullname");
                  String phone = rst.getString("phone");
                  int status = rst.getInt("status");
                  int role = rst.getInt("role");
                  acc = new Account(accID,mail,pass,fullname,phone,status,role);                  
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return acc;
    }
    
    //Checks if the given token belongs to an account.
    public static Account CheckAccount(String token){
        Connection cn = null;
        Account acc = null;
        try{
            cn = Tools.DBUtils.makeConnection();
            if(cn!=null){
                String checkQuery = "SELECT accID,email,password,fullname,phone,"
                                    +"status,role FROM Accounts a " +
                                    "WHERE a.token = "+'\''+token+'\'';
                                    
                PreparedStatement pst = cn.prepareStatement(checkQuery);                
                ResultSet rst = pst.executeQuery();
                if(rst!=null && rst.next()){
                  int accID = rst.getInt("accID");
                  String mail = rst.getString("email");
                  String pass = rst.getString("password");
                  String fullname = rst.getString("fullname");
                  String phone = rst.getString("phone");
                  int status = rst.getInt("status");
                  int role = rst.getInt("role");
                  acc = new Account(accID,mail,pass,fullname,phone,status,role);                  
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
            
        }
        return acc;
    }
    public static ArrayList<Account> GetAccounts(){
        ArrayList<Account> accList = null;
        Account eachAcc = null;
        Connection cn = null;
        try{
            cn = Tools.DBUtils.makeConnection();
            if(cn!=null){
                accList = new ArrayList<Account>();
                String query = "SELECT accID,email,password,fullname,"
                        + "phone,status,role FROM Accounts";
                PreparedStatement pst = cn.prepareStatement(query);
                ResultSet rst = pst.executeQuery();
                /*ResultSet is basically a table , with 
                THE FIRST AND LAST ROWS being BUFFER (EMPTY) AREAS!
                That's why we need the rst.next(), in order to skip
                these buffer areas.*/
                while(rst!=null && rst.next()==true){
                  int accID = rst.getInt("accID");
                  String mail = rst.getString("email");
                  String pass = rst.getString("password");
                  String fullname = rst.getString("fullname");
                  String phone = rst.getString("phone");
                  int status = rst.getInt("status");
                  int role = rst.getInt("role");
                  eachAcc = new Account(accID,mail,pass,fullname,phone,status,role);  
                  accList.add(eachAcc);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return accList;
    }
    public static boolean UpdateAccountStatus(String email,int status){
        boolean isModified = false;
        Connection cn = null;
        try{
            cn = Tools.DBUtils.makeConnection();
            if(cn!=null){
                String updateQuery = "  UPDATE Accounts \n" +
                                    "  SET status = ?\n" +
                                    "  WHERE email=?";
                PreparedStatement pst = cn.prepareStatement(updateQuery);
                pst.setInt(1,status);
                pst.setString(2,email);
                int result = pst.executeUpdate();
                if (result!=1) isModified = false;
                else isModified = true;            
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return isModified;
    }
    public static boolean UpdateAccount(String email,
            String newPassword, String newFullname,String newPhone){
        boolean isModified = false;
        Connection cn = null;
        String updateQuery = null;
        PreparedStatement pst = null;
        int result = 0;
        try{
            cn = Tools.DBUtils.makeConnection();
            if(cn!=null){
                if(email.isEmpty() || newPassword.isEmpty()){
                    updateQuery = "  UPDATE Accounts  SET fullname="+"\'"+newFullname+"\'"
                            + ",phone="+"\'"+newPhone+"\'"
                            + " WHERE email="+"\'"+email+"\'";
                    pst = cn.prepareStatement(updateQuery);                                 
                    result = pst.executeUpdate();
                } else{                                
                    updateQuery = "UPDATE Accounts \n" +
                                        "  SET \n" +
                                        "  password=?,\n" +
                                        "  fullname=?,phone=?\n" +
                                        "  WHERE email=?";
                    pst = cn.prepareStatement(updateQuery);                
                    pst.setString(1,newPassword);
                    pst.setString(2,newFullname);
                    pst.setString(3,newPhone);
                    pst.setString(4,email);
                    result = pst.executeUpdate();
                }                
                if (result!=1) isModified = false;
                else isModified = true;            
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return isModified;
    }
    /*Adds/Removes the token (a way to identify the user) in the Token Column 
    of the Account Table - PlantShop Database*/
    public static void UpdateToken(String token,String email){
        Connection cn = null;        
        try{
            cn = DBUtils.makeConnection();
            if(cn!=null){
                String updateQuery = "UPDATE Accounts\n" +
                                    "SET token = ? WHERE Accounts.email = ?";
                PreparedStatement pst = cn.prepareStatement(updateQuery);
                pst.setString(1,token);
                pst.setString(2,email);                                
                pst.executeUpdate();                
            }
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public static String GenerateToken(){               
        String token = "";
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // create random string builder
        StringBuilder sb = new StringBuilder();
             Random random = new Random();

        // specify length of random string
        int length = 7;

        for(int i = 0; i < length; i++) {

          // generate random index number
          int index = random.nextInt(alphabet.length());

          // get character specified by index
          // from the string
          char randomChar = alphabet.charAt(index);

          // append the character to string builder
          sb.append(randomChar);
        }

        token = sb.toString();  
            return token;
    }    
    
}
