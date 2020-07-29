import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionPool extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

public ConnectionPool() {
super();
}

public static Connection getConnection()
{
try
{
Class.forName("com.mysql.jdbc.Driver");
java.sql.Connection connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/gnvgp", "root", "password");
if (con != null) {
System.out.println("Connection Pool Database Connection Success");
}
}catch(final ClassNotFoundException cfe)
{

}catch(final SQLException se)
{

}

return con;
}

private static Connection con=null;
}

