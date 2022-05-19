package apm.apm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;



public class test extends Application {
    public static User user;
    @Override
    public void start(Stage stage) throws IOException {
        AuthManager login = new AuthManager();
        APM.user = login.userLogin("calmor", "1234");
        User user_logged = APM.user;
        URL fxmlLocation = getClass().getResource("checkScene.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1000, 690);
        stage.setTitle("APM Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
