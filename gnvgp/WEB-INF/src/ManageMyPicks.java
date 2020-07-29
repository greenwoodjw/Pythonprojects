//package org.apache.catalina.realm.JDBCRealm;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ManageMyPicks extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private Statement statement, statement2;
	String sql;
	String webserver;
	//private PreparedStatement uploadNewMonth=null;

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
			statement2 = connection.createStatement();
			//uploadNewMonth= connection.prepareStatement(
				//"update victims set months=? where msgID=?");
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

        String uid = request.getUserPrincipal().getName();
        request.setAttribute("uid", uid);
		DP.main("uid="+request.getUserPrincipal().getName());


		try{
			if (uid.equals("admin"))
			{
				sql = "select victimfirstname, victimlastname, months, vicID from victims order by victimlastname";
			}else{
				sql = "select victimfirstname, victimlastname, months, vicID from victims where ownerid=\""+uid+"\" order by victimlastname";
			}
			
			DP.main(sql);
			ResultSet resultsMyVictims = statement.executeQuery(sql);


			request.setAttribute("resultsMyVictims", resultsMyVictims);
			request.setAttribute("uid", uid);
			// NOTE: send up unattributed balance, add pick
			request.setAttribute("webserver", webserver);
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());
			out.println("<title>Error</title>");
			out.println("</head>");
			out.println("<body><p>Skin Database error occurred. ");
			out.println("Try again later.</p></body></html>");

		}

		try{
			sql = "select balance from users where user_name=\""+uid+"\"";
			System.out.println(sql);
			ResultSet resultsMyBalance = statement2.executeQuery(sql);
			request.setAttribute("resultsMyBalance", resultsMyBalance);
			resultsMyBalance.next();
			DP.main("my balance "+resultsMyBalance.getInt( 1 ));
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());
			out.println("<title>Error</title>");
			out.println("</head>");
			out.println("<body><p>Skin Database error occurred. ");
			out.println("Try again later.</p></body></html>");

		}



			RequestDispatcher view2 =
						 request.getRequestDispatcher("WEB-INF\\jsp\\showReport.jsp");
			view2.forward(request, response);

	}//end doGet




}