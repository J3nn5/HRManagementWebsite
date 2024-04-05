<%-- 
    Document   : updateUser
    Created on : Mar 27, 2024, 1:11:47 PM
    Author     : MAI_PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*" %>
<%@page import = "Database.DBConn" %>
<%@page import = "org.bson.types.ObjectId;" %>
<html>
    <head>
        <title>Update Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../css/bootstrap.css" type="text/css"/>
        <style>
            #frm {
                width: 500px;
                margin: auto;
                margin-top: 100px;
            }
            marquee {
                margin-top: 10px;
            }
        </style>
    </head>
    <body class="container-fluid">
        <%
            DBObject user = (DBObject) session.getAttribute("user");
            if(user != null) {
                String username = (String) user.get("Name");
        %>

    <form action= "updateStaff" method="post" class="form-group" id="frm">
        <%
            String userId = request.getParameter("userId");
            ObjectId _id = new ObjectId(userId);
            BasicDBObject query = new BasicDBObject("_id", _id);

            DB db = DBConn.getConn();
            DBCollection col = db.getCollection("Users");
            DBCursor cursor = col.find(query);
            BasicDBObject staff = (BasicDBObject) cursor.next();
        %>
        <marquee><h2 class="text-primary">This is the Update form</h2></marquee>
        <h2 class="bg-danger text-white card-header"> Update form </h2>

        <input type='hidden' name='userId' value="<%= request.getParameter("userId") %>">

        <table class="table table-hover">
            <tr>
                <td>Name</td>
                <td><input type="text" name="userName" value= "<%= staff.get("Name") %>" required></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="email" name="email" value= "<%= staff.get("Email") %>" required></td>
            </tr>
            <tr>
                <td>Password</td>
                <td><input type="password" name="psswd" required></td>
            </tr>
            <tr>
                <td>Phone Number</td>
                <td><input type="text" name="phoneNumber" value= "<%= staff.get("Phone Number") %>" required></td>
            </tr>
            <tr>
                <td>Department</td>
                <td>
                    <select class="custom-select" name="department">
                        <option value="banquantri" <%= staff.get("Department").equals("banquantri") ? "selected" : "" %>>Ban Quan Tri</option>
                        <option value="banquanly" <%= staff.get("Department").equals("banquanly") ? "selected" : "" %>>Ban Quan Ly</option>
                        <option value="nhansu" <%= staff.get("Department").equals("nhansu") ? "selected" : "" %>>Phong Nhan Su</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>Position</td>
                <td><input type="text" name="position" value= "<%= staff.get("Position") %>" required></td>
            </tr>

            <tr class="card-footer">
                <td><button class="btn btn-outline-success" type="submit">Update</button></td>
                <td><button class="btn btn-outline-danger" type="reset">Cancel</button></td>
            </tr>
        </table>
        <a href="showStaff" class="btn btn-outline-primary d-block">Show Staffs</a>
    </form>
    <a href='adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>
    
        <%
            } else {
                response.sendRedirect("../index.html");
            }
        %>
    </body>
</html>
