import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ApplySteal extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
	//private Statement statement;
	String sql;
	String webserver;
	private PreparedStatement uploadNewMonth=null, uploadNewBalance=null;
	/**
	 * Constructor of the object.
	 */
	public ApplySteal() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
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
			//statement = connection.createStatement();
			//statement2 = connection.createStatement();
			uploadNewMonth= connection.prepareStatement(
				"update victims set months=?, ownerid=? where vicID=?");
			uploadNewBalance= connection.prepareStatement(
				"update users set balance=? where user_name=?");
			//updateJackpot = connection.prepareStatement("insert into jackpot (month, skin, awarded) values (?, ?, ?);");
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
	
	   
	   
	   /**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//Integer.parseInt(aString);
		//int currentDollars = Integer.parseInt(request.getParameter("currentDollars"));
		int months = Integer.parseInt(request.getParameter("months"));
		int vicID = Integer.parseInt(request.getParameter("vicID"));
		int availableBalance=Integer.parseInt(request.getParameter("availableBalance"));
		String user_name=request.getParameter("user_name");
		
		//request.setAttribute("currentDollars", currentDollars);
		request.setAttribute("months", months);		
		request.setAttribute("vicID", vicID);	
		request.setAttribute("availableBalance",availableBalance);
		request.setAttribute("user_name",user_name);
		
		//int deltaDollars=newDollars-currentDollars;
		
		if (availableBalance >=months)
		{
			//System.out.println("you have enough");
			try
			{
				uploadNewMonth.setInt(1,months);
				uploadNewMonth.setString(2,user_name);
				uploadNewMonth.setInt(3,vicID);
				//uploadNewMonth.setBoolean(4,true);
				uploadNewMonth.executeUpdate();	
				uploadNewBalance.setInt(1,availableBalance-months);
				uploadNewBalance.setString(2,user_name);
				uploadNewBalance.executeUpdate();
				
				//java.util.Date date = new java.util.Date(System.currentTimeMillis());
				//java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
				
				//updateJackpot.setTimestamp(1,timestamp);			
				//updateJackpot.setInt(2,1);
				//updateJackpot.setInt(3,0);	
				//updateJackpot.executeUpdate();
				//need transaction log, increment jackpot
				gnvgpQueries jacked= new gnvgpQueries();
				jacked.updateJackpot("Steal "+vicID+" $",1);
				jacked.updateTransactionLog("steal", "steal", vicID, user_name, months);
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
				System.out.println(sqlException.getMessage());
				out.println("<title>Error</title>");
				out.println("</head>");
				out.println("<body><p>Skin Database error occurred. ");
				out.println("Try again later.</p></body></html>");
	
			}
			RequestDispatcher view =
				 request.getRequestDispatcher("WEB-INF\\jsp\\stealComplete.jsp");
			view.forward(request, response);
		}
		else
		{
			System.out.println("not enough");
			
			RequestDispatcher view =
				 request.getRequestDispatcher("WEB-INF\\jsp\\stealFailed.jsp");
			view.forward(request, response);
			
			
		}
		
		
		
		

		
		
		out.flush();
		out.close();
		
		
		
		
		
		
		
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
