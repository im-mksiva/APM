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


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        launch();
//        test.check_pwnd();
//        Encrypt_Decrypt test = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, "aaaaaaaaaaaaaaaa");
//        test.Encrypt("buonasera a tutti voi");
//        test.Decrypt("x3Oy/zl3X7FbBP32NaP4WX4dvmN9mlw64o0c9Ysw9iw=");

//        String percorso = "/home/mksiva/IdeaProjects/APM/APM/database/APM.db.cripto";
//        File file_da_criptare = new File(percorso);
//        test.Decrypt(file_da_criptare);
//        User calogero = new User("calmor", "aaaaaaaaaa", "calogero", "morreale");
        AuthManager prova = new AuthManager();
//        prova.userRegister(calogero);

        User logged = prova.userLogin("calmor", "aaaaaaaaaa");
        logged.Decrypt("aaaaaaaaaa");
        System.out.println("la chiave master: " + logged.getEncr_key());
        note nota = new note("oggi hanno mandato in tv una nuova puntata di barbara", 1, "ricordi");

        System.out.println("========================== passiamo alla crittografia della nota ==========================");

        nota.Encrypt(logged.getEncr_key());
        System.out.println(nota.testo);
        nota.Decrypt(logged.getEncr_key());
        System.out.println("Il testo della nota sbloccata è: " + nota.testo);
//        archivio_note test = new archivio_note(2);
//        test.add(nota);
//        SQLite_agent db_agent = new SQLite_agent();
//        db_agent.delete_userData(1, "note");
//        while (i < 10) {

//        i++;
//        }
    }
}
