package src.tools;

import java.io.BufferedReader;
import java.io.PrintWriter;

public class GestionHttpClient extends GestionHttp {

    public static int sendFile(PrintWriter pw, String filename) {
        //TODO créer le header
        String header = "";
        return GestionHttp.sendFile(pw, filename, header);
    }

    public static int receiveFile(BufferedReader buff, String filename) {
        return GestionHttp.receiveFile(buff, filename);
    }
}
