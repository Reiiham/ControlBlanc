package ma.ensa.controlblanc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;

public class AjoutController {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField phone;
    @FXML
    private Button boutton;

    @FXML
    void Ajout(ActionEvent event) {

        String nomValue = nom.getText().trim();
        String prenomValue = prenom.getText().trim();
        String emailValue = email.getText().trim();
        String phoneValue = phone.getText().trim();

        // Validation des champs
        if (nomValue.isEmpty() || prenomValue.isEmpty() || emailValue.isEmpty() || phoneValue.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        if (!emailValue.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "L'adresse email n'est pas valide !");
            return;
        }

        if (!phoneValue.matches("\\d{10}")) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de téléphone doit contenir 10 chiffres !");
            return;
        }

        // Création de l'objet membre
        String identifiant = IdentifierGenerator.generateRandomIdentifier();
        Membre membre = new Membre(identifiant, nomValue, prenomValue, emailValue, phoneValue,null);

        // Insertion dans la base de données
        try (Connection connection = DataBase.getConnection()) {
            MembreDaoImpl membreDao = new MembreDaoImpl(connection);
            boolean success = membreDao.insere(membre); // Ajoutez cette méthode dans votre DAO

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le membre a été ajouté avec succès !");
                clearFields(); // Nettoie les champs
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur est survenue lors de l'insertion !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de se connecter à la base de données !");
        }
    }

    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour nettoyer les champs
    private void clearFields() {
        nom.clear();
        prenom.clear();
        email.clear();
        phone.clear();
    }
}

