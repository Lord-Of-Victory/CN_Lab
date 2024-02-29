
// package BitStuffing;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TCPClientBitStuffing {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    // constructor to put ip address and port
    public TCPClientBitStuffing(String address, int port) {
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
        String stuffedLine = "";
        // String anLine = "";//

        while (true) {
            try {
                // SEND to Server
                System.out.println("Enter data (in bits Format) >> ");
                // System.out.printf(" ");
                line = input.readLine();
                List<String> sendArray = inputToArray(line);
                // stuffedLine = byteStuffing(line);
                for (int i = 0; i < sendArray.size(); i++) {
                    String iStr = sendArray.get(i);
                    out.writeUTF(iStr);
                }
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

    public String bitStuffing(String inputData) {
        // Takes input of unstuffed data from user
        // Scanner sc=new Scanner(null)
        
        String data = inputData;
        int count = 0;
        String resultString = "";
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            if (ch == '1') {
                // count number of consecutive 1'resultString
                // in user'resultString data
                count++;
                if (count < 5)
                    resultString += ch;
                else {
                    // add one '0' after 5 consecutive 1'resultString
                    resultString = resultString + ch + '0';
                    count = 0;
                }
            } else {
                resultString += ch;
                count = 0;
            }
        }
        // add flag byte in the beginning
        // and end of stuffed data
        String flag = "01111110";
        resultString =  flag + resultString + flag;
        return resultString;
    }

    public List<String> inputToArray(String inputString) {
        List<String> strArray = new ArrayList<String>();
        int framesNumber = (int) (inputString.length() / 6);
        int rem = (inputString.length() % 6);
        if (rem > 0) {
            framesNumber += 1;
        }

        int index = 0;
        while (index < inputString.length()) {
            strArray.add(inputString.substring(index, Math.min(index + 6, inputString.length())));
            index += 6;
        }
        int lastIndex = framesNumber - 1;
        System.out.println(strArray.get(lastIndex));
        if (strArray.get(lastIndex).length() < 6) {
            String str = strArray.get(lastIndex);
            for (int i = 0; i < 6 - rem; i++) {
                str = str + '0';
            }
            strArray.set(lastIndex, str);
        }
        System.out.println(strArray);

        for (int i = 0; i < framesNumber; i++) {
            strArray.set(i, bitStuffing(strArray.get(i)));
        }
        System.out.println(strArray);
        return strArray;
    }

    public static void main(String args[]) {
        TCPClientBitStuffing client = new TCPClientBitStuffing("127.0.0.1", 5000);
    }
}