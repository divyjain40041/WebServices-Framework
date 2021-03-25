service(method) 
   
	    cumpolsory annotation   ----->   
	    Allowed annotation        ----->  Path,GET,POST,OnStartup,Forward
	    Not allowed   annotation ----->

-------------------------------------------------------------------------------------------------------------------------------------------------------------
Forward
	    cumpolsory annotation   ----->    Path
	    Allowed annotation        -----> Path,GET,POST
	    Not allowed   annotation -----> OnStartup


-------------------------------------------------------------------------------------------------------------------------------------------------------------
OnStartup

	    cumpolsory annotation   ----->    
	    Allowed annotation        -----> 
	    Not allowed   annotation -----> Forward,Path,GET,POST


-------------------------------------------------------------------------------------------------------------------------------------------------------------
isInjectSessionScope  

	    cumpolsory annotation   -----> Path
	    Allowed annotation        -----> Forward,Path,GET,POST
	    Not allowed   annotation -----> OnStartup(if Path applied in method)

-------------------------------------------------------------------------------------------------------------------------------------------------------------
isInjectRequestScope  

	    cumpolsory annotation   -----> Path   
	    Allowed annotation        -----> Forward,Path,GET,POST
	    Not allowed   annotation ----->  OnStartup(if Path applied in method)

-------------------------------------------------------------------------------------------------------------------------------------------------------------
isInjectApplicationScope

	    cumpolsory annotation   ----->
	    Allowed annotation        -----> Forward,Path,GET,POST,OnStartup
	    Not allowed   annotation ----->  OnStartup(if Path applied in method)


-------------------------------------------------------------------------------------------------------------------------------------------------------------
isInjectApplicationDirectory

	    cumpolsory annotation   -----> Path   


-------------------------------------------------------------------------------------------------------------------------------------------------------------
