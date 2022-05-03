import java.sql.SQLException;
import java.util.ArrayList;

public class archivio_note extends base_operations {
    private int user_id;
    private ArrayList<note> lista_note;
    private String user_pass;
    private User user;

    archivio_note(User user) {
        this.user = user;
    }


    public void add(note new_nota) {
        SQLite_agent db_agent = new SQLite_agent();
        new_nota.Encrypt(user.getEncr_key());
        db_agent.insert_note(user.getId(), new_nota);
        db_agent.closeConnection();
    }

    @Override
    public void remove(Object nota) {
        SQLite_agent db_agent = new SQLite_agent();
        note toRemove = (note) nota;
        db_agent.deleteRecord(toRemove.getId(), "note", "note_id");
        db_agent.closeConnection();

    }

    public void removeAll() {
        SQLite_agent db_agent = new SQLite_agent();
        db_agent.deleteRecord(user.getId(), "note", "user_id");
        db_agent.closeConnection();
    }


    @Override
    public ArrayList find(String text) {
        SQLite_agent db_agent = new SQLite_agent();
        return null;
    }
}
