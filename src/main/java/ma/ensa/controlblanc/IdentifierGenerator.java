package ma.ensa.controlblanc;

import java.util.UUID;

import java.util.UUID;

public class IdentifierGenerator {
    public static String generateRandomIdentifier() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8);
    }

    public static void main(String[] args) {
        System.out.println("Identifiant aléatoire: " + generateRandomIdentifier());
    }
}
