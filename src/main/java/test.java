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
        Encrypt_Decrypt test = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, "aaaaaaaaaaaaaaaa");
//        test.Encrypt("buonasera a tutti voi");
//        test.Decrypt("x3Oy/zl3X7FbBP32NaP4WX4dvmN9mlw64o0c9Ysw9iw=");

        String percorso = "/home/mksiva/IdeaProjects/APM/APM/database/APM.db.cripto";
        File file_da_criptare = new File(percorso);
        test.Decrypt(file_da_criptare);


        //x3Oy/zl3X7FbBP32NaP4WX4dvmN9mlw64o0c9Ysw9iw=
    }
}
