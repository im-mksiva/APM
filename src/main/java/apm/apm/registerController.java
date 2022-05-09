package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class registerController {

    @FXML
    private MFXButton loginButton;

    @FXML
    private MFXPasswordField password;

    @FXML
    private MFXTextField username;

    @FXML
    private MFXTextField nome;

    @FXML
    protected void onClick() {
        String user = username.getText();
        String pass = password.getText();
//        AuthManager.userRegister(user, pass);
    }
}

