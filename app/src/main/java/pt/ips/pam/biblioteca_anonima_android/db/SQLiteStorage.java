package pt.ips.pam.biblioteca_anonima_android.db;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

public class SQLiteStorage {

    public SQLiteStorage() {

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

    public void addLocalDB(JSONObject data) {

    }

    public void updateLocalDB(JSONObject data) {

    }

    public void deleteLocalDB(JSONObject data) {

    }

    public void getAdmin() {

    }

    public void reset(@Nullable VolleyHandler.callback callback) {

        if (callback != null) callback.onSuccess();
    }
}
