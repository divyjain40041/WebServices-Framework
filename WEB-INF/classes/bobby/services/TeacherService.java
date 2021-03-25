package bobby.services;
import bobby.exceptions.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
import bobby.pojo.*;
import java.util.*;
import java.sql.*;
@Path("/teacherservice")
public class TeacherService
{

@InjectRequestParameter(injectRequestParameterKey="roll")
public int t;
public void setT(int t)
{
System.out.println("TeacherService: "+t);
}





@Forward("/teacherservice/getall")
@POST
@Path("/add")
public void add(Teacher teacher) throws BException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("insert into student (roll_number,name,gender) values(?,?,?)");
ps.setInt(1,teacher.getRollNumber());
ps.setString(2,teacher.getName());
ps.setString(3,String.valueOf(teacher.getGender()));
ps.executeUpdate();
ps.close();
connection.close();
}
catch(Exception exception)
{
System.out.println("ADD Exception: "+exception.getMessage());
}
}

@POST
@Path("/edit")
public void edit(Teacher teacher) throws BException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("update teacher set name=?,gender=? where roll_number=?");
ps.setString(1,teacher.getName());
ps.setString(2,String.valueOf(teacher.getGender()));
ps.setInt(3,teacher.getRollNumber());
ps.executeUpdate();
ps.close();
connection.close();
}
catch(Exception exception)
{
System.out.println("ADD Exception: "+exception.getMessage());
}
}
@GET
@Path("/delete")
public void delete(@RequestParameter(requestParameterKey="roll")int rollNumber)
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("delete from teacher where roll_number=?");
ps.setInt(1,rollNumber);
ps.executeUpdate();
ps.close();
connection.close();
}
catch(Exception exception)
{
System.out.println("DELETE Exception: "+exception.getMessage());
}
//return "success";
}
@GET
@Path("/getby")
public Teacher getByTeacherRoll(@RequestParameter(requestParameterKey="roll")int rollNumber,@RequestParameter(requestParameterKey="nm")String nm,ApplicationScope as){
Teacher s=null;
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select *from teacher where roll_number="+rollNumber);
while(resultSet.next())
{
s=new Teacher();
s.setRollNumber(resultSet.getInt("roll_number"));
s.setName(resultSet.getString("name"));
s.setGender(resultSet.getString("gender").charAt(0));
}
resultSet.close();
statement.close();
connection.close();
}
catch(Exception exception)
{
System.out.println(exception.getMessage());
}
return s;
}
public List<Teacher> getAll()  throws BException
{
List<Teacher> teachers=new LinkedList<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select *from teacher order by roll_number");
while(resultSet.next())
{
Teacher s=new Teacher();
s.setRollNumber(resultSet.getInt("roll_number"));
s.setName(resultSet.getString("name"));
s.setGender(resultSet.getString("gender").charAt(0));
teachers.add(s);
}
resultSet.close();
statement.close();
connection.close();
}
catch(Exception exception)
{
System.out.println(exception.getMessage());
}
return teachers;
}
}