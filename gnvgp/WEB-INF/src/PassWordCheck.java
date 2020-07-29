import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PassWordCheck extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement;
	String sql,correctID, correctPassWord;

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


    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
      response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userID = request.getParameter("uid");
        System.out.println("uid="+request.getParameter("uid"));
		String passWord = request.getParameter("password");

        out.println("<html>");
        out.println("<head>");
		out.println("<title>Login page</title>");
        out.println("</head>");
        out.println("<body>");


//use case for submit with null input types, generates login page
	if (userID== (null)){
        	out.println("<h1>Please Login</h1>");
        	out.println("<form action=\"PassWordCheck\" method=GET>");
			out.println("Cheat Sheet ... correct uid=jim, pwd =password.");
			out.println("<br>");
			out.println("userid   :");
			out.println("<input type=text size=20 name=uid value=jim>");
			out.println("<br>");
			out.println("password:");
			out.println("<input type=password size=20 name=password value=password>");
			out.println("<br>");
			out.println("<input type=submit>");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
//end null input
	}else{
//begin login page

//**
        try {

					sql = "select user_name, user_pass from users where user_name = '" + userID +"'";
					System.out.println(sql);
					ResultSet results = statement.executeQuery(sql);

					results.next();
					correctID = results.getString(1);
					correctPassWord = results.getString(2);



							if (userID.equals(correctID)){
								if (passWord.equals(correctPassWord)){

									out.println("<h1>Welcome!</h1>");
									out.println("user_name= " + userID + "<br>");
									out.println("pwd= " + passWord + "<br>");
									out.println("<form action=\"GetReport\" method=GET>");
									out.println("<input type=\"hidden\" name=uid value=\""+correctID+ "\">");
									out.println("<input type=submit value=\"Get Report\">");
									out.println("</form>");
									out.println("<form action=\"ManageMyPicks\" method=GET>");
									out.println("<input type=\"hidden\" name=uid value=\""+correctID+ "\">");
									out.println("<input type=submit value = \"ManageMyPicks\">");
									out.println("</form>");
									out.println("<form action=\"ApplySkin\" method=GET>");
									out.println("<input type=\"hidden\" name=uid value=\""+correctID+ "\">");
									out.println("<input type=submit value = \"Apply Skin\">");
									out.println("</form>");
									out.println("</body>");
									out.println("</html>");
					        	}else{
									out.println("<h1>Incorrect Password</h1>");
									out.println("uid= " + userID + "<br>");
									out.println("pwd= " + passWord + "<br>");
									out.println("</body>");
									out.println("</html>");
								}
							}else{
					        	out.println("<h1>Unknown userID</h1>");
								out.println("uid= " + userID + " is unknown <br>");
			}



			// setup web page header
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



	}
  }
}