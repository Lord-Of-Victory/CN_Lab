import java.net.*;
import java.util.ArrayList;
import java.util.List;
// import java.util.Scanner;
import java.io.*;

public class TCPServerByteStuffing {
    // initialize socket and input stream
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor with port
    public TCPServerByteStuffing(int port) {
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

    public String byteDestuffing(String inputData) {
        String data = inputData;
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            // If 'E' is encountered, skip it and check the next character
            if (data.charAt(i) == 'E') {
                i++;
                // If 'F' is encountered after 'E', then add 'F' to the result
                if (i < data.length() && data.charAt(i) == 'F') {
                    res.append('F');
                }
                // If 'E' is encountered after 'E', then add 'E' to the result
                else if (i < data.length() && data.charAt(i) == 'E') {
                    res.append('E');
                }
            }
            // If 'F' is encountered, skip it
            else if (data.charAt(i) == 'F') {
                continue;
            }
            // Otherwise, add the character to the result
            else {
                res.append(data.charAt(i));
            }
        }
        return res.toString();
    }

    public void handleClientMessage(List<String> messages) {
        List<String> destuffedMessages = new ArrayList<>();
        for (String message : messages) {
            destuffedMessages.add(byteDestuffing(message));
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
        TCPServerByteStuffing server = new TCPServerByteStuffing(5000);
    }
}