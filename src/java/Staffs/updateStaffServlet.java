package Users;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

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
import org.bson.types.ObjectId;


/**
 *
 * @author MAI_PHUONG
 */
// @WebServlet("/UpdateUserServlet")
public class updateStaffServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            try {
                String userId = req.getParameter("userId");
                ObjectId _id = new ObjectId(userId);
                BasicDBObject query = new BasicDBObject("_id", _id);

                MongoClient mongo = new MongoClient("localhost", 27017);
                DB db = mongo.getDB("my_database");
                DBCollection col = db.getCollection("Users");
                DBCursor cursor = col.find(query);
                BasicDBObject update = (BasicDBObject) cursor.next();
                
                String name = req.getParameter("userName");
                String email = req.getParameter("email");
                String password = req.getParameter("psswd");
                String phoneNumber =req.getParameter("phoneNumber");
                String department = req.getParameter("department");
                String position = req.getParameter("position");
                
                // hash pass
                String hashedPassword = hashPassword(password);
                
                update.put("Name", name);
                update.put("Email", email);
                update.put("Password", hashedPassword);
                update.put("Phone Number", phoneNumber);
                update.put("Department", department);
                update.put("Position", position);
                
                // Perform update query
                BasicDBObject updateQuery = new BasicDBObject("$set", update);
                col.update(query, updateQuery);
                mongo.close();
                out.println("<h2>Updated</h2>");
            }
            catch(Exception e) {
                e.printStackTrace();
                out.println("<h2>Failed !!</h2>");
            }
            out.close();
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

    // Ham hash
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
