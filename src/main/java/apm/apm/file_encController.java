package apm.apm;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.crypto.Cipher;
import java.io.File;

public class file_encController {


    public Circle accountCircle;
    public MFXButton credentialButton;
    public Circle noteCircle;
    public Circle credCircle;
    public Circle fileCircle;
    public MFXButton file_button;

    @FXML
    private Circle back;

    @FXML
    private MFXButton cripta_file;

    @FXML
    private MFXButton decripta_file;

    @FXML
    private MFXButton salva;

    File file_selezionato;
    String estensione;
    int flag;

    private User logged;

    public void initialize() {
        logged = APM.user;
        back.setFill(new ImagePattern( new Image("file:src/main/resources/apm/apm/icons/back1.png")));
    }

    @FXML
    void open_file_cripto(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Scegli file da criptare");
        //fc.getExtensionFilters().removeAll(new FileChooser.ExtensionFilter("File criptato", "*.cripto"));
        File file = fc.showOpenDialog(null);
        if (file != null){
            System.out.println(file.getAbsolutePath());
            this.file_selezionato = file;
            int punto = file.getAbsolutePath().lastIndexOf(".");
            this.estensione = file.getAbsolutePath().substring(punto, file.getAbsolutePath().length());
            this.flag=1;
            cripta_file.setText(file.getName());
            decripta_file.setDisable(true);
            salva.setDisable(false);
            //cripta.Encrypt(file);
        }else{
            this.flag=3;
        }
    }

    @FXML
    void open_file_decripto(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Scegli file da decriptare");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File criptato", "*.cripto"));
        File file = fc.showOpenDialog(null);
        //Encrypt_Decrypt decripta = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, logged.getEncr_key());
        if (file != null){
            System.out.println(file.getAbsolutePath());
            this.file_selezionato = file;
            String path_senza_cripto = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
            this.estensione = path_senza_cripto.substring(path_senza_cripto.lastIndexOf("."), path_senza_cripto.length());
            this.flag=0;
            decripta_file.setText(file.getName());
            cripta_file.setDisable(true);
            salva.setDisable(false);
        }else{
            this.flag=3;
        }
    }

    @FXML
    void salva_file(){
        FileChooser fc = new FileChooser();
        if (this.flag == 1) {
            System.out.println("flag -> " + this.flag);
            fc.setTitle("Salva file da criptare");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File " + estensione, "*" + estensione));
            File file = fc.showSaveDialog(null);
            Encrypt_Decrypt cripta = new Encrypt_Decrypt(Cipher.ENCRYPT_MODE, logged.getEncr_key());
            if (file != null) {
                System.out.println(file.getAbsolutePath());
                cripta.Encrypt(this.file_selezionato, file.getAbsolutePath());
            }
        }else if (this.flag==0){
            System.out.println("flag -> " + this.flag);
            fc.setTitle("Salva file da decriptare");
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("File " + estensione, "*" + estensione));
            File file = fc.showSaveDialog(null);
            Encrypt_Decrypt decripta = new Encrypt_Decrypt(Cipher.DECRYPT_MODE, logged.getEncr_key());
            if (file != null) {
                System.out.println(file.getAbsolutePath());
                decripta.Decrypt(this.file_selezionato, file.getAbsolutePath());
            }

        }else{
            System.out.println("nessun file da criptare o da decriptare e' stato selezionato");
        }

    }

    @FXML
    void torna_indietro(MouseEvent event) {
        cripta_file.setText("Scegli file da decriptare");
        salva.setDisable(true);
    }

}
