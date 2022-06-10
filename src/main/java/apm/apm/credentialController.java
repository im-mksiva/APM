package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    MFXLegacyTableView<credentialTableCell> tabella;

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
    private Text messaggio_da_mostrare;

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
        if (selezione == null){
            System.out.println("nessun elemento selezionato");
            return;
        }
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
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void deleteRow(ActionEvent event) {
//        System.out.println("cancellazione");

        credentialTableCell selezione = tabella.getSelectionModel().getSelectedItem();
        if (selezione == null){
            System.out.println("nessun elemento selezionato");
            return;
        }

        this.logged.portachiavi.remove(selezione);
        new dissolvenza_testo(messaggio_da_mostrare, "Credenziale rimossa Correttamente");
        FilteredList esterna = (FilteredList) tabella.getItems();
        esterna.getSource().remove(selezione);
        tabella.refresh();
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
            stage.setResizable(false);
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
            Task task = new Task<Void>() {
                @FXML
                MFXLegacyTableView<credentialTableCell> tabella;
                @Override public Void call() {
                    APM.user.portachiavi.import_csv(file.getAbsolutePath());
                    new dissolvenza_testo(messaggio_da_mostrare, "Credenziali importate correttamente");
                    tabella.refresh();
                    return null;
                };
            };
            new Thread(task).start();
            tabella.refresh();
        }
    }

    @FXML
    public void export_csv(){
        tabella.refresh();
        FileChooser fc = new FileChooser();
        fc.setTitle("Salva file");
        fc.getExtensionFilters().addAll((new FileChooser.ExtensionFilter("File CSV", "*.csv")));
        File file = fc.showSaveDialog(null);
        if (file != null) {
            //System.out.println(file.getAbsolutePath());
            Task task = new Task<Void>() {
                @Override public Void call() {
                    APM.user.portachiavi.export_csv(file.getAbsolutePath());
                    new dissolvenza_testo(messaggio_da_mostrare, "Credenziali esportate correttamente");
                    return null;
                };
            };
            new Thread(task).start();
        }
    }



    public void checkScene(ActionEvent event) {
        Pane schermata = (Pane) tabella.getScene().lookup("#schermata");
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL fxmlLocation = getClass().getResource("checkScene.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        try {
            schermata.getChildren().add(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }


