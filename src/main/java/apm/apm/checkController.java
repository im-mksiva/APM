package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private TableColumn<?, ?> servizio;

    @FXML
    private TableColumn<?, ?> tag;

    @FXML
    private TableColumn<?, ?> url;

    @FXML
    private Text controllo_compromessa;

    double percentage,step;

    @FXML
    public void initialize() {
        User logged = APM.user;
        ArrayList<Credenziali_servizi> lista = logged.portachiavi.getLista_credenziali();

        for (TableColumn elem: tabella.getColumns()) {
            elem.setCellValueFactory(new PropertyValueFactory<>(elem.getId()));
            elem.setStyle("-fx-alignment: CENTER; -fx-text-fill: black");
        }
        ObservableList lista_cred = FXCollections.observableArrayList(lista);
        tabella.setItems(lista_cred);
        step = (double) 1/lista_cred.size();
        percentage = 0;
        progressBar.setProgress(percentage);

    }

    @FXML
    void check_pwnd(ActionEvent event){
        Task task = new Task<Void>() {
            @Override public Void call() {
                System.out.print("inizio controllo");
                if (percentage == 1){
                    percentage=0;
                    progressBar.setProgress(percentage);
                }
                ObservableList lista = tabella.getItems();
                for (int i = 0; i < lista.size(); i++) {
                    try {
                        Credenziali_servizi temp = (Credenziali_servizi) lista.get(i);
                        temp.check_pwnd();
                        temp.
                                update_credenziale(APM.user);
                        percentage += step;
                        progressBar.setProgress(percentage);

                    } catch (NoSuchAlgorithmException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                tabella.refresh();
                return null;
            };
        };
        new Thread(task).start();



    }
}
