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
import owl.app.catalogo.models.Ventas;

public class VentasAdapter extends RecyclerView.Adapter<VentasAdapter.ViewHolder> {

    private List<Ventas> ventas;
    private  int layout;
    private Context context;


    public VentasAdapter(List<Ventas> ventas, int layout){
        this.ventas = ventas;
        this.layout = layout;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(layout,  viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        context = viewGroup.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.bind(ventas.get(i));
    }

    @Override
    public int getItemCount() {
        return ventas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView usuarios;
        private TextView productos;
        private  TextView costos;
        private  TextView fechas;
        private ImageView imagen;

        public ViewHolder(View itemView) {
            super(itemView);

            usuarios = itemView.findViewById(R.id.textViewUsuarioVentas);
            productos = itemView.findViewById(R.id.textViewProductosVentas);
            costos  = itemView.findViewById(R.id.textViewCostoVentas);
            fechas = itemView.findViewById(R.id.textViewFechaVentas);
            imagen = itemView.findViewById(R.id.imageViewVentas);
        }

        public  void  bind(final Ventas ventas){
            String precio = String.valueOf(ventas.getCosto());

            usuarios.setText(ventas.getUsuario());
            productos.setText(ventas.getProductos());
            costos.setText(precio);
            fechas.setText(ventas.getFecha());

            Picasso.get().load(Api.GALERIA + ventas.getRuta()).fit().into(imagen);
        }
    }
}
