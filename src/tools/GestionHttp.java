package src.tools;

import java.io.*;

public class GestionHttp {

    private String http_version = "HTTP/1.1 ";
    private String connection = "Connection: ";
    private String keep_alive = "Keep-Alive: ";

    public static int sendFile(PrintWriter pw, String filename, int request_number){
        String s;
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
            s = new String(baos.toByteArray(), "UTF-8");
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
            return 2;
        }

        catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
        //TODO create the header
        pw.println(s);
        pw.flush();
        return 0;
    }
    public static int receiveFile(BufferedReader buff, String filename){
        try{
            String s = "";
            File file = new File(filename);
            FileOutputStream fo = new FileOutputStream(file);
            while((s = buff.readLine())!=null) {
                fo.write(s.getBytes());
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return 2;
        }catch(IOException e){
            e.printStackTrace();
            return 1;
        }
        return 0;

    }

}
