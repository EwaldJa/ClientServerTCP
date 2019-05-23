package src;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import src.tools.*;

public class Serveur implements Runnable {
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

    public Socket recevoirConnection(){
        Socket servConnect = null;

        try {
            servConnect = this.servSocket.accept();
            return servConnect;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return servConnect;
    }
    public void run(){
        while(true){
            new Thread(new Communication(this.recevoir())).start();
            System.out.println("La connection a été établie");
        }
    }


}
