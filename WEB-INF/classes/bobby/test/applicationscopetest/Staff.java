package bobby.test.applicationscopetest;
import com.thinking.machines.webrock.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@InjectApplicationScope
public class Staff
{
ApplicationScope applicationScope;
public void setApplicationScope(ApplicationScope applicationScope)
{
this.applicationScope=applicationScope;
System.out.println("Bobby Application scope:"+this.applicationScope);
}
public ApplicationScope getApplicationScope()
{
return this.applicationScope;
}

@OnStartup(priority=3)
public void add()
{
System.out.println("School add method");
}
public int get()
{
System.out.println("School get method");
return 10;
}
}