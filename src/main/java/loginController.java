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
        AuthManager login = new AuthManager();
        String user = username.getText();
        String pass = password.getText();
        User user_logged = login.userLogin(user, pass);
        if (user_logged != null) {
            System.out.println("tutto ok");
        }


    }
}
