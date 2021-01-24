<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Web Student Tracker</title>

<link type="text/css" rel="stylesheet" href="css/style.css" >
</head>

<body> 

<div id="wrapper">
<div id="header">
<h2>EduTech University</h2>
</div>
</div>
<div id="container">
<div id="content">
<input type="button" value="Add Student"
onclick="window.location.href='add-student-form.jsp'"
class="add-student-button"/>
<form action="StudentControllerServlet" method="GET">
<input type="hidden" name="command" value="SEARCH" />
Search Name:<input type="text" name="theSearchName"/>
<input type="submit" value="search" class="add-student-button"/>
</form>

<table>
<tr>
<th>FirstName</th>
<th>LastName</th>
<th>Email</th>
<th>Action</th>
</tr>




<c:forEach var="tempStudent" items="${STUDENT_LIST}" >
<c:url var="tempLink" value="StudentControllerServlet">
<c:param name="command" value="LOAD"/>
<c:param name="studentId" value="${tempStudent.id }"/>
</c:url>
<c:url var="deleteLink" value="StudentControllerServlet">
<c:param name="command" value="DELETE"/>
<c:param name="studentId" value="${tempStudent.id }"/>
</c:url>
<tr>
<td>${ tempStudent.firstname}</td>
<td>${ tempStudent.lastname}</td>
<td>${ tempStudent.email}</td>
<td>
	<a href="${tempLink}">update</a>
	|
	<a href="${deleteLink}"
		onclick="if(!(confirm('Are you sure you want to delete this student?'))) return false">delete</a>

</td>
</tr>

</c:forEach>
</table>
</div>
</div>
</body>
</html>