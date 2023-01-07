package pt.ips.pam.biblioteca_anonima_android.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SQLiteStorage {

    private final Context currentContext;
    private final SharedPreferences sPref;
    private final SQLiteDatabase LocalDB;

    public SQLiteStorage(Context context) {
        currentContext = context;
        sPref = context.getSharedPreferences("pref_PAM", Context.MODE_PRIVATE);
        LocalDB = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath("SQLite_PAM").toString(), null, errorHandler());
    }

    private DatabaseErrorHandler errorHandler() {
        return new DatabaseErrorHandler() {
            @Override
            public void onCorruption(SQLiteDatabase dbObj) {
                Toast.makeText(currentContext, "Base de dados está com problemas.", Toast.LENGTH_LONG).show();
                reset(null);
            }
        };
    }

    private Cursor SQLResult;
    private JSONArray array;
    private JSONObject object;

    public JSONArray getBooks() {
        try {
            if (checkCursor("SELECT * FROM Livro")) {
                object = new JSONObject();
                array = new JSONArray();
                do {
                    object.put("", SQLResult.getInt());

                    array.put(object);
                    SQLResult.moveToNext();
                }
                while (SQLResult.isAfterLast());
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
    public JSONObject getBook(int index) {
        try {
            if (checkCursor("SELECT * FROM Livro WHERE ID = " + index)) {
                object = new JSONObject();
                object.put("", SQLResult.getInt());

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

    public JSONArray getAuthors() {
        try {
            if (checkCursor("SELECT * FROM Autor")) {
                object = new JSONObject();
                array = new JSONArray();
                do {
                    object.put("", SQLResult.getInt());

                    array.put(object);
                    SQLResult.moveToNext();
                }
                while (SQLResult.isAfterLast());
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
    public JSONObject getAuthor(int index) {
        try {
            if (checkCursor("SELECT * FROM Autor WHERE ID = " + index)) {
                object = new JSONObject();
                object.put("", SQLResult.getInt());

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

    public JSONArray getCategories() {
        try {
            if (checkCursor("SELECT * FROM Categoria")) {
                object = new JSONObject();
                array = new JSONArray();
                do {
                    object.put("", SQLResult.getInt());

                    array.put(object);
                    SQLResult.moveToNext();
                }
                while (SQLResult.isAfterLast());
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
    public JSONObject getCategory(int index) {
        try {
            if (checkCursor("SELECT * FROM Categoria WHERE ID = " + index)) {
                object = new JSONObject();
                object.put("", SQLResult.getInt());

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

    public JSONArray getPublishers() {
        try {
            if (checkCursor("SELECT * FROM Editora")) {
                object = new JSONObject();
                array = new JSONArray();
                do {
                    object.put("ID", SQLResult.getInt(0));
                    object.put("Nome", SQLResult.getString(1));
                    object.put("Pais", SQLResult.getString(2));
                    object.put("Logo", SQLResult.getString(3));
                    array.put(object);
                    SQLResult.moveToNext();
                }
                while (SQLResult.isAfterLast());
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
    public JSONObject getPublisher(int index) {
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

        try {
            ContentValues values = new ContentValues();
            JSONArray authors = result.getJSONArray(0);
            for (int i = 0; i < authors.length(); i++) {
                JSONObject data = authors.getJSONObject(i);
                values.put("ID", data.getInt("ID"));
                values.put("Nome", data.getString("Nome"));
                values.put("Pais", data.getString("Pais"));
            }
            LocalDB.insert("Autor", null, values);

            values = new ContentValues();
            JSONArray categories = result.getJSONArray(1);
            for (int i = 0; i < categories.length(); i++) {
                JSONObject data = categories.getJSONObject(i);
                values.put("ID", data.getInt("ID"));
                values.put("Nome", data.getString("Nome"));
            }
            LocalDB.insert("Categoria", null, values);

            values = new ContentValues();
            JSONArray publishers = result.getJSONArray(2);
            for (int i = 0; i < publishers.length(); i++) {
                JSONObject data = publishers.getJSONObject(i);
                values.put("ID", data.getInt("ID"));
                values.put("Nome", data.getString("Nome"));
                values.put("Pais", data.getString("Pais"));
                values.put("Logo", data.getString("Logo"));
            }
            LocalDB.insert("Editora", null, values);

            values = new ContentValues();
            JSONArray books = result.getJSONArray(3);
            for (int i = 0; i < books.length(); i++) {
                JSONObject data = books.getJSONObject(i);
                values.put("ID", data.getInt("ID"));
                values.put("Titulo", data.getString("Titulo"));
                values.put("ISBN", data.getString("ISBN"));
                values.put("Numero_Paginas", data.getInt("Numero_Paginas"));
                values.put("IDEditora", data.getInt("IDEditora"));
                values.put("Capa", data.getString("Capa"));
            }
            LocalDB.insert("Livro", null, values);

            values = new ContentValues();
            JSONArray book_author = result.getJSONArray(4);
            for (int i = 0; i < book_author.length(); i++) {
                JSONObject data = book_author.getJSONObject(i);
                values.put("IDLivro", data.getInt("IDLivro"));
                values.put("IDAutor", data.getInt("IDAutor"));
            }
            LocalDB.insert("Livro_Autor", null, values);

            values = new ContentValues();
            JSONArray book_category = result.getJSONArray(5);
            for (int i = 0; i < book_category.length(); i++) {
                JSONObject data = book_category.getJSONObject(i);
                values.put("IDLivro", data.getInt("IDLivro"));
                values.put("IDCategoria", data.getInt("IDCategoria"));
            }
            LocalDB.insert("Livro_Categoria", null, values);

            callback.onSuccess();
        }
        catch (JSONException error) {
            Log.d("JSONException", error.toString());
            Toast.makeText(currentContext, "Erro a copiar para a BD local.", Toast.LENGTH_LONG).show();
        }
    }

    protected void addLocalDB(DatabaseTables table, JSONObject data, VolleyHandler.callback callback) {
        try {
            switch (table) {
                case CATEGORIA:
                    break;
                case AUTOR:
                case EDITORA:
                    break;
                case LIVRO:
                    break;
            }
            callback.onSuccess();
        }
        catch (Exception error) {
            Log.d("Exception", error.toString());
            Toast.makeText(currentContext, "Erro a adicionar para a BD local.", Toast.LENGTH_LONG).show();
        }
    }

    protected void updateLocalDB(DatabaseTables table, JSONObject data, VolleyHandler.callback callback) {
        try {
            switch (table) {
                case CATEGORIA:
                    break;
                case AUTOR:
                case EDITORA:
                    break;
                case LIVRO:
                    break;
            }
            callback.onSuccess();
        }
        catch (Exception error) {
            Log.d("Exception", error.toString());
            Toast.makeText(currentContext, "Erro a atualizar a BD local.", Toast.LENGTH_LONG).show();
        }
    }

    protected void deleteLocalDB(DatabaseTables table, JSONObject data, VolleyHandler.callback callback) {
        try {
            switch (table) {
                case CATEGORIA:
                    break;
                case AUTOR:
                case EDITORA:
                    break;
                case LIVRO:
                    break;
            }
            callback.onSuccess();
        }
        catch (Exception error) {
            Log.d("Exception", error.toString());
            Toast.makeText(currentContext, "Erro a apagar a BD local.", Toast.LENGTH_LONG).show();
        }
    }

    public void setAdmin(String name, VolleyHandler.callback callback) {
        sPref.edit().putString("admin", name).apply();
        callback.onSuccess();
    }

    public String getAdmin() {
        return sPref.getString("admin", null);
    }

    protected void reset(@Nullable VolleyHandler.callback callback) {
        LocalDB.close();
        currentContext.deleteDatabase("SQLite_PAM");
        sPref.edit().clear().apply();
        if (callback != null) callback.onSuccess();
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
