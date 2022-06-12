package apm.apm;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt_Decrypt {
    private Cipher cipher = null;

    //costruttore
    //Quando inizializzo l'oggetto scelgo la modalità operativa (Cripto o decripto)
    // e passo la chiave master come chiave di cifratura per la criptazione dei dati
    Encrypt_Decrypt(int cipherMode, String chiave) {
        SecretKeySpec secretKey = null;
        //criptazione della encr_key, come chiave uso la pass dell'utente, verifico prima se la user_pass è di 16 caratteri.
        // In caso contrario creo una stringa a partire dalla user_pass

            while (chiave.length() < 16) {
                chiave += chiave;
            }
            chiave = chiave.substring(0, 16);
            try {
                secretKey = new SecretKeySpec(chiave.getBytes("UTF-8"), "AES");
                this.cipher = Cipher.getInstance("AES");
                this.cipher.init(cipherMode, secretKey);
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }


    // uso la chiave già presente nell'oggetto istanziato per criptare le informazioni. Qui passo "text" contenente le informazioni da criptare
    String Encrypt(String text) {
        byte[] encrypt = new byte[0];
        try {
            encrypt = this.cipher.doFinal(text.getBytes());

        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);

        }
        return Base64.getEncoder().encodeToString(encrypt);    // codifico in una stringa per semplificare le operazioni di inserimento nel db (o di manipolazione)
    }

    // uso la chiave già presente nell'oggetto istanziato per decriptare le informazioni. Qui invece passo le informazioni che devono essere decodificate.
    String Decrypt(String text) {
        byte[] temp = Base64.getDecoder().decode(text); // converto le informazioni salvate (ad esempio le password nel db) in un formato utile al cifrario
        byte[] decrypt = new byte[0];
        try {
            decrypt = this.cipher.doFinal(temp);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return new String(decrypt);
    }


    //metodo per criptare i file
    void Encrypt(File file_da_criptare, String percorso_salva_file) {
        FileInputStream inputStream = null;
        String percorso = file_da_criptare.getAbsolutePath();
        int punto = percorso.lastIndexOf(".");
        String estensione = percorso.substring(punto, percorso.length());
        if(estensione.contentEquals(".cripto")){
            return;
        }
        try {
            inputStream = new FileInputStream(file_da_criptare);
            byte[] inputBytes = new byte[(int) file_da_criptare.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes = new byte[0];
            outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream file_criptato = null;
            file_criptato = new FileOutputStream(percorso_salva_file + ".cripto");
            file_criptato.write(outputBytes);
            inputStream.close();
            file_criptato.close();
        } catch (IllegalBlockSizeException | IOException | BadPaddingException e) {
            e.printStackTrace();
        }

    }

    //metodo per decriptare i file
    void Decrypt(File file_da_decriptare, String percorso_file_da_decriptare)  {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file_da_decriptare);
            byte[] inputBytes = inputStream.readAllBytes();
            byte[] outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream file_criptato = new FileOutputStream(percorso_file_da_decriptare);
            file_criptato.write(outputBytes);
            inputStream.close();
            file_criptato.close();
        } catch (IllegalBlockSizeException | BadPaddingException | IOException e) {
            e.printStackTrace();
        }


    }

}