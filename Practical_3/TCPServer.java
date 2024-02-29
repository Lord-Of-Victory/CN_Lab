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

            // Initializing another input and out to send Messages
            // DataInputStream input = new DataInputStream(System.in); //
            // DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            ;//
            String line = "";
            // String anLine = "";//

            // reads message from client until "END" is sent
            while (true) {
                try {
                    // READ From Client
                    line = in.readUTF();
                    System.out.println("Client>> " + line);
                    if (line.equals("END")) {//
                        break;//
                    } //
                    //   // SEND to Client
                    // System.out.printf(">> ");//
                    // anLine = input.readLine();//
                    // out.writeUTF(anLine);//
                    // if (anLine.equals("END")) {//
                    //     break;//
                    // } //
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