import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ApplySkin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
	String sql,correctID, webserver, correctPassWord;
	int jackpotDelta=0;
	private PreparedStatement uploadNewMonth=null, updateJackpot=null;

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
			uploadNewMonth= connection.prepareStatement(
				"update victims set months=? where vicID=?");
			updateJackpot = connection.prepareStatement("insert into jackpot (month, skin, awarded) values (?, ?, ?);");
			//webserver=context.getInitParameter("webserver");
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


		out.println("<html>");
		out.println("<head>");
		out.println("<title>GNVGP</title>");
		out.println("</head>");
		out.println("<body>");



		gnvgpQueries jacked= new gnvgpQueries();
		jacked.applySkin("apply skin to vics");
		jacked.updateJackpot("apply skin",jacked.getSkin());
		request.setAttribute("jackpotDelta", jacked.getSkin());
		RequestDispatcher view =
			 request.getRequestDispatcher("WEB-INF\\jsp\\applySkinComplete.jsp");
view.forward(request, response);
						/*try{
							sql = "select vicID, months from victims where months >0";
							System.out.println(sql);
							ResultSet resultsActiveVictims = statement.executeQuery(sql);
							jackpotDelta=0;
								while ( resultsActiveVictims.next() )
								{
									int newMonth = resultsActiveVictims.getInt(2) -1;
									uploadNewMonth.setInt(1,newMonth);
									uploadNewMonth.setInt(2,resultsActiveVictims.getInt(1));
									//uploadNewMonth.executeUpdate();
									jackpotDelta++;
								}
							out.println("<br>Complete "+ jackpotDelta);
							out.println("<br><br>");
							out.println("<br> ");
							

						}
						catch (SQLException sqlException) {
							sqlException.printStackTrace();
							System.out.println(sqlException.getMessage());
							out.println("<title>Error</title>");
							out.println("</head>");
							out.println("<body><p>Skin Database error occurred. ");
							out.println("Try again later.</p></body></html>");

						}*/
						
						/*try{

									//int newMonth = resultsActiveVictims.getInt(2) -1;
									//java.util.Date date=new java.util.Date();
									java.util.Date date = new java.util.Date(System.currentTimeMillis());
									java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
									updateJackpot.setTimestamp(1,timestamp);
									//updateJackpot.setInt(2,jackpotDelta);
									//updateJackpot.setInt(3,0);									
									//updateJackpot.executeUpdate();*/
									//gnvgpQueries jacked= new gnvgpQueries();
									//jacked.updateJackpot("apply skin",jackpotDelta);
									//jackpotDelta++;

							//out.println("<br>added to jackpot"+ jackpotDelta);
							//out.println("<br><br>");
							//out.println("<br> ");

						/*}
						catch (SQLException sqlException) {
							sqlException.printStackTrace();
							System.out.println(sqlException.getMessage());
							out.println("<title>Error</title>");
							out.println("</head>");
							out.println("<body><p>Skin Database error occurred. ");
							out.println("Try again later.</p></body></html>");

						}		*/			
						
						
						
	}//end doGet




}