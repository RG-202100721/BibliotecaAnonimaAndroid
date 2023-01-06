package pt.ips.pam.biblioteca_anonima_android.db;

import android.content.Context;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

public class SQLiteStorage {

    private final Context currentContext;

    public SQLiteStorage(Context context) {
        currentContext = context;
    }

    public void getBooks() {

    }
    public void getBook(int index) {

    }

    public void getAuthors() {

    }
    public void getAuthor(int index) {

    }

    public void getCategories() {

    }
    public void getCategory(int index) {

    }

    public void getPublishers() {

    }
    public void getPublisher(int index) {

    }

    public void copyToLocalDB(JSONArray result, VolleyHandler.callback callback) {

        callback.onSuccess();
    }

    public void addLocalDB(JSONObject data, VolleyHandler.callback callback) {

    }

    public void updateLocalDB(JSONObject data, VolleyHandler.callback callback) {

    }

    public void deleteLocalDB(JSONObject data, VolleyHandler.callback callback) {

    }

    public void setAdmin(String name, VolleyHandler.callback callback) {

    }

    public void getAdmin() {

    }

    public void checkLocalDB() {
        
        if (false) new DatabaseRequest(currentContext).getData(null);
    }

    public void reset(@Nullable VolleyHandler.callback callback) {

        if (callback != null) callback.onSuccess();
    }
}
