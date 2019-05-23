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
		try {
			String line = in.readLine();
			String request = "";
			int i = 0;
			while(line.charAt(i) != ' ') {
				request+=line.charAt(i);
				i++;
			}
			i+=2;
			String filename = "";
			while(line.charAt(i) != ' ') {
				filename+=line.charAt(i);
				i++;
			}
			if (filename == "") {
				filename = "index.html";
			}
			switch (request) {
				case "GET":
					GestionHttp.sendFile(out, filename);
					break;
				case "PUT":
					GestionHttp.receiveFile(in, filename);
					break;
				default:
					break;
			}
		} catch (IOException e) {
			//TODO deal with the exception
		} catch (NullPointerException e) {
			//TODO deal with the exception
		}
		return false;
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
