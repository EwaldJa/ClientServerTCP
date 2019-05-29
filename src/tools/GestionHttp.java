package src.tools;

import java.io.*;

public class GestionHttp {

    private final static String content_length_tag = "Content-Length: ";
    private final static int byte_number_read = 2048;

    protected static int sendFile(OutputStream os, String filepath, String header){
        try{
            int totallength = 0;
            byte[] buff = new byte[byte_number_read];
            File file = new File(filepath);
            FileInputStream fo = new FileInputStream(file);
            int size = fo.read(buff);
            totallength+=size;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.out.println(" SendFile 1");
            try {
                baos.write(buff);
                while (size == byte_number_read) {
                    size = fo.read(buff);
                    totallength+=size;
                    baos.write(buff);
                    System.out.println(" SendFile 2");
                }
            } catch (IOException e) {
                //TODO deal with the exception
            }
            fo.close();
            System.out.println(" SendFile 3");
            String contentLength = content_length_tag + totallength + "\r\n\r\n";
            String totalRequest = header + contentLength;// + payload;
            os.write(totalRequest.getBytes());
            os.flush();
            os.write(baos.toByteArray());
            os.flush();
            os.close();
            System.out.println("fin SendFile");
            return 0;
        }
        catch(FileNotFoundException e) {
            return 404;
        }
        catch (IOException e) {
            return 500;
        }
    }
    protected static int writeFile(InputStream in, String filename, int length){
        int writtenbyte = 0;
        try{
            String s = "";
            File file = new File(filename);
            FileOutputStream fo = new FileOutputStream(file);

            while (length >= writtenbyte) {
                int read = in.read();
                System.out.println("octet :" + read);
                if (read == -1) {
                    break;
                } else {
                    fo.write(read);
                    fo.flush();
                }

                /*
                s = in.readLine();
                fo.write(s.getBytes());
                fo.flush();
                writtenbyte+=(s.getBytes().length + "\r\n".getBytes().length);
                if (writtenbyte >= length) {
                    break;
                }
                fo.write("\r\n".getBytes());
                fo.flush();
                */
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
