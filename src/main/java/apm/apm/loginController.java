package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Stack;

public class loginController extends sceneController {
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
        User user_logged = login.userLogin(user, pass);
        if (user_logged != null) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            System.out.println("tutto ok");
            stage = new Stage();
            URL fxmlLocation = getClass().getResource("credential.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 1000, 690);
            stage.setTitle("APM Login");
            stage.setScene(scene);
            credentialController toFill = fxmlLoader.getController()
            stage.show();
        }
        }


    public void registerScene(MouseEvent mouseEvent) {
    }
}
