package ipn.docentes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class reportes extends AppCompatActivity {

    EditText descripcionEditText;
    Spinner spinnerLabs;
    Button btnReportar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        descripcionEditText = findViewById(R.id.repo);
        spinnerLabs = findViewById(R.id.spinner_labs);
        btnReportar = findViewById(R.id.btn_reportar);

        btnReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnviarReporte();
            }
        });
    }

    public void EnviarReporte(){
        String descripcion = descripcionEditText.getText().toString();
        String idLaboratorio = spinnerLabs.getSelectedItem().toString();

        String url= "http://192.168.100.40/Administrador/insert_report.php?descripcion="+descripcion+"&id_laboratorio"+idLaboratorio;

        RequestQueue servicio= Volley.newRequestQueue(this);
        StringRequest respuesta= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Reporte enviado con exito", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error de conexion",Toast.LENGTH_SHORT).show();
            }
        });
        servicio.add(respuesta);
    }
}