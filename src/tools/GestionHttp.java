package src.tools;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class GestionHttp {

    private final static String content_length_tag = "Content-Length: ";
    private final static int byte_number_read = 2048;

    protected static int sendFile(PrintWriter pw, String filepath, String header){
        String payload;
        try{
            int totallength = 0;
            byte[] buff = new byte[byte_number_read];
            File file = new File(filepath);
            FileInputStream fo = new FileInputStream(file);
            int size = fo.read(buff);
            totallength+=size;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                baos.write(buff);
                while (size == byte_number_read) {
                    size = fo.read(buff);
                    totallength+=size;
                    baos.write(buff);
                }
            } catch (IOException e) {
                //TODO deal with the exception
            }
            payload = new String(baos.toByteArray(), StandardCharsets.US_ASCII);
            String contentLength = content_length_tag + totallength + "\r\n\r\n";
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
    protected static int writeFile(BufferedReader buff, String filename, int length){
        int writtenbyte = 0;
        try{
            String s = "";
            File file = new File(filename);
            FileOutputStream fo = new FileOutputStream(file);

            while (length >= writtenbyte) {
                s = buff.readLine();
                System.out.println("WriteFile : "+s);
                s+="\r\n";
                writtenbyte+=(s.getBytes().length);
                if (writtenbyte > length) {
                    break;                }
                fo.write(s.getBytes());
                fo.flush();
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
