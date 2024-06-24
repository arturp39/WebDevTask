<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

<h1>Login</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="login">
    Login: <input type="text" name="login" required/><br/>
    Password: <input type="password" name="pass" required/><br/>
    <input type="submit" value="Submit"/><br/>
    ${login_msg}
</form>

<h1>Register</h1>
<a href="${pageContext.request.contextPath}/pages/register.jsp">
    <button type="button">Register</button>
</a>

</body>
</html>
