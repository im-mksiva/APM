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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;


public class credentialController {

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
    private TableColumn<?, ?> copia_pass;
    //
    @FXML
    private TableColumn<?, ?> copia_user;

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

    public void setLogged(User logged) {
        this.logged = logged;
    }

    private User logged;
    ObservableList<credentialTableCell> lista_cred;
    gestione_file_csv imp_exp_file_csv = new gestione_file_csv();

    @FXML
    public void initialize() {
        logged = APM.user;
        lista_cred = null;

        for (TableColumn elem: tabella.getColumns()) {
            elem.setCellValueFactory(new PropertyValueFactory<>(elem.getId()));
            elem.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        }

        if (lista_cred == null){

            ArrayList<credentialTableCell> lista = logged.portachiavi.getLista_credenziali();
            lista_cred = FXCollections.observableArrayList(lista);
            tabella.setItems(lista_cred);
        }
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
                }else if (lista_cred.getServizio() != null && lista_cred.getServizio().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (lista_cred.getUrl() != null && lista_cred.getUrl().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        tabella.setItems(filtro_dati);
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

    public void insertNewCredential(ActionEvent actionEvent) {
        try {
            URL fxmlLocation = getClass().getResource("insert_credential.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(fxmlLoader.load(), 567, 540);
            Stage stage = new Stage();
            stage.setTitle("Inserisci credenziale");
            stage.setScene(scene);
            edit_credController edit_credController = fxmlLoader.getController();
            edit_credController.logged = logged;
            edit_credController.tabella = tabella;
            edit_credController.favicon.setFill(new ImagePattern( new Image("file:favicon/default.png")));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void import_csv(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Scegli file da importare");
        fc.getExtensionFilters().addAll((new FileChooser.ExtensionFilter("File CSV", "*.csv")));
        File file = fc.showOpenDialog(null);
        if (file != null){
            //System.out.println(file.getAbsolutePath());
            APM.user.portachiavi.import_csv(file.getAbsolutePath());
        }
    }

    @FXML
    public void export_csv(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Salva file");
        fc.getExtensionFilters().addAll((new FileChooser.ExtensionFilter("File CSV", "*.csv")));
        File file = fc.showSaveDialog(null);
        if (file != null) {
            //System.out.println(file.getAbsolutePath());
            APM.user.portachiavi.export_csv(file.getAbsolutePath());
        }
    }






}

