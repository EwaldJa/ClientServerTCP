package src.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class GestionHttpServer extends GestionHttp {

    private final static String http_version_tag = "HTTP/1.1 ";

    public static int sendFile(BufferedOutputStream bos, String filepath) {
        String header = http_version_tag + 200 + " OK\r\n";
        return GestionHttp.sendFile(bos, filepath, header);
    }

    public static int writeFile(BufferedInputStream bis, String filename, int length) {

        return GestionHttp.writeFile(bis, "server"+filename, length);
    }
}
