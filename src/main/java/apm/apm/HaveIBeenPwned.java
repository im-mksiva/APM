package apm.apm;

import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class HaveIBeenPwned {

    /* Sha-1 è un algoritmo di tipo hash e come tale produce un digest di lunghezza fissa a partire da un messaggio di lunghezza variabile.
    I punti di forza degli algoritmi di tipo hash sono:
    1 - l'elevata improbablità che 2 messaggi uguali abbiano lo stesso digest;
    2 - la funzione hash è invertibile, ovvero non è possibile risalire dal digest alla password.
    Sha-1 produce un digest del messaggio di 160 bit.
    */
    //prende in ingresso una password come stringa e restituisce un digest sempre come stringa
    private String digest_sha1(String password) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }


    //prende in ingresso un digest come stringa, la rende maiuscola e separa i primi 5 caratteri
    //dai restanti inserendoli in una lista
    private String[] prefix_suffix(String digest) {
        String[] lista = {null, null};
        String new_digest = digest.toUpperCase();
        lista[0] = new_digest.substring(0, 5);
        lista[1] = new_digest.substring(5, new_digest.length());
        return lista;
    }

    //prende in ingresso un i primi 5 caratteri del digest per ottenere il file tramite URL
    String valuta_password(String password) throws NoSuchAlgorithmException, IOException {
        String digest = digest_sha1(password);
        String[] lista = prefix_suffix(digest);
        String prefix = lista[0];
        String suffix = lista[1];
        URL url = null;
        url = new URL("https://api.pwnedpasswords.com/range/" + prefix);
        BufferedReader read = null;
        read = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = read.readLine();

        while (line != null) {
            String[] linea_divisa = line.split(":");
            if (Objects.equals(linea_divisa[0], suffix)) {
                return linea_divisa[1];
            }
            line = read.readLine();
        }
        read.close();
        return null;
    }


}