package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ServeJSFile extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try
{
String fileName=request.getParameter("js_filename");
ServletContext servletContext=getServletContext();
String packageStartsWith=servletContext.getInitParameter("SERVICE_PACKAGE_PREFIX");
String currentDirectory = System.getProperty("user.dir");
String maindirpath = currentDirectory.substring(0,currentDirectory.indexOf("classes")+7+1)+packageStartsWith; 
String jsPath = currentDirectory.substring(0,maindirpath.indexOf("WEB-INF")+7)+"\\jsFiles"+"\\js"+"\\"+fileName;

File file=new File(jsPath);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
PrintWriter printWriter=response.getWriter();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
printWriter.print(randomAccessFile.readLine());
}
printWriter.close();
}
catch(Exception exception)
{
System.out.println("ServeJSFileException: "+exception.getMessage());
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
//method not allowed
}
}