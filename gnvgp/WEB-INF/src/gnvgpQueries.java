import java.util.Date;
import java.sql.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

//import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServlet;


public class gnvgpQueries extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	//private Connection connection;
	private Statement statement;
	String sql,correctID, webserver, correctPassWord;
	int jackpotDelta=0;
	private PreparedStatement  updateJackpot=null, updateLog=null, uploadNewMonth=null, updateMonths=null, updateBalance=null,markJackpot=null;
	
	
    Connection conn = null;
    Statement stmt = null;
	InitialContext ctx;
    DataSource ds;
    int vicID ;
    String victimFirstName,victimLastName,victimFullName;
    String contextDataSource="java:comp/env/jdbc/gnvgpDB";
	/***
	 * updates the jackpot table when money is added or subtracted
	 * 
	 * @param s describes action taken
	 * @param increment  normally 1
	 */   
 
	
	 public void updateJackpot( String s, Integer increment ) 
	 {

	  Date now=new java.util.Date (System.currentTimeMillis());
	  System.out.println( now+" -- "+ s );
	  
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
/*            if (con.isClosed()) {
                log("Removed bad connection from " + name);
                // Try again recursively
                con = getConnection();
            }
*/
		    
		    
		    
			updateJackpot = conn.prepareStatement("insert into jackpot (month, skin, awarded) values (?, ?, ?);");

			java.util.Date date = new java.util.Date(System.currentTimeMillis());
			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			
			updateJackpot.setTimestamp(1,timestamp);			
			updateJackpot.setInt(2,increment);
			updateJackpot.setInt(3,0);	
			updateJackpot.executeUpdate();
			DP.main("incremented jackpot $"+increment);

			conn.close();

		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());


		}finally {
			DP.main("finally, gnvgpQueries.updateJackpot");
		 }
		  
	 }//end updateJackpot
	 
	 public void markJackpotWin( String s ) 
	 {

	  Date now=new java.util.Date (System.currentTimeMillis());
	  System.out.println( now+" -- "+ s );
	  
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
/*            if (con.isClosed()) {
                log("Removed bad connection from " + name);
                // Try again recursively
                con = getConnection();
            }
*/
		    
		    
		    
			markJackpot = conn.prepareStatement("update jackpot set awarded=1 where awarded=0");

			//java.util.Date date = new java.util.Date(System.currentTimeMillis());
			//java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			
			//updateJackpot.setTimestamp(1,timestamp);			
			//updateJackpot.setInt(2,increment);
			//updateJackpot.setInt(3,0);	
			markJackpot.executeUpdate();
			DP.main("jackpot marked");

			conn.close();

		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());


		}finally {
			DP.main("finally, gnvgpQueries.updateJackpot");
		 }
		  
	 }//end updateJackpot
	 
	 public void updateTransactionLog( String s, String type,Integer vicID, String ownerid, Integer amount ) 
	 {

	  Date now=new java.util.Date (System.currentTimeMillis());
	  System.out.println( now+" -- "+ s );
	  
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
		    
			updateLog = conn.prepareStatement("insert into log (month, type, vicID, ownerid, amount) values (?, ?, ?,?,?);");
			
			
			
			java.util.Date date = new java.util.Date(System.currentTimeMillis());
			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			
			updateLog.setTimestamp(1,timestamp);			
			updateLog.setString(2,type);
			updateLog.setInt(3,vicID);
			updateLog.setString(4,ownerid);			
			updateLog.setInt(5,amount);			
			updateLog.executeUpdate();
			DP.main("updated log table");
			conn.close();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());
		}
		  
	 }//end updateTransactionLog
	 
	 /***
	  * returns value of victimID.  equal to zero if victim does not exist
	  * 
	  * @return victimID
	  */
	 public int getVicID()
	 {
	 	return vicID;
	 }
	 

	 /***
	  * setVicID finds the victim's unique ID in the db
	  * @param victimfirstname
	  * @param victimlastname
	  */
	 public void setVicName( int vicID ) 
	 {
		 
	  //Date now=new java.util.Date (System.currentTimeMillis());
	  //System.out.println( now+" -- "+ s );
	  
		try{
	        
			try {
				ctx = new InitialContext();


			} catch (NamingException e) {
				// TODO Auto-generated catch block
				System.out.print("context error");
				e.printStackTrace();
			}


			try {
				//ds = (DataSource)ctx.lookup("java:comp/env/jdbc/gnvgpDB");
				ds = (DataSource)ctx.lookup(contextDataSource);	
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				System.out.print("datasource error");
				e.printStackTrace();
			}		
			conn = ds.getConnection();
		    statement = conn.createStatement();
		    
			//updateLog = conn.prepareStatement("insert into log (month, type, vicID, ownerid, amount) values (?, ?, ?,?,?);");
			
			sql = "select victimFirstName,VictimLastName from victims where vicID=\""+vicID+"\"";
			System.out.println(sql);
			ResultSet resultsVicName = statement.executeQuery(sql);
			victimFullName=" ";
			while(resultsVicName.next())
			{
				victimFullName="\""+resultsVicName.getString(1)+ " "+resultsVicName.getString(2)+"\"";
				System.out.println(vicID);
			}
			

			conn.close();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());
		}
		finally {
		      try {
		          if (conn != null)
		            conn.close();
		        }
		        catch (SQLException e) {
					e.printStackTrace();;
		        }
		}
		  
	 }
	 /***
	  * returns value of victimFullName.  
	  * 
	  * @return victimFullName
	  */
	 public String getVicName()
	 {
	 	return victimFullName;
	 } 
	 
	 /***
	  * setVicID finds the victim's unique ID in the db
	  * @param victimfirstname
	  * @param victimlastname
	  */
	 public void setVicID( String victimfirstname, String victimlastname ) 
	 {
		 
	  //Date now=new java.util.Date (System.currentTimeMillis());
	  //System.out.println( now+" -- "+ s );
	  
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
		    
			//updateLog = conn.prepareStatement("insert into log (month, type, vicID, ownerid, amount) values (?, ?, ?,?,?);");
			
			sql = "select vicID from victims where victimfirstname=\""+victimfirstname+"\" and victimlastname=\""+victimlastname+"\"";
			System.out.println(sql);
			ResultSet resultsVicID = statement.executeQuery(sql);
			vicID=0;
			while(resultsVicID.next())
			{
				vicID=resultsVicID.getInt(1);
				System.out.println(vicID);
			}
			
			//java.util.Date date = new java.util.Date(System.currentTimeMillis());
			//java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			
			//updateLog.setTimestamp(1,timestamp);			
			//updateLog.setString(2,type);
			//updateLog.setInt(3,vicID);
			//updateLog.setString(4,ownerid);			
			//updateLog.setInt(5,amount);			
			//updateLog.executeUpdate();
			//DP.main("incremented jackpot $"+increment);
					//jackpotDelta++;
			//	}
			//out.println("<br>Complete "+ jackpotDelta);
			//out.println("<br><br>");
			//out.println("<br> ");
			conn.close();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());
			
			//out.println("<title>Error</title>");
			//out.println("</head>");
			//out.println("<body><p>Skin Database error occurred. ");
			//out.println("Try again later.</p></body></html>");

		}
		  
	 }



	 /***
	  * gets jackpot delta associated with skin
	  * @return
	  */
	 public int getSkin()
	 {
		 return jackpotDelta;
	
	 }
	 /***
	  * applySkin decrements all victim's balances by $1, adds total to jackpot table.  used on occasion of new month or victim win
	  * 
	  * @param s string for log
	  * 
	  */

	 public void applySkin( String s ) 
	 {

	  Date now=new java.util.Date (System.currentTimeMillis());
	  System.out.println( now+" -- "+ s );
	  
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

			//java.util.Date date = new java.util.Date(System.currentTimeMillis());
			//java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			
			uploadNewMonth= conn.prepareStatement("update victims set months=? where vicID=?");
			sql = "select vicID, months from victims where months >0";
			ResultSet resultsActiveVictims = statement.executeQuery(sql);
			jackpotDelta=0;
				while ( resultsActiveVictims.next() )
				{
					int newMonth = resultsActiveVictims.getInt(2) -1;
					uploadNewMonth.setInt(1,newMonth);
					uploadNewMonth.setInt(2,resultsActiveVictims.getInt(1));
					uploadNewMonth.executeUpdate();
					jackpotDelta++;
				}
			resultsActiveVictims.last();	
			jackpotDelta=resultsActiveVictims.getRow();
			DP.main("applied skin to "+jackpotDelta+ " vics");
			conn.close();

		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());


		}finally {
			
			DP.main("finally, gnvgpQueries.applySkin");

		 }
		  
	 }//end updateJackpot
	 /*
	 public int getVicID()
	 {
	 	return vicID;
	 }
	 
	 */
	 
