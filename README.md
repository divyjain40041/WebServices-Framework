# Java-Based-WebServices-Framework
It is a Java based framework. Using this framework, the user can create backend/serverside services for web-applications. User no need to write the entire web.xml, and this framework also creates the JS file automatically.This framework help user to create web-application very easily and fastly.


# Benifits of using this FrameWork:
1) No need to write servlets.
2) User can use ServicesDoc tool which is available in framework to get structure of POJO & service classes in PDF form.
3) In PDF user also get list of Exception which may get occur along with reason due to which it may get occur.
4) User don't need to fully configure web.xml, Just by doing little configuration in web.xml as describe in below section user can easily use it.
5) User don't have to worry about how to handle get/post type request.
6) User don't need to write classes and method in JS file for respecive POJO and services.JS file for that automatically get generate when server starts.


# Steps to use a framework:- 

1) Download web_services.jar file
    Cut/Copy that jar file to tomcat9/webapps/<project_folder_name>/WEB-INF/lib/;
                                          or
   Download zip file 
     Extract zip file in tomcat9/webapps/<project_folder_name>/;

2) Changes to perform in web.xml:
You need to specify param-value against param-name 'SERVICE_PACKAGE_PREFIX'.In my project it is 'bobby' as shown below. User just need to change/write a single word inside web.xml and that was the param-value against param-name 'SERVICE_PACKAGE_PREFIX' i.e. by default there was "bobby", user have to change it. It is the package prefix like if package name is : "bobby.abc.def" then you need to write 'bobby'. 'bobby' is the name of a folder.

#### Note : The SERVICE_PACKAGE_PREFIX mention here i.e. 'bobby' should exist inside tomcat9/webapps/"project name"/WEB-INF/classes/.
```
<context-param>
<param-name>SERVICE_PACKAGE_PREFIX</param-name>
<param-value>bobby</param-value>
</context-param>
```

You have to change only one word inside web.xml, instead of 'schoolservices' user have to write his/her application entity name there.
```
<servlet>
<servlet-name>TMWebRock</servlet-name>
<servlet-class>com.thinking.machines.webrock.TMWebRock</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>TMWebRock</servlet-name>
<url-pattern>/schoolservices/*</url-pattern>
</servlet-mapping>

```
Now the web.xml has been configured.

3) Now copy all files inside dependencies folder and paste them inside tomcat9/webapps/<project_folder_name>/WEB-INF/lib/. these are all the files you will ever need to create a web service. Our framework is dependent on some of the these files.
4) Now enviroment setup is done,you can use the frameWork easily.'

# Tutorials and Reference documentation:
User has to apply different types of annotations which is in framework according to need.By applying this simple annotation user get free from work of making servlets.The user has to follow the guidelines regarding each annotation mentioned below. If user does not follows the guidelines then ServiceException is raised.

## Annotations:
#### 1. @Path("/studentservice")
Path annotation can be applied on class and method. The value of this annotation should always starts with front Slash("/") followed by path.

Applying @Path annotaion over class
```
@Path("/studentservice")
public class StudentService
{
@Path("/add")
public void add()
{
System.out.println("Student added!");
}
}
```
user can access this service by sending request to "User's entity name"/studentservice/add.

#### 2. @GET
Get annotation can be applied to both class and method.By using this annotation user is declaring that only GET type request allowed for this service.If this annotation is applied on class then all the services inside that class can only accept GET type request.

Applying @GET annotation over class
```
@GET
@Path("/studentservice")
public class StudentService
{
@Path("/display_student_detail")
public void displayStudentDetail(int rollNumber)
{
System.out.println("Student detail!");
}
}
```

Applying @GET annotation over method
```
@Path("/studentservice")
public class StudentService
{
@GET
@Path("/display_student_detail")
public void displayStudentDetail(int rollNumber)
{
System.out.println("Student detail!");
}
}
```
##### 3. @POST
Similarly as Get annotation, Post annotation can be used for allowing POST type request. and it can also applied on both class and method.

Applying @POST annotation over class
```
@POST
@Path("/studentservice")
public class StudentService
{
@Path("/add")
public void add()
{
System.out.println("Student added!");
}
}
```

Applying @POST annotation over method
```
@Path("/studentservice")
public class StudentService
{
@POST
@Path("/add")
public void add()
{
System.out.println("Student added!");
}
}
```

User can also apply both annotation simultaneously:

```
@Path("/studentservice")
public class StudentService
{
@POST
@Path("/add")
public void add()
{
System.out.println("Student added!");
}
@GET
@Path("/get_all")
public void getAll()
{
System.out.println("Student list");
}
}
```

#### 4. @RequestParameter(requestParameterKey="<key_name>").

@RequestParameter annotation can only be applied on parameter.User can use the following annotation to request data from framework which arrives as web request. framework finds the value of the annotation and search for data with given name in request Bag and if found provide this requested data to user without user having to worry about conversions. 

