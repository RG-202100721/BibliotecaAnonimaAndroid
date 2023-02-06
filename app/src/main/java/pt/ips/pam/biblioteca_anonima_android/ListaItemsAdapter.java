package pt.ips.pam.biblioteca_anonima_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ListaItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }
    class LivroViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView foto;
        TextView autores;
        TextView ISBN;
        LivroViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.book_cover);
            nome = view.findViewById(R.id.book_title);
            autores = view.findViewById(R.id.book_author);
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
    private final List<JSONObject> items;
    private final OnItemClickListener itemClickListener;
    public ListaItemsAdapter(List<JSONObject> items, OnItemClickListener clickListener) {
        this.items = items;
        this.itemClickListener = clickListener;
    }
    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_list, parent, false);
        return new LivroViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
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
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}