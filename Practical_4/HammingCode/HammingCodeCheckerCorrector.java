public class HammingCodeCheckerCorrector {

    // Method to calculate the number of parity bits required
    public static int calculateParityBits(int dataSize) {
        int m = 0;
        while (Math.pow(2, m) < (dataSize + m + 1)) {
            m++;
        }
        return m;
    }

    // Method to calculate parity bit
    public static int calculateParity(int[] hammingCode, int parityIndex) {
        int parity = 0;
        for (int i = parityIndex; i < hammingCode.length; i++) {
            if (((i + 1) & (parityIndex + 1)) != 0) {
                parity ^= hammingCode[i];
            }
        }
        return parity;
    }

    // Method to check and correct Hamming code
    public static int[] checkAndCorrect(String data) {
        int received[]=new int[data.length()];
        for (int i = 0; i < data.length(); i++) {
            received[i]=Character.getNumericValue(data.charAt(i));
        }
        int parityBits = calculateParityBits(received.length);
        int[] errorPositions = new int[parityBits];
        int errorIndex = 0;
        for (int i = 0; i < parityBits; i++) {
            int parityIndex = (int) Math.pow(2, i) - 1;
            int parity = calculateParity(received, parityIndex);
            if (parity != 0) {
                errorPositions[errorIndex++] = parityIndex;
            }
        }
        if (errorIndex != 0) {
            int errorPosition = 0;
            for (int i = 0; i < errorIndex; i++) {
                errorPosition += errorPositions[i];
            }
            if (errorPosition < received.length) {
                received[errorPosition] ^= 1;
                System.out.println("Error detected and corrected at bit position " + errorPosition);
                return received;
            } else {
                System.out.println("Error detected but cannot be corrected");
            }
        } else {
            System.out.println("No error detected");
            return received;
        }
        return received;
    }

    public static String decodeHammingCode(int[] received) {
        int parityBits = calculateParityBits(received.length);
        int[] decodedData = new int[received.length - parityBits+1];
        int j = 0;
        for (int i = 0; i < received.length; i++) {
            if ((i + 1 & i) != 0) { // Skip parity bits
                // System.out.print(received[i]);
                decodedData[j++] = received[i];
            }
        }
        StringBuilder decodeDataStr=new StringBuilder();
        for (int i = 0; i < decodedData.length; i++) {
            decodeDataStr.append(decodedData[i]);
        }
        return decodeDataStr.toString();
    }
}
