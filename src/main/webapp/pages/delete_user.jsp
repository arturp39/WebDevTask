<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Account</title>
</head>
<body>

<h1>Delete Account</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="delete_user">
    Login: <input type="text" name="login" required/>
    <br/>
    Password: <input type="password" name="pass" required/>
    <br/>
    <input type="submit" value="Delete Account"/>
    <br/>
    ${delete_msg}
</form>

</body>
</html>
