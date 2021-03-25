import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.util.*;
import java.util.jar.*;
import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.pojo.*;
import com.thinking.machines.webrock.model.*;
class ServiceDOC
{
Stack<File> fileStack;
ServiceDOC()
{
this.fileStack=new Stack<>();
}

public void recursivePrint(File[] arr,int index,int level)  
{
if(index == arr.length) 
return; 
if(arr[index].isFile()) 
{
if(arr[index].getName().substring(arr[index].getName().length()-6).equals(".class")) this.fileStack.add(arr[index]);
}
else if(arr[index].isDirectory()) 
{ 
recursivePrint(arr[index].listFiles(), 0, level + 1); 
} 
recursivePrint(arr,++index, level); 
} 



public void createPDF(java.util.List<Class> classNames,String pdfPath)
{
try
{
Font boldHeading = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
Font bold = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
Font regular = new Font(Font.FontFamily.TIMES_ROMAN, 14);


FileOutputStream file=null;
file=new FileOutputStream(new File(pdfPath));
Document document=new Document();
PdfWriter.getInstance(document,file);
document.open();


Paragraph serviceClassesTitle=new Paragraph("List of Service classes\n\n\n",boldHeading);
serviceClassesTitle.setAlignment(Paragraph.ALIGN_CENTER);
document.add(serviceClassesTitle);
int i=0;
for(Class serviceClass: classNames)
{
if(serviceClass.isAnnotationPresent(Path.class))
{
document.add(new Paragraph(serviceClass.getName()+"\n\n",bold));
PdfPTable pdfPTable = new PdfPTable(1);
pdfPTable.setWidthPercentage(100);



String annotationAppliedOverClassTitle="\nAnnotation(s) applied over class: \n";
String annotationAppliedOverClass="";

java.lang.annotation.Annotation pathClassAnnotation=serviceClass.getAnnotation(Path.class);
Path pathClass=(Path)pathClassAnnotation;
annotationAppliedOverClass+="            @Path(\""+pathClass.value()+"\")\n";


if(serviceClass.isAnnotationPresent(GET.class)) annotationAppliedOverClass+="            @GET\n";
if(serviceClass.isAnnotationPresent(POST.class)) annotationAppliedOverClass+="            @POST\n";
if(serviceClass.isAnnotationPresent(InjectApplicationScope.class)) annotationAppliedOverClass+="            @InjectApplicationScope\n";
if(serviceClass.isAnnotationPresent(InjectSessionScope.class)) annotationAppliedOverClass+="            @InjectSessionScope\n";
if(serviceClass.isAnnotationPresent(InjectRequestScope.class)) annotationAppliedOverClass+="            @InjectRequestScope\n";
if(serviceClass.isAnnotationPresent(InjectApplicationDirectory.class)) annotationAppliedOverClass+="            @InjectApplicationDirectory\n";

if(serviceClass.isAnnotationPresent(SecuredAccess.class)) 
{
java.lang.annotation.Annotation annotation=serviceClass.getAnnotation(SecuredAccess.class);
SecuredAccess securedAccess=(SecuredAccess)annotation;
annotationAppliedOverClass+="            @SecuredAccess(checkPost=\""+securedAccess.checkPost()+"\",Gaurd=\""+securedAccess.gaurd()+"\")\n";
}


String fieldString="";
String fieldTitle="\nField(s)";
Field fields[]=serviceClass.getDeclaredFields();
for(Field field:fields)
{
field.setAccessible(true);
fieldString+="               "+field.getType()+" "+field.getName();
if(field.isAnnotationPresent(AutoWired.class))
{
java.lang.annotation.Annotation annotation=field.getAnnotation(AutoWired.class);
AutoWired autoWired=(AutoWired)annotation;

fieldString+="    @AutoWired(name=\""+autoWired.name()+"\")\n";

}
}
if(fields.length==0) fieldString+="               No field\n\n";
else  fieldString+="\n";

//done
PdfPCell annotationAppliedOverClassTitleCell=new PdfPCell(new Paragraph(annotationAppliedOverClassTitle,bold));
annotationAppliedOverClassTitleCell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
PdfPCell annotationAppliedOverClassCell=new PdfPCell(new Paragraph(annotationAppliedOverClass,regular));
annotationAppliedOverClassCell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
pdfPTable.addCell(annotationAppliedOverClassTitleCell);
pdfPTable.addCell(annotationAppliedOverClassCell);

PdfPCell fieldTitleCell=new PdfPCell(new Paragraph(fieldTitle,bold));
fieldTitleCell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
PdfPCell fieldStringCell=new PdfPCell(new Paragraph(fieldString,regular));
fieldStringCell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);
pdfPTable.addCell(fieldTitleCell);
pdfPTable.addCell(fieldStringCell);

Method methods[]=serviceClass.getDeclaredMethods();
for(Method method:methods)
{
String methodTitle="";
String methodName="";
String parameterTitle="";
String parametersType="";
String returnTypeTitle="";
String returnType="";
String annotationAppliedOverMethodTitle="Annotation(s) applied over method: \n";
String annotationAppliedOverMethod="";
method.setAccessible(true);
if(method.isAnnotationPresent(Path.class))
{
java.lang.annotation.Annotation pathAnnotation=method.getAnnotation(Path.class);
Path path=(Path)pathAnnotation;
annotationAppliedOverMethod+="            @Path(\""+path.value()+"\")\n";
if(method.isAnnotationPresent(Forward.class)) 
{
java.lang.annotation.Annotation annotation=method.getAnnotation(Forward.class);
Forward forward=(Forward)annotation;
annotationAppliedOverMethod+="            @Forward(\""+forward.value()+"\")\n";
}



if(method.isAnnotationPresent(GET.class)) annotationAppliedOverMethod+="            @GET\n";
if(method.isAnnotationPresent(POST.class)) annotationAppliedOverMethod+="            @POST\n";

if(method.isAnnotationPresent(SecuredAccess.class)) 
{
java.lang.annotation.Annotation annotation=method.getAnnotation(Forward.class);
SecuredAccess securedAccess=(SecuredAccess)annotation;
annotationAppliedOverMethod+="            @SecuredAccess(checkPost=\""+securedAccess.checkPost()+"\",Gaurd=\""+securedAccess.gaurd()+"\")\n";
}




methodTitle+="\nMethod: ";
methodName="             "+method.getName()+"\n\n";
parameterTitle="\nParameters: ";
Parameter parameters[]=method.getParameters();
for(Parameter parameter:parameters)
{
parametersType+="             "+String.valueOf(parameter.getParameterizedType())+"\n";
}
if(parameters.length==0) parametersType+="             "+"Zero parameters";

returnTypeTitle+="\nReturn Type: ";
returnType+="             "+method.getReturnType()+"\n\n";




PdfPCell methodTitleCell=new PdfPCell(new Paragraph(methodTitle,bold));
methodTitleCell.setBorder(Rectangle.TOP | Rectangle.RIGHT | Rectangle.LEFT);
PdfPCell methodNameCell=new PdfPCell(new Paragraph(methodName,regular));
methodNameCell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);

PdfPCell annotationAppliedOverMethodTitleCell=new PdfPCell(new Paragraph(annotationAppliedOverMethodTitle,bold));
annotationAppliedOverMethodTitleCell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
PdfPCell annotationAppliedOverMethodCell=new PdfPCell(new Paragraph(annotationAppliedOverMethod,regular));
annotationAppliedOverMethodCell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);

PdfPCell parameterTitleCell=new PdfPCell(new Paragraph(parameterTitle,bold));
parameterTitleCell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
PdfPCell parameterTypeCell=new PdfPCell(new Paragraph(parametersType,regular));
parameterTypeCell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);

PdfPCell returnTypeTitleCell=new PdfPCell(new Paragraph(returnTypeTitle,bold));
returnTypeTitleCell.setBorder(Rectangle.RIGHT | Rectangle.LEFT);
PdfPCell returnTypeCell=new PdfPCell(new Paragraph(returnType,regular));
returnTypeCell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT | Rectangle.LEFT);





pdfPTable.addCell(methodTitleCell);
pdfPTable.addCell(methodNameCell);
pdfPTable.addCell(annotationAppliedOverMethodTitleCell);
pdfPTable.addCell(annotationAppliedOverMethodCell);
pdfPTable.addCell(parameterTitleCell);
pdfPTable.addCell(parameterTypeCell);
pdfPTable.addCell(returnTypeTitleCell);
pdfPTable.addCell(returnTypeCell);

}

}
document.add(pdfPTable);
i++;
if(i<classNames.size()) document.add(new Paragraph("\n\n\n\n"));
}
} //for each ends


