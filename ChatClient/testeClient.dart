import 'dart:io';
String msg;
 
void main() {
   
  

  //connect to google port 80
  Socket.connect('127.0.0.1', 10997).then((socket) {
    print('Connected to: '
      '${socket.remoteAddress.address}:${socket.remotePort}');
   
    //Establish the onData, and onDone callbacks
    socket.listen((data) {
      var msg = new String.fromCharCodes(data).trim();
      print(msg);
      var grupos = msg.split("-");
      grupos.forEach((f) => print(f));
      
      socket.destroy();
    });
  
  });
}