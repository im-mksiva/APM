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
        User caloger = new User("calmor", "aaaaaaaaaa", "calogero", "morreale");
//        prova.userRegister(caloger);
        User logged = prova.userLogin("calmor", "aaaaaaaaaa");
        logged.Decrypt("aaaaaaaaaa");
        logged.create_keychain();
//        logged.portachiavi.add(gomorra);
//        logged.portachiavi.add(libano);
        ArrayList<Credenziali_servizi> lista = logged.portachiavi.find("google");
        for (Credenziali_servizi elem : lista) {
            System.out.println(elem.getServizio());
        }


//        System.out.println("la chiave master: " + logged.getEncr_key());
//
//        gomorra.Encrypt(logged.getEncr_key());
//        gomorra.Decrypt(logged.getEncr_key());
//        System.out.println(gomorra.getPassword());

//
        note nota = new note("oggi hanno mandato in tv una nuova puntata di barbara d'urso", 1, "ricordi");
//        System.out.println(nota.testo);

//        System.out.println("========================== passiamo alla crittografia della nota ==========================");
//
//        nota.Encrypt(logged.getEncr_key());
//        System.out.println(nota.testo);
//
//        nota.Decrypt(logged.getEncr_key());
//        System.out.println("Il testo della nota sbloccata è: " + nota.testo);
//        archivio_note test = new archivio_note(2);
//        for (int i = 0; i < 10; i++) {
//            test.add(nota);
//        }
//        SQLite_agent db_agent = new SQLite_agent();

//        db_agent.deleteRecord(80, "note", "note_id");


//        }
    }
}
