import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class doWin extends HttpServlet {

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
        //PrintWriter out = response.getWriter();

      
       //*
       //**** add back prepay to winner balance
       //**** calculate jackpot (done in report already)
       //**** add winnings to player balance
       //**** set jackpot.awarded=1 where =0
       //* 
       //**** set vic to -99
       //* add victim to alltime list
       //**** decrement victims
       //**** add skin to jackpot
       //* 
       //* 
       //*/
   
      
      
      //get stuff from jsp
		int currentDollars = Integer.parseInt(request.getParameter("currentDollars"));
		int vicID=Integer.parseInt(request.getParameter("vicID"));
		String user_name=request.getParameter("user_name");		
		//String firstName=request.getParameter("firstname");
        System.out.println("currentDollars"+currentDollars);
        System.out.println("vicID"+vicID);      
      
      
      
		gnvgpQueries died= new gnvgpQueries();
		died.setDeath("set vic to -99",vicID);
		died.applySkin("apply skin to vics");
		died.markJackpotWin("jackpot 0=1");
		died.updateJackpot("apply skin",died.getSkin());
		died.addToBalance("add win", died.getSkin(), user_name); //adds win into balance
		died.addToBalance("add back unpaid", currentDollars, user_name); //adds unpaid to balance
		
		
		
		
		
		
		
		
		request.setAttribute("jackpotDelta", died.getSkin());
		request.setAttribute("currentDollars", currentDollars);
		
		RequestDispatcher view =
			 request.getRequestDispatcher("WEB-INF\\jsp\\applyWinComplete.jsp");
			 view.forward(request, response);
						
						
						
						
	}//end doPost




}