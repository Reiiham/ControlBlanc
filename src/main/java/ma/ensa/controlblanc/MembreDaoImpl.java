package ma.ensa.controlblanc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class MembreDaoImpl implements MembreDao {
    private Connection connection;
    //constructeur
    public MembreDaoImpl (Connection connection) {
        this.connection = connection;
    }
    @Override
    public boolean insere(Membre m) {
        String sql = "INSERT INTO membre (identifiant, nom, prenom, email, phone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, m.getIdentifiant());  // Set identifiant
            ps.setString(2, m.getNom());          // Set nom
            ps.setString(3, m.getPrenom());       // Set prenom
            ps.setString(4, m.getEmail());        // Set email
            ps.setString(5, m.getPhone());        // Set phone

            return ps.executeUpdate() > 0;         // Execute update and return success status
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    @Override

    public Set<Incident> chargerListIncidents(int membreId) {
        String sql = "SELECT reference, time, status FROM incident WHERE membre_id = ?";
        Set<Incident> incidents = new HashSet<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, membreId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Incident incident = new Incident();
                    incident.setReference(rs.getString("reference"));
                    incident.setTime(rs.getTimestamp("time"));
                    incident.setStatus(rs.getString("status"));
                    incidents.add(incident);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incidents;
    }
    public Set<Membre> chargerListeMembre(String nomFichier) {
        Set<Membre> membres = new HashSet<>();
        int lignesLues = 0;
        int lignesIgnorées = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;

            while ((ligne = reader.readLine()) != null) {
                lignesLues++;

                // Ignorer les lignes vides
                if (ligne.trim().isEmpty()) {
                    lignesIgnorées++;
                    continue;
                }

                // Diviser la ligne en colonnes
                String[] colonnes = ligne.split(",");

                // Vérifier que la ligne a bien 4 colonnes
                if (colonnes.length != 4) {
                    System.err.println("Ligne ignorée (format incorrect) : " + ligne);
                    lignesIgnorées++;
                    continue;
                }

                // Extraire les valeurs
                String nom = colonnes[0].trim();
                String prenom = colonnes[1].trim();
                String email = colonnes[2].trim();
                String phone = colonnes[3].trim();

                // Valider les données
                if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$") || !phone.matches("\\d{10}")) {
                    System.err.println("Ligne ignorée (données invalides) : " + ligne);
                    lignesIgnorées++;
                    continue;
                }

                // Générer un identifiant
                String identifiant = IdentifierGenerator.generateRandomIdentifier();

                // Créer un membre
                Membre membre = new Membre(identifiant, nom, prenom, email, phone, null);

                // Ajouter au Set (ignorer les doublons automatiquement)
                if (!membres.add(membre)) {
                    System.err.println("Doublon détecté : " + ligne);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Affichage des statistiques
        System.out.println("Total de lignes lues : " + lignesLues);
        System.out.println("Lignes ignorées : " + lignesIgnorées);
        System.out.println("Membres uniques ajoutés : " + membres.size());

        return membres;
    }

}
