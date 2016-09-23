<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.text.*, java.util.*" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<% // session option
		session.setAttribute("session_id","dev01");
		SimpleDateFormat fmt = new SimpleDateFormat("yy-MM-dd [ hh:mm:ss ]");
		long createTime = (long)session.getCreationTime(); // 세션 생성시간
		long lastTime = (long)session.getLastAccessedTime(); // 마지막 처리 시간
	%>
	session 생성 시간 = <%=fmt.format(new Date(createTime))%><br />
	session 마지막 처리시간 = <%=fmt.format(new Date(lastTime))%><br />
	session 유지시간 = <%=(int)session.getMaxInactiveInterval()%><br />
	session 유지시간 변경 = <%session.setMaxInactiveInterval(5);%><br />
	session 값 = <%=(String)session.getAttribute("session_id")%><br />
	session ID = <%=session.getId()%><br />
	session 유지시간 = <%=(int)session.getMaxInactiveInterval()%><br />
</body>
</html>