/*
	SessionListener.java
*/
//package com.example.web;

import javax.servlet.http.*;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
//import java.util.*;
//import com.example.*;
//import com.example.*;
//import com.example.model.*;
import javax.servlet.*;
//import javax.servlet.http.*;
import java.sql.*;
import java.sql.PreparedStatement;

public class SessionListener  implements HttpSessionListener {

	private static int activeSessions = 0;

	public void sessionCreated(HttpSessionEvent se) {
		java.util.Date date=new java.util.Date();
		String id = se.getSession().getId();
		System.out.println(date+" -- session "+id+" created");
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		//HttpSession session = se.getSession();
		//java.util.Date date=new java.util.Date();
		String id = se.getSession().getId();
		DP.main(" session "+id+" destroyed");

		  /*try {
		   RemoveFromCart(session);
		  } catch(Exception e) {
		   System.out.println("error while logging out at session destroyed : " + e);
  }*/

	}

	public static int getActiveSessions() {
		return activeSessions;
	}


    //public void main(ServletConfig config) throws ServletException {
	 public void RemoveFromCart(HttpSession httpSession)  {
				Connection connection;
				Statement statement, statement2;
				String sql;
				PreparedStatement cleanCart=null, updateInventory=null;
				//float partprice;

			try {
				ServletContext context = httpSession.getServletContext();
				Class.forName(context.getInitParameter("databaseDriver"));
				connection = DriverManager.getConnection(context
						.getInitParameter("databaseName"), context
						.getInitParameter("username"), context
						.getInitParameter("password"));
				statement = connection.createStatement();
				statement2=connection.createStatement();
				//webserver=context.getInitParameter("webserver");
				cleanCart= connection.prepareStatement("delete from shoppingcart where sessionid=(?)");
				updateInventory=connection.prepareStatement("update partslist set inventory=(?) where partID=(?)");


				try{
					sql = "select partID, count from shoppingcart where sessionid=\""+ httpSession.getId()+"\"";
					System.out.println(sql);
					ResultSet result = statement.executeQuery(sql);
					result.next();

					   if (result.first())
					   {
						   do{
								//update partslist with new total
								//remove shappingcart record
							try{
								sql = "select inventory from partslist where partID=\""+ result.getString(1)+"\"";
								//System.out.println(sql);
								ResultSet result2 = statement2.executeQuery(sql);
								result2.next();

								updateInventory.setInt(1,result2.getInt(1)+Integer.parseInt(result.getString(2)));
								updateInventory.setString(2,result.getString(1));
								updateInventory.executeUpdate();
								cleanCart.setString(1,httpSession.getId());
								cleanCart.executeUpdate();
								//System.out.println("partID ="+result.getString(1)+"count="+result.getString(2));

							}	catch (SQLException sqlException) {
								sqlException.printStackTrace();
								System.out.println(sqlException.getMessage());
							}


						   }while (result.next());
					   }

				}
				catch (SQLException sqlException) {
					sqlException.printStackTrace();
					System.out.println(sqlException.getMessage());
					//out.println("<title>Error</title>");
					//out.println("</head>");
					//out.println("<body><p>Part Database error occurred. ");
					//out.println("Try again later.</p></body></html>");

				}







			} // end try

			catch (Exception exception) {
				System.out.print(exception.getMessage());
				exception.printStackTrace();
				//throw new UnavailableException(exception.getMessage());

			} // end catch
		} // end method init
//* end init*/

}