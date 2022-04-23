import java.util.ArrayList;

public class archivio_note implements base_operations {
    private int id;
    private ArrayList<note> lista_note;
    private SQLite_agent db;
    private String user_pass;

    archivio_note(String user_id) {
        db = new SQLite_agent();
    }

    @Override
    public void add() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void update() {

    }

    @Override
    public void find() {

    }
}
