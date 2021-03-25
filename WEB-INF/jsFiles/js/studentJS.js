class Teacher
{
constructor()
{
this.rollNumber=0;
this.name='';
this.gender='\0';
this.mbNo=0;
this.salary=0.0;
this.isIndian=false;
this.tax=0.0;
this.student=null;
}
}
class Student
{
constructor()
{
this.rollNumber=0;
this.name='';
this.gender='\0';
}
}
class Peon
{
get()
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/peon/get',
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
}
class TeacherService
{
edit(arg0)
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'POST',
url:'schoolservices/teacherservice/edit',
data:JSON.stringify(arg0),
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
getByTeacherRoll(arg0,arg1)
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/teacherservice/getby',
data:{roll:arg0,nm:arg1},
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
add(arg0)
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'POST',
url:'schoolservices/teacherservice/add',
data:JSON.stringify(arg0),
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
delete(arg0)
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/teacherservice/delete',
data:{roll:arg0},
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
}
class School
{
edit()
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/school/edit',
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
get()
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/school/get',
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
}
class AutoWiredTest
{
invoke()
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/autowire/invoke',
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
}
class login
{
loginStd()
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/login/lgn',
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
}
class StudentService
{
delete(arg0)
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/studentservice/delete',
data:{roll:arg0},
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
getByStudentRoll(arg0)
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/studentservice/getby',
data:{roll:arg0},
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
edit(arg0)
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'POST',
url:'schoolservices/studentservice/edit',
data:JSON.stringify(arg0),
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
getAll()
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/studentservice/getall',
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
add(arg0)
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'POST',
url:'schoolservices/studentservice/add',
data:JSON.stringify(arg0),
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
}
class Account
{
add()
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/account/add',
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
get()
{
var promise=new Promise(function(resolve,reject){
$.ajax({
type:'GET',
url:'schoolservices/account/get',
success:function(data){
resolve(data);
},
failure:function(data){
reject(data);
}
});
});
return promise;
}
}
class Sess
{
}
