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

    Encrypt_Decrypt(int cipherMode, String chiave) {
        SecretKeySpec secretKey = null;

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

    String Encrypt(String text) {
        byte[] encrypt = new byte[0];
        try {
            encrypt = this.cipher.doFinal(text.getBytes());

        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);

        }
        return Base64.getEncoder().encodeToString(encrypt);
    }

    String Decrypt(String text) {
        byte[] temp = Base64.getDecoder().decode(text);
        byte[] decrypt = new byte[0];
        try {
            decrypt = this.cipher.doFinal(temp);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        return new String(decrypt);
    }

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
            byte[] inputBytes = inputStream.readAllBytes();
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