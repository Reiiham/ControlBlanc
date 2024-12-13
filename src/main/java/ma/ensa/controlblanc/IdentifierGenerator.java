package ma.ensa.controlblanc;

import java.util.UUID;

public class IdentifierGenerator {
    public static UUID generateRandomIdentifier() {
        return UUID.randomUUID();
    }

    public static void main(String[] args) {
        System.out.println("Identifiant aléatoire: " + generateRandomIdentifier());
    }
}
