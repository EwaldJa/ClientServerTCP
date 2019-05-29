package src.tools;

import java.io.InputStream;
import java.io.OutputStream;

public class GestionHttpClient extends GestionHttp {

    public static int sendFile(OutputStream os, String filepath, String filename) {
        String header = "PUT " + filename + " HTTP/1.1\r\n";
        return GestionHttp.sendFile(os, filepath, header);
    }

    public static int writeFile(InputStream in, String filename, int length) {
        return GestionHttp.writeFile(in, "client"+filename, length);
    }
}
