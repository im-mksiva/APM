import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

//public class test extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
//        URL fxmlLocation = getClass().getResource("/login.fxml");
//        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
//        Scene scene = new Scene(fxmlLoader.load(), 1200, 700);
//        stage.setTitle("APM Login");
//        stage.setScene(scene);
//        stage.show();
//    }

public class test {


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//        launch();
//        test.check_pwnd();

        AuthManager registrazione = new AuthManager();
        User new_user = new User("calmor", "aaaaaa", "calogero", "morreale");
        registrazione.userRegister(new_user);
//        Keychain prova = new Keychain(1);
//        prova.import_csv("/home/mksiva/IdeaProjects/APM/APM/src/main/resources/Password Chrome.csv");
        Credenziali_servizi test = new Credenziali_servizi(1, 0, "pippo", "ciao", "www.google.it", "google");
        SQLite_agent prova = new SQLite_agent();
        prova.insertCredential(test);
        return;
    }
}
