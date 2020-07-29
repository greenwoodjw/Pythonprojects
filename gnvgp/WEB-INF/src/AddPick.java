import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
//import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * 
 * @author Jim
 *
 */


/***
 * servlet that adds pick to your own account
 */
public class AddPick extends HttpServlet {

	/**
	 * 
	 */
	
	
    Connection conn = null;
    Statement statement = null;
	InitialContext ctx;
    DataSource ds;
	private static final long serialVersionUID = 1L;
	//private Connection connection;
	//private Statement statement;
	String sql;
	String webserver;
	
	private PreparedStatement uploadNewVictim=null,  getNewVictimID=null;
	/**
	 * Constructor of the object.
	 */
	public AddPick() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	

	   
	   
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
		
		//pass in add a pick variables  NEXT

		int months = Integer.parseInt(request.getParameter("months"));
		String firstName=request.getParameter("firstname");
		String lastName=request.getParameter("lastname");
		String user_name=request.getParameter("uid");
		int availableBalance=Integer.parseInt(request.getParameter("availableBalance"));
	
		request.setAttribute("firstname", firstName);
		request.setAttribute("lastname", lastName);
		request.setAttribute("months", months);		
		request.setAttribute("user_name",user_name);
		
		
		
		//jwg
		try{

			try {
				ctx = new InitialContext();
	
	
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				System.out.print("context error");
				e.printStackTrace();
			}
	
	
			try {
				ds = (DataSource)ctx.lookup("java:comp/env/jdbc/gnvgpDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				System.out.print("datasource error");
				e.printStackTrace();
			}		
			conn = ds.getConnection();
		    statement = conn.createStatement();
			uploadNewVictim= conn.prepareStatement(
			"insert into victims (ownerid, victimfirstname, victimlastname, months , stolen  ) VALUES (?,?,?,?,?)");
			getNewVictimID= conn.prepareStatement(
			"select vicID from victims where victimfirstname=? and victimlastname=?");
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());
		}   
		
		//jwg
		
	
		
		//int deltaDollars=newDollars-currentDollars;
		
		if (availableBalance >=months)
		{
			System.out.println("you have enough");
			//is it a duplicate?
			
			gnvgpQueries jacked= new gnvgpQueries();
			//jacked.findDupAdd(firstName, lastName);
			jacked.setVicID(firstName, lastName);
			int vicID=jacked.getVicID();
			System.out.println("is this a dup?"+vicID);
			if (vicID >0)
			{
				System.out.print("dup");
				RequestDispatcher view =
					 request.getRequestDispatcher("WEB-INF\\jsp\\addFailed.jsp");
				view.forward(request, response);
			}else{
				System.out.print("new");
			//}
			
			try
			{
				uploadNewVictim.setString(1,user_name);
				uploadNewVictim.setString(2,firstName);
				uploadNewVictim.setString(3,lastName);
				uploadNewVictim.setInt(4,months);
				uploadNewVictim.setInt(5,0);
				uploadNewVictim.executeUpdate();	
				//uploadNewBalance.setInt(1,availableBalance-months);
				//uploadNewBalance.setString(2,user_name);
				//uploadNewBalance.executeUpdate();
				
				//gnvgpQueries jacked= new gnvgpQueries();
				jacked.setVicID(firstName, lastName);
				vicID=jacked.getVicID();
				jacked.updateTransactionLog("added", "add $", vicID, user_name, availableBalance-months);
				//if (currentDollars==0)
				//{
					/*java.util.Date date = new java.util.Date(System.currentTimeMillis());
					java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
					
					updateJackpot.setTimestamp(1,timestamp);			
					updateJackpot.setInt(2,1);
					updateJackpot.setInt(3,0);	
					updateJackpot.executeUpdate();*/

					jacked.updateJackpot("Added "+vicID+" $",1);

				//}
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
				 request.getRequestDispatcher("WEB-INF\\jsp\\addComplete.jsp");
			view.forward(request, response);
			}
		}
		else
		{
			System.out.println("not enough");
			
			RequestDispatcher view =
				 request.getRequestDispatcher("WEB-INF\\jsp\\addFailed.jsp");
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
