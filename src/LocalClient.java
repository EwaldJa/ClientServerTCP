package src;

import src.tools.GestionHttpClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LocalClient {
    public static final int transfer_successful = 0;
    public static int ReceiveFile(String server_address_str, String server_port_str, String filepath) {
        try {
            File file = new File(filepath);
            InetAddress ServeurAdresse= InetAddress.getByName(server_address_str);
            Socket socket = new Socket(ServeurAdresse, Integer.parseInt(server_port_str));
            BufferedReader buffSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //send get
            String header = "GET " + filepath + " HTTP/1.1\r\nHost: "+server_address_str+"\r\n\r\n";
            PrintWriter outSocket = new PrintWriter(socket.getOutputStream());
            outSocket.print(header);
            outSocket.flush();


            String reponse=buffSocket.readLine();
            if(reponse.split(" ").length>1 && !reponse.split(" ")[1].equals("200")){
                return -1;
            }

            while(!buffSocket.readLine().equals(""));
            GestionHttpClient.writeFile(buffSocket,file.getName(), 1212);


        } catch (UnknownHostException e) {
            e.printStackTrace();
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 2;
        }


        return 0;
    }
    public static int quitter(String server_address_str, String server_port_str) {
        try {
            InetAddress ServeurAdresse= InetAddress.getByName(server_address_str);
            Socket socket = new Socket(ServeurAdresse, Integer.parseInt(server_port_str));

            //send get
            String header = "HTTP/1.1\r\nConnection: close\r\n\r\n";
            PrintWriter outSocket = new PrintWriter(socket.getOutputStream());
            outSocket.print(header);
            outSocket.flush();
            return 0;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 2;
        }
    }

    public static int SendFile(String server_address_str, String server_port_str, String filepath) {
        try {
            File file = new File(filepath);
            InetAddress ServeurAdresse= InetAddress.getByName(server_address_str);
            Socket socket = new Socket(ServeurAdresse, Integer.parseInt(server_port_str));

            //PrintWriter outSocket = new PrintWriter(socket.getOutputStream());
            GestionHttpClient.sendFile(socket.getOutputStream(), filepath, file.getName());

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 2;
        }
        return 0;
    }

}
