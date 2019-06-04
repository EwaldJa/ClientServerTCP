package src;

import src.tools.GestionHttpClient;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LocalClient {

    public static final int transfer_successful = 0;
    public static final int local_error = 10;
    public static final int unknown_server = 20;
    public static final int succesfully_turned_off = 1000;

    private Socket socket;
    private String server_address;
    private String server_port;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;

    public LocalClient(String server_address_str, String server_port_str) throws Exception {
        server_address = server_address_str;
        server_port= server_port_str;
        socket = new Socket(InetAddress.getByName(server_address), Integer.parseInt(server_port));
        bos = new BufferedOutputStream(socket.getOutputStream());
        bis = new BufferedInputStream(socket.getInputStream());
    }

    public int ReceiveFile(String server_address_str, String server_port_str, String filepath) {
        try {
            File file = new File(filepath);
            verifSocket(server_address_str, server_port_str);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String header = "GET " + filepath + " HTTP/1.1\r\nHost: "+server_address_str+"\r\n\r\n";
            out.print(header);
            out.flush();

            BufferedReader buffSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            int reponse = recevoir(buffSocket.readLine());
            if (reponse != 200) {
                return reponse;
            }

            while(!buffSocket.readLine().equals(""));
            GestionHttpClient.writeFile(bis,file.getName(), 1212);
            return transfer_successful;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return unknown_server;
        } catch (IOException e) {
            e.printStackTrace();
            return local_error;
        }
    }

    public int quitter(String server_address_str, String server_port_str) {
        try {
            verifSocket(server_address_str, server_port_str);

            String header = "HTTP/1.1\r\nConnection: close\r\n\r\n";
            PrintWriter outSocket = new PrintWriter(socket.getOutputStream());
            outSocket.print(header);
            outSocket.flush();
            socket.close();
            return succesfully_turned_off;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return unknown_server;
        } catch (IOException e) {
            e.printStackTrace();
            return local_error;
        }
    }

    public int SendFile(String server_address_str, String server_port_str, String filepath) {
        try {
            File file = new File(filepath);
            verifSocket(server_address_str, server_port_str);
            System.out.println("appel sendfile");
            int res = GestionHttpClient.sendFile(bos, filepath, file.getName());
            if (res != 0) return res;
            System.out.println("sendfile fait : " + res);

            BufferedReader buffSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return recevoir(buffSocket.readLine());
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return unknown_server;
        } catch (IOException e) {
            e.printStackTrace();
            return local_error;
        }
    }

    private int recevoir(String reponse) {
        System.out.println("reponse : " + reponse);
        if (reponse != null) {
            String[] splittedresponse = reponse.split(" ");
            if (splittedresponse.length > 1) {
                return Integer.parseInt(splittedresponse[1]);
            }
        }
        return transfer_successful;
    }

    private void verifSocket(String server_address_str, String server_port_str) throws UnknownHostException, IOException {
        if ( (server_address != server_address_str) || (server_port != server_port_str) ) {
            socket = new Socket(InetAddress.getByName(server_address_str), Integer.parseInt(server_port_str));
            bos = new BufferedOutputStream(socket.getOutputStream());
            bis = new BufferedInputStream(socket.getInputStream());
        }
    }

}
