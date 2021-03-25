package com.thinking.machines.webrock.pojo;
public class SecuredAccessWrapper
{
private String checkPost;
private String gaurd;
public SecuredAccessWrapper()
{
this.checkPost="";
this.gaurd="";
}
public void setCheckPost(String checkPost)
{
this.checkPost=checkPost;
}
public String getCheckPost()
{
return this.checkPost;
}
public void setGaurd(String gaurd)
{
this.gaurd=gaurd;
}
public String getGaurd()
{
return this.gaurd;
}
}