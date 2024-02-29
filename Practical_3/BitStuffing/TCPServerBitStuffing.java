import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class TCPServerBitStuffing {
    // initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor with port
    public TCPServerBitStuffing(int port) {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");
            socket = server.accept();
            System.out.println("Client accepted");
            // takes input from the client socket
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

            List<String> lineArray = new ArrayList<String>();
            String line = "";//

            // reads message from client until "END" is sent
            while (true) {
                try {
                    // READ From Client
                    Boolean flag = true;
                    do {
                        line = in.readUTF();
                        if (line.equals("/")) {
                            flag = false;
                            break;
                        }

                        if (line.equals("END")) {
                            flag = false;
                            lineArray.add(line);
                            break;
                        }
                        lineArray.add(line);
                    } while (flag);
                    System.out.println("Recieved from Client>> " + lineArray);
                    handleClientMessage(lineArray);
                    if (lineArray.get(lineArray.size() - 1).equals("END")) {//
                        break;//
                    } //
                    lineArray.clear();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
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

    public static String bitDestuffing(String stuffedData) {
        // Remove flag bytes
        stuffedData = stuffedData.substring(8, stuffedData.length() - 8);
        
        // Perform destuffing
        StringBuilder destuffedData = new StringBuilder();
        int count = 0;
        for (int i = 0; i < stuffedData.length(); i++) {
            char ch = stuffedData.charAt(i);
            if (ch == '1') {
                count++;
                destuffedData.append(ch);
                if (count == 5 && i != stuffedData.length() - 1) {
                    i++;
                    count = 0;
                }
            } else {
                count = 0;
                destuffedData.append(ch);
            }
        }
        return destuffedData.toString();
    }

    public void handleClientMessage(List<String> messages) {
        List<String> destuffedMessages = new ArrayList<>();
        for (String message : messages) {
            destuffedMessages.add(bitDestuffing(message));
        }
        System.out.println("Destuffed Message>> " + elemToStr(destuffedMessages));
    }

    public String elemToStr(List<String> destuffedMessages) {
        StringBuilder result = new StringBuilder();
        for (String str : destuffedMessages) {
            result.append(str); // Append each string
        }

        // Remove the last space
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }

        // Print the result
        return result.toString();
    }

    public static void main(String args[]) {
        TCPServerBitStuffing server = new TCPServerBitStuffing(5000);
    }
}