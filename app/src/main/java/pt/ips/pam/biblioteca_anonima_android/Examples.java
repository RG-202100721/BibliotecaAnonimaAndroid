//para apagar antes da apresentação final

package pt.ips.pam.biblioteca_anonima_android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Examples {

    private DatabaseRequest DB = new DatabaseRequest(null); //<-- contexto da atividade em que está a ser usado (null porque isto é só um exemplo)

    public void DB_Operations() {
        //exemplos de uso da classe DatabaseRequest (Pedidos CRUD à API).
        //se quiserem fazer uma operação só depois da outra ter terminado, têm de fazer-la no callback da 1º

        JSONObject livro = new JSONObject();
        JSONArray autores = new JSONArray();
        JSONArray categorias = new JSONArray();
        try {
            livro.put("Titulo", "final test");
            livro.put("ISBN", "16453138468");
            livro.put("Numero_Paginas", 543);
            livro.put("IDEditora", 2);
            livro.put("Capa", "https://google.com");
            autores.put(3);
            autores.put(1);
            livro.put("IDAutores", autores);
            categorias.put(7);
            categorias.put(5);
            categorias.put(18);
            categorias.put(10);
            livro.put("IDCategorias", categorias);
        }
        catch (JSONException e) {
            Log.d("volleyError", e.toString());
        }

        DB.create(DatabaseTables.LIVRO, livro, null);
        DB.edit(DatabaseTables.LIVRO, 8, livro, null);
        DB.delete(DatabaseTables.LIVRO, 10, new VolleyHandler.normalCallback() {
            @Override
            public void onSuccess() {
                //fazer algo depois da operação
                //ou não... é com vocês
            }
        });
    }

    public void SQLite_Operations() {
        //exemplos de uso da classe SQLiteStorage (Pedidos à base de dados local em SQLite).
        //utilizem estas funções para mostrar os dados ao utilizador/admin

        DatabaseRequest DB = new DatabaseRequest(null); //<-- contexto da atividade em que está a ser usado (null porque isto é só um exemplo)
        SQLiteStorage SQLite = new SQLiteStorage();

        SQLite.getBooks();
        SQLite.getBook(1);

        SQLite.getAuthors();
        SQLite.getAuthor(2);

        SQLite.getCategories();
        SQLite.getCategory(3);

        SQLite.getPublishers();
        SQLite.getPublisher(4);
    }
}
