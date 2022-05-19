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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class noteController {

    public Circle credCircle;
    public Circle accountCircle;
    public Circle fileCircle;
    @FXML
    private MFXButton account;

    @FXML
    private MFXLegacyTableView<note> tabella;

    @FXML
    private ContextMenu tastoDestro;

    @FXML
    private TableColumn<?, ?> tag;

    @FXML
    private TableColumn<?, ?> testo;

    @FXML
    private TableColumn<?, ?> titolo;

    @FXML
    private TableColumn<?, ?> Ultima_modifica;

    ObservableList <note> new_list;

    @FXML
    private MFXTextField search_text;

    @FXML
    private Circle noteCircle;

    private User logged;

    @FXML
    public void initialize(){
        logged = APM.user;
        ArrayList<note> lista_note = logged.note.getLista_note();
        tag.setCellValueFactory(new PropertyValueFactory<>("tag"));
        titolo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        testo.setCellValueFactory(new PropertyValueFactory<>("testo"));
        Ultima_modifica.setCellValueFactory(new PropertyValueFactory<>("last_modified"));

        tag.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        titolo.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        testo.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        Ultima_modifica.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");

        tabella.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){
                this.visualModifyNote(null);
            }
        });

        new_list = FXCollections.observableArrayList(lista_note);
        tabella.setItems(new_list);
        FilteredList<note> filtro_dati = new FilteredList<>(new_list, b -> true);
        search_text.textProperty().addListener((observable, oldValue, newValue) -> {
            filtro_dati.setPredicate(new_list -> {
                if (newValue.isEmpty() || newValue.isBlank()){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(new_list.getTag() != null && new_list.getTag().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if(new_list.getTesto().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else if (new_list.getNome().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }else{
                    return false;
                }
            });
        });
        tabella.setItems(filtro_dati);
    }

    @FXML
    void deleteRow(ActionEvent event) {
        System.out.println("cancellazione");
        note selezione = tabella.getSelectionModel().getSelectedItem();
        this.logged.note.remove(selezione);
        FilteredList esterna = (FilteredList) tabella.getItems();
        esterna.getSource().remove(selezione);
        tabella.refresh();
    }

    @FXML
    void edit(ActionEvent event){}

    @FXML
    void InsertNewNote(ActionEvent event) {
        try {
            URL fxmlLocation = getClass().getResource("ins_note.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(fxmlLoader.load(), 567, 540);
            Stage stage = new Stage();
            stage.setTitle("Inserisci nota");
            stage.setScene(scene);
            insNoteController ins = fxmlLoader.getController();
            ins.logged = logged;
            ins.tabella = tabella;
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void visualModifyNote(ActionEvent event) {
        note selezione = tabella.getSelectionModel().getSelectedItem();
        try {
            URL fxmlLocation = getClass().getResource("modif_note.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            Scene scene = new Scene(fxmlLoader.load(), 567, 540);
            Stage stage = new Stage();
            stage.setTitle("Modifica nota");
            stage.setScene(scene);
            stage.setUserData(selezione);
            insNoteController vis_mod_controller = fxmlLoader.getController();
            vis_mod_controller.selezione = selezione;
            vis_mod_controller.logged = logged;
            vis_mod_controller.tabella = tabella;
            vis_mod_controller.setup();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
