package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) throws IOException {
        boolean listening = true;
        
        int port = 8888;
        
        String host = InetAddress.getLocalHost().getHostAddress();
        /*ARRIBA ESTA LA CONEXIÓN QLA QUE TOMA EL HOST ADDRESS*/
        
        
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("USAGE: java Server [hostname] [port] ");
                System.exit(0);
            }
            host = args[0];
            if (args[0].equalsIgnoreCase("-h") || args[0].equalsIgnoreCase("-help")) {
                System.out.println("USAGE: java Server [hostname] [port] ");
                System.exit(1);
            }
        }
        try {
            
            InetAddress addr = InetAddress.getByName(host);
            ServerSocket serversocket = new ServerSocket(port, 1000, addr);
            while (listening) {    
                System.out.println("La conexión se estableció en el servidor "+host+" : "+port+"...");
                Socket clientsocket = serversocket.accept();
               (new Controlador(clientsocket)).start();
            }
            serversocket.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}