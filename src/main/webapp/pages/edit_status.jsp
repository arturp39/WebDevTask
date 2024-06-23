<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Task Status</title>
</head>
<body>
<h1>Edit Task Status</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
  <input type="hidden" name="command" value="update_task_status">
  <input type="hidden" name="task_id" value="${param.task_id}">
  Status:
  <select name="status" required>
    <option value="NOT_STARTED">Not Started</option>
    <option value="IN_PROGRESS">In Progress</option>
    <option value="COMPLETED">Completed</option>
  </select><br/>
  <input type="submit" value="Update Status"/>
</form>
<br/>
<form action="${pageContext.request.contextPath}/controller?command=list_tasks" method="get">
  <input type="submit" value="Back to Task List"/>
</form>
</body>
</html>
