package com.thinking.machines.webrock.pojo;
import javax.servlet.*;
public class ApplicationScope
{
private ServletContext servletContext;
public ApplicationScope()
{
this.servletContext=null;
}
public void setServletContext(ServletContext servletContext)
{
this.servletContext=servletContext;
}
public void setAttribute(String key,Object value) 
{
this.servletContext.setAttribute(key,value);
}
public Object getAttribute(String key) 
{
return this.servletContext.getAttribute(key);
}
}