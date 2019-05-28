package src;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LocalClient {
    private int port;
    public static int ReceiveFile(String server_address_str, String server_port_str, String filepath) {
        try {
            InetAddress ServeurAdresse= InetAddress.getByName(server_address_str);
            Socket socket = new Socket(ServeurAdresse, Integer.parseInt(server_port_str));


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return 0;
    }

    public static int SendFile(String server_address_str, String server_port_str, String filepath) {
        try {
            InetAddress ServeurAdresse= InetAddress.getByName(server_address_str);
            Socket socket = new Socket(ServeurAdresse, Integer.parseInt(server_port_str));


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
