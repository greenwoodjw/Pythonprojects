<%@ page import="java.util.*" 
import="java.sql.*"
import="java.lang.*"%>

<html>

<body>

<head>
<script type="text/javascript" src="numbersOnly.js"></script>

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
	out.print("<td width=150 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Owner</FONT></FONT></P></td>");
	out.print("<td width=150 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Available Bal</FONT></FONT></P></td>");
	out.print("</tr>");
   	out.println("</center>");
   
   
   String webserver= (String)request.getAttribute("webserver");
   //String uid = (String)request.getAttribute("uid");
   //ResultSet resultsMyBalance = (ResultSet)request.getAttribute("resultsMyBalance"); 
 
 //out.println("available balance="+resultsMyBalance.getInt( 1 ));



 out.println("<TR align=center>");
 out.println("</TR>");

   ResultSet resultsAllVictims = (ResultSet)request.getAttribute("resultsAllVictims");
   
   
   int orderCount;
   float itemTotal=0, price, cartTotal=0;
   
 
   if (resultsAllVictims.first())
   {

	   do{

		out.println("	<TR>");

		out.print("<td width=251 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsAllVictims.getString( 1 ) +" "+resultsAllVictims.getString( 2 ) +"</FONT></FONT></P></td>");

			if (resultsAllVictims.getInt( 3 ) > 0 )
			{
				//out.print("<td width=64 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsAllVictims.getInt( 3 ) +"</FONT></FONT></P></td>");
				out.print("<td  <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">");
				out.print("<form action=\"ApplyChange.do\" method=Post >");
				
				out.print("<input type=text size=4 name=newDollars value="+resultsAllVictims.getInt( 3 ) +" onkeypress=\"return numbersonly(this, event)\">");
				out.print("<input type=hidden name=currentDollars value="+resultsAllVictims.getInt( 3 ) +">");
				
				//out.print("<input type=text size=4 name=months value="+resultsAllVictims.getInt( 3 ) +" onkeypress=\"return numbersonly(this, event)\">");
				out.print("<input type=hidden size=4 name=vicID value="+resultsAllVictims.getInt( 6 ) +">");
				out.print("<input type=hidden name=user_name value="+resultsAllVictims.getString( 4 )+">");
				out.print("<input type=hidden name=availableBalance value="+resultsAllVictims.getString( 5 )+">");
				out.print("<input type=submit value=\"Update\">");
				out.print("</input></form></FONT></FONT></P>");
						
				
				//start dead
				out.print("<P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">");
				out.print("<form action=\"doWin.do\" method=Post >");
				
				out.print("<input type=text size=4 name=newDollars value="+resultsAllVictims.getInt( 3 ) +" onkeypress=\"return numbersonly(this, event)\">");
				out.print("<input type=hidden name=currentDollars value="+resultsAllVictims.getInt( 3 ) +">");
				
				out.print("<input type=hidden size=4 name=vicID value="+resultsAllVictims.getInt( 6 ) +">");
				out.print("<input type=hidden name=user_name value="+resultsAllVictims.getString( 4 )+">");
				out.print("<input type=hidden name=availableBalance value="+resultsAllVictims.getString( 5 )+">");
				out.print("<input type=submit value=\"Dead\">");
				out.print("</input></form></FONT></FONT></P>");
				//end dead
				
						
				out.print("</td>");
				

			

				
				
			}else if ( resultsAllVictims.getInt( 3 ) <= 0)
			{
				
				
				out.print("<td  <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">");
				out.print("<form action=\"ApplyChange.do\" method=Post >");
				
				out.print("<input type=text size=4 name=newDollars value="+resultsAllVictims.getInt( 3 ) +" onkeypress=\"return numbersonly(this, event)\">");
				out.print("<input type=hidden name=currentDollars value="+resultsAllVictims.getInt( 3 ) +">");
				
				//out.print("<input type=text size=4 name=months value="+resultsAllVictims.getInt( 3 ) +" onkeypress=\"return numbersonly(this, event)\">");
				out.print("<input type=hidden size=4 name=vicID value="+resultsAllVictims.getInt( 6 ) +">");
				out.print("<input type=hidden name=user_name value="+resultsAllVictims.getString( 4 )+">");
				out.print("<input type=hidden name=availableBalance value="+resultsAllVictims.getString( 5 )+">");
				out.print("<input type=submit value=\"Update\">");
				out.print("</input></form></FONT></FONT></P>");
						
				
				//start dead
				out.print("<P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">");
				out.print("<form action=\"doWin.do\" method=Post >");
				
				out.print("<input type=text size=4 name=newDollars value="+resultsAllVictims.getInt( 3 ) +" onkeypress=\"return numbersonly(this, event)\">");
				out.print("<input type=hidden name=currentDollars value="+resultsAllVictims.getInt( 3 ) +">");
				
				out.print("<input type=hidden size=4 name=vicID value="+resultsAllVictims.getInt( 6 ) +">");
				out.print("<input type=hidden name=user_name value="+resultsAllVictims.getString( 4 )+">");
				out.print("<input type=hidden name=availableBalance value="+resultsAllVictims.getString( 5 )+">");
				out.print("<input type=submit value=\"Dead\">");
				out.print("</input></form></FONT></FONT></P>");
				//end dead
				out.print("<form action=\"RemovePick.do\" method=Post >");
				out.print("<input type=hidden size=4 name=vicID value="+resultsAllVictims.getInt( 6 ) +">");
				out.print("<input type=hidden name=user_name value="+resultsAllVictims.getString( 4 )+">");
				out.print("WINNER or abandoned ");
				out.print("<input type=submit value=\"Remove\">");
				out.print("</input></form>");
				out.print("</td>");
				
				
								
			}else{
				out.print("<td width=64 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">********</FONT></FONT></P></td>");
			}

		out.print("<td width=251 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsAllVictims.getString( 4 ) +"</FONT></FONT></P></td>");
		out.print("<td width=251 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsAllVictims.getString( 5 ) +"</FONT></FONT></P></td>");
		out.print("</tr>");

	   }while (resultsAllVictims.next());
   }
  
   
   
    out.println("</TABLE>");
    
			out.println("<br>Add a pick");
			out.println("<TABLE WIDTH=750 BORDER=5 CELLPADDING=2 CELLSPACING=3>");
			out.println("	<COL WIDTH=285>");
			out.println("	<COL WIDTH=150>");
			out.println("	<COL WIDTH=150>");
			out.println("	<TR>");
			out.print("<td width=285 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">First -            Last</FONT></FONT></P></td>");
			out.print("<td width=150 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Ghoul</FONT></FONT></P></td>");
			out.print("<td width=150 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Through</FONT></FONT></P></td>");
			out.print("</tr>");
			out.println("	<TR>");
			out.print("<form action=\"AddPick.do\" method=GET target=_new>");
			out.print("<td><input type=text size=22 name=firstname ><input type=text size=22 name=lastname ></td>");
			out.println("<td><input type=text name=uid value=\""+uid+ "\"></td>");
			out.print("<td><input type=text size=4 name=months >");
			out.print("<input type=submit value=\"Update\"></td>");
			out.print("</tr>");
			out.print("</form>");
			out.println("	</Table>");


%>

</body>
</html>