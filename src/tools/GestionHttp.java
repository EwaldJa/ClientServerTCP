package src.tools;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GestionHttp {

    private final static String content_length_tag = "Content-Length: ";

    protected static int sendFile(PrintWriter pw, String filename, String header){
        String payload;
        try{
            byte[] buff = new byte[512];
            File file = new File(filename);
            FileInputStream fo = new FileInputStream(file);
            int size = fo.read(buff);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                baos.write(buff);
                while (size == 512) {
                    size = fo.read(buff);
                    baos.write(buff);
                }
            } catch (IOException e) {
                //TODO deal with the exception
            }
            payload = new String(baos.toByteArray(), StandardCharsets.US_ASCII);
            String contentLength = content_length_tag + payload.length() + "\r\n";
            String totalRequest = header + contentLength + payload;
            pw.print(totalRequest);
            pw.flush();
            return 0;
        }
        catch(FileNotFoundException e) {
            return 404;
        }
        catch (IOException e) {
            return 500;
        }
    }
    protected static int writeFile(BufferedReader buff, String filename){
        try{
            String s = "";
            File file = new File(filename);
            FileOutputStream fo = new FileOutputStream(file);

            while((s = buff.readLine()).isEmpty()) {
                System.out.println("WriteFile : "+s);
                s+="\r\n";
                fo.write(s.getBytes());
            }
            fo.close();

        }catch(FileNotFoundException e){
            System.out.println("erreur 404");
            System.out.println(e.getMessage());
            return 404;
        }catch(IOException e){
            System.out.println("erreur 500");
            System.out.println(e.getMessage());
            return 500;
        }
        System.out.println("coucou2");
        return 0;

    }

}
