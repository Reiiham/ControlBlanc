package ma.ensa.controlblanc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML
        //FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Ajout.fxml"));
        Parent root = loader.load();

        // Configuration de la fenêtre principale
        primaryStage.setTitle("Ajout d'un Membre");
        primaryStage.setScene(new Scene(root, 600, 400)); // Taille de la fenêtre
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Démarrage de l'application JavaFX
    }
}