```
import com.thinking.machines.webrock.annotations.*;
@Path("/studentservice")
public class StudentService
{
@GET
@Path("/get_by_rollnumber")
public Student getByStudentRoll(@RequestParameter(requestParameterKey="roll")int rollNumber)
{   
    System.out.println("Student detail");
}
}
```

#### 5. @InjectRequestParameter("<>")

This annotation is same as @RequestParameter annotation but unlike it only apply on class properties.It work similar as @RequestParameter but benefit of using this annotation is that if some data is arriving through query string and more than one service required that same data then instead of using applying @RequestParameter annotation on each services user can use @InjectRequestParameter annotation on that field, which is accessible to all services.

```
import com.thinking.machines.webrock.annotations.*;
@Path("/studentservice")
public class StudentService
{
@InjectRequestParameter("roll_number")
private int rollNumber;
@Path("/getByRollNumber")
public Student getByRollNumber()
{
int rollNumber=this.rollNumber;
Student student=getStudent(rollNumber);
return student;
}
}
```


#### 6. @Forward("/school/detail")
Using this annotation user can forward request to another web service or to some client side technology like (jsp file/ html file) .The example below shows, How to use forward annotaton to forward request to other service "/school/view",you can also forward to some JSP also.by giving JSP file name as value of forward annotation. Forward annotation can only be applied on Services(Method which has path annotation applied on it).

```
@GET
@Path("/school")
public class School
{
@Forward("/school/school_detail.html")
@Path("/school"_detail)
public void schoolDetail()
{
System.out.println("School detail");
}
}
```

#### 7. @SecuredAccess(checkPost="bobby.test.secureaccess",guard="authenticateUser")

By using this annotation user dont have to write verification code for every service that need to be secured,user can just apply this annotation to all the services that are needed to be secured from unidentified access. SecuredAccess annotation can only be applied on Service/Method.

checkPost = full classname(with package) to your verification class.
guard = method name of verification class.

```
@SecuredAccess(checkPost=bobby.test.secureaccess.Authenticaion",gaurd="authenticateUser")
@GET
@Path("/login")
public class Login
{
@Forward("/school/edit")
@Path("/lgn")
public void loginValidator()
{
System.out.println("Login request");
}
}
```

Framework provides four classes, names are -

RequestScope, SessionScope, ApplicationScope, ApplicationDirectory inside package com.thinking.machines.webrock.scopes, i.e. if user want to use these classes user have to import/write - "import com.thinking.machines.webrock.pojo.*;" Basically user uses these classes whenever user require any kind of scopes/application-directory inside any service or class.

These 3 classes which represent scopes have only 2 functionalities, they are - setAttribute and getAttribute.

public void setAttribute(String,Object); - for setting an attribute against name inside any scope.

public Object getAttribute(String); - for getting attribute against name from any scope.

and class with name ApplicationDirectory has only one functionality and it was - getDirectory.

public File getDirectory(); - for getting application directory.

For all the three classes there are three annotations:

#### 8. @InjectApplicationScope

#### 9. @InjectSessionScope

#### 10. @InjectRequestScope

```
package bobby.scope.example;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@InjectApplicationScope
public class ScopeExample
{
Student student;
ApplicationScope applicationScope;
public void setApplicationScope(ApplicationScope applicationScope)
{
this.applicationScope=applicationScope;
}
public ApplicationScope getApplicationScope()
{
return this.applicationScope;
}
@Path("/add")
public void addStudent()
{
this.student=new Student();
this.student.setRollNumber(110);
this.applicationScope.setAttribute("stduent_info",this.student);
}
public void setApplicationScope(ApplicationScope applicationScope)
{
this.applicationScope=applicationScope;
}
}
```

The class ScopeExample requires application scope. For that, the user has to declare a field of type ApplicationScope along with the setter method as shown in the above code. Whenever there is a request arrived for sam, then before the invocation of sam service the setApplicationScope method got invoked.

##### Note: All this three class are in package : com.thinking.machines.webrock.pojo.*;

There is also a alternative approach to write that code. Instead of applying the InjectApplicationScope on that class you can simply introduce one more parameter in sam method of type Application Scope, As shown below in code.

```
package bobby.scope.example;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
public class ScopeExample
{
Student student;
@Path("/add")
public void addStudent(ApplicationScope applicationScope)
{
this.student=new Student();
this.student.setRollNumber(110);
applicationScope.setAttribute("stduent_info",this.student);
}
```
Similarly, The code can be written for RequestScope & SessionScope.

#### 11.@InjectApplicationDirectory

If you want to get the working directory of your project then you have to apply this annotation on class. The class should contain a field of type ApplicationDirectory and the setter method for this.The way of writing code is as same as the above code.

The Class ApplicationDirectory has only one method:

*File getDirectory();
```
package bobby.scope.example;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
@InjectApplicationDirectory
public class ScopeExample
{
ApplicationDirectory applicationDirectory;
public void setApplicationDirectory(ApplicationDirectory applicationDirectory)
{
this.applicationDirectory=applicationDirectory;
}
@Path("/app_directory")
public void displayDirectory()
{
System.out.println("Current Directory is "+this.applicationDirectory.getRealPath());
}
}
```
                     or

