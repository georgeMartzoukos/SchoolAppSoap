<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page pageEncoding="UTF-8" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Νέα Στοιχεία User </h1>
	<p>user ${requestScope.user.username}</p>
<%--	<p>user ${requestScope.user.password}</p>--%>
	<a href="${pageContext.request.contextPath}/schoolapp/static/usertemplates/usersmenu.jsp">Επιστροφή</a>
</body>
</html>
