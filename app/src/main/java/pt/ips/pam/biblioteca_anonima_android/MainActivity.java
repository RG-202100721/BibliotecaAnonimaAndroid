package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
        /*
        DB.getBooks(new VolleyHandler.callback() {
            @Override
            public void onSuccess(JSONArray data) throws JSONException {
                TextView text = findViewById(R.id.text);
                text.setText("");
                for (int i = 0; i < data.length(); i++){
                    JSONObject book = data.getJSONObject(i);
                    text.append(book + "\n");
                }
            }
        });*/

        JSONObject livro = new JSONObject();
        JSONArray autores = new JSONArray();
        try {
            livro.put("Titulo", "test");
            livro.put("ISBN", "16453138468");
            livro.put("Numero_Paginas", 543);
            livro.put("IDEditora", 2);
            livro.put("Capa", "https://google.com");
            livro.put("IDAutores", 1);
            livro.put("IDCategorias", 14);
        }
        catch (JSONException e) {
            Log.d("volleyError", e.toString());
        }

        DB.create(DatabaseTables.LIVRO, livro);
        //DB.edit(DatabaseTables.EDITORA, 5, livro);

        //DB.delete(DatabaseTables.LIVRO, 8);
    }
}