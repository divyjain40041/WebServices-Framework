package com.thinking.machines.webrock;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.webrock.pojo.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.exceptions.*;

import com.thinking.machines.webrock.model.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.lang.annotation.*;
import com.google.gson.*;
public class TMWebRock extends HttpServlet
{
SessionScope sessionScope;
ApplicationScope applicationScope;
RequestScope requestScope;
ApplicationDirectory applicationDirectory;


public void sendResponse(Object returnValue,boolean isException,Object exception,PrintWriter printWriter)
{
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setResponseData(returnValue);
serviceResponse.setIsException(isException);
serviceResponse.setException(exception);
Gson gson=new Gson();
String jsonString=gson.toJson(serviceResponse);
printWriter.print(jsonString);
printWriter.flush();  
printWriter.close();
}

public Object getValueFromRequest(String injectRequestParameterKey,String dataType,HttpServletRequest request)
{
if(dataType.equals("class java.lang.String"))
{
return request.getParameter(injectRequestParameterKey);
}
if(dataType.equals("int"))
{
return Integer.parseInt(request.getParameter(injectRequestParameterKey));
}
if(dataType.equals("float"))
{
return Float.parseFloat(request.getParameter(injectRequestParameterKey));
}
if(dataType.equals("double"))
{
return Double.parseDouble(request.getParameter(injectRequestParameterKey));
}
if(dataType.equals("long"))
{
return Long.parseLong(request.getParameter(injectRequestParameterKey));
}
if(dataType.equals("char"))
{
return request.getParameter(injectRequestParameterKey).charAt(0);
}
if(dataType.equals("boolean"))
{
return Boolean.parseBoolean(request.getParameter(injectRequestParameterKey));
}
if(dataType.equals("byte"))
{
return Byte.parseByte(request.getParameter(injectRequestParameterKey));
}
if(dataType.equals("short"))
{
return Short.parseShort(request.getParameter(injectRequestParameterKey));
}

return null;
}


public boolean isPrimitiveWrapperClass(String typeName)
{
if(typeName.equals("class java.lang.Integer")) return true;
if(typeName.equals("class java.lang.Float")) return true;
if(typeName.equals("class java.lang.Double")) return true;
if(typeName.equals("class java.lang.Long")) return true;
if(typeName.equals("class java.lang.Character")) return true;
if(typeName.equals("class java.lang.Boolean")) return true;
if(typeName.equals("class java.lang.Byte")) return true;
if(typeName.equals("class java.lang.Short")) return true;
if(typeName.equals("class java.lang.String")) return true;
return false;
}



public boolean isClientSideResource(String url)
{
if(url.indexOf(".")==-1) return false;
return true;
}

public Class getPrimitiveClass(String typeName)
{
if(typeName.equals("int")) 
{
Integer i=10;
return i.getClass();
}
if(typeName.equals("float")) 
{
Float i=10.1f;
return i.getClass();
}
if(typeName.equals("double")) 
{
Double i=10.2;
return i.getClass();
}
if(typeName.equals("long")) 
{
Long i=new Long(10);
return i.getClass();
}
if(typeName.equals("char")) 
{
Character i='A';
return i.getClass();
}
if(typeName.equals("boolean")) 
{
Boolean i=false;
return i.getClass();
}
if(typeName.equals("byte")) 
{
Byte i=10;
return i.getClass();
}
if(typeName.equals("short")) 
{
Short i=10;
return i.getClass();
}
return null;
}

public void setInjectSessionScope(Service service,Class serviceClass,Object serviceClassObj,HttpServletRequest request) throws ServiceException
{
if(service.getIsInjectSessionScope())
{
try 
{
Method setSessionMethod = serviceClass.getMethod("setSessionScope",SessionScope.class);

sessionScope.setHttpSession(request.getSession());
setSessionMethod.invoke(serviceClassObj,sessionScope);
}catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException at "+serviceClass.getName());
}
catch(IllegalAccessException illegalAccessException) {
throw new ServiceException("IllicitAccessException: at "+serviceClass.getName());
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}
}
}


public void setInjectRequestScope(Service service,Class serviceClass,Object serviceClassObj,HttpServletRequest request) throws ServiceException
{
if(service.getIsInjectRequestScope())
{
try 
{
Method setRequestMethod = serviceClass.getMethod("setRequestScope",RequestScope.class);
requestScope.setHttpServletRequest(request);
//Object classObject=serviceClass.newInstance();
setRequestMethod.invoke(serviceClassObj,requestScope);
}catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException at "+serviceClass.getName());
}
catch(IllegalAccessException illegalAccessException) {
throw new ServiceException("IllicitAccessException: at "+serviceClass.getName());
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}
}
}


