<%-- 
    Document   : admin
    Created on : Mar 26, 2024, 10:12:22 AM
    Author     : MAI_PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*;" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
    </head>
    <body
        <div>
            <%
                DBObject user = (DBObject) session.getAttribute("user");
                String username = (String) user.get("Name");
            %>
            <h2><%= username%></h2>
        </div>
        
        <!-- Add User -->
        <div>
            <a href="register.html" class = "btn btn-outline-primary d-block">Add User</a>
        </div>
        
        <!-- User -->
        <div>
            <a href="showuser" class = "btn btn-outline-primary d-block">Show Staffs</a>
        </div>
        
        <!-- Add Project -->
        <div>
            <a href="addproject.jsp" class = "btn btn-outline-primary d-block">Add Project</a>
        </div>
        
    </body>
</html>