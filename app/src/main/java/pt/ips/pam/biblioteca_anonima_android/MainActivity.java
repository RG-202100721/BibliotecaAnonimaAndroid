package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStorage();

        SQLiteStorage SQLite = new SQLiteStorage(MainActivity.this);

        //lista para recyclerView
        List<JSONObject> lista = new ArrayList<JSONObject>();
        try {
            JSONArray array = SQLite.getBooks();
            for (int i = 0; i < array.length(); i++) lista.add(array.getJSONObject(i));
        }
        catch (JSONException e) { e.printStackTrace(); }

        RecyclerView recyclerView = findViewById(R.id.Recicla_livros);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ListaItemsAdapter adapter = new ListaItemsAdapter(lista, new
                ListaItemsAdapter.OnItemClickListener() {
                    @Override
                    //metodo que muda o layout no click do objeto na recyclerView
                    //e coloca o nome,imagem e autores nas textviews e imageviews vazias no layout livro_view_holder
                    public void onItemClick(int pos) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", pos);
                        Intent intent = new Intent(MainActivity.this, LivroActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
        recyclerView.setAdapter(adapter);
    }
    private void checkStorage() {
        //não mexer nesta função, sff
        SQLiteStorage SQLite = new SQLiteStorage(MainActivity.this);
        SQLite.checkLocalDB();
    }
}