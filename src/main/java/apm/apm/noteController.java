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
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
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
        Ultima_modifica.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");

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
        SortedList<note> ordinamento_dati = new SortedList<>(filtro_dati);
        ordinamento_dati.comparatorProperty().bind(tabella.comparatorProperty());
        tabella.setItems(ordinamento_dati);
    }

}
