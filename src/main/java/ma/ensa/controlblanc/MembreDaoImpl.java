package ma.ensa.controlblanc;

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
    public boolean insere (Membre m){
        String sql = "INSERT INTO membre (identifiant, nom, prenom, email,phone ) VALUES (?, ?, ?,?,?)";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getIdentifiant());
            ps.setString(2, m.getNom());
            ps.setString(1, m.getPrenom());
            ps.setString(1, m.getEmail());
            ps.setString(1, m.getPhone());
            return ps.executeUpdate() > 0;
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
}
