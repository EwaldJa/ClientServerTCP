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
            InetAddress ServeurAdresse= InetAddress.getByName(server_address_str);
            Socket socket = new Socket(ServeurAdresse, Integer.parseInt(server_port_str));
            BufferedReader buffSocket=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //TODO send get



            //TODO read response

            GestionHttpClient.writeFile(buffSocket,filepath);

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 2;
        }


        return 0;
    }

    public static int SendFile(String server_address_str, String server_port_str, String filepath) {
        try {
            File file = new File(filepath);
            InetAddress ServeurAdresse= InetAddress.getByName(server_address_str);
            Socket socket = new Socket(ServeurAdresse, Integer.parseInt(server_port_str));
            //socket.getInputStream();



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
