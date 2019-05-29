package src.tools;

import java.io.InputStream;
import java.io.OutputStream;

public class GestionHttpServer extends GestionHttp {

    private final static String http_version_tag = "HTTP/1.1 ";

    public static int sendFile(OutputStream os, String filepath) {
        String header = http_version_tag + 200 + " OK\r\n";
        return GestionHttp.sendFile(os, filepath, header);
    }

    public static int writeFile(InputStream in, String filename, int length) {

        return GestionHttp.writeFile(in, "server"+filename, length);
    }
}