/***
 * setDeath marks the celeb as dead in victims table
 * 
 */
	 public void setDeath(String s,  Integer vicID ) 
	 {
		 
  
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
		    
			updateMonths = conn.prepareStatement("update victims set months=-99 where vicID=?;");
			updateMonths.setInt(1,vicID);
			updateMonths.executeUpdate();
			DP.main(s + "vicID="+ vicID);
			
			/*
			//sql = "select vicID from victims where victimfirstname=\""+victimfirstname+"\" and victimlastname=\""+victimlastname+"\"";
			//System.out.println(sql);
			ResultSet resultsVicID = statement.executeQuery(sql);
			vicID=0;
			while(resultsVicID.next())
			{
				vicID=resultsVicID.getInt(1);
				System.out.println(vicID);
			}*/

			conn.close();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());
		

		}
		  
	 }
	 public void addToBalance(String s,  Integer dollars, String uid ) 
	 {
		 
  
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
		    

			
			
			sql = "select balance from users where user_name=\""+uid+"\"";
			System.out.println(sql);
			ResultSet resultsBalance = statement.executeQuery(sql);
			resultsBalance.first();
			//vicID=0;
			//while(resultsVicID.next())
			//{
				int balance=resultsBalance.getInt(1);
				System.out.println("balance="+balance);
			//}
				balance=balance+dollars;
				System.out.println("balance="+balance);
		    
		    
			updateBalance= conn.prepareStatement("update users set balance=? where user_name=?;");
			updateBalance.setInt(1,balance);
			updateBalance.setString(2,uid);
			updateBalance.executeUpdate();
			DP.main(s + "new user balance="+ balance);
			conn.close();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
			System.out.println(sqlException.getMessage());
		

		}
		  
	 }
	 	 
}
