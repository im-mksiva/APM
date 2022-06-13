package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class credentialTableCell extends Credenziali_servizi {
    MFXButton copia_user = new MFXButton("Copia username");
    MFXButton copia_pass = new MFXButton("Copia password");
    
    String strenght;


    credentialTableCell(Credenziali_servizi credenziale) {
        super(credenziale.getId(), credenziale.getUser_id(), credenziale.getRobustezza(), credenziale.getPwnd(), credenziale.getUsername(), credenziale.getPassword(), credenziale.getUrl(), credenziale.getServizio(), credenziale.getTag());
        copia_user.setStyle("-fx-background-color: #00487C; -fx-text-fill: white");
        copia_user.setOnAction(actionEvent -> {
            System.out.println("Username copiato");
            copyToClip(credenziale.getUsername());
        });
        copia_pass.setStyle("-fx-background-color: #00487C; -fx-text-fill: white");
        copia_pass.setOnAction(actionEvent -> {
            System.out.println("Password copiata");
            copyToClip(credenziale.getPassword());
        });
        strenght = strenth_as_Text();

    }

    public MFXButton getCopia_user() {
        return copia_user;
    }
    
    public String strenth_as_Text(){
        String text = null;
        switch (this.getRobustezza()){
            case 5: return "forte";
            case 4: return "media";
            default: return "debole";
        }
    };

    public String getStrenght() {
        return strenght;
    }

    public void setCopia_user(MFXButton copia_user) {
        this.copia_user = copia_user;
    }

    public MFXButton getCopia_pass() {
        return copia_pass;
    }

    public void setCopia_pass(MFXButton copia_pass) {
        this.copia_pass = copia_pass;
    }

    public static void copyToClip(String text) {
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    }


