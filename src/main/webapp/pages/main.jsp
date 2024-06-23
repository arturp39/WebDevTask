<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
Hello, ${user}
<hr/>
Hi, ${user_name}
<hr/>
<form action="controller" method="get">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="Logout"/>
</form>
<hr/>
<a href="pages/delete_user.jsp">Delete Account</a>
</body>
</html>
