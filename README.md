This chat program has a chatsver and a chat client.


The server icludes the following classes: Clientservermain,Clienthandler,Messagehandler and Server. 

* ChatServermain initializes the serverclass and calls runserver.

* Server creates a construtor for the server and have the runServer method in body - the runServer method initializes the clienthandler, and call a new thread everytime a new client logs in to the system. 

* ClientHandler run the userinput for each clienthandler thread connect to the system, and initializes the MessageHandler to handle the incomming trafic 
The Clienthandler also thorws exeptions with illegal outputs 

* The messagehandler spilts the command/argumts/message and direct the message to the right person.

Testclass
* ClientHandlerTest  starts up it's own server to do the test of the code.
* MyRunnable runs a simple test input and response.
