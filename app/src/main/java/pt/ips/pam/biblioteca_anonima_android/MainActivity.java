package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pt.ips.pam.biblioteca_anonima_android.db.Livro;
import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.ips.pam.biblioteca_anonima_android.db.AuthRequest;
import pt.ips.pam.biblioteca_anonima_android.db.DatabaseRequest;
import pt.ips.pam.biblioteca_anonima_android.db.DatabaseTables;
import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;
import pt.ips.pam.biblioteca_anonima_android.db.VolleyHandler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStorage();
        JSONObject livro = new JSONObject();
        JSONArray autores = new JSONArray();
        JSONArray Editoras = new JSONArray();
        try {
            livro.put("Titulo", "Harry Potter e a Pedra filosofal");
            livro.put("ISBN", "9780545069670");
            livro.put("Numero_Paginas", 260);
            livro.put("IDEditora", 2);
            livro.put("Capa", "https://google.com");
            autores.put(3);
            autores.put(1);
            livro.put("IDAutores", autores);
            Editoras.put(7);
            Editoras.put(5);
            Editoras.put(18);
            Editoras.put(10);
        }
        catch (JSONException e) {
            Log.d("JSONException", e.toString());
        }
        DatabaseRequest DB = new DatabaseRequest(getApplicationContext()); //<-- contexto da atividade
        DB.create(DatabaseTables.LIVRO, livro, null);
        DB.edit(DatabaseTables.LIVRO,1,livro,null);
        //lista para recyclerView
        List<Object> lista=new ArrayList<Object>();
        SQLiteStorage SQLite = new SQLiteStorage(getApplicationContext());
        lista.add(SQLite.getBook(1));
        RecyclerView recyclerView = findViewById(R.id.Recicla_livros);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ListaItemsAdapter adapter = new ListaItemsAdapter(lista, new
                ListaItemsAdapter.OnItemClickListener() {
                    @Override
                    //metodo que muda o layout no click do objeto na recyclerView
                    //e coloca o nome,imagem e autores nas textviews e imageviews vazias no layout livro_view_holder
                    public void onItemClick(Object item) {
                        if (item instanceof Livro) {
                            Livro livro = (Livro) item;
                            //cria um bundle para enviar o nome,autores e foto de um livro para
                            //a LivroActivity
                            Bundle bundle = new Bundle();
                            bundle.putString("nome", livro.getNome());
                            bundle.putString("Autores", livro.getAutores());
                            bundle.putInt("foto", livro.getFoto());
                            bundle.putString("categoria",livro.getCategoria());
                            Intent intent = new Intent(MainActivity.this, LivroActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
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