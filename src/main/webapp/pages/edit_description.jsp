<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Task Description</title>
</head>
<body>
<h1>Edit Task Description</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
  <input type="hidden" name="command" value="update_task_description">
  <input type="hidden" name="task_id" value="${param.task_id}">
  Description: <textarea name="description" required></textarea><br/>
  <input type="submit" value="Update Description"/>
</form>
<br/>
<form action="${pageContext.request.contextPath}/controller?command=list_tasks" method="get">
  <input type="submit" value="Back to Task List"/>
</form>
</body>
</html>
