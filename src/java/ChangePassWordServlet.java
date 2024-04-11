/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import Database.DBConn;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bson.types.ObjectId;

/**
 *
 * @author PHUONG
 */
public class ChangePassWordServlet extends HttpServlet {

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
            String userId = request.getParameter("userId");
            ObjectId _id = new ObjectId(userId);
            String old_pass = request.getParameter("old_psswd");
            String new_pass = request.getParameter("new_psswd");
            String new_pass1 = request.getParameter("new_psswd1");

            String hash_old_pass = hashPassword(old_pass);
            if (!new_pass.equalsIgnoreCase(new_pass1)) {
                out.print("<script>");
                out.print("     alert(\'Confirm password incorrect!\');");
                out.println("   window.location.href = 'changePass.jsp';");
                out.print("</script>");
            }

            DB db = DBConn.getConn();
            DBCollection admin = db.getCollection("Admins");
            BasicDBObject query1 = new BasicDBObject();
            query1.put("_id", _id);
            DBCursor cursor1 = admin.find(query1);
            if (cursor1.hasNext()) {
                DBObject ad = cursor1.next();
                String storedPass = (String) ad.get("Password");
                if (storedPass.equalsIgnoreCase(hash_old_pass)) {
                    BasicDBObject update = new BasicDBObject();
                    update.put("Password", hashPassword(new_pass));
                    BasicDBObject pass = new BasicDBObject("$set", update);
                    admin.update(query1, pass);
                    DBConn.closeConn();

                    out.print("<script>");
                    out.print("     alert(\'Success! Please login again.\');");
                    out.println("   window.location.href = 'logout';");
                    out.print("</script>");
                } else {
                    out.print("<script>");
                    out.print("     alert(\'Password incorrect!\');");
                    out.println("   window.location.href = 'changePass.jsp';");
                    out.print("</script>");
                }
            } else {
                DBCollection employee = db.getCollection("Employees");
                BasicDBObject query2 = new BasicDBObject();
                query2.put("_id", _id);
                DBCursor cursor2 = employee.find(query2);
                if (cursor2.hasNext()) {
                    DBObject emp = cursor2.next();
                    String storedPass = (String) emp.get("Password");
                    if (storedPass.equalsIgnoreCase(hash_old_pass)) {
                        BasicDBObject update = new BasicDBObject();
                        update.put("Password", hashPassword(new_pass));
                        BasicDBObject pass = new BasicDBObject("$set", update);
                        employee.update(query2, pass);
                        DBConn.closeConn();

                        out.print("<script>");
                        out.print("     alert(\'Success! Please login again.\');");
                        out.println("   window.location.href = 'logout';");
                        out.print("</script>");
                    } else {
                        out.print("<script>");
                        out.print("     alert(\'Password incorrect!\');");
                        out.println("   window.location.href = 'changePass.jsp';");
                        out.print("</script>");
                    }
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
