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
    Cipher cipher = null;

    //come funziona? Abbiamo un oggetto capace di poter effettuare le operazioni. Quando inizializzo l'oggetto scelgo la modalità operativa (Cripto o decripto)
    // e passo la password dell'utente come base per la chiave di cifratura.
    Encrypt_Decrypt(int cipherMode, String chiave) {
        SecretKeySpec secretKey = null;
        //criptazione della encr_key, come chiave uso la pass dell'utente, verifico prima se la user_pass è di 16 caratteri.
        // In caso contrario creo una stringa a partire dalla user_pass

        while (chiave.length() < 17) {
            chiave += chiave;
        }
        chiave = chiave.substring(0, 16);
        System.out.println("la chiave da 16 è: " + chiave);

        try {
            secretKey = new SecretKeySpec(chiave.getBytes("UTF-8"), "AES");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        try {
            this.cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        try {
            this.cipher.init(cipherMode, secretKey);
        } catch (InvalidKeyException e) {
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


    void Encrypt(File file_da_criptare) {
        FileInputStream inputStream = null;
        String percorso = file_da_criptare.getAbsolutePath();
        try {
            inputStream = new FileInputStream(file_da_criptare);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        byte[] inputBytes = new byte[(int) file_da_criptare.length()];
        try {
            inputStream.read(inputBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] outputBytes = new byte[0];
        try {
            outputBytes = cipher.doFinal(inputBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
//        FileOutputStream file_criptato = new FileOutputStream(percorso.substring(0, percorso.lastIndexOf(".")) + "_cripto");
        FileOutputStream file_criptato = null;
        try {
            file_criptato = new FileOutputStream(percorso + ".cripto");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            file_criptato.write(outputBytes);
            inputStream.close();
            file_criptato.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void Decrypt(File file_da_decriptare) throws IOException, IllegalBlockSizeException, BadPaddingException {
        FileInputStream inputStream = new FileInputStream(file_da_decriptare);
        byte[] inputBytes = new byte[(int) file_da_decriptare.length()];
        byte[] outputBytes = cipher.doFinal(inputBytes);

        String percorso = file_da_decriptare.getAbsolutePath();
        FileOutputStream file_criptato = new FileOutputStream(percorso.substring(0, percorso.lastIndexOf(".")));
        file_criptato.write(outputBytes);

        inputStream.close();
        file_criptato.close();
    }

}