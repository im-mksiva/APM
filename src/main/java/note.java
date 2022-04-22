import java.util.Date;

public class note implements Encrypt_decrypt_info {

    private String testo, tag;

    private int id;
    private Date last_modifed;

    public note(String testo, String tag) {
        this.testo = testo;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public Date getLast_modifed() {
        return last_modifed;
    }


    @Override
    public void Encrypt() {

    }

    @Override
    public void Decrypt() {

    }
}
