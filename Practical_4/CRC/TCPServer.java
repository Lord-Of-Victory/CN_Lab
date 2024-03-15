import java.net.*;
// import java.util.Scanner;
import java.io.*;

public class TCPServer {
    // initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor with port
    public TCPServer(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");
            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            String data = "";

            // reads message from client until "END" is sent
            while (true) {
                try {
                    // READ From Client
                    data = in.readUTF();
                    System.out.println("Client>> Data:" + data);
                    String key;
                    key = in.readUTF();
                    System.out.println("Client>> Key:" + key);
                    CRC.Receiver(data + CRC.Mod2Div(data + new String(new char[key.length() - 1]).replace("\0", "0"), key), key);
                    if (data.equals("END")) {//
                        break;//
                    } //
                } catch (IOException i) {
                    System.out.println(i);
                }
            }
            System.out.println("-----Closing connection-----");
            // close connection
            socket.close();
            in.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        TCPServer server = new TCPServer(5000);
    }
}