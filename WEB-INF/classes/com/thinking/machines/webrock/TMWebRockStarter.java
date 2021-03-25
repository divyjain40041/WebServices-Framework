package com.thinking.machines.webrock;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.model.*;
import com.thinking.machines.webrock.exceptions.*;
import com.thinking.machines.webrock.pojo.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.jar.*;
import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
public class TMWebRockStarter extends HttpServlet
{
Stack<File> fileStack;
List<Service> methodPriorityArrayList;
Set<String> pojoClassSet;
Map<Class,Set<Method>> serviceClassMap;


public String getDefaultValue(String typeName)
{
if(typeName.equals("int")) return "0";
if(typeName.equals("float")) return "0.0";
if(typeName.equals("double")) return "0.0";
if(typeName.equals("long")) return "0";
if(typeName.equals("char")) return "'\\0'";
if(typeName.equals("boolean")) return "false";
if(typeName.equals("byte")) return "0";
if(typeName.equals("short")) return "0";
if(typeName.equals("class java.lang.String")) return "''";
return "null";
}




private void createJSFile()
{
ServletContext servletContext=getServletContext();
String baseURL=servletContext.getInitParameter("BASE_URL");
if(baseURL==null || baseURL.length()==0) throw new ServiceException("BaseURLNotFoundException");
String jsFileName=servletContext.getInitParameter("JS_FILE");
String packageStartsWith=servletContext.getInitParameter("SERVICE_PACKAGE_PREFIX");
String currentDirectory = System.getProperty("user.dir");
String maindirpath = currentDirectory.substring(0,currentDirectory.indexOf("classes")+7+1)+packageStartsWith; 
String jsPath = currentDirectory.substring(0,maindirpath.indexOf("WEB-INF")+7)+"\\jsFiles"+"\\js";
try
{
File file=new File(jsPath+"\\"+jsFileName+".js");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");

//generating pojo class starts here
String pojoClassString="";
for(String pojoName: pojoClassSet)
{
Class pojoClass=Class.forName(pojoName.substring(6));
pojoClassString+="class "+pojoClass.getSimpleName()+"\r\n"+"{\r\n"+"constructor()\r\n"+"{\r\n";
for(Field field:pojoClass.getDeclaredFields())
{
pojoClassString+="this."+field.getName()+"="+getDefaultValue(String.valueOf(field.getType()))+";\r\n";
}
pojoClassString+="}\r\n";
pojoClassString+="}\r\n";
}
randomAccessFile.writeBytes(pojoClassString);

String serviceClassString="";
for (Map.Entry<Class,Set<Method>> entry : serviceClassMap.entrySet())  
{
Class serviceClass=entry.getKey();
serviceClassString+="class "+serviceClass.getSimpleName()+"\r\n"+"{\r\n";
for(Method method:entry.getValue())
{
serviceClassString+=method.getName()+"(";
Parameter parameters[]=method.getParameters();
for(int i=0;i<parameters.length;i++)
{
if(dataTypeAllow(parameters[i].getParameterizedType().toString())==true && particularDataTypeAllow(parameters[i].getParameterizedType().toString())==false)
{
if(i==0) 
{
serviceClassString+=parameters[i].getName();
}
else   
{
serviceClassString+=","+parameters[i].getName();
}

}
else
{
if(dataTypeAllow(parameters[i].getParameterizedType().toString())==false && particularDataTypeAllow(parameters[i].getParameterizedType().toString())==false)
{
serviceClassString+=parameters[i].getName();
}
}
}
serviceClassString+=")\r\n";
serviceClassString+="{\r\n";
serviceClassString+="var promise=new Promise(function(resolve,reject){\r\n";
serviceClassString+="$.ajax({\r\n";
if(method.isAnnotationPresent(POST.class))
{
serviceClassString+="type:'POST',\r\n";
}else
if(method.isAnnotationPresent(GET.class))
{
serviceClassString+="type:'GET',\r\n";
}else
if(serviceClass.isAnnotationPresent(POST.class))
{
serviceClassString+="type:'POST',\r\n";
}else
if(serviceClass.isAnnotationPresent(GET.class))
{
serviceClassString+="type:'GET',\r\n";
}else
{
serviceClassString+="type:'GET',\r\n";
}
Annotation methodAnnotation=method.getAnnotation(Path.class);
Path methodPath=(Path)methodAnnotation;
Annotation classAnnotation=serviceClass.getAnnotation(Path.class);
Path classPath=(Path)classAnnotation;
serviceClassString+="url:'"+baseURL.substring(1)+classPath.value()+methodPath.value()+"',\r\n";



int complex=0;
for(int i=0;i<parameters.length;i++)
{
if(dataTypeAllow(parameters[i].getParameterizedType().toString())==false && particularDataTypeAllow(parameters[i].getParameterizedType().toString())==false)
{
serviceClassString+="data:JSON.stringify("+parameters[i].getName()+"),\r\n";
complex++;
if(complex>1) throw new ServiceException("Service Exception: ExcessParameterTypeException at "+serviceClass.getName()+" "+method.getName()+"  "+parameters[i].getParameterizedType());
}
}

boolean flag=false;
if(complex==0) 
{
for(int i=0;i<parameters.length;i++)
{
if(dataTypeAllow(parameters[i].getParameterizedType().toString())==true)
{
if(parameters[i].isAnnotationPresent(RequestParameter.class)==true)
{
Annotation requestParameterAnnotation=parameters[i].getAnnotation(RequestParameter.class);
RequestParameter requestParameterPath=(RequestParameter)requestParameterAnnotation;
if(flag==false) 
{
serviceClassString+="data:{";
flag=true;
}
if(i==0) 
{
serviceClassString+=requestParameterPath.requestParameterKey()+":"+parameters[i].getName();
}
else 
{
serviceClassString+=","+requestParameterPath.requestParameterKey()+":"+parameters[i].getName();
}
}

}
}
if(flag==true) serviceClassString+="},\r\n";

//later we apply condition related to patameter type and then we throw exception
}



serviceClassString+="success:function(data){\r\n"+"resolve(data);\r\n"+"},\r\n";
serviceClassString+="failure:function(data){\r\n"+"reject(data);\r\n"+"}\r\n";
serviceClassString+="});\r\n";
serviceClassString+="});\r\n";
serviceClassString+="return promise;\r\n";
serviceClassString+="}\r\n";
}
serviceClassString+="}\r\n";
}

randomAccessFile.writeBytes(serviceClassString);
randomAccessFile.close();
}
catch(Exception exception)
{
exception.printStackTrace();
}
}





private void RecursivePrint(File[] arr,int index,int level)  
{
if(index == arr.length) return; 
if(arr[index].isFile()) 
{
if(arr[index].getName().substring(arr[index].getName().length()-6).equals(".class")) this.fileStack.add(arr[index]);
}
else if(arr[index].isDirectory()) 
{ 
RecursivePrint(arr[index].listFiles(), 0, level + 1); 
} 
RecursivePrint(arr,++index, level); 
} 

public boolean dataTypeAllow(String typeName)
{
if(typeName.equals("int")) return true;
if(typeName.equals("float")) return true;
if(typeName.equals("double")) return true;
if(typeName.equals("long")) return true;
if(typeName.equals("char")) return true;
if(typeName.equals("boolean")) return true;
if(typeName.equals("byte")) return true;
if(typeName.equals("short")) return true;
if(typeName.equals("class java.lang.String")) return true;
return false;
}

public boolean particularDataTypeAllow(String typeName)
{
if(typeName.equals("class com.thinking.machines.webrock.pojo.ApplicationScope")) return true;
if(typeName.equals("class com.thinking.machines.webrock.pojo.RequestScope")) return true;
if(typeName.equals("class com.thinking.machines.webrock.pojo.SessionScope")) return true;
if(typeName.equals("class com.thinking.machines.webrock.pojo.ApplicationDirectory")) return true;
return false;
}

public void init() throws ServletException
{
try
{
ServletContext servletContext=getServletContext();
String packageStartsWith=servletContext.getInitParameter("SERVICE_PACKAGE_PREFIX");

if(packageStartsWith.length==null || packageStartsWith.length()==0) throw new ServiceException("ServicePackagePrefixNotFoundException");

pojoClassSet=new HashSet<>();
serviceClassMap=new HashMap<>();


String currentDirectory = System.getProperty("user.dir");
String maindirpath = currentDirectory.substring(0,currentDirectory.indexOf("classes")+7+1)+packageStartsWith; 
this.fileStack=new Stack<>();
File arr[]=null;      
File maindir = new File(maindirpath);   
if(maindir.exists() && maindir.isDirectory()) 
{ 
arr= maindir.listFiles(); 
RecursivePrint(arr,0,0);
}
else
{
throw new ServiceException("ServicePackagePrefixException");
}




WebRockModel webRockModel=new WebRockModel();
methodPriorityArrayList=new ArrayList<>();
for(int i=0;i<this.fileStack.size();i++)
{
String absolutePath=this.fileStack.get(i).getAbsolutePath();
String className=absolutePath.substring(absolutePath.indexOf("classes")+7+1).replace("\\",".").replace(".class","").replace("/",".");
Class classObj=Class.forName(className);
Set<Method> serviceClassMethodsList=new HashSet<>(); 




if((classObj.isAnnotationPresent(GET.class) || classObj.isAnnotationPresent(POST.class) ) && classObj.isAnnotationPresent(Path.class)==false) throw new ServiceException("LeaningOnPathException at "+classObj.getName());
if(classObj.isAnnotationPresent(SecuredAccess.class) && classObj.isAnnotationPresent(Path.class)==false) throw new ServiceException("LeaningOnPathException at "+classObj.getName());
if(classObj.isAnnotationPresent(Path.class))  //checking for Path annotation on class
{
Annotation annotation=classObj.getAnnotation(Path.class);
Path path=(Path)annotation;

/*Checking for annotations over method starts from here*/
for(Method method:classObj.getDeclaredMethods())
{
if((method.isAnnotationPresent(GET.class) || method.isAnnotationPresent(POST.class) ) && method.isAnnotationPresent(Path.class)==false) throw new ServiceException("LeaningOnPathException at "+classObj.getName()+" "+method.getName());
//if(method.isAnnotationPresent(GET.class)==false && method.isAnnotationPresent(POST.class)==false  && method.isAnnotationPresent(Forward.class)) throw new ServiceException("LeaningOnRequestTypeException at "+classObj.getName()+" "+method.getName());
if(method.isAnnotationPresent(Path.class)==false  && method.isAnnotationPresent(Forward.class)) throw new ServiceException("LeaningOnPathException at "+classObj.getName()+" "+method.getName());
if(method.isAnnotationPresent(Path.class)==false  && method.isAnnotationPresent(SecuredAccess.class)) throw new ServiceException("LeaningOnPathException at "+classObj.getName()+" "+method.getName());


if(method.isAnnotationPresent(Path.class))  //checking for Path annotation on method
{

serviceClassMethodsList.add(method); //adding method to serviceClass method List


Annotation methodAnnotation=method.getAnnotation(Path.class);
Path methodPath=(Path)methodAnnotation;
Service service=new Service();
service.setServiceClass(classObj);
service.setPath(path.value()+methodPath.value()); //remove later


if(method.isAnnotationPresent(GET.class) && method.isAnnotationPresent(POST.class)) throw new ServiceException("RequestTypeConflictException in "+classObj.getName()+" "+method.getName());



if(classObj.isAnnotationPresent(GET.class) && method.isAnnotationPresent(GET.class)==false && method.isAnnotationPresent(POST.class)==false)   //checking for GET annotation on class
{
service.setRequestType("GET");  //checking for GET annotation on class
}else
if(classObj.isAnnotationPresent(POST.class) && method.isAnnotationPresent(GET.class)==false && method.isAnnotationPresent(POST.class)==false)  //checking for POST annotation on class
{
service.setRequestType("POST");
}else
if(method.isAnnotationPresent(GET.class)) //checking for GET annotation on method
{
service.setRequestType("GET");
}else
if(method.isAnnotationPresent(POST.class)) //checking for POST annotation on method
{
service.setRequestType("POST");
}
else
{
service.setRequestType("GET");
}


//checking for Forward annotation
if(method.isAnnotationPresent(Forward.class) && method.isAnnotationPresent(OnStartup.class)==true) throw new ServiceException("Service Exception: OnStartupException");
if(method.isAnnotationPresent(Forward.class) && method.isAnnotationPresent(OnStartup.class)==false)
{
Annotation forwardAnnotaion=method.getAnnotation(Forward.class);
Forward forward=(Forward)forwardAnnotaion;
service.setForwardTo(forward.value());
}


//checking for InjectSessionScope annotation
if(method.isAnnotationPresent(InjectSessionScope.class) && method.isAnnotationPresent(OnStartup.class)==true) throw new ServiceException("Service Exception: OnStartupException");
if(classObj.isAnnotationPresent(InjectSessionScope.class) && method.isAnnotationPresent(OnStartup.class)==false)
{
Annotation injectSessionScopeAnnotaion=method.getAnnotation(InjectSessionScope.class);
InjectSessionScope injectSessionScope=(InjectSessionScope)injectSessionScopeAnnotaion;
service.setIsInjectSessionScope(true);
}

//checking for InjectRequestScope annotation
if(method.isAnnotationPresent(InjectRequestScope.class) && method.isAnnotationPresent(OnStartup.class)==true) throw new ServiceException("Service Exception: OnStartupException");
if(classObj.isAnnotationPresent(InjectRequestScope.class) && method.isAnnotationPresent(OnStartup.class)==false)
{
Annotation injectRequestScopeAnnotaion=method.getAnnotation(InjectRequestScope.class);
InjectRequestScope injectRequestScope=(InjectRequestScope)injectRequestScopeAnnotaion;
service.setIsInjectRequestScope(true);
}

//checking for InjectApplicationScope annotation
if(method.isAnnotationPresent(InjectApplicationScope.class) && method.isAnnotationPresent(OnStartup.class)==true) throw new ServiceException("Service Exception: OnStartupException");
if(classObj.isAnnotationPresent(InjectApplicationScope.class) && method.isAnnotationPresent(OnStartup.class)==false)
{
Annotation injectApplicationScopeAnnotaion=method.getAnnotation(InjectApplicationScope.class);
InjectApplicationScope injectApplicationScope=(InjectApplicationScope)injectApplicationScopeAnnotaion;
service.setIsInjectApplicationScope(true);
//Method method = classObj.getMethod("setApplicationScope",ApplicationScope.class);
//ApplicationScope applicationScope=new ApplicationScope();
//applicationScope.setServletContext(getServletContext());
//method.invoke(classObject,applicationScope);
}

//checking for InjectApplicationDirectory annotation
if(method.isAnnotationPresent(InjectApplicationDirectory.class) && method.isAnnotationPresent(OnStartup.class)==true) throw new ServiceException("Service Exception: OnStartupException");
if(classObj.isAnnotationPresent(InjectApplicationDirectory.class) && method.isAnnotationPresent(OnStartup.class)==false)
{
Annotation injectApplicationDirectoryAnnotaion=method.getAnnotation(InjectApplicationDirectory.class);
InjectApplicationDirectory injectApplicationDirectory=(InjectApplicationDirectory)injectApplicationDirectoryAnnotaion;
service.setIsInjectApplicationDirectory(true);
}


List<AutoWiredWrapper> autoWiredList=new ArrayList<>();
List<InjectRequestParameterWrapper> injectRequestParameterList=new ArrayList<>();
for(Field field:classObj.getDeclaredFields())
{
if(field.isAnnotationPresent(AutoWired.class)) //checking for AutoWired annotation
{
Annotation fieldAnnotation=field.getAnnotation(AutoWired.class);
AutoWired autoWired=(AutoWired)fieldAnnotation;

AutoWiredWrapper autoWiredWrapper=new AutoWiredWrapper();
autoWiredWrapper.setName(autoWired.name());
autoWiredWrapper.setField(field);
autoWiredList.add(autoWiredWrapper);
}


if(field.isAnnotationPresent(InjectRequestParameter.class) && dataTypeAllow(String.valueOf(field.getType()))==false) throw new ServiceException("IllegalInjectRequestParameterException at "+field.getType()+" "+field.getName()+" in "+classObj.getName());
if(field.isAnnotationPresent(InjectRequestParameter.class)) //checking for InjectRequestParameter annotation
{
Annotation injectRequestParameterAnnotation=field.getAnnotation(InjectRequestParameter.class);
InjectRequestParameter injectRequestParameter=(InjectRequestParameter)injectRequestParameterAnnotation;
InjectRequestParameterWrapper injectRequestParameterWrapper=new InjectRequestParameterWrapper();
injectRequestParameterWrapper.setInjectRequestParameterKey(injectRequestParameter.injectRequestParameterKey());
injectRequestParameterWrapper.setField(field);
injectRequestParameterList.add(injectRequestParameterWrapper);
}

}
if(autoWiredList.size()>0) service.setAutoWiredObjectArrayList(autoWiredList);
if(injectRequestParameterList.size()>0) service.setInjectRequestParameterObjectArrayList(injectRequestParameterList);
/*Checking for annotation over method ends from here*/


/*Checking for request parameter key*/
boolean flag=false;
String requestParameterKeyArr[]=new String[method.getParameters().length];
Parameter parameters[]=method.getParameters();

/*Checking for parameters type*/
service.setParameterType(parameters);
int complexData=0;
for(Parameter parameter:parameters)
{
if(dataTypeAllow(parameter.getParameterizedType().toString())==false && particularDataTypeAllow(parameter.getParameterizedType().toString())==false && parameters.length==1)
{
pojoClassSet.add(String.valueOf(parameter.getParameterizedType()));
if(complexData>1) throw new ServiceException("Service Exception: ExcessParameterTypeException at "+classObj.getName()+" "+method.getName()+"  "+parameter.getParameterizedType());
complexData++;
}

}





int k=0;
flag=false;
complexData=0;
for(Parameter parameter:parameters)
{
if(dataTypeAllow(parameter.getParameterizedType().toString())==false && particularDataTypeAllow(parameter.getParameterizedType().toString())==false)
{
Annotation requestParameterAnnotation=parameter.getAnnotation(RequestParameter.class);
RequestParameter requestParameter=(RequestParameter)requestParameterAnnotation;
complexData++;
if(complexData>1) throw new ServiceException("Service Exception: ExcessParameterTypeException at "+classObj.getName()+" "+method.getName()+"  "+parameter.getParameterizedType());
else requestParameterKeyArr[k]=parameter.getName();	
}
else
{
if(dataTypeAllow(parameter.getParameterizedType().toString()) && parameter.isAnnotationPresent(RequestParameter.class) &&complexData==0)  //may be later we apply validation that if complex data type and other than String and all scope so we will not add in class
{
Annotation requestParameterAnnotation=parameter.getAnnotation(RequestParameter.class);
RequestParameter requestParameter=(RequestParameter)requestParameterAnnotation;
requestParameterKeyArr[k]=requestParameter.requestParameterKey();
}
else
{
if(dataTypeAllow(parameter.getParameterizedType().toString()) && parameter.isAnnotationPresent(RequestParameter.class)==false) 
{
flag=true;
break;
}
else
{
if(particularDataTypeAllow(parameter.getParameterizedType().toString())&&(complexData==0||complexData==1)) requestParameterKeyArr[k]=parameter.getName();
else
{
 throw new ServiceException("Service Exception: ExcessParameterTypeException at "+classObj.getName()+" "+method.getName()+"  "+parameter.getParameterizedType());
}
}
}
}
k++;
}
//if(flag==true) continue;
service.setRequestParameterKey(requestParameterKeyArr);


//checking for Secured Access



if(method.isAnnotationPresent(SecuredAccess.class))
{
Annotation securedAccessAnnotation=method.getAnnotation(SecuredAccess.class);
SecuredAccess securedAccess=(SecuredAccess)securedAccessAnnotation;
String checkPost=securedAccess.checkPost();
String gaurd=securedAccess.gaurd();
SecuredAccessWrapper securedAccessWrapper=new SecuredAccessWrapper();
securedAccessWrapper.setCheckPost(checkPost);
securedAccessWrapper.setGaurd(gaurd);
service.setSecuredAccessWrapper(securedAccessWrapper);
}
else
{
if(classObj.isAnnotationPresent(SecuredAccess.class))
{
Annotation securedAccessAnnotation=classObj.getAnnotation(SecuredAccess.class);
SecuredAccess securedAccess=(SecuredAccess)securedAccessAnnotation;
String checkPost=securedAccess.checkPost();
String gaurd=securedAccess.gaurd();
SecuredAccessWrapper securedAccessWrapper=new SecuredAccessWrapper();
securedAccessWrapper.setCheckPost(checkPost);
securedAccessWrapper.setGaurd(gaurd);
service.setSecuredAccessWrapper(securedAccessWrapper);
}
}




service.setService(method);
webRockModel.add(path.value()+methodPath.value(),service);
}
}
}



//checking for InjectApplicationScope annotation if Path annotation is not applied
if(classObj.isAnnotationPresent(InjectApplicationScope.class))
{
try 
{
Method method = classObj.getMethod("setApplicationScope",ApplicationScope.class);
ApplicationScope applicationScope=new ApplicationScope();
applicationScope.setServletContext(getServletContext());
method.invoke(classObj.newInstance(),applicationScope);
}catch(SecurityException securityException) {
//do nothing
}
catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException: at "+classObj.getName());
}
}




//checking for InjectApplicationDirectory annotation if Path annotation is not applied
if(classObj.isAnnotationPresent(InjectApplicationDirectory.class))
{
try 
{
Method method = classObj.getMethod("setApplicationDirectory",ApplicationDirectory.class);
ApplicationDirectory applicationDirectory=new ApplicationDirectory(new File(getServletContext().getRealPath("")));

method.invoke(classObj.newInstance(),applicationDirectory);
}catch(SecurityException securityException) {
//do nothing
}
catch(NoSuchMethodException noSuchMethodException) {
throw new ServiceException("SetterNotFoundException: at "+classObj.getName());
}
}


//checking for OnStartup annotation
for(Method method:classObj.getDeclaredMethods())
{
if(method.isAnnotationPresent(OnStartup.class) && method.isAnnotationPresent(Path.class)==false && method.isAnnotationPresent(Forward.class)==false)
{

Service service=new Service();
service.setServiceClass(classObj);
Annotation onStartupAnnotaion=method.getAnnotation(OnStartup.class);
OnStartup onStartup=(OnStartup)onStartupAnnotaion;
service.setRunOnStartup(true);
service.setPriority(onStartup.priority());
service.setService(method);
methodPriorityArrayList.add(service);
}
}

if(classObj.isAnnotationPresent(Path.class)) serviceClassMap.put(classObj,serviceClassMethodsList);
}
createJSFile();
servletContext.setAttribute("WEB_ROCK_MODEL",webRockModel);


invokeMethodsOnPriorityBasis();
}catch(ServiceException serviceException)
{
serviceException.printStackTrace();
}
catch(Exception exception)
{
exception.printStackTrace();
}
}

public void invokeMethodsOnPriorityBasis()
{
Collections.sort(methodPriorityArrayList,new  Comparator<Service>(){
public int compare(Service s1,Service s2)
{
return s1.getPriority()-s2.getPriority();
}
});

try
{
try
{
for(int i=0;i<methodPriorityArrayList.size();i++)
{
Service service=methodPriorityArrayList.get(i);
Class serviceClass=service.getServiceClass();
Method method=service.getService();
method.setAccessible(true);
Object serviceClassObj=serviceClass.newInstance();
method.invoke(serviceClassObj);
}
}catch(InstantiationException instantiationException)
{
throw new ServiceException("ServiceException : ClassInstantiationException"); // It is used to display exception
}
catch(IllegalAccessException illegalAccessException)
{
throw new ServiceException("ServiceException : IllicitAccessException"); // It is used to display exception
}
catch(InvocationTargetException invocationTargetException)
{
 // do nothing
}
 
}
catch(ServiceException serviceException)
{
serviceException.printStackTrace();
}
catch(Exception exception)
{
exception.printStackTrace();
}
}
//remove one braces from here



}