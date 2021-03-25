package bobby.test.applicationdirectorytest;
import com.thinking.machines.webrock.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@InjectApplicationDirectory
@GET
@Path("/peon")
public class Peon
{
private ApplicationDirectory applicationDirectory;
public void setApplicationDirectory(ApplicationDirectory applicationDirectory)
{
this.applicationDirectory=applicationDirectory;
System.out.println("Bobby Application Directory:"+this.applicationDirectory);
}
public ApplicationDirectory getApplicationDirectory()
{
return this.applicationDirectory;
}

@OnStartup(priority=4)
public void add()
{
System.out.println("peon add method");
}
@Path("/get")
public int get(ApplicationDirectory applicationDirectory)
{
System.out.println("!@!@!!@!@!!@!@Application Directory test!@!!!@!@@! : "+applicationDirectory);

System.out.println("School get method");
return 10;
}
}