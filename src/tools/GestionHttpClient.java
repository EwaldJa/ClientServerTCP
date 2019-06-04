package src.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class GestionHttpClient extends GestionHttp {

    public static int sendFile(BufferedOutputStream bos, String filepath, String filename) {
        String header = "PUT " + filename + " HTTP/1.1\r\n";
        return GestionHttp.sendFile(bos, filepath, header);
    }

    public static int writeFile(BufferedInputStream bis, String filename, int length) {
        return GestionHttp.writeFile(bis, "client"+filename, length);
    }
}
