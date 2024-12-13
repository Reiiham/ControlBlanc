package ma.ensa.controlblanc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

public class IncidentDaoImpl implements IncidentDao {
    private Connection connection;
    //constructeur
    public IncidentDaoImpl (Connection connection) {
        this.connection = connection;
    }
    @Override
    public int inserer(Incident i) {
        String sql = "INSERT INTO incident (reference, time, status) VALUES (?, ?, ?)";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, i.getReference());
            //ps.setDate(2, i.getTime());
            ps.setTimestamp(2, i.getTime());
            ps.setString(3, i.getStatus());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void inser(Set<Incident> incidents) {
        String sql = "INSERT INTO incident (reference, time, status) VALUES (?, ?, ?)";
        try (Connection conn = DataBase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            //looping through the set
            for (Incident i : incidents) {
                ps.setString(1, i.getReference());
                ps.setTimestamp(2, i.getTime());
                ps.setString(3, i.getStatus());
                ps.addBatch(); // Add to batch
            }
            ps.executeBatch(); // Execute batch update
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

