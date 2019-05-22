package src;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Serveur {
    private int port;
    private ServerSocket servSocket;

    public Serveur(int port, int backlog){
        try {
            this.servSocket = new ServerSocket(port, backlog);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Le port est inaccessible");
        }
        this.port = port;
    }

    public String recevoir(){
        String message = new String();
    }


}
