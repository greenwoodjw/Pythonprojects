<h1 align="center">GNVGP</h1>
<head>
<meta http-equiv="refresh" content="60;url=./index.jsp">
</head>
<!--<iframe frameborder="0" border=0 width="800" height=1000
src="http://gnvgp.blogspot.com/" name=iframe scrolling=yes
style="position:absolute;" allowtransparency="true"></iframe>
-->
	
<%@ page import="java.net.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.sql.*" %>
<% 



//out.println(session.getId());
		 String uid=request.getUserPrincipal().getName(); 
		 String thisRole=null;



try{
	Class.forName("com.mysql.jdbc.Driver").newInstance();
}
catch (Exception E) {
	out.println("Unable to load driver.");
	E.printStackTrace();
}

try{
	Connection C = DriverManager.getConnection("jdbc:mysql://localhost:3306/gnvgp", "root","G0FastTurnLeft");
	Statement S = C.createStatement();

	ResultSet rs = S.executeQuery("SELECT role_name FROM user_roles where user_name=\""+uid+"\"");
	ResultSetMetaData rsStruc = rs.getMetaData();
	int colCount = rsStruc.getColumnCount();
	String colName = "";
	for(int i=1;i <= colCount; i++){
	colName = rsStruc.getColumnName(i) ;
}//end try
	rs.next();
	thisRole=rs.getString(1);
	//out.println("role="+thisRole);
	 

		
	rs.close();
	C.close();
}
 		catch (Exception E) {
	 out.println("SQLException: " + E.getMessage());
 }//end catch
 		

	
 		
out.println("<TABLE WIDTH=150 BORDER=5 CELLPADDING=2 CELLSPACING=3 align=left>");
out.println("<COL WIDTH=150>");

out.println("<TR>");
out.println("<TD>");
out.println("	<P><center><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3><br><form method=\"POST\"");
out.println("	action=\"GetReport.do\">");
out.println("	<input type=\"SUBMIT\" value=\"Get Current Report\">");
out.println("	</center>");
out.println("	</form></FONT></FONT></FONT></P>");
out.println("</TD>	");
out.println("</Tr>	");	

out.println("<TD WIDTH=150>");
out.println("<P><center><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3><br><form method=\"POST\"");
out.println("action=\"showSteals.do\">");
out.println("<input type=\"SUBMIT\" value=\"Steals\">");
out.println("</center>");
out.println("</form></FONT></FONT></FONT></P>");
out.println("</TD>");


if (thisRole.equals("user"))
{
out.println("<TR>");
out.println("<TD>");
out.println("	<P><center><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3><br><form method=\"POST\"");
out.println("action=\"ManageMyPicks.do\">");
out.println("<input name=\"uid\" type=hidden value=\""+request.getUserPrincipal().getName()+"\">");
out.println("<input type=\"SUBMIT\" value=\"Get my picks\">");
out.println("</form></FONT></FONT></FONT></P>");
out.println("</TD> 	");	
out.println("</Tr>	");	




}

