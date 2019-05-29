package src.tools;

import java.io.BufferedReader;
import java.io.OutputStream;

public class GestionHttpClient extends GestionHttp {

    public static int sendFile(OutputStream os, String filepath, String filename) {
        String header = "PUT " + filename + " HTTP/1.1\r\n";
        return GestionHttp.sendFile(os, filepath, header);
    }

    public static int writeFile(BufferedReader buff, String filename, int length) {
        return GestionHttp.writeFile(buff, "client"+filename, length);
    }
}
