<%@ page contentType="text/html; charset=utf-8" %>
<%@ page isErrorPage="true" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<strong>[ERROR] 오류가 발생했습니다.</strong>
	<br />
	<br />
	<% if (exception != null) { %>
	- 에러타입: <%= exception.getClass().getName() %>
	<br />
	- 에러메시지: <%= exception.getMessage() %>
	<br />
	<br />
	<% } %>
	<a href="javascript:history.back();">▶ 이전 페이지로 돌아가기</a>
</body>
</html>