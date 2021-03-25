package com.thinking.machines.webrock.pojo;
public class ServiceResponse
{
public boolean isException;
public Object responseData;
public Object exception;
public ServiceResponse()
{
this.isException=false;
this.responseData=null;
}
public void setIsException(boolean isException)
{
this.isException=isException;
}
public boolean getIsException()
{
return this.isException;
}
public void  setResponseData(Object responseData)
{
this.responseData=responseData;
}
public Object getResponseData()
{
return this.responseData;
}
public void setException(Object exception)
{
this.exception=exception;
}
public Object getException()
{
return this.exception;
}
}