package Projects;

import Database.DBConn;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.WebServlet;
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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author MAI_PHUONG
 */
//@WebServlet("/addtask")
@MultipartConfig
public class AddProjectServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {            
            String title = req.getParameter("title");
            String note = req.getParameter("note");
            String date = req.getParameter("date");
            String manager = req.getParameter("manager");
            
            try {
                DB dbl = DBConn.getConn();
                DBCollection col = dbl.getCollection("Projects");
                
                BasicDBObject data = new BasicDBObject();
                
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = dateFormat.parse(date);
                
                data.put("Title", title);
                data.put("Note", note);
                data.put("Date", date1);
                data.put("Manager", manager);
                data.put("Status", "Not");
                
                // Files
                Collection<Part> parts = req.getParts();
                List<String> fileNames = new ArrayList<>();
                List<byte[]> fileContents = new ArrayList<>();
                for(Part part : parts) {
                   if(part.getName().equals("files")) {
                       String fileName = part.getSubmittedFileName();
                       InputStream fileContent = part.getInputStream();
                       
//                       jdk17 v10
//                       byte[] bytes = fileContent.readAllBytes();
//                       JDK1.8 V9
                       byte[] bytes = Files.readAllBytes(Paths.get(fileName));
                       
                       fileNames.add(fileName);
                       fileContents.add(bytes);
                   }
                }
                data.put("FileNames", fileNames);
                data.put("FileContents", fileContents);
                       
                col.insert(data);
                out.println("Successful");
                DBConn.closeConn();
            }
            catch(Exception e) {
                out.println("Failed" + e);
            }
            
            out.println("<a href='adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>");
            out.println("</div>");
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
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
