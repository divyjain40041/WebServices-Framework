package bobby.services;
import bobby.exceptions.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;

import bobby.pojo.*;
import java.util.*;
import java.sql.*;
@Path("/studentservice")
public class StudentService
{
@Forward("/studentservice/getall")
@POST
@Path("/add")
public void add(Student student,ApplicationScope as) throws BException
{
System.out.println("ADD STUDENT METHOD");
System.out.println("AS: "+as);
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select * from student where roll_number=?");
ps.setInt(1,student.getRollNumber());
ResultSet resultSet=ps.executeQuery();
if(resultSet.next())
{
resultSet.close();
ps.close();
connection.close();
throw new BException("Roll number"+student.getRollNumber()+" exists");
}
resultSet.close();
ps.close();
ps=connection.prepareStatement("insert into student (roll_number,name,gender) values(?,?,?)");
ps.setInt(1,student.getRollNumber());
ps.setString(2,student.getName());
ps.setString(3,String.valueOf(student.getGender()));
ps.executeUpdate();
ps.close();
connection.close();
}
catch(Exception exception)
{
throw new BException(exception.getMessage());
}
}
@POST
@Path("/edit")
public void edit(Student student) throws BException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select * from student where roll_number=?");
ps.setInt(1,student.getRollNumber());
ResultSet resultSet=ps.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
ps.close();
connection.close();
throw new BException("Roll number"+student.getRollNumber()+" not exists");
}
ps=connection.prepareStatement("update student set name=?,gender=? where roll_number=?");
ps.setString(1,student.getName());
ps.setString(2,String.valueOf(student.getGender()));
ps.setInt(3,student.getRollNumber());
ps.executeUpdate();
ps.close();
connection.close();
}
catch(Exception exception)
{
throw new BException(exception.getMessage());
}
}
@GET
@Path("/delete")
public void delete(@RequestParameter(requestParameterKey="roll")int rollNumber) throws BException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement ps=connection.prepareStatement("select * from student where roll_number=?");
ps.setInt(1,rollNumber);
ResultSet resultSet=ps.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
ps.close();
connection.close();
throw new BException("Roll number "+rollNumber+" not exists");
}
ps=connection.prepareStatement("delete from student where roll_number=?");
ps.setInt(1,rollNumber);
ps.executeUpdate();
ps.close();
connection.close();
}
catch(Exception exception)
{
throw new BException("DELETE Exception: "+exception.getMessage());
}
//return "success";
}
@GET
@Path("/getby")
public Student getByStudentRoll(@RequestParameter(requestParameterKey="roll")int rollNumber) throws BException
{
Student s=null;
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select *from student where roll_number="+rollNumber);
while(resultSet.next())
{
s=new Student();
s.setRollNumber(resultSet.getInt("roll_number"));
s.setName(resultSet.getString("name"));
s.setGender(resultSet.getString("gender").charAt(0));
}
resultSet.close();
statement.close();
connection.close();
if(s==null) throw new BException("Roll number not exist");
}
catch(Exception exception)
{
throw new BException(exception.getMessage());
}
return s;
}
@GET
@Path("/getall")
public List<Student> getAll()  throws BException
{
List<Student> students=new LinkedList<>();
try
{
Connection connection=DAOConnection.getConnection();
Statement statement=connection.createStatement();
ResultSet resultSet=statement.executeQuery("select *from student order by roll_number");
while(resultSet.next())
{
Student s=new Student();
s.setRollNumber(resultSet.getInt("roll_number"));
s.setName(resultSet.getString("name"));
s.setGender(resultSet.getString("gender").charAt(0));
students.add(s);
}
resultSet.close();
statement.close();
connection.close();
}
catch(Exception exception)
{
throw new BException(exception.getMessage());
}
return students;
}
}