document.newPage();
Paragraph serviceExceptionTitle=new Paragraph("List of ServiceException\n\n\n",boldHeading);
serviceExceptionTitle.setAlignment(Paragraph.ALIGN_CENTER);
document.add(serviceExceptionTitle);


PdfPTable pdfPExceptionTable = new PdfPTable(1);
pdfPExceptionTable.setWidthPercentage(100);


PdfPCell exception1TitleCell=new PdfPCell(new Paragraph("1) BaseURLNotFoundException:\n",bold));
PdfPCell exception1Cell=new PdfPCell(new Paragraph("\tThis is raise when  BASE_URL in param-name or value against it in param-value tag in web.xml is not provided.\n\n",regular));
exception1TitleCell.setBorder(Rectangle.NO_BORDER);
exception1Cell.setBorder(Rectangle.NO_BORDER);
exception1TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception1Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

PdfPCell exception2TitleCell=new PdfPCell(new Paragraph("2) ClassInstantiationException:\n",bold));
PdfPCell exception2Cell=new PdfPCell(new Paragraph("\tThe instantiation can fail for a variety of reasons including but not limited to:\n\t\t(i) the class object represents an abstract class, an interface, an array class, a primitive type, or void.\n\t\t(ii) the class has no nullary constructor.\n\n",regular));
exception2TitleCell.setBorder(Rectangle.NO_BORDER);
exception2Cell.setBorder(Rectangle.NO_BORDER);
exception2TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception2Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

