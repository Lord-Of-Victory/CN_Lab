import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpConnectionClient{
    public static void main(String[] args) throws IOException{
        Scanner sc = new Scanner(System.in);

        DatagramSocket dataSoc = new DatagramSocket();
        InetAddress ip = InetAddress.getLocalHost();

        byte buff[] = null;

        while(true){
            System.out.printf(">> ");
            String input=sc.nextLine();
            buff=input.getBytes();
            DatagramPacket dpSend= new DatagramPacket(buff,buff.length,ip,5000);
            dataSoc.send(dpSend);

            if(input.equals("bye")){
                System.out.println("Exiting");
                break;
            }

            byte[] recievedData = new byte[123456];
            DatagramPacket dpRecieved =new DatagramPacket(recievedData, recievedData.length);
            dataSoc.receive(dpRecieved);
        
            System.out.println("Server>> "+ dataToString(recievedData));
            if (dataToString(recievedData).toString().equals("bye")) {
                System.out.println("Exiting");
                break;
            }

        }
        dataSoc.close();
        sc.close();
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
