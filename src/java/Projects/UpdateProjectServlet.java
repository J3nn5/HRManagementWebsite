/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Projects;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.mongodb.*;
import Database.DBConn;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bson.types.ObjectId;

/**
 *
 * @author MAI_PHUONG
 */
public class UpdateProjectServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            try {
                String projectId = req.getParameter("projectId");
                ObjectId _id = new ObjectId(projectId);
                BasicDBObject query = new BasicDBObject("_id", _id);
                
                DB db = DBConn.getConn();
                
                String title = req.getParameter("title");
                String manager = req.getParameter("manager");
                String date = req.getParameter("date");
                String note = req.getParameter("note");
                String status = req.getParameter("status");
                
                //Document
                Collection<Part> parts = req.getParts();
                List<String> fileNames = new ArrayList<>();
                List<byte[]> fileContents = new ArrayList<>();
                for(Part part : parts) {
                   if(part.getName().equals("files")) {
                       String fileName = part.getSubmittedFileName();
                       InputStream fileContent = part.getInputStream();
                       
                       byte[] bytes = fileContent.readAllBytes();
                       
                       fileNames.add(fileName);
                       fileContents.add(bytes);
                   }
                }
                
                DBCollection col = db.getCollection("Projects");
                DBCursor cursor = col.find(query);
                BasicDBObject update = (BasicDBObject) cursor.next();
                
                update.put("Title", title);
                update.put("Manager", manager);
                update.put("Date", date);
                update.put("Note", note);
                update.put("Status", status);
                update.put("Title", fileNames);
                update.put("Title", fileContents);
                
                BasicDBObject updateQuery = new BasicDBObject("$set", update);
                col.update(query, updateQuery);
                
                DBConn.closeConn();
            }
            catch(Exception e){
                out.println("<script>");
                out.println("   alert(\'Failed.\'); ");
                out.println("   window.location.href = './showStaff';");
                out.println("</script>");
                out.close();
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
