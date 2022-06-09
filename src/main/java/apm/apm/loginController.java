package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class loginController {
    public Text register;
    @FXML
    private MFXTextField username;
    @FXML
    private MFXPasswordField password;
    @FXML
    private MFXButton loginButton;

    @FXML
    protected void onClick() throws IOException {
        AuthManager login = new AuthManager();
        String user = username.getText();
        String pass = password.getText();
//        APM.user = login.userLogin(user, pass);
        APM.user = login.userLogin("calmor", "1234");
        User user_logged = APM.user;
        if (user_logged != null) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            System.out.println("tutto ok");
            stage = new Stage();

            URL fxmlLocation = getClass().getResource("principale.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1000, 690);
            stage.setTitle("Gestione credenziali");
            stage.setScene(scene);

            stage.show();
        }
        }

    @FXML
    void catch_enter(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)){
            onClick();
        }
    }


    public void registerScene(MouseEvent mouseEvent) {
        Pane schermata = (Pane) register.getScene().lookup("#schermata");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("register.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        try {
            schermata.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    }
