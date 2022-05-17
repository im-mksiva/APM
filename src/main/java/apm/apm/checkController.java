package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;

public class checkController {

    @FXML
    private MFXButton button_pwnd;

    @FXML
    private MenuItem cancella;

    @FXML
    private TableColumn<?, ?> cp_pass;

    @FXML
    private TableColumn<?, ?> cp_user;

    @FXML
    private MenuItem editCred;

    @FXML
    private TableColumn<?, ?> servizio;

    @FXML
    private MFXLegacyTableView<?> tabella;

    @FXML
    private TableColumn<?, ?> tag;

    @FXML
    private TableColumn<?, ?> url;

    @FXML
    void check_pwnd(ActionEvent event) {

    }

    @FXML
    void deleteRow(ActionEvent event) {

    }

    @FXML
    void edit(ActionEvent event) {

    }

    public void initialize(){
        User logged = APM.user;
    }



}