public void setInjectApplicationScope(Service service,Class serviceClass,Object serviceClassObj,HttpServletRequest request) throws ServiceException
{
if(service.getIsInjectApplicationScope())
{
try 
{
Method setApplicationMethod = serviceClass.getMethod("setApplicationScope",ApplicationScope.class);
applicationScope.setServletContext(getServletContext());
//Object classObject=serviceClass.newInstance();
setApplicationMethod.invoke(serviceClassObj,applicationScope);
}catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException at "+serviceClass.getName());
}
catch(IllegalAccessException illegalAccessException) {
throw new ServiceException("IllicitAccessException: at "+serviceClass.getName());
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}
}
}



//include code for set inject ApplicationDirectory
public void setInjectApplicationDirectory(Service service,Class serviceClass,Object serviceClassObj,HttpServletRequest request) throws ServiceException
{
if(service.getIsInjectApplicationDirectory())
{
try 
{
Method setApplicationMethod = serviceClass.getMethod("setApplicationDirectory",ApplicationDirectory.class);
//Object classObject=serviceClass.newInstance();
setApplicationMethod.invoke(serviceClassObj,applicationDirectory);
}catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException at "+serviceClass.getName());
}
catch(IllegalAccessException illegalAccessException) {
throw new ServiceException("IllicitAccessException: at "+serviceClass.getName());
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}


}
}



public void setInjectRequestParameterField(Service service,Class serviceClass,Object serviceClassObj,HttpServletRequest request) throws ServiceException
{
List<InjectRequestParameterWrapper> injectRequestParameterObjectArrayList=service.getInjectRequestParameterObjectArrayList();
if(injectRequestParameterObjectArrayList!=null)
{
for(int i=0;i<injectRequestParameterObjectArrayList.size();i++)
{
Field field=injectRequestParameterObjectArrayList.get(i).getField();
String setterMethodName="set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
String injectRequestParameterKey=injectRequestParameterObjectArrayList.get(i).getInjectRequestParameterKey();
Object argument=getValueFromRequest(injectRequestParameterKey,field.getType().toString(),request);
Method setterMethod=null;
try
{
setterMethod = serviceClass.getMethod(setterMethodName,field.getType());
setterMethod.invoke(serviceClassObj,argument);
}
catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException at "+serviceClass.getName());
}
catch(IllegalAccessException illegalAccessException) {
throw new ServiceException("IllicitAccessException: at "+serviceClass.getName());
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}
if(argument!=null)
{
try
{
//setterMethod.invoke(serviceClassObj,argument);
}
catch(Exception exception)
{
//later we have to introduce code to send 500 server error
System.out.println("Service exception: "+exception.getMessage()); //I have to change this msg to appropriate msg later
}

}
else
{
System.out.println("Service exception"); //I have to change this msg to appropriate msg later
}
}
}
}



public void setAutoWired(Service service,Class serviceClass,Object serviceClassObj,HttpServletRequest request) throws ServiceException
{
List<AutoWiredWrapper> autoWiredObjectArrayList=service.getAutoWiredObjectArrayList();
if(autoWiredObjectArrayList!=null)
{
for(int i=0;i<autoWiredObjectArrayList.size();i++)
{
String name=autoWiredObjectArrayList.get(i).getName();
Field field=autoWiredObjectArrayList.get(i).getField();
String fieldName=field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
HttpSession httpSession=request.getSession();
HttpServletRequest httpServletRequest=request;
ServletContext servletContext=getServletContext();

if(httpSession!=null && httpSession.getAttribute(name)!=null) //checking in session scope
{
if(field.getType().isInstance(httpSession.getAttribute(name)))
{
try
{
Method setterMethod = serviceClass.getMethod("set"+fieldName,field.getType());
setterMethod.invoke(serviceClassObj,httpSession.getAttribute(name));
}catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException at "+serviceClass.getName());
}
catch(IllegalAccessException illegalAccessException) {
throw new ServiceException("IllicitAccessException: at "+serviceClass.getName());
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}
}
}


if(httpServletRequest!=null) //checking in Request scope
{
if(field.getType().isInstance(httpServletRequest.getAttribute(name)))
{
try
{
Method setterMethod = serviceClass.getMethod("set"+fieldName,field.getType());
setterMethod.invoke(serviceClassObj,httpServletRequest.getAttribute(name));
}catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException at "+serviceClass.getName());
}
catch(IllegalAccessException illegalAccessException) {
throw new ServiceException("IllicitAccessException: at "+serviceClass.getName());
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}
}
}


if(servletContext!=null) //checking in servlet context
{
if(field.getType().isInstance(servletContext.getAttribute(name)))
{
try
{
Method setterMethod = serviceClass.getMethod("set"+fieldName,field.getType());
setterMethod.invoke(serviceClassObj,servletContext.getAttribute(name));
}catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException at "+serviceClass.getName());
}
catch(IllegalAccessException illegalAccessException) {
throw new ServiceException("IllicitAccessException: at "+serviceClass.getName());
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}
}
}

}
}
}


