package ma.ensa.controlblanc;
import java.util.Set;
public class CsvFilter {

        public static void main(String[] args) {
            // Path to the CSV file containing member data
            String fileName = "src/main/resources/membres.csv"; // Change this path as needed

            // Create an instance of MembreLoader
            StreamMember loader = new StreamMember();

            // Load members from the CSV file
            Set<Membre> membres = loader.chargerListeMembre(fileName);

            // Print the unique members
            System.out.println("Membres uniques:");
            for (Membre membre : membres) {
                System.out.println(membre.getNom() + " " + membre.getPrenom());
            }
        }
    }