PdfPCell exception3TitleCell=new PdfPCell(new Paragraph("3) ExcessParameterTypeException:\n",bold));
PdfPCell exception3Cell=new PdfPCell(new Paragraph("\tYou cannot use more than one complex data type {except : java.lang.String,ApplicationScope,RequestScope,SessionScope,ApplicationDirectory} in single function over which @Path annotation is applied.\n\n",regular));
exception3TitleCell.setBorder(Rectangle.NO_BORDER);
exception3Cell.setBorder(Rectangle.NO_BORDER);
exception3TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception3Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

PdfPCell exception4TitleCell=new PdfPCell(new Paragraph("4) GaurdNotFoundException:\n",bold));
PdfPCell exception4Cell=new PdfPCell(new Paragraph("\tThis exception occurs when you do not define a gaurd method.\n\n",regular));
exception4TitleCell.setBorder(Rectangle.NO_BORDER);
exception4Cell.setBorder(Rectangle.NO_BORDER);
exception4TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception4Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

PdfPCell exception5TitleCell=new PdfPCell(new Paragraph("5) IllegalGaurdParameterException:\n",bold));
PdfPCell exception5Cell=new PdfPCell(new Paragraph("\tThis exception occurs when your gaurd method has parameters other than {ApplicationScope, SessionScope, RequestScope, ApplicationDirectory}.\n\n",regular));
exception5TitleCell.setBorder(Rectangle.NO_BORDER);
exception5Cell.setBorder(Rectangle.NO_BORDER);
exception5TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception5Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);


PdfPCell exception6TitleCell=new PdfPCell(new Paragraph("6) IllegalInjectRequestParameterException:\n",bold));
PdfPCell exception6Cell=new PdfPCell(new Paragraph("\tYou cannot apply @InjectRequestParameter annotation with data types other than primitive and java.lang.String.\n\n",regular));
exception6TitleCell.setBorder(Rectangle.NO_BORDER);
exception6Cell.setBorder(Rectangle.NO_BORDER);
exception6TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception6Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);


PdfPCell exception7TitleCell=new PdfPCell(new Paragraph("7) IllegalMethodException:\n",bold));
PdfPCell exception7Cell=new PdfPCell(new Paragraph("\tThis exception occurs when you try to access resources through a method that is not allowed on that resource.\n\n",regular));
exception7TitleCell.setBorder(Rectangle.NO_BORDER);
exception7Cell.setBorder(Rectangle.NO_BORDER);
exception7TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception7Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);


