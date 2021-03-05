
Requirements<br>
-
- The protocol must follow the rules given above and be 100% (unit) tested.
- (The server must include a log-file to hold important run-time information)
- The server and the client must be written in Java. Your server must be designed to handle many clients simultaneous
- The system must include a simple non-GUI client

Commands from clients to the server
-
Command|Content| Example(s)  	
---|---|---
CONNECT	|Name of the client (use a hardcodeddata structure to hold users)|CONNECT#LarsM
| |CONNECT can only be followed by an ONLINE command from the server. If a user is not found, the server will send CLOSE#2 and close the connection.
SEND    |Receiver followed by the message. (receiver must be a name received via an ONLINE command)|SEND#Peter# Hello Peter <br>SEND#Peter,Hans#Hello Peter and Hans <br>SEND#*#Hello everybody
| |After a SEND command, the server can send an ONLINE, MESSAGE or a CLOSE command
CLOSE   |Nothing|CLOSE#
| |After having sent a CLOSE command the client should discard all messages received until the server responds with a corresponding CLOSE command on which the client should close the connection.


Commands from Server to client(s)
-
Command|Content|Example(s)
---|---|---
ONLINE|Name of all clients, currently online.<br> The server should send this message to all clients, each time a client has connected or disconnected)<br>The list should include the name of the specific client that receives this message|ONLINE#LarsM,Peter,Hans
MESSAGE|Sender followed by the message|MESSAGE#Peter#Hello Hans
CLOSE|0 for a normal close<br>1 illegal input was received<br>2 User not found|CLOSE#0 or CLOSE#1 or CLOSE#2
| |Having sent a CLOSE command the server should close the connection and release all resources attached to that client.


