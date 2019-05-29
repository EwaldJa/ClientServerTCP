package src.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class GestionHttpClient extends GestionHttp {

    public static int sendFile(PrintWriter pw, String filepath, String filename) {
        //TODO créer le header
        String header = "PUT " + filename + " HTTP/1.1\r\n";
        return GestionHttp.sendFile(pw, filepath, header);
    }

    public static int writeFile(BufferedReader buff, String filename, int length) {

        return GestionHttp.writeFile(buff, "client"+filename, length);
    }
}
