package owl.app.catalogo.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import owl.app.catalogo.R;
import owl.app.catalogo.api.Api;
import owl.app.catalogo.api.RequestHandler;

public class RegistraseActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mEditTextUserRegister;
    EditText mEditTextMailRegister;
    EditText mEditTextPasswordRegister;
    Button mButtonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrase);
        mEditTextUserRegister = findViewById(R.id.editTextUsuarioRegistro);
        mEditTextMailRegister = findViewById(R.id.editTextMailRegistro);
        mEditTextPasswordRegister = findViewById(R.id.editTextPasswordRegistro);
        mButtonRegister = findViewById(R.id.buttonRegistro);

        mButtonRegister.setOnClickListener(this);

    }

    private  void createUsuario(){
        String user = mEditTextUserRegister.getText().toString().trim();
        String mail = mEditTextMailRegister.getText().toString().trim();
        String password = mEditTextPasswordRegister.getText().toString().trim();

        if(TextUtils.isEmpty(user)){
            mEditTextUserRegister.setError(getString(R.string.suser_name_label));
            mEditTextUserRegister.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mail)){
            mEditTextMailRegister.setError(getString(R.string.mail_label));
            mEditTextMailRegister.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            mEditTextPasswordRegister.setError(getString(R.string.password_label));
            mEditTextPasswordRegister.requestFocus();
            return;
        }
        HashMap<String,String> params = new HashMap<>();
        params.put("usuario", user);
        params.put("mail", mail);
        params.put("password", password);
        params.put("role", "cliente");

        PerformNetworkRequest request = new PerformNetworkRequest(Api.URL_CREATE_USUARIO, params, Api.CODE_POST_REQUEST);
        request.execute();

        Toast.makeText(RegistraseActivity.this, getString(R.string.message_register), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        createUsuario();
    }

    //clase interna para realizar la solicitud de red extendiendo un AsyncTask
    private class PerformNetworkRequest  extends AsyncTask<Void,Void,String>{

        //la  url donde necesitamos enviar la solicitud
        String url;

        //parametros
        HashMap<String,String> params;

        // el codigo de solicitud para definir si se trata de un GET o POST
        int requestCode;

        //constructor para inicializar los valores

        PerformNetworkRequest(String url, HashMap<String, String> params, int requestCode){
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }


        // la operacion en red se realizara en segundo plano
        @Override
        protected String doInBackground(Void... voids) {

            RequestHandler requestHandler  = new RequestHandler();

            if (requestCode == Api.CODE_POST_REQUEST)
            return  requestHandler.sendPostRequest(url,params);

            if (requestCode == Api.CODE_GET_REQUEST)
            return  requestHandler.sendGetRequest(url);

            return null;

        }
    }



}