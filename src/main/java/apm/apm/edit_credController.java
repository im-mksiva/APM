package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
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

    public void setup() {
        password.setText(selezione.getPassword());
        url.setText(selezione.getUrl());
        tag.setText(selezione.getTag());
        servizio.setText(selezione.getServizio());
        username.setText(selezione.getUsername());
        setFavicon();
        checkPassStrenght();
    }

    public void setFavicon(){
        favicon setter = new favicon();
        String nome = setter.getfavicon(selezione.getUrl());
        favicon.setFill(new ImagePattern( new Image("file:favicon/"+ nome + ".png")));
    }

    public void apply(ActionEvent actionEvent) {
        String nuova_pass = password.getText();
        selezione.setPassword(nuova_pass);
        selezione.setUrl(url.getText());
        selezione.setTag(tag.getText());
        selezione.setServizio(servizio.getText());
        selezione.setUsername(username.getText());
        selezione.update(logged);
        selezione.setPassword(nuova_pass);
        tabella.refresh();

    }

    @FXML
    void getSecurePass(MouseEvent mouseEvent) {
        System.out.println("È partita la funzione SecurePass");
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
