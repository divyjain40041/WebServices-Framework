package com.thinking.machines.webrock.pojo;
import javax.servlet.http.*;
public class SessionScope
{
private HttpSession httpSession;
public SessionScope()
{
this.httpSession=null;
}
public void setHttpSession(HttpSession httpSession)
{
this.httpSession=httpSession;
}
public void setAttribute(String key,Object value)
{
this.httpSession.setAttribute(key,value);
}
public Object getAttribute(String key)
{
return this.httpSession.getAttribute(key);
}
}