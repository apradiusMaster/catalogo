package owl.app.catalogo.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import owl.app.catalogo.R;
import owl.app.catalogo.adapters.ProductosAdapter;
import owl.app.catalogo.api.Api;
import owl.app.catalogo.api.RequestHandler;
import owl.app.catalogo.models.Productos;
import owl.app.catalogo.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    List<Productos> productosList;

    private MenuItem loginMenuItem;
    private MenuItem singIntMenuITem;
    private MenuItem administrativoMenuItem;
    private MenuItem perfilMenuItem;
    private MenuItem logOutMenuItem;

    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerViewProductos);
        productosList = new ArrayList<>();
        readProductos();
        setMyToolbar();
    }

    private  void setMyToolbar(){
        myToolbar = (Toolbar) findViewById(R.id.toolbarProductos);
        setSupportActionBar(myToolbar);
    }

    private void readProductos(){
        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_READ_PRODUCTOS, null, Api.CODE_GET_REQUEST);
        request.execute();
    }

    private void refreshContenidoList(JSONArray contenido) throws JSONException{
        //limpiar las noticias anteriores
        productosList.clear();

        //recorrer todos los elementos de la matriz json
        //del json que recibimos la respuesta

        for(int i = 0; i < contenido.length(); i++){
            //obtener el json de nuestros productos
            JSONObject obj = contenido.getJSONObject(i);

            //añadiendo los productos de nuestro json a la clase productos
            productosList.add(new Productos(
                    obj.getInt("id"),
                    obj.getString("titulo"),
                    obj.getString("descripcion"),
                    obj.getString("contenido"),
                    obj.getString("imagen"),
                    obj.getDouble("precio"),
                    obj.getDouble("calificacion"),
                    obj.getInt("categoria")
            ));
        }

        //crear el adaptador y configurar en la vista nuestra lista de informacion que llega en el formato json
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new ProductosAdapter(productosList, R.layout.card_view_productos, new ProductosAdapter.OnClickListener() {
            @Override
            public void onItemClick(Productos productos, int position) {
                Toast.makeText(MainActivity.this, "Click corto", Toast.LENGTH_SHORT).show();
            }
        }, new ProductosAdapter.OnLongClickListener() {
            @Override
            public void onItemClick(Productos productos, int position) {
                Toast.makeText(MainActivity.this, "Click largo", Toast.LENGTH_SHORT).show();

            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);

        loginMenuItem = menu.findItem(R.id.loginActionBar);
        singIntMenuITem = menu.findItem(R.id.singInActionBar);
        administrativoMenuItem  = menu.findItem(R.id.administrativoActionBar);
        perfilMenuItem = menu.findItem(R.id.perfilActionBar);
        logOutMenuItem = menu.findItem(R.id.logOutActionBar);

        if (SharedPrefManager.getmInstance(MainActivity.this).isLoggedIn()){

            if (SharedPrefManager.getmInstance(MainActivity.this).getUser().getRole().equals("administrador")){
                loginMenuItem.setVisible(false);
                singIntMenuITem.setVisible(false);
                administrativoMenuItem.setVisible(true);
                perfilMenuItem.setVisible(false);
                logOutMenuItem.setVisible(true);

            }
            else {
                loginMenuItem.setVisible(false);
                singIntMenuITem.setVisible(false);
                administrativoMenuItem.setVisible(false);
                perfilMenuItem.setVisible(true);
                logOutMenuItem.setVisible(true);
            }

        } else {
                loginMenuItem.setVisible(true);
                singIntMenuITem.setVisible(true);
                administrativoMenuItem.setVisible(false);
                perfilMenuItem.setVisible(false);
                logOutMenuItem.setVisible(false);
        }



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.loginActionBar:
                startActivity( new Intent( MainActivity.this, LoginActivity.class));
                return true;

            case  R.id.singInActionBar:
                startActivity(new Intent(MainActivity.this, RegistraseActivity.class));
                return  true;

            case R.id.logOutActionBar:
                 SharedPrefManager.getmInstance(MainActivity.this).logOut();
                 return true;

            case  R.id.administrativoActionBar:
                startActivity(new Intent(MainActivity.this, AdministradorActivity.class));

            case  R.id.perfilActionBar:
                startActivity(new Intent(MainActivity.this, PerfilActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //clase interna para realizar la solicitud de red extendiendo un AsyncTask
    private class PerformNetworkRequest extends AsyncTask<Void, Void, String>{

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

}
