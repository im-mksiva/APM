package apm.apm;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;

public class sceneController {

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

}
