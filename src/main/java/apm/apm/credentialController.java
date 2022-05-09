package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyListView;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;


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

    @FXML
    private TableColumn<?, ?> cp_user;

    private ObservableList<Credenziali_servizi> lista_credenziali;

    @FXML
    public void initialize() {
        AuthManager prova = new AuthManager();
        User logged = prova.userLogin("calmor", "bbbbbbbbbbbbbbbbbbbbb");
        System.out.println(logged.getId());
        System.out.println("PARTEEEEE");
        Credenziali_servizi esempio = (Credenziali_servizi) logged.portachiavi.find("google").get(0);
        credentialTableCell esempio1 = new credentialTableCell(esempio);
        lista_credenziali = FXCollections.observableArrayList(esempio);
        lista_credenziali.add(esempio);

//        ObservableList<Credenziali_servizi> lista = (ObservableList<Credenziali_servizi>) logged.portachiavi.getLista_credenziali();

        servizio.setCellValueFactory(new PropertyValueFactory<>("servizio"));
        url.setCellValueFactory(new PropertyValueFactory<>("url"));
        cp_user.setCellValueFactory(new PropertyValueFactory<>("copia_user"));
        cp_pass.setCellValueFactory(new PropertyValueFactory<>("copia_pass"));

        servizio.setStyle("-fx-alignment: CENTER");
        url.setStyle("-fx-alignment: CENTER");
        cp_pass.setStyle("-fx-alignment: CENTER");
        cp_user.setStyle("-fx-alignment: CENTER");
        tabella.getItems().add(esempio1);
//        tabella.setItems(lista);

//        for (Credenziali_servizi elem : lista) {
//            tabella.getItems().add(elem);
//        }
    }
}
