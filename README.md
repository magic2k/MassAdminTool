MassAdminTool
=============

Tool for mass remote command execution by ssh using jsch library (http://www.jcraft.com/jsch/).
Now with ugly GUI interface :)

It takes two txt files - one with list of servers in format:

   user:password@host:port
   
and second with commands, which must be written as one string (you can separate commands with ';', just like in console).
Example:

  mkdir /root/testdir/; echo woohooo; if test -e /root/testdir;then echo "good";else echo "bad";fi 
  
Missing features:

 - fine formatted log for command results output for each ssh server
 - ThreadPool for simultaneous connections (update 1000 servers in 30 seconds - "Let Galaxy Burn!" (c))
 - support for ssh keys   
 - less ugly interface :)



 Im not planning to do any of these features, unless I will need it to solve my own tasks, 
 but if you need any of them - feel free to contact me, I think I'll add them :) 
