# SHS
/==========Generation of the jar file from Eclipse===============/
1: right clic on the project
2: export
3: java >Runnable jar file>next
4: We choose are project in the Launch configuration
5: We choose an export destination folder and a name to the jar file
6: Finish
7: After the generation of the jar we created a Manifest file in the project folder and a MyRessources folder whith the properties file and the driver JDBC
8: We made a copy of the MyRessources folder and the Manifest file to the bin folder of the JDK forlder in the C repository
9: we executed the jar file in the JDK repository with the command line java -jar "C:\Program Files\Java\jdk-10.0.2\bin\SHS_Client.jar"

/==========The scenario of the test for the users table===============/
Client : get all the users of the table
Application : select * from users 
=>Result : print all the users

Client : update a user of the table and change his function
Application : update users set function where name=? 
=>Result : print ok if it succeed or error if not

Client :delete a user of the table 
Application : delete from users where name=? 
=>Result : print ok if it succeed or error if not

Client :insert into the users table 
Application : insert into users(first_name,last_name,email, password,function) values('pierre','paul','pierre.paul@gmail.com','1234','agent de supervision');
=>Result : print ok if it succeed or error if not