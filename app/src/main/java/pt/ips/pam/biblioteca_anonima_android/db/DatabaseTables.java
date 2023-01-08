//enumeração das tabelas da base de dados e verificação do JSON a ser enviado para a API.

package pt.ips.pam.biblioteca_anonima_android.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum DatabaseTables {
    ADMIN, AUTOR, EDITORA, LIVRO, CATEGORIA;

    @NonNull
    public String toString() {
        String table = "";
        if (this == ADMIN) {
            table = "Administrador";
        } else if (this == AUTOR) {
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

    public boolean checkJSON(DatabaseTables table, JSONObject data, Context context) {
        boolean pass = false, conflict = false;
        JSONArray trial;
        SQLiteStorage SQLite = new SQLiteStorage(context);
        String text = "Campos ou valores do objeto JSON estão errados.\n";
        try {
            switch (table) {
                case CATEGORIA:
                    text += "Campo JSON para a tabela \"Categoria\" é:\nNome [String] - UNIQUE";
                    if (data.length() == 1 && !data.getString("Nome").equals("")) {
                        trial = SQLite.getCategories();
                        for (int i = 0; i < trial.length(); i++)
                            if (trial.getJSONObject(i).getString("Nome").equals(data.getString("Nome")))
                                conflict = true;
                        if (!conflict) pass = true;
                    }
                    break;

                case AUTOR:
                    text += "Campos JSON para a tabela \"Autor\" são:\nNome [String] - UNIQUE\nPais [String]";
                    if (data.length() == 2 && !data.getString("Nome").equals("") && !data.getString("Pais").equals("")) {
                        trial = SQLite.getAuthors();
                        for (int i = 0; i < trial.length(); i++)
                            if (trial.getJSONObject(i).getString("Nome").equals(data.getString("Nome")))
                                conflict = true;
                        if (!conflict) pass = true;
                    }
                    break;

                case EDITORA:
                    text += "Campos JSON para a tabela \"Editora\" são:\nNome [String] - UNIQUE\nPais [String]\nLogo [String] - UNIQUE";
                    if (data.length() == 3 && !data.getString("Nome").equals("") && !data.getString("Pais").equals("")
                            && !data.getString("Logo").equals("")) {
                        trial = SQLite.getPublishers();
                        for (int i = 0; i < trial.length(); i++)
                            if (trial.getJSONObject(i).getString("Nome").equals(data.getString("Nome"))
                                    || trial.getJSONObject(i).getString("Logo").equals(data.getString("Logo")))
                                conflict = true;
                        if (!conflict) pass = true;
                    }
                    break;

                case LIVRO:
                    text += "Campos JSON para a tabela \"Livro\" são:\nTitulo [String] - UNIQUE\nISBN [String] - UNIQUE\nNumero_Paginas [int]\nIDEditora [int]\nCapa [String] - UNIQUE\nIDAutores [Array JSON de ints]\nIDCategorias [Array JSON de ints]";
                    if (data.length() == 7 && !data.getString("Titulo").equals("") && !data.getString("ISBN").equals("")
                            && data.getInt("Numero_Paginas") > 0 && data.getInt("IDEditora") > 0
                            && !data.getString("Capa").equals("")) {
                        trial = SQLite.getBooks();
                        for (int i = 0; i < trial.length(); i++)
                            if (trial.getJSONObject(i).getString("Titulo").equals(data.getString("Titulo"))
                                    || trial.getJSONObject(i).getString("ISBN").equals(data.getString("ISBN"))
                                    || trial.getJSONObject(i).getString("Capa").equals(data.getString("Capa")))
                                conflict = true;
                        if (!conflict) {
                            trial = SQLite.getPublishers();
                            for (int i = 0; i < trial.length(); i++) {
                                if (trial.getJSONObject(i).getInt("ID") == data.getInt("IDEditora")) {
                                    conflict = false;
                                    break;
                                }
                                else conflict = true;
                            }
                            if (!conflict) {
                                JSONArray categorias = data.getJSONArray("IDCategorias");
                                JSONArray autores = data.getJSONArray("IDAutores");
                                if (autores.length() > 0 && categorias.length() > 0) {
                                    trial = SQLite.getAuthors();
                                    for (int i = 0; i < autores.length(); i++) {
                                        if (autores.getInt(i) <= 0)
                                            throw new JSONException("DB row index 0 or below");
                                        for (int x = 0; x < trial.length(); x++) {
                                            if (autores.getInt(i) == trial.getJSONObject(x).getInt("ID")) {
                                                conflict = false;
                                                break;
                                            } else conflict = true;
                                        }
                                        if (conflict) break;
                                    }
                                    if (!conflict) {
                                        trial = SQLite.getCategories();
                                        for (int i = 0; i < categorias.length(); i++) {
                                            if (categorias.getInt(i) <= 0)
                                                throw new JSONException("DB row index 0 or below");
                                            for (int x = 0; x < trial.length(); x++) {
                                                if (categorias.getInt(i) == trial.getJSONObject(x).getInt("ID")) {
                                                    conflict = false;
                                                    break;
                                                } else conflict = true;
                                            }
                                            if (conflict) break;
                                        }
                                        if (!conflict) pass = true;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case ADMIN:
                    text += "Campos JSON para fazer o login são:\nNumero_Conta [int]\nPassword [String]";
                    if (data.length() == 2 && data.getInt("Numero_Conta") > 0 && !data.getString("Password").equals(""))
                        pass = true;
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