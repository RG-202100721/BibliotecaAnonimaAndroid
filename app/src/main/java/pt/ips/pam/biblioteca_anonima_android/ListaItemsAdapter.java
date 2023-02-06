package pt.ips.pam.biblioteca_anonima_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ListaItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    class LivroViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView foto;
        TextView autores;
        TextView ISBN;
        LivroViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.book_cover);
            nome = view.findViewById(R.id.book_title);
            autores = view.findViewById(R.id.book_authors);
            ISBN = view.findViewById(R.id.book_isbn);
        }
        public void bind(final int pos, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(pos);
                }
            });
        }
    }
    class AutorViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        TextView country;
        AutorViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.author_title);
            country = view.findViewById(R.id.author_country);
        }
    }
    class EditoraViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView foto;
        TextView country;
        EditoraViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.publisher_cover);
            nome = view.findViewById(R.id.publisher_title);
            country = view.findViewById(R.id.publisher_country);
        }
    }
    class CategoriaViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        CategoriaViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.category_title);
        }
    }

    private final List<JSONObject> items;
    private final OnItemClickListener itemClickListener;
    private final Context context;
    public ListaItemsAdapter(List<JSONObject> items, @Nullable OnItemClickListener clickListener, Context context) {
        this.items = items;
        this.itemClickListener = clickListener;
        this.context = context;
    }
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (context.getClass().getSimpleName()) {
            case "MainActivity":
                View itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.book_list, parent, false);
                return new LivroViewHolder(itemView);
            case "AutorActivity":
                View itemView2 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.author_list, parent, false);
                return new AutorViewHolder(itemView2);
            case "EditoraActivity":
                View itemView3 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.publisher_list, parent, false);
                return new EditoraViewHolder(itemView3);
            default:
                View itemView4 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_list, parent, false);
                return new CategoriaViewHolder(itemView4);
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (context.getClass().getSimpleName()) {
            case "MainActivity":
                LivroViewHolder livroViewHolder = (LivroViewHolder) holder;

                JSONObject item = items.get(position);
                try {
                    livroViewHolder.nome.setText(item.getString("Titulo"));

                    Picasso.get().load(item.getString("Capa")).into(livroViewHolder.foto);

                    StringBuilder text = new StringBuilder();
                    JSONArray autores = item.getJSONArray("IDAutores");
                    for (int i = 0; i < autores.length(); i++) text.append(autores.getJSONObject(i).getString("Nome")).append(", ");
                    livroViewHolder.autores.setText(text.delete(text.length() - 2, text.length()).toString());

                    livroViewHolder.ISBN.setText(item.getString("ISBN"));
                }
                catch (JSONException e) {
                    livroViewHolder.nome.setText("error");
                }

                livroViewHolder.bind(position, itemClickListener);
                break;

            case "AutorActivity":
                AutorViewHolder autorViewHolder = (AutorViewHolder) holder;

                JSONObject item2 = items.get(position);
                try {
                    autorViewHolder.nome.setText(item2.getString("Nome"));
                    autorViewHolder.country.setText(item2.getString("Pais"));
                }
                catch (JSONException e) {
                    autorViewHolder.nome.setText("error");
                }
                break;

            case "EditoraActivity":
                EditoraViewHolder editoraViewHolder = (EditoraViewHolder) holder;

                JSONObject item3 = items.get(position);
                try {
                    editoraViewHolder.nome.setText(item3.getString("Nome"));
                    editoraViewHolder.country.setText(item3.getString("Pais"));
                    Picasso.get().load(item3.getString("Logo")).into(editoraViewHolder.foto);
                }
                catch (JSONException e) {
                    editoraViewHolder.nome.setText("error");
                }
                break;

            case "CategoriaActivity":
                CategoriaViewHolder categoriaViewHolder = (CategoriaViewHolder) holder;

                JSONObject item4 = items.get(position);
                try {
                    categoriaViewHolder.nome.setText(item4.getString("Nome"));
                }
                catch (JSONException e) {
                    categoriaViewHolder.nome.setText("error");
                }
                break;
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}