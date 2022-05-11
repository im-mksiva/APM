package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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

    User logged;
    private ObservableList<Credenziali_servizi> lista_credenziali;

    @FXML
    public void initialize() {
        AuthManager prova = new AuthManager();
        logged = prova.userLogin("calmor", "bbbbbbbbbbbbbbbbbbbbb");

        ArrayList<credentialTableCell> lista = logged.portachiavi.find("google");

        ObservableList<credentialTableCell> lista_cred = FXCollections.observableArrayList(lista);

//        ObservableList<Credenziali_servizi> lista = (ObservableList<Credenziali_servizi>) logged.portachiavi.getLista_credenziali();

        servizio.setCellValueFactory(new PropertyValueFactory<>("servizio"));
        url.setCellValueFactory(new PropertyValueFactory<>("url"));
        cp_user.setCellValueFactory(new PropertyValueFactory<>("copia_user"));
        cp_pass.setCellValueFactory(new PropertyValueFactory<>("copia_pass"));

        servizio.setStyle("-fx-alignment: CENTER");
        url.setStyle("-fx-alignment: CENTER");
        cp_pass.setStyle("-fx-alignment: CENTER");
        cp_user.setStyle("-fx-alignment: CENTER");
//        tabella.setItems(lista);
        tabella.setItems(lista_cred);

//        for (Credenziali_servizi elem : lista) {
//            tabella.getItems().add(elem);
//        }
    }


    @FXML
    void edit(ActionEvent event) {
        credentialTableCell selezione = tabella.getSelectionModel().getSelectedItem();


    }

    @FXML
    void deleteRow(ActionEvent event) {
        System.out.println("cancellazione");
        credentialTableCell selezione = tabella.getSelectionModel().getSelectedItem();
        this.logged.portachiavi.remove(selezione);
        tabella.getItems().remove(selezione);
    }

    }

