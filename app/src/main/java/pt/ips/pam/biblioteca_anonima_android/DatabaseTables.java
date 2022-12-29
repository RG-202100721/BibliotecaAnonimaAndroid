//enumeração das tabelas da base de dados e verificação do JSON a ser enviado para a API.

package pt.ips.pam.biblioteca_anonima_android;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
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
        String text = "Campos ou valores do objeto JSON estão errados.\n";
        try {
            switch (table) {
                case CATEGORIA:
                    text += "Campo JSON para a tabela \"Categoria\" é:\nNome [String]";
                    if (data.length() == 1 && !data.getString("Nome").equals("")) pass = true;
                    break;
                case AUTOR:
                case EDITORA:
                    text += "Campos JSON para a tabela \"Autor\" e \"Editora\" são:\nNome [String]\nPais [String]";
                    if (data.length() == 2 && !data.getString("Nome").equals("") && !data.getString("Pais").equals(""))
                        pass = true;
                    break;
                case LIVRO:
                    text += "Campos JSON para a tabela \"Livro\" são:\nTitulo [String]\nISBN [String]\nNumero_Paginas [int]\nIDEditora [int]\nCapa [String]\nIDAutores [Array JSON de ints]\nIDCategorias [Array JSON de ints]";
                    if (data.length() == 7 && !data.getString("Titulo").equals("") && !data.getString("ISBN").equals("")
                            && data.getInt("Numero_Paginas") > 0 && data.getInt("IDEditora") > 0
                            && !data.getString("Capa").equals("")) {
                        JSONArray autores = data.getJSONArray("IDAutores");
                        JSONArray categorias = data.getJSONArray("IDCategorias");
                        if (autores.length() > 0 && categorias.length() > 0) {
                            for (int i = 0; i < autores.length(); i++)
                                if (autores.getInt(i) <= 0) throw new JSONException("DB row index 0 or below");
                            for (int i = 0; i < categorias.length(); i++)
                                if (categorias.getInt(i) <= 0) throw new JSONException("DB row index 0 or below");
                            pass = true;
                        }
                    }
                    break;
            }
            if (!pass) errorJSON(text, null);
        }
        catch (JSONException e) {
            errorJSON(text, e);
        }
        return pass;
    }

    public void errorJSON(String text, @Nullable JSONException e) {
        if (e == null) Log.d("volleyError", text);
        else Log.d("volleyError", e + "\n" + text);
    }
}