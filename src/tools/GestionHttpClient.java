package src.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GestionHttpClient extends GestionHttp {

    public static int sendFile(PrintWriter pw, String filename) {
        //TODO créer le header
        String header = "PUT " + filename + " HTTP/1.1+\r\n";
        return GestionHttp.sendFile(pw, filename, header);
    }

    public static int writeFile(BufferedReader buff, String filename) {
        return GestionHttp.writeFile(buff, filename);
    }
}
