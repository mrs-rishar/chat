package ru.professional.chat.server;
import ru.professional.network.TCPConnection;
import ru.professional.network.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer implements TCPConnectionListener {
    public static void main(String[] args){
        new ChatServer();


    }
    private final ArrayList<TCPConnection> connections = new ArrayList<>();
    private ChatServer(){
        System.out.println("Server running...");
        try(ServerSocket serverSocket = new ServerSocket(8189)){
            while(true){
                try {
                    new TCPConnection(this,serverSocket.accept());
                }catch (IOException e){
                    System.out.println("TCPConnection exception: "+ e);
                }
            }

        }catch (IOException e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sentToALLConnections("Client connectedL: " + tcpConnection);

    }

    @Override
    public synchronized void onReceiveString(TCPConnection tcpConnection, String value) {
        sentToALLConnections(value);

    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        connections.remove(tcpConnection);
        sentToALLConnections("Client disconnectedL: " + tcpConnection);

    }

    @Override
    public synchronized void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: "+ e);

    }
    private void sentToALLConnections(String Value){
        System.out.println(Value);
        final int cnt = connections.size();
        for (int i = 0; i<cnt; i++)
            connections.get(i).sendString(Value);

    }
}
