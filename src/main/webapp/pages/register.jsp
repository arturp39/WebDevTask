<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
<h1>Register</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="register">
    Login: <input type="text" name="login" required/><br/>
    Password: <input type="password" name="pass" required/><br/>
    <input type="submit" value="Register"/>
    <br/>
    ${register_msg}
</form>
<br/>
<form action="${pageContext.request.contextPath}/index.jsp" method="get">
    <input type="submit" value="Back to Login Page"/>
</form>
</body>
</html>