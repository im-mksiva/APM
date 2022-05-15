package apm.apm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;


public class APM extends Application {
    public static User user;
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = getClass().getResource("login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 580);
        stage.setTitle("APM Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
