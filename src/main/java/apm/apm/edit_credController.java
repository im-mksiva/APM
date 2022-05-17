package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class edit_credController {

    @FXML
    Circle favicon;
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


    credentialTableCell selezione, new_credential;
    @FXML
    private MFXButton bottoneInsert;

    @FXML
    private MFXButton bottoneReset;

    @FXML
    private MFXButton bottoneConferma;
    User logged;

    @FXML
    public void initialize() {
        logged = APM.user;
        this.favicon.setFill(new ImagePattern( new Image("file:favicon/default.png")));
    }



    public void setup() {
        password.setText(selezione.getPassword());
        url.setText(selezione.getUrl());
        tag.setText(selezione.getTag());
        servizio.setText(selezione.getServizio());
        username.setText(selezione.getUsername());
        setFavicon(selezione.getUrl());
        checkPassStrenght();
    }

    public void setFavicon(String url){
        favicon setter = new favicon();
        String nome = setter.getfavicon(url);
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
    void insert_credential(ActionEvent event) {
        //int user_id, String username, String password, String servizio, String url, String tag
        Credenziali_servizi new_credential = new Credenziali_servizi (
                logged.getId(),
                username.getText(),
                password.getText(),
                servizio.getText(),
                url.getText(),
                tag.getText());

        credentialTableCell new_cell = new credentialTableCell(new_credential);
        logged.portachiavi.add(new_credential);
        setFavicon(url.getText());
        //System.out.println("prima di essere cancellato l'username " + new_cell.getUsername());
        username.clear();
        password.clear();
        servizio.clear();
        url.clear();
        tag.clear();
        favicon.setFill(new ImagePattern( new Image("file:favicon/default.png")));
        bottoneInsert.setDisable(true);
        bottoneConferma.setDisable(false);
        // il problema era che FilteredList è un oggetto read-only che racchiude la lista di partenza,
        // quindi dobbiamo aggiungere la nuova cella alla lista "interna"
        FilteredList esterna = (FilteredList) tabella.getItems();
        esterna.getSource().add(new_cell);
        tabella.refresh();
    }

    @FXML
    void confermaCredenziale(ActionEvent event) {
        setFavicon(url.getText());
        bottoneInsert.setDisable(false);
        bottoneConferma.setDisable(true);
        bottoneReset.setDisable(false);

    }
    @FXML
    void reset(ActionEvent event) {

        username.clear();
        password.clear();
        servizio.clear();
        url.clear();
        tag.clear();
        favicon.setFill(new ImagePattern( new Image("file:favicon/default.png")));
        bottoneInsert.setDisable(true);
        bottoneConferma.setDisable(false);
        bottoneReset.setDisable(true);

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
