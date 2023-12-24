package ipn.docentes;


import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    EditText editcorreo, editpassword;
    Button btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editcorreo=findViewById(R.id.user);
        editpassword=findViewById(R.id.pwd);
        btnlogin=findViewById(R.id.buttonL);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarUsuario("http://192.168.100.40/Administrador/validar_usuario.php");
            }
        });
    }

    private void validarUsuario(String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta del servidor", response);
                if(!response.trim().isEmpty()){
                    try {
                        JSONObject userData = new JSONObject(response);
                        // Almacena los datos del usuario en SharedPreferences
                        guardarDatosUsuario(userData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent= new Intent(getApplicationContext(),menu1.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(MainActivity.this,"Usuario no encontrado",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String responseBody = null;
                if (error.networkResponse != null && error.networkResponse.data != null) {
                    responseBody = new String(error.networkResponse.data);
                }
                Toast.makeText(MainActivity.this, "Error: " + responseBody, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros= new HashMap<String,String>();
                parametros.put("correo",editcorreo.getText().toString());
                parametros.put("contraseña",editpassword.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    // Método para guardar los datos del usuario en SharedPreferences
    private void guardarDatosUsuario(JSONObject userData) {
        SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        try {
            editor.putString("correo", userData.getString("correo"));
            // Agrega más campos según sea necesario
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.apply();
    }
}