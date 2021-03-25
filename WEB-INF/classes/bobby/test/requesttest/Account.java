package bobby.test.requesttest;
import com.thinking.machines.webrock.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@InjectRequestScope
@GET
@Path("/account")
public class Account
{
RequestScope requestScope;
public void setRequestScope(RequestScope requestScope)
{
this.requestScope=requestScope;
}
public RequestScope getRequestScope()
{
return this.requestScope;
}

@Forward("/forwardTest.html")
@Path("/add")
public void add()
{
System.out.println("School add method");
}

@Path("/get")
public int get()
{
System.out.println("Account get method");
System.out.println("Request scope: "+getRequestScope());
return 10;
}
}