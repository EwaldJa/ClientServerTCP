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
        int requestreturn = 0;
		try {
			String line = in.readLine();
			System.out.println(line);

            String[] head = line.split(" ");
			System.out.println(head.length);
			String request = head[0];
			String filename = head[1];
			String httpVersion = head[2];

			if (!httpVersion.toLowerCase().equals("http/1.1")) {
			    sendError(505);
				System.out.println("ERREUR 505");
			    //in.reset();
			    return true;
            }

			String[] field;
			boolean headerskipped = false;
			while (!headerskipped) {
				line = in.readLine();
				System.out.println("HEADER : "+line);
				field = line.split(" ");
				if (field[0].equals("Connection:")) {
					closeConnection = (field[1].toLowerCase().equals("close"));
				}
				headerskipped = (line.equals(""));
			}
			switch (request) {
				case "GET":
					requestreturn = GestionHttpServer.sendFile(out, filename);
					break;
				case "PUT":
                    requestreturn = GestionHttpServer.writeFile(in, filename);
					break;
				default:
                    requestreturn = 400;
                    break;
			}

		} catch (IOException e) {
			mylogger.log(Logger.OFF, "Erreur lors de la réception");
			mylogger.log(Logger.IMPORTANT, e.getMessage());
			requestreturn = 500;
		} catch (NullPointerException e) {
			mylogger.log(Logger.INFO, "Erreur NullPointer à la réception");
			mylogger.log(Logger.DEBUG, e.getMessage());
			requestreturn = 500;
		}
        if (requestreturn != 0) {
            sendError(requestreturn);
            closeConnection = true;
        }
		return closeConnection;
	}
	
	private void sendError(int error_code) {
	    String error_name = "";
	    switch (error_code) {
            case 400:
                error_name = "Bad Request";
                break;
            case 404:
                error_name = "Not Found";
                break;
            case 500:
                error_name = "Internal Server Error";
                break;
            case 505:
                error_name = "HTTP Version not supported";
                break;
            default:
                error_code = 500;
                error_name = "Internal Server Error";
                break;
        }
        String request = "HTTP/1.1 " + error_code + error_name + "\r\n";
	    String connection = "Connection: close\r\n";
	    out.write(request + connection);
	    out.flush();
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
