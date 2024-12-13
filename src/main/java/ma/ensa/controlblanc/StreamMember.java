package ma.ensa.controlblanc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StreamMember {

    // Method to filter duplicates based on nom, prenom, email, and phone
    public Set<Membre> chargerListeMembre(String nomFichier) {
        Set<Membre> membres = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                // Ignore empty lines
                if (ligne.trim().isEmpty()) continue;

                // Split the line into columns
                String[] colonnes = ligne.split(",");

                // Ensure the line has 4 columns
                if (colonnes.length != 4) {
                    System.err.println("Ligne ignorée : " + ligne);
                    continue;
                }

                // Extract values
                String nom = colonnes[0].trim();
                String prenom = colonnes[1].trim();
                String email = colonnes[2].trim();
                String phone = colonnes[3].trim();

                // Validate data
                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$") || !phone.matches("\\d{10}")) {
                    System.err.println("Ligne ignorée (données invalides) : " + ligne);
                    continue;
                }

                // Create a Membre object
                Membre membre = new Membre(IdentifierGenerator.generateRandomIdentifier(), nom, prenom, email, phone, null);

                // Use a composite key to filter duplicates based on nom, prenom, email, and phone
                MembreKey key = new MembreKey(nom, prenom, email, phone);

                // Use a map to eliminate duplicates
                membres = membres.stream()
                        .filter(m -> !new MembreKey(m.getNom(), m.getPrenom(), m.getEmail(), m.getPhone()).equals(key))
                        .collect(Collectors.toSet());

                // Add to the set if not a duplicate
                membres.add(membre);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return membres;
    }

    private static class MembreKey {
        private final String nom;
        private final String prenom;
        private final String email;
        private final String phone;

        public MembreKey(String nom, String prenom, String email, String phone) {
            this.nom = nom;
            this.prenom = prenom;
            this.email = email;
            this.phone = phone;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            MembreKey that = (MembreKey) obj;
            return nom.equals(that.nom) &&
                    prenom.equals(that.prenom) &&
                    email.equals(that.email) &&
                    phone.equals(that.phone);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nom, prenom, email, phone);
        }
    }
}

