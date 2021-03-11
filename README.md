This chat program has a chatsver and a chat client.


The server icludes the following classes: Clientservermain,Clienthandler,Messagehandler 

* ChatServermain initializes the serverclass and calls runserver

* Server creates a construtor for the the server and have the run server method in body - the runServer method initializes the clienthandler and call a new thread everytime a new client logs in to the system. 

* ClientHandler run the userinput for each clienthandler thread connect to the system, and initializes the MessageHandler to handle the incomming trafic 
The Clienthandler also thorws exeptions with illegal outputs 

*The messagehandler spilts the command/argumts/message and direct the message to the right person 
