package src.tools;

import java.io.*;

public class GestionHttp {

    public static int sendFile(PrintWriter pw, String filename){
        try{
            byte[] buff = {};
            File file = new File(filename);
            FileInputStream fo = new FileInputStream(file);
            fo.read(buff);
            String s = new String(buff, "UTF-8");
            pw.println(s);
            pw.flush();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
            return 2;
        }

        catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
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