```
package bobby.scope.example;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
public class ScopeExample
{
@Path("/app_directory")
public void displayDirectory(ApplicationDirectory applicationDirectory)
{
System.out.println("Current Directory is"+this.applicationDirectory.getRealPath());
}
}
```

#### 12. @AutoWired(name="<property_name>")

AutoWired annotation can only be applied on properties of a service class. User apply these annotation when he/she wants that the value against 'name' property of AutoWired annotation should setted to that property on which annotation applied and value will be extracted from scopes (request scope > session scope > application scope). Setter method should be present for that property so that value can be setted.

i.e. order of finding the value against value of name field of annotation was - Request scope -> Session scope -> Application scope.

```
import com.thinking.machines.webrock.annotations.*;
@Path("/studentservice")
public class Student
{
@AutoWired(name="student_name")
String name;
public void setName(String name)
{
this.name=name;
}
@Path("/get")
public String getName()
{
System.out.print("Name of student: ");
System.out.println(this.name);
return this.name;
}
}
```

#### 13. @OnStartup(priority=1)

If you wanted to invoke the service when the server get started then you can apply this annotation on that service. This annotation can only be applied on method/service. If user wants to invoke more than one service at startup of server then he should mention the priority of invocation of services. Service with lesser priority number will be called first.

```
package bobby.scope.example;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
public class StudentDS
{
@Startup(PRIORITY=1)
public void populateStudents()
{
System.out.println("Populate students!");
}
}
```
##### Note: You are not supposed to apply Path annotation with startup annotation. you cannot use RequestScope or SessionScope as a parameter. you can only use ApplicationScope as a parameter.

# Serivce Exeptions:

Here are some exceptions which may get occur if user do some mistake.

|S.No.|Exception|Description|
| -------- | ------- | --------- |
|1.|ServicePackagePrefixException         |No such package exists, which contains the value mentioned against SERVICE_PACKAGE_PREFIX in web.xml as a prefix.|
|2.|RequestTypeConflictException | You could not use both @GET and @Post annotations at a time on a single method or class.|
|3.|LeaningOnPathException | You did not implement the @Path annotation with one of the following annotations @GET,@ POST, @Forward ,@InjectSessionScope ,@InjectRequestScope,@InjectApplicationScope ,@InjectApplicationDirectoryScope ,@SecuredAccess|
|4.|LeaningOnRequestTypeException|You did not implement the @GET or @POST annotation with one of the following annotations @Forward ,@SecuredAccess.|
|5.|OnStartupException|You cannot implement @OnStartup annotation along with other annotations present in the WebServicesFramework.|
|6.|IllegalInjectRequestParameterException|You cannot apply @InjectRequestParameter annotation with data types other than primitive and java.lang.String.|
|7.|ExcessParameterTypeException|You cannot use more than one complex data type {except :java.lang.String,ApplicationScope,RequestScope,SessionScope,ApplicationDirectory} in single function over which @Path annotation is applied.|
|8.|ClassInstantiationException|The instantiation can fail for a variety of reasons including but not limited to:(i) the class object represents an abstract class, an interface, an array class, a primitive type,or void.(ii) the class has no nullary constructor.|
|9.|InvalidURLRequestException|This exception occurs when you request some URL and it is not found.|
|10.|IllegalMethodException|This exception occurs when you try to access resources through a method that is not allowed on that resource.|
|11.|IllegalGaurdParameterException|This exception occurs when your gaurd method has parameters other than {ApplicationScope, SessionScope, RequestScope, ApplicationDirectory}.|
|12.|IllegalServiceAccessException|An IllegalAccessException is thrown when an application tries to reflectively create an instance (other than an array), set or get a field, or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.|
|13.| InvalidNumberOfParameterException|Method to which request has been forwarded has either 0 parameter or more than one parameters|


# Advance features:
## To dynamically generate JavaScript file:-
1) You required to add two more param-name in web.xml as shown below.

```
<context-param>
<param-name>JS_FILE</param-name>
<param-value>studentJS</param-value>
</context-param>

<context-param>
<param-name>BASE_URL</param-name>
<param-value>/schoolservices</param-value>
</context-param>
```
* You need to specify the name for the dynamically generated js file as param-value of param-name "JS_FILE"
* The second param-name is BASE_URL_PATTERN in which you have to set the same URL pattern as you set previously.

## To generate PDF for structure of service and POJO classes:
Framework has tool name ServiceDOC to generate PDF. 

package name: com.thinking.machines.tools.ServiceDOC You have to pass two things as command line argument

First argument : Path where pdf will be saved.
Second arguement : The Path to the folder where package exists

If you are in some pqr folder currently, Then you need to write:
```
java -classpath c:\tomcat9\webapps\Web_Services\WEB-INF\lib;c:\tomcat9\webapps\Web_Services\WEB-INF\classes;c:\tomcat9\lib\*;. ServiceDOC <First argument> <Second argument>
```
Then pdf will be created in current folder.
