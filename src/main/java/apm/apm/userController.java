package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class userController {

    @FXML
    private MFXButton changeButton;

    @FXML
    private Circle userCircle;

    @FXML
    private MFXPasswordField user_pass;

    @FXML
    private Text pass_user_modificata;

    User logged;

    @FXML
    void changePass(ActionEvent event) {
        User logged = APM.user;
        String new_pass = user_pass.getText();
        logged.setPassword(new_pass);
        logged.update_credenziale(logged);
        javafx.application.Platform.exit();
    }



}
