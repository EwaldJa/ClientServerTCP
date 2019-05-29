package src.tools;

import java.io.BufferedReader;
import java.io.OutputStream;

public class GestionHttpServer extends GestionHttp {

    private final static String http_version_tag = "HTTP/1.1 ";

    public static int sendFile(OutputStream os, String filepath) {
        String header = http_version_tag + 200 + " OK\r\n";
        return GestionHttp.sendFile(os, filepath, header);
    }

    public static int writeFile(BufferedReader buff, String filename, int length) {

        return GestionHttp.writeFile(buff, "server"+filename, length);
    }
}
