package pt.ips.pam.biblioteca_anonima_android;

import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public enum DatabaseTables {
    AUTOR, EDITORA, LIVRO, CATEGORIA;

    @NonNull
    public String toString() {
        String table = "";
        if (this == AUTOR) {
            table = "Autor";
        } else if (this == EDITORA) {
            table = "Editora";
        } else if (this == LIVRO) {
            table = "Livro";
        } else if (this == CATEGORIA) {
            table = "Categoria";
        }
        return table;
    }

    public boolean checkJSON(DatabaseTables table, JSONObject data) {
        boolean pass = false;

        try {
            switch (table) {
                case CATEGORIA:
                    if (data.length() == 1 && !data.getString("Nome").equals("")) pass = true;
                    break;
                case AUTOR:
                case EDITORA:
                    if (data.length() == 2 && !data.getString("Nome").equals("") && !data.getString("Pais").equals(""))
                        pass = true;
                    break;
                case LIVRO:
                    if (data.length() == 5 && !data.getString("Titulo").equals("") && !data.getString("ISBN").equals("") && data.getInt("Numero_Paginas") > 0 && data.getInt("IDEditora") > 0 && !data.getString("Capa").equals(""))
                        pass = true;
                    break;
            }
        }
        catch (JSONException e) {
            Log.d("volleyError", "JSON fields were wrong.\n" + e);
        }
        return pass;
    }
}