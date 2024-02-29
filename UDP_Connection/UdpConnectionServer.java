import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.SocketException;
import java.util.Scanner;
import java.net.InetAddress;

public class UdpConnectionServer extends Thread{
    public static void main(String[] args) throws IOException,SocketException{
        DatagramSocket ds= new DatagramSocket(5000);
        byte[] recievedData = new byte[123456];

        DatagramPacket dpRecieved = null;
        Scanner sc= new Scanner(System.in);

        while(true){
            dpRecieved =new DatagramPacket(recievedData, recievedData.length);
            ds.receive(dpRecieved);
        
            System.out.println("Client >>"+ dataToString(recievedData));
            if (dataToString(recievedData).toString().equals("bye")) {
                System.out.println("Exiting");
                break;
            }
            recievedData = new byte[123456];

            InetAddress ip = InetAddress.getLocalHost();
            System.out.printf(">> ");
            String input=sc.nextLine();
            byte[] buff=input.getBytes();
            DatagramPacket dpSend = new DatagramPacket(buff,buff.length,ip,dpRecieved.getPort());
            ds.send(dpSend);
            if (input.equals("bye")) {
                System.out.println("Exiting");
                break;
            }

        }
        sc.close();
        ds.close();
    }

    public static StringBuilder dataToString(byte[] byteArray){
        if (byteArray == null) {
            return null;
        }
        StringBuilder res = new StringBuilder();
        int i = 0;
        while (byteArray[i]!=0) {
            res.append((char) byteArray[i]);
            i++;
        }
        return res;
    }
}