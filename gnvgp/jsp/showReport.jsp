<%@ page import="java.util.*" 
import="java.sql.*"
import="java.lang.*"%>

<html>

<body>

<head>
<script type="text/javascript" src="numbersOnly.js"></script>
<meta http-equiv="refresh" content="600;url=http://localhost:8080/gnvgp/index.jsp">
</head>

<%@ include file="header.jsp"%>





<%
   
	out.println("<html>");
	out.println("<head>");
	out.println("<title>GNVGP</title>");
	out.println("</head>");
	out.println("<body><br><br>");
	out.println("<center>");	
	out.println("<TABLE WIDTH=750 BORDER=5 CELLPADDING=2 CELLSPACING=3>");
	out.println("	<COL WIDTH=281>");
	out.println("	<COL WIDTH=150>");

	out.println("	<TR>");
	out.print("<td width=281 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Who</FONT></FONT></P></td>");
	out.print("<td width=150 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Through</FONT></FONT></P></td>");
	out.print("</tr>");
   	out.println("</center>");
   
   
   String webserver= (String)request.getAttribute("webserver");
   //String uid = (String)request.getAttribute("uid");
   ResultSet resultsMyBalance = (ResultSet)request.getAttribute("resultsMyBalance"); 
 
 out.println("available balance="+resultsMyBalance.getInt( 1 ));



 out.println("<TR align=center>");
 out.println("</TR>");

   ResultSet resultsMyVictims = (ResultSet)request.getAttribute("resultsMyVictims");
   
   
   int orderCount;
   float itemTotal=0, price, cartTotal=0;
   
 
   if (resultsMyVictims.first())
   {

	   do{

		out.println("	<TR>");

		out.print("<td width=251 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsMyVictims.getString( 1 ) +" "+resultsMyVictims.getString( 2 ) +"</FONT></FONT></P></td>");
//out.print("<td width=150 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsMyVictims.getInt( 3 )+"</FONT></FONT></P></td>");

			if (resultsMyVictims.getInt( 3 ) >= 0 )
			{
				//out.print("<td width=64 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsMyVictims.getInt( 3 ) +"</FONT></FONT></P></td>");
				out.print("<td  <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">");
				out.print("<form action=\"ApplyChange.do\" method=POST >");
				out.print("<input type=text size=4 name=newDollars value="+resultsMyVictims.getInt( 3 ) +" onkeypress=\"return numbersonly(this, event)\">");
				out.print("<input type=hidden name=currentDollars value="+resultsMyVictims.getInt( 3 ) +">");
				out.print("<input type=hidden name=vicID value="+resultsMyVictims.getInt( 4 ) +">");
				out.print("<input type=hidden name=user_name value="+uid+">");
				out.print("<input type=hidden name=availableBalance value="+resultsMyBalance.getInt( 1 )+">");
				out.print("<input type=submit value=\"Update\">");
				out.print("</input></form></FONT></FONT></P></td>");

			}else if ( resultsMyVictims.getInt( 3 ) <= -10)
			{
				out.print("<td width=64 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">WINNER"+resultsMyVictims.getInt( 3 )+"</FONT></FONT></P></td>");
			}else{
				out.print("<td width=64 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">********</FONT></FONT></P></td>");
			}
		out.print("</tr>");


	   }while (resultsMyVictims.next());
   }
  
   
    //out.println("<TR><TD WIDTH=40></TD><TD WIDTH=40></TD><TD WIDTH=40></TD><TD WIDTH=40></TD><TD WIDTH=40></TD></TR>");
    
    
    //out.println("<tr>");
    //out.println("<TD WIDTH=40></TD><TD WIDTH=40></TD><TD WIDTH=40></TD><TD WIDTH=40></TD>");
    //out.println("<TD WIDTH=40 align=right><P><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\"><FONT SIZE=3>"+cartTotal+"</FONT></FONT></FONT></P></TD>");   	
    //out.println("</tr>");  	
    
    out.println("</TABLE>");
    
			out.println("<br>Add a pick");
			out.println("<TABLE WIDTH=750 BORDER=5 CELLPADDING=2 CELLSPACING=3>");
			//out.println("	<COL WIDTH=285>");
			out.println("	<COL WIDTH=281>");
			out.println("	<COL WIDTH=150>");
			out.println("	<TR>");
			//out.print("<td width=285 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">You</FONT></FONT></P></td>");
			out.print("<td width=281 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">First - Last</FONT></FONT></P></td>");
			out.print("<td width=150 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Through</FONT></FONT></P></td>");
			out.print("</tr>");
			out.println("	<TR>");
			out.print("<form action=\"AddPick.do\" method=POST target=_new>");
			out.print("<td><input type=text size=22 name=firstname ><input type=text size=22 name=lastname ></td>");
			out.println("<input type=\"hidden\" name=uid value=\""+uid+ "\"><input type=hidden name=availableBalance value="+resultsMyBalance.getInt( 1 )+">");
			out.print("<td><input type=text size=4 name=months  onkeypress=\"return numbersonly(this, event)\">");
			out.print("<input type=submit value=\"Update\"></td>");
			out.print("</tr>");
			out.print("</form>");
			out.println("	</Table>");


%>

</body>
</html>