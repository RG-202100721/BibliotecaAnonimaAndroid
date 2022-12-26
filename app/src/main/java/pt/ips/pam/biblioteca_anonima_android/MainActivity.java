package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private DatabaseRequest DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DatabaseRequest(MainActivity.this);

        DB.getBooks(new VolleyHandler.callback() {
            @Override
            public void onSuccess(JSONArray data) throws JSONException {
                for (int i = 0; i < data.length(); i++){
                    JSONObject book = data.getJSONObject(i);

                    TextView text = findViewById(R.id.text);
                    text.setText(book.toString() + "\n");
                }
            }
        });

        DB.create(DatabaseTables.LIVRO, new JSONObject());

        DB.edit(DatabaseTables.EDITORA, 5, new JSONObject());

        DB.delete(DatabaseTables.CATEGORIA, 3);
    }
}