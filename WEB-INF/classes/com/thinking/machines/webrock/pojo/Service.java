package com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;
import java.util.*;
public class Service
{
private Class serviceClass;
private String path;
private Method service;
private String requestType;
private String forwardTo;
private boolean runOnStartup;
private int priority;
private boolean isInjectSessionScope;
private boolean isInjectApplicationScope;
private boolean isInjectApplicationDirectory;
private boolean isInjectRequestScope;
private List<AutoWiredWrapper> autoWiredObjectArrayList;
private List<InjectRequestParameterWrapper> injectRequestParameterObjectArrayList;
private Parameter parameterType[];
private String requestParameterKey[];
private SecuredAccessWrapper securedAccessWrapper;
public Service()
{
this.serviceClass=null;
this.path="";
this.service=null;
this.requestType="";
this.forwardTo="";
this.runOnStartup=false;
this.priority=0;
this.isInjectSessionScope=false;
this.isInjectApplicationScope=false;
this.isInjectApplicationDirectory=false;
this.isInjectRequestScope=false;
this.autoWiredObjectArrayList=null;
this.parameterType=null;
this.requestParameterKey=null;
this.injectRequestParameterObjectArrayList=null;
this.securedAccessWrapper=null;
}
public void setServiceClass(Class serviceClass)
{
this.serviceClass=serviceClass;
}
public Class getServiceClass()
{
return this.serviceClass;
}
public void setPath(String path)
{
this.path=path;
}
public String getPath()
{
return this.path;
}
public void setService(Method service)
{
this.service=service;
}
public Method getService()
{
return this.service;
}
public void setRequestType(String requestType)
{
this.requestType=requestType;
}
public String getRequestType()
{
return this.requestType;
}
public void setForwardTo(String forwardTo)
{
this.forwardTo=forwardTo;
}
public String getForwardTo()
{
return this.forwardTo;
}
public void setRunOnStartup(boolean runOnStartup)
{
this.runOnStartup=runOnStartup;
}
public boolean getRunOnStartup()
{
return this.runOnStartup;
}
public void setPriority(int priority)
{
this.priority=priority;
}
public int getPriority()
{
return this.priority;
}

public void setIsInjectSessionScope(boolean isInjectSessionScope)
{
this.isInjectSessionScope=isInjectSessionScope;
}
public boolean getIsInjectSessionScope()
{
return this.isInjectSessionScope;
}

public void setIsInjectApplicationScope(boolean isInjectApplicationScope)
{
this.isInjectApplicationScope=isInjectApplicationScope;
}
public boolean getIsInjectApplicationScope()
{
return this.isInjectApplicationScope;
}

public void setIsInjectApplicationDirectory(boolean isInjectApplicationDirectory)
{
this.isInjectApplicationDirectory=isInjectApplicationDirectory;
}
public boolean getIsInjectApplicationDirectory()
{
return this.isInjectApplicationDirectory;
}

public void setIsInjectRequestScope(boolean isInjectRequestScope)
{
this.isInjectRequestScope=isInjectRequestScope;
}
public boolean getIsInjectRequestScope()
{
return this.isInjectRequestScope;
}
public void setAutoWiredObjectArrayList(List<AutoWiredWrapper> autoWiredObjectArrayList)
{
this.autoWiredObjectArrayList=autoWiredObjectArrayList;
}
public List<AutoWiredWrapper> getAutoWiredObjectArrayList()
{
return this.autoWiredObjectArrayList;
}
public void setParameterType(Parameter parameterType[])
{
this.parameterType=parameterType;
System.out.println("POJO : "+this.parameterType+"Length :"+this.parameterType.length);
}
public Parameter[] getParameterType()
{
return this.parameterType;
}
public void setRequestParameterKey(String requestParameterKey[])
{
this.requestParameterKey=requestParameterKey;
}
public String[] getRequestParameterKey()
{
return this.requestParameterKey;
}

public void setInjectRequestParameterObjectArrayList(List<InjectRequestParameterWrapper> injectRequestParameterObjectArrayList)
{
this.injectRequestParameterObjectArrayList=injectRequestParameterObjectArrayList;
}
public List<InjectRequestParameterWrapper> getInjectRequestParameterObjectArrayList()
{
return this.injectRequestParameterObjectArrayList;
}

public void setSecuredAccessWrapper(SecuredAccessWrapper securedAccessWrapper)
{
this.securedAccessWrapper=securedAccessWrapper;
}
public SecuredAccessWrapper getSecuredAccessWrapper()
{
return this.securedAccessWrapper;
}

}