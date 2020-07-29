<html><body>

<%@ include file="./WEB-INF/jsp/header.jsp"%>

<%
ServletContext context_logout = getServletContext();
//String webserver = context_logout.getInitParameter("webserver");

out.println("<p><center><FONT FACE=\"Courier\" SIZE=3><a href=\"../"+context_logout.getInitParameter("webcontainer")+"/index.jsp\">Log in</a></font></center></P>");
%>

<!--
<p><center><FONT FACE=\"Courier\" SIZE=3><a href="http://localhost:8080/gnvgp-beta">Log in</a></font></center></P>

<p><center><FONT FACE=\"Courier\" SIZE=3><a href="../index.jsp">Log in</a></font></center></P>
-->

</body>
</html>
