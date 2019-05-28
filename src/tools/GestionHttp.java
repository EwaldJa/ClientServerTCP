package src.tools;

import java.io.*;

public class GestionHttp {

    private final static String http_version_tag = "HTTP/1.1 ";
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
            payload = new String(baos.toByteArray(), "UTF-8");
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
            //TODO deal with the exception
            return 1;
        }
    }
    protected static int receiveFile(BufferedReader buff, String filename){
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
