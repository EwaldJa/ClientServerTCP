package src;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LocalClient {
    public static final int transfer_successful = 0;
    public static int ReceiveFile(String server_address_str, String server_port_str, String filepath) {
        try {
            InetAddress ServeurAdresse= InetAddress.getByName(server_address_str);
            Socket socket = new Socket(ServeurAdresse, Integer.parseInt(server_port_str));


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
            socket.getInputStream();


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
