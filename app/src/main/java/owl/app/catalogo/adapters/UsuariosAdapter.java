package owl.app.catalogo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import owl.app.catalogo.R;
import owl.app.catalogo.api.Api;
import owl.app.catalogo.models.Usuarios;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {

    private List<Usuarios> usuarios;
    private  int layout;
    private OnClickListener listener;
    private Context context;

    public  UsuariosAdapter(List<Usuarios> usuarios, int layout, OnClickListener listener){
        this.usuarios = usuarios;
        this.layout = layout;
        this.listener = listener;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        context = viewGroup.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(usuarios.get(i),  listener);
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView usuario;
        private  TextView  role;
        private ImageView  imageUsuario;


        public ViewHolder(View itemView) {
            super(itemView);
            usuario = itemView.findViewById(R.id.textViewNameUsuario);
            role = itemView.findViewById(R.id.textViewRoleUsuario);
            imageUsuario = itemView.findViewById(R.id.imageViewUsuarios);
        }

        public  void bind(final Usuarios usuarios, final OnClickListener listener){

            usuario.setText(usuarios.getUsuario());
            role.setText(usuarios.getRole());

            if (usuarios.getRole().equals("administrador")){
                Picasso.get().load(R.drawable.administrador).fit().into(imageUsuario);
            }else {
                Picasso.get().load(R.drawable.cliente).fit().into(imageUsuario);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(usuarios,getAdapterPosition());
                }
            });

        }
    }

    public  interface OnClickListener{
        void onItemClick(Usuarios usuarios, int position);
    }
}
