/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets.Admin;

import DAO.PlantDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author VietAnhOdyssey
 */
public class AddPlantServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {     
            //case 1: Add plant servlet is called from the Add a new plant HYPERLINK.
            //in the main menu. In this case, NO ACTUAL PLANT IS ADDED, SO
            //ALL IT NEEDS TO DO IS TO JUST NAVIGATE TO the add Plant form in managePlants.jsp page.
            if(request.getParameter("isFromAddForm")==null){
                request.getRequestDispatcher("managePlants.jsp").forward(request, response);
            
            }else{
            
            //case 2: This servlet is called from the Add a new Plant FORM.
                //In this case, AN ACTUAL PLANT IS ADDED, SO 
                //IT NEEDS TO ADD THAT PLANT TO DB, AND redirects again to the ManagePlants home page.
                String newName = request.getParameter("newName");
                int newPrice = Integer.parseInt(request.getParameter("newPrice"));
                String newImg = request.getParameter("newImg");
                String newDesc = request.getParameter("newDesc");
                int newStatus = Integer.parseInt(request.getParameter("newStatus"));
                int newCategory = Integer.parseInt(request.getParameter("newCategory"));

                boolean isAdded = PlantDAO.addPlant(newName, newPrice, newImg, newDesc, newStatus,newCategory);
                if(isAdded){
                    request.getRequestDispatcher("managePlants.jsp").forward(request,response);
                }
            }
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
