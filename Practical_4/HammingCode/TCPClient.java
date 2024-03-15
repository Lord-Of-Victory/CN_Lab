
// package TCP_Connection;
import java.io.*;
import java.net.*;

public class TCPClient {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    // constructor to put ip address and port
    public TCPClient(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");
            // takes input from terminal
            input = new DataInputStream(System.in);
            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException u) {
            System.out.println(u);
            return;
        } catch (IOException i) {
            System.out.println(i);
            return;
        }
        String line = "";
        // String anLine = "";//

        while (true) {
            try {
                // SEND to Server
                System.out.printf(">> ");
                // line = input.readLine();
                // List<String> sendArray=inputToArray(line);
                // stuffedLine = byteStuffing(line);
                // for (int i = 0; i < sendArray.size(); i++) {
                //     String iStr=sendArray.get(i);
                //     out.writeUTF(iStr);
                // }
                // KeyValidityCheck KeyValChk = new KeyValidityCheck();
                // String key = KeyValChk.key();
                DataValidityCheck dataValChk = new DataValidityCheck();
                String data = dataValChk.Data();
                String hammingCode= HammingCodeGenerator.generateHammingCode(data);
                out.writeUTF(hammingCode);
                if (line.equals("END")) {//
                    break;//
                } //

                // // READ From Server
                // DataInputStream in = new DataInputStream(new
                // BufferedInputStream(socket.getInputStream()));//
                // anLine = in.readUTF();//
                // System.out.println("Server>> " + anLine);//
                // if (anLine.equals("END")) {//
                // break;//
                // } //

            } catch (IOException i) {
                System.out.println(i);
            }
        }
        System.out.println("-----Connection Ended-----");
        // close the connection
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) {
        TCPClient client = new TCPClient("127.0.0.1", 5000);
    }
}