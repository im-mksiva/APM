package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;

public class registerController {

    public Text login;
    @FXML
    private MFXButton registerButton;

    @FXML
    private MFXPasswordField password;

    @FXML
    private MFXTextField username;

    @FXML
    private MFXTextField nome;

    @FXML
    private Text utente_registrato;

    @FXML
    protected void onClick() {
        AuthManager register = new AuthManager();
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            return;
        }
        User new_user = new User(username.getText(), password.getText(), "nome", "cognome");
        boolean registrato = register.userRegister(new_user);
        if (registrato == true) {
            loginScene(null);
        } else {
            new dissolvenza_testo(utente_registrato, "Utente già registrato");
        }
    }

    public void loginScene(MouseEvent mouseEvent) {
        Pane schermata = (Pane) login.getScene().lookup("#schermata");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("login.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Stage stage = (Stage) login.getScene().getWindow();
        stage.setTitle("APM Login");
        try {
            schermata.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}

