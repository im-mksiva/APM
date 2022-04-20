import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class loginController {
    @FXML
    private MFXTextField username;
    @FXML
    private MFXPasswordField password;
    @FXML
    private MFXButton loginButton;

    @FXML
    protected void onClick() {
        String user = username.getText();
        String pass = password.getText();
        if (AuthManager.userLogin(user, pass)) {
            System.out.println("tutto ok");
        }
        

    }
}
