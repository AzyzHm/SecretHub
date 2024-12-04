public class CaesarEncryption {
    public static String encrypt(String text, int shift) {
        shift = (shift % 26 + 26) % 26; // Normalize shift to be within 0-25
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                char encrypted = (char) ((character - base + shift) % 26 + base);
                result.append(encrypted);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int shift) {
        return encrypt(text, -shift); // Use negative shift for decryption
    }
}