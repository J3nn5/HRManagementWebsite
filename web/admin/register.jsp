<%-- 
    Document   : register
    Created on : Apr 3, 2024, 2:53:03 PM
    Author     : MAI_PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "com.mongodb.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register Page</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="../css/bootstrap.css" type="text/css"/>
        <style>
            #frm
            {
                width: 500px;
                margin: auto;
                margin-top: 100px;
            }
            marquee
            {
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
         
        <form action="register" method="post" class = "form-group" id="frm">
            <marquee><h2 class="text-primary">This is da Register form</h2></marquee> 
            <h2 class="bg-danger text-white card-header"> Registration form </h2>
            <table class="table table-hover">
                <tr>
                    <td>Name</td>
                    <td><input type="text" name="userName" required></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td><input type="email" name="email" required></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="psswd" required></td>
                </tr>
                <tr>
                    <td>Department</td>
                    <td>
                        <select class = "custom-select" name="department">
                            <option value="banquantri">Ban Quan Tri</option>
                            <option value="banquanly">Ban Quan Ly</option>
                            <option value="nhansu">Phong Nhan Su</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Position</td>
                    <td><input type="text" name="position" required></td>
                </tr>
                <tr>
                    <td>Phone Number</td>
                    <td><input type="number" name="phoneNumber" required></td>
                </tr>
                <tr>
                    <td>DOB</td>
                    <td><input type="date" name="dob" required></td>
                </tr>
                <tr>
                    <td>Gender</td>
                    <td>
                        <input type="radio" name="gender"  value="male" required> Male &nbsp;
                        <input type="radio" name="gender"  value ="female" required> Female
                    </td>
                    
                </tr>
                <tr>
                    <td>City</td>
                    <td>
                        <select class = "custom-select" name="city">
                            <option value="cantho">Can Tho</option>
                            <option value="hanoi">Ha Noi</option>
                            <option value="saigon">Sai Gon</option>
                        </select>
                    </td>
                </tr>
                <tr class="card-footer">
                     <td><button class="btn btn-outline-success" type="submit">Register</button></td>
                     <td><a href="../login.html" class="btn btn-outline-danger">Login</a></td>
                </tr>
            </table>
            <a href="showStaff" class = "btn btn-outline-primary d-block">Show Staffs</a>
        </form>
            <a href='./adminDashboard.jsp'><button class='btn btn-outline-primary'>Home</button></a>
            
        <%
            } else {
                response.sendRedirect("../index.html");
            }
        %>
    </body>
</html>