Object getArgumentOfRespectiveParameter(Service service,String parameterKey,int index,HttpServletRequest request)
{

Parameter parameter=service.getParameterType()[index]; 
if(parameter.getParameterizedType().toString().equals("class com.thinking.machines.webrock.pojo.ApplicationScope"))
{
ApplicationScope applicationScope=new ApplicationScope();
applicationScope.setServletContext(getServletContext());
return applicationScope;
}
if(parameter.getParameterizedType().toString().equals("class com.thinking.machines.webrock.pojo.RequestScope"))
{
requestScope.setHttpServletRequest(request);
return requestScope;
}
if(parameter.getParameterizedType().toString().equals("class com.thinking.machines.webrock.pojo.SessionScope"))
{
sessionScope.setHttpSession(request.getSession());
return sessionScope;
}
if(parameter.getParameterizedType().toString().equals("class com.thinking.machines.webrock.pojo.ApplicationDirectory"))
{
return applicationDirectory;
}

if(request.getContentType()!=null)
{
if(request.getContentType().equals("application/json")||request.getContentType().equals("application/x-www-form-urlencoded; charset=UTF-8"))
{
parameter=service.getParameterType()[0]; 
try
{
BufferedReader bufferReader=request.getReader();
StringBuffer stringBuffer=new StringBuffer();
String d;
while(true)
{
d=bufferReader.readLine();
if(d==null) break;
stringBuffer.append(d);
}
String rawData=stringBuffer.toString();
Gson gson=new Gson();
Object jsonObject=gson.fromJson(rawData,parameter.getParameterizedType());	
return jsonObject;
}
catch(IOException ioException)
{
//do nothing
}
}
}
else
{
if(parameter.getParameterizedType().toString().equals("class java.lang.String"))
{
return request.getParameter(parameterKey);
}
if(parameter.getParameterizedType().toString().equals("int"))
{
return Integer.parseInt(request.getParameter(parameterKey));
}
if(parameter.getParameterizedType().toString().equals("float"))
{
return Float.parseFloat(request.getParameter(parameterKey));
}
if(parameter.getParameterizedType().toString().equals("double"))
{
return Double.parseDouble(request.getParameter(parameterKey));
}
if(parameter.getParameterizedType().toString().equals("long"))
{
return Long.parseLong(request.getParameter(parameterKey));
}
if(parameter.getParameterizedType().toString().equals("char"))
{
return request.getParameter(parameterKey).charAt(0);
}
if(parameter.getParameterizedType().toString().equals("boolean"))
{
return Boolean.parseBoolean(request.getParameter(parameterKey));
}
if(parameter.getParameterizedType().toString().equals("byte"))
{
return Byte.parseByte(request.getParameter(parameterKey));
}
if(parameter.getParameterizedType().toString().equals("short"))
{
return Short.parseShort(request.getParameter(parameterKey));
}
}
return null;
}



