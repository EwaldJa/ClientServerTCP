package src.tools;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class GestionHttpServer extends GestionHttp {

    private final static String http_version_tag = "HTTP/1.1 ";

    public static int sendFile(PrintWriter pw, String filename) {
        //TODO créer le header
        int res;
        String header = http_version_tag + 200 + " OK+\r\n";
        res = GestionHttp.sendFile(pw, filename, header);
        if (res != 0) {
            //TODO SEND AN ERROR REQUEST
        }
        return res;
    }

    public static int writeFile(BufferedReader buff, String filename) {
        return GestionHttp.writeFile(buff, filename);
    }
}
