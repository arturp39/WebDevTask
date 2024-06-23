<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Account</title>
</head>
<body>

<h1>Update Account</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="update_user">
    New Login: <input type="text" name="new_login" required/>
    <br/>
    New Password: <input type="password" name="new_pass" required/>
    <br/>
    <input type="submit" value="Update Account"/>
    <br/>
    ${update_msg}
</form>

</body>
</html>
