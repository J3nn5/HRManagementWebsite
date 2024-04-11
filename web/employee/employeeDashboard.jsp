<%-- 
    Document   : admin
    Created on : Apr 6, 2024, 10:12:22 AM
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
        <title>Employee Page</title>
    </head>
    
    <body>
        <%
            DBObject employee = (DBObject) session.getAttribute("employee");
            if(employee != null) {
                String username = (String) employee.get("Name");
        %>
        
        <header>
            <h1>Welcome to My Website</h1>
            <nav>
                <ul>
                    <li><a href="#user">User</a></li>
                    <li><a href="#meeting">Meeting Schedule</a></li>
                    <li><a href="#project">Project</a></li>
                </ul>
            </nav>
        </header>

        <section id="user">
            <h2><%= employee.get("Name") %></h2>
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
        <section id="metting">
            <h2>Meeting Schedule</h2>
            <p>This is where you can write something about yourself.</p>
        </section>

        <section id="project">
            <h2>Project</h2>
            <div class="gallery">
                <form action= "search" method="post" class="form-group">
                    <input type='hidden' name='name' value="<%= employee.get("Name") %>">
                    <button type="submit">Search Project</button>    
                </form>
                
            </div>
        </section>
        
        
        <%
            } 
            else {
                DBObject admin = (DBObject) session.getAttribute("admin");
                if(admin != null) {
                    response.sendRedirect("../admin/adminDashboard.jsp");
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
            window.location.href = 'employeeDashboard.jsp'; // Chuyển hướng nếu người dùng hủy
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