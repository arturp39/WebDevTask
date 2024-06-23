<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Task Title</title>
</head>
<body>
<h1>Edit Task Title</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="update_task_title">
    <input type="hidden" name="task_id" value="${param.task_id}">
    Title: <input type="text" name="title" required/><br/>
    <input type="submit" value="Update Title"/>
</form>
<br/>
<form action="${pageContext.request.contextPath}/controller?command=list_tasks" method="get">
    <input type="submit" value="Back to Task List"/>
</form>
</body>
</html>
