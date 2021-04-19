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

#### 2. @Get
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
##### 3. @Post
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

#### 5. @Forward("/school/detail")
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

#### 6. @SecuredAccess(checkPost="bobby.test.secureaccess",guard="authenticateUser")

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

#### 7. @InjectApplicationScope

#### 8. @InjectSessionScope

#### 9. @InjectRequestScope

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

#### Note: All this three class are in package : com.thinking.machines.webrock.pojo.*;

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

