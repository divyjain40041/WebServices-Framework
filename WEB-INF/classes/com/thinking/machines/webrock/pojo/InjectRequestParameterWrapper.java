package com.thinking.machines.webrock.pojo;
import java.lang.reflect.*;
public class InjectRequestParameterWrapper
{
private String injectRequestParameterKey;
private Field field;
public InjectRequestParameterWrapper()
{
this.injectRequestParameterKey="";
}
public void setInjectRequestParameterKey(String injectRequestParameterKey)
{
this.injectRequestParameterKey=injectRequestParameterKey;
}
public String getInjectRequestParameterKey()
{
return this.injectRequestParameterKey;
}
public void setField(Field field)
{
this.field=field;
}
public Field getField()
{
return this.field;
}
}