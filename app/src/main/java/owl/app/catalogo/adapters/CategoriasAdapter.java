package owl.app.catalogo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import owl.app.catalogo.R;
import owl.app.catalogo.models.Categorias;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.ViewHolder> {

    private List<Categorias> categorias;
    private  int layout;
    private  OnClickListener listener;
    private  OnLongClickListener listenerLong;

    private Context context;

    public  CategoriasAdapter(List<Categorias>categorias, int layout, OnClickListener listener, OnLongClickListener listenerLong){
        this.categorias = categorias;
        this.layout = layout;
        this.listener = listener;
        this.listenerLong = listenerLong;
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        context = viewGroup.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(categorias.get(i),listener, listenerLong);
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo;
        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textViewtituloCategoria);
        }

        public  void bind(final Categorias categorias, final OnClickListener listener, final OnLongClickListener listenerLong){

            titulo.setText(categorias.getTitulo());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(categorias, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listenerLong.onLongItemClick(categorias, getAdapterPosition());
                    return true;
                }
            });


        }
    }



    public interface OnClickListener{
        void onItemClick(Categorias categorias, int position);
    }

    public  interface  OnLongClickListener{
        void onLongItemClick(Categorias categorias, int position);
    }
}