PdfPCell exception8TitleCell=new PdfPCell(new Paragraph("8) IllicitAccessException:\n",bold));
PdfPCell exception8Cell=new PdfPCell(new Paragraph("\tAn IllicitAccessException is thrown when an application tries to reflectively create an instance (other than an array), set or get a field, or invoke a method, but the currently executing method does not have access to the definition of the specified class, field, method or constructor.\n\n",regular));
exception8TitleCell.setBorder(Rectangle.NO_BORDER);
exception8Cell.setBorder(Rectangle.NO_BORDER);
exception8TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception8Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

PdfPCell exception9TitleCell=new PdfPCell(new Paragraph("9) InvalidNumberOfParameterException:\n",bold));
PdfPCell exception9Cell=new PdfPCell(new Paragraph("\tMethod to which request has been forwarded has either 0 parameter or more than 1 parameters.\n\n",regular));
exception9TitleCell.setBorder(Rectangle.NO_BORDER);
exception9Cell.setBorder(Rectangle.NO_BORDER);
exception9TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception9Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);


PdfPCell exception10TitleCell=new PdfPCell(new Paragraph("10) InvalidURLRequestException:\n",bold));
PdfPCell exception10Cell=new PdfPCell(new Paragraph("\tThis exception occurs when you request some URL and it is not found.\n\n",regular));
exception10TitleCell.setBorder(Rectangle.NO_BORDER);
exception10Cell.setBorder(Rectangle.NO_BORDER);
exception10TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception10Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);



PdfPCell exception11TitleCell=new PdfPCell(new Paragraph("11) LeaningOnPathException:\n",bold));
PdfPCell exception11Cell=new PdfPCell(new Paragraph("\tYou did not implement the @Path annotation with one of the following annotations @GET, @ POST, @Forward ,@InjectSessionScope  ,@InjectRequestScope  ,@InjectApplicationScope  ,@InjectApplicationDirectoryScope  ,@SecuredAccess\n\n",regular));
exception11TitleCell.setBorder(Rectangle.NO_BORDER);
exception11Cell.setBorder(Rectangle.NO_BORDER);
exception11TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception11Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);


PdfPCell exception12TitleCell=new PdfPCell(new Paragraph("12) LeaningOnRequestTypeException:\n",bold));
PdfPCell exception12Cell=new PdfPCell(new Paragraph("\tYou did not implement the @GET or @POST annotation with one of the following annotations @Forward ,@SecuredAccess.\n\n",regular));
exception12TitleCell.setBorder(Rectangle.NO_BORDER);
exception12Cell.setBorder(Rectangle.NO_BORDER);
exception12TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception12Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

PdfPCell exception13TitleCell=new PdfPCell(new Paragraph("13) OnStartupException:\n",bold));
PdfPCell exception13Cell=new PdfPCell(new Paragraph("\tYou cannot implement @OnStartup annotation along with other annotations present in the WebServicesFramework.\n\n",regular));
exception13TitleCell.setBorder(Rectangle.NO_BORDER);
exception13Cell.setBorder(Rectangle.NO_BORDER);
exception13TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception13Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);


PdfPCell exception14TitleCell=new PdfPCell(new Paragraph("14) RequestTypeConflictException:\n",bold));
PdfPCell exception14Cell=new PdfPCell(new Paragraph("\tYou could not use both @GET and @Post annotations at a time on a single method or class.\n\n",regular));
exception14TitleCell.setBorder(Rectangle.NO_BORDER);
exception14Cell.setBorder(Rectangle.NO_BORDER);
exception14TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception14Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);


PdfPCell exception15TitleCell=new PdfPCell(new Paragraph("15) ServicePackagePrefixException:\n",bold));
PdfPCell exception15Cell=new PdfPCell(new Paragraph("\tNo such package exists, which contains the value mentioned against SERVICE_PACKAGE_PREFIX in web.xml as a prefix.\n\n",regular));
exception15TitleCell.setBorder(Rectangle.NO_BORDER);
exception15Cell.setBorder(Rectangle.NO_BORDER);
exception15TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception15Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);

PdfPCell exception16TitleCell=new PdfPCell(new Paragraph("16) ServicePackagePrefixNotFoundException:\n",bold));
PdfPCell exception16Cell=new PdfPCell(new Paragraph("\tThis is raise when  SERVICE_PACKAGE_PREFIX in param-name or value against it in param-value tag in web.xml is not provided.\n\n",regular));
exception16TitleCell.setBorder(Rectangle.NO_BORDER);
exception16Cell.setBorder(Rectangle.NO_BORDER);
exception16TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception16Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);



