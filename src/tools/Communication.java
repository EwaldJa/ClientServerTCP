package src.tools;


import java.io.*;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.ArrayList;

public class Communication implements Runnable {
	
	private Socket clt_socket;
	private ArrayList<String> hist_requetes_clt;
	private Logger mylogger;
	private BufferedReader in;
	private PrintWriter out;

	public Communication(Socket socket) {
		clt_socket = socket;
		hist_requetes_clt = new ArrayList<String>();
		mylogger = new Logger("defaultconfigCommunication.txt", "Communication port" + clt_socket.getLocalPort());
	}

	
	private boolean recevoir() {
		byte[] buffer = new byte[8192];
		DatagramPacket rec_dp = new DatagramPacket(buffer, 8192);
		try {
			ds_com.receive(rec_dp);
		} catch (IOException e) {
			mylogger.log(Logger.OFF, "Erreur lors de la réception d'un message client");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors de la réception d'un message client : ");
			//e.printStackTrace();
		}
		String rec_msg = "";
		try {
			rec_msg = new String (rec_dp.getData(), "utf-8");
			hist_requetes_clt.add(rec_msg);
			mylogger.log(Logger.DEBUG, "Message reçu :" + rec_msg);
		} catch (UnsupportedEncodingException e) {
			mylogger.log(Logger.OFF, "Erreur lors du décodage d'un message client");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors du d�codage d'un message client : ");
			//e.printStackTrace();
		}
		if (rec_msg == "DE") {
			return false;
		}
		else {
			System.out.println("Message client : " + rec_msg);
			return true;
		}
	}
	
	private void envoyer () {

	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clt_socket.getInputStream()));
		} catch (IOException e) {
			mylogger.log(Logger.OFF, "Erreur lors de l'initialisation de la réception");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
		}
		try {
			out = new PrintWriter(clt_socket.getOutputStream());
		} catch (IOException e) {
			mylogger.log(Logger.OFF, "Erreur lors de l'initialisation de l'émission");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
		}

		
		while (recevoir()) {
			envoyer();
		}
	}
	
	public void finalize() throws Throwable{
		mylogger.dispose();
		super.finalize();
	}
}
