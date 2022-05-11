package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class credentialController {

    @FXML
    private MFXButton account;

    @FXML
    private MFXLegacyTableView<credentialTableCell> tabella;

    @FXML
    public TableColumn<credentialTableCell, String> servizio;

    @FXML
    public TableColumn<credentialTableCell, String> url;

    @FXML
    private TableColumn<?, ?> cp_pass;
//
    @FXML
    private TableColumn<?, ?> cp_user;

    @FXML
    private ContextMenu tastoDestro;

    @FXML
    private MenuItem editCred;

    User logged;
    private ObservableList<Credenziali_servizi> lista_credenziali;
    ObservableList<credentialTableCell> lista_cred;

    @FXML
    public void initialize() {
        AuthManager prova = new AuthManager();
        logged = prova.userLogin("calmor", "bbbbbbbbbbbbbbbbbbbbb");

        ArrayList<credentialTableCell> lista = logged.portachiavi.getLista_credenziali();

        lista_cred = FXCollections.observableArrayList(lista);

//        ObservableList<Credenziali_servizi> lista = (ObservableList<Credenziali_servizi>) logged.portachiavi.getLista_credenziali();

        servizio.setCellValueFactory(new PropertyValueFactory<>("servizio"));
        url.setCellValueFactory(new PropertyValueFactory<>("url"));
        cp_user.setCellValueFactory(new PropertyValueFactory<>("copia_user"));
        cp_pass.setCellValueFactory(new PropertyValueFactory<>("copia_pass"));

        servizio.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        url.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        cp_pass.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        cp_user.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
//        tabella.setItems(lista);
        tabella.setItems(lista_cred);

//        for (Credenziali_servizi elem : lista) {
//            tabella.getItems().add(elem);
//        }
    }


    @FXML
    void edit(ActionEvent event) {
        credentialTableCell selezione = tabella.getSelectionModel().getSelectedItem();
        Parent root;
        try {
            URL fxmlLocation = getClass().getResource("edit_credential.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(fxmlLoader.load(), 567, 540);
            Stage stage = new Stage();

            stage.setUserData(selezione);
            stage.setTitle("Modifica credenziale");
            stage.setScene(scene);
            edit_credController edit_credController = fxmlLoader.getController();
            edit_credController.selezione = selezione;
            edit_credController.logged = logged;
            edit_credController.tabella = tabella;
            edit_credController.setData();
            stage.show();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void deleteRow(ActionEvent event) {
        System.out.println("cancellazione");
        credentialTableCell selezione = tabella.getSelectionModel().getSelectedItem();
        this.logged.portachiavi.remove(selezione);
        tabella.getItems().remove(selezione);

    }



    }

