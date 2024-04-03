package Users;

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


@WebServlet("/register")
public class registerServlet extends HttpServlet {

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
            DBCollection col = db1.getCollection("Users");
            
            // Check user
            BasicDBObject mail = new BasicDBObject("Email", email);
            DBCursor cursor1 = col.find(mail);
            BasicDBObject phone = new BasicDBObject("Phone Number", mobile);
            DBCursor cursor2 = col.find(phone);
            
            if (cursor1.hasNext()) {
                pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px'>");
                pw.println("<h2 class='bg-danger text-light text-center'>Email already exists!</h2>");
                pw.println("</div>");
                pw.println("<a href='admin.jsp'><button class='btn btn-outline-primary'>Home</button></a>");
                pw.close();
                return;   
            }
            if (cursor2.hasNext()) {
                pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px'>");
                pw.println("<h2 class='bg-danger text-light text-center'>Phone already exists!</h2>");
                pw.println("</div>");
                pw.println("<a href='admin.jsp'><button class='btn btn-outline-primary'>Home</button></a>");
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
            
            
            col.insert(document);
            
            pw.println("<div class='card' style='margin:auto; width:300px; margin-top:100px'>");
            pw.println("<h2 class='bg-success text-light text-center'>Registration Successful!</h2>");
            // mongo.close();
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Registration Failed!</h2>");
        }
        pw.println("<a href='login.html'><button class='btn btn-outline-primary'>Home</button></a>");
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
