package apm.apm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class sceneController {
    User logged;

    void changeScene(ActionEvent event, String fxml){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root;
        try
        {
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    void changeScene(MouseEvent event, String fxml){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root;
        try
        {
            root = (Parent)loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    void changeScene(ActionEvent event, String fxml, User user){
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root;
        if (loader.getController() == null){
            switch (fxml){
                case "note.fxml":
                    noteController note_controller = new noteController();
                    loader.setController(note_controller);
                    break;
                case "credential.fxml":
                    credentialController cred_controller = new credentialController();
                    cred_controller.setLogged(user);
                    loader.setController(cred_controller);
                    break;
                case "file_enc.fxml":
                    file_encController file_controller = new file_encController();
                    loader.setController(file_controller);
                    break;
            }
        }
        try
        {
            root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void userScene(){
            try {
                URL fxmlLocation = getClass().getResource("user_page.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
                Scene scene = new Scene(fxmlLoader.load(), 567, 540);
                Stage stage = new Stage();
                stage.setTitle("Impostazioni account");
                stage.setScene(scene);
                userController userController = fxmlLoader.getController();

                userController.logged = logged;

                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
