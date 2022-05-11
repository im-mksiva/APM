package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class edit_credController {

    @FXML
    private Circle favicon;
    @FXML
    private MFXButton generatePass;
    @FXML
    private MFXTextField password;

    @FXML
    private MFXTextField servizio;

    @FXML
    private MFXButton submit;

    @FXML
    private MFXTextField tag;

    @FXML
    private MFXTextField url;

    @FXML
    private MFXTextField username;

    @FXML
    MFXLegacyTableView<credentialTableCell> tabella;


    credentialTableCell selezione;
    User logged;

    public void setData() {
        password.setText(selezione.getPassword());
        url.setText(selezione.getUrl());
        tag.setText(selezione.getTag());
        servizio.setText(selezione.getServizio());
        username.setText(selezione.getUsername());
        checkPassStrenght();
    }

    public void apply(ActionEvent actionEvent) {
        selezione.setPassword(password.getText());
        selezione.setUrl(url.getText());
        selezione.setTag(tag.getText());
        selezione.setServizio(servizio.getText());
        selezione.setUsername(username.getText());
        selezione.update(logged);
        tabella.refresh();

    }

    @FXML
    void getSecurePass(ActionEvent event) {
        passGen generator = new passGen();
        password.setText(generator.genPass(20));
        checkPassStrenght();
    }

    @FXML
    void checkPassStrenght() {
        RobustezzaPass check = new RobustezzaPass();
        ArrayList<Integer> valutazione = check.valutazione(password.getText());
        int punteggio = 0;
        for (int elem : valutazione) {
            punteggio += elem;
        }

        if (punteggio == 5) {
            password.setStyle("-fx-border-color: green;");
            password.setFloatingText("Password forte!");
        } else if (punteggio == 4) {
            password.setStyle("-fx-border-color: orange;");
            password.setFloatingText("Password media");
        } else {
            password.setStyle("-fx-border-color: red;");
            password.setFloatingText("Password debole");
        }
    }
}
