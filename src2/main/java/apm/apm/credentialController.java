package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;

import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;



public class credentialController extends sceneController {

    public Circle noteCircle;
    public Circle accountCircle;
    @FXML
    private MFXButton account;

    @FXML
    private Circle credCircle;
    @FXML
    private Circle fileCircle;

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

    @FXML
    private TableColumn<credentialTableCell, String> tag;

    @FXML
    private MFXTextField search_credential;


    @FXML
    private MFXButton note_button;


    @FXML
    private MFXButton file_button;

    User logged;
    private ObservableList<Credenziali_servizi> lista_credenziali;
    ObservableList<credentialTableCell> lista_cred;

    @FXML
    public void initialize() {
        AuthManager prova = new AuthManager();
        logged = prova.userLogin("calmor", "bbbbbbbbbbbbbbbbbbbbb");
        ArrayList<credentialTableCell> lista = logged.portachiavi.getLista_credenziali();

        tag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        servizio.setCellValueFactory(new PropertyValueFactory<>("servizio"));
        url.setCellValueFactory(new PropertyValueFactory<>("url"));
        cp_user.setCellValueFactory(new PropertyValueFactory<>("copia_user"));
        cp_pass.setCellValueFactory(new PropertyValueFactory<>("copia_pass"));

        tag.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        servizio.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        url.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        cp_pass.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        cp_user.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");

        lista_cred = FXCollections.observableArrayList(lista);
        tabella.setItems(lista_cred);
        tabella.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                this.edit(null);
            }
        });


        // ricerca nella lista credenziali
        FilteredList<credentialTableCell> filtro_dati = new FilteredList<>(lista_cred, b -> true);
        search_credential.textProperty().addListener((observable, oldValue, newValue) -> {
            filtro_dati.setPredicate(lista_cred -> {
                if (newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(lista_cred.getTag() != null && lista_cred.getTag().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (lista_cred.getUrl() != null && lista_cred.getServizio().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (lista_cred.getServizio() != null && lista_cred.getUrl().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        SortedList<credentialTableCell> ordinamento_dati = new SortedList<>(filtro_dati);
        ordinamento_dati.comparatorProperty().bind(tabella.comparatorProperty());
        tabella.setItems(ordinamento_dati);

        noteCircle.setFill(new ImagePattern( new Image("file:src/main/resources/apm/apm/icons/note_white.png")));
        credCircle.setFill(new ImagePattern( new Image("file:src/main/resources/apm/apm/icons/credenziali.png")));
        accountCircle.setFill(new ImagePattern( new Image("file:src/main/resources/apm/apm/icons/account.png")));
        fileCircle.setFill(new ImagePattern( new Image("file:src/main/resources/apm/apm/icons/file_white.png")));
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
            stage.setTitle("Modifica credenziale");
            stage.setScene(scene);

            stage.setUserData(selezione);
            edit_credController edit_credController = fxmlLoader.getController();
            edit_credController.selezione = selezione;
            edit_credController.logged = logged;
            edit_credController.tabella = tabella;
            edit_credController.setup();

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

    public void fileScene(ActionEvent actionEvent) {
        changeScene(actionEvent,"file_enc.fxml");
    }


    public void noteScene(ActionEvent actionEvent) {
        changeScene(actionEvent,"note.fxml");
    }
}

