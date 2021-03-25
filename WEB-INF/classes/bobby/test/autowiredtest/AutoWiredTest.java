package bobby.test.autowiredtest;
import com.thinking.machines.webrock.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
import bobby.pojo.*;
@GET
@Path("/autowire")
public class AutoWiredTest
{
@AutoWired(name="xyz")
private Student student;
public AutoWiredTest()
{
this.student=null;
}
public void setStudent(Student student)
{
this.student=student;
}
public Student getStudent()
{
return this.student;
}
@Path("/invoke")
public void invoke()
{
System.out.println("AutoWiredStduent:  "+ getStudent().name);
}
}