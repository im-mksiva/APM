import java.util.ArrayList;

public class archivio_note extends base_operations {
    private int user_id;
    private ArrayList<note> lista_note;
    private String user_pass;

    archivio_note(int user_id) {
        this.user_id = user_id;
    }


    public void add(note new_nota) {
        SQLite_agent db_agent = new SQLite_agent();

        db_agent.insert_note(this.user_id, new_nota);


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