PdfPCell exception17TitleCell=new PdfPCell(new Paragraph("17) SetterNotFoundException:\n",bold));
PdfPCell exception17Cell=new PdfPCell(new Paragraph("\tThis exception occurs when you do not define a setter method for the property of the {ApplicationScope,SessionScope,RequestScope or ApplicationDirectory} class type or you do not create a setter for the property to which you apply @AutoWired annotation.",regular));
exception17TitleCell.setBorder(Rectangle.NO_BORDER);
exception17Cell.setBorder(Rectangle.NO_BORDER);
exception17TitleCell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
exception17Cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);


pdfPExceptionTable.addCell(exception1TitleCell);
pdfPExceptionTable.addCell(exception1Cell);
pdfPExceptionTable.addCell(exception2TitleCell);
pdfPExceptionTable.addCell(exception2Cell);
pdfPExceptionTable.addCell(exception3TitleCell);
pdfPExceptionTable.addCell(exception3Cell);
pdfPExceptionTable.addCell(exception4TitleCell);
pdfPExceptionTable.addCell(exception4Cell);
pdfPExceptionTable.addCell(exception5TitleCell);
pdfPExceptionTable.addCell(exception5Cell);
pdfPExceptionTable.addCell(exception6TitleCell);
pdfPExceptionTable.addCell(exception6Cell);
pdfPExceptionTable.addCell(exception7TitleCell);
pdfPExceptionTable.addCell(exception7Cell);
pdfPExceptionTable.addCell(exception8TitleCell);
pdfPExceptionTable.addCell(exception8Cell);
pdfPExceptionTable.addCell(exception9TitleCell);
pdfPExceptionTable.addCell(exception9Cell);
pdfPExceptionTable.addCell(exception10TitleCell);
pdfPExceptionTable.addCell(exception10Cell);
pdfPExceptionTable.addCell(exception11TitleCell);
pdfPExceptionTable.addCell(exception11Cell);
pdfPExceptionTable.addCell(exception12TitleCell);
pdfPExceptionTable.addCell(exception12Cell);
pdfPExceptionTable.addCell(exception13TitleCell);
pdfPExceptionTable.addCell(exception13Cell);
pdfPExceptionTable.addCell(exception14TitleCell);
pdfPExceptionTable.addCell(exception14Cell);
pdfPExceptionTable.addCell(exception15TitleCell);
pdfPExceptionTable.addCell(exception15Cell);
pdfPExceptionTable.addCell(exception16TitleCell);
pdfPExceptionTable.addCell(exception16Cell);
pdfPExceptionTable.addCell(exception17TitleCell);
pdfPExceptionTable.addCell(exception17Cell);







document.add(pdfPExceptionTable);

document.close();
}//try ends
catch(Exception e)
{
e.printStackTrace();
}

}







public static void main(String gg[])
{
try
{

String givenPath=gg[0];
String pdfPath=gg[1];
java.util.List<Class> classNames = new ArrayList<>();

ServiceDOC serviceDOC=new ServiceDOC();

if(givenPath.endsWith(".jar"))
{
JarFile jarFile = new JarFile(givenPath);
Enumeration<JarEntry> e = jarFile.entries();
while (e.hasMoreElements()) 
{
JarEntry jarEntry = e.nextElement();
if (jarEntry.getName().endsWith(".class")) 
{
String className = jarEntry.getName().replace("/", ".").replace("\\", ".").replace(".class", "");
classNames.add(Class.forName(className));
}
}
serviceDOC.createPDF(classNames,pdfPath);
}
else
{
File arr[]=null;      
File maindir = new File(givenPath);   
if(maindir.exists() && maindir.isDirectory()) 
{ 
arr= maindir.listFiles(); 
serviceDOC.recursivePrint(arr,0,0);

for(int i=0;i<serviceDOC.fileStack.size();i++)
{
String absolutePath=serviceDOC.fileStack.get(i).getAbsolutePath();
String className=absolutePath.substring(absolutePath.indexOf("classes")+7+1).replace("\\",".").replace(".class","").replace("/",".");
classNames.add(Class.forName(className));
}
serviceDOC.createPDF(classNames,pdfPath);
}
}

}
catch(Exception exception)
{
System.out.println(exception);
}






}

}