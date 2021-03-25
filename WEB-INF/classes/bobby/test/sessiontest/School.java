package bobby.test.sessiontest;
import com.thinking.machines.webrock.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@InjectSessionScope
@GET
@Path("/school")
public class School
{
SessionScope sessionScope;
public void setSessionScope(SessionScope sessionScope)
{
this.sessionScope=sessionScope;
System.out.println("School Session: "+this.sessionScope);
}
public SessionScope getSessionScope()
{
return this.sessionScope;
}

@OnStartup(priority=2)
public void add()
{
System.out.println("School add method");
}

@Path("/get")
public int get()
{
System.out.println("School get method");
System.out.println("Session scope: "+getSessionScope());
getSessionScope().setAttribute("uname","divy");  
return 10;
}

@Forward("/welcome.html")
@Path("/edit")
public void edit()
{
System.out.println("School edit method");
System.out.println("UUID: "+getSessionScope().getAttribute("UUID"));
}
}