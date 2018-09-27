package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Controlador extends Thread {
	private Socket clientSocket;

	public Controlador(Socket clientSocket) {
                System.out.println("Connection Received");
		this.clientSocket = clientSocket;
               
	}

	public void run() {
		BufferedInputStream in;
		BufferedOutputStream out;

		try {
			in = new BufferedInputStream(clientSocket.getInputStream());
			out = new BufferedOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}
                
                new Juego(in, out);

		try {
			out.close();
			in.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
