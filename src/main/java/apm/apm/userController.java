package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;

public class userController {

    @FXML
    private MFXButton changeButton;

    @FXML
    private Circle userCircle;

    @FXML
    private MFXPasswordField user_pass;

    User logged;

    @FXML
    void changePass(ActionEvent event) {
        User logged = APM.user;
        String new_pass = user_pass.getText();
        logged.update_user_password(new_pass);
        javafx.application.Platform.exit();
    }

}