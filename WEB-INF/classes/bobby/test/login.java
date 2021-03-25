package bobby.test;
import com.thinking.machines.webrock.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@SecuredAccess(checkPost="bobby.test.Sess",gaurd="check")
@GET
@Path("/login")
public class login implements java.io.Serializable
{
@Forward("/school/edit")
@Path("/lgn")
public void loginStd()
{
System.out.println("Login request");
}
}