if (thisRole.equals("admin"))
	 {
	out.println("<TR>");
	out.println("<TD>");
	out.println("	<P><center><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3><br><form method=\"POST\"");
	out.println("action=\"ManageMyPicks.do\">");
	out.println("<input name=\"uid\" type=hidden value=\""+request.getUserPrincipal().getName()+"\">");
	out.println("<input type=\"SUBMIT\" value=\"Get my picks\">");
	out.println("</form></FONT></FONT></FONT></P>");
	out.println("</TD> 	");	
	out.println("</Tr>	");	
	
	out.println("<TR>");
	out.println("<TD>");
	out.println("	<P><center><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3><br><form method=\"POST\"");
	out.println("action=\"manageAllPicks.do\">");
	out.println("<input name=\"uid\" type=hidden value=\""+request.getUserPrincipal().getName()+"\">");
	out.println("<input type=\"SUBMIT\" value=\"Get All Picks\">");
	out.println("</form></FONT></FONT></FONT></P>");
	out.println("</TD> 	");	
	out.println("</Tr>	");	
	
	out.println("<TR>");
	out.println("<TD>");
	out.println("	<P><center><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3><br><form method=\"POST\"");
	out.println("action=\"ApplySkin.do\">");
	out.println("<input name=\"uid\" type=hidden value=\""+request.getUserPrincipal().getName()+"\">");
	out.println("<input type=\"SUBMIT\" value=\"apply skin\">");
	out.println("<input type=\"checkbox\" name=\"confirm\" >");
	out.println("</form></FONT></FONT></FONT></P>");
	out.println("</TD> 	");	
	out.println("</Tr>	");

	out.println("<TR>");
	out.println("<TD>");
	out.println("	<P><center><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3><br><form method=\"POST\"");
	out.println("action=\"doWin.do\">");
	out.println("<input name=\"uid\" type=hidden value=\""+request.getUserPrincipal().getName()+"\">");
	out.println("<input type=\"SUBMIT\" value=\"show wins*\">");
	out.println("</form></FONT></FONT></FONT></P>");
	out.println("</TD> 	");	
	out.println("</Tr>	");
	
	

	

	
	
	
	out.println("<TR>");
	out.println("<TD>");
	out.println("<u>to do</u><br>");
	out.println("	1. undo <br>");
	out.println("	2. apply win<br>");
	out.println("	3. add money to balance<br>");
	out.println("	4. self register<br>");
	out.println("	4a. add new celebs for others<br>");
	out.println("	5. javadocs<br>");
	out.println("	6. look winners<br>");
	out.println("	7. view transaction logs<br>");
	out.println("	8. logout button<br>");
	out.println("</TD> 	");	
	out.println("</Tr>	");
	}


out.println("<TD WIDTH=150>");
out.println("<P><center><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3><br><form method=\"POST\"");
out.println("action=\"logout.do\">");
out.println("<input type=\"SUBMIT\" value=\"logout\">");
out.println("</center>");
out.println("</form></FONT></FONT></FONT></P>");
out.println("</TD>");

out.println("</table>");		
 		
 		
 		
 	

%>

<!--<TABLE WIDTH=750 BORDER=5 CELLPADDING=2 CELLSPACING=3 align=center>
	<COL WIDTH=285>
	<COL WIDTH=281>
	<COL WIDTH=150>
	<COL WIDTH=150>
	<TR>
		<TD WIDTH=285>
			<P><center><FONT COLOR="#000000"><FONT FACE="Courier"><FONT SIZE=3><br><form method="POST"
			action="GetReport.do">
			<input type="SUBMIT" value="Get Current Report">
			</center>
			</form></FONT></FONT></FONT></P>
		</TD>
		<TD WIDTH=281>
			<P><center><FONT COLOR="#000000"><FONT FACE="Courier"><FONT SIZE=3><br><form method="POST"
			action="ManageMyPicks.do">
			<% 
				out.println("<input name=\"uid\" type=hidden value="+request.getUserPrincipal().getName()+">");
				
			%>
			<input type="SUBMIT" value="Get my picks">
			</form></FONT></FONT></FONT></P>
		</TD>
		<TD WIDTH=150>
			<P><center><FONT COLOR="#000000"><FONT FACE="Courier"><FONT SIZE=3><br><form method="POST"
			action="showSteals.do">
			<input type="SUBMIT" value="Steals">
			</center>
			</form></FONT></FONT></FONT></P>
			</TD>

			<TD WIDTH=150>
			<P><center><FONT COLOR="#000000"><FONT FACE="Courier"><FONT SIZE=3><a href="http://localhost:8080/gnvgp/checkout.jsp">Checkout<a/></FONT></FONT></FONT></P>
		</TD>
			


			ToDO
			
			Remove.do
			ApplyChange	

</TABLE>-->
			
			
