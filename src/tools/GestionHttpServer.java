package src.tools;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class GestionHttpServer extends GestionHttp {

    private final static String http_version_tag = "HTTP/1.1 ";

    public static int sendFile(PrintWriter pw, String filename) {
        String header = http_version_tag + 200 + " OK+\r\n";
        return GestionHttp.sendFile(pw, filename, header);
    }

    public static int writeFile(BufferedReader buff, String filename) {
        String filepath = "server/" + filename;
        return GestionHttp.writeFile(buff, filepath);
    }
}
