<%-- 
    Document   : admin
    Created on : Mar 26, 2024, 10:12:22 AM
    Author     : MAI_PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body
        <%
            DBObject user = (DBObject) session.getAttribute("user");
            if(user != null) {
                String username = (String) user.get("Name");
        %>
        
        <div>
            <h2><%= user.get("Name") %></h2>
        </div>
        
        <!-- Add User -->
        <div>
            <a href="register.jsp" class = "btn btn-outline-primary d-block">Add User</a>
        </div>
        
        <!-- User -->
        <div>
            <a href="showStaff" class = "btn btn-outline-primary d-block">Show Staffs</a>
        </div>
        
        <!-- Add Project -->
        <div>
            <a href="addProject.jsp" class = "btn btn-outline-primary d-block">Add Project</a>
        </div>
        
        <!-- Logout -->
        <div>
            <button onclick="confirmLogout()">Logout</button>
        </div>
        
        <%
            } else {
                response.sendRedirect("../index.html");
            }
        %>
    </body>
</html>

<script>
    function confirmLogout() {
        var result = confirm('Logout your account?');
        if (result) {
        window.location.href = 'logout'; // Chuyển hướng nếu người dùng đồng ý
        } else {
            window.location.href = 'adminDashboard.jsp'; // Chuyển hướng nếu người dùng hủy
        }
    }
</script>