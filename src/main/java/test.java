import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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


    public static void main(String[] args) {
//        launch();


        Credenziali_servizi gomorra = new Credenziali_servizi(2, 0, "calmor", "aaaaaaa", "google.it", "gmail");

        Credenziali_servizi libano = new Credenziali_servizi(2, 2, 0, 0, "calmor", "aaaaaaa", "google.it", "drive", null);

        AuthManager prova = new AuthManager();
//        User caloger = new User("calmor", "aaaaaaaaaa", "calogero", "morreale");
//        prova.userRegister(caloger);
        User logged = prova.userLogin("calmor", "bbbbbbbbbbbbbbbbbbbbb");
        System.out.println(logged.getPassword());
//        logged.Decrypt(logged.getPassword());
//        logged.create_keychain();
        System.out.println(logged.getEncr_key());
//        logged.portachiavi.add(gomorra);
//        logged.portachiavi.add(libano);


    }
}
