package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

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
    MFXLegacyTableView<note> tabella;

    User logged;


    @FXML
    void insertNote(ActionEvent event) {
        //String testo, String tag, String nome

        System.out.println("prova testo " + InserisciTesto.getParagraphs());
        note new_nota = new note(
                InserisciTesto.getText(),
                InserisciTag.getText(),
                InserisciTitolo.getText()
        );
        if (InserisciTesto.getText() != "" && InserisciTitolo.getText()!="") {
            logged.note.add(new_nota);
            inserimento_effettuato.setText("nota inserita correttamente");
        }
        InserisciTesto.clear();
        InserisciTag.clear();
        InserisciTitolo.clear();
        //tabella.getItems().add(new_nota);
        tabella.refresh();
    }

    @FXML
    void reset(ActionEvent event) {
        InserisciTesto.clear();
        InserisciTag.clear();
        InserisciTitolo.clear();
        inserimento_effettuato.setText("");
    }


}
