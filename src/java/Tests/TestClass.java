package Tests;

import DAO.OrderDAO;
import java.util.ArrayList;
import DTO.Order;
import DTO.OrderDetails;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author VietAnhOdyssey
 */
public class TestClass {
    public static void main(String[] args) {
        ArrayList<OrderDetails> orderDetailsList = OrderDAO.getOrderDetails(4);
        for(OrderDetails eachOrder:orderDetailsList){        
            System.out.println(eachOrder.returnAll());
        }
    }
}
