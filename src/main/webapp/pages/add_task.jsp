<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Add Task</title>
</head>
<body>
<h1>Add Task</h1>
<form action="${pageContext.request.contextPath}/controller" method="post">
  <input type="hidden" name="command" value="add_task">
  Title: <input type="text" name="title" required/><br/>
  Description: <textarea name="description"></textarea><br/>
  Due Date: <input type="date" name="due_date"/><br/>
  Status:
  <select name="status" required>
    <option value="NOT_STARTED">Not Started</option>
    <option value="IN_PROGRESS">In Progress</option>
    <option value="COMPLETED">Completed</option>
  </select><br/>
  <input type="submit" value="Add Task"/>
</form>
<br/>
<form action="${pageContext.request.contextPath}/pages/main.jsp" method="get">
  <input type="submit" value="Back to Main Page"/>
</form>
</body>
</html>
