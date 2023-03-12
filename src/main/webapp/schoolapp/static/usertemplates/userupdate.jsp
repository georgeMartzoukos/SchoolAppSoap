<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Update</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/schoolapp/static/css/update.css">

</head>
<body>
	<div>
		<form method="POST" action="${pageContext.request.contextPath}/schoolapp/userupdate">
			<label for="tid">Κωδικός</label>
			<input id="tid" type="text" name="id" value="${param.id}" readonly><br>
			<label for="username">Username</label>
			<input id="username" type="text" name="username" value="${param.username}"><br>
			<label for="password">Password</label>
			<input id="password" type="text" name="password" value=""><br><br>
			<button  type="submit">Update User</button>

		</form>	
	</div>	

	<c:if test="${requestScope.isError}">
		<p>${requestScope.message}</p>
	</c:if>
</body>
</html>
