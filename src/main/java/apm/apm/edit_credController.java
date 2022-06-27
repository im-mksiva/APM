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
import javafx.scene.text.Text;

import java.util.ArrayList;

public class edit_credController {

    int punteggio;
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
    private Text inserimento_effettuato;

    @FXML
    MFXLegacyTableView<credentialTableCell> tabella;

    @FXML
    private Text modifica_effettuata;

    @FXML
    private MFXButton bottoneInsert;

    @FXML
    private MFXButton bottoneReset;

    @FXML
    private MFXButton bottoneConferma;

    User logged;
    credentialTableCell selezione;

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
        selezione.setRobustezza(punteggio);
        selezione.update_credenziale(logged);
        new dissolvenza_testo(modifica_effettuata, "Credenziale modificata correttamente");
        selezione.setPassword(nuova_pass);
        tabella.refresh();

    }

    @FXML
    void insert_credential(ActionEvent event) {
        String insert_pass = password.getText();
        Credenziali_servizi new_credential = new Credenziali_servizi (
                0,
                logged.getId(),
                punteggio,
                0,
                username.getText(),
                password.getText(),
                url.getText(),
                servizio.getText(),
                tag.getText());

        credentialTableCell new_cell = new credentialTableCell(new_credential);
        if (username.getText().isEmpty() || password.getText().isEmpty()){
            new dissolvenza_testo(inserimento_effettuato, "Username o password mancanti");
            return;
        }else{
            logged.portachiavi.add(new_credential);
            new dissolvenza_testo(inserimento_effettuato, "Credenziale inserita correttamente");
        }
        System.out.println(insert_pass);
        new_cell.setPassword(insert_pass);
        setFavicon(url.getText());
        username.clear();
        password.clear();
        servizio.clear();
        url.clear();
        tag.clear();
        favicon.setFill(new ImagePattern( new Image("file:favicon/default.png")));
        bottoneInsert.setDisable(true);
        bottoneConferma.setDisable(false);
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
        password.setText(generator.genPass(10));
        password.requestFocus();
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

        this.punteggio = punteggio;
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
