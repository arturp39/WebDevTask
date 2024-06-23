<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Task Due Date</title>
</head>
<body>
<h1>Edit Task Due Date</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
  <input type="hidden" name="command" value="update_task_due_date">
  <input type="hidden" name="task_id" value="${param.task_id}">
  Due Date: <input type="date" name="due_date" required/><br/>
  <input type="submit" value="Update Due Date"/>
</form>
<br/>
<form action="${pageContext.request.contextPath}/controller?command=list_tasks" method="get">
  <input type="submit" value="Back to Task List"/>
</form>
</body>
</html>
