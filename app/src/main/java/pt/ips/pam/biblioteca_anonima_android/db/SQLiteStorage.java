package pt.ips.pam.biblioteca_anonima_android.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.ips.pam.biblioteca_anonima_android.MainActivity;

public class SQLiteStorage {

    private final Context currentContext;
    private final SharedPreferences sPref;
    private final SQLiteDatabase LocalDB;

    public SQLiteStorage(Context context) {
        currentContext = context;
        sPref = context.getSharedPreferences("pref_PAM", Context.MODE_PRIVATE);
        LocalDB = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath("SQLite_PAM").toString(), null, null);
    }

    private Cursor SQLResult;
    private JSONArray array;
    private JSONObject object;

    public JSONArray getBooks() throws NullPointerException {
        try {
            if (checkCursor("SELECT * FROM Livro")) {
                array = new JSONArray();
                do {
                    object = new JSONObject();
                    object.put("", SQLResult.getInt(0));

                    array.put(object);
                    SQLResult.moveToNext();
                }
                while (!SQLResult.isAfterLast());
                SQLResult.close();
                return array;
            }
            else return null;
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "JSON did an oppsie.", Toast.LENGTH_LONG).show();
            return null;
        }
    }
    public JSONObject getBook(int index) throws NullPointerException {
        try {
            if (checkCursor("SELECT * FROM Livro WHERE ID = " + index)) {
                object = new JSONObject();
                object.put("", SQLResult.getInt(0));

                SQLResult.close();
                return object;
            }
            else return null;
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "JSON did an oppsie.", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public JSONArray getAuthors() throws NullPointerException {
        try {
            if (checkCursor("SELECT * FROM Autor")) {
                array = new JSONArray();
                do {
                    object = new JSONObject();
                    object.put("ID", SQLResult.getInt(0));
                    object.put("Nome", SQLResult.getString(1));
                    object.put("Pais", SQLResult.getString(2));
                    array.put(object);
                    SQLResult.moveToNext();
                }
                while (!SQLResult.isAfterLast());
                SQLResult.close();
                return array;
            }
            else return null;
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "JSON did an oppsie.", Toast.LENGTH_LONG).show();
            return null;
        }
    }
    public JSONObject getAuthor(int index) throws NullPointerException {
        try {
            if (checkCursor("SELECT * FROM Autor WHERE ID = " + index)) {
                object = new JSONObject();
                object.put("ID", SQLResult.getInt(0));
                object.put("Nome", SQLResult.getString(1));
                object.put("Pais", SQLResult.getString(2));
                SQLResult.close();
                return object;
            }
            else return null;
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "JSON did an oppsie.", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public JSONArray getCategories() throws NullPointerException {
        try {
            if (checkCursor("SELECT * FROM Categoria")) {
                array = new JSONArray();
                do {
                    object = new JSONObject();
                    object.put("ID", SQLResult.getInt(0));
                    object.put("Nome", SQLResult.getString(1));
                    array.put(object);
                    SQLResult.moveToNext();
                }
                while (!SQLResult.isAfterLast());
                SQLResult.close();
                return array;
            }
            else return null;
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "JSON did an oppsie.", Toast.LENGTH_LONG).show();
            return null;
        }
    }
    public JSONObject getCategory(int index) throws NullPointerException {
        try {
            if (checkCursor("SELECT * FROM Categoria WHERE ID = " + index)) {
                object = new JSONObject();
                object.put("ID", SQLResult.getInt(0));
                object.put("Nome", SQLResult.getString(1));
                SQLResult.close();
                return object;
            }
            else return null;
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "JSON did an oppsie.", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public JSONArray getPublishers() throws NullPointerException {
        try {
            if (checkCursor("SELECT * FROM Editora")) {
                array = new JSONArray();
                do {
                    object = new JSONObject();
                    object.put("ID", SQLResult.getInt(0));
                    object.put("Nome", SQLResult.getString(1));
                    object.put("Pais", SQLResult.getString(2));
                    object.put("Logo", SQLResult.getString(3));
                    array.put(object);
                    SQLResult.moveToNext();
                }
                while (!SQLResult.isAfterLast());
                SQLResult.close();
                return array;
            }
            else return null;
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "JSON did an oppsie.", Toast.LENGTH_LONG).show();
            return null;
        }
    }
    public JSONObject getPublisher(int index) throws NullPointerException {
        try {
            if (checkCursor("SELECT * FROM Editora WHERE ID = " + index)) {
                object = new JSONObject();
                object.put("ID", SQLResult.getInt(0));
                object.put("Nome", SQLResult.getString(1));
                object.put("Pais", SQLResult.getString(2));
                object.put("Logo", SQLResult.getString(3));
                SQLResult.close();
                return object;
            }
            else return null;
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "JSON did an oppsie.", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private boolean checkCursor(String sql) {
        try {
            SQLResult = LocalDB.rawQuery(sql, null);
            return SQLResult.moveToFirst();
        }
        catch (SQLiteException e) {
            Log.d("SQLiteException", "SQL did an oppsie.");
            return false;
        }
    }

    protected void copyToLocalDB(JSONArray result, VolleyHandler.callback callback) {
        try {
            LocalDB.execSQL("DROP TABLE IF EXISTS Autor");
            LocalDB.execSQL("CREATE TABLE IF NOT EXISTS Autor (ID int NOT NULL, Nome varchar(255) NOT NULL UNIQUE, Pais varchar(255) NOT NULL, PRIMARY KEY (ID))");

            LocalDB.execSQL("DROP TABLE IF EXISTS Editora");
            LocalDB.execSQL("CREATE TABLE IF NOT EXISTS Editora (ID int NOT NULL, Nome varchar(255) NOT NULL UNIQUE, Pais varchar(255) NOT NULL, Logo varchar(255) NOT NULL UNIQUE, PRIMARY KEY (ID))");

            LocalDB.execSQL("DROP TABLE IF EXISTS Categoria");
            LocalDB.execSQL("CREATE TABLE IF NOT EXISTS Categoria (ID int PRIMARY KEY, Nome varchar(100) NOT NULL UNIQUE)");

            LocalDB.execSQL("DROP TABLE IF EXISTS Livro");
            LocalDB.execSQL("CREATE TABLE IF NOT EXISTS Livro (ID int NOT NULL, Titulo varchar(255) NOT NULL UNIQUE, ISBN varchar(255) NOT NULL UNIQUE, Numero_Paginas int NOT NULL, IDEditora int NOT NULL, Capa varchar(255) NOT NULL UNIQUE, PRIMARY KEY (ID), FOREIGN KEY (IDEditora) REFERENCES Editora(ID) ON DELETE CASCADE ON UPDATE CASCADE)");

            LocalDB.execSQL("DROP TABLE IF EXISTS Livro_Autor");
            LocalDB.execSQL("CREATE TABLE Livro_Autor (IDLivro int NOT NULL, IDAutor int NOT NULL, FOREIGN KEY (IDLivro) REFERENCES Livro(ID) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (IDAutor) REFERENCES Autor(ID) ON DELETE CASCADE ON UPDATE CASCADE)");

            LocalDB.execSQL("DROP TABLE IF EXISTS Livro_Categoria");
            LocalDB.execSQL("CREATE TABLE Livro_Categoria (IDLivro int NOT NULL, IDCategoria int NOT NULL, FOREIGN KEY (IDLivro) REFERENCES Livro(ID) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (IDCategoria) REFERENCES Categoria(ID) ON DELETE CASCADE ON UPDATE CASCADE)");
        }
        catch (SQLiteException e) {
            Log.d("SQLiteException", e.toString());
            Toast.makeText(currentContext, "Erro a criar a BD local.", Toast.LENGTH_LONG).show();
            reset(null);
        }

        try {
            ContentValues values = new ContentValues();
            JSONArray authors = result.getJSONArray(0);
            for (int i = 0; i < authors.length(); i++) {
                JSONObject data = authors.getJSONObject(i);
                values.put("ID", data.getInt("ID"));
                values.put("Nome", data.getString("Nome"));
                values.put("Pais", data.getString("Pais"));
                if (LocalDB.insert("Autor", null, values) == -1)
                    throw new JSONException("Failed to insert row in local DB.");
                else values = new ContentValues();
            }

            JSONArray categories = result.getJSONArray(1);
            for (int i = 0; i < categories.length(); i++) {
                JSONObject data = categories.getJSONObject(i);
                values.put("ID", data.getInt("ID"));
                values.put("Nome", data.getString("Nome"));
                if (LocalDB.insert("Categoria", null, values) == -1)
                    throw new JSONException("Failed to insert row in local DB.");
                else values = new ContentValues();
            }

            JSONArray publishers = result.getJSONArray(2);
            for (int i = 0; i < publishers.length(); i++) {
                JSONObject data = publishers.getJSONObject(i);
                values.put("ID", data.getInt("ID"));
                values.put("Nome", data.getString("Nome"));
                values.put("Pais", data.getString("Pais"));
                values.put("Logo", data.getString("Logo"));
                if (LocalDB.insert("Editora", null, values) == -1)
                    throw new JSONException("Failed to insert row in local DB.");
                else values = new ContentValues();
            }

            JSONArray books = result.getJSONArray(3);
            for (int i = 0; i < books.length(); i++) {
                JSONObject data = books.getJSONObject(i);
                values.put("ID", data.getInt("ID"));
                values.put("Titulo", data.getString("Titulo"));
                values.put("ISBN", data.getString("ISBN"));
                values.put("Numero_Paginas", data.getInt("Numero_Paginas"));
                values.put("IDEditora", data.getInt("IDEditora"));
                values.put("Capa", data.getString("Capa"));
                if (LocalDB.insert("Livro", null, values) == -1)
                    throw new JSONException("Failed to insert row in local DB.");
                else values = new ContentValues();
            }

            JSONArray book_author = result.getJSONArray(4);
            for (int i = 0; i < book_author.length(); i++) {
                JSONObject data = book_author.getJSONObject(i);
                values.put("IDLivro", data.getInt("IDLivro"));
                values.put("IDAutor", data.getInt("IDAutor"));
                if (LocalDB.insert("Livro_Autor", null, values) == -1)
                    throw new JSONException("Failed to insert row in local DB.");
                else values = new ContentValues();
            }

            JSONArray book_category = result.getJSONArray(5);
            for (int i = 0; i < book_category.length(); i++) {
                JSONObject data = book_category.getJSONObject(i);
                values.put("IDLivro", data.getInt("IDLivro"));
                values.put("IDCategoria", data.getInt("IDCategoria"));
                if (LocalDB.insert("Livro_Categoria", null, values) == -1)
                    throw new JSONException("Failed to insert row in local DB.");
                else values = new ContentValues();
            }

            callback.onSuccess();
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "Erro a converter JSON para a BD local.", Toast.LENGTH_LONG).show();
            reset(null);
        }
    }

    protected void addLocalDB(DatabaseTables table, JSONObject data, VolleyHandler.callback callback) {
        try {
            if (checkCursor("SELECT ID FROM " + table.toString() + " ORDER BY ID ASC")) {
                int rowId;
                for (rowId = 1; rowId - 1 < SQLResult.getCount(); rowId++) {
                    if (SQLResult.getInt(0) != rowId) break;
                    SQLResult.moveToNext();
                }
                SQLResult.close();

                String sql = "INSERT INTO " + table + " VALUES ";
                switch (table) {
                    case CATEGORIA:
                        sql += "(" + rowId + ", '" + data.getString("Nome") + "')";
                        break;
                    case AUTOR:
                        sql += "(" + rowId + ", '" + data.getString("Nome") + "', '" + data.getString("Pais") + "')";
                        break;
                    case EDITORA:
                        sql += "(" + rowId + ", '" + data.getString("Nome") + "', '" + data.getString("Pais") + "', '" + data.getString("Logo") + "')";
                        break;
                    case LIVRO:
                        sql += "(" + rowId + ", '" + data.getString("Titulo") + "', '" + data.getString("ISBN") + "', " + data.getString("Numero_Paginas") + ", " + data.getString("IDEditora") + ", '" + data.getString("Capa") + "')";
                        try {
                            LocalDB.execSQL(sql);

                            ContentValues values = new ContentValues();
                            JSONArray book_author = data.getJSONArray("IDAutores");
                            for (int i = 0; i < book_author.length(); i++) {
                                values.put("IDLivro", rowId);
                                values.put("IDAutor", book_author.getInt(i));
                                if (LocalDB.insert("Livro_Autor", null, values) == -1)
                                    throw new Exception("Failed to insert row in local DB.");
                                else values = new ContentValues();
                            }

                            JSONArray book_category = data.getJSONArray("IDCategorias");
                            for (int i = 0; i < book_category.length(); i++) {
                                values.put("IDLivro", rowId);
                                values.put("IDCategoria", book_category.getInt(i));
                                if (LocalDB.insert("Livro_Categoria", null, values) == -1)
                                    throw new Exception("Failed to insert row in local DB.");
                                else values = new ContentValues();
                            }
                        }
                        catch (SQLiteException e) { throw new Exception("Failed to insert into local DB."); }
                        break;
                }
                try { LocalDB.execSQL(sql); }
                catch (SQLiteException e) { throw new Exception("Failed to insert into local DB."); }
                callback.onSuccess();
            }
            else throw new Exception("Failed to select from local DB.");
        }
        catch (Exception error) {
            Log.d("Exception", error.toString());
            Toast.makeText(currentContext, "Erro a adicionar na BD local.", Toast.LENGTH_LONG).show();
            reset(new VolleyHandler.callback() {
                @Override
                public void onSuccess() { goMain(); }
            });
        }
    }

    protected void updateLocalDB(DatabaseTables table, JSONObject data, VolleyHandler.callback callback) {
        try {
            int result;
            ContentValues values = new ContentValues();
            switch (table) {
                case CATEGORIA:
                    values.put("Nome", data.getString("Nome"));
                    break;
                case AUTOR:
                    values.put("Nome", data.getString("Nome"));
                    values.put("Pais", data.getString("Pais"));
                    break;
                case EDITORA:
                    values.put("Nome", data.getString("Nome"));
                    values.put("Pais", data.getString("Pais"));
                    values.put("Logo", data.getString("Logo"));
                    break;
                case LIVRO:
                    result = LocalDB.delete("Livro_Autor", "IDLivro = " + data.getInt("ID"), null);
                    if (result <= 0) throw new Exception("Failed to delete row in local DB.");
                    else {
                        JSONArray book_author = data.getJSONArray("IDAutores");
                        for (int i = 0; i < book_author.length(); i++) {
                            values.put("IDLivro", data.getInt("ID"));
                            values.put("IDAutor", book_author.getInt(i));
                            if (LocalDB.insert("Livro_Autor", null, values) == -1)
                                throw new Exception("Failed to insert row in local DB.");
                            else values = new ContentValues();
                        }

                        result = LocalDB.delete("Livro_Categoria", "IDLivro = " + data.getInt("ID"), null);
                        if (result <= 0) throw new Exception("Failed to delete row in local DB.");
                        else {
                            JSONArray book_category = data.getJSONArray("IDCategorias");
                            for (int i = 0; i < book_category.length(); i++) {
                                values.put("IDLivro", data.getInt("ID"));
                                values.put("IDCategoria", book_category.getInt(i));
                                if (LocalDB.insert("Livro_Categoria", null, values) == -1)
                                    throw new Exception("Failed to insert row in local DB.");
                                else values = new ContentValues();
                            }

                            values.put("Titulo", data.getString("Titulo"));
                            values.put("ISBN", data.getString("ISBN"));
                            values.put("Numero_Paginas", data.getInt("Numero_Paginas"));
                            values.put("IDEditora", data.getInt("IDEditora"));
                            values.put("Capa", data.getString("Capa"));
                        }
                    }
                    break;
            }
            result = LocalDB.update(table.toString(), values, "ID = " + data.getInt("ID"), null);
            if (result == 1) callback.onSuccess();
            else throw new Exception("Failed to update row in local DB.");
        }
        catch (Exception error) {
            Log.d("Exception", error.toString());
            Toast.makeText(currentContext, "Erro a atualizar na BD local.", Toast.LENGTH_LONG).show();
            reset(new VolleyHandler.callback() {
                @Override
                public void onSuccess() { goMain(); }
            });
        }
    }

    protected void deleteLocalDB(DatabaseTables table, int rowId, VolleyHandler.callback callback) {
        try {
            int result = LocalDB.delete(table.toString(), "ID = " + rowId, null);
            if (result == 1) callback.onSuccess();
            else throw new Exception("Failed to delete row in local DB.");
        }
        catch (Exception error) {
            Log.d("Exception", error.toString());
            Toast.makeText(currentContext, "Erro a apagar na BD local.", Toast.LENGTH_LONG).show();
            reset(new VolleyHandler.callback() {
                @Override
                public void onSuccess() { goMain(); }
            });
        }
    }

    protected void setAdmin(String name, VolleyHandler.callback callback) {
        sPref.edit().putString("admin", name).apply();
        callback.onSuccess();
    }

    public String getAdmin() throws NullPointerException {
        return sPref.getString("admin", null);
    }

    public void reset(@Nullable VolleyHandler.callback callback) {
        LocalDB.close();
        currentContext.deleteDatabase("SQLite_PAM");
        sPref.edit().clear().apply();
        if (callback != null) callback.onSuccess();
    }

    private void goMain() {
        Intent goToStart = new Intent(currentContext, MainActivity.class);
        ContextCompat.startActivity(currentContext, goToStart, null);
    }

    public void checkLocalDB() {
        try {
            SQLResult = LocalDB.rawQuery("SELECT ID FROM Livro", null);
            if (!SQLResult.moveToFirst()) {
                SQLResult.close();
                new DatabaseRequest(currentContext).getData();
            }
        }
        catch (SQLiteException e) { new DatabaseRequest(currentContext).getData(); }
    }
}
