package bobby.services;
import java.sql.*;
public class DAOConnection
{
private DAOConnection(){}
public static Connection getConnection()
{
Connection connection=null;
try
{
Class.forName("com.mysql.cj.jdbc.Driver");
connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/school","root","JAIN@divy40041");
}catch(Exception e)
{
System.out.println(e.getMessage());
}
return connection;
}
}