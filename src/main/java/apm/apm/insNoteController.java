package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class insNoteController {
    @FXML
    private MFXTextField InserisciTag;

    @FXML
    private TextArea InserisciTesto;

    @FXML
    private MFXTextField InserisciTitolo;

    @FXML
    private MFXButton bottoneInsert;

    @FXML
    private MFXButton bottoneReset;

    @FXML
    private Text inserimento_effettuato;

    @FXML
    private MFXTextField visualTag;

    @FXML
    private TextArea visualTesto;

    @FXML
    private MFXTextField visualTitolo;

    @FXML
    private MFXButton bottoneSalva;

    @FXML
    private Text modifica_effettuata;

    @FXML
    MFXLegacyTableView<note> tabella;


    note selezione;
    User logged;


    @FXML
    void insertNote(ActionEvent event) {
        String testo_in_chiaro = InserisciTesto.getText();

        System.out.println("prova testo " + InserisciTesto.getParagraphs());
        note new_nota = new note(
                InserisciTesto.getText(),
                InserisciTag.getText(),
                InserisciTitolo.getText(),
                APM.user.getId()
        );
        if (InserisciTesto.getText() != "" && InserisciTitolo.getText()!="") {
            logged.note.add(new_nota);
            new dissolvenza_testo(inserimento_effettuato, "Nota inserita correttamente");
            //inserimento_effettuato.setText("nota inserita correttamente");
        }else{
            new dissolvenza_testo(inserimento_effettuato, "Nota non inserita per dati mancanti");
        }
        InserisciTesto.clear();
        InserisciTag.clear();
        InserisciTitolo.clear();
        FilteredList esterna = (FilteredList) tabella.getItems();
        LocalDateTime dateTime = LocalDateTime.now();
        String timeStamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(dateTime);
        new_nota.setLast_modified(timeStamp);
        new_nota.setTesto(testo_in_chiaro);
        esterna.getSource().add(new_nota);
        tabella.refresh();

    }

    @FXML
    void reset(ActionEvent event) {
        InserisciTesto.clear();
        InserisciTag.clear();
        InserisciTitolo.clear();
        inserimento_effettuato.setText("");
    }



    @FXML
    void salvaNote(ActionEvent event) {
        String testo = visualTesto.getText();
        selezione.setTag(visualTag.getText());
        selezione.setNome(visualTitolo.getText());
        selezione.setTesto(visualTesto.getText());
        LocalDateTime dateTime = LocalDateTime.now();
        String timeStamp = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(dateTime);
        selezione.setLast_modified(timeStamp);
        //selezione.setLast_modified("CURRENT_TIMESTAMP");
        //System.out.println("date   " + selezione.getLast_modified());
        selezione.update(logged);
        //Nota Modificata correttamente
        new dissolvenza_testo(modifica_effettuata, "Nota Modificata correttamente");
        tabella.refresh();
        selezione.setTesto(testo);

    }

    public void setup(){
        visualTitolo.setText(selezione.getNome());
        visualTag.setText(selezione.getTag());
        visualTesto.setText(selezione.getTesto());

    }


}
