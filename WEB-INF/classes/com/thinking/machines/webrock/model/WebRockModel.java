package com.thinking.machines.webrock.model;
import java.util.*;
import com.thinking.machines.webrock.pojo.*;
import java.lang.reflect.*;
public class WebRockModel
{
private Map<String,Service> webDataStructure;
public WebRockModel()
{
this.webDataStructure=new HashMap<>();
}
public void add(String key,Service value)
{
this.webDataStructure.put(key,value);
}
public Service get(String key)
{
return this.webDataStructure.get(key);
}
}