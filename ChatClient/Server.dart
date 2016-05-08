import 'dart:io';

void main(){

  ServerSocket.bind('127.0.0.1', 4041).then(
    (ServerSocket server){
      server.listen(handleClient);
    }
  );
}

void handleClient(Socket client){
  print('Connection from '
    '${client.remoteAddress.address}:${client.remotePort}');

  client.write("Hello from simple server!\n");
  client.close();
}