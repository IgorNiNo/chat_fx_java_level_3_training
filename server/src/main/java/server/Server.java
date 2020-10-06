package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Server {
    private List<ClientHandler> clients;
    private AuthService authService;

    private int PORT = 8189;
    ServerSocket server = null;
    Socket socket = null;

    public Server() {
        clients = new Vector<>();

//        authService = new SimpleAuthService();
        //==============//
        if (!SQLHandler.connect()) {
            throw new RuntimeException("Не удалось подключиться к БД");
        }
        authService = new DBAuthServise();
        //==============//

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");

                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            SQLHandler.disconnect();
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        String message = String.format("%s %s : %s", formater.format(new Date()), sender.getNickname(), msg);

        //==============//
        SQLHandler.addMessage(sender.getNickname(), "null", msg, formater.format(new Date()));
        //==============//
        for (ClientHandler c : clients) {
            c.sendMsg(message);
            FileHandler.addMessage(c.getNickname(), message);   //Запись сообщения в файл клиента
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
        String message = String.format("%s [%s] private [%s] : %s", formater.format(new Date()), sender.getNickname(), receiver, msg);
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(receiver)) {
                c.sendMsg(message);

                FileHandler.addMessage(c.getNickname(), message);   //Запись сообщения в файл получателя

                //==============//
                SQLHandler.addMessage(sender.getNickname(), receiver, msg, formater.format(new Date()));
                //==============//

                if (!c.equals(sender)) {
                    sender.sendMsg(message);
                    FileHandler.addMessage(sender.getNickname(), message);   //Запись сообщения в файл отправителя
                }
                return;
            }
        }

        sender.sendMsg("not found user: " + receiver);
    }


    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }


    public boolean isLoginAuthenticated(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    void broadcastClientList() {
        StringBuilder sb = new StringBuilder("/clientlist ");
        for (ClientHandler c : clients) {
            sb.append(c.getNickname()).append(" ");
        }

        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

}
