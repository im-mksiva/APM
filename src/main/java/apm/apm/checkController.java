package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class checkController {

    public MFXProgressBar progressBar;
    @FXML
    MFXLegacyTableView<?> tabella;
    @FXML
    private MFXButton button_pwnd;

    @FXML
    private MenuItem cancella;

    @FXML
    private MenuItem editCred;

    @FXML
    private TableColumn<?, ?> servizio;

    @FXML
    private TableColumn<?, ?> tag;

    @FXML
    private TableColumn<?, ?> url;

    @FXML
    private Text controllo_compromessa;

    ObservableList lista_cred;
    double percentage,step;

    @FXML
    public void initialize() {
        User logged = APM.user;
        ArrayList<Credenziali_servizi> lista = logged.portachiavi.getLista_credenziali();
//        for (Credenziali_servizi elem : lista ) {
//            System.out.println(elem.getPwnd());
//        }

        for (TableColumn elem: tabella.getColumns()) {
            elem.setCellValueFactory(new PropertyValueFactory<>(elem.getId()));

            elem.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        }

        ObservableList lista_cred = FXCollections.observableArrayList(lista);
        tabella.setItems(lista_cred);
        step = (double) 1/lista_cred.size(); // il 100% corrisponde al valore 1 della progressBar
        System.out.println(step);
        percentage = 0;
        progressBar.setProgress(percentage);

    }


    @FXML
    void deleteRow(ActionEvent event) {

    }

    @FXML
    void edit(ActionEvent event) {

    }

    @FXML
    void check_pwnd(ActionEvent event){
        Task task = new Task<Void>() {
            @Override public Void call() {
                ObservableList lista = tabella.getItems();
                for (int i = 0; i < lista.size(); i++) {
                    try {
                        Credenziali_servizi temp = (Credenziali_servizi) lista.get(i);
                        temp.check_pwnd();
                        temp.update(APM.user);
                        percentage += step;
                        progressBar.setProgress(percentage);
                        /*
                        System.out.println(" dentro il thread - " + percentage);
                        if (percentage>1){
                            new dissolvenza_testo(controllo_compromessa, "Controllo password terminato");
                            progressBar.setProgress(0);
                        }
                        */
                    } catch (NoSuchAlgorithmException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                return null;
            };
        };
        new Thread(task).start();



    }
}