public void requestProcesser(HttpServletRequest request,HttpServletResponse response,String methodType)
{
try
{
String url=request.getRequestURI().toString();
int index=0;
for(int i=0;i<3;i++)
{
index=url.indexOf("/",1);
}
index++;
url=url.substring(url.indexOf("/",index));
WebRockModel webRockModel=(WebRockModel)getServletContext().getAttribute("WEB_ROCK_MODEL");
Service service=(Service)webRockModel.get(url);

if(service==null)
{
response.sendError(HttpServletResponse.SC_NOT_FOUND);
throw new ServiceException("InvalidURLRequestException");
}
String requestType=service.getRequestType();
sessionScope=new SessionScope();
requestScope=new RequestScope();
applicationScope=new ApplicationScope();
File file=new File(getServletContext().getRealPath(""));
applicationDirectory=new ApplicationDirectory(file);








if(requestType.toUpperCase().equals(methodType.toUpperCase())==false)
{
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
throw new ServiceException("IllegalMethodException");
}
else
{
Class serviceClass=service.getServiceClass();
Method method=service.getService();
Object serviceClassObj=serviceClass.newInstance();
String forwardTo=service.getForwardTo();
SecuredAccessWrapper securedAccessWrapper=service.getSecuredAccessWrapper();

if(securedAccessWrapper!=null)
{
try
{
String checkPost=securedAccessWrapper.getCheckPost();
String gaurd=securedAccessWrapper.getGaurd();

Class checkPostClass=Class.forName(checkPost);
Object checkPostClassObj=checkPostClass.newInstance();
Method checkPostMethods[]=checkPostClass.getDeclaredMethods();
Method gaurdMethod=null;
for(Method declaredMethod:checkPostMethods)
{

if(declaredMethod.getName().equals(gaurd)) 
{
gaurdMethod=declaredMethod;
}
}
if(gaurdMethod==null) throw new ServiceException("GaurdNotFoundException");

if(checkPostClass.isAnnotationPresent(InjectApplicationScope.class))
{
ApplicationScope applicationScope=new ApplicationScope();
applicationScope.setServletContext(getServletContext());
Method setterMethod = checkPostClass.getMethod("setApplicationScope",ApplicationScope.class);
setterMethod.invoke(checkPostClassObj,applicationScope);
}else
if(checkPostClass.isAnnotationPresent(InjectSessionScope.class))
{
SessionScope sessionScope=new SessionScope();
sessionScope.setHttpSession(request.getSession());
Method setterMethod = checkPostClass.getMethod("setSessionScope",SessionScope.class);
setterMethod.invoke(checkPostClassObj,sessionScope);
}else
if(checkPostClass.isAnnotationPresent(InjectRequestScope.class))
{
RequestScope requestScope=new RequestScope();
requestScope.setHttpServletRequest(request);
Method setterMethod = checkPostClass.getMethod("setRequestScope",RequestScope.class);
setterMethod.invoke(checkPostClassObj,requestScope);
}else
if(checkPostClass.isAnnotationPresent(InjectApplicationDirectory.class))
{
ApplicationDirectory applicationDirectory=new ApplicationDirectory(file);
Method setterMethod = checkPostClass.getMethod("setApplicationDirectory",ApplicationDirectory.class);
setterMethod.invoke(checkPostClassObj,applicationDirectory);
}




Parameter gaurdMethodParameters[]=gaurdMethod.getParameters();
Object checkPostClassArguments[]=new Object[gaurdMethodParameters.length];
int i=0;
for(Parameter parameter:gaurdMethodParameters)
{

if(parameter.getParameterizedType().toString().equals("class com.thinking.machines.webrock.pojo.ApplicationScope"))
{
ApplicationScope applicationScope=new ApplicationScope();
applicationScope.setServletContext(getServletContext());
checkPostClassArguments[i]=applicationScope;
}else
if(parameter.getParameterizedType().toString().equals("class com.thinking.machines.webrock.pojo.RequestScope"))
{
requestScope.setHttpServletRequest(request);
checkPostClassArguments[i]=requestScope;
}else
if(parameter.getParameterizedType().toString().equals("class com.thinking.machines.webrock.pojo.SessionScope"))
{
sessionScope.setHttpSession(request.getSession());
checkPostClassArguments[i]=sessionScope;
}else
if(parameter.getParameterizedType().toString().equals("class com.thinking.machines.webrock.pojo.ApplicationDirectory"))
{
ApplicationDirectory applicationDirectory=new ApplicationDirectory(file);
checkPostClassArguments[i]=applicationDirectory;
}
else
{
throw new ServiceException("Service Exception: IllegalGaurdParameterException");
}
i++;
}
if(checkPostClassArguments!=null) gaurdMethod.invoke(checkPostClassObj,checkPostClassArguments);
}
catch(InvocationTargetException invocationTargetException)
{
// do nothing
}
catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("ServiceException : IllicitAccessException"); 
// abhi tak keval Exception lagaya tha 
}

}


//Giving SessionScope object to user
setInjectSessionScope(service,serviceClass,serviceClassObj,request);

//Giving RequestScope object to user
setInjectRequestScope(service,serviceClass,serviceClassObj,request);


//Giving ApplicationScope object to user
setInjectApplicationScope(service,serviceClass,serviceClassObj,request);


//Giving ApplicationDirectory object to user
setInjectApplicationDirectory(service,serviceClass,serviceClassObj,request);


//setting AutoWired
setAutoWired(service,serviceClass,serviceClassObj,request);


setInjectRequestParameterField(service,serviceClass,serviceClassObj,request);


//get argument to invoke function
Parameter ptmp[]=service.getParameterType();
String requestParameterKey[]=service.getRequestParameterKey();
Object argumentArr[]=new Object[requestParameterKey.length];
if(requestParameterKey!=null)
{
int i=0;
for(String parameterKey:requestParameterKey)
{
argumentArr[i]=getArgumentOfRespectiveParameter(service,parameterKey,i,request);
i++;
}
}




Object returnValue=null;
try
{
if(method!=null && argumentArr==null) returnValue=method.invoke(serviceClassObj);
else  returnValue=method.invoke(serviceClassObj,argumentArr);
if(returnValue!=null && forwardTo.length()==0)
{
if(isPrimitiveWrapperClass(returnValue.getClass().toString())) 
{
sendResponse(returnValue,false,null,response.getWriter());
}
else
{
Gson gson=new Gson();
String returnValueString=gson.toJson(returnValue);
sendResponse(returnValueString,false,null,response.getWriter());
}
}
}catch(InvocationTargetException exception)
{
PrintWriter pw=response.getWriter();
Gson gson=new Gson();
String exceptionString=gson.toJson(exception.getTargetException());
sendResponse(null,true,exceptionString,response.getWriter());
}




while(forwardTo.length()!=0)
{
if(isClientSideResource(forwardTo)) // if client side resource
{
RequestDispatcher requestDispatcher=request.getRequestDispatcher("/"+forwardTo);
requestDispatcher.forward(request,response);
return;
} // if client side resource then forward using request dispatcher work ends here
else// if server side resource 
{
Service forwardToService=(Service)webRockModel.get(forwardTo);
Class forwardToClass=forwardToService.getServiceClass();
Method forwardToMethod=forwardToService.getService();
forwardTo=forwardToService.getForwardTo();
Object forwardToServiceClassObj=forwardToClass.newInstance();
setInjectSessionScope(forwardToService,forwardToClass,forwardToServiceClassObj,request);
//Giving RequestScope object to user
setInjectRequestScope(forwardToService,forwardToClass,forwardToServiceClassObj,request);
//Giving ApplicationScope object to user
setInjectApplicationScope(forwardToService,forwardToClass,forwardToServiceClassObj,request);
//setting AutoWired
setAutoWired(forwardToService,forwardToClass,forwardToServiceClassObj,request);
//setting InjectRequestParameterField



setInjectRequestParameterField(forwardToService,forwardToClass,forwardToServiceClassObj,request);



if(returnValue!=null)
{
if(forwardToService.getParameterType().length==1)
{
Parameter parameter=forwardToService.getParameterType()[0];
Class primitiveDataTypeClass=getPrimitiveClass(parameter.getParameterizedType().toString());
if(primitiveDataTypeClass!=null && primitiveDataTypeClass.isInstance(returnValue))
{
try
{
returnValue=forwardToMethod.invoke(forwardToServiceClassObj,returnValue);
}catch(InvocationTargetException exception)
{
Gson gson=new Gson();
String exceptionString=gson.toJson(exception.getTargetException());
sendResponse(null,true,exceptionString,response.getWriter());
}
}
else
{
returnValue=forwardToMethod.invoke(forwardToServiceClassObj,returnValue);
}
}
else
{
throw new ServiceException("InvalidNumberOfParameterException"); // if function to which we have to invoke have 0 or more than one parameter
}
}
else
{
if(forwardToService.getParameterType().length==0) 
{
try
{
returnValue=forwardToMethod.invoke(forwardToServiceClassObj);
}catch(InvocationTargetException exception)
{
Gson gson=new Gson();
String exceptionString=gson.toJson(exception.getTargetException());
sendResponse(null,true,exceptionString,response.getWriter());
}
}
}
}// if server side resources then that work ends here
if(returnValue!=null && forwardTo.length()==0)
{
PrintWriter pw=response.getWriter();
if(isPrimitiveWrapperClass(returnValue.getClass().toString())) pw.print(returnValue);
else
{
Gson gson=new Gson();
String returnValueString=gson.toJson(returnValue);
sendResponse(returnValueString,false,null,response.getWriter());
}
}



} //while loop end here
}// method allow else braces ends here
} //try block ends here
catch(ServiceException serviceException)
{
serviceException.printStackTrace();
}
catch(Exception exception)
{
try
{
Gson gson=new Gson();
String exceptionString=gson.toJson(exception.getCause());
sendResponse(null,true,exceptionString,response.getWriter());
}
catch(IOException ioException)
{
//do nothing
}
}
}


public void doGet(HttpServletRequest request,HttpServletResponse response)
{
requestProcesser(request,response,"GET");
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
requestProcesser(request,response,"POST");
}
}