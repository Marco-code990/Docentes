package ipn.docentes;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Profile extends AppCompatActivity {
    private TextView nameTV, apTV, amTV,correoTV, tipoTV;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Mostrar();

        SharedPreferences preferences= getSharedPreferences("user_data", MODE_PRIVATE);
        String correo_usuario= preferences.getString("correo", "");

        buscarUsuarios("http://192.168.100.148/Administrador/obtener_usuario.php",correo_usuario);
    }

    public void Mostrar(){
        // Asigna los EditText en tu XML a las variables correspondientes
        nameTV = findViewById(R.id.name);
        apTV = findViewById(R.id.AP);
        amTV = findViewById(R.id.AM);
        correoTV = findViewById(R.id.correo);
        tipoTV = findViewById(R.id.tipo);
    }

    public void buscarUsuarios(String URL, String correo){

        String urlcorreo= URL+"?correo="+correo;
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(urlcorreo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        nameTV.setText(jsonObject.getString("nombre"));
                        apTV.setText(jsonObject.getString("apellidop"));
                        amTV.setText(jsonObject.getString("apellidom"));
                        correoTV.setText(jsonObject.getString("correo"));
                        tipoTV.setText(jsonObject.getString("tipo_usuario"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error de conexión",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}

