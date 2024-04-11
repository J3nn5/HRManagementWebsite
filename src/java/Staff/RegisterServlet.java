package Staff;

import Database.DBConn;
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

//@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");
        pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
        String name = req.getParameter("userName");
        String email = req.getParameter("email");
        String password = req.getParameter("psswd"); 
        String department = req.getParameter("department"); 
        String position = req.getParameter("position");
        String dob = req.getParameter("dob");
        String mobile = req.getParameter("phoneNumber");
        String city = req.getParameter("city");
        String gender = req.getParameter("gender");
        
        try {
//            MongoClient mongo = new MongoClient("localhost", 27017);
//            DB db1 = mongo.getDB("my_database");
            DB db1 = DBConn.getConn();
            DBCollection admin = db1.getCollection("Admins");
            DBCollection emp = db1.getCollection("Employees");
            
            // Check user
            BasicDBObject mail1 = new BasicDBObject("Email", email);
            DBCursor admin1 = admin.find(mail1);
            BasicDBObject phone1 = new BasicDBObject("Phone Number", mobile);
            DBCursor admin2 = admin.find(phone1);
            
            BasicDBObject mail2 = new BasicDBObject("Email", email);
            DBCursor emp1 = emp.find(mail2);
            BasicDBObject phone2 = new BasicDBObject("Phone Number", mobile);
            DBCursor emp2 = emp.find(phone2);
            
            if (admin1.hasNext() ||  emp1.hasNext()) {
                pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px'>");
                pw.println("<h2 class='bg-danger text-light text-center'>Email already exists!</h2>");
                pw.println("</div>");
                pw.println("<a href='adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>");
                pw.close();
                return;   
            }
            if (admin2.hasNext() || emp2.hasNext()) {
                pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px'>");
                pw.println("<h2 class='bg-danger text-light text-center'>Phone already exists!</h2>");
                pw.println("</div>");
                pw.println("<a href='adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>");
                pw.close();
                return; 
            }
            
            // Hash the password
            String hashedPassword = hashPassword(password);
            
            BasicDBObject document = new BasicDBObject();
            document.put("Name", name);
            document.put("Email", email);
            document.put("Password", hashedPassword); 
            document.put("Department", department); 
            document.put("Position", position);
            document.put("DOB", dob);
            document.put("Phone Number", mobile);
            document.put("Gender", gender);
            document.put("City", city);
            
            
            if(position.equals("employee")) {
                emp.insert(document);
            }
            else{
                admin.insert(document);
            }
            
            pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px'>");
            pw.println("<h2 class='bg-success text-light text-center'>Registration Successful!</h2>");
            // mongo.close();
            DBConn.closeConn();
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Registration Failed!</h2>");
        }
        pw.println("<a href='adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>");
        pw.println("</div>");
        pw.close();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
    
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
