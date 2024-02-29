import java.util.Scanner;

public class StringToByteExample {
    public static void main(String[] args) {
        String data;
        Scanner sc =new Scanner(System.in);
        data=sc.nextLine();
        byte[] byteData= data.getBytes();
        for (int i = 0; i < byteData.length; i++) {
            System.out.println(byteData[i]);
        }
        System.out.println(byteData);
        sc.close();
    }
}
