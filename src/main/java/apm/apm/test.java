package apm.apm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
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

public class test extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = getClass().getResource("credential.fxml");
//        URL posizione = new URL("C:\\Users\\Michele Sivakumaran\\IdeaProjects\\APM\\target\\classes\\apm\\apm\\credential.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 690);
        stage.setTitle("APM Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        AuthManager prova = new AuthManager();
//        User logged = prova.userLogin("calmor", "bbbbbbbbbbbbbbbbbbbbb");
//        credentialController schermata = new credentialController(logged);

//        launch();
//        System.out.println("ciao");

        favicon test = new favicon();
        test.getfavicon("http://www.poste.it");
        test.getfavicon("http://www.ebay.it");
        test.getfavicon("http://www.netflix.com");
        test.getfavicon("http://www.yahoo.com");
        //test.ficon();
    }
}
