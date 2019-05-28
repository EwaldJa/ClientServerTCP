package src.tools;


import java.io.*;
import java.net.Socket;

public class Communication implements Runnable {
	
	private Socket clt_socket;
	private Logger mylogger;
	private BufferedReader in;
	private PrintWriter out;

	public Communication(Socket socket) {
		clt_socket = socket;
		mylogger = new Logger("defaultconfigCommunication.txt", "Communication port" + clt_socket.getLocalPort());
	}

	
	private boolean recevoir() {
		boolean closeConnection = false;
		try {
			String line = in.readLine();
			String request = "";
			int i = 0;
			while(line.charAt(i) != ' ') {
				request+=line.charAt(i);
				i++;
			}
			i++;
			String filename = "";
			while(line.charAt(i) != ' ') {
				filename+=line.charAt(i);
				i++;
			}
			if (filename == "") {
				filename = "index.html";
			}

			String[] field;
			boolean headerskipped = false;
			while (!headerskipped) {
				line = in.readLine();
				field = line.split(" ");
				if (field[0].equals("Connection:")) {
					closeConnection = (field[1].toLowerCase().equals("close"));
				}
				headerskipped = (line.equals(""));
			}

			switch (request) {
				case "GET":
					GestionHttpServer.sendFile(out, filename);
					break;
				case "PUT":
                    GestionHttpServer.writeFile(in, filename);
					break;
				default:
					break;
			}
		} catch (IOException e) {
			mylogger.log(Logger.OFF, "Erreur lors de la réception");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
		} catch (NullPointerException e) {
			mylogger.log(Logger.INFO, "Erreur NullPointer à la réception");
			mylogger.log(Logger.DEBUG, e.getMessage());
		}
		return closeConnection;
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

		while (recevoir()) {/*Reçoit et répond au client*/}
		try {
			in.close();
		} catch (IOException e) {
			mylogger.log(Logger.OFF, "Erreur à la fermeture du BufferedReader de la réception");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
		}
		out.close();
	}
	
	public void finalize() throws Throwable{
		mylogger.dispose();
		super.finalize();
	}
}
