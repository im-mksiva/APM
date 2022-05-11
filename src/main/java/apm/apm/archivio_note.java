package apm.apm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class archivio_note extends base_operations {
    private int user_id;
    private ArrayList<note> lista_note;
    private String user_pass;
    private User user;
    SQLite_agent db_agent;

    archivio_note(User user) {
        this.user = user;
        this.db_agent = user.db_agent;
    }


    public void add(note new_nota) {

        new_nota.Encrypt(user.getEncr_key());
        db_agent.insert_note(user.getId(), new_nota);

    }

    @Override
    public void remove(Object nota) {

        note toRemove = (note) nota;
        db_agent.deleteRecord(toRemove.getId(), "note", "note_id");


    }

    public void removeAll() {

        db_agent.deleteRecord(user.getId(), "note", "user_id");

    }


    @Override
    public ArrayList find(String text) {

        ResultSet result = db_agent.search(text, user.getId(), 1);
        ArrayList<note> lista = new ArrayList<>();
        boolean continua;
        try {
            continua = result.next();
            while (continua) {
                note nota = new note(
                        result.getInt("note_id"),
                        result.getInt("user_id"),
                        result.getString("tag"),
                        result.getString("testo"),
                        result.getString("last_modified"),
                        result.getString("nome")
                );
                // sblocco la password così l'utente se vuole la può vedere
                nota.Decrypt(user.getEncr_key());
                lista.add(nota);
                continua = result.next();
            }
            return lista;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<note> getLista_note() {
        ResultSet result = db_agent.get_all_Note(user.getId());
        ArrayList<note> lista = new ArrayList<>();
        boolean continua;
        try {
            continua = result.next();
            while (continua) {
                //System.out.println(result.getString("username"));
                note nota = new note(
                        result.getInt("note_id"),
                        result.getInt("user_id"),
                        result.getString("tag"),
                        result.getString("testo"),
                        result.getString("last_modified"),
                        result.getString("nome")
                );
                // sblocco la password così l'utente se vuole la può vedere
                //System.out.println("criptata " + nota.getTesto());
                nota.Decrypt(user.getEncr_key());
                //System.out.println("       ->    decriptata" + nota.getTesto());
                lista.add(nota);
                continua = result.next();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

















}

