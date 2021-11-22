package owl.app.catalogo.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import owl.app.catalogo.R;
import owl.app.catalogo.adapters.CategoriasAdapter;
import owl.app.catalogo.api.Api;
import owl.app.catalogo.api.RequestHandler;
import owl.app.catalogo.models.Categorias;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriasFragment extends Fragment implements  View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private RecyclerView  mRecyclerView;
    private  RecyclerView.Adapter mAdapter;
    private  RecyclerView.LayoutManager mmLayoutManager;

    private EditText titulo;
    private Button boton;

    private  boolean isUpdate = false;

    private List<Categorias> categoriasList;

    public CategoriasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoriasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoriasFragment newInstance(String param1, String param2) {
        CategoriasFragment fragment = new CategoriasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_categorias, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerViewCategoria);
        titulo = view.findViewById(R.id.editTextAddUpdateCategorias);
        boton = view.findViewById(R.id.buttonAddUpdateCategoria);
        boton.setOnClickListener(this);
        boton.setText(getString(R.string.agregar_label));

        categoriasList = new ArrayList<>();
        readCategoria();

        return view;
    }

    public void createCategoria(){

    }

    public void readCategoria(){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_CATEGORIA,null, Api.CODE_GET_REQUEST);
        request.execute();
    }

    public void updateCategoria(){

    }

    public  void deleteCategoria(){

    }

    public  void refreshContenidoList(JSONArray contenido) throws  JSONException{
        categoriasList.clear();

        for (int i= 0; i< contenido.length(); i++){

            JSONObject obj = contenido.getJSONObject(i);

            categoriasList.add(new Categorias(
                obj.getInt("id"),
                obj.getString("titulo")

            ));
        }

        //crear el adaptador y configurarlo en la vista de la lista

        mmLayoutManager = new LinearLayoutManager(getContext());

        mAdapter = new CategoriasAdapter(categoriasList, R.layout.list_view_categorias, new CategoriasAdapter.OnClickListener() {
            @Override
            public void onItemClick(Categorias categorias, int position) {
                Toast.makeText(getContext(), "click corto", Toast.LENGTH_SHORT).show();
            }
        },
                new CategoriasAdapter.OnLongClickListener() {
                    @Override
                    public void onLongItemClick(Categorias categorias, int position) {
                        Toast.makeText(getContext(), "click largo", Toast.LENGTH_SHORT).show();
                    }
                });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mmLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }


    //clase interna para realizar la solicitud de red extendiendo un AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String> {

        //la url donde nececitamos enviar la solicitud
        String url;

        //parametros
        HashMap<String, String> params;

        //el codigo de solicitud para definir si se trata de un GET o POST
        int requestCode;

        //contructor para inicializar los valores
        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode){
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        //este metodo dará la respuesta de la petición

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                if(!object.getBoolean("error")){
                    //refrescar la lista despues de cada operación
                    //para que obtengamos una lista actualizada
                    refreshContenidoList(object.getJSONArray("contenido"));
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        //la operacion de red se realizará en segundo plano
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler = new RequestHandler();

            if(requestCode == Api.CODE_POST_REQUEST)
                return requestHandler.sendPostRequest(url, params);

            if ((requestCode == Api.CODE_GET_REQUEST))
                return requestHandler.sendGetRequest(url);

            return null;
        }
    }


    @Override
    public void onClick(View view) {

    }
}