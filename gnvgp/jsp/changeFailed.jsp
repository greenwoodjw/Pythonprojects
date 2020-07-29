<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <meta http-equiv="refresh" content="600;url=http://localhost:8080/gnvgp/index.jsp">
    
    
    <title>My JSP 'changeFailed.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <%@ include file="header.jsp"%>
  <%
  String webserver= (String)request.getAttribute("webserver");
  int currentDollars= (Integer)request.getAttribute("currentDollars");
  int newDollars= (Integer)request.getAttribute("newDollars");
  int vicID= (Integer)request.getAttribute("vicID");
  int availableBalance=(Integer)request.getAttribute("availableBalance");
  String user_name=(String)request.getAttribute("user_name");


  		out.println("    This is ");
  		out.println(this.getClass());
  		out.println(", using the POST method");
  		out.println("currentDollars"+currentDollars);
  		out.println("newDollars"+newDollars);
  		out.println("vicID"+vicID);
  		out.println("availableBalance"+availableBalance);
  		out.println("user_name"+user_name);
  		out.println("  </BODY>");
  		out.println("</HTML>");
  		
  		%><br>
  </body>
</html>
