import 'dart:io';
import 'dart:collection';

Socket server;
String credential; //email
String id;

void handleServer(Socket socket) {
  Socket.connect('127.0.0.1', 10998).then((socket) {
    server = socket;
    print('Connected to: '
        '${socket.remoteAddress.address}:${socket.remotePort}');
  });
//  print('Connected to: '
//      '${socket.remoteAddress.address}:${socket.remotePort}');
//
//  print('Mensagem (ENTER para sair): ');
//  String msg = stdin.readLineSync();
//  while(!msg.isEmpty){
//    socket.write(msg + "\n");
//    print('Mensagem enviada!');
//    print('Mensagem (ENTER para sair): ');
//    msg = stdin.readLineSync();
//  }
//
//  socket.close();
}

//<signin>:<credential>:<name>
void signIn() {
  print('Digite seu nome: ');
  String name = stdin.readLineSync();
  print('Defina um email para utilizar como login: ');
  credential = stdin.readLineSync();
  server.write("signin:" + credential + ":" + name);
  server.listen((data) {
    id = new String.fromCharCodes(data).trim();
  });
}

//<login>:<id>
void login() {
  server.write("login:" + id);
}

//<logout>:<id>
void logout() {
  server.write("logout:" + id);
}

//<getmessages>:
getMessages() {
  var resp;
  var response;
  server.write("getmessages:");
  server.listen((data) {
    resp = new String.fromCharCodes(data).trim();
    response = resp.split("/");
  });
  print("Mensagens Recebidas: \n");
  response.forEach((r) => print(r));
}

//<publish>:<id>:<grupo>:<message>
void publish() {
  getRegisteredGroups();
  print('Grupo: \n');
  String message = stdin.readLineSync();
  print('Mensagem: \n');
  String groupCode = stdin.readLineSync();
  server.write("publish:" + id + ":" + groupCode + ":" + message);
}

//<subscribe>:<id>:<grupo>
void subscribe() {
  getAvailableGroups();
  print('Código do grupo desejado: ');
  String groupCode = stdin.readLineSync();
  server.write("subscribe:" + id + ":" + groupCode);
}

//<groupsavailable>:
void getAvailableGroups() {
  var resp;
  var response;
  server.write("groupsavailable:");
  server.listen((data) {
    resp = new String.fromCharCodes(data).trim();
    response = resp.split("/");
  });
  print("Grupos Disponíveis para Registro: \n");
  response.forEach((r) => print(r));
}

//<groupsregistered>:<id>
void getRegisteredGroups() {
  var resp;
  var response;
  server.write("groupsregistered:" + id);
  server.listen((data) {
    resp = new String.fromCharCodes(data).trim();
    response = resp.split("/");
  });
  print("Grupos Registrados: \n");
  response.forEach((r) => print(r));
}

void main() {
//  Socket.connect('127.0.0.1', 10998).then((socket) {
//    handleServer(socket);
//  });
}
