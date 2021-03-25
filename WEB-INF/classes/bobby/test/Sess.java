package bobby.test;
import com.thinking.machines.webrock.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
import java.util.*;
@InjectSessionScope
@Path("/sess")
public class Sess implements java.io.Serializable
{
public SessionScope sessionScope;
public void setSessionScope(SessionScope sessionScope)
{
this.sessionScope=sessionScope;
}
public void check()
{
System.out.println("Check UUID: "+sessionScope);
UUID uuid=UUID.randomUUID();
System.out.println("Check UUID random: "+uuid);
sessionScope.setAttribute("UUID",uuid);
}
}