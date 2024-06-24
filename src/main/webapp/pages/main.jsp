<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
Hello, ${user_name}
<hr/>
<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="Logout"/>
</form>
<hr/>
<a href="${pageContext.request.contextPath}/pages/delete_user.jsp">Delete Account</a>
<hr/>
<h2>Task Management</h2>
<form action="${pageContext.request.contextPath}/pages/add_task.jsp" method="get">
    <input type="submit" value="Add Task"/>
</form>
<br/>
<form action="${pageContext.request.contextPath}/controller" method="get">
    <input type="hidden" name="command" value="list_tasks"/>
    <input type="submit" value="View Tasks"/>
</form>
</body>
</html>
