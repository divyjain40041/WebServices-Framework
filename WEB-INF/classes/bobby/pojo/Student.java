package bobby.pojo;
public class Student implements java.io.Serializable
{
public int rollNumber;
public String name;
public char gender;
public  Student()
{
this.rollNumber=0;
this.name="";
this.gender='\u0000';
}
public void setRollNumber(int rollNumber)
{
this.rollNumber=rollNumber;
}
public int getRollNumber()
{
return this.rollNumber;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setGender(char gender)
{
this.gender=gender;
}
public char getGender()
{
return this.gender;
}
}