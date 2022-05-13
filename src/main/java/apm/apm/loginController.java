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
//        User user_logged = login.userLogin(user, pass);
        User user_logged = login.userLogin("calmor", "bbbbbbbbbbbbbbbbbbbbb");
        if (user_logged != null) {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
            System.out.println("tutto ok");
            stage = new Stage();

            credentialController credentialController = new credentialController();
            credentialController.setLogged(user_logged);
            URL fxmlLocation = getClass().getResource("credential.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            fxmlLoader.setController(credentialController);
            Parent root = fxmlLoader.load();


            System.out.println("verifica");
            Scene scene = new Scene(root, 1000, 690);



            stage.setTitle("Gestione credenziali");
            stage.setScene(scene);


            stage.show();
        }
        }
// bbbbbbbbbbbbbbbbbbbbb

    public void registerScene(MouseEvent mouseEvent) {
    }
}
