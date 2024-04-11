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
        <link rel="stylesheet" href="../css/bootstrap.css" type="text/css"/>
        
        <% String colorMode = (String) session.getAttribute("colorMode");
            if("dark".equals(colorMode)) { %>
                <link rel="stylesheet" href="../css/dark.css" type="text/css"/>
        <% } else { %>
                <link rel="stylesheet" href="../css/light.css" type="text/css"/>
        <% } %>
        <title>Admin Page</title>
    </head>
    <body>
        <%
            DBObject admin = (DBObject) session.getAttribute("admin");
            if(admin != null) {
                String username = (String) admin.get("Name");
        %>
        
        <header>
            <h1>Welcome to My Website</h1>
            <nav>
                <ul>
                    <li><a href="#user">User</a></li>
                    <li><a href="#meeting">Meeting Schedule</a></li>
                    <li><a href="#project">Project</a></li>
                    <li><a href="#staff">Staff</a></li>
                    <li><a href="#admin">Admin</a></li>
                </ul>
            </nav>
        </header>
        
        <section id="user">
            <h2><%= admin.get("Name") %></h2>
            <!--Setting-->
            <form action="../theme" method="get" id="colorModeForm"coloMode>
                <label for="light">Light</label>
                <input type="radio" id="light" value="light" name="colorMode">
                <label for="dark">Dark</label>
                <input type="radio" id="dark" value="dark" name="colorMode">
            </form>
            <!--Logout--> 
            <div>
                <button onclick="confirmLogout()">Logout</button>
            </div>
             <!--Change Password--> 
            <div>
                <button><a href="../changePass.jsp" class = "btn btn-outline-primary d-block">Change Password</a></button>
            </div>
        </section>
        
        
        <!-- Staff -->
        <section id="staff">
            <h2>Staff</h2>
            <div>
                <button><a href="showStaff" class = "btn btn-outline-primary d-block">Show Staffs</a></button>
            </div>
            <div>
                <button><a href="register.jsp" class = "btn btn-outline-primary d-block">Add Staff</a></button>
            </div>
        </section>
        
        <!-- Admin -->
        <section id="admin">
            <h2>Admin</h2>
            <div>
                <button><a href="showAdmin" class = "btn btn-outline-primary d-block">Show Admins</a></button>
            </div>
        </section>

        <!-- Project -->
        <section id="project">
            <h2>Project</h2>
            <div>
                <button><a href="addProject.jsp" class = "btn btn-outline-primary d-block">Add Project</a></button>
            </div>
            <div>
                <button><a href="showProject" class = "btn btn-outline-primary d-block">Show Project</a></button>
            </div>
        </section>
        
       <!--Meeting-->
       <section id="meeting">
            <h2>Meeting</h2>
            <div>
                <button><a href="addMeeting.jsp" class = "btn btn-outline-primary d-block">Add Meeting</a></button>
            </div>
            <div>
                <button><a href="../showMeeting" class = "btn btn-outline-primary d-block">Meeting Schedule</a></button>
            </div>
        </section>
        
        <%
            } 
            else {
                DBObject employee = (DBObject) session.getAttribute("employee");
                if(employee != null) {
                    response.sendRedirect("../employee/employeeDashboard.jsp");
                }
                else {
                    response.sendRedirect("../index.html");
                }
            }       
        %>
    </body>
</html>

<script>
    function confirmLogout() {
        var result = confirm('Logout your account?');
        if (result) {
        window.location.href = '../logout'; // Chuyển hướng nếu người dùng đồng ý
        } else {
            window.location.href = 'adminDashboard.jsp'; // Chuyển hướng nếu người dùng hủy
        }
    }
</script>

<script>
    // Lắng nghe sự kiện onchange trên các nút radio
    var radioButtons = document.querySelectorAll('input[name="colorMode"]');
    radioButtons.forEach(function(radioButton) {
        radioButton.addEventListener('change', function() {
            // Tự động gửi biểu mẫu khi có sự thay đổi
            document.getElementById('colorModeForm').submit();
        });
    });
</script>