import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class GetReport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
	String sql,correctID, webserver, correctPassWord;


//*init
	   public void init(ServletConfig config) throws ServletException {
		// attempt database connection and create Statement
		try {
			ServletContext context = config.getServletContext();
			Class.forName(context.getInitParameter("databaseDriver"));
			connection = DriverManager.getConnection(context
					.getInitParameter("databaseName"), context
					.getInitParameter("username"), context
					.getInitParameter("password"));

			// create Statement to query database
			statement = connection.createStatement();
			webserver=context.getInitParameter("webserver");
		} // end try
		// for any exception throw an UnavailableException to
		// indicate that the servlet is not currently available
		catch (Exception exception) {
			System.out.print(exception.getMessage());
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());

		} // end catch
	} // end method init
//* end init



    public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
      response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        //String uid = request.getParameter("uid");
		//System.out.println("uid="+request.getParameter("uid"));


		out.println("<html>");
		out.println("<head>");
		out.println("<title>GNVGP</title>");
		out.println("</head>");
		out.println("<body>");


		out.println("<H1 ALIGN=CENTER>The Great Northern Virginia</H1>");

		out.println("<H1 ALIGN=CENTER>Ghoul Pool</H1>");
		out.println("<P ALIGN=CENTER><BR><BR>");
		out.println("</P>");
		Calendar calHeader = Calendar.getInstance();
		int headerDateInt= calHeader.get(Calendar.DAY_OF_MONTH);
		int headerMonthInt = calHeader.get(Calendar.MONTH)+1;
		int headerYear = calHeader.get(Calendar.YEAR);
		out.println("<P><FONT FACE=\"Courier\" SIZE=3>"+headerMonthInt+"/"+headerDateInt+"/"+headerYear+"</font></P>");
		out.println("<p><FONT FACE=\"Courier\" SIZE=3><a href=\"../gnvgp/index.jsp\">Home</a></font></P>");


        		try {

					sql = "select users.friendlyname, victims.victimfirstname, victims.victimlastname, victims.months from victims, users where victims.ownerid=users.user_name order by victims.victimlastname";
					//System.out.println(sql);
					DP.main(sql);
					ResultSet resultsGm = statement.executeQuery(sql);


					//resultsGm.next();

						out.println("<TABLE WIDTH=750 BORDER=5 CELLPADDING=2 CELLSPACING=3>");
						out.println("	<COL WIDTH=285>");
						out.println("	<COL WIDTH=281>");
						out.println("	<COL WIDTH=150>");

						out.println("	<TR>");
						out.print("<td width=285 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">You</FONT></FONT></P></td>");
						out.print("<td width=281 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Who</FONT></FONT></P></td>");
						out.print("<td width=150 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">Through</FONT></FONT></P></td>");
						out.print("</tr>");

						while ( resultsGm.next() )
						 {
										//cal
							Calendar c1 = Calendar.getInstance();
							int monthInt = c1.get(Calendar.MONTH);
							int currentMonthInt = c1.get(Calendar.MONTH);
							String monthString;
							//int year =c1.get(Calendar.YEAR);
							//int currentYear = c1.get(Calendar.YEAR);
							//int day=c1.get(Calendar.DAY_OF_MONTH);
							monthInt =monthInt + resultsGm.getInt( 4 )-1 ;
							c1.set(Calendar.MONTH, monthInt);//end cal

							switch (monthInt%12) {
								case 0:  monthString="Jan"; break;
								case 1:  monthString="Feb"; break;
								case 2:  monthString="Mar"; break;
								case 3:  monthString="Apr"; break;
								case 4:  monthString="May"; break;
								case 5:  monthString="Jun"; break;
								case 6:  monthString="Jul"; break;
								case 7:  monthString="Aug"; break;
								case 8:  monthString="Sep"; break;
								case 9:  monthString="Oct"; break;
								case 10: monthString="Nov"; break;
								case 11: monthString="Dec"; break;
								default: monthString="Invalid month.";break;
							}


							out.println("	<TR>");
							out.print("<td width=24 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsGm.getString( 1 ) +"</FONT></FONT></P></td>");
							out.print("<td width=24 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+resultsGm.getString( 2 ) +" "+resultsGm.getString( 3 ) +"</FONT></FONT></P></td>");

							if (monthInt >= currentMonthInt )
							{
								out.print("<td width=64 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">"+monthString+"-"+c1.get(Calendar.YEAR) +"</FONT></FONT></P></td>");
							}else if ( resultsGm.getInt( 4 ) <= -10)
							{
								out.print("<td width=64 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">WINNER"+resultsGm.getInt( 4 )+"</FONT></FONT></P></td>");
							}else{
								out.print("<td width=64 height=18 <P ALIGN=LEFT><FONT COLOR=\"#000000\"><FONT FACE=\"Courier\">********</FONT></FONT></P></td>");
							}

							out.print("</tr>");
						 } // end while

						out.println("	</Table>");



						try{
							sql = "select skin, awarded from jackpot where awarded=0";
							DP.main(sql);
								int jackpot=0;
							ResultSet resultsJackpot = statement.executeQuery(sql);
								while ( resultsJackpot.next() )
								{
									jackpot=jackpot+resultsJackpot.getInt(1);
								}
							out.println("<br>Jackpot=$"+jackpot);
							out.println("<br><br>** Denotes unpaid dues");
							out.println("<br>%  New theft ");

						}
						catch (SQLException sqlException) {
							sqlException.printStackTrace();
							System.out.println(sqlException.getMessage());
							out.println("<title>Error</title>");
							out.println("</head>");
							out.println("<body><p>Jackpot Database error occurred. ");
							out.println("Try again later.</p></body></html>");

						}



						out.println("</body>");
						out.println("</html>");


				} // end try
						// if database exception occurs, return error page
				catch (SQLException sqlException) {
					sqlException.printStackTrace();

					System.out.println(sqlException.getMessage());
					out.println("<title>Error</title>");
					out.println("</head>");
					out.println("<body><p>Database error occurred. ");
					out.println("Try again later.</p></body></html>");

				} finally {
					out.close();
				}// end catch
}//end post
}