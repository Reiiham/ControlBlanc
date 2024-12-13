package ma.ensa.controlblanc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private static Connection con ;
    private static final String URL = "jdbc:mysql://localhost:3306/controlblanc";
    private static final String USER = "root";
    private static final String PASSWORD = "Siham2004";
    // Constructeur privé pour empêcher l'instanciation
    private DataBase() {}

    public static Connection getConnection() {
        if (con == null) {
            try {
                // Charger le pilote JDBC
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Établir la connexion
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion réussie !");
            } catch (ClassNotFoundException e) {
                System.err.println("Pilote JDBC introuvable : " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            }
        }
        return con;
    }

    /**
     * Ferme la connexion à la base de données.
     */
    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Connexion fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}

