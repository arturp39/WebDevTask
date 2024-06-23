<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task List</title>
</head>
<body>
<h1>Task List</h1>
<c:forEach var="task" items="${tasks}">
    <div>
        <h2>${task.title}</h2>
        <p>${task.description}</p>
        <p>Due Date: <fmt:formatDate value="${task.dueDate}" pattern="dd-MM-yyyy"/></p>
        <p>Status: ${task.userFriendlyStatus}</p>
        <form action="${pageContext.request.contextPath}/controller" method="post" style="display:inline;">
            <input type="hidden" name="command" value="delete_task">
            <input type="hidden" name="task_id" value="${task.id}">
            <input type="submit" value="Delete"/>
        </form>
        <form action="${pageContext.request.contextPath}/pages/edit_title.jsp" method="get" style="display:inline;">
            <input type="hidden" name="task_id" value="${task.id}">
            <input type="submit" value="Edit Title"/>
        </form>
        <form action="${pageContext.request.contextPath}/pages/edit_description.jsp" method="get"
              style="display:inline;">
            <input type="hidden" name="task_id" value="${task.id}">
            <input type="submit" value="Edit Description"/>
        </form>
        <form action="${pageContext.request.contextPath}/pages/edit_due_date.jsp" method="get" style="display:inline;">
            <input type="hidden" name="task_id" value="${task.id}">
            <input type="submit" value="Edit Due Date"/>
        </form>
        <form action="${pageContext.request.contextPath}/pages/edit_status.jsp" method="get" style="display:inline;">
            <input type="hidden" name="task_id" value="${task.id}">
            <input type="submit" value="Edit Status"/>
        </form>
    </div>
</c:forEach>
<br/>
<form action="${pageContext.request.contextPath}/pages/main.jsp" method="get">
    <input type="submit" value="Back to Main Page"/>
</form>
</body>
</html>
