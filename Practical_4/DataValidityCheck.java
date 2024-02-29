import java.util.Scanner;

public class DataValidityCheck {
    public String Data() {
        Scanner dataScan = new Scanner(System.in);
        String data;
        Boolean isValid;
        do {
            System.out.println("Enter 8 bit message: ");
            if (dataScan.hasNextLine()) { // Check if there is a next line available
                data = dataScan.nextLine();
            } else {
                // Handle the case where no input is available
                System.out.println("No input found. Please try again.");
                data = ""; // Set data to an empty string or handle it based on your requirements
            }
            isValid = true;
            if (data.length() != 8) {
                System.out.println("Data not entered Properly: Error Length not equal to 8");
                isValid = false;
            }
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) != '1' && data.charAt(i) != '0') {
                    isValid = false;
                    System.out.println("Data not entered Properly: Error wrong format: Enter Bits 1,0");
                    break;
                }
            }
        } while (!isValid);
        // dataScan.close();
        return data;
    }
}