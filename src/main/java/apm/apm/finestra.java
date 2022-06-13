package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;




public class finestra {

    public Circle noteCircle;
    public Circle credCircle;
    public Circle fileCircle;
    public MFXButton file_button;
    public MFXButton credentialButton;
    public MFXButton note_button;
    public Circle accountCircle;
    public MenuItem userSettings;
    public MenuItem logout;
    @FXML
    private Pane schermata;
    @FXML
    private Button button;


    public void initialize() throws IOException {
        URL fxmlLocation = getClass().getResource("credential.fxml");

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        schermata.getChildren().add(fxmlLoader.load()); // in questo modo posso sostituire il contenuto del pane con la schermata che voglio

        noteCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/note_white.png")));
        credCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/credenziali.png")));
        fileCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/file_white.png")));
        accountCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/account.png")));

    }

    void switchScene(String fxml) {
        URL fxmlLocation = getClass().getResource(fxml);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        try {
            schermata.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public void settings (ActionEvent event){
            try {
                URL fxmlLocation = getClass().getResource("user.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                Scene scene = new Scene(fxmlLoader.load(), 567, 540);
                Stage stage = new Stage();
                stage.setTitle("Impostazioni utente");
                stage.getIcons().add(new Image(this.getClass().getResource("icons/APM.png").toString()));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);

            }
    }


        @FXML
        void fileScene (ActionEvent event){
            switchScene("file_enc.fxml");
            noteCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/note_white.png")));
            credCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/credenziali_white.png")));
            fileCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/file.png")));
        }

        @FXML
        void noteScene (ActionEvent event){
            switchScene("note.fxml");
            noteCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/note.png")));
            credCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/credenziali_white.png")));
            fileCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/file_white.png")));
        }

        public void credentialScene (ActionEvent event){
            switchScene("credential.fxml");
            noteCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/note_white.png")));
            credCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/credenziali.png")));
            fileCircle.setFill(new ImagePattern(new Image("file:src/main/resources/apm/apm/icons/file_white.png")));
        }

    public void userLogout(ActionEvent event) {
        APM.user = null;
        Stage stage = (Stage) schermata.getScene().getWindow();
        stage.close();

        stage = new Stage();
        URL fxmlLocation = getClass().getResource("login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Parent root = null;
        try {
            root = fxmlLoader.load();
            Scene scene = new Scene(root, 800, 580);
            stage.setTitle("APM Login");
            stage.setScene(scene);
            stage.getIcons().add(new Image(this.getClass().getResource("icons/APM.png").toString()));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
