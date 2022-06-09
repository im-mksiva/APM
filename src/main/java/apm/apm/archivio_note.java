package apm.apm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class archivio_note implements base_operations {
    private User user;
    SQLite_agent db_agent;

    archivio_note(User user) {
        this.user = user;
        this.db_agent = user.db_agent;
    }


    public void add(Object nuova_nota) {
        note new_nota = (note)nuova_nota;
        new_nota.Encrypt(user.getEncr_key());
        db_agent.insert_note(new_nota);

    }

    @Override
    public void remove(Object nota) {

        note toRemove = (note) nota;
        db_agent.deleteRecord(toRemove.getId(), "note", "note_id");


    }

    public void removeAll() {

        db_agent.deleteRecord(user.getId(), "note", "user_id");

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

