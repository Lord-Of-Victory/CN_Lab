import java.util.Scanner;

public class KeyValidityCheck {
    public String key() {
        Scanner KeyScan = new Scanner(System.in);
        String key;
        Boolean isValid;
        do {
            System.out.println("Enter 4 bit key: ");
            key = KeyScan.nextLine();
            if (key.length() != 4) {
                System.out.println("Key not entered Properly: Error Length not equal to 4");
            }
            for (int i = 0; i < key.length(); i++) {
                if (i > 1 || i < 0) {
                    if (key.charAt(i) == '1') {
                        isValid = true;
                    } else if (key.charAt(i) == '0') {
                        isValid = true;
                    } else {
                        isValid = false;
                        System.out.println("Key not entered Properly: Error wrong format: Enter Bits 1,0");
                        break;
                    }
                }
            }
        } while (key.length() != 4);
        // KeyScan.close();
        return key;
    }
}