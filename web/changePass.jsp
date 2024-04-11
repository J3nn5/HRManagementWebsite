<%-- 
    Document   : changePass
    Created on : 11 Apr 2024, 16:24:42
    Author     : PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.css" type="text/css"/>
        <title>Change Password Page</title>
    </head>
    <body class="container-fluid">
        <%
            DBObject admin = (DBObject) session.getAttribute("admin");
            DBObject employee = (DBObject) session.getAttribute("employee");
            if(admin == null && employee == null) {
                return;
            }
            String _id = null;
                if (admin != null) {
                    _id = admin.get("_id").toString();
                } else if (employee != null) {
                    _id = employee.get("_id").toString();
                }
        %>

        <form action="changePass" method="post" class = "form-group" id="frm">
            <marquee><h2 class="text-primary">This is da Register form</h2></marquee> 
            <h2 class="bg-danger text-white card-header"> Registration form </h2>
            <table class="table table-hover">
                <input type="hidden" name='userId' value='<%=_id%>'>
                <tr>
                    <td>Old Password</td>
                    <td><input type="password" name="old_psswd" required></td>
                </tr>
                <tr>
                    <td>New Password</td>
                    <td><input type="password" name="new_psswd" required></td>
                </tr>
                <tr>
                    <td>Confirm Password</td>
                    <td><input type="password" name="new_psswd1" required></td>
                </tr>
                <tr class="card-footer">
                    <td><button class="btn btn-outline-success" type="submit">Change</button></td>
                    <td><button class="btn btn-outline-danger" type="reset">Reset</button></td>
                </tr>
            </table>
        </form>
        <%
            if(employee == null) {
        %>
        <a href='admin/adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>
        <%
            } else {
        %>
        <a href='employee/employeeDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>
        <%
            }
        %>
    </body>
</html>
