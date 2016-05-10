import 'dart:io';
import 'dart:collection';
import "dart:async";

Socket server;
String credential; //email
String id;
bool isLog = false;
bool isSignin = false;

void handleServer(Socket socket) {
  Socket.connect('127.0.0.1', 10998).then((socket) {
    server = socket;
    print('Connected to: '
        '${socket.remoteAddress.address}:${socket.remotePort}');
  });

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
    print("Você foi cadastrado com o id " + id);
  });
  isSignin = true;
}

//<login>:<credential>
void login() {
  server.write("login:" + credential);
  isLog = true;
  print("Você está logado!");
}

//<logout>:<credential>
void logout() {
  server.write("logout:" + credential);
  isLog = false;
}

//<getmessages>:
getMessages() {
  var resp;
  var response;
  server.write("getmessages:" + id);
  server.listen((data) {
    resp = new String.fromCharCodes(data).trim();
  });
  print("Mensagens Recebidas: \n");
  resp.forEach((r) => print(r));
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

void doLogin(String cod) {
  while (!isLog) {
    switch (cod) {
      case "login":
        {
          if (isSignin) {
            login();
          } else {
            print("Primeiro você deve realizar o seu cadastro!");
            signIn();
          }
        }
        break;
      case "cadastro":
        signIn();
        break;
      default:
        {
          print("O que você gostaria de fazer?\n- login\n- cadastro");
          cod = stdin.readLineSync();
        }
    }
  }
}

Future runThreadMessages() async {
  while (true) {
    getMessages();
  }
}

Future runThreadPublish() async {
  while (true) {
    publish();
  }
}

Future main() async {
  Socket.connect('127.0.0.1', 10998).then((socket) {
    handleServer(socket);
  });

  print("O que você gostaria de fazer?\n- login\n- cadastro");
  String cod = stdin.readLineSync();
  doLogin(cod);

  await runThreadPublish();

  await runThreadMessages();
}
