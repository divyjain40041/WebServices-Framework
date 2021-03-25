class Teacher
{
constructor()
{
this.rollNumber=null;
this.name=null;
this.gender=null;
}
}
class Student
{
constructor()
{
this.rollNumber=null;
this.name=null;
this.gender=null;
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
