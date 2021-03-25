package bobby.test.secureaccess;
import com.thinking.machines.webrock.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
public class Security implements java.io.Serializable
{
public void check(SessionScope ss)
{
ss.setAttribute("UUID","YUQTWE-YWT-1523-17236"); 
}
}