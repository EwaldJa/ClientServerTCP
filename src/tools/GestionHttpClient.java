package src.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

public class GestionHttpClient extends GestionHttp {

    public static int sendFile(BufferedOutputStream bos, File file) {
        String header = "PUT " + file.getName() + " HTTP/1.1\r\n";
        return GestionHttp.sendFile(bos, file, header);
    }

    public static int writeFile(BufferedInputStream bis, String filename, int length) {
        return GestionHttp.writeFile(bis, "client"+filename, length);
    }
}
