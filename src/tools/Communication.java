package src.tools;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Communication implements Runnable {
	
	private DatagramSocket ds_com;
	private DatagramPacket dp_clt;
	private ArrayList<String> hist_msg_clt;
	private String message;
	private Logger mylogger;
	
	public Communication(InetAddress clt_adr, int clt_port, String server_message) throws SocketException {
		ds_com = new DatagramSocket();
		byte[] buffer = new byte[8192];
		dp_clt = new DatagramPacket(buffer, 8192, clt_adr, clt_port);
		hist_msg_clt = new ArrayList<String>();
		message = server_message;
		mylogger = new Logger("defaultconfigCommunication.txt", "Communication port" + ds_com.getLocalPort());
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
			hist_msg_clt.add(rec_msg);
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
	
	private void envoyer (String message, DatagramPacket dp_client) {
		mylogger.log(Logger.DEBUG, "Envoi du message '" + message + "' au client @" + dp_client.getAddress().toString() + ":" + dp_client.getPort());
		dp_client.setData(message.getBytes());
		try {
			ds_com.send(dp_client);
		} catch (IOException e) {
			mylogger.log(Logger.OFF, "Erreur lors de la réponse au client");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors de la réponse au client : ");
			//e.printStackTrace();
		}
	}

	@Override
	public void run() {
		
		String init = "Initialisation de la communication";
		dp_clt.setData(init.getBytes());
		try {
			ds_com.send(dp_clt);
		} catch (IOException e) {
			mylogger.log(Logger.OFF, "Erreur lors de l'initialisation de la communication");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
			//System.err.println("Erreur lors de l'initialisation de la communication : ");
			//e.printStackTrace();
		}
		
		while (recevoir()) {
			envoyer(message, dp_clt);
		}
		System.out.println("Fermeture de la communication, messages du client : ");
		mylogger.log(Logger.INFO, "Fermeture de la communication");
		System.out.println(hist_msg_clt);
		mylogger.log(Logger.DEBUG, "Messages du client + " + hist_msg_clt.toString());
		ds_com.close();
		mylogger.dispose();
	}
	
	public void finalize() throws Throwable{
		mylogger.dispose();
		super.finalize();
	}
}
