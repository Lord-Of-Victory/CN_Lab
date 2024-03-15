public class HammingCodeGenerator {
    public static String generateHammingCode(String pureData) {
        int data[]=new int[pureData.length()];
        for (int i = 0; i < pureData.length(); i++) {
            data[i]=Character.getNumericValue(pureData.charAt(i));
        }
        int m = data.length; // Number of data bits
        int r = calculateParityBitCount(m); // Number of parity bits
        
        // Total number of bits in the Hamming code
        int n = m + r;
        
        // Create an array to store the Hamming code
        int[] hammingCode = new int[n];
        
        // Copy data bits to Hamming code, leaving space for parity bits
        int dataIndex = 0; // Index for iterating over data bits
        for (int i = 0; i < n; i++) {
            // Check if the current index is a power of 2 (parity bit position)
            if (!isPowerOfTwo(i + 1)) {
                // If not a parity bit position, copy data bit
                hammingCode[i] = data[dataIndex];
                dataIndex++;
            }
        }
        
        // Calculate parity bits
        for (int i = 0; i < r; i++) {
            int parityBitIndex = (int) Math.pow(2, i) - 1; // Index of parity bit
            
            // Calculate parity bit value
            hammingCode[parityBitIndex] = calculateParityBit(hammingCode, parityBitIndex);
        }
        
        StringBuilder hammingCodeString=new StringBuilder();
        for (int i = 0; i < hammingCode.length; i++) {
            hammingCodeString.append(hammingCode[i]);
        }
        return hammingCodeString.toString();
    }

    // Method to calculate the number of parity bits required for given number of data bits
    public static int calculateParityBitCount(int dataBitCount) {
        int r = 0; // Number of parity bits
        
        // Calculate the number of parity bits required
        while (Math.pow(2, r) < (dataBitCount + r + 1)) {
            r++;
        }
        
        return r;
    }

    // Method to calculate the parity bit value
    public static int calculateParityBit(int[] hammingCode, int parityBitIndex) {
        int parity = 0;
        int n = hammingCode.length;
        
        // Calculate the XOR of all bits covered by this parity bit
        for (int i = parityBitIndex; i < n; i += 2 * (parityBitIndex + 1)) {
            for (int j = i; j < Math.min(i + parityBitIndex + 1, n); j++) {
                parity ^= hammingCode[j];
            }
        }
        
        return parity;
    }

    // Method to check if a number is a power of two
    public static boolean isPowerOfTwo(int num) {
        return num != 0 && (num & (num - 1)) == 0;
    }
}
